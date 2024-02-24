/*     */ package awareline.main.mod.implement.player;
/*     */ import awareline.main.component.impl.BlinkComponent;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.move.Flight;
/*     */ import awareline.main.mod.implement.world.utils.DelayTimer;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.PlayerUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.ItemBucket;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class NoFall extends Module {
/*  39 */   public final Option<Boolean> outdis = new Option("OutDistance", Boolean.valueOf(false));
/*  40 */   public final Numbers<Double> outdiss = new Numbers("OutDistances", Double.valueOf(20.0D), Double.valueOf(4.0D), Double.valueOf(300.0D), Double.valueOf(1.0D));
/*  41 */   private final String[] antiMode = new String[] { "Packet", "Watchdog", "Spoof", "Mineplex", "Minemora", "AAC3.3.15", "AAC4.4.0", "OldNCP", "RNCP", "ACR", "FakeGround", "NoGround", "Edit", "ChunkLoad", "MLG", "Blink" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public final Mode<String> mode = new Mode("Mode", this.antiMode, this.antiMode[0]);
/*  53 */   private final LinkedBlockingQueue<Packet> aac4Packets = new LinkedBlockingQueue<>();
/*  54 */   private final DelayTimer refillTimer = new DelayTimer();
/*  55 */   private final DelayTimer timer = new DelayTimer();
/*     */   
/*     */   private boolean aac4Fakelag;
/*     */   private boolean aac4PacketModify;
/*     */   private boolean needSpoof;
/*     */   private boolean reFill;
/*     */   private boolean fakeUnloaded;
/*     */   private boolean running;
/*     */   
/*     */   public NoFall() {
/*  65 */     super("NoFall", ModuleType.Player);
/*  66 */     addSettings(new Value[] { (Value)this.mode, (Value)this.outdis, (Value)this.outdiss });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  71 */     this.aac4Fakelag = false;
/*  72 */     this.aac4PacketModify = false;
/*  73 */     this.aac4Packets.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  78 */     mc.timer.timerSpeed = 1.0F;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(EventPreUpdate event) {
/*  83 */     if ((this.mode.is("Watchdog") || this.mode.is("MLG")) && ((mc.thePlayer.fallDistance > 4.0F && 
/*  84 */       BlockUtils.getDistanceToFall() < 10.0D && getSlotWaterBucket() != -1 && isMLGNeeded()) || this.reFill)) {
/*  85 */       event.setPitch(90.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(EventMove event) {
/*  91 */     if ((this.mode.is("Watchdog") || this.mode.is("MLG")) && ((mc.thePlayer.fallDistance > 4.0F && BlockUtils.getDistanceToFall() < 10.0D && getSlotWaterBucket() != -1 && isMLGNeeded()) || this.reFill)) {
/*  92 */       event.setX(0.0D);
/*  93 */       event.setZ(0.0D);
/*     */     } 
/*  95 */     if (this.mode.is("OldNCP") && 
/*  96 */       !mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.disableDamage && mc.thePlayer.motionY < 0.0D && mc.thePlayer.fallDistance > 3.0F) {
/*  97 */       sendPacketNoEvent((Packet)new C03PacketPlayer(true));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeWater(BlockPos pos) {
/* 103 */     ItemStack heldItem = mc.thePlayer.inventory.getCurrentItem();
/*     */     
/* 105 */     mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), pos, EnumFacing.UP, new Vec3(pos
/* 106 */           .getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D));
/* 107 */     if (heldItem != null) {
/* 108 */       mc.playerController.sendUseItem((EntityPlayer)mc.thePlayer, (World)mc.theWorld, heldItem);
/* 109 */       mc.entityRenderer.itemRenderer.resetEquippedProgress2();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void swapToWaterBucket(int blockSlot) {
/* 114 */     mc.thePlayer.inventory.currentItem = blockSlot;
/* 115 */     sendPacket((Packet)new C09PacketHeldItemChange(blockSlot));
/*     */   }
/*     */   
/*     */   private int getSlotWaterBucket() {
/* 119 */     for (int i = 0; i < 8; i++) {
/* 120 */       if (mc.thePlayer.inventory.mainInventory[i] != null && mc.thePlayer.inventory.mainInventory[i].getItem().getUnlocalizedName().contains("bucketWater"))
/* 121 */         return i; 
/*     */     } 
/* 123 */     return -1;
/*     */   }
/*     */   
/*     */   private boolean isMLGNeeded() {
/* 127 */     if (mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE || mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR || mc.thePlayer.capabilities.isFlying || mc.thePlayer.capabilities.allowFlying) {
/* 128 */       return false;
/*     */     }
/* 130 */     if (Flight.getInstance.isEnabled()) {
/* 131 */       return false;
/*     */     }
/* 133 */     for (double y = mc.thePlayer.posY; y > 0.0D; y--) {
/* 134 */       Block block = BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, y, mc.thePlayer.posZ));
/* 135 */       if (block.getMaterial() == Material.water) {
/* 136 */         return false;
/*     */       }
/*     */       
/* 139 */       if (block.getMaterial() != Material.air) {
/* 140 */         return true;
/*     */       }
/* 142 */       if (y < 0.0D) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPost(EventPostUpdate post) {
/* 152 */     if (this.mode.is("Watchdog") || this.mode.is("MLG")) {
/* 153 */       if (BlockUtils.isInLiquid() && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory
/* 154 */         .getCurrentItem().getItem() instanceof ItemBucket && !this.timer.hasPassed(1000.0D) && this.reFill) {
/* 155 */         if (((ItemBucket)mc.thePlayer.inventory.getCurrentItem().getItem()).getIsFull().getMaterial() == Material.air) {
/* 156 */           BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
/* 157 */           placeWater(pos);
/* 158 */           this.reFill = false;
/*     */         } 
/* 160 */       } else if (this.refillTimer.hasPassed(1500.0D) && this.reFill) {
/* 161 */         this.reFill = false;
/*     */       } 
/*     */       
/* 164 */       if (mc.thePlayer.fallDistance > 4.0F && BlockUtils.getDistanceToFall() < 10.0D && getSlotWaterBucket() != -1 && isMLGNeeded() && 
/* 165 */         BlockUtils.getDistanceToFall() < 3.0D && this.timer.hasPassed(500.0D)) {
/* 166 */         this.timer.reset();
/*     */         
/* 168 */         swapToWaterBucket(getSlotWaterBucket());
/*     */         
/* 170 */         BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - BlockUtils.getDistanceToFall() - 1.0D, mc.thePlayer.posZ);
/* 171 */         placeWater(pos);
/* 172 */         this.reFill = true;
/* 173 */         this.refillTimer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPre(EventPreUpdate event) {
/* 181 */     if (this.mode.is("ChunkLoad")) {
/* 182 */       if (this.fakeUnloaded) {
/* 183 */         mc.thePlayer.motionY = 0.0D;
/* 184 */         event.setOnGround(false);
/* 185 */         event.setPosY(event.getPosY() - 0.09799999743700027D);
/* 186 */         mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, event.getPosY(), mc.thePlayer.posZ);
/*     */         
/*     */         return;
/*     */       } 
/* 190 */       if (mc.thePlayer.motionY > 0.0D || mc.thePlayer.fallDistance <= 3.0F) {
/*     */         return;
/*     */       }
/*     */       
/* 194 */       Block nextBlock = PlayerUtil.block(new BlockPos(event
/* 195 */             .getPosX(), event
/* 196 */             .getPosY() + mc.thePlayer.motionY, event
/* 197 */             .getPosZ()));
/*     */ 
/*     */       
/* 200 */       if (nextBlock.getMaterial().isSolid()) {
/* 201 */         mc.thePlayer.fallDistance = 0.0F;
/* 202 */         this.fakeUnloaded = true;
/*     */       } 
/* 204 */     } else if (this.mode.is("Blink")) {
/* 205 */       float distance = mc.thePlayer.fallDistance;
/* 206 */       if (distance > 3.0F && PlayerUtil.isBlockUnder(20.0D)) {
/* 207 */         BlinkComponent.blinking = true;
/* 208 */         BlinkComponent.setExempt(new Class[] { C0APacketAnimation.class, C02PacketUseEntity.class, C03PacketPlayer.class, C03PacketPlayer.C04PacketPlayerPosition.class, C03PacketPlayer.C06PacketPlayerPosLook.class });
/*     */ 
/*     */ 
/*     */         
/* 212 */         event.setOnGround(true);
/* 213 */         send((Packet)new C08PacketPlayerBlockPlacement(null));
/* 214 */         this.running = true;
/* 215 */       } else if (this.running) {
/* 216 */         this.running = false;
/* 217 */         BlinkComponent.blinking = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate e) {
/* 225 */     if (((Boolean)this.outdis.get()).booleanValue()) {
/* 226 */       setSuffix((String)this.mode.get() + " (" + ((Double)this.outdiss.get()).intValue() + " blocks)");
/*     */     } else {
/* 228 */       setSuffix((Serializable)this.mode.get());
/*     */     } 
/* 230 */     if (((Boolean)this.outdis.get()).booleanValue() && 
/* 231 */       mc.thePlayer.fallDistance > ((Double)this.outdiss.get()).floatValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 235 */     if (this.mode.is("NoGround") && 
/* 236 */       MoveUtils.INSTANCE.isOnGround(0.001D)) {
/* 237 */       e.setY(e.getY() + 1.0E-4D);
/*     */     }
/*     */     
/* 240 */     if (!mc.thePlayer.isSpectator() && !mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.disableDamage && !mc.thePlayer.onGround) {
/* 241 */       switch (((String)this.mode.get()).toLowerCase()) {
/*     */         case "packet":
/* 243 */           if (mc.thePlayer.fallDistance >= 2.0F) {
/* 244 */             sendPacket((Packet)new C03PacketPlayer(true));
/*     */           }
/*     */           break;
/*     */         
/*     */         case "mineplex":
/* 249 */           if (mc.thePlayer.fallDistance > 2.5F) {
/* 250 */             sendPacket((Packet)new C03PacketPlayer(true));
/* 251 */             mc.thePlayer.fallDistance = 0.5F;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case "verus":
/* 256 */           if (mc.thePlayer.fallDistance - mc.thePlayer.motionY > 3.0D) {
/*     */             
/* 258 */             mc.thePlayer.motionY = 0.0D;
/* 259 */             mc.thePlayer.fallDistance = 0.0F;
/* 260 */             EntityPlayerSP playerSP = mc.thePlayer;
/* 261 */             playerSP.motionX *= 0.6D;
/* 262 */             playerSP.motionZ *= 0.6D;
/* 263 */             this.needSpoof = true;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case "minemora":
/* 268 */           if (mc.thePlayer.fallDistance > 2.0F) {
/* 269 */             if (!mc.isIntegratedServerRunning()) {
/* 270 */               sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, Double.NaN, mc.thePlayer.posZ, false));
/*     */             }
/*     */             
/* 273 */             mc.thePlayer.fallDistance = -9999.0F;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case "aac3.3.15":
/* 278 */           if (mc.thePlayer.fallDistance > 2.0F) {
/* 279 */             if (!mc.isIntegratedServerRunning())
/* 280 */               sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, Double.NaN, mc.thePlayer.posZ, false)); 
/* 281 */             mc.thePlayer.fallDistance = -9999.0F;
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case "aac4.4.0":
/* 288 */           if (!inVoid()) {
/* 289 */             if (this.aac4Fakelag) {
/* 290 */               this.aac4Fakelag = false;
/* 291 */               if (!this.aac4Packets.isEmpty()) {
/* 292 */                 Iterator<Packet> iterator = this.aac4Packets.iterator();
/* 293 */                 while (iterator.hasNext()) {
/* 294 */                   C03PacketPlayer packet = (C03PacketPlayer)iterator.next();
/* 295 */                   sendPacketNoEvent((Packet)packet);
/*     */                 } 
/*     */                 
/* 298 */                 this.aac4Packets.clear();
/*     */               } 
/*     */             } 
/*     */             break;
/*     */           } 
/* 303 */           if (mc.thePlayer.onGround && this.aac4Fakelag) {
/* 304 */             this.aac4Fakelag = false;
/* 305 */             if (!this.aac4Packets.isEmpty()) {
/* 306 */               Iterator<Packet> iterator = this.aac4Packets.iterator();
/*     */               
/* 308 */               while (iterator.hasNext()) {
/* 309 */                 C03PacketPlayer packet = (C03PacketPlayer)iterator.next();
/* 310 */                 sendPacketNoEvent((Packet)packet);
/*     */               } 
/*     */               
/* 313 */               this.aac4Packets.clear();
/*     */             } 
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 319 */           if (mc.thePlayer.fallDistance > 3.0F && this.aac4Fakelag) {
/* 320 */             this.aac4PacketModify = true;
/* 321 */             mc.thePlayer.fallDistance = 0.0F;
/*     */           } 
/*     */           
/* 324 */           if (inAir(4.0D, 1.0D)) {
/*     */             break;
/*     */           }
/*     */           
/* 328 */           if (!this.aac4Fakelag) {
/* 329 */             this.aac4Fakelag = true;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(EventPacketReceive event) {
/* 339 */     if (this.mode.is("Minemora") && 
/* 340 */       event.getPacket() instanceof C03PacketPlayer && mc.thePlayer.fallDistance >= 2.0F) {
/* 341 */       C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)event.getPacket();
/* 342 */       c03PacketPlayer.moving = true;
/*     */     } 
/*     */   } @EventHandler
/*     */   public void onPacket(PacketEvent e) {
/*     */     C03PacketPlayer c03;
/*     */     double[] packetPosition;
/*     */     double[] myPosition;
/*     */     boolean same;
/* 350 */     if (((Boolean)this.outdis.get()).booleanValue() && 
/* 351 */       mc.thePlayer.fallDistance > ((Double)this.outdiss.get()).floatValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 355 */     switch (((String)this.mode.get()).toLowerCase()) {
/*     */       case "spoof":
/* 357 */         if (e.getPacket() instanceof C03PacketPlayer) {
/* 358 */           C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)e.getPacket();
/* 359 */           if (mc.thePlayer.fallDistance > 3.0F && isBlockUnder()) {
/* 360 */             c03PacketPlayer.onGround = true;
/* 361 */             mc.thePlayer.fallDistance = 0.0F;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "oldncp":
/* 367 */         if (e.getPacket() instanceof C03PacketPlayer) {
/* 368 */           C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)e.getPacket();
/* 369 */           if (!mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.disableDamage && mc.thePlayer.motionY < 0.0D && c03PacketPlayer.isMoving() && mc.thePlayer.fallDistance > 2.0F) {
/* 370 */             e.setCancelled(true);
/* 371 */             sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(c03PacketPlayer.x, c03PacketPlayer.y, c03PacketPlayer.z, c03PacketPlayer.onGround));
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "fakeground":
/* 377 */         if (e.getPacket() instanceof C03PacketPlayer) {
/* 378 */           C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
/* 379 */           packet.setOnGround(true);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "aac4.4.0":
/* 384 */         if (this.aac4Fakelag) {
/* 385 */           e.setCancelled(true);
/* 386 */           if (this.aac4PacketModify) {
/* 387 */             if (e.getPacket() instanceof C03PacketPlayer) {
/* 388 */               C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
/* 389 */               packet.setOnGround(true);
/*     */             } 
/* 391 */             this.aac4PacketModify = false;
/*     */           } 
/* 393 */           this.aac4Packets.add(e.getPacket());
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "verus":
/* 398 */         if (this.needSpoof && !MoveUtils.INSTANCE.isOverVoid()) {
/* 399 */           ((C03PacketPlayer)e.getPacket()).setOnGround(true);
/* 400 */           this.needSpoof = false;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "acr":
/*     */       case "rncp":
/* 406 */         if (e.getPacket() instanceof C03PacketPlayer) {
/* 407 */           C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
/* 408 */           if (mc.thePlayer.fallDistance > 0.5D) {
/* 409 */             packet.setOnGround(true);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "edit":
/* 415 */         c03 = (C03PacketPlayer)e.getPacket();
/* 416 */         packetPosition = new double[] { c03.getPositionX(), c03.getPositionY(), c03.getPositionZ() };
/* 417 */         myPosition = new double[] { mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ };
/* 418 */         same = (packetPosition[0] == myPosition[0] && packetPosition[1] == myPosition[1] && packetPosition[2] == myPosition[2]);
/* 419 */         if (!mc.thePlayer.isSpectator() && !mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.disableDamage && !mc.thePlayer.onGround && mc.thePlayer.fallDistance >= 2.5F && same && 
/* 420 */           !c03.isOnGround()) {
/* 421 */           c03.setOnGround(true);
/*     */         }
/*     */         break;
/*     */       
/*     */       case "noground":
/* 426 */         if (e.getPacket() instanceof C03PacketPlayer) {
/* 427 */           C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
/* 428 */           packet.setOnGround(false);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBlockUnder() {
/* 437 */     for (int i = (int)(mc.thePlayer.posY - 1.0D); i > 0; i--) {
/* 438 */       BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
/* 439 */       if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir)) {
/* 440 */         return true;
/*     */       }
/*     */     } 
/* 443 */     return false;
/*     */   }
/*     */   
/*     */   private boolean inAir(double height, double plus) {
/* 447 */     if (mc.thePlayer.posY >= 0.0D) {
/* 448 */       int off; for (off = 0; off < height; off = (int)(off + plus)) {
/* 449 */         AxisAlignedBB bb = new AxisAlignedBB(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.posX, mc.thePlayer.posY - off, mc.thePlayer.posZ);
/* 450 */         if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, bb).isEmpty()) {
/* 451 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 456 */     return false;
/*     */   }
/*     */   
/*     */   private boolean inVoid() {
/* 460 */     if (mc.thePlayer.posY >= 0.0D) {
/* 461 */       for (int off = 0; off < mc.thePlayer.posY + 2.0D; off += 2) {
/* 462 */         AxisAlignedBB bb = new AxisAlignedBB(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.posX, off, mc.thePlayer.posZ);
/* 463 */         if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, bb).isEmpty()) {
/* 464 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 469 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\NoFall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */