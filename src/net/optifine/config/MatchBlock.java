/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.block.state.BlockStateBase;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class MatchBlock
/*    */ {
/*  8 */   private int blockId = -1;
/*  9 */   private int[] metadatas = null;
/*    */ 
/*    */   
/*    */   public MatchBlock(int blockId) {
/* 13 */     this.blockId = blockId;
/*    */   }
/*    */ 
/*    */   
/*    */   public MatchBlock(int blockId, int metadata) {
/* 18 */     this.blockId = blockId;
/*    */     
/* 20 */     if (metadata >= 0 && metadata <= 15)
/*    */     {
/* 22 */       this.metadatas = new int[] { metadata };
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public MatchBlock(int blockId, int[] metadatas) {
/* 28 */     this.blockId = blockId;
/* 29 */     this.metadatas = metadatas;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockId() {
/* 34 */     return this.blockId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getMetadatas() {
/* 39 */     return this.metadatas;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(BlockStateBase blockState) {
/* 44 */     return (blockState.getBlockId() != this.blockId) ? false : Matches.metadata(blockState.getMetadata(), this.metadatas);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(int id, int metadata) {
/* 49 */     return (id != this.blockId) ? false : Matches.metadata(metadata, this.metadatas);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addMetadata(int metadata) {
/* 54 */     if (this.metadatas != null)
/*    */     {
/* 56 */       if (metadata >= 0 && metadata <= 15) {
/*    */         
/* 58 */         for (int i = 0; i < this.metadatas.length; i++) {
/*    */           
/* 60 */           if (this.metadatas[i] == metadata) {
/*    */             return;
/*    */           }
/*    */         } 
/*    */ 
/*    */         
/* 66 */         this.metadatas = Config.addIntToArray(this.metadatas, metadata);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 73 */     return this.blockId + ":" + Config.arrayToString(this.metadatas);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\config\MatchBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */