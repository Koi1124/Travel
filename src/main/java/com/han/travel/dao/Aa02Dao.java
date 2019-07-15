package com.han.travel.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Aa02Dao extends CheckDao
{
	/**==============================================================================
	 *                              管理员操作部分
	 * ==============================================================================
	 */

	List<Map<String,Object>>getAllByState(Map<String,Integer>map);

	Integer selectCount();

	boolean changeStateById(@Param("id") int id,@Param("state") int state);

	/**==============================================================================
	 *                              普通增改操作
	 * ==============================================================================
	 */

	Map<String, Object> getAgencyByMail(String mail);

	Map<String, Object> getAgencyById(String id);

	boolean addAgency(Map<String, Object> map);

	boolean updateAgency(Map<String, Object> map);

	boolean updateLogo(Map<String, Object> map);

	/**==============================================================================
	 *                              以下为用户表操作
	 * ==============================================================================
	 */

	boolean addAgencyUser(@Param("map") Map<String, Object> map);

	boolean upadteAgencyUser(Map<String, Object> map);

	boolean updateAgencyUserLogo(Map<String, Object> map);
}
