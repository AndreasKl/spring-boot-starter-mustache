package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

import com.github.mustachejava.DefaultMustacheFactory;
import java.util.function.Consumer;
import net.andreaskluth.spring.boot.web.servlet.view.MustacheViewResolver;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

class MustacheAutoConfigurationTest {

  @Test
  void beansAreRegisteredViaAutoConfig() {
    withContext(
        (ctx) -> {
          assertThat(ctx.getBeanNamesForType(DefaultMustacheFactory.class)).isNotEmpty();
          assertThat(ctx.getBeanNamesForType(MustacheResourceResolver.class)).isNotEmpty();
          assertThat(ctx.getBeanNamesForType(MustacheViewResolver.class)).isNotEmpty();
        });
  }

  private void withContext(Consumer<AnnotationConfigWebApplicationContext> contextConsumer) {
    try (AnnotationConfigWebApplicationContext context =
        setupContext(MustacheAutoConfiguration.class, MustacheServletWebConfiguration.class)) {
      contextConsumer.accept(context);
    }
  }

  private AnnotationConfigWebApplicationContext setupContext(Class... configs) {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(configs);
    context.refresh();

    MockServletContext servletContext = new MockServletContext();
    context.setServletContext(servletContext);
    servletContext.setAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);

    return context;
  }
}
