package net.andreaskluth.spring.boot.autoconfigure.mustachejava;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

/** {@link ConfigurationProperties} for mustache.java. */
@ConfigurationProperties(prefix = "mustachejava")
public class MustacheProperties {

  public static final String DEFAULT_PREFIX = "classpath:/templates/";
  public static final String DEFAULT_SUFFIX = ".mustache";
  private static final MimeType DEFAULT_CONTENT_TYPE = MimeTypeUtils.TEXT_HTML;
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
  private boolean cache = true;
  private MimeType contentType = DEFAULT_CONTENT_TYPE;
  private Charset charset = DEFAULT_CHARSET;
  private String[] viewNames;
  private boolean checkTemplateLocation = true;

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setCheckTemplateLocation(boolean checkTemplateLocation) {
    this.checkTemplateLocation = checkTemplateLocation;
  }

  public boolean isCheckTemplateLocation() {
    return checkTemplateLocation;
  }

  public String[] getViewNames() {
    return viewNames;
  }

  public void setViewNames(String[] viewNames) {
    this.viewNames = viewNames;
  }

  public boolean isCache() {
    return cache;
  }

  public void setCache(boolean cache) {
    this.cache = cache;
  }

  public MimeType getContentType() {
    if (contentType.getCharset() == null) {
      Map<String, String> parameters = new LinkedHashMap<>();
      parameters.put("charset", charset.name());
      parameters.putAll(contentType.getParameters());
      return new MimeType(contentType, parameters);
    }
    return contentType;
  }

  public void setContentType(MimeType contentType) {
    this.contentType = contentType;
  }

  public Charset getCharset() {
    return charset;
  }

  public String getCharsetName() {
    return (charset != null) ? charset.name() : null;
  }

  public void setCharset(Charset charset) {
    this.charset = charset;
  }

  public String getRequestContextAttribute() {
    return requestContextAttribute;
  }

  public void setRequestContextAttribute(String requestContextAttribute) {
    this.requestContextAttribute = requestContextAttribute;
  }

  public boolean isExposeRequestAttributes() {
    return exposeRequestAttributes;
  }

  public void setExposeRequestAttributes(boolean exposeRequestAttributes) {
    this.exposeRequestAttributes = exposeRequestAttributes;
  }

  public boolean isExposeSessionAttributes() {
    return exposeSessionAttributes;
  }

  public void setExposeSessionAttributes(boolean exposeSessionAttributes) {
    this.exposeSessionAttributes = exposeSessionAttributes;
  }

  public boolean isAllowRequestOverride() {
    return allowRequestOverride;
  }

  public void setAllowRequestOverride(boolean allowRequestOverride) {
    this.allowRequestOverride = allowRequestOverride;
  }

  public boolean isAllowSessionOverride() {
    return allowSessionOverride;
  }

  public void setAllowSessionOverride(boolean allowSessionOverride) {
    this.allowSessionOverride = allowSessionOverride;
  }

  public boolean isExposeSpringMacroHelpers() {
    return exposeSpringMacroHelpers;
  }

  public void setExposeSpringMacroHelpers(boolean exposeSpringMacroHelpers) {
    this.exposeSpringMacroHelpers = exposeSpringMacroHelpers;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }
}
