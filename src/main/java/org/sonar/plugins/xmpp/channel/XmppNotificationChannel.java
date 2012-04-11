package org.sonar.plugins.xmpp.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.notifications.Notification;
import org.sonar.api.notifications.NotificationChannel;
import org.sonar.plugins.xmpp.config.UserXmppConfiguration;
import org.sonar.plugins.xmpp.config.XmppConfigurationFinder;
import org.sonar.plugins.xmpp.gateway.XmppGateway;
import org.sonar.plugins.xmpp.gateway.XmppGatewayFactory;
import org.sonar.plugins.xmpp.message.XmppMessageContent;
import org.sonar.plugins.xmpp.message.XmppMessageFactory;

public class XmppNotificationChannel extends NotificationChannel {
    private static final Logger LOG = LoggerFactory.getLogger(XmppNotificationChannel.class);
    private XmppConfigurationFinder configurationFinder;
    private XmppMessageFactory messageFactory;
    private XmppGatewayFactory gatewayFactory;

    public XmppNotificationChannel(XmppConfigurationFinder configurationFinder, XmppMessageFactory messageFactory, XmppGatewayFactory gatewayFactory) {
        this.configurationFinder = configurationFinder;
        this.messageFactory = messageFactory;
        this.gatewayFactory = gatewayFactory;
    }

    @Override
    public void deliver(Notification notification, String userName) {

        String address = getUserXmppAddress(userName);
        if (address == null) {
            LOG.info("XMPP address not defined for " + userName);
            return;
        }
        LOG.info("XMPP notification for  " + userName);
        sendNotification(notification, address);
    }

    private void sendNotification(Notification notification, String address) {
        XmppGateway gateway = gatewayFactory.create(address);
        XmppMessageContent message = messageFactory.create(notification);
        gateway.send(message);
    }

    private String getUserXmppAddress(String userName) {
        UserXmppConfiguration configuration = configurationFinder.getConfiguration(userName);
        return configuration.getAddress();

    }

}