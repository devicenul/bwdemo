package net.merc.bandwidth.demo.bwclient;

/**
 * Copyright (c) 2018, rrjefferson@gmail.com under the MIT license.
 * See LICENSE.md for details.
 */

public interface IBandwidthClient {
    boolean submitSmsMessage(final String message);
    boolean submitCall(final String sourceDN);
}
