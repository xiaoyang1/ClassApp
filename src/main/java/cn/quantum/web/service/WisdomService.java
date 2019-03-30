package cn.quantum.web.service;

import cn.quantum.web.db.domain.Wisdom;

public interface WisdomService {

    void insertOne(String content, String url);

    Wisdom findLast();
}
