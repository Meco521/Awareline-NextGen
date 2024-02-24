/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBanner;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TileEntityBannerRenderer extends TileEntitySpecialRenderer<TileEntityBanner> {
/*  22 */   private static final Map<String, TimedBannerTexture> DESIGNS = Maps.newHashMap();
/*  23 */   private static final ResourceLocation BANNERTEXTURES = new ResourceLocation("textures/entity/banner_base.png");
/*  24 */   private final ModelBanner bannerModel = new ModelBanner();
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntityBanner te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  28 */     boolean flag = (te.getWorld() != null);
/*  29 */     boolean flag1 = (!flag || te.getBlockType() == Blocks.standing_banner);
/*  30 */     int i = flag ? te.getBlockMetadata() : 0;
/*  31 */     long j = flag ? te.getWorld().getTotalWorldTime() : 0L;
/*  32 */     GlStateManager.pushMatrix();
/*  33 */     float f = 0.6666667F;
/*     */     
/*  35 */     if (flag1) {
/*     */       
/*  37 */       GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f, (float)z + 0.5F);
/*  38 */       float f1 = (i * 360) / 16.0F;
/*  39 */       GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
/*  40 */       this.bannerModel.bannerStand.showModel = true;
/*     */     }
/*     */     else {
/*     */       
/*  44 */       float f2 = 0.0F;
/*     */       
/*  46 */       if (i == 2)
/*     */       {
/*  48 */         f2 = 180.0F;
/*     */       }
/*     */       
/*  51 */       if (i == 4)
/*     */       {
/*  53 */         f2 = 90.0F;
/*     */       }
/*     */       
/*  56 */       if (i == 5)
/*     */       {
/*  58 */         f2 = -90.0F;
/*     */       }
/*     */       
/*  61 */       GlStateManager.translate((float)x + 0.5F, (float)y - 0.25F * f, (float)z + 0.5F);
/*  62 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  63 */       GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
/*  64 */       this.bannerModel.bannerStand.showModel = false;
/*     */     } 
/*     */     
/*  67 */     BlockPos blockpos = te.getPos();
/*  68 */     float f3 = (blockpos.getX() * 7 + blockpos.getY() * 9 + blockpos.getZ() * 13) + (float)j + partialTicks;
/*  69 */     this.bannerModel.bannerSlate.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(f3 * 3.1415927F * 0.02F)) * 3.1415927F;
/*  70 */     GlStateManager.enableRescaleNormal();
/*  71 */     ResourceLocation resourcelocation = func_178463_a(te);
/*     */     
/*  73 */     if (resourcelocation != null) {
/*     */       
/*  75 */       bindTexture(resourcelocation);
/*  76 */       GlStateManager.pushMatrix();
/*  77 */       GlStateManager.scale(f, -f, -f);
/*  78 */       this.bannerModel.renderBanner();
/*  79 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/*  82 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  83 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation func_178463_a(TileEntityBanner bannerObj) {
/*  88 */     String s = bannerObj.getPatternResourceLocation();
/*     */     
/*  90 */     if (s.isEmpty())
/*     */     {
/*  92 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  96 */     TimedBannerTexture tileentitybannerrenderer$timedbannertexture = DESIGNS.get(s);
/*     */     
/*  98 */     if (tileentitybannerrenderer$timedbannertexture == null) {
/*     */       
/* 100 */       if (DESIGNS.size() >= 256) {
/*     */         
/* 102 */         long i = System.currentTimeMillis();
/* 103 */         Iterator<String> iterator = DESIGNS.keySet().iterator();
/*     */         
/* 105 */         while (iterator.hasNext()) {
/*     */           
/* 107 */           String s1 = iterator.next();
/* 108 */           TimedBannerTexture tileentitybannerrenderer$timedbannertexture1 = DESIGNS.get(s1);
/*     */           
/* 110 */           if (i - tileentitybannerrenderer$timedbannertexture1.systemTime > 60000L) {
/*     */             
/* 112 */             Minecraft.getMinecraft().getTextureManager().deleteTexture(tileentitybannerrenderer$timedbannertexture1.bannerTexture);
/* 113 */             iterator.remove();
/*     */           } 
/*     */         } 
/*     */         
/* 117 */         if (DESIGNS.size() >= 256)
/*     */         {
/* 119 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 123 */       List<TileEntityBanner.EnumBannerPattern> list1 = bannerObj.getPatternList();
/* 124 */       List<EnumDyeColor> list = bannerObj.getColorList();
/* 125 */       List<String> list2 = Lists.newArrayList();
/*     */       
/* 127 */       for (TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern : list1)
/*     */       {
/* 129 */         list2.add("textures/entity/banner/" + tileentitybanner$enumbannerpattern.getPatternName() + ".png");
/*     */       }
/*     */       
/* 132 */       tileentitybannerrenderer$timedbannertexture = new TimedBannerTexture();
/* 133 */       tileentitybannerrenderer$timedbannertexture.bannerTexture = new ResourceLocation(s);
/* 134 */       Minecraft.getMinecraft().getTextureManager().loadTexture(tileentitybannerrenderer$timedbannertexture.bannerTexture, (ITextureObject)new LayeredColorMaskTexture(BANNERTEXTURES, list2, list));
/* 135 */       DESIGNS.put(s, tileentitybannerrenderer$timedbannertexture);
/*     */     } 
/*     */     
/* 138 */     tileentitybannerrenderer$timedbannertexture.systemTime = System.currentTimeMillis();
/* 139 */     return tileentitybannerrenderer$timedbannertexture.bannerTexture;
/*     */   }
/*     */   
/*     */   static class TimedBannerTexture {
/*     */     public long systemTime;
/*     */     public ResourceLocation bannerTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntityBannerRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */