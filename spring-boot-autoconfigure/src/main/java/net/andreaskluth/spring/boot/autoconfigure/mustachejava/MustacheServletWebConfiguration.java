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
    resolver.setPrefix(mustacheProperties.getPrefix());
    resolver.setSuffix(mustacheProperties.getSuffix());
    resolver.setCache(mustacheProperties.isCache());
    if (mustacheProperties.getContentType() != null) {
      resolver.setContentType(mustacheProperties.getContentType().toString());
    }
    resolver.setViewNames(mustacheProperties.getViewNames());
    resolver.setExposeRequestAttributes(mustacheProperties.isExposeRequestAttributes());
    resolver.setAllowRequestOverride(mustacheProperties.isAllowRequestOverride());
    resolver.setAllowSessionOverride(mustacheProperties.isAllowSessionOverride());
    resolver.setExposeSessionAttributes(mustacheProperties.isExposeSessionAttributes());
    resolver.setExposeSpringMacroHelpers(mustacheProperties.isExposeSpringMacroHelpers());
    resolver.setRequestContextAttribute(mustacheProperties.getRequestContextAttribute());
    resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
    resolver.setCharset(mustacheProperties.getCharsetName());
    resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
    return resolver;
  }
}
