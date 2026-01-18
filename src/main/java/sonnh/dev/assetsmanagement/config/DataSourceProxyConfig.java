package sonnh.dev.assetsmanagement.config;

import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
@Profile("local") //bật/tắt
@Configuration
public class DataSourceProxyConfig {

    @Bean
    @ConditionalOnBean(DataSource.class)
    public DataSource dataSource(DataSource originalDataSource) {

        QueryExecutionListener listener = new SLF4JQueryLoggingListener();

        return ProxyDataSourceBuilder
                .create(originalDataSource)
                .name("DS-PROXY")
                .listener(listener)
                .countQuery()
                .logQueryBySlf4j()
                .build();
    }
}