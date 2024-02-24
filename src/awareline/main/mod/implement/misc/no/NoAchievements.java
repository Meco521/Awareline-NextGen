/*    */ package awareline.main.mod.implement.misc.no;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class NoAchievements
/*    */   extends Module
/*    */ {
/*    */   public NoAchievements() {
/* 12 */     super("NoAchievements", new String[] { "na" }, ModuleType.Misc);
/*    */   }
/*    */   
/*    */   @EventHandler(4)
/*    */   public void onTick(EventTick eventTick) {
/* 17 */     mc.guiAchievement.clearAchievements();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\no\NoAchievements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */