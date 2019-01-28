package net.andreaskluth.spring.boot.web;

import com.github.mustachejava.DefaultMustacheFactory;
import net.andreaskluth.spring.boot.autoconfigure.mustachejava.MustacheResourceResolver;
import org.springframework.core.io.DefaultResourceLoader;

public class MustacheTestSupport {

  private MustacheTestSupport() {
    // NOOP
  }

  public static DefaultMustacheFactory aMustacheFactory() {
    MustacheResourceResolver mustacheResolver = new MustacheResourceResolver();
    mustacheResolver.setResourceLoader(new DefaultResourceLoader());
    return new DefaultMustacheFactory(mustacheResolver);
  }

}
