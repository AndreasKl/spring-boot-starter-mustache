package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class MustacheAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner().withConfiguration(mustacheAutoConfig());

  @Test
  void beansAreRegisteredViaAutoConfig() {
    contextRunner.run(
        ctx -> {
          assertThat(ctx).hasSingleBean(DefaultMustacheFactory.class);
          assertThat(ctx).hasSingleBean(MustacheResourceResolver.class);
        });
  }

  @Test
  void beansAreNotRegisteredIfMustacheIsNotOnTheClasspath() {
    contextRunner
        .withClassLoader(new FilteredClassLoader(Mustache.class))
        .run(
            ctx -> {
              assertThat(ctx).doesNotHaveBean(DefaultMustacheFactory.class);
              assertThat(ctx).doesNotHaveBean(MustacheResourceResolver.class);
            });
  }

  private AutoConfigurations mustacheAutoConfig() {
    return AutoConfigurations.of(MustacheAutoConfiguration.class);
  }
}
