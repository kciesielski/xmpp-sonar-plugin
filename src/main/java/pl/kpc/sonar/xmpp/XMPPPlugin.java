package pl.kpc.sonar.xmpp;

import org.sonar.api.SonarPlugin;
import pl.kpc.sonar.xmpp.view.ConfigPage;

import java.util.Arrays;
import java.util.List;

/**
 * Main plugin class.
 */
public class XMPPPlugin extends SonarPlugin {

    public List getExtensions() {
        return Arrays.asList(ConfigPage.class);
    }
    public String toString() {
        return getKey();
    }
}
