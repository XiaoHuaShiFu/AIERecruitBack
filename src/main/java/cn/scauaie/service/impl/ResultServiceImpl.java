package cn.scauaie.service.impl;

import cn.scauaie.dao.ResultMapper;
import cn.scauaie.exception.ProcessingException;
import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.model.dao.ResultDO;
import cn.scauaie.model.query.ResultQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.ResultService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("resultService")
public class ResultServiceImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Autowired
    private ResultMapper resultMapper;

    // TODO: 2019/8/20 这里要检查面试官权限
    @Override
    public Result<ResultAO> saveResult(ResultAO resultAO) {
        ResultDO resultDO = new ResultDO();
        BeanUtils.copyProperties(resultAO, resultDO);
        int count = resultMapper.insertSelective(resultDO);
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Insert result failed.");
        }

        resultAO.setId(resultDO.getId());
        return Result.success(resultAO);
    }

    /**
     * 查询结果列表
     *
     * @param pageNum 页码
     * @param pageSize 页条数
     * @param formId 报名表编号
     * @return List<ResultAO>
     */
    @Override
    public List<ResultAO> listResults(Integer pageNum, Integer pageSize, Integer formId) {
        ResultQuery query = new ResultQuery(pageNum, pageSize, formId);
        return listResults(query);
    }

    /**
     * 查询结果列表
     *
     * @param query 搜索参数
     * @return List<ResultAO>
     */
    private List<ResultAO> listResults(ResultQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<ResultDO> resultDOList = resultMapper.listByFormId(query.getFid());
        if (resultDOList.size() < 1) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        List<ResultAO> resultAOList = new ArrayList<>(resultDOList.size());
        for (ResultDO resultDO : resultDOList) {
            ResultAO resultAO = new ResultAO();
            BeanUtils.copyProperties(resultDO, resultAO);
            resultAOList.add(resultAO);
        }
        return resultAOList;
    }
}
