package org.sonar.plugins.xmpp.gateway.smack;

import org.sonar.plugins.xmpp.config.UserXmppConfiguration;
import org.sonar.plugins.xmpp.message.XmppMessage;

public interface XmppGateway {

    void send(UserXmppConfiguration userConfiguration, XmppMessage notification);

}
