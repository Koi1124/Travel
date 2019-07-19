package com.han.travel.controller;


import com.han.travel.configuration.SessionConfig;
import com.han.travel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/together")
public class CompanyController
{
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private FollowService followService;
    @Autowired
    private MessageService messageService;


    /**
     *@discription: 存入dto，返回结伴主页
     *@param
     *@date: 2019/7/5 9:01
     *@return: java.lang.String
     *@author: Han
     */
    @RequestMapping("/mdd_top")
    @ResponseBody
    public List<Map<String,Object>> getTop()
    {
        return companyService.getTopMDD();
    }

    @RequestMapping("/mdd_search")
    @ResponseBody
    public List<Map<String,Object>> getSearch()
    {
        return companyService.prepareAllMDDInfo();
    }

    @RequestMapping("/company_search")
    @ResponseBody
    public Map<String,Object> getCompany(@RequestBody Map<String,Integer> dto)
    {
        int id=dto.get("id");
        int page=dto.get("page");
        int itemCount=dto.get("itemCount");
        int type=dto.get("type");

        Map<String,Object>result=new HashMap<>();
        switch (type)
        {
            case 3:
                result=companyService.searchCompByMDDOrderByFocus(id,(page-1)*itemCount,itemCount);
                break;
            case 2:
                result=companyService.searchCompByMDDOrderByLatest(id,(page-1)*itemCount,itemCount);
                break;
            case 1:
                result=companyService.searchCompByMDDOrderBySoon(id,(page-1)*itemCount,itemCount);
                break;
            default:
                break;
        }
        return result;
    }

    @RequestMapping("/mdd_info")
    @ResponseBody
    public List<Map<String,Object>> getMddInfo()
    {
        return companyService.getMddInfo();
    }


    @RequestMapping("/publish")
    public String toPublish()
    {
        return "together/publish";
    }

    /**
     * @Author Saki
     * @Description 获取用户之前的报名信息
     * @Date 2019/7/15
     * @param map {
     *           uid:用户id,
     *           tid:结伴id
     *        }
     *
     *  返回数据，如果没有返回 null
     *   否则 map {
     *            phone: 电话,
     *            count:人数,
     *            list:同行列表,
     *            addition:备注,
     *            status:状态,
     *            sex:性别
     *       }
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    @PostMapping("/check_join")
    @ResponseBody
    public Map<String, Object> checkJoin(@RequestBody Map<String, Object> map)
    {
        return companyService.getAppByUidAndTid(map);
    }

    /**
     * @Author Saki
     * @Description 结伴报名信息更改
     * @Date 2019/7/15
     * @param map {
     *           tid: 结伴id,
     *           phone: 联系方式,
     *           count: 同行人数,
     *           list: 同行名单,
     *           addition: 备注,
     *           uid: 用户id,
     *           sex:性别
     *           rUserId: 接受消息用户id
     *           title: 结伴标题
     *        }
     * @return boolean
     **/
    @PostMapping("/join_update")
    @ResponseBody
    public boolean joinUpdate(@RequestBody Map<String, Object> map,HttpSession session)
    {
        messageService.applyUpdate(session.getAttribute(SessionConfig.USER_NAME),map.get("rUserId"),map.get("tid"),map.get("title"));
        return companyService.joinUpdate(map);
    }

    /**
     * @Author Saki
     * @Description 结伴报名
     * @Date 2019/7/15
     * @param map {
     *            tid: 结伴id,
     *            phone: 联系方式,
     *            count: 同行人数,
     *            list: 同行名单,
     *            addition: 备注,
     *            uid: 用户id,
     *            sex:性别
     *            rUserId: 接受消息用户id
     *            title: 结伴标题
     *        }
     * @return boolean
     **/
    @PostMapping("/join")
    @ResponseBody
    public boolean joinCompany(@RequestBody Map<String, Object> map,HttpSession session)
    {
        messageService.apply(session.getAttribute(SessionConfig.USER_NAME),map.get("rUserId"),map.get("tid"),map.get("title"));
        return companyService.joinComp(map);
    }

    @RequestMapping({"","/"})
    public String toHomepage()
    {
        return "together/homepage";
    }
    
