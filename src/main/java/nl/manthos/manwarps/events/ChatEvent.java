package nl.manthos.manwarps.events;

import nl.manthos.manwarps.ManWarps;
import nl.manthos.manwarps.config.PlayerConfig;
import nl.manthos.manwarps.config.WarpConfig;
import nl.manthos.manwarps.util.Formatting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    private ManWarps main;
    private WarpConfig warpConfig;
    private PlayerConfig playerConfig;

    public ChatEvent(ManWarps main) {
        this.main = main;
        this.warpConfig = main.config;
        this.playerConfig = main.playerConfig;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = (Player) e.getPlayer();

        if (this.main.panelSetWarp.contains(player.getUniqueId())) {
            if (e.getMessage().equalsIgnoreCase("cancel")) {
                e.setCancelled(true);
                this.main.panelSetWarp.remove(player.getUniqueId());

            } else {
                if (this.warpConfig.exists(e.getMessage().toLowerCase())) {
                    player.sendMessage(Formatting.format("&9&lMANWARPS &7- Do you want to set the warp with the name &9" + e.getMessage() + " &7to a new location? (&aYes&7/&cNo&7)"));
                    this.main.panelSetWarp2.add(player.getUniqueId());
                    this.main.messageSetWarp.put(player.getUniqueId(), e.getMessage());
                    this.main.panelSetWarp.remove(player.getUniqueId());
                    e.setCancelled(true);

                } else {
                    this.warpConfig.setNewWarp(e.getMessage().toLowerCase(), player.getLocation());
                    player.sendMessage(Formatting.format("&9&lMANWARPS &7- You have set a new warp with the name &9" + e.getMessage() + "&7."));
                    this.main.panelSetWarp.remove(player.getUniqueId());
                    e.setCancelled(true);
                }
            }
        } else if (this.main.panelSetWarp2.contains(player.getUniqueId())) {
            if (e.getMessage().equalsIgnoreCase("yes")) {
                String name = this.main.messageSetWarp.get(player.getUniqueId());

                player.sendMessage(Formatting.format("&9&lMANWARPS &7- You have set a new warp with the name &9" + name + "&7."));
                this.warpConfig.setNewWarp(name.toLowerCase(), player.getLocation());
                this.main.panelSetWarp2.remove(player.getUniqueId());
                this.main.messageSetWarp.remove(player.getUniqueId());
                e.setCancelled(true);
            } else if (e.getMessage().equalsIgnoreCase("no")) {
                String name = this.main.messageSetWarp.get(player.getUniqueId());

                this.main.panelSetWarp2.remove(player.getUniqueId());
                player.sendMessage(Formatting.format("&9&lMANWARPS &7- Cancel succesfull."));
                e.setCancelled(true);
            }
        } else if (this.main.panelSetStandardWarp.contains(player.getUniqueId())) {
            String message = e.getMessage().toLowerCase();
            String rawMessage = e.getMessage();
            if (this.warpConfig.exists(message)) {
                if (this.playerConfig.contains(player)) {

                    this.playerConfig.setStandardWarp(player, message);
                    player.sendMessage(Formatting.format("&9&lMANWARPS &7- You have set the warp with the name &9" + rawMessage + "&7 as your standard warp!"));
                    main.panelSetStandardWarp.remove(player.getUniqueId());
                    e.setCancelled(true);
                } else {

                    this.playerConfig.addPlayer(player);
                    this.playerConfig.setStandardWarp(player, message);
                    player.sendMessage(Formatting.format("&9&lMANWARPS &7- You have set the warp with the name &9" + rawMessage + "&7 as your standard warp!"));
                    main.panelSetStandardWarp.remove(player.getUniqueId());
                    e.setCancelled(true);
                }
            } else if (e.getMessage().equalsIgnoreCase("cancel")) {

                main.panelSetStandardWarp.remove(player.getUniqueId());
                player.sendMessage(Formatting.format("&9&lMANWARPS &7- Cancel succesfull!"));
                e.setCancelled(true);
            } else {

                player.sendMessage(Formatting.format("&9&lMANWARPS &7- This warp does not exist!"));
                e.setCancelled(true);
            }
        }
    }
}
