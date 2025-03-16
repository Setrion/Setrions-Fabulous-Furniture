package net.setrion.fabulous_furniture.world.item.crafting;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.Utf8String;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.setrion.fabulous_furniture.registry.SFFRecipeSerializers;
import net.setrion.fabulous_furniture.registry.SFFRecipeTypes;
import net.setrion.fabulous_furniture.world.level.block.state.properties.FurnitureCategory;
import net.setrion.fabulous_furniture.world.level.block.state.properties.MaterialType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class CarpentryTableRecipe implements Recipe<SingleRecipeInput> {

    private final NonNullList<StackedIngredient> ingredients;
    private final ItemStack result;
    private final FurnitureCategory category;
    static StreamCodec<FriendlyByteBuf, FurnitureCategory> FURNITURE_CATEGORY_STREAM_CODEC = StreamCodec.of((buf, type) -> {
        Utf8String.write(buf, type.name(), 128);
    }, buf -> {
        String name = Utf8String.read(buf, 128);
        FurnitureCategory w = FurnitureCategory.CRATES;
        for (FurnitureCategory wType : FurnitureCategory.values().toList()) {
            if (wType.name().equals(name)) {
                w = wType;
            }
        }
        return w;
    });
    private final NonNullList<MaterialType> materialTypes;
    static StreamCodec<FriendlyByteBuf, MaterialType> MATERIAL_TYPE_STREAM_CODEC = StreamCodec.of((buf, type) -> {
        Utf8String.write(buf, type.name(), 128);
    }, buf -> {
        String name = Utf8String.read(buf, 128);
        MaterialType w = MaterialType.OAK;
        for (MaterialType wType : MaterialType.values().toList()) {
            if (wType.name().equals(name)) {
                w = wType;
            }
        }
        return w;
    });

    public CarpentryTableRecipe(NonNullList<StackedIngredient> ingredients, ItemStack result, FurnitureCategory category, NonNullList<MaterialType> materials) {
        this.ingredients = ingredients;
        this.result = result;
        this.category = category;
        this.materialTypes = materials;
    }

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput singleRecipeInput, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return SFFRecipeSerializers.CARPENTRY_TABLE_RECIPE.get();
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return SFFRecipeTypes.CARPENTRY_TABLE_RECIPE.get();
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new ShapelessCraftingRecipeDisplay(
                        this.ingredients.stream().map(stackedIngredient -> stackedIngredient.ingredient().display()).toList(),
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public FurnitureCategory getCategory() {
        return category;
    }

    public NonNullList<MaterialType> getMaterialTypes() {
        return materialTypes;
    }

    public NonNullList<StackedIngredient> getMaterials() {
        return ingredients;
    }

    public int getResultId() {
        return Item.getId(result.getItem());
    }

    public ItemStack getResult() {
        return result;
    }

    public static Builder builder(HolderLookup.RegistryLookup<Item> items, ItemLike result, int count, FurnitureCategory category, Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag) {
        return new Builder(items, result.asItem(), count, category, hasItem, hasTag);
    }

    public static class Serializer implements RecipeSerializer<CarpentryTableRecipe> {
        public static final MapCodec<CarpentryTableRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(StackedIngredient.CODEC.listOf().fieldOf("ingredients").flatXmap(materials -> {
            NonNullList<StackedIngredient> inputs = NonNullList.create();
            inputs.addAll(materials);
            return DataResult.success(inputs);
        }, DataResult::success).forGetter(o -> o.ingredients), ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result), FurnitureCategory.CODEC.fieldOf("category").forGetter(recipe -> recipe.category), MaterialType.CODEC.listOf().fieldOf("materials").flatXmap(materials -> {
            NonNullList<MaterialType> inputs = NonNullList.create();
            inputs.addAll(materials);
            return DataResult.success(inputs);
        }, DataResult::success).forGetter(o -> o.materialTypes)).apply(builder, CarpentryTableRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CarpentryTableRecipe> STREAM_CODEC = StreamCodec.composite(
                StackedIngredient.STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity)),
                CarpentryTableRecipe::getMaterials,
                ItemStack.STREAM_CODEC,
                CarpentryTableRecipe::getResult,
                CarpentryTableRecipe.FURNITURE_CATEGORY_STREAM_CODEC,
                CarpentryTableRecipe::getCategory,
                CarpentryTableRecipe.MATERIAL_TYPE_STREAM_CODEC.apply(ByteBufCodecs.collection(NonNullList::createWithCapacity)),
                CarpentryTableRecipe::getMaterialTypes,
                CarpentryTableRecipe::new
        );

        @Override
        public MapCodec<CarpentryTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CarpentryTableRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements RecipeBuilder {
        private final HolderLookup.RegistryLookup<Item> items;
        private final Item result;
        private final int count;
        private final FurnitureCategory furnitureCategory;
        private NonNullList<MaterialType> materials = NonNullList.create();
        private final Function<ItemLike, Criterion<?>> hasItem;
        private final Function<TagKey<Item>, Criterion<?>> hasTag;
        private final NonNullList<StackedIngredient> ingredients = NonNullList.create();
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        private RecipeCategory category = RecipeCategory.MISC;

        private Builder(HolderLookup.RegistryLookup<Item> items, Item result, int count, FurnitureCategory furnitureCategory, Function<ItemLike, Criterion<?>> hasItem, Function<TagKey<Item>, Criterion<?>> hasTag) {
            this.items = items;
            this.result = result;
            this.count = count;
            this.furnitureCategory = furnitureCategory;
            this.hasItem = hasItem;
            this.hasTag = hasTag;
        }

        @Override
        public Builder unlockedBy(String name, Criterion<?> trigger) {
            criteria.put(name, trigger);
            return this;
        }

        @Override
        public Builder group(@Nullable String group) {
            return this;
        }

        public Builder requiresMaterial(ItemStack stack) {
            ingredients.add(new StackedIngredient(Ingredient.of(stack.getItem()), stack.getCount()));
            return this;
        }

        public Builder containsMaterial(MaterialType type) {
            materials.add(type);
            return this;
        }

        public Builder category(RecipeCategory category) {
            this.category = category;
            return this;
        }

        @Override
        public Item getResult() {
            return result;
        }

        @Override
        public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
            validate(id);
            Advancement.Builder builder = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(AdvancementRequirements.Strategy.OR);
            criteria.forEach(builder::addCriterion);
            output.accept(id, new CarpentryTableRecipe(ingredients, new ItemStack(result, count), furnitureCategory, materials), builder.build(id.location().withPrefix("recipes/" + category.getFolderName() + "/")));
        }

        private void validate(ResourceKey<Recipe<?>> id) {
            if(ingredients.isEmpty()) {
                throw new IllegalArgumentException("There must be at least one material for carpentry table recipe %s".formatted(id.location()));
            }
            if(criteria.isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + id.location());
            }
        }
    }
}
