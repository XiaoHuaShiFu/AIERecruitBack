package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.FormTokenAuth;
import cn.scauaie.aspect.annotation.InterviewerTokenAuth;
import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.vo.EvaluationVO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.InterviewerVO;
import cn.scauaie.model.vo.ResultVO;
import cn.scauaie.service.ResultService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 描述: Result Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:27
 */
@RestController
@RequestMapping("v1/results")
@Validated
public class ResultController {

    @Autowired
    private ResultService resultService;

    /**
     * 创建Result并返回Result
     * @param resultAO 评价表信息
     * @return ResultVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INTERNAL_ERROR: Insert result failed.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
//    @InterviewerTokenAuth
    public ResultVO post(HttpServletRequest request, @Validated ResultAO resultAO) {
        ResultAO newResultAO = resultService.saveResult(resultAO);
        ResultVO resultVO = new ResultVO();
        BeanUtils.copyProperties(newResultAO, resultVO);

        return resultVO;
    }

    /**
     * 获取结果
     * @param request HttpServletRequest
     * @param pageNum 页码
     * @param pageSize 页条数
     * @return ResultVOList
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND: Not found.
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @FormTokenAuth
    public List<ResultVO> get(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        List<ResultAO> resultAOList = resultService.listResults(pageNum, pageSize, tokenAO.getId());
        List<ResultVO> resultVOList = new ArrayList<>(resultAOList.size());
        for (ResultAO resultAO : resultAOList) {
            ResultVO resultVO = new ResultVO();
            BeanUtils.copyProperties(resultAO, resultVO);
            resultVOList.add(resultVO);
        }
        return resultVOList;
    }

}