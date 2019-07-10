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
}
