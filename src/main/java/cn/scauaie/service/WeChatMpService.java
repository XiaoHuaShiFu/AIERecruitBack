package cn.scauaie.service;

import cn.scauaie.model.dto.MessageTemplateDataDTO;
import cn.scauaie.result.Result;

import java.util.Map;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-09-01 14:12
 */
public interface WeChatMpService {

    Result sendTemplateMessage(Integer userFormId, String templateId, String page,
                               Map<String, MessageTemplateDataDTO> data, String emphasisKeyword);

    Result<String> saveFormId(String mpFormId, Integer userFormId);

}
