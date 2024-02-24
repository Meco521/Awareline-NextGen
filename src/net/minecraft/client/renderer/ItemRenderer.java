/*     */ package net.minecraft.client.renderer;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.combat.TPAura;
/*     */ import awareline.main.mod.implement.combat.advanced.AdvancedAura;
/*     */ import awareline.main.mod.implement.globals.NoFireRender;
/*     */ import awareline.main.mod.implement.visual.Animations;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ItemRenderer {
/*  39 */   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
/*  40 */   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */ 
/*     */   
/*     */   private final RenderManager renderManager;
/*     */ 
/*     */   
/*     */   private final RenderItem itemRenderer;
/*     */   
/*     */   private ItemStack itemToRender;
/*     */   
/*     */   private float equippedProgress;
/*     */   
/*     */   private float prevEquippedProgress;
/*     */   
/*  57 */   private int equippedItemSlot = -1;
/*     */ 
/*     */   
/*     */   private long circleTicks;
/*     */ 
/*     */   
/*     */   public ItemRenderer(Minecraft mcIn) {
/*  64 */     this.mc = mcIn;
/*  65 */     this.renderManager = mcIn.getRenderManager();
/*  66 */     this.itemRenderer = mcIn.getRenderItem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBlockTranslucent(Block blockIn) {
/*  73 */     return (blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateArroundXAndY(float angle, float angleY) {
/*  82 */     GlStateManager.pushMatrix();
/*  83 */     GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
/*  84 */     GlStateManager.rotate(angleY, 0.0F, 1.0F, 0.0F);
/*  85 */     RenderHelper.enableStandardItemLighting();
/*  86 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks) {
/*  93 */     float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
/*  94 */     float f1 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
/*  95 */     GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
/*  96 */     GlStateManager.rotate((entityplayerspIn.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMapAngleFromPitch(float pitch) {
/* 105 */     float f = 1.0F - pitch / 45.0F + 0.1F;
/* 106 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 107 */     f = -MathHelper.cos(f * 3.1415927F) * 0.5F + 0.5F;
/* 108 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doItemUsedTransformations(float swingProgress) {
/* 120 */     float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 121 */     float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F);
/* 122 */     float f2 = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
/* 123 */     if (!Animations.getInstance.isEnabled()) {
/* 124 */       GlStateManager.translate(f, f1, f2);
/*     */     } else {
/* 126 */       float var2, var3, var4; if (((Boolean)Animations.getInstance.Smooth.getValue()).booleanValue()) {
/* 127 */         var2 = -0.15F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 128 */         var3 = -0.05F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 1.5F);
/* 129 */         var4 = -0.0F * MathHelper.sin(swingProgress * 3.1415927F);
/*     */       } else {
/* 131 */         var2 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 132 */         var3 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F);
/* 133 */         var4 = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
/*     */       } 
/* 135 */       GlStateManager.translate(var2 + ((Double)Animations.getInstance.swingx.getValue()).doubleValue(), var3 + ((Double)Animations.getInstance.swingy.getValue()).doubleValue(), var4 + ((Double)Animations.getInstance.swingz.getValue()).doubleValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformFirstPersonItem(float equipProgress, float swingProgress) {
/* 143 */     GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
/* 144 */     GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
/* 145 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 146 */     float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 147 */     float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 148 */     GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
/* 149 */     GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 150 */     GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 151 */     GlStateManager.scale(0.4F, 0.4F, 0.4F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doBlockTransformations() {
/* 158 */     GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/* 159 */     GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
/* 160 */     GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
/* 161 */     GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
/* 165 */     if (heldStack != null) {
/* 166 */       Item item = heldStack.getItem();
/* 167 */       Block block = Block.getBlockFromItem(item);
/* 168 */       GlStateManager.pushMatrix();
/*     */       
/* 170 */       if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
/* 171 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*     */         
/* 173 */         if (isBlockTranslucent(block) && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask)) {
/* 174 */           GlStateManager.depthMask(false);
/*     */         }
/*     */       } 
/*     */       
/* 178 */       this.itemRenderer.renderItemModelForEntity(heldStack, entityIn, transform);
/*     */       
/* 180 */       if (isBlockTranslucent(block)) {
/* 181 */         GlStateManager.depthMask(true);
/*     */       }
/*     */       
/* 184 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLightMapFromPlayer(AbstractClientPlayer clientPlayer) {
/* 192 */     int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);
/*     */     
/* 194 */     if (Config.isDynamicLights()) {
/* 195 */       i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
/*     */     }
/*     */     
/* 198 */     float f = (i & 0xFFFF);
/* 199 */     float f1 = (i >> 16);
/* 200 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
/*     */   }
/*     */   
/*     */   private void renderRightArm(RenderPlayer renderPlayerIn) {
/* 204 */     GlStateManager.pushMatrix();
/* 205 */     GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
/* 206 */     GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
/* 207 */     GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
/* 208 */     GlStateManager.translate(0.25F, -0.85F, 0.75F);
/* 209 */     renderPlayerIn.renderRightArm((AbstractClientPlayer)this.mc.thePlayer);
/* 210 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private void renderLeftArm(RenderPlayer renderPlayerIn) {
/* 214 */     GlStateManager.pushMatrix();
/* 215 */     GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
/* 216 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
/* 217 */     GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
/* 218 */     GlStateManager.translate(-0.3F, -1.1F, 0.45F);
/* 219 */     renderPlayerIn.renderLeftArm((AbstractClientPlayer)this.mc.thePlayer);
/* 220 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private void renderPlayerArms(AbstractClientPlayer clientPlayer) {
/* 224 */     this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
/* 225 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.thePlayer);
/* 226 */     RenderPlayer renderplayer = (RenderPlayer)render;
/*     */     
/* 228 */     if (!clientPlayer.isInvisible()) {
/* 229 */       GlStateManager.disableCull();
/* 230 */       renderRightArm(renderplayer);
/* 231 */       renderLeftArm(renderplayer);
/* 232 */       GlStateManager.enableCull();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderItemMap(AbstractClientPlayer clientPlayer, float pitch, float equipmentProgress, float swingProgress) {
/* 237 */     float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 238 */     float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F);
/* 239 */     float f2 = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
/* 240 */     GlStateManager.translate(f, f1, f2);
/* 241 */     float f3 = getMapAngleFromPitch(pitch);
/* 242 */     GlStateManager.translate(0.0F, 0.04F, -0.72F);
/* 243 */     GlStateManager.translate(0.0F, equipmentProgress * -1.2F, 0.0F);
/* 244 */     GlStateManager.translate(0.0F, f3 * -0.5F, 0.0F);
/* 245 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 246 */     GlStateManager.rotate(f3 * -85.0F, 0.0F, 0.0F, 1.0F);
/* 247 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 248 */     renderPlayerArms(clientPlayer);
/* 249 */     float f4 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 250 */     float f5 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 251 */     GlStateManager.rotate(f4 * -20.0F, 0.0F, 1.0F, 0.0F);
/* 252 */     GlStateManager.rotate(f5 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 253 */     GlStateManager.rotate(f5 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 254 */     GlStateManager.scale(0.38F, 0.38F, 0.38F);
/* 255 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 256 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 257 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 258 */     GlStateManager.translate(-1.0F, -1.0F, 0.0F);
/* 259 */     GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
/* 260 */     this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
/* 261 */     Tessellator tessellator = Tessellator.getInstance();
/* 262 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 263 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/* 264 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 265 */     worldrenderer.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 266 */     worldrenderer.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 267 */     worldrenderer.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 268 */     worldrenderer.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 269 */     tessellator.draw();
/* 270 */     MapData mapdata = Items.filled_map.getMapData(this.itemToRender, (World)this.mc.theWorld);
/*     */     
/* 272 */     if (mapdata != null) {
/* 273 */       this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderPlayerArm(AbstractClientPlayer clientPlayer, float equipProgress, float swingProgress) {
/* 284 */     float f = -0.3F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 285 */     float f1 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F);
/* 286 */     float f2 = -0.4F * MathHelper.sin(swingProgress * 3.1415927F);
/* 287 */     GlStateManager.translate(f, f1, f2);
/* 288 */     GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
/* 289 */     GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
/* 290 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 291 */     float f3 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 292 */     float f4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 293 */     GlStateManager.rotate(f4 * 70.0F, 0.0F, 1.0F, 0.0F);
/* 294 */     GlStateManager.rotate(f3 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 295 */     this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
/* 296 */     GlStateManager.translate(-1.0F, 3.6F, 3.5F);
/* 297 */     GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
/* 298 */     GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
/* 299 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/* 300 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 301 */     GlStateManager.translate(5.6F, 0.0F, 0.0F);
/* 302 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.thePlayer);
/* 303 */     GlStateManager.disableCull();
/* 304 */     RenderPlayer renderplayer = (RenderPlayer)render;
/* 305 */     renderplayer.renderRightArm((AbstractClientPlayer)this.mc.thePlayer);
/* 306 */     GlStateManager.enableCull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performDrinking(AbstractClientPlayer clientPlayer, float partialTicks) {
/* 315 */     float f = clientPlayer.getItemInUseCount() - partialTicks + 1.0F;
/* 316 */     float f1 = f / this.itemToRender.getMaxItemUseDuration();
/* 317 */     float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
/*     */     
/* 319 */     if (f1 >= 0.8F) {
/* 320 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 323 */     GlStateManager.translate(0.0F, f2, 0.0F);
/* 324 */     float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
/* 325 */     GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
/* 326 */     GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
/* 327 */     GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
/* 328 */     GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doBowTransformations(float partialTicks, AbstractClientPlayer clientPlayer) {
/* 337 */     GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
/* 338 */     GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
/* 339 */     GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
/* 340 */     GlStateManager.translate(-0.9F, 0.2F, 0.0F);
/* 341 */     float f = this.itemToRender.getMaxItemUseDuration() - clientPlayer.getItemInUseCount() - partialTicks + 1.0F;
/* 342 */     float f1 = f / 20.0F;
/* 343 */     f1 = (f1 * f1 + f1 * 2.0F) / 3.0F;
/*     */     
/* 345 */     if (f1 > 1.0F) {
/* 346 */       f1 = 1.0F;
/*     */     }
/*     */     
/* 349 */     if (f1 > 0.1F) {
/* 350 */       float f2 = MathHelper.sin((f - 0.1F) * 1.3F);
/* 351 */       float f3 = f1 - 0.1F;
/* 352 */       float f4 = f2 * f3;
/* 353 */       GlStateManager.translate(f4 * 0.0F, f4 * 0.01F, f4 * 0.0F);
/*     */     } 
/*     */     
/* 356 */     GlStateManager.translate(f1 * 0.0F, f1 * 0.0F, f1 * 0.1F);
/* 357 */     GlStateManager.scale(1.0F, 1.0F, 1.0F + f1 * 0.2F);
/*     */   }
/*     */   public boolean use(Entity entity) {
/* 360 */     boolean flag = ((Boolean)Animations.getInstance.LeftHand.getValue()).booleanValue();
/*     */     
/* 362 */     if (entity == null)
/* 363 */       return flag; 
/* 364 */     if (!((Boolean)Animations.getInstance.LeftHand.getValue()).booleanValue())
/* 365 */       return flag; 
/* 366 */     if (!(entity instanceof AbstractClientPlayer)) {
/* 367 */       return flag;
/*     */     }
/* 369 */     AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entity;
/* 370 */     return (abstractclientplayer.inventory == null) ? flag : ((abstractclientplayer.inventory.getCurrentItem() == null) ? flag : ((abstractclientplayer.inventory.getCurrentItem().getItem() == null) ? flag : ((abstractclientplayer.inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemBow != flag))));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean use(ItemStack itemToRender) {
/* 375 */     boolean flag = ((Boolean)Animations.getInstance.LeftHand.getValue()).booleanValue();
/* 376 */     return ((itemToRender == null || itemToRender.getItem() == null || !(itemToRender.getItem() instanceof net.minecraft.item.ItemMap)) && (!((Boolean)Animations.getInstance.LeftHand.getValue()).booleanValue() ? flag : ((itemToRender == null) ? flag : ((itemToRender.getItem() == null) ? flag : (itemToRender.getItem() instanceof net.minecraft.item.ItemBow != flag)))));
/*     */   }
/*     */   public void renderItemInFirstPerson(float partialTicks) {
/* 379 */     if (Animations.getInstance.isEnabled() && (
/* 380 */       (Boolean)Animations.getInstance.highHand.get()).booleanValue() && this.mc.thePlayer.getHeldItem() == null) {
/* 381 */       GL11.glTranslated(0.0D, 0.15D, 0.0D);
/*     */     }
/*     */     
/* 384 */     float var2 = 1.0F - this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks;
/* 385 */     float var4 = this.mc.thePlayer.getSwingProgress(partialTicks);
/* 386 */     float f = 1.0F - this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks;
/* 387 */     EntityPlayerSP entityplayersp = this.mc.thePlayer;
/* 388 */     float f1 = entityplayersp.getSwingProgress(partialTicks);
/* 389 */     float f2 = entityplayersp.prevRotationPitch + (entityplayersp.rotationPitch - entityplayersp.prevRotationPitch) * partialTicks;
/* 390 */     float f3 = entityplayersp.prevRotationYaw + (entityplayersp.rotationYaw - entityplayersp.prevRotationYaw) * partialTicks;
/* 391 */     rotateArroundXAndY(f2, f3);
/* 392 */     setLightMapFromPlayer((AbstractClientPlayer)entityplayersp);
/* 393 */     rotateWithPlayerRotations(entityplayersp, partialTicks);
/* 394 */     GlStateManager.enableRescaleNormal();
/* 395 */     GlStateManager.pushMatrix();
/* 396 */     if (use(this.itemToRender)) {
/* 397 */       GL11.glScaled(-1.0D, 1.0D, 1.0D);
/* 398 */       GlStateManager.disableCull();
/*     */     } else {
/* 400 */       GlStateManager.enableCull();
/*     */     } 
/* 402 */     if (this.itemToRender != null) {
/* 403 */       KillAura killAura = KillAura.getInstance;
/* 404 */       TPAura tpAura = TPAura.getInstance;
/* 405 */       boolean kaIsBlocking = (this.itemToRender.getItem() instanceof net.minecraft.item.ItemSword && killAura.isEnabled() && killAura.isBlocking);
/* 406 */       boolean tpAuraIsBlocking = (this.itemToRender.getItem() instanceof net.minecraft.item.ItemSword && tpAura.isEnabled() && tpAura.isBlocking);
/*     */       
/* 408 */       boolean cusAuraIsBlockingStatus = (this.itemToRender.getItem() instanceof net.minecraft.item.ItemSword && AdvancedAura.getInstance.isEnabled() && AdvancedAura.getInstance.getBlockingStatus());
/* 409 */       if (this.itemToRender.getItem() instanceof net.minecraft.item.ItemMap) {
/* 410 */         renderItemMap((AbstractClientPlayer)entityplayersp, f2, f, f1);
/* 411 */       } else if (entityplayersp.getItemInUseCount() > 0 || kaIsBlocking || tpAuraIsBlocking || cusAuraIsBlockingStatus) {
/*     */         float var9;
/*     */         
/* 414 */         EnumAction enumaction = this.itemToRender.getItemUseAction();
/*     */         
/* 416 */         switch (ItemRenderer$1.field_178094_a[enumaction.ordinal()]) {
/*     */           case 1:
/* 418 */             transformFirstPersonItem(f, 0.0F);
/* 419 */             if (Animations.getInstance.isEnabled()) {
/* 420 */               GlStateManager.translate(((Double)Animations.getInstance.swingx.getValue()).doubleValue(), ((Double)Animations.getInstance.swingy.getValue()).doubleValue(), ((Double)Animations.getInstance.swingz.getValue()).doubleValue());
/*     */             }
/*     */             break;
/*     */           
/*     */           case 2:
/* 425 */             if (Animations.getInstance.isEnabled()) {
/* 426 */               GlStateManager.translate(((Double)Animations.getInstance.swingx.getValue()).doubleValue(), ((Double)Animations.getInstance.swingy.getValue()).doubleValue(), ((Double)Animations.getInstance.swingz.getValue()).doubleValue());
/*     */             }
/*     */           case 3:
/* 429 */             performDrinking((AbstractClientPlayer)entityplayersp, partialTicks);
/* 430 */             transformFirstPersonItem(f, 0.0F);
/* 431 */             if (Animations.getInstance.isEnabled()) {
/* 432 */               GlStateManager.translate(((Double)Animations.getInstance.swingx.getValue()).doubleValue(), ((Double)Animations.getInstance.swingy.getValue()).doubleValue(), ((Double)Animations.getInstance.swingz.getValue()).doubleValue());
/*     */             }
/*     */             break;
/*     */           
/*     */           case 4:
/* 437 */             var9 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927F);
/* 438 */             if (Animations.getInstance.isEnabled()) {
/* 439 */               GL11.glTranslated(((Double)Animations.getInstance.x.getValue()).doubleValue(), ((Double)Animations.getInstance.y.getValue()).doubleValue(), ((Double)Animations.getInstance.z.getValue()).doubleValue());
/* 440 */               Animations animations = Animations.getInstance;
/* 441 */               if (((String)animations.mode.getValue()).equals("Slide")) {
/* 442 */                 transformFirstPersonItem(f, 0.0F);
/* 443 */                 doBlockTransformations();
/*     */                 
/* 445 */                 GL11.glRotatef(-var9 * 70.0F / 2.0F, -8.0F, 0.0F, 9.0F);
/* 446 */                 GL11.glRotatef(-var9 * 70.0F, 1.0F, -0.4F, -0.0F); break;
/*     */               } 
/* 448 */               if (((String)animations.mode.getValue()).equals("Thinking")) {
/* 449 */                 transformFirstPersonItem(var2 * 0.1F, 0.0F);
/* 450 */                 doBlockTransformations();
/* 451 */                 float f4 = (var4 > 0.5D) ? (1.0F - var4) : var4;
/* 452 */                 float var18 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927F);
/* 453 */                 GlStateManager.rotate(-var18 * 55.0F / 2.0F, -8.0F, -0.0F, 9.0F);
/* 454 */                 GlStateManager.rotate(-var18 * 45.0F, 1.0F, var18 / 12.0F, -0.0F);
/* 455 */                 GlStateManager.rotate(-f4 * 10.0F, 10.0F, 10.0F, -9.0F);
/* 456 */                 GlStateManager.translate(0.0F, 0.0F, 0.4F);
/* 457 */                 GL11.glTranslated(1.5D, 0.3D, 0.5D);
/* 458 */                 GL11.glTranslatef(-1.0F, this.mc.thePlayer.isSneaking() ? -0.9F : -0.2F, 0.2F); break;
/*     */               } 
/* 460 */               if (((String)animations.mode.getValue()).equals("Jigsaw")) {
/* 461 */                 Jigsaw(f, f1);
/* 462 */                 doBlockTransformations(); break;
/*     */               } 
/* 464 */               if (((String)animations.mode.getValue()).equals("Stitch")) {
/* 465 */                 transformFirstPersonItem(0.1F, f1);
/* 466 */                 doBlockTransformations();
/* 467 */                 GlStateManager.translate(-0.5D, 0.0D, 0.0D); break;
/*     */               } 
/* 469 */               if (((String)animations.mode.getValue()).equals("Jello2")) {
/* 470 */                 transformFirstPersonItem(0.0F, 0.0F);
/* 471 */                 doBlockTransformations();
/* 472 */                 int alpha = (int)Math.min(255L, (System.currentTimeMillis() % 255L > 127L) ? Math.abs(Math.abs(System.currentTimeMillis()) % 255L - 255L) : (System.currentTimeMillis() % 255L));
/* 473 */                 GlStateManager.translate(0.3F, -0.0F, 0.4F);
/* 474 */                 GlStateManager.rotate(0.0F, 0.0F, 0.0F, 1.0F);
/* 475 */                 GlStateManager.translate(0.0F, 0.5F, 0.0F);
/* 476 */                 GlStateManager.rotate(90.0F, 1.0F, 0.0F, -1.0F);
/* 477 */                 GlStateManager.translate(0.6F, 0.5F, 0.0F);
/* 478 */                 GlStateManager.rotate(-90.0F, 1.0F, 0.0F, -1.0F);
/* 479 */                 GlStateManager.rotate(-10.0F, 1.0F, 0.0F, -1.0F);
/* 480 */                 GlStateManager.rotate(this.mc.thePlayer.isSwingInProgress ? (-alpha / 5.0F) : 1.0F, 1.0F, -0.0F, 1.0F); break;
/*     */               } 
/* 482 */               if (((String)animations.mode.getValue()).equals("Astro")) {
/* 483 */                 transformFirstPersonItem(var2 / 2.0F, f1);
/* 484 */                 float var9e = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927F);
/* 485 */                 GlStateManager.rotate(var9e * 30.0F / 2.0F, -var9e, -0.0F, 9.0F);
/* 486 */                 GlStateManager.rotate(var9e * 40.0F, 1.0F, -var9e / 2.0F, -0.0F);
/* 487 */                 doBlockTransformations(); break;
/*     */               } 
/* 489 */               if (((String)animations.mode.getValue()).equals("Avatar")) {
/* 490 */                 Avatar(f1);
/* 491 */                 doBlockTransformations(); break;
/*     */               } 
/* 493 */               if (((String)animations.mode.getValue()).equals("Sigma")) {
/* 494 */                 float var15 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
/* 495 */                 transformFirstPersonItem(var2 * 0.5F, 0.0F);
/* 496 */                 GlStateManager.rotate(-var15 * 55.0F / 2.0F, -8.0F, -0.0F, 9.0F);
/* 497 */                 GlStateManager.rotate(-var15 * 45.0F, 1.0F, var15 / 2.0F, -0.0F);
/* 498 */                 doBlockTransformations();
/* 499 */                 GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/* 500 */                 GL11.glTranslated(1.2D, 0.3D, 0.5D);
/*     */                 
/* 502 */                 GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/* 503 */                 GlStateManager.scale(1.2F, 1.2F, 1.2F); break;
/*     */               } 
/* 505 */               if (((String)animations.mode.getValue()).equals("Remix")) {
/* 506 */                 transformFirstPersonItem(f, 0.83F);
/* 507 */                 doBlockTransformations();
/* 508 */                 float remixVar = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.83F);
/* 509 */                 GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/*     */                 
/* 511 */                 GlStateManager.rotate(-remixVar * 0.0F, 0.0F, 0.0F, 0.0F);
/* 512 */                 GlStateManager.rotate(-remixVar * 43.0F, 58.0F, 23.0F, 45.0F); break;
/*     */               } 
/* 514 */               if (((String)animations.mode.getValue()).equals("LiquidBounce")) {
/* 515 */                 transformFirstPersonItem(f + 0.1F, f1);
/* 516 */                 doBlockTransformations();
/* 517 */                 GlStateManager.translate(-0.5F, 0.2F, 0.0F); break;
/*     */               } 
/* 519 */               if (((String)animations.mode.getValue()).equals("Push")) {
/* 520 */                 transformFirstPersonItem(f, 0.0F);
/* 521 */                 GlStateManager.rotate(f1 * -15.0F, 0.0F, 0.0F, 0.0F);
/* 522 */                 doBlockTransformations(); break;
/*     */               } 
/* 524 */               if (((String)animations.mode.getValue()).equals("Target")) {
/* 525 */                 transformFirstPersonSwordBlock(f1);
/* 526 */                 doBlockTransformations(); break;
/*     */               } 
/* 528 */               if (((String)animations.mode.getValue()).equals("Circle")) {
/* 529 */                 this.circleTicks++;
/* 530 */                 GlStateManager.translate(-0.0F, -0.2F, -0.6F);
/* 531 */                 GlStateManager.rotate((float)-this.circleTicks * 0.07F * 50.0F, 0.0F, 0.0F, -1.0F);
/* 532 */                 GlStateManager.rotate(44.0F, 0.0F, 1.0F, 0.6F);
/* 533 */                 GlStateManager.rotate(44.0F, 1.0F, 0.0F, -0.6F);
/* 534 */                 GlStateManager.translate(1.0F, -0.2F, 0.5F);
/* 535 */                 GlStateManager.rotate(-44.0F, 1.0F, 0.0F, -0.6F);
/* 536 */                 GlStateManager.scale(0.5D, 0.5D, 0.5D);
/* 537 */                 doBlockTransformations(); break;
/*     */               } 
/* 539 */               if (((String)animations.mode.getValue()).equals("Rainy")) {
/* 540 */                 transformFirstPersonItem(f, 0.83F);
/* 541 */                 doBlockTransformations();
/* 542 */                 float f4 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.83F);
/* 543 */                 GlStateManager.translate(-0.0F, 0.2F, 0.2F);
/* 544 */                 GlStateManager.rotate(-f4 * 0.0F, 0.0F, 0.0F, 0.0F);
/* 545 */                 GlStateManager.rotate(-f4 * 43.0F, 58.0F, 23.0F, 45.0F); break;
/*     */               } 
/* 547 */               if (((String)animations.mode.getValue()).equals("Jello")) {
/* 548 */                 transformFirstPersonItem(f / 2.0F, 0.0F);
/* 549 */                 GL11.glRotatef(-var9 * 40.0F / 4.0F, -(var9 / 2.0F), -0.0F, -15.0F);
/* 550 */                 GL11.glRotatef(-var9 * 30.0F, -1.0F, -(var9 / 2.0F), -0.0F);
/* 551 */                 GlStateManager.rotate(-15.0F, 1.0F, 0.0F, -0.0F);
/* 552 */                 doBlockTransformations(); break;
/*     */               } 
/* 554 */               if (((String)animations.mode.getValue()).equals("Swang")) {
/* 555 */                 transformFirstPersonItem(f / 2.0F, 0.0F);
/* 556 */                 float var15 = MathHelper.sin(f1 * f1 * 3.1415927F);
/* 557 */                 GlStateManager.rotate(-var15 * 40.0F / 2.0F, var15 / 2.0F, -0.0F, 9.0F);
/* 558 */                 GlStateManager.rotate(-var15 * 30.0F, 1.0F, var15 / 2.0F, -0.0F);
/* 559 */                 doBlockTransformations(); break;
/*     */               } 
/* 561 */               if (((String)animations.mode.getValue()).equals("Swank")) {
/* 562 */                 transformFirstPersonItem(var2 / 2.0F, var4);
/* 563 */                 float var15 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
/* 564 */                 GlStateManager.rotate(var15 * 30.0F, -var15, -0.0F, 9.0F);
/* 565 */                 GlStateManager.rotate(var15 * 50.0F, 1.0F, -var15, -0.0F);
/* 566 */                 doBlockTransformations(); break;
/*     */               } 
/* 568 */               if (((String)animations.mode.getValue()).equals("Stella")) {
/* 569 */                 transformFirstPersonItem(-0.1F, f1);
/* 570 */                 GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/*     */                 
/* 572 */                 GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
/* 573 */                 GlStateManager.rotate(-70.0F, 1.0F, 0.0F, 0.0F);
/* 574 */                 GlStateManager.rotate(40.0F, 0.0F, 1.0F, 0.0F); break;
/*     */               } 
/* 576 */               if (((String)animations.mode.getValue()).equals("Smooth")) {
/* 577 */                 transformFirstPersonItem(f / 1.5F, 0.0F);
/* 578 */                 func_178103_d(0.2F);
/* 579 */                 GlStateManager.translate(-0.05F, 0.3F, 0.3F);
/* 580 */                 GlStateManager.rotate(-var9 * 140.0F, 8.0F, 0.0F, 8.0F);
/* 581 */                 GlStateManager.rotate(var9 * 90.0F, 8.0F, 0.0F, 8.0F); break;
/*     */               } 
/* 583 */               if (animations.mode.is("Smooth2")) {
/* 584 */                 float animationProgression = 0.0F;
/* 585 */                 float convertedProgress = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927F);
/* 586 */                 transformFirstPersonItem(0.0F, 0.0F);
/* 587 */                 float y = -convertedProgress * 2.0F;
/* 588 */                 GlStateManager.translate(0.0F, y / 10.0F + 0.1F, 0.0F);
/* 589 */                 GlStateManager.rotate(y * 10.0F, 0.0F, 1.0F, 0.0F);
/* 590 */                 GlStateManager.rotate(250.0F, 0.2F, 1.0F, -0.6F);
/* 591 */                 GlStateManager.rotate(-10.0F, 1.0F, 0.5F, 1.0F);
/* 592 */                 GlStateManager.rotate(-y * 20.0F, 1.0F, 0.5F, 1.0F); break;
/*     */               } 
/* 594 */               if (((String)animations.mode.getValue()).equals("1.7")) {
/* 595 */                 transformFirstPersonItem(f, f1);
/* 596 */                 doBlockTransformations(); break;
/*     */               } 
/* 598 */               if (((String)animations.mode.getValue()).equals("Exhibition")) {
/* 599 */                 transformFirstPersonItem(f / 2.0F, 0.0F);
/* 600 */                 GL11.glRotatef(-var9 * 40.0F / 2.0F, var9 / 2.0F, -0.0F, 9.0F);
/*     */                 
/* 602 */                 GL11.glRotatef(-var9 * 30.0F, 1.0F, var9 / 2.0F, -0.0F);
/*     */                 
/* 604 */                 doBlockTransformations(); break;
/*     */               } 
/* 606 */               if (((String)animations.mode.getValue()).equals("Lucky")) {
/* 607 */                 GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
/* 608 */                 GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 609 */                 float ff = MathHelper.sin(0.0F);
/* 610 */                 float f1f = MathHelper.sin(MathHelper.sqrt_float(0.0F) * 3.1415927F);
/* 611 */                 GlStateManager.rotate(ff * -20.0F, 0.0F, 1.0F, 0.0F);
/* 612 */                 GlStateManager.rotate(f1f * -20.0F, 0.0F, 0.0F, 1.0F);
/* 613 */                 GlStateManager.rotate(f1f * -80.0F, 1.0F, 0.0F, 0.0F);
/* 614 */                 GlStateManager.scale(0.4F, 0.4F, 0.4F);
/* 615 */                 doBlockTransformations();
/* 616 */                 float f4 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.83F);
/* 617 */                 GlStateManager.translate(-0.0F, 0.2F, -0.2F);
/* 618 */                 GlStateManager.rotate(-f4 * 0.0F, 0.0F, 0.0F, 0.0F);
/* 619 */                 GlStateManager.rotate(-f4 * 7.0F, 58.0F, 23.0F, 45.0F);
/*     */               } 
/*     */               break;
/*     */             } 
/* 623 */             doItemUsedTransformations(f1);
/* 624 */             transformFirstPersonItem(f, f1);
/*     */             break;
/*     */           
/*     */           case 5:
/* 628 */             transformFirstPersonItem(f, 0.0F);
/* 629 */             doBowTransformations(partialTicks, (AbstractClientPlayer)entityplayersp);
/* 630 */             if (Animations.getInstance.isEnabled())
/* 631 */               GlStateManager.translate(((Double)Animations.getInstance.swingx.getValue()).doubleValue(), ((Double)Animations.getInstance.swingy.getValue()).doubleValue(), ((Double)Animations.getInstance.swingz.getValue()).doubleValue()); 
/*     */             break;
/*     */         } 
/*     */       } else {
/* 635 */         doItemUsedTransformations(f1);
/* 636 */         transformFirstPersonItem(f, f1);
/*     */       } 
/*     */       
/* 639 */       renderItem((EntityLivingBase)entityplayersp, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
/* 640 */     } else if (!entityplayersp.isInvisible()) {
/* 641 */       renderPlayerArm((AbstractClientPlayer)entityplayersp, f, f1);
/*     */     } 
/*     */     
/* 644 */     GlStateManager.popMatrix();
/* 645 */     GlStateManager.disableRescaleNormal();
/* 646 */     RenderHelper.disableStandardItemLighting();
/*     */   }
/*     */   
/*     */   private void Avatar(float swingProgress) {
/* 650 */     GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
/* 651 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 652 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 653 */     float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 654 */     float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 655 */     GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
/* 656 */     GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 657 */     GlStateManager.rotate(f1 * -40.0F, 1.0F, 0.0F, 0.0F);
/* 658 */     GlStateManager.scale(0.4F, 0.4F, 0.4F);
/*     */   }
/*     */   
/*     */   private void Jigsaw(float var2, float swingProgress) {
/* 662 */     GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
/* 663 */     GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
/* 664 */     float v = swingProgress * 0.8F - swingProgress * swingProgress * 0.8F;
/* 665 */     GlStateManager.rotate(45.0F, 0.0F, 2.0F + v * 0.5F, v * 3.0F);
/* 666 */     GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
/* 667 */     GlStateManager.scale(0.37F, 0.37F, 0.37F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOverlays(float partialTicks) {
/* 675 */     GlStateManager.disableAlpha();
/*     */     
/* 677 */     if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
/* 678 */       IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos((Entity)this.mc.thePlayer));
/* 679 */       BlockPos blockpos = new BlockPos((Entity)this.mc.thePlayer);
/* 680 */       EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/*     */       
/* 682 */       for (int i = 0; i < 8; i++) {
/* 683 */         double d0 = ((EntityPlayer)entityPlayerSP).posX + (((i % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 684 */         double d1 = ((EntityPlayer)entityPlayerSP).posY + ((((i >> 1) % 2) - 0.5F) * 0.1F);
/* 685 */         double d2 = ((EntityPlayer)entityPlayerSP).posZ + ((((i >> 2) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 686 */         BlockPos blockpos1 = new BlockPos(d0, d1 + entityPlayerSP.getEyeHeight(), d2);
/* 687 */         IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos1);
/*     */         
/* 689 */         if (iblockstate1.getBlock().isVisuallyOpaque()) {
/* 690 */           iblockstate = iblockstate1;
/* 691 */           blockpos = blockpos1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 696 */       Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
/*     */       
/* 698 */       if (iblockstate.getBlock().getRenderType() != -1 && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks), object, iblockstate, blockpos })) {
/* 699 */         renderBlockInHand(this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 704 */     if (!this.mc.thePlayer.isSpectator()) {
/* 705 */       if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks) })) {
/* 706 */         renderWaterOverlayTexture(partialTicks);
/*     */       }
/*     */       
/* 709 */       if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks) })) {
/* 710 */         renderFireInFirstPerson();
/*     */       }
/*     */     } 
/*     */     
/* 714 */     GlStateManager.enableAlpha();
/*     */   }
/*     */   
/*     */   private void transformFirstPersonSwordBlock(float swingProgress) {
/* 718 */     GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
/* 719 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 720 */     float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 721 */     GlStateManager.rotate(f1 * -70.0F, 1.0F, 0.0F, 0.0F);
/* 722 */     GlStateManager.scale(0.4F, 0.4F, 0.4F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderBlockInHand(TextureAtlasSprite atlas) {
/* 731 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 732 */     Tessellator tessellator = Tessellator.getInstance();
/* 733 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 734 */     GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
/* 735 */     GlStateManager.pushMatrix();
/* 736 */     float f6 = atlas.getMinU();
/* 737 */     float f7 = atlas.getMaxU();
/* 738 */     float f8 = atlas.getMinV();
/* 739 */     float f9 = atlas.getMaxV();
/* 740 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 741 */     worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex(f7, f9).endVertex();
/* 742 */     worldrenderer.pos(1.0D, -1.0D, -0.5D).tex(f6, f9).endVertex();
/* 743 */     worldrenderer.pos(1.0D, 1.0D, -0.5D).tex(f6, f8).endVertex();
/* 744 */     worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex(f7, f8).endVertex();
/* 745 */     tessellator.draw();
/* 746 */     GlStateManager.popMatrix();
/* 747 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderWaterOverlayTexture(float partialTicks) {
/* 757 */     if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
/* 758 */       Minecraft.getMinecraft().getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
/* 759 */       Tessellator tessellator = Tessellator.getInstance();
/* 760 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 761 */       float f = this.mc.thePlayer.getBrightness(partialTicks);
/* 762 */       GlStateManager.color(f, f, f, 0.5F);
/* 763 */       GlStateManager.enableBlend();
/* 764 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 765 */       GlStateManager.pushMatrix();
/* 766 */       float f7 = -this.mc.thePlayer.rotationYaw / 64.0F;
/* 767 */       float f8 = this.mc.thePlayer.rotationPitch / 64.0F;
/* 768 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 769 */       worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex((4.0F + f7), (4.0F + f8)).endVertex();
/* 770 */       worldrenderer.pos(1.0D, -1.0D, -0.5D).tex((0.0F + f7), (4.0F + f8)).endVertex();
/* 771 */       worldrenderer.pos(1.0D, 1.0D, -0.5D).tex((0.0F + f7), (0.0F + f8)).endVertex();
/* 772 */       worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex((4.0F + f7), (0.0F + f8)).endVertex();
/* 773 */       tessellator.draw();
/* 774 */       GlStateManager.popMatrix();
/* 775 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 776 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderFireInFirstPerson() {
/* 784 */     Tessellator tessellator = Tessellator.getInstance();
/* 785 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 786 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
/* 787 */     GlStateManager.depthFunc(519);
/* 788 */     GlStateManager.depthMask(false);
/* 789 */     GlStateManager.enableBlend();
/* 790 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 791 */     float f = 1.0F;
/*     */     
/* 793 */     for (int i = 0; i < 2; i++) {
/* 794 */       GlStateManager.pushMatrix();
/* 795 */       TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 796 */       this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 797 */       float f1 = textureatlassprite.getMinU();
/* 798 */       float f2 = textureatlassprite.getMaxU();
/* 799 */       float f3 = textureatlassprite.getMinV();
/* 800 */       float f4 = textureatlassprite.getMaxV();
/* 801 */       float f5 = (0.0F - f) / 2.0F;
/* 802 */       float f6 = f5 + f;
/* 803 */       float f7 = 0.0F;
/* 804 */       if (NoFireRender.getInstance.isEnabled()) {
/* 805 */         f7 += 3.0F;
/*     */       } else {
/* 807 */         f7 = 0.0F - f / 2.0F;
/*     */       } 
/* 809 */       float f8 = f7 + f;
/* 810 */       float f9 = -0.5F;
/* 811 */       GlStateManager.translate(-((i << 1) - 1) * 0.24F, -0.3F, 0.0F);
/* 812 */       GlStateManager.rotate(((i << 1) - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
/* 813 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 814 */       worldrenderer.setSprite(textureatlassprite);
/* 815 */       worldrenderer.pos(f5, f7, f9).tex(f2, f4).endVertex();
/* 816 */       worldrenderer.pos(f6, f7, f9).tex(f1, f4).endVertex();
/* 817 */       worldrenderer.pos(f6, f8, f9).tex(f1, f3).endVertex();
/* 818 */       worldrenderer.pos(f5, f8, f9).tex(f2, f3).endVertex();
/* 819 */       tessellator.draw();
/* 820 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 823 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 824 */     GlStateManager.disableBlend();
/* 825 */     GlStateManager.depthMask(true);
/* 826 */     GlStateManager.depthFunc(515);
/*     */   }
/*     */   
/*     */   private void func_178103_d(float qq) {
/* 830 */     GlStateManager.translate(-0.5F, qq, 0.0F);
/* 831 */     GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
/* 832 */     GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
/* 833 */     GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public void updateEquippedItem() {
/* 837 */     this.prevEquippedProgress = this.equippedProgress;
/* 838 */     EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 839 */     ItemStack itemstack = ((EntityPlayer)entityPlayerSP).inventory.getCurrentItem();
/* 840 */     boolean flag = false;
/*     */     
/* 842 */     if (this.itemToRender != null && itemstack != null)
/* 843 */     { if (!this.itemToRender.getIsItemStackEqual(itemstack)) {
/* 844 */         if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists()) {
/* 845 */           boolean flag1 = Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, new Object[] { this.itemToRender, itemstack, Boolean.valueOf((this.equippedItemSlot != ((EntityPlayer)entityPlayerSP).inventory.currentItem)) });
/*     */           
/* 847 */           if (!flag1) {
/* 848 */             this.itemToRender = itemstack;
/* 849 */             this.equippedItemSlot = ((EntityPlayer)entityPlayerSP).inventory.currentItem;
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 854 */         flag = true;
/*     */       }  }
/* 856 */     else { flag = (this.itemToRender != null || itemstack != null); }
/*     */     
/* 858 */     float f2 = 0.4F;
/* 859 */     float f = flag ? 0.0F : 1.0F;
/* 860 */     float f1 = MathHelper.clamp_float(f - this.equippedProgress, -0.4F, 0.4F);
/* 861 */     this.equippedProgress += f1;
/*     */     
/* 863 */     if (this.equippedProgress < 0.1F) {
/* 864 */       this.itemToRender = itemstack;
/* 865 */       this.equippedItemSlot = ((EntityPlayer)entityPlayerSP).inventory.currentItem;
/*     */       
/* 867 */       if (Config.isShaders()) {
/* 868 */         Shaders.setItemToRenderMain(itemstack);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress() {
/* 877 */     this.equippedProgress = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress2() {
/* 884 */     this.equippedProgress = 0.0F;
/*     */   }
/*     */   
/*     */   static final class ItemRenderer$1 {
/* 888 */     static final int[] field_178094_a = new int[(EnumAction.values()).length];
/*     */     
/*     */     static {
/*     */       try {
/* 892 */         field_178094_a[EnumAction.NONE.ordinal()] = 1;
/* 893 */       } catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */       
/*     */       try {
/* 897 */         field_178094_a[EnumAction.EAT.ordinal()] = 2;
/* 898 */       } catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */       
/*     */       try {
/* 902 */         field_178094_a[EnumAction.DRINK.ordinal()] = 3;
/* 903 */       } catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */       
/*     */       try {
/* 907 */         field_178094_a[EnumAction.BLOCK.ordinal()] = 4;
/* 908 */       } catch (NoSuchFieldError noSuchFieldError) {}
/*     */ 
/*     */       
/*     */       try {
/* 912 */         field_178094_a[EnumAction.BOW.ordinal()] = 5;
/* 913 */       } catch (NoSuchFieldError noSuchFieldError) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\ItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */