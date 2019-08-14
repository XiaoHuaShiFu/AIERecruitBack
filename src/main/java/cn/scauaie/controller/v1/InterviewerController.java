package cn.scauaie.controller.v1;

import cn.scauaie.model.vo.FormVO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;


/**
 * 描述: Interviewer Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-15 01:20
 */
@RestController
@RequestMapping("v1/interviewers")
@Validated
public class InterviewerController {

    /**
     * 创建Form并返回Form
     * @param authCode 认证码
     * @param wxCode 微信小程序的wx.login()接口返回值
     * @param name 面试官名字
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
            @Size(message = "INVALID_PARAMETER_SIZE: The size of code must be 32.", min = 32, max = 32) String wxCode,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The authCode must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of authCode must be 36.", min = 36, max = 36) String authCode,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The name must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of name must be from 1 to 20.", min = 1, max = 20) String name) {
        return null;
    }

}
