package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import com.github.mustachejava.MustacheNotFoundException;
import com.github.mustachejava.MustacheResolver;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * Mustache TemplateLoader implementation that uses a prefix, suffix and the Spring Resource
 * abstraction to load a template from a file, classpath, URL etc. A {@link MustacheResolver} is
 * needed in the {@link com.github.mustachejava.MustacheFactory} when you want to render partials.
 */
public class MustacheResourceResolver implements MustacheResolver, ResourceLoaderAware {

  private String charSet = StandardCharsets.UTF_8.name();
  private ResourceLoader resourceLoader = new DefaultResourceLoader();

  public void setCharset(String charSet) {
    this.charSet = charSet;
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Override
  public Reader getReader(String resourceName) {
    try {
      return new InputStreamReader(
          resourceLoader.getResource(resourceName).getInputStream(), charSet);
    } catch (IOException e) {
      throw new MustacheNotFoundException(resourceName, e);
    }
  }
}
