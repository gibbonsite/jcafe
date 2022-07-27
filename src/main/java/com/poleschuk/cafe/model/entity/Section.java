package com.poleschuk.cafe.model.entity;

/**
 * Section class represents information about a section.
 */
public class Section extends AbstractEntity {
    private long sectionId;
    private String sectionName;
    private boolean enabled;

    /**
     * Instantiates a new section.
     */
    public Section() {
    	
    }
    
    /**
     * Instantiates a new section.
     *
     * @param sectionId the section id
     * @param sectionName the section name
     * @param enabled the enabled
     */
    public Section(long sectionId, String sectionName, boolean enabled) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.enabled = enabled;
    }

    /**
     * Instantiates a new section.
     *
     * @param sectionName the section name
     * @param enabled the enabled
     */
    public Section(String sectionName, boolean enabled) {
        this.sectionName = sectionName;
        this.enabled = enabled;
    }

    /**
     * Instantiates a new section.
     *
     * @param sectionName the section name
     */
    public Section(String sectionName) {
        this.sectionName = sectionName;
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
	 * Gets the section name.
	 *
	 * @return the section name
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * Sets the section name.
	 *
	 * @param sectionName the new section name
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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

        Section section = (Section) o;

        if (sectionId != section.sectionId) return false;
        return sectionName != null ? sectionName.equals(section.sectionName) : section.sectionName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (sectionId ^ (sectionId >>> 32));
        result = 31 * result + (sectionName != null ? sectionName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Section{");
        sb.append("sectionId=").append(sectionId);
        sb.append(", sectionName='").append(sectionName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}