package com.han.travel.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.han.travel.dao.CheckDao;

public class PageBean 
{
	private int currPage;//当前页数
	private int pageSize;//每页显示记录数
	private int tolCount;//总记录数
	private int tolPage;//总页数

	public PageBean(int currPage,int pageSize,int tolCount) 
	{
		this.currPage=currPage;
		this.pageSize=pageSize;
		this.tolCount=tolCount;
		if(tolCount%pageSize==0)
		{
			this.tolPage=this.tolCount/this.pageSize;
		}
		else
		{
			this.tolPage=this.tolCount/this.pageSize+1;
		}
		if(this.currPage<=0)
		{
			this.currPage=1;
		}
		if(this.currPage>=this.tolPage+1)
		{
			this.currPage=this.tolPage;
		}
	}

	public int getCurrPage() 
	{
		return currPage;
	}

	public int getPageSize() 
	{
		return pageSize;
	}

	public int getTolCount() 
	{
		return tolCount;
	}

	public int getTolPage() 
	{
		return tolPage;
	}
	
	public static Map<String,Object> seleceByPage(int currPage,CheckDao check,String tableName)
	{
		PageBean bean=new PageBean(currPage, 5, check.selectCount());
    	int begin=(bean.getCurrPage()-1)*bean.getPageSize();
    	if(begin<=0)         //没有数据时,防止begin小于零
    	{
    		begin=0;
    	}
    	int num=bean.getPageSize();
    	Map<String,Integer>param=new HashMap<>(2);
    	param.put("begin", begin);
    	param.put("num", num);
    	List<Map<String,Object>>data=check.getAll(param);
    	Map<String,Object> result=new HashMap<>();
    	result.put("data", data);
    	result.put("currPage",bean.getCurrPage());
    	result.put("tolPage",bean.getTolPage());
    	return result;
	}
	
	public static Map<String,Object> fuzzyQuery(int currPage,CheckDao check,String tableName,String param1,String param2)
	{
		PageBean bean=new PageBean(currPage, 5, check.fuzzySelectCount(param1,param2));
    	int begin=(bean.getCurrPage()-1)*bean.getPageSize();
    	if(begin<=0)         //没有数据时,防止begin小于零
    	{
    		begin=0;
    	}
    	int num=bean.getPageSize();
    	Map<String,Object>param=new HashMap<>(4);
    	param.put("begin", begin);
    	param.put("num", num);
    	param.put("name", param1);
    	param.put("intro", param2);
    	List<Map<String,Object>>data=check.fuzzyGet(param1,param2,begin,num);
    	Map<String,Object> result=new HashMap<>();
    	result.put("data", data);
    	result.put("currPage",bean.getCurrPage());
    	result.put("tolPage",bean.getTolPage());
    	return result;
	}
	
}
