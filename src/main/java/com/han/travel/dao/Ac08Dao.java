package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName Ac08Dao
 * @Description 结伴图片
 * @Author Saki
 * @Date 2019/7/20
 **/
@Repository
public interface Ac08Dao
{

    boolean addCompanyPic(@Param("cid") int cid, @Param("pic") String pic, @Param("index") int index);

    List<String> getCompanyPicsByCid(@Param("cid") int cid);

}
