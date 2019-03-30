package cn.quantum.web.service.impl;

import cn.quantum.util.EmailUtil;
import cn.quantum.web.db.dao.TUserDao;
import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

    @Autowired
    private TUserDao tUserDao;

    @Value("${spring.mail.username}")
    private String from;
    @Override
    public TUser getEmailById(Integer id) {
        return tUserDao.getEmailById(id);
    }

    @Override
    public void deleteEmailById(Integer id) {
        tUserDao.deleteEmailById(id);
    }

    @Override
    public void updateEmail(int id, String mail) {
        tUserDao.updateEmail(id, mail);
    }

    @Override
    public TUser getEmailByUsername(String username) {
        return tUserDao.getEmailByUsername(username);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        EmailUtil.submit(from, to, subject, content);
    }
}
