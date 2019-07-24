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
 * @LastUpdate 2019/7/22
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
    @Resource
    private Ac09Dao ac09Dao;

    /**
     * @Author Saki
     * @Description 根据系统攻略id获取详细信息
     * @Date 2019/7/20
     * @param rid
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getStrategyDetailById(int rid, Integer uid)
    {
        Map<String, Object> map = getStrategyById(rid);
        if (uid != null)
        {
            map.put("thumbsUpId", ad02Dao.isThumbsUp(uid, 2, rid));
            map.put("starId", ad04Dao.hasCollect(uid, 2, rid));
        }

        map.put("thumbsUpCount", ad02Dao.getThumbsCountByTypeAndId(2, rid));
        map.put("starCount", ad04Dao.getStarCountByIdAndType(rid, 2));
        return map;
    }

    /**
     * @Author Saki
     * @Description 获取普通的信息
     *      map {
     *          rid:攻略id,
     *          pid:城市id,
     *          pname:城市名,
     *          name:攻略标题,
     *          summary:总结,
     *          routes:[{
     *              index:第几天,
     *              play:攻略,
     *              stay:住宿攻略,
     *              stayName:住宿地方,
     *              stayPic:住宿图片,
     *              stayIntro:住宿简介,
     *              pois:[{
     *                  sid:景点id,
     *                  name:景点名称,
     *                  pic:景点图片,
     *                  summary:景点介绍
     *              }]
     *          }]
     *      }
     *
     * @Date 2019/7/22
     * @param rid
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getStrategyById(int rid)
    {
        Map<String, Object> map = ab02Dao.getRouteDetailByRId(rid);
        List<Map<String, Object>> routes = ac09Dao.getRoutesBySid(rid);
        for (Map<String, Object> route : routes)
        {
            String[] pois = route.get("pois").toString().split(",");
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
            route.put("pois", poiList);
        }
        map.put("routes", routes);
        return map;
    }


    /**
     * @Author Saki
     * @Description //TODO
     * @Date 2019/7/22
     * @param cid
     * @param limit
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getTopRouteByCidAndLimit(Integer cid, Integer limit)
    {
        List<Map<String, Object>> list = ab02Dao.getTopRouteByCid(cid, limit);
        for (Map<String, Object> map : list)
        {
            int rid = Integer.valueOf(map.get("rid").toString());
            List<Map<String, Object>> routes = ac09Dao.getRoutesBySid(rid);
            List<List<Map<String, Object>>> routeList = new ArrayList<>();

            for (int i = 0; i < routes.size(); i++)
            {
                List<Map<String, Object>> poiList = new ArrayList<>();
                String[] pois = routes.get(i).get("pois").toString().split(",");
                for (String poi : pois)
                {
                    poiList.add(ab03Dao.getSightIntroById(Integer.valueOf(poi)));
                }
                routeList.add(poiList);
            }
            map.put("routes", routeList);
        }
        return list;
    }

    /**
     * @Author Saki
     * @Description 遗留方法
     * @Date 2019/7/22
     * @param cid
     * @param limit
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getTopRouteByCidAndLimitOneRoute(Integer cid, Integer limit)
    {
        List<Map<String, Object>> list = ab02Dao.getTopRouteByCid(cid, limit);
        for (Map<String, Object> map : list)
        {
            int rid = Integer.valueOf(map.get("rid").toString());
            List<Map<String, Object>> routes = ac09Dao.getRoutesBySid(rid);
//            List<List<Map<String, Object>>> routeList = new ArrayList<>();
            //2为预览上最多显示几天的数据
//            for (int i = 0; i < routes.size() && i < 2; i++)
//            {
                List<Map<String, Object>> poiList = new ArrayList<>();
                String[] pois = routes.get(0).get("pois").toString().split(",");
                for (String poi : pois)
                {
                    poiList.add(ab03Dao.getSightIntroById(Integer.valueOf(poi)));
                }
//                routeList.add(poiList);
//            }
            map.put("routes", poiList);
        }
        return list;
    }

    /**
     * @Author Saki
     * @Description 添加系统攻略
     * @Date 2019/7/22
     * @param map
     * {
     *     pid:,
     *     name:,
     *     summary:
     *     routes:[{
     *         play:,
     *         pois:,
     *         traffic:,
     *         traffic:,
     *         stayName:,
     *         stayInfo:,
     *         stayPic:
     *     }]
     * }
     * @return java.lang.Integer
     **/
    public boolean addStrategy(Map<String, Object> map)
    {
        map.put("sid", null);
        ab02Dao.addStrategy(map);
        if (map.get("sid") != null)
        {
            int sid = (Integer) map.get("sid");
            List<Map<String, Object>> routes = (ArrayList)map.get("routes");
            for (Map<String, Object> route : routes)
            {
                route.put("sid", sid);
                if (!ac09Dao.addRoute(route))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @Author Saki
     * @Description 更新系统攻略
     * @Date 2019/7/22
     * @param map
     * @return boolean
     **/
    public boolean updateStrategy(Map<String, Object> map)
    {
        int sid = Integer.valueOf(map.get("sid").toString());
        if (ac09Dao.deleteRoutesBySid(sid) && ab02Dao.updateStrategy(map))
        {
            List<Map<String, Object>> routes = (ArrayList)map.get("routes");
            for (Map<String, Object> route : routes)
            {
                route.put("sid", sid);
                if (!ac09Dao.addRoute(route))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @Author Saki
     * @Description 删除系统攻略
     * @Date 2019/7/22
     * @param sid
     * @return boolean
     **/
    public boolean deleteStrategy(int sid)
    {
        if (ac09Dao.deleteRoutesBySid(sid))
        {
            return ab02Dao.delStrategy(sid);
        }
        return false;
    }
}
