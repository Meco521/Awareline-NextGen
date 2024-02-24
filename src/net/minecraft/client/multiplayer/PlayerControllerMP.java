/*     */ package net.minecraft.client.multiplayer;
/*     */ import awareline.main.event.Event;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.world.BlockBreakEvent;
/*     */ import awareline.main.event.events.world.BlockDamageEvent;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class PlayerControllerMP {
/*  32 */   private BlockPos currentBlock = new BlockPos(-1, -1, -1);
/*     */ 
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */ 
/*     */ 
/*     */   
/*     */   private final NetHandlerPlayClient netClientHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack currentItemHittingBlock;
/*     */ 
/*     */ 
/*     */   
/*     */   public float curBlockDamageMP;
/*     */ 
/*     */ 
/*     */   
/*     */   private float stepSoundTickCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   public int blockHitDelay;
/*     */ 
/*     */   
/*     */   public boolean isHittingBlock;
/*     */ 
/*     */   
/*  62 */   private WorldSettings.GameType currentGameType = WorldSettings.GameType.SURVIVAL;
/*     */ 
/*     */   
/*     */   private int currentPlayerItem;
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerControllerMP(Minecraft mcIn, NetHandlerPlayClient netHandler) {
/*  70 */     this.mc = mcIn;
/*  71 */     this.netClientHandler = netHandler;
/*     */   }
/*     */   
/*     */   public static void clickBlockCreative(Minecraft mcIn, PlayerControllerMP playerController, BlockPos pos, EnumFacing facing) {
/*  75 */     if (!mcIn.theWorld.extinguishFire((EntityPlayer)mcIn.thePlayer, pos, facing)) {
/*  76 */       playerController.onPlayerDestroyBlock(pos, facing);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerCapabilities(EntityPlayer player) {
/*  86 */     this.currentGameType.configurePlayerCapabilities(player.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  93 */     return (this.currentGameType == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType type) {
/* 102 */     this.currentGameType = type;
/* 103 */     this.currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flipPlayer(EntityPlayer playerIn) {
/* 110 */     playerIn.rotationYaw = -180.0F;
/*     */   }
/*     */   
/*     */   public boolean shouldDrawHUD() {
/* 114 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlayerDestroyBlock(BlockPos pos, EnumFacing side) {
/* 121 */     if (this.currentGameType.isAdventure()) {
/* 122 */       if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 123 */         return false;
/*     */       }
/*     */       
/* 126 */       if (!this.mc.thePlayer.isAllowEdit()) {
/* 127 */         Block block = this.mc.theWorld.getBlockState(pos).getBlock();
/* 128 */         ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/*     */         
/* 130 */         if (itemstack == null) {
/* 131 */           return false;
/*     */         }
/*     */         
/* 134 */         if (!itemstack.canDestroy(block)) {
/* 135 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword) {
/* 141 */       return false;
/*     */     }
/* 143 */     World world = this.mc.theWorld;
/* 144 */     IBlockState iblockstate = world.getBlockState(pos);
/* 145 */     Block block1 = iblockstate.getBlock();
/*     */     
/* 147 */     if (block1.getMaterial() == Material.air) {
/* 148 */       return false;
/*     */     }
/* 150 */     world.playAuxSFX(2001, pos, Block.getStateId(iblockstate));
/* 151 */     boolean flag = world.setBlockToAir(pos);
/*     */     
/* 153 */     if (flag) {
/* 154 */       block1.onBlockDestroyedByPlayer(world, pos, iblockstate);
/*     */     }
/*     */     
/* 157 */     this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
/*     */     
/* 159 */     if (!this.currentGameType.isCreative()) {
/*     */       
/* 161 */       ItemStack itemstack1 = this.mc.thePlayer.getHeldItem();
/*     */       
/* 163 */       if (itemstack1 != null) {
/* 164 */         itemstack1.onBlockDestroyed(world, block1, pos, (EntityPlayer)this.mc.thePlayer);
/*     */         
/* 166 */         if (itemstack1.stackSize == 0) {
/* 167 */           this.mc.thePlayer.destroyCurrentEquippedItem();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 172 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/* 181 */     if (this.currentGameType.isAdventure()) {
/* 182 */       if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 183 */         return false;
/*     */       }
/*     */       
/* 186 */       if (!this.mc.thePlayer.isAllowEdit()) {
/* 187 */         Block block = this.mc.theWorld.getBlockState(loc).getBlock();
/*     */         
/* 189 */         ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/*     */         
/* 191 */         if (itemstack == null) {
/* 192 */           return false;
/*     */         }
/*     */         
/* 195 */         if (!itemstack.canDestroy(block)) {
/* 196 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     if (!this.mc.theWorld.getWorldBorder().contains(loc)) {
/* 202 */       return false;
/*     */     }
/* 204 */     BlockBreakEvent event = new BlockBreakEvent(loc);
/* 205 */     EventManager.call((Event)event);
/*     */     
/* 207 */     if (event.isCancelled()) {
/* 208 */       return false;
/*     */     }
/*     */     
/* 211 */     if (this.currentGameType.isCreative()) {
/* 212 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 213 */       clickBlockCreative(this.mc, this, loc, face);
/* 214 */       this.blockHitDelay = 5;
/* 215 */     } else if (!this.isHittingBlock || !isHittingPosition(loc)) {
/* 216 */       if (this.isHittingBlock) {
/* 217 */         this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, face));
/*     */       }
/*     */       
/* 220 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 221 */       Block block1 = this.mc.theWorld.getBlockState(loc).getBlock();
/* 222 */       boolean flag = (block1.getMaterial() != Material.air);
/*     */       
/* 224 */       if (flag && this.curBlockDamageMP == 0.0F) {
/* 225 */         block1.onBlockClicked(this.mc.theWorld, loc, (EntityPlayer)this.mc.thePlayer);
/*     */       }
/* 227 */       BlockDamageEvent bdEvent = new BlockDamageEvent(block1, this.mc.thePlayer, this.mc.thePlayer.worldObj, loc);
/* 228 */       EventManager.call((Event)bdEvent);
/* 229 */       if (flag && bdEvent.getRelativeBlockHardness() >= 1.0F) {
/* 230 */         onPlayerDestroyBlock(loc, face);
/*     */       } else {
/* 232 */         this.isHittingBlock = true;
/* 233 */         this.currentBlock = loc;
/* 234 */         this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
/*     */         
/* 236 */         this.curBlockDamageMP = 0.0F;
/* 237 */         this.stepSoundTickCounter = 0.0F;
/* 238 */         this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetBlockRemoving() {
/* 250 */     if (this.isHittingBlock) {
/* 251 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
/* 252 */       this.isHittingBlock = false;
/* 253 */       this.curBlockDamageMP = 0.0F;
/* 254 */       this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, -1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/* 259 */     syncCurrentPlayItem();
/*     */     
/* 261 */     if (this.blockHitDelay > 0) {
/* 262 */       this.blockHitDelay--;
/* 263 */       return true;
/* 264 */     }  if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(posBlock)) {
/* 265 */       this.blockHitDelay = 5;
/* 266 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, posBlock, directionFacing));
/* 267 */       clickBlockCreative(this.mc, this, posBlock, directionFacing);
/* 268 */       return true;
/* 269 */     }  if (isHittingPosition(posBlock)) {
/* 270 */       Block block = this.mc.theWorld.getBlockState(posBlock).getBlock();
/*     */       
/* 272 */       if (block.getMaterial() == Material.air) {
/* 273 */         this.isHittingBlock = false;
/* 274 */         return false;
/*     */       } 
/* 276 */       BlockDamageEvent bdEvent = new BlockDamageEvent(block, this.mc.thePlayer, this.mc.thePlayer.worldObj, posBlock);
/* 277 */       EventManager.call((Event)bdEvent);
/* 278 */       this.curBlockDamageMP += bdEvent.getRelativeBlockHardness();
/*     */ 
/*     */       
/* 281 */       if (this.stepSoundTickCounter % 4.0F == 0.0F) {
/* 282 */         this.mc.getSoundHandler().playSound((ISound)new PositionedSoundRecord(new ResourceLocation(block.stepSound.getStepSound()), (block.stepSound.getVolume() + 1.0F) / 8.0F, block.stepSound.getFrequency() * 0.5F, posBlock.getX() + 0.5F, posBlock.getY() + 0.5F, posBlock.getZ() + 0.5F));
/*     */       }
/*     */       
/* 285 */       this.stepSoundTickCounter++;
/*     */       
/* 287 */       if (this.curBlockDamageMP >= 1.0F) {
/* 288 */         this.isHittingBlock = false;
/* 289 */         this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, posBlock, directionFacing));
/* 290 */         onPlayerDestroyBlock(posBlock, directionFacing);
/* 291 */         this.curBlockDamageMP = 0.0F;
/* 292 */         this.stepSoundTickCounter = 0.0F;
/* 293 */         this.blockHitDelay = 5;
/*     */       } 
/*     */       
/* 296 */       this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
/* 297 */       return true;
/*     */     } 
/*     */     
/* 300 */     return clickBlock(posBlock, directionFacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockReachDistance() {
/* 308 */     return this.currentGameType.isCreative() ? 5.0F : 4.5F;
/*     */   }
/*     */   
/*     */   public void updateController() {
/* 312 */     syncCurrentPlayItem();
/*     */     
/* 314 */     if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
/* 315 */       this.netClientHandler.getNetworkManager().processReceivedPackets();
/*     */     } else {
/* 317 */       this.netClientHandler.getNetworkManager().checkDisconnected();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isHittingPosition(BlockPos pos) {
/* 323 */     ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/* 324 */     boolean flag = (this.currentItemHittingBlock == null && itemstack == null);
/*     */     
/* 326 */     if (this.currentItemHittingBlock != null && itemstack != null) {
/* 327 */       flag = (itemstack.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.currentItemHittingBlock) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.currentItemHittingBlock.getMetadata()));
/*     */     }
/*     */     
/* 330 */     return (pos.equals(this.currentBlock) && flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void syncCurrentPlayItem() {
/* 337 */     int i = this.mc.thePlayer.inventory.currentItem;
/*     */     
/* 339 */     if (i != this.currentPlayerItem) {
/* 340 */       this.currentPlayerItem = i;
/* 341 */       this.netClientHandler.addToSendQueue((Packet)new C09PacketHeldItemChange(this.currentPlayerItem));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onPlayerRightClick(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 hitVec) {
/* 346 */     syncCurrentPlayItem();
/* 347 */     float f = (float)(hitVec.xCoord - hitPos.getX());
/* 348 */     float f1 = (float)(hitVec.yCoord - hitPos.getY());
/* 349 */     float f2 = (float)(hitVec.zCoord - hitPos.getZ());
/* 350 */     boolean flag = false;
/*     */     
/* 352 */     if (!this.mc.theWorld.getWorldBorder().contains(hitPos)) {
/* 353 */       return false;
/*     */     }
/* 355 */     if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 356 */       IBlockState iblockstate = worldIn.getBlockState(hitPos);
/*     */       
/* 358 */       if ((!player.isSneaking() || player.getHeldItem() == null) && iblockstate.getBlock().onBlockActivated(worldIn, hitPos, iblockstate, (EntityPlayer)player, side, f, f1, f2)) {
/* 359 */         flag = true;
/*     */       }
/*     */       
/* 362 */       if (!flag && heldStack != null && heldStack.getItem() instanceof ItemBlock) {
/* 363 */         ItemBlock itemblock = (ItemBlock)heldStack.getItem();
/*     */         
/* 365 */         if (!itemblock.canPlaceBlockOnSide(worldIn, hitPos, side, (EntityPlayer)player, heldStack)) {
/* 366 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 371 */     this.netClientHandler.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(hitPos, side.getIndex(), player.inventory.getCurrentItem(), f, f1, f2));
/*     */     
/* 373 */     if (!flag && this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 374 */       if (heldStack == null)
/* 375 */         return false; 
/* 376 */       if (this.currentGameType.isCreative()) {
/* 377 */         int i = heldStack.getMetadata();
/* 378 */         int j = heldStack.stackSize;
/* 379 */         boolean flag1 = heldStack.onItemUse((EntityPlayer)player, worldIn, hitPos, side, f, f1, f2);
/* 380 */         heldStack.setItemDamage(i);
/* 381 */         heldStack.stackSize = j;
/* 382 */         return flag1;
/*     */       } 
/* 384 */       return heldStack.onItemUse((EntityPlayer)player, worldIn, hitPos, side, f, f1, f2);
/*     */     } 
/*     */     
/* 387 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendUseItem(EntityPlayer playerIn, World worldIn, ItemStack itemStackIn) {
/* 396 */     if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
/* 397 */       return false;
/*     */     }
/* 399 */     syncCurrentPlayItem();
/* 400 */     this.netClientHandler.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(playerIn.inventory.getCurrentItem()));
/* 401 */     int i = itemStackIn.stackSize;
/* 402 */     ItemStack itemstack = itemStackIn.useItemRightClick(worldIn, playerIn);
/*     */     
/* 404 */     if (itemstack != itemStackIn || (itemstack != null && itemstack.stackSize != i)) {
/* 405 */       playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = itemstack;
/*     */       
/* 407 */       if (itemstack.stackSize == 0) {
/* 408 */         playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = null;
/*     */       }
/*     */       
/* 411 */       return true;
/*     */     } 
/* 413 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter statWriter) {
/* 419 */     return new EntityPlayerSP(this.mc, worldIn, this.netClientHandler, statWriter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntity(EntityPlayer playerIn, Entity targetEntity) {
/* 426 */     syncCurrentPlayItem();
/* 427 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.ATTACK));
/*     */     
/* 429 */     if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/* 430 */       playerIn.attackTargetEntityWithCurrentItem(targetEntity);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactWithEntitySendPacket(EntityPlayer playerIn, Entity targetEntity) {
/* 438 */     syncCurrentPlayItem();
/* 439 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.INTERACT));
/* 440 */     return (this.currentGameType != WorldSettings.GameType.SPECTATOR && playerIn.interactWith(targetEntity));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerRightClickingOnEntity(EntityPlayer player, Entity entityIn, MovingObjectPosition movingObject) {
/* 451 */     syncCurrentPlayItem();
/* 452 */     Vec3 vec3 = new Vec3(movingObject.hitVec.xCoord - entityIn.posX, movingObject.hitVec.yCoord - entityIn.posY, movingObject.hitVec.zCoord - entityIn.posZ);
/* 453 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(entityIn, vec3));
/* 454 */     return (this.currentGameType != WorldSettings.GameType.SPECTATOR && entityIn.interactAt(player, vec3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn) {
/* 461 */     short short1 = playerIn.openContainer.getNextTransactionID(playerIn.inventory);
/* 462 */     ItemStack itemstack = playerIn.openContainer.slotClick(slotId, mouseButtonClicked, mode, playerIn);
/* 463 */     this.netClientHandler.addToSendQueue((Packet)new C0EPacketClickWindow(windowId, slotId, mouseButtonClicked, mode, itemstack, short1));
/* 464 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEnchantPacket(int windowID, int button) {
/* 475 */     this.netClientHandler.addToSendQueue((Packet)new C11PacketEnchantItem(windowID, button));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSlotPacket(ItemStack itemStackIn, int slotId) {
/* 482 */     if (this.currentGameType.isCreative()) {
/* 483 */       this.netClientHandler.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(slotId, itemStackIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketDropItem(ItemStack itemStackIn) {
/* 491 */     if (this.currentGameType.isCreative() && itemStackIn != null) {
/* 492 */       this.netClientHandler.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(-1, itemStackIn));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onStoppedUsingItem(EntityPlayer playerIn) {
/* 497 */     syncCurrentPlayItem();
/* 498 */     this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 499 */     playerIn.stopUsingItem();
/*     */   }
/*     */   
/*     */   public boolean gameIsSurvivalOrAdventure() {
/* 503 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotCreative() {
/* 510 */     return !this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInCreativeMode() {
/* 517 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedReach() {
/* 524 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRidingHorse() {
/* 531 */     return (this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof net.minecraft.entity.passive.EntityHorse);
/*     */   }
/*     */   
/*     */   public boolean isSpectatorMode() {
/* 535 */     return (this.currentGameType == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */   
/*     */   public WorldSettings.GameType getCurrentGameType() {
/* 539 */     return this.currentGameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsHittingBlock() {
/* 546 */     if (AbortBreaking.getInstance.isEnabled()) {
/* 547 */       return false;
/*     */     }
/* 549 */     return this.isHittingBlock;
/*     */   }
/*     */   
/*     */   public boolean isBreakingBlock() {
/* 553 */     return (this.mc.gameSettings.keyBindAttack.isKeyDown() && this.mc.thePlayer.isAllowEdit() && this.mc.objectMouseOver.typeOfHit
/* 554 */       .equals(MovingObjectPosition.MovingObjectType.BLOCK));
/*     */   }
/*     */   
/*     */   public void attackEntityNoSlow(Entity targetEntity, boolean syncItem) {
/* 558 */     if (syncItem) {
/* 559 */       syncCurrentPlayItem();
/*     */     }
/* 561 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.ATTACK));
/*     */   }
/*     */   
/*     */   public float getCurBlockDamageMP() {
/* 565 */     return this.curBlockDamageMP;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\multiplayer\PlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */