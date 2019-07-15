package com.han.travel.service;

import com.han.travel.dao.Ad04Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CollectService
{
    @Resource
    private Ad04Dao ad04Dao;

    public boolean addCollect(Map<String,Object> dto)
    {
        return ad04Dao.insertCollect(dto);
    }

    public Integer isCollect(Map<String,Object> dto)
    {
        return ad04Dao.isCollect(dto);
    }
}
