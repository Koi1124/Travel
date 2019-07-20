package com.han.travel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.han.travel.dao.Aa02Dao;
import com.han.travel.dao.Aa04Dao;
import com.han.travel.dao.Ab01Dao;
import com.han.travel.dao.Ab04Dao;
import com.han.travel.dao.Ab05Dao;
import com.han.travel.service.AdminService;
import com.han.travel.support.PageBean;

@Controller
public class AdminController 
{   
	@Autowired
    private AdminService adminService;

	@RequestMapping("/admin")
    public String toAdminLogin(Map<String,Object> dto,HttpServletRequest request)
    {
		if(request.getSession().getAttribute("adminusername")!=null)
		{
			request.getSession().removeAttribute("adminusername");
		}
    	return "admin/adminLogin";
    }
	
	@RequestMapping("/editPassword")
    public String toEditPassword(Map<String,Object> dto)
    {
    	return "admin/editPassword";
    }
	
	@RequestMapping("/editInfo")
    public String toEditInfo(Model model,@RequestParam(name = "adminname") String adminname)
    {
		model.addAttribute("adminname",adminname);
    	return "admin/editInfo";
    }
	
	@RequestMapping("/administrator")
    public String toAdministrator(Map<String,Object> dto)
    {
    	return "admin/administrator";
    }
	
	@RequestMapping("/admin_add")
    public String toaddAdmin(Map<String,Object> dto)
    {
    	return "admin/admin_add";
    }
	
	@RequestMapping("/company_list")
    public String toCompany_List(Map<String,Object> dto)
    {
    	return "admin/company_list";
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
	@RequestMapping("/admin/login")
    public String toIndex(Model model,HttpServletRequest request)
    {	
		String username=(String) request.getSession().getAttribute("adminusername");	
		if(username!=null)
		{
			String role=(String)request.getSession().getAttribute("role");
			model.addAttribute("adminusername",username);
			model.addAttribute("role",role);
			return "admin/index";
		}
		else
		{
			return "admin/error";
		}
    	
    }
	
	@RequestMapping("/strategy")
    public String toStrategey(Map<String,Object> dto)
    {
    	return "admin/strategy";
    }
	
	@RequestMapping("/editStrategy")
    public String toStrategy_edit(Model model,@RequestParam(name = "id") String id,@RequestParam(name = "operation") String operation)
    {
		model.addAttribute("id",id);
		model.addAttribute("operation",operation);
    	return "admin/strategy_edit";
    }
	
	@RequestMapping("/attraction")
    public String toAttraction(Map<String,Object> dto)
    {
    	return "admin/attraction";
    }

	@RequestMapping("/editAttraction")
    public String toAttraction_edit(Model model,@RequestParam(name = "id") String id,@RequestParam(name = "operation") String operation)
    {
		model.addAttribute("id",id);
		model.addAttribute("operation",operation);
    	return "admin/attraction_edit";
    }
	/**
     * @Author ayds
     * @Description 
     * @Date 2019/7/8
     * @param map
     * @return boolean
     **/
	//===========================结伴(ab05)和游记(ab01)模块=====================
	@PostMapping("/admin/ab05/selectByPage")
    @ResponseBody					
    public Map<String,Object> selectAb05ByPage(@RequestBody Map<String, Object> map)
    {
		return adminService.selectAb05(map);
    }
	
	@PostMapping("/admin/ab01/selectByPage")
    @ResponseBody					
    public Map<String,Object> selectAb01ByPage(@RequestBody Map<String, Object> map)
    {
		return adminService.selectAb01(map);
    }
	
	@PostMapping("/admin/ab05/changeState")
    @ResponseBody
    public boolean changeAb05State(@RequestBody Map<String, Object> map)
    {	
		return adminService.changeAb05State(map);
    }
	
	@PostMapping("/admin/ab01/changeState")
    @ResponseBody
    public boolean changeAb01State(@RequestBody Map<String, Object> map)
    {	
		return adminService.changeAb01State(map);
    }
	
	
	
	//======================管理员模块相关功能=========================
	@PostMapping("/admin/aa04/fuzzyQuery")
    @ResponseBody					
    public Map<String,Object> selectAa04ByPage(@RequestBody Map<String, Object> map)
    {
		return adminService.selectAa04(map);
    }
	
	@PostMapping("/admin/check")
    @ResponseBody					
    public boolean loginCheck(@RequestBody Map<String, Object> map,HttpServletRequest request)
    {
		String result=adminService.adminCheck(map);
		System.out.println("2");
		 if(result!=null)
		 {
			 request.getSession().setAttribute("adminusername", map.get("username"));
			 request.getSession().setAttribute("adminpassword", map.get("password"));
			 request.getSession().setAttribute("role",result);
			 return true;
		 }else
		 {
			 return false;
		 }
    }
	
	@PostMapping("/admin/editPassword")
    @ResponseBody					
    public boolean editPassword(@RequestBody Map<String, Object> map,HttpServletRequest request)
    {
		map.put("username",request.getSession().getAttribute("adminusername"));
		 if(adminService.updatePwd(map))
		 {
			 return true;
		 }else
		 {
			 return false;
		 }
    }

	@PostMapping("/admin/aa04/changeState")
    @ResponseBody
    public boolean changeAa04State(@RequestBody Map<String, Object> map)
    {	
		return adminService.changeAa04State(map);		
    }
	
	@PostMapping("/admin/aa04/del")
    @ResponseBody
    public boolean delAa04(@RequestBody Map<String, Object> map)
    {	
			return adminService.delAa04(map);		
    }
	
	
	@PostMapping("/admin/aa04/insertAdmin")
    @ResponseBody
    public String insertAa04(@RequestBody Map<String, Object> map)
    {	
		if(adminService.hasOne(map.get("username").toString()))
		{
			return "名称重复!";
		}
		else
		{
			if(adminService.insertAdmin(map))
			{
				return "插入成功!";
			}
			else
			{
				return "插入失败!";
			}
		}
					
    }
	
	@PostMapping("/admin/aa04/changeRole")
    @ResponseBody
    public boolean changeRoleAa04(@RequestBody Map<String, Object> map)
    {	
			return adminService.changeRoleAa04(map);		
    }

	@PostMapping("/admin/aa04/getAdminEmail")
    @ResponseBody
    public String getEmailAa04(@RequestBody Map<String, Object> map)
    {	
			return adminService.getEmailAa04(map);		
    }
	
	
	@PostMapping("/admin/aa04/updateInfo")
    @ResponseBody
    public String updateInfoAa04(@RequestBody Map<String, Object> map,HttpServletRequest request)
    {	
		
		return adminService.updateInfoAa04(map, request);
					
    }
	
	
	
	
	//======================攻略(ab02)和景点(ab03)模块=============================
	@PostMapping("/ab02/insertStrategy")
    @ResponseBody					
    public boolean insertStrategy(@RequestBody Map<String,Object>map)
    {
		return adminService.insertStrategy(map);
    }
	
	@PostMapping("/ab02/updateById")
    @ResponseBody					
    public boolean updateStrategy(@RequestBody Map<String,Object>map)
    {
		return adminService.updateStrategy(map);
    }
	
	@PostMapping("/ab02/delStrategy")
    @ResponseBody					
    public boolean delStrategy(@RequestBody Map<String,Object>map)
    {
		return adminService.delStrategy(map);
    }
	
	@PostMapping("/ab02/fuzzyQuery")
    @ResponseBody					
    public Map<String,Object> ab02fuzzyQuery(@RequestBody Map<String,Object>map)
    {
		return adminService.ab02fuzzyQuery(map);
    }
	
	@PostMapping("/ab02/queryById")
    @ResponseBody					
    public Map<String,Object> queryById(@RequestBody Map<String,Object>map)
    {
		return adminService.ab02queryById(map);
    }
	
	@PostMapping("/ab03/insertAttraction")
    @ResponseBody					
    public boolean insertAttraction(@RequestBody Map<String,Object>map)
    {
		return adminService.insertAttraction(map);
    }
	
	@PostMapping("/ab03/delAttraction")
    @ResponseBody					
    public boolean delAttraction(@RequestBody Map<String,Object>map)
    {
		return adminService.delAttraction(map);
    }
	
	@PostMapping("/ab03/updateById")
    @ResponseBody					
    public boolean updateAttraction(@RequestBody Map<String,Object>map)
    {
		return adminService.updateAttraction(map);
    }
	
	@PostMapping("/ab03/fuzzyQuery")
    @ResponseBody					
    public Map<String,Object> ab03fuzzyQuery(@RequestBody Map<String,Object>map)
    {
		return adminService.ad03fuzzyQuery(map);
    }
	
	@PostMapping("/ab03/queryById")
    @ResponseBody					
    public Map<String,Object> ab03queryById(@RequestBody Map<String,Object>map)
    {
		return adminService.ab03queryById(map);
    }
}
