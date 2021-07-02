package me.robertlit.lightninggun.effect;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LightningEffectTask extends BukkitRunnable {

    private final Map<Entity, LightningEffect> lightningEffects = new HashMap<>();

    public void addEffect(Entity entity) {
        if (lightningEffects.containsKey(entity)) {
            lightningEffects.get(entity).renew();
            return;
        }
        lightningEffects.put(entity, new LightningEffect(entity));
    }

    public void removeEffect(Entity entity) {
        LightningEffect effect;
        if ((effect = lightningEffects.get(entity)) != null) {
            effect.remove();
            lightningEffects.remove(entity);
        }
    }

    public void removeAll() {
        lightningEffects.values().forEach(LightningEffect::remove);
        lightningEffects.clear();
    }

    @Override
    public void run() {
        for (Map.Entry<Entity, LightningEffect> entry : new HashSet<>(lightningEffects.entrySet())) {
            LightningEffect effect = entry.getValue();
            // the second part of the check is because
            // if the player holds right click, he could hit
            // the entity after the death event was called
            // resulting in a new effect to spawn for 5 seconds
            if (effect.isExpired() || entry.getKey().isDead()) {
                effect.remove();
                lightningEffects.remove(entry.getKey());
            }
        }
    }
}
