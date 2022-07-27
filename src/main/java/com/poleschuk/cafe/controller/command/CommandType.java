package com.poleschuk.cafe.controller.command;

import com.poleschuk.cafe.controller.command.impl.ChangeLanguageCommand;
import com.poleschuk.cafe.controller.command.impl.ChangePasswordCommand;
import com.poleschuk.cafe.controller.command.impl.DefaultCommand;
import com.poleschuk.cafe.controller.command.impl.FindAllMenuBySectionCommand;
import com.poleschuk.cafe.controller.command.impl.FindAllMenuCommand;
import com.poleschuk.cafe.controller.command.impl.GoToSettingsCommand;
import com.poleschuk.cafe.controller.command.impl.RegistrationCommand;
import com.poleschuk.cafe.controller.command.impl.SignInCommand;
import com.poleschuk.cafe.controller.command.impl.SignOutCommand;
import com.poleschuk.cafe.controller.command.impl.SortAllMenuByPopularityCommand;
import com.poleschuk.cafe.controller.command.impl.SortAllMenuByPriceCommand;
import com.poleschuk.cafe.controller.command.impl.UpdateUserProfileCommand;
import com.poleschuk.cafe.controller.command.impl.admin.BlockUserByIdCommand;
import com.poleschuk.cafe.controller.command.impl.admin.ChangeOrderStateCommand;
import com.poleschuk.cafe.controller.command.impl.admin.DeleteAdminCommand;
import com.poleschuk.cafe.controller.command.impl.admin.DeleteOrdersCommand;
import com.poleschuk.cafe.controller.command.impl.admin.DeleteProductCommand;
import com.poleschuk.cafe.controller.command.impl.admin.DeleteSectionCommand;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllAdminsCommand;
import com.poleschuk.cafe.controller.command.impl.admin.FindCancelledOrdersCommand;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllClientsCommand;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllDeletedProductsCommand;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllDeletedSectionsCommand;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllOrdersCommand;
import com.poleschuk.cafe.controller.command.impl.admin.GoToUpdateProductPageCommand;
import com.poleschuk.cafe.controller.command.impl.admin.InsertNewProductCommand;
import com.poleschuk.cafe.controller.command.impl.admin.InsertNewSectionCommand;
import com.poleschuk.cafe.controller.command.impl.admin.RestoreMenuProductCommand;
import com.poleschuk.cafe.controller.command.impl.admin.RestoreSectionCommand;
import com.poleschuk.cafe.controller.command.impl.admin.UnblockUserByIdCommand;
import com.poleschuk.cafe.controller.command.impl.admin.UpdateLoyalScoreCommand;
import com.poleschuk.cafe.controller.command.impl.admin.UpdateProductCommand;
import com.poleschuk.cafe.controller.command.impl.admin.UpdateSectionNameCommand;
import com.poleschuk.cafe.controller.command.impl.admin.UploadProductPhotoCommand;
import com.poleschuk.cafe.controller.command.impl.client.AddProductToCartCommand;
import com.poleschuk.cafe.controller.command.impl.client.CreateOrderCommand;
import com.poleschuk.cafe.controller.command.impl.client.DeleteProductInCartCommand;
import com.poleschuk.cafe.controller.command.impl.client.FindAllLoyalScoreBonusesCommand;
import com.poleschuk.cafe.controller.command.impl.client.GoToAccumulativeDiscountPageCommand;
import com.poleschuk.cafe.controller.command.impl.client.GoToCartPageCommand;
import com.poleschuk.cafe.controller.command.impl.client.GoToOrderScoreReportPageCommand;
import com.poleschuk.cafe.controller.command.impl.client.GoToOrdersPageCommand;
import com.poleschuk.cafe.controller.command.impl.client.TopUpBalanceCommand;
import com.poleschuk.cafe.controller.command.impl.client.UpdateOrderScoreReportCommand;

public enum CommandType {
	SIGN_IN(new SignInCommand()),
	SIGN_OUT(new SignOutCommand()),
	CHANGE_LANGUAGE(new ChangeLanguageCommand()),
	REGISTRATION(new RegistrationCommand()),
	GO_TO_SETTINGS(new GoToSettingsCommand()),
	CHANGE_PASSWORD(new ChangePasswordCommand()),
	FIND_ALL_CLIENTS(new FindAllClientsCommand()),
	FIND_ALL_ADMINS(new FindAllAdminsCommand()),
	GO_TO_CART_PAGE(new GoToCartPageCommand()),
	CREATE_ORDER(new CreateOrderCommand()),
	GO_TO_ORDERS_PAGE(new GoToOrdersPageCommand()),
	DELETE_PRODUCT_IN_CART(new DeleteProductInCartCommand()),
	ADD_PRODUCT_TO_CART(new AddProductToCartCommand()),
	FIND_ALL_MENU_BY_SECTION(new FindAllMenuBySectionCommand()),
	FIND_ALL_MENU(new FindAllMenuCommand()),
	SORT_ALL_MENU_BY_PRICE(new SortAllMenuByPriceCommand()),
	SORT_ALL_MENU_BY_POPULARITY(new SortAllMenuByPopularityCommand()),
	UPDATE_USER_PROFILE(new UpdateUserProfileCommand()),
	BLOCK_USER_BY_ID(new BlockUserByIdCommand()),
	CHANGE_ORDER_STATE(new ChangeOrderStateCommand()),
	DELETE_ADMIN(new DeleteAdminCommand()),
	DELETE_ORDERS(new DeleteOrdersCommand()),
	DELETE_PRODUCT(new DeleteProductCommand()),
	DELETE_SECTION(new DeleteSectionCommand()),
	FIND_ALL_DELETED_PRODUCTS(new FindAllDeletedProductsCommand()),
	FIND_ALL_DELETED_SECTIONS(new FindAllDeletedSectionsCommand()),
	FIND_ALL_ORDERS(new FindAllOrdersCommand()),
	GO_TO_UPDATE_PRODUCT_PAGE(new GoToUpdateProductPageCommand()),
	INSERT_NEW_PRODUCT(new InsertNewProductCommand()),
	INSERT_NEW_SECTION(new InsertNewSectionCommand()),
	UPDATE_SECTION_NAME(new UpdateSectionNameCommand()),
	RESTORE_MENU_PRODUCT(new RestoreMenuProductCommand()),
	RESTORE_SECTION(new RestoreSectionCommand()),
	UNBLOCK_USER_BY_ID(new UnblockUserByIdCommand()),
	UPDATE_PRODUCT(new UpdateProductCommand()),
	UPDATE_SECTION(new UpdateSectionNameCommand()),
	UPLOAD_PRODUCT_PHOTO(new UploadProductPhotoCommand()),
	TOP_UP_BALANCE(new TopUpBalanceCommand()),
	UPDATE_LOYAL_SCORE(new UpdateLoyalScoreCommand()),
	FIND_CANCELLED_ORDERS(new FindCancelledOrdersCommand()),
	FIND_ALL_LOYAL_SCORE_BONUSES(new FindAllLoyalScoreBonusesCommand()),
	GO_TO_ORDER_SCORE_REPORT_PAGE(new GoToOrderScoreReportPageCommand()),
	UPDATE_ORDER_SCORE_REPORT(new UpdateOrderScoreReportCommand()),
	GO_TO_ACCUMULATIVE_DISCOUNT_PAGE(new GoToAccumulativeDiscountPageCommand()),
	DEFAULT(new DefaultCommand());
	
	Command command;
	
	private CommandType(Command command) {
		this.command = command;
	}
	
	public static Command define(String commandStr) {
		try {
			CommandType current = CommandType.valueOf(commandStr.toUpperCase());
			return current.command;
		} catch (IllegalArgumentException | NullPointerException e) {
			return DEFAULT.command;
		}
	}
}
