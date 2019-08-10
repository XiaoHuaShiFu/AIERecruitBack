package cn.scauaie.service.impl;

import cn.scauaie.assembler.FormAssembler;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.dao.FormMapper;
import cn.scauaie.error.ProcessingException;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dto.FormDTO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.WorkVO;
import cn.scauaie.service.FormService;
import cn.scauaie.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("formService")
public class FormServiceImpl implements FormService {

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private WeChatMpManager weChatMpManager;

    @Autowired
    private FormAssembler formAssembler;

    @Autowired
    private WorkService workService;

    /**
     * 获取FormVO通过openid
     *
     * @param openid 微信用户唯一标识符
     * @return FormVO
     */
    public FormVO getFormVOByOpenid(String openid) {
        FormDO formDO = getFormDOByOpenid(openid);
        FormVO formVO = formAssembler.assembleFormVOByFormDO(formDO);
        List<WorkVO> workVOs = workService.getWorkVOsByFormId(formDO.getId());
        formVO.setWorks(workVOs);
        return formVO;
    }

    /**
     * 获取FormDO通过openid
     *
     * @param openid 微信用户唯一标识符
     * @return FormDO
     */
    public FormDO getFormDOByOpenid(String openid) {
        FormDO formDO = formMapper.selectByOpenid(openid);
        if (formDO == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The specified openid does not exist.");
        }
        return formDO;
    }

    /**
     * 校验code
     *
     * @param code String
     * @return String
     */
    public String getOpenid(String code) {
        String openid = weChatMpManager.getOpenidByCode(code);
        if (openid == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }
        return openid;
    }

    /**
     * 直接保存Form，不进行参数校验
     * @param openid String
     * @param formAO FormAO
     * @return FormVO
     */
    public FormVO createForm(String openid, FormAO formAO) {
        FormDO formDO = formAssembler.assembleFormDOByOpenidAndFormAO(openid, formAO);
        return formAssembler.assembleFormVOByFormDO(createForm(formDO));
    }

    /**
     * 直接保存Form，不进行参数校验
     * @param formDO FormDO
     * @return FormDO
     */
    public FormDO createForm(FormDO formDO) {
        //没有成功创建
        if (formMapper.insertSelective(formDO) < 1) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the openid already exists.");
        }
        return formDO;
    }

}
