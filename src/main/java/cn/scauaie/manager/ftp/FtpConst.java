package cn.scauaie.manager.ftp;

import cn.scauaie.util.PropertiesUtils;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 17:07
 */
public final class FtpConst {

    /**
     * 缓存文件的文件夹
     */
    public final static String BUF_PATH =
            new PropertiesUtils("ftp.properties").getProperty("ftp.buf.path");

    /**
     * ftp文件的前缀
     */
    public final static String HOST =
            new PropertiesUtils("ftp.properties").getProperty("ftp.host.prefix");

}
