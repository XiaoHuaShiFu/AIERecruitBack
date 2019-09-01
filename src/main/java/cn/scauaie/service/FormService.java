package cn.scauaie.service;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface FormService {

    Result<FormAO> saveForm(String code, FormAO formAO);

    FormAO getForm(Integer id);

    Result<FormAO> getFormByCode(String code);

    Result<List<FormAO>> listForms(Integer pageNum, Integer pageSize);

    Result<List<FormAO>> listForms(Integer pageNum, Integer pageSize, String q);

    Result<FormAO> updateForm(FormAO formAO);

    String getName(Integer id);

    Result<String> getOpenid(Integer id);

    String getFirstDep(Integer id);

    Map<String, Integer> listDepNumbers(Boolean includeSecondDep);

    Result<String> saveAvatar(Integer formId, MultipartFile avatar);

    Result<String> updateAvatar(Integer formId, MultipartFile avatar);

    Result<WorkAO> saveWork(Integer formId, MultipartFile work);

    Result<WorkAO> deleteWork(Integer workId, Integer formId);

    Result<WorkAO> updateWork(Integer workId, Integer formId, MultipartFile work);

}
