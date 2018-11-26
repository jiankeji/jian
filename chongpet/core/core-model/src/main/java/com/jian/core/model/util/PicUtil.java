package com.jian.core.model.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicUtil {
	
       /**
         * 压缩照片
         * @return
         * @throws IOException 
         */
              public static void compressPhoto (String newFullPath) throws IOException{
        
                  //压缩处理
                 File oldfile = new File(newFullPath);
                 BufferedImage image = ImageIO.read(oldfile); 
                 int srcWidth = image.getWidth(null);//得到文件原始宽度
                 int srcHeight = image.getHeight(null);//得到文件原始高度
        
                 int newWidth =srcWidth/2;
                 if(newWidth<375) {
                	 newWidth=375;
                 }
                double scale_w = (double) newWidth / srcWidth;
                 int newHeight = (int) (srcHeight * scale_w);
        
                BufferedImage newImage = new BufferedImage(newWidth, newHeight,
                BufferedImage.TYPE_INT_RGB);
                newImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight,
                    Image.SCALE_SMOOTH), 0, 0, null);
       
             ImageIO.write(newImage, "jpg",new File(newFullPath));
        
            }

}

