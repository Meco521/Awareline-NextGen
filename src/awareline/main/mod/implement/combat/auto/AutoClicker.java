/*    */ package awareline.main.mod.implement.combat.auto;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.random.RandomUtils;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class AutoClicker extends Module {
/* 15 */   public final Numbers<Double> mincps = new Numbers("MinCPS", Double.valueOf(8.0D), Double.valueOf(2.0D), Double.valueOf(20.0D), Double.valueOf(0.5D));
/* 16 */   public final Numbers<Double> maxcps = new Numbers("MaxCPS", Double.valueOf(8.0D), Double.valueOf(2.0D), Double.valueOf(20.0D), Double.valueOf(0.5D));
/* 17 */   public final Option<Boolean> autoblock = new Option("AutoBlock", Boolean.valueOf(true));
/* 18 */   private final TimeHelper timer = new TimeHelper();
/* 19 */   private final TimeHelper blocktimer = new TimeHelper();
/*    */   private int delay;
/*    */   
/*    */   public AutoClicker() {
/* 23 */     super("AutoClicker", new String[] { "ac" }, ModuleType.Combat);
/* 24 */     addSettings(new Value[] { (Value)this.maxcps, (Value)this.mincps, (Value)this.autoblock });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 30 */     setDelay();
/* 31 */     super.onEnable();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate event) {
/* 36 */     if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
/*    */       return;
/*    */     }
/* 39 */     if (mc.playerController.getCurBlockDamageMP() != 0.0F) {
/*    */       return;
/*    */     }
/* 42 */     if (this.timer.delay(this.delay) && mc.gameSettings.keyBindAttack.pressed) {
/* 43 */       mc.gameSettings.keyBindAttack.pressed = false;
/*    */       
/* 45 */       if (((Boolean)this.autoblock.getValue()).booleanValue() && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit.isEntityAlive() && 
/* 46 */         mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemSword && this.blocktimer.delay(100L)) {
/* 47 */         mc.thePlayer.getCurrentEquippedItem().useItemRightClick((World)mc.theWorld, (EntityPlayer)mc.thePlayer);
/* 48 */         this.blocktimer.reset();
/*    */       } 
/*    */       
/* 51 */       mc.setLeftClickCounter(0);
/* 52 */       mc.clickMouse();
/* 53 */       mc.gameSettings.keyBindAttack.pressed = true;
/* 54 */       setDelay();
/* 55 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick event) {
/* 62 */     if (((Double)this.mincps.getValue()).doubleValue() > ((Double)this.maxcps.getValue()).doubleValue()) {
/* 63 */       this.mincps.setValue(this.maxcps.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   private void setDelay() {
/* 68 */     this.delay = (int)RandomUtils.nextFloat(1000.0F / ((Double)this.mincps.getValue()).floatValue(), 1000.0F / ((Double)this.maxcps.getValue()).floatValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\auto\AutoClicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */