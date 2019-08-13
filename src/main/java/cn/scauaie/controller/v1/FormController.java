package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.FormTokenAuth;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.error.ErrorCode;
import cn.scauaie.exception.ProcessingException;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.ao.group.GroupFormAO;
import cn.scauaie.model.ao.group.GroupFormAOPOST;
import cn.scauaie.model.vo.AvatarVO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.WorkVO;
import cn.scauaie.service.FormService;
import cn.scauaie.service.constant.TokenType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
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
        formAO = formService.saveForm(code, formAO);
        FormVO formVO = new FormVO();
        BeanUtils.copyProperties(formAO, formVO);
        return formVO;
    }

    /**
     * 获取form
     * @param id 报名表编号
     * @return FormVO
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
    @TokenAuth
    public FormVO get(HttpServletRequest request,
                      @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
                      @PathVariable Integer id) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        TokenType type = tokenAO.getType();

        if (type == TokenType.INTERVIEWER) {
            FormAO formAO = formService.getFormAOById(id);
            FormVO formVO = new FormVO();
            BeanUtils.copyProperties(formAO, formVO);
            return formVO;
        }

        if (type == TokenType.FORM) {
            Integer tid = tokenAO.getId();
            if (!tid.equals(id)) {
                throw new ProcessingException(ErrorCode.FORBIDDEN_SUB_USER);
            }
            FormAO formAO = formService.getFormAOById(id);
            FormVO formVO = new FormVO();
            BeanUtils.copyProperties(formAO, formVO);
            return formVO;
        }

        //非form-token或interviewer-token
        throw new ProcessingException(ErrorCode.FORBIDDEN_SUB_USER);
    }

    /**
     * 更新Form并返回Form
     * @param formAO Form信息
     * @return FormVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Update avatar failed.
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.PATCH)
    @ResponseStatus(value = HttpStatus.OK)
    @FormTokenAuth
    public FormVO patch(HttpServletRequest request, @Validated(GroupFormAO.class) FormAO formAO) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        formAO.setId(tokenAO.getId());
        formAO = formService.updateForm(formAO);
        FormVO formVO = new FormVO();
        BeanUtils.copyProperties(formAO, formVO);
        return formVO;
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
     * INTERNAL_ERROR: Update avatar exception.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(value="/avatar", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @FormTokenAuth
    public AvatarVO postAvatar(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.")
                    MultipartFile avatar) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Integer id = tokenAO.getId();
        String avatarUrl = formService.saveAvatar(id, avatar);
        return new AvatarVO(avatarUrl);
    }

    /**
     * 修改头像
     *
     * @param request HttpServletRequest
     * @param avatar MultipartFile
     * @return AvatarVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Upload file failed.
     * INTERNAL_ERROR: Delete file failed.
     * INTERNAL_ERROR: Update avatar exception.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(value="/avatar", method = RequestMethod.PATCH)
    @ResponseStatus(value = HttpStatus.OK)
    @FormTokenAuth
    public AvatarVO patchAvatar(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.")
                    MultipartFile avatar) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Integer id = tokenAO.getId();
        String newAvatarUrl = formService.updateAvatar(id, avatar);
        return new AvatarVO(newAvatarUrl);
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
    @RequestMapping(value="/works", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @FormTokenAuth
    public WorkVO postWork(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required work must be not null.")
                    MultipartFile work) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Integer fid = tokenAO.getId();

        WorkAO workAO = formService.saveWork(fid, work);
        WorkVO workVO = new WorkVO();
        BeanUtils.copyProperties(workAO, workVO);
        return workVO;
    }

    /**
     * 修改作品
     *
     * @param request HttpServletRequest
     * @param work MultipartFile
     * @return WorkVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Upload file failed.
     * INTERNAL_ERROR: Delete file failed.
     * INTERNAL_ERROR: Insert work failed.
     * FORBIDDEN_SUB_USER: The specified action is not available for you.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(value="/works/{wid}", method = RequestMethod.PATCH)
    @ResponseStatus(value = HttpStatus.OK)
    @FormTokenAuth
    public WorkVO patchWork(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required wid must be not null.")
            @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
            @PathVariable Integer wid,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required work must be not null.")
                    MultipartFile work) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Integer fid = tokenAO.getId();

        WorkAO workAO = formService.updateWork(wid, fid, work);
        WorkVO workVO = new WorkVO();
        BeanUtils.copyProperties(workAO, workVO);
        return workVO;
    }

}
