/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPublishLocalServer
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 15 */     return "publish";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 23 */     return "commands.publish.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) {
/* 30 */     String s = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);
/*    */     
/* 32 */     if (s != null) {
/*    */       
/* 34 */       notifyOperators(sender, (ICommand)this, "commands.publish.started", new Object[] { s });
/*    */     }
/*    */     else {
/*    */       
/* 38 */       notifyOperators(sender, (ICommand)this, "commands.publish.failed", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandPublishLocalServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */