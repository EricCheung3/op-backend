package com.openprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.openprice.file.FileFolderSettings;
import com.openprice.mail.EmailProperties;
import com.openprice.process.ProcessSettings;

@EnableConfigurationProperties({EmailProperties.class, FileFolderSettings.class, ProcessSettings.class})
public class OpenPriceInternalApiApplication extends AbstractApiApplication {

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OpenPriceInternalApiApplication.class);
        app.run(args);
    }

    @Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        public SecurityConfig() {
            super();
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                    .anyRequest().permitAll()
                ;
        }
    }

}
