package com.poleschuk.cafe.controller.filter.permission;

import java.util.Set;

import static com.poleschuk.cafe.controller.PagePath.*;

/**
 * PagePermission enum lists accessible pages for user roles.
 */
public enum PagePermission {

    ADMIN(Set.of(START_PAGE,
            HOME_PAGE,
            REGISTRATION_PAGE,
            USER_REGISTERED_PAGE,
            CLIENTS_PAGE,
            CLIENT_BLOCKED_PAGE,
            CLIENT_UNBLOCKED_PAGE,
            ADMINS_PAGE,
            PROFILE_PAGE,
            MENU_PAGE,
            ADD_MENU_PAGE,
            PRODUCT_ADDED_PAGE,
            SETTINGS_PAGE,
            SETTINGS_UPDATED_PAGE,
            PASSWORD_UPDATED_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE,
            UPDATE_PRODUCT_PAGE,
            PRODUCT_UPDATED_PAGE,
            PRODUCT_REMOVED_PAGE,
            ORDERS_PAGE,
            ORDER_STATE_CHANGED_PAGE,
            ORDER_STATE_NOT_CHANGED_PAGE,
            CONTACTS_PAGE,
            SECTION_PAGE,
            PASSWORD_PAGE,
            RESTORE_PAGE,
            SECTION_ADDED_PAGE,
            SECTION_UPDATED_PAGE,
            SECTION_REMOVED_PAGE,
            LOYALTY_POINTS_UPDATED_PAGE)),

    CLIENT(Set.of(START_PAGE,
            HOME_PAGE,
            SIGN_PAGE,
            PROFILE_PAGE,
            MENU_PAGE,
            SETTINGS_PAGE,
            SETTINGS_UPDATED_PAGE,
            PASSWORD_UPDATED_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            ERROR_500_PAGE,
            CART_PAGE,
            SUCCESS_PAGE,
            USER_REGISTERED_PAGE,
            ORDERS_PAGE,
            ORDER_STATE_CHANGED_PAGE,
            ORDER_STATE_NOT_CHANGED_PAGE,
            CONTACTS_PAGE,
            PASSWORD_PAGE,
            TOP_UP_BALANCE_PAGE,
            BALANCE_TOPPED_UP_PAGE,
            LOYAL_SCORE_BONUSES_PAGE,
            ORDER_SCORE_REPORT_PAGE,
            ORDER_SCORE_REPORT_UPDATED,
            ORDER_SCORE_REPORT_FAILED,
            ACCUMULATIVE_DISCOUNT_PAGE)),

    GUEST(Set.of(START_PAGE,
            HOME_PAGE,
            SIGN_PAGE,
            SIGNED_OUT_PAGE,
            ERROR_403_PAGE,
            ERROR_404_PAGE,
            REGISTRATION_PAGE,
            USER_REGISTERED_PAGE,
            ERROR_500_PAGE,
            CONTACTS_PAGE,
            MENU_PAGE));

    private final Set<String> userPages;

    PagePermission(Set<String> userPages) {
        this.userPages = userPages;
    }


    public Set<String> getUserPages() {
        return userPages;
    }
}