/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import awareline.main.event.Event;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.LBEvents.EventMotion;
/*     */ import awareline.main.event.events.LBEvents.EventMotionUpdate;
/*     */ import awareline.main.event.events.display.DisplayChestGuiEvent;
/*     */ import awareline.main.event.events.misc.EventChat;
/*     */ import awareline.main.event.events.misc.EventSendMessage;
/*     */ import awareline.main.event.events.world.moveEvents.SlowdownEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.EventUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*     */ import awareline.main.mod.implement.misc.ChatPostfix;
/*     */ import awareline.main.mod.implement.move.Sprint;
/*     */ import awareline.main.mod.implement.world.utils.Vector2f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.MovingSoundMinecartRiding;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiCommandBlock;
/*     */ import net.minecraft.client.gui.GuiEnchantment;
/*     */ import net.minecraft.client.gui.GuiHopper;
/*     */ import net.minecraft.client.gui.GuiMerchant;
/*     */ import net.minecraft.client.gui.GuiRepair;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenBook;
/*     */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*     */ import net.minecraft.client.gui.inventory.GuiBrewingStand;
/*     */ import net.minecraft.client.gui.inventory.GuiChest;
/*     */ import net.minecraft.client.gui.inventory.GuiCrafting;
/*     */ import net.minecraft.client.gui.inventory.GuiDispenser;
/*     */ import net.minecraft.client.gui.inventory.GuiEditSign;
/*     */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*     */ import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.network.play.client.C0CPacketInput;
/*     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
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
/*     */ public class EntityPlayerSP
/*     */   extends AbstractClientPlayer
/*     */ {
/*     */   public final NetHandlerPlayClient sendQueue;
/*     */   private final StatFileWriter statWriter;
/*     */   public int offGroundTicks;
/*     */   public int onGroundTicks;
/*     */   public double lastReportedPosX;
/*     */   public double lastReportedPosY;
/*     */   public double lastReportedPosZ;
/*     */   public boolean serverSprintState;
/*     */   public MovementInput movementInput;
/*     */   public int sprintingTicksLeft;
/*     */   public float renderArmYaw;
/*     */   public float renderArmPitch;
/*     */   public float prevRenderArmYaw;
/*     */   public float prevRenderArmPitch;
/*     */   public int horseJumpPowerCounter;
/*     */   public float horseJumpPower;
/*     */   public float timeInPortal;
/*     */   public float prevTimeInPortal;
/*     */   protected Minecraft mc;
/*     */   protected int sprintToggleTimer;
/*     */   private float lastReportedYaw;
/*     */   private float lastReportedPitch;
/*     */   private boolean serverSneakState;
/*     */   private int positionUpdateTicks;
/*     */   private boolean hasValidHealth;
/*     */   private String clientBrand;
/*     */   
/*     */   public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile) {
/* 121 */     super(worldIn, netHandler.getGameProfile());
/* 122 */     this.sendQueue = netHandler;
/* 123 */     this.statWriter = statFile;
/* 124 */     this.mc = mcIn;
/* 125 */     this.dimension = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void heal(float healAmount) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTool(BlockPos pos) {
/* 142 */     Block block = this.mc.theWorld.getBlockState(pos).getBlock();
/* 143 */     float strength = 1.0F;
/* 144 */     int slot = -1;
/*     */     
/* 146 */     for (int i = 0; i < 9; i++) {
/* 147 */       ItemStack itemStack = this.inventory.getStackInSlot(i);
/*     */       
/* 149 */       if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
/* 150 */         slot = i;
/* 151 */         strength = itemStack.getStrVsBlock(block);
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     if (slot != -1 && this.mc.thePlayer.inventory.getStackInSlot(this.inventory.currentItem) != this.inventory.getStackInSlot(slot)) {
/* 156 */       this.inventory.currentItem = slot;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mountEntity(Entity entityIn) {
/* 164 */     super.mountEntity(entityIn);
/*     */     
/* 166 */     if (entityIn instanceof EntityMinecart) {
/* 167 */       this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 175 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
/* 176 */       EventUpdate e = new EventUpdate(this.posX, this.posY, this.posZ);
/* 177 */       EventManager.call((Event)e);
/* 178 */       super.onUpdate();
/*     */       
/* 180 */       if (isRiding()) {
/* 181 */         this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/* 182 */         this.sendQueue.addToSendQueue((Packet)new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
/*     */       } else {
/* 184 */         onUpdateWalkingPlayer();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateWalkingPlayer() {
/* 193 */     if (this.onGround) {
/* 194 */       this.offGroundTicks = 0;
/* 195 */       this.onGroundTicks++;
/*     */     } else {
/* 197 */       this.onGroundTicks = 0;
/* 198 */       this.offGroundTicks++;
/*     */     } 
/*     */     
/* 201 */     EventMotion eventMotion = new EventMotion(EventMotion.Type.PRE);
/* 202 */     EventManager.call((Event)eventMotion);
/*     */ 
/*     */     
/* 205 */     EventPreUpdate eventPre = new EventPreUpdate(this.rotationYaw, this.rotationPitch, this.posX, (getEntityBoundingBox()).minY, this.posZ, this.onGround);
/* 206 */     EventManager.call((Event)eventPre);
/* 207 */     if (eventPre.isCancelled()) {
/* 208 */       EventPostUpdate eventPostUpdate = new EventPostUpdate(eventPre.getYaw(), eventPre.getPitch());
/* 209 */       EventManager.call((Event)eventPostUpdate);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 214 */     EventMotionUpdate event = new EventMotionUpdate(eventPre, this.mc.thePlayer.posX, (getEntityBoundingBox()).minY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer.onGround, EventMotionUpdate.Type.PRE);
/* 215 */     EventManager.call((Event)event);
/*     */ 
/*     */     
/* 218 */     boolean flag = isSprinting();
/*     */     
/* 220 */     MotionUpdateEvent preUpdate = new MotionUpdateEvent(eventPre, this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround, MotionUpdateEvent.State.PRE);
/* 221 */     MotionUpdateEvent postUpdate = new MotionUpdateEvent(MotionUpdateEvent.State.POST);
/* 222 */     EventManager.call((Event)preUpdate);
/* 223 */     if (preUpdate.isCancelled()) {
/* 224 */       EventManager.call((Event)postUpdate);
/*     */     }
/*     */     
/* 227 */     if (flag != this.serverSprintState) {
/* 228 */       if (flag) {
/* 229 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SPRINTING));
/*     */       } else {
/* 231 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SPRINTING));
/*     */       } 
/*     */       
/* 234 */       this.serverSprintState = flag;
/*     */     } 
/*     */     
/* 237 */     boolean flag1 = isSneaking();
/*     */     
/* 239 */     if (flag1 != this.serverSneakState) {
/* 240 */       if (flag1) {
/* 241 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SNEAKING));
/*     */       } else {
/* 243 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SNEAKING));
/*     */       } 
/*     */       
/* 246 */       this.serverSneakState = flag1;
/*     */     } 
/*     */     
/* 249 */     if (isCurrentViewEntity()) {
/*     */       
/* 251 */       double d0 = eventPre.getX() - this.lastReportedPosX;
/* 252 */       double d1 = eventPre.getY() - this.lastReportedPosY;
/* 253 */       double d2 = eventPre.getZ() - this.lastReportedPosZ;
/* 254 */       double d3 = (eventPre.getYaw() - this.lastReportedYaw);
/* 255 */       double d4 = (eventPre.getPitch() - this.lastReportedPitch);
/* 256 */       boolean flag2 = (d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.positionUpdateTicks >= 20);
/* 257 */       boolean flag3 = (d3 != 0.0D || d4 != 0.0D);
/*     */       
/* 259 */       if (this.ridingEntity == null) {
/* 260 */         if (flag2 && flag3) {
/* 261 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(eventPre.getX(), eventPre.getY(), eventPre
/* 262 */                 .getZ(), eventPre.getYaw(), eventPre.getPitch(), eventPre.isOnGround()));
/* 263 */         } else if (flag2) {
/* 264 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(eventPre.getX(), eventPre.getY(), eventPre
/* 265 */                 .getZ(), eventPre.isOnGround()));
/* 266 */         } else if (flag3) {
/* 267 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(eventPre.getYaw(), eventPre
/* 268 */                 .getPitch(), eventPre.isOnGround()));
/*     */         } else {
/* 270 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(eventPre.isOnGround()));
/*     */         } 
/*     */       } else {
/* 273 */         this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, eventPre
/* 274 */               .getYaw(), eventPre.getPitch(), eventPre.isOnGround()));
/* 275 */         flag2 = false;
/*     */       } 
/*     */       
/* 278 */       this.positionUpdateTicks++;
/*     */       
/* 280 */       if (flag2) {
/* 281 */         this.lastReportedPosX = eventPre.getX();
/* 282 */         this.lastReportedPosY = eventPre.getY();
/* 283 */         this.lastReportedPosZ = eventPre.getZ();
/* 284 */         this.positionUpdateTicks = 0;
/*     */       } 
/*     */       
/* 287 */       if (flag3) {
/* 288 */         this.lastReportedYaw = eventPre.getYaw();
/* 289 */         this.lastReportedPitch = eventPre.getPitch();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 294 */     EventManager.call((Event)postUpdate);
/*     */     
/* 296 */     EventPostUpdate eventPost = new EventPostUpdate(eventPre.getYaw(), eventPre.getPitch());
/* 297 */     EventManager.call((Event)eventPost);
/*     */     
/* 299 */     EventMotion eventMotionPost = new EventMotion(EventMotion.Type.POST);
/* 300 */     EventManager.call((Event)eventMotionPost);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityItem dropOneItem(boolean dropAll) {
/* 321 */     C07PacketPlayerDigging.Action c07packetplayerdigging$action = dropAll ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
/* 322 */     this.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(c07packetplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 323 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void joinEntityItemWithWorld(EntityItem itemIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String message) {
/* 336 */     EventChat e = new EventChat(message, null, null);
/* 337 */     EventManager.call((Event)e);
/* 338 */     EventSendMessage eventSendMessage = new EventSendMessage(message);
/* 339 */     EventManager.call((Event)eventSendMessage);
/* 340 */     if (!e.isCancelled() && !eventSendMessage.isCancelled())
/* 341 */       this.sendQueue.addToSendQueue((Packet)new C01PacketChatMessage(message + getChatPostfix())); 
/*     */   }
/*     */   
/*     */   private final String getChatPostfix() {
/* 345 */     if (ChatPostfix.getInstance.isEnabled()) {
/* 346 */       if (ChatPostfix.getInstance.mode.is("A"))
/* 347 */         return "锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷"; 
/* 348 */       if (ChatPostfix.getInstance.mode.is("B"))
/* 349 */         return "......"; 
/* 350 */       if (ChatPostfix.getInstance.mode.is("C"))
/* 351 */         return "锟斤拷"; 
/* 352 */       if (ChatPostfix.getInstance.mode.is("D")) {
/* 353 */         return "锟斤拷";
/*     */       }
/*     */     } 
/* 356 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swingItem() {
/* 363 */     super.swingItem();
/* 364 */     this.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
/*     */   }
/*     */   
/*     */   public void respawnPlayer() {
/* 368 */     this.sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 376 */     if (!isEntityInvulnerable(damageSrc)) {
/* 377 */       setHealth(getHealth() - damageAmount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeScreen() {
/* 385 */     this.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(this.openContainer.windowId));
/* 386 */     closeScreenAndDropStack();
/*     */   }
/*     */   
/*     */   public void closeScreen(GuiScreen current, int windowsID) {
/* 390 */     this.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(windowsID));
/* 391 */     closeScreenAndDropStack(current);
/*     */   }
/*     */   
/*     */   public void closeScreenAndDropStack(GuiScreen screen) {
/* 395 */     this.inventory.setItemStack(null);
/* 396 */     super.closeScreen();
/* 397 */     this.mc.displayGuiScreen(screen);
/*     */   }
/*     */   
/*     */   public void closeScreenAndDropStack() {
/* 401 */     this.inventory.setItemStack((ItemStack)null);
/* 402 */     super.closeScreen();
/* 403 */     this.mc.displayGuiScreen((GuiScreen)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerSPHealth(float health) {
/* 410 */     if (this.hasValidHealth) {
/* 411 */       float f = getHealth() - health;
/*     */       
/* 413 */       if (f <= 0.0F) {
/* 414 */         setHealth(health);
/*     */         
/* 416 */         if (f < 0.0F) {
/* 417 */           this.hurtResistantTime = this.maxHurtResistantTime / 2;
/*     */         }
/*     */       } else {
/* 420 */         this.lastDamage = f;
/* 421 */         setHealth(getHealth());
/* 422 */         this.hurtResistantTime = this.maxHurtResistantTime;
/* 423 */         damageEntity(DamageSource.generic, f);
/* 424 */         this.hurtTime = this.maxHurtTime = 10;
/*     */       } 
/*     */     } else {
/* 427 */       setHealth(health);
/* 428 */       this.hasValidHealth = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStat(StatBase stat, int amount) {
/* 436 */     if (stat != null && 
/* 437 */       stat.isIndependent) {
/* 438 */       super.addStat(stat, amount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerAbilities() {
/* 447 */     this.sendQueue.addToSendQueue((Packet)new C13PacketPlayerAbilities(this.capabilities));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUser() {
/* 454 */     return true;
/*     */   }
/*     */   
/*     */   protected void sendHorseJump() {
/* 458 */     this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.horseJumpPower * 100.0F)));
/*     */   }
/*     */   
/*     */   public void sendHorseInventory() {
/* 462 */     this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
/*     */   }
/*     */   
/*     */   public String getClientBrand() {
/* 466 */     return this.clientBrand;
/*     */   }
/*     */   
/*     */   public void setClientBrand(String brand) {
/* 470 */     this.clientBrand = brand;
/*     */   }
/*     */   
/*     */   public StatFileWriter getStatFileWriter() {
/* 474 */     return this.statWriter;
/*     */   }
/*     */   
/*     */   public void addChatComponentMessage(IChatComponent chatComponent) {
/* 478 */     this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
/*     */   }
/*     */   
/*     */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 482 */     if (this.noClip) {
/* 483 */       return false;
/*     */     }
/* 485 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 486 */     double d0 = x - blockpos.getX();
/* 487 */     double d1 = z - blockpos.getZ();
/*     */     
/* 489 */     if (!isOpenBlockSpace(blockpos)) {
/* 490 */       int i = -1;
/* 491 */       double d2 = 9999.0D;
/*     */       
/* 493 */       if (isOpenBlockSpace(blockpos.west()) && d0 < d2) {
/* 494 */         d2 = d0;
/* 495 */         i = 0;
/*     */       } 
/*     */       
/* 498 */       if (isOpenBlockSpace(blockpos.east()) && 1.0D - d0 < d2) {
/* 499 */         d2 = 1.0D - d0;
/* 500 */         i = 1;
/*     */       } 
/*     */       
/* 503 */       if (isOpenBlockSpace(blockpos.north()) && d1 < d2) {
/* 504 */         d2 = d1;
/* 505 */         i = 4;
/*     */       } 
/*     */       
/* 508 */       if (isOpenBlockSpace(blockpos.south()) && 1.0D - d1 < d2) {
/* 509 */         d2 = 1.0D - d1;
/* 510 */         i = 5;
/*     */       } 
/*     */       
/* 513 */       float f = 0.1F;
/*     */       
/* 515 */       if (i == 0) {
/* 516 */         this.motionX = -f;
/*     */       }
/*     */       
/* 519 */       if (i == 1) {
/* 520 */         this.motionX = f;
/*     */       }
/*     */       
/* 523 */       if (i == 4) {
/* 524 */         this.motionZ = -f;
/*     */       }
/*     */       
/* 527 */       if (i == 5) {
/* 528 */         this.motionZ = f;
/*     */       }
/*     */     } 
/*     */     
/* 532 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOpenBlockSpace(BlockPos pos) {
/* 540 */     return (!this.worldObj.getBlockState(pos).getBlock().isNormalCube() && !this.worldObj.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSprinting(boolean sprinting) {
/* 547 */     super.setSprinting(sprinting);
/* 548 */     this.sprintingTicksLeft = sprinting ? 600 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPStats(float currentXP, int maxXP, int level) {
/* 555 */     this.experience = currentXP;
/* 556 */     this.experienceTotal = maxXP;
/* 557 */     this.experienceLevel = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(IChatComponent component) {
/* 564 */     this.mc.ingameGUI.getChatGUI().printChatMessage(component);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 571 */     return (permLevel <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 579 */     return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
/*     */   }
/*     */   
/*     */   public void playSound(String name, float volume, float pitch) {
/* 583 */     this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isServerWorld() {
/* 590 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isRidingHorse() {
/* 594 */     return (this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled());
/*     */   }
/*     */   
/*     */   public float getHorseJumpPower() {
/* 598 */     return this.horseJumpPower;
/*     */   }
/*     */   
/*     */   public void openEditSign(TileEntitySign signTile) {
/* 602 */     this.mc.displayGuiScreen((GuiScreen)new GuiEditSign(signTile));
/*     */   }
/*     */   
/*     */   public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {
/* 606 */     this.mc.displayGuiScreen((GuiScreen)new GuiCommandBlock(cmdBlockLogic));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayGUIBook(ItemStack bookStack) {
/* 613 */     Item item = bookStack.getItem();
/*     */     
/* 615 */     if (item == Items.writable_book) {
/* 616 */       this.mc.displayGuiScreen((GuiScreen)new GuiScreenBook(this, bookStack, true));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayGUIChest(IInventory chestInventory) {
/* 624 */     String s = (chestInventory instanceof IInteractionObject) ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
/*     */     
/* 626 */     if ("minecraft:chest".equals(s)) {
/* 627 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/* 628 */     } else if ("minecraft:hopper".equals(s)) {
/* 629 */       this.mc.displayGuiScreen((GuiScreen)new GuiHopper(this.inventory, chestInventory));
/* 630 */     } else if ("minecraft:furnace".equals(s)) {
/* 631 */       this.mc.displayGuiScreen((GuiScreen)new GuiFurnace(this.inventory, chestInventory));
/* 632 */     } else if ("minecraft:brewing_stand".equals(s)) {
/* 633 */       this.mc.displayGuiScreen((GuiScreen)new GuiBrewingStand(this.inventory, chestInventory));
/* 634 */     } else if ("minecraft:beacon".equals(s)) {
/* 635 */       this.mc.displayGuiScreen((GuiScreen)new GuiBeacon(this.inventory, chestInventory));
/* 636 */     } else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
/* 637 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/*     */     } else {
/* 639 */       this.mc.displayGuiScreen((GuiScreen)new GuiDispenser(this.inventory, chestInventory));
/*     */     } 
/* 641 */     EventManager.call((Event)new DisplayChestGuiEvent(s));
/*     */   }
/*     */   
/*     */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
/* 645 */     this.mc.displayGuiScreen((GuiScreen)new GuiScreenHorseInventory((IInventory)this.inventory, horseInventory, horse));
/*     */   }
/*     */   
/*     */   public void displayGui(IInteractionObject guiOwner) {
/* 649 */     String s = guiOwner.getGuiID();
/*     */     
/* 651 */     if ("minecraft:crafting_table".equals(s)) {
/* 652 */       this.mc.displayGuiScreen((GuiScreen)new GuiCrafting(this.inventory, this.worldObj));
/* 653 */     } else if ("minecraft:enchanting_table".equals(s)) {
/* 654 */       this.mc.displayGuiScreen((GuiScreen)new GuiEnchantment(this.inventory, this.worldObj, (IWorldNameable)guiOwner));
/* 655 */     } else if ("minecraft:anvil".equals(s)) {
/* 656 */       this.mc.displayGuiScreen((GuiScreen)new GuiRepair(this.inventory, this.worldObj));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void displayVillagerTradeGui(IMerchant villager) {
/* 661 */     this.mc.displayGuiScreen((GuiScreen)new GuiMerchant(this.inventory, villager, this.worldObj));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCriticalHit(Entity entityHit) {
/* 668 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
/*     */   }
/*     */   
/*     */   public void onEnchantmentCritical(Entity entityHit) {
/* 672 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSneaking() {
/* 679 */     boolean flag = (this.movementInput != null) ? this.movementInput.sneak : false;
/* 680 */     return (flag && !this.sleeping);
/*     */   }
/*     */   
/*     */   public void updateEntityActionState() {
/* 684 */     super.updateEntityActionState();
/*     */     
/* 686 */     if (isCurrentViewEntity()) {
/* 687 */       this.moveStrafing = this.movementInput.moveStrafe;
/* 688 */       this.moveForward = this.movementInput.moveForward;
/* 689 */       this.isJumping = this.movementInput.jump;
/* 690 */       this.prevRenderArmYaw = this.renderArmYaw;
/* 691 */       this.prevRenderArmPitch = this.renderArmPitch;
/* 692 */       this.renderArmPitch = (float)(this.renderArmPitch + (this.rotationPitch - this.renderArmPitch) * 0.5D);
/* 693 */       this.renderArmYaw = (float)(this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5D);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isCurrentViewEntity() {
/* 698 */     return (this.mc.getRenderViewEntity() == this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 706 */     if (this.sprintingTicksLeft > 0) {
/* 707 */       this.sprintingTicksLeft--;
/*     */       
/* 709 */       if (this.sprintingTicksLeft == 0 && 
/* 710 */         !Sprint.getInstance.isEnabled()) {
/* 711 */         setSprinting(false);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 716 */     if (this.sprintToggleTimer > 0) {
/* 717 */       this.sprintToggleTimer--;
/*     */     }
/*     */     
/* 720 */     this.prevTimeInPortal = this.timeInPortal;
/*     */     
/* 722 */     if (this.inPortal) {
/* 723 */       if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
/* 724 */         this.mc.displayGuiScreen((GuiScreen)null);
/*     */       }
/*     */       
/* 727 */       if (this.timeInPortal == 0.0F) {
/* 728 */         this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
/*     */       }
/*     */       
/* 731 */       this.timeInPortal += 0.0125F;
/*     */       
/* 733 */       if (this.timeInPortal >= 1.0F) {
/* 734 */         this.timeInPortal = 1.0F;
/*     */       }
/*     */       
/* 737 */       this.inPortal = false;
/* 738 */     } else if (isPotionActive(Potion.confusion) && getActivePotionEffect(Potion.confusion).getDuration() > 60) {
/* 739 */       this.timeInPortal += 0.006666667F;
/*     */       
/* 741 */       if (this.timeInPortal > 1.0F) {
/* 742 */         this.timeInPortal = 1.0F;
/*     */       }
/*     */     } else {
/* 745 */       if (this.timeInPortal > 0.0F) {
/* 746 */         this.timeInPortal -= 0.05F;
/*     */       }
/*     */       
/* 749 */       if (this.timeInPortal < 0.0F) {
/* 750 */         this.timeInPortal = 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 754 */     if (this.timeUntilPortal > 0) {
/* 755 */       this.timeUntilPortal--;
/*     */     }
/*     */     
/* 758 */     boolean flag = this.movementInput.jump;
/* 759 */     boolean flag1 = this.movementInput.sneak;
/* 760 */     float f = 0.8F;
/* 761 */     boolean flag2 = (this.movementInput.moveForward >= f);
/* 762 */     this.movementInput.updatePlayerMoveState();
/*     */     
/* 764 */     if (isUsingItem() && !isRiding()) {
/* 765 */       SlowdownEvent event = new SlowdownEvent();
/* 766 */       EventManager.call((Event)event);
/*     */       
/* 768 */       if (!event.isCancelled()) {
/* 769 */         this.movementInput.moveStrafe *= 0.2F;
/* 770 */         this.movementInput.moveForward *= 0.2F;
/* 771 */         if (!Sprint.getInstance.isEnabled()) {
/* 772 */           this.sprintToggleTimer = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 777 */     pushOutOfBlocks(this.posX - this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ + this.width * 0.35D);
/* 778 */     pushOutOfBlocks(this.posX - this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ - this.width * 0.35D);
/* 779 */     pushOutOfBlocks(this.posX + this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ - this.width * 0.35D);
/* 780 */     pushOutOfBlocks(this.posX + this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ + this.width * 0.35D);
/* 781 */     boolean flag3 = (getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying);
/*     */     
/* 783 */     if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= f && !isSprinting() && flag3 && !isUsingItem() && !isPotionActive(Potion.blindness)) {
/* 784 */       if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
/* 785 */         this.sprintToggleTimer = 7;
/*     */       } else {
/* 787 */         setSprinting(true);
/*     */       } 
/*     */     }
/*     */     
/* 791 */     if (!isSprinting() && this.movementInput.moveForward >= f && flag3 && !isUsingItem() && !isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
/* 792 */       setSprinting(true);
/*     */     }
/*     */     
/* 795 */     if (isSprinting() && !Sprint.getInstance.isEnabled() && (this.movementInput.moveForward < f || this.isCollidedHorizontally || !flag3)) {
/* 796 */       setSprinting(false);
/*     */     }
/*     */     
/* 799 */     if (this.capabilities.allowFlying) {
/* 800 */       if (this.mc.playerController.isSpectatorMode()) {
/* 801 */         if (!this.capabilities.isFlying) {
/* 802 */           this.capabilities.isFlying = true;
/* 803 */           sendPlayerAbilities();
/*     */         } 
/* 805 */       } else if (!flag && this.movementInput.jump) {
/* 806 */         if (this.flyToggleTimer == 0) {
/* 807 */           this.flyToggleTimer = 7;
/*     */         } else {
/* 809 */           this.capabilities.isFlying = !this.capabilities.isFlying;
/* 810 */           sendPlayerAbilities();
/* 811 */           this.flyToggleTimer = 0;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 816 */     if (this.capabilities.isFlying && isCurrentViewEntity()) {
/* 817 */       if (this.movementInput.sneak) {
/* 818 */         this.motionY -= (this.capabilities.getFlySpeed() * 3.0F);
/*     */       }
/*     */       
/* 821 */       if (this.movementInput.jump) {
/* 822 */         this.motionY += (this.capabilities.getFlySpeed() * 3.0F);
/*     */       }
/*     */     } 
/*     */     
/* 826 */     if (isRidingHorse()) {
/* 827 */       if (this.horseJumpPowerCounter < 0) {
/* 828 */         this.horseJumpPowerCounter++;
/*     */         
/* 830 */         if (this.horseJumpPowerCounter == 0) {
/* 831 */           this.horseJumpPower = 0.0F;
/*     */         }
/*     */       } 
/*     */       
/* 835 */       if (flag && !this.movementInput.jump) {
/* 836 */         this.horseJumpPowerCounter = -10;
/* 837 */         sendHorseJump();
/* 838 */       } else if (!flag && this.movementInput.jump) {
/* 839 */         this.horseJumpPowerCounter = 0;
/* 840 */         this.horseJumpPower = 0.0F;
/* 841 */       } else if (flag) {
/* 842 */         this.horseJumpPowerCounter++;
/*     */         
/* 844 */         if (this.horseJumpPowerCounter < 10) {
/* 845 */           this.horseJumpPower = this.horseJumpPowerCounter * 0.1F;
/*     */         } else {
/* 847 */           this.horseJumpPower = 0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 851 */       this.horseJumpPower = 0.0F;
/*     */     } 
/*     */     
/* 854 */     super.onLivingUpdate();
/*     */     
/* 856 */     if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
/* 857 */       this.capabilities.isFlying = false;
/* 858 */       sendPlayerAbilities();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean moving() {
/* 863 */     return (this.moveForward != 0.0F || this.moveStrafing != 0.0F);
/*     */   }
/*     */   
/*     */   public boolean isMoving() {
/* 867 */     return (this.moveForward != 0.0F || this.moveStrafing != 0.0F);
/*     */   }
/*     */   
/*     */   public void swap(int inventorySlot, int hotbarSlot) {
/* 871 */     this.mc.playerController.windowClick(this.inventoryContainer.windowId, inventorySlot, hotbarSlot, 2, this);
/*     */   }
/*     */   
/*     */   public void drop(int slot) {
/* 875 */     this.mc.playerController.windowClick(this.inventoryContainer.windowId, slot, 1, 4, this);
/*     */   }
/*     */   
/*     */   public void shiftClick(int slot) {
/* 879 */     this.mc.playerController.windowClick(this.inventoryContainer.windowId, slot, 0, 1, this);
/*     */   }
/*     */   
/*     */   public double getBySprinting() {
/* 883 */     return isSprinting() ? 0.28700000047683716D : 0.22300000488758087D;
/*     */   }
/*     */   
/*     */   public double getBySprinting(boolean sprint) {
/* 887 */     return sprint ? 0.28700000047683716D : 0.22300000488758087D;
/*     */   }
/*     */   
/*     */   public int getSlotByItem(Item item) {
/* 891 */     for (int i = 0; i < 9; i++) {
/* 892 */       ItemStack stack = this.inventory.getStackInSlot(i);
/*     */       
/* 894 */       if (stack != null && stack.getItem() == item) {
/* 895 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 899 */     return -1;
/*     */   }
/*     */   
/*     */   public double getBaseMotionY() {
/* 903 */     return isPotionActive(Potion.jump) ? (0.419999986886978D + 0.1D * (getActivePotionEffect(Potion.jump).getAmplifier() + 1)) : 0.419999986886978D;
/*     */   }
/*     */   
/*     */   public double getBaseMotionY(double motionY) {
/* 907 */     return isPotionActive(Potion.jump) ? (motionY + 0.1D * (getActivePotionEffect(Potion.jump).getAmplifier() + 1)) : motionY;
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed() {
/* 911 */     double baseSpeed = getBySprinting();
/* 912 */     if (isPotionActive(Potion.moveSpeed)) {
/*     */       
/* 914 */       int amplifier = getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 - (isPotionActive(Potion.moveSlowdown) ? (getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1) : 0);
/* 915 */       baseSpeed *= 1.0D + 0.2D * amplifier;
/*     */     } 
/*     */     
/* 918 */     return baseSpeed;
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed(double multiplier) {
/* 922 */     double baseSpeed = getBySprinting();
/* 923 */     if (isPotionActive(Potion.moveSpeed)) {
/*     */       
/* 925 */       int amplifier = getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 - (isPotionActive(Potion.moveSlowdown) ? (getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1) : 0);
/* 926 */       baseSpeed *= 1.0D + multiplier * amplifier;
/*     */     } 
/*     */     
/* 929 */     return baseSpeed;
/*     */   }
/*     */   
/*     */   public void resetLastTickDistance() {
/* 933 */     this.prevPosX = this.posX;
/* 934 */     this.prevPosZ = this.posZ;
/*     */   }
/*     */   
/*     */   public double getJumpMotion() {
/* 938 */     return 0.41999998688697815D + 0.1D * b(Potion.jump);
/*     */   }
/*     */   
/*     */   public double m() {
/* 942 */     double d = 0.28630206268501246D;
/* 943 */     if (isPotionActive(Potion.moveSpeed)) {
/* 944 */       int n = getActivePotionEffect(Potion.moveSpeed).getAmplifier() - getActivePotionEffect(Potion.moveSlowdown).getAmplifier();
/* 945 */       d *= 1.0D + 0.2D * n;
/*     */     } 
/* 947 */     return d;
/*     */   }
/*     */   
/*     */   public void setSpeed(double speed) {
/* 951 */     double forward = this.moveForward, strafe = this.moveStrafing;
/* 952 */     double yaw = this.rotationYaw;
/* 953 */     boolean isMovingForward = (forward > 0.0D), isMovingBackward = (forward < 0.0D), isMovingRight = (strafe > 0.0D), isMovingLeft = (strafe < 0.0D), isMovingSideways = (isMovingLeft || isMovingRight), isMovingStraight = (isMovingForward || isMovingBackward);
/* 954 */     if (isMoving()) {
/* 955 */       if (isMovingForward && !isMovingSideways) {
/* 956 */         yaw += 0.0D;
/* 957 */       } else if (isMovingBackward && !isMovingSideways) {
/* 958 */         yaw += 180.0D;
/* 959 */       } else if (isMovingForward && isMovingLeft) {
/* 960 */         yaw += 45.0D;
/* 961 */       } else if (isMovingForward) {
/* 962 */         yaw -= 45.0D;
/* 963 */       } else if (!isMovingStraight && isMovingLeft) {
/* 964 */         yaw += 90.0D;
/* 965 */       } else if (!isMovingStraight && isMovingRight) {
/* 966 */         yaw -= 90.0D;
/* 967 */       } else if (isMovingBackward && isMovingLeft) {
/* 968 */         yaw += 126.0D;
/* 969 */       } else if (isMovingBackward) {
/* 970 */         yaw -= 126.0D;
/*     */       } 
/*     */       
/* 973 */       yaw = Math.toRadians(yaw);
/* 974 */       this.motionX = -MathHelper.sin((float)yaw) * speed;
/* 975 */       this.motionZ = MathHelper.cos((float)yaw) * speed;
/*     */     } else {
/* 977 */       this.motionX = 0.0D;
/* 978 */       this.motionZ = 0.0D;
/*     */     } 
/*     */   }
/*     */   public void setMotion(double speed) {
/* 982 */     this.motionX *= speed;
/* 983 */     this.motionZ *= speed;
/*     */   }
/*     */   
/*     */   public Slot getSlotFromPlayerContainer(int slot) {
/* 987 */     return this.inventoryContainer.getSlot(slot);
/*     */   }
/*     */   
/*     */   public int b(Potion potion) {
/* 991 */     return isPotionActive(potion) ? (((PotionEffect)getActivePotionsMap().get(Double.valueOf(potion.getEffectiveness()))).getAmplifier() + 1) : 0;
/*     */   }
/*     */   
/*     */   public Vector2f getPreviousRotation() {
/* 995 */     return new Vector2f(this.lastReportedYaw, this.lastReportedPitch);
/*     */   }
/*     */   
/*     */   public boolean isOnGround(double height) {
/* 999 */     return !this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\entity\EntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */