/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemMonsterPlacer extends Item {
/*     */   public ItemMonsterPlacer() {
/*  23 */     setHasSubtypes(true);
/*  24 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  29 */     String s = StatCollector.translateToLocal(getUnlocalizedName() + ".name").trim();
/*  30 */     String s1 = EntityList.getStringFromID(stack.getMetadata());
/*     */     
/*  32 */     if (s1 != null)
/*     */     {
/*  34 */       s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
/*     */     }
/*     */     
/*  37 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  42 */     EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(stack.getMetadata()));
/*  43 */     return (entitylist$entityegginfo != null) ? ((renderPass == 0) ? entitylist$entityegginfo.primaryColor : entitylist$entityegginfo.secondaryColor) : 16777215;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  51 */     if (worldIn.isRemote)
/*     */     {
/*  53 */       return true;
/*     */     }
/*  55 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/*     */     {
/*  57 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  61 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  63 */     if (iblockstate.getBlock() == Blocks.mob_spawner) {
/*     */       
/*  65 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  67 */       if (tileentity instanceof TileEntityMobSpawner) {
/*     */         
/*  69 */         MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
/*  70 */         mobspawnerbaselogic.setEntityName(EntityList.getStringFromID(stack.getMetadata()));
/*  71 */         tileentity.markDirty();
/*  72 */         worldIn.markBlockForUpdate(pos);
/*     */         
/*  74 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/*  76 */           stack.stackSize--;
/*     */         }
/*     */         
/*  79 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     pos = pos.offset(side);
/*  84 */     double d0 = 0.0D;
/*     */     
/*  86 */     if (side == EnumFacing.UP && iblockstate instanceof net.minecraft.block.BlockFence)
/*     */     {
/*  88 */       d0 = 0.5D;
/*     */     }
/*     */     
/*  91 */     Entity entity = spawnCreature(worldIn, stack.getMetadata(), pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D);
/*     */     
/*  93 */     if (entity != null) {
/*     */       
/*  95 */       if (entity instanceof net.minecraft.entity.EntityLivingBase && stack.hasDisplayName())
/*     */       {
/*  97 */         entity.setCustomNameTag(stack.getDisplayName());
/*     */       }
/*     */       
/* 100 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 102 */         stack.stackSize--;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 115 */     if (worldIn.isRemote)
/*     */     {
/* 117 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/* 121 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/*     */     
/* 123 */     if (movingobjectposition == null)
/*     */     {
/* 125 */       return itemStackIn;
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*     */       
/* 131 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*     */       
/* 133 */       if (!worldIn.isBlockModifiable(playerIn, blockpos))
/*     */       {
/* 135 */         return itemStackIn;
/*     */       }
/*     */       
/* 138 */       if (!playerIn.canPlayerEdit(blockpos, movingobjectposition.sideHit, itemStackIn))
/*     */       {
/* 140 */         return itemStackIn;
/*     */       }
/*     */       
/* 143 */       if (worldIn.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.BlockLiquid) {
/*     */         
/* 145 */         Entity entity = spawnCreature(worldIn, itemStackIn.getMetadata(), blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
/*     */         
/* 147 */         if (entity != null) {
/*     */           
/* 149 */           if (entity instanceof net.minecraft.entity.EntityLivingBase && itemStackIn.hasDisplayName())
/*     */           {
/* 151 */             ((EntityLiving)entity).setCustomNameTag(itemStackIn.getDisplayName());
/*     */           }
/*     */           
/* 154 */           if (!playerIn.capabilities.isCreativeMode)
/*     */           {
/* 156 */             itemStackIn.stackSize--;
/*     */           }
/*     */           
/* 159 */           playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity spawnCreature(World worldIn, int entityID, double x, double y, double z) {
/* 175 */     if (!EntityList.entityEggs.containsKey(Integer.valueOf(entityID)))
/*     */     {
/* 177 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 181 */     Entity entity = null;
/*     */     
/* 183 */     for (int i = 0; i < 1; i++) {
/*     */       
/* 185 */       entity = EntityList.createEntityByID(entityID, worldIn);
/*     */       
/* 187 */       if (entity instanceof net.minecraft.entity.EntityLivingBase) {
/*     */         
/* 189 */         EntityLiving entityliving = (EntityLiving)entity;
/* 190 */         entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
/* 191 */         entityliving.rotationYawHead = entityliving.rotationYaw;
/* 192 */         entityliving.renderYawOffset = entityliving.rotationYaw;
/* 193 */         entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), (IEntityLivingData)null);
/* 194 */         worldIn.spawnEntityInWorld(entity);
/* 195 */         entityliving.playLivingSound();
/*     */       } 
/*     */     } 
/*     */     
/* 199 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 208 */     for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.entityEggs.values())
/*     */     {
/* 210 */       subItems.add(new ItemStack(itemIn, 1, entitylist$entityegginfo.spawnedID));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemMonsterPlacer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */