package net.merc.bandwidth.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Copyright (c) 2018, rrjefferson@gmail.com under the MIT license.
 * See LICENSE.md for details.
 */

@Configuration
@PropertySource("classpath:application.properties")
public class NumberConfig implements INumberConfig {
    @Value("${number.sourceDN}")
    private String sourceDN;
    @Value("${number.destDN}")
    private String destDN;

    @Override
    public String getSourceDN() {
        return sourceDN;
    }

    @Override
    public String getDestDN() {
        return destDN;
    }
}
