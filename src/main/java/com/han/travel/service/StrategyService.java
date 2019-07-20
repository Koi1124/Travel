package com.han.travel.service;

import com.han.travel.dao.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StrategyService
 * @Description 系统攻略
 * @Author Saki
 * @Date 2019/7/20
 * @LastUpdate 2019/7/20
 **/
@Service
public class StrategyService
{
    @Resource
    private Ab02Dao ab02Dao;
    @Resource
    private Ab03Dao ab03Dao;
    @Resource
    private Ad04Dao ad04Dao;
    @Resource
    private Ad02Dao ad02Dao;

    /**
     * @Author Saki
     * @Description 根据系统攻略id获取详细信息
     * @Date 2019/7/20
     * @param rid
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getStrategyById(int rid, Integer uid)
    {
        Map<String, Object> map = ab02Dao.getRouteDetailByRId(rid);
        JSONArray jsonArray = JSONArray.fromObject((String)map.get("routes"));
        for (Object json : jsonArray)
        {
            String[] pois = ((JSONObject)json).get("pois").toString().split(",");
            List<Map<String, Object>> poiList = new ArrayList<>();
            for (int i = 0; i < pois.length; i++) {
                Map<String, Object> poiMap = ab03Dao.getSightIntroById(Integer.parseInt(pois[i]));
                String summary = ((String)poiMap.get("summary")).split("<br/>")[0];
                if (summary.length() > 60)
                {
                    summary = summary.substring(0, 60) + "...";
                }
                poiMap.put("summary", summary);
                poiList.add(poiMap);
            }
            ((JSONObject)json).put("pois", poiList);
        }
        map.put("routes", jsonArray);

        if (uid != null)
        {
            map.put("thumbsUpId", ad02Dao.isThumbsUp(uid, 2, rid));
            map.put("starId", ad04Dao.hasCollect(uid, 2, rid));
        }

        map.put("thumbsUpCount", ad02Dao.getThumbsCountByTypeAndId(2, rid));
        map.put("starCount", ad04Dao.getStarCountByIdAndType(rid, 2));
        return map;
    }

}
