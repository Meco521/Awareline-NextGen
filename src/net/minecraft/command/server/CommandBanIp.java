/*     */ package net.minecraft.command.server;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.PlayerNotFoundException;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.IPBanEntry;
/*     */ import net.minecraft.server.management.UserListEntry;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class CommandBanIp extends CommandBase {
/*  17 */   public static final Pattern field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  24 */     return "ban-ip";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  32 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/*  40 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(sender));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  48 */     return "commands.banip.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  56 */     if (args.length >= 1 && args[0].length() > 1) {
/*     */       
/*  58 */       IChatComponent ichatcomponent = (args.length >= 2) ? getChatComponentFromNthArg(sender, args, 1) : null;
/*  59 */       Matcher matcher = field_147211_a.matcher(args[0]);
/*     */       
/*  61 */       if (matcher.matches())
/*     */       {
/*  63 */         func_147210_a(sender, args[0], (ichatcomponent == null) ? null : ichatcomponent.getUnformattedText());
/*     */       }
/*     */       else
/*     */       {
/*  67 */         EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
/*     */         
/*  69 */         if (entityplayermp == null)
/*     */         {
/*  71 */           throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
/*     */         }
/*     */         
/*  74 */         func_147210_a(sender, entityplayermp.getPlayerIP(), (ichatcomponent == null) ? null : ichatcomponent.getUnformattedText());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  79 */       throw new WrongUsageException("commands.banip.usage", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  85 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_147210_a(ICommandSender sender, String address, String reason) {
/*  90 */     IPBanEntry ipbanentry = new IPBanEntry(address, (Date)null, sender.getName(), (Date)null, reason);
/*  91 */     MinecraftServer.getServer().getConfigurationManager().getBannedIPs().addEntry((UserListEntry)ipbanentry);
/*  92 */     List<EntityPlayerMP> list = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(address);
/*  93 */     String[] astring = new String[list.size()];
/*  94 */     int i = 0;
/*     */     
/*  96 */     for (EntityPlayerMP entityplayermp : list) {
/*     */       
/*  98 */       entityplayermp.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
/*  99 */       astring[i++] = entityplayermp.getName();
/*     */     } 
/*     */     
/* 102 */     if (list.isEmpty()) {
/*     */       
/* 104 */       notifyOperators(sender, (ICommand)this, "commands.banip.success", new Object[] { address });
/*     */     }
/*     */     else {
/*     */       
/* 108 */       notifyOperators(sender, (ICommand)this, "commands.banip.success.players", new Object[] { address, joinNiceString((Object[])astring) });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandBanIp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */