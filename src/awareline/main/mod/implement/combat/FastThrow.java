/*    */ package awareline.main.mod.implement.combat;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class FastThrow extends Module {
/*    */   public FastThrow() {
/* 11 */     super("FastThrow", ModuleType.Combat);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick e) {
/* 16 */     Item item = mc.thePlayer.getHeldItem().getItem();
/* 17 */     if ((item instanceof net.minecraft.item.ItemSnowball || item instanceof net.minecraft.item.ItemPotion || item instanceof net.minecraft.item.ItemEgg || item instanceof net.minecraft.item.ItemExpBottle || item instanceof net.minecraft.item.ItemFishingRod) && mc.gameSettings.keyBindUseItem.isKeyDown())
/* 18 */       mc.rightClickDelayTimer = 0; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\FastThrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */