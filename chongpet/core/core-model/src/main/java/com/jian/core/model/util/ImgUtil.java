package com.jian.core.model.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.jian.core.model.bean.inter.ImgUrls.IMGSRC;
import static com.jian.core.model.bean.inter.ImgUrls.PATH;

@Component
public class ImgUtil {
    private static Log log = LogFactory.getLog(ImgUtil.class);
    /**
     * 通用的图片获取完整路径
     * @param imgName 图片名称
     * @param imgPath 图片路径
     * @return
     */
    public static String getImgPath(String imgName,String imgPath) {
        try {
            if (imgName.indexOf("http://")!=-1 || imgName.indexOf("https://")!=-1){
                return imgName;
            }
            String imgSrc = PropertiesUtil.getProperty(PATH);
            String headImgPath = PropertiesUtil.getProperty(imgPath);
            if (headImgPath.length()==0||imgSrc.length()==0) {
                log.error("getImgPath操作：从sysconfig.properties配置文件中读取"+imgName+"的值失败");
                return "";
            }
            imgName = (imgName==null || imgName.length()==0) ? "" : imgName;
//			imgName = imgName.toLowerCase();
            if (imgName.endsWith(".png") || imgName.endsWith(".jpg") || imgName.endsWith(".jpeg") || imgName.endsWith(".bmp")) {
                imgName = imgSrc + headImgPath + imgName;
                log.info("getImgPath:拼接图片地址："+imgName);
                return imgName;
            }else {
                return "";
            }
        } catch (Exception e) {
            log.error("getImgPath操作异常",e);
            e.printStackTrace();
            return "";
        }
    }

    public static List<String> getAllDinnerImgUrl(String imgStr,String imgPath) {
        List<String> rList = new ArrayList<String>();
        if (!StringUtil.trimStr(imgStr).equals("")) {
            String[] tableImgArray = imgStr.split(",");
            for(int i= 0;i<tableImgArray.length;i++) {
                rList.add(getImgPath(tableImgArray[i],imgPath));
            }
        }
        return rList;
    }


    //拼第一张
    public static String getFirstDinnerImgUrl(String imgStr,String imgPath) {
        String rStr = "";
        if (!StringUtil.trimStr(imgStr).equals("")) {
            String[] tableImgArray = imgStr.split(",");
            if(tableImgArray.length>0) {
                rStr = getImgPath(tableImgArray[0],imgPath);
            }
        }
        return rStr;
    }

    /**
     * 下载网络图片到本地
     * @param imgUrl
     * @param imgPath
     * @return
     * @throws Exception
     */
    public static String downImg(String imgUrl, String imgPath){
        String path = "";
        try {
            URL url = new URL(imgUrl);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);


            if (imgUrl==null || imgUrl.length()==0) return path;
            //根据时间拼图片存储路径
            Integer yearVal = DateTimeUtil.getDateVal(Calendar.YEAR);
            Integer monthVal = DateTimeUtil.getDateVal(Calendar.MONTH)+1;
            Integer dayVal = DateTimeUtil.getDateVal(Calendar.DAY_OF_MONTH);
            Integer hourVal = DateTimeUtil.getDateVal(Calendar.HOUR_OF_DAY);
            String datePath = yearVal+"/"+monthVal+"/"+dayVal+"/"+hourVal+"/";
           // String datePath = yearVal+"\\"+monthVal+"\\"+dayVal+"\\"+hourVal+"\\";
            //图片资源服务器物理地址+图片类型地址+日期
            String storePath = PropertiesUtil.getProperty(IMGSRC) + PropertiesUtil.getProperty(imgPath) + datePath;
           // String storePath = "D:\\"+datePath;
                String fileName = StringUtil.getMD5(DateTimeUtil.getCurrentTimeStamp())+".png";
                fileName = fileName.toLowerCase();
                File mfile = new File(storePath);
                if (!mfile.exists()) {
                    mfile.mkdirs();
                }
            File imageFile = new File(storePath + fileName);
            imageFile.createNewFile();
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(imageFile);
            imageStream.write(data);
            imageStream.flush();
            imageStream.close();
            path = datePath+fileName;
            return path;
        } catch (Exception e) {
            log.error("",e);
        }
        return path;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

//    public static void main(String[] args) throws Exception {
//        ImgUtil imgUtil = new ImgUtil();
//        imgUtil.downImg("https://thirdwx.qlogo.cn/mmopen/vi_32/5Ka4Qiax09liaSVStjy1XcRu7Fj05eT9WCRV9wGicPo5K5ds45NmQZt1ibjbNC85M5icgoQtj6BYczZjJvmDZ8WOaGA/132","");
//    }


    /**
     * @Description 根据base64字符串批量生成图片
     * @param base64ImgStr base64字符串集合
     * @param imgPath 图片类型,如：头像图片、相册图片,保单等(从properties中读取)
     */
    public static List<String> batchGenerateImg(List<String> base64ImgStr, String imgPath) {
        List<String> pathList = new ArrayList<>();
        if (base64ImgStr==null || base64ImgStr.size()==0) return pathList;
        //根据时间拼图片存储路径
        Integer yearVal = DateTimeUtil.getDateVal(Calendar.YEAR);
        Integer monthVal = DateTimeUtil.getDateVal(Calendar.MONTH)+1;
        Integer dayVal = DateTimeUtil.getDateVal(Calendar.DAY_OF_MONTH);
        Integer hourVal = DateTimeUtil.getDateVal(Calendar.HOUR_OF_DAY);
        try {
            String datePath = yearVal+"/"+monthVal+"/"+dayVal+"/"+hourVal+"/";
            //图片资源服务器物理地址+图片类型地址+日期
            String storePath = PropertiesUtil.getProperty(IMGSRC) + PropertiesUtil.getProperty(imgPath) + datePath;
            //String storePath = config.getImages().getImg_src() + imgPath + datePath;
            log.info("图片路径："+storePath);
            log.info("要生成图片数量："+base64ImgStr.size());
            for(String tmpStr : base64ImgStr) {
                String fileName = StringUtil.getMD5(DateTimeUtil.getCurrentTimeStamp())+".png";
                fileName = fileName.toLowerCase();
                File mfile = new File(storePath);
                if (!mfile.exists()) {
                    mfile.mkdirs();
                }
                if (generateImage(tmpStr, storePath + fileName)) {
                    String tmpPathStr = datePath + fileName;
                    pathList.add(tmpPathStr);
                    log.info("生成成功:"+fileName);
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return pathList;
    }

    /**
     *
     * @Description 图片base64字符串转图片并存储
     * @param imgStr base64编码字符串
     * @param path 图片路径-具体到文件
     * @return
     */
    @SuppressWarnings("restriction")
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null) {
            return false;
        }
        imgStr = imgStr.replaceAll("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = null;
        try {
            b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            // 生成文件
            System.out.println("-------------------"+path);
            File imageFile = new File(path);
            imageFile.createNewFile();
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(imageFile);
            imageStream.write(b);
            imageStream.flush();
            imageStream.close();
            return true;
        } catch (Exception e) {
            log.error("generateImage操作异常",e);
            return false;
        }
    }
}
