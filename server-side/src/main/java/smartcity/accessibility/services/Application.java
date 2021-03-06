package smartcity.accessibility.services;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.inject.Guice;
import com.google.inject.Injector;

import jersey.repackaged.com.google.common.cache.CacheBuilder;
import jersey.repackaged.com.google.common.cache.CacheLoader;
import jersey.repackaged.com.google.common.cache.LoadingCache;
import smartcity.accessibility.database.DatabaseModule;
import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.database.UserProfileManager;

/**
 * @author yael
 */
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	public static final LoadingCache<String, UserInfo> tokenToSession = resetSessions();
	public static final long expirationLoginTime = 60;

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new DatabaseModule());
		ReviewManager.initialize(injector.getInstance(ReviewManager.class));
		LocationManager.initialize(injector.getInstance(LocationManager.class));
		UserProfileManager.initialize(injector.getInstance(UserProfileManager.class));
		SpringApplication.run(Application.class, args);
	}

	public static LoadingCache<String, UserInfo> resetSessions() {
		return CacheBuilder.newBuilder().concurrencyLevel(4).maximumSize(10000)
				.expireAfterWrite(expirationLoginTime, TimeUnit.MINUTES)
				.expireAfterAccess(expirationLoginTime, TimeUnit.MINUTES).build(new CacheLoader<String, UserInfo>() {
					@Override
					public UserInfo load(String token) throws Exception {
						return new UserInfo();
					}
				});
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}