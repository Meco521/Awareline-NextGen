/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.render.RenderEnv;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ 
/*     */ public class BlockFluidRenderer {
/*  21 */   private final TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];
/*  22 */   private final TextureAtlasSprite[] atlasSpritesWater = new TextureAtlasSprite[2];
/*     */ 
/*     */   
/*     */   public BlockFluidRenderer() {
/*  26 */     initAtlasSprites();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initAtlasSprites() {
/*  31 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  32 */     this.atlasSpritesLava[0] = texturemap.getAtlasSprite("minecraft:blocks/lava_still");
/*  33 */     this.atlasSpritesLava[1] = texturemap.getAtlasSprite("minecraft:blocks/lava_flow");
/*  34 */     this.atlasSpritesWater[0] = texturemap.getAtlasSprite("minecraft:blocks/water_still");
/*  35 */     this.atlasSpritesWater[1] = texturemap.getAtlasSprite("minecraft:blocks/water_flow");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderFluid(IBlockAccess blockAccess, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
/*     */     boolean flag2;
/*     */     try {
/*  44 */       if (Config.isShaders())
/*     */       {
/*  46 */         SVertexBuilder.pushEntity(blockStateIn, blockPosIn, blockAccess, worldRendererIn);
/*     */       }
/*     */       
/*  49 */       BlockLiquid blockliquid = (BlockLiquid)blockStateIn.getBlock();
/*  50 */       blockliquid.setBlockBoundsBasedOnState(blockAccess, blockPosIn);
/*  51 */       TextureAtlasSprite[] atextureatlassprite = (blockliquid.getMaterial() == Material.lava) ? this.atlasSpritesLava : this.atlasSpritesWater;
/*  52 */       RenderEnv renderenv = worldRendererIn.getRenderEnv(blockStateIn, blockPosIn);
/*  53 */       int i = CustomColors.getFluidColor(blockAccess, blockStateIn, blockPosIn, renderenv);
/*  54 */       float f = (i >> 16 & 0xFF) / 255.0F;
/*  55 */       float f1 = (i >> 8 & 0xFF) / 255.0F;
/*  56 */       float f2 = (i & 0xFF) / 255.0F;
/*  57 */       boolean flag = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.up(), EnumFacing.UP);
/*  58 */       boolean flag1 = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.down(), EnumFacing.DOWN);
/*  59 */       boolean[] aboolean = renderenv.getBorderFlags();
/*  60 */       aboolean[0] = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.north(), EnumFacing.NORTH);
/*  61 */       aboolean[1] = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.south(), EnumFacing.SOUTH);
/*  62 */       aboolean[2] = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.west(), EnumFacing.WEST);
/*  63 */       aboolean[3] = blockliquid.shouldSideBeRendered(blockAccess, blockPosIn.east(), EnumFacing.EAST);
/*     */       
/*  65 */       if (flag || flag1 || aboolean[0] || aboolean[1] || aboolean[2] || aboolean[3]) {
/*     */         
/*  67 */         boolean bool = false;
/*  68 */         float f3 = 0.5F;
/*  69 */         float f4 = 1.0F;
/*  70 */         float f5 = 0.8F;
/*  71 */         float f6 = 0.6F;
/*  72 */         Material material = blockliquid.getMaterial();
/*  73 */         float f7 = getFluidHeight(blockAccess, blockPosIn, material);
/*  74 */         float f8 = getFluidHeight(blockAccess, blockPosIn.south(), material);
/*  75 */         float f9 = getFluidHeight(blockAccess, blockPosIn.east().south(), material);
/*  76 */         float f10 = getFluidHeight(blockAccess, blockPosIn.east(), material);
/*  77 */         double d0 = blockPosIn.getX();
/*  78 */         double d1 = blockPosIn.getY();
/*  79 */         double d2 = blockPosIn.getZ();
/*  80 */         float f11 = 0.001F;
/*     */         
/*  82 */         if (flag) {
/*     */           float f13, f14, f15, f16, f17, f18, f19, f20;
/*  84 */           bool = true;
/*  85 */           TextureAtlasSprite textureatlassprite = atextureatlassprite[0];
/*  86 */           float f12 = (float)BlockLiquid.getFlowDirection(blockAccess, blockPosIn, material);
/*     */           
/*  88 */           if (f12 > -999.0F)
/*     */           {
/*  90 */             textureatlassprite = atextureatlassprite[1];
/*     */           }
/*     */           
/*  93 */           worldRendererIn.setSprite(textureatlassprite);
/*  94 */           f7 -= f11;
/*  95 */           f8 -= f11;
/*  96 */           f9 -= f11;
/*  97 */           f10 -= f11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 107 */           if (f12 < -999.0F) {
/*     */             
/* 109 */             f13 = textureatlassprite.getInterpolatedU(0.0D);
/* 110 */             f17 = textureatlassprite.getInterpolatedV(0.0D);
/* 111 */             f14 = f13;
/* 112 */             f18 = textureatlassprite.getInterpolatedV(16.0D);
/* 113 */             f15 = textureatlassprite.getInterpolatedU(16.0D);
/* 114 */             f19 = f18;
/* 115 */             f16 = f15;
/* 116 */             f20 = f17;
/*     */           }
/*     */           else {
/*     */             
/* 120 */             float f21 = MathHelper.sin(f12) * 0.25F;
/* 121 */             float f22 = MathHelper.cos(f12) * 0.25F;
/* 122 */             float f23 = 8.0F;
/* 123 */             f13 = textureatlassprite.getInterpolatedU((8.0F + (-f22 - f21) * 16.0F));
/* 124 */             f17 = textureatlassprite.getInterpolatedV((8.0F + (-f22 + f21) * 16.0F));
/* 125 */             f14 = textureatlassprite.getInterpolatedU((8.0F + (-f22 + f21) * 16.0F));
/* 126 */             f18 = textureatlassprite.getInterpolatedV((8.0F + (f22 + f21) * 16.0F));
/* 127 */             f15 = textureatlassprite.getInterpolatedU((8.0F + (f22 + f21) * 16.0F));
/* 128 */             f19 = textureatlassprite.getInterpolatedV((8.0F + (f22 - f21) * 16.0F));
/* 129 */             f16 = textureatlassprite.getInterpolatedU((8.0F + (f22 - f21) * 16.0F));
/* 130 */             f20 = textureatlassprite.getInterpolatedV((8.0F + (-f22 - f21) * 16.0F));
/*     */           } 
/*     */           
/* 133 */           int k2 = blockliquid.getMixedBrightnessForBlock(blockAccess, blockPosIn);
/* 134 */           int l2 = k2 >> 16 & 0xFFFF;
/* 135 */           int i3 = k2 & 0xFFFF;
/* 136 */           float f24 = f4 * f;
/* 137 */           float f25 = f4 * f1;
/* 138 */           float f26 = f4 * f2;
/* 139 */           worldRendererIn.pos(d0 + 0.0D, d1 + f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
/* 140 */           worldRendererIn.pos(d0 + 0.0D, d1 + f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
/* 141 */           worldRendererIn.pos(d0 + 1.0D, d1 + f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
/* 142 */           worldRendererIn.pos(d0 + 1.0D, d1 + f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
/*     */           
/* 144 */           if (blockliquid.shouldRenderSides(blockAccess, blockPosIn.up())) {
/*     */             
/* 146 */             worldRendererIn.pos(d0 + 0.0D, d1 + f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
/* 147 */             worldRendererIn.pos(d0 + 1.0D, d1 + f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
/* 148 */             worldRendererIn.pos(d0 + 1.0D, d1 + f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
/* 149 */             worldRendererIn.pos(d0 + 0.0D, d1 + f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
/*     */           } 
/*     */         } 
/*     */         
/* 153 */         if (flag1) {
/*     */           
/* 155 */           worldRendererIn.setSprite(atextureatlassprite[0]);
/* 156 */           float f35 = atextureatlassprite[0].getMinU();
/* 157 */           float f36 = atextureatlassprite[0].getMaxU();
/* 158 */           float f37 = atextureatlassprite[0].getMinV();
/* 159 */           float f38 = atextureatlassprite[0].getMaxV();
/* 160 */           int l1 = blockliquid.getMixedBrightnessForBlock(blockAccess, blockPosIn.down());
/* 161 */           int i2 = l1 >> 16 & 0xFFFF;
/* 162 */           int j2 = l1 & 0xFFFF;
/* 163 */           float f41 = FaceBakery.getFaceBrightness(EnumFacing.DOWN);
/* 164 */           worldRendererIn.pos(d0, d1, d2 + 1.0D).color(f * f41, f1 * f41, f2 * f41, 1.0F).tex(f35, f38).lightmap(i2, j2).endVertex();
/* 165 */           worldRendererIn.pos(d0, d1, d2).color(f * f41, f1 * f41, f2 * f41, 1.0F).tex(f35, f37).lightmap(i2, j2).endVertex();
/* 166 */           worldRendererIn.pos(d0 + 1.0D, d1, d2).color(f * f41, f1 * f41, f2 * f41, 1.0F).tex(f36, f37).lightmap(i2, j2).endVertex();
/* 167 */           worldRendererIn.pos(d0 + 1.0D, d1, d2 + 1.0D).color(f * f41, f1 * f41, f2 * f41, 1.0F).tex(f36, f38).lightmap(i2, j2).endVertex();
/* 168 */           bool = true;
/*     */         } 
/*     */         
/* 171 */         for (int i1 = 0; i1 < 4; i1++) {
/*     */           
/* 173 */           int j1 = 0;
/* 174 */           int k1 = 0;
/*     */           
/* 176 */           if (i1 == 0)
/*     */           {
/* 178 */             k1--;
/*     */           }
/*     */           
/* 181 */           if (i1 == 1)
/*     */           {
/* 183 */             k1++;
/*     */           }
/*     */           
/* 186 */           if (i1 == 2)
/*     */           {
/* 188 */             j1--;
/*     */           }
/*     */           
/* 191 */           if (i1 == 3)
/*     */           {
/* 193 */             j1++;
/*     */           }
/*     */           
/* 196 */           BlockPos blockpos = blockPosIn.add(j1, 0, k1);
/* 197 */           TextureAtlasSprite textureatlassprite1 = atextureatlassprite[1];
/* 198 */           worldRendererIn.setSprite(textureatlassprite1);
/*     */           
/* 200 */           if (aboolean[i1]) {
/*     */             float f39, f40;
/*     */ 
/*     */ 
/*     */             
/*     */             double d3, d4, d5, d6;
/*     */ 
/*     */ 
/*     */             
/* 209 */             if (i1 == 0) {
/*     */               
/* 211 */               f39 = f7;
/* 212 */               f40 = f10;
/* 213 */               d3 = d0;
/* 214 */               d5 = d0 + 1.0D;
/* 215 */               d4 = d2 + f11;
/* 216 */               d6 = d2 + f11;
/*     */             }
/* 218 */             else if (i1 == 1) {
/*     */               
/* 220 */               f39 = f9;
/* 221 */               f40 = f8;
/* 222 */               d3 = d0 + 1.0D;
/* 223 */               d5 = d0;
/* 224 */               d4 = d2 + 1.0D - f11;
/* 225 */               d6 = d2 + 1.0D - f11;
/*     */             }
/* 227 */             else if (i1 == 2) {
/*     */               
/* 229 */               f39 = f8;
/* 230 */               f40 = f7;
/* 231 */               d3 = d0 + f11;
/* 232 */               d5 = d0 + f11;
/* 233 */               d4 = d2 + 1.0D;
/* 234 */               d6 = d2;
/*     */             }
/*     */             else {
/*     */               
/* 238 */               f39 = f10;
/* 239 */               f40 = f9;
/* 240 */               d3 = d0 + 1.0D - f11;
/* 241 */               d5 = d0 + 1.0D - f11;
/* 242 */               d4 = d2;
/* 243 */               d6 = d2 + 1.0D;
/*     */             } 
/*     */             
/* 246 */             bool = true;
/* 247 */             float f42 = textureatlassprite1.getInterpolatedU(0.0D);
/* 248 */             float f27 = textureatlassprite1.getInterpolatedU(8.0D);
/* 249 */             float f28 = textureatlassprite1.getInterpolatedV(((1.0F - f39) * 16.0F * 0.5F));
/* 250 */             float f29 = textureatlassprite1.getInterpolatedV(((1.0F - f40) * 16.0F * 0.5F));
/* 251 */             float f30 = textureatlassprite1.getInterpolatedV(8.0D);
/* 252 */             int j = blockliquid.getMixedBrightnessForBlock(blockAccess, blockpos);
/* 253 */             int k = j >> 16 & 0xFFFF;
/* 254 */             int l = j & 0xFFFF;
/* 255 */             float f31 = (i1 < 2) ? FaceBakery.getFaceBrightness(EnumFacing.NORTH) : FaceBakery.getFaceBrightness(EnumFacing.WEST);
/* 256 */             float f32 = f4 * f31 * f;
/* 257 */             float f33 = f4 * f31 * f1;
/* 258 */             float f34 = f4 * f31 * f2;
/* 259 */             worldRendererIn.pos(d3, d1 + f39, d4).color(f32, f33, f34, 1.0F).tex(f42, f28).lightmap(k, l).endVertex();
/* 260 */             worldRendererIn.pos(d5, d1 + f40, d6).color(f32, f33, f34, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
/* 261 */             worldRendererIn.pos(d5, d1 + 0.0D, d6).color(f32, f33, f34, 1.0F).tex(f27, f30).lightmap(k, l).endVertex();
/* 262 */             worldRendererIn.pos(d3, d1 + 0.0D, d4).color(f32, f33, f34, 1.0F).tex(f42, f30).lightmap(k, l).endVertex();
/* 263 */             worldRendererIn.pos(d3, d1 + 0.0D, d4).color(f32, f33, f34, 1.0F).tex(f42, f30).lightmap(k, l).endVertex();
/* 264 */             worldRendererIn.pos(d5, d1 + 0.0D, d6).color(f32, f33, f34, 1.0F).tex(f27, f30).lightmap(k, l).endVertex();
/* 265 */             worldRendererIn.pos(d5, d1 + f40, d6).color(f32, f33, f34, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
/* 266 */             worldRendererIn.pos(d3, d1 + f39, d4).color(f32, f33, f34, 1.0F).tex(f42, f28).lightmap(k, l).endVertex();
/*     */           } 
/*     */         } 
/*     */         
/* 270 */         worldRendererIn.setSprite((TextureAtlasSprite)null);
/* 271 */         return bool;
/*     */       } 
/*     */       
/* 274 */       flag2 = false;
/*     */     }
/*     */     finally {
/*     */       
/* 278 */       if (Config.isShaders())
/*     */       {
/* 280 */         SVertexBuilder.popEntity(worldRendererIn);
/*     */       }
/*     */     } 
/*     */     
/* 284 */     return flag2;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getFluidHeight(IBlockAccess blockAccess, BlockPos blockPosIn, Material blockMaterial) {
/* 289 */     int i = 0;
/* 290 */     float f = 0.0F;
/*     */     
/* 292 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 294 */       BlockPos blockpos = blockPosIn.add(-(j & 0x1), 0, -(j >> 1 & 0x1));
/*     */       
/* 296 */       if (blockAccess.getBlockState(blockpos.up()).getBlock().getMaterial() == blockMaterial)
/*     */       {
/* 298 */         return 1.0F;
/*     */       }
/*     */       
/* 301 */       IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/* 302 */       Material material = iblockstate.getBlock().getMaterial();
/*     */       
/* 304 */       if (material != blockMaterial) {
/*     */         
/* 306 */         if (!material.isSolid())
/*     */         {
/* 308 */           f++;
/* 309 */           i++;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 314 */         int k = ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue();
/*     */         
/* 316 */         if (k >= 8 || k == 0) {
/*     */           
/* 318 */           f += BlockLiquid.getLiquidHeightPercent(k) * 10.0F;
/* 319 */           i += 10;
/*     */         } 
/*     */         
/* 322 */         f += BlockLiquid.getLiquidHeightPercent(k);
/* 323 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 327 */     return 1.0F - f / i;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\BlockFluidRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */