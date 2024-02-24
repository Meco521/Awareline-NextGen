/*     */ package net.minecraft.block.state.pattern;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FactoryBlockPattern
/*     */ {
/*  19 */   private static final Joiner COMMA_JOIN = Joiner.on(",");
/*  20 */   private final List<String[]> depth = Lists.newArrayList();
/*  21 */   private final Map<Character, Predicate<BlockWorldState>> symbolMap = Maps.newHashMap();
/*     */   
/*     */   private int aisleHeight;
/*     */   private int rowWidth;
/*     */   
/*     */   private FactoryBlockPattern() {
/*  27 */     this.symbolMap.put(Character.valueOf(' '), Predicates.alwaysTrue());
/*     */   }
/*     */ 
/*     */   
/*     */   public FactoryBlockPattern aisle(String... aisle) {
/*  32 */     if (!ArrayUtils.isEmpty((Object[])aisle) && !StringUtils.isEmpty(aisle[0])) {
/*     */       
/*  34 */       if (this.depth.isEmpty()) {
/*     */         
/*  36 */         this.aisleHeight = aisle.length;
/*  37 */         this.rowWidth = aisle[0].length();
/*     */       } 
/*     */       
/*  40 */       if (aisle.length != this.aisleHeight)
/*     */       {
/*  42 */         throw new IllegalArgumentException("Expected aisle with height of " + this.aisleHeight + ", but was given one with a height of " + aisle.length + ")");
/*     */       }
/*     */ 
/*     */       
/*  46 */       for (String s : aisle) {
/*     */         
/*  48 */         if (s.length() != this.rowWidth)
/*     */         {
/*  50 */           throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.rowWidth + ", found one with " + s.length() + ")");
/*     */         }
/*     */         
/*  53 */         for (char c0 : s.toCharArray()) {
/*     */           
/*  55 */           if (!this.symbolMap.containsKey(Character.valueOf(c0)))
/*     */           {
/*  57 */             this.symbolMap.put(Character.valueOf(c0), (Predicate<BlockWorldState>)null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  62 */       this.depth.add(aisle);
/*  63 */       return this;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  68 */     throw new IllegalArgumentException("Empty pattern for aisle");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FactoryBlockPattern start() {
/*  74 */     return new FactoryBlockPattern();
/*     */   }
/*     */ 
/*     */   
/*     */   public FactoryBlockPattern where(char symbol, Predicate<BlockWorldState> blockMatcher) {
/*  79 */     this.symbolMap.put(Character.valueOf(symbol), blockMatcher);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPattern build() {
/*  85 */     return new BlockPattern(makePredicateArray());
/*     */   }
/*     */ 
/*     */   
/*     */   private Predicate<BlockWorldState>[][][] makePredicateArray() {
/*  90 */     checkMissingPredicates();
/*  91 */     Predicate[][][] arrayOfPredicate = (Predicate[][][])Array.newInstance(Predicate.class, new int[] { this.depth.size(), this.aisleHeight, this.rowWidth });
/*     */     
/*  93 */     for (int i = 0; i < this.depth.size(); i++) {
/*     */       
/*  95 */       for (int j = 0; j < this.aisleHeight; j++) {
/*     */         
/*  97 */         for (int k = 0; k < this.rowWidth; k++)
/*     */         {
/*  99 */           arrayOfPredicate[i][j][k] = this.symbolMap.get(Character.valueOf(((String[])this.depth.get(i))[j].charAt(k)));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return (Predicate<BlockWorldState>[][][])arrayOfPredicate;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkMissingPredicates() {
/* 109 */     List<Character> list = Lists.newArrayList();
/*     */     
/* 111 */     for (Map.Entry<Character, Predicate<BlockWorldState>> entry : this.symbolMap.entrySet()) {
/*     */       
/* 113 */       if (entry.getValue() == null)
/*     */       {
/* 115 */         list.add(entry.getKey());
/*     */       }
/*     */     } 
/*     */     
/* 119 */     if (!list.isEmpty())
/*     */     {
/* 121 */       throw new IllegalStateException("Predicates for character(s) " + COMMA_JOIN.join(list) + " are missing");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\state\pattern\FactoryBlockPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */