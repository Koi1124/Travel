package com.han.travel.controller;

import com.han.travel.support.ImgUploadTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public boolean uploadImg(@RequestBody Map<String, Object> map)
    {
        List<String> images = (ArrayList) map.get("image");
        List<String> paths = ImgUploadTools.uploadImgs(images);

        if (images.size() == paths.size())
        {
            return true;
        }
        return false;
    }
}
