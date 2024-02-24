/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class SyntaxErrorException
/*    */   extends CommandException
/*    */ {
/*    */   public SyntaxErrorException() {
/*  7 */     this("commands.generic.snytax", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public SyntaxErrorException(String message, Object... replacements) {
/* 12 */     super(message, replacements);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\SyntaxErrorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */