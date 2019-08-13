package cn.scauaie.service.constant;

import cn.scauaie.util.PropertiesUtils;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 17:07
 */
public final class FileConsts {

    /**
     * 缓存文件的文件夹
     */
    public final static String BUF_PATH = PropertiesUtils.getProperty("ftp.buf.path", "ftp.properties");

    /**
     * ftp文件的前缀
     */
    public final static String HOST = PropertiesUtils.getProperty("ftp.host.prefix", "ftp.properties");

}
