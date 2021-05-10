package nl.manthos.manwarps.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ConsoleLogger {

    private CommandSender sender;
    private String header;

    public ConsoleLogger(String header) {
        this.sender = Bukkit.getConsoleSender();
        this.header = header;
    }

    public void log(String message) {
        sender.sendMessage(header + " " + ChatColor.WHITE + message);
    }

    public void debug(String message) {
        sender.sendMessage(header + ChatColor.GOLD + ChatColor.BOLD + " [Debug] " + ChatColor.WHITE + message);
    }

    public void warning(String message) {
        sender.sendMessage(header + ChatColor.GOLD + ChatColor.BOLD + " [WARNING] " + ChatColor.WHITE + message);
    }

    public void error(String message) {
        sender.sendMessage(header + ChatColor.RED + ChatColor.BOLD + " [ERROR] " + ChatColor.WHITE + message);
    }

    public void fatal(String message) {
        sender.sendMessage(header + ChatColor.DARK_RED + ChatColor.BOLD + " [FATAL] " + ChatColor.DARK_RED + message);
    }
}
