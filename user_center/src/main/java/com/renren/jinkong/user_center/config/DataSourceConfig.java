package com.renren.jinkong.user_center.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String  username;
    @Value("${spring.datasource.password}")
    private String  password;
    @Value("${spring.datasource.driver-class-name}")
    private String  driverClassName;
    @Value("${spring.datasource.name}")
    private String name;

    @Value("${druid1.initalSize}")
    private int initalSize;
    @Value("${druid1.maxActive}")
    private int maxActive;
    @Value("${druid1.minldle}")
    private int minldle;
    @Value("${druid1.maxWait}")
    private int maxWait;
    @Value("${druid1.validationQuery}")
    private String validationQuery;

    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    @Primary
    public DataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        log.info("conn------"+jdbcUrl+"\n"+username+"\n"+password+"\n"+driverClassName);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setName(name);
        druidDataSource.setInitialSize(initalSize);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMinIdle(minldle);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setValidationQuery(validationQuery);
        try {
            druidDataSource.setFilters("stat");//不然无法显示SQL检测信息
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        //配置用户名
        reg.addInitParameter("loginUsername", "root");
        //配置密码
        reg.addInitParameter("loginPassword", "123456");
        //在日志中打印执行慢的sql语句
        reg.addInitParameter("logSlowSql", "true");
        //另外还可配置黑白名单等信息，可参考druid官网介绍
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        //过滤文件类型
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        //监控单个url调用的sql列表
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }
}
