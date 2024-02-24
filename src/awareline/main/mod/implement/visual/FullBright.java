/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ public class FullBright extends Module {
/* 14 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Gamma", "NightPotion" }, "Gamma"); private float oldGamma;
/*    */   
/*    */   public FullBright() {
/* 17 */     super("FullBright", ModuleType.Render);
/* 18 */     addSettings(new Value[] { (Value)this.mode });
/* 19 */     setEnabledByConvention(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 24 */     this.oldGamma = mc.gameSettings.gammaSetting;
/* 25 */     if (mc.thePlayer != null) {
/* 26 */       mc.thePlayer.removePotionEffectClient(Potion.nightVision.id);
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler(3)
/*    */   private void onTick(EventTick e) {
/* 32 */     setSuffix((Serializable)this.mode.get());
/* 33 */     if (this.mode.is("Gamma")) {
/* 34 */       mc.gameSettings.gammaSetting = 100.0F;
/*    */     } else {
/* 36 */       mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2147483647, 1));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 42 */     if (this.mode.is("Gamma")) {
/* 43 */       mc.gameSettings.gammaSetting = this.oldGamma;
/*    */     }
/* 45 */     else if (mc.thePlayer.isPotionActive(Potion.nightVision)) {
/* 46 */       mc.thePlayer.removePotionEffect(Potion.nightVision.id);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\FullBright.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */