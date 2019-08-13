package cn.scauaie.service;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.WorkAO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface FormService {

    FormAO saveForm(String code, FormAO formAO);

    FormAO getFormAOByCode(String code);

    String saveAvatar(Integer formId, MultipartFile avatar);

    String updateAvatar(Integer formId, MultipartFile avatar);

    WorkAO saveWork(Integer formId, MultipartFile work);

    WorkAO updateWork(Integer workId, Integer formId, MultipartFile work);
}
