package com.beiing.xiaoxiongkanfang.utils;

/**
 * Created
 * Author:Beiing
 * Email:1101587382@qq.com
 * Date:2015/10/12
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密、解密工具
 */
public final class EncryptTools {

    private EncryptTools(){}


    /**
     * 将字节数组转换成十六进制编码的字符串
     * @param data
     * @return
     */
    public static String toHex(byte[] data){
        String result = null;
        if(data != null && data.length > 0){
            StringBuilder sb = new StringBuilder();
            for (byte b : data) {
                int v = b & 0xFF;
                String hexStr = Integer.toHexString(v);
                if(v > 0xFF){
                    sb.append(hexStr);
                } else {
                    sb.append('0').append(hexStr);
                }
            }

            result = sb.toString();
        }
        return result;
    }

    /**
     * 将网址映射为文件名
     * @param stringContent
     * @return
     */
    public static String md5(String stringContent){
        String ret = null;
        if(stringContent != null){
            try {
                //获取MD5算法的消息摘要对象
                MessageDigest digest = MessageDigest.getInstance("MD5");

                //计算stringContent对应的MD5数据，生成的数据是字节数组
                //内部包含了不可显示的字节，需要进行编码，才可以转化成字符串
                //不要使用new String(byte[])!!!
                //需要转化成16进制内容
                byte[] result = digest.digest(stringContent.getBytes());

                //byte[] 每一个字节 转化为16进制表示，并且拼接成字符串
                //0x3C-> "3C"
                ret = toHex(result);
//                ret = new String(result); 如果直接用这种方式，生成的字符串是乱码
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}





