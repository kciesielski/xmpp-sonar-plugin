package org.sonar.plugins.xmpp.gateway;

public class XmppConnectionException extends RuntimeException {
    public XmppConnectionException(Exception exception) {
        super(exception);
    }
}