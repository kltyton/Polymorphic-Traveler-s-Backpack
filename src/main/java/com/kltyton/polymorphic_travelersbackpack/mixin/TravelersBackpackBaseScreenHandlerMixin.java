package com.kltyton.polymorphic_travelersbackpack.mixin;

import com.illusivesoulworks.polymorph.common.crafting.RecipeSelection;
import com.tiviacz.travelersbackpack.inventory.screen.TravelersBackpackBaseScreenHandler;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(TravelersBackpackBaseScreenHandler.class)
public class TravelersBackpackBaseScreenHandlerMixin {
    @Inject(method = "slotChangedCraftingGrid", at = @At("HEAD"), cancellable = true
    )
    private static void onSlotChangedCraftingGrid(AbstractContainerMenu handler, Level world, Player player, CraftingContainer craftMatrix, ResultContainer craftResult, CallbackInfo ci) {
        if (!world.isClientSide) {
            RecipeType<CraftingRecipe> recipeType = RecipeType.CRAFTING;
            ServerPlayer serverPlayerEntity = (ServerPlayer)player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<CraftingRecipe> recipe = RecipeSelection.getPlayerRecipe(handler, recipeType, craftMatrix, world, player);
            ItemStack itemStack2;
            CraftingRecipe craftingRecipe;
            if (recipe.isPresent() && craftResult.setRecipeUsed(world, serverPlayerEntity, craftingRecipe = recipe.get()) && (itemStack2 = craftingRecipe.assemble(craftMatrix, world.registryAccess())).isItemEnabled(world.enabledFeatures())) {
                itemStack = itemStack2;
            }

            craftResult.setItem(0, itemStack);
            handler.setRemoteSlot(0, itemStack);
            serverPlayerEntity.connection.send(new ClientboundContainerSetSlotPacket(handler.containerId, handler.incrementStateId(), 0, itemStack));
        }
        ci.cancel();
    }
}
