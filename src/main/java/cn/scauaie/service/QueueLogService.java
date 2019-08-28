package cn.scauaie.service;

import cn.scauaie.model.query.LogQuery;
import cn.scauaie.result.Result;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-21 16:18
 */
public interface QueueLogService {

    Result<String> saveOutQueueLog(String message);

    Result<List<String>> getLogs(LogQuery query);

    Result<List<String>> listOutQueueLogs(Integer pageNum, Integer pageSize);
}
