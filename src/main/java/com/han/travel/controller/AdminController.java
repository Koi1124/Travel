package com.han.travel.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.han.travel.dao.Aa02Dao;
import com.han.travel.dao.Ab05Dao;
import com.han.travel.support.PageBean;

@Controller
public class AdminController 
{
	@Autowired
    private Ab05Dao ab05Dao;
	
	@Autowired
	private Aa02Dao aa02Dao;

	@RequestMapping("/admin")
    public String Test(Map<String,Object> dto)
    {
    	return "admin";
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
		System.out.println("ab05currpage:"+map.get("currPage"));
		return PageBean.seleceByPage(Integer.parseInt(map.get("currPage").toString()), ab05Dao, "ab05");
    }
	
	@PostMapping("/admin/aa02/selectByPage")
    @ResponseBody
    public Map<String,Object> selectAa02ByPage(@RequestBody Map<String, Object> map)
    {
		return PageBean.seleceByPage(Integer.parseInt(map.get("currPage").toString()), aa02Dao, "aa02");
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
	
}
