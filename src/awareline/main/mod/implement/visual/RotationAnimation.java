/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.event.events.world.renderEvents.EventRotationAnimation;
/*    */ import awareline.main.event.events.world.updateEvents.EventPostUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class RotationAnimation extends Module {
/*    */   public static RotationAnimation getInstance;
/* 16 */   public final Mode<String> ka = new Mode("Kill/TP Aura", new String[] { "Off", "Body", "Head", "Bitch" }, "Body");
/* 17 */   public final Mode<String> scaffold = new Mode("Scaffold", new String[] { "Off", "Body", "Head", "Bitch" }, "Body");
/* 18 */   public final Mode<String> fucker = new Mode("BedFucker", new String[] { "Off", "Body", "Head", "Bitch" }, "Body");
/* 19 */   public final Mode<String> chest = new Mode("ChestAura", new String[] { "Off", "Body", "Head", "Bitch" }, "Body");
/* 20 */   public final Mode<String> fire = new Mode("AntiFireBall", new String[] { "Off", "Body", "Head", "Bitch" }, "Body");
/* 21 */   public final Option<Boolean> slowBody = new Option("SlowBody", Boolean.valueOf(false));
/*    */   public float yaw;
/*    */   public float pitch;
/*    */   public float prevYaw;
/*    */   public float prevPitch;
/*    */   
/*    */   public RotationAnimation() {
/* 28 */     super("RotationAnimation", ModuleType.Render);
/* 29 */     addSettings(new Value[] { (Value)this.slowBody, (Value)this.ka, (Value)this.scaffold, (Value)this.fucker, (Value)this.chest, (Value)this.fire });
/* 30 */     getInstance = this;
/* 31 */     setHide(false);
/* 32 */     setEnabled(false);
/*    */   }
/*    */   
/*    */   public static float interpolateAngle(float p_219805_0_, float p_219805_1_, float p_219805_2_) {
/* 36 */     return p_219805_1_ + p_219805_0_ * MathHelper.wrapAngleTo180_float(p_219805_2_ - p_219805_1_);
/*    */   }
/*    */   
/*    */   public static float lerp(float pct, float start, float end) {
/* 40 */     return start + pct * (end - start);
/*    */   }
/*    */   
/*    */   @EventHandler(4)
/*    */   public void onPre(EventPostUpdate e) {
/* 45 */     this.yaw = e.getYaw();
/* 46 */     this.pitch = e.getPitch();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick e) {
/* 51 */     this.prevYaw = this.yaw;
/* 52 */     this.prevPitch = this.pitch;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onRotationAnimation(EventRotationAnimation e) {
/* 57 */     if (e.getEntity() == mc.thePlayer && e.getPartialTicks() != 1.0F && mc.thePlayer.ridingEntity == null) {
/* 58 */       e.setRenderYawOffset(interpolateAngle(e.getPartialTicks(), this.prevYaw, this.yaw));
/* 59 */       e.setRenderHeadYaw(interpolateAngle(e.getPartialTicks(), this.prevYaw, this.yaw) - e.getRenderYawOffset());
/* 60 */       e.setRenderHeadPitch(lerp(e.getPartialTicks(), this.prevPitch, this.pitch));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\RotationAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */