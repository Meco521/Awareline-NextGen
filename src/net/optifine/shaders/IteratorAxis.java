/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.optifine.BlockPosM;
/*     */ 
/*     */ public class IteratorAxis
/*     */   implements Iterator<BlockPos>
/*     */ {
/*     */   private final double yDelta;
/*     */   private final double zDelta;
/*     */   private final int xStart;
/*     */   private final int xEnd;
/*     */   private double yStart;
/*     */   private double yEnd;
/*     */   private double zStart;
/*     */   private double zEnd;
/*     */   private int xNext;
/*     */   private double yNext;
/*     */   private double zNext;
/*  22 */   private final BlockPosM pos = new BlockPosM(0, 0, 0);
/*     */   
/*     */   private boolean hasNext = false;
/*     */   
/*     */   public IteratorAxis(BlockPos posStart, BlockPos posEnd, double yDelta, double zDelta) {
/*  27 */     this.yDelta = yDelta;
/*  28 */     this.zDelta = zDelta;
/*  29 */     this.xStart = posStart.getX();
/*  30 */     this.xEnd = posEnd.getX();
/*  31 */     this.yStart = posStart.getY();
/*  32 */     this.yEnd = posEnd.getY() - 0.5D;
/*  33 */     this.zStart = posStart.getZ();
/*  34 */     this.zEnd = posEnd.getZ() - 0.5D;
/*  35 */     this.xNext = this.xStart;
/*  36 */     this.yNext = this.yStart;
/*  37 */     this.zNext = this.zStart;
/*  38 */     this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  43 */     return this.hasNext;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos next() {
/*  48 */     if (!this.hasNext)
/*     */     {
/*  50 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*  54 */     this.pos.setXyz(this.xNext, this.yNext, this.zNext);
/*  55 */     nextPos();
/*  56 */     this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
/*  57 */     return (BlockPos)this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextPos() {
/*  63 */     this.zNext++;
/*     */     
/*  65 */     if (this.zNext >= this.zEnd) {
/*     */       
/*  67 */       this.zNext = this.zStart;
/*  68 */       this.yNext++;
/*     */       
/*  70 */       if (this.yNext >= this.yEnd) {
/*     */         
/*  72 */         this.yNext = this.yStart;
/*  73 */         this.yStart += this.yDelta;
/*  74 */         this.yEnd += this.yDelta;
/*  75 */         this.yNext = this.yStart;
/*  76 */         this.zStart += this.zDelta;
/*  77 */         this.zEnd += this.zDelta;
/*  78 */         this.zNext = this.zStart;
/*  79 */         this.xNext++;
/*     */         
/*  81 */         if (this.xNext >= this.xEnd);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/*  91 */     throw new RuntimeException("Not implemented");
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  95 */     BlockPos blockpos = new BlockPos(-2, 10, 20);
/*  96 */     BlockPos blockpos1 = new BlockPos(2, 12, 22);
/*  97 */     double d0 = -0.5D;
/*  98 */     double d1 = 0.5D;
/*  99 */     IteratorAxis iteratoraxis = new IteratorAxis(blockpos, blockpos1, d0, d1);
/* 100 */     System.out.println("Start: " + blockpos + ", end: " + blockpos1 + ", yDelta: " + d0 + ", zDelta: " + d1);
/*     */     
/* 102 */     while (iteratoraxis.hasNext()) {
/*     */       
/* 104 */       BlockPos blockpos2 = iteratoraxis.next();
/* 105 */       System.out.println(blockpos2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\IteratorAxis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */