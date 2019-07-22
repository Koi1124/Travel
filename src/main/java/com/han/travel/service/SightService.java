package com.han.travel.service;

import com.han.travel.dao.Ab03Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SightService
 * @Description 景点信息
 * @Author Saki
 * @Date 2019/7/19
 * @LastUpdate 2019/7/19
 **/
@Service
public class SightService
{
    @Resource
    private Ab03Dao ab03Dao;


    /**
     * @Author Saki
     * @Description 景点详情页面信息
     * @Date 2019/7/19
     * @param sid 景点id
     * @param uid 用户id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getSightDetailById(Integer sid, Integer uid)
    {
        return ab03Dao.getSightDetailById(sid, uid);
    }

    /**
     * @Author Saki
     * @Description 获取所有景点编号
     * map {
     *     id:
     *     label:
     *     value:
     * }
     * @Date 2019/7/22
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getAllSightsIdAndName()
    {
        return ab03Dao.getAllSightsIdAndName();
    }

}
