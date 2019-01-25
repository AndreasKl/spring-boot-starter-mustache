package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

/**
 * {@link TemplateAvailabilityProvider} that provides availability information for Mustache view
 * templates.
 */
public class MustacheTemplateAvailabilityProvider implements TemplateAvailabilityProvider {

  public static final String MUSTACHE_CLASS_NAME = "com.github.mustachejava.Mustache";
  public static final String MUSTACHEJAVA_PREFIX = "spring.mustachejava.prefix";
  public static final String MUSTACHEJAVA_SUFFIX = "spring.mustachejava.suffix";

  @Override
  public boolean isTemplateAvailable(
      String view, Environment env, ClassLoader classLoader, ResourceLoader resourceLoader) {
    return isMustacheOnClasspath(classLoader) && isResourcePresent(view, env, resourceLoader);
  }

  private boolean isResourcePresent(String view, Environment env, ResourceLoader resourceLoader) {
    return resourceLoader.getResource(getSuffix(env) + view + getPrefix(env)).exists();
  }

  private String getPrefix(Environment environment) {
    return environment.getProperty(MUSTACHEJAVA_SUFFIX, MustacheProperties.DEFAULT_SUFFIX);
  }

  private String getSuffix(Environment environment) {
    return environment.getProperty(MUSTACHEJAVA_PREFIX, MustacheProperties.DEFAULT_PREFIX);
  }

  private boolean isMustacheOnClasspath(ClassLoader classLoader) {
    return ClassUtils.isPresent(MUSTACHE_CLASS_NAME, classLoader);
  }
}
