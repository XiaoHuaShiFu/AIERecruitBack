package cn.scauaie.service.impl;

import cn.scauaie.constant.AieConsts;
import cn.scauaie.constant.DepEnum;
import cn.scauaie.dao.QueuerMapper;
import cn.scauaie.error.ErrorCode;
import cn.scauaie.exception.ProcessingException;
import cn.scauaie.model.bo.QueuerBO;
import cn.scauaie.model.dao.QueuerDO;
import cn.scauaie.service.FormService;
import cn.scauaie.service.QueuerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("queuerService")
public class QueuerServiceImpl implements QueuerService {

    @Autowired
    private QueuerMapper queuerMapper;

    @Autowired
    private FormService formService;

    /**
     * 每个人预计等待时间
     */
    private static final int GAP = 3;

    /**
     * 部门与队列映射
     */
    private Map<String, ArrayBlockingQueue<QueuerBO>> queueMap;

    /**
     * 构造器
     */
    public QueuerServiceImpl() {
        init();
    }

    /**
     * 把排队者加进队列中
     *
     * @param dep    部门
     * @param formId 报名表编号
     */
    @Override
    public QueuerBO save(String dep, Integer formId) {
        String q = getQueueNameByDep(dep);
        QueuerBO queuerBO = new QueuerBO();
        queuerBO.setFid(formId);

        ArrayBlockingQueue<QueuerBO> queue = queueMap.get(q);
        if (queue.contains(queuerBO)) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, you has been in the queue.");
        }

        if (queuerMapper.getByFormId(formId) != null) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, you have entered the queue.");
        }

        String name = formService.getName(formId);
        queuerBO.setName(name);
        queuerBO.setDep(q);
        queuerBO.setState(0);
        if (!queue.offer(queuerBO)) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Into queue failed.");
        }

        return queuerBO;
    }

    /**
     * 获取排队者信息
     *
     * @param formId 报名表编号
     * @param dep    部门
     * @return QueuerBO
     */
    @Override
    public QueuerBO getQueuerByDepAndFormId(Integer formId, String dep) {
        String q = getQueueNameByDep(dep);
        QueuerBO queuerBO = new QueuerBO();
        queuerBO.setFid(formId);

        //先在本地的队列里面找
        ArrayBlockingQueue<QueuerBO> queue = queueMap.get(q);
        QueuerBO[] queuerBOS = new QueuerBO[queue.size()];
        queue.toArray(queuerBOS);
        for (int i = 0; i < queuerBOS.length; i++) {
            if (queuerBOS[i].equals(queuerBO)) {
                queuerBO = queuerBOS[i];
                queuerBO.setFrontNumber(i);
                queuerBO.setExpectedWaitTime(i * GAP);
                return queuerBO;
            }
        }

        //再到数据库中找
        QueuerDO queuerDO = queuerMapper.getByFormId(formId);
        if (queuerDO == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        BeanUtils.copyProperties(queuerDO, queuerBO);
        return queuerBO;
    }

    @Override
    public List<QueuerBO> listQueuersByDep(String dep) {
        String q = getQueueNameByDep(dep);
        ArrayBlockingQueue<QueuerBO> queue = queueMap.get(q);
        QueuerBO[] queuerBOS = new QueuerBO[queue.size()];
        queue.toArray(queuerBOS);
        List<QueuerBO> queuerBOList = new ArrayList<>(queue.size());
        queuerBOList.addAll(Arrays.asList(queuerBOS));
        return queuerBOList;
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

    /**
     * 初始化
     */
    private void init() {
        Map<String, ArrayBlockingQueue<QueuerBO>> queueMap = new HashMap<>(5);
        queueMap.put(DepEnum.ZKB.name(), new ArrayBlockingQueue<>(1000));
        queueMap.put(DepEnum.XCB.name(), new ArrayBlockingQueue<>(1000));
        queueMap.put(AieConsts.AIE, new ArrayBlockingQueue<>(1000));
        this.queueMap = queueMap;
    }

}
