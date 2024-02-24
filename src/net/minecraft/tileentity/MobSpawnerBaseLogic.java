/*     */ package net.minecraft.tileentity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class MobSpawnerBaseLogic {
/*  17 */   private int spawnDelay = 20;
/*  18 */   private String mobID = "Pig";
/*  19 */   private final List<WeightedRandomMinecart> minecartToSpawn = Lists.newArrayList();
/*     */   
/*     */   private WeightedRandomMinecart randomEntity;
/*     */   
/*     */   private double mobRotation;
/*     */   
/*     */   private double prevMobRotation;
/*     */   
/*  27 */   private int minSpawnDelay = 200;
/*  28 */   private int maxSpawnDelay = 800;
/*  29 */   private int spawnCount = 4;
/*     */   
/*     */   private Entity cachedEntity;
/*     */   
/*  33 */   private int maxNearbyEntities = 6;
/*     */ 
/*     */   
/*  36 */   private int activatingRangeFromPlayer = 16;
/*     */ 
/*     */   
/*  39 */   private int spawnRange = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getEntityNameToSpawn() {
/*  46 */     if (this.randomEntity == null) {
/*     */       
/*  48 */       if ("Minecart".equals(this.mobID))
/*     */       {
/*  50 */         this.mobID = "MinecartRideable";
/*     */       }
/*     */       
/*  53 */       return this.mobID;
/*     */     } 
/*     */ 
/*     */     
/*  57 */     return this.randomEntity.entityType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityName(String name) {
/*  63 */     this.mobID = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isActivated() {
/*  71 */     BlockPos blockpos = getSpawnerPosition();
/*  72 */     return getSpawnerWorld().isAnyPlayerWithinRangeAt(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D, this.activatingRangeFromPlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSpawner() {
/*  77 */     if (isActivated()) {
/*     */       
/*  79 */       BlockPos blockpos = getSpawnerPosition();
/*     */       
/*  81 */       if ((getSpawnerWorld()).isRemote) {
/*     */         
/*  83 */         double d3 = (blockpos.getX() + (getSpawnerWorld()).rand.nextFloat());
/*  84 */         double d4 = (blockpos.getY() + (getSpawnerWorld()).rand.nextFloat());
/*  85 */         double d5 = (blockpos.getZ() + (getSpawnerWorld()).rand.nextFloat());
/*  86 */         getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
/*  87 */         getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         
/*  89 */         if (this.spawnDelay > 0)
/*     */         {
/*  91 */           this.spawnDelay--;
/*     */         }
/*     */         
/*  94 */         this.prevMobRotation = this.mobRotation;
/*  95 */         this.mobRotation = (this.mobRotation + (1000.0F / (this.spawnDelay + 200.0F))) % 360.0D;
/*     */       }
/*     */       else {
/*     */         
/*  99 */         if (this.spawnDelay == -1)
/*     */         {
/* 101 */           resetTimer();
/*     */         }
/*     */         
/* 104 */         if (this.spawnDelay > 0) {
/*     */           
/* 106 */           this.spawnDelay--;
/*     */           
/*     */           return;
/*     */         } 
/* 110 */         boolean flag = false;
/*     */         
/* 112 */         for (int i = 0; i < this.spawnCount; i++) {
/*     */           
/* 114 */           Entity entity = EntityList.createEntityByName(getEntityNameToSpawn(), getSpawnerWorld());
/*     */           
/* 116 */           if (entity == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 121 */           int j = getSpawnerWorld().getEntitiesWithinAABB(entity.getClass(), (new AxisAlignedBB(blockpos.getX(), blockpos.getY(), blockpos.getZ(), (blockpos.getX() + 1), (blockpos.getY() + 1), (blockpos.getZ() + 1))).expand(this.spawnRange, this.spawnRange, this.spawnRange)).size();
/*     */           
/* 123 */           if (j >= this.maxNearbyEntities) {
/*     */             
/* 125 */             resetTimer();
/*     */             
/*     */             return;
/*     */           } 
/* 129 */           double d0 = blockpos.getX() + ((getSpawnerWorld()).rand.nextDouble() - (getSpawnerWorld()).rand.nextDouble()) * this.spawnRange + 0.5D;
/* 130 */           double d1 = (blockpos.getY() + (getSpawnerWorld()).rand.nextInt(3) - 1);
/* 131 */           double d2 = blockpos.getZ() + ((getSpawnerWorld()).rand.nextDouble() - (getSpawnerWorld()).rand.nextDouble()) * this.spawnRange + 0.5D;
/* 132 */           EntityLiving entityliving = (entity instanceof EntityLiving) ? (EntityLiving)entity : null;
/* 133 */           entity.setLocationAndAngles(d0, d1, d2, (getSpawnerWorld()).rand.nextFloat() * 360.0F, 0.0F);
/*     */           
/* 135 */           if (entityliving == null || (entityliving.getCanSpawnHere() && entityliving.isNotColliding())) {
/*     */             
/* 137 */             spawnNewEntity(entity, true);
/* 138 */             getSpawnerWorld().playAuxSFX(2004, blockpos, 0);
/*     */             
/* 140 */             if (entityliving != null)
/*     */             {
/* 142 */               entityliving.spawnExplosionParticle();
/*     */             }
/*     */             
/* 145 */             flag = true;
/*     */           } 
/*     */         } 
/*     */         
/* 149 */         if (flag)
/*     */         {
/* 151 */           resetTimer();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Entity spawnNewEntity(Entity entityIn, boolean spawn) {
/* 159 */     if (this.randomEntity != null) {
/*     */       
/* 161 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 162 */       entityIn.writeToNBTOptional(nbttagcompound);
/*     */       
/* 164 */       for (String s : this.randomEntity.nbtData.getKeySet()) {
/*     */         
/* 166 */         NBTBase nbtbase = this.randomEntity.nbtData.getTag(s);
/* 167 */         nbttagcompound.setTag(s, nbtbase.copy());
/*     */       } 
/*     */       
/* 170 */       entityIn.readFromNBT(nbttagcompound);
/*     */       
/* 172 */       if (entityIn.worldObj != null && spawn)
/*     */       {
/* 174 */         entityIn.worldObj.spawnEntityInWorld(entityIn);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 179 */       for (Entity entity = entityIn; nbttagcompound.hasKey("Riding", 10); nbttagcompound = nbttagcompound2)
/*     */       {
/* 181 */         NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Riding");
/* 182 */         Entity entity1 = EntityList.createEntityByName(nbttagcompound2.getString("id"), entityIn.worldObj);
/*     */         
/* 184 */         if (entity1 != null) {
/*     */           
/* 186 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 187 */           entity1.writeToNBTOptional(nbttagcompound1);
/*     */           
/* 189 */           for (String s1 : nbttagcompound2.getKeySet()) {
/*     */             
/* 191 */             NBTBase nbtbase1 = nbttagcompound2.getTag(s1);
/* 192 */             nbttagcompound1.setTag(s1, nbtbase1.copy());
/*     */           } 
/*     */           
/* 195 */           entity1.readFromNBT(nbttagcompound1);
/* 196 */           entity1.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*     */           
/* 198 */           if (entityIn.worldObj != null && spawn)
/*     */           {
/* 200 */             entityIn.worldObj.spawnEntityInWorld(entity1);
/*     */           }
/*     */           
/* 203 */           entity.mountEntity(entity1);
/*     */         } 
/*     */         
/* 206 */         entity = entity1;
/*     */       }
/*     */     
/* 209 */     } else if (entityIn instanceof net.minecraft.entity.EntityLivingBase && entityIn.worldObj != null && spawn) {
/*     */       
/* 211 */       if (entityIn instanceof EntityLiving)
/*     */       {
/* 213 */         ((EntityLiving)entityIn).onInitialSpawn(entityIn.worldObj.getDifficultyForLocation(new BlockPos(entityIn)), (IEntityLivingData)null);
/*     */       }
/*     */       
/* 216 */       entityIn.worldObj.spawnEntityInWorld(entityIn);
/*     */     } 
/*     */     
/* 219 */     return entityIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetTimer() {
/* 224 */     if (this.maxSpawnDelay <= this.minSpawnDelay) {
/*     */       
/* 226 */       this.spawnDelay = this.minSpawnDelay;
/*     */     }
/*     */     else {
/*     */       
/* 230 */       int i = this.maxSpawnDelay - this.minSpawnDelay;
/* 231 */       this.spawnDelay = this.minSpawnDelay + (getSpawnerWorld()).rand.nextInt(i);
/*     */     } 
/*     */     
/* 234 */     if (!this.minecartToSpawn.isEmpty())
/*     */     {
/* 236 */       setRandomEntity((WeightedRandomMinecart)WeightedRandom.getRandomItem((getSpawnerWorld()).rand, this.minecartToSpawn));
/*     */     }
/*     */     
/* 239 */     func_98267_a(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 244 */     this.mobID = nbt.getString("EntityId");
/* 245 */     this.spawnDelay = nbt.getShort("Delay");
/* 246 */     this.minecartToSpawn.clear();
/*     */     
/* 248 */     if (nbt.hasKey("SpawnPotentials", 9)) {
/*     */       
/* 250 */       NBTTagList nbttaglist = nbt.getTagList("SpawnPotentials", 10);
/*     */       
/* 252 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */       {
/* 254 */         this.minecartToSpawn.add(new WeightedRandomMinecart(nbttaglist.getCompoundTagAt(i)));
/*     */       }
/*     */     } 
/*     */     
/* 258 */     if (nbt.hasKey("SpawnData", 10)) {
/*     */       
/* 260 */       setRandomEntity(new WeightedRandomMinecart(nbt.getCompoundTag("SpawnData"), this.mobID));
/*     */     }
/*     */     else {
/*     */       
/* 264 */       setRandomEntity((WeightedRandomMinecart)null);
/*     */     } 
/*     */     
/* 267 */     if (nbt.hasKey("MinSpawnDelay", 99)) {
/*     */       
/* 269 */       this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
/* 270 */       this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
/* 271 */       this.spawnCount = nbt.getShort("SpawnCount");
/*     */     } 
/*     */     
/* 274 */     if (nbt.hasKey("MaxNearbyEntities", 99)) {
/*     */       
/* 276 */       this.maxNearbyEntities = nbt.getShort("MaxNearbyEntities");
/* 277 */       this.activatingRangeFromPlayer = nbt.getShort("RequiredPlayerRange");
/*     */     } 
/*     */     
/* 280 */     if (nbt.hasKey("SpawnRange", 99))
/*     */     {
/* 282 */       this.spawnRange = nbt.getShort("SpawnRange");
/*     */     }
/*     */     
/* 285 */     if (getSpawnerWorld() != null)
/*     */     {
/* 287 */       this.cachedEntity = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 293 */     String s = getEntityNameToSpawn();
/*     */     
/* 295 */     if (!StringUtils.isNullOrEmpty(s)) {
/*     */       
/* 297 */       nbt.setString("EntityId", s);
/* 298 */       nbt.setShort("Delay", (short)this.spawnDelay);
/* 299 */       nbt.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
/* 300 */       nbt.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
/* 301 */       nbt.setShort("SpawnCount", (short)this.spawnCount);
/* 302 */       nbt.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
/* 303 */       nbt.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
/* 304 */       nbt.setShort("SpawnRange", (short)this.spawnRange);
/*     */       
/* 306 */       if (this.randomEntity != null)
/*     */       {
/* 308 */         nbt.setTag("SpawnData", this.randomEntity.nbtData.copy());
/*     */       }
/*     */       
/* 311 */       if (this.randomEntity != null || !this.minecartToSpawn.isEmpty()) {
/*     */         
/* 313 */         NBTTagList nbttaglist = new NBTTagList();
/*     */         
/* 315 */         if (!this.minecartToSpawn.isEmpty()) {
/*     */           
/* 317 */           for (WeightedRandomMinecart mobspawnerbaselogic$weightedrandomminecart : this.minecartToSpawn)
/*     */           {
/* 319 */             nbttaglist.appendTag((NBTBase)mobspawnerbaselogic$weightedrandomminecart.toNBT());
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 324 */           nbttaglist.appendTag((NBTBase)this.randomEntity.toNBT());
/*     */         } 
/*     */         
/* 327 */         nbt.setTag("SpawnPotentials", (NBTBase)nbttaglist);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity func_180612_a(World worldIn) {
/* 334 */     if (this.cachedEntity == null) {
/*     */       
/* 336 */       Entity entity = EntityList.createEntityByName(getEntityNameToSpawn(), worldIn);
/*     */       
/* 338 */       if (entity != null) {
/*     */         
/* 340 */         entity = spawnNewEntity(entity, false);
/* 341 */         this.cachedEntity = entity;
/*     */       } 
/*     */     } 
/*     */     
/* 345 */     return this.cachedEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setDelayToMin(int delay) {
/* 353 */     if (delay == 1 && (getSpawnerWorld()).isRemote) {
/*     */       
/* 355 */       this.spawnDelay = this.minSpawnDelay;
/* 356 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 360 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private WeightedRandomMinecart getRandomEntity() {
/* 366 */     return this.randomEntity;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRandomEntity(WeightedRandomMinecart p_98277_1_) {
/* 371 */     this.randomEntity = p_98277_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void func_98267_a(int paramInt);
/*     */   
/*     */   public abstract World getSpawnerWorld();
/*     */   
/*     */   public abstract BlockPos getSpawnerPosition();
/*     */   
/*     */   public double getMobRotation() {
/* 382 */     return this.mobRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getPrevMobRotation() {
/* 387 */     return this.prevMobRotation;
/*     */   }
/*     */   
/*     */   public static class WeightedRandomMinecart
/*     */     extends WeightedRandom.Item
/*     */   {
/*     */     final NBTTagCompound nbtData;
/*     */     final String entityType;
/*     */     
/*     */     public WeightedRandomMinecart(NBTTagCompound tagCompound) {
/* 397 */       this(tagCompound.getCompoundTag("Properties"), tagCompound.getString("Type"), tagCompound.getInteger("Weight"));
/*     */     }
/*     */ 
/*     */     
/*     */     public WeightedRandomMinecart(NBTTagCompound tagCompound, String type) {
/* 402 */       this(tagCompound, type, 1);
/*     */     }
/*     */ 
/*     */     
/*     */     private WeightedRandomMinecart(NBTTagCompound tagCompound, String type, int weight) {
/* 407 */       super(weight);
/*     */       
/* 409 */       if (type.equals("Minecart"))
/*     */       {
/* 411 */         if (tagCompound != null) {
/*     */           
/* 413 */           type = EntityMinecart.EnumMinecartType.byNetworkID(tagCompound.getInteger("Type")).getName();
/*     */         }
/*     */         else {
/*     */           
/* 417 */           type = "MinecartRideable";
/*     */         } 
/*     */       }
/*     */       
/* 421 */       this.nbtData = tagCompound;
/* 422 */       this.entityType = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public NBTTagCompound toNBT() {
/* 427 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 428 */       nbttagcompound.setTag("Properties", (NBTBase)this.nbtData);
/* 429 */       nbttagcompound.setString("Type", this.entityType);
/* 430 */       nbttagcompound.setInteger("Weight", this.itemWeight);
/* 431 */       return nbttagcompound;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\MobSpawnerBaseLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */