package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.ErrorHandler;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.constant.TokenType;
import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.vo.ResultVO;
import cn.scauaie.result.Result;
import cn.scauaie.service.ResultService;
import cn.scauaie.util.BeanUtils;
import com.github.dozermapper.core.Mapper;
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

    @Autowired
    private Mapper mapper;

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
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    @ErrorHandler
    public Object post(HttpServletRequest request, @Validated ResultAO resultAO) {
        Result<ResultAO> result = resultService.saveResult(resultAO);
        if (!result.isSuccess()) {
            return result;
        }
        return mapper.map(result.getData(), ResultVO.class);
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
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object get(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Result<List<ResultAO>> result = resultService.listResults(pageNum, pageSize, tokenAO.getId());
        if (!result.isSuccess()) {
            return result;
        }

        List<ResultVO> resultVOList = new ArrayList<>(result.getData().size());
        for (ResultAO resultAO : result.getData()) {
            resultVOList.add(mapper.map(resultAO, ResultVO.class));
        }
        return resultVOList;
    }

}
