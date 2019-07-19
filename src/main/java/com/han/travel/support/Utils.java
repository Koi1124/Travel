package com.han.travel.support;

import javax.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Utils
{
    //图像传输的对应格式的请求头
    public static final String[] imgHeaders = {
            "data:image/png;base64",
            "data:image/bmp;base64",
            "data:image/jpeg;base64"
    };

    //保存省市信息
    public static Map<String, Integer> place = null;

    /**
     *@discription:  将文本信息中的景点地点自动替换为跳转链接
     * 1.0 弱智方法 ->长文本效率极低
     *@param httpSession
	 *@param text
     *@date: 2019/7/1 11:37
     *@return: void
     *@author: Han
     */
    public static void placeReplace(HttpSession httpSession,String text)
    {
        // map中key为旅游景点，value为其id，项目运行时从景点表中读取放入session中
        Map<String,String> map= (Map<String, String>) httpSession.getAttribute("word");
        Map<String,String> modifiedMap=new HashMap<>(map.size());
        for (Map.Entry entry:map.entrySet())
        {
            StringBuilder url=new StringBuilder()
                    .append("<a th:href='")
                    .append("需要修改的uri"+"?景点id="+entry.getValue())
                    .append("'>")
                    .append(entry.getKey())
                    .append("</a>")
                    ;
            modifiedMap.put((String) entry.getKey(),url.toString());
        }
        for (Map.Entry entry:modifiedMap.entrySet())
        {
            String old=(String) entry.getKey();
            if (text.contains(old))
            {
                String uri=(String) entry.getValue();
                text.replace(old,uri);
            }
        }
    }

    /**
     * @Author Saki
     * @Description 判断字典是否不为空
     * @Date 2019/7/3
     * @param map
     * @return boolean
     **/
    public static boolean isNotEmpty(Map map)
    {
        return map != null && map.size() > 0;
    }
    
    /**
     * MD5加密
     * @param inStr
     * @return
     */
    public static String encode(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        
        for (int i = 0; i < charArray.length; i++)
        {
        	byteArray[i] = (byte) charArray[i];
        } 
        
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
 
    }    
    
    
}
