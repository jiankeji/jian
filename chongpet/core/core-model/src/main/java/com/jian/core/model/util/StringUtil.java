package com.jian.core.model.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil<T> {
    private static Log log = LogFactory.getLog(StringUtil.class);

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isEmpty(String str) {
        str = (str == null) ?  "" : str;
        return str.trim().isEmpty();
    }

    /**
     * 验证日期格式是否正确
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy-MM-dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 验证手机号码
     * @param str
     * @return
     */
    public static boolean isPhoneNumber(String str){
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * @Description 生成count位随机数字,作为模拟验证码
     * @param count 要生成的随机数个数
     * @return
     */
    public static String generateCodeValue(int count) {
        if (count<=0) {		//随机数个数不能小于0
            log.error("generateCodeValue随机数个数不能小于0");
            return "";
        }
        Random random = new Random();
        StringBuffer sBuffer = new StringBuffer();
        for(int i=0; i<count; i++) {
            sBuffer.append(random.nextInt(9));
        }
        log.info(count+"位随机验证码："+sBuffer.toString());
        return sBuffer.toString();
    }

    /**
     * @Description (TODO 生成6为随机数字,作为模拟验证码)
     * @param count 要生成的随机数个数
     * @return
     */
    public static String generateSixValue(int count) {
        if (count<=0) {		//随机数个数不能小于0
            log.error("generateSixValue随机数个数不能小于0");
            return "";
        }
        Random random = new Random();
        StringBuffer sBuffer = new StringBuffer();
        for(int i=0; i<count; i++) {
            sBuffer.append(random.nextInt(9));
        }
        if (sBuffer.length()<count) {
            return "888666";
        }
        log.info("6位随机验证码："+sBuffer.toString());
        return sBuffer.toString();
    }


    /**
     * 验证list是否需要翻页
     * @param list
     * @return
     */
    public boolean flagIfNextPage(List<T> list, int pageOfCount){
        if (list != null && list.size()>0){
            if (list.size() == pageOfCount) {
                list.remove(list.size()-1);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }



    /**
     * 判断Integer 是否符合规则
     * @param num
     * @return
     */
    public static boolean flagInteger(Integer num){
        if (num instanceof  Integer){
            return true;
        }
        return  false;
    }



    /** 生成随机数池*/
    public static char[] numAndCharacterPool = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    /**
     * @Description 验证字符串是否是null,不为null返回原字符串长度，为null或空字符串返回一个长度为0
     * @param str
     * @return int 返回0表示验证字符串可能是null或长度为0
     */
    public static int validateStr(String str) {
        str = (str == null) ?  "" : str;
        str = str.trim();
        return str.length();
    }

    /**
     s	 * @Description 在parentStr字符串中找childStr,找到返回true,否则false
     * @param parentStr
     * @param childStr
     * @return
     */
    public static boolean ifExist(String parentStr, String childStr) {
        try {
            parentStr = (parentStr==null || parentStr.length()==0) ? "" : parentStr;
            childStr = (childStr==null || childStr.length()==0) ? "" : childStr;
            if (parentStr.length() == 0) {
                return false;
            }
            if (childStr.length() == 0) {
                return false;
            }
            String[] parentArray = parentStr.split(",");
            if (parentArray == null || parentArray.length == 0) {
                return false;
            }
            for(int i=0; i<parentArray.length; i++) {
                if (childStr.equals(parentArray[i])) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("ifExist操作异常",e);
            return false;
        }
    }

    /**
     * @Description 将用逗号(,)分隔的字符串按逗号切片分隔然后装入list<String>中
     * @param prefixStr 前缀，切分后的字符串可能会需要拼接一些字符串
     * @param params
     * @return
     */
    public static List<String> sliceString(String prefixStr,String params){
        List<String> result = new ArrayList<>();
        try {
            params = (params==null || params.length()==0) ? "" : params;
            if (params.length()==0) {
                return new ArrayList<>();
            }
            if (!params.contains(",")) {	//说明只有一个字符串，没有逗号,所以把这一个字符串加到list中
                result.add(prefixStr+params);
                return result;
            }
            String[] sliceArray = params.split(",");
            if (sliceArray!=null && sliceArray.length > 0) {
                for(int i=0; i<sliceArray.length; i++) {
                    String slice = sliceArray[i];
                    if (slice !=null && slice.length()>0) {
                        result.add(prefixStr + sliceArray[i]);
                    }
                }
            }
        } catch (Exception e) {
            log.error("sliceString操作异常",e);
            return new ArrayList<>();
        }
        return result;
    }

    /**
     * @Description 在parentStr字符串中找到replacedStr并删除掉
     * @param parentStr
     * @param replacedStr
     * @return 删除掉replacedStr后的新字符串
     */
    public static String doReplaceString(String parentStr, String replacedStr) {
        parentStr = (parentStr==null || parentStr.length()==0) ? "" : parentStr;
        if (parentStr.length()==0) {
            log.error("doReplaceString操作时parentStr是空值");
            return "";
        }
        replacedStr = (replacedStr==null || replacedStr.length()==0) ? "" : replacedStr;
        if (replacedStr.length()==0) {
            log.error("doReplaceString操作时replacedStr是空值");
            return "";
        }
        String[] childrenArray = parentStr.split(",");
        String newParentStr = "";
        for(int i=0; i<childrenArray.length; i++) {
            if (!replacedStr.equals(childrenArray[i])) {
                if (newParentStr.length()==0) {		//第一个元素
                    newParentStr = newParentStr + childrenArray[i];
                }else {
                    newParentStr = newParentStr +","+ childrenArray[i];
                }
            }
        }
        log.info("截取后得而字符串："+newParentStr);
        return newParentStr;
    }

    /**
     * @Description 替换手机号中间四位数字为*
     * @param phoneNumStr
     * @return
     */
    public static String doHidePhoneNum(String phoneNumStr) {
        phoneNumStr = (phoneNumStr==null || phoneNumStr.length()!=11) ? "" : phoneNumStr;
        if (phoneNumStr.length()==0) {
            log.info("doHidePhoneNum操作：手机号格式不正确");
            return "";
        }
        StringBuffer phoneNumBuffer = new StringBuffer();
        char[] phoneCharArray = phoneNumStr.toCharArray();
        for(int i=0; i<phoneCharArray.length; i++) {
            System.out.println("手机号数组："+phoneCharArray[i]);
            if (i>2 && i<7) {
                phoneNumBuffer.append("*");
            }else {
                phoneNumBuffer.append(phoneCharArray[i]);
            }
        }
        return phoneNumBuffer.toString();
    }

    /**
     * @Description 生成指定个数的字母加数字的随机字符串,可用作生成昵称等
     * @param count 要生成的字符串长度
     * @return
     */
    public static String doGenerateRandomNickName(int count) {
        if (count <= 0) {
            return "";
        }
        Random mRandom = new Random();
        StringBuffer sBuffer = new StringBuffer();
        for(int i=0; i<count; i++) {
            int index = mRandom.nextInt(36);
            sBuffer.append(numAndCharacterPool[index]);
        }
        return sBuffer.toString();
    }

    /**
     * 比较两个字符串的相识度
     * @param str
     * @param target
     * @return
     */
    public static int compare(String str, String target) {
        int d[][];              // 矩阵
        int n = str.length();
        int m = target.length();
        int i;                  // 遍历str的
        int j;                  // 遍历target的
        char ch1;               // str的
        char ch2;               // target的
        int temp;               // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        // 初始化第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        // 初始化第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) {
            // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    /**
     * 获取最小的值
     */
    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 获取两字符串的相似度
     */
    public static float getSimilarityRatio(String str, String target) {
        int max = Math.max(str.length(), target.length());
        return 1 - (float) compare(str, target) / max;
    }

    public static void main(String args[]) {
        //18501736692
        System.out.println(isPhoneNumber("18501736692"));
//		List<String> sliceList = sliceString("jce666,afesfa，各个");
//		if (sliceList!=null && sliceList.size()>0) {
//			for(String tmp : sliceList) {
//				ULog.info("切片："+tmp);
//			}
//		}else {
//			ULog.info("没值");
//		}

//		System.out.println("隐藏了中间数字的手机号："+doHidePhoneNum("15152363873"));
//		System.out.println("随机昵称："+doGenerateRandomNickName(8));
//		String lastFourStr = "15152363873".substring(7, 11);
//		System.out.println(lastFourStr);
        List<String> mList = new ArrayList<>();
        mList.add("1");
        mList.add("12");
        mList.add("123");
        mList.add("1234");
        mList.add("12345");
        mList.remove(mList.size()-1);
        mList.size();

        encryptBySolt("JCE666");
    }

    //字符串非空判断转换函数
    public static String trimStr(String s) {
        String rStr = "";
        rStr = (s==null||s.trim().equals("")?"":s);
        return rStr;
    }

    //Integer非空判断转换函数
    public static Integer trimInt(Integer i) {
        Integer rInt = 0;
        rInt = (i==null?0:i);
        return rInt;
    }

    /** 加密佐料1*/
    public static String soltVal1 = "zV6eU3wM8saeJoLZXLb1q8h1G6clm8k8jL0GVmob4XdwuY57YcmoYI4WI9t0F6Qi";
    /** 加密佐料2*/
    public static String soltVal2 = "Vg8FubNt58i7TyJNVp5eKnekftpBHzDGg4Lkhm6QgTsnpNXHODi8eC4kE1hMQTsT";

    public static String soltVal3 = "zV6eU3wM8saeJoLZXLb1q8h1G6clm8k8jL0GVmob4XdwuY57YcmoYI4WI9t0F6QiVg8FubNt58i7TyJNVp5eKnekftpBHzDGg4Lkhm6QgTsnpNXHODi8eC4kE1hMQTsT";

    /**
     * @Description 佐料md5加密,加密规则：</br>
     * <p>
     * 	step 1: (soltVal1(佐料1) + encryptStr(要加密的字符串))字典降序排序,将排序结果MD5一次<br>
     *	step 2: (soltVal2(佐料2) + md5First(第一次md5结果))字典降序排序,将排序结果md5一次<br>
     *  其中md5Second就是最后的加密结果
     * </p>
     * )
     * @param encryptStr
     * @return
     */
    public static String encryptBySolt(String encryptStr){
        try {
            if (encryptStr == null || encryptStr.length() == 0) {
                log.error("随机数加密encryptBySolt参数错误");
                return null;
            }
            //step 1: (soltVal1(佐料1) + encryptStr(要加密的字符串))字典降序排序,将排序结果MD5一次
            String sortFirst = dicSortDESC(soltVal1 + encryptStr);
            if (sortFirst == null) {
                log.error("MD5第一次加密失败,字典排序错误");
                return null;
            }
            String md5First = getMD5(sortFirst);
            log.info("第一次MD5结果："+md5First);
            //step 2: (soltVal2(佐料2) + md5First(第一次md5结果))字典降序排序,将排序结果md5一次
            String sortSecond = dicSortDESC(soltVal2 + md5First);
            if (sortSecond == null) {
                log.error("MD5第二次加密失败,字典排序错误");
                return null;
            }
            String md5Second = getMD5(sortSecond);
            if (md5Second == null) {
                log.error("MD5加密失败,字典排序错误");
                return null;
            }
            log.info("加密结果："+md5Second);
            return md5Second;
        } catch (Exception e) {
            log.error("Md5Util.encryptByRandomSolt异常："+e.getMessage());
            return null;
        }
    }

    public static final String KEY_MD5_CODE = "inacg888***lee";

    /**
     * @Description MD5加密
     * @param md5Str
     * @return
     */
    public static String getMD5(String md5Str) {
        // 用于加密的字符
        char md5String[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try {
            // 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = md5Str.getBytes();

            // 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            // MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { // i = 0
                byte byte0 = md[i]; // 95
                str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
                str[k++] = md5String[byte0 & 0xf]; // F
            }
            // 返回经过加密后的字符串
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Description 字典排序之降序
     * @param sortString 要排序的字符串
     * @return
     */
    public static String dicSortDESC(String sortString){
        log.info("字典降序排序前字符串是："+sortString);
        try {
            char[] sortArray = sortString.toCharArray();
            if (sortArray !=null && sortArray.length > 0) {
                Arrays.sort(sortArray);
                String result = new String(sortArray);
                log.info("字典降序排序结果："+result);
                return result;
            }
        } catch (Exception e) {
            log.error("要排序的字符串是："+sortString);
            log.error("字典降序排序异常："+e.getMessage());
            return null;
        }
        return null;
    }

    public static Integer praseInt(String obj){
        Integer rInt = 0;
        try{
            rInt = new Integer(obj);
            return rInt;
        }catch(Exception e){
        }
        return 0;
    }
    //ImageCode-----------------------------------------------------------------
    /** map的key, 用作存取生成的随机验证码 */
    public static final String KEY_VALIDATE_CODE = "validate_code";
    /** map的key, 用作存取生成的图片base64字符串*/
    public static final String KEY_BASE64_STR = "base64_str";

    /**
     * @Description (TODO 生成随机验证码图片,使用默认宽高和位数,默认高度40，宽度180,6位验证码)
     * @return
     */
    public static Map<String, String> getImgCodeWithDefault(ByteArrayOutputStream outputStream) {
        return generateImageCode(outputStream,180,40,6);
    }

    /**
     * @Description (TODO 生成随机验证码图片)
     * @param outputStream
     * @param width 生成图片的宽度
     * @param height 生成图片的高度
     * @param count 生成图片的随机数个数
     * @return
     */
    public static Map<String, String> getImgCodeWithDefault(ByteArrayOutputStream  outputStream,int width,int height, int count) {
        return generateImageCode(outputStream,width,height,count);
    }

    /**
     * @Description (TODO 生成图形验证码)
     * @param outputStream
     * @param width 要生成的图片宽度
     * @param height 要生成的图片高度
     * @param cout 要生成的图片中随机数个数
     * @return
     */
    @SuppressWarnings("restriction")
    private static Map<String, String> generateImageCode(ByteArrayOutputStream  outputStream,int width, int height, int cout) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            StringBuffer sb = new StringBuffer();
            Random random = new Random();

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphic = image.getGraphics();

            graphic.setColor(Color.getColor("F8F8F8"));
            graphic.fillRect(0, 0, width, height);

            Color[] colors = new Color[] { Color.BLUE, Color.GRAY, Color.GREEN, Color.RED, Color.BLACK, Color.ORANGE,
                    Color.CYAN };
            // 在 "画板"上生成干扰线条 ( 5 是线条个数)
            for (int i = 0; i < 5; i++) {
                graphic.setColor(colors[random.nextInt(colors.length)]);
                final int x = random.nextInt(width);
                final int y = random.nextInt(height);
                final int w = random.nextInt(20);
                final int h = random.nextInt(20);
                final int signA = random.nextBoolean() ? 1 : -1;
                final int signB = random.nextBoolean() ? 1 : -1;
                graphic.drawLine(x, y, x + w * signA, y + h * signB);
            }

            // 在 "画板"上绘制字母
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
            for (int i = 0; i < cout; i++) {
                final int temp = random.nextInt(26) + 97;
                String s = String.valueOf((char) temp);
                sb.append(s);
                graphic.setColor(colors[random.nextInt(colors.length)]);
                graphic.drawString(s, i * (width / cout), height - (height / 3));
            }
			//log.info("generateImgCode存储生成的验证码："+sb.toString());
            resultMap.put(KEY_VALIDATE_CODE, sb.toString());
            graphic.dispose();
            ImageIO.write(image, "jpeg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64ImageStr = encoder.encode(outputStream.toByteArray());
			//log.info(base64ImageStr);
            resultMap.put(KEY_BASE64_STR, base64ImageStr);
            return resultMap;
        } catch (Exception e) {
            log.error("generateImgCode操作异常",e);
        }
        return null;
    }
}
