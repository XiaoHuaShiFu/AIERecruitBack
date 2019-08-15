package cn.scauaie.dao;

import cn.scauaie.model.dao.InterviewerDO;

public interface InterviewerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InterviewerDO record);

    int insertSelective(InterviewerDO record);

    InterviewerDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InterviewerDO record);

    int updateByPrimaryKey(InterviewerDO record);

    int countByOpenid(String openid);

    InterviewerDO getInterviewerByOpenid(String openid);
}