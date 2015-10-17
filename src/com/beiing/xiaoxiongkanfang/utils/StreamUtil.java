package com.beiing.xiaoxiongkanfang.utils;

/**
 * Created
 * Author:Beiing
 * Email:1101587382@qq.com
 * Date:2015/10/12
 */

import java.io.*;
import java.net.HttpURLConnection;

/**
 * IO���Ĺ�����
 */
public final class StreamUtil {
    private StreamUtil() {

    }


    /**
     * �ر���
     * @param stream
     */
    public static void close(Object stream){
        if(stream != null){
            try{
                if(stream instanceof InputStream){
                    ((InputStream) stream).close();
                } else if(stream instanceof OutputStream){
                    ((OutputStream) stream).close();
                } else if(stream instanceof Reader){
                    ((Reader) stream).close();
                } else if(stream instanceof Writer){
                    ((Writer) stream).close();
                } else if(stream instanceof HttpURLConnection){
                    ((HttpURLConnection) stream).disconnect();
                }
            }catch(Exception e){

            }
        }
    }


    /**
     * ��ȡ���Ĳ���
     * @param in
     * @return
     */
    public static byte[] readStream(InputStream in) throws IOException {
        byte[] ret = null;
        if (in != null) {
            ByteArrayOutputStream bout = null;
                bout = new ByteArrayOutputStream();
                byte[] buf = new byte[512];
                int len = -1;
                while ((len = in.read(buf)) != -1) {
                    bout.write(buf, 0, len);
                }
                //ע�⣺buf����Ҫ����=null�Ĳ���
                //�����ڴ���������
                buf = null;
                ret = bout.toByteArray();

            bout.close();
        }
        return ret;
    }

}
















