/*      */ package net.minecraft.command.server;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.command.CommandException;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommand;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.SyntaxErrorException;
/*      */ import net.minecraft.command.WrongUsageException;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ 
/*      */ public class CommandScoreboard extends CommandBase {
/*      */   public String getCommandName() {
/*   28 */     return "scoreboard";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRequiredPermissionLevel() {
/*   36 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommandUsage(ICommandSender sender) {
/*   44 */     return "commands.scoreboard.usage";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*   52 */     if (!func_175780_b(sender, args)) {
/*      */       
/*   54 */       if (args.length < 1)
/*      */       {
/*   56 */         throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*   60 */       if (args[0].equalsIgnoreCase("objectives")) {
/*      */         
/*   62 */         if (args.length == 1)
/*      */         {
/*   64 */           throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*      */         }
/*      */         
/*   67 */         if (args[1].equalsIgnoreCase("list"))
/*      */         {
/*   69 */           listObjectives(sender);
/*      */         }
/*   71 */         else if (args[1].equalsIgnoreCase("add"))
/*      */         {
/*   73 */           if (args.length < 4)
/*      */           {
/*   75 */             throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*      */           }
/*      */           
/*   78 */           addObjective(sender, args, 2);
/*      */         }
/*   80 */         else if (args[1].equalsIgnoreCase("remove"))
/*      */         {
/*   82 */           if (args.length != 3)
/*      */           {
/*   84 */             throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*   87 */           removeObjective(sender, args[2]);
/*      */         }
/*      */         else
/*      */         {
/*   91 */           if (!args[1].equalsIgnoreCase("setdisplay"))
/*      */           {
/*   93 */             throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*      */           }
/*      */           
/*   96 */           if (args.length != 3 && args.length != 4)
/*      */           {
/*   98 */             throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
/*      */           }
/*      */           
/*  101 */           setObjectiveDisplay(sender, args, 2);
/*      */         }
/*      */       
/*  104 */       } else if (args[0].equalsIgnoreCase("players")) {
/*      */         
/*  106 */         if (args.length == 1)
/*      */         {
/*  108 */           throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*      */         }
/*      */         
/*  111 */         if (args[1].equalsIgnoreCase("list"))
/*      */         {
/*  113 */           if (args.length > 3)
/*      */           {
/*  115 */             throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
/*      */           }
/*      */           
/*  118 */           listPlayers(sender, args, 2);
/*      */         }
/*  120 */         else if (args[1].equalsIgnoreCase("add"))
/*      */         {
/*  122 */           if (args.length < 5)
/*      */           {
/*  124 */             throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
/*      */           }
/*      */           
/*  127 */           setPlayer(sender, args, 2);
/*      */         }
/*  129 */         else if (args[1].equalsIgnoreCase("remove"))
/*      */         {
/*  131 */           if (args.length < 5)
/*      */           {
/*  133 */             throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*  136 */           setPlayer(sender, args, 2);
/*      */         }
/*  138 */         else if (args[1].equalsIgnoreCase("set"))
/*      */         {
/*  140 */           if (args.length < 5)
/*      */           {
/*  142 */             throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
/*      */           }
/*      */           
/*  145 */           setPlayer(sender, args, 2);
/*      */         }
/*  147 */         else if (args[1].equalsIgnoreCase("reset"))
/*      */         {
/*  149 */           if (args.length != 3 && args.length != 4)
/*      */           {
/*  151 */             throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
/*      */           }
/*      */           
/*  154 */           resetPlayers(sender, args, 2);
/*      */         }
/*  156 */         else if (args[1].equalsIgnoreCase("enable"))
/*      */         {
/*  158 */           if (args.length != 4)
/*      */           {
/*  160 */             throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
/*      */           }
/*      */           
/*  163 */           func_175779_n(sender, args, 2);
/*      */         }
/*  165 */         else if (args[1].equalsIgnoreCase("test"))
/*      */         {
/*  167 */           if (args.length != 5 && args.length != 6)
/*      */           {
/*  169 */             throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
/*      */           }
/*      */           
/*  172 */           func_175781_o(sender, args, 2);
/*      */         }
/*      */         else
/*      */         {
/*  176 */           if (!args[1].equalsIgnoreCase("operation"))
/*      */           {
/*  178 */             throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*      */           }
/*      */           
/*  181 */           if (args.length != 7)
/*      */           {
/*  183 */             throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
/*      */           }
/*      */           
/*  186 */           func_175778_p(sender, args, 2);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  191 */         if (!args[0].equalsIgnoreCase("teams"))
/*      */         {
/*  193 */           throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/*      */         }
/*      */         
/*  196 */         if (args.length == 1)
/*      */         {
/*  198 */           throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/*      */         }
/*      */         
/*  201 */         if (args[1].equalsIgnoreCase("list")) {
/*      */           
/*  203 */           if (args.length > 3)
/*      */           {
/*  205 */             throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
/*      */           }
/*      */           
/*  208 */           listTeams(sender, args, 2);
/*      */         }
/*  210 */         else if (args[1].equalsIgnoreCase("add")) {
/*      */           
/*  212 */           if (args.length < 3)
/*      */           {
/*  214 */             throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/*      */           }
/*      */           
/*  217 */           addTeam(sender, args, 2);
/*      */         }
/*  219 */         else if (args[1].equalsIgnoreCase("remove")) {
/*      */           
/*  221 */           if (args.length != 3)
/*      */           {
/*  223 */             throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*  226 */           removeTeam(sender, args, 2);
/*      */         }
/*  228 */         else if (args[1].equalsIgnoreCase("empty")) {
/*      */           
/*  230 */           if (args.length != 3)
/*      */           {
/*  232 */             throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
/*      */           }
/*      */           
/*  235 */           emptyTeam(sender, args, 2);
/*      */         }
/*  237 */         else if (args[1].equalsIgnoreCase("join")) {
/*      */           
/*  239 */           if (args.length < 4 && (args.length != 3 || !(sender instanceof net.minecraft.entity.player.EntityPlayer)))
/*      */           {
/*  241 */             throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
/*      */           }
/*      */           
/*  244 */           joinTeam(sender, args, 2);
/*      */         }
/*  246 */         else if (args[1].equalsIgnoreCase("leave")) {
/*      */           
/*  248 */           if (args.length < 3 && !(sender instanceof net.minecraft.entity.player.EntityPlayer))
/*      */           {
/*  250 */             throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
/*      */           }
/*      */           
/*  253 */           leaveTeam(sender, args, 2);
/*      */         }
/*      */         else {
/*      */           
/*  257 */           if (!args[1].equalsIgnoreCase("option"))
/*      */           {
/*  259 */             throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/*      */           }
/*      */           
/*  262 */           if (args.length != 4 && args.length != 5)
/*      */           {
/*  264 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */           }
/*      */           
/*  267 */           setTeamOption(sender, args, 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean func_175780_b(ICommandSender p_175780_1_, String[] p_175780_2_) throws CommandException {
/*  276 */     int i = -1;
/*      */     
/*  278 */     for (int j = 0; j < p_175780_2_.length; j++) {
/*      */       
/*  280 */       if (isUsernameIndex(p_175780_2_, j) && "*".equals(p_175780_2_[j])) {
/*      */         
/*  282 */         if (i >= 0)
/*      */         {
/*  284 */           throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
/*      */         }
/*      */         
/*  287 */         i = j;
/*      */       } 
/*      */     } 
/*      */     
/*  291 */     if (i < 0)
/*      */     {
/*  293 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  297 */     List<String> list1 = Lists.newArrayList(getScoreboard().getObjectiveNames());
/*  298 */     String s = p_175780_2_[i];
/*  299 */     List<String> list = Lists.newArrayList();
/*      */     
/*  301 */     for (String s1 : list1) {
/*      */       
/*  303 */       p_175780_2_[i] = s1;
/*      */ 
/*      */       
/*      */       try {
/*  307 */         processCommand(p_175780_1_, p_175780_2_);
/*  308 */         list.add(s1);
/*      */       }
/*  310 */       catch (CommandException commandexception) {
/*      */         
/*  312 */         ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
/*  313 */         chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  314 */         p_175780_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*      */       } 
/*      */     } 
/*      */     
/*  318 */     p_175780_2_[i] = s;
/*  319 */     p_175780_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*      */     
/*  321 */     if (list.isEmpty())
/*      */     {
/*  323 */       throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  327 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Scoreboard getScoreboard() {
/*  334 */     return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*      */   }
/*      */ 
/*      */   
/*      */   protected ScoreObjective getObjective(String name, boolean edit) throws CommandException {
/*  339 */     Scoreboard scoreboard = getScoreboard();
/*  340 */     ScoreObjective scoreobjective = scoreboard.getObjective(name);
/*      */     
/*  342 */     if (scoreobjective == null)
/*      */     {
/*  344 */       throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { name });
/*      */     }
/*  346 */     if (edit && scoreobjective.getCriteria().isReadOnly())
/*      */     {
/*  348 */       throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { name });
/*      */     }
/*      */ 
/*      */     
/*  352 */     return scoreobjective;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ScorePlayerTeam getTeam(String name) throws CommandException {
/*  358 */     Scoreboard scoreboard = getScoreboard();
/*  359 */     ScorePlayerTeam scoreplayerteam = scoreboard.getTeam(name);
/*      */     
/*  361 */     if (scoreplayerteam == null)
/*      */     {
/*  363 */       throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { name });
/*      */     }
/*      */ 
/*      */     
/*  367 */     return scoreplayerteam;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addObjective(ICommandSender sender, String[] args, int index) throws CommandException {
/*  373 */     String s = args[index++];
/*  374 */     String s1 = args[index++];
/*  375 */     Scoreboard scoreboard = getScoreboard();
/*  376 */     IScoreObjectiveCriteria iscoreobjectivecriteria = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.INSTANCES.get(s1);
/*      */     
/*  378 */     if (iscoreobjectivecriteria == null)
/*      */     {
/*  380 */       throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { s1 });
/*      */     }
/*  382 */     if (scoreboard.getObjective(s) != null)
/*      */     {
/*  384 */       throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { s });
/*      */     }
/*  386 */     if (s.length() > 16)
/*      */     {
/*  388 */       throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { s, Integer.valueOf(16) });
/*      */     }
/*  390 */     if (s.isEmpty())
/*      */     {
/*  392 */       throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  396 */     if (args.length > index) {
/*      */       
/*  398 */       String s2 = getChatComponentFromNthArg(sender, args, index).getUnformattedText();
/*      */       
/*  400 */       if (s2.length() > 32)
/*      */       {
/*  402 */         throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { s2, Integer.valueOf(32) });
/*      */       }
/*      */       
/*  405 */       if (!s2.isEmpty())
/*      */       {
/*  407 */         scoreboard.addScoreObjective(s, iscoreobjectivecriteria).setDisplayName(s2);
/*      */       }
/*      */       else
/*      */       {
/*  411 */         scoreboard.addScoreObjective(s, iscoreobjectivecriteria);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  416 */       scoreboard.addScoreObjective(s, iscoreobjectivecriteria);
/*      */     } 
/*      */     
/*  419 */     notifyOperators(sender, (ICommand)this, "commands.scoreboard.objectives.add.success", new Object[] { s });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addTeam(ICommandSender sender, String[] args, int index) throws CommandException {
/*  425 */     String s = args[index++];
/*  426 */     Scoreboard scoreboard = getScoreboard();
/*      */     
/*  428 */     if (scoreboard.getTeam(s) != null)
/*      */     {
/*  430 */       throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { s });
/*      */     }
/*  432 */     if (s.length() > 16)
/*      */     {
/*  434 */       throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { s, Integer.valueOf(16) });
/*      */     }
/*  436 */     if (s.isEmpty())
/*      */     {
/*  438 */       throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  442 */     if (args.length > index) {
/*      */       
/*  444 */       String s1 = getChatComponentFromNthArg(sender, args, index).getUnformattedText();
/*      */       
/*  446 */       if (s1.length() > 32)
/*      */       {
/*  448 */         throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { s1, Integer.valueOf(32) });
/*      */       }
/*      */       
/*  451 */       if (!s1.isEmpty())
/*      */       {
/*  453 */         scoreboard.createTeam(s).setTeamName(s1);
/*      */       }
/*      */       else
/*      */       {
/*  457 */         scoreboard.createTeam(s);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  462 */       scoreboard.createTeam(s);
/*      */     } 
/*      */     
/*  465 */     notifyOperators(sender, (ICommand)this, "commands.scoreboard.teams.add.success", new Object[] { s });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setTeamOption(ICommandSender sender, String[] args, int index) throws CommandException {
/*  471 */     ScorePlayerTeam scoreplayerteam = getTeam(args[index++]);
/*      */     
/*  473 */     if (scoreplayerteam != null) {
/*      */       
/*  475 */       String s = args[index++].toLowerCase();
/*      */       
/*  477 */       if (!s.equalsIgnoreCase("color") && !s.equalsIgnoreCase("friendlyfire") && !s.equalsIgnoreCase("seeFriendlyInvisibles") && !s.equalsIgnoreCase("nametagVisibility") && !s.equalsIgnoreCase("deathMessageVisibility"))
/*      */       {
/*  479 */         throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */       }
/*  481 */       if (args.length == 4) {
/*      */         
/*  483 */         if (s.equalsIgnoreCase("color"))
/*      */         {
/*  485 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
/*      */         }
/*  487 */         if (!s.equalsIgnoreCase("friendlyfire") && !s.equalsIgnoreCase("seeFriendlyInvisibles")) {
/*      */           
/*  489 */           if (!s.equalsIgnoreCase("nametagVisibility") && !s.equalsIgnoreCase("deathMessageVisibility"))
/*      */           {
/*  491 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */           }
/*      */ 
/*      */           
/*  495 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  500 */         throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  505 */       String s1 = args[index];
/*      */       
/*  507 */       if (s.equalsIgnoreCase("color")) {
/*      */         
/*  509 */         EnumChatFormatting enumchatformatting = EnumChatFormatting.getValueByName(s1);
/*      */         
/*  511 */         if (enumchatformatting == null || enumchatformatting.isFancyStyling())
/*      */         {
/*  513 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
/*      */         }
/*      */         
/*  516 */         scoreplayerteam.setChatFormat(enumchatformatting);
/*  517 */         scoreplayerteam.setNamePrefix(enumchatformatting.toString());
/*  518 */         scoreplayerteam.setNameSuffix(EnumChatFormatting.RESET.toString());
/*      */       }
/*  520 */       else if (s.equalsIgnoreCase("friendlyfire")) {
/*      */         
/*  522 */         if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false"))
/*      */         {
/*  524 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */         }
/*      */         
/*  527 */         scoreplayerteam.setAllowFriendlyFire(s1.equalsIgnoreCase("true"));
/*      */       }
/*  529 */       else if (s.equalsIgnoreCase("seeFriendlyInvisibles")) {
/*      */         
/*  531 */         if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false"))
/*      */         {
/*  533 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */         }
/*      */         
/*  536 */         scoreplayerteam.setSeeFriendlyInvisiblesEnabled(s1.equalsIgnoreCase("true"));
/*      */       }
/*  538 */       else if (s.equalsIgnoreCase("nametagVisibility")) {
/*      */         
/*  540 */         Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(s1);
/*      */         
/*  542 */         if (team$enumvisible == null)
/*      */         {
/*  544 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*      */         }
/*      */         
/*  547 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*      */       }
/*  549 */       else if (s.equalsIgnoreCase("deathMessageVisibility")) {
/*      */         
/*  551 */         Team.EnumVisible team$enumvisible1 = Team.EnumVisible.func_178824_a(s1);
/*      */         
/*  553 */         if (team$enumvisible1 == null)
/*      */         {
/*  555 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*      */         }
/*      */         
/*  558 */         scoreplayerteam.setDeathMessageVisibility(team$enumvisible1);
/*      */       } 
/*      */       
/*  561 */       notifyOperators(sender, (ICommand)this, "commands.scoreboard.teams.option.success", new Object[] { s, scoreplayerteam.getRegisteredName(), s1 });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeTeam(ICommandSender p_147194_1_, String[] p_147194_2_, int p_147194_3_) throws CommandException {
/*  568 */     Scoreboard scoreboard = getScoreboard();
/*  569 */     ScorePlayerTeam scoreplayerteam = getTeam(p_147194_2_[p_147194_3_]);
/*      */     
/*  571 */     if (scoreplayerteam != null) {
/*      */       
/*  573 */       scoreboard.removeTeam(scoreplayerteam);
/*  574 */       notifyOperators(p_147194_1_, (ICommand)this, "commands.scoreboard.teams.remove.success", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void listTeams(ICommandSender p_147186_1_, String[] p_147186_2_, int p_147186_3_) throws CommandException {
/*  580 */     Scoreboard scoreboard = getScoreboard();
/*      */     
/*  582 */     if (p_147186_2_.length > p_147186_3_) {
/*      */       
/*  584 */       ScorePlayerTeam scoreplayerteam = getTeam(p_147186_2_[p_147186_3_]);
/*      */       
/*  586 */       if (scoreplayerteam == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  591 */       Collection<String> collection = scoreplayerteam.getMembershipCollection();
/*  592 */       p_147186_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection.size());
/*      */       
/*  594 */       if (collection.size() <= 0)
/*      */       {
/*  596 */         throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */       }
/*      */       
/*  599 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.getRegisteredName() });
/*  600 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/*  601 */       p_147186_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*  602 */       p_147186_1_.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString(collection.toArray())));
/*      */     }
/*      */     else {
/*      */       
/*  606 */       Collection<ScorePlayerTeam> collection1 = scoreboard.getTeams();
/*  607 */       p_147186_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection1.size());
/*      */       
/*  609 */       if (collection1.size() <= 0)
/*      */       {
/*  611 */         throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
/*      */       }
/*      */       
/*  614 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(collection1.size()) });
/*  615 */       chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/*  616 */       p_147186_1_.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*      */       
/*  618 */       for (ScorePlayerTeam scoreplayerteam1 : collection1) {
/*      */         
/*  620 */         p_147186_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { scoreplayerteam1.getRegisteredName(), scoreplayerteam1.getTeamName(), Integer.valueOf(scoreplayerteam1.getMembershipCollection().size()) }));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void joinTeam(ICommandSender p_147190_1_, String[] p_147190_2_, int p_147190_3_) throws CommandException {
/*  627 */     Scoreboard scoreboard = getScoreboard();
/*  628 */     String s = p_147190_2_[p_147190_3_++];
/*  629 */     Set<String> set = Sets.newHashSet();
/*  630 */     Set<String> set1 = Sets.newHashSet();
/*      */     
/*  632 */     if (p_147190_1_ instanceof net.minecraft.entity.player.EntityPlayer && p_147190_3_ == p_147190_2_.length) {
/*      */       
/*  634 */       String s4 = getCommandSenderAsPlayer(p_147190_1_).getName();
/*      */       
/*  636 */       if (scoreboard.addPlayerToTeam(s4, s))
/*      */       {
/*  638 */         set.add(s4);
/*      */       }
/*      */       else
/*      */       {
/*  642 */         set1.add(s4);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  647 */       while (p_147190_3_ < p_147190_2_.length) {
/*      */         
/*  649 */         String s1 = p_147190_2_[p_147190_3_++];
/*      */         
/*  651 */         if (!s1.isEmpty() && s1.charAt(0) == '@') {
/*      */           
/*  653 */           for (Entity entity : func_175763_c(p_147190_1_, s1)) {
/*      */             
/*  655 */             String s3 = getEntityName(p_147190_1_, entity.getUniqueID().toString());
/*      */             
/*  657 */             if (scoreboard.addPlayerToTeam(s3, s)) {
/*      */               
/*  659 */               set.add(s3);
/*      */               
/*      */               continue;
/*      */             } 
/*  663 */             set1.add(s3);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  669 */         String s2 = getEntityName(p_147190_1_, s1);
/*      */         
/*  671 */         if (scoreboard.addPlayerToTeam(s2, s)) {
/*      */           
/*  673 */           set.add(s2);
/*      */           
/*      */           continue;
/*      */         } 
/*  677 */         set1.add(s2);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  683 */     if (!set.isEmpty()) {
/*      */       
/*  685 */       p_147190_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
/*  686 */       notifyOperators(p_147190_1_, (ICommand)this, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(set.size()), s, joinNiceString(set.toArray((Object[])new String[0])) });
/*      */     } 
/*      */     
/*  689 */     if (!set1.isEmpty())
/*      */     {
/*  691 */       throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(set1.size()), s, joinNiceString(set1.toArray(new String[0])) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void leaveTeam(ICommandSender p_147199_1_, String[] p_147199_2_, int p_147199_3_) throws CommandException {
/*  697 */     Scoreboard scoreboard = getScoreboard();
/*  698 */     Set<String> set = Sets.newHashSet();
/*  699 */     Set<String> set1 = Sets.newHashSet();
/*      */     
/*  701 */     if (p_147199_1_ instanceof net.minecraft.entity.player.EntityPlayer && p_147199_3_ == p_147199_2_.length) {
/*      */       
/*  703 */       String s3 = getCommandSenderAsPlayer(p_147199_1_).getName();
/*      */       
/*  705 */       if (scoreboard.removePlayerFromTeams(s3))
/*      */       {
/*  707 */         set.add(s3);
/*      */       }
/*      */       else
/*      */       {
/*  711 */         set1.add(s3);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  716 */       while (p_147199_3_ < p_147199_2_.length) {
/*      */         
/*  718 */         String s = p_147199_2_[p_147199_3_++];
/*      */         
/*  720 */         if (!s.isEmpty() && s.charAt(0) == '@') {
/*      */           
/*  722 */           for (Entity entity : func_175763_c(p_147199_1_, s)) {
/*      */             
/*  724 */             String s2 = getEntityName(p_147199_1_, entity.getUniqueID().toString());
/*      */             
/*  726 */             if (scoreboard.removePlayerFromTeams(s2)) {
/*      */               
/*  728 */               set.add(s2);
/*      */               
/*      */               continue;
/*      */             } 
/*  732 */             set1.add(s2);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  738 */         String s1 = getEntityName(p_147199_1_, s);
/*      */         
/*  740 */         if (scoreboard.removePlayerFromTeams(s1)) {
/*      */           
/*  742 */           set.add(s1);
/*      */           
/*      */           continue;
/*      */         } 
/*  746 */         set1.add(s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  752 */     if (!set.isEmpty()) {
/*      */       
/*  754 */       p_147199_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
/*  755 */       notifyOperators(p_147199_1_, (ICommand)this, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(set.size()), joinNiceString(set.toArray((Object[])new String[0])) });
/*      */     } 
/*      */     
/*  758 */     if (!set1.isEmpty())
/*      */     {
/*  760 */       throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(set1.size()), joinNiceString(set1.toArray(new String[0])) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void emptyTeam(ICommandSender p_147188_1_, String[] p_147188_2_, int p_147188_3_) throws CommandException {
/*  766 */     Scoreboard scoreboard = getScoreboard();
/*  767 */     ScorePlayerTeam scoreplayerteam = getTeam(p_147188_2_[p_147188_3_]);
/*      */     
/*  769 */     if (scoreplayerteam != null) {
/*      */       
/*  771 */       Collection<String> collection = Lists.newArrayList(scoreplayerteam.getMembershipCollection());
/*  772 */       p_147188_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, collection.size());
/*      */       
/*  774 */       if (collection.isEmpty())
/*      */       {
/*  776 */         throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */       }
/*      */ 
/*      */       
/*  780 */       for (String s : collection)
/*      */       {
/*  782 */         scoreboard.removePlayerFromTeam(s, scoreplayerteam);
/*      */       }
/*      */       
/*  785 */       notifyOperators(p_147188_1_, (ICommand)this, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.getRegisteredName() });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeObjective(ICommandSender p_147191_1_, String p_147191_2_) throws CommandException {
/*  792 */     Scoreboard scoreboard = getScoreboard();
/*  793 */     ScoreObjective scoreobjective = getObjective(p_147191_2_, false);
/*  794 */     scoreboard.removeObjective(scoreobjective);
/*  795 */     notifyOperators(p_147191_1_, (ICommand)this, "commands.scoreboard.objectives.remove.success", new Object[] { p_147191_2_ });
/*      */   }
/*      */ 
/*      */   
/*      */   protected void listObjectives(ICommandSender p_147196_1_) throws CommandException {
/*  800 */     Scoreboard scoreboard = getScoreboard();
/*  801 */     Collection<ScoreObjective> collection = scoreboard.getScoreObjectives();
/*      */     
/*  803 */     if (collection.size() <= 0)
/*      */     {
/*  805 */       throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  809 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(collection.size()) });
/*  810 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/*  811 */     p_147196_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*      */     
/*  813 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/*  815 */       p_147196_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { scoreobjective.getName(), scoreobjective.getDisplayName(), scoreobjective.getCriteria().getName() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setObjectiveDisplay(ICommandSender p_147198_1_, String[] p_147198_2_, int p_147198_3_) throws CommandException {
/*  822 */     Scoreboard scoreboard = getScoreboard();
/*  823 */     String s = p_147198_2_[p_147198_3_++];
/*  824 */     int i = Scoreboard.getObjectiveDisplaySlotNumber(s);
/*  825 */     ScoreObjective scoreobjective = null;
/*      */     
/*  827 */     if (p_147198_2_.length == 4)
/*      */     {
/*  829 */       scoreobjective = getObjective(p_147198_2_[p_147198_3_], false);
/*      */     }
/*      */     
/*  832 */     if (i < 0)
/*      */     {
/*  834 */       throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { s });
/*      */     }
/*      */ 
/*      */     
/*  838 */     scoreboard.setObjectiveInDisplaySlot(i, scoreobjective);
/*      */     
/*  840 */     if (scoreobjective != null) {
/*      */       
/*  842 */       notifyOperators(p_147198_1_, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getObjectiveDisplaySlot(i), scoreobjective.getName() });
/*      */     }
/*      */     else {
/*      */       
/*  846 */       notifyOperators(p_147198_1_, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getObjectiveDisplaySlot(i) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void listPlayers(ICommandSender p_147195_1_, String[] p_147195_2_, int p_147195_3_) throws CommandException {
/*  853 */     Scoreboard scoreboard = getScoreboard();
/*      */     
/*  855 */     if (p_147195_2_.length > p_147195_3_) {
/*      */       
/*  857 */       String s = getEntityName(p_147195_1_, p_147195_2_[p_147195_3_]);
/*  858 */       Map<ScoreObjective, Score> map = scoreboard.getObjectivesForEntity(s);
/*  859 */       p_147195_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, map.size());
/*      */       
/*  861 */       if (map.size() <= 0)
/*      */       {
/*  863 */         throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { s });
/*      */       }
/*      */       
/*  866 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(map.size()), s });
/*  867 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/*  868 */       p_147195_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*      */       
/*  870 */       for (Score score : map.values())
/*      */       {
/*  872 */         p_147195_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(score.getScorePoints()), score.getObjective().getDisplayName(), score.getObjective().getName() }));
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  877 */       Collection<String> collection = scoreboard.getObjectiveNames();
/*  878 */       p_147195_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection.size());
/*      */       
/*  880 */       if (collection.size() <= 0)
/*      */       {
/*  882 */         throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
/*      */       }
/*      */       
/*  885 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(collection.size()) });
/*  886 */       chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/*  887 */       p_147195_1_.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*  888 */       p_147195_1_.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString(collection.toArray())));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setPlayer(ICommandSender p_147197_1_, String[] p_147197_2_, int p_147197_3_) throws CommandException {
/*  894 */     String s = p_147197_2_[p_147197_3_ - 1];
/*  895 */     int i = p_147197_3_;
/*  896 */     String s1 = getEntityName(p_147197_1_, p_147197_2_[p_147197_3_++]);
/*      */     
/*  898 */     if (s1.length() > 40)
/*      */     {
/*  900 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s1, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/*  904 */     ScoreObjective scoreobjective = getObjective(p_147197_2_[p_147197_3_++], true);
/*  905 */     int j = s.equalsIgnoreCase("set") ? parseInt(p_147197_2_[p_147197_3_++]) : parseInt(p_147197_2_[p_147197_3_++], 0);
/*      */     
/*  907 */     if (p_147197_2_.length > p_147197_3_) {
/*      */       
/*  909 */       Entity entity = getEntity(p_147197_1_, p_147197_2_[i]);
/*      */ 
/*      */       
/*      */       try {
/*  913 */         NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(buildString(p_147197_2_, p_147197_3_));
/*  914 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  915 */         entity.writeToNBT(nbttagcompound1);
/*      */         
/*  917 */         if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*      */         {
/*  919 */           throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { s1 });
/*      */         }
/*      */       }
/*  922 */       catch (NBTException nbtexception) {
/*      */         
/*  924 */         throw new CommandException("commands.scoreboard.players.set.tagError", new Object[] { nbtexception.getMessage() });
/*      */       } 
/*      */     } 
/*      */     
/*  928 */     Scoreboard scoreboard = getScoreboard();
/*  929 */     Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/*      */     
/*  931 */     if (s.equalsIgnoreCase("set")) {
/*      */       
/*  933 */       score.setScorePoints(j);
/*      */     }
/*  935 */     else if (s.equalsIgnoreCase("add")) {
/*      */       
/*  937 */       score.increseScore(j);
/*      */     }
/*      */     else {
/*      */       
/*  941 */       score.decreaseScore(j);
/*      */     } 
/*      */     
/*  944 */     notifyOperators(p_147197_1_, (ICommand)this, "commands.scoreboard.players.set.success", new Object[] { scoreobjective.getName(), s1, Integer.valueOf(score.getScorePoints()) });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetPlayers(ICommandSender p_147187_1_, String[] p_147187_2_, int p_147187_3_) throws CommandException {
/*  950 */     Scoreboard scoreboard = getScoreboard();
/*  951 */     String s = getEntityName(p_147187_1_, p_147187_2_[p_147187_3_++]);
/*      */     
/*  953 */     if (p_147187_2_.length > p_147187_3_) {
/*      */       
/*  955 */       ScoreObjective scoreobjective = getObjective(p_147187_2_[p_147187_3_++], false);
/*  956 */       scoreboard.removeObjectiveFromEntity(s, scoreobjective);
/*  957 */       notifyOperators(p_147187_1_, (ICommand)this, "commands.scoreboard.players.resetscore.success", new Object[] { scoreobjective.getName(), s });
/*      */     }
/*      */     else {
/*      */       
/*  961 */       scoreboard.removeObjectiveFromEntity(s, (ScoreObjective)null);
/*  962 */       notifyOperators(p_147187_1_, (ICommand)this, "commands.scoreboard.players.reset.success", new Object[] { s });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_175779_n(ICommandSender p_175779_1_, String[] p_175779_2_, int p_175779_3_) throws CommandException {
/*  968 */     Scoreboard scoreboard = getScoreboard();
/*  969 */     String s = getPlayerName(p_175779_1_, p_175779_2_[p_175779_3_++]);
/*      */     
/*  971 */     if (s.length() > 40)
/*      */     {
/*  973 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/*  977 */     ScoreObjective scoreobjective = getObjective(p_175779_2_[p_175779_3_], false);
/*      */     
/*  979 */     if (scoreobjective.getCriteria() != IScoreObjectiveCriteria.TRIGGER)
/*      */     {
/*  981 */       throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { scoreobjective.getName() });
/*      */     }
/*      */ 
/*      */     
/*  985 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/*  986 */     score.setLocked(false);
/*  987 */     notifyOperators(p_175779_1_, (ICommand)this, "commands.scoreboard.players.enable.success", new Object[] { scoreobjective.getName(), s });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_175781_o(ICommandSender p_175781_1_, String[] p_175781_2_, int p_175781_3_) throws CommandException {
/*  994 */     Scoreboard scoreboard = getScoreboard();
/*  995 */     String s = getEntityName(p_175781_1_, p_175781_2_[p_175781_3_++]);
/*      */     
/*  997 */     if (s.length() > 40)
/*      */     {
/*  999 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/* 1003 */     ScoreObjective scoreobjective = getObjective(p_175781_2_[p_175781_3_++], false);
/*      */     
/* 1005 */     if (!scoreboard.entityHasObjective(s, scoreobjective))
/*      */     {
/* 1007 */       throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { scoreobjective.getName(), s });
/*      */     }
/*      */ 
/*      */     
/* 1011 */     int i = p_175781_2_[p_175781_3_].equals("*") ? Integer.MIN_VALUE : parseInt(p_175781_2_[p_175781_3_]);
/* 1012 */     p_175781_3_++;
/* 1013 */     int j = (p_175781_3_ < p_175781_2_.length && !p_175781_2_[p_175781_3_].equals("*")) ? parseInt(p_175781_2_[p_175781_3_], i) : Integer.MAX_VALUE;
/* 1014 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/*      */     
/* 1016 */     if (score.getScorePoints() >= i && score.getScorePoints() <= j) {
/*      */       
/* 1018 */       notifyOperators(p_175781_1_, (ICommand)this, "commands.scoreboard.players.test.success", new Object[] { Integer.valueOf(score.getScorePoints()), Integer.valueOf(i), Integer.valueOf(j) });
/*      */     }
/*      */     else {
/*      */       
/* 1022 */       throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { Integer.valueOf(score.getScorePoints()), Integer.valueOf(i), Integer.valueOf(j) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_175778_p(ICommandSender p_175778_1_, String[] p_175778_2_, int p_175778_3_) throws CommandException {
/* 1030 */     Scoreboard scoreboard = getScoreboard();
/* 1031 */     String s = getEntityName(p_175778_1_, p_175778_2_[p_175778_3_++]);
/* 1032 */     ScoreObjective scoreobjective = getObjective(p_175778_2_[p_175778_3_++], true);
/* 1033 */     String s1 = p_175778_2_[p_175778_3_++];
/* 1034 */     String s2 = getEntityName(p_175778_1_, p_175778_2_[p_175778_3_++]);
/* 1035 */     ScoreObjective scoreobjective1 = getObjective(p_175778_2_[p_175778_3_], false);
/*      */     
/* 1037 */     if (s.length() > 40)
/*      */     {
/* 1039 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/* 1041 */     if (s2.length() > 40)
/*      */     {
/* 1043 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s2, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/* 1047 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/*      */     
/* 1049 */     if (!scoreboard.entityHasObjective(s2, scoreobjective1))
/*      */     {
/* 1051 */       throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { scoreobjective1.getName(), s2 });
/*      */     }
/*      */ 
/*      */     
/* 1055 */     Score score1 = scoreboard.getValueFromObjective(s2, scoreobjective1);
/*      */     
/* 1057 */     if (s1.equals("+=")) {
/*      */       
/* 1059 */       score.setScorePoints(score.getScorePoints() + score1.getScorePoints());
/*      */     }
/* 1061 */     else if (s1.equals("-=")) {
/*      */       
/* 1063 */       score.setScorePoints(score.getScorePoints() - score1.getScorePoints());
/*      */     }
/* 1065 */     else if (s1.equals("*=")) {
/*      */       
/* 1067 */       score.setScorePoints(score.getScorePoints() * score1.getScorePoints());
/*      */     }
/* 1069 */     else if (s1.equals("/=")) {
/*      */       
/* 1071 */       if (score1.getScorePoints() != 0)
/*      */       {
/* 1073 */         score.setScorePoints(score.getScorePoints() / score1.getScorePoints());
/*      */       }
/*      */     }
/* 1076 */     else if (s1.equals("%=")) {
/*      */       
/* 1078 */       if (score1.getScorePoints() != 0)
/*      */       {
/* 1080 */         score.setScorePoints(score.getScorePoints() % score1.getScorePoints());
/*      */       }
/*      */     }
/* 1083 */     else if (s1.equals("=")) {
/*      */       
/* 1085 */       score.setScorePoints(score1.getScorePoints());
/*      */     }
/* 1087 */     else if (s1.equals("<")) {
/*      */       
/* 1089 */       score.setScorePoints(Math.min(score.getScorePoints(), score1.getScorePoints()));
/*      */     }
/* 1091 */     else if (s1.equals(">")) {
/*      */       
/* 1093 */       score.setScorePoints(Math.max(score.getScorePoints(), score1.getScorePoints()));
/*      */     }
/*      */     else {
/*      */       
/* 1097 */       if (!s1.equals("><"))
/*      */       {
/* 1099 */         throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { s1 });
/*      */       }
/*      */       
/* 1102 */       int i = score.getScorePoints();
/* 1103 */       score.setScorePoints(score1.getScorePoints());
/* 1104 */       score1.setScorePoints(i);
/*      */     } 
/*      */     
/* 1107 */     notifyOperators(p_175778_1_, (ICommand)this, "commands.scoreboard.players.operation.success", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 1114 */     if (args.length == 1)
/*      */     {
/* 1116 */       return getListOfStringsMatchingLastWord(args, new String[] { "objectives", "players", "teams" });
/*      */     }
/*      */ 
/*      */     
/* 1120 */     if (args[0].equalsIgnoreCase("objectives")) {
/*      */       
/* 1122 */       if (args.length == 2)
/*      */       {
/* 1124 */         return getListOfStringsMatchingLastWord(args, new String[] { "list", "add", "remove", "setdisplay" });
/*      */       }
/*      */       
/* 1127 */       if (args[1].equalsIgnoreCase("add")) {
/*      */         
/* 1129 */         if (args.length == 4)
/*      */         {
/* 1131 */           Set<String> set = IScoreObjectiveCriteria.INSTANCES.keySet();
/* 1132 */           return getListOfStringsMatchingLastWord(args, set);
/*      */         }
/*      */       
/* 1135 */       } else if (args[1].equalsIgnoreCase("remove")) {
/*      */         
/* 1137 */         if (args.length == 3)
/*      */         {
/* 1139 */           return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*      */         }
/*      */       }
/* 1142 */       else if (args[1].equalsIgnoreCase("setdisplay")) {
/*      */         
/* 1144 */         if (args.length == 3)
/*      */         {
/* 1146 */           return getListOfStringsMatchingLastWord(args, Scoreboard.getDisplaySlotStrings());
/*      */         }
/*      */         
/* 1149 */         if (args.length == 4)
/*      */         {
/* 1151 */           return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*      */         }
/*      */       }
/*      */     
/* 1155 */     } else if (args[0].equalsIgnoreCase("players")) {
/*      */       
/* 1157 */       if (args.length == 2)
/*      */       {
/* 1159 */         return getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "remove", "reset", "list", "enable", "test", "operation" });
/*      */       }
/*      */       
/* 1162 */       if (!args[1].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("reset")) {
/*      */         
/* 1164 */         if (args[1].equalsIgnoreCase("enable")) {
/*      */           
/* 1166 */           if (args.length == 3)
/*      */           {
/* 1168 */             return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */           }
/*      */           
/* 1171 */           if (args.length == 4)
/*      */           {
/* 1173 */             return getListOfStringsMatchingLastWord(args, func_175782_e());
/*      */           }
/*      */         }
/* 1176 */         else if (!args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("test")) {
/*      */           
/* 1178 */           if (args[1].equalsIgnoreCase("operation"))
/*      */           {
/* 1180 */             if (args.length == 3)
/*      */             {
/* 1182 */               return getListOfStringsMatchingLastWord(args, getScoreboard().getObjectiveNames());
/*      */             }
/*      */             
/* 1185 */             if (args.length == 4)
/*      */             {
/* 1187 */               return getListOfStringsMatchingLastWord(args, func_147184_a(true));
/*      */             }
/*      */             
/* 1190 */             if (args.length == 5)
/*      */             {
/* 1192 */               return getListOfStringsMatchingLastWord(args, new String[] { "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><" });
/*      */             }
/*      */             
/* 1195 */             if (args.length == 6)
/*      */             {
/* 1197 */               return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */             }
/*      */             
/* 1200 */             if (args.length == 7)
/*      */             {
/* 1202 */               return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*      */             }
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1208 */           if (args.length == 3)
/*      */           {
/* 1210 */             return getListOfStringsMatchingLastWord(args, getScoreboard().getObjectiveNames());
/*      */           }
/*      */           
/* 1213 */           if (args.length == 4 && args[1].equalsIgnoreCase("test"))
/*      */           {
/* 1215 */             return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1221 */         if (args.length == 3)
/*      */         {
/* 1223 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */         }
/*      */         
/* 1226 */         if (args.length == 4)
/*      */         {
/* 1228 */           return getListOfStringsMatchingLastWord(args, func_147184_a(true));
/*      */         }
/*      */       }
/*      */     
/* 1232 */     } else if (args[0].equalsIgnoreCase("teams")) {
/*      */       
/* 1234 */       if (args.length == 2)
/*      */       {
/* 1236 */         return getListOfStringsMatchingLastWord(args, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
/*      */       }
/*      */       
/* 1239 */       if (args[1].equalsIgnoreCase("join")) {
/*      */         
/* 1241 */         if (args.length == 3)
/*      */         {
/* 1243 */           return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*      */         }
/*      */         
/* 1246 */         if (args.length >= 4)
/*      */         {
/* 1248 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1253 */         if (args[1].equalsIgnoreCase("leave"))
/*      */         {
/* 1255 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*      */         }
/*      */         
/* 1258 */         if (!args[1].equalsIgnoreCase("empty") && !args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("remove")) {
/*      */           
/* 1260 */           if (args[1].equalsIgnoreCase("option")) {
/*      */             
/* 1262 */             if (args.length == 3)
/*      */             {
/* 1264 */               return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*      */             }
/*      */             
/* 1267 */             if (args.length == 4)
/*      */             {
/* 1269 */               return getListOfStringsMatchingLastWord(args, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility" });
/*      */             }
/*      */             
/* 1272 */             if (args.length == 5)
/*      */             {
/* 1274 */               if (args[3].equalsIgnoreCase("color"))
/*      */               {
/* 1276 */                 return getListOfStringsMatchingLastWord(args, EnumChatFormatting.getValidValues(true, false));
/*      */               }
/*      */               
/* 1279 */               if (args[3].equalsIgnoreCase("nametagVisibility") || args[3].equalsIgnoreCase("deathMessageVisibility"))
/*      */               {
/* 1281 */                 return getListOfStringsMatchingLastWord(args, Team.EnumVisible.func_178825_a());
/*      */               }
/*      */               
/* 1284 */               if (args[3].equalsIgnoreCase("friendlyfire") || args[3].equalsIgnoreCase("seeFriendlyInvisibles"))
/*      */               {
/* 1286 */                 return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/* 1291 */         } else if (args.length == 3) {
/*      */           
/* 1293 */           return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1298 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<String> func_147184_a(boolean p_147184_1_) {
/* 1304 */     Collection<ScoreObjective> collection = getScoreboard().getScoreObjectives();
/* 1305 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1307 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/* 1309 */       if (!p_147184_1_ || !scoreobjective.getCriteria().isReadOnly())
/*      */       {
/* 1311 */         list.add(scoreobjective.getName());
/*      */       }
/*      */     } 
/*      */     
/* 1315 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<String> func_175782_e() {
/* 1320 */     Collection<ScoreObjective> collection = getScoreboard().getScoreObjectives();
/* 1321 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1323 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/* 1325 */       if (scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER)
/*      */       {
/* 1327 */         list.add(scoreobjective.getName());
/*      */       }
/*      */     } 
/*      */     
/* 1331 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsernameIndex(String[] args, int index) {
/* 1339 */     return !args[0].equalsIgnoreCase("players") ? (args[0].equalsIgnoreCase("teams") ? ((index == 2)) : false) : ((args.length > 1 && args[1].equalsIgnoreCase("operation")) ? ((index == 2 || index == 5)) : ((index == 2)));
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */