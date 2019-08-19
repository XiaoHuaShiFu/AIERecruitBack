package cn.scauaie.service;

import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.result.Result;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface ResultService {

    Result<ResultAO> saveResult(ResultAO resultAO);

    List<ResultAO> listResults(Integer pageNum, Integer pageSize, Integer formId);
}
