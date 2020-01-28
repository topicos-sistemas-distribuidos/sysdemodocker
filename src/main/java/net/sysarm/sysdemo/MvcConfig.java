package net.sysarm.sysdemo;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Classe de configuração das views e controllers da aplicação
 * @author armandosoaressousa
 *
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    /**
     * Registra os views princpais
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("dashboard");
        registry.addViewController("/login").setViewName("login");
    }
   
    /**
     * Bean que entrega um HttpSessionEventPublisher
     * @return nova instancia de HttpSessionEventPublisher
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }	

        /**
     * Bean responsável por tratar as mensagens do arquivo messages.properties da aplicacao
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(10); //reload messages every 10 seconds
        return messageSource;
    }
    
    /**
     * Bean que registra o local onde as mensagens do arquivo messages.properties da aplicacao ficam
     * @param messageSource
     * @return
     */
    @Bean
     public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
     }
    
}
