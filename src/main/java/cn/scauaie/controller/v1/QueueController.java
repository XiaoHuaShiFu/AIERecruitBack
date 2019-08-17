package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.FormTokenAuth;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.bo.QueuerBO;
import cn.scauaie.model.vo.QueuerVO;
import cn.scauaie.service.QueuerService;
import cn.scauaie.service.constant.TokenType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
public class QueueController {

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
    @FormTokenAuth
    public QueuerVO post(HttpServletRequest request) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        QueuerBO queuerBO = queuerService.save(tokenAO.getDep(), tokenAO.getId());

        QueuerVO queuerVO = new QueuerVO();
        BeanUtils.copyProperties(queuerBO, queuerVO);
        return queuerVO;
    }

    /**
     * 获取队列信息
     * @param request HttpServletRequest
     * @return QueuerVO
     *
     * @success:
     * HttpStatus.OK
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth
    public Object get(HttpServletRequest request,
                      @RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "10") Integer pageSize) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        if (tokenAO.getType() == TokenType.FORM) {
            QueuerBO queuerBO = queuerService.getQueuerByDepAndFormId(tokenAO.getId(), tokenAO.getDep());

            QueuerVO queuerVO = new QueuerVO();
            BeanUtils.copyProperties(queuerBO, queuerVO);
            return queuerVO;
        }

        List<QueuerBO> queuerBOList = queuerService.listQueuersByDep(tokenAO.getDep());
        return queuerBOList;

    }

}
