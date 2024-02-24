/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandPlaySound
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  18 */     return "playsound";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  26 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  34 */     return "commands.playsound.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  42 */     if (args.length < 2)
/*     */     {
/*  44 */       throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  48 */     int i = 0;
/*  49 */     String s = args[i++];
/*  50 */     EntityPlayerMP entityplayermp = getPlayer(sender, args[i++]);
/*  51 */     Vec3 vec3 = sender.getPositionVector();
/*  52 */     double d0 = vec3.xCoord;
/*     */     
/*  54 */     if (args.length > i)
/*     */     {
/*  56 */       d0 = parseDouble(d0, args[i++], true);
/*     */     }
/*     */     
/*  59 */     double d1 = vec3.yCoord;
/*     */     
/*  61 */     if (args.length > i)
/*     */     {
/*  63 */       d1 = parseDouble(d1, args[i++], 0, 0, false);
/*     */     }
/*     */     
/*  66 */     double d2 = vec3.zCoord;
/*     */     
/*  68 */     if (args.length > i)
/*     */     {
/*  70 */       d2 = parseDouble(d2, args[i++], true);
/*     */     }
/*     */     
/*  73 */     double d3 = 1.0D;
/*     */     
/*  75 */     if (args.length > i)
/*     */     {
/*  77 */       d3 = parseDouble(args[i++], 0.0D, 3.4028234663852886E38D);
/*     */     }
/*     */     
/*  80 */     double d4 = 1.0D;
/*     */     
/*  82 */     if (args.length > i)
/*     */     {
/*  84 */       d4 = parseDouble(args[i++], 0.0D, 2.0D);
/*     */     }
/*     */     
/*  87 */     double d5 = 0.0D;
/*     */     
/*  89 */     if (args.length > i)
/*     */     {
/*  91 */       d5 = parseDouble(args[i], 0.0D, 1.0D);
/*     */     }
/*     */     
/*  94 */     double d6 = (d3 > 1.0D) ? (d3 * 16.0D) : 16.0D;
/*  95 */     double d7 = entityplayermp.getDistance(d0, d1, d2);
/*     */     
/*  97 */     if (d7 > d6) {
/*     */       
/*  99 */       if (d5 <= 0.0D)
/*     */       {
/* 101 */         throw new CommandException("commands.playsound.playerTooFar", new Object[] { entityplayermp.getName() });
/*     */       }
/*     */       
/* 104 */       double d8 = d0 - entityplayermp.posX;
/* 105 */       double d9 = d1 - entityplayermp.posY;
/* 106 */       double d10 = d2 - entityplayermp.posZ;
/* 107 */       double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
/*     */       
/* 109 */       if (d11 > 0.0D) {
/*     */         
/* 111 */         d0 = entityplayermp.posX + d8 / d11 * 2.0D;
/* 112 */         d1 = entityplayermp.posY + d9 / d11 * 2.0D;
/* 113 */         d2 = entityplayermp.posZ + d10 / d11 * 2.0D;
/*     */       } 
/*     */       
/* 116 */       d3 = d5;
/*     */     } 
/*     */     
/* 119 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S29PacketSoundEffect(s, d0, d1, d2, (float)d3, (float)d4));
/* 120 */     notifyOperators(sender, this, "commands.playsound.success", new Object[] { s, entityplayermp.getName() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 126 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length > 2 && args.length <= 5) ? func_175771_a(args, 2, pos) : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 134 */     return (index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandPlaySound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */