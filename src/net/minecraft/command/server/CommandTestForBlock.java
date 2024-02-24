/*     */ package net.minecraft.command.server;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.NumberInvalidException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandTestForBlock extends CommandBase {
/*     */   public String getCommandName() {
/*  23 */     return "testforblock";
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
/*  39 */     return "commands.testforblock.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  47 */     if (args.length < 4)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  53 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  54 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  55 */     Block block = Block.getBlockFromName(args[3]);
/*     */     
/*  57 */     if (block == null)
/*     */     {
/*  59 */       throw new NumberInvalidException("commands.setblock.notFound", new Object[] { args[3] });
/*     */     }
/*     */ 
/*     */     
/*  63 */     int i = -1;
/*     */     
/*  65 */     if (args.length >= 5)
/*     */     {
/*  67 */       i = parseInt(args[4], -1, 15);
/*     */     }
/*     */     
/*  70 */     World world = sender.getEntityWorld();
/*     */     
/*  72 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  74 */       throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  78 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  79 */     boolean flag = false;
/*     */     
/*  81 */     if (args.length >= 6 && block.hasTileEntity()) {
/*     */       
/*  83 */       String s = getChatComponentFromNthArg(sender, args, 5).getUnformattedText();
/*     */ 
/*     */       
/*     */       try {
/*  87 */         nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  88 */         flag = true;
/*     */       }
/*  90 */       catch (NBTException nbtexception) {
/*     */         
/*  92 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     IBlockState iblockstate = world.getBlockState(blockpos);
/*  97 */     Block block1 = iblockstate.getBlock();
/*     */     
/*  99 */     if (block1 != block)
/*     */     {
/* 101 */       throw new CommandException("commands.testforblock.failed.tile", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), block1.getLocalizedName(), block.getLocalizedName() });
/*     */     }
/*     */ 
/*     */     
/* 105 */     if (i > -1) {
/*     */       
/* 107 */       int j = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */       
/* 109 */       if (j != i)
/*     */       {
/* 111 */         throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), Integer.valueOf(j), Integer.valueOf(i) });
/*     */       }
/*     */     } 
/*     */     
/* 115 */     if (flag) {
/*     */       
/* 117 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 119 */       if (tileentity == null)
/*     */       {
/* 121 */         throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 124 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 125 */       tileentity.writeToNBT(nbttagcompound1);
/*     */       
/* 127 */       if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*     */       {
/* 129 */         throw new CommandException("commands.testforblock.failed.nbt", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */     } 
/*     */     
/* 133 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 134 */     notifyOperators(sender, (ICommand)this, "commands.testforblock.success", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 143 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length == 4) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandTestForBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */