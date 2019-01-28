package net.andreaskluth.spring.boot.web.reactive.result.view;

import com.github.mustachejava.MustacheFactory;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;
import org.springframework.web.reactive.result.view.ViewResolver;

/** Spring WebFlux {@link ViewResolver} for mustache.java. */
public class MustacheViewResolver extends UrlBasedViewResolver {

  private final MustacheFactory factory;
  private String charset;

  /**
   * Create a {@code MustacheViewResolver} backed by a custom instance of a {@link MustacheFactory}.
   *
   * @param factory the Mustache factory used to compile templates
   */
  public MustacheViewResolver(MustacheFactory factory) {
    this.factory = factory;
    setViewClass(requiredViewClass());
  }

  /**
   * Set the charset.
   *
   * @param charset the charset
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }

  @Override
  protected Class<?> requiredViewClass() {
    return MustacheView.class;
  }

  @Override
  protected AbstractUrlBasedView createView(String viewName) {
    MustacheView view = (MustacheView) super.createView(viewName);
    view.setMustacheFactory(factory);
    view.setCharset(this.charset);
    return view;
  }
}
