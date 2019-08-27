package cn.scauaie.service.impl;

import cn.scauaie.dao.AuthCodeMapper;
import cn.scauaie.dao.InterviewerMapper;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.manager.constant.WeChatMp;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.dao.AuthCodeDO;
import cn.scauaie.model.dao.InterviewerDO;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.InterviewerService;
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
@Service("interviewerService")
public class InterviewerServiceImpl implements InterviewerService {

    private static final Logger logger = LoggerFactory.getLogger(InterviewerServiceImpl.class);

    @Autowired
    private WeChatMpManager weChatMpManager;

    @Autowired
    private AuthCodeMapper authCodeMapper;

    @Autowired
    private InterviewerMapper interviewerMapper;

    @Autowired
    private Mapper mapper;

    // TODO: 2019/8/15 此处需要加锁，防止一个认证码被多个人使用
    /**
     * 保存面试官
     *
     * @param wxCode 微信小程序wx.login()接口返回值
     * @param authCode 认证码
     * @param name 面试官名字
     * @return InterviewerAO
     */
    @Override
    public Result<InterviewerAO> saveInterviewer(String wxCode, String authCode, String name) {
        AuthCodeDO authCodeDO = authCodeMapper.getAuthCodeByCode(authCode);
        if (authCodeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified authCode does not exist.");
        }

        String openid = weChatMpManager.getOpenid(wxCode, WeChatMp.SCAU_AIE);
        if (openid == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }

        int count = interviewerMapper.countByOpenid(openid);
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the openid already exists.");
        }

        InterviewerDO interviewerDO = new InterviewerDO();
        interviewerDO.setDep(authCodeDO.getDep());
        interviewerDO.setName(name);
        interviewerDO.setOpenid(openid);
        count = interviewerMapper.insert(interviewerDO);
        if (count < 1) {
            logger.error("Insert interviewer failed. ");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert interviewer failed.");
        }

        //删除已经被使用的认证码
        count = authCodeMapper.deleteAuthCodeByCode(authCode);
        if (count < 1) {
            logger.error("Delete authCode fail. authcode: {}", authCode);
        }

        return Result.success(mapper.map(interviewerDO, InterviewerAO.class));
    }

    /**
     * 获取面试官通过编号
     *
     * @param id 面试官编号
     * @return InterviewerAO
     */
    @Override
    public InterviewerAO getInterviewer(Integer id) {
        InterviewerDO interviewerDO = interviewerMapper.getInterviewer(id);
        if (interviewerDO == null) {
            return null;
        }
        return mapper.map(interviewerDO, InterviewerAO.class);
    }


    /**
     * 通过code寻找Interviewer
     *
     * @param code 微信小程序wx.login()接口返回值
     * @return InterviewerAO
     */
    @Override
    public Result<InterviewerAO> getInterviewerByCode(String code) {
        String openid = weChatMpManager.getOpenid(code, WeChatMp.SCAU_AIE);
        if (openid == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }

        InterviewerDO interviewerDO = interviewerMapper.getInterviewerByOpenid(openid);
        if (interviewerDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The openid is not found.");
        }
        return Result.success(mapper.map(interviewerDO, InterviewerAO.class));
    }

    /**
     * 获得面试官列表
     *
     * @param pageNum 页码
     * @param pageSize 页条数
     * @return Result<List<InterviewerAO>>
     */
    @Override
    public Result<List<InterviewerAO>> listInterviewers(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<InterviewerDO> interviewerDOList = interviewerMapper.listInterviewers();
        if (interviewerDOList.size() < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        return Result.success(
                interviewerDOList.stream()
                        .map(interviewerDO -> mapper.map(interviewerDO, InterviewerAO.class))
                        .collect(Collectors.toList()));
    }

    /**
     * 通过编号获取部门
     *
     * @param id 编号
     * @return 部门
     */
    @Override
    public String getDep(Integer id) {
        return interviewerMapper.getDep(id);
    }

}
