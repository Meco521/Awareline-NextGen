/*     */ package net.minecraft.item.crafting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.BlockStoneSlab;
/*     */ import net.minecraft.block.BlockWall;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CraftingManager {
/*  21 */   private static final CraftingManager instance = new CraftingManager();
/*  22 */   private final List<IRecipe> recipes = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CraftingManager getInstance() {
/*  29 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private CraftingManager() {
/*  34 */     (new RecipesTools()).addRecipes(this);
/*  35 */     (new RecipesWeapons()).addRecipes(this);
/*  36 */     (new RecipesIngots()).addRecipes(this);
/*  37 */     (new RecipesFood()).addRecipes(this);
/*  38 */     (new RecipesCrafting()).addRecipes(this);
/*  39 */     (new RecipesArmor()).addRecipes(this);
/*  40 */     (new RecipesDyes()).addRecipes(this);
/*  41 */     this.recipes.add(new RecipesArmorDyes());
/*  42 */     this.recipes.add(new RecipeBookCloning());
/*  43 */     this.recipes.add(new RecipesMapCloning());
/*  44 */     this.recipes.add(new RecipesMapExtending());
/*  45 */     this.recipes.add(new RecipeFireworks());
/*  46 */     this.recipes.add(new RecipeRepairItem());
/*  47 */     (new RecipesBanners()).addRecipes(this);
/*  48 */     addRecipe(new ItemStack(Items.paper, 3), new Object[] { "###", Character.valueOf('#'), Items.reeds });
/*  49 */     addShapelessRecipe(new ItemStack(Items.book, 1), new Object[] { Items.paper, Items.paper, Items.paper, Items.leather });
/*  50 */     addShapelessRecipe(new ItemStack(Items.writable_book, 1), new Object[] { Items.book, new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), Items.feather });
/*  51 */     addRecipe(new ItemStack(Blocks.oak_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/*  52 */     addRecipe(new ItemStack(Blocks.birch_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/*  53 */     addRecipe(new ItemStack(Blocks.spruce_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/*  54 */     addRecipe(new ItemStack(Blocks.jungle_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/*  55 */     addRecipe(new ItemStack(Blocks.acacia_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/*  56 */     addRecipe(new ItemStack(Blocks.dark_oak_fence, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/*  57 */     addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, BlockWall.EnumType.NORMAL.getMetadata()), new Object[] { "###", "###", Character.valueOf('#'), Blocks.cobblestone });
/*  58 */     addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, BlockWall.EnumType.MOSSY.getMetadata()), new Object[] { "###", "###", Character.valueOf('#'), Blocks.mossy_cobblestone });
/*  59 */     addRecipe(new ItemStack(Blocks.nether_brick_fence, 6), new Object[] { "###", "###", Character.valueOf('#'), Blocks.nether_brick });
/*  60 */     addRecipe(new ItemStack(Blocks.oak_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/*  61 */     addRecipe(new ItemStack(Blocks.birch_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/*  62 */     addRecipe(new ItemStack(Blocks.spruce_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/*  63 */     addRecipe(new ItemStack(Blocks.jungle_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/*  64 */     addRecipe(new ItemStack(Blocks.acacia_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/*  65 */     addRecipe(new ItemStack(Blocks.dark_oak_fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/*  66 */     addRecipe(new ItemStack(Blocks.jukebox, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.diamond });
/*  67 */     addRecipe(new ItemStack(Items.lead, 2), new Object[] { "~~ ", "~O ", "  ~", Character.valueOf('~'), Items.string, Character.valueOf('O'), Items.slime_ball });
/*  68 */     addRecipe(new ItemStack(Blocks.noteblock, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.redstone });
/*  69 */     addRecipe(new ItemStack(Blocks.bookshelf, 1), new Object[] { "###", "XXX", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.book });
/*  70 */     addRecipe(new ItemStack(Blocks.snow, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.snowball });
/*  71 */     addRecipe(new ItemStack(Blocks.snow_layer, 6), new Object[] { "###", Character.valueOf('#'), Blocks.snow });
/*  72 */     addRecipe(new ItemStack(Blocks.clay, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.clay_ball });
/*  73 */     addRecipe(new ItemStack(Blocks.brick_block, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.brick });
/*  74 */     addRecipe(new ItemStack(Blocks.glowstone, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.glowstone_dust });
/*  75 */     addRecipe(new ItemStack(Blocks.quartz_block, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.quartz });
/*  76 */     addRecipe(new ItemStack(Blocks.wool, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.string });
/*  77 */     addRecipe(new ItemStack(Blocks.tnt, 1), new Object[] { "X#X", "#X#", "X#X", Character.valueOf('X'), Items.gunpowder, Character.valueOf('#'), Blocks.sand });
/*  78 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.COBBLESTONE.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.cobblestone });
/*  79 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.STONE.getMetadata()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.stone, BlockStone.EnumType.STONE.getMetadata()) });
/*  80 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.SAND.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.sandstone });
/*  81 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.BRICK.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.brick_block });
/*  82 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.stonebrick });
/*  83 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.NETHERBRICK.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.nether_brick });
/*  84 */     addRecipe(new ItemStack((Block)Blocks.stone_slab, 6, BlockStoneSlab.EnumType.QUARTZ.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.quartz_block });
/*  85 */     addRecipe(new ItemStack((Block)Blocks.stone_slab2, 6, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata()), new Object[] { "###", Character.valueOf('#'), Blocks.red_sandstone });
/*  86 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, 0), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/*  87 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, BlockPlanks.EnumType.BIRCH.getMetadata()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/*  88 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, BlockPlanks.EnumType.SPRUCE.getMetadata()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/*  89 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, BlockPlanks.EnumType.JUNGLE.getMetadata()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/*  90 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/*  91 */     addRecipe(new ItemStack((Block)Blocks.wooden_slab, 6, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/*  92 */     addRecipe(new ItemStack(Blocks.ladder, 3), new Object[] { "# #", "###", "# #", Character.valueOf('#'), Items.stick });
/*  93 */     addRecipe(new ItemStack(Items.oak_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/*  94 */     addRecipe(new ItemStack(Items.spruce_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/*  95 */     addRecipe(new ItemStack(Items.birch_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/*  96 */     addRecipe(new ItemStack(Items.jungle_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/*  97 */     addRecipe(new ItemStack(Items.acacia_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.ACACIA.getMetadata()) });
/*  98 */     addRecipe(new ItemStack(Items.dark_oak_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.DARK_OAK.getMetadata()) });
/*  99 */     addRecipe(new ItemStack(Blocks.trapdoor, 2), new Object[] { "###", "###", Character.valueOf('#'), Blocks.planks });
/* 100 */     addRecipe(new ItemStack(Items.iron_door, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), Items.iron_ingot });
/* 101 */     addRecipe(new ItemStack(Blocks.iron_trapdoor, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.iron_ingot });
/* 102 */     addRecipe(new ItemStack(Items.sign, 3), new Object[] { "###", "###", " X ", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.stick });
/* 103 */     addRecipe(new ItemStack(Items.cake, 1), new Object[] { "AAA", "BEB", "CCC", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), Items.sugar, Character.valueOf('C'), Items.wheat, Character.valueOf('E'), Items.egg });
/* 104 */     addRecipe(new ItemStack(Items.sugar, 1), new Object[] { "#", Character.valueOf('#'), Items.reeds });
/* 105 */     addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.OAK.getMetadata()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/* 106 */     addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.SPRUCE.getMetadata()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/* 107 */     addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.BIRCH.getMetadata()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/* 108 */     addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.JUNGLE.getMetadata()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/* 109 */     addRecipe(new ItemStack(Blocks.planks, 4, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/* 110 */     addRecipe(new ItemStack(Blocks.planks, 4, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/* 111 */     addRecipe(new ItemStack(Items.stick, 4), new Object[] { "#", "#", Character.valueOf('#'), Blocks.planks });
/* 112 */     addRecipe(new ItemStack(Blocks.torch, 4), new Object[] { "X", "#", Character.valueOf('X'), Items.coal, Character.valueOf('#'), Items.stick });
/* 113 */     addRecipe(new ItemStack(Blocks.torch, 4), new Object[] { "X", "#", Character.valueOf('X'), new ItemStack(Items.coal, 1, 1), Character.valueOf('#'), Items.stick });
/* 114 */     addRecipe(new ItemStack(Items.bowl, 4), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.planks });
/* 115 */     addRecipe(new ItemStack(Items.glass_bottle, 3), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.glass });
/* 116 */     addRecipe(new ItemStack(Blocks.rail, 16), new Object[] { "X X", "X#X", "X X", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Items.stick });
/* 117 */     addRecipe(new ItemStack(Blocks.golden_rail, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Items.stick });
/* 118 */     addRecipe(new ItemStack(Blocks.activator_rail, 6), new Object[] { "XSX", "X#X", "XSX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('S'), Items.stick });
/* 119 */     addRecipe(new ItemStack(Blocks.detector_rail, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Blocks.stone_pressure_plate });
/* 120 */     addRecipe(new ItemStack(Items.minecart, 1), new Object[] { "# #", "###", Character.valueOf('#'), Items.iron_ingot });
/* 121 */     addRecipe(new ItemStack(Items.cauldron, 1), new Object[] { "# #", "# #", "###", Character.valueOf('#'), Items.iron_ingot });
/* 122 */     addRecipe(new ItemStack(Items.brewing_stand, 1), new Object[] { " B ", "###", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('B'), Items.blaze_rod });
/* 123 */     addRecipe(new ItemStack(Blocks.lit_pumpkin, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.pumpkin, Character.valueOf('B'), Blocks.torch });
/* 124 */     addRecipe(new ItemStack(Items.chest_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.chest, Character.valueOf('B'), Items.minecart });
/* 125 */     addRecipe(new ItemStack(Items.furnace_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.furnace, Character.valueOf('B'), Items.minecart });
/* 126 */     addRecipe(new ItemStack(Items.tnt_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.tnt, Character.valueOf('B'), Items.minecart });
/* 127 */     addRecipe(new ItemStack(Items.hopper_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.hopper, Character.valueOf('B'), Items.minecart });
/* 128 */     addRecipe(new ItemStack(Items.boat, 1), new Object[] { "# #", "###", Character.valueOf('#'), Blocks.planks });
/* 129 */     addRecipe(new ItemStack(Items.bucket, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.iron_ingot });
/* 130 */     addRecipe(new ItemStack(Items.flower_pot, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.brick });
/* 131 */     addShapelessRecipe(new ItemStack(Items.flint_and_steel, 1), new Object[] { new ItemStack(Items.iron_ingot, 1), new ItemStack(Items.flint, 1) });
/* 132 */     addRecipe(new ItemStack(Items.bread, 1), new Object[] { "###", Character.valueOf('#'), Items.wheat });
/* 133 */     addRecipe(new ItemStack(Blocks.oak_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()) });
/* 134 */     addRecipe(new ItemStack(Blocks.birch_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()) });
/* 135 */     addRecipe(new ItemStack(Blocks.spruce_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()) });
/* 136 */     addRecipe(new ItemStack(Blocks.jungle_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()) });
/* 137 */     addRecipe(new ItemStack(Blocks.acacia_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.getMetadata() - 4) });
/* 138 */     addRecipe(new ItemStack(Blocks.dark_oak_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4) });
/* 139 */     addRecipe(new ItemStack((Item)Items.fishing_rod, 1), new Object[] { "  #", " #X", "# X", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.string });
/* 140 */     addRecipe(new ItemStack(Items.carrot_on_a_stick, 1), new Object[] { "# ", " X", Character.valueOf('#'), Items.fishing_rod, Character.valueOf('X'), Items.carrot });
/* 141 */     addRecipe(new ItemStack(Blocks.stone_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.cobblestone });
/* 142 */     addRecipe(new ItemStack(Blocks.brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.brick_block });
/* 143 */     addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.stonebrick });
/* 144 */     addRecipe(new ItemStack(Blocks.nether_brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.nether_brick });
/* 145 */     addRecipe(new ItemStack(Blocks.sandstone_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.sandstone });
/* 146 */     addRecipe(new ItemStack(Blocks.red_sandstone_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.red_sandstone });
/* 147 */     addRecipe(new ItemStack(Blocks.quartz_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.quartz_block });
/* 148 */     addRecipe(new ItemStack(Items.painting, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Blocks.wool });
/* 149 */     addRecipe(new ItemStack(Items.item_frame, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.leather });
/* 150 */     addRecipe(new ItemStack(Items.golden_apple, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.apple });
/* 151 */     addRecipe(new ItemStack(Items.golden_apple, 1, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.gold_block, Character.valueOf('X'), Items.apple });
/* 152 */     addRecipe(new ItemStack(Items.golden_carrot, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.carrot });
/* 153 */     addRecipe(new ItemStack(Items.speckled_melon, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.melon });
/* 154 */     addRecipe(new ItemStack(Blocks.lever, 1), new Object[] { "X", "#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.stick });
/* 155 */     addRecipe(new ItemStack((Block)Blocks.tripwire_hook, 2), new Object[] { "I", "S", "#", Character.valueOf('#'), Blocks.planks, Character.valueOf('S'), Items.stick, Character.valueOf('I'), Items.iron_ingot });
/* 156 */     addRecipe(new ItemStack(Blocks.redstone_torch, 1), new Object[] { "X", "#", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.redstone });
/* 157 */     addRecipe(new ItemStack(Items.repeater, 1), new Object[] { "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.redstone, Character.valueOf('I'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 158 */     addRecipe(new ItemStack(Items.comparator, 1), new Object[] { " # ", "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.quartz, Character.valueOf('I'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 159 */     addRecipe(new ItemStack(Items.clock, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.redstone });
/* 160 */     addRecipe(new ItemStack(Items.compass, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.iron_ingot, Character.valueOf('X'), Items.redstone });
/* 161 */     addRecipe(new ItemStack((Item)Items.map, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.paper, Character.valueOf('X'), Items.compass });
/* 162 */     addRecipe(new ItemStack(Blocks.stone_button, 1), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 163 */     addRecipe(new ItemStack(Blocks.wooden_button, 1), new Object[] { "#", Character.valueOf('#'), Blocks.planks });
/* 164 */     addRecipe(new ItemStack(Blocks.stone_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
/* 165 */     addRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Blocks.planks });
/* 166 */     addRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Items.iron_ingot });
/* 167 */     addRecipe(new ItemStack(Blocks.light_weighted_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Items.gold_ingot });
/* 168 */     addRecipe(new ItemStack(Blocks.dispenser, 1), new Object[] { "###", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.bow, Character.valueOf('R'), Items.redstone });
/* 169 */     addRecipe(new ItemStack(Blocks.dropper, 1), new Object[] { "###", "# #", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('R'), Items.redstone });
/* 170 */     addRecipe(new ItemStack((Block)Blocks.piston, 1), new Object[] { "TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('T'), Blocks.planks });
/* 171 */     addRecipe(new ItemStack((Block)Blocks.sticky_piston, 1), new Object[] { "S", "P", Character.valueOf('S'), Items.slime_ball, Character.valueOf('P'), Blocks.piston });
/* 172 */     addRecipe(new ItemStack(Items.bed, 1), new Object[] { "###", "XXX", Character.valueOf('#'), Blocks.wool, Character.valueOf('X'), Blocks.planks });
/* 173 */     addRecipe(new ItemStack(Blocks.enchanting_table, 1), new Object[] { " B ", "D#D", "###", Character.valueOf('#'), Blocks.obsidian, Character.valueOf('B'), Items.book, Character.valueOf('D'), Items.diamond });
/* 174 */     addRecipe(new ItemStack(Blocks.anvil, 1), new Object[] { "III", " i ", "iii", Character.valueOf('I'), Blocks.iron_block, Character.valueOf('i'), Items.iron_ingot });
/* 175 */     addRecipe(new ItemStack(Items.leather), new Object[] { "##", "##", Character.valueOf('#'), Items.rabbit_hide });
/* 176 */     addShapelessRecipe(new ItemStack(Items.ender_eye, 1), new Object[] { Items.ender_pearl, Items.blaze_powder });
/* 177 */     addShapelessRecipe(new ItemStack(Items.fire_charge, 3), new Object[] { Items.gunpowder, Items.blaze_powder, Items.coal });
/* 178 */     addShapelessRecipe(new ItemStack(Items.fire_charge, 3), new Object[] { Items.gunpowder, Items.blaze_powder, new ItemStack(Items.coal, 1, 1) });
/* 179 */     addRecipe(new ItemStack((Block)Blocks.daylight_detector), new Object[] { "GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.glass, Character.valueOf('Q'), Items.quartz, Character.valueOf('W'), Blocks.wooden_slab });
/* 180 */     addRecipe(new ItemStack((Block)Blocks.hopper), new Object[] { "I I", "ICI", " I ", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest });
/* 181 */     addRecipe(new ItemStack((Item)Items.armor_stand, 1), new Object[] { "///", " / ", "/_/", Character.valueOf('/'), Items.stick, Character.valueOf('_'), new ItemStack((Block)Blocks.stone_slab, 1, BlockStoneSlab.EnumType.STONE.getMetadata()) });
/* 182 */     this.recipes.sort(new Comparator<IRecipe>() {
/*     */           public int compare(IRecipe p_compare_1_, IRecipe p_compare_2_) {
/* 184 */             return (p_compare_1_ instanceof ShapelessRecipes && p_compare_2_ instanceof ShapedRecipes) ? 1 : ((p_compare_2_ instanceof ShapelessRecipes && p_compare_1_ instanceof ShapedRecipes) ? -1 : ((p_compare_2_.getRecipeSize() < p_compare_1_.getRecipeSize()) ? -1 : ((p_compare_2_.getRecipeSize() > p_compare_1_.getRecipeSize()) ? 1 : 0)));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapedRecipes addRecipe(ItemStack stack, Object... recipeComponents) {
/* 194 */     StringBuilder s = new StringBuilder();
/* 195 */     int i = 0;
/* 196 */     int j = 0;
/* 197 */     int k = 0;
/*     */     
/* 199 */     if (recipeComponents[i] instanceof String[]) {
/*     */       
/* 201 */       String[] astring = (String[])recipeComponents[i++];
/*     */       
/* 203 */       for (int l = 0; l < astring.length; l++)
/*     */       {
/* 205 */         String s2 = astring[l];
/* 206 */         k++;
/* 207 */         j = s2.length();
/* 208 */         s.append(s2);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 213 */       while (recipeComponents[i] instanceof String) {
/*     */         
/* 215 */         String s1 = (String)recipeComponents[i++];
/* 216 */         k++;
/* 217 */         j = s1.length();
/* 218 */         s.append(s1);
/*     */       } 
/*     */     } 
/*     */     
/*     */     Map<Character, ItemStack> map;
/*     */     
/* 224 */     for (map = Maps.newHashMap(); i < recipeComponents.length; i += 2) {
/*     */       
/* 226 */       Character character = (Character)recipeComponents[i];
/* 227 */       ItemStack itemstack = null;
/*     */       
/* 229 */       if (recipeComponents[i + 1] instanceof Item) {
/*     */         
/* 231 */         itemstack = new ItemStack((Item)recipeComponents[i + 1]);
/*     */       }
/* 233 */       else if (recipeComponents[i + 1] instanceof Block) {
/*     */         
/* 235 */         itemstack = new ItemStack((Block)recipeComponents[i + 1], 1, 32767);
/*     */       }
/* 237 */       else if (recipeComponents[i + 1] instanceof ItemStack) {
/*     */         
/* 239 */         itemstack = (ItemStack)recipeComponents[i + 1];
/*     */       } 
/*     */       
/* 242 */       map.put(character, itemstack);
/*     */     } 
/*     */     
/* 245 */     ItemStack[] aitemstack = new ItemStack[j * k];
/*     */     
/* 247 */     for (int i1 = 0; i1 < j * k; i1++) {
/*     */       
/* 249 */       char c0 = s.charAt(i1);
/*     */       
/* 251 */       if (map.containsKey(Character.valueOf(c0))) {
/*     */         
/* 253 */         aitemstack[i1] = ((ItemStack)map.get(Character.valueOf(c0))).copy();
/*     */       }
/*     */       else {
/*     */         
/* 257 */         aitemstack[i1] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, stack);
/* 262 */     this.recipes.add(shapedrecipes);
/* 263 */     return shapedrecipes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addShapelessRecipe(ItemStack stack, Object... recipeComponents) {
/* 271 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/* 273 */     for (Object object : recipeComponents) {
/*     */       
/* 275 */       if (object instanceof ItemStack) {
/*     */         
/* 277 */         list.add(((ItemStack)object).copy());
/*     */       }
/* 279 */       else if (object instanceof Item) {
/*     */         
/* 281 */         list.add(new ItemStack((Item)object));
/*     */       }
/*     */       else {
/*     */         
/* 285 */         if (!(object instanceof Block))
/*     */         {
/* 287 */           throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
/*     */         }
/*     */         
/* 290 */         list.add(new ItemStack((Block)object));
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     this.recipes.add(new ShapelessRecipes(stack, list));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRecipe(IRecipe recipe) {
/* 302 */     this.recipes.add(recipe);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack findMatchingRecipe(InventoryCrafting p_82787_1_, World worldIn) {
/* 310 */     for (IRecipe irecipe : this.recipes) {
/*     */       
/* 312 */       if (irecipe.matches(p_82787_1_, worldIn))
/*     */       {
/* 314 */         return irecipe.getCraftingResult(p_82787_1_);
/*     */       }
/*     */     } 
/*     */     
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] func_180303_b(InventoryCrafting p_180303_1_, World worldIn) {
/* 323 */     for (IRecipe irecipe : this.recipes) {
/*     */       
/* 325 */       if (irecipe.matches(p_180303_1_, worldIn))
/*     */       {
/* 327 */         return irecipe.getRemainingItems(p_180303_1_);
/*     */       }
/*     */     } 
/*     */     
/* 331 */     ItemStack[] aitemstack = new ItemStack[p_180303_1_.getSizeInventory()];
/*     */     
/* 333 */     for (int i = 0; i < aitemstack.length; i++)
/*     */     {
/* 335 */       aitemstack[i] = p_180303_1_.getStackInSlot(i);
/*     */     }
/*     */     
/* 338 */     return aitemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IRecipe> getRecipeList() {
/* 343 */     return this.recipes;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\crafting\CraftingManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */