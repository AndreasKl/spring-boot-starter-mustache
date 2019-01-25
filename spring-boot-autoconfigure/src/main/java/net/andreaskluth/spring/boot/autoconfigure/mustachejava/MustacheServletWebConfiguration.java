package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import com.github.mustachejava.MustacheFactory;
import net.andreaskluth.spring.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
class MustacheServletWebConfiguration {

  private final MustacheProperties mustacheProperties;

  protected MustacheServletWebConfiguration(MustacheProperties mustacheProperties) {
    this.mustacheProperties = mustacheProperties;
  }

  @Bean
  @ConditionalOnMissingBean
  public MustacheViewResolver mustacheViewResolver(MustacheFactory mustacheFactory) {
    MustacheViewResolver resolver = new MustacheViewResolver(mustacheFactory);
    mustacheProperties.applyToMvcViewResolver(resolver);
    resolver.setCharset(mustacheProperties.getCharsetName());
    resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
    return resolver;
  }
}
