/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import awareline.main.mod.implement.visual.ctype.Cape;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.io.File;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.IImageBuffer;
/*     */ import net.minecraft.client.renderer.ImageBufferDownload;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.optifine.player.CapeUtils;
/*     */ import net.optifine.player.PlayerConfigurations;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public abstract class AbstractClientPlayer extends EntityPlayer {
/*     */   private NetworkPlayerInfo playerInfo;
/*  30 */   private ResourceLocation locationOfCape = null;
/*  31 */   private long reloadCapeTimeMs = 0L;
/*     */   private boolean elytraOfCape = false;
/*  33 */   private String nameClear = null;
/*     */   
/*     */   public AbstractClientPlayer(World worldIn, GameProfile playerProfile) {
/*  36 */     super(worldIn, playerProfile);
/*  37 */     this.nameClear = playerProfile.getName();
/*     */     
/*  39 */     if (this.nameClear != null && !this.nameClear.isEmpty()) {
/*  40 */       this.nameClear = StringUtils.stripControlCodes(this.nameClear);
/*     */     }
/*     */     
/*  43 */     CapeUtils.downloadCape(this);
/*  44 */     PlayerConfigurations.getPlayerConfiguration(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  51 */     NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(getGameProfile().getId());
/*  52 */     return (networkplayerinfo != null && networkplayerinfo.getGameType() == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInfo() {
/*  59 */     return (getPlayerInfo() != null);
/*     */   }
/*     */   
/*     */   protected NetworkPlayerInfo getPlayerInfo() {
/*  63 */     if (this.playerInfo == null) {
/*  64 */       this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(getUniqueID());
/*     */     }
/*     */     
/*  67 */     return this.playerInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSkin() {
/*  74 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  75 */     return (networkplayerinfo != null && networkplayerinfo.hasLocationSkin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationSkin() {
/*  82 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  83 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getDefaultSkin(getUniqueID()) : networkplayerinfo.getLocationSkin();
/*     */   }
/*     */   
/*     */   public ResourceLocation getLocationCape() {
/*  87 */     if (!Config.isShowCapes()) {
/*  88 */       return null;
/*     */     }
/*  90 */     if (this.reloadCapeTimeMs != 0L && System.currentTimeMillis() > this.reloadCapeTimeMs) {
/*  91 */       CapeUtils.reloadCape(this);
/*  92 */       this.reloadCapeTimeMs = 0L;
/*     */     } 
/*     */     
/*  95 */     if (this.locationOfCape != null) {
/*  96 */       return this.locationOfCape;
/*     */     }
/*  98 */     if (this == (Minecraft.getMinecraft()).thePlayer && Cape.getInstance.isEnabled()) {
/*  99 */       return Cape.getInstance.getCape();
/*     */     }
/* 101 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 102 */     return (networkplayerinfo == null) ? null : networkplayerinfo.getLocationCape();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
/*     */     ThreadDownloadImageData threadDownloadImageData;
/* 108 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 109 */     ITextureObject itextureobject = texturemanager.getTexture(resourceLocationIn);
/*     */     
/* 111 */     if (itextureobject == null) {
/* 112 */       threadDownloadImageData = new ThreadDownloadImageData((File)null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(username) }), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), (IImageBuffer)new ImageBufferDownload());
/* 113 */       texturemanager.loadTexture(resourceLocationIn, (ITextureObject)threadDownloadImageData);
/*     */     } 
/*     */     
/* 116 */     return threadDownloadImageData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getLocationSkin(String username) {
/* 123 */     return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
/*     */   }
/*     */   
/*     */   public String getSkinType() {
/* 127 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 128 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getSkinType(getUniqueID()) : networkplayerinfo.getSkinType();
/*     */   }
/*     */   
/*     */   public float getFovModifier() {
/* 132 */     float f = 1.0F;
/*     */     
/* 134 */     if (this.capabilities.isFlying) {
/* 135 */       f *= 1.1F;
/*     */     }
/*     */     
/* 138 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 139 */     f = (float)(f * (iattributeinstance.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0D) / 2.0D);
/*     */     
/* 141 */     if (this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
/* 142 */       f = 1.0F;
/*     */     }
/*     */     
/* 145 */     if (isUsingItem() && getItemInUse().getItem() == Items.bow) {
/* 146 */       int i = getItemInUseDuration();
/* 147 */       float f1 = i / 20.0F;
/*     */       
/* 149 */       if (f1 > 1.0F) {
/* 150 */         f1 = 1.0F;
/*     */       } else {
/* 152 */         f1 *= f1;
/*     */       } 
/*     */       
/* 155 */       f *= 1.0F - f1 * 0.15F;
/*     */     } 
/*     */     
/* 158 */     return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, new Object[] { this, Float.valueOf(f) }) : f;
/*     */   }
/*     */   
/*     */   public String getNameClear() {
/* 162 */     return this.nameClear;
/*     */   }
/*     */   
/*     */   public ResourceLocation getLocationOfCape() {
/* 166 */     return this.locationOfCape;
/*     */   }
/*     */   
/*     */   public void setLocationOfCape(ResourceLocation p_setLocationOfCape_1_) {
/* 170 */     this.locationOfCape = p_setLocationOfCape_1_;
/*     */   }
/*     */   
/*     */   public boolean hasElytraCape() {
/* 174 */     ResourceLocation resourcelocation = getLocationCape();
/* 175 */     return (resourcelocation == null) ? false : ((resourcelocation == this.locationOfCape) ? this.elytraOfCape : true);
/*     */   }
/*     */   
/*     */   public void setElytraOfCape(boolean p_setElytraOfCape_1_) {
/* 179 */     this.elytraOfCape = p_setElytraOfCape_1_;
/*     */   }
/*     */   
/*     */   public boolean isElytraOfCape() {
/* 183 */     return this.elytraOfCape;
/*     */   }
/*     */   
/*     */   public long getReloadCapeTimeMs() {
/* 187 */     return this.reloadCapeTimeMs;
/*     */   }
/*     */   
/*     */   public void setReloadCapeTimeMs(long p_setReloadCapeTimeMs_1_) {
/* 191 */     this.reloadCapeTimeMs = p_setReloadCapeTimeMs_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getLook(float partialTicks) {
/* 198 */     return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\entity\AbstractClientPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */