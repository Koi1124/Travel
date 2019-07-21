package com.han.travel.controller;

import java.util.Map;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.AdminService;
import com.han.travel.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.han.travel.dao.Ab02Dao;
import com.han.travel.support.PageBean;

import javax.servlet.http.HttpSession;

@Controller
public class StrategyController 
{
	@Autowired
    private StrategyService strategyService;

//	@RequestMapping("/strategy")
//    public String toStrategey(Map<String,Object> dto)
//    {
//    	return "admin/strategy";
//    }
//
//	@RequestMapping("/editStrategy")
//    public String toStrategy_edit(Model model,@RequestParam(name = "id") String id,@RequestParam(name = "operation") String operation)
//    {
//		model.addAttribute("id",id);
//		model.addAttribute("operation",operation);
//    	return "admin/strategy_edit";
//    }
//
//	@PostMapping("/ab02/insertStrategy")
//    @ResponseBody
//    public boolean insertStrategy(@RequestBody Map<String,Object>map)
//    {
//		return ab02Dao.insertStrategy(map);
//    }
//
//	@PostMapping("/ab02/updateById")
//    @ResponseBody
//    public boolean updateStrategy(@RequestBody Map<String,Object>map)
//    {
//		return ab02Dao.updateStrategy(map);
//    }
//
//	@PostMapping("/ab02/delStrategy")
//    @ResponseBody
//    public boolean delStrategy(@RequestBody Map<String,Object>map)
//    {
//		return ab02Dao.delStrategy(Integer.parseInt(map.get("id").toString()));
//    }
//
//	@PostMapping("/ab02/fuzzyQuery")
//    @ResponseBody
//    public Map<String,Object> fuzzyQuery(@RequestBody Map<String,Object>map)
//    {
//		return PageBean.fuzzyQuery(ab02Dao, "ab02",map);
//    }
//
//	@PostMapping("/ab02/queryById")
//    @ResponseBody
//    public Map<String,Object> queryById(@RequestBody Map<String,Object>map)
//    {
//		return ab02Dao.queryById(Integer.parseInt(map.get("id").toString()));
//    }

    /**
     * @Author Saki
     * @Description 系统攻略页面
     * @Date 2019/7/20
     * @param id
     * @param session
     * @param dto
     * @return java.lang.String
     **/
    @RequestMapping("/s/{id}")
    public String getStrategyById(@PathVariable("id") int id, HttpSession session, Map<String, Object> dto)
    {
        dto.putAll(strategyService.getStrategyById(id, (Integer)session.getAttribute(SessionConfig.USER_ID)));
        return "poi/route";
    }
}
