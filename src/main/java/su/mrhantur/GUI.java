package su.mrhantur;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI implements Listener {
    private final Main plugin;

    public GUI(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + "Меню DeathCoins");

        // Телепорт к месту смерти
        ItemStack teleport = new ItemStack(Material.ENDER_PEARL);
        ItemMeta teleportMeta = teleport.getItemMeta();
        teleportMeta.setDisplayName(ChatColor.GREEN + "Телепорт к смерти");
        teleport.setItemMeta(teleportMeta);
        gui.setItem(3, teleport);

        // Проверить монеты
        ItemStack coins = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta coinsMeta = coins.getItemMeta();
        coinsMeta.setDisplayName(ChatColor.YELLOW + "У вас " + ChatColor.GOLD +
                plugin.getConfig().getDouble(player.getName() + ".coins", 0.0) +
                ChatColor.YELLOW + " монет");
        coins.setItemMeta(coinsMeta);
        gui.setItem(5, coins);

        player.openInventory(gui);
    }

    private void confirmGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "ВЫ УВЕРЕНЫ?");

        ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GREEN + "Я уверен!");
        confirm.setItemMeta(confirmMeta);
        gui.setItem(2, confirm);

        ItemStack reject = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta rejectMeta = confirm.getItemMeta();
        rejectMeta.setDisplayName(ChatColor.RED + "Я ошибся");
        reject.setItemMeta(rejectMeta);
        gui.setItem(6, reject);

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Меню DeathCoins")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            String displayName = clicked.getItemMeta().getDisplayName();

            if (displayName.equals(ChatColor.GREEN + "Телепорт к смерти")) {
                confirmGUI(player);
            } else if (displayName.equals(ChatColor.GOLD + "Ваши монеты")) {
                double coins = plugin.getConfig().getDouble(player.getName() + ".coins", 0.0);
                player.sendMessage(ChatColor.YELLOW + "У вас " + ChatColor.GOLD +
                        coins + ChatColor.YELLOW + " монет смерти");
            }
        } else if (event.getView().getTitle().equals(ChatColor.DARK_RED + "ВЫ УВЕРЕНЫ?")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            String displayName = clicked.getItemMeta().getDisplayName();

            if (displayName.equals(ChatColor.GREEN + "Я уверен!")) {
                player.closeInventory();
                plugin.teleportToDeath(player);
            } else if (displayName.equals(ChatColor.RED + "Я ошибся")) {
                openGUI(player);
            }
        }
    }
}
