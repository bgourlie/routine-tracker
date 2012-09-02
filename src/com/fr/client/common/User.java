package com.fr.client.common;

import java.util.Date;

import com.google.gwt.user.client.Cookies;

public class User {

	private static final String COOKIE_KEY = "frcookie";

	private static User instance;

	public static User instance() {

		if (instance == null) {
			instance = new User();
		}

		return instance;
	}

	private String serverToken = null;

	private User() {
	}

	public String getToken() {

		if (serverToken == null) {
			serverToken = Cookies.getCookie(COOKIE_KEY);
		}

		return serverToken;
	}

	public void setToken(String token) {
		serverToken = token;
		Date expires = new Date(108, 0, 0);

		Cookies.setCookie(COOKIE_KEY, token, expires, null, "/", false);
	}

}