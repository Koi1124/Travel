package com.han.travel.service;

import com.han.travel.dao.Ab01Dao;
import com.han.travel.dao.Ab03Dao;
import com.han.travel.dao.Ab05Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SearchService
{
    @Resource
    private Ab03Dao ab03Dao;
    @Resource
    private Ab01Dao ab01Dao;
    @Resource
    private Ab05Dao ab05Dao;


    /**
     *@discription: 根据关键字模糊查询景点信息，详情见dao层方法
     *@param keyword 
     *@date: 2019/7/22 14:39
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    public List<Map<String,Object>> getSightsByKeyword(String keyword)
    {
        return ab03Dao.getSightsByKeyword(keyword);
    }


    public List<Map<String,Object>> getNotesByKeyword(String keyword)
    {
        return ab01Dao.getNotesByKeyword(keyword);
    }

    public List<Map<String,Object>> getCompaniesByKeyword(String keyword)
    {
        return ab05Dao.getCompByKeyword(keyword);
    }


}
