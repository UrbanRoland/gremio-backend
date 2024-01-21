package com.gremio.util;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Custom key generator for caching.
 */
@Component
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(final Object target, final Method method, final Object... params) {
        return target.getClass().getSimpleName() + "_"
            + method.getName() + "_"
            + StringUtils.arrayToDelimitedString(params, "_");
    }
}