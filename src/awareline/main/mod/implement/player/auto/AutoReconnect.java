/*    */ package awareline.main.mod.implement.player.auto;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.misc.EventChat;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class AutoReconnect
/*    */   extends Module
/*    */ {
/*    */   public AutoReconnect() {
/* 12 */     super("AutoReconnect", new String[] { "ar" }, ModuleType.Player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onChat(EventChat e) {
/* 17 */     if (e.getMessage().contains("Flying or related."))
/* 18 */       mc.thePlayer.sendChatMessage("/back"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\auto\AutoReconnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */