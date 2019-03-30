package cn.quantum.web.service;

import cn.quantum.web.db.domain.TUser;

public interface EmailService {

    TUser getEmailById(Integer id);

    void deleteEmailById(Integer id);

    void updateEmail(int id, String mail);

    TUser getEmailByUsername(String username);

    void sendSimpleMail(String to, String subject, String checkCode);

}
