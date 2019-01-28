package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import static org.assertj.core.api.Assertions.assertThat;

import net.andreaskluth.spring.boot.web.reactive.result.view.MustacheViewResolver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;

class MustacheReactiveWebConfigurationTest {

  private final ReactiveWebApplicationContextRunner contextRunner =
      new ReactiveWebApplicationContextRunner().withConfiguration(reactiveAutoConfig());

  @Test
  void beansAreRegisteredViaAutoConfig() {
    contextRunner.run(
        ctx -> {
          assertThat(ctx).hasSingleBean(MustacheViewResolver.class);
        });
  }

  private AutoConfigurations reactiveAutoConfig() {
    return AutoConfigurations.of(
        MustacheReactiveWebConfiguration.class, MustacheAutoConfiguration.class);
  }
}
