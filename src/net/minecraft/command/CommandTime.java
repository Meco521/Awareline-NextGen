/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTime
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  16 */     return "time";
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
/*  32 */     return "commands.time.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  40 */     if (args.length > 1) {
/*     */       
/*  42 */       if (args[0].equals("set")) {
/*     */         int l;
/*     */ 
/*     */         
/*  46 */         if (args[1].equals("day")) {
/*     */           
/*  48 */           l = 1000;
/*     */         }
/*  50 */         else if (args[1].equals("night")) {
/*     */           
/*  52 */           l = 13000;
/*     */         }
/*     */         else {
/*     */           
/*  56 */           l = parseInt(args[1], 0);
/*     */         } 
/*     */         
/*  59 */         setTime(sender, l);
/*  60 */         notifyOperators(sender, this, "commands.time.set", new Object[] { Integer.valueOf(l) });
/*     */         
/*     */         return;
/*     */       } 
/*  64 */       if (args[0].equals("add")) {
/*     */         
/*  66 */         int k = parseInt(args[1], 0);
/*  67 */         addTime(sender, k);
/*  68 */         notifyOperators(sender, this, "commands.time.added", new Object[] { Integer.valueOf(k) });
/*     */         
/*     */         return;
/*     */       } 
/*  72 */       if (args[0].equals("query")) {
/*     */         
/*  74 */         if (args[1].equals("daytime")) {
/*     */           
/*  76 */           int j = (int)(sender.getEntityWorld().getWorldTime() % 2147483647L);
/*  77 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, j);
/*  78 */           notifyOperators(sender, this, "commands.time.query", new Object[] { Integer.valueOf(j) });
/*     */           
/*     */           return;
/*     */         } 
/*  82 */         if (args[1].equals("gametime")) {
/*     */           
/*  84 */           int i = (int)(sender.getEntityWorld().getTotalWorldTime() % 2147483647L);
/*  85 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
/*  86 */           notifyOperators(sender, this, "commands.time.query", new Object[] { Integer.valueOf(i) });
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*  92 */     throw new WrongUsageException("commands.time.usage", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  97 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "query" }) : ((args.length == 2 && args[0].equals("set")) ? getListOfStringsMatchingLastWord(args, new String[] { "day", "night" }) : ((args.length == 2 && args[0].equals("query")) ? getListOfStringsMatchingLastWord(args, new String[] { "daytime", "gametime" }) : null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTime(ICommandSender sender, int time) {
/* 105 */     for (int i = 0; i < (MinecraftServer.getServer()).worldServers.length; i++)
/*     */     {
/* 107 */       (MinecraftServer.getServer()).worldServers[i].setWorldTime(time);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTime(ICommandSender sender, int time) {
/* 116 */     for (int i = 0; i < (MinecraftServer.getServer()).worldServers.length; i++) {
/*     */       
/* 118 */       WorldServer worldserver = (MinecraftServer.getServer()).worldServers[i];
/* 119 */       worldserver.setWorldTime(worldserver.getWorldTime() + time);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */