/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandHandler
/*     */   implements ICommandManager
/*     */ {
/*  20 */   private static final Logger logger = LogManager.getLogger();
/*  21 */   private final Map<String, ICommand> commandMap = Maps.newHashMap();
/*  22 */   private final Set<ICommand> commandSet = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeCommand(ICommandSender sender, String rawCommand) {
/*  35 */     rawCommand = rawCommand.trim();
/*     */     
/*  37 */     if (!rawCommand.isEmpty() && rawCommand.charAt(0) == '/')
/*     */     {
/*  39 */       rawCommand = rawCommand.substring(1);
/*     */     }
/*     */     
/*  42 */     String[] astring = rawCommand.split(" ");
/*  43 */     String s = astring[0];
/*  44 */     astring = dropFirstString(astring);
/*  45 */     ICommand icommand = this.commandMap.get(s);
/*  46 */     int i = getUsernameIndex(icommand, astring);
/*  47 */     int j = 0;
/*     */     
/*  49 */     if (icommand == null) {
/*     */       
/*  51 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
/*  52 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  53 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation);
/*     */     }
/*  55 */     else if (icommand.canCommandSenderUseCommand(sender)) {
/*     */       
/*  57 */       if (i > -1)
/*     */       {
/*  59 */         List<Entity> list = PlayerSelector.matchEntities(sender, astring[i], Entity.class);
/*  60 */         String s1 = astring[i];
/*  61 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*     */         
/*  63 */         for (Entity entity : list) {
/*     */           
/*  65 */           astring[i] = entity.getUniqueID().toString();
/*     */           
/*  67 */           if (tryExecute(sender, astring, icommand, rawCommand))
/*     */           {
/*  69 */             j++;
/*     */           }
/*     */         } 
/*     */         
/*  73 */         astring[i] = s1;
/*     */       }
/*     */       else
/*     */       {
/*  77 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
/*     */         
/*  79 */         if (tryExecute(sender, astring, icommand, rawCommand))
/*     */         {
/*  81 */           j++;
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  87 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
/*  88 */       chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.RED);
/*  89 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*     */     } 
/*     */     
/*  92 */     sender.setCommandStat(CommandResultStats.Type.SUCCESS_COUNT, j);
/*  93 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean tryExecute(ICommandSender sender, String[] args, ICommand command, String input) {
/*     */     try {
/* 100 */       command.processCommand(sender, args);
/* 101 */       return true;
/*     */     }
/* 103 */     catch (WrongUsageException wrongusageexception) {
/*     */       
/* 105 */       ChatComponentTranslation chatcomponenttranslation2 = new ChatComponentTranslation("commands.generic.usage", new Object[] { new ChatComponentTranslation(wrongusageexception.getMessage(), wrongusageexception.getErrorObjects()) });
/* 106 */       chatcomponenttranslation2.getChatStyle().setColor(EnumChatFormatting.RED);
/* 107 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation2);
/*     */     }
/* 109 */     catch (CommandException commandexception) {
/*     */       
/* 111 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
/* 112 */       chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.RED);
/* 113 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*     */     }
/* 115 */     catch (Throwable var9) {
/*     */       
/* 117 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
/* 118 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/* 119 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 120 */       logger.warn("Couldn't process command: '" + input + "'");
/*     */     } 
/*     */     
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICommand registerCommand(ICommand command) {
/* 131 */     this.commandMap.put(command.getCommandName(), command);
/* 132 */     this.commandSet.add(command);
/*     */     
/* 134 */     for (String s : command.getCommandAliases()) {
/*     */       
/* 136 */       ICommand icommand = this.commandMap.get(s);
/*     */       
/* 138 */       if (icommand == null || !icommand.getCommandName().equals(s))
/*     */       {
/* 140 */         this.commandMap.put(s, command);
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] dropFirstString(String[] input) {
/* 152 */     String[] astring = new String[input.length - 1];
/* 153 */     System.arraycopy(input, 1, astring, 0, input.length - 1);
/* 154 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(ICommandSender sender, String input, BlockPos pos) {
/* 159 */     String[] astring = input.split(" ", -1);
/* 160 */     String s = astring[0];
/*     */     
/* 162 */     if (astring.length == 1) {
/*     */       
/* 164 */       List<String> list = Lists.newArrayList();
/*     */       
/* 166 */       for (Map.Entry<String, ICommand> entry : this.commandMap.entrySet()) {
/*     */         
/* 168 */         if (CommandBase.doesStringStartWith(s, entry.getKey()) && ((ICommand)entry.getValue()).canCommandSenderUseCommand(sender))
/*     */         {
/* 170 */           list.add(entry.getKey());
/*     */         }
/*     */       } 
/*     */       
/* 174 */       return list;
/*     */     } 
/*     */ 
/*     */     
/* 178 */     if (astring.length > 1) {
/*     */       
/* 180 */       ICommand icommand = this.commandMap.get(s);
/*     */       
/* 182 */       if (icommand != null && icommand.canCommandSenderUseCommand(sender))
/*     */       {
/* 184 */         return icommand.addTabCompletionOptions(sender, dropFirstString(astring), pos);
/*     */       }
/*     */     } 
/*     */     
/* 188 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ICommand> getPossibleCommands(ICommandSender sender) {
/* 194 */     List<ICommand> list = Lists.newArrayList();
/*     */     
/* 196 */     for (ICommand icommand : this.commandSet) {
/*     */       
/* 198 */       if (icommand.canCommandSenderUseCommand(sender))
/*     */       {
/* 200 */         list.add(icommand);
/*     */       }
/*     */     } 
/*     */     
/* 204 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, ICommand> getCommands() {
/* 209 */     return this.commandMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getUsernameIndex(ICommand command, String[] args) {
/* 217 */     if (command == null)
/*     */     {
/* 219 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 223 */     for (int i = 0; i < args.length; i++) {
/*     */       
/* 225 */       if (command.isUsernameIndex(args, i) && PlayerSelector.matchesMultiplePlayers(args[i]))
/*     */       {
/* 227 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 231 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */