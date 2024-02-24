/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RenderFish extends Render<EntityFishHook> {
/*  15 */   private static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");
/*     */ 
/*     */   
/*     */   public RenderFish(RenderManager renderManagerIn) {
/*  19 */     super(renderManagerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  27 */     GlStateManager.pushMatrix();
/*  28 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*  29 */     GlStateManager.enableRescaleNormal();
/*  30 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*  31 */     bindEntityTexture(entity);
/*  32 */     Tessellator tessellator = Tessellator.getInstance();
/*  33 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  34 */     int i = 1;
/*  35 */     int j = 2;
/*  36 */     float f = 0.0625F;
/*  37 */     float f1 = 0.125F;
/*  38 */     float f2 = 0.125F;
/*  39 */     float f3 = 0.1875F;
/*  40 */     float f4 = 1.0F;
/*  41 */     float f5 = 0.5F;
/*  42 */     float f6 = 0.5F;
/*  43 */     GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  44 */     GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  45 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*  46 */     worldrenderer.pos(-0.5D, -0.5D, 0.0D).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  47 */     worldrenderer.pos(0.5D, -0.5D, 0.0D).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  48 */     worldrenderer.pos(0.5D, 0.5D, 0.0D).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  49 */     worldrenderer.pos(-0.5D, 0.5D, 0.0D).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  50 */     tessellator.draw();
/*  51 */     GlStateManager.disableRescaleNormal();
/*  52 */     GlStateManager.popMatrix();
/*     */     
/*  54 */     if (entity.angler != null) {
/*     */       
/*  56 */       float f7 = entity.angler.getSwingProgress(partialTicks);
/*  57 */       float f8 = MathHelper.sin(MathHelper.sqrt_float(f7) * 3.1415927F);
/*  58 */       Vec3 vec3 = new Vec3(-0.36D, 0.03D, 0.35D);
/*  59 */       vec3 = vec3.rotatePitch(-(entity.angler.prevRotationPitch + (entity.angler.rotationPitch - entity.angler.prevRotationPitch) * partialTicks) * 3.1415927F / 180.0F);
/*  60 */       vec3 = vec3.rotateYaw(-(entity.angler.prevRotationYaw + (entity.angler.rotationYaw - entity.angler.prevRotationYaw) * partialTicks) * 3.1415927F / 180.0F);
/*  61 */       vec3 = vec3.rotateYaw(f8 * 0.5F);
/*  62 */       vec3 = vec3.rotatePitch(-f8 * 0.7F);
/*  63 */       double d0 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * partialTicks + vec3.xCoord;
/*  64 */       double d1 = entity.angler.prevPosY + (entity.angler.posY - entity.angler.prevPosY) * partialTicks + vec3.yCoord;
/*  65 */       double d2 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * partialTicks + vec3.zCoord;
/*  66 */       double d3 = entity.angler.getEyeHeight();
/*     */       
/*  68 */       if ((this.renderManager.options != null && this.renderManager.options.thirdPersonView > 0) || entity.angler != (Minecraft.getMinecraft()).thePlayer) {
/*     */         
/*  70 */         float f9 = (entity.angler.prevRenderYawOffset + (entity.angler.renderYawOffset - entity.angler.prevRenderYawOffset) * partialTicks) * 3.1415927F / 180.0F;
/*  71 */         double d4 = MathHelper.sin(f9);
/*  72 */         double d6 = MathHelper.cos(f9);
/*  73 */         double d8 = 0.35D;
/*  74 */         double d10 = 0.8D;
/*  75 */         d0 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * partialTicks - d6 * 0.35D - d4 * 0.8D;
/*  76 */         d1 = entity.angler.prevPosY + d3 + (entity.angler.posY - entity.angler.prevPosY) * partialTicks - 0.45D;
/*  77 */         d2 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * partialTicks - d4 * 0.35D + d6 * 0.8D;
/*  78 */         d3 = entity.angler.isSneaking() ? -0.1875D : 0.0D;
/*     */       } 
/*     */       
/*  81 */       double d13 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  82 */       double d5 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + 0.25D;
/*  83 */       double d7 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*  84 */       double d9 = (float)(d0 - d13);
/*  85 */       double d11 = (float)(d1 - d5) + d3;
/*  86 */       double d12 = (float)(d2 - d7);
/*  87 */       GlStateManager.disableTexture2D();
/*  88 */       GlStateManager.disableLighting();
/*  89 */       worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*  90 */       int k = 16;
/*     */       
/*  92 */       for (int l = 0; l <= 16; l++) {
/*     */         
/*  94 */         float f10 = l / 16.0F;
/*  95 */         worldrenderer.pos(x + d9 * f10, y + d11 * (f10 * f10 + f10) * 0.5D + 0.25D, z + d12 * f10).color(0, 0, 0, 255).endVertex();
/*     */       } 
/*     */       
/*  98 */       tessellator.draw();
/*  99 */       GlStateManager.enableLighting();
/* 100 */       GlStateManager.enableTexture2D();
/* 101 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityFishHook entity) {
/* 110 */     return FISH_PARTICLES;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderFish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */