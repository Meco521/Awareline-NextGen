/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
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
/*     */ public class CommandFill
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  24 */     return "fill";
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
/*  40 */     return "commands.fill.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  48 */     if (args.length < 7)
/*     */     {
/*  50 */       throw new WrongUsageException("commands.fill.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  54 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  55 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  56 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  57 */     Block block = CommandBase.getBlockByText(sender, args[6]);
/*  58 */     int i = 0;
/*     */     
/*  60 */     if (args.length >= 8)
/*     */     {
/*  62 */       i = parseInt(args[7], 0, 15);
/*     */     }
/*     */     
/*  65 */     BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), Math.min(blockpos.getY(), blockpos1.getY()), Math.min(blockpos.getZ(), blockpos1.getZ()));
/*  66 */     BlockPos blockpos3 = new BlockPos(Math.max(blockpos.getX(), blockpos1.getX()), Math.max(blockpos.getY(), blockpos1.getY()), Math.max(blockpos.getZ(), blockpos1.getZ()));
/*  67 */     int j = (blockpos3.getX() - blockpos2.getX() + 1) * (blockpos3.getY() - blockpos2.getY() + 1) * (blockpos3.getZ() - blockpos2.getZ() + 1);
/*     */     
/*  69 */     if (j > 32768)
/*     */     {
/*  71 */       throw new CommandException("commands.fill.tooManyBlocks", new Object[] { Integer.valueOf(j), Integer.valueOf(32768) });
/*     */     }
/*  73 */     if (blockpos2.getY() >= 0 && blockpos3.getY() < 256) {
/*     */       
/*  75 */       World world = sender.getEntityWorld();
/*     */       
/*  77 */       for (int k = blockpos2.getZ(); k < blockpos3.getZ() + 16; k += 16) {
/*     */         
/*  79 */         for (int l = blockpos2.getX(); l < blockpos3.getX() + 16; l += 16) {
/*     */           
/*  81 */           if (!world.isBlockLoaded(new BlockPos(l, blockpos3.getY() - blockpos2.getY(), k)))
/*     */           {
/*  83 */             throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  88 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  89 */       boolean flag = false;
/*     */       
/*  91 */       if (args.length >= 10 && block.hasTileEntity()) {
/*     */         
/*  93 */         String s = getChatComponentFromNthArg(sender, args, 9).getUnformattedText();
/*     */ 
/*     */         
/*     */         try {
/*  97 */           nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  98 */           flag = true;
/*     */         }
/* 100 */         catch (NBTException nbtexception) {
/*     */           
/* 102 */           throw new CommandException("commands.fill.tagError", new Object[] { nbtexception.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/* 106 */       List<BlockPos> list = Lists.newArrayList();
/* 107 */       j = 0;
/*     */       
/* 109 */       for (int i1 = blockpos2.getZ(); i1 <= blockpos3.getZ(); i1++) {
/*     */         
/* 111 */         for (int j1 = blockpos2.getY(); j1 <= blockpos3.getY(); j1++) {
/*     */           
/* 113 */           for (int k1 = blockpos2.getX(); k1 <= blockpos3.getX(); k1++) {
/*     */             
/* 115 */             BlockPos blockpos4 = new BlockPos(k1, j1, i1);
/*     */             
/* 117 */             if (args.length >= 9)
/*     */             {
/* 119 */               if (!args[8].equals("outline") && !args[8].equals("hollow")) {
/*     */                 
/* 121 */                 if (args[8].equals("destroy"))
/*     */                 {
/* 123 */                   world.destroyBlock(blockpos4, true);
/*     */                 }
/* 125 */                 else if (args[8].equals("keep"))
/*     */                 {
/* 127 */                   if (!world.isAirBlock(blockpos4))
/*     */                   {
/*     */                     continue;
/*     */                   }
/*     */                 }
/* 132 */                 else if (args[8].equals("replace") && !block.hasTileEntity())
/*     */                 {
/* 134 */                   if (args.length > 9) {
/*     */                     
/* 136 */                     Block block1 = CommandBase.getBlockByText(sender, args[9]);
/*     */                     
/* 138 */                     if (world.getBlockState(blockpos4).getBlock() != block1) {
/*     */                       continue;
/*     */                     }
/*     */                   } 
/*     */ 
/*     */                   
/* 144 */                   if (args.length > 10)
/*     */                   {
/* 146 */                     int l1 = CommandBase.parseInt(args[10]);
/* 147 */                     IBlockState iblockstate = world.getBlockState(blockpos4);
/*     */                     
/* 149 */                     if (iblockstate.getBlock().getMetaFromState(iblockstate) != l1) {
/*     */                       continue;
/*     */                     }
/*     */                   }
/*     */                 
/*     */                 }
/*     */               
/* 156 */               } else if (k1 != blockpos2.getX() && k1 != blockpos3.getX() && j1 != blockpos2.getY() && j1 != blockpos3.getY() && i1 != blockpos2.getZ() && i1 != blockpos3.getZ()) {
/*     */                 
/* 158 */                 if (args[8].equals("hollow")) {
/*     */                   
/* 160 */                   world.setBlockState(blockpos4, Blocks.air.getDefaultState(), 2);
/* 161 */                   list.add(blockpos4);
/*     */                 } 
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */             }
/*     */             
/* 168 */             TileEntity tileentity1 = world.getTileEntity(blockpos4);
/*     */             
/* 170 */             if (tileentity1 != null) {
/*     */               
/* 172 */               if (tileentity1 instanceof IInventory)
/*     */               {
/* 174 */                 ((IInventory)tileentity1).clear();
/*     */               }
/*     */               
/* 177 */               world.setBlockState(blockpos4, Blocks.barrier.getDefaultState(), (block == Blocks.barrier) ? 2 : 4);
/*     */             } 
/*     */             
/* 180 */             IBlockState iblockstate1 = block.getStateFromMeta(i);
/*     */             
/* 182 */             if (world.setBlockState(blockpos4, iblockstate1, 2)) {
/*     */               
/* 184 */               list.add(blockpos4);
/* 185 */               j++;
/*     */               
/* 187 */               if (flag) {
/*     */                 
/* 189 */                 TileEntity tileentity = world.getTileEntity(blockpos4);
/*     */                 
/* 191 */                 if (tileentity != null) {
/*     */                   
/* 193 */                   nbttagcompound.setInteger("x", blockpos4.getX());
/* 194 */                   nbttagcompound.setInteger("y", blockpos4.getY());
/* 195 */                   nbttagcompound.setInteger("z", blockpos4.getZ());
/* 196 */                   tileentity.readFromNBT(nbttagcompound);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 204 */       for (BlockPos blockpos5 : list) {
/*     */         
/* 206 */         Block block2 = world.getBlockState(blockpos5).getBlock();
/* 207 */         world.notifyNeighborsRespectDebug(blockpos5, block2);
/*     */       } 
/*     */       
/* 210 */       if (j <= 0)
/*     */       {
/* 212 */         throw new CommandException("commands.fill.failed", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 216 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, j);
/* 217 */       notifyOperators(sender, this, "commands.fill.success", new Object[] { Integer.valueOf(j) });
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 222 */       throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 229 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length == 7) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : ((args.length == 9) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "destroy", "keep", "hollow", "outline" }) : ((args.length == 10 && "replace".equals(args[8])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null))));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */