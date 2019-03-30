package cn.quantum;

import cn.quantum.util.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@MapperScan(basePackages = "cn.quantum.web.db.mapper") // 不用这个就要每个mapper都要加@mapper注解
public class ClassAppApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ClassAppApplication.class, args);
		SpringContextUtil.setApplicationContext(applicationContext);
	}
}
