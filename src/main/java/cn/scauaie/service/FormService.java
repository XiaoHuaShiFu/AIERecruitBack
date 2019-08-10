package cn.scauaie.service;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.vo.FormVO;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface FormService {
    String getOpenid(String code);

    FormVO createForm(String openid, FormAO formAO);

    FormDO createForm(FormDO formDO);

    FormVO getFormVOByOpenid(String openid);

    FormDO getFormDOByOpenid(String openid);
}
