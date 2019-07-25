package com.han.travel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.han.travel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController 
{   
	@Autowired
    private AdminService adminService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private StrategyService strategyService;
	@Autowired
	private SightService sightService;
	@Autowired
    private MessageService messageService;


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
	@RequestMapping("/show")
	public String toShowm(ModelMap map,@RequestParam(name = "msg") String msg)
	{
		map.addAttribute("msg",msg);
		return "admin/show";
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
        if (map.get("state").equals(1))
        {
            messageService.passComp(map.get("id"));
        }
        else if (map.get("state").equals(2))
        {
            messageService.rejectComp(map.get("id"));
        }
    	return adminService.changeAb05State(map);
    }
	
	@PostMapping("/admin/ab01/changeState")
    @ResponseBody
    public boolean changeAb01State(@RequestBody Map<String, Object> map)
    {
        if (map.get("state").equals('2'))
        {
            messageService.passNote(map.get("id"));
        }
        else if (map.get("state").equals('3'))
        {
            messageService.rejectNote(map.get("id"));
        }
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
    public String loginCheck(@RequestBody Map<String, Object> map,HttpServletRequest request)
    {
		String result=adminService.adminCheck(map);
		 if(result.equals("用户名或密码不正确"))
		 {
			 return "用户名或密码不正确";
		 }
		 else if(result.equals("管理员未启用"))
		 {
			 return "管理员未启用";
		 }
		 else
		 {
			 request.getSession().setAttribute("adminusername", map.get("username"));
			 request.getSession().setAttribute("adminpassword", map.get("password"));
			 request.getSession().setAttribute("role",result);
			 return "正确";
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
	@PostMapping("/admin/getCounts")
    @ResponseBody
    public Map<String,Object> getCounts(@RequestBody Map<String, Object> map)
    {	
		System.out.println("in");
		return adminService.getCounts();			
    }
	
	
	
	//======================攻略(ab02)和景点(ab03)模块=============================

	/**
	 * @Author Saki
	 * @Description 新建攻略页面
	 * @Date 2019/7/22
	 * @param dto
	 * @return java.lang.String
	 **/
	@RequestMapping("/admin/strategy/new")
	public String newStrategy(Map<String, Object> dto)
	{
		return "admin/verify/strategy";
	}

	/**
	 * @Author Saki
	 * @Description 路线详情
	 * @Date 2019/7/22
	 * @param id
	 * @param dto
	 * @return java.lang.String
	 **/
	@RequestMapping("/admin/strategy/{id}")
	public String editStrategy(@PathVariable int id, Map<String, Object> dto)
	{
		dto.putAll(strategyService.getStrategyById(id));
		return "admin/verify/strategy";
	}

	/**
	 * @Author Saki
	 * @Description 路线预览
	 * @Date 2019/7/23
	 * @param id
	 * @param dto
	 * @return java.lang.String
	 **/
	@RequestMapping("/admin/strategy/preview/{id}")
	public String previewStrategy(@PathVariable int id, Map<String, Object> dto)
	{
		dto.putAll(strategyService.getStrategyById(id));
		return "admin/verify/previewStrategy";
	}

	/**
	 * @Author Saki
	 * @Description 攻略添加
	 * @Date 2019/7/22
	 * @param map {
	 *           pid:城市id,
	 *    		 name:攻略名称,
	 *    		 summary:攻略总结
	 *    		 routes:[{
	 *    		     play:,
	 *    		     pois:,
	 *    		     traffic:,
	 *    		     traffic:,
	 *    		     stayName:,
	 *    		     stayInfo:,
	 *    		     stayPic:
	 *    		 }]
	 * 		  }
	 * @return boolean
	 **/
	@PostMapping("/admin/strategy/add")
	@ResponseBody
	public boolean addStrategy(@RequestBody Map<String, Object> map)
	{
		return strategyService.addStrategy(map);
	}

	/**
	 * @Author Saki
	 * @Description 攻略添加
	 * @Date 2019/7/22
	 * @param map {
	 *            sid:攻略id
	 *           pid:城市id,
	 *    		 name:攻略名称,
	 *    		 summary:攻略总结
	 *    		 routes:[{
	 *    		     play:,
	 *    		     pois:,
	 *    		     traffic:,
	 *    		     traffic:,
	 *    		     stayName:,
	 *    		     stayInfo:,
	 *    		     stayPic:
	 *    		 }]
	 * 		  }
	 * @return boolean
	 **/
	@PostMapping("/admin/strategy/update")
	@ResponseBody
	public boolean updateStrategy(@RequestBody Map<String, Object> map)
	{
		return strategyService.updateStrategy(map);
	}

	/**
	 * @Author Saki
	 * @Description //TODO
	 * @Date 2019/7/23 
	 * @param id
     * @param dto {
     *            pic
     *            summary
     *            name
     *            pid
     *            pname
     *            commentCount
     *            longitude
     *            latitude
     *            phone
     *            site
     *            commendTime
     *            traffic
     *            ticket
     *            openTime
     *            position
     *            collectId
     *        }
	 * @return java.lang.String 
	 **/
	@RequestMapping("/admin/sight/preview/{id}")
	public String previewSight(@PathVariable int id, Map<String, Object> dto)
	{
		dto.putAll(sightService.getSightDetailById(id, null));
		return "admin/verify/previewSight";
	}


	//========================审核详细页面=========================================
	/**
	 * @Author Saki
	 * @Description 游记详细页面
	 * @Date 2019/7/21
	 * @param id
	 * @param dto
	 * @return java.lang.String
	 **/
	@RequestMapping("/admin/note/preview/{id}")
	public String previewNote(@PathVariable int id, Map<String, Object> dto)
	{
		dto.putAll(noteService.getNoteById(id, null, null));
		return "admin/verify/note";
	}

	/**
	 * @Author Saki
	 * @Description 结伴详情页面
	 * @Date 2019/7/21
	 * @param id
	 * @param dto
	 * @return java.lang.String
	 **/
	@RequestMapping("/admin/company/{id}")
	public String previewCompany(@PathVariable int id, Map<String, Object> dto)
	{
		Map<String,Object> detail=companyService.getCompanyBaseDataByCid(id);
		dto.putAll(detail);
		dto.put("images", companyService.getCompanyPicsById(id));
		return "admin/verify/company";
	}
}
