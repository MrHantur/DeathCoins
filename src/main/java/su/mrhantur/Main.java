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
    private GUI gui;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        getCommand("teleporttodeath").setExecutor(this);
        getCommand("getdeathcoords").setExecutor(this);
        getCommand("deathcoins").setExecutor(this);
        getCommand("deathcoins").setTabCompleter(this);
        gui = new GUI(this);

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

            getConfig().set(nickname + ".coins", 0.0);
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


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String name = command.getName().toLowerCase();

        return switch (name) {
            case "teleporttodeath" -> {
                if (sender instanceof Player player) {
                    teleportToDeath(player);
                } else {
                    sender.sendMessage("Эта команда только для игроков.");
                }
                yield true;
            }

            case "deathcoins" -> {
                handleDeathCoinsCommand(sender, args);
                yield true;
            }

            case "getdeathcoords" -> {
                String nickname = args.length < 1 ? sender.getName() : args[0];
                sendDeathCoords(sender, nickname);
                yield true;
            }

            case "dc" -> {
                if (sender instanceof Player player) {
                    gui.openGUI(player);
                } else {
                    sender.sendMessage("Только игрок может использовать эту команду");
                }
                yield true;
            }

            default -> false;
        };
    }

    private void sendDeathCoords(CommandSender sender, String nickname) {
        int x = getConfig().getInt(nickname + ".x", 0);
        int y = getConfig().getInt(nickname + ".y", 0);
        int z = getConfig().getInt(nickname + ".z", 0);
        sender.sendMessage("Вы умерли на координатах " + ChatColor.GRAY + x + " " + y + " " + z);
    }


    private void handleDeathCoinsCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Подсказка: /deathcoins <nickname> <add|remove> <double>");
            return;
        }

        Player target = getPlayerExactIgnoreCase(args[0]);
        if (target == null) {
            sender.sendMessage("Игрок не найден");
            return;
        }

        String nickname = target.getName();
        double coins = getConfig().getDouble(nickname + ".coins", 0.0);
        double delta;

        try {
            delta = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Третий аргумент должен быть числом");
            return;
        }

        switch (args[1].toLowerCase()) {
            case "add" -> {
                coins = round(coins + delta, 2);
                target.sendMessage(ChatColor.GREEN + "Вы заработали немного кусочков монет смерти!");
            }
            case "remove" -> {
                coins = round(coins - delta, 2);
            }
            default -> {
                sender.sendMessage("Неправильный формат: используйте add или remove");
                return;
            }
        }

        getConfig().set(nickname + ".coins", coins);
        saveConfig();

        sender.sendMessage("Теперь у " + ChatColor.GRAY + nickname + " " + ChatColor.GOLD +
                coins + ChatColor.RESET + " монет");
    }


    public Player getPlayerExactIgnoreCase(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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

    void teleportToDeath(Player player) {
        String nickname = player.getName();
        World world = Bukkit.getWorld(getConfig().getString(nickname + ".world", "world"));
        double coins = getConfig().getDouble(nickname + ".coins", 0.0);

        if (world == null) {
            player.sendMessage(ChatColor.RED + "Мир не найден. Обратитесь к администратору.");
            return;
        }

        if (coins < 1) {
            player.sendMessage(ChatColor.RED + "У вас недостаточно монет! Сейчас у вас " + coins);
            return;
        }

        double x = getConfig().getDouble(nickname + ".x");
        double y = getConfig().getDouble(nickname + ".y");
        double z = getConfig().getDouble(nickname + ".z");
        Location teleportLocation = new Location(world, x, y, z);

        applyBuffs(player);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.2F, 1.0F);
        player.sendActionBar(ChatColor.AQUA + "Вы будете телепортированы через 5 секунд");

        getConfig().set(nickname + ".coins", round(coins - 1, 2));
        saveConfig();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || player.isDead()) {
                    getConfig().set(nickname + ".coins", round(coins + 0.9, 2)); // Возврат монеты
                    saveConfig();
                    Bukkit.getConsoleSender().sendMessage(nickname + " wasn't teleported to death location");
                    return;
                }
                player.teleport(teleportLocation);
                player.sendActionBar(ChatColor.AQUA + "Телепортация завершена!");
            }
        }.runTaskLater(this, 100);
    }

    private void applyBuffs(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 700, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 700, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 700, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
    }

}