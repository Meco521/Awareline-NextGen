/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelMinecart;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RenderMinecart<T extends EntityMinecart> extends Render<T> {
/*  16 */   private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
/*     */ 
/*     */   
/*  19 */   protected ModelBase modelMinecart = (ModelBase)new ModelMinecart();
/*     */ 
/*     */   
/*     */   public RenderMinecart(RenderManager renderManagerIn) {
/*  23 */     super(renderManagerIn);
/*  24 */     this.shadowSize = 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  32 */     GlStateManager.pushMatrix();
/*  33 */     bindEntityTexture(entity);
/*  34 */     long i = entity.getEntityId() * 493286711L;
/*  35 */     i = i * i * 4392167121L + i * 98761L;
/*  36 */     float f = (((float)(i >> 16L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  37 */     float f1 = (((float)(i >> 20L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  38 */     float f2 = (((float)(i >> 24L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  39 */     GlStateManager.translate(f, f1, f2);
/*  40 */     double d0 = ((EntityMinecart)entity).lastTickPosX + (((EntityMinecart)entity).posX - ((EntityMinecart)entity).lastTickPosX) * partialTicks;
/*  41 */     double d1 = ((EntityMinecart)entity).lastTickPosY + (((EntityMinecart)entity).posY - ((EntityMinecart)entity).lastTickPosY) * partialTicks;
/*  42 */     double d2 = ((EntityMinecart)entity).lastTickPosZ + (((EntityMinecart)entity).posZ - ((EntityMinecart)entity).lastTickPosZ) * partialTicks;
/*  43 */     double d3 = 0.30000001192092896D;
/*  44 */     Vec3 vec3 = entity.func_70489_a(d0, d1, d2);
/*  45 */     float f3 = ((EntityMinecart)entity).prevRotationPitch + (((EntityMinecart)entity).rotationPitch - ((EntityMinecart)entity).prevRotationPitch) * partialTicks;
/*     */     
/*  47 */     if (vec3 != null) {
/*     */       
/*  49 */       Vec3 vec31 = entity.func_70495_a(d0, d1, d2, d3);
/*  50 */       Vec3 vec32 = entity.func_70495_a(d0, d1, d2, -d3);
/*     */       
/*  52 */       if (vec31 == null)
/*     */       {
/*  54 */         vec31 = vec3;
/*     */       }
/*     */       
/*  57 */       if (vec32 == null)
/*     */       {
/*  59 */         vec32 = vec3;
/*     */       }
/*     */       
/*  62 */       x += vec3.xCoord - d0;
/*  63 */       y += (vec31.yCoord + vec32.yCoord) / 2.0D - d1;
/*  64 */       z += vec3.zCoord - d2;
/*  65 */       Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);
/*     */       
/*  67 */       if (vec33.lengthVector() != 0.0D) {
/*     */         
/*  69 */         vec33 = vec33.normalize();
/*  70 */         entityYaw = (float)(Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0D / Math.PI);
/*  71 */         f3 = (float)(Math.atan(vec33.yCoord) * 73.0D);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     GlStateManager.translate((float)x, (float)y + 0.375F, (float)z);
/*  76 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/*  77 */     GlStateManager.rotate(-f3, 0.0F, 0.0F, 1.0F);
/*  78 */     float f5 = entity.getRollingAmplitude() - partialTicks;
/*  79 */     float f6 = entity.getDamage() - partialTicks;
/*     */     
/*  81 */     if (f6 < 0.0F)
/*     */     {
/*  83 */       f6 = 0.0F;
/*     */     }
/*     */     
/*  86 */     if (f5 > 0.0F)
/*     */     {
/*  88 */       GlStateManager.rotate(MathHelper.sin(f5) * f5 * f6 / 10.0F * entity.getRollingDirection(), 1.0F, 0.0F, 0.0F);
/*     */     }
/*     */     
/*  91 */     int j = entity.getDisplayTileOffset();
/*  92 */     IBlockState iblockstate = entity.getDisplayTile();
/*     */     
/*  94 */     if (iblockstate.getBlock().getRenderType() != -1) {
/*     */       
/*  96 */       GlStateManager.pushMatrix();
/*  97 */       bindTexture(TextureMap.locationBlocksTexture);
/*  98 */       float f4 = 0.75F;
/*  99 */       GlStateManager.scale(f4, f4, f4);
/* 100 */       GlStateManager.translate(-0.5F, (j - 8) / 16.0F, 0.5F);
/* 101 */       func_180560_a(entity, partialTicks, iblockstate);
/* 102 */       GlStateManager.popMatrix();
/* 103 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 104 */       bindEntityTexture(entity);
/*     */     } 
/*     */     
/* 107 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 108 */     this.modelMinecart.render((Entity)entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 109 */     GlStateManager.popMatrix();
/* 110 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(T entity) {
/* 118 */     return minecartTextures;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_180560_a(T minecart, float partialTicks, IBlockState state) {
/* 123 */     GlStateManager.pushMatrix();
/* 124 */     Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(state, minecart.getBrightness(partialTicks));
/* 125 */     GlStateManager.popMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */