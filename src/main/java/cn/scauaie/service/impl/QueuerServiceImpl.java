package cn.scauaie.service.impl;

import cn.scauaie.constant.AieConsts;
import cn.scauaie.constant.DepEnum;
import cn.scauaie.dao.QueuerMapper;
import cn.scauaie.model.bo.QueuerBO;
import cn.scauaie.model.dao.QueuerDO;
import cn.scauaie.model.query.QueuerQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.FormService;
import cn.scauaie.service.QueuerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(QueuerServiceImpl.class);

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
    public Result<QueuerBO> saveQueuer(String dep, Integer formId) {
        String q = getQueueNameByDep(dep);
        QueuerBO queuerBO = new QueuerBO();
        queuerBO.setFid(formId);

        ArrayBlockingQueue<QueuerBO> queue = queueMap.get(q);
        if (queue.contains(queuerBO)) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, you has been in the queue.");
        }

        if (queuerMapper.getByFormId(formId) != null) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, you have entered the queue.");
        }

        String name = formService.getName(formId);
        queuerBO.setName(name);
        queuerBO.setDep(q);
        queuerBO.setState("0");
        if (!queue.offer(queuerBO)) {
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Into queue failed.");
        }

        return Result.success(queuerBO);
    }

    /**
     * 出队队头元素
     *
     * @param dep 部门
     */
    @Override
    // TODO: 2019/8/18 出队之后添加到数据库
    // TODO: 2019/8/19 限制每个面试官的在某个时间内的出队人数
    public Result<QueuerBO> deleteQueuerByDep(String dep) {
        String q = getQueueNameByDep(dep);
        ArrayBlockingQueue<QueuerBO> queue = queueMap.get(q);
        QueuerBO queuerBO = queue.poll();
        if (queuerBO == null) {
            return Result.fail(ErrorCode.INVALID_OPERATION_NOT_FOUND, "The queue is empty.");
        }

        QueuerDO queuerDO = new QueuerDO();
        queuerBO.setState("1");
        BeanUtils.copyProperties(queuerBO, queuerDO);
        System.out.println(queuerDO);
        int count = queuerMapper.insertSelective(queuerDO);
        if (count < 1) {
            logger.error("Insert queuer fail, fid: {}", queuerDO.getFid());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert queuer fail.");
        }

        return Result.success(queuerBO);
    }

    /**
     * 获取排队者信息
     *
     * @param formId 报名表编号
     * @param dep    部门
     * @return QueuerBO
     */
    @Override
    public Result<QueuerBO> getQueuerByDepAndFormId(Integer formId, String dep) {
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
                return Result.success(queuerBO);
            }
        }

        //再到数据库中找
        QueuerDO queuerDO = queuerMapper.getByFormId(formId);
        if (queuerDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        BeanUtils.copyProperties(queuerDO, queuerBO);
        return Result.success(queuerBO);
    }

    /**
     * 获取队列元素
     *
     * @param query 查询参数
     * @return Result<List<QueuerBO>>
     */
    @Override
    public Result<List<QueuerBO>> listQueuersByDep(QueuerQuery query) {
        int offset = (query.getPageNum() - 1) * query.getPageSize();

        String q = getQueueNameByDep(query.getDep());
        ArrayBlockingQueue<QueuerBO> queue = queueMap.get(q);

        if (offset >= queue.size()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        QueuerBO[] queuerBOS = new QueuerBO[queue.size()];
        queue.toArray(queuerBOS);
        int limit = offset + offset - 1;

        List<QueuerBO> queuerBOList = new ArrayList<>(query.getPageSize());
        queuerBOList.addAll(Arrays.asList(queuerBOS).subList(offset, (queuerBOS.length > limit ? queuerBOS.length : limit)));
        return Result.success(queuerBOList);
    }

    /**
     * 获取队列排队人数
     *
     * @param dep 部门
     * @return 排队人数
     */
    @Override
    public int getCountByDep(String dep) {
        String q = getQueueNameByDep(dep);
        return queueMap.get(q).size();
    }

    /**
     * 通过部门名字获取队列名字
     *
     * @param dep 部门名字
     * @return 队列名字
     */
    private String getQueueNameByDep(String dep) {
        if (!dep.equals(DepEnum.ZKB.name()) && !dep.equals(DepEnum.XCB.name())) {
            return AieConsts.AIE;
        }
        return dep;
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
