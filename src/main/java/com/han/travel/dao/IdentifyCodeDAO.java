package com.han.travel.dao;


import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface IdentifyCodeDAO
{

    Map<String, Object> getIdentifyCodeByMail(Map<String, Object> map);

    boolean deleteIdentifyCodeByMail(Map<String, Object> map);

    boolean updateIdentifyCode(Map<String, Object> dto);

    boolean addIdentifyCode(Map<String, Object> dto);
}
