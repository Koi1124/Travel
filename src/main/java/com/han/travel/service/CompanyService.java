package com.han.travel.service;

import com.han.travel.dao.*;
import com.han.travel.support.ImgUploadTools;
import com.han.travel.support.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Ad04Dao ad04Dao;
    @Resource
    private Ac08Dao ac08Dao;

    /**
     *@discription: 前8关注最多的结伴目的地
     *@param
     *@date: 2019/7/3 15:37
     *@return: java.util.Map<java.lang.Integer,java.lang.Object>
     *@author: Han
     */
    public List<Map<String,Object>> getTopMDD()
    {
//        long startTime=System.nanoTime();
        List<Map<String,Object>> result=new ArrayList<>(8);
//        List<Integer> mddList=ac07Dao.getAllMDD();
//        int initSize=((int)(mddList.size()/0.75)+1);
//        Map<Integer,Integer> countMap=new HashMap<>(initSize);
//        for (Integer mdd:mddList)
//        {
//            countMap.put(mdd,ac07Dao.placeCount(mdd));
//        }
//        Map<Integer,Integer> sorted=countMap.entrySet().stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue, (e1,e2)->e2,
//                        LinkedHashMap::new));
//        int index=0;
//        for (Map.Entry m:sorted.entrySet())
//        {
//            index++;
//            Map<String,Object> dto=new HashMap<>();
//            int id=(Integer) m.getKey();
//            dto.put("id",id);
//            dto.put("name",aa03Dao.getNameById(id));
//            dto.put("pic",aa03Dao.getPicById(id));
//            dto.put("count",m.getValue());
//            dto.put("app",calculateAppCount(id));
//            result.add(dto);
//            if (index==8)
//            {
//                break;
//            }
//        }
        List<Map<String,Object>> top8MDD=ac07Dao.getTop8MDD();
        for (Map<String,Object> dto:top8MDD)
        {
            int id= (Integer) dto.get("id");
            Map<String,String> temp=aa03Dao.getPicAndNameById(id);
            dto.put("pic",temp.get("pic"));
            dto.put("name",temp.get("name"));
            //dto.put("app",calculateAppCount(id));
            dto.put("app",ac05Dao.getAppCountAndPNameAndPIdByPId(id).get("app"));
            dto.put("star",ad04Dao.getStarCountByMDDID(id));
            result.add(dto);
        }
//        long endTime=System.nanoTime();
//        System.out.println(endTime-startTime);
        return result;
    }


    /**
     *@discription: 获取目的地报名人数
     *@param pid
     *@date: 2019/7/5 14:43
     *@return: java.lang.Integer
     *@author: Han
     */
    private int calculateAppCount(int pid)
    {
        int count=0;
        List<Integer> compList=ac07Dao.getCompByMDD(pid);
        for (Integer cid:compList)
        {
            count=count+ac05Dao.getAppCountByCId(cid);
        }
        return count;
    }

    /**
     *@discription: 提前获取所有城市信息
     *@param
     *@date: 2019/7/5 11:03
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    public List<Map<String,Object>> prepareAllMDDInfo()
    {
        long startTime=System.nanoTime();
        List<Map<String,Object>> result=new ArrayList<>();
        List<Integer> provinces=aa03Dao.getProvinceId();
        for (Integer pid:provinces)
        {
            List<Integer> cities=aa03Dao.getCitiesIdByProvince(pid);
            int initSize=((int)(cities.size()/0.75)+1);
            Map<String,Object> dto=new HashMap<>(initSize);
            dto.put("name",aa03Dao.getNameById(pid));
            int index=0;
            for (Integer cid:cities)
            {
                Map<String,Object> cityInfo=ac05Dao.getAppCountAndPNameAndPIdByPId(cid);
                cityInfo.put("count",ac07Dao.placeCount(cid));
                dto.put(String.valueOf(index++),cityInfo);
            }
            result.add(dto);
        }
        long endTime=System.nanoTime();
        System.out.println("mddInfo:"+result);
        System.out.println(endTime-startTime);
        return result;
    }



    /**
     *@discription: 计算开始结束天数差
     *@param startDate
	 *@param endDate
     *@date: 2019/7/5 9:23
     *@return: int
     *@author: Han
     */
    private int calculateDays(String startDate,Date endDate) throws ParseException
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date start=simpleDateFormat.parse(startDate);
        long days=endDate.getTime()-start.getTime();
        return (int)(days/(24*3600*1000));
    }

    /**
     *@discription: 根据选择的目的地显示对应结伴
     *@param pid
	 *@param order
     *@date: 2019/7/3 19:20
     *@reviseDate: 2019/7/17
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    private Map<String,Object> searchCompByMDD(int pid, String order, int page, int offset)
    {
        switch (order)
        {
            case "热门":
                order="  star DESC";
                break;
            case "最新":
                order="  t1.id DESC";
                break;
            case "即将":
                order="  t1.date";
                break;
            default:
                order="";
                break;
        }

        long startTime=System.nanoTime();
        Map<String,Object> result=new HashMap<>(2);
        List<Map<String,Object>> info=ab05Dao.getSearchCompInfoByMDD(pid,order,page,offset);
        for (Map<String,Object> m:info)
        {
            Date date=(Date)m.get("date");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = simpleDateFormat.format(new Date());
            int days=0;
            try
            {
                days=calculateDays(currentDate,date);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            m.put("days",days);
        }
        long endTime=System.nanoTime();
        result.put("count",ab05Dao.getCompTotalCountByMDD(pid));
        result.put("info",info);
        System.out.println("search test:"+ (endTime-startTime));
        return result;
    }


    /**
     *@discription: 传给前端的最终信息，包括信息的个数与信息本身
     *@param pid 
     *@date: 2019/7/8 9:06
     *@return: java.util.Map<java.lang.String,java.lang.Object>
     *@author: Han
     */
    public Map<String,Object> searchCompByMDDOrderByFocus(int pid,int page,int offset)
    {
        return searchCompByMDD(pid,"热门",page,offset);
    }
    public Map<String,Object> searchCompByMDDOrderByLatest(int pid,int page,int offset)
    {
        return searchCompByMDD(pid,"最新",page,offset);
    }
    public Map<String,Object> searchCompByMDDOrderBySoon(int pid,int page,int offset)
    {
        return searchCompByMDD(pid,"即将",page,offset);
    }



    /**
     *@discription: 如果插入成功，返回当前id
     *@param dto
     *@date: 2019/7/5 8:31
     *@return: java.lang.Integer
     *@author: Han
     */
    private Integer getKey(Map<String,Object> dto)
    {
        dto.put("id",null);
        if (ab05Dao.insertCompany(dto))
        {
            return (Integer) dto.get("id");
        }
        else return null;
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
        Integer cid = getKey(dto);
        if (cid !=null)
        {
            List<String> imgPaths = ImgUploadTools.uploadImgs((ArrayList)dto.get("images"));
            for (int i = 0; i < imgPaths.size(); i++)
            {
                if (!ac08Dao.addCompanyPic(cid, imgPaths.get(i), i))
                {
                    return false;
                }
            }

            String[] mdds = (dto.get("mdds").toString()).split(";");
            for (String mdd : mdds)
            {
                if (!ac07Dao.addCompMDD(cid, Integer.valueOf(mdd)))
                {
                    return false;
                }
            }

            tag = true;
        }
        return tag;
    }

    /**
     *@discription: 管理员更改结伴征召审核状态
     *@param cid
     *@date: 2019/7/4 8:56
     *@return: boolean
     *@author: Han
     */
    public boolean passComp(int cid)
    {
        return ab05Dao.changeStateById(cid,1);
    }
    public boolean rejectComp(int cid)
    {
        return ab05Dao.changeStateById(cid,2);
    }
    public boolean finishComp(int cid)
    {
        return ab05Dao.changeStateById(cid,3);
    }


    /**
     *@discription: 申请参加结伴
     *@param dto
     *@date: 2019/7/5 8:37
     *@return: boolean
     *@author: Han
     */
    public boolean joinComp(Map<String,Object> dto)
    {
        return ac05Dao.insertCompApp(dto);
    }

    /**
     * @Author Saki
     * @Description 更新报名信息
     * @Date 2019/7/15
     * @param map
     * @return boolean
     **/
    public boolean joinUpdate(Map<String, Object> map)
    {
        return ac05Dao.updateAppByUidAndTid(map);
    }

    /**
     *@discription: 结伴发起用户更改参加表状态
     *@param aid
     *@date: 2019/7/5 8:40
     *@return: boolean
     *@author: Han
     */
    public boolean passApp(int aid)
    {
        return ac05Dao.changeAppState(aid,1);
    }
    public boolean rejectApp(int aid)
    {
        return ac05Dao.changeAppState(aid,2);
    }

    /**
     *@discription: 结伴发起用户删除结伴征召，当且只当征召状态为未审核
     *@param cid
     *@date: 2019/7/5 8:42
     *@return: boolean
     *@author: Han
     */
    public boolean deleteComp(int cid)
    {
        return ab05Dao.deleteById(cid) && ac07Dao.deleteByCompId(cid);
    }

    
    /**
     *@discription: 添加结伴报名表信息
     *@param dto 
     *@date: 2019/7/8 15:34
     *@return: java.util.Map<java.lang.String,java.lang.Object>
     *@author: Han
     */
    private Map<String,Object> getAppDetailByCId(Map<String,Object> dto)
    {
        int id= (Integer) dto.get("id");
        Map<String,Object> detail=ab05Dao.queryById(id);
        dto.putAll(detail);
        int app=ac05Dao.getAppCountByCId(id);
        dto.put("app",app);
        dto.put("applicants",ac05Dao.getApplicantsIdAndNameAndPicByCId(id));
        return dto;
    }
    
    /**
     *@discription: 得到结伴细节页面部分细节
     *@param cid 
     *@date: 2019/7/17 16:21
     *@return: java.util.Map<java.lang.String,java.lang.Object>
     *@author: Han
     */
    public Map<String,Object> getCompPartialDetail(int cid)
    {
        Map<String,Object> dto=ab05Dao.getOriginDataByCid(cid);
        return getAppDetailByCId(dto);
    }



    public Integer handleView(int id,Integer view)
    {
        if (view==null)view=0;
        ab05Dao.updateViewByComp(id,++view);
        return view;
    }


    public List<Map<String,Object>> getMddInfo()
    {
        return aa03Dao.getAllKidNamesAndId();
    }

    /**
     * @Author Saki
     * @Description 查询用户之前的提交记录
     * @Date 2019/7/15
     * @param map
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public Map<String, Object> getAppByUidAndTid(Map<String, Object> map)
    {
        Map<String, Object> result = ac05Dao.getAppByUidAndTid(map);
        if (Utils.isNotEmpty(result))
        {
            return result;
        }
        return null;
    }

    /**
     *@discription: 根据结伴id得到申请表信息
     *@param cid 
     *@date: 2019/7/19 13:00
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    public List<Map<String,Object>> getAppInfoByCId(int cid)
    {
        return ac05Dao.getAppInfoByCId(cid);
    }

    /**
     * @Author Saki
     * @Description 获得结伴的图片信息
     * @Date 2019/7/20
     * @param cid
     * @return java.util.List<java.lang.String>
     **/
    public List<String> getCompanyPicsById(int cid)
    {
        return ac08Dao.getCompanyPicsByCid(cid);
    }


    /**
     * @Author Saki
     * @Description 获得待审核的结伴信息
     * @Date 2019/7/21
     * @param id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getCompanyBaseDataByCid(int id)
    {
        return ab05Dao.getCompanyBaseDataByCid(id);
    }
}
