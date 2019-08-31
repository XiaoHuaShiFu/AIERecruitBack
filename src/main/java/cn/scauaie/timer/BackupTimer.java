//package cn.scauaie.timer;
//
//import cn.scauaie.timer.task.BackupTask;
//import cn.scauaie.timer.task.CacheAccessTokenTask;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.Timer;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//
///**
// * 描述:
// *
// * @author xhsf
// * @email 827032783@qq.com
// * @create 2019-08-27 2:02
// */
//@Component
//public class BackupTimer {
//
//    @Autowired
//    private BackupTask backupTask;
//
//    @Autowired
//    private CacheAccessTokenTask cacheAccessTokenTask;
//
//    /**
//     * 第一次执行的延迟时间
//     */
//    private static final long DELAY = 60000;
//
//    /**
//     * 后面每次执行的间隔时间
//     */
//    private static final long PERIOD = 3600000;
//
//    /**
//     * 定时器
//     */
//    private final Timer timer = new Timer();
//
//    private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(100);
//
//    /**
//     * 初始化函数
//     */
//    @PostConstruct
//    private void init() {
//        schedule.scheduleAtFixedRate(cacheAccessTokenTask, )
//        timer.schedule(backupTask, DELAY, PERIOD);
//        timer.schedule();
//    }
//
//}
