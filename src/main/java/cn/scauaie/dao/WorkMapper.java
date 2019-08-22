package cn.scauaie.dao;

import cn.scauaie.model.dao.WorkDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkDO record);

    int insertSelective(WorkDO record);

    WorkDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkDO record);

    int updateByPrimaryKey(WorkDO record);

    List<WorkDO> selectByFid(Integer fid);

    int getCount(Integer id);

    int getCountByFormId(Integer fid);

    int getCountByWorkIdAndFormId(@Param("id") Integer id, @Param("fid") Integer fid);

    String getUrlByFormIdAndWorkId(@Param("id") Integer id, @Param("fid") Integer fid);

    int deleteByWorkIdAndFormId(@Param("id") Integer id, @Param("fid") Integer fid);

    String getUrlByWorkIdAndFormId(@Param("id") Integer id, @Param("fid") Integer fid);


}