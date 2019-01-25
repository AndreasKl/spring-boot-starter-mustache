package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.mustachejava.DefaultMustacheFactory;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MustacheAutoConfigurationTest {

  @Test
  public void beanIsRegistered() {
    withContext(
        (ctx) -> assertThat(ctx.getBeanNamesForType(DefaultMustacheFactory.class)).isNotEmpty());
  }

  private void withContext(Consumer<AnnotationConfigApplicationContext> contextConsumer) {
    try (AnnotationConfigApplicationContext context = setupContext()) {
      contextConsumer.accept(context);
    }
  }

  private AnnotationConfigApplicationContext setupContext() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(MustacheAutoConfiguration.class);
    context.refresh();
    return context;
  }
}
