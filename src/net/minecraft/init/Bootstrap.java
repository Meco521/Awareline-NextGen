/*     */ package net.minecraft.init;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.BlockPumpkin;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.BehaviorProjectileDispense;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.dispenser.IPosition;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBucket;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class Bootstrap {
/*  38 */   private static final PrintStream SYSOUT = System.out;
/*     */   
/*     */   private static boolean alreadyRegistered = false;
/*     */   
/*  42 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRegistered() {
/*  49 */     return alreadyRegistered;
/*     */   }
/*     */ 
/*     */   
/*     */   static void registerDispenserBehaviors() {
/*  54 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position)
/*     */           {
/*  58 */             EntityArrow entityarrow = new EntityArrow(worldIn, position.getX(), position.getY(), position.getZ());
/*  59 */             entityarrow.canBePickedUp = 1;
/*  60 */             return (IProjectile)entityarrow;
/*     */           }
/*     */         });
/*  63 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position)
/*     */           {
/*  67 */             return (IProjectile)new EntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */         });
/*  70 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position)
/*     */           {
/*  74 */             return (IProjectile)new EntitySnowball(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */         });
/*  77 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position)
/*     */           {
/*  81 */             return (IProjectile)new EntityExpBottle(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */           
/*     */           protected float func_82498_a() {
/*  85 */             return super.func_82498_a() * 0.5F;
/*     */           }
/*     */           
/*     */           protected float func_82500_b() {
/*  89 */             return super.func_82500_b() * 1.25F;
/*     */           }
/*     */         });
/*  92 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new IBehaviorDispenseItem()
/*     */         {
/*  94 */           private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();
/*     */           
/*     */           public ItemStack dispense(IBlockSource source, final ItemStack stack) {
/*  97 */             return ItemPotion.isSplash(stack.getMetadata()) ? (new BehaviorProjectileDispense()
/*     */               {
/*     */                 protected IProjectile getProjectileEntity(World worldIn, IPosition position)
/*     */                 {
/* 101 */                   return (IProjectile)new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
/*     */                 }
/*     */                 
/*     */                 protected float func_82498_a() {
/* 105 */                   return super.func_82498_a() * 0.5F;
/*     */                 }
/*     */                 
/*     */                 protected float func_82500_b() {
/* 109 */                   return super.func_82500_b() * 1.25F;
/*     */                 }
/* 111 */               }).dispense(source, stack) : this.field_150843_b.dispense(source, stack);
/*     */           }
/*     */         });
/* 114 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 118 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 119 */             double d0 = source.getX() + enumfacing.getFrontOffsetX();
/* 120 */             double d1 = (source.getBlockPos().getY() + 0.2F);
/* 121 */             double d2 = source.getZ() + enumfacing.getFrontOffsetZ();
/* 122 */             Entity entity = ItemMonsterPlacer.spawnCreature(source.getWorld(), stack.getMetadata(), d0, d1, d2);
/*     */             
/* 124 */             if (entity instanceof EntityLivingBase && stack.hasDisplayName())
/*     */             {
/* 126 */               ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
/*     */             }
/*     */             
/* 129 */             stack.splitStack(1);
/* 130 */             return stack;
/*     */           }
/*     */         });
/* 133 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 137 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 138 */             double d0 = source.getX() + enumfacing.getFrontOffsetX();
/* 139 */             double d1 = (source.getBlockPos().getY() + 0.2F);
/* 140 */             double d2 = source.getZ() + enumfacing.getFrontOffsetZ();
/* 141 */             EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(source.getWorld(), d0, d1, d2, stack);
/* 142 */             source.getWorld().spawnEntityInWorld((Entity)entityfireworkrocket);
/* 143 */             stack.splitStack(1);
/* 144 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 148 */             source.getWorld().playAuxSFX(1002, source.getBlockPos(), 0);
/*     */           }
/*     */         });
/* 151 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 155 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 156 */             IPosition iposition = BlockDispenser.getDispensePosition(source);
/* 157 */             double d0 = iposition.getX() + (enumfacing.getFrontOffsetX() * 0.3F);
/* 158 */             double d1 = iposition.getY() + (enumfacing.getFrontOffsetY() * 0.3F);
/* 159 */             double d2 = iposition.getZ() + (enumfacing.getFrontOffsetZ() * 0.3F);
/* 160 */             World world = source.getWorld();
/* 161 */             Random random = world.rand;
/* 162 */             double d3 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetX();
/* 163 */             double d4 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetY();
/* 164 */             double d5 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetZ();
/* 165 */             world.spawnEntityInWorld((Entity)new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
/* 166 */             stack.splitStack(1);
/* 167 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 171 */             source.getWorld().playAuxSFX(1009, source.getBlockPos(), 0);
/*     */           }
/*     */         });
/* 174 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new BehaviorDefaultDispenseItem()
/*     */         {
/* 176 */           private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*     */             double d3;
/* 179 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 180 */             World world = source.getWorld();
/* 181 */             double d0 = source.getX() + (enumfacing.getFrontOffsetX() * 1.125F);
/* 182 */             double d1 = source.getY() + (enumfacing.getFrontOffsetY() * 1.125F);
/* 183 */             double d2 = source.getZ() + (enumfacing.getFrontOffsetZ() * 1.125F);
/* 184 */             BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/* 185 */             Material material = world.getBlockState(blockpos).getBlock().getMaterial();
/*     */ 
/*     */             
/* 188 */             if (Material.water.equals(material)) {
/*     */               
/* 190 */               d3 = 1.0D;
/*     */             }
/*     */             else {
/*     */               
/* 194 */               if (!Material.air.equals(material) || !Material.water.equals(world.getBlockState(blockpos.down()).getBlock().getMaterial()))
/*     */               {
/* 196 */                 return this.field_150842_b.dispense(source, stack);
/*     */               }
/*     */               
/* 199 */               d3 = 0.0D;
/*     */             } 
/*     */             
/* 202 */             EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);
/* 203 */             world.spawnEntityInWorld((Entity)entityboat);
/* 204 */             stack.splitStack(1);
/* 205 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 209 */             source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */           }
/*     */         });
/* 212 */     BehaviorDefaultDispenseItem behaviorDefaultDispenseItem = new BehaviorDefaultDispenseItem()
/*     */       {
/* 214 */         private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();
/*     */         
/*     */         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 217 */           ItemBucket itembucket = (ItemBucket)stack.getItem();
/* 218 */           BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*     */           
/* 220 */           if (itembucket.tryPlaceContainedLiquid(source.getWorld(), blockpos)) {
/*     */             
/* 222 */             stack.setItem(Items.bucket);
/* 223 */             stack.stackSize = 1;
/* 224 */             return stack;
/*     */           } 
/*     */ 
/*     */           
/* 228 */           return this.field_150841_b.dispense(source, stack);
/*     */         }
/*     */       };
/*     */     
/* 232 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, behaviorDefaultDispenseItem);
/* 233 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, behaviorDefaultDispenseItem);
/* 234 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem()
/*     */         {
/* 236 */           private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*     */             Item item;
/* 239 */             World world = source.getWorld();
/* 240 */             BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/* 241 */             IBlockState iblockstate = world.getBlockState(blockpos);
/* 242 */             Block block = iblockstate.getBlock();
/* 243 */             Material material = block.getMaterial();
/*     */ 
/*     */             
/* 246 */             if (Material.water.equals(material) && block instanceof BlockLiquid && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/*     */               
/* 248 */               item = Items.water_bucket;
/*     */             }
/*     */             else {
/*     */               
/* 252 */               if (!Material.lava.equals(material) || !(block instanceof BlockLiquid) || ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() != 0)
/*     */               {
/* 254 */                 return super.dispenseStack(source, stack);
/*     */               }
/*     */               
/* 257 */               item = Items.lava_bucket;
/*     */             } 
/*     */             
/* 260 */             world.setBlockToAir(blockpos);
/*     */             
/* 262 */             if (--stack.stackSize == 0) {
/*     */               
/* 264 */               stack.setItem(item);
/* 265 */               stack.stackSize = 1;
/*     */             }
/* 267 */             else if (((TileEntityDispenser)source.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0) {
/*     */               
/* 269 */               this.field_150840_b.dispense(source, new ItemStack(item));
/*     */             } 
/*     */             
/* 272 */             return stack;
/*     */           }
/*     */         });
/* 275 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           private boolean field_150839_b = true;
/*     */           
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 280 */             World world = source.getWorld();
/* 281 */             BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*     */             
/* 283 */             if (world.isAirBlock(blockpos)) {
/*     */               
/* 285 */               world.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*     */               
/* 287 */               if (stack.attemptDamageItem(1, world.rand))
/*     */               {
/* 289 */                 stack.stackSize = 0;
/*     */               }
/*     */             }
/* 292 */             else if (world.getBlockState(blockpos).getBlock() == Blocks.tnt) {
/*     */               
/* 294 */               Blocks.tnt.onBlockDestroyedByPlayer(world, blockpos, Blocks.tnt.getDefaultState().withProperty((IProperty)BlockTNT.EXPLODE, Boolean.valueOf(true)));
/* 295 */               world.setBlockToAir(blockpos);
/*     */             }
/*     */             else {
/*     */               
/* 299 */               this.field_150839_b = false;
/*     */             } 
/*     */             
/* 302 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 306 */             if (this.field_150839_b) {
/*     */               
/* 308 */               source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */             }
/*     */             else {
/*     */               
/* 312 */               source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
/*     */             } 
/*     */           }
/*     */         });
/* 316 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           private boolean field_150838_b = true;
/*     */           
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 321 */             if (EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(stack.getMetadata())) {
/*     */               
/* 323 */               World world = source.getWorld();
/* 324 */               BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*     */               
/* 326 */               if (ItemDye.applyBonemeal(stack, world, blockpos)) {
/*     */                 
/* 328 */                 if (!world.isRemote)
/*     */                 {
/* 330 */                   world.playAuxSFX(2005, blockpos, 0);
/*     */                 }
/*     */               }
/*     */               else {
/*     */                 
/* 335 */                 this.field_150838_b = false;
/*     */               } 
/*     */               
/* 338 */               return stack;
/*     */             } 
/*     */ 
/*     */             
/* 342 */             return super.dispenseStack(source, stack);
/*     */           }
/*     */ 
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 347 */             if (this.field_150838_b) {
/*     */               
/* 349 */               source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */             }
/*     */             else {
/*     */               
/* 353 */               source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
/*     */             } 
/*     */           }
/*     */         });
/* 357 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 361 */             World world = source.getWorld();
/* 362 */             BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/* 363 */             EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D, (EntityLivingBase)null);
/* 364 */             world.spawnEntityInWorld((Entity)entitytntprimed);
/* 365 */             world.playSoundAtEntity((Entity)entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
/* 366 */             stack.stackSize--;
/* 367 */             return stack;
/*     */           }
/*     */         });
/* 370 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.skull, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           private boolean field_179240_b = true;
/*     */           
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 375 */             World world = source.getWorld();
/* 376 */             EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 377 */             BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/* 378 */             BlockSkull blockskull = Blocks.skull;
/*     */             
/* 380 */             if (world.isAirBlock(blockpos) && blockskull.canDispenserPlace(world, blockpos, stack)) {
/*     */               
/* 382 */               if (!world.isRemote)
/*     */               {
/* 384 */                 world.setBlockState(blockpos, blockskull.getDefaultState().withProperty((IProperty)BlockSkull.FACING, (Comparable)EnumFacing.UP), 3);
/* 385 */                 TileEntity tileentity = world.getTileEntity(blockpos);
/*     */                 
/* 387 */                 if (tileentity instanceof TileEntitySkull) {
/*     */                   
/* 389 */                   if (stack.getMetadata() == 3) {
/*     */                     
/* 391 */                     GameProfile gameprofile = null;
/*     */                     
/* 393 */                     if (stack.hasTagCompound()) {
/*     */                       
/* 395 */                       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */                       
/* 397 */                       if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*     */                         
/* 399 */                         gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*     */                       }
/* 401 */                       else if (nbttagcompound.hasKey("SkullOwner", 8)) {
/*     */                         
/* 403 */                         String s = nbttagcompound.getString("SkullOwner");
/*     */                         
/* 405 */                         if (!StringUtils.isNullOrEmpty(s))
/*     */                         {
/* 407 */                           gameprofile = new GameProfile((UUID)null, s);
/*     */                         }
/*     */                       } 
/*     */                     } 
/*     */                     
/* 412 */                     ((TileEntitySkull)tileentity).setPlayerProfile(gameprofile);
/*     */                   }
/*     */                   else {
/*     */                     
/* 416 */                     ((TileEntitySkull)tileentity).setType(stack.getMetadata());
/*     */                   } 
/*     */                   
/* 419 */                   ((TileEntitySkull)tileentity).setSkullRotation(enumfacing.getOpposite().getHorizontalIndex() << 2);
/* 420 */                   Blocks.skull.checkWitherSpawn(world, blockpos, (TileEntitySkull)tileentity);
/*     */                 } 
/*     */                 
/* 423 */                 stack.stackSize--;
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 428 */               this.field_179240_b = false;
/*     */             } 
/*     */             
/* 431 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 435 */             if (this.field_179240_b) {
/*     */               
/* 437 */               source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */             }
/*     */             else {
/*     */               
/* 441 */               source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
/*     */             } 
/*     */           }
/*     */         });
/* 445 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.pumpkin), new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           private boolean field_179241_b = true;
/*     */           
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 450 */             World world = source.getWorld();
/* 451 */             BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/* 452 */             BlockPumpkin blockpumpkin = (BlockPumpkin)Blocks.pumpkin;
/*     */             
/* 454 */             if (world.isAirBlock(blockpos) && blockpumpkin.canDispenserPlace(world, blockpos)) {
/*     */               
/* 456 */               if (!world.isRemote)
/*     */               {
/* 458 */                 world.setBlockState(blockpos, blockpumpkin.getDefaultState(), 3);
/*     */               }
/*     */               
/* 461 */               stack.stackSize--;
/*     */             }
/*     */             else {
/*     */               
/* 465 */               this.field_179241_b = false;
/*     */             } 
/*     */             
/* 468 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 472 */             if (this.field_179241_b) {
/*     */               
/* 474 */               source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */             }
/*     */             else {
/*     */               
/* 478 */               source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() {
/* 489 */     if (!alreadyRegistered) {
/*     */       
/* 491 */       alreadyRegistered = true;
/*     */       
/* 493 */       if (LOGGER.isDebugEnabled())
/*     */       {
/* 495 */         redirectOutputToLog();
/*     */       }
/*     */       
/* 498 */       Block.registerBlocks();
/* 499 */       BlockFire.init();
/* 500 */       Item.registerItems();
/* 501 */       StatList.init();
/* 502 */       registerDispenserBehaviors();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void redirectOutputToLog() {
/* 511 */     System.setErr((PrintStream)new LoggingPrintStream("STDERR", System.err));
/* 512 */     System.setOut((PrintStream)new LoggingPrintStream("STDOUT", SYSOUT));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void printToSYSOUT(String p_179870_0_) {
/* 517 */     SYSOUT.println(p_179870_0_);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\init\Bootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */