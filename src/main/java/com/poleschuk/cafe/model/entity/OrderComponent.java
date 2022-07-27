package com.poleschuk.cafe.model.entity;

/**
 * OrderComponent class encapsulates menu name and its amount.
 */
public class OrderComponent extends AbstractEntity {
    private String name;
    private int amount;

    /**
     * Instantiates a new order component.
     *
     * @param name the name
     * @param amount the amount
     */
    public OrderComponent(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderComponent that = (OrderComponent) o;

        if (amount != that.amount) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderComponent{");
        sb.append("name='").append(name).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
