package com.han.travel.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ab05Dao extends CheckDao
{
	Map<String,Object> queryById(int id);

    boolean changeStateById(@Param("id") int id,@Param("state") int state);

    boolean deleteById(int id);

    boolean insertCompany(@Param("dto") Map<String,Object> dto);

    Integer getAuthorIdById(int id);

    String getIntroById(int id);

    List<Map<String,Object>> getAll(Map<String,Integer> map);

    Integer selectCount();

    List<Map<String,Object>> getSearchCompInfoByMDD(@Param("pid") int pid,@Param("order") String order,@Param("page") int page,@Param("offset") int offset);

    Integer getCompTotalCountByMDD(int pid);
}
