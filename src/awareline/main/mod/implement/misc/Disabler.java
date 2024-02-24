/*     */ package awareline.main.mod.implement.misc;
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventJump;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.worldChangeEvents.LoadWorldEvent;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.move.AntiVoid;
/*     */ import awareline.main.mod.implement.move.InvMove;
/*     */ import awareline.main.mod.implement.move.Longjump;
/*     */ import awareline.main.mod.implement.move.Speed;
/*     */ import awareline.main.mod.implement.player.Blink;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.math.RotationUtil;
/*     */ import awareline.main.utility.timer.TimerUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.network.play.client.C0CPacketInput;
/*     */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class Disabler extends Module {
/*  39 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Watchdog", "OmniSprint", "Vehicle", "DCJ", "Vulcan", "CubeCraft", "NCP", "AACv4LessFlag", "Strafe" }, "Watchdog");
/*     */ 
/*     */   
/*  42 */   private final TimerUtils watchdogCheck = new TimerUtils();
/*     */   private final List<C03PacketPlayer> blinkPacketList;
/*  44 */   private final CopyOnWriteArrayList<Packet> watchdogPlayerPackets = new CopyOnWriteArrayList<>();
/*     */   boolean stuckMove;
/*     */   boolean cancelC03;
/*     */   private float lastYaw;
/*     */   private float lastPitch;
/*     */   
/*     */   public Disabler() {
/*  51 */     super("Disabler", new String[] { "dis", "disable" }, ModuleType.Misc);
/*  52 */     addSettings(new Value[] { (Value)this.mode });
/*  53 */     this.blinkPacketList = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  58 */     if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  65 */     this.cancelC03 = false;
/*  66 */     if (mc.thePlayer == null) {
/*     */       return;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJump(EventJump e) {
/*  73 */     if (this.mode.is("Vulcan") && ((
/*  74 */       !mc.gameSettings.keyBindForward.isKeyDown() && isMoving()) || KillAura.getInstance.isEnabled())) {
/*  75 */       sendNoEvent((Packet)new C03PacketPlayer(true));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate e) {
/*  82 */     VerifyData.instance.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     setSuffix((Serializable)this.mode.get());
/*  88 */     if (this.mode.is("NCP")) {
/*  89 */       if (mc.gameSettings.keyBindAttack.isKeyDown() || mc.gameSettings.keyBindDrop.isKeyDown() || mc.gameSettings.keyBindUseItem
/*  90 */         .isKeyDown() || KillAura.getInstance.target != null || Scaffold.getInstance.isEnabled()) {
/*     */         return;
/*     */       }
/*  93 */       float shouldYaw = (float)Math.toDegrees(MoveUtils.INSTANCE.getDirection());
/*  94 */       float shouldPitch = 90.0F;
/*  95 */       this.lastYaw = RotationUtil.getRotateForScaffold(shouldYaw, 90.0F, this.lastYaw, this.lastPitch, 120.0F, 180.0F)[0];
/*     */       
/*  97 */       this.lastPitch = RotationUtil.getRotateForScaffold(shouldYaw, 90.0F, this.lastYaw, this.lastPitch, 120.0F, 180.0F)[1];
/*     */       
/*  99 */       e.setYaw(this.lastYaw);
/* 100 */       e.setPitch(this.lastPitch);
/* 101 */     } else if (this.mode.is("Vehicle")) {
/* 102 */       send((Packet)new C0CPacketInput());
/* 103 */     } else if (this.mode.is("Vulcan")) {
/* 104 */       if (((!mc.gameSettings.keyBindForward.isKeyDown() && isMoving()) || KillAura.getInstance.isEnabled()) && e.isOnGround()) {
/* 105 */         e.setOnGround((mc.thePlayer.onGroundTicks % 2 == 0));
/*     */       }
/* 107 */       if (!mc.thePlayer.isSwingInProgress) {
/* 108 */         if (mc.thePlayer.ticksExisted % 20 == 0) {
/* 109 */           send((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
/* 110 */         } else if (mc.thePlayer.ticksExisted % 10 == 0) {
/* 111 */           send((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
/*     */         } 
/*     */       }
/* 114 */     } else if (this.mode.is("Watchdog")) {
/* 115 */       this.stuckMove = !this.watchdogCheck.hasReached(500L);
/* 116 */     } else if (this.mode.is("DCJ")) {
/*     */       
/* 118 */       mc.thePlayer.removePotionEffectClient(Potion.blindness.getId());
/*     */       
/* 120 */       mc.thePlayer.removePotionEffectClient(Potion.moveSlowdown.getId());
/*     */       
/* 122 */       mc.thePlayer.ridingEntity = null;
/*     */       
/* 124 */       if (mc.thePlayer.onGround) {
/* 125 */         mc.thePlayer.fallDistance = 0.1F;
/*     */       }
/*     */       
/* 128 */       PlayerCapabilities playerCapabilities = new PlayerCapabilities();
/* 129 */       playerCapabilities.isFlying = false;
/* 130 */       playerCapabilities.allowFlying = false;
/* 131 */       playerCapabilities.setFlySpeed(0.0F);
/* 132 */       sendPacketNoEvent((Packet)new C13PacketPlayerAbilities(playerCapabilities));
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(EventMove e) {
/* 138 */     if (this.mode.is("Watchdog") && 
/* 139 */       this.stuckMove) {
/* 140 */       MoveUtils.INSTANCE.pause(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent e) {
/* 147 */     if (this.mode.is("Strafe")) {
/* 148 */       if (e.getPacket() instanceof C03PacketPlayer && !mc.thePlayer.isPotionActive(Potion.jump)) {
/* 149 */         C03PacketPlayer c03 = (C03PacketPlayer)e.getPacket();
/* 150 */         if (Speed.getInstance.isEnabled() && mc.thePlayer.fallDistance < 1.0F) {
/* 151 */           c03.setOnGround(true);
/*     */         }
/* 153 */         if (Speed.getInstance.isEnabled() && mc.thePlayer.ticksExisted % 4 != 0 && mc.thePlayer.fallDistance < 1.0F) {
/*     */           
/* 155 */           e.cancel();
/* 156 */           this.watchdogPlayerPackets.add(e.getPacket());
/* 157 */         } else if (!this.watchdogPlayerPackets.isEmpty()) {
/* 158 */           this.watchdogPlayerPackets.forEach(this::sendPacketNoEvent);
/* 159 */           this.watchdogPlayerPackets.clear();
/*     */         } 
/*     */       } 
/* 162 */     } else if (this.mode.is("AACv4LessFlag")) {
/* 163 */       if (e.getState() == PacketEvent.State.INCOMING) {
/* 164 */         Packet packet = e.getPacket();
/* 165 */         if (packet instanceof S08PacketPlayerPosLook) {
/* 166 */           S08PacketPlayerPosLook packetS08 = (S08PacketPlayerPosLook)packet;
/* 167 */           double x = packetS08.getX() - mc.thePlayer.posX;
/* 168 */           double y = packetS08.getY() - mc.thePlayer.posY;
/* 169 */           double z = packetS08.getZ() - mc.thePlayer.posZ;
/* 170 */           double diff = Math.sqrt(x * x + y * y + z * z);
/* 171 */           if (diff <= 8.0D) {
/* 172 */             e.setCancelled(true);
/* 173 */             sendPacketNoEvent((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(packetS08.getX(), packetS08.getY(), packetS08.getZ(), packetS08.getYaw(), packetS08.getPitch(), true));
/*     */           } 
/*     */         } 
/*     */       } 
/* 177 */     } else if (this.mode.is("Watchdog")) {
/* 178 */       Packet<?> packet = e.getPacket();
/* 179 */       if (e.getState() == PacketEvent.State.INCOMING && 
/* 180 */         e.getPacket() instanceof S08PacketPlayerPosLook) {
/* 181 */         this.stuckMove = true;
/*     */       }
/*     */       
/* 184 */       if (InvMove.getInstance.isEnabled() && !InvMove.getInstance.bypass.is("Watchdog")) {
/* 185 */         if (packet instanceof C03PacketPlayer) {
/* 186 */           if (this.cancelC03) {
/* 187 */             C03PacketPlayer wrapper = (C03PacketPlayer)packet;
/* 188 */             this.blinkPacketList.add(wrapper);
/* 189 */             e.setCancelled(true);
/* 190 */           } else if (!this.blinkPacketList.isEmpty()) {
/* 191 */             msg("realese");
/* 192 */             C03PacketPlayer wrapper = (C03PacketPlayer)packet;
/* 193 */             this.blinkPacketList.add(wrapper);
/* 194 */             e.setCancelled(true);
/* 195 */             this.blinkPacketList.forEach(this::sendNoEvent);
/* 196 */             this.blinkPacketList.clear();
/*     */           } 
/*     */         }
/* 199 */         msg(this.stuckMove + "");
/* 200 */         if (isMoving()) {
/* 201 */           this.stuckMove = e.getPacket() instanceof net.minecraft.network.play.client.C0EPacketClickWindow;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 206 */       if (packet instanceof C0BPacketEntityAction) {
/* 207 */         C0BPacketEntityAction wrapper = (C0BPacketEntityAction)packet;
/*     */         
/* 209 */         if (wrapper.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING)) {
/* 210 */           if (mc.thePlayer.serverSprintState) {
/* 211 */             sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
/* 212 */             mc.thePlayer.serverSprintState = false;
/*     */           } 
/*     */           
/* 215 */           e.setCancelled(true);
/*     */         } 
/*     */         
/* 218 */         if (wrapper.getAction().equals(C0BPacketEntityAction.Action.STOP_SPRINTING)) {
/* 219 */           e.setCancelled(true);
/*     */         }
/*     */       } 
/* 222 */       if (packet instanceof C02PacketUseEntity) {
/* 223 */         C02PacketUseEntity pue = (C02PacketUseEntity)packet;
/* 224 */         if (pue.getAction() == C02PacketUseEntity.Action.ATTACK && needPreventAttack()) {
/* 225 */           e.setCancelled(true);
/*     */         }
/*     */       } 
/* 228 */       if (packet instanceof net.minecraft.network.play.client.C0APacketAnimation && 
/* 229 */         needPreventAttack()) {
/* 230 */         e.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(LoadWorldEvent e) {
/* 238 */     this.blinkPacketList.clear();
/* 239 */     this.cancelC03 = false;
/*     */   }
/*     */   
/*     */   private boolean needPreventAttack() {
/* 243 */     Longjump longJump = Longjump.getInstance;
/* 244 */     Blink blink = (Blink)getModule(Blink.class);
/*     */     
/* 246 */     boolean antiVoidCheck = (AntiVoid.getInstance.isEnabled() && (AntiVoid.getInstance.isInVoid() || AntiVoid.getInstance.isBlockUnder()));
/* 247 */     return (longJump.isEnabled() || blink.isEnabled() || this.stuckMove || antiVoidCheck);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPacketSend(EventPacketSend e) {
/* 252 */     if (this.mode.is("NCP")) {
/* 253 */       if (e.getPacket() instanceof net.minecraft.network.play.client.C0FPacketConfirmTransaction || e.getPacket() instanceof net.minecraft.network.play.client.C00PacketKeepAlive) {
/* 254 */         e.cancel();
/*     */       }
/*     */       
/* 257 */       Packet<?> p = e.getPacket();
/* 258 */       if (p instanceof C0BPacketEntityAction) {
/* 259 */         C0BPacketEntityAction wrapper = (C0BPacketEntityAction)p;
/*     */         
/* 261 */         if (wrapper.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING)) {
/* 262 */           if (mc.thePlayer.serverSprintState) {
/* 263 */             sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
/* 264 */             mc.thePlayer.serverSprintState = false;
/*     */           } 
/*     */           
/* 267 */           e.setCancelled(true);
/*     */         } 
/*     */         
/* 270 */         if (wrapper.getAction().equals(C0BPacketEntityAction.Action.STOP_SPRINTING)) {
/* 271 */           e.setCancelled(true);
/*     */         }
/*     */       } 
/* 274 */     } else if (this.mode.is("OmniSprint")) {
/* 275 */       Packet<?> p = e.getPacket();
/*     */       
/* 277 */       if (p instanceof C0BPacketEntityAction) {
/* 278 */         C0BPacketEntityAction wrapper = (C0BPacketEntityAction)p;
/*     */         
/* 280 */         if (wrapper.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING)) {
/* 281 */           if (mc.thePlayer.serverSprintState) {
/* 282 */             sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
/* 283 */             mc.thePlayer.serverSprintState = false;
/*     */           } 
/*     */           
/* 286 */           e.setCancelled(true);
/*     */         } 
/*     */         
/* 289 */         if (wrapper.getAction().equals(C0BPacketEntityAction.Action.STOP_SPRINTING)) {
/* 290 */           e.setCancelled(true);
/*     */         }
/*     */       } 
/* 293 */     } else if (this.mode.is("CubeCraft")) {
/* 294 */       if (e.getPacket() instanceof C03PacketPlayer && mc.currentScreen == null) {
/* 295 */         send((Packet)new C08PacketPlayerBlockPlacement((new BlockPos((Entity)mc.thePlayer)).down(5), EnumFacing.UP
/* 296 */               .getIndex(), null, 0.0F, 1.0F, 0.0F));
/* 297 */       } else if (e.getPacket() instanceof net.minecraft.network.play.client.C00PacketKeepAlive) {
/* 298 */         e.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\Disabler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */