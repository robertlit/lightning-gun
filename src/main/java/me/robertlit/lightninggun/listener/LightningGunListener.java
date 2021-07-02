package me.robertlit.lightninggun.listener;

import me.robertlit.lightninggun.effect.LightningEffectTask;
import me.robertlit.lightninggun.LightningGun;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class LightningGunListener implements Listener {

    private final LightningEffectTask task;

    public LightningGunListener(LightningEffectTask task) {
        this.task = task;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (event.getItem() == null || !event.getItem().isSimilar(LightningGun.ITEM)) {
            return;
        }
        event.setCancelled(true); // cancel the placement of a prismarine block

        // particles
        Player player = event.getPlayer();
        Location start = player.getEyeLocation();
        Location location = start.clone();
        Vector direction = location.getDirection().normalize();
        int i = 0;
        while (i < 15 && !location.getBlock().getType().isSolid()) {
            // cam be swapped to Player#spawnParticle if only shooter should see particles
            location.getWorld().spawnParticle(Particle.REDSTONE, location.add(direction), 1, new Particle.DustOptions(Color.AQUA, 1));
            i++;
        }

        // damage and effect
        Set<Entity> hit = new HashSet<>();
        hit.add(player); // ignore the shooter
        RayTraceResult result;
        while ((result = start.getWorld().rayTrace(
                start,
                direction,
                15,
                FluidCollisionMode.NEVER,
                true,
                0.0D,
                entity -> !hit.contains(entity) && (entity instanceof Zombie || entity instanceof Player))) != null
                && result.getHitBlock() == null) {
            Entity entity = result.getHitEntity();
            hit.add(entity);
            task.addEffect(entity);
            ((Damageable) entity).damage(4);
        }
    }
}
