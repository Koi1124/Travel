package com.han.travel.service;

import com.han.travel.dao.Ad02Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName ThumbsService
 * @Description 点赞
 * @Author Saki
 * @Date 2019/7/12
 * @LastUpdate 2019/7/12
 **/
@Service
public class ThumbsService
{

    @Resource
    private Ad02Dao ad02Dao;

    /**
     * @Author Saki
     * @Description 点赞
     * @Date 2019/7/12
     * @param map
     * @return boolean
     **/
    public boolean thumbsUp(Map<String, Object> map)
    {
        return ad02Dao.thumbsUp(map);
    }

    /**
     * @Author Saki
     * @Description 取消点赞
     * @Date 2019/7/12
     * @param map
     * @return boolean
     **/
    public boolean thumbsDown(Map<String, Object> map)
    {
        return ad02Dao.thumbsDown(map);
    }

}
