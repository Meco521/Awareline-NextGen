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
/*     */ public class NBTTagDouble
/*     */   extends NBTBase.NBTPrimitive
/*     */ {
/*     */   private double data;
/*     */   
/*     */   NBTTagDouble() {}
/*     */   
/*     */   public NBTTagDouble(double data) {
/*  20 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  28 */     output.writeDouble(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  33 */     sizeTracker.read(128L);
/*  34 */     this.data = input.readDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  42 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  47 */     return this.data + "d";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase copy() {
/*  55 */     return new NBTTagDouble(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  60 */     if (super.equals(p_equals_1_)) {
/*     */       
/*  62 */       NBTTagDouble nbttagdouble = (NBTTagDouble)p_equals_1_;
/*  63 */       return (this.data == nbttagdouble.data);
/*     */     } 
/*     */ 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  73 */     long i = Double.doubleToLongBits(this.data);
/*  74 */     return super.hashCode() ^ (int)(i ^ i >>> 32L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong() {
/*  79 */     return (long)Math.floor(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt() {
/*  84 */     return MathHelper.floor_double(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort() {
/*  89 */     return (short)(MathHelper.floor_double(this.data) & 0xFFFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte() {
/*  94 */     return (byte)(MathHelper.floor_double(this.data) & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble() {
/*  99 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat() {
/* 104 */     return (float)this.data;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\nbt\NBTTagDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */