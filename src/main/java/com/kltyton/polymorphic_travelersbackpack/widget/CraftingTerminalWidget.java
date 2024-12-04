package com.kltyton.polymorphic_travelersbackpack.widget;

import com.illusivesoulworks.polymorph.api.client.base.ITickingRecipesWidget;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.tiviacz.travelersbackpack.client.screen.TravelersBackpackHandledScreen;
import net.minecraft.world.inventory.Slot;

public class CraftingTerminalWidget extends PlayerRecipesWidget implements ITickingRecipesWidget {
    public CraftingTerminalWidget(TravelersBackpackHandledScreen screen, Slot outputSlot) {
        super(screen, outputSlot);
    }

    @Override
    public void tick() {
    }

    @Override
    public int getXPos() {
        return super.getXPos() + 20;
    }

    @Override
    public int getYPos() {
        return super.getYPos() + 72;
    }

    @Override
    public void initChildWidgets() {
        super.initChildWidgets();

        int openButtonYOffset = -50;
        this.openButton.setOffsets(this.getXPos(), this.getYPos() + openButtonYOffset);
    }
}
