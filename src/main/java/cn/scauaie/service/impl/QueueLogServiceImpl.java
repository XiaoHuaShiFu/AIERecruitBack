package cn.scauaie.service.impl;

import cn.scauaie.dao.OutQueueLogMapper;
import cn.scauaie.model.query.LogQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.QueueLogService;
import cn.scauaie.service.constant.QueueLogType;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-21 16:18
 */
@Service("queueLogService")
public class QueueLogServiceImpl implements QueueLogService {

    private static final Logger logger = LoggerFactory.getLogger(QueueLogServiceImpl.class);

    @Autowired
    private OutQueueLogMapper outQueueLogMapper;

    /**
     * 保存出队日志
     * 不检验参数
     *
     * @param message 信息
     * @return OutQueueLog
     */
    @Override
    public Result<String> saveOutQueueLog(String message) {
        int count = outQueueLogMapper.insertOutQueueLog(message);
        if (count < 1) {
            logger.error("Insert outQueueLog fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert outQueueLog fail.");
        }
        return Result.success(message);
    }

    /**
     * 获取日志
     *
     * @param query 查询参数
     * @return 日志列表
     */
    public Result<List<String>> getLogs(LogQuery query) {
        if (query.getLogType().equals(QueueLogType.OUT_QUEUE.name())) {
            return listOutQueueLogs(query.getPageNum(), query.getPageSize());
        }
        return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
    }

    /**
     * 获取出队日志列表
     *
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return Result<List<EvaluationLogAO>>
     */
    @Override
    public Result<List<String>> listOutQueueLogs(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<String> outQueueLogList = outQueueLogMapper.listOutQueueLogs();
        if (outQueueLogList.size() < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(outQueueLogList);
    }

}
