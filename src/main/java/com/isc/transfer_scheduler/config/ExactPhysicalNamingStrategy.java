
package com.isc.transfer_scheduler.config;


import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExactPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    private static final Logger logger = LoggerFactory.getLogger(ExactPhysicalNamingStrategy.class);

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        // Use the exact column name specified in @Column annotations
        // logger.error("Using exact column name: {}", name.getText());
        return name;
    }
}