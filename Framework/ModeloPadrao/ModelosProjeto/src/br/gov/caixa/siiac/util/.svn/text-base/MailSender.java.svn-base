/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.caixa.siiac.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 *
 * @author GisConsult
 */
@Service()
@Transactional
public class MailSender {

    String username;
    String password;
    
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
           String username = MailSender.this.username;
           String password = MailSender.this.password;
           return new PasswordAuthentication(username, password);
        }

    }
    
    /**
     * Método responsável por fazer o envio de e-mails.
     * O parâmetro <b>PropriedadesEmail</b> possui todos as propriedades necessárias
     * para enviar o e-mail (senha, usuário, servidor, porta...), além de informações
     * como texto do e-mail, assunto, destinatários (normais e cópias) e uma lista do tipo 
     * <b>Anexo</b> que possui os nomes e os arquivos que serão enviados por anexo.
     * @param email
     * @throws Exception 
     */
    public void sendEmail(PropriedadesEmail email) throws Exception
    {
    	try {
    		
    		//Variáveis que serão usadas na autenticação
    		username = email.getUsuario();
    		password = email.getSenha();
    		
    		Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.user", email.getUsuario());
            properties.setProperty("mail.smtp.host", email.getServidor());
            properties.setProperty("mail.smtp.port", email.getPorta());
            properties.setProperty("mail.smtp.auth", "false"); //para autenticação com ssh
   
            Authenticator auth = new SMTPAuthenticator();
            Session mail = Session.getDefaultInstance(properties, auth);
            
            mail.setDebug(false);
            
            MimeMessage message = new MimeMessage(mail);
            message.setSubject(email.getAssunto());
            message.setFrom(new InternetAddress(email.getRemetente()));
            
            
            message.setRecipients(Message.RecipientType.TO, email.getDestTo());
            
            if (email.getDestToCC().length > 0)
            {
            	message.setRecipients(Message.RecipientType.CC, email.getDestToCC());
            }
            
            Multipart multipart = new MimeMultipart("mixed");
            
            MimeBodyPart body = new MimeBodyPart();
            body.setContent(email.getCorpo(), "text/html; charset=UTF-8");
            
            multipart.addBodyPart(body);            
            
            if (email.getAnexos() != null && !email.getAnexos().isEmpty())
            {
	            for (Anexo pdfs : email.getAnexos()) {
	                MimeBodyPart messageBodyPart = new MimeBodyPart();
	                DataSource source = new ByteArrayDataSource(pdfs.getArquivo(), "application/pdf");
	                messageBodyPart.setDataHandler(new DataHandler(source));
	                messageBodyPart.setFileName(pdfs.getNomeArquivo());
	                multipart.addBodyPart(messageBodyPart);
	            }
            }
            
            message.setContent(multipart);
            
            Transport.send(message);
    		
    	} catch (Exception e)
    	{
    		LogCEF.error(e.getMessage());
    		throw new Exception(e);
    	}
    	
    }
    
    /**
     * Valida um endereço de email pela API JavaMail
     * @param email
     * @return
     */
	public static boolean validateEmail(String email) {
		try {
			new InternetAddress(email).validate();
		} catch (AddressException ex) {
			//email inválido
			return false;
		}
		return true;
	}
}
