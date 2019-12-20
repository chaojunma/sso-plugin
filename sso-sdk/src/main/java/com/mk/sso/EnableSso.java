package com.mk.sso;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Import({SsoAutoConfiguration.class})
@EnableConfigurationProperties({SsoProperties.class})
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableSso {
}
