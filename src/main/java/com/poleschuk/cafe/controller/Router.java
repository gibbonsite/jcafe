package com.poleschuk.cafe.controller;

import java.util.Optional;

public class Router {
	private static final String EMPTY_PAGE = "";
	
	private String page = EMPTY_PAGE;
	private Optional<String> url = Optional.empty();
	private RouterType type = RouterType.FORWARD;

	public Router() {
	}

	public Router(String page) {
		this.page = page;
	}
	
	public Router(RouterType type) {
		this.type = type;
	}
	
	public Router(String page, RouterType type) {
		this.page = page;
		this.type = type;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = (page != null) ? page : EMPTY_PAGE;
	}

	public void setRedirect() {
		this.type = RouterType.REDIRECT;
	}
	
	public RouterType getType() {
		return type;
	}

	public Optional<String> getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = Optional.ofNullable(url);
	}
}
