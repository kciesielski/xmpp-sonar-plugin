package org.sonar.plugins.xmpp.gateway;


import org.sonar.api.ServerExtension;
import org.sonar.plugins.xmpp.config.ServerXmppConfiguration;
import org.sonar.plugins.xmpp.gateway.smack.XmppGateway;

public interface XmppGatewayFactory extends ServerExtension {

    XmppGateway create(ServerXmppConfiguration serverConfiguration);
}
