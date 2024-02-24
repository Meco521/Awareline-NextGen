/*     */ package net.minecraft.entity.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Rotations;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class EntityArmorStand extends EntityLivingBase {
/*  25 */   private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
/*  26 */   private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
/*  27 */   private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
/*  28 */   private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
/*  29 */   private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
/*  30 */   private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
/*     */   
/*     */   private final ItemStack[] contents;
/*     */   
/*     */   private boolean canInteract;
/*     */   
/*     */   private long punchCooldown;
/*     */   
/*     */   private int disabledSlots;
/*     */   
/*     */   private boolean field_181028_bj;
/*     */   private Rotations headRotation;
/*     */   private Rotations bodyRotation;
/*     */   private Rotations leftArmRotation;
/*     */   private Rotations rightArmRotation;
/*     */   private Rotations leftLegRotation;
/*     */   private Rotations rightLegRotation;
/*     */   
/*     */   public EntityArmorStand(World worldIn) {
/*  49 */     super(worldIn);
/*  50 */     this.contents = new ItemStack[5];
/*  51 */     this.headRotation = DEFAULT_HEAD_ROTATION;
/*  52 */     this.bodyRotation = DEFAULT_BODY_ROTATION;
/*  53 */     this.leftArmRotation = DEFAULT_LEFTARM_ROTATION;
/*  54 */     this.rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
/*  55 */     this.leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
/*  56 */     this.rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;
/*  57 */     setSilent(true);
/*  58 */     this.noClip = hasNoGravity();
/*  59 */     setSize(0.5F, 1.975F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArmorStand(World worldIn, double posX, double posY, double posZ) {
/*  64 */     this(worldIn);
/*  65 */     setPosition(posX, posY, posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isServerWorld() {
/*  73 */     return (super.isServerWorld() && !hasNoGravity());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  78 */     super.entityInit();
/*  79 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/*  80 */     this.dataWatcher.addObject(11, DEFAULT_HEAD_ROTATION);
/*  81 */     this.dataWatcher.addObject(12, DEFAULT_BODY_ROTATION);
/*  82 */     this.dataWatcher.addObject(13, DEFAULT_LEFTARM_ROTATION);
/*  83 */     this.dataWatcher.addObject(14, DEFAULT_RIGHTARM_ROTATION);
/*  84 */     this.dataWatcher.addObject(15, DEFAULT_LEFTLEG_ROTATION);
/*  85 */     this.dataWatcher.addObject(16, DEFAULT_RIGHTLEG_ROTATION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getHeldItem() {
/*  93 */     return this.contents[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getEquipmentInSlot(int slotIn) {
/* 101 */     return this.contents[slotIn];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentArmor(int slotIn) {
/* 106 */     return this.contents[slotIn + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 114 */     this.contents[slotIn] = stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getInventory() {
/* 122 */     return this.contents;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*     */     int i;
/* 129 */     if (inventorySlot == 99) {
/*     */       
/* 131 */       i = 0;
/*     */     }
/*     */     else {
/*     */       
/* 135 */       i = inventorySlot - 100 + 1;
/*     */       
/* 137 */       if (i < 0 || i >= this.contents.length)
/*     */       {
/* 139 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 143 */     if (itemStackIn != null && EntityLiving.getArmorPosition(itemStackIn) != i && (i != 4 || !(itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock)))
/*     */     {
/* 145 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 149 */     setCurrentItemOrArmor(i, itemStackIn);
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 159 */     super.writeEntityToNBT(tagCompound);
/* 160 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 162 */     for (int i = 0; i < this.contents.length; i++) {
/*     */       
/* 164 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 166 */       if (this.contents[i] != null)
/*     */       {
/* 168 */         this.contents[i].writeToNBT(nbttagcompound);
/*     */       }
/*     */       
/* 171 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 174 */     tagCompound.setTag("Equipment", (NBTBase)nbttaglist);
/*     */     
/* 176 */     if (getAlwaysRenderNameTag() && (getCustomNameTag() == null || getCustomNameTag().isEmpty()))
/*     */     {
/* 178 */       tagCompound.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*     */     }
/*     */     
/* 181 */     tagCompound.setBoolean("Invisible", isInvisible());
/* 182 */     tagCompound.setBoolean("Small", isSmall());
/* 183 */     tagCompound.setBoolean("ShowArms", getShowArms());
/* 184 */     tagCompound.setInteger("DisabledSlots", this.disabledSlots);
/* 185 */     tagCompound.setBoolean("NoGravity", hasNoGravity());
/* 186 */     tagCompound.setBoolean("NoBasePlate", hasNoBasePlate());
/*     */     
/* 188 */     if (hasMarker())
/*     */     {
/* 190 */       tagCompound.setBoolean("Marker", hasMarker());
/*     */     }
/*     */     
/* 193 */     tagCompound.setTag("Pose", (NBTBase)readPoseFromNBT());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 201 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 203 */     if (tagCompund.hasKey("Equipment", 9)) {
/*     */       
/* 205 */       NBTTagList nbttaglist = tagCompund.getTagList("Equipment", 10);
/*     */       
/* 207 */       for (int i = 0; i < this.contents.length; i++)
/*     */       {
/* 209 */         this.contents[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */       }
/*     */     } 
/*     */     
/* 213 */     setInvisible(tagCompund.getBoolean("Invisible"));
/* 214 */     setSmall(tagCompund.getBoolean("Small"));
/* 215 */     setShowArms(tagCompund.getBoolean("ShowArms"));
/* 216 */     this.disabledSlots = tagCompund.getInteger("DisabledSlots");
/* 217 */     setNoGravity(tagCompund.getBoolean("NoGravity"));
/* 218 */     setNoBasePlate(tagCompund.getBoolean("NoBasePlate"));
/* 219 */     setMarker(tagCompund.getBoolean("Marker"));
/* 220 */     this.field_181028_bj = !hasMarker();
/* 221 */     this.noClip = hasNoGravity();
/* 222 */     NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Pose");
/* 223 */     writePoseToNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writePoseToNBT(NBTTagCompound tagCompound) {
/* 231 */     NBTTagList nbttaglist = tagCompound.getTagList("Head", 5);
/*     */     
/* 233 */     if (nbttaglist.tagCount() > 0) {
/*     */       
/* 235 */       setHeadRotation(new Rotations(nbttaglist));
/*     */     }
/*     */     else {
/*     */       
/* 239 */       setHeadRotation(DEFAULT_HEAD_ROTATION);
/*     */     } 
/*     */     
/* 242 */     NBTTagList nbttaglist1 = tagCompound.getTagList("Body", 5);
/*     */     
/* 244 */     if (nbttaglist1.tagCount() > 0) {
/*     */       
/* 246 */       setBodyRotation(new Rotations(nbttaglist1));
/*     */     }
/*     */     else {
/*     */       
/* 250 */       setBodyRotation(DEFAULT_BODY_ROTATION);
/*     */     } 
/*     */     
/* 253 */     NBTTagList nbttaglist2 = tagCompound.getTagList("LeftArm", 5);
/*     */     
/* 255 */     if (nbttaglist2.tagCount() > 0) {
/*     */       
/* 257 */       setLeftArmRotation(new Rotations(nbttaglist2));
/*     */     }
/*     */     else {
/*     */       
/* 261 */       setLeftArmRotation(DEFAULT_LEFTARM_ROTATION);
/*     */     } 
/*     */     
/* 264 */     NBTTagList nbttaglist3 = tagCompound.getTagList("RightArm", 5);
/*     */     
/* 266 */     if (nbttaglist3.tagCount() > 0) {
/*     */       
/* 268 */       setRightArmRotation(new Rotations(nbttaglist3));
/*     */     }
/*     */     else {
/*     */       
/* 272 */       setRightArmRotation(DEFAULT_RIGHTARM_ROTATION);
/*     */     } 
/*     */     
/* 275 */     NBTTagList nbttaglist4 = tagCompound.getTagList("LeftLeg", 5);
/*     */     
/* 277 */     if (nbttaglist4.tagCount() > 0) {
/*     */       
/* 279 */       setLeftLegRotation(new Rotations(nbttaglist4));
/*     */     }
/*     */     else {
/*     */       
/* 283 */       setLeftLegRotation(DEFAULT_LEFTLEG_ROTATION);
/*     */     } 
/*     */     
/* 286 */     NBTTagList nbttaglist5 = tagCompound.getTagList("RightLeg", 5);
/*     */     
/* 288 */     if (nbttaglist5.tagCount() > 0) {
/*     */       
/* 290 */       setRightLegRotation(new Rotations(nbttaglist5));
/*     */     }
/*     */     else {
/*     */       
/* 294 */       setRightLegRotation(DEFAULT_RIGHTLEG_ROTATION);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private NBTTagCompound readPoseFromNBT() {
/* 300 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 302 */     if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation))
/*     */     {
/* 304 */       nbttagcompound.setTag("Head", (NBTBase)this.headRotation.writeToNBT());
/*     */     }
/*     */     
/* 307 */     if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation))
/*     */     {
/* 309 */       nbttagcompound.setTag("Body", (NBTBase)this.bodyRotation.writeToNBT());
/*     */     }
/*     */     
/* 312 */     if (!DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation))
/*     */     {
/* 314 */       nbttagcompound.setTag("LeftArm", (NBTBase)this.leftArmRotation.writeToNBT());
/*     */     }
/*     */     
/* 317 */     if (!DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation))
/*     */     {
/* 319 */       nbttagcompound.setTag("RightArm", (NBTBase)this.rightArmRotation.writeToNBT());
/*     */     }
/*     */     
/* 322 */     if (!DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation))
/*     */     {
/* 324 */       nbttagcompound.setTag("LeftLeg", (NBTBase)this.leftLegRotation.writeToNBT());
/*     */     }
/*     */     
/* 327 */     if (!DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation))
/*     */     {
/* 329 */       nbttagcompound.setTag("RightLeg", (NBTBase)this.rightLegRotation.writeToNBT());
/*     */     }
/*     */     
/* 332 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/* 340 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity entityIn) {}
/*     */ 
/*     */   
/*     */   protected void collideWithNearbyEntities() {
/* 349 */     List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox());
/*     */     
/* 351 */     if (list != null && !list.isEmpty())
/*     */     {
/* 353 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 355 */         Entity entity = list.get(i);
/*     */         
/* 357 */         if (entity instanceof EntityMinecart && ((EntityMinecart)entity).getMinecartType() == EntityMinecart.EnumMinecartType.RIDEABLE && getDistanceSqToEntity(entity) <= 0.2D)
/*     */         {
/* 359 */           entity.applyEntityCollision((Entity)this);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactAt(EntityPlayer player, Vec3 targetVec3) {
/* 370 */     if (hasMarker())
/*     */     {
/* 372 */       return false;
/*     */     }
/* 374 */     if (!this.worldObj.isRemote && !player.isSpectator()) {
/*     */       
/* 376 */       int i = 0;
/* 377 */       ItemStack itemstack = player.getCurrentEquippedItem();
/* 378 */       boolean flag = (itemstack != null);
/*     */       
/* 380 */       if (flag && itemstack.getItem() instanceof ItemArmor) {
/*     */         
/* 382 */         ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*     */         
/* 384 */         if (itemarmor.armorType == 3) {
/*     */           
/* 386 */           i = 1;
/*     */         }
/* 388 */         else if (itemarmor.armorType == 2) {
/*     */           
/* 390 */           i = 2;
/*     */         }
/* 392 */         else if (itemarmor.armorType == 1) {
/*     */           
/* 394 */           i = 3;
/*     */         }
/* 396 */         else if (itemarmor.armorType == 0) {
/*     */           
/* 398 */           i = 4;
/*     */         } 
/*     */       } 
/*     */       
/* 402 */       if (flag && (itemstack.getItem() == Items.skull || itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)))
/*     */       {
/* 404 */         i = 4;
/*     */       }
/*     */       
/* 407 */       double d4 = 0.1D;
/* 408 */       double d0 = 0.9D;
/* 409 */       double d1 = 0.4D;
/* 410 */       double d2 = 1.6D;
/* 411 */       int j = 0;
/* 412 */       boolean flag1 = isSmall();
/* 413 */       double d3 = flag1 ? (targetVec3.yCoord * 2.0D) : targetVec3.yCoord;
/*     */       
/* 415 */       if (d3 >= 0.1D && d3 < 0.1D + (flag1 ? 0.8D : 0.45D) && this.contents[1] != null) {
/*     */         
/* 417 */         j = 1;
/*     */       }
/* 419 */       else if (d3 >= 0.9D + (flag1 ? 0.3D : 0.0D) && d3 < 0.9D + (flag1 ? 1.0D : 0.7D) && this.contents[3] != null) {
/*     */         
/* 421 */         j = 3;
/*     */       }
/* 423 */       else if (d3 >= 0.4D && d3 < 0.4D + (flag1 ? 1.0D : 0.8D) && this.contents[2] != null) {
/*     */         
/* 425 */         j = 2;
/*     */       }
/* 427 */       else if (d3 >= 1.6D && this.contents[4] != null) {
/*     */         
/* 429 */         j = 4;
/*     */       } 
/*     */       
/* 432 */       boolean flag2 = (this.contents[j] != null);
/*     */       
/* 434 */       if ((this.disabledSlots & 1 << j) != 0 || (this.disabledSlots & 1 << i) != 0) {
/*     */         
/* 436 */         j = i;
/*     */         
/* 438 */         if ((this.disabledSlots & 1 << i) != 0) {
/*     */           
/* 440 */           if ((this.disabledSlots & 0x1) != 0)
/*     */           {
/* 442 */             return true;
/*     */           }
/*     */           
/* 445 */           j = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 449 */       if (flag && i == 0 && !getShowArms())
/*     */       {
/* 451 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 455 */       if (flag) {
/*     */         
/* 457 */         func_175422_a(player, i);
/*     */       }
/* 459 */       else if (flag2) {
/*     */         
/* 461 */         func_175422_a(player, j);
/*     */       } 
/*     */       
/* 464 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 469 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_175422_a(EntityPlayer p_175422_1_, int p_175422_2_) {
/* 475 */     ItemStack itemstack = this.contents[p_175422_2_];
/*     */     
/* 477 */     if (itemstack == null || (this.disabledSlots & 1 << p_175422_2_ + 8) == 0)
/*     */     {
/* 479 */       if (itemstack != null || (this.disabledSlots & 1 << p_175422_2_ + 16) == 0) {
/*     */         
/* 481 */         int i = p_175422_1_.inventory.currentItem;
/* 482 */         ItemStack itemstack1 = p_175422_1_.inventory.getStackInSlot(i);
/*     */         
/* 484 */         if (p_175422_1_.capabilities.isCreativeMode && (itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.air)) && itemstack1 != null) {
/*     */           
/* 486 */           ItemStack itemstack3 = itemstack1.copy();
/* 487 */           itemstack3.stackSize = 1;
/* 488 */           setCurrentItemOrArmor(p_175422_2_, itemstack3);
/*     */         }
/* 490 */         else if (itemstack1 != null && itemstack1.stackSize > 1) {
/*     */           
/* 492 */           if (itemstack == null)
/*     */           {
/* 494 */             ItemStack itemstack2 = itemstack1.copy();
/* 495 */             itemstack2.stackSize = 1;
/* 496 */             setCurrentItemOrArmor(p_175422_2_, itemstack2);
/* 497 */             itemstack1.stackSize--;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 502 */           setCurrentItemOrArmor(p_175422_2_, itemstack1);
/* 503 */           p_175422_1_.inventory.setInventorySlotContents(i, itemstack);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 514 */     if (this.worldObj.isRemote)
/*     */     {
/* 516 */       return false;
/*     */     }
/* 518 */     if (DamageSource.outOfWorld.equals(source)) {
/*     */       
/* 520 */       setDead();
/* 521 */       return false;
/*     */     } 
/* 523 */     if (!isEntityInvulnerable(source) && !this.canInteract && !hasMarker()) {
/*     */       
/* 525 */       if (source.isExplosion()) {
/*     */         
/* 527 */         dropContents();
/* 528 */         setDead();
/* 529 */         return false;
/*     */       } 
/* 531 */       if (DamageSource.inFire.equals(source)) {
/*     */         
/* 533 */         if (!isBurning()) {
/*     */           
/* 535 */           setFire(5);
/*     */         }
/*     */         else {
/*     */           
/* 539 */           damageArmorStand(0.15F);
/*     */         } 
/*     */         
/* 542 */         return false;
/*     */       } 
/* 544 */       if (DamageSource.onFire.equals(source) && getHealth() > 0.5F) {
/*     */         
/* 546 */         damageArmorStand(4.0F);
/* 547 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 551 */       boolean flag = "arrow".equals(source.getDamageType());
/* 552 */       boolean flag1 = "player".equals(source.getDamageType());
/*     */       
/* 554 */       if (!flag1 && !flag)
/*     */       {
/* 556 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 560 */       if (source.getSourceOfDamage() instanceof net.minecraft.entity.projectile.EntityArrow)
/*     */       {
/* 562 */         source.getSourceOfDamage().setDead();
/*     */       }
/*     */       
/* 565 */       if (source.getEntity() instanceof EntityPlayer && !((EntityPlayer)source.getEntity()).capabilities.allowEdit)
/*     */       {
/* 567 */         return false;
/*     */       }
/* 569 */       if (source.isCreativePlayer()) {
/*     */         
/* 571 */         playParticles();
/* 572 */         setDead();
/* 573 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 577 */       long i = this.worldObj.getTotalWorldTime();
/*     */       
/* 579 */       if (i - this.punchCooldown > 5L && !flag) {
/*     */         
/* 581 */         this.punchCooldown = i;
/*     */       }
/*     */       else {
/*     */         
/* 585 */         dropBlock();
/* 586 */         playParticles();
/* 587 */         setDead();
/*     */       } 
/*     */       
/* 590 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 597 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 607 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/* 609 */     if (Double.isNaN(d0) || d0 == 0.0D)
/*     */     {
/* 611 */       d0 = 4.0D;
/*     */     }
/*     */     
/* 614 */     d0 *= 64.0D;
/* 615 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void playParticles() {
/* 620 */     if (this.worldObj instanceof WorldServer)
/*     */     {
/* 622 */       ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + this.height / 1.5D, this.posZ, 10, (this.width / 4.0F), (this.height / 4.0F), (this.width / 4.0F), 0.05D, new int[] { Block.getStateId(Blocks.planks.getDefaultState()) });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void damageArmorStand(float p_175406_1_) {
/* 628 */     float f = getHealth();
/* 629 */     f -= p_175406_1_;
/*     */     
/* 631 */     if (f <= 0.5F) {
/*     */       
/* 633 */       dropContents();
/* 634 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/* 638 */       setHealth(f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropBlock() {
/* 644 */     Block.spawnAsEntity(this.worldObj, new BlockPos((Entity)this), new ItemStack((Item)Items.armor_stand));
/* 645 */     dropContents();
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropContents() {
/* 650 */     for (int i = 0; i < this.contents.length; i++) {
/*     */       
/* 652 */       if (this.contents[i] != null && (this.contents[i]).stackSize > 0) {
/*     */         
/* 654 */         if (this.contents[i] != null)
/*     */         {
/* 656 */           Block.spawnAsEntity(this.worldObj, (new BlockPos((Entity)this)).up(), this.contents[i]);
/*     */         }
/*     */         
/* 659 */         this.contents[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/* 666 */     this.prevRenderYawOffset = this.prevRotationYaw;
/* 667 */     this.renderYawOffset = this.rotationYaw;
/* 668 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 673 */     return isChild() ? (this.height * 0.5F) : (this.height * 0.9F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntityWithHeading(float strafe, float forward) {
/* 681 */     if (!hasNoGravity())
/*     */     {
/* 683 */       super.moveEntityWithHeading(strafe, forward);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 692 */     super.onUpdate();
/* 693 */     Rotations rotations = this.dataWatcher.getWatchableObjectRotations(11);
/*     */     
/* 695 */     if (!this.headRotation.equals(rotations))
/*     */     {
/* 697 */       setHeadRotation(rotations);
/*     */     }
/*     */     
/* 700 */     Rotations rotations1 = this.dataWatcher.getWatchableObjectRotations(12);
/*     */     
/* 702 */     if (!this.bodyRotation.equals(rotations1))
/*     */     {
/* 704 */       setBodyRotation(rotations1);
/*     */     }
/*     */     
/* 707 */     Rotations rotations2 = this.dataWatcher.getWatchableObjectRotations(13);
/*     */     
/* 709 */     if (!this.leftArmRotation.equals(rotations2))
/*     */     {
/* 711 */       setLeftArmRotation(rotations2);
/*     */     }
/*     */     
/* 714 */     Rotations rotations3 = this.dataWatcher.getWatchableObjectRotations(14);
/*     */     
/* 716 */     if (!this.rightArmRotation.equals(rotations3))
/*     */     {
/* 718 */       setRightArmRotation(rotations3);
/*     */     }
/*     */     
/* 721 */     Rotations rotations4 = this.dataWatcher.getWatchableObjectRotations(15);
/*     */     
/* 723 */     if (!this.leftLegRotation.equals(rotations4))
/*     */     {
/* 725 */       setLeftLegRotation(rotations4);
/*     */     }
/*     */     
/* 728 */     Rotations rotations5 = this.dataWatcher.getWatchableObjectRotations(16);
/*     */     
/* 730 */     if (!this.rightLegRotation.equals(rotations5))
/*     */     {
/* 732 */       setRightLegRotation(rotations5);
/*     */     }
/*     */     
/* 735 */     boolean flag = hasMarker();
/*     */     
/* 737 */     if (!this.field_181028_bj && flag) {
/*     */       
/* 739 */       func_181550_a(false);
/*     */     }
/*     */     else {
/*     */       
/* 743 */       if (!this.field_181028_bj || flag) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 748 */       func_181550_a(true);
/*     */     } 
/*     */     
/* 751 */     this.field_181028_bj = flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181550_a(boolean p_181550_1_) {
/* 756 */     double d0 = this.posX;
/* 757 */     double d1 = this.posY;
/* 758 */     double d2 = this.posZ;
/*     */     
/* 760 */     if (p_181550_1_) {
/*     */       
/* 762 */       setSize(0.5F, 1.975F);
/*     */     }
/*     */     else {
/*     */       
/* 766 */       setSize(0.0F, 0.0F);
/*     */     } 
/*     */     
/* 769 */     setPosition(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updatePotionMetadata() {
/* 778 */     setInvisible(this.canInteract);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 783 */     this.canInteract = invisible;
/* 784 */     super.setInvisible(invisible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChild() {
/* 792 */     return isSmall();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillCommand() {
/* 800 */     setDead();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImmuneToExplosions() {
/* 805 */     return isInvisible();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setSmall(boolean p_175420_1_) {
/* 810 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 812 */     if (p_175420_1_) {
/*     */       
/* 814 */       b0 = (byte)(b0 | 0x1);
/*     */     }
/*     */     else {
/*     */       
/* 818 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 821 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSmall() {
/* 826 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setNoGravity(boolean p_175425_1_) {
/* 831 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 833 */     if (p_175425_1_) {
/*     */       
/* 835 */       b0 = (byte)(b0 | 0x2);
/*     */     }
/*     */     else {
/*     */       
/* 839 */       b0 = (byte)(b0 & 0xFFFFFFFD);
/*     */     } 
/*     */     
/* 842 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoGravity() {
/* 847 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x2) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setShowArms(boolean p_175413_1_) {
/* 852 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 854 */     if (p_175413_1_) {
/*     */       
/* 856 */       b0 = (byte)(b0 | 0x4);
/*     */     }
/*     */     else {
/*     */       
/* 860 */       b0 = (byte)(b0 & 0xFFFFFFFB);
/*     */     } 
/*     */     
/* 863 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getShowArms() {
/* 868 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setNoBasePlate(boolean p_175426_1_) {
/* 873 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 875 */     if (p_175426_1_) {
/*     */       
/* 877 */       b0 = (byte)(b0 | 0x8);
/*     */     }
/*     */     else {
/*     */       
/* 881 */       b0 = (byte)(b0 & 0xFFFFFFF7);
/*     */     } 
/*     */     
/* 884 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoBasePlate() {
/* 889 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setMarker(boolean p_181027_1_) {
/* 897 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 899 */     if (p_181027_1_) {
/*     */       
/* 901 */       b0 = (byte)(b0 | 0x10);
/*     */     }
/*     */     else {
/*     */       
/* 905 */       b0 = (byte)(b0 & 0xFFFFFFEF);
/*     */     } 
/*     */     
/* 908 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMarker() {
/* 917 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x10) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeadRotation(Rotations p_175415_1_) {
/* 922 */     this.headRotation = p_175415_1_;
/* 923 */     this.dataWatcher.updateObject(11, p_175415_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBodyRotation(Rotations p_175424_1_) {
/* 928 */     this.bodyRotation = p_175424_1_;
/* 929 */     this.dataWatcher.updateObject(12, p_175424_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLeftArmRotation(Rotations p_175405_1_) {
/* 934 */     this.leftArmRotation = p_175405_1_;
/* 935 */     this.dataWatcher.updateObject(13, p_175405_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRightArmRotation(Rotations p_175428_1_) {
/* 940 */     this.rightArmRotation = p_175428_1_;
/* 941 */     this.dataWatcher.updateObject(14, p_175428_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLeftLegRotation(Rotations p_175417_1_) {
/* 946 */     this.leftLegRotation = p_175417_1_;
/* 947 */     this.dataWatcher.updateObject(15, p_175417_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRightLegRotation(Rotations p_175427_1_) {
/* 952 */     this.rightLegRotation = p_175427_1_;
/* 953 */     this.dataWatcher.updateObject(16, p_175427_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getHeadRotation() {
/* 958 */     return this.headRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getBodyRotation() {
/* 963 */     return this.bodyRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getLeftArmRotation() {
/* 968 */     return this.leftArmRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getRightArmRotation() {
/* 973 */     return this.rightArmRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getLeftLegRotation() {
/* 978 */     return this.leftLegRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getRightLegRotation() {
/* 983 */     return this.rightLegRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 991 */     return (super.canBeCollidedWith() && !hasMarker());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */