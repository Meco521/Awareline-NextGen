/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandCompare
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  20 */     return "testforblocks";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  36 */     return "commands.compare.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  44 */     if (args.length < 9)
/*     */     {
/*  46 */       throw new WrongUsageException("commands.compare.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  50 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  51 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  52 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  53 */     BlockPos blockpos2 = parseBlockPos(sender, args, 6, false);
/*  54 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos1);
/*  55 */     StructureBoundingBox structureboundingbox1 = new StructureBoundingBox((Vec3i)blockpos2, (Vec3i)blockpos2.add(structureboundingbox.func_175896_b()));
/*  56 */     int i = structureboundingbox.getXSize() * structureboundingbox.getYSize() * structureboundingbox.getZSize();
/*     */     
/*  58 */     if (i > 524288)
/*     */     {
/*  60 */       throw new CommandException("commands.compare.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(524288) });
/*     */     }
/*  62 */     if (structureboundingbox.minY >= 0 && structureboundingbox.maxY < 256 && structureboundingbox1.minY >= 0 && structureboundingbox1.maxY < 256) {
/*     */       
/*  64 */       World world = sender.getEntityWorld();
/*     */       
/*  66 */       if (world.isAreaLoaded(structureboundingbox) && world.isAreaLoaded(structureboundingbox1))
/*     */       {
/*  68 */         boolean flag = false;
/*     */         
/*  70 */         if (args.length > 9 && args[9].equals("masked"))
/*     */         {
/*  72 */           flag = true;
/*     */         }
/*     */         
/*  75 */         i = 0;
/*  76 */         BlockPos blockpos3 = new BlockPos(structureboundingbox1.minX - structureboundingbox.minX, structureboundingbox1.minY - structureboundingbox.minY, structureboundingbox1.minZ - structureboundingbox.minZ);
/*  77 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*  78 */         BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */         
/*  80 */         for (int j = structureboundingbox.minZ; j <= structureboundingbox.maxZ; j++) {
/*     */           
/*  82 */           for (int k = structureboundingbox.minY; k <= structureboundingbox.maxY; k++) {
/*     */             
/*  84 */             for (int l = structureboundingbox.minX; l <= structureboundingbox.maxX; l++) {
/*     */               
/*  86 */               blockpos$mutableblockpos.set(l, k, j);
/*  87 */               blockpos$mutableblockpos1.set(l + blockpos3.getX(), k + blockpos3.getY(), j + blockpos3.getZ());
/*  88 */               boolean flag1 = false;
/*  89 */               IBlockState iblockstate = world.getBlockState((BlockPos)blockpos$mutableblockpos);
/*     */               
/*  91 */               if (!flag || iblockstate.getBlock() != Blocks.air) {
/*     */                 
/*  93 */                 if (iblockstate == world.getBlockState((BlockPos)blockpos$mutableblockpos1)) {
/*     */                   
/*  95 */                   TileEntity tileentity = world.getTileEntity((BlockPos)blockpos$mutableblockpos);
/*  96 */                   TileEntity tileentity1 = world.getTileEntity((BlockPos)blockpos$mutableblockpos1);
/*     */                   
/*  98 */                   if (tileentity != null && tileentity1 != null)
/*     */                   {
/* 100 */                     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 101 */                     tileentity.writeToNBT(nbttagcompound);
/* 102 */                     nbttagcompound.removeTag("x");
/* 103 */                     nbttagcompound.removeTag("y");
/* 104 */                     nbttagcompound.removeTag("z");
/* 105 */                     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 106 */                     tileentity1.writeToNBT(nbttagcompound1);
/* 107 */                     nbttagcompound1.removeTag("x");
/* 108 */                     nbttagcompound1.removeTag("y");
/* 109 */                     nbttagcompound1.removeTag("z");
/*     */                     
/* 111 */                     if (!nbttagcompound.equals(nbttagcompound1))
/*     */                     {
/* 113 */                       flag1 = true;
/*     */                     }
/*     */                   }
/* 116 */                   else if (tileentity != null)
/*     */                   {
/* 118 */                     flag1 = true;
/*     */                   }
/*     */                 
/*     */                 } else {
/*     */                   
/* 123 */                   flag1 = true;
/*     */                 } 
/*     */                 
/* 126 */                 i++;
/*     */                 
/* 128 */                 if (flag1)
/*     */                 {
/* 130 */                   throw new CommandException("commands.compare.failed", new Object[0]);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 137 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 138 */         notifyOperators(sender, this, "commands.compare.success", new Object[] { Integer.valueOf(i) });
/*     */       }
/*     */       else
/*     */       {
/* 142 */         throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 147 */       throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 154 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length > 6 && args.length <= 9) ? func_175771_a(args, 6, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "masked", "all" }) : null)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\command\CommandCompare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */