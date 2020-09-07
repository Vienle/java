package com.pairlearning.expensetrackerapi;

import com.pairlearning.expensetrackerapi.filters.AuthenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseTrackerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApiApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<AuthenFilter> filterFilterRegistrationBean(){
        FilterRegistrationBean<AuthenFilter> registrationBean = new FilterRegistrationBean<>();
        AuthenFilter authenFilter = new AuthenFilter();
        registrationBean.setFilter(authenFilter);
        registrationBean.addUrlPatterns("/api/categories/*");
        return registrationBean;
    }

}
