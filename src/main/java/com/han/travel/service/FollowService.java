package com.han.travel.service;

import com.han.travel.dao.Ad03Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FollowService
{
    @Resource
    private Ad03Dao ad03Dao;

    public boolean addFollow(int userId,int followerId)
    {
        return ad03Dao.addFollow(userId,followerId);
    }

    public boolean removeFollow(int userId,int followerId)
    {
        return ad03Dao.removeFollow(userId,followerId);
    }

    public Integer isFollow(int userId,int followerId)
    {
        return ad03Dao.isFollow(userId,followerId);
    }


}
