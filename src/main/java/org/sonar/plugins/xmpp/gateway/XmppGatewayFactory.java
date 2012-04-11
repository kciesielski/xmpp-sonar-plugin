package org.sonar.plugins.xmpp.gateway;

import org.sonar.api.ServerExtension;

public class XmppGatewayFactory implements ServerExtension {
    public XmppGateway create(String address) {
        return new XmppGateway(address);
    }
}
