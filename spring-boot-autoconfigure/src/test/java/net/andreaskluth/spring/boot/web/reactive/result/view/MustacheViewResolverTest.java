package net.andreaskluth.spring.boot.web.reactive.result.view;

import static net.andreaskluth.spring.boot.web.MustacheTestSupport.aMustacheFactory;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.result.view.View;
import reactor.core.publisher.Mono;

class MustacheViewResolverTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

  @Test
  void resolveNonExistent() {
    contextRunner.run(
        ctx -> {
          Mono<View> nonexistent = aMustacheViewResolver(ctx).resolveViewName("nonexistent", null);
          assertThat(nonexistent.block(Duration.ofSeconds(5))).isNull();
        });
  }

  @Test
  void resolveExisting() {
    contextRunner.run(
        ctx -> {
          Mono<View> view = aMustacheViewResolver(ctx).resolveViewName("view", null);
          assertThat(view.block(Duration.ofSeconds(5))).isNotNull();
        });
  }

  private MustacheViewResolver aMustacheViewResolver(ApplicationContext ctx) {
    MustacheViewResolver resolver = new MustacheViewResolver(aMustacheFactory());
    resolver.setApplicationContext(ctx);
    resolver.setPrefix("classpath:/templates/");
    resolver.setSuffix(".pornobalken");
    return resolver;
  }
}
