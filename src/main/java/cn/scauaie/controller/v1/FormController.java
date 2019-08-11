package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.FormTokenAuth;
import cn.scauaie.model.vo.AvatarVO;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.group.GroupFormAOPOST;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.WorkVO;
import cn.scauaie.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
        return formService.createForm(openid, formAO);
    }

    /**
     * 创建头像
     *
     * @param request HttpServletRequest
     * @param avatar MultipartFile
     * @return AvatarVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * OPERATION_CONFLICT: The specified avatar already exist.
     * INTERNAL_ERROR: Upload file failed.
     * INTERNAL_ERROR: Delete file failed.
     * INTERNAL_ERROR: Update avatar error.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(value="/avatar", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @FormTokenAuth
    public AvatarVO avatarPost(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.")
                    MultipartFile avatar) {
        Integer fid = (Integer) request.getAttribute("fid");
        return formService.uploadAvatar(fid, avatar);
    }

    /**
     * 创建作品
     *
     * @param request HttpServletRequest
     * @param work MultipartFile
     * @return WorkVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INTERNAL_ERROR: Upload file failed.
     * INTERNAL_ERROR: Delete file failed.
     * INTERNAL_ERROR: Insert work failed.
     * OPERATION_CONFLICT: The specified work number has reached the upper limit.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(value="/work", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @FormTokenAuth
    public WorkVO workPost(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required work must be not null.")
                    MultipartFile work) {
        Integer fid = (Integer) request.getAttribute("fid");
        return formService.uploadWork(fid, work);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @FormTokenAuth
    public Object get(@PathVariable Integer id) {
        return "ddd";
    }

}
