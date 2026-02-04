package org.example1.KissCommand;

import org.bukkit.plugin.java.JavaPlugin;
import org.example1.KissCommand.command.KissCommand;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // 自动创建配置文件
        saveDefaultConfig(); // 如果没有config.yml，自动创建

        // 注册命令
        getCommand("kiss").setExecutor(new KissCommand());

        // 插件加载完成的日志
        getLogger().info("Kiss插件已加载-1.3-SNAPSHOT");
    }
}
