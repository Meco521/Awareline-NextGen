/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandDebug
/*     */   extends CommandBase
/*     */ {
/*  17 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private long profileStartTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private int profileStartTick;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  30 */     return "debug";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  38 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  46 */     return "commands.debug.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  54 */     if (args.length < 1)
/*     */     {
/*  56 */       throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  60 */     if (args[0].equals("start")) {
/*     */       
/*  62 */       if (args.length != 1)
/*     */       {
/*  64 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  67 */       notifyOperators(sender, this, "commands.debug.start", new Object[0]);
/*  68 */       MinecraftServer.getServer().enableProfiling();
/*  69 */       this.profileStartTime = MinecraftServer.getCurrentTimeMillis();
/*  70 */       this.profileStartTick = MinecraftServer.getServer().getTickCounter();
/*     */     }
/*     */     else {
/*     */       
/*  74 */       if (!args[0].equals("stop"))
/*     */       {
/*  76 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  79 */       if (args.length != 1)
/*     */       {
/*  81 */         throw new WrongUsageException("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  84 */       if (!(MinecraftServer.getServer()).theProfiler.profilingEnabled)
/*     */       {
/*  86 */         throw new CommandException("commands.debug.notStarted", new Object[0]);
/*     */       }
/*     */       
/*  89 */       long i = MinecraftServer.getCurrentTimeMillis();
/*  90 */       int j = MinecraftServer.getServer().getTickCounter();
/*  91 */       long k = i - this.profileStartTime;
/*  92 */       int l = j - this.profileStartTick;
/*  93 */       saveProfileResults(k, l);
/*  94 */       (MinecraftServer.getServer()).theProfiler.profilingEnabled = false;
/*  95 */       notifyOperators(sender, this, "commands.debug.stop", new Object[] { Float.valueOf((float)k / 1000.0F), Integer.valueOf(l) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveProfileResults(long timeSpan, int tickSpan) {
/* 105 */     File file1 = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
/* 106 */     file1.getParentFile().mkdirs();
/*     */ 
/*     */     
/*     */     try {
/* 110 */       FileWriter filewriter = new FileWriter(file1);
/* 111 */       filewriter.write(getProfileResults(timeSpan, tickSpan));
/* 112 */       filewriter.close();
/*     */     }
/* 114 */     catch (Throwable throwable) {
/*     */       
/* 116 */       logger.error("Could not save profiler results to " + file1, throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getProfileResults(long timeSpan, int tickSpan) {
/* 125 */     StringBuilder stringbuilder = new StringBuilder();
/* 126 */     stringbuilder.append("---- Minecraft Profiler Results ----\n");
/* 127 */     stringbuilder.append("// ");
/* 128 */     stringbuilder.append(getWittyComment());
/* 129 */     stringbuilder.append("\n\n");
/* 130 */     stringbuilder.append("Time span: ").append(timeSpan).append(" ms\n");
/* 131 */     stringbuilder.append("Tick span: ").append(tickSpan).append(" ticks\n");
/* 132 */     stringbuilder.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(tickSpan / (float)timeSpan / 1000.0F) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
/* 133 */     stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
/* 134 */     func_147202_a(0, "root", stringbuilder);
/* 135 */     stringbuilder.append("--- END PROFILE DUMP ---\n\n");
/* 136 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_147202_a(int p_147202_1_, String p_147202_2_, StringBuilder stringBuilder) {
/* 141 */     List<Profiler.Result> list = (MinecraftServer.getServer()).theProfiler.getProfilingData(p_147202_2_);
/*     */     
/* 143 */     if (list != null && list.size() >= 3)
/*     */     {
/* 145 */       for (int i = 1; i < list.size(); i++) {
/*     */         
/* 147 */         Profiler.Result profiler$result = list.get(i);
/* 148 */         stringBuilder.append(String.format("[%02d] ", new Object[] { Integer.valueOf(p_147202_1_) }));
/*     */         
/* 150 */         for (int j = 0; j < p_147202_1_; j++)
/*     */         {
/* 152 */           stringBuilder.append(" ");
/*     */         }
/*     */         
/* 155 */         stringBuilder.append(profiler$result.field_76331_c).append(" - ").append(String.format("%.2f", new Object[] { Double.valueOf(profiler$result.field_76332_a) })).append("%/").append(String.format("%.2f", new Object[] { Double.valueOf(profiler$result.field_76330_b) })).append("%\n");
/*     */         
/* 157 */         if (!profiler$result.field_76331_c.equals("unspecified")) {
/*     */           
/*     */           try {
/*     */             
/* 161 */             func_147202_a(p_147202_1_ + 1, p_147202_2_ + "." + profiler$result.field_76331_c, stringBuilder);
/*     */           }
/* 163 */           catch (Exception exception) {
/*     */             
/* 165 */             stringBuilder.append("[[ EXCEPTION ").append(exception).append(" ]]");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getWittyComment() {
/* 177 */     String[] astring = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
/*     */ 
/*     */     
/*     */     try {
/* 181 */       return astring[(int)(System.nanoTime() % astring.length)];
/*     */     }
/* 183 */     catch (Throwable var2) {
/*     */       
/* 185 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 191 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "start", "stop" }) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */