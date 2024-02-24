/*     */ package net.minecraft.client.renderer.chunk;
/*     */ import java.util.BitSet;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IntegerCache;
/*     */ 
/*     */ public class VisGraph {
/*  11 */   private static final int field_178616_a = (int)Math.pow(16.0D, 0.0D);
/*  12 */   private static final int field_178614_b = (int)Math.pow(16.0D, 1.0D);
/*  13 */   private static final int field_178615_c = (int)Math.pow(16.0D, 2.0D);
/*  14 */   private final BitSet field_178612_d = new BitSet(4096);
/*  15 */   private static final int[] field_178613_e = new int[1352];
/*  16 */   private int field_178611_f = 4096;
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_178606_a(BlockPos pos) {
/*  21 */     this.field_178612_d.set(getIndex(pos), true);
/*  22 */     this.field_178611_f--;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getIndex(BlockPos pos) {
/*  28 */     return getIndex(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(int x, int y, int z) {
/*  33 */     return x | y << 8 | z << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public SetVisibility computeVisibility() {
/*  38 */     SetVisibility setvisibility = new SetVisibility();
/*     */ 
/*     */     
/*  41 */     if (4096 - this.field_178611_f < 256) {
/*     */       
/*  43 */       setvisibility.setAllVisible(true);
/*     */     }
/*  45 */     else if (this.field_178611_f == 0) {
/*     */       
/*  47 */       setvisibility.setAllVisible(false);
/*     */     }
/*     */     else {
/*     */       
/*  51 */       for (int i : field_178613_e) {
/*     */         
/*  53 */         if (!this.field_178612_d.get(i))
/*     */         {
/*  55 */           setvisibility.setManyVisible(func_178604_a(i));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     return setvisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EnumFacing> func_178609_b(BlockPos pos) {
/*  65 */     return func_178604_a(getIndex(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<EnumFacing> func_178604_a(int p_178604_1_) {
/*  70 */     Set<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/*  71 */     Queue<Integer> queue = new ArrayDeque<>(384);
/*  72 */     queue.add(IntegerCache.getInteger(p_178604_1_));
/*  73 */     this.field_178612_d.set(p_178604_1_, true);
/*     */     
/*  75 */     while (!queue.isEmpty()) {
/*     */       
/*  77 */       int i = ((Integer)queue.poll()).intValue();
/*  78 */       func_178610_a(i, set);
/*     */       
/*  80 */       for (EnumFacing enumfacing : EnumFacing.VALUES) {
/*     */         
/*  82 */         int j = func_178603_a(i, enumfacing);
/*     */         
/*  84 */         if (j >= 0 && !this.field_178612_d.get(j)) {
/*     */           
/*  86 */           this.field_178612_d.set(j, true);
/*  87 */           queue.add(IntegerCache.getInteger(j));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178610_a(int p_178610_1_, Set<EnumFacing> p_178610_2_) {
/*  97 */     int i = p_178610_1_ & 0xF;
/*     */     
/*  99 */     if (i == 0) {
/*     */       
/* 101 */       p_178610_2_.add(EnumFacing.WEST);
/*     */     }
/* 103 */     else if (i == 15) {
/*     */       
/* 105 */       p_178610_2_.add(EnumFacing.EAST);
/*     */     } 
/*     */     
/* 108 */     int j = p_178610_1_ >> 8 & 0xF;
/*     */     
/* 110 */     if (j == 0) {
/*     */       
/* 112 */       p_178610_2_.add(EnumFacing.DOWN);
/*     */     }
/* 114 */     else if (j == 15) {
/*     */       
/* 116 */       p_178610_2_.add(EnumFacing.UP);
/*     */     } 
/*     */     
/* 119 */     int k = p_178610_1_ >> 4 & 0xF;
/*     */     
/* 121 */     if (k == 0) {
/*     */       
/* 123 */       p_178610_2_.add(EnumFacing.NORTH);
/*     */     }
/* 125 */     else if (k == 15) {
/*     */       
/* 127 */       p_178610_2_.add(EnumFacing.SOUTH);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_178603_a(int p_178603_1_, EnumFacing p_178603_2_) {
/* 133 */     switch (p_178603_2_) {
/*     */       
/*     */       case DOWN:
/* 136 */         if ((p_178603_1_ >> 8 & 0xF) == 0)
/*     */         {
/* 138 */           return -1;
/*     */         }
/*     */         
/* 141 */         return p_178603_1_ - field_178615_c;
/*     */       
/*     */       case UP:
/* 144 */         if ((p_178603_1_ >> 8 & 0xF) == 15)
/*     */         {
/* 146 */           return -1;
/*     */         }
/*     */         
/* 149 */         return p_178603_1_ + field_178615_c;
/*     */       
/*     */       case NORTH:
/* 152 */         if ((p_178603_1_ >> 4 & 0xF) == 0)
/*     */         {
/* 154 */           return -1;
/*     */         }
/*     */         
/* 157 */         return p_178603_1_ - field_178614_b;
/*     */       
/*     */       case SOUTH:
/* 160 */         if ((p_178603_1_ >> 4 & 0xF) == 15)
/*     */         {
/* 162 */           return -1;
/*     */         }
/*     */         
/* 165 */         return p_178603_1_ + field_178614_b;
/*     */       
/*     */       case WEST:
/* 168 */         if ((p_178603_1_ & 0xF) == 0)
/*     */         {
/* 170 */           return -1;
/*     */         }
/*     */         
/* 173 */         return p_178603_1_ - field_178616_a;
/*     */       
/*     */       case EAST:
/* 176 */         if ((p_178603_1_ & 0xF) == 15)
/*     */         {
/* 178 */           return -1;
/*     */         }
/*     */         
/* 181 */         return p_178603_1_ + field_178616_a;
/*     */     } 
/*     */     
/* 184 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 190 */     int k = 0;
/*     */     
/* 192 */     for (int l = 0; l < 16; l++) {
/*     */       
/* 194 */       for (int i1 = 0; i1 < 16; i1++) {
/*     */         
/* 196 */         for (int j1 = 0; j1 < 16; j1++) {
/*     */           
/* 198 */           if (l == 0 || l == 15 || i1 == 0 || i1 == 15 || j1 == 0 || j1 == 15)
/*     */           {
/* 200 */             field_178613_e[k++] = getIndex(l, i1, j1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\chunk\VisGraph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */