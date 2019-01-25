package net.andreaskluth.spring.boot.autoconfigure.mustache.java;

import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties} for Mustache.
 */
@ConfigurationProperties(prefix = "spring.mustachejava")
public class MustacheProperties extends AbstractTemplateViewResolverProperties {

    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".mustache";

    /**
     * Prefix to apply to template names.
     */
    private String prefix = DEFAULT_PREFIX;

    /**
     * Suffix to apply to template names.
     */
    private String suffix = DEFAULT_SUFFIX;

    public MustacheProperties() {
        super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
