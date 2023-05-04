package uz.pentagol.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredTokenFilterConfig {


    private final TokenJwtFilter tokenJwtFilter;

    public SecuredTokenFilterConfig(TokenJwtFilter tokenJwtFilter){
        this.tokenJwtFilter = tokenJwtFilter;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(tokenJwtFilter);
        bean.addUrlPatterns("/region/adm/*");
        return bean;
    }
}
