package com.han.travel.controller;

import com.han.travel.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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




}
