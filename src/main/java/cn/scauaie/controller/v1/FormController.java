package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.FormTokenAuth;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.common.error.ErrorResponse;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.group.GroupFormAOPOST;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:27
 */
@RestController
@RequestMapping("v1/forms")
@Validated
public class FormController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Object post(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The code must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of code must be 32.", min = 32, max = 32) String code,
            @Validated(GroupFormAOPOST.class) FormAO formAO) {

        return Lists.asList("ddd", "list ddd wx".split(" "));
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @FormTokenAuth
    public Object get(@PathVariable Integer id) {
        return "ddd";
    }

}
