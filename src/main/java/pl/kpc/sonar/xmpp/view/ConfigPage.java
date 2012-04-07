package pl.kpc.sonar.xmpp.view;

import org.sonar.api.web.GwtPage;

public class ConfigPage extends GwtPage
{
    @Override
    public String getGwtId() {
        return getClass().getName();
    }

    @Override
    public String getTitle() {
        return "Config";
    }
}
