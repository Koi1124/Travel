package com.han.travel.controller;

import com.han.travel.support.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName AgencyController
 * @Description 旅行社用户控制器
 * @Author Saki
 * @Date 2019/7/9
 * @LastUpdate 2019/7/9
 **/
@Controller
public class AgencyController
{

    /**
     * @Author Saki
     * @Description 上传图片
     * @Date 2019/7/9
     * @param map
     * @return boolean
     **/
    @PostMapping("/agency/upload_img")
    @ResponseBody
    public boolean uploadImg(@RequestBody Map<String, Object> map) {
        List<String> images = (ArrayList) map.get("image");

        for (String image : images) {
            UUID id = UUID.randomUUID();
            for (String header : Utils.imgHeaders) {
                if (image.indexOf(header) != 0) {
                    continue;
                }
                // 去掉头部
                image = image.substring(header.length() + 1);
                //获取项目路径到E:/upload/images
                String filepath = "E:" + File.separator + "upload" + File.separator + "images" + File.separator ;
                File file = new File(filepath);
                if (!file.exists()) {//目录不存在就创建
                    file.mkdirs();
                }

                // 写入磁盘
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    System.out.println(image);
                    byte[] decodedBytes = decoder.decodeBuffer(image);
                    int index = header.indexOf("/");
                    String last = header.substring(index + 1, index + 4);
                    last = last.equals("jpe") ? "jpg" : last;
                    String fileName = filepath + id + "." + last;
                    FileOutputStream out = new FileOutputStream(fileName);
                    out.write(decodedBytes);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
