package net.andreaskluth.spring.boot.web.servlet.view;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractTemplateView;

/** Spring MVC {@link View} using the mustache.java template engine. */
public class MustacheView extends AbstractTemplateView {

  private MustacheFactory factory;
  private String charset;

  /**
   * Set the Mustache factory to be used by this view.
   *
   * <p>Typically this property is not set directly. Instead a single {@link MustacheFactory} is
   * expected in the Spring application context which is used to compile Mustache templates.
   *
   * @param factory the Mustache factory
   */
  public void setFactory(MustacheFactory factory) {
    this.factory = factory;
  }

  /**
   * Set the charset used for reading Mustache template files.
   *
   * @param charset the charset to use for reading template files
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }

  @Override
  public boolean checkResource(Locale locale) {
    Resource resource = getApplicationContext().getResource(getUrl());
    return resource != null && resource.exists();
  }

  @Override
  protected void renderMergedTemplateModel(
      Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    Resource resource = getApplicationContext().getResource(getUrl());
    Mustache mustache = createMustache(resource);
    if (mustache != null) {
      mustache.execute(response.getWriter(), model);
    }
  }

  private Mustache createMustache(Resource resource) throws IOException {
    try (Reader reader = getReader(resource)) {
      return factory.compile(reader, getUrl());
    }
  }

  private Reader getReader(Resource resource) throws IOException {
    if (charset != null) {
      return new InputStreamReader(resource.getInputStream(), charset);
    }
    return new InputStreamReader(resource.getInputStream());
  }
}
