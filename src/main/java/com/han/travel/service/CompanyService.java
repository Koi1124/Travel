package com.han.travel.service;

import com.han.travel.dao.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyService
{
    @Resource
    private Ab05Dao ab05Dao;
    @Resource
    private Ac05Dao ac05Dao;
    @Resource
    private Ac07Dao ac07Dao;
    @Resource
    private Aa03Dao aa03Dao;
    @Resource
    private Aa01Dao aa01Dao;
    

    /**
     *@discription: 前8关注最多的结伴目的地
     *@param
     *@date: 2019/7/3 15:37
     *@return: java.util.Map<java.lang.Integer,java.lang.Object>
     *@author: Han
     */
    public List<Map<String,Object>> getTopMDD()
    {
        List<Integer> mddList=ac07Dao.getAllMDD();
        int initSize=((int)(mddList.size()/0.75)+1);
        Map<Integer,Integer> countMap=new HashMap<>(initSize);
        List<Map<String,Object>> result=new ArrayList<>(8);
        for (Integer mdd:mddList)
        {
            countMap.put(mdd,ac07Dao.placeCount(mdd));
        }
        Map<Integer,Integer> sorted=countMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue, (e1,e2)->e2,
                        LinkedHashMap::new));
        int index=0;
        for (Map.Entry m:sorted.entrySet())
        {
            index++;
            Map<String,Object> dto=new HashMap<>();
            int id=(Integer) m.getKey();
            dto.put("id",id);
            dto.put("name",aa03Dao.getNameById(id));
            dto.put("pic",aa03Dao.getPicById(id));
            dto.put("count",m.getValue());
            result.add(dto);
            if (index==8)
            {
                break;
            }
        }
        return result;
    }


    /**
     *@discription: 根据选择的目的地显示对应结伴，地点用|隔开
     *@param pid 
     *@date: 2019/7/3 19:20
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    public List<Map<String,Object>> searchCompByMDD(int pid)
    {
        List<Map<String,Object>> result=new ArrayList<>();
        List<Integer> compList=ac07Dao.getCompByMDD(pid);
        for (Integer cid:compList)
        {
            Map<String,Object> dto=new HashMap<>();
            List<Integer> MDDList=ac07Dao.getMDDByComp(cid);
            String intro=ab05Dao.getIntroById(cid);
            int authorId=ab05Dao.getAuthorIdById(cid);
            Map<String,String> author=aa01Dao.getNameAndPicById(authorId);
            StringBuilder mddName=new StringBuilder();
            for (Integer p:MDDList)
            {
                mddName.append(aa03Dao.getNameById(p)+"|");
            }
            mddName.deleteCharAt(mddName.length()-1);
            dto.put("id",cid);
            dto.put("name",mddName.toString());
            dto.put("intro",intro);
            dto.put("authorName",author.get("name"));
            dto.put("authorPic",author.get("pic"));
            result.add(dto);
        }
        return result;
    }
    
    
    /**
     *@discription: 发布结伴征召
     *
     * dto includes MDD Id
     *
     *@param dto 
     *@date: 2019/7/4 8:45
     *@return: boolean
     *@author: Han
     */
    public boolean publishComp(Map<String,Object> dto) 
    {
        boolean tag=false;
        if (ab05Dao.insertCompanny(dto))
        {
            dto.put("aab501",)
            if (ac07Dao.insertMDD(dto))
            {
                tag=true;
            }
        }
        return tag;
    }

    /**
     *@discription: 管理员更改结伴征召审核状态
     *@param id
     *@date: 2019/7/4 8:56
     *@return: boolean
     *@author: Han
     */
    public boolean passComp(int id)
    {
        return ab05Dao.changeStateById(id,1);
    }
    public boolean rejectComp(int id)
    {
        return ab05Dao.changeStateById(id,2);
    }
    public boolean finishComp(int id)
    {
        return ab05Dao.changeStateById(id,3);
    }





}
