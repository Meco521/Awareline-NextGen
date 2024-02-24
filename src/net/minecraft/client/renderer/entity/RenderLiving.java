/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public abstract class RenderLiving<T extends EntityLiving>
/*     */   extends RendererLivingEntity<T>
/*     */ {
/*     */   public RenderLiving(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
/*  20 */     super(rendermanagerIn, modelbaseIn, shadowsizeIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/*  25 */     return (super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || (entity.hasCustomName() && entity == this.renderManager.pointedEntity)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  30 */     if (super.shouldRender(livingEntity, camera, camX, camY, camZ))
/*     */     {
/*  32 */       return true;
/*     */     }
/*  34 */     if (livingEntity.getLeashed() && livingEntity.getLeashedToEntity() != null) {
/*     */       
/*  36 */       Entity entity = livingEntity.getLeashedToEntity();
/*  37 */       return camera.isBoundingBoxInFrustum(entity.getEntityBoundingBox());
/*     */     } 
/*     */ 
/*     */     
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  50 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*  51 */     renderLeash(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLightmap(T entityLivingIn, float partialTicks) {
/*  56 */     int i = entityLivingIn.getBrightnessForRender(partialTicks);
/*  57 */     int j = i % 65536;
/*  58 */     int k = i / 65536;
/*  59 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double interpolateValue(double start, double end, double pct) {
/*  67 */     return start + (end - start) * pct;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLeash(T entityLivingIn, double x, double y, double z, float entityYaw, float partialTicks) {
/*  72 */     if (!Config.isShaders() || !Shaders.isShadowPass) {
/*     */       
/*  74 */       Entity entity = entityLivingIn.getLeashedToEntity();
/*     */       
/*  76 */       if (entity != null) {
/*     */         
/*  78 */         y -= (1.6D - ((EntityLiving)entityLivingIn).height) * 0.5D;
/*  79 */         Tessellator tessellator = Tessellator.getInstance();
/*  80 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  81 */         double d0 = interpolateValue(entity.prevRotationYaw, entity.rotationYaw, (partialTicks * 0.5F)) * 0.01745329238474369D;
/*  82 */         double d1 = interpolateValue(entity.prevRotationPitch, entity.rotationPitch, (partialTicks * 0.5F)) * 0.01745329238474369D;
/*  83 */         double d2 = Math.cos(d0);
/*  84 */         double d3 = Math.sin(d0);
/*  85 */         double d4 = Math.sin(d1);
/*     */         
/*  87 */         if (entity instanceof net.minecraft.entity.EntityHanging) {
/*     */           
/*  89 */           d2 = 0.0D;
/*  90 */           d3 = 0.0D;
/*  91 */           d4 = -1.0D;
/*     */         } 
/*     */         
/*  94 */         double d5 = Math.cos(d1);
/*  95 */         double d6 = interpolateValue(entity.prevPosX, entity.posX, partialTicks) - d2 * 0.7D - d3 * 0.5D * d5;
/*  96 */         double d7 = interpolateValue(entity.prevPosY + entity.getEyeHeight() * 0.7D, entity.posY + entity.getEyeHeight() * 0.7D, partialTicks) - d4 * 0.5D - 0.25D;
/*  97 */         double d8 = interpolateValue(entity.prevPosZ, entity.posZ, partialTicks) - d3 * 0.7D + d2 * 0.5D * d5;
/*  98 */         double d9 = interpolateValue(((EntityLiving)entityLivingIn).prevRenderYawOffset, ((EntityLiving)entityLivingIn).renderYawOffset, partialTicks) * 0.01745329238474369D + 1.5707963267948966D;
/*  99 */         d2 = Math.cos(d9) * ((EntityLiving)entityLivingIn).width * 0.4D;
/* 100 */         d3 = Math.sin(d9) * ((EntityLiving)entityLivingIn).width * 0.4D;
/* 101 */         double d10 = interpolateValue(((EntityLiving)entityLivingIn).prevPosX, ((EntityLiving)entityLivingIn).posX, partialTicks) + d2;
/* 102 */         double d11 = interpolateValue(((EntityLiving)entityLivingIn).prevPosY, ((EntityLiving)entityLivingIn).posY, partialTicks);
/* 103 */         double d12 = interpolateValue(((EntityLiving)entityLivingIn).prevPosZ, ((EntityLiving)entityLivingIn).posZ, partialTicks) + d3;
/* 104 */         x += d2;
/* 105 */         z += d3;
/* 106 */         double d13 = (float)(d6 - d10);
/* 107 */         double d14 = (float)(d7 - d11);
/* 108 */         double d15 = (float)(d8 - d12);
/* 109 */         GlStateManager.disableTexture2D();
/* 110 */         GlStateManager.disableLighting();
/* 111 */         GlStateManager.disableCull();
/*     */         
/* 113 */         if (Config.isShaders())
/*     */         {
/* 115 */           Shaders.beginLeash();
/*     */         }
/*     */         
/* 118 */         int i = 24;
/* 119 */         double d16 = 0.025D;
/* 120 */         worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*     */         
/* 122 */         for (int j = 0; j <= 24; j++) {
/*     */           
/* 124 */           float f = 0.5F;
/* 125 */           float f1 = 0.4F;
/* 126 */           float f2 = 0.3F;
/*     */           
/* 128 */           if (j % 2 == 0) {
/*     */             
/* 130 */             f *= 0.7F;
/* 131 */             f1 *= 0.7F;
/* 132 */             f2 *= 0.7F;
/*     */           } 
/*     */           
/* 135 */           float f3 = j / 24.0F;
/* 136 */           worldrenderer.pos(x + d13 * f3 + 0.0D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F), z + d15 * f3).color(f, f1, f2, 1.0F).endVertex();
/* 137 */           worldrenderer.pos(x + d13 * f3 + 0.025D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F) + 0.025D, z + d15 * f3).color(f, f1, f2, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 140 */         tessellator.draw();
/* 141 */         worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*     */         
/* 143 */         for (int k = 0; k <= 24; k++) {
/*     */           
/* 145 */           float f4 = 0.5F;
/* 146 */           float f5 = 0.4F;
/* 147 */           float f6 = 0.3F;
/*     */           
/* 149 */           if (k % 2 == 0) {
/*     */             
/* 151 */             f4 *= 0.7F;
/* 152 */             f5 *= 0.7F;
/* 153 */             f6 *= 0.7F;
/*     */           } 
/*     */           
/* 156 */           float f7 = k / 24.0F;
/* 157 */           worldrenderer.pos(x + d13 * f7 + 0.0D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F) + 0.025D, z + d15 * f7).color(f4, f5, f6, 1.0F).endVertex();
/* 158 */           worldrenderer.pos(x + d13 * f7 + 0.025D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F), z + d15 * f7 + 0.025D).color(f4, f5, f6, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 161 */         tessellator.draw();
/*     */         
/* 163 */         if (Config.isShaders())
/*     */         {
/* 165 */           Shaders.endLeash();
/*     */         }
/*     */         
/* 168 */         GlStateManager.enableLighting();
/* 169 */         GlStateManager.enableTexture2D();
/* 170 */         GlStateManager.enableCull();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */