package nl.manthos.manwarps;

import nl.manthos.manwarps.commands.user.ManWarpsCommand;
import nl.manthos.manwarps.config.WarpConfig;
import nl.manthos.manwarps.util.ConsoleLogger;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import site.phoenixthedev.panelapi.PhoPanelManager;
import site.phoenixthedev.phologger.loggers.PhoConsoleLogger;
import site.phoenixthedev.phologger.loggers.PhoLogger;

public final class ManWarps extends JavaPlugin {

    public ConsoleLogger logger;

    public PhoPanelManager phoPanelManager;
    public WarpConfig config;
    private PhoLogger phoLogger;

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.phoLogger = new PhoConsoleLogger("" + ChatColor.WHITE + ChatColor.BOLD + "[" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "ManWarps" + ChatColor.WHITE + ChatColor.BOLD + "]");
        this.phoPanelManager = new PhoPanelManager(this.phoLogger);
        this.config = new WarpConfig(this);
        this.logger = new ConsoleLogger("" + ChatColor.WHITE + "[" + ChatColor.DARK_BLUE + ChatColor.BOLD + "ExternalPackage's" + ChatColor.WHITE + "]");

        getCommand("manwarps").setExecutor(new ManWarpsCommand(this));

        phoPanelManager.register(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
