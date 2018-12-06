package com.jian.core.model.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.jian.core.model.bean.User;


public class ALILoginUtil {

    //支付宝网关
    public static final String url="https://openapi.alipay.com/gateway.do";

    //授权url
    public static final String alipay_url="";
    //应用id
    public static final String app_id="";
    //应用私钥
    public static final  String APP_PRIVATE_KEY="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC24uNR++Y3JCjGaSwuFozpp1cXJZyIsEyZm1QmCb6UI38O/OgfHRgV7dlxW3iSyzeQ3qQnv3IJIvXjqnkeoc2A8OpauHrPUu612BsL7XXQb5QhnjOMkzVZvlGvuMr/s1EmaXvMkrkbV+FWCQJCPCY0d18yCjqAuruUNepRgQRLAd+5tztJKmx8V0/04B/H/Gw6ZBGabyheRNfK3SKk6v2nuhGgjDW/XB3fgTKgQE/zU3Lju8MhfP6gE8l+57OPxjU6ckV0NBMMqYKS5tPtbVr2bNBB8D5vps8ey9RtzKEgqTrmc4xUQ1lK8HfFIzIgKaAsaGG7FcLPr5g6B/+bSlrdAgMBAAECggEBAJ04p9uPBfZZa4RGXeixEiq75S7M8qi/htp2ofyhW+jWKyCjYpl81XvkoY3F6P2pHfe4rr5M4qfgGirCHqWqy5QRiCa9eTZKQVSql3XFigbKJTpRWld7Jd4ksuyxnDH94cF35SmU6PUMs3bW/84ZD28DQB8wk+KepStgvjObCim/2dGBAXT5U+hW/UrUFiMHL97LDjkr0le7G2jZRA0sMfQoMpgiFWlc8zyMQHAsh9Yrc1y8oYeHejOtGESQ1pc54rUcXkbKA64p6gaJc1N0CM7GHsTRRQItKH/cF/BVVjjryd1UXyWBhcKHFht8Dyszkg4iQkcPfd75bLCQ1NnXNTECgYEA5ynFtIG+jsvHbG8WvbK9P/iBgzaQazBwDtniUovWbIJ0rnqqN7300zWH/pdJDQ7WbRtXOLBNJPTVj3OPwqxTyhb5GbRSwQJQbUR38QEKGFzUG14z0mSCcbKci3j3wVAl67cYh/u8zIbuYJQCDiSA+6EpCyT/y5dAGaR7nV675wcCgYEAyok9+g5revQbUp7O0CzZvb39wpa3J75Do++9oWmdP+sU+UsMXsFltSovTo0KtXnWrLj/jFTmxwZb/4xm2T31RbhVcN/QHYfuuFsDTEgodi/kuiWoa7yv9S6ojUs2l3K4CqP+rxhFy1yDiFu0WVTCl2ngQtLbLq4yaJnip99VsfsCgYAoJKvttd5Ww/Kk1QHY/ZI56rjrBW28/5NmMNZJyXj6LQmAR1MKDRXYG+K7BqCsANih5+6UrKEo+s+fcs0TXIPMg4LMwPDiDbnTz+EYXDrZkj1PZ2OOdN/mSMNAwUgdLQL41ToQW1olFoWNBgb5V8OCbzFGey1kuKZsDZFd+dadvwKBgQCIqGnFG42x/Jui97ycAmc4+8UJUyBqFJsXNmBBVT3hLwGL4/OIy/2Qa9JXshsTsA4FpR3o1jCRAd15ZIbQ9llUnbgo3RtTkbCle+ORyO5kMJSodVhauYoQaVcZXzKKpcFAjL7/7eTJ/rZRf28FB4f1Gl9TN2rk6Gzi31Jr6umrmQKBgQCyGdbGb64H4GJrnmrvv1UDWxFz/flQ2tgBWupdD+4VHPuXKeCo3VbaCalEdCt38c3VoTfBHLNjGDzRlJo4ss0IArriQdCm5xQldwZoFQ7aKNsodM70EF3nYrEuodNjM/ZiqXHXBCsi6kHvW/hZQXvOBvKA/5fM2jF5pa0sfVtLNQ==";
    public static final  String FORMAT="json";
    //编码集
    public static final  String CHARSET="UTF-8";
    //支付宝公钥
    public static final  String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtuLjUfvmNyQoxmksLhaM6adXFyWciLBMmZtUJgm+lCN/DvzoHx0YFe3ZcVt4kss3kN6kJ79yCSL146p5HqHNgPDqWrh6z1LutdgbC+110G+UIZ4zjJM1Wb5Rr7jK/7NRJml7zJK5G1fhVgkCQjwmNHdfMgo6gLq7lDXqUYEESwHfubc7SSpsfFdP9OAfx/xsOmQRmm8oXkTXyt0ipOr9p7oRoIw1v1wd34EyoEBP81Ny47vDIXz+oBPJfuezj8Y1OnJFdDQTDKmCkubT7W1a9mzQQfA+b6bPHsvUbcyhIKk65nOMVENZSvB3xSMyICmgLGhhuxXCz6+YOgf/m0pa3QIDAQAB";
    //商户生成签名字符串所使用的签名算法类型
    public static final  String SIGN_TYPE="RSA2";

    public  String alipayToken(String auth_code){


        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",app_id,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(auth_code);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            return oauthTokenResponse.getAccessToken();

        } catch (AlipayApiException e) {
            //处理异常
            return "-1";
        }

    }

    public static User getAlipay(String auth_code){

        User user = new User();
        ALILoginUtil aliLoginUtil = new ALILoginUtil();
        String accessToken = aliLoginUtil.alipayToken(auth_code);
        if(accessToken.equals("-1")){
            user.setUserId(-1);
            return user;
        }

        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",app_id,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();

        try {
            AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(request, accessToken);
            String payId = userinfoShareResponse.getUserId();
            String name = userinfoShareResponse.getNickName();
            String photo = userinfoShareResponse.getAvatar();
            user.setPayid(payId);
            user.setPhoto(photo);
            user.setName(name);
            return  user;
        } catch (AlipayApiException e) {
            user.setUserId(-1);
            return user;
        }

    }
}
