package com.izakji.izakji_smp_custom_items.item;

import com.izakji.izakji_smp_custom_items.IzakjiSMPCustomItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class IzakjiSMPCustomItemsCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> IZAKJI_SMP_CUSTOM_ITEMS_CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IzakjiSMPCustomItems.MOD_ID);

    public static final Supplier<CreativeModeTab> IZAKJI_SMP_CUSTOM_ITEMS_CREATIVE_TABS = IZAKJI_SMP_CUSTOM_ITEMS_CREATIVE_MODE_TABS.register("izakji_smp_custom_items_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.izakji_smp_custom_items.izakji_smp_custom_items_items_tab"))
            .icon(() -> new ItemStack(ModItems.IZAKJI_TABLET.get()))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.IZAKJI_TABLET);
            })
            .build());

    public static void register(IEventBus eventBus) {
        IZAKJI_SMP_CUSTOM_ITEMS_CREATIVE_MODE_TABS.register((eventBus));
    }
}
