package vote.Urne;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class MailManager {
    private static MailManager instance;

    private MailManager(){

    }

    public void sendMail(String destmailid,String subject, String text){

        System.setProperty("javax.net.ssl.trustStore", MailConf.getInstance().getPathTrust());
        System.setProperty("javax.net.ssl.trustStorePassword", MailConf.getInstance().getPwdTrust());

        String sendrmailid = MailConf.getInstance().getLogin();
        final String pwd = MailConf.getInstance().getPassword();
        String smtphost = MailConf.getInstance().getHost();

        Properties propvls = new Properties();
        propvls.put("mail.smtp.auth", "true");
        propvls.put("mail.smtp.starttls.enable", "true");
        propvls.put("mail.smtp.ssl.trust", "*");
        propvls.put("mail.smtp.ssl.protocols", "TLSv1.2");
        propvls.put("mail.smtp.host", smtphost);
        propvls.put("mail.smtp.port", MailConf.getInstance().getPort());

        propvls.put("mail.smtp.socketFactory.port", MailConf.getInstance().getPort());
        propvls.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session sessionobj = Session.getInstance(propvls,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sendrmailid, pwd);
                    }
                });

        try {
            //Create MimeMessage object & set values
            Message messageobj = new MimeMessage(sessionobj);
            messageobj.setFrom(new InternetAddress(sendrmailid));
            messageobj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destmailid));
            messageobj.setSubject(subject);
            messageobj.setText(text);
            //Now send the message
            Transport.send(messageobj);
            System.out.println("Mail envoyé");
        } catch (MessagingException exp) {
            exp.printStackTrace();
            throw new RuntimeException(exp);
        }
    }
    public static MailManager getInstance(){
        if(instance == null){
            instance = new MailManager();
        }
        return instance;
    }
}
