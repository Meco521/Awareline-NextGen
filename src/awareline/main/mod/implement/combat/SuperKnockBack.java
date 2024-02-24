/*    */ package awareline.main.mod.implement.combat;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.timer.TimerUtil;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ 
/*    */ public class SuperKnockBack extends Module {
/* 20 */   private final Numbers<Double> maxdelay = new Numbers("MaxDelay", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(2000.0D), Double.valueOf(0.1D));
/* 21 */   private final Numbers<Double> mindelay = new Numbers("MinDelay", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(2000.0D), Double.valueOf(0.1D));
/* 22 */   private final Option<Boolean> usepacket = new Option("UsePacket[Exploit]", Boolean.valueOf(false));
/* 23 */   private final Numbers<Double> packets = new Numbers("Packets", Double.valueOf(100.0D), Double.valueOf(50.0D), Double.valueOf(2000.0D), Double.valueOf(50.0D), this.usepacket::get);
/* 24 */   private final Numbers<Double> ptd = new Numbers("PacketTriggerDistance", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(6.0D), Double.valueOf(0.1D), this.usepacket::get);
/* 25 */   private final Numbers<Double> hurtTime = new Numbers("HurtTime", 
/* 26 */       Double.valueOf(17.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/*    */   private EntityLivingBase target;
/*    */   private final TimerUtil delay;
/*    */   
/* 30 */   public SuperKnockBack() { super("SuperKnockBack", new String[] { "superkb" }, ModuleType.Combat);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     this.delay = new TimerUtil();
/*    */     addSettings(new Value[] { (Value)this.maxdelay, (Value)this.mindelay, (Value)this.hurtTime, (Value)this.usepacket, (Value)this.packets, (Value)this.ptd }); }
/*    */   @EventHandler
/*    */   private void onUpdate(EventPreUpdate e) { getTarget(); } @EventHandler
/* 51 */   private void onPacket(EventPacketSend e) { if (e.getPacket() instanceof net.minecraft.network.play.client.C0APacketAnimation && this.target != null && this.target.hurtResistantTime <= ((Double)this.hurtTime.get()).intValue() && this.delay.hasReached(getDelay())) {
/* 52 */       if (((Boolean)this.usepacket.get()).booleanValue() && (
/* 53 */         mc.thePlayer.getDistanceToEntity((Entity)this.target) <= ((Double)this.ptd.get()).doubleValue() || mc.thePlayer.getEntityBoundingBox().intersectsWith(this.target.getEntityBoundingBox()))) {
/* 54 */         int i = 0;
/* 55 */         while (i < ((Double)this.packets.get()).doubleValue()) {
/* 56 */           if (mc.thePlayer.onGround) {
/* 57 */             sendPacketNoEvent((Packet)new C03PacketPlayer());
/*    */           }
/* 59 */           i++;
/*    */         } 
/*    */       } 
/*    */       
/* 63 */       if (mc.thePlayer.isSprinting() && mc.gameSettings.keyBindForward.isKeyDown()) {
/* 64 */         mc.thePlayer.setSprinting(false);
/*    */       }
/* 66 */       sendPacketNoEvent((Packet)new C0BPacketEntityAction((Entity)mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
/* 67 */       mc.thePlayer.setSprinting(true);
/* 68 */       mc.thePlayer.serverSprintState = true;
/*    */     }  }
/*    */   private double getRandomDoubleInRange(double minDouble, double maxDouble) { return (minDouble >= maxDouble) ? minDouble : ((new Random()).nextDouble() * (maxDouble - minDouble) + minDouble); } public double getDelay() {
/*    */     return Math.abs(getRandomDoubleInRange(((Double)this.mindelay.get()).doubleValue(), ((Double)this.maxdelay.get()).doubleValue()));
/*    */   } private void getTarget() {
/* 73 */     if (KillAura.getInstance.getTarget() != null) {
/* 74 */       this.target = KillAura.getInstance.getTarget();
/*    */       return;
/*    */     } 
/* 77 */     MovingObjectPosition mouseOver = mc.objectMouseOver;
/* 78 */     if (mouseOver != null)
/* 79 */       if (mouseOver.entityHit instanceof EntityLivingBase) {
/* 80 */         this.target = (EntityLivingBase)mouseOver.entityHit;
/*    */       } else {
/* 82 */         this.target = null;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\SuperKnockBack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */