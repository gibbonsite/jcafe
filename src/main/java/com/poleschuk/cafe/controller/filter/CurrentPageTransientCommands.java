package com.poleschuk.cafe.controller.filter;

import java.util.Set;

import com.poleschuk.cafe.controller.command.CommandType;

public class CurrentPageTransientCommands {
	
	public static final Set<String> transientCommands = Set.of(
			CommandType.SIGN_IN.name(),
			CommandType.REGISTRATION.name(),
			CommandType.DELETE_PRODUCT_IN_CART.name(),
			CommandType.CHANGE_PASSWORD.name(),
			CommandType.INSERT_NEW_PRODUCT.name(),
			CommandType.UPLOAD_PRODUCT_PHOTO.name());
}
