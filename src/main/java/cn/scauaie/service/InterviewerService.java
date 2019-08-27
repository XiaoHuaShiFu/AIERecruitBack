package cn.scauaie.service;

import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.result.Result;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface InterviewerService {
    Result<InterviewerAO> saveInterviewer(String wxCode, String authCode, String name);

    InterviewerAO getInterviewer(Integer id);

    Result<InterviewerAO> getInterviewerByCode(String code);

    Result<List<InterviewerAO>> listInterviewers(Integer pageNum, Integer pageSize);

    String getDep(Integer id);
}
