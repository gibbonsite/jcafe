package com.poleschuk.cafe.model.mapper.impl;

import static com.poleschuk.cafe.model.mapper.ColumnNames.ORDER_SCORE_REPORT_ORDER_ID;
import static com.poleschuk.cafe.model.mapper.ColumnNames.REPORT;
import static com.poleschuk.cafe.model.mapper.ColumnNames.SCORE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.model.entity.OrderScoreReport;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;

/**
 * OrderScoreReportMapper class extracts ResultSet row into OrderScoreReport object.
 */
public class OrderScoreReportMapper implements EntityRowMapper<OrderScoreReport> {
    private static final Logger logger = LogManager.getLogger();
    private static final EntityRowMapper<OrderScoreReport> instance = new OrderScoreReportMapper();


	public static EntityRowMapper<OrderScoreReport> getInstance() {
		return instance;
	}

	private OrderScoreReportMapper() {
	}
	
    @Override
    public Optional<OrderScoreReport> mapRow(ResultSet resultSet) {
    	OrderScoreReport orderScoreReport = new OrderScoreReport();
        Optional<OrderScoreReport> optionalOrderScoreReport;
        try {
        	orderScoreReport.setOrderId(resultSet.getLong(ORDER_SCORE_REPORT_ORDER_ID));
        	orderScoreReport.setReport(resultSet.getString(REPORT));
        	orderScoreReport.setScore(resultSet.getInt(SCORE));
            
            optionalOrderScoreReport = Optional.of(orderScoreReport);
        } catch (SQLException e) {
            optionalOrderScoreReport = Optional.empty();
        }
        return optionalOrderScoreReport;
    }
}