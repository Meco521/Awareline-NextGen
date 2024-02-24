/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandBlockData
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  19 */     return "blockdata";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  27 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  35 */     return "commands.blockdata.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     NBTTagCompound nbttagcompound2;
/*  43 */     if (args.length < 4)
/*     */     {
/*  45 */       throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  49 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  50 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  51 */     World world = sender.getEntityWorld();
/*     */     
/*  53 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  55 */       throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  59 */     TileEntity tileentity = world.getTileEntity(blockpos);
/*     */     
/*  61 */     if (tileentity == null)
/*     */     {
/*  63 */       throw new CommandException("commands.blockdata.notValid", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  67 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  68 */     tileentity.writeToNBT(nbttagcompound);
/*  69 */     NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  74 */       nbttagcompound2 = JsonToNBT.getTagFromJson(getChatComponentFromNthArg(sender, args, 3).getUnformattedText());
/*     */     }
/*  76 */     catch (NBTException nbtexception) {
/*     */       
/*  78 */       throw new CommandException("commands.blockdata.tagError", new Object[] { nbtexception.getMessage() });
/*     */     } 
/*     */     
/*  81 */     nbttagcompound.merge(nbttagcompound2);
/*  82 */     nbttagcompound.setInteger("x", blockpos.getX());
/*  83 */     nbttagcompound.setInteger("y", blockpos.getY());
/*  84 */     nbttagcompound.setInteger("z", blockpos.getZ());
/*     */     
/*  86 */     if (nbttagcompound.equals(nbttagcompound1))
/*     */     {
/*  88 */       throw new CommandException("commands.blockdata.failed", new Object[] { nbttagcompound.toString() });
/*     */     }
/*     */ 
/*     */     
/*  92 */     tileentity.readFromNBT(nbttagcompound);
/*  93 */     tileentity.markDirty();
/*  94 */     world.markBlockForUpdate(blockpos);
/*  95 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/*  96 */     notifyOperators(sender, this, "commands.blockdata.success", new Object[] { nbttagcompound.toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 105 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandBlockData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */