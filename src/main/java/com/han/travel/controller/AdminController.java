package com.han.travel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.han.travel.dao.Aa02Dao;
import com.han.travel.dao.Ab01Dao;
import com.han.travel.dao.Ab04Dao;
import com.han.travel.dao.Ab05Dao;
import com.han.travel.service.AdminService;
import com.han.travel.support.PageBean;

@Controller
public class AdminController 
{
	@Autowired
    private Ab05Dao ab05Dao;
	
	@Autowired
    private AdminService adminService;
	
	@Autowired
	private Aa02Dao aa02Dao;
	
	@Autowired
	private Ab04Dao ab04Dao;
	
	@Autowired
    private Ab01Dao ab01Dao;
	
	
	@RequestMapping("/admin")
    public String toAdminLogin(Map<String,Object> dto)
    {
    	return "admin/adminLogin";
    }
	
	@RequestMapping("/editPassword")
    public String toEditPassword(Map<String,Object> dto)
    {
    	return "admin/editPassword";
    }
	
	@RequestMapping("/admin/login")
    public String toIndex(HttpServletRequest request)
    {	
		String username=(String) request.getSession().getAttribute("username");
		
		if(username!=null &&username.equals("admin"))
		{
			return "admin/index";
		}
		else
		{
			return "admin/error";
		}
    	
    }

	
	@RequestMapping("/company_list")
    public String toCompany_List(Map<String,Object> dto)
    {
    	return "admin/company_list";
    }
	
	@RequestMapping("/agency_list")
    public String toAgency_List(Map<String,Object> dto)
    {
    	return "admin/agency_list";
    }
	@RequestMapping("/log")
    public String toLog(Map<String,Object> dto)
    {
    	return "admin/log";
    }
	@RequestMapping("/member_del")
    public String toMember_Del(Map<String,Object> dto)
    {
    	return "admin/member_del";
    }
	@RequestMapping("/member_list")
    public String toMemberList(Map<String,Object> dto)
    {
    	return "admin/member_list";
    }
	@RequestMapping("/statistic")
    public String toStatistic(Map<String,Object> dto)
    {
    	return "admin/statistic";
    }
	
	@RequestMapping("/travel_item_list")
    public String toTravel_Item(Map<String,Object> dto)
    {
    	return "admin/travel_item_list";
    }
	@RequestMapping("/travel_note")
    public String toTravel_Note(Map<String,Object> dto)
    {
    	return "admin/travel_note";
    }
	@RequestMapping("/welcome")
    public String toWelcome(Map<String,Object> dto)
    {
    	return "admin/welcome";
    }
	
	
	
	/**
     * @Author ayds
     * @Description 获取分页的结伴数据
     * @Date 2019/7/8
     * @param map
     * @return boolean
     **/
	@PostMapping("/admin/ab05/selectByPage")
    @ResponseBody					
    public Map<String,Object> selectAb05ByPage(@RequestBody Map<String, Object> map)
    {
		return PageBean.seleceByPage(Integer.parseInt(map.get("currPage").toString()), ab05Dao, "ab05");
    }
	
	@PostMapping("/admin/aa02/selectByPage")
    @ResponseBody
    public Map<String,Object> selectAa02ByPage(@RequestBody Map<String, Object> map)
    {
		return PageBean.seleceByPage(Integer.parseInt(map.get("currPage").toString()), aa02Dao, "aa02");
    }
	
	
	@PostMapping("/admin/ab04/selectByPage")
    @ResponseBody
    public Map<String,Object> selectAb04ByPage(@RequestBody Map<String, Object> map)
    {
		return PageBean.seleceByPage(Integer.parseInt(map.get("currPage").toString()), ab04Dao, "ab04");
    }
	
	@PostMapping("/admin/ab01/selectByPage")
    @ResponseBody					
    public Map<String,Object> selectAb01ByPage(@RequestBody Map<String, Object> map)
    {
		return PageBean.seleceByPage(Integer.parseInt(map.get("currPage").toString()), ab01Dao, "ab01");
    }
	
	@PostMapping("/admin/check")
    @ResponseBody					
    public boolean loginCheck(@RequestBody Map<String, Object> map,HttpServletRequest request)
    {
		 if(adminService.adminCheck(map))
		 {
			 request.getSession().setAttribute("username", map.get("username"));
			 request.getSession().setAttribute("password", map.get("password"));
			 return true;
		 }else
		 {
			 return false;
		 }
    }
	
	@PostMapping("/admin/editPassword")
    @ResponseBody					
    public boolean editPassword(@RequestBody Map<String, Object> map)
    {
		 if(adminService.updatePwd(map))
		 {
			 return true;
		 }else
		 {
			 return false;
		 }
    }
	
    /**
     * @Author ayds
     * @Description 审核改变数据状态
     * @Date 2019/7/9
     * @param int,String
     * @return
     */
	@PostMapping("/admin/ab05/changeState")
    @ResponseBody
    public boolean changeAb05State(@RequestBody Map<String, Object> map)
    {	
		return ab05Dao.changeStateById(Integer.parseInt(map.get("id").toString()),Integer.parseInt(map.get("state").toString()));
    }
	
	@PostMapping("/admin/aa02/changeState")
    @ResponseBody
    public boolean changeAa02State(@RequestBody Map<String, Object> map)
    {	
		return aa02Dao.changeStateById(Integer.parseInt(map.get("id").toString()),Integer.parseInt(map.get("state").toString()));
    }
	
	@PostMapping("/admin/ab04/changeState")
    @ResponseBody
    public boolean changeAb04State(@RequestBody Map<String, Object> map)
    {	
		return ab04Dao.changeStateById(Integer.parseInt(map.get("id").toString()),Integer.parseInt(map.get("state").toString()));
    }
	
	@PostMapping("/admin/ab01/changeState")
    @ResponseBody
    public boolean changeAb01State(@RequestBody Map<String, Object> map)
    {	
		return ab01Dao.changeStateById(Integer.parseInt(map.get("id").toString()),Integer.parseInt(map.get("state").toString()));
    }
	
	
	
}
