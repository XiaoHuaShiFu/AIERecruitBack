package cn.scauaie.controller.v1;

import cn.scauaie.manager.impl.WeChatMpManagerImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 描述: WeChat Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-30 21:10
 */
@RestController
@RequestMapping("v1/wechats")
public class WeChatController {

    /**
     * 微信公众号认证TOKEN
     */
    private final static String TOKEN = "scauaie";

    @Autowired
    private WeChatMpManagerImpl weChatMpManager;


    /**
     * 获取日志
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return 微信认证接口
     *
     * @success:
     * HttpStatus.OK
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String get(String signature, String timestamp, String nonce, String echostr) {
        String[] tmpArr = {TOKEN, timestamp, nonce};
        Arrays.sort(tmpArr);
        String tmpStr = tmpArr[0] + tmpArr[1] + tmpArr[2];
        tmpStr = DigestUtils.sha1Hex(tmpStr);

        if (tmpStr.equals(signature)) {
            return echostr;
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取access-token
     *
     * @return AccessTokenDTO
     */
    @RequestMapping(value = "/access/tokens", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Object getAccessToken() {
        return weChatMpManager.getNewAccessToken();
    }


}
