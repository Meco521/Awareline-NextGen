/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandXP
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  16 */     return "xp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  24 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  32 */     return "commands.xp.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  40 */     if (args.length <= 0)
/*     */     {
/*  42 */       throw new WrongUsageException("commands.xp.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  46 */     String s = args[0];
/*  47 */     boolean flag = ((!s.isEmpty() && s.charAt(s.length() - 1) == 'l') || (!s.isEmpty() && s.charAt(s.length() - 1) == 'L'));
/*     */     
/*  49 */     if (flag && s.length() > 1)
/*     */     {
/*  51 */       s = s.substring(0, s.length() - 1);
/*     */     }
/*     */     
/*  54 */     int i = parseInt(s);
/*  55 */     boolean flag1 = (i < 0);
/*     */     
/*  57 */     if (flag1)
/*     */     {
/*  59 */       i *= -1;
/*     */     }
/*     */     
/*  62 */     EntityPlayerMP entityPlayerMP = (args.length > 1) ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
/*     */     
/*  64 */     if (flag) {
/*     */       
/*  66 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceLevel);
/*     */       
/*  68 */       if (flag1)
/*     */       {
/*  70 */         entityPlayerMP.addExperienceLevel(-i);
/*  71 */         notifyOperators(sender, this, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */       }
/*     */       else
/*     */       {
/*  75 */         entityPlayerMP.addExperienceLevel(i);
/*  76 */         notifyOperators(sender, this, "commands.xp.success.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  81 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceTotal);
/*     */       
/*  83 */       if (flag1)
/*     */       {
/*  85 */         throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
/*     */       }
/*     */       
/*  88 */       entityPlayerMP.addExperience(i);
/*  89 */       notifyOperators(sender, this, "commands.xp.success", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  96 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, getAllUsernames()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getAllUsernames() {
/* 101 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 109 */     return (index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandXP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */