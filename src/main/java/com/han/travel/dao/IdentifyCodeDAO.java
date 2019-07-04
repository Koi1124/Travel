package com.han.travel.dao;


import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface IdentifyCodeDAO
{

    Map<String, Object> getIdentifyCodeByMail(String mail);

    boolean deleteIdentifyCodeByMail(String mail);

    boolean updateIdentifyCode(Map<String, Object> dto);

    boolean addIdentifyCode(Map<String, Object> dto);
}
