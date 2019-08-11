package cn.scauaie.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    boolean upload(MultipartFile file, String fileName, String path);

    String upload(MultipartFile file, String dirPath);

    String uploadAndGetUrl(MultipartFile file, String dirPath);

    void delete(String fileName, String path);

    void delete(String fileUrl);

    String updateFile(MultipartFile file, String oldUrl, String dirPath);
}
