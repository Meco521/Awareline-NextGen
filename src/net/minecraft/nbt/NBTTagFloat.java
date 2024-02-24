/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NBTTagFloat
/*     */   extends NBTBase.NBTPrimitive
/*     */ {
/*     */   private float data;
/*     */   
/*     */   NBTTagFloat() {}
/*     */   
/*     */   public NBTTagFloat(float data) {
/*  20 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  28 */     output.writeFloat(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  33 */     sizeTracker.read(96L);
/*  34 */     this.data = input.readFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  42 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  47 */     return this.data + "f";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase copy() {
/*  55 */     return new NBTTagFloat(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  60 */     if (super.equals(p_equals_1_)) {
/*     */       
/*  62 */       NBTTagFloat nbttagfloat = (NBTTagFloat)p_equals_1_;
/*  63 */       return (this.data == nbttagfloat.data);
/*     */     } 
/*     */ 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  73 */     return super.hashCode() ^ Float.floatToIntBits(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong() {
/*  78 */     return (long)this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt() {
/*  83 */     return MathHelper.floor_float(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort() {
/*  88 */     return (short)(MathHelper.floor_float(this.data) & 0xFFFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte() {
/*  93 */     return (byte)(MathHelper.floor_float(this.data) & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble() {
/*  98 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat() {
/* 103 */     return this.data;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\nbt\NBTTagFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */