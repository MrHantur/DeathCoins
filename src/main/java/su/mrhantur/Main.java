package su.mrhantur;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;

import java.util.*;
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

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String name = player.getName();
                    double currentCoins = getConfig().getDouble(name + ".coins", 0.0);

                    double failChance = currentCoins + 0.1;
                    double roll = Math.random(); // от 0.0 до 1.0

                    // player.sendMessage("failChance " + failChance + "   roll " + roll);

                    if (roll < failChance) {
                        continue;
                    }

                    double reward = round(0.01 + Math.random() * 0.02, 2);
                    double newTotal = round(currentCoins + reward, 2);

                    getConfig().set(name + ".coins", newTotal);
                    saveConfig();

                    player.sendMessage(ChatColor.GRAY + "Вы получили " + reward + " монет смерти (/dc)");
                }
            }
        }.runTaskTimer(this, 20 * 60 * 10, 20 * 60 * 10); // задержка и период в тиках (10 минут)

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String nickname = player.getName();
        String ip = player.getAddress().getAddress().getHostAddress();

        // Карта IP → ник
        ConfigurationSection ipMap = getConfig().getConfigurationSection("ipMap");
        if (ipMap == null) {
            ipMap = getConfig().createSection("ipMap");
        }

        // Проверка на конфликт IP
        String storedNickname = getConfig().getString("ipMap." + ip);
        if (storedNickname == null) {
            // Привязываем IP к нику, если ещё не был
            getConfig().set("ipMap." + ip, nickname);
            saveConfig();
        } else if (!storedNickname.equalsIgnoreCase(nickname)) {
            // Если IP уже привязан к другому нику — в blacklist
            if (!isBlacklisted(nickname)) {
                addToBlacklist(nickname);
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[DeathCoins] Игрок " + nickname +
                        " зашёл с IP " + ip + ", уже использованным " + storedNickname + ". Добавлен в чёрный список.");
            }
        }

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
                List<String> options = Arrays.asList("add", "remove", "blacklist");
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
        if (args.length == 2 && args[1].equalsIgnoreCase("blacklist")) {
            String target = args[0];

            if (isBlacklisted(target)) {
                removeFromBlacklist(target);
                sender.sendMessage(ChatColor.GREEN + "Игрок " + target + " удалён из чёрного списка.");
            } else {
                addToBlacklist(target);
                sender.sendMessage(ChatColor.RED + "Игрок " + target + " добавлен в чёрный список (95% комиссия).");
            }
            return;
        }

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
                target.sendMessage(ChatColor.GREEN + "Вы заработали немного кусочков монет смерти! (/dc)");
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

    public void askForAmount(Player sender, Player receiver) {
        sender.sendMessage(ChatColor.AQUA + "Введите количество монет, которые хотите передать игроку " +
                ChatColor.GOLD + receiver.getName() + ChatColor.RED +
                (isBlacklisted(sender.getName()) ? " (Внимание! Комиссия 95%)" : " (Внимание! Комиссия 10%)"));

        Bukkit.getScheduler().runTask(this, () -> {
            getServer().getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {
                    if (!event.getPlayer().equals(sender)) return;

                    event.setCancelled(true);
                    String message = event.getMessage();

                    double amount;
                    try {
                        amount = Double.parseDouble(message);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Некорректное число.");
                        AsyncPlayerChatEvent.getHandlerList().unregister(this);
                        return;
                    }

                    double senderCoins = getConfig().getDouble(sender.getName() + ".coins", 0.0);
                    if (senderCoins < amount || amount <= 0) {
                        sender.sendMessage(ChatColor.RED + "Недостаточно монет или сумма некорректна.");
                        AsyncPlayerChatEvent.getHandlerList().unregister(this);
                        return;
                    }

                    double commissionRate = isBlacklisted(sender.getName()) ? 0.95 : 0.10;
                    double finalAmount = round(amount * (1.0 - commissionRate), 2);

                    if (finalAmount == 0.01) {
                        sender.sendMessage(ChatColor.RED + "Итоговая сумма должна быть больше 0.01");
                        AsyncPlayerChatEvent.getHandlerList().unregister(this);
                        return;
                    }

                    double receiverCoins = getConfig().getDouble(receiver.getName() + ".coins", 0.0);

                    getConfig().set(sender.getName() + ".coins", round(senderCoins - amount, 2));
                    getConfig().set(receiver.getName() + ".coins", round(receiverCoins + finalAmount, 2));
                    saveConfig();

                    sender.sendMessage(ChatColor.GREEN + "Вы передали " + finalAmount + " монет игроку " + receiver.getName());
                    receiver.sendMessage(ChatColor.GOLD + sender.getName() + " передал вам " + finalAmount + " монет!");

                    // Удаляем слушатель после использования
                    AsyncPlayerChatEvent.getHandlerList().unregister(this);
                }
            }, this);
        });
    }

    private final Set<String> blacklist = new HashSet<>();

    public void loadBlacklist() {
        List<String> list = getConfig().getStringList("blacklist");
        blacklist.clear();
        blacklist.addAll(list);
    }

    public void saveBlacklist() {
        getConfig().set("blacklist", new ArrayList<>(blacklist));
        saveConfig();
    }

    public void addToBlacklist(String playerName) {
        blacklist.add(playerName.toLowerCase());
        saveBlacklist();
    }

    public void removeFromBlacklist(String playerName) {
        blacklist.remove(playerName.toLowerCase());
        saveBlacklist();
    }

    public boolean isBlacklisted(String playerName) {
        return blacklist.contains(playerName.toLowerCase());
    }

    private void applyBuffs(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 700, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 700, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 700, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
    }

}