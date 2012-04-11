package org.sonar.plugins.xmpp.gateway;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.xmpp.message.XmppMessageContent;

public class XmppGateway {

    private static final Logger LOG = LoggerFactory.getLogger(XmppGateway.class);

    private String address;

    public XmppGateway(String address) {
        this.address = address;
    }

    public void send(XmppMessageContent notification) {

        // TODO
        LOG.info("Sending XMPP message: " + notification.getText());
    }
}
