package org.sonar.plugins.xmpp.gateway;

import org.jivesoftware.smack.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.plugins.xmpp.config.ServerXmppConfiguration;
import org.sonar.plugins.xmpp.config.UserXmppConfiguration;
import org.sonar.plugins.xmpp.message.XmppMessageContent;

import static org.mockito.Mockito.*;

public class XmppGatewayTest {

    private static final String AUTHENTICATION_EXCEPTION = "Authentication exception";
    private static final String SENDING_EXCEPTION = "Sending exception";
    private static final String CONNECTION_EXCEPTION = "Connection exception";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsExceptionOnConnectionError() throws XMPPException {

        XMPPConnection connection = mock(XMPPConnection.class);
        doThrow(new XMPPException(CONNECTION_EXCEPTION)).when(connection).connect();

        ServerXmppConfiguration configuration = mock(ServerXmppConfiguration.class);
        thrown.expect(XmppConnectionException.class);
        thrown.expectMessage(CONNECTION_EXCEPTION);

        new XmppGateway(connection, configuration);
    }

    @Test
    public void throwsExceptionOnAuthenticationError() throws XMPPException {

        XMPPConnection connection = mock(XMPPConnection.class);
        doThrow(new XMPPException(AUTHENTICATION_EXCEPTION)).when(connection).login(anyString(), anyString());

        ServerXmppConfiguration configuration = mock(ServerXmppConfiguration.class);
        thrown.expect(XmppConnectionException.class);
        thrown.expectMessage(AUTHENTICATION_EXCEPTION);

        new XmppGateway(connection, configuration);
    }

    @Test
    public void throwsExceptionOnSendingError() throws XMPPException {

        XMPPConnection connection = mock(XMPPConnection.class);
        ChatManager chatManager = mock(ChatManager.class);
        when(connection.getChatManager()).thenReturn(chatManager);
        Chat chat = mock(Chat.class);
        when(chatManager.createChat(anyString(), any(MessageListener.class))).thenReturn(chat);
        doThrow(new XMPPException(SENDING_EXCEPTION)).when(chat).sendMessage(anyString());
        ServerXmppConfiguration configuration = mock(ServerXmppConfiguration.class);
        thrown.expect(XmppConnectionException.class);
        thrown.expectMessage(SENDING_EXCEPTION);

        XmppMessageContent message = mock(XmppMessageContent.class);
        when(message.getText()).thenReturn("text");
        UserXmppConfiguration userConfiguration = mock(UserXmppConfiguration.class);
        when(userConfiguration.getAddress()).thenReturn("receiver@server.com");

        new XmppGateway(connection, configuration).send(userConfiguration, message);
    }

    @Test
    public void shouldCloseInternalResource() {
        XMPPConnection connection = mock(XMPPConnection.class);
        ServerXmppConfiguration configuration = mock(ServerXmppConfiguration.class);

        new XmppGateway(connection, configuration).close();

        verify(connection, times(1)).disconnect();
    }

    @Test
    public void shouldSendCorrectMessage() throws XMPPException {

        XMPPConnection connection = mock(XMPPConnection.class);
        ChatManager chatManager = mock(ChatManager.class);
        when(connection.getChatManager()).thenReturn(chatManager);
        Chat chat = mock(Chat.class);
        when(chatManager.createChat(anyString(), any(MessageListener.class))).thenReturn(chat);
        ServerXmppConfiguration configuration = mock(ServerXmppConfiguration.class);

        XmppMessageContent message = mock(XmppMessageContent.class);
        when(message.getText()).thenReturn("text");
        UserXmppConfiguration userConfiguration = mock(UserXmppConfiguration.class);
        when(userConfiguration.getAddress()).thenReturn("receiver@server.com");

        new XmppGateway(connection, configuration).send(userConfiguration, message);

        verify(chat, times(1)).sendMessage("text");
    }

}
