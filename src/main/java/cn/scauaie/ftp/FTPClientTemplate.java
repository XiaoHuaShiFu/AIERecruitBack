package cn.scauaie.ftp;

import org.apache.commons.lang3.Validate;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-06-07 20:46
 */
public class FTPClientTemplate {

    private static final Logger logger = LoggerFactory.getLogger(FTPClientTemplate.class);

    private FTPClientConfig ftpClientConfig;

    private String server;

    private String username;

    private String password;

    private int port = 21;

    public FTPClientTemplate(String host, String username, String password) {
        this.server = host;
        this.username = username;
        this.password = password;
    }

    /**
     * 顶层模板方法
     * @param callback FTPClientCallback
     * @throws IOException .
     */
    public void execute(FTPClientCallback callback) throws IOException {
        FTPClient ftp = new FTPClient();
        try {
            if (this.getFtpClientConfig() != null) {
                ftp.configure(this.getFtpClientConfig());
            }

            ftp.connect(server, getPort());
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new IOException("failed to connect to the FTP Server:" + server);
            }

            boolean isLoginSuc = ftp.login(this.getUsername(), this.getPassword());
            if (!isLoginSuc) {
                throw new IOException("wrong username or password, please try to login again.");
            }
            callback.processFTPRequest(ftp);
        } finally {
            if (ftp.isConnected()) {
                ftp.disconnect();
            }
        }
    }

    /**
     * 上传文件
     * @param remoteDir 目录
     * @param file 文件
     * @return 是否成功
     * @throws IOException .
     */
    public boolean uploadFile(final String remoteDir, final File file) throws IOException {
        final boolean[] isSuc = {false};
        execute((ftp) -> {
            changeWorkingDir(ftp, remoteDir);
            ftp.enterLocalPassiveMode();
            ftp.setBufferSize(1024);
            ftp.setControlEncoding("UTF-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

            try (FileInputStream fis = new FileInputStream(file)){
                isSuc[0] = ftp.storeFile(file.getName(), fis);
            }
        });
        return isSuc[0];
    }

    /**
     * 删除文件
     * @param remoteDir 目录
     * @param ftpFileName 文件名
     * @return 是否成功
     * @throws IOException .
     */
    public boolean deleteFile(final String remoteDir, final String ftpFileName) throws IOException {
        final boolean[] isSuc = {false};
        execute((ftp) -> {
            ftp.enterLocalPassiveMode();
            changeWorkingDir(ftp, remoteDir);
            isSuc[0] = ftp.deleteFile(ftpFileName);
        });
        return isSuc[0];
    }

    /**
     * 修改文件名称
     * @param remoteDir 目录
     * @param from 原文件名
     * @param to 新文件名
     * @throws IOException .
     */
    public boolean rename(final String remoteDir, String from, String to) throws IOException {
        final boolean[] isSuc = {false};
        execute((ftp) -> {
            ftp.enterLocalPassiveMode();
            changeWorkingDir(ftp, remoteDir);
            isSuc[0] = ftp.rename(from, to);
        });
        return isSuc[0];
    }

    /**
     * 列出所有文件的名字
     * @param remoteDir 目录
     * @param fileNamePattern 文件名匹配模式
     * @return 文件名列表
     * @throws IOException .
     */
    public List<String> listFileNames(final String remoteDir, final String fileNamePattern) throws IOException{
        final List<String[]> container = new ArrayList<>();
        execute((ftp) -> {
            ftp.enterLocalPassiveMode();
            changeWorkingDir(ftp, remoteDir);
            container.add(ftp.listNames());
        });
        if (container.size() > 0) {
            return Arrays.asList(container.get(0));
        }
        return null;
    }

    /**
     * 改变工作目录
     * @param ftp FTPClient
     * @param remoteDir 目录名
     * @throws IOException .
     */
    protected void changeWorkingDir(FTPClient ftp, String remoteDir) throws IOException {
        Validate.notEmpty(remoteDir);
        ftp.changeWorkingDirectory(remoteDir);
        if (logger.isDebugEnabled()) {
            logger.debug("working dir:" + ftp.printWorkingDirectory());
        }
    }

    public FTPClientConfig getFtpClientConfig() {
        return ftpClientConfig;
    }

    public void setFtpClientConfig(FTPClientConfig ftpClientConfig) {
        this.ftpClientConfig = ftpClientConfig;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
