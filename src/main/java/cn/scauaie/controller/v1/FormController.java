package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.ErrorHandler;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.constant.TokenType;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.ao.group.GroupFormAO;
import cn.scauaie.model.ao.group.GroupFormAOPOST;
import cn.scauaie.model.vo.AvatarVO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.WorkVO;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.FormService;
import cn.scauaie.util.BeanUtils;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    private Mapper mapper;

    @Autowired
    private BeanUtils beanUtils;

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
    @ErrorHandler
    public Object post(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The code must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of code must be 32.", min = 32, max = 32) String code,
            @Validated(GroupFormAOPOST.class) FormAO formAO) {
        Result<FormAO> result = formService.saveForm(code, formAO);
        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), FormVO.class);
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
    @ErrorHandler
    public Object get(HttpServletRequest request,
                      @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
                      @PathVariable Integer id) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        TokenType type = TokenType.valueOf(tokenAO.getType());

        if (type == TokenType.INTERVIEWER) {
            return mapper.map(formService.getForm(id), FormVO.class);
        }

        if (type == TokenType.FORM) {
            Integer tid = tokenAO.getId();
            if (!tid.equals(id)) {
                return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
            }
            return mapper.map(formService.getForm(id), FormVO.class);
        }

        //非form-token或interviewer-token
        return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
    }

    // TODO: 2019/8/16 查询优化
    /**
     * 查询form
     * @param request HttpServletRequest
     * @param pageNum 页码
     * @param pageSize 页条数
     * @param q 查询参数
     * @return FormVOList
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    public Object get(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum,
                    @RequestParam(defaultValue = "10") Integer pageSize, String q) {
        Result<List<FormAO>> result = formService.listForms(pageNum, pageSize, q);
        if (!result.isSuccess()) {
            return result;
        }

        return beanUtils.mapList(result.getData(), FormVO.class);
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
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object put(HttpServletRequest request, @Validated(GroupFormAO.class) FormAO formAO) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        formAO.setId(tokenAO.getId());
        Result<FormAO> result = formService.updateForm(formAO);
        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), FormVO.class);
    }

    /**
     * 获取报名表数
     * @param includeSecondDep 是否包含第二志愿部门
     * @return Map<String, Integer> 部门人数列表
     *
     * @success:
     * HttpStatus.OK
     *
     */
    @RequestMapping(value="/number", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    public Object getDepNumber(HttpServletRequest request, @RequestParam(defaultValue = "false") Boolean includeSecondDep) {
        return formService.listDepNumbers(includeSecondDep);
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
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object postAvatar(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.")
                    MultipartFile avatar) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Result<String> result = formService.saveAvatar(tokenAO.getId(), avatar);
        if (!result.isSuccess()) {
            return result;
        }
        return new AvatarVO(result.getData());
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
    @RequestMapping(value="/avatar", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object putAvatar(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.")
                    MultipartFile avatar) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Result<String> result = formService.updateAvatar(tokenAO.getId(), avatar);
        if (!result.isSuccess()) {
            return result;
        }
        return new AvatarVO(result.getData());
    }

    /**
     * 修改头像
     * 此接口专门为微信小程序提供，为了适配微信小程序上传文件无put方法
     * 并非规范的rest接口
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
    @RequestMapping(value="/avatar/u", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object updateAvatar(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.")
                    MultipartFile avatar) {
        return putAvatar(request, avatar);
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
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object postWork(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required work must be not null.")
                    MultipartFile work) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");

        Result<WorkAO> result = formService.saveWork(tokenAO.getId(), work);
        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), WorkVO.class);
    }

    /**
     * 删除作品
     *
     * @param request HttpServletRequest
     * @param id 作品编号
     * @return WorkVO
     *
     * @success:
     * HttpStatus.NO_CONTENT
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND: The work of this id does not exist.
     * FORBIDDEN_SUB_USER
     * INTERNAL_ERROR: Delete work fail.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(value="/works/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object deleteWork(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required wid must be not null.")
            @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
            @PathVariable Integer id) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");

        Result<WorkAO> result = formService.deleteWork(id, tokenAO.getId());
        if (!result.isSuccess()) {
            return result;
        }

        return ResponseEntity.EMPTY;
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
    @RequestMapping(value="/works/{wid}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object putWork(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required wid must be not null.")
            @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
            @PathVariable Integer wid,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required work must be not null.")
                    MultipartFile work) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");

        Result<WorkAO> result = formService.updateWork(wid, tokenAO.getId(), work);
        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), WorkVO.class);
    }

    /**
     * 修改作品
     * 此接口专门为微信小程序提供，为了适配微信小程序上传文件无put方法
     * 并非规范的rest接口
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
    @RequestMapping(value="/works/{wid}/u", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object updateWork(
            HttpServletRequest request,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required wid must be not null.")
            @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.", value = 0)
            @PathVariable Integer wid,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required work must be not null.")
                    MultipartFile work) {
        return putWork(request, wid, work);
    }

}
