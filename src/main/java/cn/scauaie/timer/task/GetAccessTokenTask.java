package cn.scauaie.timer.task;

import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.model.dto.AccessTokenDTO;
import cn.scauaie.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimerTask;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-30 21:48
 */
@Component
public class GetAccessTokenTask extends TimerTask {

    @Autowired
    private WeChatMpManager weChatMpManager;

    @Autowired
    private CacheService cacheService;

    /**
     * 最近一次获取access-token
     */
    private LocalDateTime lastGetAccessToken;

    /**
     * 构造器
     */
    public GetAccessTokenTask() {
        this.init();
    }

    @Override
    public void run() {

    }

    public LocalDateTime getLastGetAccessToken() {
        return lastGetAccessToken;
    }

    public void setLastGetAccessToken(LocalDateTime lastGetAccessToken) {
        this.lastGetAccessToken = lastGetAccessToken;
    }

    /**
     * 初始化task
     */
    private void init() {
        Optional<AccessTokenDTO> accessToken = weChatMpManager.getAccessToken();
        if (!accessToken.isPresent()) {
            this.lastGetAccessToken = LocalDateTime.MIN;
        } else {
            this.lastGetAccessToken = LocalDateTime.now();
            cacheService.set("recruit:wechat:mp:access:token", accessToken.get().getAccess_token());
        }
    }



}
