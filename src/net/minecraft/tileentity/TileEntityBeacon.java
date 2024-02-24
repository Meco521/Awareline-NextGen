/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStainedGlass;
/*     */ import net.minecraft.block.BlockStainedGlassPane;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBeacon;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ public class TileEntityBeacon extends TileEntityLockable implements ITickable {
/*  32 */   public static final Potion[][] effectsList = new Potion[][] { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
/*  33 */   public final List<BeamSegment> beamSegments = Lists.newArrayList();
/*     */   
/*     */   private long beamRenderCounter;
/*     */   
/*     */   private float field_146014_j;
/*     */   public boolean isComplete;
/*  39 */   public int levels = -1;
/*     */ 
/*     */   
/*     */   public int primaryEffect;
/*     */ 
/*     */   
/*     */   public int secondaryEffect;
/*     */ 
/*     */   
/*     */   private ItemStack payment;
/*     */ 
/*     */   
/*     */   private String customName;
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  56 */     if (this.worldObj.getTotalWorldTime() % 80L == 0L)
/*     */     {
/*  58 */       updateBeacon();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBeacon() {
/*  64 */     updateSegmentColors();
/*  65 */     addEffectsToPlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEffectsToPlayers() {
/*  70 */     if (this.isComplete && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
/*     */       
/*  72 */       double d0 = (this.levels * 10 + 10);
/*  73 */       int i = 0;
/*     */       
/*  75 */       if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect)
/*     */       {
/*  77 */         i = 1;
/*     */       }
/*     */       
/*  80 */       int j = this.pos.getX();
/*  81 */       int k = this.pos.getY();
/*  82 */       int l = this.pos.getZ();
/*  83 */       AxisAlignedBB axisalignedbb = (new AxisAlignedBB(j, k, l, (j + 1), (k + 1), (l + 1))).expand(d0, d0, d0).addCoord(0.0D, this.worldObj.getHeight(), 0.0D);
/*  84 */       List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
/*     */       
/*  86 */       for (EntityPlayer entityplayer : list)
/*     */       {
/*  88 */         entityplayer.addPotionEffect(new PotionEffect(this.primaryEffect, 180, i, true, true));
/*     */       }
/*     */       
/*  91 */       if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0)
/*     */       {
/*  93 */         for (EntityPlayer entityplayer1 : list)
/*     */         {
/*  95 */           entityplayer1.addPotionEffect(new PotionEffect(this.secondaryEffect, 180, 0, true, true));
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSegmentColors() {
/* 103 */     int i = this.levels;
/* 104 */     int j = this.pos.getX();
/* 105 */     int k = this.pos.getY();
/* 106 */     int l = this.pos.getZ();
/* 107 */     this.levels = 0;
/* 108 */     this.beamSegments.clear();
/* 109 */     this.isComplete = true;
/* 110 */     BeamSegment tileentitybeacon$beamsegment = new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.WHITE));
/* 111 */     this.beamSegments.add(tileentitybeacon$beamsegment);
/* 112 */     boolean flag = true;
/* 113 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 115 */     int i1 = k + 1; while (true) { float[] afloat; if (i1 < 256)
/*     */       
/* 117 */       { IBlockState iblockstate = this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos.set(j, i1, l));
/*     */ 
/*     */         
/* 120 */         if (iblockstate.getBlock() == Blocks.stained_glass)
/*     */         
/* 122 */         { afloat = EntitySheep.getDyeRgb((EnumDyeColor)iblockstate.getValue((IProperty)BlockStainedGlass.COLOR)); }
/*     */         
/*     */         else
/*     */         
/* 126 */         { if (iblockstate.getBlock() != Blocks.stained_glass_pane)
/*     */           
/* 128 */           { if (iblockstate.getBlock().getLightOpacity() >= 15 && iblockstate.getBlock() != Blocks.bedrock) {
/*     */               
/* 130 */               this.isComplete = false;
/* 131 */               this.beamSegments.clear();
/*     */               
/*     */               break;
/*     */             } 
/* 135 */             tileentitybeacon$beamsegment.incrementHeight(); }
/*     */           
/*     */           else
/*     */           
/* 139 */           { afloat = EntitySheep.getDyeRgb((EnumDyeColor)iblockstate.getValue((IProperty)BlockStainedGlassPane.COLOR));
/*     */ 
/*     */             
/* 142 */             if (!flag)
/*     */             {
/* 144 */               afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F }; }  }  i1++; }  } else { break; }  if (!flag) afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F };
/*     */        }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (this.isComplete) {
/*     */       
/* 162 */       for (int l1 = 1; l1 <= 4; this.levels = l1++) {
/*     */         
/* 164 */         int i2 = k - l1;
/*     */         
/* 166 */         if (i2 < 0) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 171 */         boolean flag1 = true;
/*     */         
/* 173 */         for (int j1 = j - l1; j1 <= j + l1 && flag1; j1++) {
/*     */           
/* 175 */           for (int k1 = l - l1; k1 <= l + l1; k1++) {
/*     */             
/* 177 */             Block block = this.worldObj.getBlockState(new BlockPos(j1, i2, k1)).getBlock();
/*     */             
/* 179 */             if (block != Blocks.emerald_block && block != Blocks.gold_block && block != Blocks.diamond_block && block != Blocks.iron_block) {
/*     */               
/* 181 */               flag1 = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 187 */         if (!flag1) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 193 */       if (this.levels == 0)
/*     */       {
/* 195 */         this.isComplete = false;
/*     */       }
/*     */     } 
/*     */     
/* 199 */     if (!this.worldObj.isRemote && this.levels == 4 && i < this.levels)
/*     */     {
/* 201 */       for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, (new AxisAlignedBB(j, k, l, j, (k - 4), l)).expand(10.0D, 5.0D, 10.0D)))
/*     */       {
/* 203 */         entityplayer.triggerAchievement((StatBase)AchievementList.fullBeacon);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BeamSegment> getBeamSegments() {
/* 210 */     return this.beamSegments;
/*     */   }
/*     */ 
/*     */   
/*     */   public float shouldBeamRender() {
/* 215 */     if (!this.isComplete)
/*     */     {
/* 217 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 221 */     int i = (int)(this.worldObj.getTotalWorldTime() - this.beamRenderCounter);
/* 222 */     this.beamRenderCounter = this.worldObj.getTotalWorldTime();
/*     */     
/* 224 */     if (i > 1) {
/*     */       
/* 226 */       this.field_146014_j -= i / 40.0F;
/*     */       
/* 228 */       if (this.field_146014_j < 0.0F)
/*     */       {
/* 230 */         this.field_146014_j = 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 234 */     this.field_146014_j += 0.025F;
/*     */     
/* 236 */     if (this.field_146014_j > 1.0F)
/*     */     {
/* 238 */       this.field_146014_j = 1.0F;
/*     */     }
/*     */     
/* 241 */     return this.field_146014_j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/* 251 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 252 */     writeToNBT(nbttagcompound);
/* 253 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 3, nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/* 258 */     return 65536.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_183001_h(int p_183001_1_) {
/* 263 */     if (p_183001_1_ >= 0 && p_183001_1_ < Potion.potionTypes.length && Potion.potionTypes[p_183001_1_] != null) {
/*     */       
/* 265 */       Potion potion = Potion.potionTypes[p_183001_1_];
/* 266 */       return (potion != Potion.moveSpeed && potion != Potion.digSpeed && potion != Potion.resistance && potion != Potion.jump && potion != Potion.damageBoost && potion != Potion.regeneration) ? 0 : p_183001_1_;
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 276 */     super.readFromNBT(compound);
/* 277 */     this.primaryEffect = func_183001_h(compound.getInteger("Primary"));
/* 278 */     this.secondaryEffect = func_183001_h(compound.getInteger("Secondary"));
/* 279 */     this.levels = compound.getInteger("Levels");
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 284 */     super.writeToNBT(compound);
/* 285 */     compound.setInteger("Primary", this.primaryEffect);
/* 286 */     compound.setInteger("Secondary", this.secondaryEffect);
/* 287 */     compound.setInteger("Levels", this.levels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 295 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 303 */     return (index == 0) ? this.payment : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 311 */     if (index == 0 && this.payment != null) {
/*     */       
/* 313 */       if (count >= this.payment.stackSize) {
/*     */         
/* 315 */         ItemStack itemstack = this.payment;
/* 316 */         this.payment = null;
/* 317 */         return itemstack;
/*     */       } 
/*     */ 
/*     */       
/* 321 */       this.payment.stackSize -= count;
/* 322 */       return new ItemStack(this.payment.getItem(), count, this.payment.getMetadata());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 327 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 336 */     if (index == 0 && this.payment != null) {
/*     */       
/* 338 */       ItemStack itemstack = this.payment;
/* 339 */       this.payment = null;
/* 340 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 344 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 353 */     if (index == 0)
/*     */     {
/* 355 */       this.payment = stack;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 364 */     return hasCustomName() ? this.customName : "container.beacon";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 372 */     return (this.customName != null && !this.customName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 377 */     this.customName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 385 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 393 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 409 */     return (stack.getItem() == Items.emerald || stack.getItem() == Items.diamond || stack.getItem() == Items.gold_ingot || stack.getItem() == Items.iron_ingot);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 414 */     return "minecraft:beacon";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 419 */     return (Container)new ContainerBeacon((IInventory)playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 424 */     switch (id) {
/*     */       
/*     */       case 0:
/* 427 */         return this.levels;
/*     */       
/*     */       case 1:
/* 430 */         return this.primaryEffect;
/*     */       
/*     */       case 2:
/* 433 */         return this.secondaryEffect;
/*     */     } 
/*     */     
/* 436 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 442 */     switch (id) {
/*     */       
/*     */       case 0:
/* 445 */         this.levels = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 449 */         this.primaryEffect = func_183001_h(value);
/*     */         break;
/*     */       
/*     */       case 2:
/* 453 */         this.secondaryEffect = func_183001_h(value);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFieldCount() {
/* 459 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 464 */     this.payment = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 469 */     if (id == 1) {
/*     */       
/* 471 */       updateBeacon();
/* 472 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 476 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class BeamSegment
/*     */   {
/*     */     private final float[] colors;
/*     */     
/*     */     private int height;
/*     */     
/*     */     public BeamSegment(float[] p_i45669_1_) {
/* 487 */       this.colors = p_i45669_1_;
/* 488 */       this.height = 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void incrementHeight() {
/* 493 */       this.height++;
/*     */     }
/*     */ 
/*     */     
/*     */     public float[] getColors() {
/* 498 */       return this.colors;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 503 */       return this.height;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */