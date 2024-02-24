/*    */ package net.minecraft.item.crafting;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockPrismarine;
/*    */ import net.minecraft.block.BlockQuartz;
/*    */ import net.minecraft.block.BlockRedSandstone;
/*    */ import net.minecraft.block.BlockSand;
/*    */ import net.minecraft.block.BlockSandStone;
/*    */ import net.minecraft.block.BlockStone;
/*    */ import net.minecraft.block.BlockStoneSlab;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RecipesCrafting {
/*    */   public void addRecipes(CraftingManager p_77589_1_) {
/* 16 */     p_77589_1_.addRecipe(new ItemStack((Block)Blocks.chest), new Object[] { "###", "# #", "###", Character.valueOf('#'), Blocks.planks });
/* 17 */     p_77589_1_.addRecipe(new ItemStack(Blocks.trapped_chest), new Object[] { "#-", Character.valueOf('#'), Blocks.chest, Character.valueOf('-'), Blocks.tripwire_hook });
/* 18 */     p_77589_1_.addRecipe(new ItemStack(Blocks.ender_chest), new Object[] { "###", "#E#", "###", Character.valueOf('#'), Blocks.obsidian, Character.valueOf('E'), Items.ender_eye });
/* 19 */     p_77589_1_.addRecipe(new ItemStack(Blocks.furnace), new Object[] { "###", "# #", "###", Character.valueOf('#'), Blocks.cobblestone });
/* 20 */     p_77589_1_.addRecipe(new ItemStack(Blocks.crafting_table), new Object[] { "##", "##", Character.valueOf('#'), Blocks.planks });
/* 21 */     p_77589_1_.addRecipe(new ItemStack(Blocks.sandstone), new Object[] { "##", "##", Character.valueOf('#'), new ItemStack((Block)Blocks.sand, 1, BlockSand.EnumType.SAND.getMetadata()) });
/* 22 */     p_77589_1_.addRecipe(new ItemStack(Blocks.red_sandstone), new Object[] { "##", "##", Character.valueOf('#'), new ItemStack((Block)Blocks.sand, 1, BlockSand.EnumType.RED_SAND.getMetadata()) });
/* 23 */     p_77589_1_.addRecipe(new ItemStack(Blocks.sandstone, 4, BlockSandStone.EnumType.SMOOTH.getMetadata()), new Object[] { "##", "##", Character.valueOf('#'), new ItemStack(Blocks.sandstone, 1, BlockSandStone.EnumType.DEFAULT.getMetadata()) });
/* 24 */     p_77589_1_.addRecipe(new ItemStack(Blocks.red_sandstone, 4, BlockRedSandstone.EnumType.SMOOTH.getMetadata()), new Object[] { "##", "##", Character.valueOf('#'), new ItemStack(Blocks.red_sandstone, 1, BlockRedSandstone.EnumType.DEFAULT.getMetadata()) });
/* 25 */     p_77589_1_.addRecipe(new ItemStack(Blocks.sandstone, 1, BlockSandStone.EnumType.CHISELED.getMetadata()), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack((Block)Blocks.stone_slab, 1, BlockStoneSlab.EnumType.SAND.getMetadata()) });
/* 26 */     p_77589_1_.addRecipe(new ItemStack(Blocks.red_sandstone, 1, BlockRedSandstone.EnumType.CHISELED.getMetadata()), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack((Block)Blocks.stone_slab2, 1, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata()) });
/* 27 */     p_77589_1_.addRecipe(new ItemStack(Blocks.quartz_block, 1, BlockQuartz.EnumType.CHISELED.getMetadata()), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack((Block)Blocks.stone_slab, 1, BlockStoneSlab.EnumType.QUARTZ.getMetadata()) });
/* 28 */     p_77589_1_.addRecipe(new ItemStack(Blocks.quartz_block, 2, BlockQuartz.EnumType.LINES_Y.getMetadata()), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack(Blocks.quartz_block, 1, BlockQuartz.EnumType.DEFAULT.getMetadata()) });
/* 29 */     p_77589_1_.addRecipe(new ItemStack(Blocks.stonebrick, 4), new Object[] { "##", "##", Character.valueOf('#'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 30 */     p_77589_1_.addRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CHISELED_META), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack((Block)Blocks.stone_slab, 1, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()) });
/* 31 */     p_77589_1_.addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.MOSSY_META), new Object[] { Blocks.stonebrick, Blocks.vine });
/* 32 */     p_77589_1_.addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone, 1), new Object[] { Blocks.cobblestone, Blocks.vine });
/* 33 */     p_77589_1_.addRecipe(new ItemStack(Blocks.iron_bars, 16), new Object[] { "###", "###", Character.valueOf('#'), Items.iron_ingot });
/* 34 */     p_77589_1_.addRecipe(new ItemStack(Blocks.glass_pane, 16), new Object[] { "###", "###", Character.valueOf('#'), Blocks.glass });
/* 35 */     p_77589_1_.addRecipe(new ItemStack(Blocks.redstone_lamp, 1), new Object[] { " R ", "RGR", " R ", Character.valueOf('R'), Items.redstone, Character.valueOf('G'), Blocks.glowstone });
/* 36 */     p_77589_1_.addRecipe(new ItemStack((Block)Blocks.beacon, 1), new Object[] { "GGG", "GSG", "OOO", Character.valueOf('G'), Blocks.glass, Character.valueOf('S'), Items.nether_star, Character.valueOf('O'), Blocks.obsidian });
/* 37 */     p_77589_1_.addRecipe(new ItemStack(Blocks.nether_brick, 1), new Object[] { "NN", "NN", Character.valueOf('N'), Items.netherbrick });
/* 38 */     p_77589_1_.addRecipe(new ItemStack(Blocks.stone, 2, BlockStone.EnumType.DIORITE.getMetadata()), new Object[] { "CQ", "QC", Character.valueOf('C'), Blocks.cobblestone, Character.valueOf('Q'), Items.quartz });
/* 39 */     p_77589_1_.addShapelessRecipe(new ItemStack(Blocks.stone, 1, BlockStone.EnumType.GRANITE.getMetadata()), new Object[] { new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetadata()), Items.quartz });
/* 40 */     p_77589_1_.addShapelessRecipe(new ItemStack(Blocks.stone, 2, BlockStone.EnumType.ANDESITE.getMetadata()), new Object[] { new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetadata()), Blocks.cobblestone });
/* 41 */     p_77589_1_.addRecipe(new ItemStack(Blocks.dirt, 4, BlockDirt.DirtType.COARSE_DIRT.getMetadata()), new Object[] { "DG", "GD", Character.valueOf('D'), new ItemStack(Blocks.dirt, 1, BlockDirt.DirtType.DIRT.getMetadata()), Character.valueOf('G'), Blocks.gravel });
/* 42 */     p_77589_1_.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata()), new Object[] { "SS", "SS", Character.valueOf('S'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetadata()) });
/* 43 */     p_77589_1_.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata()), new Object[] { "SS", "SS", Character.valueOf('S'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.GRANITE.getMetadata()) });
/* 44 */     p_77589_1_.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata()), new Object[] { "SS", "SS", Character.valueOf('S'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.ANDESITE.getMetadata()) });
/* 45 */     p_77589_1_.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.ROUGH_META), new Object[] { "SS", "SS", Character.valueOf('S'), Items.prismarine_shard });
/* 46 */     p_77589_1_.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.BRICKS_META), new Object[] { "SSS", "SSS", "SSS", Character.valueOf('S'), Items.prismarine_shard });
/* 47 */     p_77589_1_.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.DARK_META), new Object[] { "SSS", "SIS", "SSS", Character.valueOf('S'), Items.prismarine_shard, Character.valueOf('I'), new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()) });
/* 48 */     p_77589_1_.addRecipe(new ItemStack(Blocks.sea_lantern, 1, 0), new Object[] { "SCS", "CCC", "SCS", Character.valueOf('S'), Items.prismarine_shard, Character.valueOf('C'), Items.prismarine_crystals });
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\crafting\RecipesCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */