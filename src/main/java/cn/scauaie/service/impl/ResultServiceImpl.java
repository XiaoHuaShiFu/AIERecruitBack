package cn.scauaie.service.impl;

import cn.scauaie.constant.DepEnum;
import cn.scauaie.dao.ResultMapper;
import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.model.dao.ResultDO;
import cn.scauaie.model.query.ResultQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.ResultService;
import cn.scauaie.util.PropertiesUtils;
import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("resultService")
public class ResultServiceImpl implements ResultService {

    private final static Logger logger = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private Mapper mapper;

    /**
     * 结果模板集合的文件名
     */
    private final static String RESULT_SET_FILE_NAME = "result-set.properties";

    /**
     * 发送面试结果
     *
     * @param id 报名表编号
     * @param firstDep 报名表编号
     * @param secondDep 报名表编号
     * @return Result<ResultAO>
     */
    public Result<ResultAO> sendInterviewResults(Integer id, String firstDep, String secondDep) {
        ResultAO resultAO = new ResultAO();
        resultAO.setFid(id);
        resultAO.setResult(PropertiesUtils.getProperty(firstDep, RESULT_SET_FILE_NAME));
        Result<ResultAO> result = saveResult(resultAO);
        if (!result.isSuccess()) {
            return result;
        }

        if (firstDep.equals(DepEnum.ZKB.name()) || firstDep.equals(DepEnum.XCB.name())) {

            return result;
        }

        if (!firstDep.equals(secondDep)) {
            resultAO.setResult(PropertiesUtils.getProperty(secondDep, RESULT_SET_FILE_NAME));
            result = saveResult(resultAO);
        }
        return result;
    }

    // TODO: 2019/8/20 这里要检查面试官权限
    @Override
    public Result<ResultAO> saveResult(ResultAO resultAO) {
        ResultDO resultDO = mapper.map(resultAO, ResultDO.class);
        int count = resultMapper.insertSelective(resultDO);
        if (count < 1) {
            logger.error("Insert result failed.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert result failed.");
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
     * @return Result<List<ResultAO>>
     */
    @Override
    public Result<List<ResultAO>> listResults(Integer pageNum, Integer pageSize, Integer formId) {
        ResultQuery query = new ResultQuery(pageNum, pageSize, formId);
        List<ResultAO> resultAOList = listResults(query);
        if (resultAOList == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        return Result.success(resultAOList);
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
            return null;
        }

        return resultDOList.stream()
                .map(resultDO -> mapper.map(resultDO, ResultAO.class))
                .collect(Collectors.toList());
    }
}
