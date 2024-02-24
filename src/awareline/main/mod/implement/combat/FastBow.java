/*    */ package awareline.main.mod.implement.combat;
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
/*    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class FastBow extends Module {
/* 18 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Vanilla", "Tick", "NCP" }, "Vanilla");
/* 19 */   private final Option<Boolean> onlyGround = new Option("OnlyGround", Boolean.valueOf(false));
/* 20 */   private final Option<Boolean> timerBypass = new Option("TimerBypass", Boolean.valueOf(false));
/*    */   
/*    */   public FastBow() {
/* 23 */     super("FastBow", ModuleType.Combat);
/* 24 */     addSettings(new Value[] { (Value)this.mode, (Value)this.onlyGround, (Value)this.timerBypass });
/*    */   }
/*    */   
/*    */   private boolean isUsingBow() {
/* 28 */     if (mc.thePlayer.getItemInUse() == null) {
/* 29 */       return false;
/*    */     }
/* 31 */     Item usingItem = mc.thePlayer.getItemInUse().getItem();
/* 32 */     return (mc.thePlayer.isUsingItem() && usingItem instanceof net.minecraft.item.ItemBow);
/*    */   }
/*    */   
/*    */   private void sendC03() {
/* 36 */     sendPacket((Packet)new C03PacketPlayer(mc.thePlayer.onGround));
/*    */   }
/*    */   
/*    */   private void sendC03(int needWhile) {
/* 40 */     for (int i = 0; i < needWhile; i++) {
/* 41 */       sendPacket((Packet)new C03PacketPlayer(mc.thePlayer.onGround));
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate event) {
/* 47 */     setSuffix((Serializable)this.mode.get());
/* 48 */     if (((Boolean)this.onlyGround.get()).booleanValue() && !mc.thePlayer.onGround) {
/*    */       return;
/*    */     }
/* 51 */     if (((Boolean)this.timerBypass.get()).booleanValue() && isUsingBow()) {
/* 52 */       sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.5D, mc.thePlayer.posZ, false));
/*    */     }
/* 54 */     if (this.mode.is("Vanilla")) {
/* 55 */       if (isUsingBow()) {
/* 56 */         sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
/* 57 */         sendC03(20);
/* 58 */         sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/*    */       } 
/* 60 */     } else if (this.mode.is("Tick")) {
/* 61 */       if (isUsingBow()) {
/* 62 */         sendC03(20);
/* 63 */         mc.playerController.onStoppedUsingItem((EntityPlayer)mc.thePlayer);
/*    */       } 
/* 65 */     } else if (this.mode.is("NCP") && 
/* 66 */       mc.thePlayer.getItemInUseDuration() >= 15 && 
/* 67 */       mc.thePlayer.onGround && isUsingBow()) {
/* 68 */       sendC03(8);
/* 69 */       mc.playerController.onStoppedUsingItem((EntityPlayer)mc.thePlayer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\FastBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */