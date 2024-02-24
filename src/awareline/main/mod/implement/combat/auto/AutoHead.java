/*    */ package awareline.main.mod.implement.combat.auto;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.timer.TimerUtil;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*    */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*    */ import net.minecraft.potion.Potion;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class AutoHead extends Module {
/* 17 */   private final Numbers<Double> health = new Numbers("Health", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(40.0D), Double.valueOf(0.5D));
/* 18 */   private final Numbers<Double> delay = new Numbers("Delay", Double.valueOf(200.0D), Double.valueOf(0.0D), Double.valueOf(8000.0D), Double.valueOf(1.0D));
/* 19 */   private final TimerUtil timerUtil = new TimerUtil();
/*    */   
/*    */   public AutoHead() {
/* 22 */     super("AutoHead", ModuleType.Combat);
/* 23 */     addSettings(new Value[] { (Value)this.health, (Value)this.delay });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 28 */     this.timerUtil.reset();
/* 29 */     super.onEnable();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick e) {
/* 34 */     if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && 
/* 35 */       this.timerUtil.hasReached(((Double)this.delay.getValue()).doubleValue())) {
/* 36 */       if (mc.thePlayer.capabilities.isCreativeMode || mc.thePlayer.isPotionActive(Potion.regeneration) || mc.thePlayer.getHealth() >= ((Double)this.health.getValue()).doubleValue()) {
/* 37 */         this.timerUtil.reset();
/*    */         return;
/*    */       } 
/* 40 */       int slot = getItemFromHotBar();
/* 41 */       if (slot == -1)
/* 42 */         return;  int oldSlot = mc.thePlayer.inventory.currentItem;
/* 43 */       if (!bad()) {
/* 44 */         sendPacket((Packet)new C09PacketHeldItemChange(slot));
/* 45 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
/* 46 */         sendPacket((Packet)new C09PacketHeldItemChange(oldSlot));
/* 47 */         this.timerUtil.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private int getItemFromHotBar() {
/* 54 */     for (int i = 0; i < 9; i++) {
/* 55 */       if (mc.thePlayer.inventory.mainInventory[i] != null) {
/* 56 */         ItemStack is = mc.thePlayer.inventory.mainInventory[i];
/* 57 */         Item item = is.getItem();
/* 58 */         String displayName = is.getDisplayName();
/* 59 */         if (Item.getIdFromItem(item) == 397 && !displayName.contains("Backpack") && !displayName.contains("锟斤拷锟斤拷")) {
/* 60 */           return i;
/*    */         }
/*    */       } 
/*    */     } 
/* 64 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\auto\AutoHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */