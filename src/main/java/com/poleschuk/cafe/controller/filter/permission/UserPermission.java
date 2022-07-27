package com.poleschuk.cafe.controller.filter.permission;

import com.poleschuk.cafe.controller.command.CommandType;

import java.util.Set;

/**
 * UserPermission enum lists accessible command for user roles.
 */
public enum UserPermission {

    ADMIN(Set.of(CommandType.CHANGE_LANGUAGE.name(),
            CommandType.REGISTRATION.name(),
            CommandType.FIND_ALL_CLIENTS.name(),
            CommandType.FIND_ALL_ADMINS.name(),
            CommandType.SIGN_OUT.name(),
            CommandType.DELETE_ADMIN.name(),
            CommandType.FIND_ALL_MENU.name(),
            CommandType.INSERT_NEW_PRODUCT.name(),
            CommandType.UPLOAD_PRODUCT_PHOTO.name(),
            CommandType.UPDATE_USER_PROFILE.name(),
            CommandType.CHANGE_PASSWORD.name(),
            CommandType.GO_TO_UPDATE_PRODUCT_PAGE.name(),
            CommandType.DELETE_PRODUCT.name(),
            CommandType.BLOCK_USER_BY_ID.name(),
            CommandType.UNBLOCK_USER_BY_ID.name(),
            CommandType.UPDATE_PRODUCT.name(),
            CommandType.FIND_ALL_ORDERS.name(),
            CommandType.CHANGE_ORDER_STATE.name(),
            CommandType.FIND_ALL_MENU_BY_SECTION.name(),
            CommandType.INSERT_NEW_SECTION.name(),
            CommandType.UPDATE_SECTION_NAME.name(),
            CommandType.DELETE_SECTION.name(),
            CommandType.DELETE_ORDERS.name(),
            CommandType.SORT_ALL_MENU_BY_PRICE.name(),
            CommandType.FIND_ALL_DELETED_PRODUCTS.name(),
            CommandType.FIND_ALL_DELETED_SECTIONS.name(),
            CommandType.RESTORE_MENU_PRODUCT.name(),
            CommandType.RESTORE_SECTION.name(),
            CommandType.SORT_ALL_MENU_BY_POPULARITY.name(),
            CommandType.GO_TO_SETTINGS.name(),
            CommandType.UPDATE_LOYAL_SCORE.name(),
            CommandType.FIND_CANCELLED_ORDERS.name())),

    CLIENT(Set.of(CommandType.CHANGE_LANGUAGE.name(),
            CommandType.SIGN_IN.name(),
            CommandType.SIGN_OUT.name(),
            CommandType.FIND_ALL_MENU.name(),
            CommandType.UPDATE_USER_PROFILE.name(),
            CommandType.CHANGE_PASSWORD.name(),
            CommandType.ADD_PRODUCT_TO_CART.name(),
            CommandType.CREATE_ORDER.name(),
            CommandType.GO_TO_CART_PAGE.name(),
            CommandType.DELETE_PRODUCT_IN_CART.name(),
            CommandType.GO_TO_ORDERS_PAGE.name(),
            CommandType.CHANGE_ORDER_STATE.name(),
            CommandType.FIND_ALL_MENU_BY_SECTION.name(),
            CommandType.SORT_ALL_MENU_BY_PRICE.name(),
            CommandType.SORT_ALL_MENU_BY_POPULARITY.name(),
            CommandType.GO_TO_SETTINGS.name(),
            CommandType.TOP_UP_BALANCE.name(),
            CommandType.FIND_ALL_LOYAL_SCORE_BONUSES.name(),
            CommandType.GO_TO_ORDER_SCORE_REPORT_PAGE.name(),
            CommandType.UPDATE_ORDER_SCORE_REPORT.name(),
            CommandType.GO_TO_ACCUMULATIVE_DISCOUNT_PAGE.name())),

    GUEST(Set.of(CommandType.SIGN_IN.name(),
            CommandType.CHANGE_LANGUAGE.name(),
            CommandType.REGISTRATION.name(),
            CommandType.FIND_ALL_MENU.name(),
            CommandType.SORT_ALL_MENU_BY_PRICE.name(),
            CommandType.SORT_ALL_MENU_BY_POPULARITY.name()));

    private final Set<String> commands;

    UserPermission(Set<String> commands) {
        this.commands = commands;
    }


    public Set<String> getCommands() {
        return commands;
    }
}