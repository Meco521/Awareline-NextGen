/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandWhitelist
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "whitelist";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  30 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  38 */     return "commands.whitelist.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  46 */     if (args.length < 1)
/*     */     {
/*  48 */       throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  52 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  54 */     if (args[0].equals("on")) {
/*     */       
/*  56 */       minecraftserver.getConfigurationManager().setWhiteListEnabled(true);
/*  57 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.enabled", new Object[0]);
/*     */     }
/*  59 */     else if (args[0].equals("off")) {
/*     */       
/*  61 */       minecraftserver.getConfigurationManager().setWhiteListEnabled(false);
/*  62 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.disabled", new Object[0]);
/*     */     }
/*  64 */     else if (args[0].equals("list")) {
/*     */       
/*  66 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.whitelist.list", new Object[] { Integer.valueOf((minecraftserver.getConfigurationManager().getWhitelistedPlayerNames()).length), Integer.valueOf((minecraftserver.getConfigurationManager().getAvailablePlayerDat()).length) }));
/*  67 */       String[] astring = minecraftserver.getConfigurationManager().getWhitelistedPlayerNames();
/*  68 */       sender.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString((Object[])astring)));
/*     */     }
/*  70 */     else if (args[0].equals("add")) {
/*     */       
/*  72 */       if (args.length < 2)
/*     */       {
/*  74 */         throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  77 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[1]);
/*     */       
/*  79 */       if (gameprofile == null)
/*     */       {
/*  81 */         throw new CommandException("commands.whitelist.add.failed", new Object[] { args[1] });
/*     */       }
/*     */       
/*  84 */       minecraftserver.getConfigurationManager().addWhitelistedPlayer(gameprofile);
/*  85 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.add.success", new Object[] { args[1] });
/*     */     }
/*  87 */     else if (args[0].equals("remove")) {
/*     */       
/*  89 */       if (args.length < 2)
/*     */       {
/*  91 */         throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
/*     */       }
/*     */       
/*  94 */       GameProfile gameprofile1 = minecraftserver.getConfigurationManager().getWhitelistedPlayers().getBannedProfile(args[1]);
/*     */       
/*  96 */       if (gameprofile1 == null)
/*     */       {
/*  98 */         throw new CommandException("commands.whitelist.remove.failed", new Object[] { args[1] });
/*     */       }
/*     */       
/* 101 */       minecraftserver.getConfigurationManager().removePlayerFromWhitelist(gameprofile1);
/* 102 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.remove.success", new Object[] { args[1] });
/*     */     }
/* 104 */     else if (args[0].equals("reload")) {
/*     */       
/* 106 */       minecraftserver.getConfigurationManager().loadWhiteList();
/* 107 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.reloaded", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 114 */     if (args.length == 1)
/*     */     {
/* 116 */       return getListOfStringsMatchingLastWord(args, new String[] { "on", "off", "list", "add", "remove", "reload" });
/*     */     }
/*     */ 
/*     */     
/* 120 */     if (args.length == 2) {
/*     */       
/* 122 */       if (args[0].equals("remove"))
/*     */       {
/* 124 */         return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getWhitelistedPlayerNames());
/*     */       }
/*     */       
/* 127 */       if (args[0].equals("add"))
/*     */       {
/* 129 */         return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getPlayerProfileCache().getUsernames());
/*     */       }
/*     */     } 
/*     */     
/* 133 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */