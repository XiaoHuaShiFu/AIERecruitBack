package cn.scauaie.service.impl;

import cn.scauaie.dao.EvaluationMapper;
import cn.scauaie.manager.constant.WeChatMpConsts;
import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.model.dao.EvaluationDO;
import cn.scauaie.model.dto.MessageTemplateDataDTO;
import cn.scauaie.model.query.EvaluationQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.*;
import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("evaluationService")
public class EvaluationServiceImpl implements EvaluationService {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private FormService formService;

    @Autowired
    private InterviewerService interviewerService;

    @Autowired
    private QueuerService queuerService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private WeChatMpService weChatMpService;

    @Autowired
    private Mapper mapper;

    /**
     * 检查面试官的部门是否有权评价报名表的部门
     * 并保存面试官评价
     * 并发送面试结果
     *
     * @param evaluationAO 评价
     * @return EvaluationAO
     */
    @Override
    public Result<EvaluationAO> checkDepAndSaveEvaluationAndSendInterviewResults(EvaluationAO evaluationAO) {
        String formDep = formService.getFirstDep(evaluationAO.getFid());
        //此编号的报名表不存在
        if (formDep == null) {
            logger.warn("The form does not exist. interviewerId: {}, formId: {}.",
                    evaluationAO.getIid(), evaluationAO.getFid());
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The form does not exist.");
        }

        String interviewerDep = interviewerService.getDep(evaluationAO.getIid());
        String formQueue = queuerService.getQueueNameByDep(formDep);
        String interviewerQueue = queuerService.getQueueNameByDep(interviewerDep);
        //面试官无权评价此报名表
        if (!formQueue.equals(interviewerQueue)) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<EvaluationAO> result = saveEvaluation(evaluationAO);
        if (!result.isSuccess()) {
            return result;
        }

        //发送面试结果
        FormAO formAO = result.getData().getForm();
        Result<ResultAO> sendInterviewResultResult = resultService.sendInterviewResult(
                formAO.getId(), formAO.getFirstDep(), formAO.getSecondDep(), evaluationAO.getPass());
        //发送失败
        if (!sendInterviewResultResult.isSuccess()) {
            logger.error("Send interview results fail. evaluationId: {}.", result.getData().getIid());
        }

        //发送面试结果模板消息
        HashMap<String, MessageTemplateDataDTO> data = new HashMap<>(7);
        data.put("keyword1", new MessageTemplateDataDTO(formAO.getName()));
        data.put("keyword2", new MessageTemplateDataDTO("第一轮面试"));
        data.put("keyword3", new MessageTemplateDataDTO(evaluationAO.getPass() ? "通过" : "未通过"));
        data.put("keyword4", new MessageTemplateDataDTO(sendInterviewResultResult.getData().getResult()));
        Result sendTemplateMessageResult = weChatMpService.sendTemplateMessage(formAO.getId(),
                WeChatMpConsts.INTERVIEW_RESULT_NOTIFACATION_TEMPLATE_ID, WeChatMpConsts.INDEX_PAGE_PATH, data,
                null);
        if (!sendTemplateMessageResult.isSuccess()) {
            logger.error("Send template message fail. evaluationId: {}.", result.getData().getIid());
        }

        return result;
    }

    /**
     * 保存面试官评价
     *
     * @param evaluationAO 评价
     * @return EvaluationAO
     */
    @Override
    public Result<EvaluationAO> saveEvaluation(EvaluationAO evaluationAO) {
        EvaluationDO evaluationDO = mapper.map(evaluationAO, EvaluationDO.class);
        int count = evaluationMapper.insertSelective(evaluationDO);
        //保存评价失败
        if (count < 1) {
            logger.error("Insert evaluation failed. formId: {}, pass: {}",
                    evaluationAO.getFid(), evaluationAO.getPass());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert evaluation failed.");
        }

        return getEvaluation(evaluationDO.getId());
    }

    /**
     * 获取评价表
     *
     * @param id 评价表编号
     * @return EvaluationAO
     */
    @Override
    public Result<EvaluationAO> getEvaluation(Integer id) {
        EvaluationDO evaluationDO = evaluationMapper.getEvaluation(id);
        if (evaluationDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(assembleEvaluationAOByEvaluationDO(evaluationDO));
    }

    /**
     * 获取评价表，需要面试官与该面试者同部门
     *
     * @param id 评价表编号
     * @param interviewerDep 面试官部门
     * @return EvaluationAO
     */
    @Override
    public Result<EvaluationAO> getEvaluation(Integer id, String interviewerDep) {
        EvaluationDO evaluationDO =
                evaluationMapper.getEvaluationByEvaluationIdAndIfInterviewerDepEqualFormFirstDepOrSecondDep(id, interviewerDep);
        if (evaluationDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(assembleEvaluationAOByEvaluationDO(evaluationDO));
    }

    /**
     * 获取评价表，通过编号和面试官编号
     *
     * @param evaluationId 评价表编号
     * @param interviewerId 面试官编号
     * @return EvaluationAO
     */
    @Override
    public Result<EvaluationAO> getEvaluation(Integer evaluationId, Integer interviewerId) {
        EvaluationDO evaluationDO =
                evaluationMapper.getEvaluationByEvaluationIdAndInterviewerId(evaluationId, interviewerId);
        if (evaluationDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(assembleEvaluationAOByEvaluationDO(evaluationDO));
    }

    /**
     * 搜索评价表列表
     *
     * @param pageNum 页码
     * @param pageSize 页条数
     * @return List<EvaluationAO>
     */
    public Result<List<EvaluationAO>> listEvaluations(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EvaluationDO> evaluationDOList = evaluationMapper.listEvaluations();
        if (evaluationDOList.size() < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(evaluationDOList.stream()
                .map(evaluationDO -> mapper.map(evaluationDO, EvaluationAO.class))
                .collect(Collectors.toList()));
    }

    /**
     * 搜索评价表列表
     *
     * @param query EvaluationQuery
     * @return List<EvaluationAO>
     */
    public Result<List<EvaluationAO>> listEvaluations(EvaluationQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        System.out.println(query);
        List<EvaluationDO> evaluationDOList = evaluationMapper.listEvaluationsByQuery(query);
        if (evaluationDOList.size() < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(evaluationDOList.stream()
                        .map(this::assembleEvaluationAOByEvaluationDO)
                        .collect(Collectors.toList()));
    }

    /**
     * 装配EvaluationDO成EvaluationAO
     *
     * @param evaluationDO EvaluationDO
     * @return EvaluationAO
     */
    private EvaluationAO assembleEvaluationAOByEvaluationDO(EvaluationDO evaluationDO) {
        EvaluationAO evaluationAO = mapper.map(evaluationDO, EvaluationAO.class);

        FormAO formAO = formService.getForm(evaluationDO.getFid());
        InterviewerAO interviewerAO = interviewerService.getInterviewer(evaluationDO.getIid());

        evaluationAO.setForm(formAO);
        evaluationAO.setInterviewer(interviewerAO);
        return evaluationAO;
    }

}
