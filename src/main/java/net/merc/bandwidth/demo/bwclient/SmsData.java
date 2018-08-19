package net.merc.bandwidth.demo.bwclient;

/**
 * Copyright (c) 2018, rrjefferson@gmail.com under the MIT license.
 * See LICENSE.md for details.
 */

public class SmsData {
    private String from;
    private String to;
    private String text;

    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
