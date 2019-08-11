package cn.scauaie.common.ftp;

import cn.scauaie.utils.PropertiesUtil;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 17:07
 */
public final class FtpCommon {

    /**
     * 缓存文件的文件夹
     */
    public final static String BUF_PATH =
            new PropertiesUtil("ftp.properties").getProperty("ftp.buf.path");

    /**
     * ftp文件的前缀
     */
    public final static String HOST =
            new PropertiesUtil("ftp.properties").getProperty("ftp.host.prefix");

}
