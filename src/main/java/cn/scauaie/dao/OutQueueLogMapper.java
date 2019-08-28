package cn.scauaie.dao;

import java.util.List;

public interface OutQueueLogMapper {
    int insertOutQueueLog(String message);

    List<String> listOutQueueLogs();
}