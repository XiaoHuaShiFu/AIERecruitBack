package cn.scauaie.assemble;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dao.FormDO;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 22:26
 */
@Component("formAssemble")
public class FormAssemble {

    public FormDO assembleFormDOByOpenidAndFormAO(String openid, FormAO formAO) {
        FormDO formDO = new FormDO();
        formDO.setOpenid(openid);
        formDO.setName(formAO.getName());
        formDO.setAvatar(formAO.getAvatar());
        formDO.setGender(formAO.getGender());
        formDO.setPhone(formAO.getPhone());
        formDO.setCollege(formAO.getCollege());
        formDO.setMajor(formAO.getMajor());
        formDO.setFirstDep(formAO.getFirstDep());
        formDO.setSecondDep(formAO.getSecondDep());
        return formDO;
    }

}
