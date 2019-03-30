package cn.quantum.web.service.impl;

import cn.quantum.web.db.dao.WisdomDao;
import cn.quantum.web.db.domain.Wisdom;
import cn.quantum.web.service.WisdomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WisdomServiceImpl implements WisdomService{

    @Autowired
    private WisdomDao wisdomDao;

    @Override
    public void insertOne(String content, String url) {
        wisdomDao.insertOne(content, url);
    }

    @Override
    public Wisdom findLast() {
        return wisdomDao.findLast();
    }
}
