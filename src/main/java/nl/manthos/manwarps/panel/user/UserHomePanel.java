package nl.manthos.manwarps.panel.user;

import nl.manthos.manwarps.ManWarps;
import nl.manthos.manwarps.config.PlayerConfig;
import nl.manthos.manwarps.config.WarpConfig;
import nl.manthos.manwarps.items.HeadItem;
import nl.manthos.manwarps.util.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import site.phoenixthedev.panelapi.PhoPanelManager;
import site.phoenixthedev.panelapi.session.BaseSessionData;
import site.phoenixthedev.panelapi.types.PhoPanel;

public class UserHomePanel extends PhoPanel {

    private ManWarps main;
    private WarpConfig config;
    private PlayerConfig playerConfig;
    private String panelName = Formatting.format("&6➼ &9&lManWarps Home");

    public UserHomePanel(PhoPanelManager phoPanelManager, ManWarps main) {
        super(phoPanelManager);
        this.main = main;
        this.config = main.config;
        this.playerConfig = main.playerConfig;
    }

    @Override
    public void initialize() {
        defaultPanel = null;
        panelSize = 54;
    }

    @Override
    public Inventory createPanel(BaseSessionData baseSessionData) {
        Inventory inv = Bukkit.createInventory(null, panelSize, panelName);

        inv.setItem(10, setWarp());
        inv.setItem(16, standardWarp());
        inv.setItem(28, setStandardWarp());
        inv.setItem(53, closeButton());

        return inv;
    }

    @Override
    public void onClickEvent(InventoryClickEvent e) {
        Inventory inv = e.getClickedInventory();
        if (!isInstance(inv)) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getRawSlot() == -999) return;
        if (e.getCurrentItem() == null) return;
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        String currentName = current.getItemMeta().getDisplayName();

        if (currentName.equals(closeButton().getItemMeta().getDisplayName())) {

            player.closeInventory();
        } else if (currentName.equals(setWarp().getItemMeta().getDisplayName())) {

            main.panelSetWarp.add(player.getUniqueId());
            player.sendMessage(Formatting.format("&9&lMANWARPS &7- Fill in a name for the warp. (1 word)"));
            player.closeInventory();
        } else if (currentName.equals(setStandardWarp().getItemMeta().getDisplayName())) {

            main.panelSetStandardWarp.add(player.getUniqueId());
            player.sendMessage(Formatting.format("&9&lMANWARPS &7- Wich warp do you want to set to your standard warp? Type &9cancel &7to cancel."));
            player.closeInventory();
        } else if (currentName.equals(standardWarp().getItemMeta().getDisplayName())) {

            String standardWarp = this.playerConfig.getStandardWarp(player);
            Location getStandardWarp = this.config.getWarpLocation(standardWarp);
            player.teleport(getStandardWarp);
            player.sendMessage(Formatting.format("&9&lMANWARPS &7- You have been teleported to you standard warp!"));
        }
    }

    private ItemStack setWarp() {
        ItemStack is = new ItemStack(HeadItem.ARROW_DOWN_BLUE.head());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format("&2&lSet Warp"));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack setStandardWarp() {
        ItemStack is = new ItemStack(HeadItem.HOUSE.head());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format("&6&lSet Standard Warp"));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack standardWarp() {
        ItemStack is = new ItemStack(HeadItem.EAGLE.head());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format("&b&lStandard Warp"));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack goWarp() {
        ItemStack is = new ItemStack(HeadItem.EAGLE.head());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format("&b&lStandard Warp"));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack closeButton() {
        ItemStack is = new ItemStack(Material.BARRIER);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format("&c&lClose"));
        im.addEnchant(Enchantment.LUCK, 999, false);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }
}
