package net.andreaskluth.spring.boot.web.servlet.view;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheNotFoundException;
import java.io.FileNotFoundException;
import java.util.Map;
import net.andreaskluth.spring.boot.autoconfigure.mustachejava.MustacheResourceResolver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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
          String rendered = renderMergedTemplateModel(aMustacheView(templateUrl, ctx));
          assertThat(rendered).isEqualTo("fun with a partial");
        });
  }

  @Test
  void failsNonExistentNestedTemplate() {
    contextRunner.run(
        ctx -> {
          assertThrows(
              MustacheNotFoundException.class,
              () -> renderMergedTemplateModel(aMustacheView(nonExistentPartial, ctx)));
        });
  }

  @Test
  void failsNonExistentTemplate() {
    contextRunner.run(
        ctx -> {
          assertThrows(
              FileNotFoundException.class,
              () -> renderMergedTemplateModel(aMustacheView(nonExistentTemplateUrl, ctx)));
        });
  }

  private String renderMergedTemplateModel(MustacheView view) throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    view.renderMergedTemplateModel(model, request, response);
    return response.getContentAsString();
  }

  private MustacheView aMustacheView(String templateUrl, ApplicationContext ctx) {
    MustacheView view = new MustacheView();
    view.setFactory(aMustacheFactory());
    view.setUrl(templateUrl);
    view.setApplicationContext(ctx);
    return view;
  }

  private DefaultMustacheFactory aMustacheFactory() {
    MustacheResourceResolver mustacheResolver = new MustacheResourceResolver();
    mustacheResolver.setResourceLoader(new DefaultResourceLoader());
    return new DefaultMustacheFactory(mustacheResolver);
  }
}
