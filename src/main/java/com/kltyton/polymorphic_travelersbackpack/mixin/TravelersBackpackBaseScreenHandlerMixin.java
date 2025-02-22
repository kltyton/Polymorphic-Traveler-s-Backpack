package com.kltyton.polymorphic_travelersbackpack.mixin;

import com.illusivesoulworks.polymorph.common.crafting.RecipeSelection;
import com.tiviacz.travelersbackpack.inventory.menu.BackpackBaseMenu;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(BackpackBaseMenu.class)
public abstract class TravelersBackpackBaseScreenHandlerMixin {

    @Redirect(
            method = "slotChangedCraftingGrid",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"
            )
    )
    private <C extends Container, T extends Recipe<C>> Optional<T> redirectGetRecipeFor(
            RecipeManager instance, RecipeType<T> recipeType, C container, Level level) {

        // 根据需要使用 RecipeSelection.getPlayerRecipe 或其他方法
        BackpackBaseMenu menu = (BackpackBaseMenu) (Object) this; // 获取当前实例
        return RecipeSelection.getPlayerRecipe(menu, recipeType, container, level, menu.slots);
    }
}
