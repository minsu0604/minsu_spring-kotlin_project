package bitc.fullstack405.fun_spring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/application.properties")
public class DBConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    DataSource dataSource() throws Exception {
        DataSource ds = new HikariDataSource(hikariConfig());

        return ds;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    public Properties hibernateConfig() {
        return new Properties();
    }
}
