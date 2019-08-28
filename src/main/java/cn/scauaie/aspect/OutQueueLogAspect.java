package cn.scauaie.aspect;

import cn.scauaie.model.vo.QueuerVO;
import cn.scauaie.service.QueueLogService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 描述: 对evaluation进行日志处理
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-26 16:04
 */
@Aspect
@Component
public class OutQueueLogAspect {

    @Autowired
    private QueueLogService queueLogService;

    /**
     * 日期格式器
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 对返回结果的错误进行解析
     *
     * @param ret 返回结果
     */
    @AfterReturning(value = "@annotation(cn.scauaie.aspect.annotation.OutQueueLog)", returning = "ret")
    public void log(Object ret) {
        if (!(ret instanceof QueuerVO)) {
            return;
        }

        QueuerVO queuerVO = (QueuerVO) ret;
        System.out.println(ret);
        String message = MessageFormat.format("[{0}] [编号: {1}, 名字: {2}, 队列: {3}] 已经出队",
                formatter.format(LocalDateTime.now()), queuerVO.getFid().toString(), queuerVO.getName(), queuerVO.getDep());
        queueLogService.saveOutQueueLog(message);
    }

}

