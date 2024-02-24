/*    */ package awareline.main.mod.implement.combat;
/*    */ import awareline.main.Client;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.combat.advanced.sucks.utils.RotationUtils;
/*    */ import awareline.main.mod.implement.misc.Teams;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.WorldUtil;
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collector;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ 
/*    */ public class AimBow extends Module {
/* 25 */   private final Option<Boolean> lockView = new Option("LockView", Boolean.valueOf(false));
/* 26 */   public final Numbers<Double> fov = new Numbers("Fov", Double.valueOf(180.0D), Double.valueOf(10.0D), Double.valueOf(360.0D), Double.valueOf(10.0D));
/* 27 */   private final Mode<String> priority = new Mode("Priority", new String[] { "Range", "Angle" }, "Distance");
/*    */   public EntityLivingBase target;
/*    */   
/*    */   public AimBow() {
/* 31 */     super("AimBow", ModuleType.Combat);
/* 32 */     addSettings(new Value[] { (Value)this.priority, (Value)this.lockView, (Value)this.fov });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPre(EventPreUpdate event) {
/* 37 */     setSuffix((Serializable)this.priority.get());
/*    */     
/* 39 */     if (mc.thePlayer.inventory.getCurrentItem().getItem() != Items.bow || !mc.thePlayer.isUsingItem()) {
/* 40 */       this.target = null;
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     this.target = getTarget();
/* 45 */     if (this.target == null) {
/*    */       return;
/*    */     }
/*    */     
/* 49 */     float[] rotation = getPlayerRotations((Entity)this.target);
/* 50 */     event.setYaw(rotation[0]);
/* 51 */     event.setPitch(rotation[1]);
/* 52 */     if (((Boolean)this.lockView.get()).booleanValue()) {
/* 53 */       mc.thePlayer.rotationYaw = rotation[0];
/* 54 */       mc.thePlayer.rotationPitch = rotation[1];
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 60 */     this.target = null;
/* 61 */     super.onDisable();
/*    */   }
/*    */   
/*    */   private float[] getPlayerRotations(Entity entity) {
/* 65 */     double distanceToEnt = mc.thePlayer.getDistanceToEntity(entity);
/* 66 */     double predictX = entity.posX + (entity.posX - entity.lastTickPosX) * distanceToEnt * 0.8D;
/* 67 */     double predictZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) * distanceToEnt * 0.8D;
/*    */     
/* 69 */     double x = predictX - mc.thePlayer.posX;
/* 70 */     double z = predictZ - mc.thePlayer.posZ;
/* 71 */     double h = entity.posY + 1.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*    */     
/* 73 */     double h1 = Math.sqrt(x * x + z * z);
/* 74 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/*    */     
/* 76 */     float pitch = -RotationUtils.getTrajAngleSolutionLow((float)h1, (float)h, 1.0F);
/*    */     
/* 78 */     return new float[] { yaw, pitch };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private EntityLivingBase getTarget() {
/* 87 */     Stream<EntityPlayer> stream = WorldUtil.getLivingPlayers().stream().filter(e -> (!Teams.getInstance.isOnSameTeam((Entity)e) || !Client.instance.friendManager.isFriend(e.getName()))).filter(e -> (!AntiBot.getInstance.isEnabled() || !AntiBot.isBot((EntityLivingBase)e))).filter(mc.thePlayer::canEntityBeSeen).filter(e -> RotationUtils.isVisibleFOV((Entity)e, ((Double)this.fov.getValue()).intValue()));
/*    */     
/* 89 */     if (this.priority.is("Range")) {
/* 90 */       stream = stream.sorted(Comparator.comparingDouble(e -> e.getDistanceToEntity((Entity)mc.thePlayer)));
/* 91 */     } else if (this.priority.is("Angle")) {
/* 92 */       stream = stream.sorted(Comparator.comparingDouble(RotationUtils::getYawToEntity));
/*    */     } 
/* 94 */     List<EntityPlayer> list = stream.collect((Collector)Collectors.toList());
/* 95 */     if (list.size() <= 0) {
/* 96 */       return null;
/*    */     }
/* 98 */     return (EntityLivingBase)list.get(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\AimBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */