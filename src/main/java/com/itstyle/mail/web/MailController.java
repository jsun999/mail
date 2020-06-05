package com.itstyle.mail.web;

import com.itstyle.mail.common.model.Email;
import com.itstyle.mail.common.model.Result;
import com.itstyle.mail.domain.MailConn;
import com.itstyle.mail.domain.MailConnCfg;
import com.itstyle.mail.domain.MailItem;
import com.itstyle.mail.domain.UniversalMail;
import com.itstyle.mail.service.IMailReceiveService;
import com.itstyle.mail.service.IMailService;
import com.itstyle.mail.service.exception.MailPlusException;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "邮件管理")
@RestController
@RequestMapping("/mail")
public class MailController {

    @Reference(version = "1.0.0")
    private IMailService mailService;

    @Autowired
    private IMailReceiveService receiveService;
    /**
     * 简单测试
     *
     * @param mail
     * @return
     */
    @PostMapping("send")
    public Result send(Email mail) {
        try {
            mailService.sendFreemarker(mail);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 列表
     *
     * @param mail
     * @return
     */
    @PostMapping("list")
    public Result list(Email mail) {
        return mailService.listMail(mail);
    }

    /**
     * 队列测试
     *
     * @param mail
     * @return
     */
    @PostMapping("queue")
    public Result queue(Email mail) {
        try {
            mailService.sendQueue(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok();
    }


    /**
     * 收件列表
     *
     * @param mail
     * @return
     */
    @GetMapping("listAll")
    public Result ReceiveEmail(Email mail) {
        try {
            List<String> list = new ArrayList<>();
            //设置邮件
            //todo
            MailConn conn = receiveService.createConn(new MailConnCfg("","","",110), false);
            List<MailItem> mailItems = receiveService.listAll(conn, list);
            mailItems.forEach(a -> {
                try {
                    UniversalMail universalMail = receiveService.parseEmail(a, "F:\\mail");
                    System.out.println("-------------------------------------");
                    System.out.println(universalMail);
                } catch (MailPlusException e) {
                    e.printStackTrace();
                }
            });
        } catch (MailPlusException e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

}
