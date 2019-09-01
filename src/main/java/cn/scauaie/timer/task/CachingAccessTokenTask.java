package cn.scauaie.timer.task;

import cn.scauaie.manager.WeChatMpManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述: 获取accessToken的任务
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-30 21:48
 */
@Component
public class CachingAccessTokenTask implements Runnable {

    @Autowired
    private WeChatMpManager weChatMpManager;

    @Override
    public void run() {
        weChatMpManager.getNewAccessToken();
    }

}
