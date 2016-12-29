package cn.com.wenyi.sprocr.controller;

import cn.com.wenyi.sprocr.service.HttpService;
import cn.com.wenyi.sprocr.service.ImageService;
import cn.com.wenyi.sprocr.service.OcrService;
import cn.com.wenyi.sprocr.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;

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
    @Autowired
    ImageService imageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object hello(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "charset=utf-8");
        return "Hello!";
    }

    @RequestMapping(value = "url", method = RequestMethod.GET)
    public Object readByUrl(@RequestParam(value = "imageUrl") String imgeUrl) {
        return ocrService.image2word(FileUtil.mkFile(httpService.request(imgeUrl), String.valueOf(System.currentTimeMillis()), ".png"));
    }

    @RequestMapping(value = "file", method = RequestMethod.POST)
    public Object readByFile(HttpServletResponse response, @RequestParam("file") MultipartFile imageFile) {
        logger.info("super/file");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "charset=utf-8");
        return ocrService.image2word(FileUtil.mkFile(imageFile));
    }

    @RequestMapping(value = "base64",method = RequestMethod.POST)
    public Object readByFile(HttpServletResponse response, @RequestBody String base64Str) {
        logger.info("super/base64");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "charset=utf-8");
        BASE64Decoder dBase64 = new BASE64Decoder();
        InputStream inputStream = null;
        try {
            byte[] base64 =  dBase64.decodeBuffer(URLDecoder.decode(base64Str, "UTF-8").split("base64,")[1]);
            inputStream = new ByteArrayInputStream(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            return ocrService.image2word(ImageIO.read(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
