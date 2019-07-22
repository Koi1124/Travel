package com.han.travel.service;


import com.han.travel.dao.Aa01Dao;
import com.han.travel.dao.Aa04Dao;
import com.han.travel.dao.Ab01Dao;
import com.han.travel.dao.Ab02Dao;
import com.han.travel.dao.Ab03Dao;
import com.han.travel.dao.Ab05Dao;
import com.han.travel.support.PageBean;
import com.han.travel.support.Utils;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;


@Service
public class AdminService
{

    @Resource
    private Aa04Dao aa04Dao;
    
    @Resource
    private Ab05Dao ab05Dao;
    
    @Resource
    private Ab01Dao ab01Dao;
    
    @Resource
    private Ab02Dao ab02Dao;
    
    @Resource
    private Ab03Dao ab03Dao;
    
    @Resource
    private Aa01Dao aa01Dao;
    /**
     * @Author 
     * @Description 
     * @Date 2019/7/15
     * @param map
     * @return boolean
     **/
    public boolean notEmpty(String str)
    {
    	if(str!=null&&!str.equals(""))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public Map<String,Object> getCounts()
    {	
		Map<String,Object> result=new HashMap<>(8);
		result.put("usercount",aa01Dao.selectCount());
		result.put("company_0",ab05Dao.selectCount());//_0未审核
		result.put("company",ab05Dao.selectCounts());
		result.put("note_0",ab01Dao.selectCount());//未审核
		result.put("note",ab01Dao.selectCounts());
		result.put("strategy",ab02Dao.selectCount());
		result.put("attraction",ab03Dao.selectCount());
		return result;			
    }
    
    //========================管理员功能的操作============================
    public boolean hasOne(String adminname)
    {
    	if(aa04Dao.hasOne(adminname)>0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public String updateInfoAa04(Map<String, Object> map,HttpServletRequest request)
    {	
		
		if(this.hasOne(map.get("newname").toString()))
		{
			return "名称已存在!";
		}
		else
		{
			if(aa04Dao.updateInfo(map))
			{
				request.getSession().setAttribute("adminusername", map.get("newname"));
				return "修改成功";
			}
			else
			{
				return "修改失败";
			}
		}
					
    }
    
    public String adminCheck(Map<String, Object> map)
    {
    	System.out.println("get");
        String username=map.get("username").toString();
        String password=map.get("password").toString();
        System.out.println("username:"+username+"password:"+password);
        Map<String,Object> result=aa04Dao.getPwdAndRoleByName(username);
        if(result!=null)  //数据库存在该数据时
        {
        	if(Utils.encode(password).substring(0,18).equals(result.get("pwd").toString()))
            {
            	return result.get("role").toString();
            }
            else
            {
            	//用户名和密码不对
            	return null;
            }
        }
        else
        {    //数据库没有该数据
        	return null;
        }
        
    }
    
    public boolean updatePwd(Map<String, Object> map)
    {
    	System.out.println("newword:"+map.get("newPassword").toString());
    	System.out.println("old:"+map.get("oldPassword").toString());
    	System.out.println("username"+map.get("username"));
    	map.put("newPassword",Utils.encode(map.get("newPassword").toString()).substring(0,18));
    	map.put("oldPassword",Utils.encode(map.get("oldPassword").toString()).substring(0,18));
    	return aa04Dao.updateAdminPwd(map);
    }
    
    public boolean changeAa04State(Map<String, Object> map)
    {	
    	return aa04Dao.changeState(map);
    }
    
    public boolean delAa04(Map<String, Object> map)
    {	
			return aa04Dao.del(map);		
    }
    
    public String getEmailAa04(Map<String, Object> map)
    {	
			return aa04Dao.getEmailByName(map.get("adminname").toString());		
    }
    
    public boolean changeRoleAa04(Map<String, Object> map)
    {	
			return aa04Dao.changeRole(map);		
    }
    
    public Map<String,Object> selectAa04(Map<String, Object> map)
    {
    	return PageBean.fuzzyQuery(aa04Dao, "aa04", map);
    }
    
    public boolean insertAdmin(Map<String, Object> map)
    {
    	map.put("password", Utils.encode(map.get("password").toString()).substring(0,18));
    	return aa04Dao.insert(map);
    }
    
    
    
    //===================结伴和游记的审核和查询=====================
    public Map<String,Object> selectAb05(Map<String, Object> map)
    {
       return PageBean.seleceByPage(Integer.parseInt(map.get("currPage").toString()), ab05Dao, "ab05");
    }
    
    public Map<String,Object> selectAb01(Map<String, Object> map)
    {
       return PageBean.seleceByPage(Integer.parseInt(map.get("currPage").toString()), ab01Dao, "ab01");
    }

    public boolean changeAb05State(Map<String, Object> map)
    {	
		return ab05Dao.changeStateById(Integer.parseInt(map.get("id").toString()),Integer.parseInt(map.get("state").toString()));
    }
    
    public boolean changeAb01State(Map<String, Object> map)
    {	
		return ab01Dao.changeStateById(map);
    }
    
    
    //=====================攻略=======================
  	public boolean insertStrategy(Map<String,Object>map)
    {
  		return ab02Dao.insertStrategy(map);
    }
  	
  	public boolean updateStrategy(Map<String,Object>map)
    {
		return ab02Dao.updateStrategy(map);
    }
  	
  	public boolean delStrategy(Map<String,Object>map)
    {
		return ab02Dao.delStrategy(Integer.parseInt(map.get("id").toString()));
    }
  	
  	public Map<String,Object> ab02fuzzyQuery(Map<String,Object>map)
    {
		return PageBean.fuzzyQuery(ab02Dao, "ab02",map);
    }
  	
  	public Map<String,Object> ab02queryById(Map<String,Object>map)
    {
		return ab02Dao.queryById(Integer.parseInt(map.get("id").toString()));
    }
  	
  	//======================景点========================
  	public boolean insertAttraction(Map<String,Object>map)
    {
		return ab03Dao.insertAttraction(map);
    }
  	
  	public boolean delAttraction(Map<String,Object>map)
    {
		return ab03Dao.delAttraction(Integer.parseInt(map.get("id").toString()));
    }
  	
  	public boolean updateAttraction(Map<String,Object>map)
    {
		return ab03Dao.updateAttraction(map);
    }
  	
  	public Map<String,Object> ad03fuzzyQuery(Map<String,Object>map)
    {
		return PageBean.fuzzyQuery(ab03Dao, "ab03",map);
    }
  	
  	public Map<String,Object> ab03queryById(Map<String,Object>map)
    {
		return ab03Dao.queryById(Integer.parseInt(map.get("id").toString()));
    }
}
