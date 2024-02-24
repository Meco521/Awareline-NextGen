/*     */ package awareline.main.mod.implement.move;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.BBSetEvent;
/*     */ import awareline.main.event.events.world.packetEvents.PacketEvent;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class WallClimb extends Module {
/*  17 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Motion", "FastJump", "Jump", "AAC4.3.6", "Vulcan", "Verus", "MineMenClub", "Kauri" }, "Jump");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  23 */   private final Numbers<Double> Speed = new Numbers("MotionSpeed", 
/*  24 */       Double.valueOf(0.1D), Double.valueOf(0.0D), Double.valueOf(0.5D), Double.valueOf(0.01D), () -> Boolean.valueOf(this.mode.is("Motion")));
/*     */   boolean climbing;
/*     */   private boolean hitHead;
/*     */   
/*     */   public WallClimb() {
/*  29 */     super("WallClimb", ModuleType.Movement);
/*  30 */     addSettings(new Value[] { (Value)this.mode, (Value)this.Speed });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate e) {
/*  35 */     setSuffix((Serializable)this.mode.get());
/*  36 */     if (this.mode.is("Motion")) {
/*  37 */       if (mc.thePlayer.isCollidedHorizontally) {
/*  38 */         mc.thePlayer.motionY += ((Double)this.Speed.get()).doubleValue();
/*  39 */         this.climbing = true;
/*     */       }
/*  41 */       else if (this.climbing) {
/*  42 */         mc.thePlayer.motionY = 0.0D;
/*  43 */         this.climbing = false;
/*     */       }
/*     */     
/*  46 */     } else if (this.mode.is("FastJump")) {
/*  47 */       if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {
/*  48 */         if (mc.thePlayer.onGround) {
/*  49 */           mc.thePlayer.motionY = 0.39D;
/*  50 */         } else if (mc.thePlayer.motionY < 0.0D) {
/*  51 */           mc.thePlayer.motionY = -0.24D;
/*     */         } 
/*     */       }
/*  54 */     } else if (this.mode.is("Jump") || this.mode.is("AAC4.3.6")) {
/*  55 */       if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && 
/*  56 */         mc.thePlayer.onGround) {
/*  57 */         mc.thePlayer.jump();
/*     */       }
/*     */     }
/*  60 */     else if (this.mode.is("Vulcan")) {
/*  61 */       if (mc.thePlayer.isCollidedHorizontally) {
/*  62 */         if (mc.thePlayer.ticksExisted % 2 == 0) {
/*  63 */           e.setOnGround(true);
/*  64 */           mc.thePlayer.motionY = MoveUtils.INSTANCE.jumpMotion();
/*     */         } 
/*     */         
/*  67 */         double yaw = MoveUtils.INSTANCE.direction();
/*  68 */         e.setX(e.getPosX() - (-MathHelper.sin((float)yaw) * 0.1F));
/*  69 */         e.setZ(e.getPosZ() - (MathHelper.cos((float)yaw) * 0.1F));
/*     */       } 
/*  71 */     } else if (this.mode.is("Verus")) {
/*  72 */       if (mc.thePlayer.isCollidedHorizontally && 
/*  73 */         mc.thePlayer.ticksExisted % 2 == 0) {
/*  74 */         mc.thePlayer.jump();
/*     */       }
/*     */     }
/*  77 */     else if (this.mode.is("MineMenClub")) {
/*  78 */       if (mc.thePlayer.isCollidedHorizontally && !this.hitHead && mc.thePlayer.ticksExisted % 3 == 0) {
/*  79 */         mc.thePlayer.motionY = MoveUtils.INSTANCE.jumpMotion();
/*     */       }
/*     */       
/*  82 */       if (mc.thePlayer.isCollidedVertically) {
/*  83 */         this.hitHead = !mc.thePlayer.onGround;
/*     */       }
/*  85 */     } else if (this.mode.is("Kauri") && 
/*  86 */       mc.thePlayer.isCollidedHorizontally && 
/*  87 */       mc.thePlayer.ticksExisted % 3 == 0) {
/*  88 */       e.setOnGround(true);
/*  89 */       mc.thePlayer.jump();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockCollide(BBSetEvent e) {
/*  97 */     if ((this.mode.is("Jump") || this.mode.is("FastJump") || this.mode.is("AAC4.3.6")) && 
/*  98 */       mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {
/*  99 */       e.boxes.add((new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)).offset(mc.thePlayer.posX, ((int)mc.thePlayer.posY - 1), mc.thePlayer.posZ));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPacket(PacketEvent event) {
/* 106 */     if ((this.mode.is("Jump") || this.mode.is("FastJump") || this.mode.is("AAC4.3.6")) && 
/* 107 */       event.getState() == PacketEvent.State.OUTGOING) { this; if (mc.thePlayer != null) { this; if (mc.theWorld != null && 
/* 108 */           event.getPacket() instanceof C03PacketPlayer) {
/* 109 */           C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)event.getPacket();
/* 110 */           if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {
/* 111 */             double speed = 1.0E-10D;
/* 112 */             float f = MoveUtils.INSTANCE.getDirection2();
/* 113 */             c03PacketPlayer.x -= MathHelper.sin(f) * 1.0E-10D;
/* 114 */             c03PacketPlayer.z += MathHelper.cos(f) * 1.0E-10D;
/*     */           } 
/*     */         }  }
/*     */        }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\WallClimb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */