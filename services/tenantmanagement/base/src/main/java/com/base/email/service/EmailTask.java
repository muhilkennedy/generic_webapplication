package com.base.email.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import com.base.email.config.EmailSystem;
import com.base.util.FileUtil;
import com.base.util.Log;

/**
 * @author Muhil
 * send email based on config provided.
 */
public class EmailTask implements Runnable {
	
	private String tenantName;
	private String businessEmail;
	private String businessPassword;
	private List<String> recipientEmail;
	private List<String> carbonCopy;
	private String subject;
	private String body;
	private Map<String, File> inlineImages;
	private List<File> attachments;
	
	public EmailTask(String tenantName, List<String> recipientEmail, String subject, String body, Map<String, File> inlineImages) {
		this.tenantName = tenantName;
		this.recipientEmail = recipientEmail;
		this.subject = subject;
		this.body = body;
		this.inlineImages = inlineImages;
	}
	
	public EmailTask(String tenantName, List<String> recipientEmail, String subject, String body, Map<String, File> inlineImages,
			List<File> attachments) {
		this.tenantName = tenantName;
		this.recipientEmail = recipientEmail;
		this.subject = subject;
		this.body = body;
		this.inlineImages = inlineImages;
		this.attachments = attachments;
	}

	public EmailTask(String tenantName, List<String> recipientEmail,
			List<String> carbonCopy, String subject, String body, Map<String, File> inlineImages,
			List<File> attachments) {
		this.tenantName = tenantName;
		this.recipientEmail = recipientEmail;
		this.carbonCopy = carbonCopy;
		this.subject = subject;
		this.body = body;
		this.inlineImages = inlineImages;
		this.attachments = attachments;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getBusinessEmail() {
		return businessEmail;
	}

	public void setBusinessEmail(String businessEmail) {
		this.businessEmail = businessEmail;
	}

	public String getBusinessPassword() {
		return businessPassword;
	}

	public void setBusinessPassword(String businessPassword) {
		this.businessPassword = businessPassword;
	}

	public List<String> getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(List<String> recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public List<String> getCarbonCopy() {
		return carbonCopy;
	}

	public void setCarbonCopy(List<String> carbonCopy) {
		this.carbonCopy = carbonCopy;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, File> getInlineImages() {
		return inlineImages;
	}

	public void setInlineImages(Map<String, File> inlineImages) {
		this.inlineImages = inlineImages;
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

	public void run() {
		try {
			//check for email status
			sendEmail();
		} catch (Exception e) {
			Log.base.error("Error in Side Thread : " + e.getMessage());
		}
		finally {
			if(inlineImages != null) {
				for (Map.Entry<String,File> entry : inlineImages.entrySet()) {
					FileUtil.deleteDirectoryOrFile(entry.getValue());
				}
			}
			if(attachments != null) {
				attachments.stream().forEach(attachment -> {
					FileUtil.deleteDirectoryOrFile(attachment);
				});
			}
		}
	}

	/**
	 * @param tenantName
	 * @param tenantId
	 * @param businessEmail
	 * @param businessPassword
	 * @param recipientEmail
	 * @param subject
	 * @param body
	 * @param inlineImages
	 * @param attachments
	 * @throws Exception
	 */
	public void sendEmail() throws Exception {
		// check if specific smtp host configured for tenant else default config
		Properties props = EmailSystem.getDefaultEmailConfig();
		if(StringUtils.isEmpty(businessEmail)) {
			businessEmail = EmailSystem.getDefaultEmailId();
			businessPassword = EmailSystem.getDefaultEmailPassword();
		}
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(businessEmail, businessPassword);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(businessEmail));
			message.setSubject(tenantName + " : " + subject);
			Multipart multipartObject = new MimeMultipart();
			// Creating first MimeBodyPart object which contains body text.
			InternetHeaders headers = new InternetHeaders();
			headers.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
			BodyPart bodyText = new MimeBodyPart(headers, body.getBytes(StandardCharsets.UTF_8.name()));
			multipartObject.addBodyPart(bodyText);
			// Creating second MimeBodyPart which contains inline body images.
			if (inlineImages != null) {
				inlineImages.entrySet().parallelStream().forEach(image -> {
					BodyPart imagePart = new MimeBodyPart();
					try {
						imagePart.setHeader("Content-ID", "<" + image.getKey() + ">");
						imagePart.setDisposition(MimeBodyPart.INLINE);
						imagePart.setFileName(image.getKey());
						InputStream stream = new FileInputStream(image.getValue());
						DataSource fds = new ByteArrayDataSource(IOUtils.toByteArray(stream),
								MediaType.IMAGE_PNG_VALUE);
						imagePart.setDataHandler(new DataHandler(fds));
						multipartObject.addBodyPart(imagePart);
					} catch (MessagingException | IOException e) {
						Log.base.error("Exception while constructing Inline Images - " + e.getMessage());
					}

				});
			}
			// Creating third MimeBodyPart object which contains attachment.
			if (attachments != null && !attachments.isEmpty()) {
				attachments.parallelStream().filter(attachment -> attachment != null).forEach(attachment -> {
					try {
						BodyPart fileBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(attachment);
						fileBodyPart.setDataHandler(new DataHandler(source));
						fileBodyPart.setFileName(attachment.getName());
						multipartObject.addBodyPart(fileBodyPart);
					} catch (Exception ex) {
						Log.base.error("Exception in adding Attachments - " + ex.getMessage());
					}

				});

			}
			// Attach body text and file attachment to the email.
			message.setContent(multipartObject, MediaType.MULTIPART_MIXED_VALUE);
			recipientEmail.stream().forEach(recipient -> {
					try {
						message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));						
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			});
			if(carbonCopy != null) {
				carbonCopy.stream().forEach(recipient -> {
					try {
						message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(recipient));
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
			Log.base.debug("sendEmail :: Sending email to Recipient - " + message.getRecipients(Message.RecipientType.TO).toString());
			Transport.send(message);
			Log.base.debug("sendEmail :: Email sent Successfully to Recipient - " + message.getRecipients(Message.RecipientType.TO).toString());

		} catch (Exception e) {
			Log.base.error("sendEmail :: Sending email to Recipient - " + e);
		}
	}

}