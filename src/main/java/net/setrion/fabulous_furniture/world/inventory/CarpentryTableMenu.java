package net.setrion.fabulous_furniture.world.inventory;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.registry.SFFMenuTypes;
import net.setrion.fabulous_furniture.registry.SFFRecipeTypes;
import net.setrion.fabulous_furniture.world.item.crafting.CarpentryTableRecipe;
import net.setrion.fabulous_furniture.world.item.crafting.StackedIngredient;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarpentryTableMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    final DataSlot selectedRecipeIndex;
    private final Level level;
    private List<RecipeHolder<CarpentryTableRecipe>> recipesForInput;
    long lastSoundTime;
    final Slot resultSlot;
    Runnable slotUpdateListener;
    public final Container container;
    final ResultContainer resultContainer;

    public CarpentryTableMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public CarpentryTableMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(SFFMenuTypes.CARPENTRY_TABLE.get(), containerId);
        this.selectedRecipeIndex = DataSlot.standalone();
        this.slotUpdateListener = () -> {
        };
        this.container = new SimpleContainer(6) {
            public void setChanged() {
                super.setChanged();
                CarpentryTableMenu.this.slotsChanged(this);
                CarpentryTableMenu.this.slotUpdateListener.run();
            }
        };
        this.resultContainer = new ResultContainer();
        this.access = access;
        this.level = playerInventory.player.level();
        for (int i = 0; i < 6; i++) {
            this.addSlot(new Slot(this.container, i, 8+(i*18), 102));
        }
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 6, 134, 102) {

            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            public void onTake(Player player, ItemStack stack) {
                stack.onCraftedBy(player.level(), player, stack.getCount());

                access.execute((level, pos) -> {
                    long i = level.getGameTime();
                    if (CarpentryTableMenu.this.lastSoundTime != i) {
                        level.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        CarpentryTableMenu.this.lastSoundTime = i;
                    }
                });
                super.onTake(player, stack);
            }
        });
        this.addStandardInventorySlots(playerInventory, 8, 134);
        this.addDataSlot(this.selectedRecipeIndex);
        recipesForInput = new ArrayList<>();
        setupRecipes();
    }

    public void registerUpdateListener(Runnable listener) {
        this.slotUpdateListener = listener;
    }

    public void setupRecipes() {
        if (level instanceof ServerLevel serverLevel) {
            recipesForInput.clear();
            serverLevel.recipeAccess().getRecipes().forEach(recipe -> {
                if (recipe.value().getType() == SFFRecipeTypes.CARPENTRY_TABLE_RECIPE.get()) {
                    recipesForInput.add((RecipeHolder<CarpentryTableRecipe>) recipe);
                }
            });
        }
    }

    private boolean isRecipeCraftable(RecipeHolder<CarpentryTableRecipe> recipe) {
        boolean b = true;
        for (StackedIngredient ingredient : recipe.value().getMaterials()) {
            if (!(container.countItem(ingredient.ingredient().getValues().get(0).value()) >= ingredient.count())) {
                b = false;
            }
        }
        return b;
    }

    @Override
    public MenuType<?> getType() {
        return SFFMenuTypes.CARPENTRY_TABLE.get();
    }

    private boolean isValidRecipeIndex(int recipeIndex) {
        return recipeIndex >= 0 && recipeIndex < this.recipesForInput.size();
    }

    public boolean clickMenuButton(Player player, int id) {
        if (isValidRecipeIndex(id)) {
            if (isRecipeCraftable(recipesForInput.get(id))) {
                selectedRecipeIndex.set(id);
                craftRecipe(recipesForInput.get(selectedRecipeIndex.get()));
                return true;
            }
        }
        broadcastChanges();
        return super.clickMenuButton(player, id);
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    void craftRecipe(RecipeHolder<CarpentryTableRecipe> recipe) {
        this.resultContainer.setRecipeUsed(recipe);
        ItemStack stack = recipe.value().getResult().copy();
        if (resultSlot.hasItem()) {
            if (resultSlot.getItem().getItem() == stack.getItem()) {
                int count = resultSlot.getItem().getCount() + stack.getCount();
                if (count <= stack.getMaxStackSize()) {

                    if (removeItems(recipe)) {
                        stack.setCount(count);
                        resultSlot.set(stack);
                    }
                }
            }
        } else {
            if (removeItems(recipe)) {
                resultSlot.set(stack);
            }
        }
        this.broadcastChanges();
    }

    private boolean removeItems(RecipeHolder<CarpentryTableRecipe> recipe) {
        AtomicBoolean removed = new AtomicBoolean(true);
        recipe.value().getMaterials().forEach(ingredient -> {
            if (!removeItem(ingredient.ingredient().getValues().get(0).value(), ingredient.count())) {
                removed.set(false);
            }
        });
        return removed.get();
    }

    private boolean removeItem(Item item, int amount) {
        for (int i = 0; i < 7; i++) {
            if (!container.getItem(i).isEmpty() && container.getItem(i).getItem() == item) {
                ItemStack stack = container.getItem(i);
                if (stack.getCount() < amount) {
                    amount -= stack.getCount();
                    stack.setCount(0);
                } else {
                    stack.shrink(amount);
                    return true;
                }
            }
        }
        return false;
    }

    public List<RecipeHolder<CarpentryTableRecipe>> getRecipes() {
        return recipesForInput;
    }

    public void setRecipes(List<RecipeHolder<CarpentryTableRecipe>> recipes) {
        recipesForInput = recipes;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index == 6) {
                item.onCraftedBy(itemstack1, player.level(), player);
                if (!this.moveItemStackTo(itemstack1, 7, 43, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index <= 6 && index >= 0) {
                if (!this.moveItemStackTo(itemstack1, 7, 43, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.recipeAccess().stonecutterRecipes().acceptsInput(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 7, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 7 && index < 34) {
                if (!this.moveItemStackTo(itemstack1, 34, 43, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 34 && index < 43 && !this.moveItemStackTo(itemstack1, 7, 34, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            if (index == 6) {
                player.drop(itemstack1, false);
            }

            this.broadcastChanges();
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, SFFBlocks.CARPENTRY_TABLE.get());
    }

    public void removed(Player player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((p_40313_, p_40314_) -> {
            this.clearContainer(player, this.container);
        });
    }
}