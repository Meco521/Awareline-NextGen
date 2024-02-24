/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureCompass;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderItemFrame extends Render<EntityItemFrame> {
/*  36 */   private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
/*  37 */   private final Minecraft mc = Minecraft.getMinecraft();
/*  38 */   private final ModelResourceLocation itemFrameModel = new ModelResourceLocation("item_frame", "normal");
/*  39 */   private final ModelResourceLocation mapModel = new ModelResourceLocation("item_frame", "map");
/*     */   private final RenderItem itemRenderer;
/*  41 */   private static double itemRenderDistanceSq = 4096.0D;
/*     */ 
/*     */   
/*     */   public RenderItemFrame(RenderManager renderManagerIn, RenderItem itemRendererIn) {
/*  45 */     super(renderManagerIn);
/*  46 */     this.itemRenderer = itemRendererIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*     */     IBakedModel ibakedmodel;
/*  54 */     GlStateManager.pushMatrix();
/*  55 */     BlockPos blockpos = entity.getHangingPosition();
/*  56 */     double d0 = blockpos.getX() - entity.posX + x;
/*  57 */     double d1 = blockpos.getY() - entity.posY + y;
/*  58 */     double d2 = blockpos.getZ() - entity.posZ + z;
/*  59 */     GlStateManager.translate(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D);
/*  60 */     GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  61 */     this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/*  62 */     BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
/*  63 */     ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
/*     */ 
/*     */     
/*  66 */     if (entity.getDisplayedItem() != null && entity.getDisplayedItem().getItem() == Items.filled_map) {
/*     */       
/*  68 */       ibakedmodel = modelmanager.getModel(this.mapModel);
/*     */     }
/*     */     else {
/*     */       
/*  72 */       ibakedmodel = modelmanager.getModel(this.itemFrameModel);
/*     */     } 
/*     */     
/*  75 */     GlStateManager.pushMatrix();
/*  76 */     GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*  77 */     blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
/*  78 */     GlStateManager.popMatrix();
/*  79 */     GlStateManager.translate(0.0F, 0.0F, 0.4375F);
/*  80 */     renderItem(entity);
/*  81 */     GlStateManager.popMatrix();
/*  82 */     renderName(entity, x + (entity.facingDirection.getFrontOffsetX() * 0.3F), y - 0.25D, z + (entity.facingDirection.getFrontOffsetZ() * 0.3F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityItemFrame entity) {
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderItem(EntityItemFrame itemFrame) {
/*  95 */     ItemStack itemstack = itemFrame.getDisplayedItem();
/*     */     
/*  97 */     if (itemstack != null) {
/*     */       
/*  99 */       if (!isRenderItem(itemFrame)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 104 */       if (!Config.zoomMode) {
/*     */         
/* 106 */         EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 107 */         double d0 = itemFrame.getDistanceSq(((Entity)entityPlayerSP).posX, ((Entity)entityPlayerSP).posY, ((Entity)entityPlayerSP).posZ);
/*     */         
/* 109 */         if (d0 > 4096.0D) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 115 */       EntityItem entityitem = new EntityItem(itemFrame.worldObj, 0.0D, 0.0D, 0.0D, itemstack);
/* 116 */       Item item = entityitem.getEntityItem().getItem();
/* 117 */       (entityitem.getEntityItem()).stackSize = 1;
/* 118 */       entityitem.hoverStart = 0.0F;
/* 119 */       GlStateManager.pushMatrix();
/* 120 */       GlStateManager.disableLighting();
/* 121 */       int i = itemFrame.getRotation();
/*     */       
/* 123 */       if (item instanceof net.minecraft.item.ItemMap)
/*     */       {
/* 125 */         i = i % 4 << 1;
/*     */       }
/*     */       
/* 128 */       GlStateManager.rotate(i * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
/*     */       
/* 130 */       if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, new Object[] { itemFrame, this }))
/*     */       {
/* 132 */         if (item instanceof net.minecraft.item.ItemMap) {
/*     */           
/* 134 */           this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
/* 135 */           GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 136 */           float f = 0.0078125F;
/* 137 */           GlStateManager.scale(f, f, f);
/* 138 */           GlStateManager.translate(-64.0F, -64.0F, 0.0F);
/* 139 */           MapData mapdata = Items.filled_map.getMapData(entityitem.getEntityItem(), itemFrame.worldObj);
/* 140 */           GlStateManager.translate(0.0F, 0.0F, -1.0F);
/*     */           
/* 142 */           if (mapdata != null)
/*     */           {
/* 144 */             this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, true);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 149 */           TextureAtlasSprite textureatlassprite = null;
/*     */           
/* 151 */           if (item == Items.compass) {
/*     */             
/* 153 */             textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite(TextureCompass.locationSprite);
/* 154 */             this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*     */             
/* 156 */             if (textureatlassprite instanceof TextureCompass) {
/*     */               
/* 158 */               TextureCompass texturecompass = (TextureCompass)textureatlassprite;
/* 159 */               double d1 = texturecompass.currentAngle;
/* 160 */               double d2 = texturecompass.angleDelta;
/* 161 */               texturecompass.currentAngle = 0.0D;
/* 162 */               texturecompass.angleDelta = 0.0D;
/* 163 */               texturecompass.updateCompass(itemFrame.worldObj, itemFrame.posX, itemFrame.posZ, MathHelper.wrapAngleTo180_float((180 + itemFrame.facingDirection.getHorizontalIndex() * 90)), false, true);
/* 164 */               texturecompass.currentAngle = d1;
/* 165 */               texturecompass.angleDelta = d2;
/*     */             }
/*     */             else {
/*     */               
/* 169 */               textureatlassprite = null;
/*     */             } 
/*     */           } 
/*     */           
/* 173 */           GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*     */           
/* 175 */           if (!this.itemRenderer.shouldRenderItemIn3D(entityitem.getEntityItem()) || item instanceof net.minecraft.item.ItemSkull)
/*     */           {
/* 177 */             GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*     */           }
/*     */           
/* 180 */           GlStateManager.pushAttrib();
/* 181 */           RenderHelper.enableStandardItemLighting();
/* 182 */           this.itemRenderer.renderItem(entityitem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
/* 183 */           RenderHelper.disableStandardItemLighting();
/* 184 */           GlStateManager.popAttrib();
/*     */           
/* 186 */           if (textureatlassprite != null && textureatlassprite.getFrameCount() > 0)
/*     */           {
/* 188 */             textureatlassprite.updateAnimation();
/*     */           }
/*     */         } 
/*     */       }
/* 192 */       GlStateManager.enableLighting();
/* 193 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderName(EntityItemFrame entity, double x, double y, double z) {
/* 199 */     if (Minecraft.isGuiEnabled() && entity.getDisplayedItem() != null && entity.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == entity) {
/*     */       
/* 201 */       float f = 1.6F;
/* 202 */       float f1 = 0.016666668F * f;
/* 203 */       double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 204 */       float f2 = entity.isSneaking() ? 32.0F : 64.0F;
/*     */       
/* 206 */       if (d0 < (f2 * f2)) {
/*     */         
/* 208 */         String s = entity.getDisplayedItem().getDisplayName();
/*     */         
/* 210 */         if (entity.isSneaking()) {
/*     */           
/* 212 */           FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 213 */           GlStateManager.pushMatrix();
/* 214 */           GlStateManager.translate((float)x + 0.0F, (float)y + entity.height + 0.5F, (float)z);
/* 215 */           GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 216 */           GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 217 */           GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 218 */           GlStateManager.scale(-f1, -f1, f1);
/* 219 */           GlStateManager.disableLighting();
/* 220 */           GlStateManager.translate(0.0F, 0.25F / f1, 0.0F);
/* 221 */           GlStateManager.depthMask(false);
/* 222 */           GlStateManager.enableBlend();
/* 223 */           GlStateManager.blendFunc(770, 771);
/* 224 */           Tessellator tessellator = Tessellator.getInstance();
/* 225 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 226 */           int i = fontrenderer.getStringWidth(s) / 2;
/* 227 */           GlStateManager.disableTexture2D();
/* 228 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 229 */           worldrenderer.pos((-i - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 230 */           worldrenderer.pos((-i - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 231 */           worldrenderer.pos((i + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 232 */           worldrenderer.pos((i + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 233 */           tessellator.draw();
/* 234 */           GlStateManager.enableTexture2D();
/* 235 */           GlStateManager.depthMask(true);
/* 236 */           fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
/* 237 */           GlStateManager.enableLighting();
/* 238 */           GlStateManager.disableBlend();
/* 239 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 240 */           GlStateManager.popMatrix();
/*     */         }
/*     */         else {
/*     */           
/* 244 */           renderLivingLabel((Entity)entity, s, x, y, z, 64);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isRenderItem(EntityItemFrame p_isRenderItem_1_) {
/* 252 */     if (Shaders.isShadowPass)
/*     */     {
/* 254 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 258 */     if (!Config.zoomMode) {
/*     */       
/* 260 */       Entity entity = this.mc.getRenderViewEntity();
/* 261 */       double d0 = p_isRenderItem_1_.getDistanceSq(entity.posX, entity.posY, entity.posZ);
/*     */       
/* 263 */       if (d0 > itemRenderDistanceSq)
/*     */       {
/* 265 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateItemRenderDistance() {
/* 275 */     Minecraft minecraft = Config.getMinecraft();
/* 276 */     double d0 = Config.limit(minecraft.gameSettings.fovSetting, 1.0F, 120.0F);
/* 277 */     double d1 = Math.max(6.0D * minecraft.displayHeight / d0, 16.0D);
/* 278 */     itemRenderDistanceSq = d1 * d1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\RenderItemFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */