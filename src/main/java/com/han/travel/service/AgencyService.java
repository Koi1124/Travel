package com.han.travel.service;

import com.han.travel.support.ImgUploadTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AgencyService
 * @Description 旅行社
 * @Author Saki
 * @Date 2019/7/10
 * @LastUpdate 2019/7/10
 **/
public class AgencyService
{

    /**
     * @Author Saki
     * @Description
     * @Date 2019/7/10
     * @param aid 旅行社的id
     * @param map 传入的图片base64码列表
     * @return boolean
     **/
    public boolean setLicence(int aid, Map<String, Object> map)
    {
        List<String> images = (ArrayList) map.get("image");
        List<String> paths = ImgUploadTools.uploadImgs(images);

        if (images.size() == paths.size())
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (String path : paths)
            {
                stringBuilder.append(path).append(",");
            }
            //TODO 存入数据库
            return true;
        }
        return false;
    }

}
