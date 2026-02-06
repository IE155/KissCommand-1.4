package org.example1.KissCommand.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example1.KissCommand.Main;

public class KissReloadCommand implements CommandExecutor {

    private final Main plugin;

    public KissReloadCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean allowed =
                sender instanceof Player player && player.isOp()
                        || !(sender instanceof Player); // 控制台

        if (!allowed) {
            return true; // 普通玩家无任何提示
        }

        plugin.reloadConfig();

        String msg = "[KissCommand] config.yml 重新加载完成";

        // 控制台
        Bukkit.getConsoleSender().sendMessage(msg);

        // 仅 OP 玩家可见
        Bukkit.getOnlinePlayers().stream()
                .filter(Player::isOp)
                .forEach(p -> p.sendMessage(msg));

        return true;
    }
}
