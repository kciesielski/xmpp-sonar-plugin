package org.sonar.plugins.xmpp.gateway;


import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.xmpp.config.ServerXmppConfiguration;
import org.sonar.plugins.xmpp.config.UserXmppConfiguration;
import org.sonar.plugins.xmpp.message.XmppMessageContent;

public class XmppGateway {

    private static final Logger LOG = LoggerFactory.getLogger(XmppGateway.class);

    private Connection connection;

    public XmppGateway(ServerXmppConfiguration serverConfiguration) {
        this(new XMPPConnection(serverConfiguration.getTargetAddress()), serverConfiguration);

    }

    XmppGateway(Connection connection, ServerXmppConfiguration serverConfiguration) {
        String userName = serverConfiguration.getUserName();
        String password = serverConfiguration.getPassword();
        try {
            connection.connect();
            connection.login(userName, password);
        } catch (XMPPException exception) {
            throw new XmppConnectionException(exception);
        }

    }


    public void send(UserXmppConfiguration userConfiguration, XmppMessageContent notification) {
        String receiverAddress = userConfiguration.getAddress();
        try {
            ChatManager chatmanager = connection.getChatManager();
            Chat newChat = chatmanager.createChat(receiverAddress, new MessageListener() {
                public void processMessage(Chat chat, Message message) {
                }
            });

            newChat.sendMessage(notification.getText());
        } catch (XMPPException e) {
            throw new XmppConnectionException(e);
        }

        LOG.debug("Sent XMPP message: " + notification.getText());
    }

    public void close() {
        connection.disconnect();
    }
}
