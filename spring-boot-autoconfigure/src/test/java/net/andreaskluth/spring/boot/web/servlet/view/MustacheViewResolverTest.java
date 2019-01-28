package net.andreaskluth.spring.boot.web.servlet.view;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.mustachejava.DefaultMustacheFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.View;

class MustacheViewResolverTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

  @Test
  void resolveNonExistent() {
    contextRunner.run(
        ctx -> {
          assertThat(aMustacheViewResolver(ctx).resolveViewName("nonexistent", null)).isNull();
        });
  }

  @Test
  void resolveExisting() {
    contextRunner.run(
        ctx -> {
          assertThat(aMustacheViewResolver(ctx).resolveViewName("view", null)).isNotNull();
        });
  }

  @Test
  void setContentType() throws Exception {
    contextRunner.run(
        ctx -> {
          MustacheViewResolver resolver = aMustacheViewResolver(ctx);
          resolver.setContentType("application/internet-property-stream");

          View view = resolver.resolveViewName("view", null);

          assertThat(view.getContentType()).isEqualTo("application/internet-property-stream");
        });
  }

  private MustacheViewResolver aMustacheViewResolver(ApplicationContext ctx) {
    MustacheViewResolver resolver = new MustacheViewResolver(new DefaultMustacheFactory());
    resolver.setApplicationContext(ctx);
    resolver.setServletContext(new MockServletContext());
    resolver.setPrefix("classpath:/templates/");
    resolver.setSuffix(".pornobalken");
    return resolver;
  }
}
