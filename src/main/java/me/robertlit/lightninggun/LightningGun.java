package me.robertlit.lightninggun;

import me.robertlit.lightninggun.command.LightningGunCommand;
import me.robertlit.lightninggun.effect.LightningEffectTask;
import me.robertlit.lightninggun.listener.LightningEffectListener;
import me.robertlit.lightninggun.listener.LightningGunListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class LightningGun extends JavaPlugin {

    public static final ItemStack ITEM = new ItemStack(Material.PRISMARINE_BRICKS);

    static {
        ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Lightning Gun");
        meta.setLore(Arrays.asList(
                "",
                ChatColor.WHITE + "Shoots electric bolts which",
                ChatColor.WHITE + "shock enemies.",
                "",
                ChatColor.YELLOW + "Right-click" + ChatColor.GRAY + " to fire."
        ));
        ITEM.setItemMeta(meta);
    }

    private final LightningEffectTask task = new LightningEffectTask();

    @Override
    public void onEnable() {
        getCommand("lightninggun").setExecutor(new LightningGunCommand());
        getServer().getPluginManager().registerEvents(new LightningGunListener(task), this);
        getServer().getPluginManager().registerEvents(new LightningEffectListener(task), this);
        task.runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        task.removeAll();
    }
}
