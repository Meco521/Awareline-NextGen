/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import awareline.main.mod.implement.globals.ChunkAnimator;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.BitSet;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.optifine.SmartAnimations;
/*    */ 
/*    */ 
/*    */ public abstract class ChunkRenderContainer
/*    */ {
/*    */   private double viewEntityX;
/*    */   private double viewEntityY;
/*    */   private double viewEntityZ;
/* 18 */   protected List<RenderChunk> renderChunks = Lists.newArrayListWithCapacity(17424);
/*    */   protected boolean initialized;
/*    */   private BitSet animatedSpritesRendered;
/* 21 */   private final BitSet animatedSpritesCached = new BitSet();
/*    */ 
/*    */   
/*    */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/* 25 */     this.initialized = true;
/* 26 */     this.renderChunks.clear();
/* 27 */     this.viewEntityX = viewEntityXIn;
/* 28 */     this.viewEntityY = viewEntityYIn;
/* 29 */     this.viewEntityZ = viewEntityZIn;
/*    */     
/* 31 */     if (SmartAnimations.isActive()) {
/*    */       
/* 33 */       if (this.animatedSpritesRendered != null) {
/*    */         
/* 35 */         SmartAnimations.spritesRendered(this.animatedSpritesRendered);
/*    */       }
/*    */       else {
/*    */         
/* 39 */         this.animatedSpritesRendered = this.animatedSpritesCached;
/*    */       } 
/*    */       
/* 42 */       this.animatedSpritesRendered.clear();
/*    */     }
/* 44 */     else if (this.animatedSpritesRendered != null) {
/*    */       
/* 46 */       SmartAnimations.spritesRendered(this.animatedSpritesRendered);
/* 47 */       this.animatedSpritesRendered = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void preRenderChunk(RenderChunk renderChunkIn) {
/* 53 */     if (ChunkAnimator.getInstance.isEnabled()) {
/* 54 */       ChunkAnimator.getInstance.animation.preRender(renderChunkIn);
/*    */     }
/* 56 */     BlockPos blockpos = renderChunkIn.getPosition();
/* 57 */     GlStateManager.translate((float)(blockpos.getX() - this.viewEntityX), (float)(blockpos.getY() - this.viewEntityY), (float)(blockpos.getZ() - this.viewEntityZ));
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRenderChunk(RenderChunk renderChunkIn, EnumWorldBlockLayer layer) {
/* 62 */     this.renderChunks.add(renderChunkIn);
/*    */     
/* 64 */     if (this.animatedSpritesRendered != null) {
/*    */       
/* 66 */       BitSet bitset = renderChunkIn.compiledChunk.getAnimatedSprites(layer);
/*    */       
/* 68 */       if (bitset != null)
/*    */       {
/* 70 */         this.animatedSpritesRendered.or(bitset);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void renderChunkLayer(EnumWorldBlockLayer paramEnumWorldBlockLayer);
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\ChunkRenderContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */