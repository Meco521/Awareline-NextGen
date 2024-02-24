/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandStats
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "stats";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  39 */     return "commands.stats.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     boolean flag;
/*     */     int i;
/*     */     CommandResultStats commandresultstats;
/*  47 */     if (args.length < 1)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     if (args[0].equals("entity")) {
/*     */       
/*  57 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/*  61 */       if (!args[0].equals("block"))
/*     */       {
/*  63 */         throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */       }
/*     */       
/*  66 */       flag = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (flag) {
/*     */       
/*  73 */       if (args.length < 5)
/*     */       {
/*  75 */         throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
/*     */       }
/*     */       
/*  78 */       i = 4;
/*     */     }
/*     */     else {
/*     */       
/*  82 */       if (args.length < 3)
/*     */       {
/*  84 */         throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
/*     */       }
/*     */       
/*  87 */       i = 2;
/*     */     } 
/*     */     
/*  90 */     String s = args[i++];
/*     */     
/*  92 */     if ("set".equals(s)) {
/*     */       
/*  94 */       if (args.length < i + 3)
/*     */       {
/*  96 */         if (i == 5)
/*     */         {
/*  98 */           throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
/*     */         }
/*     */         
/* 101 */         throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 106 */       if (!"clear".equals(s))
/*     */       {
/* 108 */         throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */       }
/*     */       
/* 111 */       if (args.length < i + 1) {
/*     */         
/* 113 */         if (i == 5)
/*     */         {
/* 115 */           throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
/*     */         }
/*     */         
/* 118 */         throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     CommandResultStats.Type commandresultstats$type = CommandResultStats.Type.getTypeByName(args[i++]);
/*     */     
/* 124 */     if (commandresultstats$type == null)
/*     */     {
/* 126 */       throw new CommandException("commands.stats.failed", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 130 */     World world = sender.getEntityWorld();
/*     */ 
/*     */     
/* 133 */     if (flag) {
/*     */       
/* 135 */       BlockPos blockpos = parseBlockPos(sender, args, 1, false);
/* 136 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 138 */       if (tileentity == null)
/*     */       {
/* 140 */         throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 143 */       if (tileentity instanceof TileEntityCommandBlock)
/*     */       {
/* 145 */         commandresultstats = ((TileEntityCommandBlock)tileentity).getCommandResultStats();
/*     */       }
/*     */       else
/*     */       {
/* 149 */         if (!(tileentity instanceof TileEntitySign))
/*     */         {
/* 151 */           throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */         }
/*     */         
/* 154 */         commandresultstats = ((TileEntitySign)tileentity).getStats();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 159 */       Entity entity = getEntity(sender, args[1]);
/* 160 */       commandresultstats = entity.getCommandStats();
/*     */     } 
/*     */     
/* 163 */     if ("set".equals(s)) {
/*     */       
/* 165 */       String s1 = args[i++];
/* 166 */       String s2 = args[i];
/*     */       
/* 168 */       if (s1.isEmpty() || s2.isEmpty())
/*     */       {
/* 170 */         throw new CommandException("commands.stats.failed", new Object[0]);
/*     */       }
/*     */       
/* 173 */       CommandResultStats.setScoreBoardStat(commandresultstats, commandresultstats$type, s1, s2);
/* 174 */       notifyOperators(sender, this, "commands.stats.success", new Object[] { commandresultstats$type.getTypeName(), s2, s1 });
/*     */     }
/* 176 */     else if ("clear".equals(s)) {
/*     */       
/* 178 */       CommandResultStats.setScoreBoardStat(commandresultstats, commandresultstats$type, (String)null, (String)null);
/* 179 */       notifyOperators(sender, this, "commands.stats.cleared", new Object[] { commandresultstats$type.getTypeName() });
/*     */     } 
/*     */     
/* 182 */     if (flag) {
/*     */       
/* 184 */       BlockPos blockpos1 = parseBlockPos(sender, args, 1, false);
/* 185 */       TileEntity tileentity1 = world.getTileEntity(blockpos1);
/* 186 */       tileentity1.markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 194 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "entity", "block" }) : ((args.length == 2 && args[0].equals("entity")) ? getListOfStringsMatchingLastWord(args, func_175776_d()) : ((args.length >= 2 && args.length <= 4 && args[0].equals("block")) ? func_175771_a(args, 1, pos) : (((args.length != 3 || !args[0].equals("entity")) && (args.length != 5 || !args[0].equals("block"))) ? (((args.length != 4 || !args[0].equals("entity")) && (args.length != 6 || !args[0].equals("block"))) ? (((args.length != 6 || !args[0].equals("entity")) && (args.length != 8 || !args[0].equals("block"))) ? null : getListOfStringsMatchingLastWord(args, func_175777_e())) : getListOfStringsMatchingLastWord(args, CommandResultStats.Type.getTypeNames())) : getListOfStringsMatchingLastWord(args, new String[] { "set", "clear" }))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] func_175776_d() {
/* 199 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<String> func_175777_e() {
/* 204 */     Collection<ScoreObjective> collection = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard().getScoreObjectives();
/* 205 */     List<String> list = Lists.newArrayList();
/*     */     
/* 207 */     for (ScoreObjective scoreobjective : collection) {
/*     */       
/* 209 */       if (!scoreobjective.getCriteria().isReadOnly())
/*     */       {
/* 211 */         list.add(scoreobjective.getName());
/*     */       }
/*     */     } 
/*     */     
/* 215 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 223 */     return (args.length > 0 && args[0].equals("entity") && index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */