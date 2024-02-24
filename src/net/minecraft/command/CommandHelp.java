/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class CommandHelp
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  20 */     return "help";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  36 */     return "commands.help.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getCommandAliases() {
/*  41 */     return Arrays.asList(new String[] { "?" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  49 */     List<ICommand> list = getSortedPossibleCommands(sender);
/*  50 */     int i = 7;
/*  51 */     int j = (list.size() - 1) / 7;
/*  52 */     int k = 0;
/*     */ 
/*     */     
/*     */     try {
/*  56 */       k = (args.length == 0) ? 0 : (parseInt(args[0], 1, j + 1) - 1);
/*     */     }
/*  58 */     catch (NumberInvalidException numberinvalidexception) {
/*     */       
/*  60 */       Map<String, ICommand> map = getCommands();
/*  61 */       ICommand icommand = map.get(args[0]);
/*     */       
/*  63 */       if (icommand != null)
/*     */       {
/*  65 */         throw new WrongUsageException(icommand.getCommandUsage(sender), new Object[0]);
/*     */       }
/*     */       
/*  68 */       if (MathHelper.parseIntWithDefault(args[0], -1) != -1)
/*     */       {
/*  70 */         throw numberinvalidexception;
/*     */       }
/*     */       
/*  73 */       throw new CommandNotFoundException();
/*     */     } 
/*     */     
/*  76 */     int l = Math.min((k + 1) * 7, list.size());
/*  77 */     ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.help.header", new Object[] { Integer.valueOf(k + 1), Integer.valueOf(j + 1) });
/*  78 */     chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/*  79 */     sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*     */     
/*  81 */     for (int i1 = k * 7; i1 < l; i1++) {
/*     */       
/*  83 */       ICommand icommand1 = list.get(i1);
/*  84 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(icommand1.getCommandUsage(sender), new Object[0]);
/*  85 */       chatcomponenttranslation.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + icommand1.getCommandName() + " "));
/*  86 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation);
/*     */     } 
/*     */     
/*  89 */     if (k == 0 && sender instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */       
/*  91 */       ChatComponentTranslation chatcomponenttranslation2 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
/*  92 */       chatcomponenttranslation2.getChatStyle().setColor(EnumChatFormatting.GREEN);
/*  93 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ICommand> getSortedPossibleCommands(ICommandSender p_71534_1_) {
/*  99 */     List<ICommand> list = MinecraftServer.getServer().getCommandManager().getPossibleCommands(p_71534_1_);
/* 100 */     Collections.sort(list);
/* 101 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, ICommand> getCommands() {
/* 106 */     return MinecraftServer.getServer().getCommandManager().getCommands();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 111 */     if (args.length == 1) {
/*     */       
/* 113 */       Set<String> set = getCommands().keySet();
/* 114 */       return getListOfStringsMatchingLastWord(args, set.<String>toArray(new String[0]));
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandHelp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */