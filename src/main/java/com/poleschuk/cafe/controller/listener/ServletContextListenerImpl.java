package com.poleschuk.cafe.controller.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.model.pool.ConnectionPool;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * ServletContextListenerImpl class implements ServletContextListener interface
 * and handles connection pool initialization and destroying.
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ConnectionPool.getInstance();
		logger.log(Level.INFO, "Connection pool is created");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionPool.getInstance()
	    		.destroyPool();
		logger.log(Level.INFO, "Connection pool is destroyed");
	}

}
