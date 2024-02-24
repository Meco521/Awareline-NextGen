/*     */ package net.minecraft.client.renderer;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.BetterSnow;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.model.BlockModelCustomizer;
/*     */ import net.optifine.model.ListQuadsOverlay;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.render.RenderEnv;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class BlockModelRenderer {
/*  29 */   private static float aoLightValueOpaque = 0.2F;
/*     */   private static boolean separateAoLightValue = false;
/*  31 */   private static final EnumWorldBlockLayer[] OVERLAY_LAYERS = new EnumWorldBlockLayer[] { EnumWorldBlockLayer.CUTOUT, EnumWorldBlockLayer.CUTOUT_MIPPED, EnumWorldBlockLayer.TRANSLUCENT };
/*     */ 
/*     */   
/*     */   public BlockModelRenderer() {
/*  35 */     if (Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists())
/*     */     {
/*  37 */       Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, Boolean.valueOf(false));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
/*  43 */     Block block = blockStateIn.getBlock();
/*  44 */     block.setBlockBoundsBasedOnState(blockAccessIn, blockPosIn);
/*  45 */     return renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/*  50 */     boolean flag = (Minecraft.isAmbientOcclusionEnabled() && blockStateIn.getBlock().getLightValue() == 0 && modelIn.isAmbientOcclusion());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  55 */       if (Config.isShaders())
/*     */       {
/*  57 */         SVertexBuilder.pushEntity(blockStateIn, blockPosIn, blockAccessIn, worldRendererIn);
/*     */       }
/*     */       
/*  60 */       RenderEnv renderenv = worldRendererIn.getRenderEnv(blockStateIn, blockPosIn);
/*  61 */       modelIn = BlockModelCustomizer.getRenderModel(modelIn, blockStateIn, renderenv);
/*  62 */       boolean flag1 = flag ? renderModelSmooth(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides) : renderModelFlat(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides);
/*     */       
/*  64 */       if (flag1)
/*     */       {
/*  66 */         renderOverlayModels(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides, 0L, renderenv, flag);
/*     */       }
/*     */       
/*  69 */       if (Config.isShaders())
/*     */       {
/*  71 */         SVertexBuilder.popEntity(worldRendererIn);
/*     */       }
/*     */       
/*  74 */       return flag1;
/*     */     }
/*  76 */     catch (Throwable throwable) {
/*     */       
/*  78 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block model");
/*  79 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tesselated");
/*  80 */       CrashReportCategory.addBlockInfo(crashreportcategory, blockPosIn, blockStateIn);
/*  81 */       crashreportcategory.addCrashSection("Using AO", Boolean.valueOf(flag));
/*  82 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModelAmbientOcclusion(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/*  88 */     IBlockState iblockstate = blockAccessIn.getBlockState(blockPosIn);
/*  89 */     return renderModelSmooth(blockAccessIn, modelIn, iblockstate, blockPosIn, worldRendererIn, checkSides);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean renderModelSmooth(IBlockAccess p_renderModelSmooth_1_, IBakedModel p_renderModelSmooth_2_, IBlockState p_renderModelSmooth_3_, BlockPos p_renderModelSmooth_4_, WorldRenderer p_renderModelSmooth_5_, boolean p_renderModelSmooth_6_) {
/*  94 */     boolean flag = false;
/*  95 */     Block block = p_renderModelSmooth_3_.getBlock();
/*  96 */     RenderEnv renderenv = p_renderModelSmooth_5_.getRenderEnv(p_renderModelSmooth_3_, p_renderModelSmooth_4_);
/*  97 */     EnumWorldBlockLayer enumworldblocklayer = p_renderModelSmooth_5_.getBlockLayer();
/*     */     
/*  99 */     for (EnumFacing enumfacing : EnumFacing.VALUES) {
/*     */       
/* 101 */       List<BakedQuad> list = p_renderModelSmooth_2_.getFaceQuads(enumfacing);
/*     */       
/* 103 */       if (!list.isEmpty()) {
/*     */         
/* 105 */         BlockPos blockpos = p_renderModelSmooth_4_.offset(enumfacing);
/*     */         
/* 107 */         if (!p_renderModelSmooth_6_ || block.shouldSideBeRendered(p_renderModelSmooth_1_, blockpos, enumfacing)) {
/*     */           
/* 109 */           list = BlockModelCustomizer.getRenderQuads(list, p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, enumfacing, enumworldblocklayer, 0L, renderenv);
/* 110 */           renderQuadsSmooth(p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, p_renderModelSmooth_5_, list, renderenv);
/* 111 */           flag = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     List<BakedQuad> list1 = p_renderModelSmooth_2_.getGeneralQuads();
/*     */     
/* 118 */     if (!list1.isEmpty()) {
/*     */       
/* 120 */       list1 = BlockModelCustomizer.getRenderQuads(list1, p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, (EnumFacing)null, enumworldblocklayer, 0L, renderenv);
/* 121 */       renderQuadsSmooth(p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, p_renderModelSmooth_5_, list1, renderenv);
/* 122 */       flag = true;
/*     */     } 
/*     */     
/* 125 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModelStandard(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/* 130 */     IBlockState iblockstate = blockAccessIn.getBlockState(blockPosIn);
/* 131 */     return renderModelFlat(blockAccessIn, modelIn, iblockstate, blockPosIn, worldRendererIn, checkSides);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderModelFlat(IBlockAccess p_renderModelFlat_1_, IBakedModel p_renderModelFlat_2_, IBlockState p_renderModelFlat_3_, BlockPos p_renderModelFlat_4_, WorldRenderer p_renderModelFlat_5_, boolean p_renderModelFlat_6_) {
/* 136 */     boolean flag = false;
/* 137 */     Block block = p_renderModelFlat_3_.getBlock();
/* 138 */     RenderEnv renderenv = p_renderModelFlat_5_.getRenderEnv(p_renderModelFlat_3_, p_renderModelFlat_4_);
/* 139 */     EnumWorldBlockLayer enumworldblocklayer = p_renderModelFlat_5_.getBlockLayer();
/*     */     
/* 141 */     for (EnumFacing enumfacing : EnumFacing.VALUES) {
/*     */       
/* 143 */       List<BakedQuad> list = p_renderModelFlat_2_.getFaceQuads(enumfacing);
/*     */       
/* 145 */       if (!list.isEmpty()) {
/*     */         
/* 147 */         BlockPos blockpos = p_renderModelFlat_4_.offset(enumfacing);
/*     */         
/* 149 */         if (!p_renderModelFlat_6_ || block.shouldSideBeRendered(p_renderModelFlat_1_, blockpos, enumfacing)) {
/*     */           
/* 151 */           int i = block.getMixedBrightnessForBlock(p_renderModelFlat_1_, blockpos);
/* 152 */           list = BlockModelCustomizer.getRenderQuads(list, p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, enumfacing, enumworldblocklayer, 0L, renderenv);
/* 153 */           renderQuadsFlat(p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, enumfacing, i, false, p_renderModelFlat_5_, list, renderenv);
/* 154 */           flag = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     List<BakedQuad> list1 = p_renderModelFlat_2_.getGeneralQuads();
/*     */     
/* 161 */     if (!list1.isEmpty()) {
/*     */       
/* 163 */       list1 = BlockModelCustomizer.getRenderQuads(list1, p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, (EnumFacing)null, enumworldblocklayer, 0L, renderenv);
/* 164 */       renderQuadsFlat(p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, (EnumFacing)null, -1, true, p_renderModelFlat_5_, list1, renderenv);
/* 165 */       flag = true;
/*     */     } 
/*     */     
/* 168 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderQuadsSmooth(IBlockAccess p_renderQuadsSmooth_1_, IBlockState p_renderQuadsSmooth_2_, BlockPos p_renderQuadsSmooth_3_, WorldRenderer p_renderQuadsSmooth_4_, List<BakedQuad> p_renderQuadsSmooth_5_, RenderEnv p_renderQuadsSmooth_6_) {
/* 173 */     Block block = p_renderQuadsSmooth_2_.getBlock();
/* 174 */     float[] afloat = p_renderQuadsSmooth_6_.getQuadBounds();
/* 175 */     BitSet bitset = p_renderQuadsSmooth_6_.getBoundsFlags();
/* 176 */     AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = p_renderQuadsSmooth_6_.getAoFace();
/* 177 */     double d0 = p_renderQuadsSmooth_3_.getX();
/* 178 */     double d1 = p_renderQuadsSmooth_3_.getY();
/* 179 */     double d2 = p_renderQuadsSmooth_3_.getZ();
/* 180 */     Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
/*     */     
/* 182 */     if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
/*     */       
/* 184 */       long i = MathHelper.getPositionRandom((Vec3i)p_renderQuadsSmooth_3_);
/* 185 */       d0 += (((float)(i >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 186 */       d2 += (((float)(i >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/*     */       
/* 188 */       if (block$enumoffsettype == Block.EnumOffsetType.XYZ)
/*     */       {
/* 190 */         d1 += (((float)(i >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */       }
/*     */     } 
/*     */     
/* 194 */     for (BakedQuad bakedquad : p_renderQuadsSmooth_5_) {
/*     */       
/* 196 */       fillQuadBounds(block, bakedquad.getVertexData(), bakedquad.getFace(), afloat, bitset);
/* 197 */       blockmodelrenderer$ambientocclusionface.updateVertexBrightness(p_renderQuadsSmooth_1_, block, p_renderQuadsSmooth_3_, bakedquad.getFace(), afloat, bitset);
/*     */       
/* 199 */       if ((bakedquad.getSprite()).isEmissive)
/*     */       {
/* 201 */         blockmodelrenderer$ambientocclusionface.setMaxBlockLight();
/*     */       }
/*     */       
/* 204 */       if (p_renderQuadsSmooth_4_.isMultiTexture()) {
/*     */         
/* 206 */         p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexDataSingle());
/*     */       }
/*     */       else {
/*     */         
/* 210 */         p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexData());
/*     */       } 
/*     */       
/* 213 */       p_renderQuadsSmooth_4_.putSprite(bakedquad.getSprite());
/* 214 */       p_renderQuadsSmooth_4_.putBrightness4(blockmodelrenderer$ambientocclusionface.vertexBrightness[0], blockmodelrenderer$ambientocclusionface.vertexBrightness[1], blockmodelrenderer$ambientocclusionface.vertexBrightness[2], blockmodelrenderer$ambientocclusionface.vertexBrightness[3]);
/* 215 */       int j = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsSmooth_2_, p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, p_renderQuadsSmooth_6_);
/*     */       
/* 217 */       if (!bakedquad.hasTintIndex() && j == -1) {
/*     */         
/* 219 */         if (separateAoLightValue) {
/*     */           
/* 221 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 222 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 223 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 224 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         }
/*     */         else {
/*     */           
/* 228 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 229 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 230 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 231 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         } 
/*     */       } else {
/*     */         int k;
/*     */ 
/*     */ 
/*     */         
/* 238 */         if (j != -1) {
/*     */           
/* 240 */           k = j;
/*     */         }
/*     */         else {
/*     */           
/* 244 */           k = block.colorMultiplier(p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, bakedquad.getTintIndex());
/*     */         } 
/*     */         
/* 247 */         if (EntityRenderer.anaglyphEnable)
/*     */         {
/* 249 */           k = TextureUtil.anaglyphColor(k);
/*     */         }
/*     */         
/* 252 */         float f = (k >> 16 & 0xFF) / 255.0F;
/* 253 */         float f1 = (k >> 8 & 0xFF) / 255.0F;
/* 254 */         float f2 = (k & 0xFF) / 255.0F;
/*     */         
/* 256 */         if (separateAoLightValue) {
/*     */           
/* 258 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 259 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 260 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 261 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         }
/*     */         else {
/*     */           
/* 265 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f2, 4);
/* 266 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f2, 3);
/* 267 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f2, 2);
/* 268 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f2, 1);
/*     */         } 
/*     */       } 
/*     */       
/* 272 */       p_renderQuadsSmooth_4_.putPosition(d0, d1, d2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillQuadBounds(Block blockIn, int[] vertexData, EnumFacing facingIn, float[] quadBounds, BitSet boundsFlags) {
/* 278 */     float f = 32.0F;
/* 279 */     float f1 = 32.0F;
/* 280 */     float f2 = 32.0F;
/* 281 */     float f3 = -32.0F;
/* 282 */     float f4 = -32.0F;
/* 283 */     float f5 = -32.0F;
/* 284 */     int i = vertexData.length / 4;
/*     */     
/* 286 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 288 */       float f6 = Float.intBitsToFloat(vertexData[j * i]);
/* 289 */       float f7 = Float.intBitsToFloat(vertexData[j * i + 1]);
/* 290 */       float f8 = Float.intBitsToFloat(vertexData[j * i + 2]);
/* 291 */       f = Math.min(f, f6);
/* 292 */       f1 = Math.min(f1, f7);
/* 293 */       f2 = Math.min(f2, f8);
/* 294 */       f3 = Math.max(f3, f6);
/* 295 */       f4 = Math.max(f4, f7);
/* 296 */       f5 = Math.max(f5, f8);
/*     */     } 
/*     */     
/* 299 */     if (quadBounds != null) {
/*     */       
/* 301 */       quadBounds[EnumFacing.WEST.getIndex()] = f;
/* 302 */       quadBounds[EnumFacing.EAST.getIndex()] = f3;
/* 303 */       quadBounds[EnumFacing.DOWN.getIndex()] = f1;
/* 304 */       quadBounds[EnumFacing.UP.getIndex()] = f4;
/* 305 */       quadBounds[EnumFacing.NORTH.getIndex()] = f2;
/* 306 */       quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
/* 307 */       int k = EnumFacing.VALUES.length;
/* 308 */       quadBounds[EnumFacing.WEST.getIndex() + k] = 1.0F - f;
/* 309 */       quadBounds[EnumFacing.EAST.getIndex() + k] = 1.0F - f3;
/* 310 */       quadBounds[EnumFacing.DOWN.getIndex() + k] = 1.0F - f1;
/* 311 */       quadBounds[EnumFacing.UP.getIndex() + k] = 1.0F - f4;
/* 312 */       quadBounds[EnumFacing.NORTH.getIndex() + k] = 1.0F - f2;
/* 313 */       quadBounds[EnumFacing.SOUTH.getIndex() + k] = 1.0F - f5;
/*     */     } 
/*     */     
/* 316 */     float f9 = 1.0E-4F;
/* 317 */     float f10 = 0.9999F;
/*     */     
/* 319 */     switch (facingIn) {
/*     */       
/*     */       case DOWN:
/* 322 */         boundsFlags.set(1, (f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F));
/* 323 */         boundsFlags.set(0, ((f1 < 1.0E-4F || blockIn.isFullCube()) && f1 == f4));
/*     */         break;
/*     */       
/*     */       case UP:
/* 327 */         boundsFlags.set(1, (f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F));
/* 328 */         boundsFlags.set(0, ((f4 > 0.9999F || blockIn.isFullCube()) && f1 == f4));
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 332 */         boundsFlags.set(1, (f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F));
/* 333 */         boundsFlags.set(0, ((f2 < 1.0E-4F || blockIn.isFullCube()) && f2 == f5));
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 337 */         boundsFlags.set(1, (f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F));
/* 338 */         boundsFlags.set(0, ((f5 > 0.9999F || blockIn.isFullCube()) && f2 == f5));
/*     */         break;
/*     */       
/*     */       case WEST:
/* 342 */         boundsFlags.set(1, (f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F));
/* 343 */         boundsFlags.set(0, ((f < 1.0E-4F || blockIn.isFullCube()) && f == f3));
/*     */         break;
/*     */       
/*     */       case EAST:
/* 347 */         boundsFlags.set(1, (f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F));
/* 348 */         boundsFlags.set(0, ((f3 > 0.9999F || blockIn.isFullCube()) && f == f3));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderQuadsFlat(IBlockAccess p_renderQuadsFlat_1_, IBlockState p_renderQuadsFlat_2_, BlockPos p_renderQuadsFlat_3_, EnumFacing p_renderQuadsFlat_4_, int p_renderQuadsFlat_5_, boolean p_renderQuadsFlat_6_, WorldRenderer p_renderQuadsFlat_7_, List<BakedQuad> p_renderQuadsFlat_8_, RenderEnv p_renderQuadsFlat_9_) {
/* 354 */     Block block = p_renderQuadsFlat_2_.getBlock();
/* 355 */     BitSet bitset = p_renderQuadsFlat_9_.getBoundsFlags();
/* 356 */     double d0 = p_renderQuadsFlat_3_.getX();
/* 357 */     double d1 = p_renderQuadsFlat_3_.getY();
/* 358 */     double d2 = p_renderQuadsFlat_3_.getZ();
/* 359 */     Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
/*     */     
/* 361 */     if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
/*     */       
/* 363 */       int i = p_renderQuadsFlat_3_.getX();
/* 364 */       int j = p_renderQuadsFlat_3_.getZ();
/* 365 */       long k = i * 3129871L ^ j * 116129781L;
/* 366 */       k = k * k * 42317861L + k * 11L;
/* 367 */       d0 += (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 368 */       d2 += (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/*     */       
/* 370 */       if (block$enumoffsettype == Block.EnumOffsetType.XYZ)
/*     */       {
/* 372 */         d1 += (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */       }
/*     */     } 
/*     */     
/* 376 */     for (BakedQuad bakedquad : p_renderQuadsFlat_8_) {
/*     */       
/* 378 */       if (p_renderQuadsFlat_6_) {
/*     */         
/* 380 */         fillQuadBounds(block, bakedquad.getVertexData(), bakedquad.getFace(), (float[])null, bitset);
/* 381 */         p_renderQuadsFlat_5_ = bitset.get(0) ? block.getMixedBrightnessForBlock(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_.offset(bakedquad.getFace())) : block.getMixedBrightnessForBlock(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_);
/*     */       } 
/*     */       
/* 384 */       if ((bakedquad.getSprite()).isEmissive)
/*     */       {
/* 386 */         p_renderQuadsFlat_5_ |= 0xF0;
/*     */       }
/*     */       
/* 389 */       if (p_renderQuadsFlat_7_.isMultiTexture()) {
/*     */         
/* 391 */         p_renderQuadsFlat_7_.addVertexData(bakedquad.getVertexDataSingle());
/*     */       }
/*     */       else {
/*     */         
/* 395 */         p_renderQuadsFlat_7_.addVertexData(bakedquad.getVertexData());
/*     */       } 
/*     */       
/* 398 */       p_renderQuadsFlat_7_.putSprite(bakedquad.getSprite());
/* 399 */       p_renderQuadsFlat_7_.putBrightness4(p_renderQuadsFlat_5_, p_renderQuadsFlat_5_, p_renderQuadsFlat_5_, p_renderQuadsFlat_5_);
/* 400 */       int i1 = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsFlat_2_, p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, p_renderQuadsFlat_9_);
/*     */       
/* 402 */       if (bakedquad.hasTintIndex() || i1 != -1) {
/*     */         int l;
/*     */ 
/*     */         
/* 406 */         if (i1 != -1) {
/*     */           
/* 408 */           l = i1;
/*     */         }
/*     */         else {
/*     */           
/* 412 */           l = block.colorMultiplier(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, bakedquad.getTintIndex());
/*     */         } 
/*     */         
/* 415 */         if (EntityRenderer.anaglyphEnable)
/*     */         {
/* 417 */           l = TextureUtil.anaglyphColor(l);
/*     */         }
/*     */         
/* 420 */         float f = (l >> 16 & 0xFF) / 255.0F;
/* 421 */         float f1 = (l >> 8 & 0xFF) / 255.0F;
/* 422 */         float f2 = (l & 0xFF) / 255.0F;
/* 423 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 4);
/* 424 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 3);
/* 425 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 2);
/* 426 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 1);
/*     */       } 
/*     */       
/* 429 */       p_renderQuadsFlat_7_.putPosition(d0, d1, d2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderModelBrightnessColor(IBakedModel bakedModel, float p_178262_2_, float red, float green, float blue) {
/* 435 */     for (EnumFacing enumfacing : EnumFacing.VALUES)
/*     */     {
/* 437 */       renderModelBrightnessColorQuads(p_178262_2_, red, green, blue, bakedModel.getFaceQuads(enumfacing));
/*     */     }
/*     */     
/* 440 */     renderModelBrightnessColorQuads(p_178262_2_, red, green, blue, bakedModel.getGeneralQuads());
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderModelBrightness(IBakedModel model, IBlockState p_178266_2_, float brightness, boolean p_178266_4_) {
/* 445 */     Block block = p_178266_2_.getBlock();
/* 446 */     block.setBlockBoundsForItemRender();
/* 447 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 448 */     int i = block.getRenderColor(block.getStateForEntityRender(p_178266_2_));
/*     */     
/* 450 */     if (EntityRenderer.anaglyphEnable)
/*     */     {
/* 452 */       i = TextureUtil.anaglyphColor(i);
/*     */     }
/*     */     
/* 455 */     float f = (i >> 16 & 0xFF) / 255.0F;
/* 456 */     float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 457 */     float f2 = (i & 0xFF) / 255.0F;
/*     */     
/* 459 */     if (!p_178266_4_)
/*     */     {
/* 461 */       GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */     }
/*     */     
/* 464 */     renderModelBrightnessColor(model, brightness, f, f1, f2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderModelBrightnessColorQuads(float brightness, float red, float green, float blue, List<BakedQuad> listQuads) {
/* 469 */     Tessellator tessellator = Tessellator.getInstance();
/* 470 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 472 */     for (BakedQuad bakedquad : listQuads) {
/*     */       
/* 474 */       worldrenderer.begin(7, DefaultVertexFormats.ITEM);
/* 475 */       worldrenderer.addVertexData(bakedquad.getVertexData());
/* 476 */       worldrenderer.putSprite(bakedquad.getSprite());
/*     */       
/* 478 */       if (bakedquad.hasTintIndex()) {
/*     */         
/* 480 */         worldrenderer.putColorRGB_F4(red * brightness, green * brightness, blue * brightness);
/*     */       }
/*     */       else {
/*     */         
/* 484 */         worldrenderer.putColorRGB_F4(brightness, brightness, brightness);
/*     */       } 
/*     */       
/* 487 */       Vec3i vec3i = bakedquad.getFace().getDirectionVec();
/* 488 */       worldrenderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/* 489 */       tessellator.draw();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static float fixAoLightValue(float p_fixAoLightValue_0_) {
/* 495 */     return (p_fixAoLightValue_0_ == 0.2F) ? aoLightValueOpaque : p_fixAoLightValue_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateAoLightValue() {
/* 500 */     aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
/* 501 */     separateAoLightValue = (Config.isShaders() && Shaders.isSeparateAo());
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderOverlayModels(IBlockAccess p_renderOverlayModels_1_, IBakedModel p_renderOverlayModels_2_, IBlockState p_renderOverlayModels_3_, BlockPos p_renderOverlayModels_4_, WorldRenderer p_renderOverlayModels_5_, boolean p_renderOverlayModels_6_, long p_renderOverlayModels_7_, RenderEnv p_renderOverlayModels_9_, boolean p_renderOverlayModels_10_) {
/* 506 */     if (p_renderOverlayModels_9_.isOverlaysRendered())
/*     */     {
/* 508 */       for (int i = 0; i < OVERLAY_LAYERS.length; i++) {
/*     */         
/* 510 */         EnumWorldBlockLayer enumworldblocklayer = OVERLAY_LAYERS[i];
/* 511 */         ListQuadsOverlay listquadsoverlay = p_renderOverlayModels_9_.getListQuadsOverlay(enumworldblocklayer);
/*     */         
/* 513 */         if (listquadsoverlay.size() > 0) {
/*     */           
/* 515 */           RegionRenderCacheBuilder regionrendercachebuilder = p_renderOverlayModels_9_.getRegionRenderCacheBuilder();
/*     */           
/* 517 */           if (regionrendercachebuilder != null) {
/*     */             
/* 519 */             WorldRenderer worldrenderer = regionrendercachebuilder.getWorldRendererByLayer(enumworldblocklayer);
/*     */             
/* 521 */             if (!worldrenderer.isDrawing()) {
/*     */               
/* 523 */               worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 524 */               worldrenderer.setTranslation(p_renderOverlayModels_5_.getXOffset(), p_renderOverlayModels_5_.getYOffset(), p_renderOverlayModels_5_.getZOffset());
/*     */             } 
/*     */             
/* 527 */             for (int j = 0; j < listquadsoverlay.size(); j++) {
/*     */               
/* 529 */               BakedQuad bakedquad = listquadsoverlay.getQuad(j);
/* 530 */               List<BakedQuad> list = listquadsoverlay.getListQuadsSingle(bakedquad);
/* 531 */               IBlockState iblockstate = listquadsoverlay.getBlockState(j);
/*     */               
/* 533 */               if (bakedquad.getQuadEmissive() != null)
/*     */               {
/* 535 */                 listquadsoverlay.addQuad(bakedquad.getQuadEmissive(), iblockstate);
/*     */               }
/*     */               
/* 538 */               p_renderOverlayModels_9_.reset(iblockstate, p_renderOverlayModels_4_);
/*     */               
/* 540 */               if (p_renderOverlayModels_10_) {
/*     */                 
/* 542 */                 renderQuadsSmooth(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, worldrenderer, list, p_renderOverlayModels_9_);
/*     */               }
/*     */               else {
/*     */                 
/* 546 */                 int k = iblockstate.getBlock().getMixedBrightnessForBlock(p_renderOverlayModels_1_, p_renderOverlayModels_4_.offset(bakedquad.getFace()));
/* 547 */                 renderQuadsFlat(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, bakedquad.getFace(), k, false, worldrenderer, list, p_renderOverlayModels_9_);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 552 */           listquadsoverlay.clear();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 557 */     if (Config.isBetterSnow() && !p_renderOverlayModels_9_.isBreakingAnimation() && BetterSnow.shouldRender(p_renderOverlayModels_1_, p_renderOverlayModels_3_, p_renderOverlayModels_4_)) {
/*     */       
/* 559 */       IBakedModel ibakedmodel = BetterSnow.getModelSnowLayer();
/* 560 */       IBlockState iblockstate1 = BetterSnow.getStateSnowLayer();
/* 561 */       renderModel(p_renderOverlayModels_1_, ibakedmodel, iblockstate1, p_renderOverlayModels_4_, p_renderOverlayModels_5_, p_renderOverlayModels_6_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AmbientOcclusionFace
/*     */   {
/*     */     public AmbientOcclusionFace() {
/* 572 */       this((BlockModelRenderer)null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 577 */     final float[] vertexColorMultiplier = new float[4];
/* 578 */     final int[] vertexBrightness = new int[4];
/*     */     
/*     */     public AmbientOcclusionFace(BlockModelRenderer p_i46235_1_) {}
/*     */     
/*     */     public void setMaxBlockLight() {
/* 583 */       int i = 240;
/* 584 */       this.vertexBrightness[0] = this.vertexBrightness[0] | i;
/* 585 */       this.vertexBrightness[1] = this.vertexBrightness[1] | i;
/* 586 */       this.vertexBrightness[2] = this.vertexBrightness[2] | i;
/* 587 */       this.vertexBrightness[3] = this.vertexBrightness[3] | i;
/* 588 */       this.vertexColorMultiplier[0] = 1.0F;
/* 589 */       this.vertexColorMultiplier[1] = 1.0F;
/* 590 */       this.vertexColorMultiplier[2] = 1.0F;
/* 591 */       this.vertexColorMultiplier[3] = 1.0F; } public void updateVertexBrightness(IBlockAccess blockAccessIn, Block blockIn, BlockPos blockPosIn, EnumFacing facingIn, float[] quadBounds, BitSet boundsFlags) { float f4; int i1, j1; float f26;
/*     */       int k1;
/*     */       float f27;
/*     */       int l1;
/*     */       float f28;
/* 596 */       BlockPos blockpos = boundsFlags.get(0) ? blockPosIn.offset(facingIn) : blockPosIn;
/* 597 */       BlockModelRenderer.EnumNeighborInfo blockmodelrenderer$enumneighborinfo = BlockModelRenderer.EnumNeighborInfo.getNeighbourInfo(facingIn);
/* 598 */       BlockPos blockpos1 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[0]);
/* 599 */       BlockPos blockpos2 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[1]);
/* 600 */       BlockPos blockpos3 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 601 */       BlockPos blockpos4 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 602 */       int i = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos1);
/* 603 */       int j = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos2);
/* 604 */       int k = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos3);
/* 605 */       int l = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos4);
/* 606 */       float f = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos1).getBlock().getAmbientOcclusionLightValue());
/* 607 */       float f1 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos2).getBlock().getAmbientOcclusionLightValue());
/* 608 */       float f2 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos3).getBlock().getAmbientOcclusionLightValue());
/* 609 */       float f3 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos4).getBlock().getAmbientOcclusionLightValue());
/* 610 */       boolean flag = blockAccessIn.getBlockState(blockpos1.offset(facingIn)).getBlock().isTranslucent();
/* 611 */       boolean flag1 = blockAccessIn.getBlockState(blockpos2.offset(facingIn)).getBlock().isTranslucent();
/* 612 */       boolean flag2 = blockAccessIn.getBlockState(blockpos3.offset(facingIn)).getBlock().isTranslucent();
/* 613 */       boolean flag3 = blockAccessIn.getBlockState(blockpos4.offset(facingIn)).getBlock().isTranslucent();
/*     */ 
/*     */ 
/*     */       
/* 617 */       if (!flag2 && !flag) {
/*     */         
/* 619 */         f4 = f;
/* 620 */         i1 = i;
/*     */       }
/*     */       else {
/*     */         
/* 624 */         BlockPos blockpos5 = blockpos1.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 625 */         f4 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos5).getBlock().getAmbientOcclusionLightValue());
/* 626 */         i1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos5);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 632 */       if (!flag3 && !flag) {
/*     */         
/* 634 */         f26 = f;
/* 635 */         j1 = i;
/*     */       }
/*     */       else {
/*     */         
/* 639 */         BlockPos blockpos6 = blockpos1.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 640 */         f26 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos6).getBlock().getAmbientOcclusionLightValue());
/* 641 */         j1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos6);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 647 */       if (!flag2 && !flag1) {
/*     */         
/* 649 */         f27 = f1;
/* 650 */         k1 = j;
/*     */       }
/*     */       else {
/*     */         
/* 654 */         BlockPos blockpos7 = blockpos2.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 655 */         f27 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos7).getBlock().getAmbientOcclusionLightValue());
/* 656 */         k1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos7);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 662 */       if (!flag3 && !flag1) {
/*     */         
/* 664 */         f28 = f1;
/* 665 */         l1 = j;
/*     */       }
/*     */       else {
/*     */         
/* 669 */         BlockPos blockpos8 = blockpos2.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 670 */         f28 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos8).getBlock().getAmbientOcclusionLightValue());
/* 671 */         l1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos8);
/*     */       } 
/*     */       
/* 674 */       int i3 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn);
/*     */       
/* 676 */       if (boundsFlags.get(0) || !blockAccessIn.getBlockState(blockPosIn.offset(facingIn)).getBlock().isOpaqueCube())
/*     */       {
/* 678 */         i3 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn.offset(facingIn));
/*     */       }
/*     */       
/* 681 */       float f5 = boundsFlags.get(0) ? blockAccessIn.getBlockState(blockpos).getBlock().getAmbientOcclusionLightValue() : blockAccessIn.getBlockState(blockPosIn).getBlock().getAmbientOcclusionLightValue();
/* 682 */       f5 = BlockModelRenderer.fixAoLightValue(f5);
/* 683 */       BlockModelRenderer.VertexTranslations blockmodelrenderer$vertextranslations = BlockModelRenderer.VertexTranslations.getVertexTranslations(facingIn);
/*     */       
/* 685 */       if (boundsFlags.get(1) && blockmodelrenderer$enumneighborinfo.field_178289_i) {
/*     */         
/* 687 */         float f29 = (f3 + f + f26 + f5) * 0.25F;
/* 688 */         float f30 = (f2 + f + f4 + f5) * 0.25F;
/* 689 */         float f31 = (f2 + f1 + f27 + f5) * 0.25F;
/* 690 */         float f32 = (f3 + f1 + f28 + f5) * 0.25F;
/* 691 */         float f10 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[1]).field_178229_m];
/* 692 */         float f11 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[3]).field_178229_m];
/* 693 */         float f12 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[5]).field_178229_m];
/* 694 */         float f13 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[7]).field_178229_m];
/* 695 */         float f14 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[1]).field_178229_m];
/* 696 */         float f15 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[3]).field_178229_m];
/* 697 */         float f16 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[5]).field_178229_m];
/* 698 */         float f17 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[7]).field_178229_m];
/* 699 */         float f18 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[1]).field_178229_m];
/* 700 */         float f19 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[3]).field_178229_m];
/* 701 */         float f20 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[5]).field_178229_m];
/* 702 */         float f21 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[7]).field_178229_m];
/* 703 */         float f22 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[1]).field_178229_m];
/* 704 */         float f23 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[3]).field_178229_m];
/* 705 */         float f24 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[5]).field_178229_m];
/* 706 */         float f25 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[7]).field_178229_m];
/* 707 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178191_g] = f29 * f10 + f30 * f11 + f31 * f12 + f32 * f13;
/* 708 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178200_h] = f29 * f14 + f30 * f15 + f31 * f16 + f32 * f17;
/* 709 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178201_i] = f29 * f18 + f30 * f19 + f31 * f20 + f32 * f21;
/* 710 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178198_j] = f29 * f22 + f30 * f23 + f31 * f24 + f32 * f25;
/* 711 */         int i2 = getAoBrightness(l, i, j1, i3);
/* 712 */         int j2 = getAoBrightness(k, i, i1, i3);
/* 713 */         int k2 = getAoBrightness(k, j, k1, i3);
/* 714 */         int l2 = getAoBrightness(l, j, l1, i3);
/* 715 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178191_g] = getVertexBrightness(i2, j2, k2, l2, f10, f11, f12, f13);
/* 716 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178200_h] = getVertexBrightness(i2, j2, k2, l2, f14, f15, f16, f17);
/* 717 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178201_i] = getVertexBrightness(i2, j2, k2, l2, f18, f19, f20, f21);
/* 718 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178198_j] = getVertexBrightness(i2, j2, k2, l2, f22, f23, f24, f25);
/*     */       }
/*     */       else {
/*     */         
/* 722 */         float f6 = (f3 + f + f26 + f5) * 0.25F;
/* 723 */         float f7 = (f2 + f + f4 + f5) * 0.25F;
/* 724 */         float f8 = (f2 + f1 + f27 + f5) * 0.25F;
/* 725 */         float f9 = (f3 + f1 + f28 + f5) * 0.25F;
/* 726 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178191_g] = getAoBrightness(l, i, j1, i3);
/* 727 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178200_h] = getAoBrightness(k, i, i1, i3);
/* 728 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178201_i] = getAoBrightness(k, j, k1, i3);
/* 729 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178198_j] = getAoBrightness(l, j, l1, i3);
/* 730 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178191_g] = f6;
/* 731 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178200_h] = f7;
/* 732 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178201_i] = f8;
/* 733 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178198_j] = f9;
/*     */       }  }
/*     */ 
/*     */ 
/*     */     
/*     */     private int getAoBrightness(int br1, int br2, int br3, int br4) {
/* 739 */       if (br1 == 0)
/*     */       {
/* 741 */         br1 = br4;
/*     */       }
/*     */       
/* 744 */       if (br2 == 0)
/*     */       {
/* 746 */         br2 = br4;
/*     */       }
/*     */       
/* 749 */       if (br3 == 0)
/*     */       {
/* 751 */         br3 = br4;
/*     */       }
/*     */       
/* 754 */       return br1 + br2 + br3 + br4 >> 2 & 0xFF00FF;
/*     */     }
/*     */ 
/*     */     
/*     */     private int getVertexBrightness(int p_178203_1_, int p_178203_2_, int p_178203_3_, int p_178203_4_, float p_178203_5_, float p_178203_6_, float p_178203_7_, float p_178203_8_) {
/* 759 */       int i = (int)((p_178203_1_ >> 16 & 0xFF) * p_178203_5_ + (p_178203_2_ >> 16 & 0xFF) * p_178203_6_ + (p_178203_3_ >> 16 & 0xFF) * p_178203_7_ + (p_178203_4_ >> 16 & 0xFF) * p_178203_8_) & 0xFF;
/* 760 */       int j = (int)((p_178203_1_ & 0xFF) * p_178203_5_ + (p_178203_2_ & 0xFF) * p_178203_6_ + (p_178203_3_ & 0xFF) * p_178203_7_ + (p_178203_4_ & 0xFF) * p_178203_8_) & 0xFF;
/* 761 */       return i << 16 | j;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumNeighborInfo
/*     */   {
/* 767 */     DOWN((String)new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.5F, false, new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0]),
/* 768 */     UP((String)new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH }, 1.0F, false, new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0]),
/* 769 */     NORTH((String)new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST }, 0.8F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST }),
/* 770 */     SOUTH((String)new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP }, 0.8F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST }),
/* 771 */     WEST((String)new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH }),
/* 772 */     EAST((String)new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH });
/*     */     
/*     */     protected final EnumFacing[] field_178276_g;
/*     */     protected final float field_178288_h;
/*     */     protected final boolean field_178289_i;
/*     */     protected final BlockModelRenderer.Orientation[] field_178286_j;
/*     */     protected final BlockModelRenderer.Orientation[] field_178287_k;
/*     */     protected final BlockModelRenderer.Orientation[] field_178284_l;
/*     */     protected final BlockModelRenderer.Orientation[] field_178285_m;
/* 781 */     private static final EnumNeighborInfo[] VALUES = new EnumNeighborInfo[6];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EnumNeighborInfo(EnumFacing[] p_i46236_3_, float p_i46236_4_, boolean p_i46236_5_, BlockModelRenderer.Orientation[] p_i46236_6_, BlockModelRenderer.Orientation[] p_i46236_7_, BlockModelRenderer.Orientation[] p_i46236_8_, BlockModelRenderer.Orientation[] p_i46236_9_) {
/*     */       this.field_178276_g = p_i46236_3_;
/*     */       this.field_178288_h = p_i46236_4_;
/*     */       this.field_178289_i = p_i46236_5_;
/*     */       this.field_178286_j = p_i46236_6_;
/*     */       this.field_178287_k = p_i46236_7_;
/*     */       this.field_178284_l = p_i46236_8_;
/*     */       this.field_178285_m = p_i46236_9_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 800 */       VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
/* 801 */       VALUES[EnumFacing.UP.getIndex()] = UP;
/* 802 */       VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
/* 803 */       VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
/* 804 */       VALUES[EnumFacing.WEST.getIndex()] = WEST;
/* 805 */       VALUES[EnumFacing.EAST.getIndex()] = EAST;
/*     */     }
/*     */     public static EnumNeighborInfo getNeighbourInfo(EnumFacing p_178273_0_) {
/*     */       return VALUES[p_178273_0_.getIndex()];
/*     */     } }
/*     */   
/* 811 */   public enum Orientation { DOWN((String)EnumFacing.DOWN, false),
/* 812 */     UP((String)EnumFacing.UP, false),
/* 813 */     NORTH((String)EnumFacing.NORTH, false),
/* 814 */     SOUTH((String)EnumFacing.SOUTH, false),
/* 815 */     WEST((String)EnumFacing.WEST, false),
/* 816 */     EAST((String)EnumFacing.EAST, false),
/* 817 */     FLIP_DOWN((String)EnumFacing.DOWN, true),
/* 818 */     FLIP_UP((String)EnumFacing.UP, true),
/* 819 */     FLIP_NORTH((String)EnumFacing.NORTH, true),
/* 820 */     FLIP_SOUTH((String)EnumFacing.SOUTH, true),
/* 821 */     FLIP_WEST((String)EnumFacing.WEST, true),
/* 822 */     FLIP_EAST((String)EnumFacing.EAST, true);
/*     */     
/*     */     protected final int field_178229_m;
/*     */ 
/*     */     
/*     */     Orientation(EnumFacing p_i46233_3_, boolean p_i46233_4_) {
/* 828 */       this.field_178229_m = p_i46233_3_.getIndex() + (p_i46233_4_ ? (EnumFacing.values()).length : 0);
/*     */     } }
/*     */ 
/*     */   
/*     */   enum VertexTranslations
/*     */   {
/* 834 */     DOWN(0, 1, 2, 3),
/* 835 */     UP(2, 3, 0, 1),
/* 836 */     NORTH(3, 0, 1, 2),
/* 837 */     SOUTH(0, 1, 2, 3),
/* 838 */     WEST(3, 0, 1, 2),
/* 839 */     EAST(1, 2, 3, 0);
/*     */     
/*     */     final int field_178191_g;
/*     */     final int field_178200_h;
/*     */     final int field_178201_i;
/*     */     final int field_178198_j;
/* 845 */     private static final VertexTranslations[] VALUES = new VertexTranslations[6];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 861 */       VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
/* 862 */       VALUES[EnumFacing.UP.getIndex()] = UP;
/* 863 */       VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
/* 864 */       VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
/* 865 */       VALUES[EnumFacing.WEST.getIndex()] = WEST;
/* 866 */       VALUES[EnumFacing.EAST.getIndex()] = EAST;
/*     */     }
/*     */     
/*     */     VertexTranslations(int p_i46234_3_, int p_i46234_4_, int p_i46234_5_, int p_i46234_6_) {
/*     */       this.field_178191_g = p_i46234_3_;
/*     */       this.field_178200_h = p_i46234_4_;
/*     */       this.field_178201_i = p_i46234_5_;
/*     */       this.field_178198_j = p_i46234_6_;
/*     */     }
/*     */     
/*     */     public static VertexTranslations getVertexTranslations(EnumFacing p_178184_0_) {
/*     */       return VALUES[p_178184_0_.getIndex()];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\BlockModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */