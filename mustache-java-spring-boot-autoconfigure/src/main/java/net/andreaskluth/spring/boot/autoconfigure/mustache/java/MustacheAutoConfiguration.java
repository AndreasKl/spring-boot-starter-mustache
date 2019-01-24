package net.andreaskluth.spring.boot.autoconfigure.mustache.java;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({Mustache.class})
public class MustacheAutoConfiguration implements InitializingBean {

  public void afterPropertiesSet() {}

  @Bean
  @ConditionalOnMissingBean
  public DefaultMustacheFactory mustacheFactory() {
    return new DefaultMustacheFactory();
  }
}
