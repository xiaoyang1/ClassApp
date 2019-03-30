package cn.quantum.web.db.dao;

import cn.quantum.web.db.domain.TSport;
import cn.quantum.web.db.mapper.TSportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TSportDao {

    @Autowired
    private TSportMapper tSportMapper;


    public TSport getSportInfoByUserId(Integer userId){
        return tSportMapper.getByUserId(userId);
    }

    public void updateToday(TSport tSport){
        tSportMapper.updateToday(tSport);
    }

    public void updateCompleteByUserId(TSport tSport){
        tSportMapper.updateCompleteByUserId(tSport);
    }

    public void insertToday(TSport tSport){
        tSportMapper.insertToday(tSport);
    }

    public Map<String, Object> getCount(){
        return tSportMapper.getCount();
    }
}
