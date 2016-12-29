package cn.com.wenyi.sprocr.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by wyiss on 2016/12/29.
 */
public class FileUtil {

    public static final String path = "img/";

    public static File mkFile(MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        String fileType = multipartFile.getContentType();
        try {
            return mkFile(multipartFile.getInputStream(), originalFileName, fileType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File mkFile(InputStream inputStream, String originalFileName, String fileType) {
        OutputStream outputStream = null;
        File file = new File(fileNameGenerator(originalFileName, fileType));
        try {
            outputStream = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * gendrator a file via MultipartFile
     * fileType like : image/png
     */
    private static String fileNameGenerator(String originalFileName, String fileType) {
        String separator = "/";
        String type = ".png";
        if (fileType.contains(separator)) {
            String[] types = fileType.split(separator, 2);
            type = "." + types[1];
        } else {
            type = "." + fileType;
        }
        return (path + originalFileName + "_" + String.valueOf(System.currentTimeMillis()) + type);
    }
}
