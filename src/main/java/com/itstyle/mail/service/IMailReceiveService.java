package com.itstyle.mail.service;

import com.itstyle.mail.domain.MailConn;
import com.itstyle.mail.domain.MailConnCfg;
import com.itstyle.mail.domain.MailItem;
import com.itstyle.mail.domain.UniversalMail;
import com.itstyle.mail.service.exception.MailPlusException;

import java.util.List;

/**
 * 邮件收取开发业务接口
 *
 * @author zeemoo
 * @date 2018/12/8
 */
public interface IMailReceiveService {

    /**
     * 解析邮件
     *
     * @param mailItem
     * @param mailItem
     * @return
     * @throws MailPlusException
     */
    UniversalMail parseEmail(MailItem mailItem, String localSavePath) throws MailPlusException;

    /**
     * 列举需要被同步的邮件
     *
     * @param mailConn
     * @param existUids
     * @return
     * @throws MailPlusException
     */
    List<MailItem> listAll(MailConn mailConn, List<String> existUids) throws MailPlusException;

    /**
     * 连接服务器
     *
     * @param mailConnCfg 连接配置
     * @param proxy       是否代理
     * @return 返回连接
     */
    MailConn createConn(MailConnCfg mailConnCfg, boolean proxy) throws MailPlusException;
}
