package com.izakji.izakji_smp_custom_items.event;

import com.izakji.izakji_smp_custom_items.IzakjiSMPCustomItems;
import com.izakji.izakji_smp_custom_items.item.ModItems;
import com.izakji.izakji_smp_custom_items.render.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = IzakjiSMPCustomItems.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        // Add particles to dropped IzakjiTablet items
        if (event.getEntity() instanceof ItemEntity itemEntity && 
            itemEntity.level() instanceof ServerLevel serverLevel) {
            
            if (itemEntity.getItem().is(ModItems.IZAKJI_TABLET.get())) {
                // Spawn particles every 3 ticks for a steady glow effect
                if (itemEntity.tickCount % 3 == 0) {
                    spawnDroppedItemParticles(serverLevel, itemEntity);
                }
            }
        }
    }

    private static void spawnDroppedItemParticles(ServerLevel level, ItemEntity itemEntity) {
        // Spawn particles around the dropped item
        double x = itemEntity.getX() + (itemEntity.getRandom().nextDouble() - 0.5) * 0.4;
        double y = itemEntity.getY() + 0.3 + itemEntity.getRandom().nextDouble() * 0.2;
        double z = itemEntity.getZ() + (itemEntity.getRandom().nextDouble() - 0.5) * 0.4;
        
        // Spawn custom particles with gentle floating movement - half speed
        level.sendParticles(ModParticles.IZAKJI_TABLET_PARTICLE.get(), x, y, z, 1,
                           (itemEntity.getRandom().nextDouble() - 0.5) * 0.01, // Half the horizontal drift
                           itemEntity.getRandom().nextDouble() * 0.005,         // Half the upward motion  
                           (itemEntity.getRandom().nextDouble() - 0.5) * 0.01,  // Half the horizontal drift
                           0.0025); // Half the speed parameter
        
        // Occasionally add enchant particles for extra magical effect
        if (itemEntity.getRandom().nextFloat() < 0.2f) {
            level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 1,
                               (itemEntity.getRandom().nextDouble() - 0.5) * 0.01,
                               itemEntity.getRandom().nextDouble() * 0.005,
                               (itemEntity.getRandom().nextDouble() - 0.5) * 0.01,
                               0.003); // Speed parameter
        }
    }
}