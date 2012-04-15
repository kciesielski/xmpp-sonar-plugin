package org.sonar.plugins.xmpp.message;

class SimpleXmppMessage implements XmppMessage {
    private String text;

    public SimpleXmppMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
