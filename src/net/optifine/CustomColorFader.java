/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class CustomColorFader
/*    */ {
/*  8 */   private Vec3 color = null;
/*  9 */   private long timeUpdate = System.currentTimeMillis();
/*    */ 
/*    */   
/*    */   public Vec3 getColor(double x, double y, double z) {
/* 13 */     if (this.color == null) {
/*    */       
/* 15 */       this.color = new Vec3(x, y, z);
/* 16 */       return this.color;
/*    */     } 
/*    */ 
/*    */     
/* 20 */     long i = System.currentTimeMillis();
/* 21 */     long j = i - this.timeUpdate;
/*    */     
/* 23 */     if (j == 0L)
/*    */     {
/* 25 */       return this.color;
/*    */     }
/*    */ 
/*    */     
/* 29 */     this.timeUpdate = i;
/*    */     
/* 31 */     if (Math.abs(x - this.color.xCoord) < 0.004D && Math.abs(y - this.color.yCoord) < 0.004D && Math.abs(z - this.color.zCoord) < 0.004D)
/*    */     {
/* 33 */       return this.color;
/*    */     }
/*    */ 
/*    */     
/* 37 */     double d0 = j * 0.001D;
/* 38 */     d0 = Config.limit(d0, 0.0D, 1.0D);
/* 39 */     double d1 = x - this.color.xCoord;
/* 40 */     double d2 = y - this.color.yCoord;
/* 41 */     double d3 = z - this.color.zCoord;
/* 42 */     double d4 = this.color.xCoord + d1 * d0;
/* 43 */     double d5 = this.color.yCoord + d2 * d0;
/* 44 */     double d6 = this.color.zCoord + d3 * d0;
/* 45 */     this.color = new Vec3(d4, d5, d6);
/* 46 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomColorFader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */