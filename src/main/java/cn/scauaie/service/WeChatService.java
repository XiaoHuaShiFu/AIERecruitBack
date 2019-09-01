package cn.scauaie.service;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-09-01 14:12
 */
public interface WeChatService {

    String checkAndGetEcho(String signature, String timestamp, String nonce, String echostr);

}
