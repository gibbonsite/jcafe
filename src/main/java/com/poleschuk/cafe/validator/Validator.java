package com.poleschuk.cafe.validator;

import java.math.BigDecimal;
import java.util.Map;

/**
 * The Interface Validator.
 */
public interface Validator {
    
    /**
     * Checks if language is correct.
     *
     * @param language the language
     * @return true, if is correct language
     */
    boolean isCorrectLanguage(String language);

    /**
     * Check name.
     *
     * @param name the name
     * @return the boolean
     */
    boolean isCorrectName(String name);

    /**
     * Check login.
     *
     * @param login the login
     * @return the boolean
     */
    boolean isCorrectLogin(String login);

    /**
     * Check password.
     *
     * @param password the password
     * @return the boolean
     */
    boolean isCorrectPassword(String password);

    /**
     * Check email .
     *
     * @param mail the mail
     * @return the boolean
     */
    boolean isCorrectEmail(String mail);

    /**
     * Check phone number.
     *
     * @param phoneNumber the phone number
     * @return the boolean
     */
    boolean isCorrectPhoneNumber(String phoneNumber);

    /**
     * Check registration.
     *
     * @param map the map
     * @return the boolean
     */
    boolean checkRegistration(Map<String, String> map);

    /**
     * Check product data.
     *
     * @param map the map
     * @return the boolean
     */
    boolean checkProductData(Map<String, String> map);

    /**
     * Check product digit.
     *
     * @param digit the digit
     * @return the boolean
     */
    boolean isCorrectProductDigit(String digit);

    /**
     * Checks if weight is correct.
     *
     * @param weight the weight
     * @return true, if is correct weight
     */
    boolean isCorrectWeight(String weight);

    /**
     * Check product.
     *
     * @param name the name
     * @return the boolean
     */
    boolean isCorrectProductName(String name);

    /**
     * Check update profile.
     *
     * @param updateData the update data
     * @return the boolean
     */
    boolean checkUpdateProfile(Map<String, String> updateData);

    /**
     * Check address.
     *
     * @param address the address
     * @return the boolean
     */
    boolean isCorrectAddress(String address);

    /**
     * Check user comment.
     *
     * @param comment the comment
     * @return the boolean
     */
    boolean isCorrectUserComment(String comment);

    /**
     * Check order information.
     *
     * @param orderInfo the order info
     * @param totalPrice the total price
     * @param userCash the user cash
     * @return the boolean
     */
    boolean checkOrderInfo(Map<String, String> orderInfo, BigDecimal totalPrice,
    		BigDecimal userCash);

    /**
     * Check section name.
     *
     * @param sectionName the section name
     * @return the boolean
     */
    boolean isCorrectSectionName(String sectionName);

    /**
     * Check date.
     *
     * @param date the date
     * @return the boolean
     */
    boolean isCorrectDate(String date);

    /**
     * Checks if loyal score is correct.
     *
     * @param loyalScore the loyal score
     * @return true, if is correct loyal score
     */
    boolean isCorrectLoyalScore(String loyalScore);

    /**
     * Checks if description is correct.
     *
     * @param description the description
     * @return true, if is correct description
     */
    boolean isCorrectDescription(String description);

    /**
     * Check topping up balance.
     *
     * @param amount the amount
     * @return true, if successful
     */
    boolean checkToppingUpBalance(BigDecimal amount);

    /**
     * Check loyal score decreasing.
     *
     * @param amount the amount
     * @return true, if successful
     */
    boolean checkLoyalScoreDecreasing(BigDecimal amount);
    
    /**
     * Checks if order score is correct.
     *
     * @param score the score
     * @return true, if is correct order score
     */
    boolean isCorrectOrderScore(String score);
    
    /**
     * Checks if order report is correct.
     *
     * @param score the score
     * @return true, if is correct order report
     */
    boolean isCorrectOrderReport(String score);
}
