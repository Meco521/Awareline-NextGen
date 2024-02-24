/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ 
/*    */ public class ProgramStack
/*    */ {
/*  8 */   private final Deque<Program> stack = new ArrayDeque<>();
/*    */ 
/*    */   
/*    */   public void push(Program p) {
/* 12 */     this.stack.addLast(p);
/*    */     
/* 14 */     if (this.stack.size() > 100)
/*    */     {
/* 16 */       throw new RuntimeException("Program stack overflow: " + this.stack.size());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Program pop() {
/* 22 */     if (this.stack.isEmpty())
/*    */     {
/* 24 */       throw new RuntimeException("Program stack empty");
/*    */     }
/*    */ 
/*    */     
/* 28 */     Program program = this.stack.pollLast();
/* 29 */     return program;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ProgramStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */