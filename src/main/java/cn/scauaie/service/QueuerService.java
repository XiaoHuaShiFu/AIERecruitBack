package cn.scauaie.service;

import cn.scauaie.model.bo.QueuerBO;
import cn.scauaie.model.query.QueuerQuery;
import cn.scauaie.result.Result;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 2:20
 */
public interface QueuerService {
    Result<QueuerBO> saveQueuer(String dep, Integer id);

    Result<QueuerBO> deleteQueuerByDep(String dep);

    Result<QueuerBO> checkGapAndDeleteQueuerByDep(Integer interviewerId, String dep);

    Result<QueuerBO> getQueuerByDepAndFormId(Integer formId, String dep);

    Result<List<QueuerBO>> listQueuersByDep(QueuerQuery query);

    int getCountByDep(String dep);

}
