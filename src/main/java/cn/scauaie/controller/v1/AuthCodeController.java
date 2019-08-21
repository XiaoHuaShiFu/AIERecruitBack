package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.ErrorHandler;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.constant.TokenType;
import cn.scauaie.model.ao.AuthCodeAO;
import cn.scauaie.model.vo.AuthCodeVO;
import cn.scauaie.service.AuthCodeService;
import cn.scauaie.validator.annotation.Dep;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


/**
 * 描述: Authcode Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-10 21:10
 */
@RestController
@RequestMapping("v1/authcodes")
@Validated
public class AuthCodeController {

    @Autowired
    private Mapper mapper;

    @Autowired
    private AuthCodeService authCodeService;

    // TODO: 2019/8/21 弄成一个特权接口
    /**
     * 生成authcode
     *
     * @param count 数量
     * @param dep 部门
     * @return AuthCodeVOList
     *
     * @success:
     * HttpStatus.CREATED
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = TokenType.INTERVIEWER)
    @ErrorHandler
    public Object postToken(
            @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of count below, min: 0.", value = 1)
            @Max(message = "INVALID_PARAMETER_VALUE_EXCEEDED: The name of count exceeded, max: 10.", value = 10) Integer count,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The dep must be not blank.") @Dep String dep) {
        List<AuthCodeAO> authCodeList = authCodeService.createAndSaveAuthCodes(dep, count);

        List<AuthCodeVO> authCodeVOList = new ArrayList<>(authCodeList.size());
        mapper.map(authCodeList, authCodeVOList);
        return authCodeVOList;
    }

}
