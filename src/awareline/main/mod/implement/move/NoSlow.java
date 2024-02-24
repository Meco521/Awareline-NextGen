/*     */ package awareline.main.mod.implement.move;
/*     */ import awareline.main.component.impl.BlinkComponent;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.SlowdownEvent;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*     */ import awareline.main.event.events.world.worldChangeEvents.LoadWorldEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.MSTimer;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class NoSlow extends Module {
/*     */   public static NoSlow getInstance;
/*  34 */   public final Option<Boolean> antiSoulSand = new Option("AntiSoulSand", Boolean.valueOf(false));
/*  35 */   private final List<Packet<?>> packetBuf = new LinkedList<>();
/*     */   
/*  37 */   private final String[] noslowmode = new String[] { "Vanilla", "Prediction", "Watchdog", "Grim", "ZQAT", "HmXix", "AAC4.4.2", "NCP", "OldNCP", "Intave", "Vulcan", "Medusa", "OldIntave", "Matrix", "Hawk", "Taka", "Spartan" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public final Mode<String> mode = new Mode("Mode", this.noslowmode, "OldNCP");
/*     */   
/*  47 */   public final Numbers<Double> multiplier = new Numbers("Multiplier", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.01D)); public final Numbers<Double> amount = new Numbers("Amount", 
/*  48 */       Double.valueOf(2.0D), Double.valueOf(2.0D), Double.valueOf(5.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(this.mode.is("Prediction")));
/*     */   
/*  50 */   private final TimerUtil time = new TimerUtil();
/*  51 */   private final MSTimer msTimer = new MSTimer(); public boolean matrixShouldSlowdown; public boolean sendPacket;
/*     */   public boolean nextTemp;
/*     */   public boolean lastBlockingStat;
/*     */   
/*     */   public NoSlow() {
/*  56 */     super("NoSlow", ModuleType.Movement);
/*  57 */     addSettings(new Value[] { (Value)this.mode, (Value)this.multiplier, (Value)this.antiSoulSand, (Value)this.amount });
/*  58 */     getInstance = this;
/*     */   }
/*     */   public boolean enabled; public boolean should_send_block_placement; public boolean usingItem; public boolean waitC03;
/*     */   
/*     */   public void onDisable() {
/*  63 */     this.time.reset();
/*  64 */     this.msTimer.reset();
/*  65 */     this.packetBuf.clear();
/*  66 */     this.waitC03 = false;
/*  67 */     this.nextTemp = false;
/*  68 */     super.onDisable();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(LoadWorldEvent e) {
/*  73 */     this.packetBuf.clear();
/*     */   }
/*     */   
/*     */   private boolean isBlocking() {
/*  77 */     return ((mc.thePlayer.isBlocking() || KillAura.getInstance.target != null) && mc.thePlayer
/*  78 */       .getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/*  84 */     if (this.mode.is("Watchdog")) {
/*     */       
/*  86 */       Packet packet = event.getPacket();
/*     */       
/*  88 */       if (packet instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)packet).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM && mc.thePlayer
/*  89 */         .getHeldItem() != null && 
/*  90 */         !(mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemBow)) {
/*  91 */         this.enabled = false;
/*  92 */         event.setCancelled(true);
/*     */       } 
/*     */       
/*  95 */       if (packet instanceof C08PacketPlayerBlockPlacement && mc.gameSettings.keyBindUseItem.isKeyDown() && !this.enabled && mc.thePlayer
/*  96 */         .getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemFood || (mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion && !ItemPotion.isSplash(mc.thePlayer.getHeldItem().getMetadata())) || mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemBucketMilk)) {
/*  97 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if ((this.mode.is("Grim") || this.mode.is("Watchdog")) && event.getState() == PacketEvent.State.OUTGOING && mc.thePlayer != null && mc.theWorld != null) {
/* 103 */       Packet<?> packet = event.getPacket();
/* 104 */       if (this.nextTemp) {
/* 105 */         if ((packet instanceof C07PacketPlayerDigging || packet instanceof C08PacketPlayerBlockPlacement) && isBlocking()) {
/* 106 */           event.setCancelled(true);
/* 107 */         } else if (packet instanceof net.minecraft.network.play.client.C03PacketPlayer || packet instanceof net.minecraft.network.play.client.C0APacketAnimation || packet instanceof C0BPacketEntityAction || packet instanceof net.minecraft.network.play.client.C02PacketUseEntity || packet instanceof C07PacketPlayerDigging || packet instanceof C08PacketPlayerBlockPlacement) {
/*     */           
/* 109 */           this.packetBuf.add(packet);
/* 110 */           event.setCancelled(true);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 115 */     if (this.mode.is("Vulcan") && event.getState() == PacketEvent.State.OUTGOING && mc.thePlayer != null && mc.theWorld != null) {
/* 116 */       Packet<?> packet = event.getPacket();
/* 117 */       if (this.nextTemp) {
/* 118 */         if ((packet instanceof C07PacketPlayerDigging || packet instanceof C08PacketPlayerBlockPlacement) && isBlocking()) {
/* 119 */           event.setCancelled(true);
/* 120 */         } else if (packet instanceof net.minecraft.network.play.client.C03PacketPlayer || packet instanceof net.minecraft.network.play.client.C0APacketAnimation || packet instanceof C0BPacketEntityAction || packet instanceof net.minecraft.network.play.client.C02PacketUseEntity || packet instanceof C07PacketPlayerDigging || packet instanceof C08PacketPlayerBlockPlacement) {
/*     */           
/* 122 */           if (this.waitC03 && packet instanceof net.minecraft.network.play.client.C03PacketPlayer) {
/* 123 */             this.waitC03 = false;
/*     */             
/*     */             return;
/*     */           } 
/* 127 */           this.packetBuf.add(packet);
/* 128 */           event.setCancelled(true);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 133 */     if (this.mode.is("Medusa")) {
/* 134 */       if ((mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking()) && this.sendPacket) {
/* 135 */         sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
/* 136 */         this.sendPacket = false;
/*     */       } 
/* 138 */       if (!mc.thePlayer.isUsingItem() || !mc.thePlayer.isBlocking()) {
/* 139 */         this.sendPacket = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(MotionUpdateEvent event) {
/* 147 */     if (this.mode.is("Grim") || this.mode.is("Watchdog")) {
/* 148 */       if (event.getState() == MotionUpdateEvent.State.PRE && (
/* 149 */         this.lastBlockingStat || isBlocking())) {
/* 150 */         if (this.msTimer.hasTimePassed(230L) && this.nextTemp) {
/* 151 */           this.nextTemp = false;
/* 152 */           sendPacketNoEvent((Packet)new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
/* 153 */           sendPacketNoEvent((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/* 154 */           if (!this.packetBuf.isEmpty()) {
/* 155 */             boolean canAttack = false;
/* 156 */             for (Packet<?> packet : this.packetBuf) {
/* 157 */               if (packet instanceof net.minecraft.network.play.client.C03PacketPlayer) {
/* 158 */                 canAttack = true;
/*     */               }
/* 160 */               if ((!(packet instanceof net.minecraft.network.play.client.C02PacketUseEntity) && !(packet instanceof net.minecraft.network.play.client.C0APacketAnimation)) || canAttack) {
/* 161 */                 sendPacketNoEvent(packet);
/*     */               }
/*     */             } 
/* 164 */             this.packetBuf.clear();
/*     */           } 
/*     */         } 
/* 167 */         if (!this.nextTemp) {
/* 168 */           this.lastBlockingStat = isBlocking();
/* 169 */           if (!isBlocking()) {
/*     */             return;
/*     */           }
/* 172 */           sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory
/* 173 */                 .getCurrentItem(), 0.0F, 0.0F, 0.0F));
/* 174 */           this.nextTemp = true;
/* 175 */           this.msTimer.reset();
/*     */         }
/*     */       
/*     */       } 
/* 179 */     } else if (this.mode.is("Vulcan")) {
/* 180 */       if (event.getState() == MotionUpdateEvent.State.PRE && (
/* 181 */         this.lastBlockingStat || isBlocking())) {
/* 182 */         if (this.msTimer.hasTimePassed(230L) && this.nextTemp) {
/* 183 */           this.nextTemp = false;
/* 184 */           sendPacketNoEvent((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
/*     */           
/* 186 */           if (!this.packetBuf.isEmpty()) {
/* 187 */             boolean canAttack = false;
/* 188 */             for (Packet<?> packet : this.packetBuf) {
/* 189 */               if (packet instanceof net.minecraft.network.play.client.C03PacketPlayer) {
/* 190 */                 canAttack = true;
/*     */               }
/* 192 */               if ((!(packet instanceof net.minecraft.network.play.client.C02PacketUseEntity) && !(packet instanceof net.minecraft.network.play.client.C0APacketAnimation)) || canAttack) {
/* 193 */                 sendPacketNoEvent(packet);
/*     */               }
/*     */             } 
/* 196 */             this.packetBuf.clear();
/*     */           } 
/*     */         } 
/* 199 */         if (!this.nextTemp) {
/* 200 */           this.lastBlockingStat = isBlocking();
/* 201 */           if (!isBlocking()) {
/*     */             return;
/*     */           }
/* 204 */           sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory
/* 205 */                 .getCurrentItem(), 0.0F, 0.0F, 0.0F));
/* 206 */           this.nextTemp = true;
/* 207 */           this.waitC03 = true;
/* 208 */           this.msTimer.reset();
/*     */         }
/*     */       
/*     */       } 
/* 212 */     } else if (this.mode.is("Watchdog")) {
/* 213 */       if (!isMoving()) {
/*     */         return;
/*     */       }
/*     */       
/* 217 */       if (event.getState() == MotionUpdateEvent.State.PRE) {
/*     */         
/* 219 */         if (this.should_send_block_placement) {
/* 220 */           for (int slot = 1; slot <= 3; slot++) {
/* 221 */             if (MoveUtils.INSTANCE.isOnGround(slot)) {
/* 222 */               BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - slot, mc.thePlayer.posZ);
/* 223 */               MovingObjectPosition position = new MovingObjectPosition(new Vec3((int)mc.thePlayer.posX + 0.5D, ((int)mc.thePlayer.posY - slot) + 0.5D, (int)mc.thePlayer.posZ + 0.5D), EnumFacing.DOWN, pos);
/* 224 */               mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, position.sideHit, position.hitVec);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 229 */           this.should_send_block_placement = false;
/*     */         } 
/*     */         
/* 232 */         if (!this.enabled && mc.thePlayer.isUsingItem() && !(mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword)) {
/* 233 */           this.enabled = true;
/* 234 */           MovingObjectPosition mousePos = mc.objectMouseOver;
/* 235 */           if (mousePos.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
/* 236 */             event.setPitch(90.0F);
/* 237 */             this.should_send_block_placement = true;
/*     */             
/*     */             return;
/*     */           } 
/* 241 */           mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), mousePos.getBlockPos(), mousePos.sideHit, mousePos.hitVec);
/*     */         }
/* 243 */         else if (this.enabled && !mc.thePlayer.isUsingItem()) {
/* 244 */           this.enabled = false;
/*     */         } 
/*     */         
/* 247 */         if (mc.gameSettings.keyBindUseItem.isKeyDown() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemBow) {
/* 248 */           int slot = mc.thePlayer.inventory.currentItem;
/* 249 */           sendPacketNoEvent((Packet)new C09PacketHeldItemChange((slot + 1) % 8));
/* 250 */           sendPacketNoEvent((Packet)new C09PacketHeldItemChange(slot));
/*     */         } 
/*     */       } 
/* 253 */     } else if (this.mode.is("Intave")) {
/* 254 */       if (event.getState() == MotionUpdateEvent.State.PRE) {
/* 255 */         Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
/*     */         
/* 257 */         if (mc.thePlayer.isUsingItem()) {
/* 258 */           if (item instanceof net.minecraft.item.ItemSword) {
/* 259 */             BlinkComponent.blinking = true;
/* 260 */             if (mc.thePlayer.ticksExisted % 5 == 0) {
/* 261 */               send((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 262 */               BlinkComponent.dispatch();
/* 263 */               sendNoEvent((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
/*     */             }
/*     */           
/* 266 */           } else if (item instanceof net.minecraft.item.ItemFood || item instanceof net.minecraft.item.ItemBow) {
/* 267 */             BlinkComponent.blinking = true;
/*     */           } 
/*     */           
/* 270 */           this.usingItem = true;
/* 271 */         } else if (this.usingItem) {
/* 272 */           this.usingItem = false;
/* 273 */           BlinkComponent.blinking = false;
/*     */         } 
/*     */       } 
/* 276 */     } else if (this.mode.is("Hawk")) {
/*     */       
/* 278 */       if (isMoving() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem() != null) {
/* 279 */         send((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
/* 280 */         send((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*     */       }
/*     */     
/* 283 */     } else if (this.mode.is("Taka")) {
/*     */       
/* 285 */       if (isUsingFood() && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem() != null) {
/* 286 */         send((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
/* 287 */         send((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*     */       }
/*     */     
/* 290 */     } else if (this.mode.is("AAC4.4.2")) {
/*     */       
/* 292 */       if (mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem() != null) {
/* 293 */         send((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
/* 294 */         send((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*     */       }
/*     */     
/* 297 */     } else if (this.mode.is("OldIntave") && 
/* 298 */       mc.thePlayer.isUsingItem()) {
/* 299 */       if (event.getState() == MotionUpdateEvent.State.PRE) {
/* 300 */         sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
/* 301 */         sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*     */       } 
/* 303 */       if (event.getState() == MotionUpdateEvent.State.POST) {
/* 304 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventoryContainer.getSlot(mc.thePlayer.inventory.currentItem + 36).getStack()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onBlock(MotionUpdateEvent event) {
/* 312 */     if (mc.thePlayer == null || mc.theWorld == null) {
/*     */       return;
/*     */     }
/* 315 */     if (!isMoving()) {
/*     */       return;
/*     */     }
/* 318 */     if (this.mode.is("Matrix")) {
/*     */       
/* 320 */       if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/* 321 */         if (isUsingFood() && mc.thePlayer.getItemInUseDuration() >= 1) {
/* 322 */           this.matrixShouldSlowdown = true;
/* 323 */           sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/* 324 */         } else if (this.matrixShouldSlowdown) {
/* 325 */           this.matrixShouldSlowdown = false;
/*     */         }
/*     */       
/*     */       }
/* 329 */     } else if (this.mode.is("HmXix")) {
/*     */       
/* 331 */       if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/*     */         
/* 333 */         if (isUsingFood() && mc.thePlayer.getItemInUseDuration() >= 1) {
/* 334 */           sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*     */         }
/*     */       }
/* 337 */       else if (mc.thePlayer.isBlocking()) {
/* 338 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory
/* 339 */               .getCurrentItem(), 0.0F, 0.0F, 0.0F));
/*     */       }
/*     */     
/* 342 */     } else if (this.mode.is("ZQAT")) {
/*     */       
/* 344 */       if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/* 345 */         if (isUsingFood() && mc.thePlayer.getItemInUseDuration() >= 1) {
/* 346 */           this.matrixShouldSlowdown = true;
/* 347 */           sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/* 348 */         } else if (this.matrixShouldSlowdown) {
/* 349 */           this.matrixShouldSlowdown = false;
/*     */         } 
/* 351 */       } else if (mc.thePlayer.isBlocking()) {
/* 352 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory
/* 353 */               .getCurrentItem(), 0.0F, 0.0F, 0.0F));
/*     */       }
/*     */     
/* 356 */     } else if (this.mode.is("OldNCP")) {
/* 357 */       if (hasSword() && mc.thePlayer.isBlocking()) {
/* 358 */         if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/* 359 */           sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/*     */         } else {
/* 361 */           sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
/*     */         } 
/*     */       }
/* 364 */     } else if (this.mode.is("NCP")) {
/* 365 */       if (isUsingFood()) {
/*     */         return;
/*     */       }
/* 368 */       if (event.getState().equals(MotionUpdateEvent.State.POST) && mc.thePlayer.isUsingItem()) {
/* 369 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventPreUpdate e) {
/* 376 */     setSuffix((Serializable)this.mode.get());
/*     */     
/* 378 */     if (mc.thePlayer == null || mc.theWorld == null) {
/*     */       return;
/*     */     }
/* 381 */     if (!isMoving()) {
/*     */       return;
/*     */     }
/* 384 */     if (this.mode.is("Matrix")) {
/* 385 */       if (hasSword() && mc.thePlayer.isBlocking() && mc.thePlayer.ticksExisted % 7 == 0 && MoveUtils.INSTANCE.isOnGround(0.42D)) {
/* 386 */         sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.UP));
/*     */       }
/*     */     }
/* 389 */     else if (this.mode.is("Taka") && hasSword() && mc.thePlayer.isBlocking() && mc.thePlayer.ticksExisted % 2 == 0 && (MoveUtils.INSTANCE
/* 390 */       .isOnGround(0.42D) || !mc.thePlayer.onGround)) {
/* 391 */       sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.UP));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 399 */     this.msTimer.reset();
/* 400 */     this.packetBuf.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacketRec(EventPacketReceive e) {
/* 406 */     if (this.mode.is("NCP")) {
/*     */       
/* 408 */       if (!isMoving() && isUsingFood()) {
/*     */         return;
/*     */       }
/* 411 */       if (mc.thePlayer.isUsingItem() && e.getPacket() instanceof net.minecraft.network.play.server.S30PacketWindowItems) {
/* 412 */         e.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onSlowDown(SlowdownEvent event) {
/* 420 */     if (this.mode.is("Watchdog")) {
/* 421 */       event.cancelEvent();
/* 422 */     } else if (this.mode.is("Prediction")) {
/* 423 */       if (mc.thePlayer.onGroundTicks % ((Double)this.amount.getValue()).intValue() != 0) {
/* 424 */         event.cancelEvent();
/*     */       }
/*     */     } else {
/* 427 */       event.cancelEvent();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isUsingFood() {
/* 432 */     if (mc.thePlayer.getItemInUse() == null) {
/* 433 */       return false;
/*     */     }
/* 435 */     Item usingItem = mc.thePlayer.getItemInUse().getItem();
/* 436 */     return (mc.thePlayer.isUsingItem() && (usingItem instanceof net.minecraft.item.ItemFood || usingItem instanceof net.minecraft.item.ItemBucketMilk || usingItem instanceof ItemPotion));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\NoSlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */