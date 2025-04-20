package net.setrion.fabulous_furniture.client.gui.screens.inventory;

import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.item.crafting.StackedIngredient;
import net.setrion.fabulous_furniture.world.level.block.state.properties.FurnitureCategory;
import net.setrion.fabulous_furniture.world.inventory.CarpentryTableMenu;
import net.setrion.fabulous_furniture.world.item.crafting.CarpentryTableRecipe;
import net.setrion.fabulous_furniture.world.level.block.state.properties.MaterialType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarpentryTableScreen extends AbstractContainerScreen<CarpentryTableMenu> {
    private static final ResourceLocation SCROLLER_SPRITE = FabulousFurniture.prefix("container/carpentry_table/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE = FabulousFurniture.prefix("container/carpentry_table/scroller_disabled");
    private static final ResourceLocation SCROLLER_FILTER_SPRITE = FabulousFurniture.prefix("container/carpentry_table/scroller_filter");
    private static final ResourceLocation SCROLLER_FILTER_DISABLED_SPRITE = FabulousFurniture.prefix("container/carpentry_table/scroller_filter_disabled");
    private static final ResourceLocation SLOT_CRAFTABLE = FabulousFurniture.prefix("container/carpentry_table/slot_craftable");
    private static final ResourceLocation SLOT_HOVERED = FabulousFurniture.prefix("container/carpentry_table/slot_hovered");
    private static final ResourceLocation SLOT_UNCRAFTABLE = FabulousFurniture.prefix("container/carpentry_table/slot_uncraftable");
    public static final ResourceLocation BG_LOCATION = FabulousFurniture.prefix("textures/gui/container/carpentry_table.png");
    private static final WidgetSprites FILTER_BUTTON_SPRITES = new WidgetSprites(
            FabulousFurniture.prefix("container/carpentry_table/filter_enabled"),
            FabulousFurniture.prefix("container/carpentry_table/filter_disabled"),
            FabulousFurniture.prefix("container/carpentry_table/filter_enabled_highlighted"),
            FabulousFurniture.prefix("container/carpentry_table/filter_disabled_highlighted")
    );
    private EditBox name;
    private boolean filterEnabled = false;
    protected StateSwitchingButton filterButton;
    private List<Checkbox> categoryList;
    private List<Checkbox> materialList;

    private float recipeListScrollOffs;
    private boolean recipeListScrolling;
    private int recipeListStartIndex;

    private float categoryListScrollOffs;
    private boolean categoryListScrolling;
    private int categoryListStartIndex;

    private float materialListScrollOffs;
    private boolean materialListScrolling;
    private int materialListStartIndex;

    private static List<RecipeHolder<CarpentryTableRecipe>> recipesForInput;
    private static List<RecipeHolder<CarpentryTableRecipe>> allRecipes;

    public CarpentryTableScreen(CarpentryTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        menu.registerUpdateListener(this::containerChanged);
        titleLabelY = titleLabelY-33;
        titleLabelX = titleLabelX-2;
        inventoryLabelY = inventoryLabelY+50;
    }

    @Override
    protected void init() {
        super.init();
        initButton();
        int i = leftPos-80;
        int j = topPos-40;
        List<FurnitureCategory> cats = FurnitureCategory.values().toList();
        categoryList = new ArrayList<>();
        for (int x = 0; x < cats.size(); x++) {
            Checkbox box = Checkbox.builder(Component.literal(""), font).selected(false).tooltip(Tooltip.create(cats.get(x).getTranslatedName())).pos(i+5, j+19+(x*19)).onValueChange(this::onCategoryListChanged).build();
            categoryList.add(x, box);
            addWidget(box);
        }

        List<MaterialType> materials = MaterialType.values().toList();
        materialList = new ArrayList<>();
        for (int x = 0; x < materials.size(); x++) {
            Checkbox box = Checkbox.builder(Component.literal(""), font).selected(false).tooltip(Tooltip.create(materials.get(x).getTranslatedName())).pos(i+5, j+128+(x*19)).onValueChange(this::onMaterialListChanged).build();
            materialList.add(x, box);
            addWidget(box);
        }
        name = new EditBox(font, i + 149, j + 13, 70, 10, Component.literal(""));
        name.setCanLoseFocus(true);
        name.setTextColor(-1);
        name.setTextColorUneditable(-1);
        name.setBordered(false);
        name.setMaxLength(50);
        name.setResponder(this::onNameChanged);
        name.setValue("");
        addWidget(name);
    }

    protected void initButton() {
        int i = leftPos-80;
        int j = topPos-40;
        filterButton = new StateSwitchingButton(i+224, j+9, 26, 16, filterEnabled);
        addRenderableWidget(filterButton);
        updateFilterButtonTooltip();
        initFilterButtonTextures();
    }

    private void onCategoryListChanged(Checkbox checkbox, boolean selected) {
        onRecipeReload(false, name.getValue());
    }

    private void onMaterialListChanged(Checkbox checkbox, boolean selected) {
        onRecipeReload(false, name.getValue());
    }

    private void onRecipeReload(boolean nameChanged, String name) {
        recipesForInput = new ArrayList<>();
        recipesForInput.addAll(allRecipes);
        recipesForInput.sort(Comparator.comparing(holder -> holder.value().getResultId()));
        if (nameChanged || !name.isEmpty()) {
            if (!name.isEmpty()) {
                recipesForInput = allRecipes.stream().filter(filter -> org.apache.commons.lang3.StringUtils.containsIgnoreCase(Component.translatable(filter.value().getResult().getItem().getDescriptionId()).getString(), name)).toList();
            }
        }
        if (isFilterEnabled(materialList)) {
            recipesForInput = recipesForInput.stream().filter(filter -> {
                List<MaterialType> mats = MaterialType.values().toList();
                for (int x = 0; x < mats.size(); x++) {
                    if (filter.value().getMaterialTypes().contains(mats.get(x)) && materialList.get(x).selected()) {
                        return true;
                    }
                }
                return false;
            }).toList();
        }
        if (isFilterEnabled(categoryList)) {
            recipesForInput = recipesForInput.stream().filter(filter -> {
                List<FurnitureCategory> cats = FurnitureCategory.values().toList();
                for (int x = 0; x < cats.size(); x++) {
                    if (filter.value().getCategory().name().equals(cats.get(x).name()) && categoryList.get(x).selected()) {
                        return true;
                    }
                }
                return false;
            }).toList();
        }
        if (filterEnabled) {
            recipesForInput = recipesForInput.stream().filter(this::isRecipeCraftable).toList();
        }
        recipeListScrollOffs = 0.0F;
        recipeListStartIndex = 0;
    }

    private boolean isFilterEnabled(List<Checkbox> list) {
        boolean checked = false;
        for (Checkbox box : list) {
            if (box.selected()) {
                checked = true;
                break;
            }
        }
        return checked;
    }

    private void onNameChanged(String s) {
        onRecipeReload(true, s);
    }

    private void updateFilterButtonTooltip() {
        filterButton.setTooltip(filterButton.isStateTriggered() ? Tooltip.create(Component.translatable("gui.recipebook.toggleRecipes.craftable")) : Tooltip.create(Component.translatable("gui.recipebook.toggleRecipes.all")));
    }

    private void initFilterButtonTextures() {
        filterButton.initTextureValues(FILTER_BUTTON_SPRITES);
    }

    public void resize(Minecraft minecraft, int width, int height) {
        String s = name.getValue();
        init(minecraft, width, height);
        name.setValue(s);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            minecraft.player.closeContainer();
        }
        return name.keyPressed(keyCode, scanCode, modifiers) || name.canConsumeInput() || super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void render(GuiGraphics guiGraphics, int p_282517_, int p_282840_, float p_282389_) {
        super.render(guiGraphics, p_282517_, p_282840_, p_282389_);
        guiGraphics.drawString(font, Component.translatable("carpentry_table.categories"), leftPos-73, topPos-34, 4210752, false);
        guiGraphics.drawString(font, Component.translatable("carpentry_table.materials"), leftPos-73, topPos+76, 4210752, false);
        renderFg(guiGraphics, p_282517_, p_282840_, p_282389_);
        filterButton.render(guiGraphics, p_282517_, p_282840_, p_282389_);
        renderTooltip(guiGraphics, p_282517_, p_282840_);
    }

    private boolean isRecipeCraftable(RecipeHolder<CarpentryTableRecipe> recipe) {
        boolean b = true;
        for (StackedIngredient ingredient : recipe.value().getMaterials()) {
            if (!(menu.container.countItem(ingredient.ingredient().getValues().get(0).value()) >= ingredient.count())) {
                b = false;
            }
        }
        return b;
    }

    private void renderButtons(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int lastVisibleElementIndex) {
        for(int i = recipeListStartIndex; i < lastVisibleElementIndex && i < recipesForInput.size(); ++i) {
            int j = i - recipeListStartIndex;
            int k = x + j % 6 * 25;
            int l = j / 6;
            int i1 = y + l * 25 + 2;
            ResourceLocation resourcelocation;
            if (!isRecipeCraftable(recipesForInput.get(i))) {
                resourcelocation = SLOT_UNCRAFTABLE;
            } else {
                if (mouseX >= k && mouseY >= i1 && mouseX < k + 25 && mouseY < i1 + 25) {
                    resourcelocation = SLOT_HOVERED;
                } else {
                    resourcelocation = SLOT_CRAFTABLE;
                }
            }

            guiGraphics.blitSprite(RenderType::guiTextured, resourcelocation, k, i1 - 1, 25, 25);
        }
    }

    private void renderRecipes(GuiGraphics guiGraphics, int x, int y, int lastVisibleElementIndex) {
        ContextMap contextmap = SlotDisplayContext.fromLevel(minecraft.level);

        for(int i = recipeListStartIndex; i < lastVisibleElementIndex && i < recipesForInput.size(); ++i) {
            int j = i - recipeListStartIndex;
            int k = x + j % 6 * 25;
            int l = j / 6;
            int i1 = y + l * 25 + 1;
            SlotDisplay slotdisplay = recipesForInput.get(i).value().display().get(0).result();
            guiGraphics.renderItem(slotdisplay.resolveForFirstStack(contextmap), k, i1);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
        categoryList.forEach(box -> {
            if (box.isActive() && box.isMouseOver(x, y)) {
                guiGraphics.renderTooltip(font, box.getTooltip().toCharSequence(Minecraft.getInstance()), x, y);
            }
        });

        materialList.forEach(box -> {
            if (box.isActive() && box.isMouseOver(x, y)) {
                guiGraphics.renderTooltip(font, box.getTooltip().toCharSequence(Minecraft.getInstance()), x, y);
            }
        });

        if (!recipesForInput.isEmpty()) {
            int i = this.leftPos + 7;
            int j = this.topPos - 10;
            int k = this.recipeListStartIndex + 24;

            for(int l = this.recipeListStartIndex; l < k && l < recipesForInput.size(); ++l) {
                int i1 = l - this.recipeListStartIndex;
                int j1 = i + i1 % 6 * 25;
                int k1 = j + i1 / 6 * 25 + 1;
                if (x >= j1 && x < j1 + 25 && y >= k1 && y < k1 + 25) {
                    ContextMap contextmap = SlotDisplayContext.fromLevel(this.minecraft.level);
                    SlotDisplay slotdisplay = recipesForInput.get(l).value().display().get(0).result();
                    List<Either<FormattedText, TooltipComponent>> components = new ArrayList<>();
                    components.add(Either.left(FormattedText.of(Component.translatable(slotdisplay.resolveForFirstStack(contextmap).getItemName().getString()).getString())));
                    components.add(Either.left(FormattedText.of(Component.translatable("carpentry_table.amount").getString()+slotdisplay.resolveForFirstStack(contextmap).getCount(), Style.EMPTY.withColor(ChatFormatting.BLUE))));
                    components.add(Either.left(FormattedText.of("")));
                    components.add(Either.left(FormattedText.of(Component.translatable("carpentry_table.ingredients").getString())));
                    recipesForInput.get(l).value().getMaterials().forEach(material -> components.add(Either.left(FormattedText.of(material.count()+"x "+Component.translatable(material.ingredient().getValues().get(0).value().getDescriptionId()).getString(), Style.EMPTY.withColor(ChatFormatting.BLUE)))));
                    guiGraphics.renderComponentTooltipFromElements(this.font, components, x, y, slotdisplay.resolveForFirstStack(contextmap));
                }
            }
        }
    }

    private void renderCategories(GuiGraphics guiGraphics, int x, int y, int lastVisibleElementIndex) {
        categoryList.forEach(box -> {
            box.active = false;
            box.visible = false;
        });
        for(int i = categoryListStartIndex; i < lastVisibleElementIndex && i < categoryList.size(); ++i) {
            Checkbox box = categoryList.get(i);
            int j = i - categoryListStartIndex;
            int i1 = y + j * 19 + 2;
            box.setPosition(x, i1);
            box.active = true;
            box.visible = true;
            box.render(guiGraphics, x, y, 1.0F);
            guiGraphics.renderItem(new ItemStack(FurnitureCategory.values().toList().get(i).item()), x+20, i1+1);
        }
    }

    private void renderMaterials(GuiGraphics guiGraphics, int x, int y, int lastVisibleElementIndex) {
        materialList.forEach(box -> {
            box.active = false;
            box.visible = false;
        });
        for(int i = materialListStartIndex; i < lastVisibleElementIndex && i < materialList.size(); ++i) {
            Checkbox box = materialList.get(i);
            int j = i - materialListStartIndex;
            int i1 = y + j * 19 + 2;
            box.setPosition(x, i1);
            box.active = true;
            box.visible = true;
            box.render(guiGraphics, x, y, 1.0F);
            guiGraphics.renderItem(new ItemStack(MaterialType.values().toList().get(i).item()), x+20, i1+1);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        int i = leftPos-80;
        int j = topPos-40;
        guiGraphics.blit(RenderType::guiTextured, BG_LOCATION, i, j, 0.0F, 0.0F, 256, 256, 256, 256);
        int r = (int)(85.0F * recipeListScrollOffs);
        int c = (int)(78.0F * categoryListScrollOffs);
        int m = (int)(78.0F * materialListScrollOffs);
        ResourceLocation scroller = isRecipeListScrollBarActive() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
        ResourceLocation scroller_filter = isCategoryScrollBarActive() ? SCROLLER_FILTER_SPRITE : SCROLLER_FILTER_DISABLED_SPRITE;
        ResourceLocation scroller_filter2 = isMaterialTypeScrollBarActive() ? SCROLLER_FILTER_SPRITE : SCROLLER_FILTER_DISABLED_SPRITE;
        guiGraphics.blitSprite(RenderType::guiTextured, scroller, i+239, j+31+r, 11, 15);
        guiGraphics.blitSprite(RenderType::guiTextured, scroller_filter, i+61, j+19+c, 12, 15);
        guiGraphics.blitSprite(RenderType::guiTextured, scroller_filter2, i+61, j+128+m, 12, 15);
        int l = leftPos+7;
        int i1 = topPos-10;
        int j1 = recipeListStartIndex + 24;
        renderButtons(guiGraphics, mouseX, mouseY, l, i1, j1);
        renderRecipes(guiGraphics, l+4, i1+4, j1);
        renderCategories(guiGraphics, i+5, j+17, categoryListStartIndex+5);
        renderMaterials(guiGraphics, i+5, j+126, materialListStartIndex+5);
    }

    public void renderFg(GuiGraphics p_283449_, int p_283263_, int p_281526_, float p_282957_) {
        name.render(p_283449_, p_283263_, p_281526_, p_282957_);
    }

    public static void setRecipes(List<RecipeHolder<CarpentryTableRecipe>> recipes) {
        allRecipes = recipes;
        recipesForInput = recipes;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!recipesForInput.isEmpty()) {
            int i = leftPos + 7;
            int j = topPos - 10;
            int k = recipeListStartIndex + 24;

            for(int l = recipeListStartIndex; l < k; ++l) {
                if (l < recipesForInput.size() && isRecipeCraftable(recipesForInput.get(l))) {
                    int i1 = l - recipeListStartIndex;
                    double d0 = mouseX - (double) (i + i1 % 6 * 25);
                    double d1 = mouseY - (double) (j + i1 / 6 * 25);
                    int id = allRecipes.indexOf(recipesForInput.get(l));
                    menu.setRecipes(allRecipes);
                    if (d0 >= 0.0 && d1 >= 0.0 && d0 < 25.0 && d1 < 25.0 && (menu).clickMenuButton(minecraft.player, id)) {
                        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                        minecraft.gameMode.handleInventoryButtonClick((menu).containerId, id);
                        return true;
                    }
                }
            }
        }
        if (filterButton.mouseClicked(mouseX, mouseY, button)) {
            filterEnabled = !filterEnabled;
            updateFilterButtonTooltip();
            filterButton.setStateTriggered(filterEnabled);
            onRecipeReload(false, name.getValue());
        }
        recipeListScrolling = false;
        int i = leftPos-80+239;
        int j = topPos-40+25;
        if (mouseX >= (double)i && mouseX < (double)(i + 11) && mouseY >= (double)j && mouseY < (double)(j + 100)) {
            recipeListScrolling = true;
        }
        categoryListScrolling = false;
        int i1 = leftPos-80+61;
        int j1 = topPos-40+14;
        if (mouseX >= (double)i1 && mouseX < (double)(i1 + 12) && mouseY >= (double)j1 && mouseY < (double)(j1 + 93)) {
            categoryListScrolling = true;
        }
        materialListScrolling = false;
        int i2 = leftPos-80+61;
        int j2 = topPos-40+123;
        if (mouseX >= (double)i2 && mouseX < (double)(i2 + 12) && mouseY >= (double)j2 && mouseY < (double)(j2 + 93)) {
            materialListScrolling = true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (recipeListScrolling && this.isRecipeListScrollBarActive()) {
            int i = topPos-10;
            int j = i + 100;
            recipeListScrollOffs = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            recipeListScrollOffs = Mth.clamp(recipeListScrollOffs, 0.0F, 1.0F);
            recipeListStartIndex = (int)((double)(recipeListScrollOffs * (float) getOffscreenRecipeRows()) + 0.5) * 6;
            return true;
        } else if (categoryListScrolling && this.isCategoryScrollBarActive()) {
            int i = topPos-21;
            int j = i + 93;
            categoryListScrollOffs = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            categoryListScrollOffs = Mth.clamp(categoryListScrollOffs, 0.0F, 1.0F);
            categoryListStartIndex = (int)((double)(categoryListScrollOffs * (float) getOffscreenCategories()) + 0.5);
            return true;
        } else if (materialListScrolling && this.isMaterialTypeScrollBarActive()) {
            int i = topPos+88;
            int j = i + 93;
            materialListScrollOffs = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            materialListScrollOffs = Mth.clamp(materialListScrollOffs, 0.0F, 1.0F);
            materialListStartIndex = (int)((double)(materialListScrollOffs * (float) getOffScreenMaterials()) + 0.5);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) {
            return true;
        } else {
            if (isRecipeListScrollBarActive()) {
                if (mouseX >= (double) leftPos + 6 && mouseX < (double) (leftPos + 171) && mouseY >= (double) topPos - 10 && mouseY < (double) (topPos + 92)) {
                    int i = getOffscreenRecipeRows();
                    float f = (float) scrollY / (float) i;
                    recipeListScrollOffs = Mth.clamp(recipeListScrollOffs - f, 0.0F, 1.0F);
                    recipeListStartIndex = (int) ((double) (recipeListScrollOffs * (float) i) + 0.5) * 6;
                }
            }
            if (isCategoryScrollBarActive()) {
                if (mouseX >= (double) leftPos - 76 && mouseX < (double) leftPos - 6 && mouseY >= (double) topPos - 22 && mouseY < (double) topPos + 73) {
                    int i = getOffscreenCategories();
                    float f = (float) scrollY / (float) i;
                    categoryListScrollOffs = Mth.clamp(categoryListScrollOffs - f, 0.0F, 1.0F);
                    categoryListStartIndex = (int) ((double) (categoryListScrollOffs * (float) i) + 0.5);
                }
            }
            if (isMaterialTypeScrollBarActive()) {
                if (mouseX >= (double)leftPos-76 && mouseX < (double)leftPos-6 && mouseY >= (double)topPos+87 && mouseY < (double)topPos+182) {
                    int i1 = getOffScreenMaterials();
                    float f1 = (float) scrollY / (float) i1;
                    materialListScrollOffs = Mth.clamp(materialListScrollOffs - f1, 0.0F, 1.0F);
                    materialListStartIndex = (int) ((double) (materialListScrollOffs * (float) i1) + 0.5);
                }
            }

            return true;
        }
    }

    private boolean isRecipeListScrollBarActive() {
        return recipesForInput.size() > 20;
    }

    private boolean isCategoryScrollBarActive() {
        return categoryList.size() > 5;
    }

    private boolean isMaterialTypeScrollBarActive() {
        return materialList.size() > 5;
    }

    protected int getOffscreenRecipeRows() {
        return (recipesForInput.size() + 6 - 1) / 6 - 4;
    }

    protected int getOffscreenCategories() {
        return categoryList.size() - 5;
    }

    protected int getOffScreenMaterials() {
        return materialList.size() - 5;
    }

    private void containerChanged() {
        onRecipeReload(false, name.getValue());
    }
}