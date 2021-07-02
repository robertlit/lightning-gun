package me.robertlit.lightninggun.listener;

import me.robertlit.lightninggun.effect.LightningEffectTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LightningEffectListener implements Listener {

    private final LightningEffectTask task;

    public LightningEffectListener(LightningEffectTask task) {
        this.task = task;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        task.removeEffect(event.getPlayer());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Zombie || entity instanceof Player) {
            task.removeEffect(entity);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityTeleport(EntityTeleportEvent event) {
        task.removeEffect(event.getEntity());
    }
}
