package io.pebbletemplates.boot.autoconfigure;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.spring.reactive.PebbleReactiveViewResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.REACTIVE)
class PebbleReactiveWebConfiguration extends AbstractPebbleConfiguration {

  @Bean
  @ConditionalOnMissingBean(name = "pebbleReactiveViewResolver")
  PebbleReactiveViewResolver pebbleReactiveViewResolver(PebbleProperties properties,
      PebbleEngine pebbleEngine) {
    String prefix = properties.getPrefix();
    if (pebbleEngine.getLoader() instanceof ClasspathLoader) {
      // classpathloader doesn't like leading slashes in paths
      prefix = this.stripLeadingSlash(properties.getPrefix());
    }
    PebbleReactiveViewResolver resolver = new PebbleReactiveViewResolver(pebbleEngine);
    resolver.setPrefix(prefix);
    resolver.setSuffix(properties.getSuffix());
    resolver.setViewNames(properties.getViewNames());
    resolver.setRequestContextAttribute(properties.getRequestContextAttribute());
    return resolver;
  }
}
