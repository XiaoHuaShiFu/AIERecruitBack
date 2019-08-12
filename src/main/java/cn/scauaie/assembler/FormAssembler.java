package cn.scauaie.assembler;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.WorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 22:26
 */
@Component("formAssemble")
public class FormAssembler {

    @Autowired
    private WorkAssembler workAssembler;

    /**
     * 把FormAO装配成FormVO
     *
     * 无设置works属性
     *
     * @param formAO FormAO
     * @return FormVO
     */
    public FormVO assembleFormVOByFormAO(FormAO formAO) {
        if (formAO == null) {
            return null;
        }
        FormVO formVO = new FormVO();
        formVO.setId(formAO.getId());
        formVO.setName(formAO.getName());
        formVO.setGender(formAO.getGender());
        formVO.setCollege(formAO.getCollege());
        formVO.setMajor(formAO.getMajor());
        formVO.setPhone(formAO.getPhone());
        formVO.setAvatar(formAO.getAvatar());
        formVO.setFirstDep(formAO.getFirstDep());
        formVO.setSecondDep(formAO.getSecondDep());
        formVO.setIntroduction(formAO.getIntroduction());
        List<WorkVO> workVOList = workAssembler.assembleWorkVOListByWorkAOList(formAO.getWorks());
        formVO.setWorks(workVOList);
        return formVO;
    }

    /**
     * 把FormDO装配成FormAO
     *
     * @param formDO FormDO
     * @return FormAO
     */
    public FormAO assembleFormAOByFormDO(FormDO formDO) {
        if (formDO == null) {
            return null;
        }
        FormAO formAO = new FormAO();
        formAO.setId(formDO.getId());
        formAO.setName(formDO.getName());
        formAO.setAvatar(formDO.getAvatar());
        formAO.setGender(formDO.getGender());
        formAO.setCollege(formDO.getCollege());
        formAO.setMajor(formDO.getMajor());
        formAO.setPhone(formDO.getPhone());
        formAO.setFirstDep(formDO.getFirstDep());
        formAO.setSecondDep(formDO.getSecondDep());
        formAO.setIntroduction(formDO.getIntroduction());
        return formAO;
    }

    /**
     * 把FormAO和Openid装配成FormDO
     *
     * 无Id
     *
     * @param openid String
     * @param formAO FormAO
     * @return FormDO
     */
    public FormDO assembleFormDOByOpenidAndFormAO(String openid, FormAO formAO) {
        FormDO formDO = new FormDO();
        formDO.setOpenid(openid);
        if (formAO == null) {
            return formDO;
        }
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

}
