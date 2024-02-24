/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandServerKick
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "kick";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 24 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 32 */     return "commands.kick.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 40 */     if (args.length > 0 && args[0].length() > 1) {
/*    */       
/* 42 */       EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
/* 43 */       String s = "Kicked by an operator.";
/* 44 */       boolean flag = false;
/*    */       
/* 46 */       if (entityplayermp == null)
/*    */       {
/* 48 */         throw new PlayerNotFoundException();
/*    */       }
/*    */ 
/*    */       
/* 52 */       if (args.length >= 2) {
/*    */         
/* 54 */         s = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
/* 55 */         flag = true;
/*    */       } 
/*    */       
/* 58 */       entityplayermp.playerNetServerHandler.kickPlayerFromServer(s);
/*    */       
/* 60 */       if (flag)
/*    */       {
/* 62 */         notifyOperators(sender, this, "commands.kick.success.reason", new Object[] { entityplayermp.getName(), s });
/*    */       }
/*    */       else
/*    */       {
/* 66 */         notifyOperators(sender, this, "commands.kick.success", new Object[] { entityplayermp.getName() });
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 72 */       throw new WrongUsageException("commands.kick.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 78 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandServerKick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */