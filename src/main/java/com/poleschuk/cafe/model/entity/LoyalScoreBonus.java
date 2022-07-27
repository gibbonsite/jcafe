package com.poleschuk.cafe.model.entity;

import java.math.BigDecimal;

/**
 * LoyalScoreBonus class represents bonus based on user loyal score.
 */
public class LoyalScoreBonus extends AbstractEntity {

	private BigDecimal loyalScore;
	private BigDecimal discount;
	
	/**
	 * Instantiate LoyalScoreBonus with loyal score and discount.
	 * @param loyalScore   the loyalScore
	 * @param discount     the discount
	 */
	public LoyalScoreBonus(BigDecimal loyalScore, BigDecimal discount) {
		this.loyalScore = loyalScore;
		this.discount = discount;
	}

	/**
	 * Instantiate LoyalScoreBonus.
	 */ 
	public LoyalScoreBonus() {
	}

	/**
	 * Gets the loyal score.
	 *
	 * @return the loyal score
	 */
	public BigDecimal getLoyalScore() {
		return loyalScore;
	}
	
	/**
	 * Sets the loyal score.
	 *
	 * @param loyalScore the new loyal score
	 */
	public void setLoyalScore(BigDecimal loyalScore) {
		this.loyalScore = loyalScore;
	}
	
	/**
	 * Gets the discount.
	 *
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	
	/**
	 * Sets the discount.
	 *
	 * @param discount the new discount
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((loyalScore == null) ? 0 : loyalScore.hashCode());
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
		LoyalScoreBonus other = (LoyalScoreBonus) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (loyalScore == null) {
			if (other.loyalScore != null)
				return false;
		} else if (!loyalScore.equals(other.loyalScore))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoyalScoreBonus [loyalScore=");
		builder.append(loyalScore);
		builder.append(", discount=");
		builder.append(discount);
		builder.append("]");
		return builder.toString();
	}
}
