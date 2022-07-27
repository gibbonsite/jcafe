package com.poleschuk.cafe.model.entity;

import java.math.BigDecimal;

/**
 * Menu class represents information about a menu item.
 */
public class Menu extends AbstractEntity {
    
    private long menuId;
    private String name;
    private String picturePath;
    private String description;
    private int weight;
    private BigDecimal loyalScore;
    private BigDecimal price;
    private long sectionId;
    private boolean enabled;

    /**
     * Instantiates a new menu.
     */
    public Menu() {}

    /**
     * Instantiates a new menu.
     *
     * @param menuId the menu id
     * @param name the name
     * @param picturePath the picture path
     * @param description the description
     * @param weight the weight
     * @param loyalScore the loyal score
     * @param price the price
     * @param sectionId the section id
     * @param enabled the enabled
     */
    public Menu(long menuId, String name, String picturePath, String description,
                int weight, BigDecimal loyalScore, BigDecimal price, long sectionId,
                boolean enabled) {
        this.menuId = menuId;
        this.name = name;
        this.picturePath = picturePath;
        this.description = description;
        this.weight = weight;
        this.loyalScore = loyalScore;
        this.price = price;
        this.sectionId = sectionId;
        this.enabled = enabled;
    }

    /**
     * Instantiates a new menu.
     *
     * @param name the name
     * @param picturePath the picture path
     * @param description the description
     * @param weight the weight
     * @param loyalScore the loyal score
     * @param price the price
     * @param sectionId the section id
     * @param enabled the enabled
     */
    public Menu(String name, String picturePath, String description,
            int weight, BigDecimal loyalScore, BigDecimal price, long sectionId,
            boolean enabled) {

        this.name = name;
        this.picturePath = picturePath;
        this.description = description;
        this.weight = weight;
        this.loyalScore = loyalScore;
        this.price = price;
        this.sectionId = sectionId;
        this.enabled = enabled;
    }

    /**
     * Instantiates a new menu.
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param weight the weight
     * @param loyalScore the loyal score
     * @param price the price
     * @param sectionId the section id
     * @param enabled the enabled
     */
    public Menu(long id, String name, String description,
            int weight, BigDecimal loyalScore, BigDecimal price, long sectionId,
            boolean enabled) {
    	this.menuId = id;
        this.name = name;
        this.picturePath = picturePath;
        this.description = description;
        this.weight = weight;
        this.loyalScore = loyalScore;
        this.price = price;
        this.sectionId = sectionId;
        this.enabled = enabled;
    }


    /**
     * Gets the menu id.
     *
     * @return the menu id
     */
    public long getMenuId() {
		return menuId;
	}

	/**
	 * Sets the menu id.
	 *
	 * @param menuId the new menu id
	 */
	public void setMenuId(long menuId) {
		this.menuId = menuId;
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
	 * Gets the picture path.
	 *
	 * @return the picture path
	 */
	public String getPicturePath() {
		return picturePath;
	}

	/**
	 * Sets the picture path.
	 *
	 * @param picturePath the new picture path
	 */
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
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
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Gets the section id.
	 *
	 * @return the section id
	 */
	public long getSectionId() {
		return sectionId;
	}

	/**
	 * Sets the section id.
	 *
	 * @param sectionId the new section id
	 */
	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (menuId != menu.menuId) return false;
        if (Double.compare(menu.weight, weight) != 0) return false;
        if (sectionId != menu.sectionId) return false;
        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
        if (picturePath != null ? !picturePath.equals(menu.picturePath) : menu.picturePath != null) return false;
        if (description != null ? !description.equals(menu.description) : menu.description != null) return false;
        return price != null ? price.equals(menu.price) : menu.price == null;
    }

    @Override
    public int hashCode() {
        int result;
        result = (int) (menuId ^ (menuId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + weight;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (int) (sectionId ^ (sectionId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("menuId=").append(menuId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", picturePath='").append(picturePath).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", weight=").append(weight);
        sb.append(", loyalScore=").append(loyalScore);
        sb.append(", price=").append(price);
        sb.append(", sectionId=").append(sectionId);
        sb.append('}');
        return sb.toString();
    }
}
