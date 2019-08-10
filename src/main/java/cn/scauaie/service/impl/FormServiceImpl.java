package cn.scauaie.service.impl;

import cn.scauaie.assembler.FormAssembler;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.dao.FormMapper;
import cn.scauaie.error.ProcessingException;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.service.FormService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
     * @return ResponseEntity
     */
    public FormDO createForm(String openid, FormAO formAO) {
        FormDO formDO = formAssembler.assembleFormDOByOpenidAndFormAO(openid, formAO);
        return createForm(formDO);
    }

    /**
     * 直接保存Form，不进行参数校验
     * @param formDO FormDO
     * @return ResponseEntity
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
