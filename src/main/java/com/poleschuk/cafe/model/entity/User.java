package com.poleschuk.cafe.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The Class User.
 */
public class User extends AbstractEntity {

    private long userId;
    
    private String firstName;
    
    private String lastName;
    
    private String login;
    
    private String password;
    
    private String email;
    
    private int phoneNumber;
    
    private LocalDate birthday;
    
    private BigDecimal cash;
	
	private BigDecimal loyalScore;
	
	private BigDecimal accumulatedCash;
    
    private UserRole role;
    
    private UserState state;

    /**
     * Instantiates a new user.
     */
    public User() {}

    /**
     * Instantiates a new user.
     *
     * @param userId the user id
     * @param firstName the first name
     * @param lastName the last name
     * @param login the login
     * @param password the password
     * @param email the email
     * @param phoneNumber the phone number
     * @param birthday the birthday
     * @param cash the cash
     * @param loyalScore the loyal score bonus
     * @param role the role
     * @param state the state
     */
    public User(long userId, String firstName, String lastName, String login,
                String password, String email, int phoneNumber, LocalDate birthday, BigDecimal cash,
                BigDecimal loyalScore, BigDecimal accumululatedCash, UserRole role, UserState state) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.cash = cash;
        this.loyalScore = loyalScore;
        this.accumulatedCash = accumululatedCash;
        this.role = role;
        this.state = state;
    }

    /**
     * Instantiates a new user.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @param login the login
     * @param password the password
     * @param email the email
     * @param phoneNumber the phone number
     * @param birthday the birthday
     * @param cash the cash
     * @param loyalScore the loyal score bonus
     * @param role the role
     * @param state the state
     */
    public User(String firstName, String lastName, String login,
                String password, String email, int phoneNumber, LocalDate birthday, BigDecimal cash,
                BigDecimal loyalScore, BigDecimal accumululatedCash, UserRole role, UserState state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.cash = cash;
        this.loyalScore = loyalScore;
        this.accumulatedCash = accumululatedCash;
        this.role = role;
        this.state = state;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     *
     * @param login the new login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the birthday.
     *
     * @return the birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday.
     *
     * @param birthday the new birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets the cash.
     *
     * @return the cash
     */
    public BigDecimal getCash() {
		return cash;
	}

	/**
	 * Sets the cash.
	 *
	 * @param cash the new cash
	 */
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	
    /**
     * Gets the role.
     *
     * @return the role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public UserState getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(UserState state) {
        this.state = state;
    }

    
    /**
     * Gets the loyal score bonus.
     *
     * @return the loyal score bonus
     */
    public BigDecimal getLoyalScore() {
		return loyalScore;
	}

	/**
	 * Sets the loyal score bonus.
	 *
	 * @param loyalScoreBonus the new loyal score bonus
	 */
	public void setLoyalScore(BigDecimal loyalScoreBonus) {
		this.loyalScore = loyalScoreBonus;
	}

	
	/**
	 * Gets the accumulated cash.
	 *
	 * @return the accumulated cash
	 */
	public BigDecimal getAccumulatedCash() {
		return accumulatedCash;
	}

	/**
	 * Sets the accumulated cash.
	 *
	 * @param accumulatedCash the new accumulated cash
	 */
	public void setAccumulatedCash(BigDecimal accumulatedCash) {
		this.accumulatedCash = accumulatedCash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accumulatedCash == null) ? 0 : accumulatedCash.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((cash == null) ? 0 : cash.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((loyalScore == null) ? 0 : loyalScore.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + phoneNumber;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accumulatedCash == null) {
			if (other.accumulatedCash != null)
				return false;
		} else if (!accumulatedCash.equals(other.accumulatedCash))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (cash == null) {
			if (other.cash != null)
				return false;
		} else if (!cash.equals(other.cash))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (loyalScore == null) {
			if (other.loyalScore != null)
				return false;
		} else if (!loyalScore.equals(other.loyalScore))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber != other.phoneNumber)
			return false;
		if (role != other.role)
			return false;
		if (state != other.state)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userId=");
		builder.append(userId);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", login=");
		builder.append(login);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append(", cash=");
		builder.append(cash);
		builder.append(", loyalScore=");
		builder.append(loyalScore);
		builder.append(", accumulatedCash=");
		builder.append(accumulatedCash);
		builder.append(", role=");
		builder.append(role);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}


}
