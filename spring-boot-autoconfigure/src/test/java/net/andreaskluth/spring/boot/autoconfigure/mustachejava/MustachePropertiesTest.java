package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.util.MimeType;

class MustachePropertiesTest {

  private static final MimeType PNG_US_ASCII_MIME_TYPE =
      MimeType.valueOf("image/png;charset=US-ASCII");

  private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner()
          .withConfiguration(AutoConfigurations.of(MustacheAutoConfiguration.class));

  @Test
  void propertiesAreFilled() {

    contextRunner
        .withPropertyValues("mustachejava.contentType=image/png", "mustachejava.charset=US-ASCII")
        .run(
            ctx -> {
              MustacheProperties props = ctx.getBean(MustacheProperties.class);
              assertThat(props.getCharset()).isEqualTo(StandardCharsets.US_ASCII);
              assertThat(props.getContentType()).isEqualTo(PNG_US_ASCII_MIME_TYPE);
            });
  }
}
