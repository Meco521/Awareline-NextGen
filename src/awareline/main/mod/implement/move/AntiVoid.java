/*     */ package awareline.main.mod.implement.move;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.event.events.world.worldChangeEvents.LoadWorldEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import awareline.main.utility.PlayerUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ public class AntiVoid
/*     */   extends Module
/*     */ {
/*  30 */   private final String[] fallmode = new String[] { "Packet", "Watchdog", "Collision", "Vulcan", "Bounce", "Teleport", "Flag" };
/*     */ 
/*     */ 
/*     */   
/*  34 */   private final Mode<String> mode = new Mode("Mode", this.fallmode, this.fallmode[0]);
/*  35 */   private final Numbers<Double> fallDistance = new Numbers("FallDistance", 
/*  36 */       Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/*  37 */   private final ConcurrentLinkedQueue<C03PacketPlayer> packets = new ConcurrentLinkedQueue<>();
/*  38 */   public TimeHelper timer = new TimeHelper();
/*     */   private double prevX;
/*     */   private double prevY;
/*     */   
/*     */   public AntiVoid() {
/*  43 */     super("AntiVoid", ModuleType.Movement);
/*  44 */     addSettings(new Value[] { (Value)this.mode, (Value)this.fallDistance });
/*  45 */     getInstance = this;
/*     */   }
/*     */   private double prevZ; private Vec3 position; public static AntiVoid getInstance;
/*     */   
/*     */   public void onDisable() {
/*  50 */     this.prevX = 0.0D;
/*  51 */     this.prevY = 0.0D;
/*  52 */     this.prevZ = 0.0D;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWorldChange(LoadWorldEvent e) {
/*  57 */     this.packets.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPre(EventPreUpdate event) {
/*  62 */     if (this.mode.is("Vulcan")) {
/*  63 */       if (mc.thePlayer.fallDistance > ((Double)this.fallDistance.get()).floatValue()) {
/*  64 */         event.setPosY(event.getPosY() - event.getPosY() % 0.015625D);
/*  65 */         event.setOnGround(true);
/*  66 */         mc.thePlayer.motionY = -0.08D;
/*  67 */         MoveUtils.INSTANCE.stop();
/*     */       } 
/*  69 */     } else if (this.mode.is("Collision") && 
/*  70 */       mc.thePlayer.fallDistance > ((Double)this.fallDistance.getValue()).intValue() && 
/*  71 */       !PlayerUtil.isBlockUnder() && mc.thePlayer.posY + mc.thePlayer.motionY < Math.floor(mc.thePlayer.posY)) {
/*  72 */       mc.thePlayer.motionY = Math.floor(mc.thePlayer.posY) - mc.thePlayer.posY;
/*  73 */       if (mc.thePlayer.motionY == 0.0D) {
/*  74 */         mc.thePlayer.onGround = true;
/*  75 */         event.setOnGround(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/*  83 */     if (this.mode.is("Watchdog")) {
/*  84 */       Packet<?> p = event.getPacket();
/*     */       
/*  86 */       if (p instanceof C03PacketPlayer) {
/*  87 */         C03PacketPlayer wrapper = (C03PacketPlayer)p;
/*     */         
/*  89 */         if (!isBlockUnder()) {
/*  90 */           this.packets.add(wrapper);
/*  91 */           event.setCancelled(true);
/*     */           
/*  93 */           if (this.position != null && mc.thePlayer.fallDistance > ((Double)this.fallDistance.get()).floatValue()) {
/*  94 */             sendNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.position.xCoord, this.position.yCoord - 1.0D, this.position.zCoord, false));
/*     */           }
/*     */         } else {
/*  97 */           if (mc.thePlayer.onGround) {
/*  98 */             this.position = new Vec3(wrapper.x, wrapper.y, wrapper.z);
/*     */           }
/*     */           
/* 101 */           if (!this.packets.isEmpty()) {
/* 102 */             KillAura.getInstance.target = null;
/* 103 */             KillAura.getInstance.targets.clear();
/* 104 */             this.packets.forEach(this::sendNoEvent);
/* 105 */             this.packets.clear();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onMove(EventMove e) {
/* 114 */     setSuffix((Serializable)this.mode.get());
/*     */     
/* 116 */     EntityPlayerSP player = mc.thePlayer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     boolean isInVoid = !isBlockUnder();
/*     */ 
/*     */ 
/*     */     
/* 125 */     if (!moduleCheck()) {
/*     */       return;
/*     */     }
/* 128 */     if (!isInVoid && player.fallDistance < 1.0D && player.onGround) {
/* 129 */       this.prevX = player.prevPosX;
/* 130 */       this.prevY = player.prevPosY;
/* 131 */       this.prevZ = player.prevPosZ;
/*     */     } 
/*     */     
/* 134 */     if (isInVoid) {
/* 135 */       if (this.mode.is("Teleport")) {
/* 136 */         if (fallDistanceCheck()) {
/* 137 */           player.setPositionAndUpdate(this.prevX, this.prevY, this.prevZ);
/*     */         }
/* 139 */       } else if (this.mode.is("Flag")) {
/* 140 */         if (fallDistanceCheck()) {
/* 141 */           player.motionY += 0.1D;
/* 142 */           player.fallDistance = 0.0F;
/*     */         } 
/* 144 */       } else if (this.mode.is("Bounce")) {
/* 145 */         if (!player.onGround && !player.isCollidedVertically && 
/* 146 */           player.fallDistance > 4.0F && player.prevPosY < this.prevY) {
/* 147 */           player.motionY += 0.23D;
/*     */         }
/*     */       }
/* 150 */       else if (this.mode.is("Packet") && 
/* 151 */         fallDistanceCheck()) {
/* 152 */         sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + player.fallDistance, player.posZ, false));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean fallDistanceCheck() {
/* 160 */     EntityPlayerSP player = mc.thePlayer;
/* 161 */     if (!player.onGround && !player.isCollidedVertically) {
/* 162 */       return (player.fallDistance > ((Double)this.fallDistance.get()).doubleValue());
/*     */     }
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean moduleCheck() {
/* 169 */     EntityPlayerSP player = mc.thePlayer;
/* 170 */     if (player.posY > 0.0D) {
/* 171 */       return !Scaffold.getInstance.isEnabled();
/*     */     }
/* 173 */     return (!Flight.getInstance.isEnabled() && !Longjump.getInstance.isEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockUnder() {
/* 178 */     for (int i = (int)(mc.thePlayer.posY - 1.0D); i > 0; i--) {
/* 179 */       BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
/* 180 */       if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir)) {
/* 181 */         return true;
/*     */       }
/*     */     } 
/* 184 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isInVoid() {
/* 188 */     for (int i = 0; i <= 128; i++) {
/* 189 */       if (MoveUtils.INSTANCE.isOnGround(i)) {
/* 190 */         return false;
/*     */       }
/*     */     } 
/* 193 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\AntiVoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */