package cn.quantum.util;



import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EmailUtil {
    private static ThreadPoolExecutor emailTheadPool = (ThreadPoolExecutor) ThreadPoolUtil.getExecutor(1, 10);

    private static JavaMailSender mailSender = (JavaMailSender) SpringContextUtil.getBean(JavaMailSender.class);

    public static void submit(String from, String to, String subject, String content){
        log.info("提交了一次邮件任务！");
        SendEmailTask emailTask = new SendEmailTask(from, to, subject, content);
        emailTheadPool.submit(emailTask);
    }

    private static class SendEmailTask implements Runnable{

        private String from;
        private String to;
        private String subject;
        private String content;

        public SendEmailTask(String from, String to, String subject, String content) {
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.content = content;
        }

        @Override
        public void run() {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(from);
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(content);

            try {
                mailSender.send(mailMessage);
                TimeUnit.SECONDS.sleep(1);
                log.info("简单邮件已经发送。");
            } catch (Exception e) {
                log.error("发送简单邮件时发生异常！", e);
            }
        }
    }
}
