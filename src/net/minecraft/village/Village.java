/*     */ package net.minecraft.village;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.PlayerProfileCache;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class Village {
/*  27 */   private final List<VillageDoorInfo> villageDoorInfoList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private World worldObj;
/*     */ 
/*     */   
/*  33 */   private BlockPos centerHelper = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*  36 */   private BlockPos center = BlockPos.ORIGIN;
/*     */   
/*     */   private int villageRadius;
/*     */   
/*     */   private int lastAddDoorTimestamp;
/*     */   private int tickCounter;
/*     */   private int numVillagers;
/*     */   private int noBreedTicks;
/*  44 */   private final TreeMap<String, Integer> playerReputation = new TreeMap<>();
/*  45 */   private final List<VillageAggressor> villageAgressors = Lists.newArrayList();
/*     */   
/*     */   private int numIronGolems;
/*     */ 
/*     */   
/*     */   public Village() {}
/*     */ 
/*     */   
/*     */   public Village(World worldIn) {
/*  54 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorld(World worldIn) {
/*  59 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(int p_75560_1_) {
/*  67 */     this.tickCounter = p_75560_1_;
/*  68 */     removeDeadAndOutOfRangeDoors();
/*  69 */     removeDeadAndOldAgressors();
/*     */     
/*  71 */     if (p_75560_1_ % 20 == 0)
/*     */     {
/*  73 */       updateNumVillagers();
/*     */     }
/*     */     
/*  76 */     if (p_75560_1_ % 30 == 0)
/*     */     {
/*  78 */       updateNumIronGolems();
/*     */     }
/*     */     
/*  81 */     int i = this.numVillagers / 10;
/*     */     
/*  83 */     if (this.numIronGolems < i && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
/*     */       
/*  85 */       Vec3 vec3 = func_179862_a(this.center, 2, 4, 2);
/*     */       
/*  87 */       if (vec3 != null) {
/*     */         
/*  89 */         EntityIronGolem entityirongolem = new EntityIronGolem(this.worldObj);
/*  90 */         entityirongolem.setPosition(vec3.xCoord, vec3.yCoord, vec3.zCoord);
/*  91 */         this.worldObj.spawnEntityInWorld((Entity)entityirongolem);
/*  92 */         this.numIronGolems++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Vec3 func_179862_a(BlockPos p_179862_1_, int p_179862_2_, int p_179862_3_, int p_179862_4_) {
/*  99 */     for (int i = 0; i < 10; i++) {
/*     */       
/* 101 */       BlockPos blockpos = p_179862_1_.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
/*     */       
/* 103 */       if (func_179866_a(blockpos) && func_179861_a(new BlockPos(p_179862_2_, p_179862_3_, p_179862_4_), blockpos))
/*     */       {
/* 105 */         return new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */       }
/*     */     } 
/*     */     
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_179861_a(BlockPos p_179861_1_, BlockPos p_179861_2_) {
/* 114 */     if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, p_179861_2_.down()))
/*     */     {
/* 116 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 120 */     int i = p_179861_2_.getX() - p_179861_1_.getX() / 2;
/* 121 */     int j = p_179861_2_.getZ() - p_179861_1_.getZ() / 2;
/*     */     
/* 123 */     for (int k = i; k < i + p_179861_1_.getX(); k++) {
/*     */       
/* 125 */       for (int l = p_179861_2_.getY(); l < p_179861_2_.getY() + p_179861_1_.getY(); l++) {
/*     */         
/* 127 */         for (int i1 = j; i1 < j + p_179861_1_.getZ(); i1++) {
/*     */           
/* 129 */           if (this.worldObj.getBlockState(new BlockPos(k, l, i1)).getBlock().isNormalCube())
/*     */           {
/* 131 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateNumIronGolems() {
/* 143 */     List<EntityIronGolem> list = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, new AxisAlignedBB((this.center.getX() - this.villageRadius), (this.center.getY() - 4), (this.center.getZ() - this.villageRadius), (this.center.getX() + this.villageRadius), (this.center.getY() + 4), (this.center.getZ() + this.villageRadius)));
/* 144 */     this.numIronGolems = list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateNumVillagers() {
/* 149 */     List<EntityVillager> list = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB((this.center.getX() - this.villageRadius), (this.center.getY() - 4), (this.center.getZ() - this.villageRadius), (this.center.getX() + this.villageRadius), (this.center.getY() + 4), (this.center.getZ() + this.villageRadius)));
/* 150 */     this.numVillagers = list.size();
/*     */     
/* 152 */     if (this.numVillagers == 0)
/*     */     {
/* 154 */       this.playerReputation.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getCenter() {
/* 160 */     return this.center;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVillageRadius() {
/* 165 */     return this.villageRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumVillageDoors() {
/* 174 */     return this.villageDoorInfoList.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTicksSinceLastDoorAdding() {
/* 179 */     return this.tickCounter - this.lastAddDoorTimestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumVillagers() {
/* 184 */     return this.numVillagers;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_179866_a(BlockPos pos) {
/* 189 */     return (this.center.distanceSq((Vec3i)pos) < (this.villageRadius * this.villageRadius));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VillageDoorInfo> getVillageDoorInfoList() {
/* 194 */     return this.villageDoorInfoList;
/*     */   }
/*     */ 
/*     */   
/*     */   public VillageDoorInfo getNearestDoor(BlockPos pos) {
/* 199 */     VillageDoorInfo villagedoorinfo = null;
/* 200 */     int i = Integer.MAX_VALUE;
/*     */     
/* 202 */     for (VillageDoorInfo villagedoorinfo1 : this.villageDoorInfoList) {
/*     */       
/* 204 */       int j = villagedoorinfo1.getDistanceToDoorBlockSq(pos);
/*     */       
/* 206 */       if (j < i) {
/*     */         
/* 208 */         villagedoorinfo = villagedoorinfo1;
/* 209 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     return villagedoorinfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VillageDoorInfo getDoorInfo(BlockPos pos) {
/* 221 */     VillageDoorInfo villagedoorinfo = null;
/* 222 */     int i = Integer.MAX_VALUE;
/*     */     
/* 224 */     for (VillageDoorInfo villagedoorinfo1 : this.villageDoorInfoList) {
/*     */       
/* 226 */       int j = villagedoorinfo1.getDistanceToDoorBlockSq(pos);
/*     */       
/* 228 */       if (j > 256) {
/*     */         
/* 230 */         j *= 1000;
/*     */       }
/*     */       else {
/*     */         
/* 234 */         j = villagedoorinfo1.getDoorOpeningRestrictionCounter();
/*     */       } 
/*     */       
/* 237 */       if (j < i) {
/*     */         
/* 239 */         villagedoorinfo = villagedoorinfo1;
/* 240 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     return villagedoorinfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VillageDoorInfo getExistedDoor(BlockPos doorBlock) {
/* 252 */     if (this.center.distanceSq((Vec3i)doorBlock) > (this.villageRadius * this.villageRadius))
/*     */     {
/* 254 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 258 */     for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList) {
/*     */       
/* 260 */       if (villagedoorinfo.getDoorBlockPos().getX() == doorBlock.getX() && villagedoorinfo.getDoorBlockPos().getZ() == doorBlock.getZ() && Math.abs(villagedoorinfo.getDoorBlockPos().getY() - doorBlock.getY()) <= 1)
/*     */       {
/* 262 */         return villagedoorinfo;
/*     */       }
/*     */     } 
/*     */     
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVillageDoorInfo(VillageDoorInfo doorInfo) {
/* 272 */     this.villageDoorInfoList.add(doorInfo);
/* 273 */     this.centerHelper = this.centerHelper.add((Vec3i)doorInfo.getDoorBlockPos());
/* 274 */     updateVillageRadiusAndCenter();
/* 275 */     this.lastAddDoorTimestamp = doorInfo.getInsidePosY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnnihilated() {
/* 283 */     return this.villageDoorInfoList.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOrRenewAgressor(EntityLivingBase entitylivingbaseIn) {
/* 288 */     for (VillageAggressor village$villageaggressor : this.villageAgressors) {
/*     */       
/* 290 */       if (village$villageaggressor.agressor == entitylivingbaseIn) {
/*     */         
/* 292 */         village$villageaggressor.agressionTime = this.tickCounter;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 297 */     this.villageAgressors.add(new VillageAggressor(entitylivingbaseIn, this.tickCounter));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLivingBase findNearestVillageAggressor(EntityLivingBase entitylivingbaseIn) {
/* 302 */     double d0 = Double.MAX_VALUE;
/* 303 */     VillageAggressor village$villageaggressor = null;
/*     */     
/* 305 */     for (int i = 0; i < this.villageAgressors.size(); i++) {
/*     */       
/* 307 */       VillageAggressor village$villageaggressor1 = this.villageAgressors.get(i);
/* 308 */       double d1 = village$villageaggressor1.agressor.getDistanceSqToEntity((Entity)entitylivingbaseIn);
/*     */       
/* 310 */       if (d1 <= d0) {
/*     */         
/* 312 */         village$villageaggressor = village$villageaggressor1;
/* 313 */         d0 = d1;
/*     */       } 
/*     */     } 
/*     */     
/* 317 */     return (village$villageaggressor != null) ? village$villageaggressor.agressor : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer getNearestTargetPlayer(EntityLivingBase villageDefender) {
/* 322 */     double d0 = Double.MAX_VALUE;
/* 323 */     EntityPlayer entityplayer = null;
/*     */     
/* 325 */     for (String s : this.playerReputation.keySet()) {
/*     */       
/* 327 */       if (isPlayerReputationTooLow(s)) {
/*     */         
/* 329 */         EntityPlayer entityplayer1 = this.worldObj.getPlayerEntityByName(s);
/*     */         
/* 331 */         if (entityplayer1 != null) {
/*     */           
/* 333 */           double d1 = entityplayer1.getDistanceSqToEntity((Entity)villageDefender);
/*     */           
/* 335 */           if (d1 <= d0) {
/*     */             
/* 337 */             entityplayer = entityplayer1;
/* 338 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     return entityplayer;
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeDeadAndOldAgressors() {
/* 349 */     Iterator<VillageAggressor> iterator = this.villageAgressors.iterator();
/*     */     
/* 351 */     while (iterator.hasNext()) {
/*     */       
/* 353 */       VillageAggressor village$villageaggressor = iterator.next();
/*     */       
/* 355 */       if (!village$villageaggressor.agressor.isEntityAlive() || Math.abs(this.tickCounter - village$villageaggressor.agressionTime) > 300)
/*     */       {
/* 357 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeDeadAndOutOfRangeDoors() {
/* 364 */     boolean flag = false;
/* 365 */     boolean flag1 = (this.worldObj.rand.nextInt(50) == 0);
/* 366 */     Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
/*     */     
/* 368 */     while (iterator.hasNext()) {
/*     */       
/* 370 */       VillageDoorInfo villagedoorinfo = iterator.next();
/*     */       
/* 372 */       if (flag1)
/*     */       {
/* 374 */         villagedoorinfo.resetDoorOpeningRestrictionCounter();
/*     */       }
/*     */       
/* 377 */       if (!isWoodDoor(villagedoorinfo.getDoorBlockPos()) || Math.abs(this.tickCounter - villagedoorinfo.getInsidePosY()) > 1200) {
/*     */         
/* 379 */         this.centerHelper = this.centerHelper.subtract((Vec3i)villagedoorinfo.getDoorBlockPos());
/* 380 */         flag = true;
/* 381 */         villagedoorinfo.setIsDetachedFromVillageFlag(true);
/* 382 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 386 */     if (flag)
/*     */     {
/* 388 */       updateVillageRadiusAndCenter();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWoodDoor(BlockPos pos) {
/* 394 */     Block block = this.worldObj.getBlockState(pos).getBlock();
/* 395 */     return (block instanceof net.minecraft.block.BlockDoor) ? ((block.getMaterial() == Material.wood)) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateVillageRadiusAndCenter() {
/* 400 */     int i = this.villageDoorInfoList.size();
/*     */     
/* 402 */     if (i == 0) {
/*     */       
/* 404 */       this.center = new BlockPos(0, 0, 0);
/* 405 */       this.villageRadius = 0;
/*     */     }
/*     */     else {
/*     */       
/* 409 */       this.center = new BlockPos(this.centerHelper.getX() / i, this.centerHelper.getY() / i, this.centerHelper.getZ() / i);
/* 410 */       int j = 0;
/*     */       
/* 412 */       for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList)
/*     */       {
/* 414 */         j = Math.max(villagedoorinfo.getDistanceToDoorBlockSq(this.center), j);
/*     */       }
/*     */       
/* 417 */       this.villageRadius = Math.max(32, (int)Math.sqrt(j) + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReputationForPlayer(String p_82684_1_) {
/* 426 */     Integer integer = this.playerReputation.get(p_82684_1_);
/* 427 */     return (integer != null) ? integer.intValue() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setReputationForPlayer(String p_82688_1_, int p_82688_2_) {
/* 435 */     int i = getReputationForPlayer(p_82688_1_);
/* 436 */     int j = MathHelper.clamp_int(i + p_82688_2_, -30, 10);
/* 437 */     this.playerReputation.put(p_82688_1_, Integer.valueOf(j));
/* 438 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerReputationTooLow(String p_82687_1_) {
/* 446 */     return (getReputationForPlayer(p_82687_1_) <= -15);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readVillageDataFromNBT(NBTTagCompound compound) {
/* 454 */     this.numVillagers = compound.getInteger("PopSize");
/* 455 */     this.villageRadius = compound.getInteger("Radius");
/* 456 */     this.numIronGolems = compound.getInteger("Golems");
/* 457 */     this.lastAddDoorTimestamp = compound.getInteger("Stable");
/* 458 */     this.tickCounter = compound.getInteger("Tick");
/* 459 */     this.noBreedTicks = compound.getInteger("MTick");
/* 460 */     this.center = new BlockPos(compound.getInteger("CX"), compound.getInteger("CY"), compound.getInteger("CZ"));
/* 461 */     this.centerHelper = new BlockPos(compound.getInteger("ACX"), compound.getInteger("ACY"), compound.getInteger("ACZ"));
/* 462 */     NBTTagList nbttaglist = compound.getTagList("Doors", 10);
/*     */     
/* 464 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 466 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 467 */       VillageDoorInfo villagedoorinfo = new VillageDoorInfo(new BlockPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Y"), nbttagcompound.getInteger("Z")), nbttagcompound.getInteger("IDX"), nbttagcompound.getInteger("IDZ"), nbttagcompound.getInteger("TS"));
/* 468 */       this.villageDoorInfoList.add(villagedoorinfo);
/*     */     } 
/*     */     
/* 471 */     NBTTagList nbttaglist1 = compound.getTagList("Players", 10);
/*     */     
/* 473 */     for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/*     */       
/* 475 */       NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(j);
/*     */       
/* 477 */       if (nbttagcompound1.hasKey("UUID")) {
/*     */         
/* 479 */         PlayerProfileCache playerprofilecache = MinecraftServer.getServer().getPlayerProfileCache();
/* 480 */         GameProfile gameprofile = playerprofilecache.getProfileByUUID(UUID.fromString(nbttagcompound1.getString("UUID")));
/*     */         
/* 482 */         if (gameprofile != null)
/*     */         {
/* 484 */           this.playerReputation.put(gameprofile.getName(), Integer.valueOf(nbttagcompound1.getInteger("S")));
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 489 */         this.playerReputation.put(nbttagcompound1.getString("Name"), Integer.valueOf(nbttagcompound1.getInteger("S")));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeVillageDataToNBT(NBTTagCompound compound) {
/* 499 */     compound.setInteger("PopSize", this.numVillagers);
/* 500 */     compound.setInteger("Radius", this.villageRadius);
/* 501 */     compound.setInteger("Golems", this.numIronGolems);
/* 502 */     compound.setInteger("Stable", this.lastAddDoorTimestamp);
/* 503 */     compound.setInteger("Tick", this.tickCounter);
/* 504 */     compound.setInteger("MTick", this.noBreedTicks);
/* 505 */     compound.setInteger("CX", this.center.getX());
/* 506 */     compound.setInteger("CY", this.center.getY());
/* 507 */     compound.setInteger("CZ", this.center.getZ());
/* 508 */     compound.setInteger("ACX", this.centerHelper.getX());
/* 509 */     compound.setInteger("ACY", this.centerHelper.getY());
/* 510 */     compound.setInteger("ACZ", this.centerHelper.getZ());
/* 511 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 513 */     for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList) {
/*     */       
/* 515 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 516 */       nbttagcompound.setInteger("X", villagedoorinfo.getDoorBlockPos().getX());
/* 517 */       nbttagcompound.setInteger("Y", villagedoorinfo.getDoorBlockPos().getY());
/* 518 */       nbttagcompound.setInteger("Z", villagedoorinfo.getDoorBlockPos().getZ());
/* 519 */       nbttagcompound.setInteger("IDX", villagedoorinfo.getInsideOffsetX());
/* 520 */       nbttagcompound.setInteger("IDZ", villagedoorinfo.getInsideOffsetZ());
/* 521 */       nbttagcompound.setInteger("TS", villagedoorinfo.getInsidePosY());
/* 522 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 525 */     compound.setTag("Doors", (NBTBase)nbttaglist);
/* 526 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 528 */     for (Map.Entry<String, Integer> entry : this.playerReputation.entrySet()) {
/*     */       
/* 530 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 531 */       PlayerProfileCache playerprofilecache = MinecraftServer.getServer().getPlayerProfileCache();
/* 532 */       GameProfile gameprofile = playerprofilecache.getGameProfileForUsername(entry.getKey());
/*     */       
/* 534 */       if (gameprofile != null) {
/*     */         
/* 536 */         nbttagcompound1.setString("UUID", gameprofile.getId().toString());
/* 537 */         nbttagcompound1.setInteger("S", ((Integer)entry.getValue()).intValue());
/* 538 */         nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 542 */     compound.setTag("Players", (NBTBase)nbttaglist1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endMatingSeason() {
/* 550 */     this.noBreedTicks = this.tickCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMatingSeason() {
/* 558 */     return (this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultPlayerReputation(int p_82683_1_) {
/* 563 */     for (String s : this.playerReputation.keySet())
/*     */     {
/* 565 */       setReputationForPlayer(s, p_82683_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class VillageAggressor
/*     */   {
/*     */     public EntityLivingBase agressor;
/*     */     public int agressionTime;
/*     */     
/*     */     VillageAggressor(EntityLivingBase p_i1674_2_, int p_i1674_3_) {
/* 576 */       this.agressor = p_i1674_2_;
/* 577 */       this.agressionTime = p_i1674_3_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\village\Village.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */