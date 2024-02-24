/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ 
/*     */ 
/*     */ public class CompiledChunk
/*     */ {
/*  14 */   public static final CompiledChunk DUMMY = new CompiledChunk()
/*     */     {
/*     */       protected void setLayerUsed(EnumWorldBlockLayer layer)
/*     */       {
/*  18 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public void setLayerStarted(EnumWorldBlockLayer layer) {
/*  22 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/*  26 */         return false;
/*     */       }
/*     */       
/*     */       public void setAnimatedSprites(EnumWorldBlockLayer p_setAnimatedSprites_1_, BitSet p_setAnimatedSprites_2_) {
/*  30 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     };
/*  33 */   private final boolean[] layersUsed = new boolean[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/*  34 */   private final boolean[] layersStarted = new boolean[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/*     */   private boolean empty = true;
/*  36 */   private final List<TileEntity> tileEntities = Lists.newArrayList();
/*  37 */   private SetVisibility setVisibility = new SetVisibility();
/*     */   private WorldRenderer.State state;
/*  39 */   private final BitSet[] animatedSprites = new BitSet[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  43 */     return this.empty;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setLayerUsed(EnumWorldBlockLayer layer) {
/*  48 */     this.empty = false;
/*  49 */     this.layersUsed[layer.ordinal()] = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLayerEmpty(EnumWorldBlockLayer layer) {
/*  54 */     return !this.layersUsed[layer.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLayerStarted(EnumWorldBlockLayer layer) {
/*  59 */     this.layersStarted[layer.ordinal()] = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLayerStarted(EnumWorldBlockLayer layer) {
/*  64 */     return this.layersStarted[layer.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TileEntity> getTileEntities() {
/*  69 */     return this.tileEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTileEntity(TileEntity tileEntityIn) {
/*  74 */     this.tileEntities.add(tileEntityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/*  79 */     return this.setVisibility.isVisible(facing, facing2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisibility(SetVisibility visibility) {
/*  84 */     this.setVisibility = visibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldRenderer.State getState() {
/*  89 */     return this.state;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(WorldRenderer.State stateIn) {
/*  94 */     this.state = stateIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public BitSet getAnimatedSprites(EnumWorldBlockLayer p_getAnimatedSprites_1_) {
/*  99 */     return this.animatedSprites[p_getAnimatedSprites_1_.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnimatedSprites(EnumWorldBlockLayer p_setAnimatedSprites_1_, BitSet p_setAnimatedSprites_2_) {
/* 104 */     this.animatedSprites[p_setAnimatedSprites_1_.ordinal()] = p_setAnimatedSprites_2_;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\chunk\CompiledChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */