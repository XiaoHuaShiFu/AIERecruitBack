package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.ErrorHandler;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.constant.TokenType;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.bo.QueuerBO;
import cn.scauaie.model.query.QueuerQuery;
import cn.scauaie.model.vo.QueuerVO;
import cn.scauaie.result.Result;
import cn.scauaie.service.QueuerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 描述: Queue Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-10 21:10
 */
@RestController
@RequestMapping("v1/queuers")
@Validated
public class QueuerController {

    @Autowired
    private QueuerService queuerService;


    /**
     * 创建队列元素
     *
     * @param request HttpServletRequest
     * @return QueuerVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND: Not found.
     * OPERATION_CONFLICT: Request was denied due to conflict, you has been in the queue.
     * OPERATION_CONFLICT: Request was denied due to conflict, you have entered the queue.
     *
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object post(HttpServletRequest request) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Result<QueuerBO> result = queuerService.saveQueuer(tokenAO.getDep(), tokenAO.getId());
        return checkAndReturn(result);
    }

    /**
     * 删除队头元素
     * @param request HttpServletRequest
     *
     * @success:
     * HttpStatus.NO_CONTENT
     *
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    @ErrorHandler
    public Object delete(HttpServletRequest request) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Result<QueuerBO> result = queuerService.checkGapAndDeleteQueuerByDep(tokenAO.getId(), tokenAO.getDep());
        return checkAndReturn(result);
    }

    /**
     * 获取队列排队者信息
     * @param request HttpServletRequest
     * @return Queuer or QueuerList
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND: Not found.
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth
    @ErrorHandler
    public Object get(HttpServletRequest request,
                      @RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "10") Integer pageSize) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        if (tokenAO.getType().equals(TokenType.FORM.name())) {
            Result<QueuerBO> result = queuerService.getQueuerByDepAndFormId(tokenAO.getId(), tokenAO.getDep());
            return checkAndReturn(result);
        }

        QueuerQuery queuerQuery = new QueuerQuery(pageNum, pageSize, tokenAO.getDep());
        Result<List<QueuerBO>> result = queuerService.listQueuersByDep(queuerQuery);
        if (!result.isSuccess()) {
            return result;
        }
        List<QueuerBO> queuerBOList = result.getData();
        List<QueuerVO> queuerVOList = new ArrayList<>(queuerBOList.size());
        for (QueuerBO queuerBO : queuerBOList) {
            QueuerVO queuerVO = new QueuerVO();
            BeanUtils.copyProperties(queuerBO, queuerVO);
            queuerVOList.add(queuerVO);
        }
        return queuerVOList;
    }

    /**
     * 获取队列人数
     * @param request HttpServletRequest
     * @return 队列人数
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(value = "/number",method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth
    @ErrorHandler
    public Object getNumber(HttpServletRequest request) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        return queuerService.getCountByDep(tokenAO.getDep());
    }

    /**
     * 检查result并返回
     *
     * @param result Result<QueuerBO>
     * @return QueuerVO
     */
    private Object checkAndReturn(Result<QueuerBO> result) {
        if (!result.isSuccess()) {
            return result;
        }
        QueuerVO queuerVO = new QueuerVO();
        BeanUtils.copyProperties(result.getData(), queuerVO);
        return queuerVO;
    }

}
