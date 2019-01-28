package net.andreaskluth.spring.boot.web.reactive.result.view;

import static java.util.Collections.singletonMap;
import static net.andreaskluth.spring.boot.web.MustacheTestSupport.aMustacheFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.mustachejava.MustacheNotFoundException;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

class MustacheViewTest {

  private static final Map<String, Object> model = singletonMap("value", "fun with ");
  private static final String templateUrl = "classpath:/templates/view.mustache";
  private static final String nonExistentPartial =
      "classpath:/templates/nonexistentpartialview.mustache";
  private static final String nonExistentTemplateUrl = "classpath:/templates/nonexistent.mustache";

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

  @Test
  void rendersNestedTemplate() {
    contextRunner.run(
        ctx -> {
          String rendered = renderMergedTemplateModel(aMustacheReactiveView(templateUrl, ctx));
          assertThat(rendered).isEqualTo("fun with a partial");
        });
  }

  @Test
  void failsNonExistentNestedTemplate() {
    contextRunner.run(
        ctx -> {
          assertThrows(
              MustacheNotFoundException.class,
              () -> renderMergedTemplateModel(aMustacheReactiveView(nonExistentPartial, ctx)));
        });
  }

  @Test
  void failsNonExistentTemplate() {
    contextRunner.run(
        ctx -> {
          assertThrows(
              FileNotFoundException.class,
              () -> renderMergedTemplateModel(aMustacheReactiveView(nonExistentTemplateUrl, ctx)));
        });
  }

  private String renderMergedTemplateModel(MustacheView view) {
    view.render(model, MediaType.TEXT_HTML, anExchange()).block(Duration.ofSeconds(5));
    return anExchange().getResponse().getBodyAsString().block(Duration.ofSeconds(5));
  }

  private MockServerWebExchange anExchange() {
    return MockServerWebExchange.from(MockServerHttpRequest.get("/").build());
  }

  private MustacheView aMustacheReactiveView(String templateUrl, ApplicationContext ctx) {
    MustacheView view = new MustacheView();
    view.setMustacheFactory(aMustacheFactory());
    view.setUrl(templateUrl);
    view.setApplicationContext(ctx);
    return view;
  }
}
