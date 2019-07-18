package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.NoteService;
import com.han.travel.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName NoteController
 * @Description 游记
 * @Author Saki
 * @Date 2019/7/16
 * @LastUpdate 2019/7/16
 **/
@Controller
public class NoteController
{

    @Autowired
    private NoteService noteService;

    /**
     *@discription: 主页游记显示
     *@param dto 
     *@date: 2019/7/16 14:56
     *@return: java.util.Map<java.lang.String,java.lang.Object>
     *@author: Han
     */
    @RequestMapping("/displayNotes")
    @ResponseBody
    public Map<String,Object> getNotesInfo(@RequestBody Map<String,Integer> dto)
    {
        int page= dto.get("page");
        int itemCount=dto.get("itemCount");
        int type=dto.get("type");
        int mddId=dto.get("mddId");
        Map<String,Object> result=new HashMap<>();
        if (mddId==0)
        {
            result.put("count",noteService.getAllNotesCount());
            switch (type)
            {
                case 2:
                    result.put("noteInfo",noteService.getAllNotesByFocus((page-1)*itemCount,itemCount));
                    break;
                case 1:
                    result.put("noteInfo",noteService.getAllNotesByLatest((page-1)*itemCount,itemCount));
                    break;
                default:
                    break;
            }
        }
        else
        {
            result.put("count",noteService.getNotesCountByMdd(mddId));
            switch (type)
            {
                case 2:
                    result.put("noteInfo",noteService.getNotesByMddAndByFocus((page-1)*itemCount,itemCount,mddId));
                    break;
                case 1:
                    result.put("noteInfo",noteService.getNotesByMddAndByLatest((page-1)*itemCount,itemCount,mddId));
                    break;
                default:
                    break;
            }
        }
        return result;
    }


    @RequestMapping("/home")
    public String toHome(Map<String,Object> dto)
    {
        return "homepage";
    }

    /**
     * @Author Saki
     * @Description 上传图片
     * @Date 2019/7/16
     * @param map {
     *            images:List[imgBase64]
     *        }
     * 返回参数list[imagePath]
     * @return java.util.List<java.lang.String>
     **/
    @PostMapping("/note/uploadImg")
    @ResponseBody
    public List<String> uploadImg(@RequestBody Map<String, Object> map)
    {
        return noteService.uploadImg((ArrayList)map.get("images"));
    }


    /**
     * @Author Saki
     * @Description 游记添加
     * @Date 2019/7/16
     * @return java.lang.Integer 游记id
     **/
    @RequestMapping("/note/writeNote")
    public String addNote(Map<String, Object> dto, HttpSession session)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", (int)session.getAttribute(SessionConfig.USER_ID));
        noteService.addNote(map);
        dto.putAll(map);
        return "note/edit";
    }


    /**
     * @Author Saki
     * @Description 草稿编辑
     * @Date 2019/7/17
     * @param
     * @return
     **/
    @RequestMapping("/note/editNote/{id}")
    public String editNote(Map<String, Object> dto,@PathVariable("id") int id, HttpSession session)
    {
        Map<String, Object> map = noteService.getMyNoteById(id, (int)session.getAttribute(SessionConfig.USER_ID));
        dto.putAll(map);
        return "note/edit";
    }

    /**
     * @Author Saki
     * @Description 获得草稿
     *  返回参数 list[map{
     *              nid:游记id
     *              title:标题
     *              editTime:上次保存时间
     *              topImg:头图
     *          }]
     * @Date 2019/7/17 
     * @param session
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
     **/
    @PostMapping("/note/myDraft")
    @ResponseBody
    public List<Map<String, Object>> getDraft(HttpSession session)
    {
        return noteService.getDraftByUid((int)session.getAttribute(SessionConfig.USER_ID));
    }


    /**
     * @Author Saki
     * @Description 发布游记是获取相关地点
     * @Date 2019/7/17
     * @param map {
     *            content:内容
     *        }
     * 返回参数map {
     *           省市:省市id
     *        }
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/
    @PostMapping("/note/getPlace")
    @ResponseBody
    public Map<String, Integer> getPlace(@RequestBody Map<String, String> map)
    {
        return noteService.getPlace(map.get("content"));
    }

    /**
     * @Author Saki
     * @Description 更新游记
     * @Date 2019/7/16
     * @param map {
     *            title:标题 ,
     *            date:出发日期 ,*
     *            time:预计时间 ,*
     *            money:评价消费 ,*
     *            poi:地点,*
     *            status:状态,*
     *            content:内容 ,
     *            topImg:头图 ,
     *            intro:简介 ,
     *            nid:游记id
     *        }
     * @return boolean
     **/
    @PostMapping("/note/updateNote")
    @ResponseBody
    public boolean updateNote(@RequestBody Map<String, Object> map)
    {
        return noteService.updateNote(map);
    }

    /**
     * @Author Saki
     * @Description 改变游记状态
     * @Date 2019/7/17
     * @param map {
     *            nid:游记id,
     *            status:状态
     *        }
     * @return boolean
     **/
    @PostMapping("/note/changeStatus")
    @ResponseBody
    public boolean changeStatus(@RequestBody Map<String, Object> map)
    {
        return noteService.changeStatus(map);
    }
}