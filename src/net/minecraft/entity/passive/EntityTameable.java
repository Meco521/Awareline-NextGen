/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.ai.EntityAISit;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.management.PreYggdrasilConverter;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityTameable
/*     */   extends EntityAnimal implements IEntityOwnable {
/*  18 */   protected EntityAISit aiSit = new EntityAISit(this);
/*     */ 
/*     */   
/*     */   public EntityTameable(World worldIn) {
/*  22 */     super(worldIn);
/*  23 */     setupTamedAI();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  28 */     super.entityInit();
/*  29 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  30 */     this.dataWatcher.addObject(17, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  38 */     super.writeEntityToNBT(tagCompound);
/*     */     
/*  40 */     if (getOwnerId() == null) {
/*     */       
/*  42 */       tagCompound.setString("OwnerUUID", "");
/*     */     }
/*     */     else {
/*     */       
/*  46 */       tagCompound.setString("OwnerUUID", getOwnerId());
/*     */     } 
/*     */     
/*  49 */     tagCompound.setBoolean("Sitting", isSitting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  57 */     super.readEntityFromNBT(tagCompund);
/*  58 */     String s = "";
/*     */     
/*  60 */     if (tagCompund.hasKey("OwnerUUID", 8)) {
/*     */       
/*  62 */       s = tagCompund.getString("OwnerUUID");
/*     */     }
/*     */     else {
/*     */       
/*  66 */       String s1 = tagCompund.getString("Owner");
/*  67 */       s = PreYggdrasilConverter.getStringUUIDFromName(s1);
/*     */     } 
/*     */     
/*  70 */     if (!s.isEmpty()) {
/*     */       
/*  72 */       setOwnerId(s);
/*  73 */       setTamed(true);
/*     */     } 
/*     */     
/*  76 */     this.aiSit.setSitting(tagCompund.getBoolean("Sitting"));
/*  77 */     setSitting(tagCompund.getBoolean("Sitting"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playTameEffect(boolean play) {
/*  85 */     EnumParticleTypes enumparticletypes = EnumParticleTypes.HEART;
/*     */     
/*  87 */     if (!play)
/*     */     {
/*  89 */       enumparticletypes = EnumParticleTypes.SMOKE_NORMAL;
/*     */     }
/*     */     
/*  92 */     for (int i = 0; i < 7; i++) {
/*     */       
/*  94 */       double d0 = this.rand.nextGaussian() * 0.02D;
/*  95 */       double d1 = this.rand.nextGaussian() * 0.02D;
/*  96 */       double d2 = this.rand.nextGaussian() * 0.02D;
/*  97 */       this.worldObj.spawnParticle(enumparticletypes, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 103 */     if (id == 7) {
/*     */       
/* 105 */       playTameEffect(true);
/*     */     }
/* 107 */     else if (id == 6) {
/*     */       
/* 109 */       playTameEffect(false);
/*     */     }
/*     */     else {
/*     */       
/* 113 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTamed() {
/* 119 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTamed(boolean tamed) {
/* 124 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 126 */     if (tamed) {
/*     */       
/* 128 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x4)));
/*     */     }
/*     */     else {
/*     */       
/* 132 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFB)));
/*     */     } 
/*     */     
/* 135 */     setupTamedAI();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupTamedAI() {}
/*     */ 
/*     */   
/*     */   public boolean isSitting() {
/* 144 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSitting(boolean sitting) {
/* 149 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 151 */     if (sitting) {
/*     */       
/* 153 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 157 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOwnerId() {
/* 163 */     return this.dataWatcher.getWatchableObjectString(17);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwnerId(String ownerUuid) {
/* 168 */     this.dataWatcher.updateObject(17, ownerUuid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase getOwner() {
/*     */     try {
/* 175 */       UUID uuid = UUID.fromString(getOwnerId());
/* 176 */       return (uuid == null) ? null : (EntityLivingBase)this.worldObj.getPlayerEntityByUUID(uuid);
/*     */     }
/* 178 */     catch (IllegalArgumentException var2) {
/*     */       
/* 180 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwner(EntityLivingBase entityIn) {
/* 186 */     return (entityIn == getOwner());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAISit getAISit() {
/* 194 */     return this.aiSit;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Team getTeam() {
/* 204 */     if (isTamed()) {
/*     */       
/* 206 */       EntityLivingBase entitylivingbase = getOwner();
/*     */       
/* 208 */       if (entitylivingbase != null)
/*     */       {
/* 210 */         return entitylivingbase.getTeam();
/*     */       }
/*     */     } 
/*     */     
/* 214 */     return super.getTeam();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity) {
/* 219 */     if (isTamed()) {
/*     */       
/* 221 */       EntityLivingBase entitylivingbase = getOwner();
/*     */       
/* 223 */       if (otherEntity == entitylivingbase)
/*     */       {
/* 225 */         return true;
/*     */       }
/*     */       
/* 228 */       if (entitylivingbase != null)
/*     */       {
/* 230 */         return entitylivingbase.isOnSameTeam(otherEntity);
/*     */       }
/*     */     } 
/*     */     
/* 234 */     return super.isOnSameTeam(otherEntity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 242 */     if (!this.worldObj.isRemote && this.worldObj.getGameRules().getBoolean("showDeathMessages") && hasCustomName() && getOwner() instanceof EntityPlayerMP)
/*     */     {
/* 244 */       ((EntityPlayerMP)getOwner()).addChatMessage(getCombatTracker().getDeathMessage());
/*     */     }
/*     */     
/* 247 */     super.onDeath(cause);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityTameable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */