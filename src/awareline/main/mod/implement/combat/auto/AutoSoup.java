/*    */ package awareline.main.mod.implement.combat.auto;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.timer.TimerUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*    */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*    */ 
/*    */ public class AutoSoup extends Module {
/* 17 */   private final TimerUtil timer = new TimerUtil();
/* 18 */   public final Numbers<Double> MaxDELAY = new Numbers("MaxDelay", Double.valueOf(350.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(10.0D));
/* 19 */   public final Numbers<Double> MinDELAY = new Numbers("MinDelay", Double.valueOf(350.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(10.0D));
/* 20 */   public final Numbers<Double> HEALTH = new Numbers("Health", Double.valueOf(3.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/* 21 */   public final Option<Boolean> DROP = new Option("Drop", Boolean.valueOf(true));
/* 22 */   public final Option<Boolean> safeSwap = new Option("SafeSwap", Boolean.valueOf(false));
/*    */   
/*    */   public AutoSoup() {
/* 25 */     super("AutoSoup", ModuleType.Combat);
/* 26 */     addSettings(new Value[] { (Value)this.MaxDELAY, (Value)this.MinDELAY, (Value)this.HEALTH, (Value)this.DROP, (Value)this.safeSwap });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onEvent(EventPreUpdate event) {
/* 31 */     int soupSlot = getSoupFromInventory();
/* 32 */     if (soupSlot != -1 && mc.thePlayer.getHealth() < ((Double)this.HEALTH.getValue()).floatValue() && this.timer
/* 33 */       .hasReached(MathUtil.randomNumber(((Double)this.MaxDELAY.getValue()).doubleValue(), ((Double)this.MinDELAY.getValue()).doubleValue()))) {
/* 34 */       if (!((Boolean)this.safeSwap.get()).booleanValue() || !isMoving()) {
/* 35 */         swap(getSoupFromInventory());
/*    */       }
/* 37 */       sendPacket((Packet)new C09PacketHeldItemChange(6));
/* 38 */       sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/* 39 */       sendPacket((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void swap(int slot) {
/* 44 */     mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 6, 2, (EntityPlayer)mc.thePlayer);
/*    */   }
/*    */   
/*    */   public int getSoupFromInventory() {
/* 48 */     int soup = -1;
/* 49 */     for (int i = 1; i < 45; i++) {
/* 50 */       if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
/* 51 */         ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/* 52 */         Item item = is.getItem();
/* 53 */         if (Item.getIdFromItem(item) == 282) {
/* 54 */           soup = i;
/*    */         }
/*    */       } 
/*    */     } 
/* 58 */     return soup;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\auto\AutoSoup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */