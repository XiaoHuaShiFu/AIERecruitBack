package cn.scauaie.assembler;

import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.model.vo.WorkVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 22:26
 */
@Component("workAssemble")
public class WorkAssembler {

    /**
     * 把WorkDO装配成WorkVO
     *
     * @param workDO WorkDO
     * @return WorkVO
     */
    public WorkVO assembleWorkVOByWorkDO(WorkDO workDO) {
        if (workDO == null) {
            return null;
        }
        WorkVO workVO = new WorkVO();
        workVO.setId(workDO.getId());
        workVO.setUrl(workDO.getUrl());
        return workVO;
    }

    /**
     * 把WorkDOs装配成WorkVOs
     *
     * @param workDOs List<WorkDO>
     * @return List<WorkVO>
     */
    public List<WorkVO> assembleWorkVOsByWorkDOs(List<WorkDO> workDOs) {
        if (workDOs == null) {
            return null;
        }
        List<WorkVO> workVOs = new ArrayList<>();
        for (WorkDO workDO : workDOs) {
            workVOs.add(assembleWorkVOByWorkDO(workDO));
        }
        return workVOs;
    }
}
