package org.sonar.plugins.xmpp;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;
import org.sonar.plugins.xmpp.channel.XmppNotificationChannel;
import org.sonar.plugins.xmpp.config.XmppConfigurationFinder;
import org.sonar.plugins.xmpp.config.XmppConstants;
import org.sonar.plugins.xmpp.gateway.smack.SmackXmppGatewayFactory;
import org.sonar.plugins.xmpp.message.NewViolationsXmppMessageFactory;

import java.util.Arrays;
import java.util.List;

@Properties({

        @Property(
                key = XmppConstants.XMPP_SERVER_PROPERTY,
                defaultValue = "localhost",
                name = "XMPP Server host",
                description = "Hostname of the XMPP server",
                global = true),
        @Property(
                key = XmppConstants.XMPP_SONAR_USER_PROPERTY,
                defaultValue = "sonar",
                name = "Username",
                description = "Sonar username on the XMPP server",
                global = true),
        @Property(
                key = XmppConstants.XMPP_USER_ADDRESSES,
                defaultValue = "",
                name = "User addresses",
                description = "Comma-separated list of user XMPP adresses. Example: john=johndoe@jabber.com,ann=annrice@someserver.com",
                global = true),
        @Property(
                key = XmppConstants.XMPP_PASSWORD_PROPERTY,
                type = PropertyType.PASSWORD,
                name = "Password",
                defaultValue = "",
                description = "Password for Sonar user on the XMPP server",
                global = true)})
public class XmppPlugin extends SonarPlugin {

    public List getExtensions() {
        return Arrays.asList(XmppNotificationChannel.class,

                XmppConfigurationFinder.class, SmackXmppGatewayFactory.class, NewViolationsXmppMessageFactory.class);
    }

}
