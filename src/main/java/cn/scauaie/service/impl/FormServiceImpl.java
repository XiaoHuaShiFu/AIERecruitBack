package cn.scauaie.service.impl;

import cn.scauaie.assemble.FormAssemble;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.common.error.ErrorResponse;
import cn.scauaie.dao.FormMapper;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

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
    private FormAssemble formAssemble;

    /**
     * 校验code并创建Form
     *
     * @param code String
     * @param formAO FormAO
     * @return
     */
    public ResponseEntity checkCodeAndCreateFormAndGetForm(String code, FormAO formAO) {
        String openid = weChatMpManager.getOpenidByCode(code);
        if (openid == null) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_PARAMETER.getCode(), "The code is not valid.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        FormDO formDO = formAssemble.assembleFormDOByOpenidAndFormAO(openid, formAO);
        return createForm(formDO);
    }

    /**
     * 直接保存Form，不进行参数校验
     * @param formDO FormDO
     * @return ResponseEntity
     */
    public ResponseEntity createForm(FormDO formDO) {
        if (formMapper.insertSelective(formDO) < 1) {
            return new ResponseEntity<>(
                    new ErrorResponse(ErrorCode.OPERATION_CONFLICT.getCode(),
                            "Request was denied due to conflict, the openid already exists."),
                    HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
