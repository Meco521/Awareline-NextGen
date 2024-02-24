/*     */ package net.optifine.player;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.image.BufferedImage;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class PlayerItemModel
/*     */ {
/*  18 */   private Dimension textureSize = null;
/*     */   private boolean usePlayerTexture = false;
/*  20 */   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
/*  21 */   private ResourceLocation textureLocation = null;
/*  22 */   private BufferedImage textureImage = null;
/*  23 */   private DynamicTexture texture = null;
/*  24 */   private final ResourceLocation locationMissing = new ResourceLocation("textures/blocks/wool_colored_red.png");
/*     */   
/*     */   public static final int ATTACH_BODY = 0;
/*     */   public static final int ATTACH_HEAD = 1;
/*     */   public static final int ATTACH_LEFT_ARM = 2;
/*     */   public static final int ATTACH_RIGHT_ARM = 3;
/*     */   public static final int ATTACH_LEFT_LEG = 4;
/*     */   public static final int ATTACH_RIGHT_LEG = 5;
/*     */   public static final int ATTACH_CAPE = 6;
/*     */   
/*     */   public PlayerItemModel(Dimension textureSize, boolean usePlayerTexture, PlayerItemRenderer[] modelRenderers) {
/*  35 */     this.textureSize = textureSize;
/*  36 */     this.usePlayerTexture = usePlayerTexture;
/*  37 */     this.modelRenderers = modelRenderers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
/*  42 */     TextureManager texturemanager = Config.getTextureManager();
/*     */     
/*  44 */     if (this.usePlayerTexture) {
/*     */       
/*  46 */       texturemanager.bindTexture(player.getLocationSkin());
/*     */     }
/*  48 */     else if (this.textureLocation != null) {
/*     */       
/*  50 */       if (this.texture == null && this.textureImage != null) {
/*     */         
/*  52 */         this.texture = new DynamicTexture(this.textureImage);
/*  53 */         Minecraft.getMinecraft().getTextureManager().loadTexture(this.textureLocation, (ITextureObject)this.texture);
/*     */       } 
/*     */       
/*  56 */       texturemanager.bindTexture(this.textureLocation);
/*     */     }
/*     */     else {
/*     */       
/*  60 */       texturemanager.bindTexture(this.locationMissing);
/*     */     } 
/*     */     
/*  63 */     for (int i = 0; i < this.modelRenderers.length; i++) {
/*     */       
/*  65 */       PlayerItemRenderer playeritemrenderer = this.modelRenderers[i];
/*  66 */       GlStateManager.pushMatrix();
/*     */       
/*  68 */       if (player.isSneaking())
/*     */       {
/*  70 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  73 */       playeritemrenderer.render(modelBiped, scale);
/*  74 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelRenderer getAttachModel(ModelBiped modelBiped, int attachTo) {
/*  80 */     switch (attachTo) {
/*     */       
/*     */       case 0:
/*  83 */         return modelBiped.bipedBody;
/*     */       
/*     */       case 1:
/*  86 */         return modelBiped.bipedHead;
/*     */       
/*     */       case 2:
/*  89 */         return modelBiped.bipedLeftArm;
/*     */       
/*     */       case 3:
/*  92 */         return modelBiped.bipedRightArm;
/*     */       
/*     */       case 4:
/*  95 */         return modelBiped.bipedLeftLeg;
/*     */       
/*     */       case 5:
/*  98 */         return modelBiped.bipedRightLeg;
/*     */     } 
/*     */     
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage getTextureImage() {
/* 107 */     return this.textureImage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureImage(BufferedImage textureImage) {
/* 112 */     this.textureImage = textureImage;
/*     */   }
/*     */ 
/*     */   
/*     */   public DynamicTexture getTexture() {
/* 117 */     return this.texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation() {
/* 122 */     return this.textureLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureLocation(ResourceLocation textureLocation) {
/* 127 */     this.textureLocation = textureLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsePlayerTexture() {
/* 132 */     return this.usePlayerTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\PlayerItemModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */