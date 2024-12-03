package com.kltyton.polymorphic_travelersbackpack.widget;

import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.menu.slot.CraftingTermSlot;
import com.illusivesoulworks.polymorph.api.client.base.ITickingRecipesWidget;
import com.illusivesoulworks.polymorph.client.recipe.widget.PlayerRecipesWidget;
import com.tiviacz.travelersbackpack.inventory.screen.TravelersBackpackBaseScreenHandler;
import com.tiviacz.travelersbackpack.inventory.screen.TravelersBackpackItemScreenHandler;
import gripe._90.polyeng.PolymorphicEnergistics;
import gripe._90.polyeng.mixin.AEBaseMenuAccessor;
import gripe._90.polyeng.mixin.AbstractContainerScreenAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class CraftingTerminalWidget extends PlayerRecipesWidget implements ITickingRecipesWidget {
    /**
     * 用于存储输出槽位。
     */
    private Slot outputSlot;
    /**
     * 用于存储菜单的高度。
     */
    private int menuHeight;

    /**
     * 选择一个配方。
     * @param id 配方的唯一标识符。
     */
    @SuppressWarnings("resource")
    @Override
    public void selectRecipe(ResourceLocation id) {
        super.selectRecipe(id);
        System.out.println("选择配方：" + id.toString());
        // 获取合成终端的菜单。
        var menu = TravelersBackpackBaseScreenHandler.getMenu();
        // 获取玩家实例。
        menu.getPlayer().level().getRecipeManager().byKey(id).ifPresent(recipe -> ((AEBaseMenuAccessor) menu).invokeSendClientAction(PolymorphicEnergistics.ACTION));
    }

    @Override
    public Slot getOutputSlot() {
        System.out.println("获取输出槽位");
        return outputSlot;
    }

    /**
     * 每次游戏刻（tick）时调用，用于更新界面。
     */
    @Override
    public void tick() {
        var screen = (AbstractContainerScreenAccessor) containerScreen;
        // 检查菜单的高度是否发生变化。
        if (screen.getImageHeight() != menuHeight) {
            System.out.println("菜单高度发生变化，重新计算界面布局。");
            // 遍历所有槽位。
            for (var slot : containerScreen.getMenu().slots) {
                // 如果找到合成终端槽位。
                if (slot instanceof CraftingTermSlot craftingSlot) {
                    outputSlot = craftingSlot;
                    resetWidgetOffsets();
                    System.out.println("找到新的输出槽位，重置界面偏移量。");
                    break;
                }
            }
            // 更新菜单的高度。
            menuHeight = screen.getImageHeight();
            System.out.println("更新菜单高度为：" + menuHeight);
        }
    }
}