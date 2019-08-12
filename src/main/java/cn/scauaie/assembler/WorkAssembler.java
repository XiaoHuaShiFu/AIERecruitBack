package cn.scauaie.assembler;

import cn.scauaie.model.ao.WorkAO;
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
     * 把WorkDOList装配成WorkVOList
     *
     * @param workDOList List<WorkDO>
     * @return List<WorkVO>
     */
    public List<WorkVO> assembleWorkVOListByWorkDOList(List<WorkDO> workDOList) {
        if (workDOList == null) {
            return null;
        }
        List<WorkVO> workVOList = new ArrayList<>();
        for (WorkDO workDO : workDOList) {
            workVOList.add(assembleWorkVOByWorkDO(workDO));
        }
        return workVOList;
    }

    /**
     * 把WorkDO装配成WorkAO
     *
     * @param workDO WorkDO
     * @return WorkAO
     */
    public WorkAO assembleWorkAOByWorkDO(WorkDO workDO) {
        if (workDO == null) {
            return null;
        }
        WorkAO workAO = new WorkAO();
        workAO.setId(workDO.getId());
        workAO.setUrl(workDO.getUrl());
        return workAO;
    }

    /**
     * 把WorkAO装配成WorkVO
     *
     * @param workAO WorkAO
     * @return WorkVO
     */
    public WorkVO assembleWorkVOByWorkAO(WorkAO workAO) {
        if (workAO == null) {
            return null;
        }
        WorkVO workVO = new WorkVO();
        workVO.setId(workAO.getId());
        workVO.setUrl(workAO.getUrl());
        return workVO;
    }

    /**
     * 把WorkDOList装配成WorkAOList
     *
     * @param workDOList List<WorkDO>
     * @return List<WorkAO>
     */
    public List<WorkAO> assembleWorkAOListByWorkDOList(List<WorkDO> workDOList) {
        if (workDOList == null) {
            return null;
        }
        List<WorkAO> workAOList = new ArrayList<>();
        for (WorkDO workDO : workDOList) {
            workAOList.add(assembleWorkAOByWorkDO(workDO));
        }
        return workAOList;
    }

    /**
     * 把WorkAOList装配成WorkVOList
     *
     * @param workAOList List<WorkAO>
     * @return List<WorkVO>
     */
    public List<WorkVO> assembleWorkVOListByWorkAOList(List<WorkAO> workAOList) {
        if (workAOList == null) {
            return null;
        }
        List<WorkVO> workVOList = new ArrayList<>();
        for (WorkAO workAO : workAOList) {
            workVOList.add(assembleWorkVOByWorkAO(workAO));
        }
        return workVOList;
    }
}
