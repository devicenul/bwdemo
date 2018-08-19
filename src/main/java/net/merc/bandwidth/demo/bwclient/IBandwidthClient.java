package net.merc.bandwidth.demo.bwclient;

public interface IBandwidthClient {
    boolean submitSmsMessage(final String message);
    boolean submitCall(final String sourceDN);
}
