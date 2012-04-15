package org.sonar.plugins.xmpp.gateway.smack;

import org.sonar.plugins.xmpp.config.ServerXmppConfiguration;
import org.sonar.plugins.xmpp.gateway.XmppGatewayFactory;

public class SmackXmppGatewayFactory implements XmppGatewayFactory {

    public XmppGateway create(ServerXmppConfiguration serverConfiguration) {
        return new SmackXmppGateway(serverConfiguration);
    }
}
