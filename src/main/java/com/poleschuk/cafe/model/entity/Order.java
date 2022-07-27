package com.poleschuk.cafe.model.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order class encapsulates order data.
 */
public class Order extends AbstractEntity {
    private long orderId;
    private LocalDateTime orderDate;
    private OrderState orderState;
    private BigDecimal totalCost;
    private BigDecimal accumulatedCash;
    private PaymentType paymentType;
    private String address;
    private String userComment;
    private long userId;

    /**
     * Instantiates a new order.
     */
    public Order() {}

    /**
     * Instantiates a new order.
     *
     * @param orderId the order id
     * @param orderDate the order date
     * @param orderState the order state
     * @param totalCost the total cost
     * @param accumulatedCash the accumulated cash
     * @param userComment the user comment
     * @param userId the user id
     */
    public Order(long orderId, LocalDateTime orderDate, OrderState orderState, BigDecimal totalCost,
    		BigDecimal accumulatedCash, String userComment, long userId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderState = orderState;
        this.totalCost = totalCost;
        this.setAccumulatedCash(accumulatedCash);
        this.userComment = userComment;
        this.userId = userId;
    }

    /**
     * Instantiates a new order.
     *
     * @param orderDate the order date
     * @param orderState the order state
     * @param totalCost the total cost
     * @param accumulatedCash the accumulated cash
     * @param userComment the user comment
     * @param userId the user id
     * @param paymentType the payment type
     * @param address the address
     */
    public Order(LocalDateTime orderDate, OrderState orderState, BigDecimal totalCost,
    		BigDecimal accumulatedCash, String userComment, long userId, PaymentType paymentType,
    		String address) {
        this.orderDate = orderDate;
        this.orderState = orderState;
        this.totalCost = totalCost;
        this.setAccumulatedCash(accumulatedCash);
        this.userComment = userComment;
        this.userId = userId;
        this.paymentType = paymentType;
        this.address = address;
    }


    /**
     * Gets the order id.
     *
     * @return the order id
     */
    public long getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	/**
	 * Gets the order date.
	 *
	 * @return the order date
	 */
	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	/**
	 * Sets the order date.
	 *
	 * @param orderDate the new order date
	 */
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * Gets the order state.
	 *
	 * @return the order state
	 */
	public OrderState getOrderState() {
		return orderState;
	}

	/**
	 * Sets the order state.
	 *
	 * @param orderState the new order state
	 */
	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}

	/**
	 * Gets the total cost.
	 *
	 * @return the total cost
	 */
	public BigDecimal getTotalCost() {
		return totalCost;
	}

	/**
	 * Sets the total cost.
	 *
	 * @param totalCost the new total cost
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * Gets the user comment.
	 *
	 * @return the user comment
	 */
	public String getUserComment() {
		return userComment;
	}

	/**
	 * Sets the user comment.
	 *
	 * @param userComment the new user comment
	 */
	public void setUserComment(String userComment) {
		this.userComment = userComment;
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
	 * Gets the payment type.
	 *
	 * @return the payment type
	 */
	public PaymentType getPaymentType() {
		return paymentType;
	}

	/**
	 * Sets the payment type.
	 *
	 * @param paymentType the new payment type
	 */
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((orderState == null) ? 0 : orderState.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((totalCost == null) ? 0 : totalCost.hashCode());
		result = prime * result + ((userComment == null) ? 0 : userComment.hashCode());
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
		Order other = (Order) obj;
		if (accumulatedCash == null) {
			if (other.accumulatedCash != null)
				return false;
		} else if (!accumulatedCash.equals(other.accumulatedCash))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (orderId != other.orderId)
			return false;
		if (orderState != other.orderState)
			return false;
		if (paymentType != other.paymentType)
			return false;
		if (totalCost == null) {
			if (other.totalCost != null)
				return false;
		} else if (!totalCost.equals(other.totalCost))
			return false;
		if (userComment == null) {
			if (other.userComment != null)
				return false;
		} else if (!userComment.equals(other.userComment))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderId=");
		builder.append(orderId);
		builder.append(", orderDate=");
		builder.append(orderDate);
		builder.append(", orderState=");
		builder.append(orderState);
		builder.append(", totalCost=");
		builder.append(totalCost);
		builder.append(", accumulatedCash=");
		builder.append(accumulatedCash);
		builder.append(", paymentType=");
		builder.append(paymentType);
		builder.append(", address=");
		builder.append(address);
		builder.append(", userComment=");
		builder.append(userComment);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}
}