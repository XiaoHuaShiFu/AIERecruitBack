package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.FormTokenAuth;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.service.FormService;
import cn.scauaie.service.QueueService;
import cn.scauaie.service.TokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.PriorityQueue;


/**
 * 描述: Queue Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-10 21:10
 */
@RestController
@RequestMapping("v1/queues")
@Validated
public class QueueController {

    @Autowired
    private QueueService queueService;

    /**
     * 创建form-token凭证
     *
     * @param request HttpServletRequest
     * @return FormVO
     *
     * Http header里的Authorization带有form-token凭证
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER: The code is not valid.
     * INVALID_PARAMETER_NOT_FOUND: The specified openid does not exist.
     * INTERNAL_ERROR: Failed to create form-token.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @FormTokenAuth
    public Object post(HttpServletRequest request) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        queueService.put(tokenAO.getDep(), tokenAO.getId());
        return null;
    }

}
