package su.mrhantur;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        getCommand("teleporttodeath").setExecutor(this);
        getCommand("getdeathcoords").setExecutor(this);
        getCommand("deathcoins").setExecutor(this);
        getCommand("deathcoins").setTabCompleter(this);

        Bukkit.getConsoleSender().sendMessage("Death Coins plugin enabled!\n" +
                                                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⠤⠤⠒⠒⠒⠒⠲⠦⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                                                "⠀⠀⠀⢀⡠⠐⠊⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢦⡀⠀⠀⠀⠀\n" +
                                                "⠀⢀⡶⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢳⠀⠀\n" +
                                                "⠀⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀DEATH⠀⠀⠀⠀⠀⠀⠀⠀⠀  ⢹⡄\n" +
                                                "⢸⠁⡤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀COINS⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻\n" +
                                                "⡏⢠⠁⠱⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸\n" +
                                                "⡇⡞⠀⠀⢣⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠠⠤⢀⠀⠀⠀⠀⠀⢀⡠⠀⠘⢆⢻\n" +
                                                "⡗⡇⠀⠀⠈⢆⠀⠀⠀⠀⠀⠀⢀⣀⡠⠖⠒⠒⠢⣄⠁⠀⢀⢀⣠⠞⠉⠑⠢⣜⠀\n" +
                                                "⢠⠃⠀⠀⠀⠈⣆⠀⠀⠀⠀⢠⣿⡏⠀⠀⠀⢀⣀⠈⠆⠐⠁⠈⡏⠀⠀⢀⣤⡜⡆\n" +
                                                "⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠒⣿⣿⡆⠀⠀⠀⣛⣿⡇⣤⠀⠀⠀⠑⡀⠀⠘⣘⣃⠃\n" +
                                                "⠀⢇⠀⠀⡀⠀⠀⠀⠀⠀⠀⠸⣇⠙⢦⣀⠀⠈⣉⡴⠃⠀⢀⡴⡆⠳⡤⠤⠆⡇⠀\n" +
                                                "⠀⠈⣏⠈⠉⢦⡀⠀⠀⠀⠀⠀⠙⠒⠈⠉⠛⡛⣫⠆⠀⢠⣾⣷⣷⠀⠀⠢⢠⠇⠀\n" +
                                                "⠀⠀⠘⣧⣄⠀⣩⠢⣄⠀⠀⠀⠠⠤⠴⠚⠉⠺⠃⠀⢀⡟⣿⠙⢿⢀⣄⣤⡞⠀⠀\n" +
                                                "⠀⠀⠀⠀⠙⢳⣬⠀⢼⣷⡀⢄⣤⣤⣴⣦⠴⠁⠀⠐⡜⣆⠸⣆⣘⢸⡇⠀⠀⠀⠀\n" +
                                                "⠀⠀⠀⠀⠀⠈⠟⠀⠀⠙⣯⠉⠉⢒⣯⣿⠀⠀⠀⠀⠀⠈⠉⠙⠛⠈⡇⠀⠀⠀⠀\n" +
                                                "⠀⠀⠀⠀⠀⠀⢸⡀⢀⣀⣈⣇⣴⣿⢏⣼⣦⡈⠑⠲⠤⣤⣀⣀⡠⠺⠇⠀⠀⠀⠀\n" +
                                                "⠀⠀⠀⠀⠀⠀⠀⢧⠀⠀⠉⠉⢻⣵⣿⣿⣿⣿⢷⢠⣤⣀⣈⣀⠈⠜⠀⠀⠀⠀⠀\n" +
                                                "⠀⠀⠀⠀⠀⠀⠀⠘⢣⡀⢀⡀⠀⠙⢿⣿⣿⢏⠎⣼⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                                                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣆⠙⠢⣕⣤⠙⠓⢋⡜⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                                                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠑⠶⢦⠭⣽⡶⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String nickname = player.getName();

        if (!player.hasPlayedBefore()) {
            savePlayerLocation(player);

            getConfig().set(nickname + ".coins", 0);
            saveConfig();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        savePlayerLocation(player);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("deathcoins")) {
            // Для первого аргумента (ник игрока)
            if (args.length == 1) {
                // Предлагаем игроков из конфига
                List<String> playerNames = new ArrayList<>();

                // Игроки из конфига
                playerNames.addAll(getConfig().getKeys(false));

                // Фильтрация и удаление дубликатов
                return StringUtil.copyPartialMatches(args[0], playerNames, new ArrayList<>());
            }

            // Для второго аргумента (add/remove)
            if (args.length == 2) {
                List<String> options = Arrays.asList("add", "remove");
                return StringUtil.copyPartialMatches(args[1], options, new ArrayList<>());
            }

            // Для третьего аргумента (число) - не предлагаем вариантов
            return Collections.emptyList();
        } else if (command.getName().equalsIgnoreCase("getdeathcoords")) {
            if (args.length == 1) {
                // Предлагаем игроков из конфига
                List<String> playerNames = new ArrayList<>();

                // Игроки из конфига
                playerNames.addAll(getConfig().getKeys(false));

                // Фильтрация и удаление дубликатов
                return StringUtil.copyPartialMatches(args[0], playerNames, new ArrayList<>());
            }

            return Collections.emptyList();
        }
        return null;
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("teleporttodeath")) {
            teleportToDeath((Player) sender);
            return true;
        } else if (command.getName().equalsIgnoreCase("deathcoins")) {
            if (args.length < 3) {
                sender.sendMessage("Подсказка: /deathcoins <nickname> <add|remove> <int>");
            } else {
                String nickname = getPlayerExactIgnoreCase(args[0]).getName();
                // СЛЕДУЮЩИХ ПРОВЕРОК ЛУЧШЕ НЕ ДОПУСКАТЬ,
                // ОНИ НУЖНЫ, ЧТОБЫ НИЧЕГО НЕ СЛОМАЛОСЬ.
                // ОНИ МОГУТ ПРИВЕСТИ К ЧЕМУ-ТО ОЧЕНЬ ПЛОХОМУ
                if (!getConfig().contains(nickname + ".world")) {
                    getConfig().set(nickname + ".world", "world");
                    getConfig().set(nickname + ".x", 0.5);
                    getConfig().set(nickname + ".y", 100);
                    getConfig().set(nickname + ".z", 0.5);
                    saveConfig();
                    sender.sendMessage("Пришлось принудительно создать данные для " + nickname);
                }
                if (!getConfig().contains(nickname + ".coins")) {
                    getConfig().set(nickname + ".coins", 0); // Если нет, то ставим 0
                    saveConfig();
                }
                if (args[1].equalsIgnoreCase("add")) {
                    getPlayerExactIgnoreCase(args[0]).sendMessage(ChatColor.GREEN + "Вы заработали монеты смерти!");
                    getConfig().set(nickname + ".coins", getConfig().getInt(nickname + ".coins") + Integer.parseInt(args[2]));
                    saveConfig();
                } else if (args[1].equalsIgnoreCase("remove")) {
                    getConfig().set(nickname + ".coins", getConfig().getInt(nickname + ".coins") - Integer.parseInt(args[2]));
                    saveConfig();
                } else {
                    sender.sendMessage("Неправильный формат данных, используйте /deathcoins <nickname> <add|remove> <int>");
                    return true;
                }
                sender.sendMessage("Теперь у " + ChatColor.GRAY + nickname + " " + ChatColor.GOLD +
                        getConfig().getInt(nickname + ".coins") + ChatColor.RESET + " монет");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("getdeathcoords")) {
            String nickname = "MrHantur"; // Заглушка
            if (args.length < 1) {
                nickname = sender.getName();
            } else {
                nickname = args[0];
            }
            int x = getConfig().getInt(nickname + ".x");
            int y = getConfig().getInt(nickname + ".y");
            int z = getConfig().getInt(nickname + ".z");
            sender.sendMessage("Вы умерли на координатах " + ChatColor.GRAY +
                    x + " " + y + " " + z);
        }
        return false;
    }

    public Player getPlayerExactIgnoreCase(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    private void savePlayerLocation(Player player) {
        Location location = player.getLocation();
        String nickname = player.getName();
        getConfig().set(nickname + ".world", location.getWorld().getName());
        getConfig().set(nickname + ".x", location.getX());
        getConfig().set(nickname + ".y", location.getY());
        getConfig().set(nickname + ".z", location.getZ());
        saveConfig();
    }

    private void teleportToDeath(Player player) {
        if (player instanceof ConsoleCommandSender) {
            player.sendMessage("Эта команда для игроков!");
        }

        String nickname = player.getName();
        double x = getConfig().getDouble(nickname + ".x");
        double y = getConfig().getDouble(nickname + ".y");
        double z = getConfig().getDouble(nickname + ".z");

        World world = Bukkit.getWorld(getConfig().getString(nickname + ".world"));
        if (world == null) {
            player.sendMessage(ChatColor.RED + "Не удалось найти измерение, в котором вы умерли." +
                    ChatColor.DARK_RED + "Обратитесь к админу с этой проблемой");
            return;
        }

        // Существуют ли монеты у игрока?
        if (!getConfig().contains(nickname + ".coins")) {
            getConfig().set(nickname + ".coins", 0); // Если нет, то ставим 0
            saveConfig();
        }

        int coins = getConfig().getInt(nickname + ".coins");

        if (coins < 1) {
            player.sendMessage(ChatColor.RED + "У вас недостаточно монет! Сейчас у вас " + coins);
            return;
        }

        getConfig().set(nickname + ".coins", coins-1);
        saveConfig();
        Bukkit.getConsoleSender().sendMessage(nickname + " ");
        Location teleportLocation = new Location(world, x, y, z);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 700, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 700, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 700, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.2F, 1.0F);
        player.sendActionBar(ChatColor.AQUA + "Вы будете телепортированы через 5 секунд");
        new BukkitRunnable() {
            @Override
            public void run() {
                // Код, который выполнится через 5 секунд
                if (!player.isOnline() || player.isDead()) {
                    getConfig().set(nickname + ".coins", coins+1);
                    saveConfig();
                    Bukkit.getConsoleSender().sendMessage(nickname + " wasn't teleported to death location");
                    return;
                }
                player.teleport(teleportLocation);
                player.sendActionBar(ChatColor.AQUA + "Телепортация завершена!");
            }
        }.runTaskLater(this, 100); // 100 тиков = 5 секунд
    }

}