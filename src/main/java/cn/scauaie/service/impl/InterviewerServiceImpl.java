package cn.scauaie.service.impl;

import cn.scauaie.dao.AuthCodeMapper;
import cn.scauaie.dao.InterviewerMapper;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.exception.ProcessingException;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.manager.constant.WeChatMp;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.dao.AuthCodeDO;
import cn.scauaie.model.dao.InterviewerDO;
import cn.scauaie.service.InterviewerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("interviewerService")
public class InterviewerServiceImpl implements InterviewerService {

    @Autowired
    private WeChatMpManager weChatMpManager;

    @Autowired
    private AuthCodeMapper authCodeMapper;

    @Autowired
    private InterviewerMapper interviewerMapper;

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
    public InterviewerAO saveInterviewer(String wxCode, String authCode, String name) {
        AuthCodeDO authCodeDO = authCodeMapper.getAuthCodeByCode(authCode);
        if (authCodeDO == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The specified authCode does not exist.");
        }

        String openid = weChatMpManager.getOpenid(wxCode, WeChatMp.SCAU_AIE);
        if (openid == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }

        int count = interviewerMapper.countByOpenid(openid);
        if (count >= 1) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the openid already exists.");
        }

        InterviewerDO interviewerDO = new InterviewerDO();
        interviewerDO.setDep(authCodeDO.getDep());
        interviewerDO.setName(name);
        interviewerDO.setOpenid(openid);
        count = interviewerMapper.insert(interviewerDO);
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Insert interviewer failed.");
        }

        //删除已经被使用的认证码
        count = authCodeMapper.deleteAuthCodeByCode(authCode);
        if (count < 1) {
            // TODO: 2019/8/15 加进日志
        }

        InterviewerAO interviewerAO = new InterviewerAO();
        BeanUtils.copyProperties(interviewerDO, interviewerAO);
        return interviewerAO;
    }

    /**
     * 通过code寻找Interviewer
     *
     * @param code 微信小程序wx.login()接口返回值
     * @return InterviewerAO
     */
    @Override
    public InterviewerAO getInterviewerByCode(String code) {
        String openid = weChatMpManager.getOpenid(code, WeChatMp.SCAU_AIE);
        if (openid == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }

        InterviewerDO interviewerDO = interviewerMapper.getInterviewerByOpenid(openid);
        if (interviewerDO == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The openid is not found.");
        }
        InterviewerAO interviewerAO = new InterviewerAO();
        BeanUtils.copyProperties(interviewerDO, interviewerAO);
        return interviewerAO;
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

    /**
     * 获取面试官通过编号
     *
     * @param id 面试官编号
     * @return InterviewerAO
     */
    @Override
    public InterviewerAO getInterviewer(Integer id) {
        InterviewerDO interviewerDO = interviewerMapper.selectByPrimaryKey(id);
        InterviewerAO interviewerAO = new InterviewerAO();
        BeanUtils.copyProperties(interviewerDO, interviewerAO);
        return interviewerAO;
    }
}
