package com.han.travel.service;

import com.han.travel.dao.Aa03Dao;
import com.han.travel.dao.Ab02Dao;
import com.han.travel.dao.Ab03Dao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CityService
 * @Description 省市
 * @Author Saki
 * @Date 2019/7/20
 * @LastUpdate 2019/7/20
 **/
@Service
public class CityService
{
    @Resource
    private Aa03Dao aa03Dao;
    @Resource
    private Ab02Dao ab02Dao;
    @Resource
    private Ab03Dao ab03Dao;

    /**
     * @Author Saki
     * @Description 获取所有的省
     * @Date 2019/7/210
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getAllProvince()
    {
        return aa03Dao.getAllProvince();
    }

    /**
     * @Author Saki
     * @Description
     * @Date 2019/7/21
     * @param id
     * @return java.lang.String
     **/
    public String getNameById(int id)
    {
        return aa03Dao.getNameById(id);
    }
    /**
     * @Author Saki
     * @Description 获取一个省的城市信息
     * @Date 2019/7/21
     * @param pid
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getCityByProvinceId(int pid)
    {
        return aa03Dao.getCityByProvinceId(pid);
    }

    /**
     * @Author Saki
     * @Description 获取所有的省市信息
     * 返回数据 [{
     *            id:省id,
     *            name:省名称,
     *            cities:[{
     *                id:市id,
     *                name:市名称
     *            }]
     *        }]
     * @Date 2019/7/21
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getAllProvinceAndCity()
    {
        List<Map<String, Object>> list = getAllProvince();
        for (Map<String, Object> map : list)
        {
            map.put("cities", getCityByProvinceId((Integer) map.get("id")));
        }
        System.out.println(list);
        return list;
    }

    /**
     * @Author Saki
     * @Description 获取赞最多的两个路线
     * @Date 2019/7/21
     * @param cid
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> getTopRouteByCid(int cid)
    {
        List<Map<String, Object>> list = ab02Dao.getTopRouteByCid(cid, 2);
        for (Map<String, Object> map : list)
        {
            JSONArray jsonArray = JSONArray.fromObject((String) map.get("routes"));

            String[] pois = ((JSONObject) jsonArray.get(0)).get("pois").toString().split(",");
            List<Map<String, Object>> poiList = new ArrayList<>();
            for (int i = 0; i < pois.length; i++)
            {
                poiList.add(ab03Dao.getSightIntroById(Integer.parseInt(pois[i])));
            }
            map.put("routes", poiList);
        }
        return list;
    }
}
