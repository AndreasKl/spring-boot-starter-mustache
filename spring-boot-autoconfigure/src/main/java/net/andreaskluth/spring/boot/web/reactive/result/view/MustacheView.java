package net.andreaskluth.spring.boot.web.reactive.result.view;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.reactive.result.view.View;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Spring WebFlux {@link View} using the mustache.java template engine. */
public class MustacheView extends AbstractUrlBasedView {

  private MustacheFactory mustacheFactory;
  private String charset;

  /**
   * Set the mustache.java mustacheFactory to be used by this view. Typically this property is not
   * set directly. Instead a single {@link MustacheFactory} is expected in the Spring application
   * context which is used to compile Mustache templates.
   *
   * @param mustacheFactory the mustacheFactory
   */
  public void setMustacheFactory(MustacheFactory mustacheFactory) {
    this.mustacheFactory = mustacheFactory;
  }

  /**
   * Set the charset used for reading Mustache template files.
   *
   * @param charset the charset to use for reading template files
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }

  @Override
  public boolean checkResourceExists(Locale locale) throws Exception {
    return resolveResource() != null;
  }

  @Override
  protected Mono<Void> renderInternal(
      Map<String, Object> model, MediaType contentType, ServerWebExchange exchange) {
    Resource resource = resolveResource();
    if (resource == null) {
      return Mono.error(
          new IllegalStateException(
              "Could not find Mustache template with URL [" + getUrl() + "]"));
    }

    DataBuffer dataBuffer = exchange.getResponse().bufferFactory().allocateBuffer();
    try (Reader reader = getReader(resource)) {
      Mustache mustache = this.mustacheFactory.compile(reader, getUrl());
      Charset charset = getCharset(contentType).orElse(getDefaultCharset());
      try (Writer writer = new OutputStreamWriter(dataBuffer.asOutputStream(), charset)) {
        mustache.execute(writer, model);
        writer.flush();
      }
    } catch (Exception ex) {
      DataBufferUtils.release(dataBuffer);
      return Mono.error(ex);
    }
    return exchange.getResponse().writeWith(Flux.just(dataBuffer));
  }

  private Resource resolveResource() {
    Resource resource = getApplicationContext().getResource(getUrl());
    return !resource.exists() ? null : resource;
  }

  private Reader getReader(Resource resource) throws IOException {
    if (this.charset != null) {
      return new InputStreamReader(resource.getInputStream(), this.charset);
    }
    return new InputStreamReader(resource.getInputStream());
  }

  private Optional<Charset> getCharset(MediaType mediaType) {
    return Optional.ofNullable((mediaType != null) ? mediaType.getCharset() : null);
  }
}
