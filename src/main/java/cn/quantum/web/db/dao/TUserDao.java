package cn.quantum.web.db.dao;

import cn.quantum.web.db.domain.TUser;
import cn.quantum.web.db.mapper.TUserMapper;
import cn.quantum.web.vo.TUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TUserDao {
    @Autowired
    private TUserMapper tUserMapper;

    public TUserVO findByUsername(String userName){
        return tUserMapper.findByUsername(userName);
    }

    public boolean addUser(TUser user){
        return tUserMapper.addUser(user);
    }

    public TUserVO findUserVOById(Integer id) {
        return tUserMapper.findUserVOById(id);
    }

    public TUser findById(Integer id) {
        return tUserMapper.findById(id);
    }

    public boolean updatePassword(Integer id, String password) {
        return tUserMapper.updatePassword(id, password);
    }

    public boolean modifyAntName(Integer id, String entName) {
        return tUserMapper.modifyAntName(id, entName);
    }

    public TUser getEmailById(Integer id){
        return tUserMapper.getEmailById(id);
    }

    public void updateEmail(int id, String mail){
        tUserMapper.updateEmail(id, mail);
    }

    public void deleteEmailById(Integer id){
        tUserMapper.deleteEmailById(id);
    }

    public TUser getEmailByUsername(String username){
        return tUserMapper.getEmailByUsername(username);
    }
}
