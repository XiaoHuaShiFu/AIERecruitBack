package cn.scauaie.service.impl;

import cn.scauaie.converter.EvaluationQueryConverter;
import cn.scauaie.dao.EvaluationMapper;
import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.dao.EvaluationDO;
import cn.scauaie.model.query.EvaluationQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.EvaluationService;
import cn.scauaie.service.FormService;
import cn.scauaie.service.InterviewerService;
import cn.scauaie.service.QueuerService;
import cn.scauaie.util.BeanUtils;
import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private EvaluationQueryConverter evaluationQueryConverter;

    @Autowired
    private Mapper mapper;

    @Autowired
    private BeanUtils beanUtils;

    /**
     * 检查面试官的部门是否有权评价报名表的部门
     * 并保存面试官评价
     *
     * @param evaluationAO 评价
     * @return EvaluationAO
     */
    @Override
    public Result<EvaluationAO> checkDepAndSaveEvaluation(EvaluationAO evaluationAO) {
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

        return saveEvaluation(evaluationAO);
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
            logger.error("Insert evaluation failed.");
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
     * 搜索评价表列表
     *
     * @param pageNum 页码
     * @param pageSize 页条数
     * @param q 搜索参数
     * @return List<EvaluationAO>
     */
    public Result<List<EvaluationAO>> listEvaluations(Integer pageNum, Integer pageSize, String q) {
        EvaluationQuery query = evaluationQueryConverter.convert(q);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        List<EvaluationAO> evaluationAOList = listEvaluations(query);
        if (evaluationAOList == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        return Result.success(evaluationAOList);
    }

    /**
     * 搜索评价表列表
     *
     * @param query EvaluationQuery
     * @return List<EvaluationAO>
     */
    private List<EvaluationAO> listEvaluations(EvaluationQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<EvaluationDO> evaluationDOList = evaluationMapper.listEvaluationsByQuery(query);
        if (evaluationDOList.size() < 1) {
            return null;
        }

        List<EvaluationAO> list = new ArrayList<>(evaluationDOList.size());
        for (EvaluationDO evaluationDO : evaluationDOList) {
            list.add(assembleEvaluationAOByEvaluationDO(evaluationDO));
        }
        return list;
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
