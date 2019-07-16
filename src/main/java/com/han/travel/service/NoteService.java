package com.han.travel.service;

import com.han.travel.dao.Ab01Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class NoteService
{

    @Resource
    private Ab01Dao ab01Dao;

    /**
     *@discription: 游记查询
     *@param page
	 *@param offset
	 *@param order 
     *@date: 2019/7/16 14:24
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    private List<Map<String,Object>> getNotes(int page,int offset,String order,String mdd)
    {
        switch (order)
        {
            case "热门":
                order=" thumbsUpCount DESC";
                break;
            case "最新":
                order=" t1.id DESC";
                break;
            default:
                break;
        }
        return ab01Dao.getHomeNotes(order,page,offset,mdd);
    }
    
    /**
     *@discription: 所有游记查询
     *@param page
	 *@param offset
	 *@param order 
     *@date: 2019/7/16 14:28
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    private List<Map<String,Object>> getAllNotes(int page,int offset,String order)
    {
        String mdd=" 1=1";
        return getNotes(page,offset,order,mdd);
    }

    public List<Map<String,Object>> getAllNotesByFocus(int page,int offset)
    {
        return getAllNotes(page,offset,"热门");
    }

    public List<Map<String,Object>> getAllNotesByLatest(int page,int offset)
    {
        return getAllNotes(page,offset,"最新");
    }
    
    /**
     *@discription: 根据目的地查询
     *@param page
	 *@param offset
	 *@param order
	 *@param mddId 
     *@date: 2019/7/16 14:31
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    private List<Map<String,Object>> getNotesByMdd(int page,int offset,String order,int mddId)
    {
        String mdd=" b.aaa301="+mddId;
        return getNotes(page,offset,order,mdd);
    }

    public List<Map<String,Object>> getNotesByMddAndByFocus(int page,int offset,int mddId)
    {
        return getNotesByMdd(page,offset,"热门",mddId);
    }
    public List<Map<String,Object>> getNotesByMddAndByLatest(int page,int offset,int mddId)
    {
        return getNotesByMdd(page,offset,"最新",mddId);
    }

    private Integer getNotesCount(String mdd)
    {
        return ab01Dao.getNotesCount(mdd);
    }
    public Integer getAllNotesCount()
    {
        String mdd=" 1=1";
        return getNotesCount(mdd);
    }
    public Integer getNotesCountByMdd(int mddId)
    {
        String mdd=" aaa301="+mddId;
        return getNotesCount(mdd);
    }




}
