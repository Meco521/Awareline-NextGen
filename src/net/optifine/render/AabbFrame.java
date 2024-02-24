/*    */ package net.optifine.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.culling.Frustum;
/*    */ import net.minecraft.client.renderer.culling.ICamera;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ public class AabbFrame
/*    */   extends AxisAlignedBB {
/*  9 */   private int frameCount = -1;
/*    */   
/*    */   private boolean inFrustumFully;
/*    */   
/*    */   public AabbFrame(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 14 */     super(x1, y1, z1, x2, y2, z2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBoundingBoxInFrustumFully(ICamera camera, int frameCount) {
/* 19 */     if (this.frameCount != frameCount) {
/*    */       
/* 21 */       this.inFrustumFully = (camera instanceof Frustum && ((Frustum)camera).isBoxInFrustumFully(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ));
/* 22 */       this.frameCount = frameCount;
/*    */     } 
/*    */     
/* 25 */     return this.inFrustumFully;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\AabbFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */