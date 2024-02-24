/*     */ package awareline.main.mod.manager;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ public class FriendManager
/*     */   implements Manager
/*     */ {
/*     */   public HashMap<String, String> friends;
/*     */   
/*     */   public void init() {
/*  16 */     this.friends = new HashMap<>();
/*  17 */     List<String> friends = FileManager.read("Friends.txt");
/*     */     
/*  19 */     for (String friend : friends) {
/*  20 */       if (friend.contains(":")) {
/*  21 */         String name = friend.split(":")[0];
/*  22 */         String alias = friend.split(":")[1];
/*  23 */         Client.instance.friendManager.friends.put(name, alias); continue;
/*     */       } 
/*  25 */       Client.instance.friendManager.friends.put(friend, friend);
/*     */     } 
/*     */ 
/*     */     
/*  29 */     Client.instance.getCommandManager().add(new Command());
/*     */   }
/*     */   
/*     */   public boolean isFriend(String name) {
/*  33 */     return this.friends.containsKey(name);
/*     */   }
/*     */   
/*     */   public String getAlias(String friends2) {
/*  37 */     return this.friends.get(friends2);
/*     */   }
/*     */   
/*     */   static class Command
/*     */     extends awareline.main.cmd.Command {
/*     */     Command() {
/*  43 */       super("friend", new String[] { "f", "friends", "fr" });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void execute(String[] args) {
/*  52 */       if (args.length >= 3) {
/*  53 */         if (args[0].equalsIgnoreCase("add")) {
/*  54 */           String friends = "";
/*  55 */           friends = friends + String.format("%s:%s%s", new Object[] { args[1], args[2], System.lineSeparator() });
/*  56 */           Client.instance.friendManager.friends.put(args[1], args[2]);
/*  57 */           Helper.sendMessage("> " + String.format("%s has been added as %s", new Object[] { args[1], args[2] }));
/*  58 */           FileManager.save("Friends.txt", friends, true);
/*  59 */         } else if (args[0].equalsIgnoreCase("del")) {
/*  60 */           Client.instance.friendManager.friends.remove(args[1]);
/*  61 */           Helper.sendMessage("> " + String.format("%s has been removed from your friends list", new Object[] { args[1] }));
/*  62 */         } else if (args[0].equalsIgnoreCase("list")) {
/*  63 */           if (!Client.instance.friendManager.friends.isEmpty()) {
/*  64 */             int var5 = 1;
/*     */             
/*  66 */             for (Iterator<String> var4 = Client.instance.friendManager.friends.values().iterator(); var4.hasNext(); var5++) {
/*  67 */               String fr = var4.next();
/*  68 */               Helper.sendMessage("> " + String.format("%s. %s", new Object[] { Integer.valueOf(var5), fr }));
/*     */             } 
/*     */           } else {
/*  71 */             Helper.sendMessage("> get some friends fag lmao");
/*     */           } 
/*     */         } 
/*  74 */       } else if (args.length == 2) {
/*  75 */         if (args[0].equalsIgnoreCase("add")) {
/*  76 */           String friends = "";
/*  77 */           friends = friends + String.format("%s%s", new Object[] { args[1], System.lineSeparator() });
/*  78 */           Client.instance.friendManager.friends.put(args[1], args[1]);
/*  79 */           Helper.sendMessage("> " + String.format("%s has been added as %s", new Object[] { args[1], args[1] }));
/*  80 */           FileManager.save("Friends.txt", friends, true);
/*  81 */         } else if (args[0].equalsIgnoreCase("del")) {
/*  82 */           Client.instance.friendManager.friends.remove(args[1]);
/*  83 */           Helper.sendMessage("> " + String.format("%s has been removed from your friends list", new Object[] { args[1] }));
/*  84 */         } else if (args[0].equalsIgnoreCase("list")) {
/*  85 */           if (!Client.instance.friendManager.friends.isEmpty()) {
/*  86 */             int var5 = 1;
/*     */             
/*  88 */             for (Iterator<String> var4 = Client.instance.friendManager.friends.values().iterator(); var4.hasNext(); var5++) {
/*  89 */               String fr = var4.next();
/*  90 */               Helper.sendMessage("> " + String.format("%s. %s", new Object[] { Integer.valueOf(var5), fr }));
/*     */             } 
/*     */           } else {
/*  93 */             Helper.sendMessage("> you don't have any you lonely fuck");
/*     */           } 
/*     */         } 
/*  96 */       } else if (args.length == 1) {
/*  97 */         if (args[0].equalsIgnoreCase("list")) {
/*  98 */           if (!Client.instance.friendManager.friends.isEmpty()) {
/*  99 */             int var5 = 1;
/*     */             
/* 101 */             for (Iterator<String> var4 = Client.instance.friendManager.friends.values().iterator(); var4.hasNext(); var5++) {
/* 102 */               String fr = var4.next();
/* 103 */               Helper.sendMessage(String.format("%s. %s", new Object[] { Integer.valueOf(var5), fr }));
/*     */             } 
/*     */           } else {
/* 106 */             Helper.sendMessage("you don't have any you lonely fuck");
/*     */           } 
/* 108 */         } else if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("del")) {
/* 109 */           Helper.sendMessage("> Correct usage: " + EnumChatFormatting.GRAY + "Valid \\f add/del <player>");
/*     */         } else {
/* 111 */           Helper.sendMessage("> " + EnumChatFormatting.GRAY + "Please enter a players name");
/*     */         } 
/*     */       } else {
/* 114 */         Helper.sendMessage("> Correct usage: " + EnumChatFormatting.GRAY + "Valid \\f add/del <player>");
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\manager\FriendManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */