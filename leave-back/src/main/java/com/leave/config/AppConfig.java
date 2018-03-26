package com.leave.config;

import com.leave.service.JwtService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@EnableWebMvc
@EnableSwagger2
@ComponentScan(AppConfig.PACKAGE)
public class AppConfig implements WebMvcConfigurer {
	public final static String PACKAGE = "com.leave";
	public final static String PACKAGE_ENTITY = PACKAGE + ".entity";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, null, "Bearer " + jwtService.generateJWT("SWAGGER", new Long(1), 0), ApiKeyVehicle.HEADER, "Authorization", ",");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}

	@Autowired
	private Environment env;

	@Autowired
	private JwtService jwtService;

	@Bean
	public HibernateTemplate hibernateTemplate() throws IOException {
		return new HibernateTemplate(getSessionFactory());
	}

	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory() throws IOException {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(getDataSource());
		lsfb.setPackagesToScan(PACKAGE_ENTITY);
		lsfb.setHibernateProperties(hibernateProperties());
		lsfb.afterPropertiesSet();
		return lsfb.getObject();
	}

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("db.driver"));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager hibernateTransactionManager() throws IOException {
		return new HibernateTransactionManager(getSessionFactory());
	}

	private Properties hibernateProperties() {
		Properties props = new Properties();
		props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		return props;
	}

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// Using gmail
		mailSender.setHost(env.getProperty("smtp.mail.host"));
		mailSender.setPort(Integer.parseInt(env.getProperty("smtp.mail.port")));
		/*
		 * mailSender.setUsername("Your-gmail-id");
		 * mailSender.setPassword("Your-gmail-password");
		 */
		Properties props = new Properties();
		mailSender.setJavaMailProperties(props);
		props.put("mail.smtp.auth", "false");

		return mailSender;
	}
}