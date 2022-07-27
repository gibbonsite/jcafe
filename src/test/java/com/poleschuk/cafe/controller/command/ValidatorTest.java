package com.poleschuk.cafe.controller.command;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;

import org.testng.annotations.Test;

import com.poleschuk.cafe.validator.Validator;
import com.poleschuk.cafe.validator.impl.ValidatorImpl;

public class ValidatorTest {

	private static final Validator VALIDATOR = ValidatorImpl.getInstance();
	private static final String GOOD_LOGIN = "good_login";
	private static final String BAD_LOGIN = "log";
	private static final String EMAIL = "m@m.ru";
	private static final String PASSWORD = "pw";
	private static final String PHONE_NUMBER = "172206745";

    @Test
    public void isCorrectGoodLoginTest() {
    	assertTrue(VALIDATOR.isCorrectLogin(GOOD_LOGIN));
    }

    @Test
	public void isCorrectEmailTest() {
    	assertFalse(VALIDATOR.isCorrectEmail(EMAIL));
    }

    @Test
    public void checkLoyalScoreDecreasingTest() {
    	assertFalse(VALIDATOR.checkLoyalScoreDecreasing(BigDecimal.valueOf(100.0)));
    }

    @Test
    public void isCorrectBadLoginTest() {
    	assertFalse(VALIDATOR.isCorrectLogin(BAD_LOGIN));
    }

    @Test
    public void isCorrectPasswordTest() {
    	assertFalse(VALIDATOR.isCorrectPassword(PASSWORD));
    }

    @Test
    public void checkToppingUpBalanceTest() {
    	assertFalse(VALIDATOR.checkToppingUpBalance(BigDecimal.valueOf(-50)));
    }

    @Test
    public void isCorrectPhoneNumberTest() {
    	assertFalse(VALIDATOR.isCorrectPhoneNumber(PHONE_NUMBER));
    }
}
