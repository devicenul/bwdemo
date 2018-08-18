package net.merc.bandwidth.demo.rest;

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
