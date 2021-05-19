package nl.manthos.manwarps;

import nl.manthos.manwarps.commands.user.ManWarpsCommand;
import nl.manthos.manwarps.config.PlayerConfig;
import nl.manthos.manwarps.config.WarpConfig;
import nl.manthos.manwarps.events.ChatEvent;
import nl.manthos.manwarps.panel.Panel;
import nl.manthos.manwarps.panel.user.UserHomePanel;
import nl.manthos.manwarps.util.ConsoleLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import site.phoenixthedev.panelapi.PhoPanelManager;
import site.phoenixthedev.phologger.loggers.PhoConsoleLogger;
import site.phoenixthedev.phologger.loggers.PhoLogger;

import java.util.*;

public final class ManWarps extends JavaPlugin {

    public ConsoleLogger logger;

    public PhoPanelManager phoPanelManager;
    public WarpConfig config;
    public PlayerConfig playerConfig;
    private PhoLogger phoLogger;
    public List<UUID> panelSetWarp;
    public List<UUID> panelSetWarp2;
    public List<UUID> panelSetStandardWarp;
    public HashMap<UUID, String> messageSetWarp;

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.phoLogger = new PhoConsoleLogger("" + ChatColor.WHITE + ChatColor.BOLD + "[" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "ManWarps" + ChatColor.WHITE + ChatColor.BOLD + "]");
        this.phoPanelManager = new PhoPanelManager(this.phoLogger);
        this.config = new WarpConfig(this);
        this.playerConfig = new PlayerConfig(this);
        this.logger = new ConsoleLogger("" + ChatColor.WHITE + "[" + ChatColor.DARK_BLUE + ChatColor.BOLD + "ExternalPackage's" + ChatColor.WHITE + "]");

        this.panelSetWarp = new ArrayList();
        this.panelSetWarp2 = new ArrayList();
        this.panelSetStandardWarp = new ArrayList<>();
        this.messageSetWarp = new HashMap<>();

        registerListeners(
                new ChatEvent(this)
        );

        getCommand("manwarps").setExecutor(new ManWarpsCommand(this));

        phoPanelManager.registerPanel(Panel.USER_HOME_PANEL.getKey(), new UserHomePanel(this.phoPanelManager, this));
        phoPanelManager.register(this);
    }

    @Override
    public void onDisable() {
        this.panelSetWarp.clear();
        this.panelSetWarp2.clear();
        this.messageSetWarp.clear();
    }

    public void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener ->
                Bukkit.getPluginManager().registerEvents(listener, this)
        );
    }
}
