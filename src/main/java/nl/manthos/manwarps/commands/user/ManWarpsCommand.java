package nl.manthos.manwarps.commands.user;

import nl.manthos.manwarps.ManWarps;
import nl.manthos.manwarps.config.WarpConfig;
import nl.manthos.manwarps.items.HeadItem;
import nl.manthos.manwarps.manager.ManWarpsManager;
import nl.manthos.manwarps.panel.Panel;
import nl.manthos.manwarps.util.Formatting;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import site.phoenixthedev.panelapi.PhoPanelManager;
import site.phoenixthedev.panelapi.session.SessionData;

import java.util.List;

public class ManWarpsCommand implements CommandExecutor {

    private ManWarps main;
    private WarpConfig config;
    private PhoPanelManager phoPanelManager;


    public ManWarpsCommand(ManWarps main) {
        this.main = main;
        this.phoPanelManager = main.phoPanelManager;
        this.config = main.config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {

                phoPanelManager.openPanel(new SessionData(player), Panel.USER_HOME_PANEL.getKey());
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    this.helpMethod(player);

                } else if (args[0].equalsIgnoreCase("menu")) {
                    phoPanelManager.openPanel(new SessionData(player), Panel.USER_HOME_PANEL.getKey());

                } else {
                    if (config.exists(args[0].toLowerCase())) {
                        player.teleport(this.config.getWarp(args[0].toLowerCase()));
                        player.sendMessage(Formatting.format("&9&lMANWARPS &7- You have been teleported to &9" + args[0] + "&7."));
                    } else {
                        player.sendMessage(Formatting.format("&9&lMANWARPS &7- A warp with the name &9" + args[0] + " &7does not exist!"));
                    }
                }
            } else if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (this.config.exists(args[1].toLowerCase())) {

                        player.sendMessage(Formatting.format("&9&lMANWARPS &7- Do you want to set the warp with the name &9" + args[1] + " &7to a new location? (&aYes&7/&cNo&7)"));
                        this.main.panelSetWarp2.add(player.getUniqueId());
                        this.main.messageSetWarp.put(player.getUniqueId(), args[1]);
                        player.closeInventory();
                    } else {
                        if (args[0].equalsIgnoreCase("set")) {
                            player.sendMessage(Formatting.format("&9&lMANWARPS &7- You can't choose that name!"));

                        } else {
                            config.setNewWarp(args[1].toLowerCase(), player.getLocation());
                            player.sendMessage(Formatting.format("&9&lMANWARPS &7- You have set a new warp with the name &9" + args[1] + "&7."));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    final String page = args[1];
                    int pagina;
                    try {
                        pagina = Integer.parseInt(page);
                    } catch (NumberFormatException e) {
                        player.sendMessage(Formatting.format("&9&lManWarps &7- This is not a valid number!"));
                        return true;
                    }
                    -- pagina;
                    this.listMethod(player, pagina);
                }
            }
        } else {
            System.out.println("[ManWarps] You can only use this command in-game!");
        }
        return true;
    }

    void listMethod(CommandSender player, int page) {
        final ManWarpsManager manager = new ManWarpsManager();
        final List<String> warps = config.getWarps();
        final int perPage = 10;
        final double pages = warps.size() / perPage;
        if ((pages < 1.0 && page > 1) || page > pages) {
            player.sendMessage(Formatting.format("&9&lMANWARPS &7- There is no page with that number!"));
            return;
        }
        player.sendMessage(Formatting.format("&1&m----------&r &9&lManWarps &7[" + (page + 1) + "/" + ((int) pages + 1) + "] &1&m----------"));
        int start = page * perPage;
        if (start < 0) {
            start = 0;
        }
        for (int cap = start + perPage, i = start; i < cap; ++i) {
            String warp;
            try {
                warp = warps.get(i);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                break;
            }
            player.sendMessage(Formatting.format("&1- &9Warp: &7" + warp));
        }
    }
    //                      ITEMSTACKS

    private ItemStack setWarp() {
        ItemStack is = new ItemStack(HeadItem.ARROW_DOWN_BLUE.head());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format("&2&lSet New Warp"));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }


    void helpMethod(CommandSender player) {
        player.sendMessage(Formatting.format("&1&m----------&r &9&lManWarps &7" + main.getDescription().getVersion() + " &1&m----------"));
        player.sendMessage(Formatting.format(" &7&o" + main.getDescription().getDescription()));
        player.sendMessage("");
        player.sendMessage(Formatting.format("&f/&9manwarps help &7- Shows this message."));
        player.sendMessage(Formatting.format("&f/&9manwarps <warpname> &7- Teleport to a warp."));
        player.sendMessage(Formatting.format("&f/&9manwarps set <warpname> &7- Set a new warp."));
        player.sendMessage(Formatting.format("&f/&9manwarps list <pagenumber> &7- Lists all current warps."));
    }
}
