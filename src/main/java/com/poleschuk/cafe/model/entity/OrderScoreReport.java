package com.poleschuk.cafe.model.entity;

/**
 * The class OrderScoreReport contains order score and report.
 */
public class OrderScoreReport extends AbstractEntity{

	private long orderId;
	private String report;
	private int score;
	
	/**
	 * Instantiates a new order score and report.
	 */
	public OrderScoreReport() {
	}
	
	/**
	 * Instantiates a new order score and report.
	 *
	 * @param orderId the order id
	 * @param report the report
	 * @param score the score
	 */
	public OrderScoreReport(long orderId, String report, int score) {
		this.orderId = orderId;
		this.report = report;
		this.score = score;
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
	 * Gets the report.
	 *
	 * @return the report
	 */
	public String getReport() {
		return report;
	}
	
	/**
	 * Sets the report.
	 *
	 * @param report the new report
	 */
	public void setReport(String report) {
		this.report = report;
	}
	
	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Sets the score.
	 *
	 * @param score the new score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((report == null) ? 0 : report.hashCode());
		result = prime * result + score;
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
		OrderScoreReport other = (OrderScoreReport) obj;
		if (orderId != other.orderId)
			return false;
		if (report == null) {
			if (other.report != null)
				return false;
		} else if (!report.equals(other.report))
			return false;
		if (score != other.score)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderScoreReport [orderId=");
		builder.append(orderId);
		builder.append(", report=");
		builder.append(report);
		builder.append(", score=");
		builder.append(score);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
