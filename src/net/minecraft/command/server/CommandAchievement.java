/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandAchievement
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  27 */     return "achievement";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  35 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  43 */     return "commands.achievement.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  51 */     if (args.length < 2)
/*     */     {
/*  53 */       throw new WrongUsageException("commands.achievement.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  57 */     final StatBase statbase = StatList.getOneShotStat(args[1]);
/*     */     
/*  59 */     if (statbase == null && !args[1].equals("*"))
/*     */     {
/*  61 */       throw new CommandException("commands.achievement.unknownAchievement", new Object[] { args[1] });
/*     */     }
/*     */ 
/*     */     
/*  65 */     final EntityPlayerMP entityplayermp = (args.length >= 3) ? getPlayer(sender, args[2]) : getCommandSenderAsPlayer(sender);
/*  66 */     boolean flag = args[0].equalsIgnoreCase("give");
/*  67 */     boolean flag1 = args[0].equalsIgnoreCase("take");
/*     */     
/*  69 */     if (flag || flag1)
/*     */     {
/*  71 */       if (statbase == null) {
/*     */         
/*  73 */         if (flag)
/*     */         {
/*  75 */           for (Achievement achievement4 : AchievementList.achievementList)
/*     */           {
/*  77 */             entityplayermp.triggerAchievement((StatBase)achievement4);
/*     */           }
/*     */           
/*  80 */           notifyOperators(sender, (ICommand)this, "commands.achievement.give.success.all", new Object[] { entityplayermp.getName() });
/*     */         }
/*  82 */         else if (flag1)
/*     */         {
/*  84 */           for (Achievement achievement5 : Lists.reverse(AchievementList.achievementList))
/*     */           {
/*  86 */             entityplayermp.func_175145_a((StatBase)achievement5);
/*     */           }
/*     */           
/*  89 */           notifyOperators(sender, (ICommand)this, "commands.achievement.take.success.all", new Object[] { entityplayermp.getName() });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  94 */         if (statbase instanceof Achievement) {
/*     */           
/*  96 */           Achievement achievement = (Achievement)statbase;
/*     */           
/*  98 */           if (flag) {
/*     */             
/* 100 */             if (entityplayermp.getStatFile().hasAchievementUnlocked(achievement))
/*     */             {
/* 102 */               throw new CommandException("commands.achievement.alreadyHave", new Object[] { entityplayermp.getName(), statbase.createChatComponent() });
/*     */             }
/*     */             
/*     */             List<Achievement> list;
/*     */             
/* 107 */             for (list = Lists.newArrayList(); achievement.parentAchievement != null && !entityplayermp.getStatFile().hasAchievementUnlocked(achievement.parentAchievement); achievement = achievement.parentAchievement)
/*     */             {
/* 109 */               list.add(achievement.parentAchievement);
/*     */             }
/*     */             
/* 112 */             for (Achievement achievement1 : Lists.reverse(list))
/*     */             {
/* 114 */               entityplayermp.triggerAchievement((StatBase)achievement1);
/*     */             }
/*     */           }
/* 117 */           else if (flag1) {
/*     */             
/* 119 */             if (!entityplayermp.getStatFile().hasAchievementUnlocked(achievement))
/*     */             {
/* 121 */               throw new CommandException("commands.achievement.dontHave", new Object[] { entityplayermp.getName(), statbase.createChatComponent() });
/*     */             }
/*     */             
/* 124 */             List<Achievement> list1 = Lists.newArrayList((Iterator)Iterators.filter(AchievementList.achievementList.iterator(), new Predicate<Achievement>()
/*     */                   {
/*     */                     public boolean apply(Achievement p_apply_1_)
/*     */                     {
/* 128 */                       return (entityplayermp.getStatFile().hasAchievementUnlocked(p_apply_1_) && p_apply_1_ != statbase);
/*     */                     }
/*     */                   }));
/* 131 */             List<Achievement> list2 = Lists.newArrayList(list1);
/*     */             
/* 133 */             for (Achievement achievement2 : list1) {
/*     */               
/* 135 */               Achievement achievement3 = achievement2;
/*     */               
/*     */               boolean flag2;
/* 138 */               for (flag2 = false; achievement3 != null; achievement3 = achievement3.parentAchievement) {
/*     */                 
/* 140 */                 if (achievement3 == statbase)
/*     */                 {
/* 142 */                   flag2 = true;
/*     */                 }
/*     */               } 
/*     */               
/* 146 */               if (!flag2)
/*     */               {
/* 148 */                 for (achievement3 = achievement2; achievement3 != null; achievement3 = achievement3.parentAchievement)
/*     */                 {
/* 150 */                   list2.remove(achievement2);
/*     */                 }
/*     */               }
/*     */             } 
/*     */             
/* 155 */             for (Achievement achievement6 : list2)
/*     */             {
/* 157 */               entityplayermp.func_175145_a((StatBase)achievement6);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 162 */         if (flag) {
/*     */           
/* 164 */           entityplayermp.triggerAchievement(statbase);
/* 165 */           notifyOperators(sender, (ICommand)this, "commands.achievement.give.success.one", new Object[] { entityplayermp.getName(), statbase.createChatComponent() });
/*     */         }
/* 167 */         else if (flag1) {
/*     */           
/* 169 */           entityplayermp.func_175145_a(statbase);
/* 170 */           notifyOperators(sender, (ICommand)this, "commands.achievement.take.success.one", new Object[] { statbase.createChatComponent(), entityplayermp.getName() });
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 180 */     if (args.length == 1)
/*     */     {
/* 182 */       return getListOfStringsMatchingLastWord(args, new String[] { "give", "take" });
/*     */     }
/* 184 */     if (args.length != 2)
/*     */     {
/* 186 */       return (args.length == 3) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*     */     }
/*     */ 
/*     */     
/* 190 */     List<String> list = Lists.newArrayList();
/*     */     
/* 192 */     for (StatBase statbase : StatList.allStats)
/*     */     {
/* 194 */       list.add(statbase.statId);
/*     */     }
/*     */     
/* 197 */     return getListOfStringsMatchingLastWord(args, list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 206 */     return (index == 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandAchievement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */