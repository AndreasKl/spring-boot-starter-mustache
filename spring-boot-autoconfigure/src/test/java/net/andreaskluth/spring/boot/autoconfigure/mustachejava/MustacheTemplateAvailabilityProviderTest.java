package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import static net.andreaskluth.spring.boot.autoconfigure.mustachejava.MustacheTemplateAvailabilityProvider.MUSTACHEJAVA_PREFIX;
import static net.andreaskluth.spring.boot.autoconfigure.mustachejava.MustacheTemplateAvailabilityProvider.MUSTACHEJAVA_SUFFIX;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.util.ClassUtils;

class MustacheTemplateAvailabilityProviderTest {

  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

  @AfterEach
  void cleanup() {
    ctx.close();
  }

  @Test
  void templateExists() {
    assertThat(isTemplateAvailable("view")).isTrue();
    assertThat(isTemplateAvailable("unknown_resource")).isFalse();
  }

  private boolean isTemplateAvailable(String view) {
    return anAvailabilityProvider()
        .isTemplateAvailable(view, anEnvironment(), ClassUtils.getDefaultClassLoader(), ctx);
  }

  private MockEnvironment anEnvironment() {
    MockEnvironment mockEnvironment = new MockEnvironment();
    mockEnvironment.setProperty(MUSTACHEJAVA_PREFIX, "classpath:/templates/");
    mockEnvironment.setProperty(MUSTACHEJAVA_SUFFIX, ".mustache");
    return mockEnvironment;
  }

  private MustacheTemplateAvailabilityProvider anAvailabilityProvider() {
    return new MustacheTemplateAvailabilityProvider();
  }
}
