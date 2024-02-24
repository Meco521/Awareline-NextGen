/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.Vec3i;
/*      */ import net.minecraft.util.WeightedRandomChestContent;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class StructureNetherBridgePieces
/*      */ {
/*   21 */   static final PieceWeight[] primaryComponents = new PieceWeight[] { new PieceWeight((Class)Straight.class, 30, 0, true), new PieceWeight((Class)Crossing3.class, 10, 4), new PieceWeight((Class)Crossing.class, 10, 4), new PieceWeight((Class)Stairs.class, 10, 3), new PieceWeight((Class)Throne.class, 5, 2), new PieceWeight((Class)Entrance.class, 5, 1) };
/*   22 */   static final PieceWeight[] secondaryComponents = new PieceWeight[] { new PieceWeight((Class)Corridor5.class, 25, 0, true), new PieceWeight((Class)Crossing2.class, 15, 5), new PieceWeight((Class)Corridor2.class, 5, 10), new PieceWeight((Class)Corridor.class, 5, 10), new PieceWeight((Class)Corridor3.class, 10, 3, true), new PieceWeight((Class)Corridor4.class, 7, 2), new PieceWeight((Class)NetherStalkRoom.class, 5, 2) };
/*      */ 
/*      */   
/*      */   public static void registerNetherFortressPieces() {
/*   26 */     MapGenStructureIO.registerStructureComponent((Class)Crossing3.class, "NeBCr");
/*   27 */     MapGenStructureIO.registerStructureComponent((Class)End.class, "NeBEF");
/*   28 */     MapGenStructureIO.registerStructureComponent((Class)Straight.class, "NeBS");
/*   29 */     MapGenStructureIO.registerStructureComponent((Class)Corridor3.class, "NeCCS");
/*   30 */     MapGenStructureIO.registerStructureComponent((Class)Corridor4.class, "NeCTB");
/*   31 */     MapGenStructureIO.registerStructureComponent((Class)Entrance.class, "NeCE");
/*   32 */     MapGenStructureIO.registerStructureComponent((Class)Crossing2.class, "NeSCSC");
/*   33 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "NeSCLT");
/*   34 */     MapGenStructureIO.registerStructureComponent((Class)Corridor5.class, "NeSC");
/*   35 */     MapGenStructureIO.registerStructureComponent((Class)Corridor2.class, "NeSCRT");
/*   36 */     MapGenStructureIO.registerStructureComponent((Class)NetherStalkRoom.class, "NeCSR");
/*   37 */     MapGenStructureIO.registerStructureComponent((Class)Throne.class, "NeMT");
/*   38 */     MapGenStructureIO.registerStructureComponent((Class)Crossing.class, "NeRC");
/*   39 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "NeSR");
/*   40 */     MapGenStructureIO.registerStructureComponent((Class)Start.class, "NeStart");
/*      */   }
/*      */ 
/*      */   
/*      */   static Piece func_175887_b(PieceWeight p_175887_0_, List<StructureComponent> p_175887_1_, Random p_175887_2_, int p_175887_3_, int p_175887_4_, int p_175887_5_, EnumFacing p_175887_6_, int p_175887_7_) {
/*   45 */     Class<? extends Piece> oclass = p_175887_0_.weightClass;
/*   46 */     Piece structurenetherbridgepieces$piece = null;
/*      */     
/*   48 */     if (oclass == Straight.class) {
/*      */       
/*   50 */       structurenetherbridgepieces$piece = Straight.func_175882_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   52 */     else if (oclass == Crossing3.class) {
/*      */       
/*   54 */       structurenetherbridgepieces$piece = Crossing3.func_175885_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   56 */     else if (oclass == Crossing.class) {
/*      */       
/*   58 */       structurenetherbridgepieces$piece = Crossing.func_175873_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   60 */     else if (oclass == Stairs.class) {
/*      */       
/*   62 */       structurenetherbridgepieces$piece = Stairs.func_175872_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
/*      */     }
/*   64 */     else if (oclass == Throne.class) {
/*      */       
/*   66 */       structurenetherbridgepieces$piece = Throne.func_175874_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
/*      */     }
/*   68 */     else if (oclass == Entrance.class) {
/*      */       
/*   70 */       structurenetherbridgepieces$piece = Entrance.func_175881_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   72 */     else if (oclass == Corridor5.class) {
/*      */       
/*   74 */       structurenetherbridgepieces$piece = Corridor5.func_175877_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   76 */     else if (oclass == Corridor2.class) {
/*      */       
/*   78 */       structurenetherbridgepieces$piece = Corridor2.func_175876_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   80 */     else if (oclass == Corridor.class) {
/*      */       
/*   82 */       structurenetherbridgepieces$piece = Corridor.func_175879_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   84 */     else if (oclass == Corridor3.class) {
/*      */       
/*   86 */       structurenetherbridgepieces$piece = Corridor3.func_175883_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   88 */     else if (oclass == Corridor4.class) {
/*      */       
/*   90 */       structurenetherbridgepieces$piece = Corridor4.func_175880_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   92 */     else if (oclass == Crossing2.class) {
/*      */       
/*   94 */       structurenetherbridgepieces$piece = Crossing2.func_175878_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   96 */     else if (oclass == NetherStalkRoom.class) {
/*      */       
/*   98 */       structurenetherbridgepieces$piece = NetherStalkRoom.func_175875_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     } 
/*      */     
/*  101 */     return structurenetherbridgepieces$piece;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor
/*      */     extends Piece
/*      */   {
/*      */     private boolean field_111021_b;
/*      */ 
/*      */     
/*      */     public Corridor() {}
/*      */     
/*      */     public Corridor(int p_i45615_1_, Random p_i45615_2_, StructureBoundingBox p_i45615_3_, EnumFacing p_i45615_4_) {
/*  114 */       super(p_i45615_1_);
/*  115 */       this.coordBaseMode = p_i45615_4_;
/*  116 */       this.boundingBox = p_i45615_3_;
/*  117 */       this.field_111021_b = (p_i45615_2_.nextInt(3) == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  122 */       super.readStructureFromNBT(tagCompound);
/*  123 */       this.field_111021_b = tagCompound.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  128 */       super.writeStructureToNBT(tagCompound);
/*  129 */       tagCompound.setBoolean("Chest", this.field_111021_b);
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  134 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor func_175879_a(List<StructureComponent> p_175879_0_, Random p_175879_1_, int p_175879_2_, int p_175879_3_, int p_175879_4_, EnumFacing p_175879_5_, int p_175879_6_) {
/*  139 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175879_2_, p_175879_3_, p_175879_4_, -1, 0, 0, 5, 7, 5, p_175879_5_);
/*  140 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175879_0_, structureboundingbox) == null) ? new Corridor(p_175879_6_, p_175879_1_, structureboundingbox, p_175879_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  145 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  146 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  147 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  148 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  149 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  150 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  151 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 3, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  152 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  153 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  155 */       if (this.field_111021_b && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(3, 3), getYWithOffset(2), getZWithOffset(3, 3)))) {
/*      */         
/*  157 */         this.field_111021_b = false;
/*  158 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, field_111019_a, 2 + randomIn.nextInt(4));
/*      */       } 
/*      */       
/*  161 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  163 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  165 */         for (int j = 0; j <= 4; j++)
/*      */         {
/*  167 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  171 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor2
/*      */     extends Piece
/*      */   {
/*      */     private boolean field_111020_b;
/*      */ 
/*      */     
/*      */     public Corridor2() {}
/*      */     
/*      */     public Corridor2(int p_i45613_1_, Random p_i45613_2_, StructureBoundingBox p_i45613_3_, EnumFacing p_i45613_4_) {
/*  185 */       super(p_i45613_1_);
/*  186 */       this.coordBaseMode = p_i45613_4_;
/*  187 */       this.boundingBox = p_i45613_3_;
/*  188 */       this.field_111020_b = (p_i45613_2_.nextInt(3) == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  193 */       super.readStructureFromNBT(tagCompound);
/*  194 */       this.field_111020_b = tagCompound.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  199 */       super.writeStructureToNBT(tagCompound);
/*  200 */       tagCompound.setBoolean("Chest", this.field_111020_b);
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  205 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor2 func_175876_a(List<StructureComponent> p_175876_0_, Random p_175876_1_, int p_175876_2_, int p_175876_3_, int p_175876_4_, EnumFacing p_175876_5_, int p_175876_6_) {
/*  210 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175876_2_, p_175876_3_, p_175876_4_, -1, 0, 0, 5, 7, 5, p_175876_5_);
/*  211 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175876_0_, structureboundingbox) == null) ? new Corridor2(p_175876_6_, p_175876_1_, structureboundingbox, p_175876_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  216 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  217 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  218 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  219 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  220 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  221 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  222 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  223 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  224 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  226 */       if (this.field_111020_b && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(1, 3), getYWithOffset(2), getZWithOffset(1, 3)))) {
/*      */         
/*  228 */         this.field_111020_b = false;
/*  229 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 1, 2, 3, field_111019_a, 2 + randomIn.nextInt(4));
/*      */       } 
/*      */       
/*  232 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  234 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  236 */         for (int j = 0; j <= 4; j++)
/*      */         {
/*  238 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  242 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor3
/*      */     extends Piece
/*      */   {
/*      */     public Corridor3() {}
/*      */ 
/*      */     
/*      */     public Corridor3(int p_i45619_1_, Random p_i45619_2_, StructureBoundingBox p_i45619_3_, EnumFacing p_i45619_4_) {
/*  254 */       super(p_i45619_1_);
/*  255 */       this.coordBaseMode = p_i45619_4_;
/*  256 */       this.boundingBox = p_i45619_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  261 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor3 func_175883_a(List<StructureComponent> p_175883_0_, Random p_175883_1_, int p_175883_2_, int p_175883_3_, int p_175883_4_, EnumFacing p_175883_5_, int p_175883_6_) {
/*  266 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175883_2_, p_175883_3_, p_175883_4_, -1, -7, 0, 5, 14, 10, p_175883_5_);
/*  267 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175883_0_, structureboundingbox) == null) ? new Corridor3(p_175883_6_, p_175883_1_, structureboundingbox, p_175883_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  272 */       int i = getMetadataWithOffset(Blocks.nether_brick_stairs, 2);
/*      */       
/*  274 */       for (int j = 0; j <= 9; j++) {
/*      */         
/*  276 */         int k = Math.max(1, 7 - j);
/*  277 */         int l = Math.min(Math.max(k + 5, 14 - j), 13);
/*  278 */         int i1 = j;
/*  279 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, j, 4, k, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  280 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, k + 1, j, 3, l - 1, j, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */         
/*  282 */         if (j <= 6) {
/*      */           
/*  284 */           setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 1, k + 1, j, structureBoundingBoxIn);
/*  285 */           setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 2, k + 1, j, structureBoundingBoxIn);
/*  286 */           setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 3, k + 1, j, structureBoundingBoxIn);
/*      */         } 
/*      */         
/*  289 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, l, j, 4, l, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  290 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, k + 1, j, 0, l - 1, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  291 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, k + 1, j, 4, l - 1, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         
/*  293 */         if ((j & 0x1) == 0) {
/*      */           
/*  295 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, k + 2, j, 0, k + 3, j, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  296 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 4, k + 2, j, 4, k + 3, j, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */         } 
/*      */         
/*  299 */         for (int j1 = 0; j1 <= 4; j1++)
/*      */         {
/*  301 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j1, -1, i1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  305 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor4
/*      */     extends Piece
/*      */   {
/*      */     public Corridor4() {}
/*      */ 
/*      */     
/*      */     public Corridor4(int p_i45618_1_, Random p_i45618_2_, StructureBoundingBox p_i45618_3_, EnumFacing p_i45618_4_) {
/*  317 */       super(p_i45618_1_);
/*  318 */       this.coordBaseMode = p_i45618_4_;
/*  319 */       this.boundingBox = p_i45618_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  324 */       int i = 1;
/*      */       
/*  326 */       if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH)
/*      */       {
/*  328 */         i = 5;
/*      */       }
/*      */       
/*  331 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, i, (rand.nextInt(8) > 0));
/*  332 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, i, (rand.nextInt(8) > 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor4 func_175880_a(List<StructureComponent> p_175880_0_, Random p_175880_1_, int p_175880_2_, int p_175880_3_, int p_175880_4_, EnumFacing p_175880_5_, int p_175880_6_) {
/*  337 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175880_2_, p_175880_3_, p_175880_4_, -3, 0, 0, 9, 7, 9, p_175880_5_);
/*  338 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175880_0_, structureboundingbox) == null) ? new Corridor4(p_175880_6_, p_175880_1_, structureboundingbox, p_175880_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  343 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  344 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 8, 5, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  345 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 8, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  346 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 2, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  347 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 8, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  348 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  349 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  350 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 8, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  351 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  352 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 7, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  353 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  354 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  355 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  356 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 4, 0, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  357 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 3, 4, 8, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  358 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  359 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 5, 7, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  360 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  361 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/*  363 */       for (int i = 0; i <= 5; i++) {
/*      */         
/*  365 */         for (int j = 0; j <= 8; j++)
/*      */         {
/*  367 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j, -1, i, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  371 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor5
/*      */     extends Piece
/*      */   {
/*      */     public Corridor5() {}
/*      */ 
/*      */     
/*      */     public Corridor5(int p_i45614_1_, Random p_i45614_2_, StructureBoundingBox p_i45614_3_, EnumFacing p_i45614_4_) {
/*  383 */       super(p_i45614_1_);
/*  384 */       this.coordBaseMode = p_i45614_4_;
/*  385 */       this.boundingBox = p_i45614_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  390 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor5 func_175877_a(List<StructureComponent> p_175877_0_, Random p_175877_1_, int p_175877_2_, int p_175877_3_, int p_175877_4_, EnumFacing p_175877_5_, int p_175877_6_) {
/*  395 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175877_2_, p_175877_3_, p_175877_4_, -1, 0, 0, 5, 7, 5, p_175877_5_);
/*  396 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175877_0_, structureboundingbox) == null) ? new Corridor5(p_175877_6_, p_175877_1_, structureboundingbox, p_175877_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  401 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  402 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  403 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  404 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  405 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  406 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  407 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  408 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  409 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  411 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  413 */         for (int j = 0; j <= 4; j++)
/*      */         {
/*  415 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  419 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Crossing
/*      */     extends Piece
/*      */   {
/*      */     public Crossing() {}
/*      */ 
/*      */     
/*      */     public Crossing(int p_i45610_1_, Random p_i45610_2_, StructureBoundingBox p_i45610_3_, EnumFacing p_i45610_4_) {
/*  431 */       super(p_i45610_1_);
/*  432 */       this.coordBaseMode = p_i45610_4_;
/*  433 */       this.boundingBox = p_i45610_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  438 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 2, 0, false);
/*  439 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 2, false);
/*  440 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 2, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Crossing func_175873_a(List<StructureComponent> p_175873_0_, Random p_175873_1_, int p_175873_2_, int p_175873_3_, int p_175873_4_, EnumFacing p_175873_5_, int p_175873_6_) {
/*  445 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175873_2_, p_175873_3_, p_175873_4_, -2, 0, 0, 7, 9, 7, p_175873_5_);
/*  446 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175873_0_, structureboundingbox) == null) ? new Crossing(p_175873_6_, p_175873_1_, structureboundingbox, p_175873_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  451 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  452 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 7, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  453 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  454 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 6, 1, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  455 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  456 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 6, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  457 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  458 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  459 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 6, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  460 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 5, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  461 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 0, 4, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  462 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  463 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 6, 4, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  464 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  465 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 2, 0, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  466 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  467 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 6, 2, 6, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  468 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/*  470 */       for (int i = 0; i <= 6; i++) {
/*      */         
/*  472 */         for (int j = 0; j <= 6; j++)
/*      */         {
/*  474 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  478 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Crossing2
/*      */     extends Piece
/*      */   {
/*      */     public Crossing2() {}
/*      */ 
/*      */     
/*      */     public Crossing2(int p_i45616_1_, Random p_i45616_2_, StructureBoundingBox p_i45616_3_, EnumFacing p_i45616_4_) {
/*  490 */       super(p_i45616_1_);
/*  491 */       this.coordBaseMode = p_i45616_4_;
/*  492 */       this.boundingBox = p_i45616_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  497 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*  498 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*  499 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Crossing2 func_175878_a(List<StructureComponent> p_175878_0_, Random p_175878_1_, int p_175878_2_, int p_175878_3_, int p_175878_4_, EnumFacing p_175878_5_, int p_175878_6_) {
/*  504 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175878_2_, p_175878_3_, p_175878_4_, -1, 0, 0, 5, 7, 5, p_175878_5_);
/*  505 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175878_0_, structureboundingbox) == null) ? new Crossing2(p_175878_6_, p_175878_1_, structureboundingbox, p_175878_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  510 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  511 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  512 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  513 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  514 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  515 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  516 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  518 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  520 */         for (int j = 0; j <= 4; j++)
/*      */         {
/*  522 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  526 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Crossing3
/*      */     extends Piece
/*      */   {
/*      */     public Crossing3() {}
/*      */ 
/*      */     
/*      */     public Crossing3(int p_i45622_1_, Random p_i45622_2_, StructureBoundingBox p_i45622_3_, EnumFacing p_i45622_4_) {
/*  538 */       super(p_i45622_1_);
/*  539 */       this.coordBaseMode = p_i45622_4_;
/*  540 */       this.boundingBox = p_i45622_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Crossing3(Random p_i2042_1_, int p_i2042_2_, int p_i2042_3_) {
/*  545 */       super(0);
/*  546 */       this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2042_1_);
/*      */       
/*  548 */       switch (this.coordBaseMode) {
/*      */         
/*      */         case NORTH:
/*      */         case SOUTH:
/*  552 */           this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
/*      */           return;
/*      */       } 
/*      */       
/*  556 */       this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  562 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 8, 3, false);
/*  563 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 3, 8, false);
/*  564 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 3, 8, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Crossing3 func_175885_a(List<StructureComponent> p_175885_0_, Random p_175885_1_, int p_175885_2_, int p_175885_3_, int p_175885_4_, EnumFacing p_175885_5_, int p_175885_6_) {
/*  569 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175885_2_, p_175885_3_, p_175885_4_, -8, -3, 0, 19, 10, 19, p_175885_5_);
/*  570 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175885_0_, structureboundingbox) == null) ? new Crossing3(p_175885_6_, p_175885_1_, structureboundingbox, p_175885_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  575 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 11, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  576 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 7, 18, 4, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  577 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  578 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 8, 18, 7, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  579 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 5, 0, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  580 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 5, 11, 7, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  581 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 11, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  582 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 11, 11, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  583 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  584 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 7, 18, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  585 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 11, 7, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  586 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 11, 18, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  587 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 11, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  588 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 13, 11, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  589 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 0, 11, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  590 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 15, 11, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  592 */       for (int i = 7; i <= 11; i++) {
/*      */         
/*  594 */         for (int j = 0; j <= 2; j++) {
/*      */           
/*  596 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*  597 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, 18 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  601 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 7, 5, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  602 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 2, 7, 18, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  603 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 7, 3, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  604 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 0, 7, 18, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  606 */       for (int k = 0; k <= 2; k++) {
/*      */         
/*  608 */         for (int l = 7; l <= 11; l++) {
/*      */           
/*  610 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), k, -1, l, structureBoundingBoxIn);
/*  611 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 18 - k, -1, l, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  615 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class End
/*      */     extends Piece
/*      */   {
/*      */     private int fillSeed;
/*      */ 
/*      */     
/*      */     public End() {}
/*      */     
/*      */     public End(int p_i45621_1_, Random p_i45621_2_, StructureBoundingBox p_i45621_3_, EnumFacing p_i45621_4_) {
/*  629 */       super(p_i45621_1_);
/*  630 */       this.coordBaseMode = p_i45621_4_;
/*  631 */       this.boundingBox = p_i45621_3_;
/*  632 */       this.fillSeed = p_i45621_2_.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public static End func_175884_a(List<StructureComponent> p_175884_0_, Random p_175884_1_, int p_175884_2_, int p_175884_3_, int p_175884_4_, EnumFacing p_175884_5_, int p_175884_6_) {
/*  637 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175884_2_, p_175884_3_, p_175884_4_, -1, -3, 0, 5, 10, 8, p_175884_5_);
/*  638 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175884_0_, structureboundingbox) == null) ? new End(p_175884_6_, p_175884_1_, structureboundingbox, p_175884_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  643 */       super.readStructureFromNBT(tagCompound);
/*  644 */       this.fillSeed = tagCompound.getInteger("Seed");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  649 */       super.writeStructureToNBT(tagCompound);
/*  650 */       tagCompound.setInteger("Seed", this.fillSeed);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  655 */       Random random = new Random(this.fillSeed);
/*      */       
/*  657 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  659 */         for (int j = 3; j <= 4; j++) {
/*      */           
/*  661 */           int k = random.nextInt(8);
/*  662 */           fillWithBlocks(worldIn, structureBoundingBoxIn, i, j, 0, i, j, k, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         } 
/*      */       } 
/*      */       
/*  666 */       int l = random.nextInt(8);
/*  667 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 0, 5, l, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  668 */       l = random.nextInt(8);
/*  669 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 0, 4, 5, l, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  671 */       for (l = 0; l <= 4; l++) {
/*      */         
/*  673 */         int i1 = random.nextInt(5);
/*  674 */         fillWithBlocks(worldIn, structureBoundingBoxIn, l, 2, 0, l, 2, i1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       } 
/*      */       
/*  677 */       for (l = 0; l <= 4; l++) {
/*      */         
/*  679 */         for (int j1 = 0; j1 <= 1; j1++) {
/*      */           
/*  681 */           int k1 = random.nextInt(3);
/*  682 */           fillWithBlocks(worldIn, structureBoundingBoxIn, l, j1, 0, l, j1, k1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         } 
/*      */       } 
/*      */       
/*  686 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Entrance
/*      */     extends Piece
/*      */   {
/*      */     public Entrance() {}
/*      */ 
/*      */     
/*      */     public Entrance(int p_i45617_1_, Random p_i45617_2_, StructureBoundingBox p_i45617_3_, EnumFacing p_i45617_4_) {
/*  698 */       super(p_i45617_1_);
/*  699 */       this.coordBaseMode = p_i45617_4_;
/*  700 */       this.boundingBox = p_i45617_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  705 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 3, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Entrance func_175881_a(List<StructureComponent> p_175881_0_, Random p_175881_1_, int p_175881_2_, int p_175881_3_, int p_175881_4_, EnumFacing p_175881_5_, int p_175881_6_) {
/*  710 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175881_2_, p_175881_3_, p_175881_4_, -5, -3, 0, 13, 14, 13, p_175881_5_);
/*  711 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175881_0_, structureboundingbox) == null) ? new Entrance(p_175881_6_, p_175881_1_, structureboundingbox, p_175881_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  716 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  717 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  718 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  719 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  720 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  721 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  722 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  723 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  724 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  725 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  726 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  727 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/*  729 */       for (int i = 1; i <= 11; i += 2) {
/*      */         
/*  731 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 0, i, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  732 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 12, i, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  733 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 10, i, 0, 11, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  734 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 10, i, 12, 11, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  735 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), i, 13, 0, structureBoundingBoxIn);
/*  736 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), i, 13, 12, structureBoundingBoxIn);
/*  737 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, i, structureBoundingBoxIn);
/*  738 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, i, structureBoundingBoxIn);
/*  739 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), i + 1, 13, 0, structureBoundingBoxIn);
/*  740 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), i + 1, 13, 12, structureBoundingBoxIn);
/*  741 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, i + 1, structureBoundingBoxIn);
/*  742 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, i + 1, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  745 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  746 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, structureBoundingBoxIn);
/*  747 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  748 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, structureBoundingBoxIn);
/*      */       
/*  750 */       for (int k = 3; k <= 9; k += 2) {
/*      */         
/*  752 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, k, 1, 8, k, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  753 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 7, k, 11, 8, k, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       } 
/*      */       
/*  756 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  757 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  758 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  759 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  760 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  761 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  763 */       for (int l = 4; l <= 8; l++) {
/*      */         
/*  765 */         for (int j = 0; j <= 2; j++) {
/*      */           
/*  767 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), l, -1, j, structureBoundingBoxIn);
/*  768 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), l, -1, 12 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  772 */       for (int i1 = 0; i1 <= 2; i1++) {
/*      */         
/*  774 */         for (int j1 = 4; j1 <= 8; j1++) {
/*      */           
/*  776 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i1, -1, j1, structureBoundingBoxIn);
/*  777 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 12 - i1, -1, j1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  781 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 5, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  782 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 6, 6, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  783 */       setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
/*  784 */       setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 6, 5, 6, structureBoundingBoxIn);
/*  785 */       BlockPos blockpos = new BlockPos(getXWithOffset(6, 6), getYWithOffset(5), getZWithOffset(6, 6));
/*      */       
/*  787 */       if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos))
/*      */       {
/*  789 */         worldIn.forceBlockUpdateTick((Block)Blocks.flowing_lava, blockpos, randomIn);
/*      */       }
/*      */       
/*  792 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class NetherStalkRoom
/*      */     extends Piece
/*      */   {
/*      */     public NetherStalkRoom() {}
/*      */ 
/*      */     
/*      */     public NetherStalkRoom(int p_i45612_1_, Random p_i45612_2_, StructureBoundingBox p_i45612_3_, EnumFacing p_i45612_4_) {
/*  804 */       super(p_i45612_1_);
/*  805 */       this.coordBaseMode = p_i45612_4_;
/*  806 */       this.boundingBox = p_i45612_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  811 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 3, true);
/*  812 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 11, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static NetherStalkRoom func_175875_a(List<StructureComponent> p_175875_0_, Random p_175875_1_, int p_175875_2_, int p_175875_3_, int p_175875_4_, EnumFacing p_175875_5_, int p_175875_6_) {
/*  817 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175875_2_, p_175875_3_, p_175875_4_, -5, -3, 0, 13, 14, 13, p_175875_5_);
/*  818 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175875_0_, structureboundingbox) == null) ? new NetherStalkRoom(p_175875_6_, p_175875_1_, structureboundingbox, p_175875_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  823 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  824 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  825 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  826 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  827 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  828 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  829 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  830 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  831 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  832 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  833 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  835 */       for (int i = 1; i <= 11; i += 2) {
/*      */         
/*  837 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 0, i, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  838 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 12, i, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  839 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 10, i, 0, 11, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  840 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 10, i, 12, 11, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  841 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), i, 13, 0, structureBoundingBoxIn);
/*  842 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), i, 13, 12, structureBoundingBoxIn);
/*  843 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, i, structureBoundingBoxIn);
/*  844 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, i, structureBoundingBoxIn);
/*  845 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), i + 1, 13, 0, structureBoundingBoxIn);
/*  846 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), i + 1, 13, 12, structureBoundingBoxIn);
/*  847 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, i + 1, structureBoundingBoxIn);
/*  848 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, i + 1, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  851 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  852 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, structureBoundingBoxIn);
/*  853 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  854 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, structureBoundingBoxIn);
/*      */       
/*  856 */       for (int j1 = 3; j1 <= 9; j1 += 2) {
/*      */         
/*  858 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, j1, 1, 8, j1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  859 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 7, j1, 11, 8, j1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       } 
/*      */       
/*  862 */       int k1 = getMetadataWithOffset(Blocks.nether_brick_stairs, 3);
/*      */       
/*  864 */       for (int j = 0; j <= 6; j++) {
/*      */         
/*  866 */         int k = j + 4;
/*      */         
/*  868 */         for (int l = 5; l <= 7; l++)
/*      */         {
/*  870 */           setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(k1), l, 5 + j, k, structureBoundingBoxIn);
/*      */         }
/*      */         
/*  873 */         if (k >= 5 && k <= 8) {
/*      */           
/*  875 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, k, 7, j + 4, k, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         }
/*  877 */         else if (k >= 9 && k <= 10) {
/*      */           
/*  879 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 8, k, 7, j + 4, k, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         } 
/*      */         
/*  882 */         if (j >= 1)
/*      */         {
/*  884 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6 + j, k, 7, 9 + j, k, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */         }
/*      */       } 
/*      */       
/*  888 */       for (int l1 = 5; l1 <= 7; l1++)
/*      */       {
/*  890 */         setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(k1), l1, 12, 11, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  893 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  894 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  895 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 13, 12, 7, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  896 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 2, 3, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  897 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 9, 3, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  898 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 4, 2, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  899 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 2, 10, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  900 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 9, 10, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  901 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 4, 10, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  902 */       int i2 = getMetadataWithOffset(Blocks.nether_brick_stairs, 0);
/*  903 */       int j2 = getMetadataWithOffset(Blocks.nether_brick_stairs, 1);
/*  904 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(j2), 4, 5, 2, structureBoundingBoxIn);
/*  905 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(j2), 4, 5, 3, structureBoundingBoxIn);
/*  906 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(j2), 4, 5, 9, structureBoundingBoxIn);
/*  907 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(j2), 4, 5, 10, structureBoundingBoxIn);
/*  908 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i2), 8, 5, 2, structureBoundingBoxIn);
/*  909 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i2), 8, 5, 3, structureBoundingBoxIn);
/*  910 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i2), 8, 5, 9, structureBoundingBoxIn);
/*  911 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i2), 8, 5, 10, structureBoundingBoxIn);
/*  912 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 4, 4, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
/*  913 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 4, 4, 9, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
/*  914 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 5, 4, 4, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
/*  915 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 4, 9, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
/*  916 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  917 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  918 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  919 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  920 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  921 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  923 */       for (int k2 = 4; k2 <= 8; k2++) {
/*      */         
/*  925 */         for (int i1 = 0; i1 <= 2; i1++) {
/*      */           
/*  927 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), k2, -1, i1, structureBoundingBoxIn);
/*  928 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), k2, -1, 12 - i1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  932 */       for (int l2 = 0; l2 <= 2; l2++) {
/*      */         
/*  934 */         for (int i3 = 4; i3 <= 8; i3++) {
/*      */           
/*  936 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), l2, -1, i3, structureBoundingBoxIn);
/*  937 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 12 - l2, -1, i3, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  941 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Piece
/*      */     extends StructureComponent {
/*  947 */     protected static final List<WeightedRandomChestContent> field_111019_a = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 2, 4, 2) });
/*      */ 
/*      */ 
/*      */     
/*      */     public Piece() {}
/*      */ 
/*      */     
/*      */     protected Piece(int p_i2054_1_) {
/*  955 */       super(p_i2054_1_);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {}
/*      */ 
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {}
/*      */ 
/*      */     
/*      */     private int getTotalWeight(List<StructureNetherBridgePieces.PieceWeight> p_74960_1_) {
/*  968 */       boolean flag = false;
/*  969 */       int i = 0;
/*      */       
/*  971 */       for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : p_74960_1_) {
/*      */         
/*  973 */         if (structurenetherbridgepieces$pieceweight.field_78824_d > 0 && structurenetherbridgepieces$pieceweight.field_78827_c < structurenetherbridgepieces$pieceweight.field_78824_d)
/*      */         {
/*  975 */           flag = true;
/*      */         }
/*      */         
/*  978 */         i += structurenetherbridgepieces$pieceweight.field_78826_b;
/*      */       } 
/*      */       
/*  981 */       return flag ? i : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     private Piece func_175871_a(StructureNetherBridgePieces.Start p_175871_1_, List<StructureNetherBridgePieces.PieceWeight> p_175871_2_, List<StructureComponent> p_175871_3_, Random p_175871_4_, int p_175871_5_, int p_175871_6_, int p_175871_7_, EnumFacing p_175871_8_, int p_175871_9_) {
/*  986 */       int i = getTotalWeight(p_175871_2_);
/*  987 */       boolean flag = (i > 0 && p_175871_9_ <= 30);
/*  988 */       int j = 0;
/*      */       
/*  990 */       while (j < 5 && flag) {
/*      */         
/*  992 */         j++;
/*  993 */         int k = p_175871_4_.nextInt(i);
/*      */         
/*  995 */         for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : p_175871_2_) {
/*      */           
/*  997 */           k -= structurenetherbridgepieces$pieceweight.field_78826_b;
/*      */           
/*  999 */           if (k < 0) {
/*      */             
/* 1001 */             if (!structurenetherbridgepieces$pieceweight.func_78822_a(p_175871_9_) || (structurenetherbridgepieces$pieceweight == p_175871_1_.theNetherBridgePieceWeight && !structurenetherbridgepieces$pieceweight.field_78825_e)) {
/*      */               break;
/*      */             }
/*      */ 
/*      */             
/* 1006 */             Piece structurenetherbridgepieces$piece = StructureNetherBridgePieces.func_175887_b(structurenetherbridgepieces$pieceweight, p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
/*      */             
/* 1008 */             if (structurenetherbridgepieces$piece != null) {
/*      */               
/* 1010 */               structurenetherbridgepieces$pieceweight.field_78827_c++;
/* 1011 */               p_175871_1_.theNetherBridgePieceWeight = structurenetherbridgepieces$pieceweight;
/*      */               
/* 1013 */               if (!structurenetherbridgepieces$pieceweight.func_78823_a())
/*      */               {
/* 1015 */                 p_175871_2_.remove(structurenetherbridgepieces$pieceweight);
/*      */               }
/*      */               
/* 1018 */               return structurenetherbridgepieces$piece;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1024 */       return StructureNetherBridgePieces.End.func_175884_a(p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
/*      */     }
/*      */ 
/*      */     
/*      */     private StructureComponent func_175870_a(StructureNetherBridgePieces.Start p_175870_1_, List<StructureComponent> p_175870_2_, Random p_175870_3_, int p_175870_4_, int p_175870_5_, int p_175870_6_, EnumFacing p_175870_7_, int p_175870_8_, boolean p_175870_9_) {
/* 1029 */       if (Math.abs(p_175870_4_ - (p_175870_1_.getBoundingBox()).minX) <= 112 && Math.abs(p_175870_6_ - (p_175870_1_.getBoundingBox()).minZ) <= 112) {
/*      */         
/* 1031 */         List<StructureNetherBridgePieces.PieceWeight> list = p_175870_1_.primaryWeights;
/*      */         
/* 1033 */         if (p_175870_9_)
/*      */         {
/* 1035 */           list = p_175870_1_.secondaryWeights;
/*      */         }
/*      */         
/* 1038 */         StructureComponent structurecomponent = func_175871_a(p_175870_1_, list, p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_ + 1);
/*      */         
/* 1040 */         if (structurecomponent != null) {
/*      */           
/* 1042 */           p_175870_2_.add(structurecomponent);
/* 1043 */           p_175870_1_.field_74967_d.add(structurecomponent);
/*      */         } 
/*      */         
/* 1046 */         return structurecomponent;
/*      */       } 
/*      */ 
/*      */       
/* 1050 */       return StructureNetherBridgePieces.End.func_175884_a(p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentNormal(StructureNetherBridgePieces.Start p_74963_1_, List<StructureComponent> p_74963_2_, Random p_74963_3_, int p_74963_4_, int p_74963_5_, boolean p_74963_6_) {
/* 1056 */       if (this.coordBaseMode != null)
/*      */       {
/* 1058 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case NORTH:
/* 1061 */             return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ - 1, this.coordBaseMode, getComponentType(), p_74963_6_);
/*      */           
/*      */           case SOUTH:
/* 1064 */             return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.maxZ + 1, this.coordBaseMode, getComponentType(), p_74963_6_);
/*      */           
/*      */           case WEST:
/* 1067 */             return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, this.coordBaseMode, getComponentType(), p_74963_6_);
/*      */           
/*      */           case EAST:
/* 1070 */             return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, this.coordBaseMode, getComponentType(), p_74963_6_);
/*      */         } 
/*      */       
/*      */       }
/* 1074 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentX(StructureNetherBridgePieces.Start p_74961_1_, List<StructureComponent> p_74961_2_, Random p_74961_3_, int p_74961_4_, int p_74961_5_, boolean p_74961_6_) {
/* 1079 */       if (this.coordBaseMode != null)
/*      */       {
/* 1081 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case NORTH:
/* 1084 */             return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, getComponentType(), p_74961_6_);
/*      */           
/*      */           case SOUTH:
/* 1087 */             return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, getComponentType(), p_74961_6_);
/*      */           
/*      */           case WEST:
/* 1090 */             return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType(), p_74961_6_);
/*      */           
/*      */           case EAST:
/* 1093 */             return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType(), p_74961_6_);
/*      */         } 
/*      */       
/*      */       }
/* 1097 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentZ(StructureNetherBridgePieces.Start p_74965_1_, List<StructureComponent> p_74965_2_, Random p_74965_3_, int p_74965_4_, int p_74965_5_, boolean p_74965_6_) {
/* 1102 */       if (this.coordBaseMode != null)
/*      */       {
/* 1104 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case NORTH:
/* 1107 */             return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, getComponentType(), p_74965_6_);
/*      */           
/*      */           case SOUTH:
/* 1110 */             return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, getComponentType(), p_74965_6_);
/*      */           
/*      */           case WEST:
/* 1113 */             return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType(), p_74965_6_);
/*      */           
/*      */           case EAST:
/* 1116 */             return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType(), p_74965_6_);
/*      */         } 
/*      */       
/*      */       }
/* 1120 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected static boolean isAboveGround(StructureBoundingBox p_74964_0_) {
/* 1125 */       return (p_74964_0_ != null && p_74964_0_.minY > 10);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PieceWeight
/*      */   {
/*      */     public Class<? extends StructureNetherBridgePieces.Piece> weightClass;
/*      */     public final int field_78826_b;
/*      */     public int field_78827_c;
/*      */     public int field_78824_d;
/*      */     public boolean field_78825_e;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> p_i2055_1_, int p_i2055_2_, int p_i2055_3_, boolean p_i2055_4_) {
/* 1139 */       this.weightClass = p_i2055_1_;
/* 1140 */       this.field_78826_b = p_i2055_2_;
/* 1141 */       this.field_78824_d = p_i2055_3_;
/* 1142 */       this.field_78825_e = p_i2055_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> p_i2056_1_, int p_i2056_2_, int p_i2056_3_) {
/* 1147 */       this(p_i2056_1_, p_i2056_2_, p_i2056_3_, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean func_78822_a(int p_78822_1_) {
/* 1152 */       return (this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean func_78823_a() {
/* 1157 */       return (this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Stairs
/*      */     extends Piece
/*      */   {
/*      */     public Stairs() {}
/*      */ 
/*      */     
/*      */     public Stairs(int p_i45609_1_, Random p_i45609_2_, StructureBoundingBox p_i45609_3_, EnumFacing p_i45609_4_) {
/* 1169 */       super(p_i45609_1_);
/* 1170 */       this.coordBaseMode = p_i45609_4_;
/* 1171 */       this.boundingBox = p_i45609_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1176 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 6, 2, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Stairs func_175872_a(List<StructureComponent> p_175872_0_, Random p_175872_1_, int p_175872_2_, int p_175872_3_, int p_175872_4_, int p_175872_5_, EnumFacing p_175872_6_) {
/* 1181 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175872_2_, p_175872_3_, p_175872_4_, -2, 0, 0, 7, 11, 7, p_175872_6_);
/* 1182 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175872_0_, structureboundingbox) == null) ? new Stairs(p_175872_5_, p_175872_1_, structureboundingbox, p_175872_6_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1187 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1188 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 10, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1189 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1190 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1191 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1192 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 1, 6, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1193 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 6, 5, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1194 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1195 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1196 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1197 */       setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/* 1198 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 5, 4, 3, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1199 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 5, 3, 4, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1200 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1201 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 1, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1202 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 1, 5, 7, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1203 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 8, 2, 6, 8, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1204 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 0, 4, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1205 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/* 1207 */       for (int i = 0; i <= 6; i++) {
/*      */         
/* 1209 */         for (int j = 0; j <= 6; j++)
/*      */         {
/* 1211 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1215 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Start
/*      */     extends Crossing3 {
/*      */     public StructureNetherBridgePieces.PieceWeight theNetherBridgePieceWeight;
/*      */     public List<StructureNetherBridgePieces.PieceWeight> primaryWeights;
/*      */     public List<StructureNetherBridgePieces.PieceWeight> secondaryWeights;
/* 1224 */     public List<StructureComponent> field_74967_d = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */     
/*      */     public Start() {}
/*      */ 
/*      */     
/*      */     public Start(Random p_i2059_1_, int p_i2059_2_, int p_i2059_3_) {
/* 1232 */       super(p_i2059_1_, p_i2059_2_, p_i2059_3_);
/* 1233 */       this.primaryWeights = Lists.newArrayList();
/*      */       
/* 1235 */       for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : StructureNetherBridgePieces.primaryComponents) {
/*      */         
/* 1237 */         structurenetherbridgepieces$pieceweight.field_78827_c = 0;
/* 1238 */         this.primaryWeights.add(structurenetherbridgepieces$pieceweight);
/*      */       } 
/*      */       
/* 1241 */       this.secondaryWeights = Lists.newArrayList();
/*      */       
/* 1243 */       for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight1 : StructureNetherBridgePieces.secondaryComponents) {
/*      */         
/* 1245 */         structurenetherbridgepieces$pieceweight1.field_78827_c = 0;
/* 1246 */         this.secondaryWeights.add(structurenetherbridgepieces$pieceweight1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1252 */       super.readStructureFromNBT(tagCompound);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1257 */       super.writeStructureToNBT(tagCompound);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Straight
/*      */     extends Piece
/*      */   {
/*      */     public Straight() {}
/*      */ 
/*      */     
/*      */     public Straight(int p_i45620_1_, Random p_i45620_2_, StructureBoundingBox p_i45620_3_, EnumFacing p_i45620_4_) {
/* 1269 */       super(p_i45620_1_);
/* 1270 */       this.coordBaseMode = p_i45620_4_;
/* 1271 */       this.boundingBox = p_i45620_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1276 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 3, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Straight func_175882_a(List<StructureComponent> p_175882_0_, Random p_175882_1_, int p_175882_2_, int p_175882_3_, int p_175882_4_, EnumFacing p_175882_5_, int p_175882_6_) {
/* 1281 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175882_2_, p_175882_3_, p_175882_4_, -1, -3, 0, 5, 10, 19, p_175882_5_);
/* 1282 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175882_0_, structureboundingbox) == null) ? new Straight(p_175882_6_, p_175882_1_, structureboundingbox, p_175882_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1287 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 4, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1288 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 0, 3, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1289 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 0, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1290 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 0, 4, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1291 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1292 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 13, 4, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1293 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1294 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 15, 4, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/* 1296 */       for (int i = 0; i <= 4; i++) {
/*      */         
/* 1298 */         for (int j = 0; j <= 2; j++) {
/*      */           
/* 1300 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/* 1301 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, 18 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1305 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1306 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 4, 0, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1307 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 14, 0, 4, 14, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1308 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 17, 0, 4, 17, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1309 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1310 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 4, 4, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1311 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 14, 4, 4, 14, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1312 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 17, 4, 4, 17, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1313 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Throne
/*      */     extends Piece
/*      */   {
/*      */     private boolean hasSpawner;
/*      */ 
/*      */     
/*      */     public Throne() {}
/*      */     
/*      */     public Throne(int p_i45611_1_, Random p_i45611_2_, StructureBoundingBox p_i45611_3_, EnumFacing p_i45611_4_) {
/* 1327 */       super(p_i45611_1_);
/* 1328 */       this.coordBaseMode = p_i45611_4_;
/* 1329 */       this.boundingBox = p_i45611_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1334 */       super.readStructureFromNBT(tagCompound);
/* 1335 */       this.hasSpawner = tagCompound.getBoolean("Mob");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1340 */       super.writeStructureToNBT(tagCompound);
/* 1341 */       tagCompound.setBoolean("Mob", this.hasSpawner);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Throne func_175874_a(List<StructureComponent> p_175874_0_, Random p_175874_1_, int p_175874_2_, int p_175874_3_, int p_175874_4_, int p_175874_5_, EnumFacing p_175874_6_) {
/* 1346 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175874_2_, p_175874_3_, p_175874_4_, -2, 0, 0, 7, 8, 9, p_175874_6_);
/* 1347 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175874_0_, structureboundingbox) == null) ? new Throne(p_175874_5_, p_175874_1_, structureboundingbox, p_175874_6_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1352 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 7, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1353 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 1, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1354 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 5, 2, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1355 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 2, 5, 3, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1356 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 3, 5, 4, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1357 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 1, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1358 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 5, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1359 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 2, 1, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1360 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 2, 5, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1361 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 3, 0, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1362 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 3, 6, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1363 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 8, 5, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1364 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 1, 6, 3, structureBoundingBoxIn);
/* 1365 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 5, 6, 3, structureBoundingBoxIn);
/* 1366 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1367 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1368 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1369 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/* 1371 */       if (!this.hasSpawner) {
/*      */         
/* 1373 */         BlockPos blockpos = new BlockPos(getXWithOffset(3, 5), getYWithOffset(5), getZWithOffset(3, 5));
/*      */         
/* 1375 */         if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*      */           
/* 1377 */           this.hasSpawner = true;
/* 1378 */           worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
/* 1379 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*      */           
/* 1381 */           if (tileentity instanceof TileEntityMobSpawner)
/*      */           {
/* 1383 */             ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Blaze");
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1388 */       for (int i = 0; i <= 6; i++) {
/*      */         
/* 1390 */         for (int j = 0; j <= 6; j++)
/*      */         {
/* 1392 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1396 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\StructureNetherBridgePieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */