package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.ErrorHandler;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.vo.InterviewerVO;
import cn.scauaie.result.Result;
import cn.scauaie.service.InterviewerService;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @Autowired
    private InterviewerService interviewerService;

    @Autowired
    private Mapper mapper;

    /**
     * 创建Interviewer并返回Interviewer
     * @param authCode 认证码
     * @param wxCode 微信小程序的wx.login()接口返回值
     * @param name 面试官名字
     * @return InterviewerVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER: The code is not valid.
     * INVALID_PARAMETER_NOT_FOUND: The specified authCode does not exist.
     * OPERATION_CONFLICT: Request was denied due to conflict, the openid already exists.
     * INTERNAL_ERROR: Insert interviewer failed.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ErrorHandler
    public Object post(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The wxCode must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of wxCode must be 32.", min = 32, max = 32) String wxCode,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The authCode must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of authCode must be 36.", min = 36, max = 36) String authCode,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The name must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of name must be from 1 to 10.", min = 1, max = 10) String name) {
        Result<InterviewerAO> result = interviewerService.saveInterviewer(wxCode, authCode, name);
        if (!result.isSuccess()) {
            return result;
        }

        return mapper.map(result.getData(), InterviewerVO.class);
    }

}
