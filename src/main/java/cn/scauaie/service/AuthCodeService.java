package cn.scauaie.service;

import cn.scauaie.model.ao.AuthCodeAO;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-21 16:18
 */
public interface AuthCodeService {

    List<AuthCodeAO> createAndSaveAuthCodes(String dep, Integer count);

}
