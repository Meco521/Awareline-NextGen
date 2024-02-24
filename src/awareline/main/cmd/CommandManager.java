/*     */ package awareline.main.cmd;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.cmd.cmds.Bind;
/*     */ import awareline.main.cmd.cmds.Hide;
/*     */ import awareline.main.cmd.cmds.NameProtect;
/*     */ import awareline.main.cmd.cmds.ReloadConfig;
/*     */ import awareline.main.cmd.cmds.WorldClean;
/*     */ import awareline.main.cmd.cmds.setClientTitle;
/*     */ import awareline.main.cmd.cmds.setSpammer;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.misc.EventChat;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import awareline.main.utility.translate.StringUtils;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class CommandManager implements Manager {
/*  21 */   private final List<Command> commands = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public void init() {
/*  25 */     regCommands(new Command[] { (Command)new Help(), (Command)new Toggle(), (Command)new Bind(), (Command)new Binds(), (Command)new Modules(), (Command)new Say(), (Command)new Config(), (Command)new ReloadConfig(), (Command)new setSpammer(), (Command)new NameProtect(), (Command)new setClientTitle(), (Command)new setClientName(), (Command)new setModName(), (Command)new Prefix(), (Command)new Hide(), (Command)new WorldClean(), (Command)new Teleport(), (Command)new VClip() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     EventManager.register(new Object[] { this });
/*     */   }
/*     */   
/*     */   public void regCommands(Command... cmds) {
/*  35 */     Collections.addAll(this.commands, cmds);
/*     */   }
/*     */   
/*     */   public List<Command> getCommands() {
/*  39 */     return this.commands;
/*     */   }
/*     */   
/*     */   private Optional<Command> getCommandByName(String name) {
/*  43 */     return this.commands.stream().filter(c2 -> {
/*     */           boolean isAlias = false;
/*     */           
/*     */           String[] arrString = c2.getAlias();
/*     */           
/*     */           int n = arrString.length;
/*     */           for (int n2 = 0; n2 < n; n2++) {
/*     */             String str = arrString[n2];
/*     */             if (str.equalsIgnoreCase(name)) {
/*     */               isAlias = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*  56 */           return (c2.getName().equalsIgnoreCase(name) || isAlias);
/*  57 */         }).findFirst();
/*     */   }
/*     */   
/*     */   public void add(Command command) {
/*  61 */     this.commands.add(command);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onSendMessage(EventChat event) {
/*  66 */     String message = event.getMessage();
/*  67 */     if (!message.isEmpty() && message.charAt(0) == '/') {
/*  68 */       String s = message.substring(1);
/*  69 */       String[] command = s.split(" ");
/*  70 */       if (command.length > 0) {
/*  71 */         StringBuilder msg = new StringBuilder();
/*  72 */         for (int index = 1; index < command.length; index++)
/*  73 */           msg.append(command[index]).append(" "); 
/*  74 */         switch (command[0]) {
/*     */           case "messagecopy":
/*  76 */             event.setCancelled(true);
/*  77 */             copy(msg.toString());
/*  78 */             Helper.sendMessage("ChatCopy", "Chat copy out.");
/*     */             break;
/*     */           case "translate":
/*  81 */             event.setCancelled(true);
/*  82 */             translate(msg.toString());
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void translate(String str) {
/*  90 */     Thread translate = new Thread(() -> {
/*     */           if ((Minecraft.getMinecraft()).thePlayer != null) {
/*     */             Helper.sendMessage("Translate", StringUtils.translate(str));
/*     */           }
/*     */         });
/*  95 */     translate.setDaemon(true);
/*  96 */     translate.start();
/*     */   }
/*     */   
/*     */   public void copy(String str) {
/* 100 */     StringSelection stsel = new StringSelection(str);
/* 101 */     Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
/*     */   }
/*     */   
/*     */   @EventHandler(3)
/*     */   private void onChat(EventChat e) {
/* 106 */     if (!NoCommand.getInstance.isEnabled() && 
/* 107 */       e.getMessage().length() > 1 && e.getMessage().startsWith(Client.instance.prefix)) {
/* 108 */       e.setCancelled(true);
/* 109 */       String[] args = e.getMessage().trim().substring(1).split(" ");
/* 110 */       Optional<Command> possibleCmd = getCommandByName(args[0]);
/* 111 */       if (possibleCmd.isPresent()) {
/* 112 */         ((Command)possibleCmd.get()).execute(Arrays.<String>copyOfRange(args, 1, args.length));
/*     */       } else {
/* 114 */         Helper.sendMessage("Command not found Try '" + Client.instance.prefix + "help'");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */