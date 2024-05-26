package com.ruoyi.dust.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MultipartFileToFile {

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {
        File toFile = null;
        if (file != null && StringUtils.isNotBlank(file.getOriginalFilename()) && file.getSize() > 0) {
            toFile = new File(file.getOriginalFilename());
            try (
                    InputStream ins = file.getInputStream();
                    OutputStream os = new FileOutputStream(toFile)
            ) {
                int bytesRead;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        }
        return toFile;
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     * @return
     */
    public static Boolean deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            return del.delete();
        }
        return null;
    }
}