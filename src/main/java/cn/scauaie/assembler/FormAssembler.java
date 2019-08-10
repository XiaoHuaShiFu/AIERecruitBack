package cn.scauaie.assembler;

import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dto.FormDTO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.model.vo.FormVO;
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

    /**
     * 把FormDO装配成FormVO
     *
     * 无avatar
     * 无works
     *
     * @param formDO FormDO
     * @return FormVO
     */
    public FormVO assembleFormVOByFormDO(FormDO formDO) {
        if (formDO == null) {
            return null;
        }
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

    /**
     * 把FormDO和WorkDOs装配成FormVO
     *
     * @param formDO FormDO
     * @param workDOs List<WorkDO>
     * @return FormVO
     */
    public FormVO assembleFormVOByFormDOAndWorkDOs(FormDO formDO, List<WorkDO> workDOs) {
        if (formDO == null) {
            return null;
        }
        FormVO formVO = assembleFormVOByFormDO(formDO);
        formVO.setWorks(workAssembler.assembleWorkVOsByWorkDOs(workDOs));
        return formVO;
    }
}
