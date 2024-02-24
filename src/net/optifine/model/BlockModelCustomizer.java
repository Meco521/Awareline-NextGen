/*     */ package net.optifine.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.BetterGrass;
/*     */ import net.optifine.ConnectedTextures;
/*     */ import net.optifine.NaturalTextures;
/*     */ import net.optifine.SmartLeaves;
/*     */ import net.optifine.render.RenderEnv;
/*     */ 
/*     */ 
/*     */ public class BlockModelCustomizer
/*     */ {
/*  22 */   private static final List<BakedQuad> NO_QUADS = (List<BakedQuad>)ImmutableList.of();
/*     */ 
/*     */   
/*     */   public static IBakedModel getRenderModel(IBakedModel modelIn, IBlockState stateIn, RenderEnv renderEnv) {
/*  26 */     if (renderEnv.isSmartLeaves())
/*     */     {
/*  28 */       modelIn = SmartLeaves.getLeavesModel(modelIn, stateIn);
/*     */     }
/*     */     
/*  31 */     return modelIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<BakedQuad> getRenderQuads(List<BakedQuad> quads, IBlockAccess worldIn, IBlockState stateIn, BlockPos posIn, EnumFacing enumfacing, EnumWorldBlockLayer layer, long rand, RenderEnv renderEnv) {
/*  36 */     if (enumfacing != null) {
/*     */       
/*  38 */       if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(worldIn.getBlockState(posIn.offset(enumfacing)), stateIn))
/*     */       {
/*  40 */         return NO_QUADS;
/*     */       }
/*     */       
/*  43 */       if (!renderEnv.isBreakingAnimation(quads) && Config.isBetterGrass())
/*     */       {
/*  45 */         quads = BetterGrass.getFaceQuads(worldIn, stateIn, posIn, enumfacing, quads);
/*     */       }
/*     */     } 
/*     */     
/*  49 */     List<BakedQuad> list = renderEnv.getListQuadsCustomizer();
/*  50 */     list.clear();
/*     */     
/*  52 */     for (int i = 0; i < quads.size(); i++) {
/*     */       
/*  54 */       BakedQuad bakedquad = quads.get(i);
/*  55 */       BakedQuad[] abakedquad = getRenderQuads(bakedquad, worldIn, stateIn, posIn, enumfacing, rand, renderEnv);
/*     */       
/*  57 */       if (i == 0 && quads.size() == 1 && abakedquad.length == 1 && abakedquad[0] == bakedquad && bakedquad.getQuadEmissive() == null)
/*     */       {
/*  59 */         return quads;
/*     */       }
/*     */       
/*  62 */       for (int j = 0; j < abakedquad.length; j++) {
/*     */         
/*  64 */         BakedQuad bakedquad1 = abakedquad[j];
/*  65 */         list.add(bakedquad1);
/*     */         
/*  67 */         if (bakedquad1.getQuadEmissive() != null) {
/*     */           
/*  69 */           renderEnv.getListQuadsOverlay(getEmissiveLayer(layer)).addQuad(bakedquad1.getQuadEmissive(), stateIn);
/*  70 */           renderEnv.setOverlaysRendered(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumWorldBlockLayer getEmissiveLayer(EnumWorldBlockLayer layer) {
/*  80 */     return (layer != null && layer != EnumWorldBlockLayer.SOLID) ? layer : EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getRenderQuads(BakedQuad quad, IBlockAccess worldIn, IBlockState stateIn, BlockPos posIn, EnumFacing enumfacing, long rand, RenderEnv renderEnv) {
/*  85 */     if (renderEnv.isBreakingAnimation(quad))
/*     */     {
/*  87 */       return renderEnv.getArrayQuadsCtm(quad);
/*     */     }
/*     */ 
/*     */     
/*  91 */     BakedQuad bakedquad = quad;
/*     */     
/*  93 */     if (Config.isConnectedTextures()) {
/*     */       
/*  95 */       BakedQuad[] abakedquad = ConnectedTextures.getConnectedTexture(worldIn, stateIn, posIn, quad, renderEnv);
/*     */       
/*  97 */       if (abakedquad.length != 1 || abakedquad[0] != quad)
/*     */       {
/*  99 */         return abakedquad;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     if (Config.isNaturalTextures()) {
/*     */       
/* 105 */       quad = NaturalTextures.getNaturalTexture(posIn, quad);
/*     */       
/* 107 */       if (quad != bakedquad)
/*     */       {
/* 109 */         return renderEnv.getArrayQuadsCtm(quad);
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return renderEnv.getArrayQuadsCtm(quad);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\model\BlockModelCustomizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */