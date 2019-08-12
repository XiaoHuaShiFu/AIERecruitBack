package cn.scauaie.service;

import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.model.vo.WorkVO;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface WorkService {

    List<WorkAO> listWorkAOsByFormId(Integer formId);

    List<WorkDO> listWorkDOsByFormId(Integer formId);
}
