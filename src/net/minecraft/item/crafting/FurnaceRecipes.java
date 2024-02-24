/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStoneBrick;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FurnaceRecipes
/*     */ {
/*  18 */   private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
/*  19 */   private final Map<ItemStack, ItemStack> smeltingList = Maps.newHashMap();
/*  20 */   private final Map<ItemStack, Float> experienceList = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FurnaceRecipes instance() {
/*  27 */     return smeltingBase;
/*     */   }
/*     */ 
/*     */   
/*     */   private FurnaceRecipes() {
/*  32 */     addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7F);
/*  33 */     addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0F);
/*  34 */     addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0F);
/*  35 */     addSmeltingRecipeForBlock((Block)Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
/*  36 */     addSmelting(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35F);
/*  37 */     addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35F);
/*  38 */     addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35F);
/*  39 */     addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35F);
/*  40 */     addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35F);
/*  41 */     addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1F);
/*  42 */     addSmeltingRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CRACKED_META), 0.1F);
/*  43 */     addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3F);
/*  44 */     addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35F);
/*  45 */     addSmeltingRecipeForBlock((Block)Blocks.cactus, new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()), 0.2F);
/*  46 */     addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15F);
/*  47 */     addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15F);
/*  48 */     addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0F);
/*  49 */     addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35F);
/*  50 */     addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1F);
/*  51 */     addSmeltingRecipe(new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.15F);
/*     */     
/*  53 */     for (ItemFishFood.FishType itemfishfood$fishtype : ItemFishFood.FishType.values()) {
/*     */       
/*  55 */       if (itemfishfood$fishtype.canCook())
/*     */       {
/*  57 */         addSmeltingRecipe(new ItemStack(Items.fish, 1, itemfishfood$fishtype.getMetadata()), new ItemStack(Items.cooked_fish, 1, itemfishfood$fishtype.getMetadata()), 0.35F);
/*     */       }
/*     */     } 
/*     */     
/*  61 */     addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1F);
/*  62 */     addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7F);
/*  63 */     addSmeltingRecipeForBlock(Blocks.lapis_ore, new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2F);
/*  64 */     addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience) {
/*  72 */     addSmelting(Item.getItemFromBlock(input), stack, experience);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmelting(Item input, ItemStack stack, float experience) {
/*  80 */     addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience) {
/*  88 */     this.smeltingList.put(input, stack);
/*  89 */     this.experienceList.put(stack, Float.valueOf(experience));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSmeltingResult(ItemStack stack) {
/*  97 */     for (Map.Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet()) {
/*     */       
/*  99 */       if (compareItemStacks(stack, entry.getKey()))
/*     */       {
/* 101 */         return entry.getValue();
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
/* 113 */     return (stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ItemStack, ItemStack> getSmeltingList() {
/* 118 */     return this.smeltingList;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSmeltingExperience(ItemStack stack) {
/* 123 */     for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
/*     */       
/* 125 */       if (compareItemStacks(stack, entry.getKey()))
/*     */       {
/* 127 */         return ((Float)entry.getValue()).floatValue();
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\crafting\FurnaceRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */