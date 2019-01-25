package net.andreaskluth.spring.boot.autoconfigure.mustache.java;

import static net.andreaskluth.spring.boot.autoconfigure.mustache.java.MustacheProperties.DEFAULT_PREFIX;
import static net.andreaskluth.spring.boot.autoconfigure.mustache.java.MustacheProperties.DEFAULT_SUFFIX;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

/**
 * {@link TemplateAvailabilityProvider} that provides availability information for Mustache view
 * templates.
 */
public class MustacheTemplateAvailabilityProvider implements TemplateAvailabilityProvider {

  @Override
  public boolean isTemplateAvailable(
      String view,
      Environment environment,
      ClassLoader classLoader,
      ResourceLoader resourceLoader) {
    if (ClassUtils.isPresent("com.github.mustachejava.Mustache", classLoader)) {
      String prefix = environment.getProperty("spring.mustachejava.prefix", DEFAULT_PREFIX);
      String suffix = environment.getProperty("spring.mustachejava.suffix", DEFAULT_SUFFIX);
      return resourceLoader.getResource(prefix + view + suffix).exists();
    }
    return false;
  }
}
