package net.setrion.fabulous_furniture.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.setrion.fabulous_furniture.client.gui.screens.inventory.CarpentryTableScreen;
import net.setrion.fabulous_furniture.world.inventory.CarpentryTableMenu;
import net.setrion.fabulous_furniture.world.item.crafting.StackedIngredient;
import org.joml.Matrix4f;

import java.util.List;

public class ClientIngredientTooltip implements ClientTooltipComponent {

    private final CarpentryTableMenu menu;
    private final StackedIngredient material;
    private ItemStack display = ItemStack.EMPTY;
    private long lastUpdateTime = -1;

    public ClientIngredientTooltip(CarpentryTableMenu menu, StackedIngredient material) {
        this.menu = menu;
        this.material = material;
    }

    @Override
    public int getHeight(Font font) {
        return 18;
    }

    @Override
    public int getWidth(Font font) {
        return 18 + font.width(getStack().getDisplayName());
    }

    @Override
    public void renderText(Font font, int mouseX, int mouseY, Matrix4f matrix, MultiBufferSource.BufferSource bufferSource) {
        ClientTooltipComponent.super.renderText(font, mouseX, mouseY, matrix, bufferSource);
    }

    @Override
    public void renderImage(Font font, int start, int top, int width, int height, GuiGraphics graphics) {
        ItemStack material = this.getStack().copy();
        material.setCount(this.material.count());
        graphics.renderFakeItem(material, start, top);
        graphics.renderItemDecorations(font, material, start, top);
        MutableComponent name = material.getHoverName().copy().withStyle(ChatFormatting.GRAY);
        graphics.drawString(font, name, start + 18 + 5, top + 4, 0xFFFFFFFF);

        // Draw check or cross depending on if we have the materials
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 200);
        graphics.blit(RenderType::guiTextured, CarpentryTableScreen.BG_LOCATION, start, top, 240, 40, 6, 5, 256, 256);
        pose.popPose();
    }

    private ItemStack getStack() {
        long time = Util.getMillis() / 1000;
        if(this.lastUpdateTime != time) {
            List<Holder<Item>> items = this.material.ingredient().items().toList();
            int index = (int) time % items.size();
            this.display = new ItemStack(items.get(index));
            this.lastUpdateTime = time;
        }
        return this.display;
    }
}