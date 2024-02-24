/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ 
/*    */ public class NBTTagEnd
/*    */   extends NBTBase {
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) {
/*  9 */     sizeTracker.read(64L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 23 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     return "END";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase copy() {
/* 36 */     return new NBTTagEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\nbt\NBTTagEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */