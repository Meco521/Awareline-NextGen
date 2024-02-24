/*    */ package net.optifine.expr;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ public class TestExpressions
/*    */ {
/*    */   public static void main(String[] args) {
/*  9 */     ExpressionParser expressionparser = new ExpressionParser((IExpressionResolver)null);
/*    */ 
/*    */ 
/*    */     
/*    */     while (true) {
/*    */       try {
/* 15 */         InputStreamReader inputstreamreader = new InputStreamReader(System.in);
/* 16 */         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 17 */         String s = bufferedreader.readLine();
/*    */         
/* 19 */         if (s.length() <= 0) {
/*    */           return;
/*    */         }
/*    */ 
/*    */         
/* 24 */         IExpression iexpression = expressionparser.parse(s);
/*    */         
/* 26 */         if (iexpression instanceof IExpressionFloat) {
/*    */           
/* 28 */           IExpressionFloat iexpressionfloat = (IExpressionFloat)iexpression;
/* 29 */           float f = iexpressionfloat.eval();
/* 30 */           System.out.println(f);
/*    */         } 
/*    */         
/* 33 */         if (iexpression instanceof IExpressionBool)
/*    */         {
/* 35 */           IExpressionBool iexpressionbool = (IExpressionBool)iexpression;
/* 36 */           boolean flag = iexpressionbool.eval();
/* 37 */           System.out.println(flag);
/*    */         }
/*    */       
/* 40 */       } catch (Exception exception) {
/*    */         
/* 42 */         exception.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\TestExpressions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */