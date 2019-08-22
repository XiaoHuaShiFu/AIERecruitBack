package cn.scauaie.service;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface FormService {

    FormAO saveForm(String code, FormAO formAO);

    FormAO getForm(Integer id);

    String getName(Integer id);

    FormAO getFormByCode(String code);

    List<FormAO> listForms(Integer pageNum, Integer pageSize, String q);

    FormAO updateForm(FormAO formAO);

    String saveAvatar(Integer formId, MultipartFile avatar);

    String updateAvatar(Integer formId, MultipartFile avatar);

    WorkAO saveWork(Integer formId, MultipartFile work);

    Result<WorkAO> deleteWork(Integer workId, Integer formId);

    WorkAO updateWork(Integer workId, Integer formId, MultipartFile work);

    Map<String, Integer> listDepNumbers(Boolean includeSecondDep);

    String getFirstDep(Integer id);

}
