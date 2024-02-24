/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ public class SetVisibility
/*     */ {
/*   9 */   private static final int COUNT_FACES = (EnumFacing.values()).length;
/*     */   
/*     */   private long bits;
/*     */   
/*     */   public void setManyVisible(Set<EnumFacing> p_178620_1_) {
/*  14 */     for (EnumFacing enumfacing : p_178620_1_) {
/*     */       
/*  16 */       for (EnumFacing enumfacing1 : p_178620_1_)
/*     */       {
/*  18 */         setVisible(enumfacing, enumfacing1, true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(EnumFacing facing, EnumFacing facing2, boolean p_178619_3_) {
/*  25 */     setBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES, p_178619_3_);
/*  26 */     setBit(facing2.ordinal() + facing.ordinal() * COUNT_FACES, p_178619_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAllVisible(boolean visible) {
/*  31 */     if (visible) {
/*     */       
/*  33 */       this.bits = -1L;
/*     */     }
/*     */     else {
/*     */       
/*  37 */       this.bits = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/*  43 */     return getBit(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  48 */     StringBuilder stringbuilder = new StringBuilder();
/*  49 */     stringbuilder.append(' ');
/*     */     
/*  51 */     for (EnumFacing enumfacing : EnumFacing.values())
/*     */     {
/*  53 */       stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
/*     */     }
/*     */     
/*  56 */     stringbuilder.append('\n');
/*     */     
/*  58 */     for (EnumFacing enumfacing2 : EnumFacing.values()) {
/*     */       
/*  60 */       stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0));
/*     */       
/*  62 */       for (EnumFacing enumfacing1 : EnumFacing.values()) {
/*     */         
/*  64 */         if (enumfacing2 == enumfacing1) {
/*     */           
/*  66 */           stringbuilder.append("  ");
/*     */         }
/*     */         else {
/*     */           
/*  70 */           boolean flag = isVisible(enumfacing2, enumfacing1);
/*  71 */           stringbuilder.append(' ').append(flag ? 89 : 110);
/*     */         } 
/*     */       } 
/*     */       
/*  75 */       stringbuilder.append('\n');
/*     */     } 
/*     */     
/*  78 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean getBit(int p_getBit_1_) {
/*  83 */     return ((this.bits & 1L << p_getBit_1_) != 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBit(int p_setBit_1_, boolean p_setBit_2_) {
/*  88 */     if (p_setBit_2_) {
/*     */       
/*  90 */       setBit(p_setBit_1_);
/*     */     }
/*     */     else {
/*     */       
/*  94 */       clearBit(p_setBit_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBit(int p_setBit_1_) {
/* 100 */     this.bits |= 1L << p_setBit_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearBit(int p_clearBit_1_) {
/* 105 */     this.bits &= 1L << p_clearBit_1_ ^ 0xFFFFFFFFFFFFFFFFL;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\chunk\SetVisibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */