/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.BlockModelRenderer;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.model.ListQuadsOverlay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderEnv
/*     */ {
/*     */   private IBlockState blockState;
/*     */   private BlockPos blockPos;
/*  27 */   private int blockId = -1;
/*  28 */   private int metadata = -1;
/*  29 */   private int breakingAnimation = -1;
/*  30 */   private int smartLeaves = -1;
/*  31 */   private final float[] quadBounds = new float[EnumFacing.VALUES.length << 1];
/*  32 */   private final BitSet boundsFlags = new BitSet(3);
/*  33 */   private final BlockModelRenderer.AmbientOcclusionFace aoFace = new BlockModelRenderer.AmbientOcclusionFace();
/*  34 */   private BlockPosM colorizerBlockPosM = null;
/*  35 */   private boolean[] borderFlags = null;
/*  36 */   private boolean[] borderFlags2 = null;
/*  37 */   private boolean[] borderFlags3 = null;
/*  38 */   private EnumFacing[] borderDirections = null;
/*  39 */   private final List<BakedQuad> listQuadsCustomizer = new ArrayList<>();
/*  40 */   private final List<BakedQuad> listQuadsCtmMultipass = new ArrayList<>();
/*  41 */   private final BakedQuad[] arrayQuadsCtm1 = new BakedQuad[1];
/*  42 */   private final BakedQuad[] arrayQuadsCtm2 = new BakedQuad[2];
/*  43 */   private final BakedQuad[] arrayQuadsCtm3 = new BakedQuad[3];
/*  44 */   private final BakedQuad[] arrayQuadsCtm4 = new BakedQuad[4];
/*  45 */   private RegionRenderCacheBuilder regionRenderCacheBuilder = null;
/*  46 */   private final ListQuadsOverlay[] listsQuadsOverlay = new ListQuadsOverlay[(EnumWorldBlockLayer.values()).length];
/*     */   
/*     */   private boolean overlaysRendered = false;
/*     */   private static final int UNKNOWN = -1;
/*     */   private static final int FALSE = 0;
/*     */   private static final int TRUE = 1;
/*     */   
/*     */   public RenderEnv(IBlockState blockState, BlockPos blockPos) {
/*  54 */     this.blockState = blockState;
/*  55 */     this.blockPos = blockPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset(IBlockState blockStateIn, BlockPos blockPosIn) {
/*  60 */     if (this.blockState != blockStateIn || this.blockPos != blockPosIn) {
/*     */       
/*  62 */       this.blockState = blockStateIn;
/*  63 */       this.blockPos = blockPosIn;
/*  64 */       this.blockId = -1;
/*  65 */       this.metadata = -1;
/*  66 */       this.breakingAnimation = -1;
/*  67 */       this.smartLeaves = -1;
/*  68 */       this.boundsFlags.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockId() {
/*  74 */     if (this.blockId < 0)
/*     */     {
/*  76 */       if (this.blockState instanceof BlockStateBase) {
/*     */         
/*  78 */         BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
/*  79 */         this.blockId = blockstatebase.getBlockId();
/*     */       }
/*     */       else {
/*     */         
/*  83 */         this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
/*     */       } 
/*     */     }
/*     */     
/*  87 */     return this.blockId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMetadata() {
/*  92 */     if (this.metadata < 0)
/*     */     {
/*  94 */       if (this.blockState instanceof BlockStateBase) {
/*     */         
/*  96 */         BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
/*  97 */         this.metadata = blockstatebase.getMetadata();
/*     */       }
/*     */       else {
/*     */         
/* 101 */         this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
/*     */       } 
/*     */     }
/*     */     
/* 105 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getQuadBounds() {
/* 110 */     return this.quadBounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public BitSet getBoundsFlags() {
/* 115 */     return this.boundsFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
/* 120 */     return this.aoFace;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation(List listQuads) {
/* 125 */     if (this.breakingAnimation == -1 && !listQuads.isEmpty())
/*     */     {
/* 127 */       if (listQuads.get(0) instanceof net.minecraft.client.renderer.block.model.BreakingFour) {
/*     */         
/* 129 */         this.breakingAnimation = 1;
/*     */       }
/*     */       else {
/*     */         
/* 133 */         this.breakingAnimation = 0;
/*     */       } 
/*     */     }
/*     */     
/* 137 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation(BakedQuad quad) {
/* 142 */     if (this.breakingAnimation < 0)
/*     */     {
/* 144 */       if (quad instanceof net.minecraft.client.renderer.block.model.BreakingFour) {
/*     */         
/* 146 */         this.breakingAnimation = 1;
/*     */       }
/*     */       else {
/*     */         
/* 150 */         this.breakingAnimation = 0;
/*     */       } 
/*     */     }
/*     */     
/* 154 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation() {
/* 159 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState() {
/* 164 */     return this.blockState;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM getColorizerBlockPosM() {
/* 169 */     if (this.colorizerBlockPosM == null)
/*     */     {
/* 171 */       this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
/*     */     }
/*     */     
/* 174 */     return this.colorizerBlockPosM;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags() {
/* 179 */     if (this.borderFlags == null)
/*     */     {
/* 181 */       this.borderFlags = new boolean[4];
/*     */     }
/*     */     
/* 184 */     return this.borderFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags2() {
/* 189 */     if (this.borderFlags2 == null)
/*     */     {
/* 191 */       this.borderFlags2 = new boolean[4];
/*     */     }
/*     */     
/* 194 */     return this.borderFlags2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags3() {
/* 199 */     if (this.borderFlags3 == null)
/*     */     {
/* 201 */       this.borderFlags3 = new boolean[4];
/*     */     }
/*     */     
/* 204 */     return this.borderFlags3;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing[] getBorderDirections() {
/* 209 */     if (this.borderDirections == null)
/*     */     {
/* 211 */       this.borderDirections = new EnumFacing[4];
/*     */     }
/*     */     
/* 214 */     return this.borderDirections;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing[] getBorderDirections(EnumFacing dir0, EnumFacing dir1, EnumFacing dir2, EnumFacing dir3) {
/* 219 */     EnumFacing[] aenumfacing = getBorderDirections();
/* 220 */     aenumfacing[0] = dir0;
/* 221 */     aenumfacing[1] = dir1;
/* 222 */     aenumfacing[2] = dir2;
/* 223 */     aenumfacing[3] = dir3;
/* 224 */     return aenumfacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSmartLeaves() {
/* 229 */     if (this.smartLeaves == -1)
/*     */     {
/* 231 */       if (Config.isTreesSmart() && this.blockState.getBlock() instanceof net.minecraft.block.BlockLeaves) {
/*     */         
/* 233 */         this.smartLeaves = 1;
/*     */       }
/*     */       else {
/*     */         
/* 237 */         this.smartLeaves = 0;
/*     */       } 
/*     */     }
/*     */     
/* 241 */     return (this.smartLeaves == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getListQuadsCustomizer() {
/* 246 */     return this.listQuadsCustomizer;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad) {
/* 251 */     this.arrayQuadsCtm1[0] = quad;
/* 252 */     return this.arrayQuadsCtm1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1) {
/* 257 */     this.arrayQuadsCtm2[0] = quad0;
/* 258 */     this.arrayQuadsCtm2[1] = quad1;
/* 259 */     return this.arrayQuadsCtm2;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1, BakedQuad quad2) {
/* 264 */     this.arrayQuadsCtm3[0] = quad0;
/* 265 */     this.arrayQuadsCtm3[1] = quad1;
/* 266 */     this.arrayQuadsCtm3[2] = quad2;
/* 267 */     return this.arrayQuadsCtm3;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1, BakedQuad quad2, BakedQuad quad3) {
/* 272 */     this.arrayQuadsCtm4[0] = quad0;
/* 273 */     this.arrayQuadsCtm4[1] = quad1;
/* 274 */     this.arrayQuadsCtm4[2] = quad2;
/* 275 */     this.arrayQuadsCtm4[3] = quad3;
/* 276 */     return this.arrayQuadsCtm4;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getListQuadsCtmMultipass(BakedQuad[] quads) {
/* 281 */     this.listQuadsCtmMultipass.clear();
/*     */     
/* 283 */     if (quads != null)
/*     */     {
/* 285 */       this.listQuadsCtmMultipass.addAll(Arrays.asList(quads));
/*     */     }
/*     */     
/* 288 */     return this.listQuadsCtmMultipass;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
/* 293 */     return this.regionRenderCacheBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilder) {
/* 298 */     this.regionRenderCacheBuilder = regionRenderCacheBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListQuadsOverlay getListQuadsOverlay(EnumWorldBlockLayer layer) {
/* 303 */     ListQuadsOverlay listquadsoverlay = this.listsQuadsOverlay[layer.ordinal()];
/*     */     
/* 305 */     if (listquadsoverlay == null) {
/*     */       
/* 307 */       listquadsoverlay = new ListQuadsOverlay();
/* 308 */       this.listsQuadsOverlay[layer.ordinal()] = listquadsoverlay;
/*     */     } 
/*     */     
/* 311 */     return listquadsoverlay;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOverlaysRendered() {
/* 316 */     return this.overlaysRendered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverlaysRendered(boolean overlaysRendered) {
/* 321 */     this.overlaysRendered = overlaysRendered;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\RenderEnv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */