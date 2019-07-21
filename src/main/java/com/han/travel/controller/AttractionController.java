package com.han.travel.controller;


import java.util.Map;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.AdminService;
import com.han.travel.service.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.han.travel.dao.Ab03Dao;
import com.han.travel.support.PageBean;

import javax.servlet.http.HttpSession;

@Controller
public class AttractionController 
{	
	@Autowired
    private AdminService adminService;
	
	@Autowired
    private SightService sightService;

//	@RequestMapping("/attraction")
//    public String toAttraction(Map<String,Object> dto)
//    {
//    	return "admin/attraction";
//    }
//
//	@RequestMapping("/editAttraction")
//    public String toAttraction_edit(Model model,@RequestParam(name = "id") String id,@RequestParam(name = "operation") String operation)
//    {
//		model.addAttribute("id",id);
//		model.addAttribute("operation",operation);
//    	return "admin/attraction_edit";
//    }
//
//	@PostMapping("/ab03/insertAttraction")
//    @ResponseBody
//    public boolean insertAttraction(@RequestBody Map<String,Object>map)
//    {
//		return ab03Dao.insertAttraction(map);
//    }
//
//	@PostMapping("/ab03/delAttraction")
//    @ResponseBody
//    public boolean delAttraction(@RequestBody Map<String,Object>map)
//    {
//		return ab03Dao.delAttraction(Integer.parseInt(map.get("id").toString()));
//    }
//
//	@PostMapping("/ab03/updateById")
//    @ResponseBody
//    public boolean updateAttraction(@RequestBody Map<String,Object>map)
//    {
//		return ab03Dao.updateAttraction(map);
//    }
//
//	@PostMapping("/ab03/fuzzyQuery")
//    @ResponseBody
//    public Map<String,Object> fuzzyQuery(@RequestBody Map<String,Object>map)
//    {
//		return PageBean.fuzzyQuery(ab03Dao, "ab03",map);
//    }
//
//	@PostMapping("/ab03/queryById")
//    @ResponseBody
//    public Map<String,Object> queryById(@RequestBody Map<String,Object>map)
//    {
//		return ab03Dao.queryById(Integer.parseInt(map.get("id").toString()));
//    }
	

    /**
     * @Author Saki
     * @Description 景点详细页面
     * @Date 2019/7/19
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
     * @param session
     * @return java.lang.String
     **/
    @RequestMapping("/sight/{id}")
    public String getSight(Map<String, Object> dto, @PathVariable("id") int id, HttpSession session)
    {
        dto.putAll(sightService.getSightDetailById(id, (Integer)session.getAttribute(SessionConfig.USER_ID)));
        return "poi/sight";
    }
	
}
