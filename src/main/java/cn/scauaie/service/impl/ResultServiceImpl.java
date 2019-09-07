package cn.scauaie.service.impl;

import cn.scauaie.constant.AieConsts;
import cn.scauaie.constant.DepEnum;
import cn.scauaie.dao.ResultMapper;
import cn.scauaie.manager.ResultManager;
import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.model.ao.ResultQrcodeAO;
import cn.scauaie.model.dao.ResultDO;
import cn.scauaie.model.query.ResultQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.ResultService;
import cn.scauaie.util.PropertiesUtils;
import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
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

    @Autowired
    private ResultManager resultManager;

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private Mapper mapper;

    /**
     * 结果模板集合的文件名
     */
    private final static String RESULT_SET_FILE_NAME = "resultSet.properties";

    /**
     * 二维码前缀
     */
    private final static String QRCODE_PREFIX =
            PropertiesUtils.getProperty("qrcode.prefix", RESULT_SET_FILE_NAME);

    /**
     * 二维码后缀
     */
    private final static String QRCODE_SUFFIX = PropertiesUtils.getProperty("qrcode.suffix", RESULT_SET_FILE_NAME);

    /**
     * 发送面试结果
     *
     * @param id 报名表编号
     * @param firstDep 报名表编号
     * @param secondDep 报名表编号
     * @param pass 是否通过
     * @param onlyPassSecondDep 只通过第二志愿部门
     * @return Result<ResultAO>
     */
    public Result<ResultAO> sendInterviewResult(Integer id, String firstDep, String secondDep,
                                                Boolean pass, Boolean onlyPassSecondDep) {
        ResultAO resultAO = new ResultAO();
        resultAO.setFid(id);

        // 未通过
        if (!pass) {
            if (firstDep.equals(DepEnum.ZKB.name()) || firstDep.equals(DepEnum.XCB.name())) {
                resultAO.setResult(PropertiesUtils.getProperty(firstDep+".not.pass", RESULT_SET_FILE_NAME));
                return saveResult(resultAO);
            }
            resultAO.setResult(PropertiesUtils.getProperty("AIE.not.pass", RESULT_SET_FILE_NAME));
            return saveResult(resultAO);
        }


        // 只通过第二志愿部门的，发第二志愿部门二维码
        if (onlyPassSecondDep) {
            String result = MessageFormat.format(Objects.requireNonNull(PropertiesUtils.getProperty(
                    "only.pass.second.dep", RESULT_SET_FILE_NAME)), DepEnum.valueOf(secondDep).getCn());
            resultAO.setResult(result
                    + MessageFormat.format("（如群人数已满，请联系微信{0}：{1}。）",
                    DepEnum.valueOf(secondDep).getCn(),
                    PropertiesUtils.getProperty(secondDep + ".wx", RESULT_SET_FILE_NAME)));

            ResultQrcodeAO qrcode1 = new ResultQrcodeAO();
            qrcode1.setDep(secondDep);
            qrcode1.setQrcode(QRCODE_PREFIX + secondDep + QRCODE_SUFFIX);
            resultAO.setQrcodes(Collections.singletonList(qrcode1));
            return saveResult(resultAO);
        }


        // 下面的情况都需要设置第一志愿部门
        ResultQrcodeAO qrcode1 = new ResultQrcodeAO();
        qrcode1.setDep(firstDep);
        qrcode1.setQrcode(QRCODE_PREFIX + firstDep + QRCODE_SUFFIX);

        // 第一志愿部门是自科或者宣传部，只发第一志愿部门的通知和二维码
        if (firstDep.equals(DepEnum.ZKB.name()) || firstDep.equals(DepEnum.XCB.name())) {
            resultAO.setResult(PropertiesUtils.getProperty(firstDep, RESULT_SET_FILE_NAME)
                            + MessageFormat.format("（如群人数已满，请联系微信{0}：{1}。）",
                    DepEnum.valueOf(firstDep).getCn(),
                    PropertiesUtils.getProperty(firstDep + ".wx", RESULT_SET_FILE_NAME)));

            resultAO.setQrcodes(Collections.singletonList(qrcode1));
            return saveResult(resultAO);
        }

        // 下面的情况都是群面

        // 第一不是自科，第二是自科的，只发第一志愿部门
        if ((!firstDep.equals(DepEnum.ZKB.name())) && secondDep.equals(DepEnum.ZKB.name())) {
            resultAO.setResult(PropertiesUtils.getProperty(AieConsts.AIE, RESULT_SET_FILE_NAME)
                    + MessageFormat.format("（如群人数已满，请联系微信{0}：{1}。）",
                    DepEnum.valueOf(firstDep).getCn(),
                    PropertiesUtils.getProperty(firstDep + ".wx", RESULT_SET_FILE_NAME)));
            resultAO.setQrcodes(Collections.singletonList(qrcode1));
            return saveResult(resultAO);
        }

        // 第一志愿部门等于第二志愿部门
        if (firstDep.equals(secondDep)) {
            resultAO.setResult(PropertiesUtils.getProperty(AieConsts.AIE, RESULT_SET_FILE_NAME)
                    + MessageFormat.format("（如群人数已满，请联系微信{0}：{1}。）",
                    DepEnum.valueOf(firstDep).getCn(),
                    PropertiesUtils.getProperty(firstDep + ".wx", RESULT_SET_FILE_NAME)));
            resultAO.setQrcodes(Collections.singletonList(qrcode1));
            return saveResult(resultAO);
        }

        // 第一志愿部门不等于第二志愿部门，要设置两个二维码
        ResultQrcodeAO qrcode2 = new ResultQrcodeAO();
        qrcode2.setDep(secondDep);
        qrcode2.setQrcode(QRCODE_PREFIX + secondDep + QRCODE_SUFFIX);
        List<ResultQrcodeAO> resultQrcodeAOList = new ArrayList<>(2);
        resultQrcodeAOList.add(qrcode1);
        resultQrcodeAOList.add(qrcode2);
        resultAO.setQrcodes(resultQrcodeAOList);
        resultAO.setResult(PropertiesUtils.getProperty(AieConsts.AIE, RESULT_SET_FILE_NAME)
                + MessageFormat.format("（如群人数已满，请联系微信{0}：{1}，{2}：{3}。）",
                DepEnum.valueOf(firstDep).getCn(),
                PropertiesUtils.getProperty(firstDep + ".wx", RESULT_SET_FILE_NAME),
                DepEnum.valueOf(secondDep).getCn(),
                PropertiesUtils.getProperty(secondDep + ".wx", RESULT_SET_FILE_NAME)));

        return saveResult(resultAO);
    }

    // TODO: 2019/8/20 这里要检查面试官权限
    // TODO: 2019/8/28 在某些日期前不可以发布结果
    /**
     * 保存面试结果
     *
     * @param resultAO ResultAO
     * @return Result<ResultAO>
     */
    @Override
    public Result<ResultAO> saveResult(ResultAO resultAO) {
        return resultManager.saveResult(resultAO);
    }

    /**
     * 查询结果列表
     * 不附带二维码
     *
     * @param pageNum 页码
     * @param pageSize 页条数
     * @return Result<List<ResultAO>>
     */
    @Override
    public Result<List<ResultAO>> listResults(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ResultDO> resultDOList = resultMapper.listResults();
        if (resultDOList.size() < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        return Result.success(resultDOList.stream()
                .map(resultDO -> mapper.map(resultDO, ResultAO.class))
                .collect(Collectors.toList()));
    }

    /**
     * 查询结果列表
     * 附带二维码
     *
     * @param query 查询参数
     * @return Result<List<ResultAO>>
     */
    @Override
    public Result<List<ResultAO>> listResults(ResultQuery query) {
        Optional<List<ResultAO>> resultAOList = resultManager.listResults(query);
        return resultAOList.map(Result::success)
                .orElseGet(() -> Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found."));
    }

}
