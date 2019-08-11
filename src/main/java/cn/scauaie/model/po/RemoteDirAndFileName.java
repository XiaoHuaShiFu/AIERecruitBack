package cn.scauaie.model.po;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 15:56
 */
public class RemoteDirAndFileName {

    private String remoteDir;
    private String fileName;

    public RemoteDirAndFileName() {
    }

    public RemoteDirAndFileName(String remoteDir, String fileName) {
        this.remoteDir = remoteDir;
        this.fileName = fileName;
    }

    public String getRemoteDir() {
        return remoteDir;
    }

    public void setRemoteDir(String remoteDir) {
        this.remoteDir = remoteDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
