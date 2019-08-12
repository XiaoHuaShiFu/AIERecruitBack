package cn.scauaie.service.impl;

import cn.scauaie.assembler.WorkAssembler;
import cn.scauaie.dao.WorkMapper;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("workService")
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private WorkAssembler workAssembler;

    /**
     * 获取对应报名表编号的作品 List<WorkDO>
     *
     * @param formId 报名表编号
     * @return List<WorkDO>
     */
    @Override
    public List<WorkDO> listWorkDOsByFormId(Integer formId) {
        return workMapper.selectByFid(formId);
    }

    /**
     * 获取对应报名表编号的作品 List<WorkAO>
     *
     * @param formId 报名表编号
     * @return List<WorkAO>
     */
    @Override
    public List<WorkAO> listWorkAOsByFormId(Integer formId) {
        List<WorkDO> workDOList = listWorkDOsByFormId(formId);
        return workAssembler.assembleWorkAOListByWorkDOList(workDOList);
    }

}
