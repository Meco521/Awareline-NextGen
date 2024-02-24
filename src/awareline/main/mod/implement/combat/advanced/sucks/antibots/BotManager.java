/*    */ package awareline.main.mod.implement.combat.advanced.sucks.antibots;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.EventManager;
/*    */ import awareline.main.event.events.world.worldChangeEvents.RespawnEvent;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BotManager
/*    */   extends ArrayList<Entity>
/*    */ {
/*    */   public void init() {
/* 18 */     EventManager.register(new Object[] { this });
/*    */   }
/*    */   
/*    */   @EventHandler(4)
/*    */   public void onWorldChange(RespawnEvent e) {
/* 23 */     clear();
/*    */   }
/*    */   
/*    */   public boolean add(Entity entity) {
/* 27 */     if (!contains(entity)) super.add(entity); 
/* 28 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\sucks\antibots\BotManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */