package org.sonar.plugins.xmpp.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.notifications.Notification;
import org.sonar.api.notifications.NotificationChannel;
import org.sonar.plugins.xmpp.config.IncompleteXmppConfigurationException;
import org.sonar.plugins.xmpp.config.ServerXmppConfiguration;
import org.sonar.plugins.xmpp.config.UserXmppConfiguration;
import org.sonar.plugins.xmpp.config.XmppConfigurationFinder;
import org.sonar.plugins.xmpp.gateway.XmppGatewayFactory;
import org.sonar.plugins.xmpp.gateway.smack.XmppGateway;
import org.sonar.plugins.xmpp.message.DefaultXmppMessageFactory;
import org.sonar.plugins.xmpp.message.XmppMessage;
import org.sonar.plugins.xmpp.message.XmppMessageFactory;

public class XmppNotificationChannel extends NotificationChannel {
    private static final Logger LOG = LoggerFactory.getLogger(XmppNotificationChannel.class);

    private XmppConfigurationFinder configurationFinder;
    private XmppMessageFactory[] messageFactories;
    private XmppGatewayFactory gatewayFactory;

    public XmppNotificationChannel(XmppConfigurationFinder configurationFinder, XmppMessageFactory[] messageFactories, XmppGatewayFactory gatewayFactory) {
        this.configurationFinder = configurationFinder;
        this.messageFactories = messageFactories;
        this.gatewayFactory = gatewayFactory;
    }

    @Override
    public void deliver(Notification notification, String userName) {

        UserXmppConfiguration userConfiguration;
        ServerXmppConfiguration serverConfiguration;
        try {
            userConfiguration = configurationFinder.getUserConfiguration(userName);
            serverConfiguration = configurationFinder.getServerConfiguration();
        } catch (IncompleteXmppConfigurationException exception) {
            LOG.debug("Could not send XMPP notification", exception);
            return;
        }
        LOG.info("XMPP notification for  " + userName);
        sendNotification(notification, serverConfiguration, userConfiguration);

    }

    private void sendNotification(Notification notification, ServerXmppConfiguration serverConfiguration, UserXmppConfiguration userConfiguration) {
        XmppGateway gateway = gatewayFactory.create(serverConfiguration);
        XmppMessageFactory messageFactory = findMatchingFactory(notification);
        XmppMessage message = messageFactory.create(notification);
        gateway.send(userConfiguration, message);
    }

    private XmppMessageFactory findMatchingFactory(Notification notification) {
        for (XmppMessageFactory factory : messageFactories) {
            if (factory.matches(notification)) {
                return factory;
            }
        }
        return new DefaultXmppMessageFactory();
    }

}