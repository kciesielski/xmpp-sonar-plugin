package org.sonar.plugins.xmpp.message;

public class XmppMessageContent {
    private String text;

    public XmppMessageContent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
