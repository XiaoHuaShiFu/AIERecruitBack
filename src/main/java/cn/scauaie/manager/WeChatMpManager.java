package cn.scauaie.manager;

import cn.scauaie.manager.constant.WeChatMp;
import cn.scauaie.model.dto.AccessTokenDTO;

import java.util.Optional;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-31 1:34
 */
public interface WeChatMpManager {

    Optional<String> getAccessToken();

    Optional<AccessTokenDTO> getNewAccessToken();

    String getOpenid(String code, String mpName);

    String getOpenid(String code, WeChatMp weChatMp);

}
