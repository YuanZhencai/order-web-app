package service.impl;

import common.util.ConfigUtil;
import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;
import service.EmailService;
import vo.EmailVo;

import static common.util.ConfigUtil.SMTP_FROM;

/**
 * Created by guxuelong on 2014/12/12.
 */
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmail(EmailVo emailVo) {
        final Email email = new Email();
        email.setSubject(emailVo.getSubject());
        email.setTo(emailVo.getTo());
        email.setBodyHtml(emailVo.getBodyHtml());
        email.setFrom(ConfigUtil.getConfigValue(SMTP_FROM));
        MailerPlugin.send(email);
    }
}
