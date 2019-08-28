package cn.scauaie.dao;

import cn.scauaie.model.ao.ResultQrcodeAO;
import cn.scauaie.model.dao.ResultQrcodeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResultQrcodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResultQrcodeDO record);

    int insertSelective(ResultQrcodeDO record);

    ResultQrcodeDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResultQrcodeDO record);

    int updateByPrimaryKey(ResultQrcodeDO record);

    /**
     * 插入二维码列表
     *
     * @param qrcodeList 二维码列表
     * @param resultId 结果编号
     * @return 成功插入数量
     */
    int insertQrcodeList(@Param("qrcodeList") List<ResultQrcodeDO> qrcodeList, @Param("rid") Integer resultId);

    /**
     * 获取二维码列表
     *
     * @param resultId 结果编号
     * @return List<ResultQrcodeAO>
     */
    List<ResultQrcodeAO> listQrcodes(@Param("rid") Integer resultId);
}