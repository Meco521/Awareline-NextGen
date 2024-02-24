/*    */ package com.me.guichaguri.betterfps.clones.world;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.transformers.cloner.CopyMode;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ 
/*    */ public class ChunkLogic
/*    */   extends Chunk
/*    */ {
/*    */   public boolean shouldTick;
/*    */   
/*    */   @CopyMode(CopyMode.Mode.IGNORE)
/*    */   private ChunkLogic(World worldIn, int x, int z) {
/* 15 */     super(worldIn, x, z);
/*    */ 
/*    */     
/* 18 */     this.shouldTick = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\clones\world\ChunkLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */