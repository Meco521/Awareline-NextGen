/*     */ package net.optifine.render;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CloudRenderer
/*     */ {
/*     */   private final Minecraft mc;
/*     */   private boolean updated;
/*     */   private boolean renderFancy;
/*     */   int cloudTickCounter;
/*     */   private Vec3 cloudColor;
/*     */   float partialTicks;
/*     */   private boolean updateRenderFancy;
/*     */   private int updateCloudTickCounter;
/*  20 */   private Vec3 updateCloudColor = new Vec3(-1.0D, -1.0D, -1.0D);
/*     */   
/*     */   private double updatePlayerX;
/*     */   private double updatePlayerY;
/*     */   private double updatePlayerZ;
/*     */   private final int glListClouds;
/*     */   
/*     */   public CloudRenderer(Minecraft mc) {
/*  28 */     this.mc = mc;
/*  29 */     this.glListClouds = GLAllocation.generateDisplayLists(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareToRender(boolean renderFancy, int cloudTickCounter, float partialTicks, Vec3 cloudColor) {
/*  34 */     this.renderFancy = renderFancy;
/*  35 */     this.cloudTickCounter = cloudTickCounter;
/*  36 */     this.partialTicks = partialTicks;
/*  37 */     this.cloudColor = cloudColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldUpdateGlList() {
/*  42 */     if (!this.updated)
/*     */     {
/*  44 */       return true;
/*     */     }
/*  46 */     if (this.renderFancy != this.updateRenderFancy)
/*     */     {
/*  48 */       return true;
/*     */     }
/*  50 */     if (this.cloudTickCounter >= this.updateCloudTickCounter + 20)
/*     */     {
/*  52 */       return true;
/*     */     }
/*  54 */     if (Math.abs(this.cloudColor.xCoord - this.updateCloudColor.xCoord) > 0.003D)
/*     */     {
/*  56 */       return true;
/*     */     }
/*  58 */     if (Math.abs(this.cloudColor.yCoord - this.updateCloudColor.yCoord) > 0.003D)
/*     */     {
/*  60 */       return true;
/*     */     }
/*  62 */     if (Math.abs(this.cloudColor.zCoord - this.updateCloudColor.zCoord) > 0.003D)
/*     */     {
/*  64 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  68 */     Entity entity = this.mc.getRenderViewEntity();
/*  69 */     boolean flag = (this.updatePlayerY + entity.getEyeHeight() < 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F));
/*  70 */     boolean flag1 = (entity.prevPosY + entity.getEyeHeight() < 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F));
/*  71 */     return (flag1 != flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startUpdateGlList() {
/*  77 */     GL11.glNewList(this.glListClouds, 4864);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endUpdateGlList() {
/*  82 */     GL11.glEndList();
/*  83 */     this.updateRenderFancy = this.renderFancy;
/*  84 */     this.updateCloudTickCounter = this.cloudTickCounter;
/*  85 */     this.updateCloudColor = this.cloudColor;
/*  86 */     this.updatePlayerX = (this.mc.getRenderViewEntity()).prevPosX;
/*  87 */     this.updatePlayerY = (this.mc.getRenderViewEntity()).prevPosY;
/*  88 */     this.updatePlayerZ = (this.mc.getRenderViewEntity()).prevPosZ;
/*  89 */     this.updated = true;
/*  90 */     GlStateManager.resetColor();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderGlList() {
/*  95 */     Entity entity = this.mc.getRenderViewEntity();
/*  96 */     double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * this.partialTicks;
/*  97 */     double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * this.partialTicks;
/*  98 */     double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * this.partialTicks;
/*  99 */     double d3 = ((this.cloudTickCounter - this.updateCloudTickCounter) + this.partialTicks);
/* 100 */     float f = (float)(d0 - this.updatePlayerX + d3 * 0.03D);
/* 101 */     float f1 = (float)(d1 - this.updatePlayerY);
/* 102 */     float f2 = (float)(d2 - this.updatePlayerZ);
/* 103 */     GlStateManager.pushMatrix();
/*     */     
/* 105 */     if (this.renderFancy) {
/*     */       
/* 107 */       GlStateManager.translate(-f / 12.0F, -f1, -f2 / 12.0F);
/*     */     }
/*     */     else {
/*     */       
/* 111 */       GlStateManager.translate(-f, -f1, -f2);
/*     */     } 
/*     */     
/* 114 */     GlStateManager.callList(this.glListClouds);
/* 115 */     GlStateManager.popMatrix();
/* 116 */     GlStateManager.resetColor();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 121 */     this.updated = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\CloudRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */