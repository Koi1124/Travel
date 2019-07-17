package com.han.travel.service;

import com.han.travel.dao.Ab01Dao;
import com.han.travel.support.ImgUploadTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName NoteService
 * @Description 游记
 * @Author Saki
 * @Date 2019/7/16
 * @LastUpdate 2019/7/16
 **/
@Service
public class NoteService
{
    @Resource
    private Ab01Dao ab01Dao;

    /**
     * @Author Saki
     * @Description 上传图片
     * @Date 2019/7/16
     * @param list
     * @return java.util.List<java.lang.String>
     **/
    public List<String> uploadImg(List<String> list)
    {
        return ImgUploadTools.uploadImgs(list);
    }

    /**
     * @Author Saki
     * @Description 添加
     * @Date 2019/7/16
     * @param map
     * @return java.lang.Integer
     **/
    public Integer addNote(Map<String, Object> map)
    {
        map.put("nid", null);
        System.out.println(map);
        ab01Dao.addNote(map);
        return (Integer) map.get("nid");
    }

    /**
     * @Author Saki
     * @Description 更新游记
     * @Date 2019/7/16
     * @param map
     * @return
     **/
    public boolean updateNote(Map<String, Object> map)
    {
        return ab01Dao.updateNote(map);
    }
}
