package cn.scauaie.service.impl;

import cn.scauaie.converter.EvaluationQueryConverter;
import cn.scauaie.dao.EvaluationMapper;
import cn.scauaie.dao.FormMapper;
import cn.scauaie.dao.InterviewerMapper;
import cn.scauaie.error.ErrorCode;
import cn.scauaie.exception.ProcessingException;
import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.dao.EvaluationDO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.dao.InterviewerDO;
import cn.scauaie.model.query.EvaluationQuery;
import cn.scauaie.service.EvaluationService;
import cn.scauaie.service.FormService;
import cn.scauaie.service.InterviewerService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private FormService formService;

    @Autowired
    private InterviewerService interviewerService;

    @Autowired
    private EvaluationQueryConverter evaluationQueryConverter;

    @Override
    public EvaluationAO saveEvaluation(EvaluationAO evaluationAO) {
        int count = formService.getCountById(evaluationAO.getFid());
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The form does not exist.");
        }

        EvaluationDO evaluationDO = new EvaluationDO();
        BeanUtils.copyProperties(evaluationAO, evaluationDO);
        count = evaluationMapper.insertSelective(evaluationDO);
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Insert evaluation failed.");
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
    public EvaluationAO getEvaluation(Integer id) {
        EvaluationDO evaluationDO = evaluationMapper.selectByPrimaryKey(id);

        EvaluationAO evaluationAO = new EvaluationAO();
        BeanUtils.copyProperties(evaluationDO, evaluationAO);

        FormAO formAO = formService.getFormAOById(evaluationDO.getFid());
        InterviewerAO interviewerAO = interviewerService.getInterviewer(evaluationDO.getIid());

        evaluationAO.setForm(formAO);
        evaluationAO.setInterviewer(interviewerAO);

        return evaluationAO;
    }

    /**
     * 搜索评价表列表
     *
     * @param pageNum 页码
     * @param pageSize 页条数
     * @param q 搜索参数
     * @return List<EvaluationAO>
     */
    public List<EvaluationAO> listEvaluations(Integer pageNum, Integer pageSize, String q) {
        EvaluationQuery query = evaluationQueryConverter.convert(q);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        return listEvaluations(query);
    }

    /**
     * 搜索评价表列表
     *
     * @param query EvaluationQuery
     * @return List<EvaluationAO>
     */
    private List<EvaluationAO> listEvaluations(EvaluationQuery query) {
        System.out.println(query);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<EvaluationDO> evaluationDOList = evaluationMapper.listEvaluationsByQuery(query);
        if (evaluationDOList.size() < 1) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        List<EvaluationAO> evaluationAOList = new ArrayList<>(evaluationDOList.size());
        for (EvaluationDO evaluationDO : evaluationDOList) {
            EvaluationAO evaluationAO = new EvaluationAO();
            BeanUtils.copyProperties(evaluationDO, evaluationAO);

            FormAO formAO = formService.getFormAOById(evaluationDO.getFid());
            InterviewerAO interviewerAO = interviewerService.getInterviewer(evaluationDO.getIid());

            evaluationAO.setForm(formAO);
            evaluationAO.setInterviewer(interviewerAO);

            evaluationAOList.add(evaluationAO);
        }
        return evaluationAOList;
    }

}
