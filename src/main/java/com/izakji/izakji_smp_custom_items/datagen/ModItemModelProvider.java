package com.izakji.izakji_smp_custom_items.datagen;

import com.izakji.izakji_smp_custom_items.IzakjiSMPCustomItems;
import com.izakji.izakji_smp_custom_items.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IzakjiSMPCustomItems.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItemWithEmissiveTexture(ModItems.IZAKJI_TABLET);
    }

    private ItemModelBuilder basicItemWithEmissiveTexture(DeferredItem<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated"))
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(IzakjiSMPCustomItems.MOD_ID, "item/" + item.getId().getPath()))
                .texture("emissive",
                        ResourceLocation.fromNamespaceAndPath(IzakjiSMPCustomItems.MOD_ID, "item/" + item.getId().getPath() + "_e"));
    }
}
