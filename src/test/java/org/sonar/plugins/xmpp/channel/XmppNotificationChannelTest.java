package org.sonar.plugins.xmpp.channel;

import org.junit.Test;
import org.sonar.api.notifications.Notification;
import org.sonar.plugins.xmpp.config.UserXmppConfiguration;
import org.sonar.plugins.xmpp.config.XmppConfigurationFinder;
import org.sonar.plugins.xmpp.gateway.XmppGateway;
import org.sonar.plugins.xmpp.gateway.XmppGatewayFactory;
import org.sonar.plugins.xmpp.message.XmppMessageContent;
import org.sonar.plugins.xmpp.message.XmppMessageFactory;

import static org.mockito.Mockito.*;

public class XmppNotificationChannelTest {

    @Test
    public void shouldNotSendAnythingIfAddressNotConfiguredForUser() {
        XmppConfigurationFinder configFinder = mock(XmppConfigurationFinder.class);
        XmppMessageFactory messageFactory = mock(XmppMessageFactory.class);
        XmppGatewayFactory gatewayFactory = mock(XmppGatewayFactory.class);
        Notification notification = mock(Notification.class);
        UserXmppConfiguration configuration = mock(UserXmppConfiguration.class);
        when(configFinder.getConfiguration(anyString())).thenReturn(configuration);
        when(configuration.getAddress()).thenReturn(null);
        XmppNotificationChannel channel = new XmppNotificationChannel(configFinder, messageFactory, gatewayFactory);

        channel.deliver(notification, "user");

        verify(configFinder, times(1)).getConfiguration(anyString());
        verify(messageFactory, never()).create(any(Notification.class));
        verify(gatewayFactory, never()).create(anyString());
    }

    @Test
    public void shouldSendOneMessageForUserWithConfiguredAddress() {
        XmppConfigurationFinder configFinder = mock(XmppConfigurationFinder.class);
        XmppMessageFactory messageFactory = mock(XmppMessageFactory.class);
        XmppGatewayFactory gatewayFactory = mock(XmppGatewayFactory.class);
        Notification notification = mock(Notification.class);
        UserXmppConfiguration configuration = mock(UserXmppConfiguration.class);
        when(configFinder.getConfiguration(anyString())).thenReturn(configuration);
        String address = "address@jabber-server.com";
        when(configuration.getAddress()).thenReturn(address);
        XmppMessageContent message = mock(XmppMessageContent.class);
        when(messageFactory.create(notification)).thenReturn(message);
        XmppGateway gateway = mock(XmppGateway.class);
        when(gatewayFactory.create(address)).thenReturn(gateway);

        XmppNotificationChannel channel = new XmppNotificationChannel(configFinder, messageFactory, gatewayFactory);

        channel.deliver(notification, "user");

        verify(configFinder, times(1)).getConfiguration(anyString());
        verify(messageFactory, times(1)).create(notification);
        verify(gatewayFactory, times(1)).create(address);
        verify(gateway, times(1)).send(message);
    }
}