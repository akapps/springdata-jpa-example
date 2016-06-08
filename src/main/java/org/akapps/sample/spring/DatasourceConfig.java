package org.akapps.sample.spring;

import org.hsqldb.jdbc.JDBCDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Antoine Kapps
 */
@Configuration
public class DatasourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceConfig.class);

    private String homeDir() {
        return System.getProperty("user.home");
    }

    @Bean
    public DataSource dataSource() {
        final String url = "jdbc:hsqldb:file:" + homeDir() + "/.FullstackExample/hsql/db";
        LOGGER.info("Datasource URL configured to \"{}\"", url);

        JDBCDataSource ds = new JDBCDataSource();
        ds.setUrl(url);
        ds.setUser("sa");
        ds.setPassword("");

        return ds;
    }

    @Bean(name = "hbm-properties")
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        return properties;
    }
}
