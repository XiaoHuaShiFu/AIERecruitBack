package cn.scauaie.manager;

import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.model.query.ResultQuery;
import cn.scauaie.result.Result;

import java.util.List;
import java.util.Optional;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-27 21:48
 */
public interface ResultManager {

    Result<ResultAO> saveResult(ResultAO resultAO);

    Optional<List<ResultAO>> listResults(ResultQuery query);

}
