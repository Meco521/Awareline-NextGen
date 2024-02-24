/*    */ package awareline.main.mod.implement.combat;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.math.RotationUtil;
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*    */ 
/*    */ public class AntiFireball extends Module {
/* 15 */   private final Option<Boolean> swing = new Option("Swing", Boolean.valueOf(true));
/* 16 */   private final Option<Boolean> rot = new Option("Rotations", Boolean.valueOf(true));
/* 17 */   private final Numbers<Double> range = new Numbers("Range", Double.valueOf(4.2D), Double.valueOf(1.0D), Double.valueOf(6.0D), Double.valueOf(0.1D));
/*    */   boolean canSee;
/*    */   
/*    */   public AntiFireball() {
/* 21 */     super("AntiFireball", new String[] { "afb" }, ModuleType.Combat);
/* 22 */     addSettings(new Value[] { (Value)this.rot, (Value)this.swing, (Value)this.range });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 27 */     this.canSee = false;
/* 28 */     super.onDisable();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 33 */     setSuffix((Serializable)this.range.getValue());
/* 34 */     for (Entity entity : mc.theWorld.loadedEntityList) {
/* 35 */       if (entity instanceof net.minecraft.entity.projectile.EntityFireball) {
/* 36 */         double distance = mc.thePlayer.getDistanceToEntity(entity);
/* 37 */         if (distance <= ((Double)this.range.getValue()).doubleValue()) {
/* 38 */           if (((Boolean)this.swing.getValue()).booleanValue())
/* 39 */             mc.thePlayer.swingItem(); 
/* 40 */           sendPacket((Packet)new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
/* 41 */           if (((Boolean)this.rot.getValue()).booleanValue()) {
/* 42 */             float[] rotation = RotationUtil.getRotations(entity);
/* 43 */             e.setYaw(rotation[0]);
/* 44 */             e.setPitch(rotation[1]);
/*    */           } 
/*    */         }  continue;
/*    */       } 
/* 48 */       this.canSee = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\AntiFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */