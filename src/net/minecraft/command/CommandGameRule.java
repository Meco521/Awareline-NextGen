/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.GameRules;
/*     */ 
/*     */ 
/*     */ public class CommandGameRule
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  19 */     return "gamerule";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  27 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  35 */     return "commands.gamerule.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     String s2;
/*  43 */     GameRules gamerules = getGameRules();
/*  44 */     String s = (args.length > 0) ? args[0] : "";
/*  45 */     String s1 = (args.length > 1) ? buildString(args, 1) : "";
/*     */     
/*  47 */     switch (args.length) {
/*     */       
/*     */       case 0:
/*  50 */         sender.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString((Object[])gamerules.getRules())));
/*     */         return;
/*     */       
/*     */       case 1:
/*  54 */         if (!gamerules.hasRule(s))
/*     */         {
/*  56 */           throw new CommandException("commands.gamerule.norule", new Object[] { s });
/*     */         }
/*     */         
/*  59 */         s2 = gamerules.getString(s);
/*  60 */         sender.addChatMessage((new ChatComponentText(s)).appendText(" = ").appendText(s2));
/*  61 */         sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gamerules.getInt(s));
/*     */         return;
/*     */     } 
/*     */     
/*  65 */     if (gamerules.areSameType(s, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(s1) && !"false".equals(s1))
/*     */     {
/*  67 */       throw new CommandException("commands.generic.boolean.invalid", new Object[] { s1 });
/*     */     }
/*     */     
/*  70 */     gamerules.setOrCreateGameRule(s, s1);
/*  71 */     func_175773_a(gamerules, s);
/*  72 */     notifyOperators(sender, this, "commands.gamerule.success", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void func_175773_a(GameRules rules, String p_175773_1_) {
/*  78 */     if ("reducedDebugInfo".equals(p_175773_1_)) {
/*     */       
/*  80 */       byte b0 = (byte)(rules.getBoolean(p_175773_1_) ? 22 : 23);
/*     */       
/*  82 */       for (EntityPlayerMP entityplayermp : MinecraftServer.getServer().getConfigurationManager().getPlayerList())
/*     */       {
/*  84 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)new S19PacketEntityStatus((Entity)entityplayermp, b0));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  91 */     if (args.length == 1)
/*     */     {
/*  93 */       return getListOfStringsMatchingLastWord(args, getGameRules().getRules());
/*     */     }
/*     */ 
/*     */     
/*  97 */     if (args.length == 2) {
/*     */       
/*  99 */       GameRules gamerules = getGameRules();
/*     */       
/* 101 */       if (gamerules.areSameType(args[0], GameRules.ValueType.BOOLEAN_VALUE))
/*     */       {
/* 103 */         return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GameRules getGameRules() {
/* 116 */     return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandGameRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */