/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandSetBlock
/*     */   extends CommandBase {
/*     */   public String getCommandName() {
/*  24 */     return "setblock";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  32 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  40 */     return "commands.setblock.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  48 */     if (args.length < 4)
/*     */     {
/*  50 */       throw new WrongUsageException("commands.setblock.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  54 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  55 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  56 */     Block block = CommandBase.getBlockByText(sender, args[3]);
/*  57 */     int i = 0;
/*     */     
/*  59 */     if (args.length >= 5)
/*     */     {
/*  61 */       i = parseInt(args[4], 0, 15);
/*     */     }
/*     */     
/*  64 */     World world = sender.getEntityWorld();
/*     */     
/*  66 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  68 */       throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  72 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  73 */     boolean flag = false;
/*     */     
/*  75 */     if (args.length >= 7 && block.hasTileEntity()) {
/*     */       
/*  77 */       String s = getChatComponentFromNthArg(sender, args, 6).getUnformattedText();
/*     */ 
/*     */       
/*     */       try {
/*  81 */         nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  82 */         flag = true;
/*     */       }
/*  84 */       catch (NBTException nbtexception) {
/*     */         
/*  86 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     if (args.length >= 6)
/*     */     {
/*  92 */       if (args[5].equals("destroy")) {
/*     */         
/*  94 */         world.destroyBlock(blockpos, true);
/*     */         
/*  96 */         if (block == Blocks.air) {
/*     */           
/*  98 */           notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */           
/*     */           return;
/*     */         } 
/* 102 */       } else if (args[5].equals("keep") && !world.isAirBlock(blockpos)) {
/*     */         
/* 104 */         throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */       } 
/*     */     }
/*     */     
/* 108 */     TileEntity tileentity1 = world.getTileEntity(blockpos);
/*     */     
/* 110 */     if (tileentity1 != null) {
/*     */       
/* 112 */       if (tileentity1 instanceof IInventory)
/*     */       {
/* 114 */         ((IInventory)tileentity1).clear();
/*     */       }
/*     */       
/* 117 */       world.setBlockState(blockpos, Blocks.air.getDefaultState(), (block == Blocks.air) ? 2 : 4);
/*     */     } 
/*     */     
/* 120 */     IBlockState iblockstate = block.getStateFromMeta(i);
/*     */     
/* 122 */     if (!world.setBlockState(blockpos, iblockstate, 2))
/*     */     {
/* 124 */       throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 128 */     if (flag) {
/*     */       
/* 130 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 132 */       if (tileentity != null) {
/*     */         
/* 134 */         nbttagcompound.setInteger("x", blockpos.getX());
/* 135 */         nbttagcompound.setInteger("y", blockpos.getY());
/* 136 */         nbttagcompound.setInteger("z", blockpos.getZ());
/* 137 */         tileentity.readFromNBT(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     world.notifyNeighborsRespectDebug(blockpos, iblockstate.getBlock());
/* 142 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 143 */     notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 151 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length == 4) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : ((args.length == 6) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "destroy", "keep" }) : null));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\server\CommandSetBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */