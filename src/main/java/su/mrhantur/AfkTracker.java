package su.mrhantur;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class AfkTracker implements Listener {

    private final JavaPlugin plugin;
    private final HashMap<UUID, Long> lastActive = new HashMap<>();
    private final long afkTimeout = 5 * 60 * 1000; // 5 минут в миллисекундах

    public AfkTracker(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        startChecker();
    }

    // Обновление времени активности при действиях игрока
    private void updateActivity(Player player) {
        lastActive.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!event.getFrom().toVector().equals(event.getTo().toVector())) {
            updateActivity(event.getPlayer());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        updateActivity(event.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        updateActivity(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        updateActivity(event.getPlayer());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        updateActivity(event.getPlayer());
    }

    public boolean isAfk(Player player) {
        return System.currentTimeMillis() - lastActive.getOrDefault(player.getUniqueId(), 0L) >= afkTimeout;
    }

    private void startChecker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    boolean afk = isAfk(player);
                    player.setPlayerListName((afk ? "§7[AFK] §r" : "") + player.getName());
                }
            }
        }.runTaskTimer(plugin, 0L, 20 * 30); // каждые 30 секунд
    }
}
