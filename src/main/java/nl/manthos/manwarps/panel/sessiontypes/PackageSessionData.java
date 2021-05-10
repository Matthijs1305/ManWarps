package nl.manthos.manwarps.panel.sessiontypes;

import org.bukkit.entity.Player;
import site.phoenixthedev.panelapi.session.BaseSessionData;

public class PackageSessionData extends BaseSessionData {

    public String id;

    public PackageSessionData(Player owner, String id) {
        super(owner);
        this.id = id;
    }
}
