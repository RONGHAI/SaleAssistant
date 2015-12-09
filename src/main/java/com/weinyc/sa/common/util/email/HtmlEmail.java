package com.weinyc.sa.common.util.email;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
 

public class HtmlEmail extends MultiPartEmail {
	public static final int CID_LENGTH = 10;
	private static final String HTML_MESSAGE_START = "<html><body><pre> ";
	private static final String HTML_MESSAGE_END = " </pre></body></html>";

	protected String text =  "Your email client does not support HTML messages";
	protected String html;
	protected Map<String, InlineImage> inlineEmbeds = new HashMap<String, InlineImage>();

	public HtmlEmail setTextMessage(String aText) {
		if (EmailUtils.isEmpty(aText)) {
			aText = "Your email client does not support HTML messages";
		}
		this.text = aText;
		return this;
	}

	public HtmlEmail setHtmlMessage(String aHtml) {
		if (EmailUtils.isEmpty(aHtml)) {
			aHtml = "";
		}
		this.html = aHtml;
		return this;
	}

	public HtmlEmail setMessage(String aText) {
		if (EmailUtils.isEmpty(aText)) {
			aText = "";
		} 
		this.setTextMessage(aText);

		this.setHtmlMessage(HTML_MESSAGE_START + aText + HTML_MESSAGE_END);

		return this;
	}

	public String embed(String urlString, String name) throws MalformedURLException, Exception {
		return embed(new URL(urlString), name);

	}

	public String embed(File file) throws Exception {
		String cid = EmailUtils.randomAlphabetic(CID_LENGTH).toLowerCase();
		return embed(file, cid);
	}

	public String embed(URL url, String name) throws Exception {
		if (EmailUtils.isEmpty(name)) {
			throw new Exception("name cannot be null or empty");
		}

		// check if a URLDataSource for this name has already been attached;
		// if so, return the cached CID value.
		if (inlineEmbeds.containsKey(name)) {
			InlineImage ii = (InlineImage) inlineEmbeds.get(name);
			URLDataSource urlDataSource = (URLDataSource) ii.getDataSource();
			// make sure the supplied URL points to the same thing
			// as the one already associated with this name.
			// NOTE: Comparing URLs with URL.equals() is a blocking operation
			// in the case of a network failure therefore we use
			// url.toExternalForm().equals() here.
			if (url.toExternalForm().equals(urlDataSource.getURL().toExternalForm())) {
				return ii.getCid();
			} else {
				throw new Exception("embedded name '" + name + "' is already bound to URL " + urlDataSource.getURL() + "; existing names cannot be rebound");
			}
		}

		// verify that the URL is valid
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (IOException e) {
			throw new Exception("Invalid URL", e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) { /* sigh */
			}
		}

		return embed(new URLDataSource(url), name);
	}

	public String embed(DataSource dataSource, String name) throws Exception {
		// check if the DataSource has already been attached;
		// if so, return the cached CID value.
		if (inlineEmbeds.containsKey(name)) {
			InlineImage ii = (InlineImage) inlineEmbeds.get(name);
			// make sure the supplied URL points to the same thing
			// as the one already associated with this name.
			if (dataSource.equals(ii.getDataSource())) {
				return ii.getCid();
			} else {
				throw new Exception("embedded DataSource '" + name + "' is already bound to name " + ii.getDataSource().toString() + "; existing names cannot be rebound");
			}
		} 
		String cid = EmailUtils.randomAlphabetic(HtmlEmail.CID_LENGTH).toLowerCase();
		return embed(dataSource, name, cid);
	}

	public String embed(File file, String cid) throws Exception {
		if (EmailUtils.isEmpty(file.getName())) {
			throw new Exception("file name cannot be null or empty");
		}

		// verify that the File can provide a canonical path
		String filePath = null;
		try {
			filePath = file.getCanonicalPath();
		} catch (IOException ioe) {
			throw new Exception("couldn't get canonical path for " + file.getName(), ioe);
		}

		// check if a FileDataSource for this name has already been attached;
		// if so, return the cached CID value.
		if (inlineEmbeds.containsKey(file.getName())) {
			InlineImage ii = (InlineImage) inlineEmbeds.get(file.getName());
			FileDataSource fileDataSource = (FileDataSource) ii.getDataSource();
			// make sure the supplied file has the same canonical path
			// as the one already associated with this name.
			String existingFilePath = null;
			try {
				existingFilePath = fileDataSource.getFile().getCanonicalPath();
			} catch (IOException ioe) {
				throw new Exception("couldn't get canonical path for file " + fileDataSource.getFile().getName() + "which has already been embedded", ioe);
			}
			if (filePath.equals(existingFilePath)) {
				return ii.getCid();
			} else {
				throw new Exception("embedded name '" + file.getName() + "' is already bound to file " + existingFilePath + "; existing names cannot be rebound");
			}
		}

		// verify that the file is valid
		if (!file.exists()) {
			throw new Exception("file " + filePath + " doesn't exist");
		}
		if (!file.isFile()) {
			throw new Exception("file " + filePath + " isn't a normal file");
		}
		if (!file.canRead()) {
			throw new Exception("file " + filePath + " isn't readable");
		}

		return embed(new FileDataSource(file), file.getName());
	}

	public void buildMessage() throws Exception { 
       
    	build(); 
        super.buildMessage();
    }

	public String embed(DataSource dataSource, String name, String cid) throws Exception {
		if (EmailUtils.isEmpty(name)) {
			throw new Exception("name cannot be null or empty");
		}

		MimeBodyPart mbp = new MimeBodyPart();

		try {
			mbp.setDataHandler(new DataHandler(dataSource));
			mbp.setFileName(name);
			mbp.setDisposition("inline");
			mbp.setContentID("<" + cid + ">");

			InlineImage ii = new InlineImage(cid, dataSource, mbp);
			this.inlineEmbeds.put(name, ii);

			return cid;
		} catch (MessagingException me) {
			throw new Exception(me);
		}
	}

	private void build() throws MessagingException {
		MimeMultipart rootContainer = this.getContainer();
		 
		MimeMultipart bodyEmbedsContainer = rootContainer;
		MimeMultipart bodyContainer = rootContainer;
	 
		rootContainer.setSubType("mixed"); 

		if (EmailUtils.isNotEmpty(this.html) && this.inlineEmbeds.size() > 0) {
			//If HTML body and embeds are used, create a related container and add it to the root container
			bodyEmbedsContainer = new MimeMultipart("related");
			bodyContainer = bodyEmbedsContainer;
			this.addPart(bodyEmbedsContainer, 0); 
			//If TEXT body was specified, create a alternative container and add it to the embeds container
			if (EmailUtils.isNotEmpty(this.text)) {
				bodyContainer = new MimeMultipart("alternative");
				BodyPart bodyPart = createBodyPart();
				try {
					bodyPart.setContent(bodyContainer);
					bodyEmbedsContainer.addBodyPart(bodyPart, 0);
				} catch (MessagingException me) {
					throw me;
				}
			}
		} else if (EmailUtils.isNotEmpty(this.text) && EmailUtils.isNotEmpty(this.html)) {
		 	bodyContainer = new MimeMultipart("alternative");
			this.addPart(bodyContainer, 0);
		}
 
		
		if (EmailUtils.isNotEmpty(this.html)) {
			BodyPart msgHtml = new MimeBodyPart();
			bodyContainer.addBodyPart(msgHtml, 0);
			if (EmailUtils.isNotEmpty(this.charset)) {
				msgHtml.setContent(this.html, Email.TEXT_HTML + "; charset=" + this.charset);
			} else {
				msgHtml.setContent(this.html, Email.TEXT_HTML);
			}
			Iterator<InlineImage> iter = this.inlineEmbeds.values().iterator();
			while (iter.hasNext()) {
				InlineImage ii = (InlineImage) iter.next();
				bodyEmbedsContainer.addBodyPart(ii.getMimeBodyPart());
			}
		}

		if (EmailUtils.isNotEmpty(this.text)) {
			BodyPart msgText =  new MimeBodyPart();
			bodyContainer.addBodyPart(msgText, 0);
			if (EmailUtils.isNotEmpty(this.charset)) {
				msgText.setContent(this.text, Email.TEXT_PLAIN + "; charset=" + this.charset);
			} else {
				msgText.setContent(this.text, Email.TEXT_PLAIN);
			}
		}
	}

	private static class InlineImage {
		/** content id */
		private String cid;
		 
		private DataSource dataSource;
		private MimeBodyPart bodyPart;

	 
		public InlineImage(String cid, DataSource dataSource, MimeBodyPart bodyPart) {
			this.cid = cid;
			this.dataSource = dataSource;
			this.bodyPart = bodyPart;
		}

		/**
		 * Returns the unique content ID of this InlineImage.
		 * @return the unique content ID of this InlineImage
		 */
		public String getCid() {
			return cid;
		}

		/**
		 * Returns the <code>DataSource</code> that represents the encoded content.
		 * @return the <code>DataSource</code> representing the encoded content
		 */
		public DataSource getDataSource() {
			return dataSource;
		}

		/**
		 * Returns the <code>MimeBodyPart</code> that contains the
		 * encoded InlineImage data.
		 * @return the <code>MimeBodyPart</code> containing the encoded
		 * InlineImage data
		 */
		public MimeBodyPart getMimeBodyPart() {
			return bodyPart;
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof InlineImage)) {
				return false;
			}

			InlineImage that = (InlineImage) obj;
			return this.cid.equals(that.cid);
		}

		public int hashCode() {
			return cid.hashCode();
		}
	}

}
