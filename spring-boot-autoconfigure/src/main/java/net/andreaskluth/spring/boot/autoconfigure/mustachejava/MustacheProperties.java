package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

/** {@link ConfigurationProperties} for Mustache. */
@ConfigurationProperties(prefix = "spring.mustachejava")
public class MustacheProperties {

  public static final String DEFAULT_PREFIX = "classpath:/templates/";
  public static final String DEFAULT_SUFFIX = ".mustache";
  private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");
  private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

  private String prefix = DEFAULT_PREFIX;
  private String suffix = DEFAULT_SUFFIX;
  private String requestContextAttribute;
  private boolean exposeRequestAttributes = false;
  private boolean exposeSessionAttributes = false;
  private boolean allowRequestOverride = false;
  private boolean exposeSpringMacroHelpers = true;
  private boolean allowSessionOverride = false;
  private boolean enabled = true;
  private boolean cache;
  private MimeType contentType = DEFAULT_CONTENT_TYPE;
  private Charset charset = DEFAULT_CHARSET;
  private String[] viewNames;
  private boolean checkTemplateLocation = true;

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setCheckTemplateLocation(boolean checkTemplateLocation) {
    this.checkTemplateLocation = checkTemplateLocation;
  }

  public boolean isCheckTemplateLocation() {
    return this.checkTemplateLocation;
  }

  public String[] getViewNames() {
    return this.viewNames;
  }

  public void setViewNames(String[] viewNames) {
    this.viewNames = viewNames;
  }

  public boolean isCache() {
    return this.cache;
  }

  public void setCache(boolean cache) {
    this.cache = cache;
  }

  public MimeType getContentType() {
    if (this.contentType.getCharset() == null) {
      Map<String, String> parameters = new LinkedHashMap<>();
      parameters.put("charset", this.charset.name());
      parameters.putAll(this.contentType.getParameters());
      return new MimeType(this.contentType, parameters);
    }
    return this.contentType;
  }

  public void setContentType(MimeType contentType) {
    this.contentType = contentType;
  }

  public Charset getCharset() {
    return this.charset;
  }

  public String getCharsetName() {
    return (this.charset != null) ? this.charset.name() : null;
  }

  public void setCharset(Charset charset) {
    this.charset = charset;
  }

  public String getRequestContextAttribute() {
    return this.requestContextAttribute;
  }

  public void setRequestContextAttribute(String requestContextAttribute) {
    this.requestContextAttribute = requestContextAttribute;
  }

  public boolean isExposeRequestAttributes() {
    return this.exposeRequestAttributes;
  }

  public void setExposeRequestAttributes(boolean exposeRequestAttributes) {
    this.exposeRequestAttributes = exposeRequestAttributes;
  }

  public boolean isExposeSessionAttributes() {
    return this.exposeSessionAttributes;
  }

  public void setExposeSessionAttributes(boolean exposeSessionAttributes) {
    this.exposeSessionAttributes = exposeSessionAttributes;
  }

  public boolean isAllowRequestOverride() {
    return this.allowRequestOverride;
  }

  public void setAllowRequestOverride(boolean allowRequestOverride) {
    this.allowRequestOverride = allowRequestOverride;
  }

  public boolean isAllowSessionOverride() {
    return this.allowSessionOverride;
  }

  public void setAllowSessionOverride(boolean allowSessionOverride) {
    this.allowSessionOverride = allowSessionOverride;
  }

  public boolean isExposeSpringMacroHelpers() {
    return this.exposeSpringMacroHelpers;
  }

  public void setExposeSpringMacroHelpers(boolean exposeSpringMacroHelpers) {
    this.exposeSpringMacroHelpers = exposeSpringMacroHelpers;
  }

  public String getPrefix() {
    return this.prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return this.suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  /**
   * Apply the given properties to a {@link AbstractTemplateViewResolver}. Use Object in signature
   * to avoid runtime dependency on MVC, which means that the template engine can be used in a
   * non-web application.
   *
   * @param viewResolver the resolver to apply the properties to.
   */
  public void applyToMvcViewResolver(Object viewResolver) {
    Assert.isInstanceOf(
        AbstractTemplateViewResolver.class,
        viewResolver,
        "ViewResolver is not an instance of AbstractTemplateViewResolver :" + viewResolver);
    AbstractTemplateViewResolver resolver = (AbstractTemplateViewResolver) viewResolver;
    resolver.setPrefix(getPrefix());
    resolver.setSuffix(getSuffix());
    resolver.setCache(isCache());
    if (getContentType() != null) {
      resolver.setContentType(getContentType().toString());
    }
    resolver.setViewNames(getViewNames());
    resolver.setExposeRequestAttributes(isExposeRequestAttributes());
    resolver.setAllowRequestOverride(isAllowRequestOverride());
    resolver.setAllowSessionOverride(isAllowSessionOverride());
    resolver.setExposeSessionAttributes(isExposeSessionAttributes());
    resolver.setExposeSpringMacroHelpers(isExposeSpringMacroHelpers());
    resolver.setRequestContextAttribute(getRequestContextAttribute());
    // The resolver usually acts as a fallback resolver (e.g. like a
    // InternalResourceViewResolver) so it needs to have low precedence
    resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
  }
}
