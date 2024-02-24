/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.util.IProgressUpdate;
/*    */ import net.minecraft.world.MinecraftException;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ public class CommandSaveAll
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 18 */     return "save-all";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 26 */     return "commands.save.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) {
/* 33 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 34 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.start", new Object[0]));
/*    */     
/* 36 */     if (minecraftserver.getConfigurationManager() != null)
/*    */     {
/* 38 */       minecraftserver.getConfigurationManager().saveAllPlayerData();
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 43 */       for (int i = 0; i < minecraftserver.worldServers.length; i++) {
/*    */         
/* 45 */         if (minecraftserver.worldServers[i] != null) {
/*    */           
/* 47 */           WorldServer worldserver = minecraftserver.worldServers[i];
/* 48 */           boolean flag = worldserver.disableLevelSaving;
/* 49 */           worldserver.disableLevelSaving = false;
/* 50 */           worldserver.saveAllChunks(true, (IProgressUpdate)null);
/* 51 */           worldserver.disableLevelSaving = flag;
/*    */         } 
/*    */       } 
/*    */       
/* 55 */       if (args.length > 0 && "flush".equals(args[0]))
/*    */       {
/* 57 */         sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
/*    */         
/* 59 */         for (int j = 0; j < minecraftserver.worldServers.length; j++) {
/*    */           
/* 61 */           if (minecraftserver.worldServers[j] != null) {
/*    */             
/* 63 */             WorldServer worldserver1 = minecraftserver.worldServers[j];
/* 64 */             boolean flag1 = worldserver1.disableLevelSaving;
/* 65 */             worldserver1.disableLevelSaving = false;
/* 66 */             worldserver1.saveChunkData();
/* 67 */             worldserver1.disableLevelSaving = flag1;
/*    */           } 
/*    */         } 
/*    */         
/* 71 */         sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
/*    */       }
/*    */     
/* 74 */     } catch (MinecraftException minecraftexception) {
/*    */       
/* 76 */       notifyOperators(sender, (ICommand)this, "commands.save.failed", new Object[] { minecraftexception.getMessage() });
/*    */       
/*    */       return;
/*    */     } 
/* 80 */     notifyOperators(sender, (ICommand)this, "commands.save.success", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandSaveAll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */