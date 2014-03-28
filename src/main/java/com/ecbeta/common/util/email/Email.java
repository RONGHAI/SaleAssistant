package com.ecbeta.common.util.email;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {
	/** */
	public static final String SENDER_EMAIL = "sender.email";
	/** */
	public static final String SENDER_NAME = "sender.name";
	/** */
	public static final String RECEIVER_EMAIL = "receiver.email";
	/** */
	public static final String RECEIVER_NAME = "receiver.name";
	/** */
	public static final String EMAIL_SUBJECT = "email.subject";
	/** */
	public static final String EMAIL_BODY = "email.body";
	/** */
	public static final String CONTENT_TYPE = "content.type";

	/** */
	public static final String MAIL_HOST = "mail.smtp.host";
	/** */
	public static final String MAIL_PORT = "mail.smtp.port";
	/** */
	public static final String MAIL_SMTP_FROM = "mail.smtp.from";
	/** */
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	/** */
	public static final String MAIL_SMTP_USER = "mail.smtp.user";
	/** */
	public static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";
	/** */
	public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

	public static final String MAIL_TRANSPORT_TLS = "mail.smtp.starttls.enable";
	/** */
	public static final String MAIL_SMTP_SOCKET_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
	/** */
	public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
	/** */
	public static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";

	public static final String MAIL_SMTP_CONNECTIONTIMEOUT = "mail.smtp.connectiontimeout";

	public static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";

	/** */
	public static final String SMTP = "smtp";
	/** */
	public static final String TEXT_HTML = "text/html";
	/** */
	public static final String TEXT_PLAIN = "text/plain";
	
	public static final String APP_PDF = "application/pdf";
	public static final String APP_EXCEL = "application/octet-stream";
	
	/** */
	public static final String ATTACHMENTS = "attachments";
	/** */
	public static final String FILE_SERVER = "file.server"; 
	
	public static final String MAIL_DEBUG = "mail.debug";

	/** */
	public static final String KOI8_R = "koi8-r";
	/** */
	public static final String ISO_8859_1 = "iso-8859-1";
	/** */
	public static final String US_ASCII = "us-ascii";

	public static final String UTF_8 = "utf-8";

	protected List<InternetAddress> toList = new ArrayList<InternetAddress>();
	protected List<InternetAddress> ccList = new ArrayList<InternetAddress>();
	protected List<InternetAddress> bccList = new ArrayList<InternetAddress>();
	protected List<InternetAddress> replyList = new ArrayList<InternetAddress>();
	protected String subject;
	protected boolean debug;
	protected Date sentDate;
	protected Authenticator authenticator;
	protected String mailHost;
	protected String bounceAddress;

	protected MimeMessage message;
	protected Session session;

	protected String smtpPort = "25", sslSmtpPort = "465";
	protected boolean ssl, tls;

	protected int socketTimeout, socketConnectionTimeout;

	protected String charset;

	protected Map<String, String> headers = new HashMap<String, String>() , properties =  new HashMap<String, String>() ;

	protected String contentType;

	protected boolean popBeforeSmtp;
	protected String popHost;
	protected String popUsername;
	protected String popPassword;

	protected InternetAddress fromAddress;

	protected MimeMultipart emailBody;
	protected Object content;

	
	public void clearAllRecipients(){
		this.toList = new ArrayList<InternetAddress>();
		this.ccList = new ArrayList<InternetAddress>();
		this.bccList = new ArrayList<InternetAddress>(); 
	}
	
	public Email addBcc(String email) throws AddressException, UnsupportedEncodingException {
		return this.addBcc(email, null);
	}

	public Email addBcc(String email, String name) throws AddressException, UnsupportedEncodingException {
		return this.addBcc(email, name, this.charset);
	}

	public Email addBcc(String email, String name, String charset) throws AddressException, UnsupportedEncodingException {
		if (this.bccList == null) {
			this.bccList = new ArrayList<InternetAddress>();
		}
		this.bccList.add(createInternetAddress(email, name, charset));
		return this;
	}

	public Email addCc(String email) throws AddressException, UnsupportedEncodingException {
		return this.addCc(email, null);
	}

	public Email addCc(String email, String name) throws AddressException, UnsupportedEncodingException {
		return this.addCc(email, name, this.charset);
	}

	public Email addCc(String email, String name, String charset) throws AddressException, UnsupportedEncodingException {
		if (this.ccList == null) {
			this.ccList = new ArrayList<InternetAddress>();
		}
		this.ccList.add(createInternetAddress(email, name, charset));
		return this;
	}

	public void addHeader(String name, String value) {
		if (EmailUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name can not be null");
		}
		if (EmailUtils.isEmpty(value)) {
			throw new IllegalArgumentException("value can not be null");
		}
		if (this.headers == null) {
			this.headers = new HashMap<String, String>();
		}
		this.headers.put(name, value);
	}

	public void addProperty(String name, String value) {
		if (EmailUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name can not be null");
		}
		if (EmailUtils.isEmpty(value)) {
			throw new IllegalArgumentException("value can not be null");
		}
		if (this.properties == null) {
			this.properties = new HashMap<String, String>();
		}
		this.properties.put(name, value);
	}
	
	
	public Email addReplyTo(String email) throws AddressException, UnsupportedEncodingException {
		return this.addReplyTo(email, null);
	}

	public Email addReplyTo(String email, String name) throws AddressException, UnsupportedEncodingException {
		return this.addReplyTo(email, name, this.charset);
	}

	public Email addReplyTo(String email, String name, String charset) throws AddressException, UnsupportedEncodingException {
		if (this.replyList == null) {
			this.replyList = new ArrayList<InternetAddress>();
		}
		this.replyList.add(createInternetAddress(email, name, charset));
		return this;
	}

	public Email addTo(String email) throws AddressException, UnsupportedEncodingException {
		return this.addTo(email, null);
	}

	public Email addTo(String email, String name) throws AddressException, UnsupportedEncodingException {
		return this.addTo(email, name, this.charset);
	}

	public Email addTo(String email, String name, String charset) throws AddressException, UnsupportedEncodingException {
		if (this.toList == null) {
			this.toList = new ArrayList<InternetAddress>();
		}
		this.toList.add(createInternetAddress(email, name, charset));
		return this;
	}

	public void buildMessage() throws Exception {
		this.getSession();
		this.message = this.createMimeMessage(this.session);
		if (EmailUtils.isNotEmpty(this.subject)) {
			if (EmailUtils.isNotEmpty(this.charset)) {
				this.message.setSubject(this.subject, this.charset);
			} else {
				this.message.setSubject(this.subject);
			}
		}
		// update content type (and encoding)
		this.updateContentType(this.contentType);
		if (this.content != null) {
			this.message.setContent(this.content, this.contentType);
		} else if (this.emailBody != null) {
			if (this.contentType == null) {
				this.message.setContent(this.emailBody);
			} else {
				this.message.setContent(this.emailBody, this.contentType);
			}
		} else {
			this.message.setContent("", Email.TEXT_PLAIN);
		}

		if (this.fromAddress != null) {
			this.message.setFrom(this.fromAddress);
		} else {
			if (this.session.getProperty(MAIL_SMTP_FROM) == null) {
				throw new Exception("From address required");
			}
		}

		if (this.toList.size() + this.ccList.size() + this.bccList.size() == 0) {
			throw new Exception("At least one receiver address required");
		}

		if (this.toList.size() > 0) {
			this.message.setRecipients(Message.RecipientType.TO, this.toInternetAddressArray(this.toList));
		}

		if (this.ccList.size() > 0) {
			this.message.setRecipients(Message.RecipientType.CC, this.toInternetAddressArray(this.ccList));
		}

		if (this.bccList.size() > 0) {
			this.message.setRecipients(Message.RecipientType.BCC, this.toInternetAddressArray(this.bccList));
		}

		if (this.replyList.size() > 0) {
			this.message.setReplyTo(this.toInternetAddressArray(this.replyList));
		}

		if (this.headers.size() > 0) {
			Iterator<String> iterHeaderKeys = this.headers.keySet().iterator();
			while (iterHeaderKeys.hasNext()) {
				String name = iterHeaderKeys.next();
				String value = this.headers.get(name);
				this.message.addHeader(name, value);
			}
		}

		if (this.message.getSentDate() == null) {
			this.message.setSentDate(this.getSentDate());
		}

		if (this.popBeforeSmtp) {
			javax.mail.Store store = this.session.getStore("pop3");
			store.connect(this.popHost, this.popUsername, this.popPassword);
		}

	}

	public static InternetAddress createInternetAddress(String email, String name, String charsetName) throws AddressException, UnsupportedEncodingException {
		InternetAddress address = null;
		address = new InternetAddress(email);
		if (EmailUtils.isEmpty(name)) {
			name = email;
		}
		if (EmailUtils.isEmpty(charsetName)) {
			address.setPersonal(name);
		} else {
			Charset set = Charset.forName(charsetName);
			address.setPersonal(name, set.name());
		}
		address.validate();
		return address;
	}

	protected MimeMessage createMimeMessage(Session aSession) {
		return new MimeMessage(aSession);
	}

	// /
	 
 
	public Properties creatProperties() throws Exception {
		Properties properties = new Properties(System.getProperties());
		properties.setProperty(MAIL_TRANSPORT_PROTOCOL, SMTP);
		
		String mailHost  =  this.getMailHost();;
		if (EmailUtils.isEmpty(mailHost)) {
			mailHost = properties.getProperty(MAIL_HOST);
		}

		if (EmailUtils.isEmpty(mailHost)) {
			throw new Exception("Cannot find valid hostname for mail session");
		}
		if (this.smtpPort != null) {
			properties.setProperty(MAIL_PORT, this.smtpPort);
		}

		properties.setProperty(MAIL_HOST, mailHost);
		properties.setProperty(MAIL_DEBUG, String.valueOf(this.debug));

		if (this.authenticator != null) {
			properties.setProperty(MAIL_TRANSPORT_TLS, this.tls ? "true" : "false");
			properties.setProperty(MAIL_SMTP_AUTH, "true");
		}

		if (this.ssl) {
			if (this.sslSmtpPort != null) {
				properties.setProperty(MAIL_PORT, this.sslSmtpPort);
				properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_PORT, this.sslSmtpPort);
			}
			properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
			properties.setProperty(MAIL_SMTP_SOCKET_FACTORY_FALLBACK, "false");
		}

		if (this.bounceAddress != null) {
			properties.setProperty(MAIL_SMTP_FROM, this.bounceAddress);
		}

		if (this.socketTimeout > 0) {
			properties.setProperty(MAIL_SMTP_TIMEOUT, Integer.toString(this.socketTimeout));
		}

		if (this.socketConnectionTimeout > 0) {
			properties.setProperty(MAIL_SMTP_CONNECTIONTIMEOUT, Integer.toString(this.socketConnectionTimeout));
		}
		
		if(this.properties != null){
			for(Map.Entry<String, String>  etnry :this.properties.entrySet()){
				properties.put(etnry.getKey(), etnry.getValue());
			}
		}

		return properties;
	}

	/**
	 * @return the authenticator
	 */
	public Authenticator getAuthenticator() {
		return this.authenticator;
	}

	/**
	 * @return the bccList
	 */
	public List<InternetAddress> getBccList() {
		return this.bccList;
	}

	/**
	 * @return the bounceAddress
	 */
	public String getBounceAddress() {
		return this.bounceAddress;
	}

	/**
	 * @return the ccList
	 */
	public List<InternetAddress> getCcList() {
		return this.ccList;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return this.charset;
	}

	/**
	 * @return the content
	 */
	public Object getContent() {
		return this.content;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public String getHostName() {
		if (EmailUtils.isNotEmpty(this.mailHost)) {
			return this.mailHost;
		} else if (this.session != null) {
			return this.session.getProperty(MAIL_HOST);
		}
		return null;
	}

	/**
	 * @return the mailHost
	 */
	public String getMailHost() {
		if (EmailUtils.isNotEmpty(this.mailHost)) {
			return this.mailHost;
		} else if (this.session != null) {
			return this.session.getProperty(MAIL_HOST);
		}
		
		return this.mailHost;
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return this.message;
	}

	/**
	 * @return the replyList
	 */
	public List<InternetAddress> getReplyList() {
		return this.replyList;
	}

	/**
	 * @return the sentDate
	 */
	public Date getSentDate() {
		if (this.sentDate == null) {
			return new Date();
		}
		return this.sentDate;
	}

	/**
	 * @return the session
	 * @throws Exception
	 */
	public Session getSession() throws Exception {
		if (this.session == null) {
			Properties properties = this.creatProperties();
			this.session = Session.getInstance(properties, this.authenticator);
		}

		return this.session;
	}

	/**
	 * @return the smtpPort
	 */
	public String getSmtpPort() {
		if (EmailUtils.isNotEmpty(this.smtpPort)) {
			return this.smtpPort;
		} else if (this.session != null) {
			return this.session.getProperty(MAIL_PORT);
		}
		return null;
	}

	/**
	 * @return the socketConnectionTimeout
	 */
	public int getSocketConnectionTimeout() {
		return this.socketConnectionTimeout;
	}

	/**
	 * @return the socketTimeout
	 */
	public int getSocketTimeout() {
		return this.socketTimeout;
	}

	public String getSslSmtpPort() {
		if (EmailUtils.isNotEmpty(this.sslSmtpPort)) {
			return this.sslSmtpPort;
		} else if (this.session != null) {
			return this.session.getProperty(MAIL_SMTP_SOCKET_FACTORY_PORT);
		}
		return null;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * @return the toList
	 */
	public List<InternetAddress> getToList() {
		return this.toList;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return this.debug;
	}

	/**
	 * @return the ssl
	 */
	public boolean isSsl() {
		return this.ssl;
	}

	/**
	 * @return the tls
	 */
	public boolean isTls() {
		return this.tls;
	}

	public String send() throws Exception {
		this.buildMessage();
		return this.sendMessage();
	}

	public String sendMessage() throws Exception {
		EmailUtils.notNull(this.message, "message");
		
		
		
	/*	this.message.saveChanges();// 
		Transport transport= this.session .getTransport("smtp");
		transport.connect(this.mailHost ,"","");// 
		transport.sendMessage(this.message,this.message.getAllRecipients());// 
		transport.close();
	 */ 
		try {
			Transport.send(this.message);
		 
			return this.message.getMessageID();
		} catch (Throwable t) {
			String msg = "Sending the email to the following server failed : " + this.getHostName() + ":" +( this.ssl?this.getSslSmtpPort():this.getSmtpPort());
			t.printStackTrace();
			throw new Exception(msg, t);
		}
	}

	public void setAuthentication(final String userName, final String password) {
		this.authenticator = new Authenticator() {// 
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		this.setAuthenticator(this.authenticator);
	}

	/**
	 * @param authenticator
	 *            the authenticator to set
	 */
	public Email setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
		return this;
	}

	public Email setBcc(Collection<InternetAddress> aCollection) throws Exception {
		if (aCollection == null || aCollection.isEmpty()) {

		} else {
			this.bccList = new ArrayList<InternetAddress>(aCollection);
		}
		return this;
	}

	/**
	 * @param bounceAddress
	 *            the bounceAddress to set
	 */
	public Email setBounceAddress(String bounceAddress) {
		this.bounceAddress = bounceAddress;
		return this;
	}

	public Email setCc(Collection<InternetAddress> aCollection) throws Exception {
		if (aCollection == null || aCollection.isEmpty()) {

		} else {
			this.ccList = new ArrayList<InternetAddress>(aCollection);
		}
		return this;
	}

	public void setCharset(String newCharset) {
		Charset set = Charset.forName(newCharset);
		this.charset = set.name();
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public Email setEmailBody(MimeMultipart content) {
		this.emailBody = content;
		return this;
	}

	public void setContent(Object aObject, String aContentType) {
		this.content = aObject;
		this.updateContentType(aContentType);
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public Email setDebug(boolean debug) {
		this.debug = debug;
		return this;
	}

	public Email setFrom(String email)    throws AddressException, UnsupportedEncodingException {
		return this.setFrom(email, null);
	}

	public Email setFrom(String email, String name)   throws AddressException, UnsupportedEncodingException {
		return this.setFrom(email, name, this.charset);
	}

	public Email setFrom(String email, String name, String charset)   throws AddressException, UnsupportedEncodingException{
		this.fromAddress = createInternetAddress(email, name, charset);
		return this;
	}

	public void setHeaders(Map<String, String> map) {
		Iterator<Map.Entry<String, String>> iterKeyBad = map.entrySet().iterator();

		while (iterKeyBad.hasNext()) {
			Map.Entry<String, String> entry = iterKeyBad.next();
			String strName = entry.getKey();
			String strValue = entry.getValue();

			if (EmailUtils.isEmpty(strName)) {
				throw new IllegalArgumentException("name can not be null");
			}
			if (EmailUtils.isEmpty(strValue)) {
				throw new IllegalArgumentException("value can not be null");
			}
		}
		// all is ok, update headers
		this.headers = map;
	}

	/**
	 * @param mailHost
	 *            the mailHost to set
	 */
	public Email setMailHost(String mailHost) {
		this.mailHost = mailHost;
		return this;
	}

 
	public void setPopBeforeSmtp(boolean newPopBeforeSmtp, String newPopHost, String newPopUsername, String newPopPassword) {
		this.popBeforeSmtp = newPopBeforeSmtp;
		this.popHost = newPopHost;
		this.popUsername = newPopUsername;
		this.popPassword = newPopPassword;
	}

	public Email setReplyTo(Collection<InternetAddress> aCollection) throws Exception {
		if (aCollection == null || aCollection.isEmpty()) {

		} else {
			this.replyList = new ArrayList<InternetAddress>(aCollection);
		}
		return this;
	}

	/**
	 * @param sentDate
	 *            the sentDate to set
	 */
	public Email setSentDate(Date sentDate) {
		if (sentDate != null) {
			this.sentDate = new Date(sentDate.getTime());
		}
		return this;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(Session aSession) {
		EmailUtils.notNull(aSession, "no mail session supplied");

		Properties sessionProperties = aSession.getProperties();
		String auth = sessionProperties.getProperty(MAIL_SMTP_AUTH);

		if ("true".equalsIgnoreCase(auth)) {
			final String userName = sessionProperties.getProperty(MAIL_SMTP_USER);
			final String password = sessionProperties.getProperty(MAIL_SMTP_PASSWORD);

			if (EmailUtils.isNotEmpty(userName) && EmailUtils.isNotEmpty(password)) {
				this.authenticator = new Authenticator() {// 
					@Override
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName, password);
					}
				};
				this.session = Session.getInstance(sessionProperties, this.authenticator);
			} else {
				this.session = aSession;
			}
		} else {
			this.session = aSession;
		}
	}

	public void setSmtpPort(int aPortNumber) {
		if (aPortNumber < 1) {
			throw new IllegalArgumentException("Cannot connect to a port number that is less than 1 ( " + aPortNumber + " )");
		}

		this.smtpPort = Integer.toString(aPortNumber);
	}

	/**
	 * @param smtpPort
	 *            the smtpPort to set
	 */
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	/**
	 * @param socketConnectionTimeout
	 *            the socketConnectionTimeout to set
	 */
	public Email setSocketConnectionTimeout(int socketConnectionTimeout) {
		this.socketConnectionTimeout = socketConnectionTimeout;
		return this;
	}

	/**
	 * @param socketTimeout
	 *            the socketTimeout to set
	 */
	public Email setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	/**
	 * @param ssl
	 *            the ssl to set
	 */
	public Email setSsl(boolean ssl) {
		this.ssl = ssl;
		return this;
	}

	/**
	 * @param sslSmtpPort
	 *            the sslSmtpPort to set
	 */
	public Email setSslSmtpPort(String sslSmtpPort) {
		this.sslSmtpPort = sslSmtpPort;
		return this;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public Email setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	/**
	 * @param tls
	 *            the tls to set
	 */
	public Email setTls(boolean tls) {
		this.tls = tls;
		return this;
	}

	public Email setTo(Collection<InternetAddress> aCollection) throws Exception {
		if (aCollection == null || aCollection.isEmpty()) {

		} else {
			this.toList = new ArrayList<InternetAddress>(aCollection);
		}
		return this;
	}

	protected InternetAddress[] toInternetAddressArray(List<InternetAddress> list) {
		InternetAddress[] ia = list.toArray(new InternetAddress[list.size()]);

		return ia;
	}

	public void updateContentType(final String aContentType) {
		if (EmailUtils.isEmpty(aContentType)) {
			this.contentType = null;
		} else {
			// set the content type
			this.contentType = aContentType;
			// set the charset if the input was properly formed
			String strMarker = "; charset=";
			int charsetPos = aContentType.toLowerCase().indexOf(strMarker);
			if (charsetPos != -1) {
				// find the next space (after the marker)
				charsetPos += strMarker.length();
				int intCharsetEnd = aContentType.toLowerCase().indexOf(" ", charsetPos);
				if (intCharsetEnd != -1) {
					this.charset = aContentType.substring(charsetPos, intCharsetEnd);
				} else {
					this.charset = aContentType.substring(charsetPos);
				}
			} else {
				// use the default charset, if one exists, for messages
				// whose content-type is some form of text.
				if (this.contentType.startsWith("text/") && EmailUtils.isNotEmpty(this.charset)) {
					StringBuffer contentTypeBuf = new StringBuffer(this.contentType);
					contentTypeBuf.append(strMarker);
					contentTypeBuf.append(this.charset);
					this.contentType = contentTypeBuf.toString();
				}
			}
		}
	} 
	
	
	public static class EmailAttachment{
		public static final String ATTACHMENT = javax.mail.Part.ATTACHMENT;
		public static final String INLINE = javax.mail.Part.INLINE;
		private String name = "";
		private String path = "";
		private String description = "";private URL url;
		private String disposition = EmailAttachment.ATTACHMENT;
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}
		/**
		 * @param path the path to set
		 */
		public void setPath(String path) {
			this.path = path;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		/**
		 * @return the url
		 */
		public URL getUrl() {
			return url;
		}
		public URL getURL() {
			return url;
		}
		/**
		 * @param url the url to set
		 */
		public void setUrl(URL url) {
			this.url = url;
		}
		/**
		 * @return the disposition
		 */
		public String getDisposition() {
			return disposition;
		}
		/**
		 * @param disposition the disposition to set
		 */
		public void setDisposition(String disposition) {
			this.disposition = disposition;
		}
		public EmailAttachment(String name, String description, String disposition) {
			super();
			this.name = name;
			this.description = description;
			this.disposition = disposition;
		}
		public EmailAttachment() {
			super();
		}
		public EmailAttachment(String name, String path, String description, String disposition) {
			super();
			this.name = name;
			this.path = path;
			this.description = description;
			this.disposition = disposition;
		}
		
	}
	public Email setMessage(String msg)  throws  Exception { return this;}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * @return the popBeforeSmtp
	 */
	public boolean isPopBeforeSmtp() {
		return popBeforeSmtp;
	}

	/**
	 * @return the popHost
	 */
	public String getPopHost() {
		return popHost;
	}

	/**
	 * @return the popPassword
	 */
	public String getPopPassword() {
		return popPassword;
	}

	/**
	 * @return the fromAddress
	 */
	public InternetAddress getFromAddress() {
		return fromAddress;
	}

	/**
	 * @return the emailBody
	 */
	public MimeMultipart getEmailBody() {
		return emailBody;
	} ;
}
