package com.han.travel.service;


import com.han.travel.dao.Ab01Dao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EmpService implements Ab01Dao
{
    @Resource
    private Ab01Dao ab01Dao;

    @Override
    public List<Map<String, String>> query() {
        return this.ab01Dao.query();
    }

    @Override
    public boolean updateEmp(Map<String, Object> dto)
    {
        temp.put("dto",dto);
        return this.ab01Dao.updateEmp(temp);
    }
}
