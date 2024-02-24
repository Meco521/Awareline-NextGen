/*    */ package net.optifine.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ 
/*    */ public class ListQuadsOverlay
/*    */ {
/* 13 */   private final List<BakedQuad> listQuads = new ArrayList<>();
/* 14 */   private final List<IBlockState> listBlockStates = new ArrayList<>();
/* 15 */   private final List<BakedQuad> listQuadsSingle = Arrays.asList(new BakedQuad[1]);
/*    */ 
/*    */   
/*    */   public void addQuad(BakedQuad quad, IBlockState blockState) {
/* 19 */     if (quad != null) {
/*    */       
/* 21 */       this.listQuads.add(quad);
/* 22 */       this.listBlockStates.add(blockState);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 28 */     return this.listQuads.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public BakedQuad getQuad(int index) {
/* 33 */     return this.listQuads.get(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public IBlockState getBlockState(int index) {
/* 38 */     return (index >= 0 && index < this.listBlockStates.size()) ? this.listBlockStates.get(index) : Blocks.air.getDefaultState();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getListQuadsSingle(BakedQuad quad) {
/* 43 */     this.listQuadsSingle.set(0, quad);
/* 44 */     return this.listQuadsSingle;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 49 */     this.listQuads.clear();
/* 50 */     this.listBlockStates.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\model\ListQuadsOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */