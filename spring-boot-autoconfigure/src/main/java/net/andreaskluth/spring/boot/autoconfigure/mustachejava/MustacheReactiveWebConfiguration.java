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
@ConditionalOnWebApplication(type = Type.REACTIVE)
class MustacheReactiveWebConfiguration {

  private final MustacheProperties mustacheProperties;

  protected MustacheReactiveWebConfiguration(MustacheProperties mustacheProperties) {
    this.mustacheProperties = mustacheProperties;
  }

  @Bean
  @ConditionalOnMissingBean
  public MustacheViewResolver mustacheViewResolver(MustacheFactory mustacheFactory) {
    MustacheViewResolver resolver = new MustacheViewResolver(mustacheFactory);
    resolver.setPrefix(this.mustacheProperties.getPrefix());
    resolver.setSuffix(this.mustacheProperties.getSuffix());
    resolver.setViewNames(this.mustacheProperties.getViewNames());
    resolver.setRequestContextAttribute(this.mustacheProperties.getRequestContextAttribute());
    resolver.setCharset(this.mustacheProperties.getCharsetName());
    resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
    return resolver;
  }
}
