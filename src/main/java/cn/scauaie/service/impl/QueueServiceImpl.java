package cn.scauaie.service.impl;

import cn.scauaie.constant.AieConsts;
import cn.scauaie.constant.DepEnum;
import cn.scauaie.service.CacheService;
import cn.scauaie.service.QueueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("queueService")
public class QueueServiceImpl implements QueueService {

    @Autowired
    private CacheService cacheService;

    public void put(String dep, Integer id) {
        if (dep.equals(DepEnum.ZKB.name()) || dep.equals(DepEnum.XCB.name())) {
            cacheService.rpush(dep, id.toString());
        }

    }
}
