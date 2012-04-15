package org.sonar.plugins.xmpp.message;


import org.sonar.api.notifications.Notification;

public class SimpleXmppMessageFactory implements XmppMessageFactory {

    public SimpleXmppMessage create(Notification notification) {
        return new SimpleXmppMessage("Hello from plugin! Notification: " + notification.getType());
    }
}
