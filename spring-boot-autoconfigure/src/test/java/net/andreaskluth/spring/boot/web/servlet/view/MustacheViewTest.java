package net.andreaskluth.spring.boot.web.servlet.view;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

import com.github.mustachejava.DefaultMustacheFactory;
import java.io.FileNotFoundException;
import java.util.Map;
import net.andreaskluth.spring.boot.autoconfigure.mustachejava.MustacheResourceResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

class MustacheViewTest {

  private static final Map<String, Object> model = singletonMap("value", "fun with ");
  private static final String templateUrl = "classpath:/templates/view.mustache";
  private static final String nonExistentTemplateUrl = "classpath:/templates/nonexistent.mustache";

  private AnnotationConfigWebApplicationContext context =
      new AnnotationConfigWebApplicationContext();

  @BeforeEach
  void init() {
    setupApplicationContext();
  }

  @AfterEach
  void cleanup() {
    cleanupApplicationContext();
  }

  @Test
  void rendersNestedTemplates() throws Exception {
    String rendered = renderMergedTemplateModel(aMustacheView(templateUrl));
    assertThat(rendered).isEqualTo("fun with a partial");
  }

  @Test
  void failsNonExistentTemplate() {
    assertThrows(
        FileNotFoundException.class,
        () -> renderMergedTemplateModel(aMustacheView(nonExistentTemplateUrl)));
  }

  private String renderMergedTemplateModel(MustacheView view) throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    view.renderMergedTemplateModel(model, request, response);
    return response.getContentAsString();
  }

  private MustacheView aMustacheView(String templateUrl) {
    MustacheView view = new MustacheView();
    view.setFactory(aMustacheFactory());
    view.setUrl(templateUrl);
    view.setApplicationContext(context);
    return view;
  }

  private DefaultMustacheFactory aMustacheFactory() {
    MustacheResourceResolver mustacheResolver = new MustacheResourceResolver();
    mustacheResolver.setResourceLoader(new DefaultResourceLoader());
    return new DefaultMustacheFactory(mustacheResolver);
  }

  private void setupApplicationContext() {
    context.refresh();

    MockServletContext servletContext = new MockServletContext();
    context.setServletContext(servletContext);
    servletContext.setAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
  }

  private void cleanupApplicationContext() {
    context.close();
  }
}
