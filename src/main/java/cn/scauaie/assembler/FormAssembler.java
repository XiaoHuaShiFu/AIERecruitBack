package cn.scauaie.assembler;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.vo.FormVO;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 22:26
 */
@Component("formAssemble")
public class FormAssembler {

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
        formDO.setIntroduction(formAO.getIntroduction());
        return formDO;
    }

    public FormVO assembleFormVOByFormDO(FormDO formDO) {
        FormVO formVO = new FormVO();
        formVO.setId(formDO.getId());
        formVO.setName(formDO.getName());
        formVO.setAvatar(formDO.getAvatar());
        formVO.setGender(formDO.getGender());
        formVO.setCollege(formDO.getCollege());
        formVO.setMajor(formDO.getMajor());
        formVO.setPhone(formDO.getPhone());
        formVO.setFirstDep(formDO.getFirstDep());
        formVO.setSecondDep(formDO.getSecondDep());
        formVO.setIntroduction(formDO.getIntroduction());
        return formVO;
    }

}
