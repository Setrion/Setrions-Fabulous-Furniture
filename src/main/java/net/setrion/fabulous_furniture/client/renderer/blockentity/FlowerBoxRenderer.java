package net.setrion.fabulous_furniture.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.EmptyBlockAndTintGetter;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.Vec3;
import net.setrion.fabulous_furniture.world.level.block.FlowerBoxBlock;
import net.setrion.fabulous_furniture.world.level.block.entity.FlowerBoxBlockEntity;

public class FlowerBoxRenderer implements BlockEntityRenderer<FlowerBoxBlockEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public FlowerBoxRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    public void render(FlowerBoxBlockEntity blockEntity, float size, PoseStack stack, MultiBufferSource bufferSource, int p_112348_, int p_112349_, Vec3 p_401331_) {
        Direction direction = blockEntity.getBlockState().getValue(CampfireBlock.FACING);
        NonNullList<ItemStack> nonnulllist = blockEntity.getFlowers();
        float h = 0.0F;
        if (blockEntity.getBlockState().getValue(FlowerBoxBlock.HALF) == Half.TOP) {
            h = 0.625F;
        }

        for (int j = 0; j < nonnulllist.size(); j++) {
            ItemStack itemstack = nonnulllist.get(j);
            if (j < 2) {
                if (itemstack != ItemStack.EMPTY && itemstack.getItem() instanceof BlockItem blockItem) {
                    stack.pushPose();
                    stack.translate(0.5F, 0.3F+h, 0.5F);
                    Direction direction1 = Direction.from2DDataValue((direction.get2DDataValue()) % 4);
                    float f = -direction1.getOpposite().toYRot();
                    stack.mulPose(Axis.YP.rotationDegrees(f));
                    stack.translate(-0.1249 - (j * 0.5), 0.0F, -0.1251F);
                    stack.scale(0.75F, 0.75F, 0.75F);
                    this.blockRenderer.renderSingleBlock(blockItem.getBlock().defaultBlockState(), stack, bufferSource, p_112348_, p_112349_, EmptyBlockAndTintGetter.INSTANCE, blockEntity.getBlockPos());
                    stack.popPose();
                }
            } else {
                int d = 0;
                if (j == 3) d = 1;
                if (itemstack != ItemStack.EMPTY && itemstack.getItem() instanceof BlockItem blockItem) {
                    stack.pushPose();
                    stack.translate(0.5F, 0.3F+h, 0.5F);
                    Direction direction1 = Direction.from2DDataValue((direction.get2DDataValue()) % 4);
                    float f = -direction1.getOpposite().toYRot();
                    stack.mulPose(Axis.YP.rotationDegrees(f));
                    stack.translate(-0.6251F + (d * 0.5), 0.0F, -0.6249F);
                    stack.scale(0.75F, 0.75F, 0.75F);
                    this.blockRenderer.renderSingleBlock(blockItem.getBlock().defaultBlockState(), stack, bufferSource, p_112348_, p_112349_, EmptyBlockAndTintGetter.INSTANCE, blockEntity.getBlockPos());
                    stack.popPose();
                }
            }
        }
    }
}