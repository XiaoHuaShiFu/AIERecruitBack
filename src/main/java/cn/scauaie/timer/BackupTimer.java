package cn.scauaie.timer;

import cn.scauaie.timer.task.BackupTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Timer;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-27 2:02
 */
@Component
public class BackupTimer {
    private final Timer timer = new Timer();
    @Autowired
    public BackupTimer(BackupTask backupTask) {
        timer.schedule(backupTask, 10000, 10000);
    }
}
