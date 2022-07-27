package com.poleschuk.cafe.model.entity;

import java.util.List;

/**
 * CompleteOrder class encapsulates user, order info and menu list.
 */
public class CompleteOrder extends AbstractEntity {
    private User user;
    private Order order;
    private List<OrderComponent> menuList;

    /**
     * Instantiates a new complete order.
     *
     * @param user the user
     * @param order the order
     * @param menuList the menu list
     */
    public CompleteOrder(User user, Order order, List<OrderComponent> menuList) {
        this.user = user;
        this.order = order;
        this.menuList = menuList;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

    /**
     * Gets the order.
     *
     * @return the order
     */
    public Order getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * Gets the menu list.
	 *
	 * @return the menu list
	 */
	public List<OrderComponent> getMenuList() {
		return menuList;
	}

	/**
	 * Sets the menu list.
	 *
	 * @param menuList the new menu list
	 */
	public void setMenuList(List<OrderComponent> menuList) {
		this.menuList = menuList;
	}


	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompleteOrder that = (CompleteOrder) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return menuList != null ? menuList.equals(that.menuList) : that.menuList == null;
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (menuList != null ? menuList.hashCode() : 0);
        return result;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompleteOrder{");
        sb.append("user=").append(user);
        sb.append(", order=").append(order);
        sb.append(", menuList=").append(menuList);
        sb.append('}');
        return sb.toString();
    }
}
