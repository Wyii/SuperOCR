package cn.com.wenyi.sprocr.controller;

import cn.com.wenyi.sprocr.service.HttpService;
import cn.com.wenyi.sprocr.service.OcrService;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by wyiss on 2016/12/28.
 */
@RestController
@RequestMapping("super")
public class ImageController {

    static private Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    OcrService ocrService;
    @Autowired
    HttpService httpService;

    @RequestMapping(value = "url",method = RequestMethod.GET)
    public Object readByUrl(@RequestParam(value = "imageUrl") String imgeUrl) {
        File imageFile = new File(httpService.request(imgeUrl));
        return ocrService.image2word(imageFile);
    }

    @RequestMapping(value = "file",method = RequestMethod.POST)
    public Object readByFile(@RequestParam("file") MultipartFile imageFile) {
        logger.info("super/file");
        File newImageFile = null;
        try {
            newImageFile = new File(httpService.mkFile(imageFile.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ocrService.image2word(newImageFile);
    }
}
