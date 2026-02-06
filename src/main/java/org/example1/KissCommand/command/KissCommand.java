package org.example1.KissCommand.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.example1.KissCommand.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KissCommand implements CommandExecutor {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final Main plugin;

    public KissCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /*kiss2load*/
        if (label.equalsIgnoreCase("kiss2load")) {

            boolean isConsole = !(sender instanceof Player);

            if (!isConsole && sender instanceof Player player && !player.isOp()) {
                return true; // 普通玩家静默
            }

            plugin.reloadConfig();

            Bukkit.getConsoleSender().sendMessage("[KissCommand] 配置已重加载完成");

            if (sender instanceof Player player) {
                player.sendMessage("§a[KissCommand] 配置已重加载完成");
            }

            return true;
        }

        //kiss

        if (!(sender instanceof Player player)) {
            sender.sendMessage("ERROE");
            return true;
        }

        ConfigurationSection kiss = plugin.getConfig().getConfigurationSection("kiss");
        if (kiss == null || !kiss.getBoolean("enabled", true)) {
            player.sendMessage("该功能已被服务器关闭");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("用法: /kiss <玩家ID>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            player.sendMessage("玩家不在线或不存在");
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage("你不能对自己使用这个指令,谁会去亲自己呢？");
            return true;
        }

        //冷却
        int cooldownSeconds = kiss.getInt("cooldown-seconds", 3);
        if (cooldownSeconds > 0) {
            UUID id = player.getUniqueId();
            long now = System.currentTimeMillis();
            long last = cooldowns.getOrDefault(id, 0L);

            if (now - last < cooldownSeconds * 1000L) {
                int remain = (int) Math.ceil(
                        (cooldownSeconds * 1000 - (now - last)) / 1000.0
                );
                player.sendMessage("指令冷却中，请等待 " + remain + " 秒");
                return true;
            }
            cooldowns.put(id, now);
        }

        //渐变色
        String start = kiss.getString("color.start", "#ff9acd");
        String end = kiss.getString("color.end", "#c77dff");

        Component targetMsg = miniMessage.deserialize(
                player.getName() +"<gradient:" + start + ":" + end + ">" + " 亲了你一口！❤" + "</gradient>");
        target.sendMessage(targetMsg);

        Component senderMsg = miniMessage.deserialize(
                "<gradient:" + start + ":" + end + ">" + "你亲吻了 " + "</gradient>" + target.getName() + "<gradient:" + start + ":" + end + ">" +" ❤" + "</gradient>");
        player.sendMessage(senderMsg);

        /*头顶爱心*/
        Location head = target.getLocation().clone().add(0, 2.2, 0);
        target.getWorld().spawnParticle(
                Particle.HEART,
                head,
                12, // 原 6 → 2 倍
                0.4, 0.4, 0.4,
                0
        );

        /*Love Aura*/
        if (kiss.getBoolean("Love-Aura-Render", true)) {
            spawnLoveAura(target, kiss);
        }

        target.playSound(
                target.getLocation(),
                Sound.ENTITY_PLAYER_LEVELUP,
                1.0f,
                1.0f
        );

        return true;
    }

    /**
     * 光环（由 config.yml 控制）
     */
    private void spawnLoveAura(Player target, ConfigurationSection kiss) {

        int radius = kiss.getInt("Love-Aura-Radius", 1);
        int points = kiss.getInt("Love-Aura-Dense", 18);

        // 防止配置炸服
        if (radius <= 0) radius = 1;
        if (radius > 10) radius = 10;

        if (points < 4) points = 4;
        if (points > 50) points = 50;

        Location center = target.getLocation().clone().add(0, 1.0, 0);

        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;

            target.getWorld().spawnParticle(
                    Particle.HEART,
                    center.clone().add(x, 0, z),
                    1,
                    0, 0, 0,
                    0
            );
        }
    }
}
