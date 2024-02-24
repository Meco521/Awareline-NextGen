/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class TextureClock
/*    */   extends TextureAtlasSprite
/*    */ {
/*    */   private double currentAngle;
/*    */   private double angleDelta;
/*    */   
/*    */   public TextureClock(String iconName) {
/* 13 */     super(iconName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateAnimation() {
/* 18 */     if (!this.framesTextureData.isEmpty()) {
/*    */       
/* 20 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 21 */       double d0 = 0.0D;
/*    */       
/* 23 */       if (minecraft.theWorld != null && minecraft.thePlayer != null) {
/*    */         
/* 25 */         d0 = minecraft.theWorld.getCelestialAngle(1.0F);
/*    */         
/* 27 */         if (!minecraft.theWorld.provider.isSurfaceWorld())
/*    */         {
/* 29 */           d0 = Math.random();
/*    */         }
/*    */       } 
/*    */       
/*    */       double d1;
/*    */       
/* 35 */       for (d1 = d0 - this.currentAngle; d1 < -0.5D; d1++);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 40 */       while (d1 >= 0.5D)
/*    */       {
/* 42 */         d1--;
/*    */       }
/*    */       
/* 45 */       d1 = MathHelper.clamp_double(d1, -1.0D, 1.0D);
/* 46 */       this.angleDelta += d1 * 0.1D;
/* 47 */       this.angleDelta *= 0.8D;
/* 48 */       this.currentAngle += this.angleDelta;
/*    */       
/*    */       int i;
/* 51 */       for (i = (int)((this.currentAngle + 1.0D) * this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size());
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 56 */       if (i != this.frameCounter) {
/*    */         
/* 58 */         this.frameCounter = i;
/* 59 */         TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\texture\TextureClock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */