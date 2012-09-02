package com.fr.server.common;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//singleton object

public final class ServerConfig extends DefaultHandler {
	private static ServerConfig __instance = null;

	private static final String SETTINGS_ROOT = "/etc/fr/";

	public static ServerConfig instance() {
		if (__instance == null) {
			__instance = new ServerConfig();
		}

		return __instance;
	}

	private String dbHost = null;

	private String dbName = null;

	private String dbPassword = null;

	private String dbUser = null;

	private String jdbcDriver = null;

	private String smtpHost = null;

	private int smtpPort = 0;

	private ServerConfig() {
		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			// parse the file and also register this class for call backs
			sp.parse(SETTINGS_ROOT + "settings.xml", this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public String getDBHost() {
		return dbHost;
	}

	public String getDBName() {
		return dbName;
	}

	public String getDBPassword() {
		return dbPassword;
	}

	public String getDBUser() {
		return dbUser;
	}

	public String getJDBCDriver() {
		return jdbcDriver;
	}

	public String getSMTPHost() {
		return smtpHost;
	}

	public int getSMTPPort() {
		return smtpPort;
	}

	// Event Handlers
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// reset

		if (qName.equalsIgnoreCase("FRDatabase")) {
			dbHost = attributes.getValue("host");
			dbUser = attributes.getValue("user");
			dbPassword = attributes.getValue("password");
			dbName = attributes.getValue("name");
		} else if (qName.equalsIgnoreCase("JDBC")) {
			jdbcDriver = attributes.getValue("driver");
		} else if (qName.equalsIgnoreCase("SMTPServer")) {
			smtpHost = attributes.getValue("host");
			smtpPort = Integer.parseInt(attributes.getValue("port"));
		}
	}

}
