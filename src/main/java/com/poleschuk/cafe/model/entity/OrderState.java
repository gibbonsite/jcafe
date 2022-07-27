package com.poleschuk.cafe.model.entity;

/**
 * OrderState enum represents different states of an order.
 */
public enum OrderState {

    /** The processing. */
    PROCESSING("processing"),

    /** The cancelled. */
    CANCELLED("cancelled"),

    /** The received. */
    RECEIVED("received"),

    /** The completed. */
    COMPLETED("completed"),

    /** Cancelled and finished. */
    CANCELLED_FINISHED("cancelled_finished");
 
    String state;

    /**
     * Instantiates a new order state.
     *
     * @param state the state
     */
    OrderState(String state) {
        this.state = state;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }
}
