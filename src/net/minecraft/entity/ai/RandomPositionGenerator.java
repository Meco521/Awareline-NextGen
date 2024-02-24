/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomPositionGenerator
/*     */ {
/*  16 */   private static Vec3 staticVector = new Vec3(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 findRandomTarget(EntityCreature entitycreatureIn, int xz, int y) {
/*  23 */     return findRandomTargetBlock(entitycreatureIn, xz, y, (Vec3)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 findRandomTargetBlockTowards(EntityCreature entitycreatureIn, int xz, int y, Vec3 targetVec3) {
/*  31 */     staticVector = targetVec3.subtract(entitycreatureIn.posX, entitycreatureIn.posY, entitycreatureIn.posZ);
/*  32 */     return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 findRandomTargetBlockAwayFrom(EntityCreature entitycreatureIn, int xz, int y, Vec3 targetVec3) {
/*  40 */     staticVector = (new Vec3(entitycreatureIn.posX, entitycreatureIn.posY, entitycreatureIn.posZ)).subtract(targetVec3);
/*  41 */     return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Vec3 findRandomTargetBlock(EntityCreature entitycreatureIn, int xz, int y, Vec3 targetVec3) {
/*     */     boolean flag1;
/*  50 */     Random random = entitycreatureIn.getRNG();
/*  51 */     boolean flag = false;
/*  52 */     int i = 0;
/*  53 */     int j = 0;
/*  54 */     int k = 0;
/*  55 */     float f = -99999.0F;
/*     */ 
/*     */     
/*  58 */     if (entitycreatureIn.hasHome()) {
/*     */       
/*  60 */       double d0 = entitycreatureIn.getHomePosition().distanceSq(MathHelper.floor_double(entitycreatureIn.posX), MathHelper.floor_double(entitycreatureIn.posY), MathHelper.floor_double(entitycreatureIn.posZ)) + 4.0D;
/*  61 */       double d1 = (entitycreatureIn.getMaximumHomeDistance() + xz);
/*  62 */       flag1 = (d0 < d1 * d1);
/*     */     }
/*     */     else {
/*     */       
/*  66 */       flag1 = false;
/*     */     } 
/*     */     
/*  69 */     for (int j1 = 0; j1 < 10; j1++) {
/*     */       
/*  71 */       int l = random.nextInt(2 * xz + 1) - xz;
/*  72 */       int k1 = random.nextInt(2 * y + 1) - y;
/*  73 */       int i1 = random.nextInt(2 * xz + 1) - xz;
/*     */       
/*  75 */       if (targetVec3 == null || l * targetVec3.xCoord + i1 * targetVec3.zCoord >= 0.0D) {
/*     */         
/*  77 */         if (entitycreatureIn.hasHome() && xz > 1) {
/*     */           
/*  79 */           BlockPos blockpos = entitycreatureIn.getHomePosition();
/*     */           
/*  81 */           if (entitycreatureIn.posX > blockpos.getX()) {
/*     */             
/*  83 */             l -= random.nextInt(xz / 2);
/*     */           }
/*     */           else {
/*     */             
/*  87 */             l += random.nextInt(xz / 2);
/*     */           } 
/*     */           
/*  90 */           if (entitycreatureIn.posZ > blockpos.getZ()) {
/*     */             
/*  92 */             i1 -= random.nextInt(xz / 2);
/*     */           }
/*     */           else {
/*     */             
/*  96 */             i1 += random.nextInt(xz / 2);
/*     */           } 
/*     */         } 
/*     */         
/* 100 */         l += MathHelper.floor_double(entitycreatureIn.posX);
/* 101 */         k1 += MathHelper.floor_double(entitycreatureIn.posY);
/* 102 */         i1 += MathHelper.floor_double(entitycreatureIn.posZ);
/* 103 */         BlockPos blockpos1 = new BlockPos(l, k1, i1);
/*     */         
/* 105 */         if (!flag1 || entitycreatureIn.isWithinHomeDistanceFromPosition(blockpos1)) {
/*     */           
/* 107 */           float f1 = entitycreatureIn.getBlockPathWeight(blockpos1);
/*     */           
/* 109 */           if (f1 > f) {
/*     */             
/* 111 */             f = f1;
/* 112 */             i = l;
/* 113 */             j = k1;
/* 114 */             k = i1;
/* 115 */             flag = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     if (flag)
/*     */     {
/* 123 */       return new Vec3(i, j, k);
/*     */     }
/*     */ 
/*     */     
/* 127 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\RandomPositionGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */