package com.han.travel.service;

import com.han.travel.dao.Aa01Dao;
import com.han.travel.dao.Ae01Dao;
import com.han.travel.dao.Af01Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class LetterService
{
    @Resource
    private Ae01Dao ae01Dao;
    @Resource
    private Af01Dao af01Dao;
    @Resource
    private Aa01Dao aa01Dao;



    /**
     *@discription: 通过用户id得到最新消息 dto->
     * toClient: 发送给的用户-> name: 名字, pic: 头像, id: id
     * time: 时间
     * content: 私信内容
     * state: 已读未读状态
     * helper_id: 帮助删除最新的id
     *@param cid 
     *@date: 2019/7/10 17:03
     *@return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@author: Han
     */
    public List<Map<String,Object>> getLatestLettersByClient(int cid)
    {
        List<Map<String,Object>> letterList=ae01Dao.getLatestLetterByClient(cid);
        for (Map<String,Object> letter:letterList)
        {
            if ((Integer)letter.get("client1") == cid)
            {
                letter.put("toClient",aa01Dao.getNameAndPicAndIdById((int)letter.get("client2")));
            }
            else
            {
                letter.put("toClient",aa01Dao.getNameAndPicAndIdById((int)letter.get("client1")));
            }
            letter.remove("client1");
            letter.remove("client2");
        }
        return letterList;
    }


    /**
     *@discription: 
     *@param dto 
     *@date: 2019/7/10 16:44
     *@return: boolean
     *@author: Han
     */
    public boolean insertLettter(Map<String,Object> dto)
    {
        dto.put("id",null);
        ae01Dao.insertLetterToUId(dto);
        int letterId= (int) dto.get("id");
        return updateLatest(letterId,dto);
    }

    /**
     *@discription: 更新聊天栏表最新数据
     *@param letterId
	 *@param dto 
     *@date: 2019/7/10 16:44
     *@return: boolean
     *@author: Han
     */
    private boolean updateLatest(int letterId,Map<String,Object> dto)
    {
        boolean tag=false;
        Integer isExist=af01Dao.isExistLatest(dto);
        if (isExist!=null)
        {
            af01Dao.deleteChatBar(isExist);
            tag=true;
        }
        af01Dao.insertChatBar(letterId);
        return tag;
    }

    public List<Map<String,Object>> getDetailByClientAndToClient(int cid,int tocid)
    {
        return ae01Dao.getDetailByClientAndToClient(cid,tocid);
    }
    
    /**
     *@discription: 根据letter id删除chatBar
     *@param id 
     *@date: 2019/7/18 19:22
     *@return: boolean
     *@author: Han
     */
    public boolean removeChat(Integer id)
    {
        return af01Dao.deleteChatBar(id);
    }
    
    /**
     *@discription: 根据letter_id 改变私信状态，设为已读
     *@param id 
     *@date: 2019/7/18 21:33
     *@return: boolean
     *@author: Han
     */
    public boolean readLetter(Integer id)
    {
        return ae01Dao.readDone(id);
    }


    public boolean haveLetter(Integer uid)
    {
        boolean tag=false;
        if (af01Dao.haveReadLatest(uid)!=null)
        {
            tag=true;
        }
        return tag;
    }

}
