/*     */ package net.minecraft.client.renderer.entity;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.entity.model.IEntityRenderer;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public abstract class Render<T extends Entity> implements IEntityRenderer {
/*  26 */   private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
/*     */ 
/*     */   
/*     */   protected final RenderManager renderManager;
/*     */   
/*     */   public float shadowSize;
/*     */   
/*  33 */   protected float shadowOpaque = 1.0F;
/*  34 */   private Class entityClass = null;
/*  35 */   private ResourceLocation locationTextureCustom = null;
/*     */ 
/*     */   
/*     */   protected Render(RenderManager renderManager) {
/*  39 */     this.renderManager = renderManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  44 */     AxisAlignedBB axisalignedbb = livingEntity.getEntityBoundingBox();
/*     */     
/*  46 */     if (axisalignedbb.hasNaN() || axisalignedbb.getAverageEdgeLength() == 0.0D)
/*     */     {
/*  48 */       axisalignedbb = new AxisAlignedBB(((Entity)livingEntity).posX - 2.0D, ((Entity)livingEntity).posY - 2.0D, ((Entity)livingEntity).posZ - 2.0D, ((Entity)livingEntity).posX + 2.0D, ((Entity)livingEntity).posY + 2.0D, ((Entity)livingEntity).posZ + 2.0D);
/*     */     }
/*     */     
/*  51 */     return (livingEntity.isInRangeToRender3d(camX, camY, camZ) && (((Entity)livingEntity).ignoreFrustumCheck || camera.isBoundingBoxInFrustum(axisalignedbb)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  59 */     renderName(entity, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderName(T entity, double x, double y, double z) {
/*  64 */     if (canRenderName(entity))
/*     */     {
/*  66 */       renderLivingLabel(entity, entity.getDisplayName().getFormattedText(), x, y, z, 64);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/*  72 */     return (entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderOffsetLivingLabel(T entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
/*  77 */     renderLivingLabel(entityIn, str, x, y, z, 64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ResourceLocation getEntityTexture(T paramT);
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean bindEntityTexture(T entity) {
/*  87 */     ResourceLocation resourcelocation = getEntityTexture(entity);
/*     */     
/*  89 */     if (this.locationTextureCustom != null)
/*     */     {
/*  91 */       resourcelocation = this.locationTextureCustom;
/*     */     }
/*     */     
/*  94 */     if (resourcelocation == null)
/*     */     {
/*  96 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 100 */     bindTexture(resourcelocation);
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindTexture(ResourceLocation location) {
/* 107 */     this.renderManager.renderEngine.bindTexture(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks) {
/* 115 */     GlStateManager.disableLighting();
/* 116 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/* 117 */     TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_0");
/* 118 */     TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 119 */     GlStateManager.pushMatrix();
/* 120 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 121 */     float f = entity.width * 1.4F;
/* 122 */     GlStateManager.scale(f, f, f);
/* 123 */     Tessellator tessellator = Tessellator.getInstance();
/* 124 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 125 */     float f1 = 0.5F;
/* 126 */     float f2 = 0.0F;
/* 127 */     float f3 = entity.height / f;
/* 128 */     float f4 = (float)(entity.posY - (entity.getEntityBoundingBox()).minY);
/* 129 */     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 130 */     GlStateManager.translate(0.0F, 0.0F, -0.3F + (int)f3 * 0.02F);
/* 131 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 132 */     float f5 = 0.0F;
/* 133 */     int i = 0;
/* 134 */     boolean flag = Config.isMultiTexture();
/*     */     
/* 136 */     if (flag)
/*     */     {
/* 138 */       worldrenderer.setBlockLayer(EnumWorldBlockLayer.SOLID);
/*     */     }
/*     */     
/* 141 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 143 */     while (f3 > 0.0F) {
/*     */       
/* 145 */       TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
/* 146 */       worldrenderer.setSprite(textureatlassprite2);
/* 147 */       bindTexture(TextureMap.locationBlocksTexture);
/* 148 */       float f6 = textureatlassprite2.getMinU();
/* 149 */       float f7 = textureatlassprite2.getMinV();
/* 150 */       float f8 = textureatlassprite2.getMaxU();
/* 151 */       float f9 = textureatlassprite2.getMaxV();
/*     */       
/* 153 */       if (i / 2 % 2 == 0) {
/*     */         
/* 155 */         float f10 = f8;
/* 156 */         f8 = f6;
/* 157 */         f6 = f10;
/*     */       } 
/*     */       
/* 160 */       worldrenderer.pos((f1 - f2), (0.0F - f4), f5).tex(f8, f9).endVertex();
/* 161 */       worldrenderer.pos((-f1 - f2), (0.0F - f4), f5).tex(f6, f9).endVertex();
/* 162 */       worldrenderer.pos((-f1 - f2), (1.4F - f4), f5).tex(f6, f7).endVertex();
/* 163 */       worldrenderer.pos((f1 - f2), (1.4F - f4), f5).tex(f8, f7).endVertex();
/* 164 */       f3 -= 0.45F;
/* 165 */       f4 -= 0.45F;
/* 166 */       f1 *= 0.9F;
/* 167 */       f5 += 0.03F;
/* 168 */       i++;
/*     */     } 
/*     */     
/* 171 */     tessellator.draw();
/*     */     
/* 173 */     if (flag) {
/*     */       
/* 175 */       worldrenderer.setBlockLayer((EnumWorldBlockLayer)null);
/* 176 */       GlStateManager.bindCurrentTexture();
/*     */     } 
/*     */     
/* 179 */     GlStateManager.popMatrix();
/* 180 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderShadow(Entity entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 189 */     if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
/*     */       
/* 191 */       GlStateManager.enableBlend();
/* 192 */       GlStateManager.blendFunc(770, 771);
/* 193 */       this.renderManager.renderEngine.bindTexture(shadowTextures);
/* 194 */       World world = getWorldFromRenderManager();
/* 195 */       GlStateManager.depthMask(false);
/* 196 */       float f = this.shadowSize;
/*     */       
/* 198 */       if (entityIn instanceof EntityLiving) {
/*     */         
/* 200 */         EntityLiving entityliving = (EntityLiving)entityIn;
/* 201 */         f *= entityliving.getRenderSizeModifier();
/*     */         
/* 203 */         if (entityliving.isChild())
/*     */         {
/* 205 */           f *= 0.5F;
/*     */         }
/*     */       } 
/*     */       
/* 209 */       double d5 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 210 */       double d0 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 211 */       double d1 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 212 */       int i = MathHelper.floor_double(d5 - f);
/* 213 */       int j = MathHelper.floor_double(d5 + f);
/* 214 */       int k = MathHelper.floor_double(d0 - f);
/* 215 */       int l = MathHelper.floor_double(d0);
/* 216 */       int i1 = MathHelper.floor_double(d1 - f);
/* 217 */       int j1 = MathHelper.floor_double(d1 + f);
/* 218 */       double d2 = x - d5;
/* 219 */       double d3 = y - d0;
/* 220 */       double d4 = z - d1;
/* 221 */       Tessellator tessellator = Tessellator.getInstance();
/* 222 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 223 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */       
/* 225 */       for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(i, k, i1), new BlockPos(j, l, j1))) {
/*     */         
/* 227 */         Block block = world.getBlockState(blockpos.down()).getBlock();
/*     */         
/* 229 */         if (block.getRenderType() != -1 && world.getLightFromNeighbors(blockpos) > 3)
/*     */         {
/* 231 */           renderShadowBlock(block, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */         }
/*     */       } 
/*     */       
/* 235 */       tessellator.draw();
/* 236 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 237 */       GlStateManager.disableBlend();
/* 238 */       GlStateManager.depthMask(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private World getWorldFromRenderManager() {
/* 247 */     return this.renderManager.worldObj;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderShadowBlock(Block blockIn, double p_180549_2_, double p_180549_4_, double p_180549_6_, BlockPos pos, float p_180549_9_, float p_180549_10_, double p_180549_11_, double p_180549_13_, double p_180549_15_) {
/* 252 */     if (blockIn.isFullCube()) {
/*     */       
/* 254 */       Tessellator tessellator = Tessellator.getInstance();
/* 255 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 256 */       double d0 = (p_180549_9_ - (p_180549_4_ - pos.getY() + p_180549_13_) / 2.0D) * 0.5D * getWorldFromRenderManager().getLightBrightness(pos);
/*     */       
/* 258 */       if (d0 >= 0.0D) {
/*     */         
/* 260 */         if (d0 > 1.0D)
/*     */         {
/* 262 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 265 */         double d1 = pos.getX() + blockIn.getBlockBoundsMinX() + p_180549_11_;
/* 266 */         double d2 = pos.getX() + blockIn.getBlockBoundsMaxX() + p_180549_11_;
/* 267 */         double d3 = pos.getY() + blockIn.getBlockBoundsMinY() + p_180549_13_ + 0.015625D;
/* 268 */         double d4 = pos.getZ() + blockIn.getBlockBoundsMinZ() + p_180549_15_;
/* 269 */         double d5 = pos.getZ() + blockIn.getBlockBoundsMaxZ() + p_180549_15_;
/* 270 */         float f = (float)((p_180549_2_ - d1) / 2.0D / p_180549_10_ + 0.5D);
/* 271 */         float f1 = (float)((p_180549_2_ - d2) / 2.0D / p_180549_10_ + 0.5D);
/* 272 */         float f2 = (float)((p_180549_6_ - d4) / 2.0D / p_180549_10_ + 0.5D);
/* 273 */         float f3 = (float)((p_180549_6_ - d5) / 2.0D / p_180549_10_ + 0.5D);
/* 274 */         worldrenderer.pos(d1, d3, d4).tex(f, f2).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 275 */         worldrenderer.pos(d1, d3, d5).tex(f, f3).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 276 */         worldrenderer.pos(d2, d3, d5).tex(f1, f3).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 277 */         worldrenderer.pos(d2, d3, d4).tex(f1, f2).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderOffsetAABB(AxisAlignedBB boundingBox, double x, double y, double z) {
/* 287 */     GlStateManager.disableTexture2D();
/* 288 */     Tessellator tessellator = Tessellator.getInstance();
/* 289 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 290 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 291 */     worldrenderer.setTranslation(x, y, z);
/* 292 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_NORMAL);
/* 293 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 294 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 295 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 296 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 297 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 298 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 299 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 300 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 301 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 302 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 303 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 304 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 305 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 306 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 307 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 308 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 309 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 310 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 311 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 312 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 313 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 314 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 315 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 316 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 317 */     tessellator.draw();
/* 318 */     worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 319 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 327 */     if (this.renderManager.options != null) {
/*     */       
/* 329 */       if (this.renderManager.options.entityShadows && this.shadowSize > 0.0F && !entityIn.isInvisible() && this.renderManager.isRenderShadow()) {
/*     */         
/* 331 */         double d0 = this.renderManager.getDistanceToCamera(entityIn.posX, entityIn.posY, entityIn.posZ);
/* 332 */         float f = (float)((1.0D - d0 / 256.0D) * this.shadowOpaque);
/*     */         
/* 334 */         if (f > 0.0F)
/*     */         {
/* 336 */           renderShadow(entityIn, x, y, z, f, partialTicks);
/*     */         }
/*     */       } 
/*     */       
/* 340 */       if (entityIn.canRenderOnFire() && (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).isSpectator()))
/*     */       {
/* 342 */         renderEntityOnFire(entityIn, x, y, z, partialTicks);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRendererFromRenderManager() {
/* 352 */     return this.renderManager.getFontRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance) {
/* 360 */     double d0 = entityIn.getDistanceSqToEntity(this.renderManager.livingPlayer);
/*     */     
/* 362 */     if (d0 <= (maxDistance * maxDistance)) {
/*     */       
/* 364 */       FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 365 */       float f = 1.6F;
/* 366 */       float f1 = 0.016666668F * f;
/* 367 */       GlStateManager.pushMatrix();
/* 368 */       GlStateManager.translate((float)x + 0.0F, (float)y + ((Entity)entityIn).height + 0.5F, (float)z);
/* 369 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 370 */       GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 371 */       GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 372 */       GlStateManager.scale(-f1, -f1, f1);
/* 373 */       GlStateManager.disableLighting();
/* 374 */       GlStateManager.depthMask(false);
/* 375 */       GlStateManager.disableDepth();
/* 376 */       GlStateManager.enableBlend();
/* 377 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 378 */       Tessellator tessellator = Tessellator.getInstance();
/* 379 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 380 */       int i = 0;
/*     */       
/* 382 */       if (str.equals("deadmau5"))
/*     */       {
/* 384 */         i = -10;
/*     */       }
/*     */       
/* 387 */       int j = fontrenderer.getStringWidth(str) / 2;
/* 388 */       GlStateManager.disableTexture2D();
/* 389 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 390 */       worldrenderer.pos((-j - 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 391 */       worldrenderer.pos((-j - 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 392 */       worldrenderer.pos((j + 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 393 */       worldrenderer.pos((j + 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 394 */       tessellator.draw();
/* 395 */       GlStateManager.enableTexture2D();
/* 396 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
/* 397 */       GlStateManager.enableDepth();
/* 398 */       GlStateManager.depthMask(true);
/* 399 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
/* 400 */       GlStateManager.enableLighting();
/* 401 */       GlStateManager.disableBlend();
/* 402 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 403 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderManager getRenderManager() {
/* 409 */     return this.renderManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMultipass() {
/* 414 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderMultipass(T p_renderMultipass_1_, double p_renderMultipass_2_, double p_renderMultipass_4_, double p_renderMultipass_6_, float p_renderMultipass_8_, float p_renderMultipass_9_) {}
/*     */ 
/*     */   
/*     */   public Class getEntityClass() {
/* 423 */     return this.entityClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntityClass(Class p_setEntityClass_1_) {
/* 428 */     this.entityClass = p_setEntityClass_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationTextureCustom() {
/* 433 */     return this.locationTextureCustom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
/* 438 */     this.locationTextureCustom = p_setLocationTextureCustom_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setModelBipedMain(RenderBiped p_setModelBipedMain_0_, ModelBiped p_setModelBipedMain_1_) {
/* 443 */     p_setModelBipedMain_0_.modelBipedMain = p_setModelBipedMain_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\Render.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */