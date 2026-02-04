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
import org.bukkit.entity.Player;

public class KissCommand implements CommandExecutor {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // 确保指令是由玩家执行
        if (!(sender instanceof Player player)) {
            sender.sendMessage("该指令只能由玩家执行");
            return true;
        }

        // 检查玩家是否输入了正确的目标玩家名
        if (args.length != 1) {
            player.sendMessage("用法: /kiss <玩家ID>");
            return true;
        }

        // 获取目标玩家
        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            player.sendMessage("玩家不在线或不存在");
            return true;
        }

        // 防止玩家亲吻自己
        if (target.equals(player)) {
            player.sendMessage("你不能对自己使用这个指令");
            return true;
        }

        // 向目标玩家发送亲吻消息
        Component targetMsg = miniMessage.deserialize(
                "<gradient:#ff9acd:#c77dff>[kiss]: 你被玩家 "
                        + player.getName()
                        + " 亲吻了！</gradient>"
        );
        target.sendMessage(targetMsg);

        // 在目标玩家头顶生成心形粒子
        Location heartLoc = target.getLocation().clone().add(0, 2.2, 0);
        target.getWorld().spawnParticle(
                Particle.HEART,
                heartLoc,
                8,
                0.4, 0.4, 0.4,
                0
        );

        // 播放亲吻音效
        target.playSound(
                target.getLocation(),
                Sound.ENTITY_PLAYER_LEVELUP,
                1.0f,
                1.0f
        );

        // 给发送指令的玩家发送亲吻确认消息
        Component senderMsg = miniMessage.deserialize(
                "<gradient:#ff9acd:#c77dff>[kiss]: 你亲吻了玩家 "
                        + target.getName()
                        + "！</gradient>"
        );
        player.sendMessage(senderMsg);

        return true;
    }
}
