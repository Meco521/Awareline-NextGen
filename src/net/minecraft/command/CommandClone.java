/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandClone
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  25 */     return "clone";
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
/*  41 */     return "commands.clone.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  49 */     if (args.length < 9)
/*     */     {
/*  51 */       throw new WrongUsageException("commands.clone.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  55 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  56 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  57 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  58 */     BlockPos blockpos2 = parseBlockPos(sender, args, 6, false);
/*  59 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos1);
/*  60 */     StructureBoundingBox structureboundingbox1 = new StructureBoundingBox((Vec3i)blockpos2, (Vec3i)blockpos2.add(structureboundingbox.func_175896_b()));
/*  61 */     int i = structureboundingbox.getXSize() * structureboundingbox.getYSize() * structureboundingbox.getZSize();
/*     */     
/*  63 */     if (i > 32768)
/*     */     {
/*  65 */       throw new CommandException("commands.clone.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(32768) });
/*     */     }
/*     */ 
/*     */     
/*  69 */     boolean flag = false;
/*  70 */     Block block = null;
/*  71 */     int j = -1;
/*     */     
/*  73 */     if ((args.length < 11 || (!args[10].equals("force") && !args[10].equals("move"))) && structureboundingbox.intersectsWith(structureboundingbox1))
/*     */     {
/*  75 */       throw new CommandException("commands.clone.noOverlap", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  79 */     if (args.length >= 11 && args[10].equals("move"))
/*     */     {
/*  81 */       flag = true;
/*     */     }
/*     */     
/*  84 */     if (structureboundingbox.minY >= 0 && structureboundingbox.maxY < 256 && structureboundingbox1.minY >= 0 && structureboundingbox1.maxY < 256) {
/*     */       
/*  86 */       World world = sender.getEntityWorld();
/*     */       
/*  88 */       if (world.isAreaLoaded(structureboundingbox) && world.isAreaLoaded(structureboundingbox1))
/*     */       {
/*  90 */         boolean flag1 = false;
/*     */         
/*  92 */         if (args.length >= 10)
/*     */         {
/*  94 */           if (args[9].equals("masked")) {
/*     */             
/*  96 */             flag1 = true;
/*     */           }
/*  98 */           else if (args[9].equals("filtered")) {
/*     */             
/* 100 */             if (args.length < 12)
/*     */             {
/* 102 */               throw new WrongUsageException("commands.clone.usage", new Object[0]);
/*     */             }
/*     */             
/* 105 */             block = getBlockByText(sender, args[11]);
/*     */             
/* 107 */             if (args.length >= 13)
/*     */             {
/* 109 */               j = parseInt(args[12], 0, 15);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 114 */         List<StaticCloneData> list = Lists.newArrayList();
/* 115 */         List<StaticCloneData> list1 = Lists.newArrayList();
/* 116 */         List<StaticCloneData> list2 = Lists.newArrayList();
/* 117 */         LinkedList<BlockPos> linkedlist = Lists.newLinkedList();
/* 118 */         BlockPos blockpos3 = new BlockPos(structureboundingbox1.minX - structureboundingbox.minX, structureboundingbox1.minY - structureboundingbox.minY, structureboundingbox1.minZ - structureboundingbox.minZ);
/*     */         
/* 120 */         for (int k = structureboundingbox.minZ; k <= structureboundingbox.maxZ; k++) {
/*     */           
/* 122 */           for (int l = structureboundingbox.minY; l <= structureboundingbox.maxY; l++) {
/*     */             
/* 124 */             for (int i1 = structureboundingbox.minX; i1 <= structureboundingbox.maxX; i1++) {
/*     */               
/* 126 */               BlockPos blockpos4 = new BlockPos(i1, l, k);
/* 127 */               BlockPos blockpos5 = blockpos4.add((Vec3i)blockpos3);
/* 128 */               IBlockState iblockstate = world.getBlockState(blockpos4);
/*     */               
/* 130 */               if ((!flag1 || iblockstate.getBlock() != Blocks.air) && (block == null || (iblockstate.getBlock() == block && (j < 0 || iblockstate.getBlock().getMetaFromState(iblockstate) == j)))) {
/*     */                 
/* 132 */                 TileEntity tileentity = world.getTileEntity(blockpos4);
/*     */                 
/* 134 */                 if (tileentity != null) {
/*     */                   
/* 136 */                   NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 137 */                   tileentity.writeToNBT(nbttagcompound);
/* 138 */                   list1.add(new StaticCloneData(blockpos5, iblockstate, nbttagcompound));
/* 139 */                   linkedlist.addLast(blockpos4);
/*     */                 }
/* 141 */                 else if (!iblockstate.getBlock().isFullBlock() && !iblockstate.getBlock().isFullCube()) {
/*     */                   
/* 143 */                   list2.add(new StaticCloneData(blockpos5, iblockstate, (NBTTagCompound)null));
/* 144 */                   linkedlist.addFirst(blockpos4);
/*     */                 }
/*     */                 else {
/*     */                   
/* 148 */                   list.add(new StaticCloneData(blockpos5, iblockstate, (NBTTagCompound)null));
/* 149 */                   linkedlist.addLast(blockpos4);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 156 */         if (flag) {
/*     */           
/* 158 */           for (BlockPos blockpos6 : linkedlist) {
/*     */             
/* 160 */             TileEntity tileentity1 = world.getTileEntity(blockpos6);
/*     */             
/* 162 */             if (tileentity1 instanceof IInventory)
/*     */             {
/* 164 */               ((IInventory)tileentity1).clear();
/*     */             }
/*     */             
/* 167 */             world.setBlockState(blockpos6, Blocks.barrier.getDefaultState(), 2);
/*     */           } 
/*     */           
/* 170 */           for (BlockPos blockpos7 : linkedlist)
/*     */           {
/* 172 */             world.setBlockState(blockpos7, Blocks.air.getDefaultState(), 3);
/*     */           }
/*     */         } 
/*     */         
/* 176 */         List<StaticCloneData> list3 = Lists.newArrayList();
/* 177 */         list3.addAll(list);
/* 178 */         list3.addAll(list1);
/* 179 */         list3.addAll(list2);
/* 180 */         List<StaticCloneData> list4 = Lists.reverse(list3);
/*     */         
/* 182 */         for (StaticCloneData commandclone$staticclonedata : list4) {
/*     */           
/* 184 */           TileEntity tileentity2 = world.getTileEntity(commandclone$staticclonedata.pos);
/*     */           
/* 186 */           if (tileentity2 instanceof IInventory)
/*     */           {
/* 188 */             ((IInventory)tileentity2).clear();
/*     */           }
/*     */           
/* 191 */           world.setBlockState(commandclone$staticclonedata.pos, Blocks.barrier.getDefaultState(), 2);
/*     */         } 
/*     */         
/* 194 */         i = 0;
/*     */         
/* 196 */         for (StaticCloneData commandclone$staticclonedata1 : list3) {
/*     */           
/* 198 */           if (world.setBlockState(commandclone$staticclonedata1.pos, commandclone$staticclonedata1.blockState, 2))
/*     */           {
/* 200 */             i++;
/*     */           }
/*     */         } 
/*     */         
/* 204 */         for (StaticCloneData commandclone$staticclonedata2 : list1) {
/*     */           
/* 206 */           TileEntity tileentity3 = world.getTileEntity(commandclone$staticclonedata2.pos);
/*     */           
/* 208 */           if (commandclone$staticclonedata2.compound != null && tileentity3 != null) {
/*     */             
/* 210 */             commandclone$staticclonedata2.compound.setInteger("x", commandclone$staticclonedata2.pos.getX());
/* 211 */             commandclone$staticclonedata2.compound.setInteger("y", commandclone$staticclonedata2.pos.getY());
/* 212 */             commandclone$staticclonedata2.compound.setInteger("z", commandclone$staticclonedata2.pos.getZ());
/* 213 */             tileentity3.readFromNBT(commandclone$staticclonedata2.compound);
/* 214 */             tileentity3.markDirty();
/*     */           } 
/*     */           
/* 217 */           world.setBlockState(commandclone$staticclonedata2.pos, commandclone$staticclonedata2.blockState, 2);
/*     */         } 
/*     */         
/* 220 */         for (StaticCloneData commandclone$staticclonedata3 : list4)
/*     */         {
/* 222 */           world.notifyNeighborsRespectDebug(commandclone$staticclonedata3.pos, commandclone$staticclonedata3.blockState.getBlock());
/*     */         }
/*     */         
/* 225 */         List<NextTickListEntry> list5 = world.func_175712_a(structureboundingbox, false);
/*     */         
/* 227 */         if (list5 != null)
/*     */         {
/* 229 */           for (NextTickListEntry nextticklistentry : list5) {
/*     */             
/* 231 */             if (structureboundingbox.isVecInside((Vec3i)nextticklistentry.position)) {
/*     */               
/* 233 */               BlockPos blockpos8 = nextticklistentry.position.add((Vec3i)blockpos3);
/* 234 */               world.scheduleBlockUpdate(blockpos8, nextticklistentry.getBlock(), (int)(nextticklistentry.scheduledTime - world.getWorldInfo().getWorldTotalTime()), nextticklistentry.priority);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 239 */         if (i <= 0)
/*     */         {
/* 241 */           throw new CommandException("commands.clone.failed", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/* 245 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 246 */         notifyOperators(sender, this, "commands.clone.success", new Object[] { Integer.valueOf(i) });
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 251 */         throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 256 */       throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 265 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length > 6 && args.length <= 9) ? func_175771_a(args, 6, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "masked", "filtered" }) : ((args.length == 11) ? getListOfStringsMatchingLastWord(args, new String[] { "normal", "force", "move" }) : ((args.length == 12 && "filtered".equals(args[9])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null)))));
/*     */   }
/*     */ 
/*     */   
/*     */   static class StaticCloneData
/*     */   {
/*     */     public final BlockPos pos;
/*     */     public final IBlockState blockState;
/*     */     public final NBTTagCompound compound;
/*     */     
/*     */     public StaticCloneData(BlockPos posIn, IBlockState stateIn, NBTTagCompound compoundIn) {
/* 276 */       this.pos = posIn;
/* 277 */       this.blockState = stateIn;
/* 278 */       this.compound = compoundIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandClone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */