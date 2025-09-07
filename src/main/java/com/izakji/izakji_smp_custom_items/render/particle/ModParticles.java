package com.izakji.izakji_smp_custom_items.render.particle;

import com.izakji.izakji_smp_custom_items.IzakjiSMPCustomItems;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, IzakjiSMPCustomItems.MOD_ID);

    public static final Supplier<SimpleParticleType> IZAKJI_TABLET_PARTICLE = PARTICLE_TYPES.register("izakji_tablet_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
