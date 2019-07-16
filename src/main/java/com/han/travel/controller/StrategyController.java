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

import com.han.travel.dao.Ab02Dao;
import com.han.travel.support.PageBean;

@Controller
public class StrategyController 
{
	@Autowired
    private Ab02Dao ab02Dao;
	
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
	
	@PostMapping("/ab02/insertStrategy")
    @ResponseBody					
    public boolean insertStrategy(@RequestBody Map<String,Object>map)
    {
		return ab02Dao.insertStrategy(map);
    }
	
	@PostMapping("/ab02/updateById")
    @ResponseBody					
    public boolean updateStrategy(@RequestBody Map<String,Object>map)
    {
		return ab02Dao.updateStrategy(map);
    }
	
	@PostMapping("/ab02/delStrategy")
    @ResponseBody					
    public boolean delStrategy(@RequestBody Map<String,Object>map)
    {
		return ab02Dao.delStrategy(Integer.parseInt(map.get("id").toString()));
    }

	@PostMapping("/ab02/fuzzyQuery")
    @ResponseBody					
    public Map<String,Object> fuzzyQuery(@RequestBody Map<String,Object>map)
    {
		int currPage=Integer.parseInt(map.get("currPage").toString());
		return PageBean.fuzzyQuery(currPage, ab02Dao, "ab02",map.get("city").toString(),map.get("name").toString());
    }
	
	@PostMapping("/ab02/queryById")
    @ResponseBody					
    public Map<String,Object> queryById(@RequestBody Map<String,Object>map)
    {
		return ab02Dao.queryById(Integer.parseInt(map.get("id").toString()));
    }
	
	
	
}