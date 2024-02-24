/*     */ package awareline.main.mod.implement.move;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventStep;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.world.Scaffold;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.BlockUtils;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ 
/*     */ public class Step extends Module {
/*  18 */   public final Numbers<Double> height = new Numbers("Height", 
/*  19 */       Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(0.5D));
/*  20 */   private final String[] StepMode = new String[] { "Vanilla", "Watchdog", "Matrix", "NCP", "Spartan", "Jump", "LowJump" };
/*     */ 
/*     */   
/*  23 */   public final Mode<String> mode = new Mode("Mode", this.StepMode, this.StepMode[3]);
/*  24 */   public final Option<Boolean> timer = new Option("Timer", Boolean.valueOf(true), () -> Boolean.valueOf(!this.mode.isCurrentMode("Vanilla")));
/*     */   
/*  26 */   public final Option<Boolean> noLimit = new Option("NoLimit", Boolean.valueOf(false), () -> Boolean.valueOf(!this.mode.isCurrentMode("Vanilla")));
/*     */   
/*  28 */   private final Numbers<Double> delay = new Numbers("Delay", 
/*  29 */       Double.valueOf(45.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(5.0D));
/*  30 */   private final TimerUtil stepDelay = new TimerUtil(); public boolean isStepping;
/*     */   public static Step getInstance;
/*     */   
/*     */   public Step() {
/*  34 */     super("Step", ModuleType.Movement);
/*  35 */     addSettings(new Value[] { (Value)this.mode, (Value)this.delay, (Value)this.height, (Value)this.timer, (Value)this.noLimit });
/*  36 */     getInstance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  41 */     mc.thePlayer.stepHeight = 0.6F;
/*  42 */     mc.timer.timerSpeed = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  47 */     mc.thePlayer.stepHeight = 0.6F;
/*  48 */     mc.timer.timerSpeed = 1.0F;
/*     */   }
/*     */   
/*     */   private boolean moduleCheck() {
/*  52 */     if (!this.mode.is("Vanilla") && !((Boolean)this.noLimit.get()).booleanValue()) {
/*  53 */       return (Scaffold.getInstance.isEnabled() || Speed.getInstance.isEnabled());
/*     */     }
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*  61 */     setSuffix((Serializable)this.mode.get());
/*  62 */     if (moduleCheck()) {
/*     */       return;
/*     */     }
/*  65 */     mc.thePlayer.stepHeight = 0.625F;
/*  66 */     if (this.mode.is("LowJump") || this.mode.is("Jump")) {
/*  67 */       if (this.stepDelay.hasReached(((Double)this.delay.get()).intValue())) {
/*  68 */         if (!mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.movementInput.jump && mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround && 
/*  69 */           !BlockUtils.isInLiquid()) {
/*  70 */           if (this.mode.is("LowJump")) {
/*  71 */             mc.thePlayer.motionY = 0.36899998784065247D;
/*  72 */           } else if (this.mode.is("Jump")) {
/*  73 */             mc.thePlayer.motionY = 0.41999998688697815D;
/*  74 */           } else if (this.mode.is("Matrix")) {
/*  75 */             mc.thePlayer.motionY = 0.41652D;
/*     */           } 
/*     */         }
/*  78 */         this.stepDelay.reset();
/*     */       } 
/*     */       return;
/*     */     } 
/*  82 */     if (this.stepDelay.hasReached(((Double)this.delay.get()).intValue())) {
/*  83 */       if (this.mode.is("Vanilla")) {
/*  84 */         mc.thePlayer.stepHeight = ((Double)this.height.get()).floatValue();
/*     */       } else {
/*  86 */         if (mc.thePlayer.movementInput != null && 
/*  87 */           !mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.movementInput.jump && mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround && !BlockUtils.isInLiquid()) {
/*  88 */           mc.thePlayer.stepHeight = 1.0F;
/*  89 */           mc.thePlayer.stepHeight = ((Double)this.height.get()).floatValue();
/*     */           
/*     */           return;
/*     */         } 
/*  93 */         mc.thePlayer.stepHeight = 0.625F;
/*     */       } 
/*  95 */       this.stepDelay.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onStep(EventStep event) {
/* 102 */     if (moduleCheck()) {
/*     */       return;
/*     */     }
/* 105 */     if ((this.mode.is("NCP") || this.mode.is("Spartan") || this.mode.is("Watchdog")) && 
/* 106 */       this.stepDelay.hasReached(((Double)this.delay.get()).intValue())) {
/* 107 */       if (event.getStepHeight() >= 0.625F && event.getStepHeight() <= ((Double)this.height.get()).floatValue()) {
/* 108 */         this.isStepping = true;
/* 109 */         if (this.mode.is("Watchdog")) {
/* 110 */           doWatchdogStep((mc.thePlayer.getEntityBoundingBox()).minY - mc.thePlayer.posY);
/*     */         } else {
/* 112 */           doNCPStep((mc.thePlayer.getEntityBoundingBox()).minY - mc.thePlayer.posY);
/*     */         } 
/* 114 */         mc.thePlayer.stepHeight = ((Double)this.height.get()).floatValue();
/* 115 */         if (((Boolean)this.timer.get()).booleanValue()) {
/* 116 */           mc.timer.timerSpeed = 0.55F;
/* 117 */           (new Thread(() -> {
/*     */                 try {
/*     */                   Thread.sleep(100L);
/* 120 */                 } catch (InterruptedException e) {
/*     */                   e.printStackTrace();
/*     */                 } 
/*     */                 this.isStepping = false;
/*     */                 mc.timer.timerSpeed = 1.0F;
/* 125 */               })).start();
/*     */         } else {
/* 127 */           this.isStepping = false;
/*     */         } 
/* 129 */         mc.thePlayer.stepHeight = 0.625F;
/*     */       } 
/* 131 */       this.stepDelay.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doNCPStep(double stepHeight) {
/* 137 */     double posX = mc.thePlayer.posX;
/* 138 */     double posZ = mc.thePlayer.posZ;
/* 139 */     double posY = mc.thePlayer.posY;
/* 140 */     if (stepHeight < 1.0D) {
/* 141 */       double[] array = { 0.39D, 0.698D };
/* 142 */       int length = array.length;
/* 143 */       int j = 0;
/* 144 */       while (j < length) {
/* 145 */         sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + array[j], posZ, false));
/* 146 */         j++;
/*     */       } 
/* 148 */     } else if (stepHeight < 1.1D) {
/* 149 */       double[] array = { 0.42D, 0.748D };
/* 150 */       int length = array.length;
/* 151 */       int j = 0;
/* 152 */       while (j < length) {
/* 153 */         sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + array[j], posZ, false));
/* 154 */         j++;
/*     */       } 
/* 156 */     } else if (stepHeight < 1.6D) {
/* 157 */       double[] array = { 0.42D, 0.753D, 1.001D, 1.084D, 1.006D };
/* 158 */       int length = array.length;
/* 159 */       int j = 0;
/* 160 */       while (j < length) {
/* 161 */         sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + array[j], posZ, false));
/* 162 */         j++;
/*     */       } 
/* 164 */     } else if (stepHeight < 2.1D) {
/* 165 */       double[] array = { 0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D };
/* 166 */       int length = array.length;
/* 167 */       int j = 0;
/* 168 */       while (j < length) {
/* 169 */         sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + array[j], posZ, false));
/* 170 */         j++;
/*     */       } 
/*     */     } else {
/* 173 */       double[] array = { 0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D };
/* 174 */       int length = array.length;
/* 175 */       int j = 0;
/* 176 */       while (j < length) {
/* 177 */         sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + array[j], posZ, false));
/* 178 */         j++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doWatchdogStep(double height) {
/* 184 */     double posX = mc.thePlayer.posX;
/* 185 */     double posY = mc.thePlayer.posY;
/* 186 */     double posZ = mc.thePlayer.posZ;
/* 187 */     sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
/* 188 */     mc.thePlayer.setSprinting(false);
/*     */     
/* 190 */     if (height <= 1.0D) {
/* 191 */       float[] values = { 0.42F, 0.75F };
/* 192 */       if (height != 1.0D) {
/* 193 */         values[0] = (float)(values[0] * height);
/* 194 */         values[1] = (float)(values[1] * height);
/* 195 */         if (values[0] > 0.425D) {
/* 196 */           values[0] = 0.425F;
/*     */         }
/*     */         
/* 199 */         if (values[1] > 0.78D) {
/* 200 */           values[1] = 0.78F;
/*     */         }
/*     */         
/* 203 */         if (values[1] < 0.49D) {
/* 204 */           values[1] = 0.49F;
/*     */         }
/*     */       } 
/*     */       
/* 208 */       if (values[0] == 0.42D) {
/* 209 */         values[0] = 0.42F;
/*     */       }
/*     */       
/* 212 */       mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + values[0], posZ, false));
/* 213 */       if (posY + values[1] < posY + height) {
/* 214 */         mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + values[1], posZ, false));
/*     */       }
/* 216 */     } else if (height <= 1.5D) {
/* 217 */       float[] values = { 0.42F, 0.7532F, 1.001336F, 1.060836F, 0.982436F };
/* 218 */       float[] var10 = values;
/* 219 */       int var11 = values.length;
/*     */       
/* 221 */       for (int var12 = 0; var12 < var11; var12++) {
/* 222 */         double val = var10[var12];
/* 223 */         mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + val, posZ, false));
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
/* 228 */     mc.thePlayer.stepHeight = 0.625F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\Step.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */