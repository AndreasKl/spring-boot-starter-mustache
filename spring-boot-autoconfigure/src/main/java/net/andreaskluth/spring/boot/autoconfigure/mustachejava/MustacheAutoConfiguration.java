package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheResolver;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass({Mustache.class})
@EnableConfigurationProperties(MustacheProperties.class)
@Import({MustacheServletWebConfiguration.class, MustacheReactiveWebConfiguration.class})
public class MustacheAutoConfiguration {

  private static final Log logger = LogFactory.getLog(MustacheAutoConfiguration.class);

  private final MustacheProperties mustacheProperties;
  private final ApplicationContext applicationContext;

  public MustacheAutoConfiguration(
      MustacheProperties mustacheProperties, ApplicationContext applicationContext) {
    this.mustacheProperties = mustacheProperties;
    this.applicationContext = applicationContext;
  }

  @PostConstruct
  public void checkTemplateLocationExists() {
    if (mustacheProperties.isCheckTemplateLocation()) {
      TemplateLocation location = new TemplateLocation(mustacheProperties.getPrefix());
      if (!location.exists(this.applicationContext)) {
        logger.warn(
            "Cannot find template location: "
                + location
                + " (please add some templates, check your Mustache "
                + "configuration, or set mustachejava.check-template-location=false)");
      }
    }
  }

  @Bean
  @ConditionalOnMissingBean
  public DefaultMustacheFactory mustacheFactory(MustacheResolver mustacheResolver) {
    return new DefaultMustacheFactory(mustacheResolver);
  }

  @Bean
  @ConditionalOnMissingBean(MustacheResolver.class)
  public MustacheResourceResolver mustacheTemplateLoader() {
    MustacheResourceResolver loader = new MustacheResourceResolver();
    loader.setCharset(mustacheProperties.getCharsetName());
    return loader;
  }
}
