package cn.scauaie.service;

import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface WorkService {

    Result<WorkAO> saveWork(Integer formId, MultipartFile work);

    Result<WorkAO> deleteWork(Integer workId, Integer formId);

    List<WorkAO> listWorksByFormId(Integer formId);

    Result<WorkAO> updateWork(Integer workId, Integer formId, MultipartFile work);

    Result<WorkAO> updatedWorkByOldWorkUrl(Integer workId, String oldWorkUrl, MultipartFile work);

}
