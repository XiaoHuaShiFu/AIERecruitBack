package cn.scauaie.dao;

import cn.scauaie.model.dao.WorkDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkMapper {

    int insertWork(WorkDO record);

    int insertWorkSelective(WorkDO record);

    int deleteWork(Integer id);

    int deleteByWorkIdAndFormId(@Param("id") Integer id, @Param("fid") Integer fid);

    WorkDO getWork(Integer id);

    List<WorkDO> listWorksByFormId(Integer fid);

    int updateWorkSelective(WorkDO record);

    int updateWork(WorkDO record);

    int getCount(Integer id);

    int getCountByFormId(Integer fid);

    int getCountByWorkIdAndFormId(@Param("id") Integer id, @Param("fid") Integer fid);

    String getUrlByFormIdAndWorkId(@Param("id") Integer id, @Param("fid") Integer fid);

    String getUrlByWorkIdAndFormId(@Param("id") Integer id, @Param("fid") Integer fid);

}