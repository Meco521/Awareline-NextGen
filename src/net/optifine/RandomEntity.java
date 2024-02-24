/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ 
/*    */ public class RandomEntity
/*    */   implements IRandomEntity
/*    */ {
/*    */   private Entity entity;
/*    */   
/*    */   public int getId() {
/* 16 */     UUID uuid = this.entity.getUniqueID();
/* 17 */     long i = uuid.getLeastSignificantBits();
/* 18 */     int j = (int)(i & 0x7FFFFFFFL);
/* 19 */     return j;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getSpawnPosition() {
/* 24 */     return (this.entity.getDataWatcher()).spawnPosition;
/*    */   }
/*    */ 
/*    */   
/*    */   public BiomeGenBase getSpawnBiome() {
/* 29 */     return (this.entity.getDataWatcher()).spawnBiome;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 34 */     return this.entity.hasCustomName() ? this.entity.getCustomNameTag() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHealth() {
/* 39 */     if (!(this.entity instanceof EntityLiving))
/*    */     {
/* 41 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 45 */     EntityLiving entityliving = (EntityLiving)this.entity;
/* 46 */     return (int)entityliving.getHealth();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxHealth() {
/* 52 */     if (!(this.entity instanceof EntityLiving))
/*    */     {
/* 54 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 58 */     EntityLiving entityliving = (EntityLiving)this.entity;
/* 59 */     return (int)entityliving.getMaxHealth();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity getEntity() {
/* 65 */     return this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntity(Entity entity) {
/* 70 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\RandomEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */