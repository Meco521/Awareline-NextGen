/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ public class FlipTextures
/*    */ {
/*    */   private final IntBuffer textures;
/*    */   private final int indexFlipped;
/*    */   private final boolean[] flips;
/*    */   private final boolean[] changed;
/*    */   
/*    */   public FlipTextures(IntBuffer textures, int indexFlipped) {
/* 15 */     this.textures = textures;
/* 16 */     this.indexFlipped = indexFlipped;
/* 17 */     this.flips = new boolean[textures.capacity()];
/* 18 */     this.changed = new boolean[textures.capacity()];
/*    */   }
/*    */ 
/*    */   
/*    */   public int getA(int index) {
/* 23 */     return get(index, this.flips[index]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getB(int index) {
/* 28 */     return get(index, !this.flips[index]);
/*    */   }
/*    */ 
/*    */   
/*    */   private int get(int index, boolean flipped) {
/* 33 */     int i = flipped ? this.indexFlipped : 0;
/* 34 */     return this.textures.get(i + index);
/*    */   }
/*    */ 
/*    */   
/*    */   public void flip(int index) {
/* 39 */     this.flips[index] = !this.flips[index];
/* 40 */     this.changed[index] = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isChanged(int index) {
/* 45 */     return this.changed[index];
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 50 */     Arrays.fill(this.flips, false);
/* 51 */     Arrays.fill(this.changed, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\FlipTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */