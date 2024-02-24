/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.antileak.VerifyData;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.misc.EventRenderEntity;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class FPSBoost extends Module {
/*    */   private final Option<Boolean> adaptiveRenderDistance;
/*    */   private final Option<Boolean> crappyModels;
/*    */   private final Option<Boolean> clearFarEntities;
/*    */   public boolean clear;
/*    */   
/*    */   public FPSBoost() {
/* 22 */     super("FPSBoost", ModuleType.Render);
/* 23 */     this.adaptiveRenderDistance = new Option("AdaptiveRenderDistance", Boolean.valueOf(true));
/* 24 */     this.crappyModels = new Option("RemoveBots", Boolean.valueOf(true));
/* 25 */     this.clearFarEntities = new Option("ClearFarEntities", Boolean.valueOf(true));
/* 26 */     addSettings(new Value[] { (Value)this.adaptiveRenderDistance, (Value)this.crappyModels, (Value)this.clearFarEntities });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate event) {
/* 31 */     VerifyData.instance.getClass();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 36 */     this.clear = ((Boolean)this.crappyModels.getValue()).booleanValue();
/* 37 */     if (((Boolean)this.adaptiveRenderDistance.getValue()).booleanValue()) {
/* 38 */       EntityLivingBase entity = getFarthest(96.0D);
/* 39 */       if (entity == null) {
/* 40 */         mc.gameSettings.renderDistanceChunks = 4;
/*    */       } else {
/* 42 */         int renderDistanceChunks; GameSettings gameSettings = mc.gameSettings;
/*    */         
/* 44 */         if (mc.thePlayer.getDistanceToEntity((Entity)entity) > 96.0F) {
/* 45 */           renderDistanceChunks = 6;
/*    */         } else {
/* 47 */           renderDistanceChunks = (int)(mc.thePlayer.getDistanceToEntity((Entity)entity) / 16.0F);
/*    */         } 
/* 49 */         gameSettings.renderDistanceChunks = renderDistanceChunks;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onRenderPlayer(EventRenderEntity event) {
/* 56 */     Entity entity = event.getEntity();
/* 57 */     if (entity.getDistanceToEntity((Entity)mc.thePlayer) > 100.0F && ((Boolean)this.clearFarEntities.getValue()).booleanValue()) {
/* 58 */       event.setCancelled(true);
/*    */     }
/*    */   }
/*    */   
/*    */   private EntityLivingBase getFarthest(double range) {
/* 63 */     double dist = range;
/* 64 */     EntityLivingBase target = null;
/* 65 */     for (Entity object : mc.theWorld.loadedEntityList) {
/* 66 */       if (object instanceof EntityLivingBase) {
/* 67 */         EntityLivingBase player = (EntityLivingBase)object;
/* 68 */         double currentDist = mc.thePlayer.getDistanceToEntity((Entity)player);
/* 69 */         if (currentDist < dist) {
/*    */           continue;
/*    */         }
/* 72 */         dist = currentDist;
/* 73 */         target = player;
/*    */       } 
/*    */     } 
/* 76 */     return target;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\FPSBoost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */