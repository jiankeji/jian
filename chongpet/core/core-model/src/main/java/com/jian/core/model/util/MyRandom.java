package com.jian.core.model.util;

import java.util.Random;

public class MyRandom {
	
	private final static int codeLength =4;
    /**
     *  产生随机验证码
     * @return 验证码字符串
     */
	  public static String getCode(){
		  
		 Random rand = new  Random();
		 int  a ;
		 String  result ="";
		 for( int j =0; j<codeLength; j++ ){
			 a = Math.abs( rand.nextInt()%9 );
			 result += String.valueOf(a);
		 }
		 return  result;
	  }
	  
	  /**
	   * 获取一个默认头像
	   * @return
	   */
	  public static int getRandom() {
		  int x=1+(int)(Math.random()*20);
		  return x;
	  }
}



