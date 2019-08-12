package cn.scauaie.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    boolean save(MultipartFile file, String fileName, String path);

    String save(MultipartFile file, String dirPath);

    String saveAndGetUrl(MultipartFile file, String dirPath);

    void delete(String fileName, String path);

    void delete(String fileUrl);

    String updateFile(MultipartFile file, String oldUrl, String dirPath);
}
