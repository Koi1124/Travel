package com.han.travel.controller;

import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        //TODO
        session.setAttribute(SessionConfig.USER_ID, 1);

        Map<String, Object> map = new HashMap<>();
        map.put("uid", (int)session.getAttribute(SessionConfig.USER_ID));
        noteService.addNote(map);
        dto.putAll(map);
        return "note/edit";
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

}