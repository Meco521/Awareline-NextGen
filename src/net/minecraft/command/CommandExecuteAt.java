/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandExecuteAt
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "execute";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  37 */     return "commands.execute.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(final ICommandSender sender, String[] args) throws CommandException {
/*  45 */     if (args.length < 5)
/*     */     {
/*  47 */       throw new WrongUsageException("commands.execute.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  51 */     final Entity entity = getEntity(sender, args[0], Entity.class);
/*  52 */     final double d0 = parseDouble(entity.posX, args[1], false);
/*  53 */     final double d1 = parseDouble(entity.posY, args[2], false);
/*  54 */     final double d2 = parseDouble(entity.posZ, args[3], false);
/*  55 */     final BlockPos blockpos = new BlockPos(d0, d1, d2);
/*  56 */     int i = 4;
/*     */     
/*  58 */     if ("detect".equals(args[4]) && args.length > 10) {
/*     */       
/*  60 */       World world = entity.getEntityWorld();
/*  61 */       double d3 = parseDouble(d0, args[5], false);
/*  62 */       double d4 = parseDouble(d1, args[6], false);
/*  63 */       double d5 = parseDouble(d2, args[7], false);
/*  64 */       Block block = getBlockByText(sender, args[8]);
/*  65 */       int k = parseInt(args[9], -1, 15);
/*  66 */       BlockPos blockpos1 = new BlockPos(d3, d4, d5);
/*  67 */       IBlockState iblockstate = world.getBlockState(blockpos1);
/*     */       
/*  69 */       if (iblockstate.getBlock() != block || (k >= 0 && iblockstate.getBlock().getMetaFromState(iblockstate) != k))
/*     */       {
/*  71 */         throw new CommandException("commands.execute.failed", new Object[] { "detect", entity.getName() });
/*     */       }
/*     */       
/*  74 */       i = 10;
/*     */     } 
/*     */     
/*  77 */     String s = buildString(args, i);
/*  78 */     ICommandSender icommandsender = new ICommandSender()
/*     */       {
/*     */         public String getName()
/*     */         {
/*  82 */           return entity.getName();
/*     */         }
/*     */         
/*     */         public IChatComponent getDisplayName() {
/*  86 */           return entity.getDisplayName();
/*     */         }
/*     */         
/*     */         public void addChatMessage(IChatComponent component) {
/*  90 */           sender.addChatMessage(component);
/*     */         }
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  94 */           return sender.canCommandSenderUseCommand(permLevel, commandName);
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/*  98 */           return blockpos;
/*     */         }
/*     */         
/*     */         public Vec3 getPositionVector() {
/* 102 */           return new Vec3(d0, d1, d2);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/* 106 */           return entity.worldObj;
/*     */         }
/*     */         
/*     */         public Entity getCommandSenderEntity() {
/* 110 */           return entity;
/*     */         }
/*     */         
/*     */         public boolean sendCommandFeedback() {
/* 114 */           MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 115 */           return (minecraftserver == null || minecraftserver.worldServers[0].getGameRules().getBoolean("commandBlockOutput"));
/*     */         }
/*     */         
/*     */         public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 119 */           entity.setCommandStat(type, amount);
/*     */         }
/*     */       };
/* 122 */     ICommandManager icommandmanager = MinecraftServer.getServer().getCommandManager();
/*     */ 
/*     */     
/*     */     try {
/* 126 */       int j = icommandmanager.executeCommand(icommandsender, s);
/*     */       
/* 128 */       if (j < 1)
/*     */       {
/* 130 */         throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { s });
/*     */       }
/*     */     }
/* 133 */     catch (Throwable var23) {
/*     */       
/* 135 */       throw new CommandException("commands.execute.failed", new Object[] { s, entity.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 142 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : ((args.length > 5 && args.length <= 8 && "detect".equals(args[4])) ? func_175771_a(args, 5, pos) : ((args.length == 9 && "detect".equals(args[4])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 150 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandExecuteAt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */