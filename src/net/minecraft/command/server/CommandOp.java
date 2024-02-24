/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandOp
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 21 */     return "op";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 29 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 37 */     return "commands.op.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 45 */     if (args.length == 1 && !args[0].isEmpty()) {
/*    */       
/* 47 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 48 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 50 */       if (gameprofile == null)
/*    */       {
/* 52 */         throw new CommandException("commands.op.failed", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 56 */       minecraftserver.getConfigurationManager().addOp(gameprofile);
/* 57 */       notifyOperators(sender, (ICommand)this, "commands.op.success", new Object[] { args[0] });
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 62 */       throw new WrongUsageException("commands.op.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 68 */     if (args.length == 1) {
/*    */       
/* 70 */       String s = args[args.length - 1];
/* 71 */       List<String> list = Lists.newArrayList();
/*    */       
/* 73 */       for (GameProfile gameprofile : MinecraftServer.getServer().getGameProfiles()) {
/*    */         
/* 75 */         if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(gameprofile) && doesStringStartWith(s, gameprofile.getName()))
/*    */         {
/* 77 */           list.add(gameprofile.getName());
/*    */         }
/*    */       } 
/*    */       
/* 81 */       return list;
/*    */     } 
/*    */ 
/*    */     
/* 85 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */