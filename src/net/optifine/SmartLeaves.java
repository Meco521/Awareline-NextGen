/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockNewLeaf;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.model.ModelUtils;
/*     */ 
/*     */ public class SmartLeaves
/*     */ {
/*  22 */   private static IBakedModel modelLeavesCullAcacia = null;
/*  23 */   private static IBakedModel modelLeavesCullBirch = null;
/*  24 */   private static IBakedModel modelLeavesCullDarkOak = null;
/*  25 */   private static IBakedModel modelLeavesCullJungle = null;
/*  26 */   private static IBakedModel modelLeavesCullOak = null;
/*  27 */   private static IBakedModel modelLeavesCullSpruce = null;
/*  28 */   private static List generalQuadsCullAcacia = null;
/*  29 */   private static List generalQuadsCullBirch = null;
/*  30 */   private static List generalQuadsCullDarkOak = null;
/*  31 */   private static List generalQuadsCullJungle = null;
/*  32 */   private static List generalQuadsCullOak = null;
/*  33 */   private static List generalQuadsCullSpruce = null;
/*  34 */   private static IBakedModel modelLeavesDoubleAcacia = null;
/*  35 */   private static IBakedModel modelLeavesDoubleBirch = null;
/*  36 */   private static IBakedModel modelLeavesDoubleDarkOak = null;
/*  37 */   private static IBakedModel modelLeavesDoubleJungle = null;
/*  38 */   private static IBakedModel modelLeavesDoubleOak = null;
/*  39 */   private static IBakedModel modelLeavesDoubleSpruce = null;
/*     */ 
/*     */   
/*     */   public static IBakedModel getLeavesModel(IBakedModel model, IBlockState stateIn) {
/*  43 */     if (!Config.isTreesSmart())
/*     */     {
/*  45 */       return model;
/*     */     }
/*     */ 
/*     */     
/*  49 */     List list = model.getGeneralQuads();
/*  50 */     return (list == generalQuadsCullAcacia) ? modelLeavesDoubleAcacia : ((list == generalQuadsCullBirch) ? modelLeavesDoubleBirch : ((list == generalQuadsCullDarkOak) ? modelLeavesDoubleDarkOak : ((list == generalQuadsCullJungle) ? modelLeavesDoubleJungle : ((list == generalQuadsCullOak) ? modelLeavesDoubleOak : ((list == generalQuadsCullSpruce) ? modelLeavesDoubleSpruce : model)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSameLeaves(IBlockState state1, IBlockState state2) {
/*  56 */     if (state1 == state2)
/*     */     {
/*  58 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  62 */     Block block = state1.getBlock();
/*  63 */     Block block1 = state2.getBlock();
/*  64 */     return (block != block1) ? false : ((block instanceof BlockOldLeaf) ? ((BlockPlanks.EnumType)state1.getValue((IProperty)BlockOldLeaf.VARIANT)).equals(state2.getValue((IProperty)BlockOldLeaf.VARIANT)) : ((block instanceof BlockNewLeaf) ? ((BlockPlanks.EnumType)state1.getValue((IProperty)BlockNewLeaf.VARIANT)).equals(state2.getValue((IProperty)BlockNewLeaf.VARIANT)) : false));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateLeavesModels() {
/*  70 */     List list = new ArrayList();
/*  71 */     modelLeavesCullAcacia = getModelCull("acacia", list);
/*  72 */     modelLeavesCullBirch = getModelCull("birch", list);
/*  73 */     modelLeavesCullDarkOak = getModelCull("dark_oak", list);
/*  74 */     modelLeavesCullJungle = getModelCull("jungle", list);
/*  75 */     modelLeavesCullOak = getModelCull("oak", list);
/*  76 */     modelLeavesCullSpruce = getModelCull("spruce", list);
/*  77 */     generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
/*  78 */     generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
/*  79 */     generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
/*  80 */     generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
/*  81 */     generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
/*  82 */     generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
/*  83 */     modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
/*  84 */     modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
/*  85 */     modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
/*  86 */     modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
/*  87 */     modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
/*  88 */     modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
/*     */     
/*  90 */     if (!list.isEmpty())
/*     */     {
/*  92 */       Config.dbg("Enable face culling: " + Config.arrayToString(list.toArray()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static List getGeneralQuadsSafe(IBakedModel model) {
/*  98 */     return (model == null) ? null : model.getGeneralQuads();
/*     */   }
/*     */ 
/*     */   
/*     */   static IBakedModel getModelCull(String type, List<String> updatedTypes) {
/* 103 */     ModelManager modelmanager = Config.getModelManager();
/*     */     
/* 105 */     if (modelmanager == null)
/*     */     {
/* 107 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 111 */     ResourceLocation resourcelocation = new ResourceLocation("blockstates/" + type + "_leaves.json");
/*     */     
/* 113 */     if (Config.getDefiningResourcePack(resourcelocation) != Config.getDefaultResourcePack())
/*     */     {
/* 115 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 119 */     ResourceLocation resourcelocation1 = new ResourceLocation("models/block/" + type + "_leaves.json");
/*     */     
/* 121 */     if (Config.getDefiningResourcePack(resourcelocation1) != Config.getDefaultResourcePack())
/*     */     {
/* 123 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 127 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation(type + "_leaves", "normal");
/* 128 */     IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
/*     */     
/* 130 */     if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
/*     */       
/* 132 */       List list = ibakedmodel.getGeneralQuads();
/*     */       
/* 134 */       if (list.isEmpty())
/*     */       {
/* 136 */         return ibakedmodel;
/*     */       }
/* 138 */       if (list.size() != 6)
/*     */       {
/* 140 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 144 */       for (Object e : list) {
/*     */         
/* 146 */         BakedQuad bakedquad = (BakedQuad)e;
/* 147 */         List<BakedQuad> list1 = ibakedmodel.getFaceQuads(bakedquad.getFace());
/*     */         
/* 149 */         if (!list1.isEmpty())
/*     */         {
/* 151 */           return null;
/*     */         }
/*     */         
/* 154 */         list1.add(bakedquad);
/*     */       } 
/*     */       
/* 157 */       list.clear();
/* 158 */       updatedTypes.add(type + "_leaves");
/* 159 */       return ibakedmodel;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IBakedModel getModelDoubleFace(IBakedModel model) {
/* 173 */     if (model == null)
/*     */     {
/* 175 */       return null;
/*     */     }
/* 177 */     if (!model.getGeneralQuads().isEmpty()) {
/*     */       
/* 179 */       Config.warn("SmartLeaves: Model is not cube, general quads: " + model.getGeneralQuads().size() + ", model: " + model);
/* 180 */       return model;
/*     */     } 
/*     */ 
/*     */     
/* 184 */     EnumFacing[] aenumfacing = EnumFacing.VALUES;
/*     */     
/* 186 */     for (int i = 0; i < aenumfacing.length; i++) {
/*     */       
/* 188 */       EnumFacing enumfacing = aenumfacing[i];
/* 189 */       List<BakedQuad> list = model.getFaceQuads(enumfacing);
/*     */       
/* 191 */       if (list.size() != 1) {
/*     */         
/* 193 */         Config.warn("SmartLeaves: Model is not cube, side: " + enumfacing + ", quads: " + list.size() + ", model: " + model);
/* 194 */         return model;
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     IBakedModel ibakedmodel = ModelUtils.duplicateModel(model);
/* 199 */     List[] alist = new List[aenumfacing.length];
/*     */     
/* 201 */     for (int k = 0; k < aenumfacing.length; k++) {
/*     */       
/* 203 */       EnumFacing enumfacing1 = aenumfacing[k];
/* 204 */       List<BakedQuad> list1 = ibakedmodel.getFaceQuads(enumfacing1);
/* 205 */       BakedQuad bakedquad = list1.get(0);
/* 206 */       BakedQuad bakedquad1 = new BakedQuad((int[])bakedquad.getVertexData().clone(), bakedquad.getTintIndex(), bakedquad.getFace(), bakedquad.getSprite());
/* 207 */       int[] aint = bakedquad1.getVertexData();
/* 208 */       int[] aint1 = (int[])aint.clone();
/* 209 */       int j = aint.length / 4;
/* 210 */       System.arraycopy(aint, 0, aint1, 3 * j, j);
/* 211 */       System.arraycopy(aint, j, aint1, 2 * j, j);
/* 212 */       System.arraycopy(aint, 2 * j, aint1, j, j);
/* 213 */       System.arraycopy(aint, 3 * j, aint1, 0, j);
/* 214 */       System.arraycopy(aint1, 0, aint, 0, aint1.length);
/* 215 */       list1.add(bakedquad1);
/*     */     } 
/*     */     
/* 218 */     return ibakedmodel;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\SmartLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */