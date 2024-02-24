/*     */ package net.minecraft.world.gen.structure;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class StructureMineshaftPieces {
/*  26 */   static final List<WeightedRandomChestContent> CHEST_CONTENT_WEIGHT_LIST = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1) });
/*     */ 
/*     */   
/*     */   public static void registerStructurePieces() {
/*  30 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "MSCorridor");
/*  31 */     MapGenStructureIO.registerStructureComponent((Class)Cross.class, "MSCrossing");
/*  32 */     MapGenStructureIO.registerStructureComponent((Class)Room.class, "MSRoom");
/*  33 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "MSStairs");
/*     */   }
/*     */ 
/*     */   
/*     */   private static StructureComponent func_175892_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing, int type) {
/*  38 */     int i = rand.nextInt(100);
/*     */     
/*  40 */     if (i >= 80) {
/*     */       
/*  42 */       StructureBoundingBox structureboundingbox = Cross.func_175813_a(listIn, rand, x, y, z, facing);
/*     */       
/*  44 */       if (structureboundingbox != null)
/*     */       {
/*  46 */         return new Cross(type, rand, structureboundingbox, facing);
/*     */       }
/*     */     }
/*  49 */     else if (i >= 70) {
/*     */       
/*  51 */       StructureBoundingBox structureboundingbox1 = Stairs.func_175812_a(listIn, rand, x, y, z, facing);
/*     */       
/*  53 */       if (structureboundingbox1 != null)
/*     */       {
/*  55 */         return new Stairs(type, rand, structureboundingbox1, facing);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  60 */       StructureBoundingBox structureboundingbox2 = Corridor.func_175814_a(listIn, rand, x, y, z, facing);
/*     */       
/*  62 */       if (structureboundingbox2 != null)
/*     */       {
/*  64 */         return new Corridor(type, rand, structureboundingbox2, facing);
/*     */       }
/*     */     } 
/*     */     
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static StructureComponent func_175890_b(StructureComponent componentIn, List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing, int type) {
/*  73 */     if (type > 8)
/*     */     {
/*  75 */       return null;
/*     */     }
/*  77 */     if (Math.abs(x - (componentIn.getBoundingBox()).minX) <= 80 && Math.abs(z - (componentIn.getBoundingBox()).minZ) <= 80) {
/*     */       
/*  79 */       StructureComponent structurecomponent = func_175892_a(listIn, rand, x, y, z, facing, type + 1);
/*     */       
/*  81 */       if (structurecomponent != null) {
/*     */         
/*  83 */         listIn.add(structurecomponent);
/*  84 */         structurecomponent.buildComponent(componentIn, listIn, rand);
/*     */       } 
/*     */       
/*  87 */       return structurecomponent;
/*     */     } 
/*     */ 
/*     */     
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Corridor
/*     */     extends StructureComponent
/*     */   {
/*     */     private boolean hasRails;
/*     */     
/*     */     private boolean hasSpiders;
/*     */     
/*     */     private boolean spawnerPlaced;
/*     */     private int sectionCount;
/*     */     
/*     */     public Corridor() {}
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 108 */       tagCompound.setBoolean("hr", this.hasRails);
/* 109 */       tagCompound.setBoolean("sc", this.hasSpiders);
/* 110 */       tagCompound.setBoolean("hps", this.spawnerPlaced);
/* 111 */       tagCompound.setInteger("Num", this.sectionCount);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 116 */       this.hasRails = tagCompound.getBoolean("hr");
/* 117 */       this.hasSpiders = tagCompound.getBoolean("sc");
/* 118 */       this.spawnerPlaced = tagCompound.getBoolean("hps");
/* 119 */       this.sectionCount = tagCompound.getInteger("Num");
/*     */     }
/*     */ 
/*     */     
/*     */     public Corridor(int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
/* 124 */       super(type);
/* 125 */       this.coordBaseMode = facing;
/* 126 */       this.boundingBox = structurebb;
/* 127 */       this.hasRails = (rand.nextInt(3) == 0);
/* 128 */       this.hasSpiders = (!this.hasRails && rand.nextInt(23) == 0);
/*     */       
/* 130 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
/*     */         
/* 132 */         this.sectionCount = structurebb.getXSize() / 5;
/*     */       }
/*     */       else {
/*     */         
/* 136 */         this.sectionCount = structurebb.getZSize() / 5;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public static StructureBoundingBox func_175814_a(List<StructureComponent> p_175814_0_, Random rand, int x, int y, int z, EnumFacing facing) {
/* 142 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
/*     */       
/*     */       int i;
/* 145 */       for (i = rand.nextInt(3) + 2; i > 0; i--) {
/*     */         
/* 147 */         int j = i * 5;
/*     */         
/* 149 */         switch (facing) {
/*     */           
/*     */           case NORTH:
/* 152 */             structureboundingbox.maxX = x + 2;
/* 153 */             structureboundingbox.minZ = z - j - 1;
/*     */             break;
/*     */           
/*     */           case SOUTH:
/* 157 */             structureboundingbox.maxX = x + 2;
/* 158 */             structureboundingbox.maxZ = z + j - 1;
/*     */             break;
/*     */           
/*     */           case WEST:
/* 162 */             structureboundingbox.minX = x - j - 1;
/* 163 */             structureboundingbox.maxZ = z + 2;
/*     */             break;
/*     */           
/*     */           case EAST:
/* 167 */             structureboundingbox.maxX = x + j - 1;
/* 168 */             structureboundingbox.maxZ = z + 2;
/*     */             break;
/*     */         } 
/* 171 */         if (StructureComponent.findIntersecting(p_175814_0_, structureboundingbox) == null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 177 */       return (i > 0) ? structureboundingbox : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 182 */       int i = getComponentType();
/* 183 */       int j = rand.nextInt(4);
/*     */       
/* 185 */       if (this.coordBaseMode != null)
/*     */       {
/* 187 */         switch (this.coordBaseMode) {
/*     */           
/*     */           case NORTH:
/* 190 */             if (j <= 1) {
/*     */               
/* 192 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, i); break;
/*     */             } 
/* 194 */             if (j == 2) {
/*     */               
/* 196 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, EnumFacing.WEST, i);
/*     */               
/*     */               break;
/*     */             } 
/* 200 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, EnumFacing.EAST, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case SOUTH:
/* 206 */             if (j <= 1) {
/*     */               
/* 208 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, i); break;
/*     */             } 
/* 210 */             if (j == 2) {
/*     */               
/* 212 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.WEST, i);
/*     */               
/*     */               break;
/*     */             } 
/* 216 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.EAST, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case WEST:
/* 222 */             if (j <= 1) {
/*     */               
/* 224 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, i); break;
/*     */             } 
/* 226 */             if (j == 2) {
/*     */               
/* 228 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */               
/*     */               break;
/*     */             } 
/* 232 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case EAST:
/* 238 */             if (j <= 1) {
/*     */               
/* 240 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, i); break;
/*     */             } 
/* 242 */             if (j == 2) {
/*     */               
/* 244 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */               
/*     */               break;
/*     */             } 
/* 248 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             break;
/*     */         } 
/*     */       
/*     */       }
/* 253 */       if (i < 8)
/*     */       {
/* 255 */         if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
/*     */           
/* 257 */           for (int i1 = this.boundingBox.minX + 3; i1 + 3 <= this.boundingBox.maxX; i1 += 5) {
/*     */             
/* 259 */             int j1 = rand.nextInt(5);
/*     */             
/* 261 */             if (j1 == 0)
/*     */             {
/* 263 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, i1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i + 1);
/*     */             }
/* 265 */             else if (j1 == 1)
/*     */             {
/* 267 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, i1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i + 1);
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 273 */           for (int k = this.boundingBox.minZ + 3; k + 3 <= this.boundingBox.maxZ; k += 5) {
/*     */             
/* 275 */             int l = rand.nextInt(5);
/*     */             
/* 277 */             if (l == 0) {
/*     */               
/* 279 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, k, EnumFacing.WEST, i + 1);
/*     */             }
/* 281 */             else if (l == 1) {
/*     */               
/* 283 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, k, EnumFacing.EAST, i + 1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean generateChestContents(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, List<WeightedRandomChestContent> listIn, int max) {
/* 292 */       BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */       
/* 294 */       if (boundingBoxIn.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
/*     */         
/* 296 */         int i = rand.nextBoolean() ? 1 : 0;
/* 297 */         worldIn.setBlockState(blockpos, Blocks.rail.getStateFromMeta(getMetadataWithOffset(Blocks.rail, i)), 2);
/* 298 */         EntityMinecartChest entityminecartchest = new EntityMinecartChest(worldIn, (blockpos.getX() + 0.5F), (blockpos.getY() + 0.5F), (blockpos.getZ() + 0.5F));
/* 299 */         WeightedRandomChestContent.generateChestContents(rand, listIn, (IInventory)entityminecartchest, max);
/* 300 */         worldIn.spawnEntityInWorld((Entity)entityminecartchest);
/* 301 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 305 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 311 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*     */       {
/* 313 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 317 */       int i = 0;
/* 318 */       int j = 2;
/* 319 */       int k = 0;
/* 320 */       int l = 2;
/* 321 */       int i1 = this.sectionCount * 5 - 1;
/* 322 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 1, i1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 323 */       func_175805_a(worldIn, structureBoundingBoxIn, randomIn, 0.8F, 0, 2, 0, 2, 2, i1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       
/* 325 */       if (this.hasSpiders)
/*     */       {
/* 327 */         func_175805_a(worldIn, structureBoundingBoxIn, randomIn, 0.6F, 0, 0, 0, 2, 1, i1, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       }
/*     */       
/* 330 */       for (int j1 = 0; j1 < this.sectionCount; j1++) {
/*     */         
/* 332 */         int k1 = 2 + j1 * 5;
/* 333 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, k1, 0, 1, k1, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 334 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, k1, 2, 1, k1, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */         
/* 336 */         if (randomIn.nextInt(4) == 0) {
/*     */           
/* 338 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, k1, 0, 2, k1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 339 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, k1, 2, 2, k1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */         }
/*     */         else {
/*     */           
/* 343 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, k1, 2, 2, k1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */         } 
/*     */         
/* 346 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 0, 2, k1 - 1, Blocks.web.getDefaultState());
/* 347 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 2, 2, k1 - 1, Blocks.web.getDefaultState());
/* 348 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 0, 2, k1 + 1, Blocks.web.getDefaultState());
/* 349 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 2, 2, k1 + 1, Blocks.web.getDefaultState());
/* 350 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 0, 2, k1 - 2, Blocks.web.getDefaultState());
/* 351 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 2, 2, k1 - 2, Blocks.web.getDefaultState());
/* 352 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 0, 2, k1 + 2, Blocks.web.getDefaultState());
/* 353 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 2, 2, k1 + 2, Blocks.web.getDefaultState());
/* 354 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 1, 2, k1 - 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
/* 355 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 1, 2, k1 + 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
/*     */         
/* 357 */         if (randomIn.nextInt(100) == 0)
/*     */         {
/* 359 */           generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 2, 0, k1 - 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn) }), 3 + randomIn.nextInt(4));
/*     */         }
/*     */         
/* 362 */         if (randomIn.nextInt(100) == 0)
/*     */         {
/* 364 */           generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 0, 0, k1 + 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn) }), 3 + randomIn.nextInt(4));
/*     */         }
/*     */         
/* 367 */         if (this.hasSpiders && !this.spawnerPlaced) {
/*     */           
/* 369 */           int l1 = getYWithOffset(0);
/* 370 */           int i2 = k1 - 1 + randomIn.nextInt(3);
/* 371 */           int j2 = getXWithOffset(1, i2);
/* 372 */           i2 = getZWithOffset(1, i2);
/* 373 */           BlockPos blockpos = new BlockPos(j2, l1, i2);
/*     */           
/* 375 */           if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*     */             
/* 377 */             this.spawnerPlaced = true;
/* 378 */             worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
/* 379 */             TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */             
/* 381 */             if (tileentity instanceof TileEntityMobSpawner)
/*     */             {
/* 383 */               ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("CaveSpider");
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 389 */       for (int k2 = 0; k2 <= 2; k2++) {
/*     */         
/* 391 */         for (int i3 = 0; i3 <= i1; i3++) {
/*     */           
/* 393 */           int j3 = -1;
/* 394 */           IBlockState iblockstate1 = getBlockStateFromPos(worldIn, k2, j3, i3, structureBoundingBoxIn);
/*     */           
/* 396 */           if (iblockstate1.getBlock().getMaterial() == Material.air) {
/*     */             
/* 398 */             int k3 = -1;
/* 399 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), k2, k3, i3, structureBoundingBoxIn);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 404 */       if (this.hasRails)
/*     */       {
/* 406 */         for (int l2 = 0; l2 <= i1; l2++) {
/*     */           
/* 408 */           IBlockState iblockstate = getBlockStateFromPos(worldIn, 1, -1, l2, structureBoundingBoxIn);
/*     */           
/* 410 */           if (iblockstate.getBlock().getMaterial() != Material.air && iblockstate.getBlock().isFullBlock())
/*     */           {
/* 412 */             randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.7F, 1, 0, l2, Blocks.rail.getStateFromMeta(getMetadataWithOffset(Blocks.rail, 0)));
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 417 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Cross
/*     */     extends StructureComponent
/*     */   {
/*     */     private EnumFacing corridorDirection;
/*     */     
/*     */     private boolean isMultipleFloors;
/*     */ 
/*     */     
/*     */     public Cross() {}
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 433 */       tagCompound.setBoolean("tf", this.isMultipleFloors);
/* 434 */       tagCompound.setInteger("D", this.corridorDirection.getHorizontalIndex());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 439 */       this.isMultipleFloors = tagCompound.getBoolean("tf");
/* 440 */       this.corridorDirection = EnumFacing.getHorizontal(tagCompound.getInteger("D"));
/*     */     }
/*     */ 
/*     */     
/*     */     public Cross(int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
/* 445 */       super(type);
/* 446 */       this.corridorDirection = facing;
/* 447 */       this.boundingBox = structurebb;
/* 448 */       this.isMultipleFloors = (structurebb.getYSize() > 3);
/*     */     }
/*     */ 
/*     */     
/*     */     public static StructureBoundingBox func_175813_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 453 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
/*     */       
/* 455 */       if (rand.nextInt(4) == 0)
/*     */       {
/* 457 */         structureboundingbox.maxY += 4;
/*     */       }
/*     */       
/* 460 */       switch (facing) {
/*     */         
/*     */         case NORTH:
/* 463 */           structureboundingbox.minX = x - 1;
/* 464 */           structureboundingbox.maxX = x + 3;
/* 465 */           structureboundingbox.minZ = z - 4;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 469 */           structureboundingbox.minX = x - 1;
/* 470 */           structureboundingbox.maxX = x + 3;
/* 471 */           structureboundingbox.maxZ = z + 4;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 475 */           structureboundingbox.minX = x - 4;
/* 476 */           structureboundingbox.minZ = z - 1;
/* 477 */           structureboundingbox.maxZ = z + 3;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 481 */           structureboundingbox.maxX = x + 4;
/* 482 */           structureboundingbox.minZ = z - 1;
/* 483 */           structureboundingbox.maxZ = z + 3;
/*     */           break;
/*     */       } 
/* 486 */       return (StructureComponent.findIntersecting(listIn, structureboundingbox) != null) ? null : structureboundingbox;
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 491 */       int i = getComponentType();
/*     */       
/* 493 */       switch (this.corridorDirection) {
/*     */         
/*     */         case NORTH:
/* 496 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 497 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/* 498 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 502 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 503 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/* 504 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 508 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 509 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 510 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 514 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 515 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 516 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */       } 
/* 519 */       if (this.isMultipleFloors) {
/*     */         
/* 521 */         if (rand.nextBoolean())
/*     */         {
/* 523 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */         }
/*     */         
/* 526 */         if (rand.nextBoolean())
/*     */         {
/* 528 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/*     */         }
/*     */         
/* 531 */         if (rand.nextBoolean())
/*     */         {
/* 533 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */         }
/*     */         
/* 536 */         if (rand.nextBoolean())
/*     */         {
/* 538 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 545 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*     */       {
/* 547 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 551 */       if (this.isMultipleFloors) {
/*     */         
/* 553 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 554 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 555 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 556 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 557 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       }
/*     */       else {
/*     */         
/* 561 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 562 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       } 
/*     */       
/* 565 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 566 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 567 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 568 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       
/* 570 */       for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; i++) {
/*     */         
/* 572 */         for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; j++) {
/*     */           
/* 574 */           if (getBlockStateFromPos(worldIn, i, this.boundingBox.minY - 1, j, structureBoundingBoxIn).getBlock().getMaterial() == Material.air)
/*     */           {
/* 576 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), i, this.boundingBox.minY - 1, j, structureBoundingBoxIn);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 581 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Room
/*     */     extends StructureComponent
/*     */   {
/* 588 */     private final List<StructureBoundingBox> roomsLinkedToTheRoom = Lists.newLinkedList();
/*     */ 
/*     */ 
/*     */     
/*     */     public Room() {}
/*     */ 
/*     */     
/*     */     public Room(int type, Random rand, int x, int z) {
/* 596 */       super(type);
/* 597 */       this.boundingBox = new StructureBoundingBox(x, 50, z, x + 7 + rand.nextInt(6), 54 + rand.nextInt(6), z + 7 + rand.nextInt(6));
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 602 */       int i = getComponentType();
/* 603 */       int j = this.boundingBox.getYSize() - 3 - 1;
/*     */       
/* 605 */       if (j <= 0)
/*     */       {
/* 607 */         j = 1;
/*     */       }
/*     */       
/* 610 */       int k = 0;
/*     */       
/* 612 */       for (int lvt_5_1_ = 0; k < this.boundingBox.getXSize(); k += 4) {
/*     */         
/* 614 */         k += rand.nextInt(this.boundingBox.getXSize());
/*     */         
/* 616 */         if (k + 3 > this.boundingBox.getXSize()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 621 */         StructureComponent structurecomponent = StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + k, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */         
/* 623 */         if (structurecomponent != null) {
/*     */           
/* 625 */           StructureBoundingBox structureboundingbox = structurecomponent.getBoundingBox();
/* 626 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox.minX, structureboundingbox.minY, this.boundingBox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, this.boundingBox.minZ + 1));
/*     */         } 
/*     */       } 
/*     */       
/* 630 */       for (k = 0; k < this.boundingBox.getXSize(); k += 4) {
/*     */         
/* 632 */         k += rand.nextInt(this.boundingBox.getXSize());
/*     */         
/* 634 */         if (k + 3 > this.boundingBox.getXSize()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 639 */         StructureComponent structurecomponent1 = StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + k, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */         
/* 641 */         if (structurecomponent1 != null) {
/*     */           
/* 643 */           StructureBoundingBox structureboundingbox1 = structurecomponent1.getBoundingBox();
/* 644 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox1.minX, structureboundingbox1.minY, this.boundingBox.maxZ - 1, structureboundingbox1.maxX, structureboundingbox1.maxY, this.boundingBox.maxZ));
/*     */         } 
/*     */       } 
/*     */       
/* 648 */       for (k = 0; k < this.boundingBox.getZSize(); k += 4) {
/*     */         
/* 650 */         k += rand.nextInt(this.boundingBox.getZSize());
/*     */         
/* 652 */         if (k + 3 > this.boundingBox.getZSize()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 657 */         StructureComponent structurecomponent2 = StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ + k, EnumFacing.WEST, i);
/*     */         
/* 659 */         if (structurecomponent2 != null) {
/*     */           
/* 661 */           StructureBoundingBox structureboundingbox2 = structurecomponent2.getBoundingBox();
/* 662 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, structureboundingbox2.minY, structureboundingbox2.minZ, this.boundingBox.minX + 1, structureboundingbox2.maxY, structureboundingbox2.maxZ));
/*     */         } 
/*     */       } 
/*     */       
/* 666 */       for (k = 0; k < this.boundingBox.getZSize(); k += 4) {
/*     */         
/* 668 */         k += rand.nextInt(this.boundingBox.getZSize());
/*     */         
/* 670 */         if (k + 3 > this.boundingBox.getZSize()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 675 */         StructureComponent structurecomponent3 = StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ + k, EnumFacing.EAST, i);
/*     */         
/* 677 */         if (structurecomponent3 != null) {
/*     */           
/* 679 */           StructureBoundingBox structureboundingbox3 = structurecomponent3.getBoundingBox();
/* 680 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, structureboundingbox3.minY, structureboundingbox3.minZ, this.boundingBox.maxX, structureboundingbox3.maxY, structureboundingbox3.maxZ));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 687 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*     */       {
/* 689 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 693 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt.getDefaultState(), Blocks.air.getDefaultState(), true);
/* 694 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       
/* 696 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom)
/*     */       {
/* 698 */         fillWithBlocks(worldIn, structureBoundingBoxIn, structureboundingbox.minX, structureboundingbox.maxY - 2, structureboundingbox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, structureboundingbox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       }
/*     */       
/* 701 */       randomlyRareFillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), false);
/* 702 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_181138_a(int p_181138_1_, int p_181138_2_, int p_181138_3_) {
/* 708 */       super.func_181138_a(p_181138_1_, p_181138_2_, p_181138_3_);
/*     */       
/* 710 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom)
/*     */       {
/* 712 */         structureboundingbox.offset(p_181138_1_, p_181138_2_, p_181138_3_);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 718 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 720 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom)
/*     */       {
/* 722 */         nbttaglist.appendTag((NBTBase)structureboundingbox.toNBTTagIntArray());
/*     */       }
/*     */       
/* 725 */       tagCompound.setTag("Entrances", (NBTBase)nbttaglist);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 730 */       NBTTagList nbttaglist = tagCompound.getTagList("Entrances", 11);
/*     */       
/* 732 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */       {
/* 734 */         this.roomsLinkedToTheRoom.add(new StructureBoundingBox(nbttaglist.getIntArrayAt(i)));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Stairs
/*     */     extends StructureComponent
/*     */   {
/*     */     public Stairs() {}
/*     */ 
/*     */     
/*     */     public Stairs(int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
/* 747 */       super(type);
/* 748 */       this.coordBaseMode = facing;
/* 749 */       this.boundingBox = structurebb;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */     
/*     */     public static StructureBoundingBox func_175812_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 762 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y - 5, z, x, y + 2, z);
/*     */       
/* 764 */       switch (facing) {
/*     */         
/*     */         case NORTH:
/* 767 */           structureboundingbox.maxX = x + 2;
/* 768 */           structureboundingbox.minZ = z - 8;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 772 */           structureboundingbox.maxX = x + 2;
/* 773 */           structureboundingbox.maxZ = z + 8;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 777 */           structureboundingbox.minX = x - 8;
/* 778 */           structureboundingbox.maxZ = z + 2;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 782 */           structureboundingbox.maxX = x + 8;
/* 783 */           structureboundingbox.maxZ = z + 2;
/*     */           break;
/*     */       } 
/* 786 */       return (StructureComponent.findIntersecting(listIn, structureboundingbox) != null) ? null : structureboundingbox;
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 791 */       int i = getComponentType();
/*     */       
/* 793 */       if (this.coordBaseMode != null)
/*     */       {
/* 795 */         switch (this.coordBaseMode) {
/*     */           
/*     */           case NORTH:
/* 798 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */             break;
/*     */           
/*     */           case SOUTH:
/* 802 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             break;
/*     */           
/*     */           case WEST:
/* 806 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, i);
/*     */             break;
/*     */           
/*     */           case EAST:
/* 810 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, i);
/*     */             break;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 817 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*     */       {
/* 819 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 823 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 824 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       
/* 826 */       for (int i = 0; i < 5; i++)
/*     */       {
/* 828 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5 - i - ((i < 4) ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       }
/*     */       
/* 831 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\StructureMineshaftPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */