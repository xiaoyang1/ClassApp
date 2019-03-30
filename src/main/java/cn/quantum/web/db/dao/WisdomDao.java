package cn.quantum.web.db.dao;

import cn.quantum.web.db.domain.Wisdom;
import cn.quantum.web.db.mapper.WisdomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WisdomDao {
    @Autowired
    private WisdomMapper wisdomMapper;

    public void insertOne(String content, String url){
        wisdomMapper.insertOne(content, url);
    }

    public Wisdom findLast(){
        return wisdomMapper.findLast();
    }
}
