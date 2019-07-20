package com.han.travel.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Af01Dao
{
    boolean insertChatBar(int lId);

    boolean deleteChatBar(int lId);

    Integer isExistLatest(@Param("dto") Map<String,Object> dto);

    /**
     *@discription: 查找是否有没读的最新消息
     *@param uid 
     *@date: 2019/7/20 16:30
     *@return: java.lang.Integer
     *@author: Han
     */
    Integer haveReadLatest(int uid);
}
