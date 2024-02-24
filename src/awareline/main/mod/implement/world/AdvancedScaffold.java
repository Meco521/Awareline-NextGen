/*     */ package awareline.main.mod.implement.world;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.LBEvents.EventMotion;
/*     */ import awareline.main.event.events.LBEvents.EventMotionUpdate;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.InventoryUtils;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.MSTimer;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.TimeUtils;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.LiquidRender;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.Rotation;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.RotationUtils;
/*     */ import awareline.main.mod.implement.world.utils.ScaffoldUtils.liquidbounce.PlaceInfo;
/*     */ import awareline.main.mod.implement.world.utils.ScaffoldUtils.liquidbounce.PlaceRotation;
/*     */ import awareline.main.mod.implement.world.utils.ScaffoldUtils.liquidbounce.SafeWalkUtil;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import java.awt.Color;
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.Vec3i;
/*     */ 
/*     */ 
/*     */ public class AdvancedScaffold
/*     */   extends Module
/*     */ {
/*  56 */   public final Mode<String> modeValue = new Mode("Mode", new String[] { "Normal", "Rewinside", "Expand", "Huayuting" }, "Normal");
/*     */ 
/*     */   
/*  59 */   private final Numbers<Double> maxDelayValue = new Numbers("MaxDelay", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*     */   
/*  61 */   private final Numbers<Double> minDelayValue = new Numbers("MinDelay", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*  62 */   private final Option<Boolean> placeableDelay = new Option("PlaceableDelay", Boolean.valueOf(false));
/*     */ 
/*     */   
/*  65 */   private final Option<Boolean> autoBlockValue = new Option("AutoBlock", Boolean.valueOf(true));
/*  66 */   private final Option<Boolean> stayAutoBlock = new Option("StayAutoBlock", Boolean.valueOf(false));
/*     */   
/*  68 */   private final Option<Boolean> HypixelSlow = new Option("SetMotion", Boolean.valueOf(true));
/*  69 */   private final Numbers<Double> MotionSpeed = new Numbers("MotionSpeed", Double.valueOf(0.22D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.01D));
/*  70 */   private final Option<Boolean> GroundOnly = new Option("OnlyGround", Boolean.valueOf(true));
/*     */   
/*  72 */   public final Option<Boolean> sprintValue = new Option("Sprint", Boolean.valueOf(true));
/*  73 */   private final Option<Boolean> swingValue = new Option("Swing", Boolean.valueOf(true));
/*  74 */   private final Option<Boolean> searchValue = new Option("Search", Boolean.valueOf(true));
/*  75 */   private final Option<Boolean> downValue = new Option("Down", Boolean.valueOf(true));
/*  76 */   private final Mode<String> placeModeValue = new Mode("PlaceTiming", new String[] { "Pre", "Post" }, "Post");
/*     */ 
/*     */   
/*  79 */   private final Option<Boolean> eagleValue = new Option("Eagle", Boolean.valueOf(false));
/*  80 */   private final Option<Boolean> eagleSilentValue = new Option("EagleSilent", Boolean.valueOf(false));
/*  81 */   private final Numbers<Double> blocksToEagleValue = new Numbers("BlocksToEagle", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/*     */ 
/*     */   
/*  84 */   private final Numbers<Double> expandLengthValue = new Numbers("ExpandLength", Double.valueOf(5.0D), Double.valueOf(1.0D), Double.valueOf(6.0D), Double.valueOf(1.0D));
/*     */ 
/*     */   
/*  87 */   private final Option<Boolean> rotationsValue = new Option("Rotations", Boolean.valueOf(true));
/*  88 */   private final Numbers<Double> keepLengthValue = new Numbers("KeepRotationLength", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/*  89 */   private final Option<Boolean> keepRotationValue = new Option("KeepRotation", Boolean.valueOf(false));
/*     */ 
/*     */   
/*  92 */   private final Option<Boolean> zitterValue = new Option("Zitter", Boolean.valueOf(false));
/*  93 */   private final Mode<String> zitterModeValue = new Mode("ZitterMode", new String[] { "Teleport", "Smooth" }, "Smooth");
/*  94 */   private final Numbers<Double> zitterSpeed = new Numbers("ZitterSpeed", Double.valueOf(0.13D), Double.valueOf(0.1D), Double.valueOf(0.3D), Double.valueOf(0.01D));
/*  95 */   private final Numbers<Double> zitterStrength = new Numbers("ZitterStrength", Double.valueOf(0.072D), Double.valueOf(0.05D), Double.valueOf(0.2D), Double.valueOf(0.01D));
/*     */ 
/*     */   
/*  98 */   private final Numbers<Double> timerValue = new Numbers("Timer", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(10.0D), Double.valueOf(0.01D));
/*  99 */   private final Numbers<Double> speedModifierValue = new Numbers("SpeedModifier", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(2.0D), Double.valueOf(1.0D));
/*     */ 
/*     */   
/* 102 */   private final Option<Boolean> sameYValue = new Option("SameY", Boolean.valueOf(false));
/* 103 */   private final Option<Boolean> safeWalkValue = new Option("SafeWalk", Boolean.valueOf(true));
/* 104 */   private final Option<Boolean> airSafeValue = new Option("AirSafe", Boolean.valueOf(false));
/*     */ 
/*     */   
/* 107 */   private final Option<Boolean> counterDisplayValue = new Option("Counter", Boolean.valueOf(true));
/* 108 */   private final Option<Boolean> markValue = new Option("Mark", Boolean.valueOf(false));
/*     */ 
/*     */ 
/*     */   
/*     */   private PlaceInfo targetPlace;
/*     */ 
/*     */ 
/*     */   
/*     */   private int launchY;
/*     */ 
/*     */ 
/*     */   
/*     */   private Rotation lockRotation;
/*     */ 
/*     */ 
/*     */   
/*     */   private int slot;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean zitterDirection;
/*     */ 
/*     */   
/* 131 */   private final MSTimer delayTimer = new MSTimer(); private long delay; private int placedBlocksWithoutEagle; private boolean eagleSneaking; private boolean shouldGoDown; public final TimeUtils timeUtils;
/* 132 */   private final MSTimer zitterTimer = new MSTimer();
/*     */   public static boolean renderBlockCount;
/*     */   final CFontRenderer font;
/*     */   public void onEnable() { if (mc.thePlayer == null) return;  this.launchY = (int)mc.thePlayer.posY; } @EventHandler public void onUpdate(EventMotionUpdate event) { event.setYaw(this.lockRotation.getYaw()); event.setPitch(this.lockRotation.getPitch()); mc.timer.timerSpeed = ((Double)this.timerValue.get()).floatValue(); this.shouldGoDown = (((Boolean)this.downValue.get()).booleanValue() && GameSettings.isKeyDown(mc.gameSettings.keyBindSneak) && getBlocksAmount() > 1); if (this.shouldGoDown) { mc.gameSettings.keyBindSneak.pressed = false; SafeWalkUtil.setSafe(false); }  if (((Boolean)this.sprintValue.getValue()).booleanValue()) { if (mc.gameSettings.keyBindSprint.isKeyDown())
/*     */         mc.thePlayer.setSprinting(false);  } else { mc.thePlayer.setSprinting(false); }  if (mc.thePlayer.onGround) { if (this.modeValue.is("Rewinside")) { MoveUtils.INSTANCE.strafe(0.2F); mc.thePlayer.motionY = 0.0D; }  int DiffYaw = 0; if (mc.thePlayer.movementInput.moveForward > 0.0F)
/*     */         DiffYaw = 180;  if (this.modeValue.is("Huayuting"))
/*     */         RotationUtils.setTargetRotation(new Rotation(mc.thePlayer.rotationYaw + DiffYaw, 86.0F));  if (((Boolean)this.zitterValue.get()).booleanValue() && this.zitterModeValue.is("Smooth")) { if (!GameSettings.isKeyDown(mc.gameSettings.keyBindRight))
/*     */           mc.gameSettings.keyBindRight.pressed = false;  if (!GameSettings.isKeyDown(mc.gameSettings.keyBindLeft))
/*     */           mc.gameSettings.keyBindLeft.pressed = false;  if (this.zitterTimer.hasTimePassed(100L)) { this.zitterDirection = !this.zitterDirection; this.zitterTimer.reset(); }  if (this.zitterDirection) { mc.gameSettings.keyBindRight.pressed = true; mc.gameSettings.keyBindLeft.pressed = false; } else { mc.gameSettings.keyBindRight.pressed = false; mc.gameSettings.keyBindLeft.pressed = true; }  }  if (((Boolean)this.eagleValue.get()).booleanValue() && !this.shouldGoDown)
/*     */         if (this.placedBlocksWithoutEagle >= ((Double)this.blocksToEagleValue.get()).doubleValue()) { boolean shouldEagle = (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() == Blocks.air); if (((Boolean)this.eagleSilentValue.get()).booleanValue()) { if (this.eagleSneaking != shouldEagle)
/*     */               mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, shouldEagle ? C0BPacketEntityAction.Action.START_SNEAKING : C0BPacketEntityAction.Action.STOP_SNEAKING));  this.eagleSneaking = shouldEagle; } else { mc.gameSettings.keyBindSneak.pressed = shouldEagle; }  this.placedBlocksWithoutEagle = 0; } else { this.placedBlocksWithoutEagle++; }   if (((Boolean)this.zitterValue.get()).booleanValue() && this.zitterModeValue.is("Teleport")) { MoveUtils.INSTANCE.strafe(((Double)this.zitterSpeed.get()).floatValue()); double yaw = Math.toRadians(mc.thePlayer.rotationYaw + (this.zitterDirection ? 90.0D : -90.0D)); mc.thePlayer.motionX -= Math.sin(yaw) * ((Double)this.zitterStrength.get()).doubleValue(); mc.thePlayer.motionZ += Math.cos(yaw) * ((Double)this.zitterStrength.get()).doubleValue(); this.zitterDirection = !this.zitterDirection; }  }  } @EventHandler public void onPacket(PacketEvent event) { if (mc.thePlayer == null)
/* 143 */       return;  Packet<?> packet = event.getPacket(); if (packet instanceof C09PacketHeldItemChange) { C09PacketHeldItemChange packetHeldItemChange = (C09PacketHeldItemChange)packet; this.slot = packetHeldItemChange.getSlotId(); }  } public AdvancedScaffold() { super("AdvancedScaffold", new String[] { "BlockFly" }, ModuleType.World);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     this.timeUtils = new TimeUtils();
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
/* 453 */     this.font = Client.instance.FontLoaders.SF18; addSettings(new Value[] { (Value)this.modeValue, (Value)this.maxDelayValue, (Value)this.minDelayValue, (Value)this.placeableDelay, (Value)this.autoBlockValue, (Value)this.stayAutoBlock, (Value)this.HypixelSlow, (Value)this.MotionSpeed, (Value)this.GroundOnly, (Value)this.sprintValue, (Value)this.swingValue, (Value)this.searchValue, (Value)this.downValue, (Value)this.placeModeValue, (Value)this.eagleValue, (Value)this.eagleSilentValue, (Value)this.blocksToEagleValue, (Value)this.expandLengthValue, (Value)this.rotationsValue, (Value)this.keepLengthValue, (Value)this.keepRotationValue, (Value)this.zitterValue, (Value)this.zitterModeValue, (Value)this.zitterSpeed, (Value)this.zitterStrength, (Value)this.timerValue, (Value)this.speedModifierValue, (Value)this.sameYValue, (Value)this.safeWalkValue, (Value)this.airSafeValue, 
/*     */           (Value)this.counterDisplayValue, (Value)this.markValue }); } @EventHandler public void onMotion(EventMotion event) { EventMotion.Type eventState = event.getTypes(); if (((Boolean)this.rotationsValue.get()).booleanValue() && ((Boolean)this.keepRotationValue.get()).booleanValue() && this.lockRotation != null) RotationUtils.setTargetRotation(this.lockRotation);  if (this.placeModeValue.is(eventState.name())) place();  if (eventState == EventMotion.Type.PRE) update();  if (this.targetPlace == null && ((Boolean)this.placeableDelay.get()).booleanValue()) this.delayTimer.reset();  }
/*     */   private void update() { boolean isHeldItemBlock = (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock); if (((Boolean)this.autoBlockValue.get()).booleanValue() ? (InventoryUtils.findAutoBlockBlock() == -1 && !isHeldItemBlock) : !isHeldItemBlock) return;  findBlock(this.modeValue.is("Expand")); }
/* 456 */   public void renderBlock() { if (!renderBlockCount) {
/*     */       return;
/*     */     }
/* 459 */     String var10001 = (getBlocksAmount() == 0) ? "No blocks left." : (Integer.toString(getBlocksAmount()) + EnumChatFormatting.GRAY + " blocks left.");
/* 460 */     RenderItem ir = new RenderItem(mc.getTextureManager(), mc.modelManager);
/* 461 */     int var10002 = this.sr.getScaledWidth() / 2 + 1;
/* 462 */     this.font.drawStringWithShadow(var10001, (var10002 - mc.fontRendererObj.getStringWidth(Integer.toString(getBlocksAmount())) / 2.0F), (this.sr.getScaledHeight() / 2 + 12), -1);
/* 463 */     RenderHelper.enableGUIStandardItemLighting();
/* 464 */     if (InventoryUtils.findAutoBlockBlock() != -1)
/* 465 */       ir.renderItemIntoGUI(mc.thePlayer.inventory.mainInventory[InventoryUtils.findAutoBlockBlock() - 36], var10002 - this.font
/* 466 */           .getStringHeight(Integer.toString(getBlocksAmount())) - 15, this.sr.getScaledHeight() / 2 + 7); 
/* 467 */     RenderHelper.disableStandardItemLighting(); } private void findBlock(boolean expand) { BlockPos blockPosition = this.shouldGoDown ? ((mc.thePlayer.posY == (int)mc.thePlayer.posY + 0.5D) ? new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.6D, mc.thePlayer.posZ) : (new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.6D, mc.thePlayer.posZ)).down()) : ((mc.thePlayer.posY == (int)mc.thePlayer.posY + 0.5D) ? new BlockPos((Entity)mc.thePlayer) : (new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)).down()); if (!expand && (!BlockUtils.isReplaceable(blockPosition) || search(blockPosition, !this.shouldGoDown)))
/*     */       return;  if (expand) { for (int i = 0; i < ((Double)this.expandLengthValue.get()).doubleValue(); i++) { if (search(blockPosition.add((mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) ? -i : ((mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST) ? i : 0), 0, (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH) ? -i : ((mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH) ? i : 0)), false))
/*     */           return;  }  } else if (((Boolean)this.searchValue.get()).booleanValue()) { for (int x = -1; x <= 1; x++) { for (int z = -1; z <= 1; z++) { if (search(blockPosition.add(x, 0, z), !this.shouldGoDown))
/*     */             return;  }  }  }  }
/*     */   private void place() { if (this.targetPlace == null) { if (((Boolean)this.placeableDelay.get()).booleanValue())
/*     */         this.delayTimer.reset();  return; }  if (!this.delayTimer.hasTimePassed(this.delay) || (((Boolean)this.sameYValue.get()).booleanValue() && this.launchY - 1 != (int)(this.targetPlace.getVec3()).yCoord))
/*     */       return;  int blockSlot = -1; ItemStack itemStack = mc.thePlayer.getHeldItem(); if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) || (mc.thePlayer.getHeldItem()).stackSize <= 0) { if (!((Boolean)this.autoBlockValue.get()).booleanValue())
/*     */         return;  blockSlot = InventoryUtils.findAutoBlockBlock(); if (blockSlot == -1)
/*     */         return;  mc.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange(blockSlot - 36)); itemStack = mc.thePlayer.inventoryContainer.getSlot(blockSlot).getStack(); }  if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemStack, this.targetPlace.getBlockPos(), this.targetPlace.getEnumFacing(), this.targetPlace.getVec3())) { this.delayTimer.reset(); this.delay = this.timeUtils.randomDelay(((Double)this.minDelayValue.get()).intValue(), ((Double)this.maxDelayValue.get()).intValue()); if (mc.thePlayer.onGround) { float modifier = ((Double)this.speedModifierValue.get()).floatValue(); mc.thePlayer.motionX *= modifier; mc.thePlayer.motionZ *= modifier; }  if (((Boolean)this.swingValue.get()).booleanValue()) { mc.thePlayer.swingItem(); } else { mc.getNetHandler().addToSendQueue((Packet)new C0APacketAnimation()); }  }  if (!((Boolean)this.stayAutoBlock.get()).booleanValue() && blockSlot >= 0)
/*     */       mc.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));  this.targetPlace = null; }
/* 477 */   @EventHandler public void onRender3D(EventRender3D event) { if (!((Boolean)this.markValue.get()).booleanValue()) {
/*     */       return;
/*     */     }
/* 480 */     for (int i = 0; i < (this.modeValue.is("Expand") ? (((Double)this.expandLengthValue.get()).doubleValue() + 1.0D) : 2.0D); i++) {
/* 481 */       BlockPos blockPos = new BlockPos(mc.thePlayer.posX + ((mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) ? -i : ((mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST) ? i : false)), mc.thePlayer.posY - ((mc.thePlayer.posY == (int)mc.thePlayer.posY + 0.5D) ? 0.0D : 1.0D) - (this.shouldGoDown ? 1.0D : 0.0D), mc.thePlayer.posZ + ((mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH) ? -i : ((mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH) ? i : false)));
/* 482 */       PlaceInfo placeInfo = PlaceInfo.get(blockPos);
/*     */       
/* 484 */       if (BlockUtils.isReplaceable(blockPos) && placeInfo != null)
/* 485 */       { LiquidRender.drawBlockBox(blockPos, new Color(234, 122, 255, 102), false); break; } 
/*     */     }  } public void onDisable() { SafeWalkUtil.setSafe(false); if (mc.thePlayer == null)
/*     */       return;  if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) { mc.gameSettings.keyBindSneak.pressed = false; if (this.eagleSneaking)
/*     */         mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));  }
/*     */      if (!GameSettings.isKeyDown(mc.gameSettings.keyBindRight))
/*     */       mc.gameSettings.keyBindRight.pressed = false;  if (!GameSettings.isKeyDown(mc.gameSettings.keyBindLeft))
/*     */       mc.gameSettings.keyBindLeft.pressed = false;  this.lockRotation = null; mc.timer.timerSpeed = 1.0F; this.shouldGoDown = false; if (this.slot != mc.thePlayer.inventory.currentItem)
/*     */       mc.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));  }
/*     */   @EventHandler public void onMove(EventMove event) { if (this.modeValue.is("Huayuting") && mc.thePlayer.isPotionActive(Potion.moveSpeed))
/*     */       MoveUtils.INSTANCE.setMotion(event, 0.28049999475479126D);  if (((Boolean)this.HypixelSlow.get()).booleanValue() && isMoving() && ((((Boolean)this.GroundOnly.get()).booleanValue() && mc.thePlayer.onGround) || !((Boolean)this.GroundOnly.get()).booleanValue()))
/*     */       MoveUtils.INSTANCE.setMotion(event, ((Double)this.MotionSpeed.get()).doubleValue());  if (!((Boolean)this.safeWalkValue.get()).booleanValue() || this.shouldGoDown)
/*     */       return;  if (((Boolean)this.airSafeValue.get()).booleanValue() || mc.thePlayer.onGround)
/*     */       SafeWalkUtil.setSafe(true);  }
/*     */   @EventHandler public void onRender2D(EventRender2D event) { setSuffix((Serializable)this.modeValue.get()); renderBlock(); renderBlockCount = ((Boolean)this.counterDisplayValue.getValue()).booleanValue(); }
/* 499 */   private boolean search(BlockPos blockPosition, boolean checks) { if (!BlockUtils.isReplaceable(blockPosition)) {
/* 500 */       return false;
/*     */     }
/* 502 */     Vec3 eyesPos = new Vec3(mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
/*     */     
/* 504 */     PlaceRotation placeRotation = null;
/*     */     
/* 506 */     for (EnumFacing side : EnumFacing.values()) {
/* 507 */       BlockPos neighbor = blockPosition.offset(side);
/*     */       
/* 509 */       if (BlockUtils.canBeClicked(neighbor)) {
/*     */ 
/*     */         
/* 512 */         Vec3 dirVec = new Vec3(side.getDirectionVec());
/*     */         double xSearch;
/* 514 */         for (xSearch = 0.1D; xSearch < 0.9D; xSearch += 0.1D) {
/* 515 */           double ySearch; for (ySearch = 0.1D; ySearch < 0.9D; ySearch += 0.1D) {
/* 516 */             double zSearch; for (zSearch = 0.1D; zSearch < 0.9D; zSearch += 0.1D) {
/* 517 */               Vec3 posVec = (new Vec3((Vec3i)blockPosition)).addVector(xSearch, ySearch, zSearch);
/* 518 */               double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
/* 519 */               Vec3 hitVec = posVec.add(new Vec3(dirVec.xCoord * 0.5D, dirVec.yCoord * 0.5D, dirVec.zCoord * 0.5D));
/*     */               
/* 521 */               if (!checks || (eyesPos.squareDistanceTo(hitVec) <= 18.0D && distanceSqPosVec <= eyesPos.squareDistanceTo(posVec.add(dirVec)) && mc.theWorld.rayTraceBlocks(eyesPos, hitVec, false, true, false) == null)) {
/*     */ 
/*     */ 
/*     */                 
/* 525 */                 double diffX = hitVec.xCoord - eyesPos.xCoord;
/* 526 */                 double diffY = hitVec.yCoord - eyesPos.yCoord;
/* 527 */                 double diffZ = hitVec.zCoord - eyesPos.zCoord;
/*     */                 
/* 529 */                 double diffXZ = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/*     */ 
/*     */ 
/*     */                 
/* 533 */                 Rotation rotation = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), MathHelper.wrapAngleTo180_float((float)-Math.toDegrees(Math.atan2(diffY, diffXZ))));
/*     */ 
/*     */                 
/* 536 */                 Vec3 rotationVector = new Vec3(RotationUtils.getVectorForRotation(rotation).getX(), RotationUtils.getVectorForRotation(rotation).getY(), RotationUtils.getVectorForRotation(rotation).getZ());
/* 537 */                 Vec3 vector = eyesPos.addVector(rotationVector.xCoord * 4.0D, rotationVector.yCoord * 4.0D, rotationVector.zCoord * 4.0D);
/* 538 */                 MovingObjectPosition obj = mc.theWorld.rayTraceBlocks(eyesPos, vector, false, false, true);
/*     */                 
/* 540 */                 if (obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && obj.getBlockPos().equals(neighbor))
/*     */                 {
/*     */                   
/* 543 */                   if (placeRotation == null || RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation()))
/* 544 */                     placeRotation = new PlaceRotation(new PlaceInfo(neighbor, side.getOpposite(), hitVec), rotation);  } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 550 */     }  if (placeRotation == null) return false;
/*     */     
/* 552 */     if (((Boolean)this.rotationsValue.get()).booleanValue()) {
/* 553 */       RotationUtils.setTargetRotation(placeRotation.getRotation(), ((Double)this.keepLengthValue.get()).intValue());
/* 554 */       this.lockRotation = placeRotation.getRotation();
/*     */     } 
/* 556 */     this.targetPlace = placeRotation.getPlaceInfo();
/* 557 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlocksAmount() {
/* 564 */     int amount = 0;
/*     */     
/* 566 */     for (int i = 36; i < 45; i++) {
/* 567 */       ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */       
/* 569 */       if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
/* 570 */         Block block = ((ItemBlock)itemStack.getItem()).getBlock();
/* 571 */         if (mc.thePlayer.getHeldItem() == itemStack || !InventoryUtils.BLOCK_BLACKLIST.contains(block)) {
/* 572 */           amount += itemStack.stackSize;
/*     */         }
/*     */       } 
/*     */     } 
/* 576 */     return amount;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\AdvancedScaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */