package com.poleschuk.cafe.validator.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import com.poleschuk.cafe.model.entity.OrderDiscount;
import com.poleschuk.cafe.model.entity.PaymentType;
import com.poleschuk.cafe.validator.Validator;

import static com.poleschuk.cafe.controller.Parameter.*;

/**
 * ValidatorImpl class implements Validator interface
 * and consists of validation methods.
 */
public class ValidatorImpl implements Validator {
    private static final String NAME_PATTERN = "^[A-Za-zА-Яа-я]{3,50}$";
    private static final String USER_LOGIN_PATTERN = "^[A-Za-zА-Яа-я0-9_]{4,16}$";
    private static final String USER_PASSWORD_PATTERN = "^[A-Za-zА-Яа-я0-9\\.]{5,40}$";
    private static final String USER_MAIL_PATTERN = "^[A-Za-z0-9\\.]{1,30}@[a-z]{2,7}\\.[a-z]{2,4}$";
    private static final String USER_PHONE_NUMBER_PATTERN = "(29|33|25|44)\\d{7}";
    private static final String USER_COMMENT_PATTERN = "^.{0,200}$";
    private static final String ORDER_REPORT_PATTERN = "^.{0,900}$";
    private static final String ADDRESS_PATTERN = "^.{4,200}$";
    private static final String PRODUCT_NAME_PATTERN = "^[A-Za-zА-Яа-я0-9\\s-,]{3,50}$";
    private static final String DIGIT_PRODUCT_PATTERN = "\\d{1,6}(\\.[0-9]{1,2})?";
    private static final String WEIGHT_PATTERN = "\\d{1,6}";
    private static final String DESCRIPTION_PATTERN = "^.{0,200}$";
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final String SECTION_NAME_PATTERN = "^.{1,20}$";
    private static final String ENGLISH_LANGUAGE = "en_US";
    private static final String RUSSIAN_LANGUAGE = "ru_RU";

    private static final ValidatorImpl instance = new ValidatorImpl();

    private ValidatorImpl() {}

    /**
     * Get instance of the validator.
     *
     * @return the validator
     */
    public static ValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isCorrectLanguage(String language) {
        return language != null && (language.equals(ENGLISH_LANGUAGE) || language.equals(RUSSIAN_LANGUAGE));
    }

    @Override
    public boolean isCorrectName(String name) {
        return isNotNullOrEmpty(name) && name.matches(NAME_PATTERN);
    }

    @Override
    public boolean isCorrectLogin(String login) {
        return isNotNullOrEmpty(login) && login.matches(USER_LOGIN_PATTERN);
    }

    @Override
    public boolean isCorrectPassword(String password) {
        return isNotNullOrEmpty(password) && password.matches(USER_PASSWORD_PATTERN);
    }

    @Override
    public boolean isCorrectEmail(String mail) {
        return isNotNullOrEmpty(mail) && mail.matches(USER_MAIL_PATTERN);
    }

    @Override
    public boolean isCorrectPhoneNumber(String phoneNumber) {
        return isNotNullOrEmpty(phoneNumber) && phoneNumber.matches(USER_PHONE_NUMBER_PATTERN);
    }

    @Override
    public boolean isCorrectDate(String date) {
        return isNotNullOrEmpty(date) && date.matches(DATE_PATTERN);
    }

    @Override
    public boolean isCorrectUserComment(String comment) {
        return comment != null && comment.matches(USER_COMMENT_PATTERN);
    }
    
    @Override
    public boolean isCorrectAddress(String address) {
        return isNotNullOrEmpty(address) && address.matches(ADDRESS_PATTERN);
    }

    @Override
    public boolean isCorrectProductDigit(String digit) {
        return isNotNullOrEmpty(digit) && digit.matches(DIGIT_PRODUCT_PATTERN);
    }

    @Override
    public boolean isCorrectLoyalScore(String loyalScore) {
        return isNotNullOrEmpty(loyalScore) && loyalScore.matches(DIGIT_PRODUCT_PATTERN);
    }

    @Override
    public boolean isCorrectWeight(String weight) {
        return isNotNullOrEmpty(weight) && weight.matches(WEIGHT_PATTERN);
    }

    @Override
    public boolean isCorrectProductName(String name) {
        return isNotNullOrEmpty(name) && name.matches(PRODUCT_NAME_PATTERN);
    }

    @Override
    public boolean isCorrectDescription(String description) {
        return description != null && description.matches(DESCRIPTION_PATTERN);
    }

    @Override
    public boolean isCorrectSectionName(String sectionName) {
        return isNotNullOrEmpty(sectionName) && sectionName.matches(SECTION_NAME_PATTERN);
    }


    @Override
    public boolean checkRegistration(Map<String, String> map) {
        boolean result = true;
        String firstName = map.get(USER_FIRST_NAME);
        String lastName = map.get(USER_LAST_NAME);
        String login = map.get(LOGIN);
        String password = map.get(PASSWORD);
        String email = map.get(USER_EMAIL);
        String phone = map.get(USER_PHONE_NUMBER);
        String birthday = map.get(USER_BIRTHDAY);
        if (!isCorrectName(firstName)) {
            map.put(USER_FIRST_NAME, INVALID_FIRST_NAME);
            result = false;
        }
        if (!isCorrectName(lastName)) {
            map.put(USER_LAST_NAME, INVALID_LAST_NAME);
            result = false;
        }
        if (!isCorrectLogin(login)) {
            map.put(LOGIN, INVALID_LOGIN);
            result = false;
        }
        if (!isCorrectPassword(password)) {
            map.put(PASSWORD, INVALID_PASSWORD);
            result = false;
        }
        if (!isCorrectEmail(email)) {
            map.put(USER_EMAIL, INVALID_EMAIL);
            result = false;
        }
        if (!isCorrectPhoneNumber(phone)) {
            map.put(USER_PHONE_NUMBER, INVALID_PHONE_NUMBER);
            result = false;
        }
        if (!isCorrectDate(birthday)) {
            map.put(USER_BIRTHDAY, INVALID_BIRTHDAY);
            result = false;
        }
        return result;
    }
    
    @Override
    public boolean checkOrderInfo(Map<String, String> orderInfo,
    		BigDecimal totalPrice, BigDecimal userCash) {
        boolean result = true;
        String address = orderInfo.get(ADDRESS);
        if (!isCorrectAddress(address)) {
            orderInfo.put(ADDRESS, INVALID_ORDER_ADDRESS);
            result = false;
        }
        try {
        	PaymentType.valueOf(orderInfo.get(PRODUCT_PAYMENT));
        } catch (IllegalArgumentException | NullPointerException e) {
            orderInfo.put(PRODUCT_PAYMENT, INVALID_ORDER_PAYMENT);
        	result = false;
        }
        try {
        	OrderDiscount.valueOf(orderInfo.get(ORDER_DISCOUNT));
        } catch (IllegalArgumentException | NullPointerException e) {
            orderInfo.put(ORDER_DISCOUNT, INVALID_ORDER_DISCOUNT);
        	result = false;
        }
        String comment = orderInfo.get(USER_COMMENT);
        if (!isCorrectUserComment(comment)) {
            orderInfo.put(USER_COMMENT, INVALID_ORDER_COMMENT);
            result = false;
        }
        boolean orderDateResult = true;
        try {
	        LocalDateTime orderDate = LocalDateTime.parse(orderInfo.get(ORDER_DATE));
	        if (!orderDate.isAfter(LocalDateTime.now())) {
	        	orderDateResult = false;
	        }
        } catch (DateTimeParseException | NullPointerException e) {
        	orderDateResult = false;
        }
        if (!orderDateResult) {
            orderInfo.put(ORDER_DATE, INVALID_ORDER_DATE);
        }
        result = result && orderDateResult;

    	if (userCash.compareTo(totalPrice) == -1) {
    		orderInfo.put(USER_CASH, OUT_OF_CASH);
    		result = false;
    	}

        return result;
    }

    @Override
    public boolean isCorrectOrderScore(String score) {
    	boolean result = false;
	    try {
	    	Integer integerScore = Integer.parseInt(score);
	    	result = integerScore >= 0 && integerScore <= 10;
	    } catch (NumberFormatException e) {
	    	result = false;
	    }
	    return result;
    }

    @Override
    public boolean isCorrectOrderReport(String report) {
    	return report != null && report.matches(ORDER_REPORT_PATTERN);
    }

    
    @Override
    public boolean checkProductData(Map<String, String> map) {
        boolean result = true;
        String name = map.get(PRODUCT_NAME);
        String weight = map.get(PRODUCT_WEIGHT);
        String loyalScore = map.get(PRODUCT_LOYAL_SCORE);
        String price = map.get(PRODUCT_PRICE);
        String description = map.get(PRODUCT_DESCRIPTION);
        String section = map.get(PRODUCT_SECTION);
        if (!isCorrectProductName(name)) {
            map.put(PRODUCT_NAME, INVALID_PRODUCT_NAME);
            result = false;
        }
        if (!isCorrectWeight(weight)) {
            map.put(PRODUCT_WEIGHT, INVALID_PRODUCT_WEIGHT);
            result = false;
        }

        if (!isCorrectProductDigit(price)) {
            map.put(PRODUCT_PRICE, INVALID_PRODUCT_PRICE);
            result = false;
        }
        if (!isCorrectLoyalScore(loyalScore)) {
            map.put(PRODUCT_LOYAL_SCORE, INVALID_PRODUCT_LOYAL_SCORE);
            result = false;
        }
        if (!isCorrectDescription(description)) {
            map.put(PRODUCT_DESCRIPTION, INVALID_PRODUCT_DESCRIPTION);
            result = false;
        }
        if (!isNotNullOrEmpty(section)) {
            map.put(PRODUCT_SECTION, INVALID_PRODUCT_SECTION);
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkUpdateProfile(Map<String, String> updateData) {
        boolean result = true;
        String firstName = updateData.get(USER_FIRST_NAME);
        String lastName = updateData.get(USER_LAST_NAME);
        String email = updateData.get(USER_EMAIL);
        String phone = updateData.get(USER_PHONE_NUMBER);
        String birthday = updateData.get(USER_BIRTHDAY);

        if (!isCorrectName(firstName)) {
            updateData.put(USER_FIRST_NAME, INVALID_FIRST_NAME);
            result = false;
        }
        if (!isCorrectName(lastName)) {
            updateData.put(USER_LAST_NAME, INVALID_LAST_NAME);
            result = false;
        }
        if (!isCorrectEmail(email)) {
            updateData.put(USER_EMAIL, INVALID_EMAIL);
            result = false;
        }
        if (!isCorrectPhoneNumber(phone)) {
            updateData.put(USER_PHONE_NUMBER, INVALID_PHONE_NUMBER);
            result = false;
        }
        if (!isCorrectDate(birthday)) {
            updateData.put(USER_BIRTHDAY, INVALID_BIRTHDAY);
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkToppingUpBalance(BigDecimal amount) {
    	return amount != null && amount.compareTo(BigDecimal.ZERO) != -1;
    }
    
    @Override
    public boolean checkLoyalScoreDecreasing(BigDecimal amount) {
    	return amount != null && amount.compareTo(BigDecimal.ZERO) == -1;
    }

    private boolean isNotNullOrEmpty(String line) {
        return line != null && !line.isEmpty();
    }

}
