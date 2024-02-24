/*     */ package net.minecraft.village;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ 
/*     */ public class VillageCollection extends WorldSavedData {
/*  21 */   private final List<BlockPos> villagerPositionsList = Lists.newArrayList(); private World worldObj;
/*  22 */   private final List<VillageDoorInfo> newDoors = Lists.newArrayList();
/*  23 */   private final List<Village> villageList = Lists.newArrayList();
/*     */   
/*     */   private int tickCounter;
/*     */   
/*     */   public VillageCollection(String name) {
/*  28 */     super(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public VillageCollection(World worldIn) {
/*  33 */     super(fileNameForProvider(worldIn.provider));
/*  34 */     this.worldObj = worldIn;
/*  35 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldsForAll(World worldIn) {
/*  40 */     this.worldObj = worldIn;
/*     */     
/*  42 */     for (Village village : this.villageList)
/*     */     {
/*  44 */       village.setWorld(worldIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToVillagerPositionList(BlockPos pos) {
/*  50 */     if (this.villagerPositionsList.size() <= 64)
/*     */     {
/*  52 */       if (!positionInList(pos))
/*     */       {
/*  54 */         this.villagerPositionsList.add(pos);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  64 */     this.tickCounter++;
/*     */     
/*  66 */     for (Village village : this.villageList)
/*     */     {
/*  68 */       village.tick(this.tickCounter);
/*     */     }
/*     */     
/*  71 */     removeAnnihilatedVillages();
/*  72 */     dropOldestVillagerPosition();
/*  73 */     addNewDoorsToVillageOrCreateVillage();
/*     */     
/*  75 */     if (this.tickCounter % 400 == 0)
/*     */     {
/*  77 */       markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeAnnihilatedVillages() {
/*  83 */     Iterator<Village> iterator = this.villageList.iterator();
/*     */     
/*  85 */     while (iterator.hasNext()) {
/*     */       
/*  87 */       Village village = iterator.next();
/*     */       
/*  89 */       if (village.isAnnihilated()) {
/*     */         
/*  91 */         iterator.remove();
/*  92 */         markDirty();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Village> getVillageList() {
/*  99 */     return this.villageList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Village getNearestVillage(BlockPos doorBlock, int radius) {
/* 104 */     Village village = null;
/* 105 */     double d0 = 3.4028234663852886E38D;
/*     */     
/* 107 */     for (Village village1 : this.villageList) {
/*     */       
/* 109 */       double d1 = village1.getCenter().distanceSq((Vec3i)doorBlock);
/*     */       
/* 111 */       if (d1 < d0) {
/*     */         
/* 113 */         float f = (radius + village1.getVillageRadius());
/*     */         
/* 115 */         if (d1 <= (f * f)) {
/*     */           
/* 117 */           village = village1;
/* 118 */           d0 = d1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     return village;
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropOldestVillagerPosition() {
/* 128 */     if (!this.villagerPositionsList.isEmpty())
/*     */     {
/* 130 */       addDoorsAround(this.villagerPositionsList.remove(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void addNewDoorsToVillageOrCreateVillage() {
/* 136 */     for (int i = 0; i < this.newDoors.size(); i++) {
/*     */       
/* 138 */       VillageDoorInfo villagedoorinfo = this.newDoors.get(i);
/* 139 */       Village village = getNearestVillage(villagedoorinfo.getDoorBlockPos(), 32);
/*     */       
/* 141 */       if (village == null) {
/*     */         
/* 143 */         village = new Village(this.worldObj);
/* 144 */         this.villageList.add(village);
/* 145 */         markDirty();
/*     */       } 
/*     */       
/* 148 */       village.addVillageDoorInfo(villagedoorinfo);
/*     */     } 
/*     */     
/* 151 */     this.newDoors.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addDoorsAround(BlockPos central) {
/* 156 */     int i = 16;
/* 157 */     int j = 4;
/* 158 */     int k = 16;
/*     */     
/* 160 */     for (int l = -i; l < i; l++) {
/*     */       
/* 162 */       for (int i1 = -j; i1 < j; i1++) {
/*     */         
/* 164 */         for (int j1 = -k; j1 < k; j1++) {
/*     */           
/* 166 */           BlockPos blockpos = central.add(l, i1, j1);
/*     */           
/* 168 */           if (isWoodDoor(blockpos)) {
/*     */             
/* 170 */             VillageDoorInfo villagedoorinfo = checkDoorExistence(blockpos);
/*     */             
/* 172 */             if (villagedoorinfo == null) {
/*     */               
/* 174 */               addToNewDoorsList(blockpos);
/*     */             }
/*     */             else {
/*     */               
/* 178 */               villagedoorinfo.func_179849_a(this.tickCounter);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private VillageDoorInfo checkDoorExistence(BlockPos doorBlock) {
/* 191 */     for (VillageDoorInfo villagedoorinfo : this.newDoors) {
/*     */       
/* 193 */       if (villagedoorinfo.getDoorBlockPos().getX() == doorBlock.getX() && villagedoorinfo.getDoorBlockPos().getZ() == doorBlock.getZ() && Math.abs(villagedoorinfo.getDoorBlockPos().getY() - doorBlock.getY()) <= 1)
/*     */       {
/* 195 */         return villagedoorinfo;
/*     */       }
/*     */     } 
/*     */     
/* 199 */     for (Village village : this.villageList) {
/*     */       
/* 201 */       VillageDoorInfo villagedoorinfo1 = village.getExistedDoor(doorBlock);
/*     */       
/* 203 */       if (villagedoorinfo1 != null)
/*     */       {
/* 205 */         return villagedoorinfo1;
/*     */       }
/*     */     } 
/*     */     
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToNewDoorsList(BlockPos doorBlock) {
/* 214 */     EnumFacing enumfacing = BlockDoor.getFacing((IBlockAccess)this.worldObj, doorBlock);
/* 215 */     EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 216 */     int i = countBlocksCanSeeSky(doorBlock, enumfacing, 5);
/* 217 */     int j = countBlocksCanSeeSky(doorBlock, enumfacing1, i + 1);
/*     */     
/* 219 */     if (i != j)
/*     */     {
/* 221 */       this.newDoors.add(new VillageDoorInfo(doorBlock, (i < j) ? enumfacing : enumfacing1, this.tickCounter));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int countBlocksCanSeeSky(BlockPos centerPos, EnumFacing direction, int limitation) {
/* 230 */     int i = 0;
/*     */     
/* 232 */     for (int j = 1; j <= 5; j++) {
/*     */       
/* 234 */       if (this.worldObj.canSeeSky(centerPos.offset(direction, j))) {
/*     */         
/* 236 */         i++;
/*     */         
/* 238 */         if (i >= limitation)
/*     */         {
/* 240 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean positionInList(BlockPos pos) {
/* 250 */     for (BlockPos blockpos : this.villagerPositionsList) {
/*     */       
/* 252 */       if (blockpos.equals(pos))
/*     */       {
/* 254 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 258 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWoodDoor(BlockPos doorPos) {
/* 263 */     Block block = this.worldObj.getBlockState(doorPos).getBlock();
/* 264 */     return (block instanceof BlockDoor) ? ((block.getMaterial() == Material.wood)) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 272 */     this.tickCounter = nbt.getInteger("Tick");
/* 273 */     NBTTagList nbttaglist = nbt.getTagList("Villages", 10);
/*     */     
/* 275 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 277 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 278 */       Village village = new Village();
/* 279 */       village.readVillageDataFromNBT(nbttagcompound);
/* 280 */       this.villageList.add(village);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 289 */     nbt.setInteger("Tick", this.tickCounter);
/* 290 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 292 */     for (Village village : this.villageList) {
/*     */       
/* 294 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 295 */       village.writeVillageDataToNBT(nbttagcompound);
/* 296 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 299 */     nbt.setTag("Villages", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fileNameForProvider(WorldProvider provider) {
/* 304 */     return "villages" + provider.getInternalNameSuffix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\village\VillageCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */