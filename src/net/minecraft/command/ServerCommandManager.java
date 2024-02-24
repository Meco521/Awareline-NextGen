/*     */ package net.minecraft.command;
/*     */ import net.minecraft.command.server.CommandAchievement;
/*     */ import net.minecraft.command.server.CommandBroadcast;
/*     */ import net.minecraft.command.server.CommandPardonIp;
/*     */ import net.minecraft.command.server.CommandSummon;
/*     */ import net.minecraft.command.server.CommandTestForBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class ServerCommandManager extends CommandHandler implements IAdminCommand {
/*     */   public ServerCommandManager() {
/*  15 */     registerCommand(new CommandTime());
/*  16 */     registerCommand(new CommandGameMode());
/*  17 */     registerCommand(new CommandDifficulty());
/*  18 */     registerCommand(new CommandDefaultGameMode());
/*  19 */     registerCommand(new CommandKill());
/*  20 */     registerCommand(new CommandToggleDownfall());
/*  21 */     registerCommand(new CommandWeather());
/*  22 */     registerCommand(new CommandXP());
/*  23 */     registerCommand((ICommand)new CommandTeleport());
/*  24 */     registerCommand(new CommandGive());
/*  25 */     registerCommand(new CommandReplaceItem());
/*  26 */     registerCommand(new CommandStats());
/*  27 */     registerCommand(new CommandEffect());
/*  28 */     registerCommand(new CommandEnchant());
/*  29 */     registerCommand(new CommandParticle());
/*  30 */     registerCommand((ICommand)new CommandEmote());
/*  31 */     registerCommand(new CommandShowSeed());
/*  32 */     registerCommand(new CommandHelp());
/*  33 */     registerCommand(new CommandDebug());
/*  34 */     registerCommand((ICommand)new CommandMessage());
/*  35 */     registerCommand((ICommand)new CommandBroadcast());
/*  36 */     registerCommand(new CommandSetSpawnpoint());
/*  37 */     registerCommand((ICommand)new CommandSetDefaultSpawnpoint());
/*  38 */     registerCommand(new CommandGameRule());
/*  39 */     registerCommand(new CommandClearInventory());
/*  40 */     registerCommand((ICommand)new CommandTestFor());
/*  41 */     registerCommand(new CommandSpreadPlayers());
/*  42 */     registerCommand(new CommandPlaySound());
/*  43 */     registerCommand((ICommand)new CommandScoreboard());
/*  44 */     registerCommand(new CommandExecuteAt());
/*  45 */     registerCommand(new CommandTrigger());
/*  46 */     registerCommand((ICommand)new CommandAchievement());
/*  47 */     registerCommand((ICommand)new CommandSummon());
/*  48 */     registerCommand((ICommand)new CommandSetBlock());
/*  49 */     registerCommand(new CommandFill());
/*  50 */     registerCommand(new CommandClone());
/*  51 */     registerCommand(new CommandCompare());
/*  52 */     registerCommand(new CommandBlockData());
/*  53 */     registerCommand((ICommand)new CommandTestForBlock());
/*  54 */     registerCommand((ICommand)new CommandMessageRaw());
/*  55 */     registerCommand(new CommandWorldBorder());
/*  56 */     registerCommand(new CommandTitle());
/*  57 */     registerCommand(new CommandEntityData());
/*     */     
/*  59 */     if (MinecraftServer.getServer().isDedicatedServer()) {
/*     */       
/*  61 */       registerCommand((ICommand)new CommandOp());
/*  62 */       registerCommand((ICommand)new CommandDeOp());
/*  63 */       registerCommand((ICommand)new CommandStop());
/*  64 */       registerCommand((ICommand)new CommandSaveAll());
/*  65 */       registerCommand((ICommand)new CommandSaveOff());
/*  66 */       registerCommand((ICommand)new CommandSaveOn());
/*  67 */       registerCommand((ICommand)new CommandBanIp());
/*  68 */       registerCommand((ICommand)new CommandPardonIp());
/*  69 */       registerCommand((ICommand)new CommandBanPlayer());
/*  70 */       registerCommand((ICommand)new CommandListBans());
/*  71 */       registerCommand((ICommand)new CommandPardonPlayer());
/*  72 */       registerCommand(new CommandServerKick());
/*  73 */       registerCommand((ICommand)new CommandListPlayers());
/*  74 */       registerCommand((ICommand)new CommandWhitelist());
/*  75 */       registerCommand(new CommandSetPlayerTimeout());
/*     */     }
/*     */     else {
/*     */       
/*  79 */       registerCommand((ICommand)new CommandPublishLocalServer());
/*     */     } 
/*     */     
/*  82 */     CommandBase.setAdminCommander(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOperators(ICommandSender sender, ICommand command, int flags, String msgFormat, Object... msgParams) {
/*  90 */     boolean flag = true;
/*  91 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  93 */     if (!sender.sendCommandFeedback())
/*     */     {
/*  95 */       flag = false;
/*     */     }
/*     */     
/*  98 */     ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.type.admin", new Object[] { sender.getName(), new ChatComponentTranslation(msgFormat, msgParams) });
/*  99 */     chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY);
/* 100 */     chatComponentTranslation.getChatStyle().setItalic(Boolean.valueOf(true));
/*     */     
/* 102 */     if (flag)
/*     */     {
/* 104 */       for (EntityPlayer entityplayer : minecraftserver.getConfigurationManager().getPlayerList()) {
/*     */         
/* 106 */         if (entityplayer != sender && minecraftserver.getConfigurationManager().canSendCommands(entityplayer.getGameProfile()) && command.canCommandSenderUseCommand(sender)) {
/*     */           
/* 108 */           boolean flag1 = (sender instanceof MinecraftServer && MinecraftServer.getServer().shouldBroadcastConsoleToOps());
/* 109 */           boolean flag2 = (sender instanceof net.minecraft.network.rcon.RConConsoleSource && MinecraftServer.getServer().shouldBroadcastRconToOps());
/*     */           
/* 111 */           if (flag1 || flag2 || (!(sender instanceof net.minecraft.network.rcon.RConConsoleSource) && !(sender instanceof MinecraftServer)))
/*     */           {
/* 113 */             entityplayer.addChatMessage((IChatComponent)chatComponentTranslation);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 119 */     if (sender != minecraftserver && minecraftserver.worldServers[0].getGameRules().getBoolean("logAdminCommands"))
/*     */     {
/* 121 */       minecraftserver.addChatMessage((IChatComponent)chatComponentTranslation);
/*     */     }
/*     */     
/* 124 */     boolean flag3 = minecraftserver.worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*     */     
/* 126 */     if (sender instanceof CommandBlockLogic)
/*     */     {
/* 128 */       flag3 = ((CommandBlockLogic)sender).shouldTrackOutput();
/*     */     }
/*     */     
/* 131 */     if (((flags & 0x1) != 1 && flag3) || sender instanceof MinecraftServer)
/*     */     {
/* 133 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation(msgFormat, msgParams));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\ServerCommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */