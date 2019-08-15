package cn.scauaie.service;

import cn.scauaie.model.ao.InterviewerAO;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface InterviewerService {
    InterviewerAO saveInterviewer(String wxCode, String authCode, String name);

    InterviewerAO getInterviewer(Integer id);

    InterviewerAO getInterviewerByCode(String code);
}
