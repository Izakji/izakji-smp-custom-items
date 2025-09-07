package com.izakji.izakji_smp_custom_items.item.custom;

import com.izakji.izakji_smp_custom_items.Config;
import com.izakji.izakji_smp_custom_items.render.particle.ModParticles;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IzakjiTablet extends Item {
    public IzakjiTablet(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide()) {
            // Get coordinates from config
            double targetX = Config.TELEPORT_X.get();
            double targetY = Config.TELEPORT_Y.get();
            double targetZ = Config.TELEPORT_Z.get();

            // Validate coordinates (check if they are reasonable)
            if (!isValidCoordinate(targetX, targetY, targetZ)) {
                // Send error message to player
                player.displayClientMessage(
                    Component.translatable("item.izakji_smp_custom_items.izakji_tablet.invalid_coordinates"),
                    true // Show in action bar (middle of screen)
                );
                return InteractionResultHolder.fail(player.getItemInHand(usedHand));
            }

            player.teleportTo(targetX, targetY, targetZ);
            player.getCooldowns().addCooldown(this, 100);
        }
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    private boolean isValidCoordinate(double x, double y, double z) {
        // Check if coordinates are within valid Minecraft world bounds
        // Also check if they're not the default values (0, 100, 0) which might indicate unconfigured
        if (x == 0.0 && y == 100.0 && z == 0.0) {
            return false; // Default values, likely not configured
        }
        
        // Check reasonable bounds for Minecraft coordinates
        return x >= -30000000 && x <= 30000000 &&
               y >= -2048 && y <= 2048 &&
               z >= -30000000 && z <= 30000000;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level instanceof ServerLevel serverLevel && entity instanceof Player player) {
            // Spawn particles when item is held in main hand (right hand)
            if (isSelected && player.getMainHandItem() == stack) {
                spawnHoldingParticles(serverLevel, player);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    private void spawnHoldingParticles(ServerLevel level, Player player) {
        // Calculate right hand position relative to player's facing direction
        double yaw = Math.toRadians(player.getYRot());
        
        // Offset to right hand position (right side of player)
        double rightX = -Math.sin(yaw + Math.PI/2) * 0.3; // Right side offset
        double rightZ = Math.cos(yaw + Math.PI/2) * 0.3;
        
        // Forward offset to move particles in front of player
        double forwardX = -Math.sin(yaw) * 0.3; // Forward direction
        double forwardZ = Math.cos(yaw) * 0.3;
        
        // Spawn particles from the tablet in right hand - higher and more forward
        double x = player.getX() + rightX + forwardX + (player.getRandom().nextDouble() - 0.5) * 0.1;
        double y = player.getY() + 1.3 + (player.getRandom().nextDouble() - 0.5) * 0.1; // Higher - at chest/hand level
        double z = player.getZ() + rightZ + forwardZ + (player.getRandom().nextDouble() - 0.5) * 0.1;
        
        // Spawn custom particles with gentle movement - half speed
        level.sendParticles(ModParticles.IZAKJI_TABLET_PARTICLE.get(), x, y, z, 1,
                           (player.getRandom().nextDouble() - 0.5) * 0.015, // Half the horizontal drift
                           player.getRandom().nextDouble() * 0.01,           // Half the upward motion
                           (player.getRandom().nextDouble() - 0.5) * 0.015,  // Half the horizontal drift
                           0.005); // Half the speed parameter
    }
}
