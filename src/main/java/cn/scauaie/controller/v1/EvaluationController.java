package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.constant.TokenType;
import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.vo.EvaluationVO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.InterviewerVO;
import cn.scauaie.service.EvaluationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: Evaluation Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-15 01:20
 */
@RestController
@RequestMapping("v1/evaluations")
@Validated
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    // TODO: 2019/8/17 面试官评价权判断
    /**
     * 创建Evaluation并返回Evaluation
     * @param evaluationAO 评价表信息
     * @return EvaluationVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND: The form does not exist.
     * INTERNAL_ERROR: Insert evaluation failed.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    public Object post(HttpServletRequest request, @Validated EvaluationAO evaluationAO) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        evaluationAO.setIid(tokenAO.getId());
        EvaluationAO newEvaluationAO = evaluationService.saveEvaluation(evaluationAO);

        EvaluationVO evaluationVO = new EvaluationVO();
        BeanUtils.copyProperties(newEvaluationAO, evaluationVO);

        FormVO formVO = new FormVO();
        BeanUtils.copyProperties(newEvaluationAO.getForm(), formVO);
        evaluationVO.setForm(formVO);

        InterviewerVO interviewerVO = new InterviewerVO();
        BeanUtils.copyProperties(newEvaluationAO.getInterviewer(), interviewerVO);
        evaluationVO.setInterviewer(interviewerVO);

        return evaluationVO;
    }

    /**
     * 获取Evaluation
     * @param id 评价表编号
     * @return EvaluationVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * FORBIDDEN_SUB_USER
     *
     * @bindErrors
     * INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    public Object get(HttpServletRequest request,
                      @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
                      @PathVariable Integer id) {
        EvaluationAO newEvaluationAO = evaluationService.getEvaluation(id);

        EvaluationVO evaluationVO = new EvaluationVO();
        BeanUtils.copyProperties(newEvaluationAO, evaluationVO);
        FormVO formVO = new FormVO();
        BeanUtils.copyProperties(newEvaluationAO.getForm(), formVO);
        evaluationVO.setForm(formVO);
        InterviewerVO interviewerVO = new InterviewerVO();

        BeanUtils.copyProperties(newEvaluationAO.getInterviewer(), interviewerVO);
        evaluationVO.setInterviewer(interviewerVO);

        return evaluationVO;
    }

    /**
     * 查询Evaluation
     * @param request HttpServletRequest
     * @param pageNum 页码
     * @param pageSize 页条数
     * @param q 查询参数
     * @return EvaluationVOList
     *
     * @success:
     * HttpStatus.OK
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    public Object get(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize, String q) {
        List<EvaluationAO> evaluationAOList = evaluationService.listEvaluations(pageNum, pageSize, q);
        List<EvaluationVO> evaluationVOList = new ArrayList<>(evaluationAOList.size());
        for (EvaluationAO evaluationAO : evaluationAOList) {
            EvaluationVO evaluationVO = new EvaluationVO();
            BeanUtils.copyProperties(evaluationAO, evaluationVO);
            FormVO formVO = new FormVO();
            BeanUtils.copyProperties(evaluationAO.getForm(), formVO);
            evaluationVO.setForm(formVO);
            InterviewerVO interviewerVO = new InterviewerVO();

            BeanUtils.copyProperties(evaluationAO.getInterviewer(), interviewerVO);
            evaluationVO.setInterviewer(interviewerVO);

            evaluationVOList.add(evaluationVO);
        }
        return evaluationVOList;
    }

}
