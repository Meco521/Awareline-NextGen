/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockStairs;
/*      */ import net.minecraft.block.BlockTorch;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.IEntityLivingData;
/*      */ import net.minecraft.entity.passive.EntityVillager;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Vec3i;
/*      */ import net.minecraft.util.WeightedRandomChestContent;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.biome.WorldChunkManager;
/*      */ 
/*      */ public class StructureVillagePieces
/*      */ {
/*      */   public static void registerVillagePieces() {
/*   33 */     MapGenStructureIO.registerStructureComponent((Class)House1.class, "ViBH");
/*   34 */     MapGenStructureIO.registerStructureComponent((Class)Field1.class, "ViDF");
/*   35 */     MapGenStructureIO.registerStructureComponent((Class)Field2.class, "ViF");
/*   36 */     MapGenStructureIO.registerStructureComponent((Class)Torch.class, "ViL");
/*   37 */     MapGenStructureIO.registerStructureComponent((Class)Hall.class, "ViPH");
/*   38 */     MapGenStructureIO.registerStructureComponent((Class)House4Garden.class, "ViSH");
/*   39 */     MapGenStructureIO.registerStructureComponent((Class)WoodHut.class, "ViSmH");
/*   40 */     MapGenStructureIO.registerStructureComponent((Class)Church.class, "ViST");
/*   41 */     MapGenStructureIO.registerStructureComponent((Class)House2.class, "ViS");
/*   42 */     MapGenStructureIO.registerStructureComponent((Class)Start.class, "ViStart");
/*   43 */     MapGenStructureIO.registerStructureComponent((Class)Path.class, "ViSR");
/*   44 */     MapGenStructureIO.registerStructureComponent((Class)House3.class, "ViTRH");
/*   45 */     MapGenStructureIO.registerStructureComponent((Class)Well.class, "ViW");
/*      */   }
/*      */ 
/*      */   
/*      */   public static List<PieceWeight> getStructureVillageWeightedPieceList(Random random, int size) {
/*   50 */     List<PieceWeight> list = Lists.newArrayList();
/*   51 */     list.add(new PieceWeight((Class)House4Garden.class, 4, MathHelper.getRandomIntegerInRange(random, 2 + size, 4 + (size << 1))));
/*   52 */     list.add(new PieceWeight((Class)Church.class, 20, MathHelper.getRandomIntegerInRange(random, size, 1 + size)));
/*   53 */     list.add(new PieceWeight((Class)House1.class, 20, MathHelper.getRandomIntegerInRange(random, size, 2 + size)));
/*   54 */     list.add(new PieceWeight((Class)WoodHut.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + size, 5 + size * 3)));
/*   55 */     list.add(new PieceWeight((Class)Hall.class, 15, MathHelper.getRandomIntegerInRange(random, size, 2 + size)));
/*   56 */     list.add(new PieceWeight((Class)Field1.class, 3, MathHelper.getRandomIntegerInRange(random, 1 + size, 4 + size)));
/*   57 */     list.add(new PieceWeight((Class)Field2.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + size, 4 + (size << 1))));
/*   58 */     list.add(new PieceWeight((Class)House2.class, 15, MathHelper.getRandomIntegerInRange(random, 0, 1 + size)));
/*   59 */     list.add(new PieceWeight((Class)House3.class, 8, MathHelper.getRandomIntegerInRange(random, size, 3 + (size << 1))));
/*      */     
/*   61 */     list.removeIf(pieceWeight -> (pieceWeight.villagePiecesLimit == 0));
/*      */     
/*   63 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int func_75079_a(List<PieceWeight> p_75079_0_) {
/*   68 */     boolean flag = false;
/*   69 */     int i = 0;
/*      */     
/*   71 */     for (PieceWeight structurevillagepieces$pieceweight : p_75079_0_) {
/*      */       
/*   73 */       if (structurevillagepieces$pieceweight.villagePiecesLimit > 0 && structurevillagepieces$pieceweight.villagePiecesSpawned < structurevillagepieces$pieceweight.villagePiecesLimit)
/*      */       {
/*   75 */         flag = true;
/*      */       }
/*      */       
/*   78 */       i += structurevillagepieces$pieceweight.villagePieceWeight;
/*      */     } 
/*      */     
/*   81 */     return flag ? i : -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Village func_176065_a(Start start, PieceWeight weight, List<StructureComponent> p_176065_2_, Random rand, int p_176065_4_, int p_176065_5_, int p_176065_6_, EnumFacing facing, int p_176065_8_) {
/*   86 */     Class<? extends Village> oclass = weight.villagePieceClass;
/*   87 */     Village structurevillagepieces$village = null;
/*      */     
/*   89 */     if (oclass == House4Garden.class) {
/*      */       
/*   91 */       structurevillagepieces$village = House4Garden.func_175858_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     }
/*   93 */     else if (oclass == Church.class) {
/*      */       
/*   95 */       structurevillagepieces$village = Church.func_175854_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     }
/*   97 */     else if (oclass == House1.class) {
/*      */       
/*   99 */       structurevillagepieces$village = House1.func_175850_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     }
/*  101 */     else if (oclass == WoodHut.class) {
/*      */       
/*  103 */       structurevillagepieces$village = WoodHut.func_175853_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     }
/*  105 */     else if (oclass == Hall.class) {
/*      */       
/*  107 */       structurevillagepieces$village = Hall.func_175857_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     }
/*  109 */     else if (oclass == Field1.class) {
/*      */       
/*  111 */       structurevillagepieces$village = Field1.func_175851_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     }
/*  113 */     else if (oclass == Field2.class) {
/*      */       
/*  115 */       structurevillagepieces$village = Field2.func_175852_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     }
/*  117 */     else if (oclass == House2.class) {
/*      */       
/*  119 */       structurevillagepieces$village = House2.func_175855_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     }
/*  121 */     else if (oclass == House3.class) {
/*      */       
/*  123 */       structurevillagepieces$village = House3.func_175849_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     } 
/*      */     
/*  126 */     return structurevillagepieces$village;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Village func_176067_c(Start start, List<StructureComponent> p_176067_1_, Random rand, int p_176067_3_, int p_176067_4_, int p_176067_5_, EnumFacing facing, int p_176067_7_) {
/*  131 */     int i = func_75079_a(start.structureVillageWeightedPieceList);
/*      */     
/*  133 */     if (i <= 0)
/*      */     {
/*  135 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  139 */     int j = 0;
/*      */     
/*  141 */     while (j < 5) {
/*      */       
/*  143 */       j++;
/*  144 */       int k = rand.nextInt(i);
/*      */       
/*  146 */       for (PieceWeight structurevillagepieces$pieceweight : start.structureVillageWeightedPieceList) {
/*      */         
/*  148 */         k -= structurevillagepieces$pieceweight.villagePieceWeight;
/*      */         
/*  150 */         if (k < 0) {
/*      */           
/*  152 */           if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePiecesOfType(p_176067_7_) || (structurevillagepieces$pieceweight == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1)) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  157 */           Village structurevillagepieces$village = func_176065_a(start, structurevillagepieces$pieceweight, p_176067_1_, rand, p_176067_3_, p_176067_4_, p_176067_5_, facing, p_176067_7_);
/*      */           
/*  159 */           if (structurevillagepieces$village != null) {
/*      */             
/*  161 */             structurevillagepieces$pieceweight.villagePiecesSpawned++;
/*  162 */             start.structVillagePieceWeight = structurevillagepieces$pieceweight;
/*      */             
/*  164 */             if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePieces())
/*      */             {
/*  166 */               start.structureVillageWeightedPieceList.remove(structurevillagepieces$pieceweight);
/*      */             }
/*      */             
/*  169 */             return structurevillagepieces$village;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  175 */     StructureBoundingBox structureboundingbox = Torch.func_175856_a(start, p_176067_1_, rand, p_176067_3_, p_176067_4_, p_176067_5_, facing);
/*      */     
/*  177 */     if (structureboundingbox != null)
/*      */     {
/*  179 */       return new Torch(start, p_176067_7_, rand, structureboundingbox, facing);
/*      */     }
/*      */ 
/*      */     
/*  183 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static StructureComponent func_176066_d(Start start, List<StructureComponent> p_176066_1_, Random rand, int p_176066_3_, int p_176066_4_, int p_176066_5_, EnumFacing facing, int p_176066_7_) {
/*  190 */     if (p_176066_7_ > 50)
/*      */     {
/*  192 */       return null;
/*      */     }
/*  194 */     if (Math.abs(p_176066_3_ - (start.getBoundingBox()).minX) <= 112 && Math.abs(p_176066_5_ - (start.getBoundingBox()).minZ) <= 112) {
/*      */       
/*  196 */       StructureComponent structurecomponent = func_176067_c(start, p_176066_1_, rand, p_176066_3_, p_176066_4_, p_176066_5_, facing, p_176066_7_ + 1);
/*      */       
/*  198 */       if (structurecomponent != null) {
/*      */         
/*  200 */         int i = (structurecomponent.boundingBox.minX + structurecomponent.boundingBox.maxX) / 2;
/*  201 */         int j = (structurecomponent.boundingBox.minZ + structurecomponent.boundingBox.maxZ) / 2;
/*  202 */         int k = structurecomponent.boundingBox.maxX - structurecomponent.boundingBox.minX;
/*  203 */         int l = structurecomponent.boundingBox.maxZ - structurecomponent.boundingBox.minZ;
/*  204 */         int i1 = (k > l) ? k : l;
/*      */         
/*  206 */         if (start.getWorldChunkManager().areBiomesViable(i, j, i1 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
/*      */           
/*  208 */           p_176066_1_.add(structurecomponent);
/*  209 */           start.field_74932_i.add(structurecomponent);
/*  210 */           return structurecomponent;
/*      */         } 
/*      */       } 
/*      */       
/*  214 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  218 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static StructureComponent func_176069_e(Start start, List<StructureComponent> p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, EnumFacing facing, int p_176069_7_) {
/*  224 */     if (p_176069_7_ > 3 + start.terrainType)
/*      */     {
/*  226 */       return null;
/*      */     }
/*  228 */     if (Math.abs(p_176069_3_ - (start.getBoundingBox()).minX) <= 112 && Math.abs(p_176069_5_ - (start.getBoundingBox()).minZ) <= 112) {
/*      */       
/*  230 */       StructureBoundingBox structureboundingbox = Path.func_175848_a(start, p_176069_1_, rand, p_176069_3_, p_176069_4_, p_176069_5_, facing);
/*      */       
/*  232 */       if (structureboundingbox != null && structureboundingbox.minY > 10) {
/*      */         
/*  234 */         StructureComponent structurecomponent = new Path(start, p_176069_7_, rand, structureboundingbox, facing);
/*  235 */         int i = (structurecomponent.boundingBox.minX + structurecomponent.boundingBox.maxX) / 2;
/*  236 */         int j = (structurecomponent.boundingBox.minZ + structurecomponent.boundingBox.maxZ) / 2;
/*  237 */         int k = structurecomponent.boundingBox.maxX - structurecomponent.boundingBox.minX;
/*  238 */         int l = structurecomponent.boundingBox.maxZ - structurecomponent.boundingBox.minZ;
/*  239 */         int i1 = (k > l) ? k : l;
/*      */         
/*  241 */         if (start.getWorldChunkManager().areBiomesViable(i, j, i1 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
/*      */           
/*  243 */           p_176069_1_.add(structurecomponent);
/*  244 */           start.field_74930_j.add(structurecomponent);
/*  245 */           return structurecomponent;
/*      */         } 
/*      */       } 
/*      */       
/*  249 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  253 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Church
/*      */     extends Village
/*      */   {
/*      */     public Church() {}
/*      */ 
/*      */     
/*      */     public Church(StructureVillagePieces.Start start, int p_i45564_2_, Random rand, StructureBoundingBox p_i45564_4_, EnumFacing facing) {
/*  265 */       super(start, p_i45564_2_);
/*  266 */       this.coordBaseMode = facing;
/*  267 */       this.boundingBox = p_i45564_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static Church func_175854_a(StructureVillagePieces.Start start, List<StructureComponent> p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, EnumFacing facing, int p_175854_7_) {
/*  272 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, facing);
/*  273 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175854_1_, structureboundingbox) == null) ? new Church(start, p_175854_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  278 */       if (this.field_143015_k < 0) {
/*      */         
/*  280 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  282 */         if (this.field_143015_k < 0)
/*      */         {
/*  284 */           return true;
/*      */         }
/*      */         
/*  287 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
/*      */       } 
/*      */       
/*  290 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  291 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  292 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  293 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  294 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  295 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  296 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  297 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  298 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  299 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  300 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  301 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  302 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  303 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 11, 2, structureBoundingBoxIn);
/*  304 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 11, 2, structureBoundingBoxIn);
/*  305 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 0, structureBoundingBoxIn);
/*  306 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 4, structureBoundingBoxIn);
/*  307 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 6, structureBoundingBoxIn);
/*  308 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 7, structureBoundingBoxIn);
/*  309 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 1, 7, structureBoundingBoxIn);
/*  310 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 6, structureBoundingBoxIn);
/*  311 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 7, structureBoundingBoxIn);
/*  312 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 1, 5, structureBoundingBoxIn);
/*  313 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 1, 6, structureBoundingBoxIn);
/*  314 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 1, 5, structureBoundingBoxIn);
/*  315 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 1)), 1, 2, 7, structureBoundingBoxIn);
/*  316 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 0)), 3, 2, 7, structureBoundingBoxIn);
/*  317 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  318 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
/*  319 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
/*  320 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
/*  321 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 6, 2, structureBoundingBoxIn);
/*  322 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
/*  323 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 6, 2, structureBoundingBoxIn);
/*  324 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
/*  325 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 0, structureBoundingBoxIn);
/*  326 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
/*  327 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 4, structureBoundingBoxIn);
/*  328 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
/*  329 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 6, structureBoundingBoxIn);
/*  330 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 6, structureBoundingBoxIn);
/*  331 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 3, 8, structureBoundingBoxIn);
/*  332 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.getOpposite()), 2, 4, 7, structureBoundingBoxIn);
/*  333 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.rotateY()), 1, 4, 6, structureBoundingBoxIn);
/*  334 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.rotateYCCW()), 3, 4, 6, structureBoundingBoxIn);
/*  335 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 2, 4, 5, structureBoundingBoxIn);
/*  336 */       int i = getMetadataWithOffset(Blocks.ladder, 4);
/*      */       
/*  338 */       for (int j = 1; j <= 9; j++)
/*      */       {
/*  340 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, j, 3, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  343 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/*  344 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/*  345 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/*  347 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
/*      */       {
/*  349 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  352 */       for (int l = 0; l < 9; l++) {
/*      */         
/*  354 */         for (int k = 0; k < 5; k++) {
/*      */           
/*  356 */           clearCurrentPositionBlocksUpwards(worldIn, k, 12, l, structureBoundingBoxIn);
/*  357 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), k, -1, l, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  361 */       spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
/*  362 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/*  367 */       return 2;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Field1
/*      */     extends Village
/*      */   {
/*      */     private Block cropTypeA;
/*      */     
/*      */     private Block cropTypeB;
/*      */     private Block cropTypeC;
/*      */     private Block cropTypeD;
/*      */     
/*      */     public Field1() {}
/*      */     
/*      */     public Field1(StructureVillagePieces.Start start, int p_i45570_2_, Random rand, StructureBoundingBox p_i45570_4_, EnumFacing facing) {
/*  384 */       super(start, p_i45570_2_);
/*  385 */       this.coordBaseMode = facing;
/*  386 */       this.boundingBox = p_i45570_4_;
/*  387 */       this.cropTypeA = func_151559_a(rand);
/*  388 */       this.cropTypeB = func_151559_a(rand);
/*  389 */       this.cropTypeC = func_151559_a(rand);
/*  390 */       this.cropTypeD = func_151559_a(rand);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  395 */       super.writeStructureToNBT(tagCompound);
/*  396 */       tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
/*  397 */       tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
/*  398 */       tagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
/*  399 */       tagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  404 */       super.readStructureFromNBT(tagCompound);
/*  405 */       this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
/*  406 */       this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
/*  407 */       this.cropTypeC = Block.getBlockById(tagCompound.getInteger("CC"));
/*  408 */       this.cropTypeD = Block.getBlockById(tagCompound.getInteger("CD"));
/*      */     }
/*      */ 
/*      */     
/*      */     private Block func_151559_a(Random rand) {
/*  413 */       switch (rand.nextInt(5)) {
/*      */         
/*      */         case 0:
/*  416 */           return Blocks.carrots;
/*      */         
/*      */         case 1:
/*  419 */           return Blocks.potatoes;
/*      */       } 
/*      */       
/*  422 */       return Blocks.wheat;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static Field1 func_175851_a(StructureVillagePieces.Start start, List<StructureComponent> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, EnumFacing facing, int p_175851_7_) {
/*  428 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, facing);
/*  429 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175851_1_, structureboundingbox) == null) ? new Field1(start, p_175851_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  434 */       if (this.field_143015_k < 0) {
/*      */         
/*  436 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  438 */         if (this.field_143015_k < 0)
/*      */         {
/*  440 */           return true;
/*      */         }
/*      */         
/*  443 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/*  446 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  447 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  448 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  449 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  450 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  451 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  452 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  453 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  454 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  455 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  456 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
/*  457 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
/*      */       
/*  459 */       for (int i = 1; i <= 7; i++) {
/*      */         
/*  461 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 1, 1, i, structureBoundingBoxIn);
/*  462 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 2, 1, i, structureBoundingBoxIn);
/*  463 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 4, 1, i, structureBoundingBoxIn);
/*  464 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 5, 1, i, structureBoundingBoxIn);
/*  465 */         setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 7, 1, i, structureBoundingBoxIn);
/*  466 */         setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 8, 1, i, structureBoundingBoxIn);
/*  467 */         setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 10, 1, i, structureBoundingBoxIn);
/*  468 */         setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 11, 1, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  471 */       for (int k = 0; k < 9; k++) {
/*      */         
/*  473 */         for (int j = 0; j < 13; j++) {
/*      */           
/*  475 */           clearCurrentPositionBlocksUpwards(worldIn, j, 4, k, structureBoundingBoxIn);
/*  476 */           replaceAirAndLiquidDownwards(worldIn, Blocks.dirt.getDefaultState(), j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  480 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Field2
/*      */     extends Village
/*      */   {
/*      */     private Block cropTypeA;
/*      */     
/*      */     private Block cropTypeB;
/*      */     
/*      */     public Field2() {}
/*      */     
/*      */     public Field2(StructureVillagePieces.Start start, int p_i45569_2_, Random rand, StructureBoundingBox p_i45569_4_, EnumFacing facing) {
/*  495 */       super(start, p_i45569_2_);
/*  496 */       this.coordBaseMode = facing;
/*  497 */       this.boundingBox = p_i45569_4_;
/*  498 */       this.cropTypeA = func_151560_a(rand);
/*  499 */       this.cropTypeB = func_151560_a(rand);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  504 */       super.writeStructureToNBT(tagCompound);
/*  505 */       tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
/*  506 */       tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  511 */       super.readStructureFromNBT(tagCompound);
/*  512 */       this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
/*  513 */       this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
/*      */     }
/*      */ 
/*      */     
/*      */     private Block func_151560_a(Random rand) {
/*  518 */       switch (rand.nextInt(5)) {
/*      */         
/*      */         case 0:
/*  521 */           return Blocks.carrots;
/*      */         
/*      */         case 1:
/*  524 */           return Blocks.potatoes;
/*      */       } 
/*      */       
/*  527 */       return Blocks.wheat;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static Field2 func_175852_a(StructureVillagePieces.Start start, List<StructureComponent> p_175852_1_, Random rand, int p_175852_3_, int p_175852_4_, int p_175852_5_, EnumFacing facing, int p_175852_7_) {
/*  533 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175852_3_, p_175852_4_, p_175852_5_, 0, 0, 0, 7, 4, 9, facing);
/*  534 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175852_1_, structureboundingbox) == null) ? new Field2(start, p_175852_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  539 */       if (this.field_143015_k < 0) {
/*      */         
/*  541 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  543 */         if (this.field_143015_k < 0)
/*      */         {
/*  545 */           return true;
/*      */         }
/*      */         
/*  548 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/*  551 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  552 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  553 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  554 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  555 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  556 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  557 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  558 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
/*      */       
/*  560 */       for (int i = 1; i <= 7; i++) {
/*      */         
/*  562 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 1, 1, i, structureBoundingBoxIn);
/*  563 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 2, 1, i, structureBoundingBoxIn);
/*  564 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 4, 1, i, structureBoundingBoxIn);
/*  565 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 5, 1, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  568 */       for (int k = 0; k < 9; k++) {
/*      */         
/*  570 */         for (int j = 0; j < 7; j++) {
/*      */           
/*  572 */           clearCurrentPositionBlocksUpwards(worldIn, j, 4, k, structureBoundingBoxIn);
/*  573 */           replaceAirAndLiquidDownwards(worldIn, Blocks.dirt.getDefaultState(), j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  577 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Hall
/*      */     extends Village
/*      */   {
/*      */     public Hall() {}
/*      */ 
/*      */     
/*      */     public Hall(StructureVillagePieces.Start start, int p_i45567_2_, Random rand, StructureBoundingBox p_i45567_4_, EnumFacing facing) {
/*  589 */       super(start, p_i45567_2_);
/*  590 */       this.coordBaseMode = facing;
/*  591 */       this.boundingBox = p_i45567_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static Hall func_175857_a(StructureVillagePieces.Start start, List<StructureComponent> p_175857_1_, Random rand, int p_175857_3_, int p_175857_4_, int p_175857_5_, EnumFacing facing, int p_175857_7_) {
/*  596 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, facing);
/*  597 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175857_1_, structureboundingbox) == null) ? new Hall(start, p_175857_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  602 */       if (this.field_143015_k < 0) {
/*      */         
/*  604 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  606 */         if (this.field_143015_k < 0)
/*      */         {
/*  608 */           return true;
/*      */         }
/*      */         
/*  611 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
/*      */       } 
/*      */       
/*  614 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  615 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  616 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
/*  617 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
/*  618 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  619 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  620 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  621 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  622 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  623 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  624 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  625 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  626 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  627 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  628 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  629 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  630 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  631 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
/*  632 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
/*  633 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
/*  634 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
/*  635 */       int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
/*  636 */       int j = getMetadataWithOffset(Blocks.oak_stairs, 2);
/*      */       
/*  638 */       for (int k = -1; k <= 2; k++) {
/*      */         
/*  640 */         for (int l = 0; l <= 8; l++) {
/*      */           
/*  642 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 4 + k, k, structureBoundingBoxIn);
/*  643 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 4 + k, 5 - k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  647 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
/*  648 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/*  649 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
/*  650 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
/*  651 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  652 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/*  653 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/*  654 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/*  655 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
/*  656 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
/*  657 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/*  658 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  659 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 3, structureBoundingBoxIn);
/*  660 */       setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 3, structureBoundingBoxIn);
/*  661 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 4, structureBoundingBoxIn);
/*  662 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 4, structureBoundingBoxIn);
/*  663 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 3, structureBoundingBoxIn);
/*  664 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
/*  665 */       setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
/*  666 */       setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 2, structureBoundingBoxIn);
/*  667 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/*  668 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/*  669 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
/*  670 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/*  672 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
/*      */       {
/*  674 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  677 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/*  678 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  679 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.getOpposite()), 6, 3, 4, structureBoundingBoxIn);
/*  680 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/*  682 */       for (int i1 = 0; i1 < 5; i1++) {
/*      */         
/*  684 */         for (int j1 = 0; j1 < 9; j1++) {
/*      */           
/*  686 */           clearCurrentPositionBlocksUpwards(worldIn, j1, 7, i1, structureBoundingBoxIn);
/*  687 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j1, -1, i1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  691 */       spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
/*  692 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/*  697 */       return (p_180779_1_ == 0) ? 4 : super.func_180779_c(p_180779_1_, p_180779_2_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class House1
/*      */     extends Village
/*      */   {
/*      */     public House1() {}
/*      */ 
/*      */     
/*      */     public House1(StructureVillagePieces.Start start, int p_i45571_2_, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing) {
/*  709 */       super(start, p_i45571_2_);
/*  710 */       this.coordBaseMode = facing;
/*  711 */       this.boundingBox = p_i45571_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static House1 func_175850_a(StructureVillagePieces.Start start, List<StructureComponent> p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, EnumFacing facing, int p_175850_7_) {
/*  716 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, facing);
/*  717 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175850_1_, structureboundingbox) == null) ? new House1(start, p_175850_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  722 */       if (this.field_143015_k < 0) {
/*      */         
/*  724 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  726 */         if (this.field_143015_k < 0)
/*      */         {
/*  728 */           return true;
/*      */         }
/*      */         
/*  731 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
/*      */       } 
/*      */       
/*  734 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  735 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  736 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  737 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  738 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  739 */       int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
/*  740 */       int j = getMetadataWithOffset(Blocks.oak_stairs, 2);
/*      */       
/*  742 */       for (int k = -1; k <= 2; k++) {
/*      */         
/*  744 */         for (int l = 0; l <= 8; l++) {
/*      */           
/*  746 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 6 + k, k, structureBoundingBoxIn);
/*  747 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 6 + k, 5 - k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  751 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  752 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  753 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  754 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  755 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  756 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  757 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  758 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  759 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  760 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  761 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  762 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  763 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
/*  764 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/*  765 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
/*  766 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
/*  767 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
/*  768 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
/*  769 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  770 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/*  771 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
/*  772 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
/*  773 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/*  774 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/*  775 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
/*  776 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
/*  777 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
/*  778 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
/*  779 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/*  780 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  781 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  782 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  783 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  784 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 1, 4, structureBoundingBoxIn);
/*  785 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 0)), 7, 1, 3, structureBoundingBoxIn);
/*  786 */       int j1 = getMetadataWithOffset(Blocks.oak_stairs, 3);
/*  787 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j1), 6, 1, 4, structureBoundingBoxIn);
/*  788 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j1), 5, 1, 4, structureBoundingBoxIn);
/*  789 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j1), 4, 1, 4, structureBoundingBoxIn);
/*  790 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j1), 3, 1, 4, structureBoundingBoxIn);
/*  791 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
/*  792 */       setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
/*  793 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 1, 3, structureBoundingBoxIn);
/*  794 */       setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 4, 2, 3, structureBoundingBoxIn);
/*  795 */       setBlockState(worldIn, Blocks.crafting_table.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
/*  796 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/*  797 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/*  798 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/*  800 */       if (getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
/*      */       {
/*  802 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  805 */       for (int k1 = 0; k1 < 6; k1++) {
/*      */         
/*  807 */         for (int i1 = 0; i1 < 9; i1++) {
/*      */           
/*  809 */           clearCurrentPositionBlocksUpwards(worldIn, i1, 9, k1, structureBoundingBoxIn);
/*  810 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), i1, -1, k1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  814 */       spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
/*  815 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/*  820 */       return 1;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class House2
/*      */     extends Village {
/*  826 */     private static final List<WeightedRandomChestContent> villageBlacksmithChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
/*      */     
/*      */     private boolean hasMadeChest;
/*      */ 
/*      */     
/*      */     public House2() {}
/*      */ 
/*      */     
/*      */     public House2(StructureVillagePieces.Start start, int p_i45563_2_, Random rand, StructureBoundingBox p_i45563_4_, EnumFacing facing) {
/*  835 */       super(start, p_i45563_2_);
/*  836 */       this.coordBaseMode = facing;
/*  837 */       this.boundingBox = p_i45563_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static House2 func_175855_a(StructureVillagePieces.Start start, List<StructureComponent> p_175855_1_, Random rand, int p_175855_3_, int p_175855_4_, int p_175855_5_, EnumFacing facing, int p_175855_7_) {
/*  842 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, facing);
/*  843 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175855_1_, structureboundingbox) == null) ? new House2(start, p_175855_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  848 */       super.writeStructureToNBT(tagCompound);
/*  849 */       tagCompound.setBoolean("Chest", this.hasMadeChest);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  854 */       super.readStructureFromNBT(tagCompound);
/*  855 */       this.hasMadeChest = tagCompound.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  860 */       if (this.field_143015_k < 0) {
/*      */         
/*  862 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  864 */         if (this.field_143015_k < 0)
/*      */         {
/*  866 */           return true;
/*      */         }
/*      */         
/*  869 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/*  872 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  873 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  874 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  875 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  876 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  877 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  878 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  879 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  880 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  881 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, 1, structureBoundingBoxIn);
/*  882 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  883 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  884 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  885 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  886 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 5, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  887 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  888 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  889 */       setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
/*  890 */       setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, structureBoundingBoxIn);
/*  891 */       setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 5, structureBoundingBoxIn);
/*  892 */       setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 4, structureBoundingBoxIn);
/*  893 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  894 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
/*  895 */       setBlockState(worldIn, Blocks.furnace.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
/*  896 */       setBlockState(worldIn, Blocks.furnace.getDefaultState(), 6, 3, 3, structureBoundingBoxIn);
/*  897 */       setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, structureBoundingBoxIn);
/*  898 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  899 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/*  900 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
/*  901 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);
/*  902 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 4, structureBoundingBoxIn);
/*  903 */       setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
/*  904 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 5, structureBoundingBoxIn);
/*  905 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 5, structureBoundingBoxIn);
/*  906 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 4, structureBoundingBoxIn);
/*      */       
/*  908 */       if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(5, 5), getYWithOffset(1), getZWithOffset(5, 5)))) {
/*      */         
/*  910 */         this.hasMadeChest = true;
/*  911 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, villageBlacksmithChestContents, 3 + randomIn.nextInt(6));
/*      */       } 
/*      */       
/*  914 */       for (int i = 6; i <= 8; i++) {
/*      */         
/*  916 */         if (getBlockStateFromPos(worldIn, i, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
/*      */         {
/*  918 */           setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), i, 0, -1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  922 */       for (int k = 0; k < 7; k++) {
/*      */         
/*  924 */         for (int j = 0; j < 10; j++) {
/*      */           
/*  926 */           clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
/*  927 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  931 */       spawnVillagers(worldIn, structureBoundingBoxIn, 7, 1, 1, 1);
/*  932 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/*  937 */       return 3;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class House3
/*      */     extends Village
/*      */   {
/*      */     public House3() {}
/*      */ 
/*      */     
/*      */     public House3(StructureVillagePieces.Start start, int p_i45561_2_, Random rand, StructureBoundingBox p_i45561_4_, EnumFacing facing) {
/*  949 */       super(start, p_i45561_2_);
/*  950 */       this.coordBaseMode = facing;
/*  951 */       this.boundingBox = p_i45561_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static House3 func_175849_a(StructureVillagePieces.Start start, List<StructureComponent> p_175849_1_, Random rand, int p_175849_3_, int p_175849_4_, int p_175849_5_, EnumFacing facing, int p_175849_7_) {
/*  956 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, facing);
/*  957 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175849_1_, structureboundingbox) == null) ? new House3(start, p_175849_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  962 */       if (this.field_143015_k < 0) {
/*      */         
/*  964 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  966 */         if (this.field_143015_k < 0)
/*      */         {
/*  968 */           return true;
/*      */         }
/*      */         
/*  971 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
/*      */       } 
/*      */       
/*  974 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  975 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  976 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  977 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  978 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  979 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  980 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  981 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  982 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  983 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  984 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  985 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  986 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  987 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  988 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  989 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
/*  990 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
/*  991 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
/*  992 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
/*  993 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 4, structureBoundingBoxIn);
/*  994 */       int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
/*  995 */       int j = getMetadataWithOffset(Blocks.oak_stairs, 2);
/*      */       
/*  997 */       for (int k = -1; k <= 2; k++) {
/*      */         
/*  999 */         for (int l = 0; l <= 8; l++) {
/*      */           
/* 1001 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 4 + k, k, structureBoundingBoxIn);
/*      */           
/* 1003 */           if ((k > -1 || l <= 1) && (k > 0 || l <= 3) && (k > 1 || l <= 4 || l >= 6))
/*      */           {
/* 1005 */             setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 4 + k, 5 - k, structureBoundingBoxIn);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1010 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1011 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1012 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1013 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1014 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1015 */       int k1 = getMetadataWithOffset(Blocks.oak_stairs, 0);
/*      */       
/* 1017 */       for (int l1 = 4; l1 >= 1; l1--) {
/*      */         
/* 1019 */         setBlockState(worldIn, Blocks.planks.getDefaultState(), l1, 2 + l1, 7 - l1, structureBoundingBoxIn);
/*      */         
/* 1021 */         for (int i1 = 8 - l1; i1 <= 10; i1++)
/*      */         {
/* 1023 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(k1), l1, 2 + l1, i1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1027 */       int i2 = getMetadataWithOffset(Blocks.oak_stairs, 1);
/* 1028 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 6, 6, 3, structureBoundingBoxIn);
/* 1029 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 5, 4, structureBoundingBoxIn);
/* 1030 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i2), 6, 6, 4, structureBoundingBoxIn);
/*      */       
/* 1032 */       for (int j2 = 6; j2 <= 8; j2++) {
/*      */         
/* 1034 */         for (int j1 = 5; j1 <= 10; j1++)
/*      */         {
/* 1036 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i2), j2, 12 - j2, j1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1040 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
/* 1041 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/* 1042 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/* 1043 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/* 1044 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
/* 1045 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/* 1046 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
/* 1047 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
/* 1048 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/* 1049 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/* 1050 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
/* 1051 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 2, 5, structureBoundingBoxIn);
/* 1052 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 6, structureBoundingBoxIn);
/* 1053 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
/* 1054 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
/* 1055 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 9, structureBoundingBoxIn);
/* 1056 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
/* 1057 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
/* 1058 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
/* 1059 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 2, 9, structureBoundingBoxIn);
/* 1060 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 4, 4, 10, structureBoundingBoxIn);
/* 1061 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
/* 1062 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 6, 4, 10, structureBoundingBoxIn);
/* 1063 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 5, 5, 10, structureBoundingBoxIn);
/* 1064 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/* 1065 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/* 1066 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
/* 1067 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/* 1068 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       
/* 1070 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
/*      */       {
/* 1072 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/* 1075 */       for (int k2 = 0; k2 < 5; k2++) {
/*      */         
/* 1077 */         for (int i3 = 0; i3 < 9; i3++) {
/*      */           
/* 1079 */           clearCurrentPositionBlocksUpwards(worldIn, i3, 7, k2, structureBoundingBoxIn);
/* 1080 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), i3, -1, k2, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1084 */       for (int l2 = 5; l2 < 11; l2++) {
/*      */         
/* 1086 */         for (int j3 = 2; j3 < 9; j3++) {
/*      */           
/* 1088 */           clearCurrentPositionBlocksUpwards(worldIn, j3, 7, l2, structureBoundingBoxIn);
/* 1089 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j3, -1, l2, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1093 */       spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
/* 1094 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class House4Garden
/*      */     extends Village
/*      */   {
/*      */     private boolean isRoofAccessible;
/*      */ 
/*      */     
/*      */     public House4Garden() {}
/*      */     
/*      */     public House4Garden(StructureVillagePieces.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, EnumFacing facing) {
/* 1108 */       super(start, p_i45566_2_);
/* 1109 */       this.coordBaseMode = facing;
/* 1110 */       this.boundingBox = p_i45566_4_;
/* 1111 */       this.isRoofAccessible = rand.nextBoolean();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1116 */       super.writeStructureToNBT(tagCompound);
/* 1117 */       tagCompound.setBoolean("Terrace", this.isRoofAccessible);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1122 */       super.readStructureFromNBT(tagCompound);
/* 1123 */       this.isRoofAccessible = tagCompound.getBoolean("Terrace");
/*      */     }
/*      */ 
/*      */     
/*      */     public static House4Garden func_175858_a(StructureVillagePieces.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, EnumFacing facing, int p_175858_7_) {
/* 1128 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
/* 1129 */       return (StructureComponent.findIntersecting(p_175858_1_, structureboundingbox) != null) ? null : new House4Garden(start, p_175858_7_, rand, structureboundingbox, facing);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1134 */       if (this.field_143015_k < 0) {
/*      */         
/* 1136 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1138 */         if (this.field_143015_k < 0)
/*      */         {
/* 1140 */           return true;
/*      */         }
/*      */         
/* 1143 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/* 1146 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/* 1147 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1148 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1149 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 0, structureBoundingBoxIn);
/* 1150 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 0, structureBoundingBoxIn);
/* 1151 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 0, structureBoundingBoxIn);
/* 1152 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 0, structureBoundingBoxIn);
/* 1153 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
/* 1154 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
/* 1155 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 4, structureBoundingBoxIn);
/* 1156 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/* 1157 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 4, structureBoundingBoxIn);
/* 1158 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
/* 1159 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 4, structureBoundingBoxIn);
/* 1160 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 4, structureBoundingBoxIn);
/* 1161 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1162 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1163 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1164 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/* 1165 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
/* 1166 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
/* 1167 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/* 1168 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/* 1169 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 3, 0, structureBoundingBoxIn);
/* 1170 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 2, 3, 0, structureBoundingBoxIn);
/* 1171 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, 0, structureBoundingBoxIn);
/* 1172 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 2, 0, structureBoundingBoxIn);
/* 1173 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 1, 0, structureBoundingBoxIn);
/*      */       
/* 1175 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
/*      */       {
/* 1177 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/* 1180 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       
/* 1182 */       if (this.isRoofAccessible) {
/*      */         
/* 1184 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 0, structureBoundingBoxIn);
/* 1185 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 0, structureBoundingBoxIn);
/* 1186 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 0, structureBoundingBoxIn);
/* 1187 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 0, structureBoundingBoxIn);
/* 1188 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 0, structureBoundingBoxIn);
/* 1189 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 4, structureBoundingBoxIn);
/* 1190 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 4, structureBoundingBoxIn);
/* 1191 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 4, structureBoundingBoxIn);
/* 1192 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 4, structureBoundingBoxIn);
/* 1193 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 4, structureBoundingBoxIn);
/* 1194 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 1, structureBoundingBoxIn);
/* 1195 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 2, structureBoundingBoxIn);
/* 1196 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 3, structureBoundingBoxIn);
/* 1197 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 1, structureBoundingBoxIn);
/* 1198 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 2, structureBoundingBoxIn);
/* 1199 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1202 */       if (this.isRoofAccessible) {
/*      */         
/* 1204 */         int i = getMetadataWithOffset(Blocks.ladder, 3);
/* 1205 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 1, 3, structureBoundingBoxIn);
/* 1206 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 2, 3, structureBoundingBoxIn);
/* 1207 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 3, 3, structureBoundingBoxIn);
/* 1208 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 4, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1211 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
/*      */       
/* 1213 */       for (int k = 0; k < 5; k++) {
/*      */         
/* 1215 */         for (int j = 0; j < 5; j++) {
/*      */           
/* 1217 */           clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
/* 1218 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1222 */       spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
/* 1223 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Path
/*      */     extends Road
/*      */   {
/*      */     private int length;
/*      */ 
/*      */     
/*      */     public Path() {}
/*      */     
/*      */     public Path(StructureVillagePieces.Start start, int p_i45562_2_, Random rand, StructureBoundingBox p_i45562_4_, EnumFacing facing) {
/* 1237 */       super(start, p_i45562_2_);
/* 1238 */       this.coordBaseMode = facing;
/* 1239 */       this.boundingBox = p_i45562_4_;
/* 1240 */       this.length = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1245 */       super.writeStructureToNBT(tagCompound);
/* 1246 */       tagCompound.setInteger("Length", this.length);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1251 */       super.readStructureFromNBT(tagCompound);
/* 1252 */       this.length = tagCompound.getInteger("Length");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1257 */       boolean flag = false;
/*      */       int i;
/* 1259 */       for (i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5)) {
/*      */         
/* 1261 */         StructureComponent structurecomponent = getNextComponentNN((StructureVillagePieces.Start)componentIn, listIn, rand, 0, i);
/*      */         
/* 1263 */         if (structurecomponent != null) {
/*      */           
/* 1265 */           i += Math.max(structurecomponent.boundingBox.getXSize(), structurecomponent.boundingBox.getZSize());
/* 1266 */           flag = true;
/*      */         } 
/*      */       } 
/*      */       int j;
/* 1270 */       for (j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5)) {
/*      */         
/* 1272 */         StructureComponent structurecomponent1 = getNextComponentPP((StructureVillagePieces.Start)componentIn, listIn, rand, 0, j);
/*      */         
/* 1274 */         if (structurecomponent1 != null) {
/*      */           
/* 1276 */           j += Math.max(structurecomponent1.boundingBox.getXSize(), structurecomponent1.boundingBox.getZSize());
/* 1277 */           flag = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1281 */       if (flag && rand.nextInt(3) > 0 && this.coordBaseMode != null)
/*      */       {
/* 1283 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case NORTH:
/* 1286 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, getComponentType());
/*      */             break;
/*      */           
/*      */           case SOUTH:
/* 1290 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, getComponentType());
/*      */             break;
/*      */           
/*      */           case WEST:
/* 1294 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */             break;
/*      */           
/*      */           case EAST:
/* 1298 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */             break;
/*      */         } 
/*      */       }
/* 1302 */       if (flag && rand.nextInt(3) > 0 && this.coordBaseMode != null)
/*      */       {
/* 1304 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case NORTH:
/* 1307 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, getComponentType());
/*      */             break;
/*      */           
/*      */           case SOUTH:
/* 1311 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, getComponentType());
/*      */             break;
/*      */           
/*      */           case WEST:
/* 1315 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */             break;
/*      */           
/*      */           case EAST:
/* 1319 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */             break;
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     public static StructureBoundingBox func_175848_a(StructureVillagePieces.Start start, List<StructureComponent> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, EnumFacing facing) {
/* 1326 */       for (int i = 7 * MathHelper.getRandomIntegerInRange(rand, 3, 5); i >= 7; i -= 7) {
/*      */         
/* 1328 */         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, i, facing);
/*      */         
/* 1330 */         if (StructureComponent.findIntersecting(p_175848_1_, structureboundingbox) == null)
/*      */         {
/* 1332 */           return structureboundingbox;
/*      */         }
/*      */       } 
/*      */       
/* 1336 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1341 */       IBlockState iblockstate = func_175847_a(Blocks.gravel.getDefaultState());
/* 1342 */       IBlockState iblockstate1 = func_175847_a(Blocks.cobblestone.getDefaultState());
/*      */       
/* 1344 */       for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; i++) {
/*      */         
/* 1346 */         for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; j++) {
/*      */           
/* 1348 */           BlockPos blockpos = new BlockPos(i, 64, j);
/*      */           
/* 1350 */           if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*      */             
/* 1352 */             blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos).down();
/* 1353 */             worldIn.setBlockState(blockpos, iblockstate, 2);
/* 1354 */             worldIn.setBlockState(blockpos.down(), iblockstate1, 2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1359 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class PieceWeight
/*      */   {
/*      */     public Class<? extends StructureVillagePieces.Village> villagePieceClass;
/*      */     public final int villagePieceWeight;
/*      */     public int villagePiecesSpawned;
/*      */     public int villagePiecesLimit;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureVillagePieces.Village> p_i2098_1_, int p_i2098_2_, int p_i2098_3_) {
/* 1372 */       this.villagePieceClass = p_i2098_1_;
/* 1373 */       this.villagePieceWeight = p_i2098_2_;
/* 1374 */       this.villagePiecesLimit = p_i2098_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canSpawnMoreVillagePiecesOfType(int p_75085_1_) {
/* 1379 */       return (this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canSpawnMoreVillagePieces() {
/* 1384 */       return (this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static abstract class Road
/*      */     extends Village
/*      */   {
/*      */     public Road() {}
/*      */ 
/*      */     
/*      */     protected Road(StructureVillagePieces.Start start, int type) {
/* 1396 */       super(start, type);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Start
/*      */     extends Well {
/*      */     public WorldChunkManager worldChunkMngr;
/*      */     public boolean inDesert;
/*      */     public int terrainType;
/*      */     public StructureVillagePieces.PieceWeight structVillagePieceWeight;
/*      */     public List<StructureVillagePieces.PieceWeight> structureVillageWeightedPieceList;
/* 1407 */     public List<StructureComponent> field_74932_i = Lists.newArrayList();
/* 1408 */     public List<StructureComponent> field_74930_j = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */     
/*      */     public Start() {}
/*      */ 
/*      */     
/*      */     public Start(WorldChunkManager chunkManagerIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List<StructureVillagePieces.PieceWeight> p_i2104_6_, int p_i2104_7_) {
/* 1416 */       super((Start)null, 0, rand, p_i2104_4_, p_i2104_5_);
/* 1417 */       this.worldChunkMngr = chunkManagerIn;
/* 1418 */       this.structureVillageWeightedPieceList = p_i2104_6_;
/* 1419 */       this.terrainType = p_i2104_7_;
/* 1420 */       BiomeGenBase biomegenbase = chunkManagerIn.getBiomeGenerator(new BlockPos(p_i2104_4_, 0, p_i2104_5_), BiomeGenBase.field_180279_ad);
/* 1421 */       this.inDesert = (biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills);
/* 1422 */       func_175846_a(this.inDesert);
/*      */     }
/*      */ 
/*      */     
/*      */     public WorldChunkManager getWorldChunkManager() {
/* 1427 */       return this.worldChunkMngr;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Torch
/*      */     extends Village
/*      */   {
/*      */     public Torch() {}
/*      */ 
/*      */     
/*      */     public Torch(StructureVillagePieces.Start start, int p_i45568_2_, Random rand, StructureBoundingBox p_i45568_4_, EnumFacing facing) {
/* 1439 */       super(start, p_i45568_2_);
/* 1440 */       this.coordBaseMode = facing;
/* 1441 */       this.boundingBox = p_i45568_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static StructureBoundingBox func_175856_a(StructureVillagePieces.Start start, List<StructureComponent> p_175856_1_, Random rand, int p_175856_3_, int p_175856_4_, int p_175856_5_, EnumFacing facing) {
/* 1446 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, facing);
/* 1447 */       return (StructureComponent.findIntersecting(p_175856_1_, structureboundingbox) != null) ? null : structureboundingbox;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1452 */       if (this.field_143015_k < 0) {
/*      */         
/* 1454 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1456 */         if (this.field_143015_k < 0)
/*      */         {
/* 1458 */           return true;
/*      */         }
/*      */         
/* 1461 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/* 1464 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1465 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 0, 0, structureBoundingBoxIn);
/* 1466 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/* 1467 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/* 1468 */       setBlockState(worldIn, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBoxIn);
/* 1469 */       boolean flag = (this.coordBaseMode == EnumFacing.EAST || this.coordBaseMode == EnumFacing.NORTH);
/* 1470 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.rotateY()), flag ? 2 : 0, 3, 0, structureBoundingBoxIn);
/* 1471 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 1, 3, 1, structureBoundingBoxIn);
/* 1472 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.rotateYCCW()), flag ? 0 : 2, 3, 0, structureBoundingBoxIn);
/* 1473 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.getOpposite()), 1, 3, -1, structureBoundingBoxIn);
/* 1474 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Village
/*      */     extends StructureComponent {
/* 1480 */     protected int field_143015_k = -1;
/*      */     
/*      */     private int villagersSpawned;
/*      */     
/*      */     private boolean isDesertVillage;
/*      */ 
/*      */     
/*      */     public Village() {}
/*      */     
/*      */     protected Village(StructureVillagePieces.Start start, int type) {
/* 1490 */       super(type);
/*      */       
/* 1492 */       if (start != null)
/*      */       {
/* 1494 */         this.isDesertVillage = start.inDesert;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1500 */       tagCompound.setInteger("HPos", this.field_143015_k);
/* 1501 */       tagCompound.setInteger("VCount", this.villagersSpawned);
/* 1502 */       tagCompound.setBoolean("Desert", this.isDesertVillage);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1507 */       this.field_143015_k = tagCompound.getInteger("HPos");
/* 1508 */       this.villagersSpawned = tagCompound.getInteger("VCount");
/* 1509 */       this.isDesertVillage = tagCompound.getBoolean("Desert");
/*      */     }
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentNN(StructureVillagePieces.Start start, List<StructureComponent> p_74891_2_, Random rand, int p_74891_4_, int p_74891_5_) {
/* 1514 */       if (this.coordBaseMode != null)
/*      */       {
/* 1516 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case NORTH:
/* 1519 */             return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1522 */             return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1525 */             return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */           
/*      */           case EAST:
/* 1528 */             return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1532 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentPP(StructureVillagePieces.Start start, List<StructureComponent> p_74894_2_, Random rand, int p_74894_4_, int p_74894_5_) {
/* 1537 */       if (this.coordBaseMode != null)
/*      */       {
/* 1539 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case NORTH:
/* 1542 */             return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1545 */             return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1548 */             return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */           
/*      */           case EAST:
/* 1551 */             return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1555 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int getAverageGroundLevel(World worldIn, StructureBoundingBox p_74889_2_) {
/* 1560 */       int i = 0;
/* 1561 */       int j = 0;
/* 1562 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 1564 */       for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; k++) {
/*      */         
/* 1566 */         for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; l++) {
/*      */           
/* 1568 */           blockpos$mutableblockpos.set(l, 64, k);
/*      */           
/* 1570 */           if (p_74889_2_.isVecInside((Vec3i)blockpos$mutableblockpos)) {
/*      */             
/* 1572 */             i += Math.max(worldIn.getTopSolidOrLiquidBlock((BlockPos)blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel());
/* 1573 */             j++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1578 */       if (j == 0)
/*      */       {
/* 1580 */         return -1;
/*      */       }
/*      */ 
/*      */       
/* 1584 */       return i / j;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected static boolean canVillageGoDeeper(StructureBoundingBox p_74895_0_) {
/* 1590 */       return (p_74895_0_ != null && p_74895_0_.minY > 10);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void spawnVillagers(World worldIn, StructureBoundingBox p_74893_2_, int p_74893_3_, int p_74893_4_, int p_74893_5_, int p_74893_6_) {
/* 1595 */       if (this.villagersSpawned < p_74893_6_)
/*      */       {
/* 1597 */         for (int i = this.villagersSpawned; i < p_74893_6_; i++) {
/*      */           
/* 1599 */           int j = getXWithOffset(p_74893_3_ + i, p_74893_5_);
/* 1600 */           int k = getYWithOffset(p_74893_4_);
/* 1601 */           int l = getZWithOffset(p_74893_3_ + i, p_74893_5_);
/*      */           
/* 1603 */           if (!p_74893_2_.isVecInside((Vec3i)new BlockPos(j, k, l))) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/* 1608 */           this.villagersSpawned++;
/* 1609 */           EntityVillager entityvillager = new EntityVillager(worldIn);
/* 1610 */           entityvillager.setLocationAndAngles(j + 0.5D, k, l + 0.5D, 0.0F, 0.0F);
/* 1611 */           entityvillager.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), (IEntityLivingData)null);
/* 1612 */           entityvillager.setProfession(func_180779_c(i, entityvillager.getProfession()));
/* 1613 */           worldIn.spawnEntityInWorld((Entity)entityvillager);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/* 1620 */       return p_180779_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected IBlockState func_175847_a(IBlockState p_175847_1_) {
/* 1625 */       if (this.isDesertVillage) {
/*      */         
/* 1627 */         if (p_175847_1_.getBlock() == Blocks.log || p_175847_1_.getBlock() == Blocks.log2)
/*      */         {
/* 1629 */           return Blocks.sandstone.getDefaultState();
/*      */         }
/*      */         
/* 1632 */         if (p_175847_1_.getBlock() == Blocks.cobblestone)
/*      */         {
/* 1634 */           return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
/*      */         }
/*      */         
/* 1637 */         if (p_175847_1_.getBlock() == Blocks.planks)
/*      */         {
/* 1639 */           return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
/*      */         }
/*      */         
/* 1642 */         if (p_175847_1_.getBlock() == Blocks.oak_stairs)
/*      */         {
/* 1644 */           return Blocks.sandstone_stairs.getDefaultState().withProperty((IProperty)BlockStairs.FACING, p_175847_1_.getValue((IProperty)BlockStairs.FACING));
/*      */         }
/*      */         
/* 1647 */         if (p_175847_1_.getBlock() == Blocks.stone_stairs)
/*      */         {
/* 1649 */           return Blocks.sandstone_stairs.getDefaultState().withProperty((IProperty)BlockStairs.FACING, p_175847_1_.getValue((IProperty)BlockStairs.FACING));
/*      */         }
/*      */         
/* 1652 */         if (p_175847_1_.getBlock() == Blocks.gravel)
/*      */         {
/* 1654 */           return Blocks.sandstone.getDefaultState();
/*      */         }
/*      */       } 
/*      */       
/* 1658 */       return p_175847_1_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 1663 */       IBlockState iblockstate = func_175847_a(blockstateIn);
/* 1664 */       super.setBlockState(worldIn, iblockstate, x, y, z, boundingboxIn);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly) {
/* 1669 */       IBlockState iblockstate = func_175847_a(boundaryBlockState);
/* 1670 */       IBlockState iblockstate1 = func_175847_a(insideBlockState);
/* 1671 */       super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, iblockstate, iblockstate1, existingOnly);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 1676 */       IBlockState iblockstate = func_175847_a(blockstateIn);
/* 1677 */       super.replaceAirAndLiquidDownwards(worldIn, iblockstate, x, y, z, boundingboxIn);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void func_175846_a(boolean p_175846_1_) {
/* 1682 */       this.isDesertVillage = p_175846_1_;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Well
/*      */     extends Village
/*      */   {
/*      */     public Well() {}
/*      */ 
/*      */     
/*      */     public Well(StructureVillagePieces.Start start, int p_i2109_2_, Random rand, int p_i2109_4_, int p_i2109_5_) {
/* 1694 */       super(start, p_i2109_2_);
/* 1695 */       this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(rand);
/*      */       
/* 1697 */       switch (this.coordBaseMode) {
/*      */         
/*      */         case NORTH:
/*      */         case SOUTH:
/* 1701 */           this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
/*      */           return;
/*      */       } 
/*      */       
/* 1705 */       this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1711 */       StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, getComponentType());
/* 1712 */       StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, getComponentType());
/* 1713 */       StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/* 1714 */       StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1719 */       if (this.field_143015_k < 0) {
/*      */         
/* 1721 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1723 */         if (this.field_143015_k < 0)
/*      */         {
/* 1725 */           return true;
/*      */         }
/*      */         
/* 1728 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
/*      */       } 
/*      */       
/* 1731 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), false);
/* 1732 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 12, 2, structureBoundingBoxIn);
/* 1733 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 3, 12, 2, structureBoundingBoxIn);
/* 1734 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 12, 3, structureBoundingBoxIn);
/* 1735 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 3, 12, 3, structureBoundingBoxIn);
/* 1736 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 1, structureBoundingBoxIn);
/* 1737 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 1, structureBoundingBoxIn);
/* 1738 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 1, structureBoundingBoxIn);
/* 1739 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 1, structureBoundingBoxIn);
/* 1740 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 4, structureBoundingBoxIn);
/* 1741 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 4, structureBoundingBoxIn);
/* 1742 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 4, structureBoundingBoxIn);
/* 1743 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 4, structureBoundingBoxIn);
/* 1744 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*      */       
/* 1746 */       for (int i = 0; i <= 5; i++) {
/*      */         
/* 1748 */         for (int j = 0; j <= 5; j++) {
/*      */           
/* 1750 */           if (j == 0 || j == 5 || i == 0 || i == 5) {
/*      */             
/* 1752 */             setBlockState(worldIn, Blocks.gravel.getDefaultState(), j, 11, i, structureBoundingBoxIn);
/* 1753 */             clearCurrentPositionBlocksUpwards(worldIn, j, 12, i, structureBoundingBoxIn);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1758 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class WoodHut
/*      */     extends Village
/*      */   {
/*      */     private boolean isTallHouse;
/*      */     
/*      */     private int tablePosition;
/*      */     
/*      */     public WoodHut() {}
/*      */     
/*      */     public WoodHut(StructureVillagePieces.Start start, int p_i45565_2_, Random rand, StructureBoundingBox p_i45565_4_, EnumFacing facing) {
/* 1773 */       super(start, p_i45565_2_);
/* 1774 */       this.coordBaseMode = facing;
/* 1775 */       this.boundingBox = p_i45565_4_;
/* 1776 */       this.isTallHouse = rand.nextBoolean();
/* 1777 */       this.tablePosition = rand.nextInt(3);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1782 */       super.writeStructureToNBT(tagCompound);
/* 1783 */       tagCompound.setInteger("T", this.tablePosition);
/* 1784 */       tagCompound.setBoolean("C", this.isTallHouse);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1789 */       super.readStructureFromNBT(tagCompound);
/* 1790 */       this.tablePosition = tagCompound.getInteger("T");
/* 1791 */       this.isTallHouse = tagCompound.getBoolean("C");
/*      */     }
/*      */ 
/*      */     
/*      */     public static WoodHut func_175853_a(StructureVillagePieces.Start start, List<StructureComponent> p_175853_1_, Random rand, int p_175853_3_, int p_175853_4_, int p_175853_5_, EnumFacing facing, int p_175853_7_) {
/* 1796 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, facing);
/* 1797 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175853_1_, structureboundingbox) == null) ? new WoodHut(start, p_175853_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1802 */       if (this.field_143015_k < 0) {
/*      */         
/* 1804 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1806 */         if (this.field_143015_k < 0)
/*      */         {
/* 1808 */           return true;
/*      */         }
/*      */         
/* 1811 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/* 1814 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1815 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/* 1816 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
/*      */       
/* 1818 */       if (this.isTallHouse) {
/*      */         
/* 1820 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*      */       }
/*      */       else {
/*      */         
/* 1824 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*      */       } 
/*      */       
/* 1827 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 1, 4, 0, structureBoundingBoxIn);
/* 1828 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 4, 0, structureBoundingBoxIn);
/* 1829 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 1, 4, 4, structureBoundingBoxIn);
/* 1830 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 4, 4, structureBoundingBoxIn);
/* 1831 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 1, structureBoundingBoxIn);
/* 1832 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
/* 1833 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
/* 1834 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 1, structureBoundingBoxIn);
/* 1835 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 2, structureBoundingBoxIn);
/* 1836 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 3, structureBoundingBoxIn);
/* 1837 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1838 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1839 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1840 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1841 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1842 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1843 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1844 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1845 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/* 1846 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);
/*      */       
/* 1848 */       if (this.tablePosition > 0) {
/*      */         
/* 1850 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, structureBoundingBoxIn);
/* 1851 */         setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1854 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/* 1855 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/* 1856 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/* 1858 */       if (getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air)
/*      */       {
/* 1860 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/* 1863 */       for (int i = 0; i < 5; i++) {
/*      */         
/* 1865 */         for (int j = 0; j < 4; j++) {
/*      */           
/* 1867 */           clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBoxIn);
/* 1868 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, i, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1872 */       spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
/* 1873 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\StructureVillagePieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */