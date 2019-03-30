package cn.quantum.ClassApp;


import cn.quantum.constant.VersionConstant;
import cn.quantum.util.BonusPoolUtil;
import cn.quantum.util.EmailUtil;
import cn.quantum.util.SpringContextUtil;
import cn.quantum.util.ValidCodeUtil;
import cn.quantum.web.controller.activity.WisdomController;
import cn.quantum.web.db.domain.Fund;
import cn.quantum.web.db.mapper.TFundMapper;
import cn.quantum.web.service.WisdomService;
import cn.quantum.web.vo.TUserVO;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@MapperScan(basePackages = "cn.quantum.web.db.mapper")  // 不用这个就要每个mapper都要加@mapper注解
@SpringBootTest
public class ClassAppApplicationTests {

    @Autowired
    private WisdomService wisdomService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VersionConstant versionConstant;
    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    WisdomController wisdomController;
    @Autowired
    TFundMapper tFundMapper;

    @Test
    public void Test() throws Exception {
        String url = "/tcheckin/checkInToday";
        String url2 = "/twisdom/getNewest";
        String url3 = "/twisdom/addWisdom";
        String url4 = "/tversion/getLastVersion";
        String result = mockMvc.perform(MockMvcRequestBuilders.post(url4)
//                    .param("wisdom","我....默默地想你....")
//                    .param("url", "/test"))
                )
                .andReturn()
                .getResponse().getContentAsString();

        System.out.println("结果为： " + result);
    }

    @Test
    public void test2() throws Exception{

//        Set<Integer> ids = new HashSet();
//        for(int i = 1; i< 30; i++){
//            ids.add(i);
//        }
//
//        float result = tFundMapper.getBonusPoolOfTodayByIds(ids);
//        System.out.println(result);

        SpringContextUtil.setApplicationContext(webApplicationContext);

        System.out.println("total ; " + BonusPoolUtil.getTotalBonus());
        System.out.println("people: " + BonusPoolUtil.getNumOfPeopleToDevide());
//        ValidCodeUtil.put("111","222");
//        ValidCodeUtil.put("112","333");
//        ValidCodeUtil.put("113","444");
//        System.out.println(ValidCodeUtil.get("111"));
//        if(ValidCodeUtil.isExist("111")){
//            System.out.println(ValidCodeUtil.get("111"));
//        } else {
//            System.out.println("哈哈哈哈");
//        }
//        System.out.println(ValidCodeUtil.get("112"));
//        System.out.println(ValidCodeUtil.get("112"));

//        SpringContextUtil.setApplicationContext(webApplicationContext);
//        EmailUtil.submit("jdkcbcode@163.com", "954597783@qq.com", "test", "ai gushuaixuan");
//        TimeUnit.SECONDS.sleep(10);
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom("jdkcbcode@163.com");
//        mailMessage.setTo("954597783@qq.com");
//        mailMessage.setSubject("test");
//        mailMessage.setText("i love you gushuaixuan");
//        mailMessage.setSentDate(new Date());
//        javaMailSender.send(mailMessage);

//        System.out.println(javaMailSender);
//        TUserVO userVO = new TUserVO();
//        userVO.setId(11);
//        userVO.setEntname("ajkdsa");
//        userVO.setPassword("hahah");
//        userVO.setRegisterTime(new Date());
//        userVO.setUsername("ndakfas");
//        System.out.println(userVO);
//        System.out.println("在哪呢？");
    }
}
