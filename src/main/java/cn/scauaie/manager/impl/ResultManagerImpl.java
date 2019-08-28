package cn.scauaie.manager.impl;

import cn.scauaie.dao.ResultMapper;
import cn.scauaie.dao.ResultQrcodeMapper;
import cn.scauaie.manager.ResultManager;
import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.model.dao.ResultDO;
import cn.scauaie.model.dao.ResultQrcodeDO;
import cn.scauaie.model.query.ResultQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-27 21:47
 */
@Component("resultManager")
public class ResultManagerImpl implements ResultManager {

    private static final Logger logger = LoggerFactory.getLogger(ResultManagerImpl.class);

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private ResultQrcodeMapper resultQrcodeMapper;

    @Autowired
    private Mapper mapper;

    /**
     * 保存面试结果
     *
     * @param resultAO ResultAO
     * @return Result<ResultAO>
     */
    @Override
    public Result<ResultAO> saveResult(ResultAO resultAO) {
        ResultDO resultDO = mapper.map(resultAO, ResultDO.class);
        int count = resultMapper.insertSelective(resultDO);
        if (count < 1) {
            logger.error("Insert result failed.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert result failed.");
        }

        if (resultAO.getQrcodes() != null) {
            List<ResultQrcodeDO> resultQrcodeDOList =  resultAO.getQrcodes()
                    .stream().map(resultQrcodeAO -> mapper.map(resultQrcodeAO, ResultQrcodeDO.class))
                    .collect(Collectors.toList());
            count = resultQrcodeMapper.insertQrcodeList(resultQrcodeDOList, resultDO.getId());
            if (count < 1) {
                logger.error("Insert qrcode failed.");
                return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert qrcode failed.");
            }
        }

        resultAO.setId(resultDO.getId());
        return Result.success(resultAO);
    }

    /**
     * 查询结果列表
     *
     * @param query 搜索参数
     * @return List<ResultAO>
     */
    public Optional<List<ResultAO>> listResults(ResultQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<ResultDO> resultDOList = resultMapper.listResultsByFormId(query.getFid());
        if (resultDOList.size() < 1) {
            return Optional.empty();
        }

        return Optional.of(resultDOList.stream()
                .map(resultDO -> {
                    ResultAO resultAO = mapper.map(resultDO, ResultAO.class);
                    resultAO.setQrcodes(resultQrcodeMapper.listQrcodes(resultAO.getId()));
                    return resultAO;
                })
                .collect(Collectors.toList()));
    }

}
