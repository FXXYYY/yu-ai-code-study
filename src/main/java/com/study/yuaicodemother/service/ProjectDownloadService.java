package com.study.yuaicodemother.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author fxy
 * @date 2026/4/23
 */
public interface ProjectDownloadService {
    void downloadProjectAsZip(String projectPath, String downloadFileName, HttpServletResponse response);
}
