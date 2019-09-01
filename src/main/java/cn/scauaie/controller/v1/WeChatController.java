package cn.scauaie.controller.v1;

import cn.scauaie.aspect.annotation.ErrorHandler;
import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.constant.TokenType;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.result.Result;
import cn.scauaie.service.WeChatMpService;
import cn.scauaie.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 描述: WeChat Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-30 21:10
 */
@RestController
@RequestMapping("v1/wechat")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private WeChatMpService weChatMpService;

    /**
     * 微信公共平台认证接口
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return echostr
     *
     * @success:
     * HttpStatus.OK
     *
     */
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String getEcho(String signature, String timestamp, String nonce, String echostr) {
        return weChatService.checkAndGetEcho(signature, timestamp, nonce, echostr);
    }

    /**
     * 表单事件获取到的formId
     * @param formId 表单事件获取到的formId
     * @return echostr
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INTERNAL_ERROR: Insert formId fail.
     *
     */
    @RequestMapping(value = "/mp/form-ids", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = TokenType.FORM)
    @ErrorHandler
    public Object postFormId(HttpServletRequest request, String formId) {
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        Result<String> result = weChatMpService.saveFormId(formId, tokenAO.getId());
        if (!result.isSuccess()) {
            return result;
        }
        return result.getData();
    }

}
