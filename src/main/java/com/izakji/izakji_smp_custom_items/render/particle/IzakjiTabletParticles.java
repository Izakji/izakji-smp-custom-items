package com.izakji.izakji_smp_custom_items.render.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class IzakjiTabletParticles extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    
    protected IzakjiTabletParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.spriteSet = spriteSet;
        
        // Physics settings for smooth floating
        this.friction = 0.99f; // Higher friction for slower movement
        this.gravity = -0.01f; // Half the downward drift for slower movement
        
        // Lifetime (20-40 ticks = 1-2 seconds)
        this.lifetime = 20 + (int)(Math.random() * 20);
        
        // Initial velocity adjustments for gentle floating - half speed
        this.xd *= 0.15; // Half the horizontal speed
        this.yd = Math.abs(this.yd) * 0.05; // Half the upward start
        this.zd *= 0.15; // Half the horizontal speed
        
        // Color settings (nice green glow)
        this.rCol = 0.13f; // 34/255
        this.gCol = 0.75f; // 191/255  
        this.bCol = 0.26f; // 67/255
        
        // Size settings - 5x smaller
        this.quadSize = 0.02f + (float)(Math.random() * 0.02); // 5x smaller particles
        
        // Set initial sprite
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        
        // Update sprite based on age for animation
        this.setSpriteFromAge(spriteSet);
        
        // Only fade in the last tick - stay fully solid until then
        if (this.age >= this.lifetime - 1) {
            this.alpha = 0.0f; // Fade out completely in final tick
        } else {
            this.alpha = 1.0f; // Stay fully solid until the end
        }
        
        // Add gentle floating motion - half speed
        if (this.age > 5) { // After initial spawn, add floating behavior
            this.xd += (Math.random() - 0.5) * 0.001; // Half the drift speed
            this.zd += (Math.random() - 0.5) * 0.001; // Half the drift speed
        }
        
        // Clamp velocity to prevent crazy movement - half the max speed
        double maxSpeed = 0.025;
        this.xd = Math.max(-maxSpeed, Math.min(maxSpeed, this.xd));
        this.yd = Math.max(-maxSpeed, Math.min(maxSpeed, this.yd));
        this.zd = Math.max(-maxSpeed, Math.min(maxSpeed, this.zd));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5) {
            return new IzakjiTabletParticles(clientLevel, v, v1, v2, this.spriteSet, v3, v4, v5);
        }
    }
}
