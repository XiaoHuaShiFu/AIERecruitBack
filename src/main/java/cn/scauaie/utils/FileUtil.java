package cn.scauaie.utils;

import cn.scauaie.model.po.RemoteDirAndFileName;

import java.util.UUID;

/**
 * 描述: 文件相关的辅助类
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 14:09
 */
public class FileUtil {

    /**
     * 获取文件的扩展名
     * @param fileName String
     * @return fileExtensionName
     */
    public static String getFileExtensionName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 随机生成一个文件名，可指定扩展名
     *
     * @param extensionName String
     * @return randomFileName
     */
    public static String randomFileName(String extensionName) {
        String randomName = UUID.randomUUID().toString();
        return randomName + "." + extensionName;
    }

    /**
     * 随机生成一个文件名，从oldFileName获取扩展名
     *
     * @param originalFileName 原文件名
     * @return randomFileName
     */
    public static String randomFileNameByOriginalFileName(String originalFileName) {
        String randomName = UUID.randomUUID().toString() ;
        String extensionName = getFileExtensionName(originalFileName);
        return randomName + "." + extensionName;
    }

    /**
     * 对文件的url进行解析
     * 成为dirPath和fileName
     *
     * @param fileUrl 文件url
     * @return RemoteDirAndFileName
     */
    public static RemoteDirAndFileName parseFileUrl(String host, String fileUrl) {
        int remoteDirIdx = fileUrl.indexOf(host) + host.length();
        int fileNameIdx = fileUrl.lastIndexOf("/") + 1;
        String remoteDir = fileUrl.substring(remoteDirIdx, fileNameIdx);
        String fileName = fileUrl.substring(fileNameIdx);
        return new RemoteDirAndFileName(remoteDir, fileName);
    }

}
