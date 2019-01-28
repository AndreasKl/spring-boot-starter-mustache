package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import static org.assertj.core.api.Assertions.assertThat;

import net.andreaskluth.spring.boot.web.servlet.view.MustacheViewResolver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

public class MustacheServletWebConfigurationTest {

  private final WebApplicationContextRunner contextRunner =
      new WebApplicationContextRunner().withConfiguration(servletAutoConfig());

  @Test
  void beansAreRegisteredViaAutoConfig() {
    contextRunner.run(
        ctx -> {
          assertThat(ctx).hasSingleBean(MustacheViewResolver.class);
        });
  }

  private AutoConfigurations servletAutoConfig() {
    return AutoConfigurations.of(
        MustacheServletWebConfiguration.class, MustacheAutoConfiguration.class);
  }
}
