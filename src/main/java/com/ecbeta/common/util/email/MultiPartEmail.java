package com.ecbeta.common.util.email;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

public class MultiPartEmail extends Email {
	private MimeMultipart container;
	
	private BodyPart primaryBodyPart;

	/** The MIME subtype. */
	private String subType;

	private boolean initialized;

	/** Indicates if attachments have been added to the message */
	private boolean boolHasAttachments;

	/**
	 * @return the subType
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Email addPart(String partContent, String partContentType) throws MessagingException

	{
		BodyPart bodyPart = createBodyPart();

		bodyPart.setContent(partContent, partContentType);
		getContainer().addBodyPart(bodyPart);
		return this;
	}

	public Email addPart(MimeMultipart multipart) throws MessagingException {
		return addPart(multipart, getContainer().getCount());
	}

	public Email addPart(MimeMultipart multipart, int index) throws MessagingException {
		BodyPart bodyPart = createBodyPart(); 
		bodyPart.setContent(multipart);
		getContainer().addBodyPart(bodyPart, index);

		return this;
	}

	protected MimeMultipart getContainer() {
		if (!initialized) {
			init();
		}
		return container;
	}

	protected void init() {
		if (initialized) {
			throw new IllegalStateException("Already initialized");
		}

		container = createMimeMultipart();
		super.setEmailBody(container);

		initialized = true;
	}

	protected BodyPart createBodyPart() {
		BodyPart bodyPart = new MimeBodyPart();
		return bodyPart;
	}

	protected MimeMultipart createMimeMultipart() {
		MimeMultipart mmp = new MimeMultipart();
		return mmp;
	}

	public void buildMessage() throws Exception {
		  
	    if (primaryBodyPart != null) {
			// before a multipart message can be sent, we must make sure that
			// the content for the main body part was actually set. If not,
			// an IOException will be thrown during super.send(). 
			BodyPart body = this.getPrimaryBodyPart();
			try {
				body.getContent();
			} catch (IOException e) {
				// do nothing here. content will be set to an empty string
				// as a result.
				// (Should this really be rethrown as an email exception?)
				// throw new EmailException(e);
			}
		} 
		if (subType != null) {
			getContainer().setSubType(subType);
		} 
		super.buildMessage();
	   
	         
	}
	
	@Override
	public Email setMessage(String msg) throws  Exception {
		// throw exception on null message
		if (EmailUtils.isEmpty(msg)) {
			msg = "";
		}

		BodyPart primary = getPrimaryBodyPart();

		if ((primary instanceof MimePart) && EmailUtils.isNotEmpty(charset)) {
			((MimePart) primary).setText(msg, charset);
		} else {
			primary.setText(msg);
		}

		return this;
	}

	protected BodyPart getPrimaryBodyPart() throws MessagingException {
		if (!initialized) {
			init();
		}
		if (this.primaryBodyPart == null) {
			primaryBodyPart = createBodyPart();
			getContainer().addBodyPart(primaryBodyPart, 0);
		}

		return primaryBodyPart;
	}

	public MultiPartEmail attach(URL url, String name, String description) throws Exception {
		return attach(url, name, description, EmailAttachment.ATTACHMENT);
	}

	public MultiPartEmail attach(URL url, String name, String description, String disposition) throws Exception

	{
		// verify that the URL is valid

		try {
			InputStream is = url.openStream();
			is.close();
		} catch (IOException e) {
			throw new Exception("Invalid URL set:" + url, e);
		}

		return attach(new URLDataSource(url), name, description, disposition);
	}

	public MultiPartEmail attach(DataSource ds, String name, String description) throws Exception {
		// verify that the DataSource is valid
		try {
			if (ds == null || ds.getInputStream() == null) {
				throw new Exception("Invalid Datasource");
			}
		} catch (IOException e) {
			throw new Exception("Invalid Datasource", e);
		}

		return attach(ds, name, description, EmailAttachment.ATTACHMENT);
	}

	public MultiPartEmail attach(DataSource ds, String name, String description, String disposition) throws Exception {
		if (EmailUtils.isEmpty(name)) {
			name = ds.getName();
		}
		BodyPart bodyPart = createBodyPart();

		bodyPart.setDisposition(disposition);
		bodyPart.setFileName(name);
		bodyPart.setDescription(description);
		bodyPart.setDataHandler(new DataHandler(ds));

		getContainer().addBodyPart(bodyPart);

		setBoolHasAttachments(true);

		return this;
	}

	/**
	 * @return the boolHasAttachments
	 */
	public boolean isBoolHasAttachments() {
		return boolHasAttachments;
	}

	/**
	 * @param boolHasAttachments the boolHasAttachments to set
	 */
	public void setBoolHasAttachments(boolean boolHasAttachments) {
		this.boolHasAttachments = boolHasAttachments;
	}
	
	public MultiPartEmail attach(EmailAttachment attachment) throws Exception 
	{
		MultiPartEmail result = null; 
		if (attachment == null) {
			throw new Exception("Invalid attachment supplied");
		} 
		URL url = attachment.getURL(); 
		if (url == null) {
			String fileName = null;
			try {
				fileName = attachment.getPath();
				File file = new File(fileName);
				if (!file.exists()) {
					throw new IOException("\"" + fileName + "\" does not exist");
				}
				result = attach(new FileDataSource(file), attachment.getName(), attachment.getDescription(), attachment.getDisposition());
			} catch (IOException e) {
				throw new Exception("Cannot attach file \"" + fileName + "\"", e);
			}
		} else {
			result = attach(url, attachment.getName(), attachment.getDescription(), attachment.getDisposition());
		} 
		return result;
	}

}
