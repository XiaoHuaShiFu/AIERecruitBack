package cn.scauaie.service;

import cn.scauaie.model.bo.QueuerBO;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 2:20
 */
public interface QueuerService {
    QueuerBO save(String dep, Integer id);

    QueuerBO getQueuerByDepAndFormId(Integer formId, String dep);

    List<QueuerBO> listQueuersByDep(String dep);
}
