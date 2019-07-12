package com.han.travel.support;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName ImgUploadTools
 * @Description 图像上传类
 * @Author Saki
 * @Date 2019/7/10
 * @LastUpdate 2019/7/10
 **/
public class ImgUploadTools
{

    /**
     * @Author Saki
     * @Description 上传多张图片 返回图片地址list
     * @Date 2019/7/10
     * @param images
     * @return java.util.List<java.lang.String>
     **/
    public static final List<String> uploadImgs(List<String> images)
    {
        List<String> paths = new ArrayList<>();
        for (String image : images)
        {
            paths.add(uploadImg(image));
        }
        return paths;
    }

    /**
     * @Author Saki
     * @Description 上传单张图片 返回图片地址
     * @Date 2019/7/10
     * @param image
     * @return java.lang.String
     **/
    public static final String uploadImg(String image)
    {
        UUID id = UUID.randomUUID();
        for (String header : Utils.imgHeaders)
        {
            if (image.indexOf(header) != 0)
            {
                continue;
            }
            // 去掉头部
            image = image.substring(header.length() + 1);
            //获取项目路径到E:/upload/images
            String filepath = "E:" + File.separator + "upload" + File.separator + "images" + File.separator ;
            File file = new File(filepath);
            if (!file.exists())
            {//目录不存在就创建
                file.mkdirs();
            }

            // 写入磁盘
            BASE64Decoder decoder = new BASE64Decoder();
            try
            {
                byte[] decodedBytes = decoder.decodeBuffer(image);
                int index = header.indexOf("/");
                String last = header.substring(index + 1, index + 4);
                last = last.equals("jpe") ? "jpg" : last;
                String fileName = filepath + id + "." + last;
                FileOutputStream out = new FileOutputStream(fileName);
                out.write(decodedBytes);
                out.close();
                return "/image/" + id + "." + last;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
