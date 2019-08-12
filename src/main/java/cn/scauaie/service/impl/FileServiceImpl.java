package cn.scauaie.service.impl;

import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.error.ProcessingException;
import cn.scauaie.util.ftp.FTPClientTemplate;
import cn.scauaie.manager.ftp.FtpConst;
import cn.scauaie.util.file.FileUrl;
import cn.scauaie.service.FileService;
import cn.scauaie.util.file.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private FTPClientTemplate ftpClientTemplate;

    /**
     * 上传文件到ftp服务器
     *
     * @param file MultipartFile
     * @param fileName String
     * @param path String
     * @return boolean
     */
    @Override
    public boolean save(MultipartFile file, String fileName, String path) {
        //将文件缓存到本地文件夹
        File bufFile = bufferFileOnLocal(file, fileName);
        //上传是否成功
        boolean success;
        try {
            //将file上传到ftp服务器
            success = ftpClientTemplate.uploadFile(path, bufFile);
        } catch (IOException e) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Upload file failed.");
        }
        //删除本地缓存文件
        deleteLocalFile(bufFile);

        //上传成功
        return success;
    }

    /**
     * 上传文件，会随机生成文件名
     *
     * @param file 文件
     * @param dirPath 文件目录路径
     * @return 文件名
     */
    @Override
    public String save(MultipartFile file, String dirPath) {
        //随机生成文件名
        String fileName = FileNameUtils.getRandomFileNameByOriginalFileName(file.getOriginalFilename());
        //上传文件
        boolean success = save(file, fileName, dirPath);
        //上传失败
        if (!success) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Upload file failed.");
        }
        return fileName;
    }

    /**
     * 上传文件，并获得文件的Url
     *
     * @param file 文件
     * @param dirPath 文件目录路径
     * @return 文件名
     */
    @Override
    public String saveAndGetUrl(MultipartFile file, String dirPath) {
        String fileName = save(file, dirPath);
        //文件url
        return FtpConst.HOST + dirPath + fileName;
    }

    /**
     * 删除文件，通过路径和文件名
     *
     * @param fileName String
     * @param remoteDir String
     */
    @Override
    public void delete(String fileName, String remoteDir) {
        try {
            ftpClientTemplate.deleteFile(remoteDir, fileName);
        } catch (IOException e) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Delete file failed.");
        }
    }

    /**
     * 删除文件，通过文件url
     *
     * @param fileUrl 文件url
     */
    @Override
    public void delete(String fileUrl) {
        //解析url
        FileUrl remoteDirAndFileName = FileNameUtils.parseFileUrl(fileUrl);
        //删除文件
        System.out.println(remoteDirAndFileName.getFileName());
        System.out.println(remoteDirAndFileName.getDirectoryPath());
        delete(remoteDirAndFileName.getFileName(), remoteDirAndFileName.getDirectoryPath());
    }

    /**
     * 更新文件，需要MultipartFile和原文件Url
     *
     * @param file
     * @param oldUrl
     * @return
     */
    public String updateFile(MultipartFile file, String oldUrl, String dirPath) {
        //上传文件
        String newUrl = saveAndGetUrl(file, dirPath);
        //删除旧文件
        if (oldUrl != null) {
            delete(oldUrl);
        }
        return newUrl;
    }


    // TODO: 2019/8/11 有机会思考一下，我觉得不太优雅
    /**
     * 删除保存在本地的文件
     *
     * @param file File
     */
    private void deleteLocalFile(File file) {
        //删除本地文件夹里面的file
        boolean delSuccess = file.delete();
        //删除失败
        if (!delSuccess) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Delete file failed.");
        }
    }

    // TODO: 2019/8/11 有机会思考一下，我觉得不太优雅
    /**
     * 将文件缓存到本地
     *
     * @param file MultipartFile
     * @param fileName String
     * @return File
     */
    private File bufferFileOnLocal(MultipartFile file, String fileName) {
        //将文件缓存到本地文件夹
        File bufFile = new File(FtpConst.BUF_PATH, fileName);
        try {
            //保存文件
            file.transferTo(bufFile);
        } catch (IOException e) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Upload file failed.");
        }
        return bufFile;
    }

}
