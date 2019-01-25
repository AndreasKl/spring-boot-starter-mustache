package net.andreaskluth.spring.boot.web.servlet.view;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.mustachejava.DefaultMustacheFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.View;

class MustacheViewResolverTests {

  private AnnotationConfigApplicationContext applicationContext =
      new AnnotationConfigApplicationContext();

  @BeforeEach
  void init() {
    applicationContext.refresh();
  }

  @AfterEach
  void cleanup() {
    applicationContext.close();
  }

  @Test
  void resolveNonExistent() throws Exception {
    assertThat(aMustacheViewResolver().resolveViewName("nonexistent", null)).isNull();
  }

  @Test
  void resolveExisting() throws Exception {
    assertThat(aMustacheViewResolver().resolveViewName("view", null)).isNotNull();
  }

  @Test
  void setContentType() throws Exception {
    MustacheViewResolver resolver = aMustacheViewResolver();
    resolver.setContentType("application/internet-property-stream");
    View view = resolver.resolveViewName("view", null);
    assertThat(view.getContentType()).isEqualTo("application/internet-property-stream");
  }

  private MustacheViewResolver aMustacheViewResolver() {
    MustacheViewResolver resolver = new MustacheViewResolver(new DefaultMustacheFactory());
    resolver.setApplicationContext(applicationContext);
    resolver.setServletContext(new MockServletContext());
    resolver.setPrefix("classpath:/templates/");
    resolver.setSuffix(".pornobalken");
    return resolver;
  }
}
