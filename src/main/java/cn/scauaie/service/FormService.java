package cn.scauaie.service;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dao.FormDO;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface FormService {
    String getOpenid(String code);

    FormDO createForm(String openid, FormAO formAO);

    FormDO createForm(FormDO formDO);
}
