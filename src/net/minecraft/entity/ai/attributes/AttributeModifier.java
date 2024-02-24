/*     */ package net.minecraft.entity.ai.attributes;
/*     */ 
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributeModifier
/*     */ {
/*     */   private final double amount;
/*     */   private final int operation;
/*     */   private final String name;
/*     */   private final UUID id;
/*     */   private boolean isSaved;
/*     */   
/*     */   public AttributeModifier(String nameIn, double amountIn, int operationIn) {
/*  23 */     this(MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()), nameIn, amountIn, operationIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeModifier(UUID idIn, String nameIn, double amountIn, int operationIn) {
/*  28 */     this.isSaved = true;
/*  29 */     this.id = idIn;
/*  30 */     this.name = nameIn;
/*  31 */     this.amount = amountIn;
/*  32 */     this.operation = operationIn;
/*  33 */     Validate.notEmpty(nameIn, "Modifier name cannot be empty", new Object[0]);
/*  34 */     Validate.inclusiveBetween(0L, 2L, operationIn, "Invalid operation");
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getID() {
/*  39 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  44 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOperation() {
/*  49 */     return this.operation;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAmount() {
/*  54 */     return this.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSaved() {
/*  62 */     return this.isSaved;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeModifier setSaved(boolean saved) {
/*  70 */     this.isSaved = saved;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  76 */     if (this == p_equals_1_)
/*     */     {
/*  78 */       return true;
/*     */     }
/*  80 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/*  82 */       AttributeModifier attributemodifier = (AttributeModifier)p_equals_1_;
/*     */       
/*  84 */       if (this.id != null) {
/*     */         
/*  86 */         if (!this.id.equals(attributemodifier.id))
/*     */         {
/*  88 */           return false;
/*     */         }
/*     */       }
/*  91 */       else if (attributemodifier.id != null) {
/*     */         
/*  93 */         return false;
/*     */       } 
/*     */       
/*  96 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     return (this.id != null) ? this.id.hashCode() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + this.name + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\attributes\AttributeModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */