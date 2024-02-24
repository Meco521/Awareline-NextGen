/*     */ package net.optifine.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelUtils
/*     */ {
/*     */   public static void dbgModel(IBakedModel model) {
/*  16 */     if (model != null) {
/*     */       
/*  18 */       Config.dbg("Model: " + model + ", ao: " + model.isAmbientOcclusion() + ", gui3d: " + model.isGui3d() + ", builtIn: " + model.isBuiltInRenderer() + ", particle: " + model.getParticleTexture());
/*  19 */       EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */       
/*  21 */       for (int i = 0; i < aenumfacing.length; i++) {
/*     */         
/*  23 */         EnumFacing enumfacing = aenumfacing[i];
/*  24 */         List list = model.getFaceQuads(enumfacing);
/*  25 */         dbgQuads(enumfacing.getName(), list, "  ");
/*     */       } 
/*     */       
/*  28 */       List list1 = model.getGeneralQuads();
/*  29 */       dbgQuads("General", list1, "  ");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbgQuads(String name, List quads, String prefix) {
/*  35 */     for (Object e : quads) {
/*     */       
/*  37 */       BakedQuad bakedquad = (BakedQuad)e;
/*  38 */       dbgQuad(name, bakedquad, prefix);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbgQuad(String name, BakedQuad quad, String prefix) {
/*  44 */     Config.dbg(prefix + "Quad: " + quad.getClass().getName() + ", type: " + name + ", face: " + quad.getFace() + ", tint: " + quad.getTintIndex() + ", sprite: " + quad.getSprite());
/*  45 */     dbgVertexData(quad.getVertexData(), "  " + prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbgVertexData(int[] vd, String prefix) {
/*  50 */     int i = vd.length / 4;
/*  51 */     Config.dbg(prefix + "Length: " + vd.length + ", step: " + i);
/*     */     
/*  53 */     for (int j = 0; j < 4; j++) {
/*     */       
/*  55 */       int k = j * i;
/*  56 */       float f = Float.intBitsToFloat(vd[k]);
/*  57 */       float f1 = Float.intBitsToFloat(vd[k + 1]);
/*  58 */       float f2 = Float.intBitsToFloat(vd[k + 2]);
/*  59 */       int l = vd[k + 3];
/*  60 */       float f3 = Float.intBitsToFloat(vd[k + 4]);
/*  61 */       float f4 = Float.intBitsToFloat(vd[k + 5]);
/*  62 */       Config.dbg(prefix + j + " xyz: " + f + "," + f1 + "," + f2 + " col: " + l + " u,v: " + f3 + "," + f4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBakedModel duplicateModel(IBakedModel model) {
/*  68 */     List list = duplicateQuadList(model.getGeneralQuads());
/*  69 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*  70 */     List<List> list1 = new ArrayList();
/*     */     
/*  72 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/*  74 */       EnumFacing enumfacing = aenumfacing[i];
/*  75 */       List list2 = model.getFaceQuads(enumfacing);
/*  76 */       List list3 = duplicateQuadList(list2);
/*  77 */       list1.add(list3);
/*     */     } 
/*     */     
/*  80 */     SimpleBakedModel simplebakedmodel = new SimpleBakedModel(list, list1, model.isAmbientOcclusion(), model.isGui3d(), model.getParticleTexture(), model.getItemCameraTransforms());
/*  81 */     return (IBakedModel)simplebakedmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List duplicateQuadList(List lists) {
/*  86 */     List<BakedQuad> list = new ArrayList();
/*     */     
/*  88 */     for (Object e : lists) {
/*     */       
/*  90 */       BakedQuad bakedquad = (BakedQuad)e;
/*  91 */       BakedQuad bakedquad1 = duplicateQuad(bakedquad);
/*  92 */       list.add(bakedquad1);
/*     */     } 
/*     */     
/*  95 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BakedQuad duplicateQuad(BakedQuad quad) {
/* 100 */     BakedQuad bakedquad = new BakedQuad((int[])quad.getVertexData().clone(), quad.getTintIndex(), quad.getFace(), quad.getSprite());
/* 101 */     return bakedquad;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\model\ModelUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */