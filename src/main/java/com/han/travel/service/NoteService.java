package com.han.travel.service;

import com.han.travel.dao.Aa03Dao;
import com.han.travel.dao.Ab01Dao;
import com.han.travel.dao.TopNoteDao;
import com.han.travel.support.ImgUploadTools;
import com.han.travel.support.Utils;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName NoteService
 * @Description 游记
 * @Author Saki
 * @Date 2019/7/16
 * @LastUpdate 2019/7/16
 **/
@Service
public class NoteService
{
    @Resource
    private Ab01Dao ab01Dao;

    @Resource
    private Aa03Dao aa03Dao;

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
    private List<Map<String,Object>> getNotesByMdd(int page, int offset, String order, int mddId)
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



    /**
     * @Author Saki
     * @Description 上传图片
     * @Date 2019/7/16
     * @param list
     * @return java.util.List<java.lang.String>
     **/
    public List<String> uploadImg(List<String> list)
    {
        return ImgUploadTools.uploadImgs(list);
    }

    /**
     * @Author Saki
     * @Description 添加
     * @Date 2019/7/16
     * @param map
     * @return java.lang.Integer
     **/
    public Integer addNote(Map<String, Object> map)
    {
        map.put("nid", null);
        ab01Dao.addNote(map);
        return (Integer) map.get("nid");
    }

    /**
     * @Author Saki
     * @Description 更新游记
     * @Date 2019/7/16
     * @param map
     * @return
     **/
    public boolean updateNote(Map<String, Object> map)
    {
        map.put("content", (String)map.get("content").toString());
        return ab01Dao.updateNote(map);
    }


    /**
     * @Author Saki
     * @Description 获取所有省市的名字和编号
     * @Date 2019/7/17
     * @param content 文章内容
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public Map<String, Integer> getPlace(String content)
    {
        setPlace();
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> entry : Utils.place.entrySet())
        {
            if (content.contains(entry.getKey()))
            {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * @Author Saki
     * @Description 获得城市列表
     * @Date 2019/7/18 
     * @param 
     * @return void 
     **/
    private void setPlace()
    {
        if (!Utils.isNotEmpty(Utils.place))
        {
            List<Map<String, Object>> list = aa03Dao.getAllNameAndId();
            Map<String, Integer> map = new HashMap<>((int)(list.size() / 0.75) + 1);
            for (Map<String, Object> place : list)
            {
                map.put((String)place.get("name"), (int)place.get("id"));
            }
            Utils.place = map;
        }
    }
    
    
    /**
     * @Author Saki
     * @Description 根据用户id获取草稿列表
     * @Date 2019/7/17
     * @param uid
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getDraftByUid(int uid)
    {
        return ab01Dao.getDraftByUid(uid);
    }

    /**
     * @Author Saki
     * @Description 根据游记id获得游记详情
     *            编辑页面使用，判断用户是否游记编写者
     * @Date 2019/7/17
     * @param nid
     * @param uid
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getMyNoteById(int nid, int uid)
    {
        Map<String, Object> result = ab01Dao.getMyNoteById(nid);
        if ((int)result.get("uid") == uid)
        {
            JSONArray jsonArray = JSONArray.fromObject((String)result.get("content"));
            result.put("content", jsonArray);
            return result;
        }
        return null;
    }


    /**
     * @Author Saki
     * @Description 根据游记id获得游记详情
     * @Date 2019/7/18
     * @param nid
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getNoteById(int nid, Integer status, Integer myId)
    {
        Map<String, Object> result = ab01Dao.getNoteByIdAndStatus(nid, status);
        if (Utils.isNotEmpty(result))
        {
            //分词以及序列化
            setPlace();
            String content = (String) result.get("content");
            for (Map.Entry<String, Integer> entry : Utils.place.entrySet())
            {
                content = content.replaceAll(entry.getKey(), "<a href='/c/" + entry.getValue() + "' class='link _j_keyword_mdd' target='_blank'>"
                        + entry.getKey() + "</a>");
            }

            JSONArray jsonArray = JSONArray.fromObject(content);
            result.put("content", jsonArray);
            if (myId != null)
            {
                result.putAll(ab01Dao.getNoteExtraMsgByIdAndUid(nid, (int) result.get("uid"), myId));
            }
        }
        return result;
    }


    /**
     * @Author Saki
     * @Description 改变游记状态
     * @Date 2019/7/17
     * @param map
     * @return boolean
     **/
    public boolean changeStatus(Map<String, Object> map)
    {
        return ab01Dao.changeStateById(map);
    }

    /**
     * @Author Saki
     * @Description 获得用户的所有游记概览
     * @Date 2019/7/19
     * @param uid
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getNoteByUid(int uid)
    {
        return ab01Dao.getNoteByUid(uid);
    }

    /**
     * @Author Saki
     * @Description 增加浏览量
     * @Date 2019/7/18
     * @param nid
     * @return void
     **/
    public void addViews(int nid)
    {
        ab01Dao.addViews(nid);
    }


    @Resource
    private TopNoteDao topNoteDao;

    public List<Map<String, Object>> getTopNotes()
    {
        return topNoteDao.getTopNotes();
    }
}