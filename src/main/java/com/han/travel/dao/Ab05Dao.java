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

    Map<String,Object> getAuthorIdAndTitleOfUnpassById(int id);

    String getIntroById(int id);

    List<Map<String,Object>> getAll(Map<String,Integer> map);

    Integer selectCount();

    
    /**
     *@discription: map->
     * id: 结伴id
     * intro: 结伴简介
     * date: 结伴日期
     * authorName: 作者名
     * authorPic: 作者头像
     * star: 收藏数
     * name: 结伴目的地合集 用'|'分隔目的地
     * mddPic: 目的地图片
     *@param pid
	 *@param order
	 *@param page
	 *@param offset 
     *@date: 2019/7/17 18:26
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> getSearchCompInfoByMDD(@Param("pid") int pid,@Param("order") String order,@Param("page") int page,@Param("offset") int offset);

    Integer getCompTotalCountByMDD(int pid);

    boolean updateViewByComp(@Param("cid") int cid,@Param("view") int view);

    /**
     *@discription: map->
     *  id: 结伴id
     *  name: 结伴目的地名合集 用'|'分隔目的地
     *  star: 收藏数
     *  authorName: 作者名
     *  authorPic: 作者头像
     *  mddPic: 目的地图片
     *@param cid 
     *@date: 2019/7/17 16:15
     *@return: java.util.Map<java.lang.String,java.lang.Object>
     *@author: Han
     */
    Map<String,Object> getOriginDataByCid(int cid);

    /**
     * @Author Saki
     * @Description 得到待审核的
     * @Date 2019/7/21
     * @param id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> getCompanyBaseDataByCid(@Param("id") int id);


    /**
     *@discription: map->
     * tid: 结伴id
     * state: 结伴状态
     * authorId: 用户id
     * authorName: 用户名
     * authorPic: 用户头像
     * mddName: 目的地名合集
     * setout: 出发地
     * leftTime: 剩余天数
     * isStar: 是否收藏
     * type: 为发布者还是参与者
     * title: 结伴标题
     *@param uid 
     *@date: 2019/7/20 8:51
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> getCompInfoByPublishUId(int uid);


    /**
     *@discription:
     *@param uid 
     *@date: 2019/7/20 8:57
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> getCompInfoByJoinUId(int uid);
    
    
    /**
     *@discription: 根据关键字模糊搜索结伴 map->
     * tid: 结伴id
     * tname: 结伴名
     * mdd: 结伴目的地
     * time: 出发时间
     * starCount: 收藏数
     * commentCount: 评论数
     *@param keyword 
     *@date: 2019/7/22 15:03
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    List<Map<String,Object>> getCompByKeyword(String keyword);

}
