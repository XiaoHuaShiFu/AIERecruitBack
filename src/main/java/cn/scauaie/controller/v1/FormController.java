package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.FormTokenAuth;
import cn.scauaie.assembler.FormAssembler;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.group.GroupFormAOPOST;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 描述: Form Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:27
 */
@RestController
@RequestMapping("v1/forms")
@Validated
public class FormController {

    @Autowired
    private FormService formService;

    @Autowired
    private FormAssembler formAssembler;

    /**
     * 创建Form并返回Form
     * @param code 微信小程序的wx.login()接口返回值
     * @param formAO Form信息
     * @return FormVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER: The code is not valid.
     * OPERATION_CONFLICT: Request was denied due to conflict, the openid already exists.
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public FormVO post(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The code must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of code must be 32.", min = 32, max = 32) String code,
            @Validated(GroupFormAOPOST.class) FormAO formAO) {
        String openid = formService.getOpenid(code);
        FormDO formDO = formService.createForm(openid, formAO);
        return formAssembler.assembleFormVOByFormDO(formDO);
    }

    @RequestMapping(value="/{id}/avatar", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @FormTokenAuth
    public Object avatarPost(HttpServletRequest request, @PathVariable Integer id) {
        System.out.println(request.getHeader("authorization"));
        System.out.println(request.getAttribute("fid") + "lalallalala");
        return "ddd";
    }


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @FormTokenAuth
    public Object get(@PathVariable Integer id) {
        return "ddd";
    }

}
