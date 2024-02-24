/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandWorldBorder
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  18 */     return "worldborder";
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
/*  34 */     return "commands.worldborder.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  42 */     if (args.length < 1)
/*     */     {
/*  44 */       throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  48 */     WorldBorder worldborder = getWorldBorder();
/*     */     
/*  50 */     if (args[0].equals("set")) {
/*     */       
/*  52 */       if (args.length != 2 && args.length != 3)
/*     */       {
/*  54 */         throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
/*     */       }
/*     */       
/*  57 */       double d0 = worldborder.getTargetSize();
/*  58 */       double d2 = parseDouble(args[1], 1.0D, 6.0E7D);
/*  59 */       long i = (args.length > 2) ? (parseLong(args[2], 0L, 9223372036854775L) * 1000L) : 0L;
/*     */       
/*  61 */       if (i > 0L) {
/*     */         
/*  63 */         worldborder.setTransition(d0, d2, i);
/*     */         
/*  65 */         if (d0 > d2)
/*     */         {
/*  67 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }), Long.toString(i / 1000L) });
/*     */         }
/*     */         else
/*     */         {
/*  71 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }), Long.toString(i / 1000L) });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  76 */         worldborder.setTransition(d2);
/*  77 */         notifyOperators(sender, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }) });
/*     */       }
/*     */     
/*  80 */     } else if (args[0].equals("add")) {
/*     */       
/*  82 */       if (args.length != 2 && args.length != 3)
/*     */       {
/*  84 */         throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  87 */       double d4 = worldborder.getDiameter();
/*  88 */       double d8 = d4 + parseDouble(args[1], -d4, 6.0E7D - d4);
/*  89 */       long i1 = worldborder.getTimeUntilTarget() + ((args.length > 2) ? (parseLong(args[2], 0L, 9223372036854775L) * 1000L) : 0L);
/*     */       
/*  91 */       if (i1 > 0L) {
/*     */         
/*  93 */         worldborder.setTransition(d4, d8, i1);
/*     */         
/*  95 */         if (d4 > d8)
/*     */         {
/*  97 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }), Long.toString(i1 / 1000L) });
/*     */         }
/*     */         else
/*     */         {
/* 101 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }), Long.toString(i1 / 1000L) });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 106 */         worldborder.setTransition(d8);
/* 107 */         notifyOperators(sender, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }) });
/*     */       }
/*     */     
/* 110 */     } else if (args[0].equals("center")) {
/*     */       
/* 112 */       if (args.length != 3)
/*     */       {
/* 114 */         throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
/*     */       }
/*     */       
/* 117 */       BlockPos blockpos = sender.getPosition();
/* 118 */       double d1 = parseDouble(blockpos.getX() + 0.5D, args[1], true);
/* 119 */       double d3 = parseDouble(blockpos.getZ() + 0.5D, args[2], true);
/* 120 */       worldborder.setCenter(d1, d3);
/* 121 */       notifyOperators(sender, this, "commands.worldborder.center.success", new Object[] { Double.valueOf(d1), Double.valueOf(d3) });
/*     */     }
/* 123 */     else if (args[0].equals("damage")) {
/*     */       
/* 125 */       if (args.length < 2)
/*     */       {
/* 127 */         throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
/*     */       }
/*     */       
/* 130 */       if (args[1].equals("buffer"))
/*     */       {
/* 132 */         if (args.length != 3)
/*     */         {
/* 134 */           throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
/*     */         }
/*     */         
/* 137 */         double d5 = parseDouble(args[2], 0.0D);
/* 138 */         double d9 = worldborder.getDamageBuffer();
/* 139 */         worldborder.setDamageBuffer(d5);
/* 140 */         notifyOperators(sender, this, "commands.worldborder.damage.buffer.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d5) }), String.format("%.1f", new Object[] { Double.valueOf(d9) }) });
/*     */       }
/* 142 */       else if (args[1].equals("amount"))
/*     */       {
/* 144 */         if (args.length != 3)
/*     */         {
/* 146 */           throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
/*     */         }
/*     */         
/* 149 */         double d6 = parseDouble(args[2], 0.0D);
/* 150 */         double d10 = worldborder.getDamageAmount();
/* 151 */         worldborder.setDamageAmount(d6);
/* 152 */         notifyOperators(sender, this, "commands.worldborder.damage.amount.success", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d6) }), String.format("%.2f", new Object[] { Double.valueOf(d10) }) });
/*     */       }
/*     */     
/* 155 */     } else if (args[0].equals("warning")) {
/*     */       
/* 157 */       if (args.length < 2)
/*     */       {
/* 159 */         throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
/*     */       }
/*     */       
/* 162 */       int j = parseInt(args[2], 0);
/*     */       
/* 164 */       if (args[1].equals("time"))
/*     */       {
/* 166 */         if (args.length != 3)
/*     */         {
/* 168 */           throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
/*     */         }
/*     */         
/* 171 */         int k = worldborder.getWarningTime();
/* 172 */         worldborder.setWarningTime(j);
/* 173 */         notifyOperators(sender, this, "commands.worldborder.warning.time.success", new Object[] { Integer.valueOf(j), Integer.valueOf(k) });
/*     */       }
/* 175 */       else if (args[1].equals("distance"))
/*     */       {
/* 177 */         if (args.length != 3)
/*     */         {
/* 179 */           throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
/*     */         }
/*     */         
/* 182 */         int l = worldborder.getWarningDistance();
/* 183 */         worldborder.setWarningDistance(j);
/* 184 */         notifyOperators(sender, this, "commands.worldborder.warning.distance.success", new Object[] { Integer.valueOf(j), Integer.valueOf(l) });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 189 */       if (!args[0].equals("get"))
/*     */       {
/* 191 */         throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
/*     */       }
/*     */       
/* 194 */       double d7 = worldborder.getDiameter();
/* 195 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, MathHelper.floor_double(d7 + 0.5D));
/* 196 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.worldborder.get.success", new Object[] { String.format("%.0f", new Object[] { Double.valueOf(d7) }) }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldBorder getWorldBorder() {
/* 203 */     return (MinecraftServer.getServer()).worldServers[0].getWorldBorder();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 208 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "set", "center", "damage", "warning", "add", "get" }) : ((args.length == 2 && args[0].equals("damage")) ? getListOfStringsMatchingLastWord(args, new String[] { "buffer", "amount" }) : ((args.length >= 2 && args.length <= 3 && args[0].equals("center")) ? func_181043_b(args, 1, pos) : ((args.length == 2 && args[0].equals("warning")) ? getListOfStringsMatchingLastWord(args, new String[] { "time", "distance" }) : null)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandWorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */