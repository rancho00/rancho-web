package com.rancho.web.admin.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ValidatorConfig {

    /**
     * 配置validator bean
     * 在验证参数失败后立即返回，而不是继续验证
     * @return
     */
    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory= Validation.byProvider(HibernateValidator.class)
                    .configure()
                    .failFast(true)
                    .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
