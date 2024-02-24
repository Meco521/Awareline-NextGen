/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class PlayerNotFoundException
/*    */   extends CommandException
/*    */ {
/*    */   public PlayerNotFoundException() {
/*  7 */     this("commands.generic.player.notFound", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlayerNotFoundException(String message, Object... replacements) {
/* 12 */     super(message, replacements);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\PlayerNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */