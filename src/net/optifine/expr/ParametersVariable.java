/*    */ package net.optifine.expr;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ParametersVariable
/*    */   implements IParameters {
/*    */   private final ExpressionType[] first;
/*    */   private final ExpressionType[] repeat;
/*    */   private final ExpressionType[] last;
/*    */   private int maxCount;
/* 13 */   private static final ExpressionType[] EMPTY = new ExpressionType[0];
/*    */ 
/*    */   
/*    */   public ParametersVariable() {
/* 17 */     this((ExpressionType[])null, (ExpressionType[])null, (ExpressionType[])null);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParametersVariable(ExpressionType[] first, ExpressionType[] repeat, ExpressionType[] last) {
/* 22 */     this(first, repeat, last, 2147483647);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParametersVariable(ExpressionType[] first, ExpressionType[] repeat, ExpressionType[] last, int maxCount) {
/* 27 */     this.maxCount = Integer.MAX_VALUE;
/* 28 */     this.first = normalize(first);
/* 29 */     this.repeat = normalize(repeat);
/* 30 */     this.last = normalize(last);
/* 31 */     this.maxCount = maxCount;
/*    */   }
/*    */ 
/*    */   
/*    */   private static ExpressionType[] normalize(ExpressionType[] exprs) {
/* 36 */     return (exprs == null) ? EMPTY : exprs;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType[] getFirst() {
/* 41 */     return this.first;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType[] getRepeat() {
/* 46 */     return this.repeat;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType[] getLast() {
/* 51 */     return this.last;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCountRepeat() {
/* 56 */     return (this.first == null) ? 0 : this.first.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public ExpressionType[] getParameterTypes(IExpression[] arguments) {
/* 61 */     int i = this.first.length + this.last.length;
/* 62 */     int j = arguments.length - i;
/* 63 */     int k = 0;
/*    */     int l;
/* 65 */     for (l = 0; l + this.repeat.length <= j && i + l + this.repeat.length <= this.maxCount; l += this.repeat.length)
/*    */     {
/* 67 */       k++;
/*    */     }
/*    */     
/* 70 */     List<ExpressionType> list = new ArrayList<>(Arrays.asList(this.first));
/*    */     
/* 72 */     for (int i1 = 0; i1 < k; i1++)
/*    */     {
/* 74 */       list.addAll(Arrays.asList(this.repeat));
/*    */     }
/*    */     
/* 77 */     list.addAll(Arrays.asList(this.last));
/* 78 */     ExpressionType[] aexpressiontype = list.<ExpressionType>toArray(new ExpressionType[0]);
/* 79 */     return aexpressiontype;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParametersVariable first(ExpressionType... first) {
/* 84 */     return new ParametersVariable(first, this.repeat, this.last);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParametersVariable repeat(ExpressionType... repeat) {
/* 89 */     return new ParametersVariable(this.first, repeat, this.last);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParametersVariable last(ExpressionType... last) {
/* 94 */     return new ParametersVariable(this.first, this.repeat, last);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParametersVariable maxCount(int maxCount) {
/* 99 */     return new ParametersVariable(this.first, this.repeat, this.last, maxCount);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\ParametersVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */