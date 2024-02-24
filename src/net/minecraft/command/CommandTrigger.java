/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTrigger
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "trigger";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  30 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  38 */     return "commands.trigger.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     EntityPlayerMP entityplayermp;
/*  46 */     if (args.length < 3)
/*     */     {
/*  48 */       throw new WrongUsageException("commands.trigger.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     if (sender instanceof EntityPlayerMP) {
/*     */       
/*  56 */       entityplayermp = (EntityPlayerMP)sender;
/*     */     }
/*     */     else {
/*     */       
/*  60 */       Entity entity = sender.getCommandSenderEntity();
/*     */       
/*  62 */       if (!(entity instanceof EntityPlayerMP))
/*     */       {
/*  64 */         throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
/*     */       }
/*     */       
/*  67 */       entityplayermp = (EntityPlayerMP)entity;
/*     */     } 
/*     */     
/*  70 */     Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*  71 */     ScoreObjective scoreobjective = scoreboard.getObjective(args[0]);
/*     */     
/*  73 */     if (scoreobjective != null && scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
/*     */       
/*  75 */       int i = parseInt(args[2]);
/*     */       
/*  77 */       if (!scoreboard.entityHasObjective(entityplayermp.getName(), scoreobjective))
/*     */       {
/*  79 */         throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
/*     */       }
/*     */ 
/*     */       
/*  83 */       Score score = scoreboard.getValueFromObjective(entityplayermp.getName(), scoreobjective);
/*     */       
/*  85 */       if (score.isLocked())
/*     */       {
/*  87 */         throw new CommandException("commands.trigger.disabled", new Object[] { args[0] });
/*     */       }
/*     */ 
/*     */       
/*  91 */       if ("set".equals(args[1])) {
/*     */         
/*  93 */         score.setScorePoints(i);
/*     */       }
/*     */       else {
/*     */         
/*  97 */         if (!"add".equals(args[1]))
/*     */         {
/*  99 */           throw new CommandException("commands.trigger.invalidMode", new Object[] { args[1] });
/*     */         }
/*     */         
/* 102 */         score.increseScore(i);
/*     */       } 
/*     */       
/* 105 */       score.setLocked(true);
/*     */       
/* 107 */       if (entityplayermp.theItemInWorldManager.isCreative())
/*     */       {
/* 109 */         notifyOperators(sender, this, "commands.trigger.success", new Object[] { args[0], args[1], args[2] });
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 116 */       throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 123 */     if (args.length == 1) {
/*     */       
/* 125 */       Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/* 126 */       List<String> list = Lists.newArrayList();
/*     */       
/* 128 */       for (ScoreObjective scoreobjective : scoreboard.getScoreObjectives()) {
/*     */         
/* 130 */         if (scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER)
/*     */         {
/* 132 */           list.add(scoreobjective.getName());
/*     */         }
/*     */       } 
/*     */       
/* 136 */       return getListOfStringsMatchingLastWord(args, list.<String>toArray(new String[0]));
/*     */     } 
/*     */ 
/*     */     
/* 140 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, new String[] { "add", "set" }) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */