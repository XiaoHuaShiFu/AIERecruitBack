package cn.scauaie.controller.v1;

import cn.scauaie.model.vo.FormVO;
import cn.scauaie.service.FormService;
import cn.scauaie.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 描述: Token Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-10 21:10
 */
@RestController
@RequestMapping("v1/tokens")
@Validated
public class TokenController {

    @Autowired
    private FormService formService;

    @Autowired
    private TokenService tokenService;


    /**
     * 创建form-token凭证
     *
     * @param code 微信小程序的wx.login()接口返回值
     * @param response HttpServletResponse
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
    @RequestMapping(value="/form", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public FormVO postFormToken(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The code must be not blank.")
            @Size(message = "INVALID_PARAMETER_SIZE: The size of code must be 32.", min = 32, max = 32)
                    String code, HttpServletResponse response) {
        String openid = formService.getOpenid(code);
        FormVO formVO = formService.getFormVOByOpenid(openid);
        String token = tokenService.createFormToken(formVO.getId());
        response.setHeader("Authorization", token);
        return formVO;
    }

}
