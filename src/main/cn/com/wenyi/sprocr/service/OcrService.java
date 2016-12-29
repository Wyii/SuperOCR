package cn.com.wenyi.sprocr.service;

import cn.com.wenyi.sprocr.util.TesseractUtil;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created by wyiss on 2016/12/28.
 */
@Service
public class OcrService {

    public String image2word(File imageFile) {
        return TesseractUtil.doOCR(imageFile);
    }

    public String image2word(BufferedImage bufferedImage) {
        return TesseractUtil.doOCR(bufferedImage);
    }
}
