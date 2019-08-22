package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.ErrorHandler;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.constant.TokenType;
import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.vo.EvaluationVO;
import cn.scauaie.result.Result;
import cn.scauaie.service.EvaluationService;
import cn.scauaie.util.BeanUtils;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.List;

// TODO: 2019/8/23 面试官权限问题
// TODO: 2019/8/23 面试结果自动发送问题


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

    @Autowired
    private Mapper mapper;

    @Autowired
    private BeanUtils beanUtils;

    /**
     * 创建Evaluation并返回Evaluation
     * @param evaluationAO 评价表信息
     * @return EvaluationVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * FORBIDDEN_SUB_USER: The specified action is not available for you.
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
    @ErrorHandler
    public Object post(HttpServletRequest request, @Validated EvaluationAO evaluationAO) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        evaluationAO.setIid(tokenAO.getId());
        Result<EvaluationAO> result = evaluationService.checkDepAndSaveEvaluation(evaluationAO);

        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), EvaluationVO.class);
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
    @ErrorHandler
    public Object get(HttpServletRequest request,
                      @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
                      @PathVariable Integer id) {
        Result<EvaluationAO> result = evaluationService.getEvaluation(id);
        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), EvaluationVO.class);
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
    @ErrorHandler
    public Object get(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize, String q) {
        Result<List<EvaluationAO>> result = evaluationService.listEvaluations(pageNum, pageSize, q);
        if (!result.isSuccess()) {
            return result;
        }

        return beanUtils.mapList(result.getData(), EvaluationVO.class);
    }

}
