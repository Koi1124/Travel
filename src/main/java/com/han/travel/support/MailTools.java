package com.han.travel.support;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @ClassName MailTools
 * @Description 邮件发送工具类
 * @Author Saki
 * @Date 2019/7/2
 * @LastUpdate 2019/7/3
 **/
public class MailTools
{
    //发件人地址
    private static String senderAddress = "2270816244@qq.com";
    //发件人账户密码
    private static String senderPassword = "dcvqpspmdmrjeaeg";

    /**
     * @Author Saki
     * @Description 发送邮件
     * @Date 2019/7/3
     * @param toAddress 目的地址
     * @param content 邮件内容
     * @return java.lang.String
     **/
    public static void sendMail(String toAddress, String content) throws Exception
    {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        //session.setDebug(true);

        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session, toAddress, content);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect("smtp.qq.com", senderAddress, senderPassword);

        //如果只想发送给指定的人，可以如下写法
        transport.sendMessage(msg, new Address[]{new InternetAddress(toAddress)});

        //5、关闭邮件连接
        transport.close();
    }


    /**
     * @Author Saki
     * @Description 获得创建一封邮件的实例对象
     * @Date 2019/7/2
     * @param session
     * @return javax.mail.internet.MimeMessage
     **/
    private static MimeMessage getMimeMessage(Session session, String toAddress, String content) throws Exception
    {
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toAddress));
        //设置邮件主题
        msg.setSubject("周游旅行平台服务","UTF-8");
        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

    /**
     * @Author Saki
     * @Description 获取6位随机验证码
     * @Date 2019/7/3
     * @return java.lang.String
     **/
    public static String getRandomIdentifyCode()
    {
        String str = String.valueOf((int)(Math.random() * 1000000));
        return "000000".substring(str.length()) + str;
    }

}