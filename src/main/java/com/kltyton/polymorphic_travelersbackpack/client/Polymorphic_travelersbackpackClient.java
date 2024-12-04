package com.kltyton.polymorphic_travelersbackpack.client;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.kltyton.polymorphic_travelersbackpack.widget.CraftingTerminalWidget;
import com.tiviacz.travelersbackpack.client.screen.TravelersBackpackHandledScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.inventory.ResultSlot;

public class Polymorphic_travelersbackpackClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PolymorphApi.client().registerWidget(screen -> {
            if (screen instanceof TravelersBackpackHandledScreen craftingTerminal) {
                for (var slot : craftingTerminal.getMenu().slots) {
                    if (slot instanceof ResultSlot) {
                        return new CraftingTerminalWidget(craftingTerminal, slot);
                    }
                }
            }
            return null;
        });
    }
}
