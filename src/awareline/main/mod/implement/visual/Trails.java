/*    */ package awareline.main.mod.implement.visual;
/*    */ import awareline.antileak.VerifyData;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ 
/*    */ public class Trails extends Module {
/* 13 */   private final Mode<String> mode = new Mode("Mode", new String[] { "SMOKE", "HEART", "FIREWORK", "FLAME", "CLOUD", "WATER", "LAVA", "SLIME" }, "SMOKE");
/*    */   
/*    */   public Trails() {
/* 16 */     super("Trails", ModuleType.Render);
/* 17 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void onUpdate(EventPreUpdate e) {
/* 23 */     VerifyData.instance.getClass();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 29 */     setSuffix((Serializable)this.mode.get());
/* 30 */     if (isMoving())
/* 31 */       switch ((String)this.mode.get()) {
/*    */         case "HEART":
/* 33 */           mc.effectRenderer.emitParticleAtEntity((Entity)mc.thePlayer, EnumParticleTypes.HEART);
/*    */           break;
/*    */         case "LAVA":
/* 36 */           mc.effectRenderer.emitParticleAtEntity((Entity)mc.thePlayer, EnumParticleTypes.LAVA);
/*    */           break;
/*    */         case "SMOKE":
/* 39 */           mc.effectRenderer.emitParticleAtEntity((Entity)mc.thePlayer, EnumParticleTypes.REDSTONE);
/*    */           break;
/*    */         case "CLOUD":
/* 42 */           mc.effectRenderer.emitParticleAtEntity((Entity)mc.thePlayer, EnumParticleTypes.CLOUD);
/*    */           break;
/*    */         case "FLAME":
/* 45 */           mc.effectRenderer.emitParticleAtEntity((Entity)mc.thePlayer, EnumParticleTypes.FLAME);
/*    */           break;
/*    */         case "SLIME":
/* 48 */           mc.effectRenderer.emitParticleAtEntity((Entity)mc.thePlayer, EnumParticleTypes.SLIME);
/*    */           break;
/*    */         case "WATER":
/* 51 */           mc.effectRenderer.emitParticleAtEntity((Entity)mc.thePlayer, EnumParticleTypes.WATER_SPLASH);
/*    */           break;
/*    */         case "FIREWORK":
/* 54 */           mc.effectRenderer.emitParticleAtEntity((Entity)mc.thePlayer, EnumParticleTypes.FIREWORKS_SPARK);
/*    */           break;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Trails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */