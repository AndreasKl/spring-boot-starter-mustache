package net.andreaskluth.spring.boot.autoconfigure.mustache.java;

import com.github.mustachejava.MustacheResolver;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Mustache TemplateLoader implementation that uses a prefix, suffix and the Spring Resource
 * abstraction to load a template from a file, classpath, URL etc. A {@link MustacheResolver} is
 * needed in the {@link com.github.mustachejava.MustacheFactory} when you want to render partials
 * (i.e. tiles-like features).
 *
 * @see Resource
 */
public class MustacheResourceResolver implements MustacheResolver, ResourceLoaderAware {

  private String prefix = "";

  private String suffix = "";

  private String charSet = "UTF-8";

  private ResourceLoader resourceLoader = new DefaultResourceLoader();

  public MustacheResourceResolver() {}

  public MustacheResourceResolver(String prefix, String suffix) {
    this.prefix = prefix;
    this.suffix = suffix;
  }

  public void setCharset(String charSet) {
    this.charSet = charSet;
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Override
  public Reader getReader(final String resourceName) {
    try {
      return new InputStreamReader(
          this.resourceLoader
              .getResource(this.prefix + resourceName + this.suffix)
              .getInputStream(),
          this.charSet);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
