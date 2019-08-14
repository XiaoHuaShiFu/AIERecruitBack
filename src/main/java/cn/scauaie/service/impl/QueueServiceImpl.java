package cn.scauaie.service.impl;

import cn.scauaie.constant.AieConsts;
import cn.scauaie.constant.DepEnum;
import cn.scauaie.model.ao.QueueAO;
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
        String q = getQueueNameByDep(dep);
        cacheService.rpush(q, id.toString());

    }

    public QueueAO getQueueByDepAndFormId(Integer formId, String dep) {
        String q = getQueueNameByDep(dep);
        return null;
    }


    /**
     * 通过部门名字获取队列名字
     *
     * @param dep 部门名字
     * @return 队列名字
     */
    private String getQueueNameByDep(String dep) {
        if (dep.equals(DepEnum.ZKB.name()) || dep.equals(DepEnum.XCB.name())) {
            return dep;
        }
        return AieConsts.AIE;
    }
}
