package com.han.travel.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TopNoteDao
 * @Description TODO
 * @Author Saki
 * @Date 2019/7/25
 **/
@Repository
public interface TopNoteDao
{

    List<Map<String, Object>> getTopNotes();

    boolean addTopNote(Map<String, Object> map);

}
