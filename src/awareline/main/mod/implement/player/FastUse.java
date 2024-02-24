/*    */ package awareline.main.mod.implement.player;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ 
/*    */ public class FastUse extends Module {
/* 16 */   private final Option<Boolean> noMove = new Option("NoMove", Boolean.valueOf(false));
/* 17 */   private final Option<Boolean> onlyGround = new Option("OnlyGround", Boolean.valueOf(false));
/* 18 */   public final String[] modes = new String[] { "Vanilla", "NCP", "Matrix", "Watchdog" };
/* 19 */   private final Mode<String> mode = new Mode("Mode", this.modes, this.modes[0]);
/*    */   
/*    */   public FastUse() {
/* 22 */     super("FastUse", ModuleType.Player);
/* 23 */     addSettings(new Value[] { (Value)this.mode, (Value)this.onlyGround, (Value)this.noMove });
/*    */   }
/*    */   
/*    */   private boolean isUsingFood() {
/* 27 */     if (mc.thePlayer.getItemInUse() == null) {
/* 28 */       return false;
/*    */     }
/* 30 */     Item usingItem = mc.thePlayer.getItemInUse().getItem();
/* 31 */     return (mc.thePlayer.isUsingItem() && (usingItem instanceof net.minecraft.item.ItemFood || usingItem instanceof net.minecraft.item.ItemBucketMilk || usingItem instanceof net.minecraft.item.ItemPotion));
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onUpdate(EventPreUpdate e) {
/* 36 */     setSuffix((Serializable)this.mode.get());
/* 37 */     if (mc.thePlayer == null || mc.theWorld == null) {
/*    */       return;
/*    */     }
/* 40 */     if (((Boolean)this.onlyGround.get()).booleanValue() && !mc.thePlayer.onGround) {
/*    */       return;
/*    */     }
/* 43 */     if (((Boolean)this.noMove.get()).booleanValue() && isMoving()) {
/*    */       return;
/*    */     }
/* 46 */     if (this.mode.is("Watchdog")) {
/* 47 */       if (isUsingFood()) {
/* 48 */         mc.timer.timerSpeed = isMoving() ? 1.0F : 1.2F;
/*    */       } else {
/* 50 */         mc.timer.timerSpeed = 1.0F;
/*    */       } 
/* 52 */     } else if (this.mode.is("Matrix")) {
/* 53 */       mc.timer.timerSpeed = isUsingFood() ? 0.5F : 1.0F;
/* 54 */       if (isUsingFood()) {
/* 55 */         send();
/*    */       }
/* 57 */     } else if (this.mode.is("Vanilla")) {
/* 58 */       if (isUsingFood()) {
/* 59 */         send(35);
/* 60 */         mc.playerController.onStoppedUsingItem((EntityPlayer)mc.thePlayer);
/*    */       } 
/* 62 */     } else if (this.mode.is("NCP") && 
/* 63 */       isUsingFood() && mc.thePlayer.getItemInUseDuration() == 15) {
/* 64 */       send(20);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void send() {
/* 70 */     sendPacketNoEvent((Packet)new C03PacketPlayer(mc.thePlayer.onGround));
/*    */   }
/*    */   
/*    */   public void send(int needWhile) {
/* 74 */     for (int i = 0; i < needWhile; i++)
/* 75 */       sendPacketNoEvent((Packet)new C03PacketPlayer(mc.thePlayer.onGround)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\FastUse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */