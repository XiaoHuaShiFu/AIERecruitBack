package cn.scauaie.aspect;

import cn.scauaie.model.ao.EvaluationLogAO;
import cn.scauaie.model.vo.EvaluationVO;
import cn.scauaie.service.EvaluationLogService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class EvaluationLogAspect {

    @Autowired
    private EvaluationLogService evaluationLogService;

    /**
     * 日期格式器
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 保存评价日志
     *
     * @param ret 返回结果
     */
    @AfterReturning(value = "@annotation(cn.scauaie.aspect.annotation.EvaluationLog)", returning = "ret")
    public void log(Object ret) {
        if (!(ret instanceof EvaluationVO)) {
            return;
        }

        EvaluationVO evaluationVO = (EvaluationVO) ret;
        EvaluationLogAO evaluationLogAO = new EvaluationLogAO();
        evaluationLogAO.setEid(evaluationVO.getId());
        evaluationLogAO.setFid(evaluationVO.getForm().getId());
        evaluationLogAO.setIid(evaluationVO.getInterviewer().getId());
        evaluationLogAO.setMessage("你在" + formatter.format(LocalDateTime.now()) + "评价了"
                + evaluationVO.getForm().getName() + "，报名表编号是：" + evaluationVO.getForm().getId() + "。");

        evaluationLogService.saveEvaluationLog(evaluationLogAO);
    }

}

