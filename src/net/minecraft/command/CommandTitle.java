/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S45PacketTitle;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentProcessor;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandTitle extends CommandBase {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  25 */     return "title";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  33 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  41 */     return "commands.title.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  49 */     if (args.length < 2)
/*     */     {
/*  51 */       throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  55 */     if (args.length < 3) {
/*     */       
/*  57 */       if ("title".equals(args[1]) || "subtitle".equals(args[1]))
/*     */       {
/*  59 */         throw new WrongUsageException("commands.title.usage.title", new Object[0]);
/*     */       }
/*     */       
/*  62 */       if ("times".equals(args[1]))
/*     */       {
/*  64 */         throw new WrongUsageException("commands.title.usage.times", new Object[0]);
/*     */       }
/*     */     } 
/*     */     
/*  68 */     EntityPlayerMP entityplayermp = getPlayer(sender, args[0]);
/*  69 */     S45PacketTitle.Type s45packettitle$type = S45PacketTitle.Type.byName(args[1]);
/*     */     
/*  71 */     if (s45packettitle$type != S45PacketTitle.Type.CLEAR && s45packettitle$type != S45PacketTitle.Type.RESET) {
/*     */       
/*  73 */       if (s45packettitle$type == S45PacketTitle.Type.TIMES) {
/*     */         
/*  75 */         if (args.length != 5)
/*     */         {
/*  77 */           throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/*  81 */         int i = parseInt(args[2]);
/*  82 */         int j = parseInt(args[3]);
/*  83 */         int k = parseInt(args[4]);
/*  84 */         S45PacketTitle s45packettitle2 = new S45PacketTitle(i, j, k);
/*  85 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle2);
/*  86 */         notifyOperators(sender, this, "commands.title.success", new Object[0]);
/*     */       } else {
/*     */         IChatComponent ichatcomponent;
/*  89 */         if (args.length < 3)
/*     */         {
/*  91 */           throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/*  95 */         String s = buildString(args, 2);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 100 */           ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/*     */         }
/* 102 */         catch (JsonParseException jsonparseexception) {
/*     */           
/* 104 */           Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
/* 105 */           throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
/*     */         } 
/*     */         
/* 108 */         S45PacketTitle s45packettitle1 = new S45PacketTitle(s45packettitle$type, ChatComponentProcessor.processComponent(sender, ichatcomponent, (Entity)entityplayermp));
/* 109 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle1);
/* 110 */         notifyOperators(sender, this, "commands.title.success", new Object[0]);
/*     */       } 
/*     */     } else {
/* 113 */       if (args.length != 2)
/*     */       {
/* 115 */         throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 119 */       S45PacketTitle s45packettitle = new S45PacketTitle(s45packettitle$type, (IChatComponent)null);
/* 120 */       entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle);
/* 121 */       notifyOperators(sender, this, "commands.title.success", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 128 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, S45PacketTitle.Type.getNames()) : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 136 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */