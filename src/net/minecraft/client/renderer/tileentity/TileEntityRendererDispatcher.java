/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.EmissiveTextures;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class TileEntityRendererDispatcher {
/*  27 */   public Map<Class, TileEntitySpecialRenderer> mapSpecialRenderers = Maps.newHashMap();
/*  28 */   public static TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
/*     */   
/*     */   public FontRenderer fontRenderer;
/*     */   
/*     */   public static double staticPlayerX;
/*     */   
/*     */   public static double staticPlayerY;
/*     */   
/*     */   public static double staticPlayerZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   public Entity entity;
/*     */   public float entityYaw;
/*     */   public float entityPitch;
/*     */   public double entityX;
/*     */   public double entityY;
/*     */   public double entityZ;
/*     */   public TileEntity tileEntityRendered;
/*  48 */   private final Tessellator batchBuffer = new Tessellator(2097152);
/*     */   
/*     */   private boolean drawingBatch = false;
/*     */   
/*     */   private TileEntityRendererDispatcher() {
/*  53 */     this.mapSpecialRenderers.put(TileEntitySign.class, new TileEntitySignRenderer());
/*  54 */     this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
/*  55 */     this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
/*  56 */     this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
/*  57 */     this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
/*  58 */     this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
/*  59 */     this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
/*  60 */     this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
/*  61 */     this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
/*  62 */     this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
/*     */     
/*  64 */     for (TileEntitySpecialRenderer<?> tileentityspecialrenderer : this.mapSpecialRenderers.values())
/*     */     {
/*  66 */       tileentityspecialrenderer.setRendererDispatcher(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRendererByClass(Class<? extends TileEntity> teClass) {
/*  72 */     TileEntitySpecialRenderer<? extends TileEntity> tileentityspecialrenderer = this.mapSpecialRenderers.get(teClass);
/*     */     
/*  74 */     if (tileentityspecialrenderer == null && teClass != TileEntity.class) {
/*     */       
/*  76 */       tileentityspecialrenderer = getSpecialRendererByClass((Class)teClass.getSuperclass());
/*  77 */       this.mapSpecialRenderers.put(teClass, tileentityspecialrenderer);
/*     */     } 
/*     */     
/*  80 */     return (TileEntitySpecialRenderer)tileentityspecialrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRenderer(TileEntity tileEntityIn) {
/*  85 */     return (tileEntityIn != null && !tileEntityIn.isInvalid()) ? getSpecialRendererByClass((Class)tileEntityIn.getClass()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cacheActiveRenderInfo(World worldIn, TextureManager textureManagerIn, FontRenderer fontrendererIn, Entity entityIn, float partialTicks) {
/*  90 */     if (this.worldObj != worldIn)
/*     */     {
/*  92 */       this.worldObj = worldIn;
/*     */     }
/*     */     
/*  95 */     this.renderEngine = textureManagerIn;
/*  96 */     this.entity = entityIn;
/*  97 */     this.fontRenderer = fontrendererIn;
/*  98 */     this.entityYaw = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
/*  99 */     this.entityPitch = entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks;
/* 100 */     this.entityX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 101 */     this.entityY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 102 */     this.entityZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTileEntity(TileEntity tileentityIn, float partialTicks, int destroyStage) {
/* 107 */     if (tileentityIn.getDistanceSq(this.entityX, this.entityY, this.entityZ) < tileentityIn.getMaxRenderDistanceSquared()) {
/*     */       
/* 109 */       boolean flag = true;
/*     */       
/* 111 */       if (Reflector.ForgeTileEntity_hasFastRenderer.exists())
/*     */       {
/* 113 */         flag = (!this.drawingBatch || !Reflector.callBoolean(tileentityIn, Reflector.ForgeTileEntity_hasFastRenderer, new Object[0]));
/*     */       }
/*     */       
/* 116 */       if (flag) {
/*     */         
/* 118 */         RenderHelper.enableStandardItemLighting();
/* 119 */         int i = this.worldObj.getCombinedLight(tileentityIn.getPos(), 0);
/* 120 */         int j = i % 65536;
/* 121 */         int k = i / 65536;
/* 122 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 123 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 126 */       BlockPos blockpos = tileentityIn.getPos();
/*     */       
/* 128 */       if (!this.worldObj.isBlockLoaded(blockpos, false)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 133 */       if (EmissiveTextures.isActive())
/*     */       {
/* 135 */         EmissiveTextures.beginRender();
/*     */       }
/*     */       
/* 138 */       renderTileEntityAt(tileentityIn, blockpos.getX() - staticPlayerX, blockpos.getY() - staticPlayerY, blockpos.getZ() - staticPlayerZ, partialTicks, destroyStage);
/*     */       
/* 140 */       if (EmissiveTextures.isActive()) {
/*     */         
/* 142 */         if (EmissiveTextures.hasEmissive()) {
/*     */           
/* 144 */           EmissiveTextures.beginRenderEmissive();
/* 145 */           renderTileEntityAt(tileentityIn, blockpos.getX() - staticPlayerX, blockpos.getY() - staticPlayerY, blockpos.getZ() - staticPlayerZ, partialTicks, destroyStage);
/* 146 */           EmissiveTextures.endRenderEmissive();
/*     */         } 
/*     */         
/* 149 */         EmissiveTextures.endRender();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntity tileEntityIn, double x, double y, double z, float partialTicks) {
/* 159 */     renderTileEntityAt(tileEntityIn, x, y, z, partialTicks, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
/* 164 */     TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = getSpecialRenderer(tileEntityIn);
/*     */     
/* 166 */     if (tileentityspecialrenderer != null) {
/*     */       
/*     */       try {
/*     */         
/* 170 */         this.tileEntityRendered = tileEntityIn;
/*     */         
/* 172 */         if (this.drawingBatch && Reflector.callBoolean(tileEntityIn, Reflector.ForgeTileEntity_hasFastRenderer, new Object[0])) {
/*     */           
/* 174 */           tileentityspecialrenderer.renderTileEntityFast(tileEntityIn, x, y, z, partialTicks, destroyStage, this.batchBuffer.getWorldRenderer());
/*     */         }
/*     */         else {
/*     */           
/* 178 */           tileentityspecialrenderer.renderTileEntityAt(tileEntityIn, x, y, z, partialTicks, destroyStage);
/*     */         } 
/*     */         
/* 181 */         this.tileEntityRendered = null;
/*     */       }
/* 183 */       catch (Throwable throwable) {
/*     */         
/* 185 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Block Entity");
/* 186 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block Entity Details");
/* 187 */         tileEntityIn.addInfoToCrashReport(crashreportcategory);
/* 188 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorld(World worldIn) {
/* 195 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 200 */     return this.fontRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDrawBatch() {
/* 205 */     this.batchBuffer.getWorldRenderer().begin(7, DefaultVertexFormats.BLOCK);
/* 206 */     this.drawingBatch = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBatch(int p_drawBatch_1_) {
/* 211 */     this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 212 */     RenderHelper.disableStandardItemLighting();
/* 213 */     GlStateManager.blendFunc(770, 771);
/* 214 */     GlStateManager.enableBlend();
/* 215 */     GlStateManager.disableCull();
/*     */     
/* 217 */     if (Minecraft.isAmbientOcclusionEnabled()) {
/*     */       
/* 219 */       GlStateManager.shadeModel(7425);
/*     */     }
/*     */     else {
/*     */       
/* 223 */       GlStateManager.shadeModel(7424);
/*     */     } 
/*     */     
/* 226 */     if (p_drawBatch_1_ > 0)
/*     */     {
/* 228 */       this.batchBuffer.getWorldRenderer().sortVertexData((float)staticPlayerX, (float)staticPlayerY, (float)staticPlayerZ);
/*     */     }
/*     */     
/* 231 */     this.batchBuffer.draw();
/* 232 */     RenderHelper.enableStandardItemLighting();
/* 233 */     this.drawingBatch = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntityRendererDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */