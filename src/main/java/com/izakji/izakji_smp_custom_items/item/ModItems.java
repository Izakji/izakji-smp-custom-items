package com.izakji.izakji_smp_custom_items.item;

import com.izakji.izakji_smp_custom_items.IzakjiSMPCustomItems;
import com.izakji.izakji_smp_custom_items.item.custom.IzakjiTablet;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IzakjiSMPCustomItems.MOD_ID);

    public static final DeferredItem<Item> IZAKJI_TABLET = ITEMS.registerItem("izakji_tablet", IzakjiTablet::new, new Item.Properties().stacksTo(1));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
