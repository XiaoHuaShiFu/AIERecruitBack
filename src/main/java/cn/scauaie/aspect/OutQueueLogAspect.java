package cn.scauaie.aspect;

import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.vo.QueuerVO;
import cn.scauaie.service.InterviewerService;
import cn.scauaie.service.QueueLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private InterviewerService interviewerService;

    /**
     * 日期格式器
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 添加出队日志
     *
     * @param joinPoint ProceedingJoinPoint
     * @param request HttpServletRequest
     *
     */
    @Around(value = "@annotation(cn.scauaie.aspect.annotation.OutQueueLog) && args(request, ..)")
    public Object log(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        Object result = joinPoint.proceed();
        if (!(result instanceof QueuerVO)) {
            return result;
        }

        QueuerVO queuerVO = (QueuerVO) result;
        TokenAO tokenAO = (TokenAO) request.getAttribute("tokenAO");
        System.out.println(tokenAO);
        String message = MessageFormat.format("[{0}] [编号: {1}, 名字: {2}, 队列: {3}] 已经出队，面试官：{4}。",
                formatter.format(LocalDateTime.now()), queuerVO.getFid().toString(), queuerVO.getName(),
                queuerVO.getDep(), interviewerService.getName(tokenAO.getId()));
        System.out.println(message);
        queueLogService.saveOutQueueLog(message);

        return result;
    }

//    /**
//     * 添加出队日志
//     *
//     * @param ret 返回结果
//     */
//    @AfterReturning(value = "@annotation(cn.scauaie.aspect.annotation.OutQueueLog)", returning = "ret")
//    public void log(Object ret) {
//        if (!(ret instanceof QueuerVO)) {
//            return;
//        }
//
//        QueuerVO queuerVO = (QueuerVO) ret;
//        String message = MessageFormat.format("[{0}] [编号: {1}, 名字: {2}, 队列: {3}] 已经出队",
//                formatter.format(LocalDateTime.now()), queuerVO.getFid().toString(), queuerVO.getName(), queuerVO.getDep(), queuerVO);
//        queueLogService.saveOutQueueLog(message);
//    }
}

