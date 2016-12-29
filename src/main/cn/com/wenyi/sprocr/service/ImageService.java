package cn.com.wenyi.sprocr.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wyiss on 2016/12/29.
 */
public class ImageService {

    public void addContrasc(InputStream inputStream) {
        try {
            BufferedImage imageBuffered = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
