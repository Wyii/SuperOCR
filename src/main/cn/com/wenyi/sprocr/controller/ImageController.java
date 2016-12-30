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
import javax.xml.bind.DatatypeConverter;
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
        return ocrService.image2word(FileUtil.mkFile(httpService.request(imgeUrl), String.valueOf(System.currentTimeMillis())));
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
        try {
            String a = "iVBORw0KGgoAAAANSUhEUgAAAGQAAADOCAIAAABHBzCLAAAFfUlEQVR42u2dMUjrXBiGxaFIKVK6\n" +
                    "FSlCKYIILkIXQQqCIAhOQidBcHBy6OTg5lBw6CB0cOzg4ODg0KFDBxeHDl06umRw6dDFoUuH/N9/\n" +
                    "P29umpOERLuked5Bku/mQM/DOScJeW6yYpPIWQEBsIAFrFg5PT1d+ZPV1dXn52envr+/r/VCoWBZ\n" +
                    "ltnw6+trc3NTjzk4OPhZPUmwGo2G/PTBYCDbJycnsq31o6Mj2R6NRpPJRGCVy2Wz7e7ubj6flwOG\n" +
                    "w6EcfH5+/oN6wkaW/Hr9W6vVHFiy8fj4qNsyrHq9nqfVbDaTYzqdju5eXl5q27j15K1ZNzc3Oju6\n" +
                    "3a7syoCS7evray2WSqXxeOxp8vb2Jv+koCVCVjsft548WNPpVBYUmW7r6+uyK8ikJ7K+WH+SzWZl\n" +
                    "29Pk9fXVGYYSWex0N249qWfDh4cH7UC/35cNZ1Fvt9vufmre3999R0rcepJgyTpVrVZ1u9VqaQdk\n" +
                    "oMmGdM8D0VyznLOnZ22KXk8SrNvbW12qZFVaW1uTE5bWK5VKJpP5+PiQukzDnZ0ds+3W1pZMWznA\n" +
                    "c9aLVU/YNNQrBol0RlYu5xQpu1qX6wadPnd3d+4hJt0uFot6jFyU/ay+zFfwchLgdidS5Grr7OwM\n" +
                    "WNwbAgtYwCLAAhawgAUsYBFgAQtYwAIWsAiwgAWsyPEVaYLsGieps2jsAJEmyK5xJ40WjR0g0vgW\n" +
                    "naTXorENkSakqEmvRWMbIk1IUZN2i8YOcEB8i2m0aOwAkca3aK5Z6bJo7ACRJsiucSeNFo0dINKY\n" +
                    "RSyaeMGiiRosGu4NgQUsYBFgAQtYwAIWsAiwgAUsYAELWARYwAJWnPjaMkEmjDu8i+b7xweZMO7w\n" +
                    "Lpow48Ud3kXzbcsEGS/u8C6ab1smyHhxh3fRPIQbL+7wLppWuPFirlm8i+b/BJkw7vAumm+Fxtd4\n" +
                    "waKJFyyaqMGi4d4QWMACFgEWsIAFLGABiwALWMACFrCARYCVdlifn58bGxv6ECGfzw+HQykeHx+v\n" +
                    "zOfw8NBsmzoxpFQqCSPLsiaTiVDLZDKeA66urqRv5qdk7BSKIQLLefDZ6XQ8D1O1V09PT2bDVL9e\n" +
                    "RSJTwwNLUJpf3NGk+vUqKmq5B5HaCf1+3/f49IohuqI7HwfT1Gq1bDYb1CSNYogsJeVy2XcESVFW\n" +
                    "lpCGqRND9vb25EfLBYSnLkMgZA5q0iWG6NfmPJEhYP/9XJgzazSIIfGCGBI1iCHcGwILWMAiwAIW\n" +
                    "sIAFLGARYAELWMACFrAIsIAFrMjxtWjc0f9E7dsWi2bOonGelfm2xaKZ+/G5XK5arfrCwqKZs2jq\n" +
                    "9XqxWHx5efGFhUXzz6Lp9XqyK9MzCBYWzbdFM51OZbfZbMp2ECwsmrlR40m73TbXLCyauQSNLBuL\n" +
                    "xrFofGFh0cQLFk3UYNFwbwgsYAGLAAtYwAIWsIBFgAUsYAELWMAiwAIWsCIn3KLpdrtBz8FsLBq3\n" +
                    "RdNsNkMUGhuLxkFTr9dlW//6NsSi+WfRjEYj6V7I42gsGu8LjUJgYdE8euohsLBo7OiwsGhiwLKx\n" +
                    "aDwWjQcWFk28YNFEDRYN94bAAhawCLCABSxgAQtYBFjAAhawgAUsAixgASt+TGFG30KjyeVycoDZ\n" +
                    "6vciTfJgmcKMPiVrNBqz2WwymZif5NH8XqRJGCxfYWY8Hius6XQqfavVaiashYg0CYMVJMx4XoUx\n" +
                    "GAw8DRci0iRyzTJhyW65XJa+6ZSsVCqeJgsRaZYBlo4Cy7J09/7+3uzXQkSaZYAlk052ZcHS3Var\n" +
                    "FbRm/VKkWZ5peHFxYf/9cN/29rbZ6vcizZLAkqWqUCjo6i6kdPosXKRZ8iv4xYo0ywxr4SIN94bA\n" +
                    "AhawgAUsAixgAQtYwAIW+Q+huha5hOXpkAAAAABJRU5ErkJggg==";
//            byte[] base64 = DatatypeConverter.parseBase64Binary(URLDecoder.decode(base64Str, "UTF-8").split("base64,")[1]);
            byte[] base64 =  dBase64.decodeBuffer(URLDecoder.decode(base64Str, "UTF-8").split("base64,")[1]);
            logger.info(URLDecoder.decode(base64Str, "UTF-8").split("base64,")[1]);

//            return ocrService.image2word(FileUtil.mkFile(base64,"xxx.png"));
            return ocrService.image2word(ImageIO.read(new ByteArrayInputStream(base64)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
