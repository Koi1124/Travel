package com.han.travel.controller;


import com.han.travel.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController
{
    @Autowired
    private SearchService searchService;

    @RequestMapping("/innerSight")
    @ResponseBody
    public List<Map<String,Object>> getInnerSights(@RequestBody Map<String,Object> dto)
    {
        return searchService.getSightsByKeyword(dto.get("key_word").toString());
    }
    @RequestMapping("/innerNote")
    @ResponseBody
    public List<Map<String,Object>> getInnerNotes(@RequestBody Map<String,Object> dto)
    {
        return searchService.getNotesByKeyword(dto.get("key_word").toString());
    }
    @RequestMapping("/innerTogether")
    @ResponseBody
    public List<Map<String,Object>> getInnerCompanies(@RequestBody Map<String,Object> dto)
    {
        return searchService.getCompaniesByKeyword(dto.get("key_word").toString());
    }


    @RequestMapping("/sight")
    public String toSight(String s_content, Map<String,Object> dto)
    {
        dto.put("type",1);
        dto.put("sights",searchService.getSightsByKeyword(s_content));
        return "search";
    }

    @RequestMapping("/note")
    public String toNote(String s_content, Map<String,Object> dto)
    {
        dto.put("type",2);
        dto.put("notes",searchService.getNotesByKeyword(s_content));
        return "search";
    }

    @RequestMapping("/together")
    public String toTogether(String s_content, Map<String,Object> dto)
    {
        dto.put("type",3);
        dto.put("companies",searchService.getCompaniesByKeyword(s_content));
        return "search";
    }



}
