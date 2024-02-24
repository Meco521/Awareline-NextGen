/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public class FrameTimer
/*    */ {
/*  6 */   private final long[] frames = new long[240];
/*    */ 
/*    */ 
/*    */   
/*    */   private int lastIndex;
/*    */ 
/*    */ 
/*    */   
/*    */   private int counter;
/*    */ 
/*    */ 
/*    */   
/*    */   private int index;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addFrame(long runningTime) {
/* 24 */     this.frames[this.index] = runningTime;
/* 25 */     this.index++;
/*    */     
/* 27 */     if (this.index == 240)
/*    */     {
/* 29 */       this.index = 0;
/*    */     }
/*    */     
/* 32 */     if (this.counter < 240) {
/*    */       
/* 34 */       this.lastIndex = 0;
/* 35 */       this.counter++;
/*    */     }
/*    */     else {
/*    */       
/* 39 */       this.lastIndex = parseIndex(this.index + 1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLagometerValue(long time, int multiplier) {
/* 51 */     double d0 = time / 1.6666666E7D;
/* 52 */     return (int)(d0 * multiplier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLastIndex() {
/* 60 */     return this.lastIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 68 */     return this.index;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int parseIndex(int rawIndex) {
/* 78 */     return rawIndex % 240;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long[] getFrames() {
/* 86 */     return this.frames;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\FrameTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */