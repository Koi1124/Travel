package com.han.travel.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Ac05Dao
{
    boolean insertCompApp(@Param("dto")Map<String, Object> dto);

    boolean changeAppState(@Param("id") int id,@Param("state") int state);

    Integer getAppCountByCId(int cid);

    Map<String,Object> getAppCountAndPNameAndPIdByPId(int id);

    List<Map<String,Object>> getApplicantsIdAndNameAndPicByCId(int cid);

    Map<String, Object> getAppByUidAndTid(Map<String, Object> map);

    boolean updateAppByUidAndTid(Map<String, Object> map);

    /**
     *@discription: 根据结伴id得到所有申请的报名表信息 dto->
     * aid: 报名表id
     * name: 申请用户名
     * tel: 联系电话
     * count: 同行人数
     * nlist: 同行名单
     * addition: 备注
     * state: 状态
     *@param cid 
     *@date: 2019/7/19 11:32
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> getAppInfoByCId(int cid);
}
