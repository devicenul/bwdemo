package net.merc.bandwidth.demo.rest;

/**
 * Copyright (c) 2018, rrjefferson@gmail.com under the MIT license.
 * See LICENSE.md for details.
 */

public class DemoForm {

    private String sourceDN;
    private String customMessage;

    public String getSourceDN() {
        return sourceDN;
    }

    public void setSourceDN(final String sourceDN) {
        this.sourceDN = sourceDN;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(final String customMessage) {
        this.customMessage = customMessage;
    }
}
