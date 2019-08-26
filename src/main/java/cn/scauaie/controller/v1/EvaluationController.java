package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.ErrorHandler;
import cn.scauaie.aspect.annotation.EvaluationLog;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.constant.TokenType;
import cn.scauaie.converter.EvaluationQueryConverter;
import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.EvaluationLogAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.query.EvaluationQuery;
import cn.scauaie.model.vo.EvaluationLogVO;
import cn.scauaie.model.vo.EvaluationVO;
import cn.scauaie.result.Result;
import cn.scauaie.service.EvaluationLogService;
import cn.scauaie.service.EvaluationService;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

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
    private EvaluationLogService evaluationLogService;

    @Autowired
    private EvaluationQueryConverter evaluationQueryConverter;

    @Autowired
    private Mapper mapper;

    /**
     * 创建Evaluation并返回Evaluation
     *
     * @param evaluationAO 评价表信息
     * @return EvaluationVO
     * @success: HttpStatus.CREATED
     * @errors: FORBIDDEN_SUB_USER: The specified action is not available for you.
     * INVALID_PARAMETER_NOT_FOUND: The form does not exist.
     * INTERNAL_ERROR: Insert evaluation failed.
     * @bindErrors INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    @ErrorHandler
    @EvaluationLog
    public Object post(HttpServletRequest request, @Validated EvaluationAO evaluationAO) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        evaluationAO.setIid(tokenAO.getId());
        Result<EvaluationAO> result = evaluationService.checkDepAndSaveEvaluationAndSendInterviewResults(evaluationAO);

        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), EvaluationVO.class);
    }

    /**
     * 获取Evaluation
     *
     * @param id 评价表编号
     * @return EvaluationVO
     * @success: HttpStatus.OK
     * @errors: FORBIDDEN_SUB_USER
     * @bindErrors INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    @ErrorHandler
    public Object get(HttpServletRequest request,
                      @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
                      @PathVariable Integer id) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");

        Result<EvaluationAO> result = evaluationService.getEvaluation(id, tokenAO.getId());
        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), EvaluationVO.class);
    }

    /**
     * 查询Evaluation
     *
     * @param request  HttpServletRequest
     * @param pageNum  页码
     * @param pageSize 页条数
     * @param q        查询参数
     * @return EvaluationVOList
     * @success: HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    @ErrorHandler
    public Object get(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "10") Integer pageSize, String q) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        EvaluationQuery query = evaluationQueryConverter.convert(q);
        query.setInterviewerDep(tokenAO.getDep());
        query.setPageSize(pageSize);
        query.setPageNum(pageNum);
        Result<List<EvaluationAO>> result = evaluationService.listEvaluations(query);
        if (!result.isSuccess()) {
            return result;
        }

        return result.getData().stream()
                .map(evaluationAO -> mapper.map(evaluationAO, EvaluationVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 查询EvaluationLog
     *
     * @param request  HttpServletRequest
     * @param pageNum  页码
     * @param pageSize 页条数
     * @return EvaluationLogVOList
     * @success: HttpStatus.OK
     * @errors: INVALID_PARAMETER_NOT_FOUND: Not found.
     */
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    @ErrorHandler
    public Object getEvaluationLog(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Result<List<EvaluationLogAO>> result = evaluationLogService.listEvaluationLogs(tokenAO.getId(), pageNum, pageSize);
        if (!result.isSuccess()) {
            return result;
        }

        return result.getData().stream()
                .map(evaluationLogAO -> mapper.map(evaluationLogAO, EvaluationLogVO.class))
                .collect(Collectors.toList());
    }

}
