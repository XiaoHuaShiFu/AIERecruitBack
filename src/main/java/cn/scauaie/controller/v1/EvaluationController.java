package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.InterviewerTokenAuth;
import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.ao.group.GroupFormAOPOST;
import cn.scauaie.model.vo.EvaluationVO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.InterviewerVO;
import cn.scauaie.service.EvaluationService;
import cn.scauaie.service.InterviewerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @InterviewerTokenAuth
    public EvaluationVO post(HttpServletRequest request, @Validated EvaluationAO evaluationAO) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        evaluationAO.setIid(tokenAO.getId());
        EvaluationAO newEvaluationAO = evaluationService.saveEvaluation(evaluationAO);

        EvaluationVO evaluationVO = new EvaluationVO();
        BeanUtils.copyProperties(newEvaluationAO, evaluationVO);

        return evaluationVO;
    }
}
