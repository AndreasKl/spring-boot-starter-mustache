package net.andreaskluth.spring.boot.web.servlet.view;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/** Spring MVC {@link ViewResolver} for Mustache. */
public class MustacheViewResolver extends AbstractTemplateViewResolver {

  private final MustacheFactory factory;

  private String charset;

  /**
   * Create a {@code MustacheViewResolver} backed by a default instance of a {@link
   * MustacheFactory}.
   */
  public MustacheViewResolver() {
    this.factory = new DefaultMustacheFactory();
    setViewClass(requiredViewClass());
  }

  /**
   * Create a {@code MustacheViewResolver} backed by a custom instance of a {@link MustacheFactory}.
   *
   * @param factory the Mustache factory used to compile templates
   */
  public MustacheViewResolver(MustacheFactory factory) {
    this.factory = factory;
    setViewClass(requiredViewClass());
  }

  @Override
  protected Class<?> requiredViewClass() {
    return MustacheView.class;
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
  protected AbstractUrlBasedView buildView(String viewName) throws Exception {
    MustacheView view = (MustacheView) super.buildView(viewName);
    view.setFactory(this.factory);
    view.setCharset(this.charset);
    return view;
  }
}