    /**
     *@discription: 结伴细节页面信息 map->
     * date: 日期  authorName: 用户名  authorId: 用户ID  authorPic: 用户头像
     * star: 收藏数  commentCount: 评论数  title: 结伴标题  mddPic: 目的地图片
     * setout: 出发地  view: 浏览量  intro: 结伴介绍  app: 报名数
     * id: 结伴id  period: 大约用时天数  name: 结伴目的地合集
     * applicants: 报名表信息-> applicantPic: 报名人头像  applicantName: 报名人名  applicantId: 报名人ID
     * appInfo: 申请表信息-> aid: 报名表id  name: 申请用户名  tel: 联系电话  count: 同行人数  nlist: 同行名单  addition: 备注  uid:申请用户id  sex: 申请用户性别  state: 状态
     *@param id
	 *@param map
	 *@param session 
     *@date: 2019/7/17 16:35
     *@return: java.lang.String
     *@author: Han
     */
    @RequestMapping("/company/detail/{id}.html")
    public String toCompanyDetail(@PathVariable("id") int id, Map<String,Object> map, HttpSession session)
    {
//        if (dto.size()>0)
//        {
//            System.out.println("origin数据："+dto);
//            session.setAttribute("c_authorName",dto.get("authorName"));
//            session.setAttribute("c_authorPic",dto.get("authorPic"));
//            session.setAttribute("c_name",dto.get("name"));
//            session.setAttribute("c_star",dto.get("star"));
//            session.setAttribute("c_mddPic",dto.get("mddPic"));
//        }
//        else
//        {
//            dto.put("id",String.valueOf(id));
//            dto.put("authorName",session.getAttribute("c_authorName"));
//            dto.put("authorPic",session.getAttribute("c_authorPic"));
//            dto.put("name",session.getAttribute("c_name"));
//            dto.put("star",session.getAttribute("c_star"));
//            dto.put("mddPic",session.getAttribute("c_mddPic"));
//            System.out.println(dto);
//        }
        Map<String,Object> detail=companyService.getCompPartialDetail(id);
        map.putAll(detail);
        map.put("view",companyService.handleView(id,(Integer) detail.get("view")));
        Map<String,Object> isCollectDto=new HashMap<>(3);
        isCollectDto.put("userId",session.getAttribute(SessionConfig.USER_ID));
        isCollectDto.put("collectId",id);
        isCollectDto.put("type",'5');
        map.put("isCollect",collectService.isCollect(isCollectDto));
        map.put("isFollow",followService.isFollow((Integer) map.get("authorId"), (Integer) session.getAttribute(SessionConfig.USER_ID)));
        map.put("appInfo",companyService.getAppInfoByCId(id));
        System.out.println(map);

        return "together/detail";
    }


    @RequestMapping("/testUser")
    public String tests(HttpSession session)
    {
        session.setAttribute(SessionConfig.USER_ID,7);
        session.setAttribute(SessionConfig.USER_NAME,"user7");
        return "together/homepage";
    }


    /**
     *@discription: 审核通过申请 dto->
     * rUserId: 接受消息用户id
     * pid: 结伴id
     * title: 结伴标题
     * aid: 结伴申请表id
     *@param dto 
     *@date: 2019/7/19 14:27
     *@return: boolean
     *@author: Han
     */
    @RequestMapping("/passApp")
    @ResponseBody
    public Map<String,Object> agreeApp(@RequestBody Map<String,Object> dto)
    {
        Map<String,Object> result=new HashMap<>(3);
        messageService.passApply(dto.get("rUserId"),dto.get("pid"),dto.get("title"));
        if (companyService.passApp((Integer) dto.get("aid")))
        {
            Map<String,Object> temp=userService.getUserPicAndNameById((Integer) dto.get("rUserId"));
            result.putAll(temp);
        }
        return result;
    }

    @RequestMapping("/rejectApp")
    @ResponseBody
    public boolean rejectApp(@RequestBody Map<String,Object> dto)
    {
        messageService.rejectApply(dto.get("rUserId"),dto.get("pid"),dto.get("title"));
        return companyService.rejectApp((Integer) dto.get("aid"));
    }


}
