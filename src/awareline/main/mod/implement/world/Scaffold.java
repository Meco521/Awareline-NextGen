/*      */ package awareline.main.mod.implement.world;
/*      */ import awareline.main.Client;
/*      */ import awareline.main.event.EventHandler;
/*      */ import awareline.main.event.events.LBEvents.EventMotion;
/*      */ import awareline.main.event.events.world.EventTick;
/*      */ import awareline.main.event.events.world.moveEvents.EventJump;
/*      */ import awareline.main.event.events.world.moveEvents.EventMove;
/*      */ import awareline.main.event.events.world.moveEvents.EventStrafe;
/*      */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*      */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*      */ import awareline.main.mod.Module;
/*      */ import awareline.main.mod.implement.combat.advanced.sucks.utils.MSTimer;
/*      */ import awareline.main.mod.implement.combat.advanced.sucks.utils.TimeUtils;
/*      */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.Rotation;
/*      */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.RotationUtils;
/*      */ import awareline.main.mod.implement.move.Flight;
/*      */ import awareline.main.mod.implement.move.Longjump;
/*      */ import awareline.main.mod.implement.move.Speed;
/*      */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*      */ import awareline.main.mod.implement.visual.HUD;
/*      */ import awareline.main.mod.implement.world.utils.DelayTimer;
/*      */ import awareline.main.mod.implement.world.utils.RayCastUtil;
/*      */ import awareline.main.mod.implement.world.utils.ScaffoldUtils.liquidbounce.PlaceInfo;
/*      */ import awareline.main.mod.implement.world.utils.ScaffoldUtils.liquidbounce.PlaceRotation;
/*      */ import awareline.main.mod.implement.world.utils.ScaffoldUtils.liquidbounce.SafeWalkUtil;
/*      */ import awareline.main.mod.implement.world.utils.Vector2f;
/*      */ import awareline.main.mod.values.Mode;
/*      */ import awareline.main.mod.values.Numbers;
/*      */ import awareline.main.mod.values.Option;
/*      */ import awareline.main.mod.values.Value;
/*      */ import awareline.main.utility.BlockUtils;
/*      */ import awareline.main.utility.MoveUtils;
/*      */ import awareline.main.utility.PlayerUtil;
/*      */ import awareline.main.utility.animations.Animation;
/*      */ import awareline.main.utility.animations.Direction;
/*      */ import awareline.main.utility.math.RotationUtil;
/*      */ import awareline.main.utility.render.RenderUtil;
/*      */ import awareline.main.utility.render.RoundedUtil;
/*      */ import awareline.main.utility.timer.TimerUtil;
/*      */ import io.netty.util.internal.ThreadLocalRandom;
/*      */ import java.awt.Color;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemBlock;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*      */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovementInput;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.Vec3;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ 
/*      */ public class Scaffold extends Module {
/*      */   public static Scaffold getInstance;
/*   72 */   public final Option<Boolean> down = new Option("Downwards", Boolean.valueOf(true)); public final List<Block> blacklist;
/*   73 */   public final Option<Boolean> StopSpeed = new Option("StopSpeed", Boolean.valueOf(false));
/*   74 */   public final Option<Boolean> safe = new Option("SafeWalk", Boolean.valueOf(false));
/*   75 */   public final Option<Boolean> combatspoof = new Option("CombatSpoofer", Boolean.valueOf(false));
/*   76 */   public final TimeUtils timerUtil = new TimeUtils();
/*   77 */   private final Option<Boolean> airSafeValue = new Option("AirSafe", 
/*   78 */       Boolean.valueOf(false), this.safe::get);
/*   79 */   private final String[] rots = new String[] { "None", "Watchdog", "Watchdog2", "OldWatchdog", "AAC", "AAC2", "Facing", "NCP", "OldNCP", "Normal" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   public final Mode<String> rot = new Mode("Rotations", this.rots, "Normal");
/*   86 */   private final String[] scmode = new String[] { "Normal", "Telly" };
/*      */ 
/*      */   
/*   89 */   private final String[] towermodes = new String[] { "NCP", "NCPFly", "Watchdog", "DCJ", "AAC3.6.4", "AACv4", "Vulcan", "MMC", "Matrix", "LowJump", "LowJumpFast" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   private final String[] placeTiming1 = new String[] { "Pre", "Post" };
/*      */ 
/*      */   
/*   97 */   private final Mode<String> scaffoldmode = new Mode("Mode", this.scmode);
/*   98 */   private final Mode<String> placeTiming = new Mode("PlaceTiming", this.placeTiming1, this.placeTiming1[1]);
/*   99 */   private final Numbers<Double> expandDist = new Numbers("ExpandDist", 
/*  100 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(6.0D), Double.valueOf(0.1D));
/*      */   
/*  102 */   private final Option<Boolean> tower = new Option("Tower", Boolean.valueOf(true));
/*  103 */   private final Option<Boolean> towerMove = new Option("TowerMove", Boolean.valueOf(true), this.tower::get);
/*  104 */   private final Mode<String> towermode = new Mode("TowerMode", this.towermodes, this.tower::get);
/*      */   
/*  106 */   private final Option<Boolean> placeableDelay = new Option("PlaceableDelay", Boolean.valueOf(false));
/*  107 */   private final Option<Boolean> randomDelay = new Option("Random", Boolean.valueOf(false));
/*  108 */   private final Numbers<Double> maxblockdelay = new Numbers("MaxDelay", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(500.0D), Double.valueOf(0.1D));
/*  109 */   private final Numbers<Double> minblockdelay = new Numbers("MinDelay", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(500.0D), Double.valueOf(0.1D));
/*      */   
/*  111 */   private final Option<Boolean> onlyPlaceTurn = new Option("Turnless", Boolean.valueOf(true));
/*  112 */   private final Numbers<Double> minturnspeed = new Numbers("MinTurnSpeed", Double.valueOf(85.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(0.1D));
/*  113 */   private final Numbers<Double> maxturnspeed = new Numbers("MaxTurnSpeed", Double.valueOf(180.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(0.1D));
/*      */   
/*  115 */   private final Option<Boolean> autoBlock = new Option("AutoBlock", Boolean.valueOf(true));
/*  116 */   private final Option<Boolean> stayAutoBlock = new Option("StayAutoBlock", 
/*  117 */       Boolean.valueOf(false), this.autoBlock::get);
/*  118 */   private final Option<Boolean> searchValue = new Option("Search", Boolean.valueOf(true));
/*      */   
/*  120 */   private final Option<Boolean> sprint = new Option("AllowSprint", Boolean.valueOf(true));
/*  121 */   private final Mode<String> sprintMode = new Mode("Sprints", new String[] { "Normal", "Legit", "Switch", "AirSwitch", "Alternating", "SlowSpeed", "PlaceSlow", "Bypass", "Watchdog", "Watchdog2", "WatchdogSlow" }, "Normal", this.sprint::get);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   private final Option<Boolean> moveControl = new Option("Strafe", Boolean.valueOf(false));
/*  130 */   private final Option<Boolean> swing = new Option("Swing", Boolean.valueOf(false));
/*  131 */   private final Option<Boolean> pick = new Option("Pick", Boolean.valueOf(false));
/*  132 */   private final Option<Boolean> hideJump = new Option("HideJump", Boolean.valueOf(false));
/*  133 */   private final Option<Boolean> safeSwap = new Option("SafeSwap", Boolean.valueOf(false));
/*  134 */   private final Option<Boolean> flashSwing = new Option("Flash", Boolean.valueOf(false));
/*      */   
/*  136 */   private final Option<Boolean> eagle = new Option("Eagle", Boolean.valueOf(false));
/*  137 */   private final Option<Boolean> eagleSilentValue = new Option("EagleSilent", 
/*  138 */       Boolean.valueOf(false), this.eagle::get);
/*      */   
/*  140 */   private final Numbers<Double> blocksToEagleValue = new Numbers("BlocksToEagle", 
/*  141 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D), this.eagle::get);
/*  142 */   private final Mode<String> rayCast = new Mode("RayCast", new String[] { "Off", "Normal", "Strict" }, "Off");
/*      */ 
/*      */ 
/*      */   
/*  146 */   private final Option<Boolean> zitter = new Option("Zitter", Boolean.valueOf(false));
/*      */   
/*  148 */   private final Option<Boolean> sameY = new Option("SameY", Boolean.valueOf(false));
/*  149 */   private final String[] sameyModes = new String[] { "Normal", "OnlySpeed", "AutoJump" };
/*  150 */   private final Mode<String> sameYMode = new Mode("SameYs", this.sameyModes, this.sameyModes[0], this.sameY::get);
/*      */ 
/*      */   
/*  153 */   private final Option<Boolean> delayStop = new Option("DelayStop", Boolean.valueOf(false));
/*      */   
/*  155 */   private final Option<Boolean> autoF5 = new Option("AutoF5", Boolean.valueOf(false));
/*  156 */   private final Mode<String> renderCountMode = new Mode("Counts", new String[] { "Off", "New", "Classic", "Item" }, "Item");
/*      */ 
/*      */   
/*  159 */   private final Option<Boolean> mark = new Option("Mark", Boolean.valueOf(false));
/*  160 */   private final Animation anim = (Animation)new DecelerateAnimation(250, 1.0D);
/*  161 */   private final MSTimer delayTimer = new MSTimer();
/*  162 */   private final DelayTimer clickTimer = new DelayTimer();
/*  163 */   private final TimerUtil watchdogTowerWaitTimer = new TimerUtil();
/*  164 */   private final TimeHelper blockTimer = new TimeHelper();
/*      */   
/*      */   public float[] angles;
/*      */   
/*      */   public Vec3 hitVec;
/*      */   
/*      */   double jumpGround;
/*      */   int lastSlot;
/*      */   int hotbar;
/*      */   int watchdogPlaceBlocks;
/*      */   int watchdogTowerPlaces;
/*      */   boolean canPlace;
/*      */   boolean isOut = false;
/*      */   boolean inAir;
/*      */   boolean needChanged;
/*      */   boolean switcher;
/*      */   private PlaceInfo blockData;
/*      */   private double y;
/*      */   private int theSlot;
/*      */   private float lastYaw;
/*      */   private float lastPitch;
/*      */   private float blockPitch;
/*      */   private int oldBlockUse;
/*      */   private int placedBlocksWithoutEagle;
/*      */   private boolean eagleSneaking;
/*      */   private long delay;
/*      */   private boolean shouldGoDown;
/*      */   private Rotation lockRotation;
/*      */   private float targetYaw;
/*      */   private float targetPitch;
/*      */   private int launchY;
/*      */   private boolean zitterDirection;
/*      */   
/*      */   public Scaffold() {
/*  198 */     super("Scaffold", ModuleType.World);
/*  199 */     addSettings(new Value[] { (Value)this.scaffoldmode, (Value)this.expandDist, (Value)this.towermode, (Value)this.rot, (Value)this.placeTiming, (Value)this.sameYMode, (Value)this.renderCountMode, (Value)this.maxblockdelay, (Value)this.minblockdelay, (Value)this.placeableDelay, (Value)this.randomDelay, (Value)this.maxturnspeed, (Value)this.minturnspeed, (Value)this.autoBlock, (Value)this.stayAutoBlock, (Value)this.onlyPlaceTurn, (Value)this.swing, (Value)this.sprint, (Value)this.sprintMode, (Value)this.tower, (Value)this.towerMove, (Value)this.searchValue, (Value)this.down, (Value)this.sameY, (Value)this.pick, (Value)this.hideJump, (Value)this.eagle, (Value)this.eagleSilentValue, (Value)this.blocksToEagleValue, (Value)this.zitter, (Value)this.rayCast, (Value)this.moveControl, (Value)this.delayStop, (Value)this.StopSpeed, (Value)this.combatspoof, (Value)this.safe, (Value)this.airSafeValue, (Value)this.flashSwing, (Value)this.safeSwap, (Value)this.autoF5, (Value)this.mark });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  210 */     this.theSlot = -1;
/*  211 */     getInstance = this;
/*  212 */     this.blacklist = Arrays.asList(new Block[] { Blocks.air, (Block)Blocks.water, (Block)Blocks.flowing_water, (Block)Blocks.lava, (Block)Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, (Block)Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, (Block)Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, (Block)Blocks.tallgrass, Blocks.tripwire, (Block)Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, (Block)Blocks.red_flower, (Block)Blocks.red_mushroom, (Block)Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, (Block)Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, (Block)Blocks.sand, Blocks.gravel, Blocks.ender_chest, (Block)Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.redstone_torch });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isScaffoldBlock(ItemStack itemStack) {
/*  224 */     if (itemStack == null) {
/*  225 */       return false;
/*      */     }
/*  227 */     if (itemStack.stackSize <= 0) {
/*  228 */       return false;
/*      */     }
/*  230 */     if (!(itemStack.getItem() instanceof ItemBlock)) {
/*  231 */       return false;
/*      */     }
/*  233 */     ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
/*      */ 
/*      */     
/*  236 */     if (itemBlock.getBlock() == Blocks.glass) {
/*  237 */       return true;
/*      */     }
/*  239 */     if (itemBlock.getBlock() == Blocks.tnt) {
/*  240 */       return false;
/*      */     }
/*      */     
/*  243 */     return itemBlock.getBlock().isFullBlock();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnable() {
/*  248 */     checkModule(Flight.class);
/*  249 */     this.launchY = (int)mc.thePlayer.posY;
/*  250 */     this.watchdogPlaceBlocks = 0;
/*  251 */     mc.timer.timerSpeed = 1.0F;
/*  252 */     this.theSlot = mc.thePlayer.inventory.currentItem;
/*  253 */     if (!((Boolean)this.autoBlock.get()).booleanValue()) {
/*  254 */       this.oldBlockUse = mc.thePlayer.inventory.currentItem;
/*      */     }
/*  256 */     this.y = mc.thePlayer.posY;
/*  257 */     this.lastYaw = mc.thePlayer.rotationYaw;
/*  258 */     this.lastPitch = 90.0F;
/*  259 */     this.blockPitch = 80.0F;
/*  260 */     this.targetYaw = mc.thePlayer.rotationYaw - 180.0F;
/*  261 */     this.targetPitch = 90.0F;
/*  262 */     this.clickTimer.reset();
/*  263 */     this.blockTimer.reset();
/*  264 */     if (((Boolean)this.autoF5.getValue()).booleanValue()) {
/*  265 */       mc.gameSettings.thirdPersonView = 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDisable() {
/*  271 */     SafeWalkUtil.setSafe(false);
/*  272 */     if (mc.thePlayer == null) {
/*      */       return;
/*      */     }
/*  275 */     mc.timer.timerSpeed = 1.0F;
/*  276 */     if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) {
/*  277 */       mc.gameSettings.keyBindSneak.pressed = false;
/*      */       
/*  279 */       if (this.eagleSneaking)
/*  280 */         sendPacket((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING)); 
/*      */     } 
/*  282 */     if (!GameSettings.isKeyDown(mc.gameSettings.keyBindRight)) {
/*  283 */       mc.gameSettings.keyBindRight.pressed = false;
/*      */     }
/*  285 */     if (!GameSettings.isKeyDown(mc.gameSettings.keyBindLeft)) {
/*  286 */       mc.gameSettings.keyBindLeft.pressed = false;
/*      */     }
/*  288 */     mc.timer.timerSpeed = 1.0F;
/*      */     
/*  290 */     if (!((Boolean)this.autoBlock.get()).booleanValue()) {
/*  291 */       mc.thePlayer.inventory.currentItem = this.oldBlockUse;
/*      */     }
/*  293 */     if (this.theSlot != mc.thePlayer.inventory.currentItem) {
/*  294 */       sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*      */     }
/*  296 */     this.lastYaw = mc.thePlayer.rotationYaw;
/*  297 */     this.lastPitch = this.blockPitch = mc.thePlayer.rotationPitch;
/*  298 */     if (((Boolean)this.autoF5.get()).booleanValue()) {
/*  299 */       mc.gameSettings.thirdPersonView = 0;
/*      */     }
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onStrafe(EventStrafe event) {
/*  305 */     if (((Boolean)this.moveControl.get()).booleanValue()) {
/*  306 */       event.setCancelled(true);
/*  307 */       silentRotationStrafe(event, this.lastYaw);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void onJump(EventJump e) {
/*  314 */     if (((Boolean)this.tower.get()).booleanValue()) {
/*  315 */       if (mc.gameSettings.keyBindJump.isKeyDown()) {
/*  316 */         this.delay = 0L;
/*      */       }
/*  318 */       if (this.towermode.is("Watchdog") && (
/*  319 */         towering() || towerMoving() || (Speed.getInstance.isEnabled() && mc.gameSettings.keyBindJump.isKeyDown()))) {
/*  320 */         e.setCancelled(true);
/*      */       }
/*      */       
/*  323 */       if ((this.towermode.is("DCJ") || this.towermode
/*  324 */         .is("LowJump") || this.towermode.is("LowJumpFast")) && 
/*  325 */         mc.gameSettings.keyBindJump.isKeyDown()) {
/*  326 */         e.cancelEvent();
/*      */       }
/*      */     } 
/*      */     
/*  330 */     if (this.sameYMode.is("AutoJump") && (Speed.getInstance.isEnabled() || Longjump.getInstance.isEnabled()) && isMoving()) {
/*  331 */       e.cancelEvent();
/*      */     }
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onPacketSend(EventPacketSend event) {
/*  337 */     if (event.getPacket() instanceof C09PacketHeldItemChange) {
/*  338 */       C09PacketHeldItemChange packet = (C09PacketHeldItemChange)event.getPacket();
/*  339 */       this.theSlot = packet.getSlotId();
/*      */     } 
/*  341 */     if (this.towermode.is("Watchdog")) {
/*  342 */       if (event.getPacket() instanceof C03PacketPlayer) {
/*  343 */         C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
/*      */         
/*  345 */         if (towering()) {
/*  346 */           this.canPlace = !packet.isMoving();
/*      */         }
/*      */       }
/*      */     
/*  350 */     } else if (!this.canPlace) {
/*  351 */       this.canPlace = true;
/*      */     } 
/*      */     
/*  354 */     if (((Boolean)this.tower.get()).booleanValue() && 
/*  355 */       this.towermode.is("MMC")) {
/*  356 */       Packet<?> packet = event.getPacket();
/*      */       
/*  358 */       C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement)packet;
/*      */       
/*  360 */       if (mc.gameSettings.keyBindJump.isKeyDown() && packet instanceof C08PacketPlayerBlockPlacement && c08PacketPlayerBlockPlacement.getPosition().equals(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.4D, mc.thePlayer.posZ))) {
/*  361 */         mc.gameSettings.keyBindSprint.setPressed(false);
/*  362 */         mc.thePlayer.setSprinting(false);
/*  363 */         mc.thePlayer.motionY = 0.41999998688697815D;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   private void onTick(EventTick event) {
/*  372 */     setSuffix((Serializable)this.scaffoldmode.get());
/*  373 */     if (((Boolean)this.safeSwap.get()).booleanValue() && isMoving()) {
/*      */       return;
/*      */     }
/*      */     
/*  377 */     int slot = getBlockFromInventory();
/*      */     
/*  379 */     if (slot == -1) {
/*      */       return;
/*      */     }
/*  382 */     if (getHotbarBlocksLeft() < 2 && getBlockFromInventory() != -1 && 
/*  383 */       this.clickTimer.isDelayComplete(200.0D)) {
/*  384 */       swap(getBlockFromInventory(), findEmptySlot());
/*  385 */       this.clickTimer.reset();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void onUpdate(EventPreUpdate event) {
/*  392 */     if (getBlockCount() == 0) {
/*  393 */       notiWarning(getHUDName(), "No blocks can place!");
/*  394 */       setEnabled(false);
/*      */       
/*      */       return;
/*      */     } 
/*  398 */     this.shouldGoDown = (((Boolean)this.down.get()).booleanValue() && GameSettings.isKeyDown(mc.gameSettings.keyBindSneak) && getBlocksAmount() > 1);
/*  399 */     if (this.shouldGoDown) {
/*  400 */       mc.gameSettings.keyBindSneak.pressed = false;
/*  401 */       SafeWalkUtil.setSafe(false);
/*      */     } 
/*      */     
/*  404 */     if (((Boolean)this.zitter.get()).booleanValue() && MoveUtils.INSTANCE.isMovingKeyBindingActive()) {
/*  405 */       if (!GameSettings.isKeyDown(mc.gameSettings.keyBindRight)) {
/*  406 */         mc.gameSettings.keyBindRight.pressed = false;
/*      */       }
/*  408 */       if (!GameSettings.isKeyDown(mc.gameSettings.keyBindLeft)) {
/*  409 */         mc.gameSettings.keyBindLeft.pressed = false;
/*      */       }
/*  411 */       if (mc.thePlayer.ticksExisted % 2 == 0) {
/*  412 */         this.zitterDirection = !this.zitterDirection;
/*      */       }
/*      */       
/*  415 */       if (this.zitterDirection) {
/*  416 */         mc.gameSettings.keyBindRight.pressed = true;
/*  417 */         mc.gameSettings.keyBindLeft.pressed = false;
/*      */       } else {
/*  419 */         mc.gameSettings.keyBindRight.pressed = false;
/*  420 */         mc.gameSettings.keyBindLeft.pressed = true;
/*      */       } 
/*      */     } 
/*      */     
/*  424 */     if (((Boolean)this.eagle.get()).booleanValue() && !this.shouldGoDown)
/*  425 */       if (this.placedBlocksWithoutEagle >= ((Double)this.blocksToEagleValue.get()).doubleValue()) {
/*      */         
/*  427 */         boolean shouldEagle = (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() == Blocks.air);
/*      */         
/*  429 */         if (((Boolean)this.eagleSilentValue.get()).booleanValue()) {
/*  430 */           if (this.eagleSneaking != shouldEagle) {
/*  431 */             sendPacket((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, shouldEagle ? C0BPacketEntityAction.Action.START_SNEAKING : C0BPacketEntityAction.Action.STOP_SNEAKING));
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  438 */           this.eagleSneaking = shouldEagle;
/*      */         } else {
/*  440 */           mc.gameSettings.keyBindSneak.pressed = shouldEagle;
/*      */         } 
/*  442 */         this.placedBlocksWithoutEagle = 0;
/*      */       } else {
/*  444 */         this.placedBlocksWithoutEagle++;
/*      */       }  
/*      */   }
/*      */   
/*      */   private void fakeJump() {
/*  449 */     mc.thePlayer.isAirBorne = true;
/*  450 */     mc.thePlayer.triggerAchievement(StatList.jumpStat);
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onMove(EventMove e) {
/*  455 */     if (((Boolean)this.delayStop.get()).booleanValue() && 
/*  456 */       mc.thePlayer.ticksExisted % 2 == 0) {
/*  457 */       MoveUtils.INSTANCE.setSpeed(e, 0.22D);
/*      */     }
/*      */ 
/*      */     
/*  461 */     if (((Boolean)this.sprint.get()).booleanValue() && this.sprintMode.is("SlowSpeed") && 
/*  462 */       !Speed.getInstance.isEnabled() && isMoving() && 
/*  463 */       !mc.thePlayer.isSneaking() && mc.thePlayer.onGround) {
/*  464 */       MoveUtils.INSTANCE.setSpeed(e, 0.22D);
/*      */     }
/*      */ 
/*      */     
/*  468 */     if (!((Boolean)this.safe.get()).booleanValue() || this.shouldGoDown) {
/*      */       return;
/*      */     }
/*  471 */     if (((Boolean)this.airSafeValue.get()).booleanValue() || mc.thePlayer.onGround)
/*  472 */       SafeWalkUtil.setSafe(true); 
/*      */   }
/*      */   
/*      */   private void doRotate(EventPreUpdate e) {
/*  476 */     if (!((Boolean)this.onlyPlaceTurn.get()).booleanValue()) {
/*  477 */       syncYawAndPitch();
/*      */     }
/*  479 */     if (this.scaffoldmode.is("Telly") && 
/*  480 */       mc.thePlayer.fallDistance < 0.001D && !mc.thePlayer.onGround && isMoving()) {
/*  481 */       mc.thePlayer.setSprinting(false);
/*  482 */       this.lastYaw = mc.thePlayer.rotationYaw;
/*      */     } 
/*      */ 
/*      */     
/*  486 */     e.setYaw(this.lastYaw);
/*  487 */     e.setPitch(this.lastPitch);
/*      */   }
/*      */   
/*      */   public float[] advancedRotations(float yawOffset) {
/*  491 */     boolean found = false;
/*      */     
/*  493 */     for (float possibleYaw = mc.thePlayer.rotationYaw - 180.0F + yawOffset; possibleYaw <= mc.thePlayer.rotationYaw + 360.0F - 180.0F && !found; possibleYaw += 45.0F) {
/*  494 */       float possiblePitch; for (possiblePitch = 90.0F; possiblePitch > 30.0F && !found; possiblePitch -= (possiblePitch > 50.0F) ? 1.0F : 10.0F) {
/*  495 */         if (RayCastUtil.overBlock(new Vector2f(possibleYaw, possiblePitch), this.blockData.getEnumFacing(), this.blockData.getBlockPos(), true)) {
/*  496 */           this.targetYaw = possibleYaw;
/*  497 */           this.targetPitch = possiblePitch;
/*  498 */           found = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  503 */     if (!found) {
/*  504 */       Vector2f rotations = RotationUtil.calculate(new Vector3d(this.blockData
/*  505 */             .getBlockPos().getX(), this.blockData.getBlockPos().getY(), this.blockData.getBlockPos().getZ()), this.blockData.getEnumFacing());
/*      */       
/*  507 */       this.targetYaw = rotations.x;
/*  508 */       this.targetPitch = rotations.y;
/*      */     } 
/*  510 */     return new float[] { this.targetYaw, this.targetPitch };
/*      */   }
/*      */ 
/*      */   
/*      */   public void syncYawAndPitch() {
/*  515 */     float yaw = mc.thePlayer.rotationYaw, pitch = 90.0F;
/*  516 */     if (this.rot.is("AAC2")) {
/*  517 */       yaw = mc.thePlayer.rotationYaw - 180.0F;
/*  518 */       pitch = 87.5F;
/*  519 */     } else if (this.rot.is("NCP")) {
/*  520 */       if (this.blockData != null) {
/*  521 */         RotationUtil.Rotation rotation = RotationUtil.toRotation(this.blockData.getVec3(), false, false);
/*  522 */         yaw = rotation.getYaw();
/*  523 */         pitch = rotation.getPitch();
/*      */       } else {
/*  525 */         yaw = getRotation(((Double)this.minturnspeed.get()).floatValue(), ((Double)this.maxturnspeed.get()).floatValue())[0];
/*  526 */         pitch = getRotation(((Double)this.minturnspeed.get()).floatValue(), ((Double)this.maxturnspeed.get()).floatValue())[1];
/*      */       } 
/*  528 */     } else if (this.rot.is("AAC")) {
/*  529 */       yaw = getRotation(180.0F, 180.0F)[0];
/*  530 */       pitch = getRotation(180.0F, 180.0F)[1];
/*  531 */     } else if (this.rot.is("Facing")) {
/*  532 */       if (mc.gameSettings.keyBindJump.isKeyDown() && !isMoving()) {
/*  533 */         yaw = getRotation(180.0F, 180.0F)[0];
/*  534 */         pitch = getRotation(180.0F, 180.0F)[1];
/*      */       } else {
/*  536 */         if (this.blockData.getEnumFacing().getName().equalsIgnoreCase("north")) {
/*  537 */           yaw = 0.0F;
/*      */         }
/*  539 */         if (this.blockData.getEnumFacing().getName().equalsIgnoreCase("south")) {
/*  540 */           yaw = 180.0F;
/*      */         }
/*  542 */         if (this.blockData.getEnumFacing().getName().equalsIgnoreCase("west")) {
/*  543 */           yaw = -90.0F;
/*      */         }
/*  545 */         if (this.blockData.getEnumFacing().getName().equalsIgnoreCase("east")) {
/*  546 */           yaw = 90.0F;
/*      */         }
/*  548 */         pitch = 85.0F;
/*      */       } 
/*  550 */     } else if (this.rot.is("OldWatchdog")) {
/*  551 */       this.needChanged = RayCastUtil.overBlock(new Vector2f(this.lastYaw, this.lastPitch), this.blockData.getEnumFacing(), this.blockData.getBlockPos(), false);
/*  552 */       if (!this.needChanged) {
/*  553 */         yaw = advancedRotations(0.0F)[0];
/*  554 */         pitch = advancedRotations(0.0F)[1];
/*  555 */         msg("changed");
/*      */       } else {
/*  557 */         yaw = getRotation(180.0F, 180.0F)[0];
/*  558 */         pitch = 81.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  585 */     else if (this.rot.is("Normal")) {
/*  586 */       if (this.lockRotation != null) {
/*  587 */         yaw = this.lockRotation.getYaw();
/*  588 */         pitch = this.lockRotation.getPitch();
/*      */       } 
/*  590 */     } else if (this.rot.is("Watchdog")) {
/*  591 */       if (this.lockRotation != null) {
/*  592 */         yaw = this.lockRotation.getYaw();
/*  593 */         pitch = this.lockRotation.getPitch();
/*      */       } 
/*  595 */       float moveDir = MoveUtils.INSTANCE.getMovementDirection(mc.thePlayer.moveForward, mc.thePlayer.moveStrafing, mc.thePlayer.rotationYaw);
/*      */ 
/*      */ 
/*      */       
/*  599 */       this.angles = new float[] { moveDir + 135.0F, 86.0F };
/*  600 */       yaw = isMoving() ? (!mc.gameSettings.keyBindJump.isKeyDown() ? this.angles[0] : yaw) : yaw;
/*      */       
/*  602 */       pitch = isMoving() ? (!mc.gameSettings.keyBindJump.isKeyDown() ? this.angles[1] : pitch) : pitch;
/*      */     }
/*  604 */     else if (this.rot.is("Watchdog2")) {
/*  605 */       if (mc.gameSettings.keyBindJump.isKeyDown() && !isMoving()) {
/*  606 */         yaw = getRotation(180.0F, 180.0F)[0];
/*  607 */         pitch = getRotation(180.0F, 180.0F)[1];
/*      */       } else {
/*  609 */         yaw = advancedRotations(0.0F)[0];
/*  610 */         pitch = advancedRotations(0.0F)[1];
/*      */       } 
/*  612 */     } else if (this.rot.is("OldNCP")) {
/*  613 */       if (this.blockData != null) {
/*  614 */         float[] rotations = RotationUtil.getRotations(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer
/*  615 */             .getEyeHeight(), this.blockData.getBlockPos(), this.blockData.getEnumFacing());
/*  616 */         yaw = rotations[0];
/*  617 */         pitch = rotations[1];
/*      */       } else {
/*  619 */         yaw = getRotation(((Double)this.minturnspeed.get()).floatValue(), ((Double)this.maxturnspeed.get()).floatValue())[0];
/*  620 */         pitch = getRotation(((Double)this.minturnspeed.get()).floatValue(), ((Double)this.maxturnspeed.get()).floatValue())[1];
/*      */       } 
/*  622 */     } else if (this.rot.is("None")) {
/*  623 */       yaw = mc.thePlayer.rotationYaw;
/*  624 */       pitch = this.lockRotation.getPitch();
/*      */     } 
/*  626 */     this.lastYaw = RotationUtil.getRotateForScaffold(yaw, pitch, this.lastYaw, this.lastPitch, ((Double)this.minturnspeed.get()).floatValue(), ((Double)this.maxturnspeed.get()).floatValue())[0];
/*  627 */     this.lastPitch = RotationUtil.getRotateForScaffold(yaw, pitch, this.lastYaw, this.lastPitch, ((Double)this.minturnspeed.get()).floatValue(), ((Double)this.maxturnspeed.get()).floatValue())[1];
/*      */   }
/*      */   
/*      */   public float getDelay() {
/*  631 */     return (float)this.timerUtil.randomDelay(((Double)this.minblockdelay.get()).intValue(), ((Double)this.maxblockdelay.get()).intValue()) + (
/*  632 */       ((Boolean)this.randomDelay.get()).booleanValue() ? MathUtil.getRandomInRange(-15.0F, 40.0F) : 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   private void onLoop(LoopEvent event) {
/*  638 */     for (int i = 0; i < 8; i++) {
/*  639 */       if (mc.thePlayer.inventory.mainInventory[i] != null && (mc.thePlayer.inventory.mainInventory[i]).stackSize <= 0)
/*      */       {
/*  641 */         mc.thePlayer.inventory.mainInventory[i] = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean sameYCheck() {
/*  647 */     if (this.scaffoldmode.is("Telly")) {
/*  648 */       return false;
/*      */     }
/*  650 */     if (((Boolean)this.sameY.get()).booleanValue()) {
/*  651 */       if (((Boolean)this.down.get()).booleanValue() && Keyboard.isKeyDown(42)) {
/*  652 */         return false;
/*      */       }
/*  654 */       if (this.sameYMode.is("Normal") || (this.sameYMode.is("AutoJump") && mc.thePlayer.onGround)) {
/*  655 */         return true;
/*      */       }
/*  657 */       if (this.sameYMode.is("OnlySpeed")) {
/*  658 */         return Speed.getInstance.isEnabled();
/*      */       }
/*      */     } 
/*  661 */     return false;
/*      */   }
/*      */   
/*      */   private void runSameY() {
/*  665 */     if (!isMoving() || mc.thePlayer.onGround) {
/*  666 */       this.launchY = (int)mc.thePlayer.posY;
/*      */     }
/*  668 */     if (sameYCheck()) {
/*  669 */       if (this.sameYMode.is("AutoJump") && mc.thePlayer.onGround && isMoving() && MoveUtils.INSTANCE
/*  670 */         .isMovingKeyBindingActive() && !Speed.getInstance.isEnabled() && !Longjump.getInstance.isEnabled()) {
/*  671 */         mc.thePlayer.jump();
/*      */       }
/*  673 */       if (mc.thePlayer.fallDistance > 1.2D + MoveUtils.INSTANCE.getJumpEffect() || !isMoving()) {
/*  674 */         this.y = mc.thePlayer.posY;
/*  675 */         this.launchY = (int)mc.thePlayer.posY;
/*      */       } 
/*      */     } else {
/*  678 */       this.y = mc.thePlayer.posY;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int getBlockFromHotbar() {
/*  684 */     if (this.blockTimer.isDelayComplete(200.0D) || (mc.thePlayer.inventory.getStackInSlot(this.hotbar)).stackSize < 2) {
/*  685 */       int biggest = 0;
/*  686 */       int biggestSlot = -1;
/*  687 */       for (int i = 0; i < 9; i++) {
/*  688 */         ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
/*      */         
/*  690 */         if (isScaffoldBlock(itemStack))
/*      */         {
/*      */           
/*  693 */           if (biggest < itemStack.stackSize) {
/*  694 */             biggest = itemStack.stackSize;
/*  695 */             biggestSlot = i;
/*      */           }  } 
/*      */       } 
/*  698 */       this.blockTimer.reset();
/*  699 */       this.lastSlot = biggestSlot;
/*  700 */       return biggestSlot;
/*      */     } 
/*  702 */     return this.lastSlot;
/*      */   }
/*      */ 
/*      */   
/*      */   public double[] getNormalCoords(double y) {
/*  707 */     double xCalc = 0.0D, zCalc = 0.0D;
/*  708 */     BlockPos underPos = new BlockPos(mc.thePlayer.posX, y, mc.thePlayer.posZ);
/*  709 */     Block underBlock = mc.theWorld.getBlockState(underPos).getBlock();
/*  710 */     float forward = mc.thePlayer.movementInput.getMoveForward();
/*  711 */     float strafe = mc.thePlayer.movementInput.getMoveStrafe();
/*  712 */     float yaw = mc.thePlayer.rotationYaw;
/*  713 */     double dist = 0.0D;
/*  714 */     double expandDist = (((Double)this.expandDist.getValue()).doubleValue() != 0.0D) ? (((Double)this.expandDist.getValue()).doubleValue() * 2.0D) : 0.0D;
/*      */     
/*  716 */     while (!isAirBlock(underBlock)) {
/*  717 */       xCalc = mc.thePlayer.posX;
/*  718 */       zCalc = mc.thePlayer.posZ;
/*  719 */       dist++;
/*  720 */       if (dist > expandDist) dist = expandDist; 
/*  721 */       xCalc += (forward * 0.45D * Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * 0.45D * Math.sin(Math.toRadians((yaw + 90.0F)))) * dist;
/*  722 */       zCalc += (forward * 0.45D * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * 0.45D * Math.cos(Math.toRadians((yaw + 90.0F)))) * dist;
/*  723 */       if (dist == expandDist)
/*  724 */         break;  underPos = new BlockPos(xCalc, y, zCalc);
/*  725 */       underBlock = mc.theWorld.getBlockState(underPos).getBlock();
/*      */     } 
/*  727 */     return new double[] { xCalc, zCalc };
/*      */   }
/*      */   
/*      */   private void findBlock(boolean expand) {
/*  731 */     double posY = this.y;
/*      */ 
/*      */ 
/*      */     
/*  735 */     BlockPos blockPosition = this.shouldGoDown ? ((mc.thePlayer.posY == (int)mc.thePlayer.posY + 0.5D) ? new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.6D, mc.thePlayer.posZ) : (new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.6D, mc.thePlayer.posZ)).down()) : ((mc.thePlayer.posY == (int)posY + 0.5D) ? new BlockPos((Entity)mc.thePlayer) : (new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ)).down());
/*      */     
/*  737 */     if (!expand && (!BlockUtils.isReplaceable(blockPosition) || search(blockPosition, !this.shouldGoDown))) {
/*      */       return;
/*      */     }
/*      */     
/*  741 */     if (expand) {
/*  742 */       boolean air = isAirBlock(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, this.y, mc.thePlayer.posZ)).getBlock());
/*  743 */       double x = mc.thePlayer.posX, z = mc.thePlayer.posZ;
/*  744 */       if (((Double)this.expandDist.get()).doubleValue() != 0.0D) {
/*  745 */         x = mc.thePlayer.posX;
/*  746 */         z = mc.thePlayer.posZ;
/*  747 */         if (!mc.thePlayer.isCollidedHorizontally) {
/*  748 */           double[] coords = getNormalCoords(this.y - 1.0D);
/*  749 */           x = coords[0];
/*  750 */           z = coords[1];
/*      */         } 
/*  752 */         if (isAirBlock(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock())) {
/*  753 */           x = mc.thePlayer.posX;
/*  754 */           z = mc.thePlayer.posZ;
/*      */         } 
/*      */       } 
/*  757 */       double xPos = air ? mc.thePlayer.posX : getNormalCoords(this.y)[0];
/*  758 */       double zPos = air ? mc.thePlayer.posZ : getNormalCoords(this.y)[1];
/*  759 */       BlockPos blockPos = new BlockPos((((Double)this.expandDist.get()).doubleValue() != 0.0D) ? x : xPos, this.y - 1.0D, (((Double)this.expandDist.get()).doubleValue() != 0.0D) ? z : zPos);
/*  760 */       search(blockPos, false);
/*  761 */     } else if (((Boolean)this.searchValue.get()).booleanValue()) {
/*  762 */       for (int x = -1; x <= 1; x++) {
/*  763 */         for (int z = -1; z <= 1; z++) {
/*  764 */           if (search(blockPosition.add(x, 0, z), !this.shouldGoDown))
/*      */             return; 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   } private void update() {
/*  770 */     boolean isHeldItemBlock = (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock);
/*  771 */     if (((Boolean)this.autoBlock.get()).booleanValue() && this.theSlot == -1 && !isHeldItemBlock) {
/*      */       return;
/*      */     }
/*  774 */     findBlock((((Double)this.expandDist.get()).doubleValue() > 0.0D));
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void onPre(EventPreUpdate event) {
/*  780 */     sprintModule(event);
/*      */ 
/*      */     
/*  783 */     doRotate(event);
/*      */ 
/*      */     
/*  786 */     tower(event);
/*      */   }
/*      */   
/*      */   public void sprintModule(EventPreUpdate event) {
/*  790 */     if (((Boolean)this.sprint.get()).booleanValue()) {
/*  791 */       if (isMoving()) {
/*  792 */         if (this.sprintMode.is("Normal") || this.sprintMode
/*  793 */           .is("Switch") || this.sprintMode
/*  794 */           .is("AirSwitch") || this.sprintMode
/*  795 */           .is("SlowSpeed") || this.sprintMode
/*  796 */           .is("PlaceSlow")) {
/*  797 */           mc.thePlayer.setSprinting(true);
/*  798 */         } else if (this.sprintMode.is("Legit")) {
/*  799 */           if (Math.abs(MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) - MathHelper.wrapAngleTo180_float(this.lastYaw)) > 90.0F) {
/*  800 */             mc.gameSettings.keyBindSprint.pressed = false;
/*  801 */             mc.thePlayer.setSprinting(false);
/*      */           } 
/*  803 */         } else if (this.sprintMode.is("Bypass")) {
/*  804 */           if (isMoving() && mc.thePlayer.isSprinting() && mc.thePlayer.onGround) {
/*  805 */             MoveUtils.INSTANCE.getClass(); double speed = 0.221D;
/*  806 */             float yaw = (float)MoveUtils.INSTANCE.direction();
/*  807 */             double posX = MathHelper.sin(yaw) * speed + mc.thePlayer.posX;
/*  808 */             double posZ = -MathHelper.cos(yaw) * speed + mc.thePlayer.posZ;
/*  809 */             sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, event.getPosY(), posZ, false));
/*      */           } 
/*  811 */         } else if (this.sprintMode.is("Alternating")) {
/*  812 */           mc.thePlayer.setSprinting((mc.thePlayer.ticksExisted % 2 == 0));
/*  813 */         } else if (this.sprintMode.is("Watchdog")) {
/*  814 */           EntityPlayerSP thePlayer = mc.thePlayer;
/*      */ 
/*      */ 
/*      */           
/*  818 */           if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
/*  819 */             thePlayer.motionX *= 0.949999988079071D;
/*  820 */             thePlayer.motionZ *= 0.949999988079071D;
/*      */           } else {
/*  822 */             thePlayer.motionX *= 0.7900000214576721D;
/*  823 */             thePlayer.motionZ *= 0.7900000214576721D;
/*  824 */             mc.thePlayer.setSprinting(false);
/*  825 */             double forward = mc.thePlayer.movementInput.moveForward;
/*  826 */             double strafe = mc.thePlayer.movementInput.moveStrafe;
/*  827 */             if ((forward != 0.0D || strafe != 0.0D) && !mc.thePlayer.isJumping && 
/*  828 */               !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isCollidedHorizontally) {
/*  829 */               if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.4D, 0.0D)).isEmpty()) {
/*  830 */                 event.setY(mc.thePlayer.posY + ((mc.thePlayer.ticksExisted % 2 != 0) ? 0.2D : 0.0D));
/*      */               } else {
/*  832 */                 event.setY(mc.thePlayer.posY + ((mc.thePlayer.ticksExisted % 2 != 0) ? 0.4198D : 0.0D));
/*      */               } 
/*      */             }
/*      */             
/*  836 */             double speed = Math.max((mc.thePlayer.ticksExisted % 2 == 0) ? 1.5D : 0.9D, MoveUtils.INSTANCE.defaultSpeed());
/*  837 */             float yaw = mc.thePlayer.rotationYaw;
/*  838 */             if (forward == 0.0D && strafe == 0.0D) {
/*  839 */               mc.thePlayer.motionX = 0.0D;
/*  840 */               mc.thePlayer.motionZ = 0.0D;
/*      */             } else {
/*  842 */               if (forward != 0.0D) {
/*  843 */                 if (strafe > 0.0D) {
/*  844 */                   yaw += ((forward > 0.0D) ? -45 : 45);
/*  845 */                 } else if (strafe < 0.0D) {
/*  846 */                   yaw += ((forward > 0.0D) ? 45 : -45);
/*      */                 } 
/*      */                 
/*  849 */                 strafe = 0.0D;
/*  850 */                 if (forward > 0.0D) {
/*  851 */                   forward = 0.15D;
/*  852 */                 } else if (forward < 0.0D) {
/*  853 */                   forward = -0.15D;
/*      */                 } 
/*      */               } 
/*      */               
/*  857 */               if (strafe > 0.0D) {
/*  858 */                 strafe = 0.15D;
/*  859 */               } else if (strafe < 0.0D) {
/*  860 */                 strafe = -0.15D;
/*      */               } 
/*  862 */               mc.thePlayer
/*  863 */                 .motionX = forward * speed * Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((yaw + 90.0F)));
/*  864 */               mc.thePlayer
/*  865 */                 .motionZ = forward * speed * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((yaw + 90.0F)));
/*      */             } 
/*      */           } 
/*  868 */         } else if (this.sprintMode.is("Watchdog2")) {
/*  869 */           if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
/*  870 */             MoveUtils.INSTANCE.setSpeed(0.11D + Speed.getInstance.getSpeedPotion() * 0.01D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  883 */         else if (this.sprintMode.is("WatchdogSlow")) {
/*  884 */           mc.thePlayer.setSprinting(false);
/*  885 */           if (!mc.gameSettings.keyBindJump.isKeyDown() && 
/*  886 */             isMoving() && mc.thePlayer.onGround) {
/*  887 */             mc.thePlayer.setSprinting(true);
/*  888 */             if (!mc.thePlayer.isSneaking() && this.watchdogPlaceBlocks >= 1) {
/*  889 */               MoveUtils.INSTANCE.setMotion(!mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.1D : 0.055D);
/*      */               
/*  891 */               mc.thePlayer.motionX *= 0.800000011920929D;
/*  892 */               mc.thePlayer.motionZ *= 0.800000011920929D;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  898 */         mc.thePlayer.setSprinting(false);
/*      */       } 
/*      */     } else {
/*  901 */       mc.thePlayer.setSprinting(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onMotion(EventMotion event) {
/*  907 */     EventMotion.Type eventState = event.getTypes();
/*      */     
/*  909 */     if (((Boolean)this.hideJump.isEnabled()).booleanValue() && !mc.gameSettings.keyBindJump.isKeyDown() && isMoving() && !mc.thePlayer.onGround) {
/*  910 */       mc.thePlayer.posY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
/*  911 */       mc.thePlayer.lastTickPosY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
/*  912 */       mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = 0.1F;
/*      */     } 
/*      */ 
/*      */     
/*  916 */     runSameY();
/*      */     
/*  918 */     if (this.placeTiming.is(eventState.name())) {
/*  919 */       place();
/*      */     }
/*      */ 
/*      */     
/*  923 */     if (eventState == EventMotion.Type.PRE) {
/*  924 */       update();
/*      */     }
/*      */ 
/*      */     
/*  928 */     if (this.blockData == null && ((Boolean)this.placeableDelay.get()).booleanValue()) {
/*  929 */       this.delayTimer.reset();
/*      */     }
/*      */   }
/*      */   
/*      */   public void place() {
/*  934 */     if (this.blockData == null) {
/*  935 */       if (((Boolean)this.placeableDelay.get()).booleanValue())
/*  936 */         this.delayTimer.reset(); 
/*      */       return;
/*      */     } 
/*  939 */     if (!this.delayTimer.hasTimePassed(this.delay) || (this.scaffoldmode.is("Telly") && isMoving() && this.launchY - 1 != (int)(this.blockData.getVec3()).yCoord)) {
/*      */       return;
/*      */     }
/*  942 */     this.hotbar = getBlockFromHotbar();
/*      */     
/*  944 */     if (this.hotbar == -1) {
/*      */       return;
/*      */     }
/*      */     
/*  948 */     if (((Boolean)this.pick.get()).booleanValue()) {
/*  949 */       pickBlock();
/*      */     }
/*      */ 
/*      */     
/*  953 */     if (this.theSlot != this.hotbar) {
/*  954 */       sendPacket((Packet)new C09PacketHeldItemChange(this.hotbar));
/*      */     }
/*      */     
/*  957 */     boolean silentAutoBlock = (mc.thePlayer.inventory.currentItem != this.theSlot && ((Boolean)this.autoBlock.get()).booleanValue());
/*  958 */     boolean isSilent = false;
/*  959 */     if (!((Boolean)this.autoBlock.get()).booleanValue()) {
/*  960 */       if (mc.thePlayer.inventory.currentItem != this.theSlot) {
/*  961 */         mc.thePlayer.inventory.currentItem = this.theSlot;
/*      */       }
/*      */     }
/*  964 */     else if (silentAutoBlock) {
/*  965 */       sendPacket((Packet)new C09PacketHeldItemChange(this.theSlot));
/*  966 */       isSilent = true;
/*      */     } 
/*      */     
/*  969 */     if (!bad(false, true, false, false, true) && (
/*  970 */       ((Boolean)this.autoBlock.get()).booleanValue() ? (isSilent || mc.thePlayer.inventory.currentItem == this.theSlot) : (mc.thePlayer.inventory.currentItem == this.theSlot)))
/*      */     {
/*  972 */       placeBlock();
/*      */     }
/*      */     
/*  975 */     if (silentAutoBlock && !((Boolean)this.stayAutoBlock.get()).booleanValue()) {
/*  976 */       sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*      */     }
/*      */     
/*  979 */     this.blockData = null;
/*      */   }
/*      */   
/*      */   private void runSwing() {
/*  983 */     if (((Boolean)this.swing.get()).booleanValue()) {
/*  984 */       mc.thePlayer.swingItem();
/*      */     } else {
/*  986 */       sendPacket((Packet)new C0APacketAnimation());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void pickBlock() {
/*  991 */     int blockCount = 0;
/*  992 */     for (int i = 0; i < 9; i++) {
/*  993 */       ItemStack itemStacke = mc.thePlayer.inventory.getStackInSlot(i);
/*  994 */       if (itemStacke != null) {
/*      */ 
/*      */         
/*  997 */         int stackSize = itemStacke.stackSize;
/*  998 */         if (isValidItem(itemStacke.getItem()) && stackSize > blockCount) {
/*      */ 
/*      */           
/* 1001 */           blockCount = stackSize;
/* 1002 */           this.hotbar = i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void placeBlock() {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield blockData : Lawareline/main/mod/implement/world/utils/ScaffoldUtils/liquidbounce/PlaceInfo;
/*      */     //   4: ifnonnull -> 8
/*      */     //   7: return
/*      */     //   8: aload_0
/*      */     //   9: getfield onlyPlaceTurn : Lawareline/main/mod/values/Option;
/*      */     //   12: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   15: checkcast java/lang/Boolean
/*      */     //   18: invokevirtual booleanValue : ()Z
/*      */     //   21: ifeq -> 84
/*      */     //   24: new net/minecraft/util/BlockPos
/*      */     //   27: dup
/*      */     //   28: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   31: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   34: getfield posX : D
/*      */     //   37: aload_0
/*      */     //   38: getfield y : D
/*      */     //   41: dconst_1
/*      */     //   42: dsub
/*      */     //   43: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   46: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   49: getfield posZ : D
/*      */     //   52: invokespecial <init> : (DDD)V
/*      */     //   55: astore_1
/*      */     //   56: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   59: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   62: aload_1
/*      */     //   63: invokevirtual getBlockState : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*      */     //   66: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*      */     //   71: astore_2
/*      */     //   72: aload_0
/*      */     //   73: aload_2
/*      */     //   74: invokevirtual isAirBlock : (Lnet/minecraft/block/Block;)Z
/*      */     //   77: ifeq -> 84
/*      */     //   80: aload_0
/*      */     //   81: invokevirtual syncYawAndPitch : ()V
/*      */     //   84: aload_0
/*      */     //   85: getfield blockData : Lawareline/main/mod/implement/world/utils/ScaffoldUtils/liquidbounce/PlaceInfo;
/*      */     //   88: invokevirtual getVec3 : ()Lnet/minecraft/util/Vec3;
/*      */     //   91: astore_1
/*      */     //   92: new awareline/main/mod/implement/world/utils/Vector2f
/*      */     //   95: dup
/*      */     //   96: aload_0
/*      */     //   97: getfield lastYaw : F
/*      */     //   100: aload_0
/*      */     //   101: getfield lastPitch : F
/*      */     //   104: invokespecial <init> : (FF)V
/*      */     //   107: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   110: getfield playerController : Lnet/minecraft/client/multiplayer/PlayerControllerMP;
/*      */     //   113: invokevirtual getBlockReachDistance : ()F
/*      */     //   116: f2d
/*      */     //   117: invokestatic rayCast : (Lawareline/main/mod/implement/world/utils/Vector2f;D)Lnet/minecraft/util/MovingObjectPosition;
/*      */     //   120: astore_2
/*      */     //   121: aload_2
/*      */     //   122: ifnull -> 147
/*      */     //   125: aload_2
/*      */     //   126: invokevirtual getBlockPos : ()Lnet/minecraft/util/BlockPos;
/*      */     //   129: aload_0
/*      */     //   130: getfield blockData : Lawareline/main/mod/implement/world/utils/ScaffoldUtils/liquidbounce/PlaceInfo;
/*      */     //   133: invokevirtual getBlockPos : ()Lnet/minecraft/util/BlockPos;
/*      */     //   136: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   139: ifeq -> 147
/*      */     //   142: aload_2
/*      */     //   143: getfield hitVec : Lnet/minecraft/util/Vec3;
/*      */     //   146: astore_1
/*      */     //   147: aload_0
/*      */     //   148: getfield rayCast : Lawareline/main/mod/values/Mode;
/*      */     //   151: ldc 'Off'
/*      */     //   153: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   156: ifne -> 203
/*      */     //   159: new awareline/main/mod/implement/world/utils/Vector2f
/*      */     //   162: dup
/*      */     //   163: aload_0
/*      */     //   164: getfield lastYaw : F
/*      */     //   167: aload_0
/*      */     //   168: getfield lastPitch : F
/*      */     //   171: invokespecial <init> : (FF)V
/*      */     //   174: aload_0
/*      */     //   175: getfield blockData : Lawareline/main/mod/implement/world/utils/ScaffoldUtils/liquidbounce/PlaceInfo;
/*      */     //   178: invokevirtual getEnumFacing : ()Lnet/minecraft/util/EnumFacing;
/*      */     //   181: aload_0
/*      */     //   182: getfield blockData : Lawareline/main/mod/implement/world/utils/ScaffoldUtils/liquidbounce/PlaceInfo;
/*      */     //   185: invokevirtual getBlockPos : ()Lnet/minecraft/util/BlockPos;
/*      */     //   188: aload_0
/*      */     //   189: getfield rayCast : Lawareline/main/mod/values/Mode;
/*      */     //   192: ldc 'Strict'
/*      */     //   194: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   197: invokestatic overBlock : (Lawareline/main/mod/implement/world/utils/Vector2f;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/BlockPos;Z)Z
/*      */     //   200: ifeq -> 574
/*      */     //   203: aload_0
/*      */     //   204: getfield towermode : Lawareline/main/mod/values/Mode;
/*      */     //   207: ldc 'Watchdog'
/*      */     //   209: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   212: ifeq -> 229
/*      */     //   215: aload_0
/*      */     //   216: invokespecial towering : ()Z
/*      */     //   219: ifeq -> 229
/*      */     //   222: aload_0
/*      */     //   223: getfield canPlace : Z
/*      */     //   226: ifeq -> 574
/*      */     //   229: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   232: getfield playerController : Lnet/minecraft/client/multiplayer/PlayerControllerMP;
/*      */     //   235: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   238: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   241: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   244: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   247: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   250: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   253: getfield inventory : Lnet/minecraft/entity/player/InventoryPlayer;
/*      */     //   256: aload_0
/*      */     //   257: getfield theSlot : I
/*      */     //   260: invokevirtual getStackInSlot : (I)Lnet/minecraft/item/ItemStack;
/*      */     //   263: aload_0
/*      */     //   264: getfield blockData : Lawareline/main/mod/implement/world/utils/ScaffoldUtils/liquidbounce/PlaceInfo;
/*      */     //   267: invokevirtual getBlockPos : ()Lnet/minecraft/util/BlockPos;
/*      */     //   270: aload_0
/*      */     //   271: getfield blockData : Lawareline/main/mod/implement/world/utils/ScaffoldUtils/liquidbounce/PlaceInfo;
/*      */     //   274: invokevirtual getEnumFacing : ()Lnet/minecraft/util/EnumFacing;
/*      */     //   277: aload_1
/*      */     //   278: invokevirtual onPlayerRightClick : (Lnet/minecraft/client/entity/EntityPlayerSP;Lnet/minecraft/client/multiplayer/WorldClient;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/Vec3;)Z
/*      */     //   281: ifeq -> 574
/*      */     //   284: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   287: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   290: getfield keyBindJump : Lnet/minecraft/client/settings/KeyBinding;
/*      */     //   293: invokevirtual isKeyDown : ()Z
/*      */     //   296: ifeq -> 309
/*      */     //   299: aload_0
/*      */     //   300: dup
/*      */     //   301: getfield watchdogTowerPlaces : I
/*      */     //   304: iconst_1
/*      */     //   305: iadd
/*      */     //   306: putfield watchdogTowerPlaces : I
/*      */     //   309: aload_0
/*      */     //   310: getfield flashSwing : Lawareline/main/mod/values/Option;
/*      */     //   313: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   316: checkcast java/lang/Boolean
/*      */     //   319: invokevirtual booleanValue : ()Z
/*      */     //   322: ifeq -> 334
/*      */     //   325: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   328: invokevirtual getItemRenderer : ()Lnet/minecraft/client/renderer/ItemRenderer;
/*      */     //   331: invokevirtual resetEquippedProgress2 : ()V
/*      */     //   334: aload_0
/*      */     //   335: getfield delayTimer : Lawareline/main/mod/implement/combat/advanced/sucks/utils/MSTimer;
/*      */     //   338: invokevirtual reset : ()V
/*      */     //   341: aload_0
/*      */     //   342: aload_0
/*      */     //   343: getfield timerUtil : Lawareline/main/mod/implement/combat/advanced/sucks/utils/TimeUtils;
/*      */     //   346: aload_0
/*      */     //   347: getfield minblockdelay : Lawareline/main/mod/values/Numbers;
/*      */     //   350: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   353: checkcast java/lang/Double
/*      */     //   356: invokevirtual intValue : ()I
/*      */     //   359: aload_0
/*      */     //   360: getfield maxblockdelay : Lawareline/main/mod/values/Numbers;
/*      */     //   363: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   366: checkcast java/lang/Double
/*      */     //   369: invokevirtual intValue : ()I
/*      */     //   372: invokevirtual randomDelay : (II)J
/*      */     //   375: putfield delay : J
/*      */     //   378: aload_0
/*      */     //   379: invokespecial runSwing : ()V
/*      */     //   382: aload_0
/*      */     //   383: getfield sprint : Lawareline/main/mod/values/Option;
/*      */     //   386: invokevirtual get : ()Ljava/lang/Object;
/*      */     //   389: checkcast java/lang/Boolean
/*      */     //   392: invokevirtual booleanValue : ()Z
/*      */     //   395: ifeq -> 574
/*      */     //   398: aload_0
/*      */     //   399: getfield sprintMode : Lawareline/main/mod/values/Mode;
/*      */     //   402: ldc 'Switch'
/*      */     //   404: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   407: ifeq -> 423
/*      */     //   410: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   413: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   416: iconst_0
/*      */     //   417: invokevirtual setSprinting : (Z)V
/*      */     //   420: goto -> 574
/*      */     //   423: aload_0
/*      */     //   424: getfield sprintMode : Lawareline/main/mod/values/Mode;
/*      */     //   427: ldc 'AirSwitch'
/*      */     //   429: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   432: ifeq -> 460
/*      */     //   435: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   438: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   441: getfield onGround : Z
/*      */     //   444: ifeq -> 574
/*      */     //   447: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   450: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   453: iconst_0
/*      */     //   454: invokevirtual setSprinting : (Z)V
/*      */     //   457: goto -> 574
/*      */     //   460: aload_0
/*      */     //   461: getfield sprintMode : Lawareline/main/mod/values/Mode;
/*      */     //   464: ldc 'PlaceSlow'
/*      */     //   466: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   469: ifeq -> 528
/*      */     //   472: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   475: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   478: getfield onGround : Z
/*      */     //   481: ifeq -> 574
/*      */     //   484: aload_0
/*      */     //   485: invokevirtual isMoving : ()Z
/*      */     //   488: ifeq -> 574
/*      */     //   491: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   494: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   497: dup
/*      */     //   498: getfield motionX : D
/*      */     //   501: ldc2_w 0.3
/*      */     //   504: dmul
/*      */     //   505: putfield motionX : D
/*      */     //   508: getstatic awareline/main/mod/implement/world/Scaffold.mc : Lnet/minecraft/client/Minecraft;
/*      */     //   511: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   514: dup
/*      */     //   515: getfield motionZ : D
/*      */     //   518: ldc2_w 0.3
/*      */     //   521: dmul
/*      */     //   522: putfield motionZ : D
/*      */     //   525: goto -> 574
/*      */     //   528: aload_0
/*      */     //   529: getfield sprintMode : Lawareline/main/mod/values/Mode;
/*      */     //   532: ldc 'Watchdog'
/*      */     //   534: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   537: ifne -> 564
/*      */     //   540: aload_0
/*      */     //   541: getfield sprintMode : Lawareline/main/mod/values/Mode;
/*      */     //   544: ldc 'WatchdogSlow'
/*      */     //   546: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   549: ifne -> 564
/*      */     //   552: aload_0
/*      */     //   553: getfield sprintMode : Lawareline/main/mod/values/Mode;
/*      */     //   556: ldc 'Watchdog2'
/*      */     //   558: invokevirtual is : (Ljava/lang/String;)Z
/*      */     //   561: ifeq -> 574
/*      */     //   564: aload_0
/*      */     //   565: dup
/*      */     //   566: getfield watchdogPlaceBlocks : I
/*      */     //   569: iconst_1
/*      */     //   570: iadd
/*      */     //   571: putfield watchdogPlaceBlocks : I
/*      */     //   574: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1008	-> 0
/*      */     //   #1009	-> 7
/*      */     //   #1011	-> 8
/*      */     //   #1012	-> 24
/*      */     //   #1013	-> 56
/*      */     //   #1014	-> 72
/*      */     //   #1015	-> 80
/*      */     //   #1019	-> 84
/*      */     //   #1021	-> 92
/*      */     //   #1022	-> 121
/*      */     //   #1023	-> 142
/*      */     //   #1026	-> 147
/*      */     //   #1027	-> 178
/*      */     //   #1026	-> 197
/*      */     //   #1028	-> 203
/*      */     //   #1029	-> 229
/*      */     //   #1030	-> 260
/*      */     //   #1031	-> 267
/*      */     //   #1029	-> 278
/*      */     //   #1032	-> 284
/*      */     //   #1033	-> 299
/*      */     //   #1035	-> 309
/*      */     //   #1036	-> 325
/*      */     //   #1038	-> 334
/*      */     //   #1039	-> 341
/*      */     //   #1040	-> 378
/*      */     //   #1041	-> 382
/*      */     //   #1042	-> 398
/*      */     //   #1043	-> 410
/*      */     //   #1044	-> 423
/*      */     //   #1045	-> 435
/*      */     //   #1046	-> 447
/*      */     //   #1048	-> 460
/*      */     //   #1049	-> 472
/*      */     //   #1050	-> 491
/*      */     //   #1051	-> 508
/*      */     //   #1053	-> 528
/*      */     //   #1054	-> 546
/*      */     //   #1055	-> 558
/*      */     //   #1056	-> 564
/*      */     //   #1062	-> 574
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   56	28	1	blockPos	Lnet/minecraft/util/BlockPos;
/*      */     //   72	12	2	getBlock	Lnet/minecraft/block/Block;
/*      */     //   0	575	0	this	Lawareline/main/mod/implement/world/Scaffold;
/*      */     //   92	483	1	hitVec	Lnet/minecraft/util/Vec3;
/*      */     //   121	454	2	movingObjectPosition	Lnet/minecraft/util/MovingObjectPosition;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void towerMove() {
/* 1065 */     if (mc.thePlayer.isOnGround(0.76D) && !mc.thePlayer.isOnGround(0.75D) && mc.thePlayer.motionY > 0.23D && mc.thePlayer.motionY < 0.25D) {
/* 1066 */       mc.thePlayer.motionY = Math.round(mc.thePlayer.posY) - mc.thePlayer.posY;
/*      */     }
/*      */     
/* 1069 */     if (mc.thePlayer.isOnGround(1.0E-4D)) {
/* 1070 */       mc.thePlayer.motionY = 0.41999998688698D;
/* 1071 */       mc.thePlayer.setMotion(0.9D);
/*      */     }
/* 1073 */     else if (mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 1.0E-4D && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 1.0E-4D && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
/* 1074 */       mc.thePlayer.motionY = 0.0D;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void tower() {
/* 1079 */     mc.thePlayer.setSprinting(false);
/* 1080 */     mc.thePlayer.setSpeed(0.0D);
/* 1081 */     towerMove();
/*      */   }
/*      */   
/*      */   private boolean towerMoving() {
/* 1085 */     return (((Boolean)this.towerMove.get()).booleanValue() && !sameYCheck() && Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && mc.thePlayer.isMoving());
/*      */   }
/*      */   
/*      */   private boolean towering() {
/* 1089 */     return (!isMoving() && Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && !mc.thePlayer.isPotionActive(Potion.jump));
/*      */   }
/*      */   
/*      */   public void tower(EventPreUpdate event) {
/* 1093 */     if (((Boolean)this.tower.get()).booleanValue() && this.towermode.is("Watchdog")) {
/* 1094 */       if (mc.gameSettings.keyBindJump.isKeyDown() && isMoving()) {
/* 1095 */         this.watchdogTowerPlaces = 0;
/* 1096 */         this.watchdogTowerWaitTimer.reset();
/*      */       } 
/* 1098 */       if (this.watchdogTowerWaitTimer.hasReached(10000.0D)) {
/* 1099 */         this.watchdogTowerPlaces = 0;
/* 1100 */         this.watchdogTowerWaitTimer.reset();
/*      */       } 
/*      */     } 
/* 1103 */     if (((Boolean)this.tower.get()).booleanValue() && getBlockCount() != 0) {
/* 1104 */       if (getHotbarBlocksLeft() == 0) {
/*      */         return;
/*      */       }
/* 1107 */       if (!isMoving() && mc.gameSettings.keyBindJump.isKeyDown()) {
/* 1108 */         mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = 0.0F;
/*      */       }
/* 1110 */       if (this.towermode.is("NCP")) {
/* 1111 */         if (MoveUtils.INSTANCE.getJumpEffect() == 0) {
/* 1112 */           ncpTower();
/*      */         }
/* 1114 */       } else if (this.towermode.is("NCPFly")) {
/* 1115 */         if (MoveUtils.INSTANCE.getJumpEffect() == 0) {
/* 1116 */           ncpFlyTower();
/*      */         }
/* 1118 */       } else if (this.towermode.is("AAC3.6.4")) {
/* 1119 */         if (mc.gameSettings.keyBindJump.isKeyDown() && 
/* 1120 */           !isMoving()) {
/* 1121 */           mc.thePlayer.motionX = 0.0D;
/* 1122 */           mc.thePlayer.motionZ = 0.0D;
/* 1123 */           mc.thePlayer.jumpMovementFactor = 0.0F;
/* 1124 */           if (mc.thePlayer.ticksExisted % 4 == 1) {
/* 1125 */             mc.thePlayer.motionY = 0.4195464D;
/* 1126 */             mc.thePlayer.setPosition(mc.thePlayer.posX - 0.035D, mc.thePlayer.posY, mc.thePlayer.posZ);
/* 1127 */           } else if (mc.thePlayer.ticksExisted % 4 == 0) {
/* 1128 */             mc.thePlayer.motionY = -0.5D;
/* 1129 */             mc.thePlayer.setPosition(mc.thePlayer.posX + 0.035D, mc.thePlayer.posY, mc.thePlayer.posZ);
/*      */           }
/*      */         
/*      */         } 
/* 1133 */       } else if (this.towermode.is("LowJump")) {
/* 1134 */         if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround) {
/* 1135 */           mc.thePlayer.motionY = 0.4D;
/*      */         }
/* 1137 */       } else if (this.towermode.is("LowJumpFast")) {
/* 1138 */         if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround) {
/* 1139 */           mc.thePlayer.motionY = 0.36899998784065247D;
/*      */         }
/* 1141 */       } else if (this.towermode.is("AACv4")) {
/* 1142 */         if (MoveUtils.INSTANCE.getJumpEffect() == 0 && (mc.gameSettings.keyBindJump.isKeyDown() || mc.thePlayer.movementInput.jump)) {
/* 1143 */           mc.thePlayer.motionX = 0.0D;
/* 1144 */           mc.thePlayer.motionZ = 0.0D;
/* 1145 */           mc.thePlayer.jumpMovementFactor = 0.0F;
/* 1146 */           if (mc.thePlayer.onGround) {
/* 1147 */             fakeJump();
/* 1148 */             this.jumpGround = mc.thePlayer.posY;
/* 1149 */             mc.thePlayer.motionY = 0.42D;
/*      */           } 
/*      */           
/* 1152 */           if (mc.thePlayer.posY > this.jumpGround + 0.76D) {
/* 1153 */             fakeJump();
/* 1154 */             mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
/* 1155 */             mc.thePlayer.motionY = 0.42D;
/* 1156 */             this.jumpGround = mc.thePlayer.posY;
/*      */           } 
/*      */           
/* 1159 */           mc.timer.timerSpeed = 0.7F;
/* 1160 */         } else if (!mc.gameSettings.keyBindJump.isKeyDown()) {
/* 1161 */           mc.timer.timerSpeed = 1.0F;
/*      */         } 
/* 1163 */       } else if (this.towermode.is("Watchdog")) {
/* 1164 */         if (towerMoving()) {
/* 1165 */           towerMove();
/*      */         }
/* 1167 */         if (towering()) {
/* 1168 */           tower();
/*      */         }
/* 1170 */         if (towering() || towerMoving()) {
/* 1171 */           mc.thePlayer.cameraPitch = 0.0F;
/* 1172 */           mc.thePlayer.cameraYaw = 0.0F;
/* 1173 */         } else if (mc.thePlayer.getDistanceY(this.y) == 1.0D) {
/* 1174 */           mc.thePlayer.cameraYaw = 0.1F;
/* 1175 */           mc.thePlayer.onGround = true;
/* 1176 */           mc.thePlayer.motionY = 0.0D;
/* 1177 */           event.setOnGround(true);
/*      */         } 
/* 1179 */       } else if (this.towermode.is("DCJ")) {
/* 1180 */         if (mc.gameSettings.keyBindJump.isKeyDown()) {
/* 1181 */           if (MoveUtils.INSTANCE.isOnGround(-1.0D)) {
/*      */             return;
/*      */           }
/* 1184 */           if (!isMoving()) {
/* 1185 */             mc.timer.timerSpeed = 2.0F;
/* 1186 */             if (MoveUtils.INSTANCE.isOnGround(0.76D) && !MoveUtils.INSTANCE.isOnGround(0.75D) && mc.thePlayer.motionY > 0.23D && mc.thePlayer.motionY < 0.25D) {
/* 1187 */               mc.thePlayer.motionY = Math.round(mc.thePlayer.posY) - mc.thePlayer.posY;
/*      */             }
/* 1189 */             if (MoveUtils.INSTANCE.isOnGround(1.0E-4D)) {
/* 1190 */               mc.thePlayer.motionY = 0.41999000310897827D;
/* 1191 */               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0D, mc.thePlayer.posZ);
/* 1192 */             } else if (!MoveUtils.INSTANCE.isOnGround(1.0E-4D) && mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 1.0E-4D && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 1.0E-4D) {
/* 1193 */               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0D, mc.thePlayer.posZ);
/* 1194 */               mc.thePlayer.motionY = 0.0D;
/*      */             } 
/*      */           } else {
/* 1197 */             mc.timer.timerSpeed = 1.0F;
/* 1198 */             if (mc.thePlayer.onGround) {
/* 1199 */               mc.thePlayer.motionY = 0.42D;
/* 1200 */             } else if (mc.thePlayer.motionY < 0.23D) {
/* 1201 */               mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
/* 1202 */               mc.thePlayer.onGround = (mc.thePlayer.ticksExisted % 5 == 0);
/* 1203 */               mc.thePlayer.motionY = 0.42D;
/*      */             } 
/*      */           } 
/*      */         } else {
/* 1207 */           mc.timer.timerSpeed = 1.0F;
/*      */         } 
/* 1209 */       } else if (this.towermode.is("Vulcan")) {
/* 1210 */         if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(2) && mc.thePlayer.offGroundTicks > 3) {
/* 1211 */           ItemStack itemStack = mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem];
/*      */           
/* 1213 */           if (itemStack == null || itemStack.stackSize > 2) {
/* 1214 */             sendNoEvent((Packet)new C08PacketPlayerBlockPlacement(null));
/*      */           }
/* 1216 */           mc.thePlayer.motionY = 0.41999998688697815D;
/*      */         } 
/* 1218 */       } else if (this.towermode.is("Matrix") && 
/* 1219 */         mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.isBlockUnder(2.0D, false) && mc.thePlayer.motionY < 0.2D) {
/* 1220 */         mc.thePlayer.motionY = 0.41999998688697815D;
/* 1221 */         event.setOnGround(true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isValidItem(Item item) {
/* 1228 */     if (item instanceof ItemBlock) {
/* 1229 */       ItemBlock iBlock = (ItemBlock)item;
/* 1230 */       Block block = iBlock.getBlock();
/* 1231 */       return !this.blacklist.contains(block);
/*      */     } 
/* 1233 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isAirBlock(Block block) {
/* 1237 */     return (block.getMaterial().isReplaceable() && (!(block instanceof net.minecraft.block.BlockSnow) || block.getBlockBoundsMaxY() <= 0.125D));
/*      */   }
/*      */   
/*      */   public int getBlockCount() {
/* 1241 */     int blockCount = 0;
/*      */     
/* 1243 */     for (int i = 0; i < 45; i++) {
/* 1244 */       if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
/*      */         
/* 1246 */         ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/* 1247 */         Item item = is.getItem();
/*      */         
/* 1249 */         if (is.getItem() instanceof ItemBlock && !this.blacklist.contains(((ItemBlock)item).getBlock()))
/*      */         {
/*      */           
/* 1252 */           blockCount += is.stackSize; } 
/*      */       } 
/* 1254 */     }  return blockCount;
/*      */   }
/*      */   
/*      */   public void renderBlockCount(ScaledResolution sr) {
/* 1258 */     if (this.renderCountMode.is("None")) {
/*      */       return;
/*      */     }
/* 1261 */     if (this.renderCountMode.is("New")) {
/* 1262 */       this.anim.setDirection(this.enabled ? Direction.FORWARDS : Direction.BACKWARDS);
/* 1263 */       if (!this.enabled && this.anim.isDone())
/* 1264 */         return;  int slot = getBlockSlot();
/* 1265 */       ItemStack heldItem = (slot == -1) ? null : mc.thePlayer.inventory.mainInventory[slot];
/* 1266 */       int count = (slot == -1) ? 0 : getBlockCount();
/* 1267 */       String countStr = String.valueOf(count);
/*      */       
/* 1269 */       float output = this.anim.getOutput().floatValue();
/* 1270 */       float blockWH = (heldItem != null) ? 15.0F : -2.0F;
/* 1271 */       int spacing = 3;
/* 1272 */       String text = "搂l" + countStr + "搂r block" + ((count != 1) ? "s" : "");
/* 1273 */       float textWidth = Client.instance.FontLoaders.regular18.getStringWidth(text);
/*      */       
/* 1275 */       float totalWidth = (textWidth + blockWH + spacing + 6.0F) * output;
/* 1276 */       float x = sr.getScaledWidth() / 2.0F - totalWidth / 2.0F;
/* 1277 */       float y = sr.getScaledHeight() - sr.getScaledHeight() / 2.0F - 20.0F;
/* 1278 */       float height = 20.0F;
/* 1279 */       RenderUtil.scissorStart(x - 1.5D, y - 1.5D, (totalWidth + 3.0F), 23.0D);
/*      */       
/* 1281 */       RoundedUtil.drawRound(x, y, totalWidth, 20.0F, 5.0F, new Color(0, 0, 0, 150));
/*      */       
/* 1283 */       Client.instance.FontLoaders.regular18.drawString(text, x + 3.0F + blockWH + spacing, y + Client.instance.FontLoaders.regular18.getMiddleOfBox(20.0F) + 0.5F, -1);
/*      */       
/* 1285 */       if (heldItem != null) {
/* 1286 */         RenderHelper.enableGUIStandardItemLighting();
/* 1287 */         mc.getRenderItem().renderItemAndEffectIntoGUI(heldItem, (int)x + 3, (int)(y + 10.0F - blockWH / 2.0F));
/* 1288 */         RenderHelper.disableStandardItemLighting();
/*      */       } 
/* 1290 */       RenderUtil.scissorEnd();
/* 1291 */     } else if (this.renderCountMode.is("Classic")) {
/* 1292 */       if (HUD.getInstance.alphaScaffold <= 0) {
/*      */         return;
/*      */       }
/* 1295 */       String blokcsAmount = (getBlocksAmount() == 0) ? "empty" : (Integer.toString(getBlocksAmount()) + EnumChatFormatting.WHITE + " blocks");
/* 1296 */       int x = sr.getScaledWidth() / 2 + 10;
/* 1297 */       Client.instance.FontLoaders.regular18.drawString(blokcsAmount, x - Client.instance.FontLoaders.regular18
/* 1298 */           .getStringWidth(Integer.toString(getBlocksAmount())) / 2.0F, (sr
/* 1299 */           .getScaledHeight() / 2 + 6), (new Color(255, 255, 255, Math.abs(Math.min(HUD.getInstance.alphaScaffold, 255))))
/* 1300 */           .getRGB());
/* 1301 */     } else if (this.renderCountMode.is("Item")) {
/* 1302 */       if (!isEnabled()) {
/*      */         return;
/*      */       }
/* 1305 */       ItemStack stack = mc.thePlayer.inventory.getStackInSlot(this.lastSlot);
/* 1306 */       if (stack != null && stack.getItem() instanceof ItemBlock) {
/* 1307 */         float width = Client.instance.FontLoaders.SF18.getStringWidth("/" + getBlockCount());
/* 1308 */         float x = (sr.getScaledWidth() / 2) - width / 2.0F, y = (sr.getScaledHeight() / 2);
/*      */         
/* 1310 */         RenderUtil.drawStack(stack, x - 5.0F, y + 11.0F);
/* 1311 */         Client.instance.FontLoaders.SF18.drawString(Integer.toString(getBlockCount()), x + 11.0F, y + 16.0F, -1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderBlockCountBloom(ScaledResolution sr) {
/* 1317 */     if (this.renderCountMode.is("None")) {
/*      */       return;
/*      */     }
/* 1320 */     if (this.renderCountMode.is("New")) {
/* 1321 */       this.anim.setDirection(this.enabled ? Direction.FORWARDS : Direction.BACKWARDS);
/* 1322 */       if (!this.enabled && this.anim.isDone())
/* 1323 */         return;  int slot = getBlockSlot();
/* 1324 */       ItemStack heldItem = (slot == -1) ? null : mc.thePlayer.inventory.mainInventory[slot];
/* 1325 */       int count = (slot == -1) ? 0 : getBlockCount();
/* 1326 */       String countStr = String.valueOf(count);
/*      */       
/* 1328 */       float output = this.anim.getOutput().floatValue();
/* 1329 */       float blockWH = (heldItem != null) ? 15.0F : -2.0F;
/* 1330 */       int spacing = 3;
/* 1331 */       String text = "搂l" + countStr + "搂r block" + ((count != 1) ? "s" : "");
/* 1332 */       float textWidth = Client.instance.FontLoaders.regular18.getStringWidth(text);
/*      */       
/* 1334 */       float totalWidth = (textWidth + blockWH + spacing + 6.0F) * output;
/* 1335 */       float x = sr.getScaledWidth() / 2.0F - totalWidth / 2.0F;
/* 1336 */       float y = sr.getScaledHeight() - sr.getScaledHeight() / 2.0F - 20.0F;
/* 1337 */       float height = 20.0F;
/* 1338 */       RenderUtil.scissorStart(x - 1.5D, y - 1.5D, (totalWidth + 3.0F), 23.0D);
/*      */       
/* 1340 */       RoundedUtil.drawRound(x, y, totalWidth, 20.0F, 5.0F, Client.instance.getClientColorNoHash(255));
/*      */       
/* 1342 */       RenderUtil.scissorEnd();
/* 1343 */     } else if (this.renderCountMode.is("Classic")) {
/* 1344 */       if (HUD.getInstance.alphaScaffold <= 0) {
/*      */         return;
/*      */       }
/* 1347 */       String blokcsAmount = (getBlocksAmount() == 0) ? "empty" : (Integer.toString(getBlocksAmount()) + EnumChatFormatting.WHITE + " blocks");
/* 1348 */       int x = sr.getScaledWidth() / 2 + 10;
/* 1349 */       Client.instance.FontLoaders.regular18.drawString(blokcsAmount, x - Client.instance.FontLoaders.regular18
/* 1350 */           .getStringWidth(Integer.toString(getBlocksAmount())) / 2.0F, (sr
/* 1351 */           .getScaledHeight() / 2 + 6), (new Color(255, 255, 255, Math.abs(Math.min(HUD.getInstance.alphaScaffold, 255))))
/* 1352 */           .getRGB());
/* 1353 */     } else if (this.renderCountMode.is("Item")) {
/* 1354 */       if (!isEnabled()) {
/*      */         return;
/*      */       }
/* 1357 */       ItemStack stack = mc.thePlayer.inventory.getStackInSlot(this.lastSlot);
/* 1358 */       if (stack != null && stack.getItem() instanceof ItemBlock) {
/* 1359 */         float width = Client.instance.FontLoaders.SF18.getStringWidth("/" + getBlockCount());
/* 1360 */         float x = (sr.getScaledWidth() / 2) - width / 2.0F, y = (sr.getScaledHeight() / 2);
/*      */         
/* 1362 */         RenderUtil.drawStack(stack, x - 5.0F, y + 11.0F);
/* 1363 */         Client.instance.FontLoaders.SF18.drawString(Integer.toString(getBlockCount()), x + 11.0F, y + 16.0F, -1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public float[] getRotation(float minturnspeed, float maxturnspeed) {
/* 1369 */     if (mc.gameSettings.keyBindForward.isKeyDown()) {
/* 1370 */       if (mc.thePlayer.movementInput.moveStrafe == 0.0D)
/* 1371 */         return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw - 180.0F, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed); 
/* 1372 */       if (mc.gameSettings.keyBindLeft.isKeyDown())
/* 1373 */         return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw - 225.0F, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed); 
/* 1374 */       if (mc.gameSettings.keyBindRight.isKeyDown()) {
/* 1375 */         return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw - 135.0F, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed);
/*      */       }
/* 1377 */     } else if (mc.gameSettings.keyBindBack.isKeyDown()) {
/* 1378 */       if (mc.thePlayer.movementInput.moveStrafe == 0.0D)
/* 1379 */         return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed); 
/* 1380 */       if (mc.gameSettings.keyBindLeft.isKeyDown())
/* 1381 */         return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw - 315.0F, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed); 
/* 1382 */       if (mc.gameSettings.keyBindRight.isKeyDown())
/* 1383 */         return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw - 45.0F, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed); 
/*      */     } else {
/* 1385 */       if (mc.gameSettings.keyBindLeft.isKeyDown())
/* 1386 */         return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw - 270.0F, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed); 
/* 1387 */       if (mc.gameSettings.keyBindRight.isKeyDown())
/* 1388 */         return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw - 90.0F, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed); 
/*      */     } 
/* 1390 */     return RotationUtil.getRotateForScaffold(mc.thePlayer.rotationYaw - 180.0F, this.blockPitch, this.lastYaw, this.lastPitch, minturnspeed, maxturnspeed);
/*      */   }
/*      */   
/*      */   private void ncpTower() {
/* 1394 */     if (((Boolean)this.tower.get()).booleanValue() && (!isMoving() || ((Boolean)this.towerMove.get()).booleanValue())) {
/* 1395 */       BlockPos blockPos0 = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
/* 1396 */       Block getBlock0 = mc.theWorld.getBlockState(blockPos0).getBlock();
/* 1397 */       PlaceInfo blockData0 = this.blockData;
/* 1398 */       if (!mc.gameSettings.keyBindJump.isKeyDown()) {
/* 1399 */         if (isMoving()) {
/* 1400 */           if (sameYCheck()) {
/*      */             return;
/*      */           }
/* 1403 */           if (MoveUtils.INSTANCE.isOnGround(0.76D) && !MoveUtils.INSTANCE.isOnGround(0.75D) && mc.thePlayer.motionY > 0.23D && mc.thePlayer.motionY < 0.25D) {
/* 1404 */             mc.thePlayer.motionY = Math.round(mc.thePlayer.posY) - mc.thePlayer.posY;
/*      */           }
/* 1406 */           if (!MoveUtils.INSTANCE.isOnGround(1.0E-4D) && 
/* 1407 */             mc.thePlayer.motionY > 0.1D && mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 1.0E-4D && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 1.0E-4D) {
/* 1408 */             mc.thePlayer.motionY = 0.0D;
/*      */           }
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/* 1414 */       if (isMoving()) {
/* 1415 */         if (sameYCheck()) {
/*      */           return;
/*      */         }
/* 1418 */         if (!MoveUtils.INSTANCE.isOnGround(-1.0D)) {
/* 1419 */           if (MoveUtils.INSTANCE.isOnGround(0.76D) && !MoveUtils.INSTANCE.isOnGround(0.75D) && mc.thePlayer.motionY > 0.23D && mc.thePlayer.motionY < 0.25D) {
/* 1420 */             mc.thePlayer.motionY = Math.round(mc.thePlayer.posY) - mc.thePlayer.posY;
/*      */           }
/* 1422 */           if (MoveUtils.INSTANCE.isOnGround(1.0E-4D)) {
/* 1423 */             mc.thePlayer.motionY = mc.gameSettings.keyBindForward.isKeyDown() ? 0.41999998688697815D : 0.3799999952316284D;
/* 1424 */             mc.thePlayer.motionX *= 0.95D;
/* 1425 */             mc.thePlayer.motionZ *= 0.95D;
/* 1426 */           } else if (!MoveUtils.INSTANCE.isOnGround(1.0E-4D) && mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 1.0E-4D && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 1.0E-4D) {
/* 1427 */             mc.thePlayer.motionY = 0.0D;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1431 */         mc.thePlayer.motionX = 0.0D;
/* 1432 */         mc.thePlayer.motionZ = 0.0D;
/* 1433 */         mc.thePlayer.jumpMovementFactor = 0.0F;
/* 1434 */         if (isAirBlock(getBlock0) && blockData0 != null) {
/* 1435 */           mc.thePlayer.setPosition(mc.thePlayer.prevPosX, mc.thePlayer.posY, mc.thePlayer.prevPosZ);
/* 1436 */           if (!MoveUtils.INSTANCE.isOnGround(-1.0D)) {
/* 1437 */             mc.thePlayer.motionY = 0.41999974846839905D;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void ncpFlyTower() {
/* 1445 */     if (((Boolean)this.tower.get()).booleanValue() && (!isMoving() || ((Boolean)this.towerMove.get()).booleanValue())) {
/* 1446 */       if (!mc.gameSettings.keyBindJump.isKeyDown()) {
/* 1447 */         if (isMoving()) {
/* 1448 */           if (sameYCheck()) {
/*      */             return;
/*      */           }
/* 1451 */           if (MoveUtils.INSTANCE.isOnGround(0.76D) && !MoveUtils.INSTANCE.isOnGround(0.75D) && mc.thePlayer.motionY > 0.23D && mc.thePlayer.motionY < 0.25D) {
/* 1452 */             mc.thePlayer.motionY = Math.round(mc.thePlayer.posY) - mc.thePlayer.posY;
/*      */           }
/* 1454 */           if (!MoveUtils.INSTANCE.isOnGround(1.0E-4D) && 
/* 1455 */             mc.thePlayer.motionY > 0.1D && mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 1.0E-4D && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 1.0E-4D) {
/* 1456 */             mc.thePlayer.motionY = 0.0D;
/*      */           }
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/* 1462 */       if (isMoving()) {
/* 1463 */         if (sameYCheck()) {
/*      */           return;
/*      */         }
/* 1466 */         if (!MoveUtils.INSTANCE.isOnGround(-1.0D)) {
/* 1467 */           EntityPlayerSP thePlayer = mc.thePlayer;
/* 1468 */           thePlayer.motionX *= ThreadLocalRandom.current().nextBoolean() ? 0.8999999761581421D : 0.8399999737739563D;
/* 1469 */           thePlayer.motionZ *= ThreadLocalRandom.current().nextBoolean() ? 0.8999999761581421D : 0.8399999737739563D;
/* 1470 */           mc.thePlayer.motionY = ThreadLocalRandom.current().nextBoolean() ? 0.4000000059604645D : 0.39994099736213684D;
/*      */         } 
/*      */       } else {
/* 1473 */         mc.thePlayer.motionX = 0.0D;
/* 1474 */         mc.thePlayer.motionZ = 0.0D;
/* 1475 */         mc.thePlayer.jumpMovementFactor = 0.0F;
/* 1476 */         BlockPos blockPos0 = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
/* 1477 */         Block getBlock0 = mc.theWorld.getBlockState(blockPos0).getBlock();
/* 1478 */         PlaceInfo blockData0 = this.blockData;
/* 1479 */         if (isAirBlock(getBlock0) && blockData0 != null) {
/* 1480 */           mc.thePlayer.setPosition(mc.thePlayer.prevPosX, mc.thePlayer.posY, mc.thePlayer.prevPosZ);
/* 1481 */           if (!MoveUtils.INSTANCE.isOnGround(-1.0D)) {
/* 1482 */             mc.thePlayer.motionY = 0.41999974846839905D;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void swap(int slot, int hotbarNum) {
/* 1490 */     mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, (EntityPlayer)mc.thePlayer);
/* 1491 */     mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, (EntityPlayer)mc.thePlayer);
/*      */   }
/*      */   
/*      */   private boolean search(BlockPos blockPosition, boolean checks) {
/* 1495 */     if (!BlockUtils.isReplaceable(blockPosition)) {
/* 1496 */       return false;
/*      */     }
/* 1498 */     Vec3 eyesPos = new Vec3(mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
/*      */     
/* 1500 */     PlaceRotation placeRotation = null;
/*      */     
/* 1502 */     for (EnumFacing side : EnumFacing.values()) {
/* 1503 */       BlockPos neighbor = blockPosition.offset(side);
/*      */       
/* 1505 */       if (BlockUtils.canBeClicked(neighbor)) {
/*      */ 
/*      */         
/* 1508 */         Vec3 dirVec = new Vec3(side.getDirectionVec());
/*      */         double xSearch;
/* 1510 */         for (xSearch = 0.1D; xSearch < 0.9D; xSearch += 0.1D) {
/* 1511 */           double ySearch; for (ySearch = 0.1D; ySearch < 0.9D; ySearch += 0.1D) {
/* 1512 */             double zSearch; for (zSearch = 0.1D; zSearch < 0.9D; zSearch += 0.1D) {
/* 1513 */               Vec3 posVec = (new Vec3((Vec3i)blockPosition)).addVector(xSearch, ySearch, zSearch);
/* 1514 */               double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
/* 1515 */               Vec3 hitVec = posVec.add(new Vec3(dirVec.xCoord * 0.5D, dirVec.yCoord * 0.5D, dirVec.zCoord * 0.5D));
/*      */               
/* 1517 */               if (!checks || (eyesPos.squareDistanceTo(hitVec) <= 18.0D && distanceSqPosVec <= eyesPos.squareDistanceTo(posVec.add(dirVec)) && mc.theWorld.rayTraceBlocks(eyesPos, hitVec, false, true, false) == null)) {
/*      */ 
/*      */ 
/*      */                 
/* 1521 */                 double diffX = hitVec.xCoord - eyesPos.xCoord;
/* 1522 */                 double diffY = hitVec.yCoord - eyesPos.yCoord;
/* 1523 */                 double diffZ = hitVec.zCoord - eyesPos.zCoord;
/*      */                 
/* 1525 */                 double diffXZ = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/*      */ 
/*      */ 
/*      */                 
/* 1529 */                 Rotation rotation = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), MathHelper.wrapAngleTo180_float((float)-Math.toDegrees(Math.atan2(diffY, diffXZ))));
/*      */ 
/*      */                 
/* 1532 */                 Vec3 rotationVector = new Vec3(RotationUtils.getVectorForRotation(rotation).getX(), RotationUtils.getVectorForRotation(rotation).getY(), RotationUtils.getVectorForRotation(rotation).getZ());
/* 1533 */                 Vec3 vector = eyesPos.addVector(rotationVector.xCoord * 4.0D, rotationVector.yCoord * 4.0D, rotationVector.zCoord * 4.0D);
/* 1534 */                 MovingObjectPosition obj = mc.theWorld.rayTraceBlocks(eyesPos, vector, false, false, true);
/*      */                 
/* 1536 */                 if (obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && obj.getBlockPos().equals(neighbor))
/*      */                 {
/*      */                   
/* 1539 */                   if (placeRotation == null || RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation()))
/* 1540 */                     placeRotation = new PlaceRotation(new PlaceInfo(neighbor, side.getOpposite(), hitVec), rotation);  } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1546 */     }  if (placeRotation == null) return false; 
/* 1547 */     this.lockRotation = placeRotation.getRotation();
/*      */     
/* 1549 */     this.blockData = placeRotation.getPlaceInfo();
/* 1550 */     return true;
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void onRender3D(EventRender3D e) {
/* 1555 */     if (((Boolean)this.mark.get()).booleanValue()) {
/* 1556 */       for (int i = 0; i < ((((Double)this.expandDist.get()).doubleValue() != 0.0D) ? (((Double)this.expandDist.get()).doubleValue() + 1.0D) : 2.0D); i++) {
/* 1557 */         BlockPos blockPos = new BlockPos(mc.thePlayer.posX + ((mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) ? -i : ((mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST) ? i : false)), mc.thePlayer.posY - ((mc.thePlayer.posY == (int)mc.thePlayer.posY + 0.5D) ? 0.0D : 1.0D) - (this.shouldGoDown ? 1.0D : 0.0D), mc.thePlayer.posZ + ((mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH) ? -i : ((mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH) ? i : false)));
/* 1558 */         PlaceInfo placeInfo = PlaceInfo.get(blockPos);
/* 1559 */         if (mc.theWorld.getBlockState(blockPos).getBlock().isReplaceable((World)mc.theWorld, blockPos) && placeInfo != null) {
/* 1560 */           RenderUtils.drawBlockBox(blockPos, new Color(255, 255, 255), true);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void silentRotationStrafe(EventStrafe event, float yaw) {
/* 1568 */     int dif = (int)((MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - yaw - 23.5F - 135.0F) + 180.0F) / 45.0F);
/* 1569 */     float strafe = event.getStrafe();
/* 1570 */     float forward = event.getForward();
/* 1571 */     float friction = event.getFriction();
/* 1572 */     float calcForward = 0.0F;
/* 1573 */     float calcStrafe = 0.0F;
/* 1574 */     switch (dif) {
/*      */       case 0:
/* 1576 */         calcForward = forward;
/* 1577 */         calcStrafe = strafe;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 1582 */         calcForward += forward;
/* 1583 */         calcStrafe -= forward;
/* 1584 */         calcForward += strafe;
/* 1585 */         calcStrafe += strafe;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 1590 */         calcForward = strafe;
/* 1591 */         calcStrafe = -forward;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1596 */         calcForward -= forward;
/* 1597 */         calcStrafe -= forward;
/* 1598 */         calcForward += strafe;
/* 1599 */         calcStrafe -= strafe;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1604 */         calcForward = -forward;
/* 1605 */         calcStrafe = -strafe;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 1610 */         calcForward -= forward;
/* 1611 */         calcStrafe += forward;
/* 1612 */         calcForward -= strafe;
/* 1613 */         calcStrafe -= strafe;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 6:
/* 1618 */         calcForward = -strafe;
/* 1619 */         calcStrafe = forward;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 7:
/* 1624 */         calcForward += forward;
/* 1625 */         calcStrafe += forward;
/* 1626 */         calcForward -= strafe;
/* 1627 */         calcStrafe += strafe;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1632 */     if (calcForward > 1.0F || (calcForward < 0.9F && calcForward > 0.3F) || calcForward < -1.0F || (calcForward > -0.9F && calcForward < -0.3F)) {
/* 1633 */       calcForward *= 0.5F;
/*      */     }
/* 1635 */     if (calcStrafe > 1.0F || (calcStrafe < 0.9F && calcStrafe > 0.3F) || calcStrafe < -1.0F || (calcStrafe > -0.9F && calcStrafe < -0.3F)) {
/* 1636 */       calcStrafe *= 0.5F;
/*      */     }
/*      */     float d;
/* 1639 */     if ((d = calcStrafe * calcStrafe + calcForward * calcForward) >= 1.0E-4F) {
/* 1640 */       if ((d = MathHelper.sqrt_float(d)) < 1.0F) {
/* 1641 */         d = 1.0F;
/*      */       }
/* 1643 */       d = friction / d;
/* 1644 */       float yawSin = MathHelper.sin((float)(yaw * Math.PI / 180.0D));
/* 1645 */       float yawCos = MathHelper.cos((float)(yaw * Math.PI / 180.0D));
/* 1646 */       mc.thePlayer.motionX += ((calcStrafe *= d) * yawCos - (calcForward *= d) * yawSin);
/* 1647 */       mc.thePlayer.motionZ += (calcForward * yawCos + calcStrafe * yawSin);
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getYawBackward() {
/* 1652 */     float yaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
/*      */     
/* 1654 */     MovementInput input = mc.thePlayer.movementInput;
/* 1655 */     float strafe = input.getMoveStrafe(), forward = input.getMoveForward();
/*      */     
/* 1657 */     if (forward != 0.0F) {
/* 1658 */       if (strafe < 0.0F) {
/* 1659 */         yaw += (forward < 0.0F) ? 135.0F : 45.0F;
/* 1660 */       } else if (strafe > 0.0F) {
/* 1661 */         yaw -= (forward < 0.0F) ? 135.0F : 45.0F;
/* 1662 */       } else if (forward < 0.0F) {
/* 1663 */         yaw -= 180.0F;
/*      */       }
/*      */     
/*      */     }
/* 1667 */     else if (strafe < 0.0F) {
/* 1668 */       yaw += 90.0F;
/* 1669 */     } else if (strafe > 0.0F) {
/* 1670 */       yaw -= 90.0F;
/*      */     } 
/*      */ 
/*      */     
/* 1674 */     return MathHelper.wrapAngleTo180_float(yaw - 180.0F);
/*      */   }
/*      */   
/*      */   private int getBlockFromInventory() {
/* 1678 */     int biggest = 0;
/* 1679 */     int biggestSlot = -1;
/* 1680 */     for (int i = 9; i < 36; i++) {
/* 1681 */       this; ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
/*      */       
/* 1683 */       if (isScaffoldBlock(itemStack))
/*      */       {
/*      */         
/* 1686 */         if (biggest < itemStack.stackSize) {
/* 1687 */           biggest = itemStack.stackSize;
/* 1688 */           biggestSlot = i;
/*      */         } 
/*      */       }
/*      */     } 
/* 1692 */     return biggestSlot;
/*      */   }
/*      */   
/*      */   private int getHotbarBlocksLeft() {
/* 1696 */     return BlockUtils.getHotbarContent().stream().filter(this::isScaffoldBlock)
/* 1697 */       .mapToInt(itemStack -> itemStack.stackSize).sum();
/*      */   }
/*      */   
/*      */   public int findEmptySlot() {
/* 1701 */     for (int i = 0; i < 8; i++) {
/* 1702 */       if (mc.thePlayer.inventory.mainInventory[i] == null) {
/* 1703 */         return i;
/*      */       }
/*      */     } 
/* 1706 */     return mc.thePlayer.inventory.currentItem + ((mc.thePlayer.inventory.getCurrentItem() == null) ? 0 : ((mc.thePlayer.inventory.currentItem < 8) ? 1 : -1));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBlocksAmount() {
/* 1711 */     int amount = 0;
/*      */     
/* 1713 */     for (int i = 36; i < 45; i++) {
/* 1714 */       ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*      */       
/* 1716 */       if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
/* 1717 */         Block block = ((ItemBlock)itemStack.getItem()).getBlock();
/* 1718 */         if (mc.thePlayer.getHeldItem() == itemStack || !this.blacklist.contains(block)) {
/* 1719 */           amount += itemStack.stackSize;
/*      */         }
/*      */       } 
/*      */     } 
/* 1723 */     return amount;
/*      */   }
/*      */   
/*      */   private boolean isBlockValid(Block block) {
/* 1727 */     return ((block.isFullBlock() || block == Blocks.glass) && block != Blocks.sand && block != Blocks.gravel && block != Blocks.dispenser && block != Blocks.command_block && block != Blocks.noteblock && block != Blocks.furnace && block != Blocks.crafting_table && block != Blocks.tnt && block != Blocks.dropper && block != Blocks.beacon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBlockSlot() {
/* 1742 */     for (int i = 0; i < 9; i++) {
/* 1743 */       ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
/* 1744 */       if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
/* 1745 */         ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
/* 1746 */         if (isBlockValid(itemBlock.getBlock())) {
/* 1747 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 1751 */     return -1;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\Scaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */