package com.han.travel.controller;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.han.travel.dao.Ab03Dao;
import com.han.travel.support.PageBean;

@Controller
public class AttractionController 
{
	@Autowired
    private Ab03Dao ab03Dao;
	
	
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

	@PostMapping("/ab03/insertAttraction")
    @ResponseBody					
    public boolean insertAttraction(@RequestBody Map<String,Object>map)
    {
		return ab03Dao.insertAttraction(map);
    }
	
	@PostMapping("/ab03/delAttraction")
    @ResponseBody					
    public boolean delAttraction(@RequestBody Map<String,Object>map)
    {
		return ab03Dao.delAttraction(Integer.parseInt(map.get("id").toString()));
    }
	
	@PostMapping("/ab03/updateById")
    @ResponseBody					
    public boolean updateAttraction(@RequestBody Map<String,Object>map)
    {
		return ab03Dao.updateAttraction(map);
    }
	
	@PostMapping("/ab03/fuzzyQuery")
    @ResponseBody					
    public Map<String,Object> fuzzyQuery(@RequestBody Map<String,Object>map)
    {
		int currPage=Integer.parseInt(map.get("currPage").toString());
		return PageBean.fuzzyQuery(currPage, ab03Dao, "ab03",map.get("name").toString(),map.get("intro").toString());
    }
	
	@PostMapping("/ab03/queryById")
    @ResponseBody					
    public Map<String,Object> queryById(@RequestBody Map<String,Object>map)
    {
		return ab03Dao.queryById(Integer.parseInt(map.get("id").toString()));
    }
	
	
	
}
