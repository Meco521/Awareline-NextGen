/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.BlockEndPortalFrame;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockStoneSlab;
/*      */ import net.minecraft.block.properties.IProperty;
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
/*      */ public class StructureStrongholdPieces {
/*   24 */   private static final PieceWeight[] pieceWeightArray = new PieceWeight[] { new PieceWeight((Class)Straight.class, 40, 0), new PieceWeight((Class)Prison.class, 5, 5), new PieceWeight((Class)LeftTurn.class, 20, 0), new PieceWeight((Class)RightTurn.class, 20, 0), new PieceWeight((Class)RoomCrossing.class, 10, 6), new PieceWeight((Class)StairsStraight.class, 5, 5), new PieceWeight((Class)Stairs.class, 5, 5), new PieceWeight((Class)Crossing.class, 5, 4), new PieceWeight((Class)ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2)
/*      */       {
/*      */         public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
/*      */         {
/*   28 */           return (super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4);
/*      */         }
/*      */       }, 
/*      */       new PieceWeight(PortalRoom.class, 20, 1)
/*      */       {
/*      */         public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
/*   34 */           return (super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5);
/*      */         }
/*      */       } };
/*      */   
/*      */   private static List<PieceWeight> structurePieceList;
/*      */   static Class<? extends Stronghold> strongComponentType;
/*      */   static int totalWeight;
/*   41 */   static final Stones strongholdStones = new Stones();
/*      */ 
/*      */   
/*      */   public static void registerStrongholdPieces() {
/*   45 */     MapGenStructureIO.registerStructureComponent((Class)ChestCorridor.class, "SHCC");
/*   46 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "SHFC");
/*   47 */     MapGenStructureIO.registerStructureComponent((Class)Crossing.class, "SH5C");
/*   48 */     MapGenStructureIO.registerStructureComponent((Class)LeftTurn.class, "SHLT");
/*   49 */     MapGenStructureIO.registerStructureComponent((Class)Library.class, "SHLi");
/*   50 */     MapGenStructureIO.registerStructureComponent((Class)PortalRoom.class, "SHPR");
/*   51 */     MapGenStructureIO.registerStructureComponent((Class)Prison.class, "SHPH");
/*   52 */     MapGenStructureIO.registerStructureComponent((Class)RightTurn.class, "SHRT");
/*   53 */     MapGenStructureIO.registerStructureComponent((Class)RoomCrossing.class, "SHRC");
/*   54 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "SHSD");
/*   55 */     MapGenStructureIO.registerStructureComponent((Class)Stairs2.class, "SHStart");
/*   56 */     MapGenStructureIO.registerStructureComponent((Class)Straight.class, "SHS");
/*   57 */     MapGenStructureIO.registerStructureComponent((Class)StairsStraight.class, "SHSSD");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void prepareStructurePieces() {
/*   65 */     structurePieceList = Lists.newArrayList();
/*      */     
/*   67 */     for (PieceWeight structurestrongholdpieces$pieceweight : pieceWeightArray) {
/*      */       
/*   69 */       structurestrongholdpieces$pieceweight.instancesSpawned = 0;
/*   70 */       structurePieceList.add(structurestrongholdpieces$pieceweight);
/*      */     } 
/*      */     
/*   73 */     strongComponentType = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean canAddStructurePieces() {
/*   78 */     boolean flag = false;
/*   79 */     totalWeight = 0;
/*      */     
/*   81 */     for (PieceWeight structurestrongholdpieces$pieceweight : structurePieceList) {
/*      */       
/*   83 */       if (structurestrongholdpieces$pieceweight.instancesLimit > 0 && structurestrongholdpieces$pieceweight.instancesSpawned < structurestrongholdpieces$pieceweight.instancesLimit)
/*      */       {
/*   85 */         flag = true;
/*      */       }
/*      */       
/*   88 */       totalWeight += structurestrongholdpieces$pieceweight.pieceWeight;
/*      */     } 
/*      */     
/*   91 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Stronghold func_175954_a(Class<? extends Stronghold> p_175954_0_, List<StructureComponent> p_175954_1_, Random p_175954_2_, int p_175954_3_, int p_175954_4_, int p_175954_5_, EnumFacing p_175954_6_, int p_175954_7_) {
/*   96 */     Stronghold structurestrongholdpieces$stronghold = null;
/*      */     
/*   98 */     if (p_175954_0_ == Straight.class) {
/*      */       
/*  100 */       structurestrongholdpieces$stronghold = Straight.func_175862_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  102 */     else if (p_175954_0_ == Prison.class) {
/*      */       
/*  104 */       structurestrongholdpieces$stronghold = Prison.func_175860_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  106 */     else if (p_175954_0_ == LeftTurn.class) {
/*      */       
/*  108 */       structurestrongholdpieces$stronghold = LeftTurn.func_175867_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  110 */     else if (p_175954_0_ == RightTurn.class) {
/*      */       
/*  112 */       structurestrongholdpieces$stronghold = RightTurn.func_175867_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  114 */     else if (p_175954_0_ == RoomCrossing.class) {
/*      */       
/*  116 */       structurestrongholdpieces$stronghold = RoomCrossing.func_175859_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  118 */     else if (p_175954_0_ == StairsStraight.class) {
/*      */       
/*  120 */       structurestrongholdpieces$stronghold = StairsStraight.func_175861_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  122 */     else if (p_175954_0_ == Stairs.class) {
/*      */       
/*  124 */       structurestrongholdpieces$stronghold = Stairs.func_175863_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  126 */     else if (p_175954_0_ == Crossing.class) {
/*      */       
/*  128 */       structurestrongholdpieces$stronghold = Crossing.func_175866_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  130 */     else if (p_175954_0_ == ChestCorridor.class) {
/*      */       
/*  132 */       structurestrongholdpieces$stronghold = ChestCorridor.func_175868_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  134 */     else if (p_175954_0_ == Library.class) {
/*      */       
/*  136 */       structurestrongholdpieces$stronghold = Library.func_175864_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  138 */     else if (p_175954_0_ == PortalRoom.class) {
/*      */       
/*  140 */       structurestrongholdpieces$stronghold = PortalRoom.func_175865_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     } 
/*      */     
/*  143 */     return structurestrongholdpieces$stronghold;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Stronghold func_175955_b(Stairs2 p_175955_0_, List<StructureComponent> p_175955_1_, Random p_175955_2_, int p_175955_3_, int p_175955_4_, int p_175955_5_, EnumFacing p_175955_6_, int p_175955_7_) {
/*  148 */     if (!canAddStructurePieces())
/*      */     {
/*  150 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  154 */     if (strongComponentType != null) {
/*      */       
/*  156 */       Stronghold structurestrongholdpieces$stronghold = func_175954_a(strongComponentType, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
/*  157 */       strongComponentType = null;
/*      */       
/*  159 */       if (structurestrongholdpieces$stronghold != null)
/*      */       {
/*  161 */         return structurestrongholdpieces$stronghold;
/*      */       }
/*      */     } 
/*      */     
/*  165 */     int j = 0;
/*      */     
/*  167 */     while (j < 5) {
/*      */       
/*  169 */       j++;
/*  170 */       int i = p_175955_2_.nextInt(totalWeight);
/*      */       
/*  172 */       for (PieceWeight structurestrongholdpieces$pieceweight : structurePieceList) {
/*      */         
/*  174 */         i -= structurestrongholdpieces$pieceweight.pieceWeight;
/*      */         
/*  176 */         if (i < 0) {
/*      */           
/*  178 */           if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructuresOfType(p_175955_7_) || structurestrongholdpieces$pieceweight == p_175955_0_.strongholdPieceWeight) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  183 */           Stronghold structurestrongholdpieces$stronghold1 = func_175954_a(structurestrongholdpieces$pieceweight.pieceClass, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
/*      */           
/*  185 */           if (structurestrongholdpieces$stronghold1 != null) {
/*      */             
/*  187 */             structurestrongholdpieces$pieceweight.instancesSpawned++;
/*  188 */             p_175955_0_.strongholdPieceWeight = structurestrongholdpieces$pieceweight;
/*      */             
/*  190 */             if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructures())
/*      */             {
/*  192 */               structurePieceList.remove(structurestrongholdpieces$pieceweight);
/*      */             }
/*      */             
/*  195 */             return structurestrongholdpieces$stronghold1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  201 */     StructureBoundingBox structureboundingbox = Corridor.func_175869_a(p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);
/*      */     
/*  203 */     if (structureboundingbox != null && structureboundingbox.minY > 1)
/*      */     {
/*  205 */       return new Corridor(p_175955_7_, p_175955_2_, structureboundingbox, p_175955_6_);
/*      */     }
/*      */ 
/*      */     
/*  209 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static StructureComponent func_175953_c(Stairs2 p_175953_0_, List<StructureComponent> p_175953_1_, Random p_175953_2_, int p_175953_3_, int p_175953_4_, int p_175953_5_, EnumFacing p_175953_6_, int p_175953_7_) {
/*  216 */     if (p_175953_7_ > 50)
/*      */     {
/*  218 */       return null;
/*      */     }
/*  220 */     if (Math.abs(p_175953_3_ - (p_175953_0_.getBoundingBox()).minX) <= 112 && Math.abs(p_175953_5_ - (p_175953_0_.getBoundingBox()).minZ) <= 112) {
/*      */       
/*  222 */       StructureComponent structurecomponent = func_175955_b(p_175953_0_, p_175953_1_, p_175953_2_, p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);
/*      */       
/*  224 */       if (structurecomponent != null) {
/*      */         
/*  226 */         p_175953_1_.add(structurecomponent);
/*  227 */         p_175953_0_.field_75026_c.add(structurecomponent);
/*      */       } 
/*      */       
/*  230 */       return structurecomponent;
/*      */     } 
/*      */ 
/*      */     
/*  234 */     return null;
/*      */   }
/*      */   
/*      */   public static class ChestCorridor
/*      */     extends Stronghold
/*      */   {
/*  240 */     private static final List<WeightedRandomChestContent> strongholdChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
/*      */     
/*      */     private boolean hasMadeChest;
/*      */ 
/*      */     
/*      */     public ChestCorridor() {}
/*      */ 
/*      */     
/*      */     public ChestCorridor(int p_i45582_1_, Random p_i45582_2_, StructureBoundingBox p_i45582_3_, EnumFacing p_i45582_4_) {
/*  249 */       super(p_i45582_1_);
/*  250 */       this.coordBaseMode = p_i45582_4_;
/*  251 */       this.field_143013_d = getRandomDoor(p_i45582_2_);
/*  252 */       this.boundingBox = p_i45582_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  257 */       super.writeStructureToNBT(tagCompound);
/*  258 */       tagCompound.setBoolean("Chest", this.hasMadeChest);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  263 */       super.readStructureFromNBT(tagCompound);
/*  264 */       this.hasMadeChest = tagCompound.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  269 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public static ChestCorridor func_175868_a(List<StructureComponent> p_175868_0_, Random p_175868_1_, int p_175868_2_, int p_175868_3_, int p_175868_4_, EnumFacing p_175868_5_, int p_175868_6_) {
/*  274 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
/*  275 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175868_0_, structureboundingbox) == null) ? new ChestCorridor(p_175868_6_, p_175868_1_, structureboundingbox, p_175868_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  280 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  282 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  286 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  287 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/*  288 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
/*  289 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 1, 4, Blocks.stonebrick.getDefaultState(), Blocks.stonebrick.getDefaultState(), false);
/*  290 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 1, structureBoundingBoxIn);
/*  291 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 5, structureBoundingBoxIn);
/*  292 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 2, structureBoundingBoxIn);
/*  293 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 4, structureBoundingBoxIn);
/*      */       
/*  295 */       for (int i = 2; i <= 4; i++)
/*      */       {
/*  297 */         setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 2, 1, i, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  300 */       if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(3, 3), getYWithOffset(2), getZWithOffset(3, 3)))) {
/*      */         
/*  302 */         this.hasMadeChest = true;
/*  303 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, WeightedRandomChestContent.func_177629_a(strongholdChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn) }), 2 + randomIn.nextInt(2));
/*      */       } 
/*      */       
/*  306 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor
/*      */     extends Stronghold
/*      */   {
/*      */     private int field_74993_a;
/*      */ 
/*      */     
/*      */     public Corridor() {}
/*      */ 
/*      */     
/*      */     public Corridor(int p_i45581_1_, Random p_i45581_2_, StructureBoundingBox p_i45581_3_, EnumFacing p_i45581_4_) {
/*  321 */       super(p_i45581_1_);
/*  322 */       this.coordBaseMode = p_i45581_4_;
/*  323 */       this.boundingBox = p_i45581_3_;
/*  324 */       this.field_74993_a = (p_i45581_4_ != EnumFacing.NORTH && p_i45581_4_ != EnumFacing.SOUTH) ? p_i45581_3_.getXSize() : p_i45581_3_.getZSize();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  329 */       super.writeStructureToNBT(tagCompound);
/*  330 */       tagCompound.setInteger("Steps", this.field_74993_a);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  335 */       super.readStructureFromNBT(tagCompound);
/*  336 */       this.field_74993_a = tagCompound.getInteger("Steps");
/*      */     }
/*      */ 
/*      */     
/*      */     public static StructureBoundingBox func_175869_a(List<StructureComponent> p_175869_0_, Random p_175869_1_, int p_175869_2_, int p_175869_3_, int p_175869_4_, EnumFacing p_175869_5_) {
/*  341 */       int i = 3;
/*  342 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
/*  343 */       StructureComponent structurecomponent = StructureComponent.findIntersecting(p_175869_0_, structureboundingbox);
/*      */       
/*  345 */       if (structurecomponent == null)
/*      */       {
/*  347 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  351 */       if ((structurecomponent.getBoundingBox()).minY == structureboundingbox.minY)
/*      */       {
/*  353 */         for (int j = 3; j >= 1; j--) {
/*      */           
/*  355 */           structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j - 1, p_175869_5_);
/*      */           
/*  357 */           if (!structurecomponent.getBoundingBox().intersectsWith(structureboundingbox))
/*      */           {
/*  359 */             return StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j, p_175869_5_);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  364 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  370 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  372 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  376 */       for (int i = 0; i < this.field_74993_a; i++) {
/*      */         
/*  378 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, 0, i, structureBoundingBoxIn);
/*  379 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 0, i, structureBoundingBoxIn);
/*  380 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 0, i, structureBoundingBoxIn);
/*  381 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 0, i, structureBoundingBoxIn);
/*  382 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, 0, i, structureBoundingBoxIn);
/*      */         
/*  384 */         for (int j = 1; j <= 3; j++) {
/*      */           
/*  386 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, j, i, structureBoundingBoxIn);
/*  387 */           setBlockState(worldIn, Blocks.air.getDefaultState(), 1, j, i, structureBoundingBoxIn);
/*  388 */           setBlockState(worldIn, Blocks.air.getDefaultState(), 2, j, i, structureBoundingBoxIn);
/*  389 */           setBlockState(worldIn, Blocks.air.getDefaultState(), 3, j, i, structureBoundingBoxIn);
/*  390 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, j, i, structureBoundingBoxIn);
/*      */         } 
/*      */         
/*  393 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, 4, i, structureBoundingBoxIn);
/*  394 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, i, structureBoundingBoxIn);
/*  395 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, i, structureBoundingBoxIn);
/*  396 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 4, i, structureBoundingBoxIn);
/*  397 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, 4, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  400 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Crossing
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean field_74996_b;
/*      */     
/*      */     private boolean field_74997_c;
/*      */     
/*      */     private boolean field_74995_d;
/*      */     private boolean field_74999_h;
/*      */     
/*      */     public Crossing() {}
/*      */     
/*      */     public Crossing(int p_i45580_1_, Random p_i45580_2_, StructureBoundingBox p_i45580_3_, EnumFacing p_i45580_4_) {
/*  418 */       super(p_i45580_1_);
/*  419 */       this.coordBaseMode = p_i45580_4_;
/*  420 */       this.field_143013_d = getRandomDoor(p_i45580_2_);
/*  421 */       this.boundingBox = p_i45580_3_;
/*  422 */       this.field_74996_b = p_i45580_2_.nextBoolean();
/*  423 */       this.field_74997_c = p_i45580_2_.nextBoolean();
/*  424 */       this.field_74995_d = p_i45580_2_.nextBoolean();
/*  425 */       this.field_74999_h = (p_i45580_2_.nextInt(3) > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  430 */       super.writeStructureToNBT(tagCompound);
/*  431 */       tagCompound.setBoolean("leftLow", this.field_74996_b);
/*  432 */       tagCompound.setBoolean("leftHigh", this.field_74997_c);
/*  433 */       tagCompound.setBoolean("rightLow", this.field_74995_d);
/*  434 */       tagCompound.setBoolean("rightHigh", this.field_74999_h);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  439 */       super.readStructureFromNBT(tagCompound);
/*  440 */       this.field_74996_b = tagCompound.getBoolean("leftLow");
/*  441 */       this.field_74997_c = tagCompound.getBoolean("leftHigh");
/*  442 */       this.field_74995_d = tagCompound.getBoolean("rightLow");
/*  443 */       this.field_74999_h = tagCompound.getBoolean("rightHigh");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  448 */       int i = 3;
/*  449 */       int j = 5;
/*      */       
/*  451 */       if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
/*      */         
/*  453 */         i = 8 - i;
/*  454 */         j = 8 - j;
/*      */       } 
/*      */       
/*  457 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 5, 1);
/*      */       
/*  459 */       if (this.field_74996_b)
/*      */       {
/*  461 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, i, 1);
/*      */       }
/*      */       
/*  464 */       if (this.field_74997_c)
/*      */       {
/*  466 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, j, 7);
/*      */       }
/*      */       
/*  469 */       if (this.field_74995_d)
/*      */       {
/*  471 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, i, 1);
/*      */       }
/*      */       
/*  474 */       if (this.field_74999_h)
/*      */       {
/*  476 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, j, 7);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public static Crossing func_175866_a(List<StructureComponent> p_175866_0_, Random p_175866_1_, int p_175866_2_, int p_175866_3_, int p_175866_4_, EnumFacing p_175866_5_, int p_175866_6_) {
/*  482 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
/*  483 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175866_0_, structureboundingbox) == null) ? new Crossing(p_175866_6_, p_175866_1_, structureboundingbox, p_175866_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  488 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  490 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  494 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 8, 10, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  495 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 4, 3, 0);
/*      */       
/*  497 */       if (this.field_74996_b)
/*      */       {
/*  499 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/*  502 */       if (this.field_74995_d)
/*      */       {
/*  504 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 3, 1, 9, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/*  507 */       if (this.field_74997_c)
/*      */       {
/*  509 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 0, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/*  512 */       if (this.field_74999_h)
/*      */       {
/*  514 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 7, 9, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/*  517 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 10, 7, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  518 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 8, 2, 6, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  519 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 4, 4, 9, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  520 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 5, 8, 4, 9, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  521 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 4, 7, 3, 4, 9, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  522 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 3, 3, 6, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  523 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 3, 3, 4, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  524 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 6, 3, 4, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  525 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 1, 7, 7, 1, 8, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  526 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 7, 1, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  527 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 7, 2, 7, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  528 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 7, 4, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  529 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 7, 8, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  530 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
/*  531 */       setBlockState(worldIn, Blocks.torch.getDefaultState(), 6, 5, 6, structureBoundingBoxIn);
/*  532 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LeftTurn
/*      */     extends Stronghold
/*      */   {
/*      */     public LeftTurn() {}
/*      */ 
/*      */     
/*      */     public LeftTurn(int p_i45579_1_, Random p_i45579_2_, StructureBoundingBox p_i45579_3_, EnumFacing p_i45579_4_) {
/*  545 */       super(p_i45579_1_);
/*  546 */       this.coordBaseMode = p_i45579_4_;
/*  547 */       this.field_143013_d = getRandomDoor(p_i45579_2_);
/*  548 */       this.boundingBox = p_i45579_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  553 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
/*      */         
/*  555 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       }
/*      */       else {
/*      */         
/*  559 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public static LeftTurn func_175867_a(List<StructureComponent> p_175867_0_, Random p_175867_1_, int p_175867_2_, int p_175867_3_, int p_175867_4_, EnumFacing p_175867_5_, int p_175867_6_) {
/*  565 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
/*  566 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175867_0_, structureboundingbox) == null) ? new LeftTurn(p_175867_6_, p_175867_1_, structureboundingbox, p_175867_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  571 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  573 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  577 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  578 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/*      */       
/*  580 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
/*      */         
/*  582 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       else {
/*      */         
/*  586 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       } 
/*      */       
/*  589 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Library
/*      */     extends Stronghold
/*      */   {
/*  596 */     private static final List<WeightedRandomChestContent> strongholdLibraryChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.book, 0, 1, 3, 20), new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20), new WeightedRandomChestContent((Item)Items.map, 0, 1, 1, 1), new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1) });
/*      */     
/*      */     private boolean isLargeRoom;
/*      */ 
/*      */     
/*      */     public Library() {}
/*      */ 
/*      */     
/*      */     public Library(int p_i45578_1_, Random p_i45578_2_, StructureBoundingBox p_i45578_3_, EnumFacing p_i45578_4_) {
/*  605 */       super(p_i45578_1_);
/*  606 */       this.coordBaseMode = p_i45578_4_;
/*  607 */       this.field_143013_d = getRandomDoor(p_i45578_2_);
/*  608 */       this.boundingBox = p_i45578_3_;
/*  609 */       this.isLargeRoom = (p_i45578_3_.getYSize() > 6);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  614 */       super.writeStructureToNBT(tagCompound);
/*  615 */       tagCompound.setBoolean("Tall", this.isLargeRoom);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  620 */       super.readStructureFromNBT(tagCompound);
/*  621 */       this.isLargeRoom = tagCompound.getBoolean("Tall");
/*      */     }
/*      */ 
/*      */     
/*      */     public static Library func_175864_a(List<StructureComponent> p_175864_0_, Random p_175864_1_, int p_175864_2_, int p_175864_3_, int p_175864_4_, EnumFacing p_175864_5_, int p_175864_6_) {
/*  626 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);
/*      */       
/*  628 */       if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(p_175864_0_, structureboundingbox) != null) {
/*      */         
/*  630 */         structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);
/*      */         
/*  632 */         if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(p_175864_0_, structureboundingbox) != null)
/*      */         {
/*  634 */           return null;
/*      */         }
/*      */       } 
/*      */       
/*  638 */       return new Library(p_175864_6_, p_175864_1_, structureboundingbox, p_175864_5_);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  643 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  645 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  649 */       int i = 11;
/*      */       
/*  651 */       if (!this.isLargeRoom)
/*      */       {
/*  653 */         i = 6;
/*      */       }
/*      */       
/*  656 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 13, i - 1, 14, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  657 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 4, 1, 0);
/*  658 */       func_175805_a(worldIn, structureBoundingBoxIn, randomIn, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.web.getDefaultState(), Blocks.web.getDefaultState(), false);
/*  659 */       int j = 1;
/*  660 */       int k = 12;
/*      */       
/*  662 */       for (int l = 1; l <= 13; l++) {
/*      */         
/*  664 */         if ((l - 1) % 4 == 0) {
/*      */           
/*  666 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  667 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  668 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 2, 3, l, structureBoundingBoxIn);
/*  669 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 11, 3, l, structureBoundingBoxIn);
/*      */           
/*  671 */           if (this.isLargeRoom)
/*      */           {
/*  673 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  674 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  679 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  680 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*      */           
/*  682 */           if (this.isLargeRoom) {
/*      */             
/*  684 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  685 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  690 */       for (int k1 = 3; k1 < 12; k1 += 2) {
/*      */         
/*  692 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, k1, 4, 3, k1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  693 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, k1, 7, 3, k1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  694 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, k1, 10, 3, k1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*      */       } 
/*      */       
/*  697 */       if (this.isLargeRoom) {
/*      */         
/*  699 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  700 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 1, 12, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  701 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 1, 9, 5, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  702 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 12, 9, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  703 */         setBlockState(worldIn, Blocks.planks.getDefaultState(), 9, 5, 11, structureBoundingBoxIn);
/*  704 */         setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 5, 11, structureBoundingBoxIn);
/*  705 */         setBlockState(worldIn, Blocks.planks.getDefaultState(), 9, 5, 10, structureBoundingBoxIn);
/*  706 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 6, 2, 3, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  707 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 6, 2, 10, 6, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  708 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 2, 9, 6, 2, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  709 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 12, 8, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  710 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 11, structureBoundingBoxIn);
/*  711 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 8, 6, 11, structureBoundingBoxIn);
/*  712 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 10, structureBoundingBoxIn);
/*  713 */         int l1 = getMetadataWithOffset(Blocks.ladder, 3);
/*  714 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 1, 13, structureBoundingBoxIn);
/*  715 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 2, 13, structureBoundingBoxIn);
/*  716 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 3, 13, structureBoundingBoxIn);
/*  717 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 4, 13, structureBoundingBoxIn);
/*  718 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 5, 13, structureBoundingBoxIn);
/*  719 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 6, 13, structureBoundingBoxIn);
/*  720 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 7, 13, structureBoundingBoxIn);
/*  721 */         int i1 = 7;
/*  722 */         int j1 = 7;
/*  723 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 9, j1, structureBoundingBoxIn);
/*  724 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 9, j1, structureBoundingBoxIn);
/*  725 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 8, j1, structureBoundingBoxIn);
/*  726 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 8, j1, structureBoundingBoxIn);
/*  727 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 7, j1, structureBoundingBoxIn);
/*  728 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 7, j1, structureBoundingBoxIn);
/*  729 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 2, 7, j1, structureBoundingBoxIn);
/*  730 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 + 1, 7, j1, structureBoundingBoxIn);
/*  731 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 7, j1 - 1, structureBoundingBoxIn);
/*  732 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 7, j1 + 1, structureBoundingBoxIn);
/*  733 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 7, j1 - 1, structureBoundingBoxIn);
/*  734 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 7, j1 + 1, structureBoundingBoxIn);
/*  735 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1 - 2, 8, j1, structureBoundingBoxIn);
/*  736 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1 + 1, 8, j1, structureBoundingBoxIn);
/*  737 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1 - 1, 8, j1 - 1, structureBoundingBoxIn);
/*  738 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1 - 1, 8, j1 + 1, structureBoundingBoxIn);
/*  739 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1, 8, j1 - 1, structureBoundingBoxIn);
/*  740 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1, 8, j1 + 1, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  743 */       generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 3, 3, 5, WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn, 1, 5, 2) }), 1 + randomIn.nextInt(4));
/*      */       
/*  745 */       if (this.isLargeRoom) {
/*      */         
/*  747 */         setBlockState(worldIn, Blocks.air.getDefaultState(), 12, 9, 1, structureBoundingBoxIn);
/*  748 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 12, 8, 1, WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn, 1, 5, 2) }), 1 + randomIn.nextInt(4));
/*      */       } 
/*      */       
/*  751 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PieceWeight
/*      */   {
/*      */     public Class<? extends StructureStrongholdPieces.Stronghold> pieceClass;
/*      */     
/*      */     public final int pieceWeight;
/*      */     public int instancesSpawned;
/*      */     public int instancesLimit;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureStrongholdPieces.Stronghold> p_i2076_1_, int p_i2076_2_, int p_i2076_3_) {
/*  765 */       this.pieceClass = p_i2076_1_;
/*  766 */       this.pieceWeight = p_i2076_2_;
/*  767 */       this.instancesLimit = p_i2076_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
/*  772 */       return (this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canSpawnMoreStructures() {
/*  777 */       return (this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class PortalRoom
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean hasSpawner;
/*      */ 
/*      */     
/*      */     public PortalRoom() {}
/*      */     
/*      */     public PortalRoom(int p_i45577_1_, Random p_i45577_2_, StructureBoundingBox p_i45577_3_, EnumFacing p_i45577_4_) {
/*  791 */       super(p_i45577_1_);
/*  792 */       this.coordBaseMode = p_i45577_4_;
/*  793 */       this.boundingBox = p_i45577_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  798 */       super.writeStructureToNBT(tagCompound);
/*  799 */       tagCompound.setBoolean("Mob", this.hasSpawner);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  804 */       super.readStructureFromNBT(tagCompound);
/*  805 */       this.hasSpawner = tagCompound.getBoolean("Mob");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  810 */       if (componentIn != null)
/*      */       {
/*  812 */         ((StructureStrongholdPieces.Stairs2)componentIn).strongholdPortalRoom = this;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public static PortalRoom func_175865_a(List<StructureComponent> p_175865_0_, Random p_175865_1_, int p_175865_2_, int p_175865_3_, int p_175865_4_, EnumFacing p_175865_5_, int p_175865_6_) {
/*  818 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175865_2_, p_175865_3_, p_175865_4_, -4, -1, 0, 11, 8, 16, p_175865_5_);
/*  819 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175865_0_, structureboundingbox) == null) ? new PortalRoom(p_175865_6_, p_175865_1_, structureboundingbox, p_175865_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  824 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 7, 15, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  825 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
/*  826 */       int i = 6;
/*  827 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, i, 1, 1, i, 14, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  828 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, i, 1, 9, i, 14, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  829 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 1, 8, i, 2, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  830 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 14, 8, i, 14, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  831 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 2, 1, 4, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  832 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 1, 9, 1, 4, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  833 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
/*  834 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
/*  835 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 3, 1, 8, 7, 1, 12, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  836 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
/*      */       
/*  838 */       for (int j = 3; j < 14; j += 2) {
/*      */         
/*  840 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, j, 0, 4, j, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*  841 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 3, j, 10, 4, j, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*      */       } 
/*      */       
/*  844 */       for (int k1 = 2; k1 < 9; k1 += 2)
/*      */       {
/*  846 */         fillWithBlocks(worldIn, structureBoundingBoxIn, k1, 3, 15, k1, 4, 15, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*      */       }
/*      */       
/*  849 */       int l1 = getMetadataWithOffset(Blocks.stone_brick_stairs, 3);
/*  850 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 6, 1, 7, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  851 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 2, 6, 6, 2, 7, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  852 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 3, 7, 6, 3, 7, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*      */       
/*  854 */       for (int k = 4; k <= 6; k++) {
/*      */         
/*  856 */         setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(l1), k, 1, 4, structureBoundingBoxIn);
/*  857 */         setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(l1), k, 2, 5, structureBoundingBoxIn);
/*  858 */         setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(l1), k, 3, 6, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  861 */       int i2 = EnumFacing.NORTH.getHorizontalIndex();
/*  862 */       int l = EnumFacing.SOUTH.getHorizontalIndex();
/*  863 */       int i1 = EnumFacing.EAST.getHorizontalIndex();
/*  864 */       int j1 = EnumFacing.WEST.getHorizontalIndex();
/*      */       
/*  866 */       if (this.coordBaseMode != null)
/*      */       {
/*  868 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case OPENING:
/*  871 */             i2 = EnumFacing.SOUTH.getHorizontalIndex();
/*  872 */             l = EnumFacing.NORTH.getHorizontalIndex();
/*      */             break;
/*      */           
/*      */           case WOOD_DOOR:
/*  876 */             i2 = EnumFacing.WEST.getHorizontalIndex();
/*  877 */             l = EnumFacing.EAST.getHorizontalIndex();
/*  878 */             i1 = EnumFacing.SOUTH.getHorizontalIndex();
/*  879 */             j1 = EnumFacing.NORTH.getHorizontalIndex();
/*      */             break;
/*      */           
/*      */           case GRATES:
/*  883 */             i2 = EnumFacing.EAST.getHorizontalIndex();
/*  884 */             l = EnumFacing.WEST.getHorizontalIndex();
/*  885 */             i1 = EnumFacing.SOUTH.getHorizontalIndex();
/*  886 */             j1 = EnumFacing.NORTH.getHorizontalIndex();
/*      */             break;
/*      */         } 
/*      */       }
/*  890 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i2).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 4, 3, 8, structureBoundingBoxIn);
/*  891 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i2).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 5, 3, 8, structureBoundingBoxIn);
/*  892 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i2).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 6, 3, 8, structureBoundingBoxIn);
/*  893 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(l).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 4, 3, 12, structureBoundingBoxIn);
/*  894 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(l).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 5, 3, 12, structureBoundingBoxIn);
/*  895 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(l).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 6, 3, 12, structureBoundingBoxIn);
/*  896 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 3, 3, 9, structureBoundingBoxIn);
/*  897 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 3, 3, 10, structureBoundingBoxIn);
/*  898 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 3, 3, 11, structureBoundingBoxIn);
/*  899 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(j1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 7, 3, 9, structureBoundingBoxIn);
/*  900 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(j1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 7, 3, 10, structureBoundingBoxIn);
/*  901 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(j1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 7, 3, 11, structureBoundingBoxIn);
/*      */       
/*  903 */       if (!this.hasSpawner) {
/*      */         
/*  905 */         i = getYWithOffset(3);
/*  906 */         BlockPos blockpos = new BlockPos(getXWithOffset(5, 6), i, getZWithOffset(5, 6));
/*      */         
/*  908 */         if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*      */           
/*  910 */           this.hasSpawner = true;
/*  911 */           worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
/*  912 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*      */           
/*  914 */           if (tileentity instanceof TileEntityMobSpawner)
/*      */           {
/*  916 */             ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Silverfish");
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  921 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Prison
/*      */     extends Stronghold
/*      */   {
/*      */     public Prison() {}
/*      */ 
/*      */     
/*      */     public Prison(int p_i45576_1_, Random p_i45576_2_, StructureBoundingBox p_i45576_3_, EnumFacing p_i45576_4_) {
/*  933 */       super(p_i45576_1_);
/*  934 */       this.coordBaseMode = p_i45576_4_;
/*  935 */       this.field_143013_d = getRandomDoor(p_i45576_2_);
/*  936 */       this.boundingBox = p_i45576_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  941 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Prison func_175860_a(List<StructureComponent> p_175860_0_, Random p_175860_1_, int p_175860_2_, int p_175860_3_, int p_175860_4_, EnumFacing p_175860_5_, int p_175860_6_) {
/*  946 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
/*  947 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175860_0_, structureboundingbox) == null) ? new Prison(p_175860_6_, p_175860_1_, structureboundingbox, p_175860_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  952 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  954 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  958 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 4, 10, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  959 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/*  960 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 10, 3, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  961 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 1, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  962 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 4, 3, 3, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  963 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 7, 4, 3, 7, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  964 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 4, 3, 9, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  965 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 4, 4, 3, 6, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*  966 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 7, 3, 5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*  967 */       setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
/*  968 */       setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 8, structureBoundingBoxIn);
/*  969 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 2, structureBoundingBoxIn);
/*  970 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 2, structureBoundingBoxIn);
/*  971 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 8, structureBoundingBoxIn);
/*  972 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 8, structureBoundingBoxIn);
/*  973 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class RightTurn
/*      */     extends LeftTurn
/*      */   {
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  982 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
/*      */         
/*  984 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       }
/*      */       else {
/*      */         
/*  988 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  994 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  996 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1000 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureStrongholdPieces.strongholdStones);
/* 1001 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/*      */       
/* 1003 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
/*      */         
/* 1005 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       else {
/*      */         
/* 1009 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       } 
/*      */       
/* 1012 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RoomCrossing
/*      */     extends Stronghold
/*      */   {
/* 1019 */     private static final List<WeightedRandomChestContent> strongholdRoomCrossingChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1) });
/*      */     
/*      */     protected int roomType;
/*      */ 
/*      */     
/*      */     public RoomCrossing() {}
/*      */ 
/*      */     
/*      */     public RoomCrossing(int p_i45575_1_, Random p_i45575_2_, StructureBoundingBox p_i45575_3_, EnumFacing p_i45575_4_) {
/* 1028 */       super(p_i45575_1_);
/* 1029 */       this.coordBaseMode = p_i45575_4_;
/* 1030 */       this.field_143013_d = getRandomDoor(p_i45575_2_);
/* 1031 */       this.boundingBox = p_i45575_3_;
/* 1032 */       this.roomType = p_i45575_2_.nextInt(5);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1037 */       super.writeStructureToNBT(tagCompound);
/* 1038 */       tagCompound.setInteger("Type", this.roomType);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1043 */       super.readStructureFromNBT(tagCompound);
/* 1044 */       this.roomType = tagCompound.getInteger("Type");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1049 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 4, 1);
/* 1050 */       getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 4);
/* 1051 */       getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public static RoomCrossing func_175859_a(List<StructureComponent> p_175859_0_, Random p_175859_1_, int p_175859_2_, int p_175859_3_, int p_175859_4_, EnumFacing p_175859_5_, int p_175859_6_) {
/* 1056 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
/* 1057 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175859_0_, structureboundingbox) == null) ? new RoomCrossing(p_175859_6_, p_175859_1_, structureboundingbox, p_175859_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*      */       int i1, i, j, k, l;
/* 1062 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1064 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1068 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 6, 10, true, randomIn, StructureStrongholdPieces.strongholdStones);
/* 1069 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 4, 1, 0);
/* 1070 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 10, 6, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1071 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1072 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 4, 10, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       
/* 1074 */       switch (this.roomType) {
/*      */         
/*      */         case 0:
/* 1077 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
/* 1078 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/* 1079 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/* 1080 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
/* 1081 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);
/* 1082 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
/* 1083 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
/* 1084 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
/* 1085 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
/* 1086 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 6, structureBoundingBoxIn);
/* 1087 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 4, structureBoundingBoxIn);
/* 1088 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/* 1089 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 6, structureBoundingBoxIn);
/* 1090 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
/* 1091 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
/*      */           break;
/*      */         
/*      */         case 1:
/* 1095 */           for (i1 = 0; i1 < 5; i1++) {
/*      */             
/* 1097 */             setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 1, 3 + i1, structureBoundingBoxIn);
/* 1098 */             setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 7, 1, 3 + i1, structureBoundingBoxIn);
/* 1099 */             setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3 + i1, 1, 3, structureBoundingBoxIn);
/* 1100 */             setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3 + i1, 1, 7, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1103 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
/* 1104 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/* 1105 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/* 1106 */           setBlockState(worldIn, Blocks.flowing_water.getDefaultState(), 5, 4, 5, structureBoundingBoxIn);
/*      */           break;
/*      */         
/*      */         case 2:
/* 1110 */           for (i = 1; i <= 9; i++) {
/*      */             
/* 1112 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 3, i, structureBoundingBoxIn);
/* 1113 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 9, 3, i, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1116 */           for (j = 1; j <= 9; j++) {
/*      */             
/* 1118 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), j, 3, 1, structureBoundingBoxIn);
/* 1119 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), j, 3, 9, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1122 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
/* 1123 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
/* 1124 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
/* 1125 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
/* 1126 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
/* 1127 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/* 1128 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
/* 1129 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);
/*      */           
/* 1131 */           for (k = 1; k <= 3; k++) {
/*      */             
/* 1133 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, k, 4, structureBoundingBoxIn);
/* 1134 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, k, 4, structureBoundingBoxIn);
/* 1135 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, k, 6, structureBoundingBoxIn);
/* 1136 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, k, 6, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1139 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/*      */           
/* 1141 */           for (l = 2; l <= 8; l++) {
/*      */             
/* 1143 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), 2, 3, l, structureBoundingBoxIn);
/* 1144 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, l, structureBoundingBoxIn);
/*      */             
/* 1146 */             if (l <= 3 || l >= 7) {
/*      */               
/* 1148 */               setBlockState(worldIn, Blocks.planks.getDefaultState(), 4, 3, l, structureBoundingBoxIn);
/* 1149 */               setBlockState(worldIn, Blocks.planks.getDefaultState(), 5, 3, l, structureBoundingBoxIn);
/* 1150 */               setBlockState(worldIn, Blocks.planks.getDefaultState(), 6, 3, l, structureBoundingBoxIn);
/*      */             } 
/*      */             
/* 1153 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 3, l, structureBoundingBoxIn);
/* 1154 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 3, l, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1157 */           setBlockState(worldIn, Blocks.ladder.getStateFromMeta(getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 1, 3, structureBoundingBoxIn);
/* 1158 */           setBlockState(worldIn, Blocks.ladder.getStateFromMeta(getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 2, 3, structureBoundingBoxIn);
/* 1159 */           setBlockState(worldIn, Blocks.ladder.getStateFromMeta(getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 3, 3, structureBoundingBoxIn);
/* 1160 */           generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 3, 4, 8, WeightedRandomChestContent.func_177629_a(strongholdRoomCrossingChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn) }), 1 + randomIn.nextInt(4));
/*      */           break;
/*      */       } 
/* 1163 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Stairs
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean field_75024_a;
/*      */ 
/*      */     
/*      */     public Stairs() {}
/*      */ 
/*      */     
/*      */     public Stairs(int p_i2081_1_, Random p_i2081_2_, int p_i2081_3_, int p_i2081_4_) {
/* 1178 */       super(p_i2081_1_);
/* 1179 */       this.field_75024_a = true;
/* 1180 */       this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2081_2_);
/* 1181 */       this.field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;
/*      */       
/* 1183 */       switch (this.coordBaseMode) {
/*      */         
/*      */         case OPENING:
/*      */         case IRON_DOOR:
/* 1187 */           this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
/*      */           return;
/*      */       } 
/*      */       
/* 1191 */       this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Stairs(int p_i45574_1_, Random p_i45574_2_, StructureBoundingBox p_i45574_3_, EnumFacing p_i45574_4_) {
/* 1197 */       super(p_i45574_1_);
/* 1198 */       this.field_75024_a = false;
/* 1199 */       this.coordBaseMode = p_i45574_4_;
/* 1200 */       this.field_143013_d = getRandomDoor(p_i45574_2_);
/* 1201 */       this.boundingBox = p_i45574_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1206 */       super.writeStructureToNBT(tagCompound);
/* 1207 */       tagCompound.setBoolean("Source", this.field_75024_a);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1212 */       super.readStructureFromNBT(tagCompound);
/* 1213 */       this.field_75024_a = tagCompound.getBoolean("Source");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1218 */       if (this.field_75024_a)
/*      */       {
/* 1220 */         StructureStrongholdPieces.strongComponentType = (Class)StructureStrongholdPieces.Crossing.class;
/*      */       }
/*      */       
/* 1223 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Stairs func_175863_a(List<StructureComponent> p_175863_0_, Random p_175863_1_, int p_175863_2_, int p_175863_3_, int p_175863_4_, EnumFacing p_175863_5_, int p_175863_6_) {
/* 1228 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
/* 1229 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175863_0_, structureboundingbox) == null) ? new Stairs(p_175863_6_, p_175863_1_, structureboundingbox, p_175863_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1234 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1236 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1240 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 4, true, randomIn, StructureStrongholdPieces.strongholdStones);
/* 1241 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 7, 0);
/* 1242 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
/* 1243 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 6, 1, structureBoundingBoxIn);
/* 1244 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 1, structureBoundingBoxIn);
/* 1245 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 6, 1, structureBoundingBoxIn);
/* 1246 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 2, structureBoundingBoxIn);
/* 1247 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, 3, structureBoundingBoxIn);
/* 1248 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 5, 3, structureBoundingBoxIn);
/* 1249 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, 3, structureBoundingBoxIn);
/* 1250 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 3, structureBoundingBoxIn);
/* 1251 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 4, 3, structureBoundingBoxIn);
/* 1252 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 2, structureBoundingBoxIn);
/* 1253 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 2, 1, structureBoundingBoxIn);
/* 1254 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 3, 1, structureBoundingBoxIn);
/* 1255 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 2, 1, structureBoundingBoxIn);
/* 1256 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 1, structureBoundingBoxIn);
/* 1257 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 2, 1, structureBoundingBoxIn);
/* 1258 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 2, structureBoundingBoxIn);
/* 1259 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 1, 3, structureBoundingBoxIn);
/* 1260 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Stairs2
/*      */     extends Stairs
/*      */   {
/*      */     public StructureStrongholdPieces.PieceWeight strongholdPieceWeight;
/*      */     public StructureStrongholdPieces.PortalRoom strongholdPortalRoom;
/* 1269 */     public List<StructureComponent> field_75026_c = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */     
/*      */     public Stairs2() {}
/*      */ 
/*      */     
/*      */     public Stairs2(int p_i2083_1_, Random p_i2083_2_, int p_i2083_3_, int p_i2083_4_) {
/* 1277 */       super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
/*      */     }
/*      */ 
/*      */     
/*      */     public BlockPos getBoundingBoxCenter() {
/* 1282 */       return (this.strongholdPortalRoom != null) ? this.strongholdPortalRoom.getBoundingBoxCenter() : super.getBoundingBoxCenter();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class StairsStraight
/*      */     extends Stronghold
/*      */   {
/*      */     public StairsStraight() {}
/*      */ 
/*      */     
/*      */     public StairsStraight(int p_i45572_1_, Random p_i45572_2_, StructureBoundingBox p_i45572_3_, EnumFacing p_i45572_4_) {
/* 1294 */       super(p_i45572_1_);
/* 1295 */       this.coordBaseMode = p_i45572_4_;
/* 1296 */       this.field_143013_d = getRandomDoor(p_i45572_2_);
/* 1297 */       this.boundingBox = p_i45572_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1302 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public static StairsStraight func_175861_a(List<StructureComponent> p_175861_0_, Random p_175861_1_, int p_175861_2_, int p_175861_3_, int p_175861_4_, EnumFacing p_175861_5_, int p_175861_6_) {
/* 1307 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
/* 1308 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175861_0_, structureboundingbox) == null) ? new StairsStraight(p_175861_6_, p_175861_1_, structureboundingbox, p_175861_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1313 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1315 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1319 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 7, true, randomIn, StructureStrongholdPieces.strongholdStones);
/* 1320 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 7, 0);
/* 1321 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
/* 1322 */       int i = getMetadataWithOffset(Blocks.stone_stairs, 2);
/*      */       
/* 1324 */       for (int j = 0; j < 6; j++) {
/*      */         
/* 1326 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 1, 6 - j, 1 + j, structureBoundingBoxIn);
/* 1327 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 2, 6 - j, 1 + j, structureBoundingBoxIn);
/* 1328 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 3, 6 - j, 1 + j, structureBoundingBoxIn);
/*      */         
/* 1330 */         if (j < 5) {
/*      */           
/* 1332 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5 - j, 1 + j, structureBoundingBoxIn);
/* 1333 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 5 - j, 1 + j, structureBoundingBoxIn);
/* 1334 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 5 - j, 1 + j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1338 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Stones
/*      */     extends StructureComponent.BlockSelector
/*      */   {
/*      */     public void selectBlocks(Random rand, int x, int y, int z, boolean p_75062_5_) {
/* 1351 */       if (p_75062_5_) {
/*      */         
/* 1353 */         float f = rand.nextFloat();
/*      */         
/* 1355 */         if (f < 0.2F)
/*      */         {
/* 1357 */           this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
/*      */         }
/* 1359 */         else if (f < 0.5F)
/*      */         {
/* 1361 */           this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
/*      */         }
/* 1363 */         else if (f < 0.55F)
/*      */         {
/* 1365 */           this.blockstate = Blocks.monster_egg.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.getMetadata());
/*      */         }
/*      */         else
/*      */         {
/* 1369 */           this.blockstate = Blocks.stonebrick.getDefaultState();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1374 */         this.blockstate = Blocks.air.getDefaultState();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Straight
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean expandsX;
/*      */     
/*      */     private boolean expandsZ;
/*      */     
/*      */     public Straight() {}
/*      */     
/*      */     public Straight(int p_i45573_1_, Random p_i45573_2_, StructureBoundingBox p_i45573_3_, EnumFacing p_i45573_4_) {
/* 1390 */       super(p_i45573_1_);
/* 1391 */       this.coordBaseMode = p_i45573_4_;
/* 1392 */       this.field_143013_d = getRandomDoor(p_i45573_2_);
/* 1393 */       this.boundingBox = p_i45573_3_;
/* 1394 */       this.expandsX = (p_i45573_2_.nextInt(2) == 0);
/* 1395 */       this.expandsZ = (p_i45573_2_.nextInt(2) == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1400 */       super.writeStructureToNBT(tagCompound);
/* 1401 */       tagCompound.setBoolean("Left", this.expandsX);
/* 1402 */       tagCompound.setBoolean("Right", this.expandsZ);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1407 */       super.readStructureFromNBT(tagCompound);
/* 1408 */       this.expandsX = tagCompound.getBoolean("Left");
/* 1409 */       this.expandsZ = tagCompound.getBoolean("Right");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1414 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       
/* 1416 */       if (this.expandsX)
/*      */       {
/* 1418 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 2);
/*      */       }
/*      */       
/* 1421 */       if (this.expandsZ)
/*      */       {
/* 1423 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 2);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public static Straight func_175862_a(List<StructureComponent> p_175862_0_, Random p_175862_1_, int p_175862_2_, int p_175862_3_, int p_175862_4_, EnumFacing p_175862_5_, int p_175862_6_) {
/* 1429 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
/* 1430 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175862_0_, structureboundingbox) == null) ? new Straight(p_175862_6_, p_175862_1_, structureboundingbox, p_175862_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1435 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1437 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1441 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureStrongholdPieces.strongholdStones);
/* 1442 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/* 1443 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
/* 1444 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 1, Blocks.torch.getDefaultState());
/* 1445 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 1, Blocks.torch.getDefaultState());
/* 1446 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 5, Blocks.torch.getDefaultState());
/* 1447 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 5, Blocks.torch.getDefaultState());
/*      */       
/* 1449 */       if (this.expandsX)
/*      */       {
/* 1451 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 2, 0, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/* 1454 */       if (this.expandsZ)
/*      */       {
/* 1456 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 2, 4, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/* 1459 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Stronghold
/*      */     extends StructureComponent
/*      */   {
/* 1466 */     protected Door field_143013_d = Door.OPENING;
/*      */ 
/*      */ 
/*      */     
/*      */     public Stronghold() {}
/*      */ 
/*      */     
/*      */     protected Stronghold(int p_i2087_1_) {
/* 1474 */       super(p_i2087_1_);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1479 */       tagCompound.setString("EntryDoor", this.field_143013_d.name());
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1484 */       this.field_143013_d = Door.valueOf(tagCompound.getString("EntryDoor"));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void placeDoor(World worldIn, Random p_74990_2_, StructureBoundingBox p_74990_3_, Door p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_) {
/* 1489 */       switch (p_74990_4_) {
/*      */ 
/*      */         
/*      */         default:
/* 1493 */           fillWithBlocks(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */           return;
/*      */         
/*      */         case WOOD_DOOR:
/* 1497 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1498 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1499 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1500 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1501 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1502 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1503 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1504 */           setBlockState(worldIn, Blocks.oak_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1505 */           setBlockState(worldIn, Blocks.oak_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/*      */           return;
/*      */         
/*      */         case GRATES:
/* 1509 */           setBlockState(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1510 */           setBlockState(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1511 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1512 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1513 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1514 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1515 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1516 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1517 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_); return;
/*      */         case IRON_DOOR:
/*      */           break;
/*      */       } 
/* 1521 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1522 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1523 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1524 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1525 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1526 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1527 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1528 */       setBlockState(worldIn, Blocks.iron_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1529 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1530 */       setBlockState(worldIn, Blocks.stone_button.getStateFromMeta(getMetadataWithOffset(Blocks.stone_button, 4)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
/* 1531 */       setBlockState(worldIn, Blocks.stone_button.getStateFromMeta(getMetadataWithOffset(Blocks.stone_button, 3)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Door getRandomDoor(Random p_74988_1_) {
/* 1537 */       int i = p_74988_1_.nextInt(5);
/*      */       
/* 1539 */       switch (i) {
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/* 1544 */           return Door.OPENING;
/*      */         
/*      */         case 2:
/* 1547 */           return Door.WOOD_DOOR;
/*      */         
/*      */         case 3:
/* 1550 */           return Door.GRATES;
/*      */         case 4:
/*      */           break;
/* 1553 */       }  return Door.IRON_DOOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentNormal(StructureStrongholdPieces.Stairs2 p_74986_1_, List<StructureComponent> p_74986_2_, Random p_74986_3_, int p_74986_4_, int p_74986_5_) {
/* 1559 */       if (this.coordBaseMode != null)
/*      */       {
/* 1561 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case IRON_DOOR:
/* 1564 */             return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ - 1, this.coordBaseMode, getComponentType());
/*      */           
/*      */           case OPENING:
/* 1567 */             return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.maxZ + 1, this.coordBaseMode, getComponentType());
/*      */           
/*      */           case WOOD_DOOR:
/* 1570 */             return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, this.coordBaseMode, getComponentType());
/*      */           
/*      */           case GRATES:
/* 1573 */             return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, this.coordBaseMode, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1577 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentX(StructureStrongholdPieces.Stairs2 p_74989_1_, List<StructureComponent> p_74989_2_, Random p_74989_3_, int p_74989_4_, int p_74989_5_) {
/* 1582 */       if (this.coordBaseMode != null)
/*      */       {
/* 1584 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case IRON_DOOR:
/* 1587 */             return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case OPENING:
/* 1590 */             return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case WOOD_DOOR:
/* 1593 */             return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */           
/*      */           case GRATES:
/* 1596 */             return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1600 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentZ(StructureStrongholdPieces.Stairs2 p_74987_1_, List<StructureComponent> p_74987_2_, Random p_74987_3_, int p_74987_4_, int p_74987_5_) {
/* 1605 */       if (this.coordBaseMode != null)
/*      */       {
/* 1607 */         switch (this.coordBaseMode) {
/*      */           
/*      */           case IRON_DOOR:
/* 1610 */             return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case OPENING:
/* 1613 */             return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case WOOD_DOOR:
/* 1616 */             return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */           
/*      */           case GRATES:
/* 1619 */             return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1623 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected static boolean canStrongholdGoDeeper(StructureBoundingBox p_74991_0_) {
/* 1628 */       return (p_74991_0_ != null && p_74991_0_.minY > 10);
/*      */     }
/*      */     
/*      */     public enum Door
/*      */     {
/* 1633 */       OPENING,
/* 1634 */       WOOD_DOOR,
/* 1635 */       GRATES,
/* 1636 */       IRON_DOOR;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\StructureStrongholdPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */