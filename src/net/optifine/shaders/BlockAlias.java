/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.config.MatchBlock;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockAlias
/*    */ {
/*    */   private final int blockAliasId;
/*    */   private final MatchBlock[] matchBlocks;
/*    */   
/*    */   public BlockAlias(int blockAliasId, MatchBlock[] matchBlocks) {
/* 18 */     this.blockAliasId = blockAliasId;
/* 19 */     this.matchBlocks = matchBlocks;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockAliasId() {
/* 24 */     return this.blockAliasId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(int id, int metadata) {
/* 29 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*    */       
/* 31 */       MatchBlock matchblock = this.matchBlocks[i];
/*    */       
/* 33 */       if (matchblock.matches(id, metadata))
/*    */       {
/* 35 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getMatchBlockIds() {
/* 44 */     Set<Integer> set = new HashSet<>();
/*    */     
/* 46 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*    */       
/* 48 */       MatchBlock matchblock = this.matchBlocks[i];
/* 49 */       int j = matchblock.getBlockId();
/* 50 */       set.add(Integer.valueOf(j));
/*    */     } 
/*    */     
/* 53 */     Integer[] ainteger = set.<Integer>toArray(new Integer[0]);
/* 54 */     int[] aint = Config.toPrimitive(ainteger);
/* 55 */     return aint;
/*    */   }
/*    */ 
/*    */   
/*    */   public MatchBlock[] getMatchBlocks(int matchBlockId) {
/* 60 */     List<MatchBlock> list = new ArrayList<>();
/*    */     
/* 62 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*    */       
/* 64 */       MatchBlock matchblock = this.matchBlocks[i];
/*    */       
/* 66 */       if (matchblock.getBlockId() == matchBlockId)
/*    */       {
/* 68 */         list.add(matchblock);
/*    */       }
/*    */     } 
/*    */     
/* 72 */     MatchBlock[] amatchblock = list.<MatchBlock>toArray(new MatchBlock[0]);
/* 73 */     return amatchblock;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return "block." + this.blockAliasId + "=" + Config.arrayToString((Object[])this.matchBlocks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\BlockAlias.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */