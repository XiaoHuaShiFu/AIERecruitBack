package cn.scauaie.manager;

import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-22 20:40
 */
public interface FormManager {

    Result<WorkAO> deleteWork(Integer workId, Integer formId);

    String getWorkUrlByWorkIdAndFormId(Integer workId, Integer formId);

}
