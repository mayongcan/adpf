package com.adpf.tracking.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class util {
	
	/**
     * 编码格式
     */
    private static final String ENCODING = "UTF-8";
   /**
     * 加密算法
    */
    public static final String KEY_ALGORITHM = "AES";
   /**
     * 签名算法
      */
    public static final String SIGN_ALGORITHMS = "SHA1PRNG";
	
	public static String AESEncode(String encodeRules,String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(encodeRules.getBytes(ENCODING));
            keygen.init(128, random);
            //keygen.init(128, new SecureRandom(encodeRules.getBytes()));
              //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
              //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
              //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode=content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byte_AES=cipher.doFinal(byte_encode);
          //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            //String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
            //String AES_encode=Base64.getMimeEncoder().encodeToString(byte_AES);
            String AES_encode = com.adpf.tracking.tools.Base64.encodeToString(byte_AES,com.adpf.tracking.tools.Base64.NO_WRAP);
          //11.将字符串返回
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        //如果有错就返加nulll
        return null;         
    }
    /*
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String AESDncode(String encodeRules,String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(encodeRules.getBytes(ENCODING));
            keygen.init(128, random);
            //keygen.init(128, new SecureRandom(encodeRules.getBytes()));
              //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
              //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
              //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            //byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
            //byte [] byte_content= Base64.getDecoder().decode(content);
            byte [] byte_content= com.adpf.tracking.tools.Base64.decode(content, com.adpf.tracking.tools.Base64.NO_WRAP);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        
        //如果有错就返加nulll
        return null;         
    }
    
    public static String usingMath(int length) {
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        
        String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;
        
        StringBuffer randomString = new StringBuffer();
        
        for (int i = 0; i < length; i++) {
          
          int randomIndex = (int)(Math.random() * allCharacters.length());
          
          randomString.append(allCharacters.charAt(randomIndex));
        } 
        return randomString.toString();
      }
    
     
     
     public static void main(String[] args) {
    	 util uu = new util();
    	 String encodeRules="c172XMDjfu2WVRQK";//秘钥 
    	 String jf = "123456";
    	 String aa = "{\"_deviceid\":\"008796763511690\",\"_androidid\":\"008796763511690\",\"_ip\":\"10.0.3.15\",\"_imei\":\"008796763511690\",\"_accountid\":\"2333\",\"_orderid\":\"\",\"_paytype\":\"\",\"_currencytyp\":\"CNY\",\"_fee\":0}";
    	 String content = "U2FsdGVkX191GL967JJWYbTGwrF2SglMDwYIlXyNj8aZCN+cdam6NhIqqZ5WOPrxeAi2UPQve7x0WoI9KdwBQ7v41bsEyxsRFblU+l/Jw0mrS133tBkUQkN/cGgikoK8FZsi05lEGxqHMhoxQVWfL5GiCq7NondRJfEELHorNhZ9ACqTAkwscW+XHTfvf9xq66NL7JHZnk2ODURDHDRs7PiRe6N5nnTrWTh9FPlL4Quw9VuMy1CNg1lCn8/ZJZfOO8urrmW4ZfB9vRXcuMintw==";
    	 //String bb = "853bpSVKzGU0WvylsJGt1cQiEMkXdNhmgacc/N53lJTb+gOAa2AURmb0p+oL38722PlFHCSg3bhm8glLtHfl3VqFkhRtjvgCJpaP5wvtrMg9iva28pCPoCw0aNnzxa7K/ylCeH1dnXc6Q4ZD2nYK3VSzGwOfyJ+sJZbtpw1S3JsunxyC9pj8y6D7/6GWfcDVAg372+Qtty0/NFpJsI5m+PQ0PLQyf6KuCtscJaUjdlEiD2er17oEq9Yq4SjbH18Y";
    	 String bb = "H/H47MFAc1HnyCD+JwgcJPyBiU100KpLkoXHytsWWRImlkQfau5M8Z6giMwJINOdqOfq7JG9xOGEi59H19I0QBBkSjjcunV/d15wDkw7tYpX8S9ZHk0BR2RR4bIGNkMDSzSqKqj3S2rfZ/sxYrvCk7USKl8kQQYj5zLpdYi+5FRkLHpWDD81w1mihopXCbYF3dNmVYApnMOaMVQs28iDNDt0+i5nT1h++7AutlaDM8tQZNt+duekb6SGnx/XWS1m";
    	 System.out.println(uu.AESEncode(encodeRules, jf));
    	 //System.out.println(uu.AESDncode(encodeRules, bb));
    	 
    	 
    	
     }

}
