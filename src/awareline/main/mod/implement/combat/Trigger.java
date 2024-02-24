/*    */ package awareline.main.mod.implement.combat;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.math.MathUtil;
/*    */ import awareline.main.utility.timer.TimerUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class Trigger
/*    */   extends Module
/*    */ {
/* 20 */   public final TimerUtil time = new TimerUtil();
/* 21 */   private final Option<Boolean> bRaycast = new Option("ThroughWalls", Boolean.valueOf(false));
/* 22 */   private final Numbers<Double> maxCPS = new Numbers("MaxCPS", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/* 23 */   private final Numbers<Double> minCPS = new Numbers("MinCPS", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
/* 24 */   private final Option<Boolean> players = new Option("Players", Boolean.valueOf(true));
/* 25 */   private final Option<Boolean> mobs = new Option("Mobs", Boolean.valueOf(true));
/* 26 */   private final Option<Boolean> animals = new Option("Animals", Boolean.valueOf(false));
/*    */   
/*    */   public Trigger() {
/* 29 */     super("Trigger", ModuleType.Combat);
/* 30 */     addSettings(new Value[] { (Value)this.bRaycast, (Value)this.players, (Value)this.mobs, (Value)this.animals, (Value)this.maxCPS, (Value)this.minCPS });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 35 */     setSuffix(this.maxCPS.get() + " - " + this.minCPS.get());
/* 36 */     if (KillAura.getInstance.isEnabled() && KillAura.getInstance.target != null) {
/*    */       return;
/*    */     }
/*    */     Entity onPoint;
/* 40 */     if (mc.objectMouseOver != null && (onPoint = mc.objectMouseOver.entityHit) != null) {
/* 41 */       boolean ray = false;
/* 42 */       if (((Boolean)this.bRaycast.get()).booleanValue() && !findRaycast(onPoint).isEmpty()) {
/* 43 */         onPoint = findRaycast(onPoint).get(0);
/* 44 */         ray = true;
/*    */       } 
/*    */       
/* 47 */       if (entitycheck(onPoint) && 
/* 48 */         !ray && this.time.hasReached(CPStoDelay())) {
/* 49 */         hitEntity();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean entitycheck(Entity e) {
/* 56 */     if (!e.isEntityAlive()) {
/* 57 */       return false;
/*    */     }
/* 59 */     if (((Boolean)this.players.getValue()).booleanValue() && e instanceof net.minecraft.entity.player.EntityPlayer) {
/* 60 */       return true;
/*    */     }
/* 62 */     if (((Boolean)this.mobs.getValue()).booleanValue() && e instanceof net.minecraft.entity.monster.EntityMob) {
/* 63 */       return true;
/*    */     }
/* 65 */     return (((Boolean)this.animals.getValue()).booleanValue() && e instanceof net.minecraft.entity.passive.EntityAnimal);
/*    */   }
/*    */   
/*    */   private void hitEntity() {
/* 69 */     mc.clickMouse();
/* 70 */     this.time.reset();
/*    */   }
/*    */   
/*    */   private List findRaycast(Entity e) {
/* 74 */     ArrayList<Entity> arrayList = new ArrayList<>();
/* 75 */     for (Entity rs : mc.theWorld.loadedEntityList) {
/* 76 */       if (rs.getDistanceToEntity(e) > 0.5D || !rs.isInvisible())
/* 77 */         continue;  arrayList.add(rs);
/*    */     } 
/* 79 */     return arrayList;
/*    */   }
/*    */   
/*    */   private long CPStoDelay() {
/* 83 */     return (long)(MathUtil.randomNumber(((Double)this.maxCPS.getValue()).doubleValue(), ((Double)this.minCPS.getValue()).doubleValue()) / MathUtil.randomNumber(((Double)this.maxCPS.getValue()).doubleValue(), ((Double)this.minCPS.getValue()).doubleValue()) * 40.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\Trigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */