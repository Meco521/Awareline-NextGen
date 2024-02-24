/*     */ package awareline.main.mod.implement.move;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.MoveUtils;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class NoWeb extends Module {
/*  11 */   private final Mode<String> modes = new Mode("Mode", new String[] { "Vanilla", "AAC5.0.1", "AAC4.3.6", "AAC3.3.11", "AAC3.0.1", "Spartan", "OldMatrix" }, "Vanilla");
/*     */   
/*     */   private boolean usedTimer;
/*     */ 
/*     */   
/*     */   public NoWeb() {
/*  17 */     super("NoWeb", ModuleType.Movement);
/*  18 */     addSettings(new Value[] { (Value)this.modes });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  23 */     mc.timer.timerSpeed = 1.0F;
/*  24 */     super.onDisable();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventPreUpdate e) {
/*  29 */     setSuffix((Serializable)this.modes.get());
/*     */     
/*  31 */     if (this.usedTimer) {
/*  32 */       mc.timer.timerSpeed = 1.0F;
/*  33 */       this.usedTimer = false;
/*     */     } 
/*     */     
/*  36 */     if (!mc.thePlayer.isInWeb) {
/*     */       return;
/*     */     }
/*     */     
/*  40 */     switch ((String)this.modes.get()) {
/*     */       case "Vanilla":
/*  42 */         if (mc.thePlayer.isInWeb) {
/*  43 */           mc.thePlayer.isInWeb = false;
/*     */         }
/*     */         break;
/*     */       case "AAC5.0.1":
/*  47 */         mc.thePlayer.jumpMovementFactor = 0.42F;
/*     */         
/*  49 */         if (mc.thePlayer.onGround) {
/*  50 */           mc.thePlayer.jump();
/*     */         }
/*     */         break;
/*     */       case "AAC4.3.6":
/*  54 */         mc.timer.timerSpeed = 0.99F;
/*  55 */         mc.thePlayer.jumpMovementFactor = 0.02958F;
/*  56 */         mc.thePlayer.motionY -= 0.00775D;
/*  57 */         if (mc.thePlayer.onGround) {
/*  58 */           mc.thePlayer.motionY = 0.405D;
/*  59 */           mc.timer.timerSpeed = 1.35F;
/*     */         } 
/*  61 */         mc.gameSettings.keyBindJump.pressed = false;
/*     */         break;
/*     */       
/*     */       case "AAC3.3.11":
/*  65 */         mc.thePlayer.jumpMovementFactor = 0.59F;
/*     */         
/*  67 */         if (!mc.gameSettings.keyBindSneak.isKeyDown()) {
/*  68 */           mc.thePlayer.motionY = 0.0D;
/*     */         }
/*     */         break;
/*     */       case "AAC3.0.1":
/*  72 */         mc.thePlayer.jumpMovementFactor = (mc.thePlayer.movementInput.moveStrafe != 0.0F) ? 1.0F : 1.21F;
/*     */         
/*  74 */         if (!mc.gameSettings.keyBindSneak.isKeyDown()) {
/*  75 */           mc.thePlayer.motionY = 0.0D;
/*     */         }
/*  77 */         if (mc.thePlayer.onGround) {
/*  78 */           mc.thePlayer.jump();
/*     */         }
/*     */         break;
/*     */       case "Spartan":
/*  82 */         MoveUtils.INSTANCE.strafe(0.27F);
/*  83 */         mc.timer.timerSpeed = 3.7F;
/*  84 */         if (!mc.gameSettings.keyBindSneak.isKeyDown())
/*  85 */           mc.thePlayer.motionY = 0.0D; 
/*  86 */         if (mc.thePlayer.ticksExisted % 2 == 0) {
/*  87 */           mc.timer.timerSpeed = 1.7F;
/*     */         }
/*  89 */         if (mc.thePlayer.ticksExisted % 40 == 0) {
/*  90 */           mc.timer.timerSpeed = 3.0F;
/*     */         }
/*  92 */         this.usedTimer = true;
/*     */         break;
/*     */       
/*     */       case "OldMatrix":
/*  96 */         mc.thePlayer.jumpMovementFactor = 0.12413333F;
/*  97 */         mc.thePlayer.motionY = -0.0125D;
/*  98 */         if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.thePlayer.motionY = -0.1625D; 
/*  99 */         if (mc.thePlayer.onGround) {
/* 100 */           mc.thePlayer.jump();
/* 101 */           mc.thePlayer.motionY = 0.2425D;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\NoWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */