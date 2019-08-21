package cn.scauaie.service.impl;

import cn.scauaie.dao.WorkMapper;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.service.WorkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 获取对应报名表编号的作品 List<WorkAO>
     *
     * @param formId 报名表编号
     * @return List<WorkAO>
     */
    @Override
    public List<WorkAO> listWorksByFormId(Integer formId) {
        List<WorkDO> workDOList = workMapper.selectByFid(formId);
        List<WorkAO> workAOList = new ArrayList<>(workDOList.size());
        workDOList.forEach((workDO) -> {
            WorkAO workAO = new WorkAO();
            BeanUtils.copyProperties(workDO, workAO);
            workAOList.add(workAO);
        });
        return workAOList;
    }

}
