/*     */ package net.minecraft.village;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.SpawnerAnimals;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class VillageSiege
/*     */ {
/*     */   private final World worldObj;
/*     */   private boolean field_75535_b;
/*  20 */   private int field_75536_c = -1;
/*     */   
/*     */   private int field_75533_d;
/*     */   
/*     */   private int field_75534_e;
/*     */   
/*     */   private Village theVillage;
/*     */   private int field_75532_g;
/*     */   private int field_75538_h;
/*     */   private int field_75539_i;
/*     */   
/*     */   public VillageSiege(World worldIn) {
/*  32 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  40 */     if (this.worldObj.isDaytime()) {
/*     */       
/*  42 */       this.field_75536_c = 0;
/*     */     }
/*  44 */     else if (this.field_75536_c != 2) {
/*     */       
/*  46 */       if (this.field_75536_c == 0) {
/*     */         
/*  48 */         float f = this.worldObj.getCelestialAngle(0.0F);
/*     */         
/*  50 */         if (f < 0.5D || f > 0.501D) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  55 */         this.field_75536_c = (this.worldObj.rand.nextInt(10) == 0) ? 1 : 2;
/*  56 */         this.field_75535_b = false;
/*     */         
/*  58 */         if (this.field_75536_c == 2) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  64 */       if (this.field_75536_c != -1) {
/*     */         
/*  66 */         if (!this.field_75535_b) {
/*     */           
/*  68 */           if (!func_75529_b()) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  73 */           this.field_75535_b = true;
/*     */         } 
/*     */         
/*  76 */         if (this.field_75534_e > 0) {
/*     */           
/*  78 */           this.field_75534_e--;
/*     */         }
/*     */         else {
/*     */           
/*  82 */           this.field_75534_e = 2;
/*     */           
/*  84 */           if (this.field_75533_d > 0) {
/*     */             
/*  86 */             spawnZombie();
/*  87 */             this.field_75533_d--;
/*     */           }
/*     */           else {
/*     */             
/*  91 */             this.field_75536_c = 2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_75529_b() {
/* 100 */     List<EntityPlayer> list = this.worldObj.playerEntities;
/* 101 */     Iterator<EntityPlayer> iterator = list.iterator();
/*     */ 
/*     */     
/*     */     while (true) {
/* 105 */       if (!iterator.hasNext())
/*     */       {
/* 107 */         return false;
/*     */       }
/*     */       
/* 110 */       EntityPlayer entityplayer = iterator.next();
/*     */       
/* 112 */       if (!entityplayer.isSpectator()) {
/*     */         
/* 114 */         this.theVillage = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos((Entity)entityplayer), 1);
/*     */         
/* 116 */         if (this.theVillage != null && this.theVillage.getNumVillageDoors() >= 10 && this.theVillage.getTicksSinceLastDoorAdding() >= 20 && this.theVillage.getNumVillagers() >= 20) {
/*     */           
/* 118 */           BlockPos blockpos = this.theVillage.getCenter();
/* 119 */           float f = this.theVillage.getVillageRadius();
/* 120 */           boolean flag = false;
/*     */           
/* 122 */           for (int i = 0; i < 10; i++) {
/*     */             
/* 124 */             float f1 = this.worldObj.rand.nextFloat() * 3.1415927F * 2.0F;
/* 125 */             this.field_75532_g = blockpos.getX() + (int)((MathHelper.cos(f1) * f) * 0.9D);
/* 126 */             this.field_75538_h = blockpos.getY();
/* 127 */             this.field_75539_i = blockpos.getZ() + (int)((MathHelper.sin(f1) * f) * 0.9D);
/* 128 */             flag = false;
/*     */             
/* 130 */             for (Village village : this.worldObj.getVillageCollection().getVillageList()) {
/*     */               
/* 132 */               if (village != this.theVillage && village.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) {
/*     */                 
/* 134 */                 flag = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 139 */             if (!flag) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 145 */           if (flag)
/*     */           {
/* 147 */             return false;
/*     */           }
/*     */           
/* 150 */           Vec3 vec3 = func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
/*     */           
/* 152 */           if (vec3 != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 160 */     this.field_75534_e = 0;
/* 161 */     this.field_75533_d = 20;
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   private boolean spawnZombie() {
/*     */     EntityZombie entityzombie;
/* 167 */     Vec3 vec3 = func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
/*     */     
/* 169 */     if (vec3 == null)
/*     */     {
/* 171 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 179 */       entityzombie = new EntityZombie(this.worldObj);
/* 180 */       entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), (IEntityLivingData)null);
/* 181 */       entityzombie.setVillager(false);
/*     */     }
/* 183 */     catch (Exception exception) {
/*     */       
/* 185 */       exception.printStackTrace();
/* 186 */       return false;
/*     */     } 
/*     */     
/* 189 */     entityzombie.setLocationAndAngles(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
/* 190 */     this.worldObj.spawnEntityInWorld((Entity)entityzombie);
/* 191 */     BlockPos blockpos = this.theVillage.getCenter();
/* 192 */     entityzombie.setHomePosAndDistance(blockpos, this.theVillage.getVillageRadius());
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vec3 func_179867_a(BlockPos p_179867_1_) {
/* 199 */     for (int i = 0; i < 10; i++) {
/*     */       
/* 201 */       BlockPos blockpos = p_179867_1_.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
/*     */       
/* 203 */       if (this.theVillage.func_179866_a(blockpos) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, blockpos))
/*     */       {
/* 205 */         return new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */       }
/*     */     } 
/*     */     
/* 209 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\village\VillageSiege.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */