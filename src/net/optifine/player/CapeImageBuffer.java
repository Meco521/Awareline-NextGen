/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.ImageBufferDownload;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class CapeImageBuffer
/*    */   extends ImageBufferDownload
/*    */ {
/*    */   private AbstractClientPlayer player;
/*    */   private final ResourceLocation resourceLocation;
/*    */   private boolean elytraOfCape;
/*    */   
/*    */   public CapeImageBuffer(AbstractClientPlayer player, ResourceLocation resourceLocation) {
/* 17 */     this.player = player;
/* 18 */     this.resourceLocation = resourceLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage parseUserSkin(BufferedImage imageRaw) {
/* 23 */     BufferedImage bufferedimage = CapeUtils.parseCape(imageRaw);
/* 24 */     this.elytraOfCape = CapeUtils.isElytraCape(imageRaw, bufferedimage);
/* 25 */     return bufferedimage;
/*    */   }
/*    */ 
/*    */   
/*    */   public void skinAvailable() {
/* 30 */     if (this.player != null) {
/*    */       
/* 32 */       this.player.setLocationOfCape(this.resourceLocation);
/* 33 */       this.player.setElytraOfCape(this.elytraOfCape);
/*    */     } 
/*    */     
/* 36 */     cleanup();
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanup() {
/* 41 */     this.player = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isElytraOfCape() {
/* 46 */     return this.elytraOfCape;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\CapeImageBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */