package org.sonar.plugins.xmpp.gateway;

import org.sonar.api.ServerExtension;
import org.sonar.plugins.xmpp.config.ServerXmppConfiguration;

public class XmppGatewayFactory implements ServerExtension {

    public XmppGateway create(ServerXmppConfiguration serverConfiguration) {
        return new XmppGateway(serverConfiguration);
    }
}
