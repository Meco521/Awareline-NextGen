/*    */ package awareline.main.mod.implement.move;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.event.events.world.moveEvents.EventMove;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.utility.MoveUtils;
/*    */ import awareline.main.utility.TPUtil;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class TeleportMod
/*    */   extends Module {
/*    */   public TeleportMod() {
/* 18 */     super("TeleportMod", new String[] { "teleport" }, ModuleType.Movement);
/* 19 */     setHide(true);
/* 20 */     getInstance = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public static TeleportMod getInstance;
/*    */   public static float x;
/*    */   public static float y;
/*    */   public static float z;
/*    */   public static boolean isTPPlayer;
/*    */   public static String playername;
/*    */   private Vec3 target;
/*    */   boolean tp;
/*    */   
/*    */   public void onEnable() {
/* 34 */     if (mc.thePlayer == null)
/*    */       return; 
/* 36 */     this.tp = true;
/* 37 */     mc.thePlayer.stepHeight = 0.0F;
/* 38 */     mc.thePlayer.motionX = 0.0D;
/* 39 */     mc.thePlayer.motionZ = 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 44 */     mc.timer.timerSpeed = 1.0F;
/* 45 */     mc.thePlayer.stepHeight = 0.625F;
/* 46 */     mc.thePlayer.motionX = 0.0D;
/* 47 */     mc.thePlayer.motionZ = 0.0D;
/* 48 */     isTPPlayer = false;
/* 49 */     playername = null;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick event) {
/* 54 */     if (isTPPlayer) {
/* 55 */       if (mc.theWorld.getPlayerEntityByName(playername) == null) {
/* 56 */         setEnabled(false);
/*    */         return;
/*    */       } 
/* 59 */       x = (float)(mc.theWorld.getPlayerEntityByName(playername)).posX;
/* 60 */       y = (float)(mc.theWorld.getPlayerEntityByName(playername)).posY + 3.0F;
/* 61 */       z = (float)(mc.theWorld.getPlayerEntityByName(playername)).posZ;
/*    */     } 
/* 63 */     if (this.tp) {
/* 64 */       double lastY = mc.thePlayer.posY, downY = 0.0D;
/* 65 */       for (Vec3 vec3 : TPUtil.computePath(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), new Vec3(x, y, z), 1.0D, true)) {
/*    */ 
/*    */         
/* 68 */         if (vec3.getY() < lastY) {
/* 69 */           downY += lastY - vec3.getY();
/*    */         }
/* 71 */         if (downY > 2.5D) {
/* 72 */           downY = 0.0D;
/* 73 */           mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vec3.getX(), vec3
/* 74 */                 .getY(), vec3.getZ(), true));
/*    */         } else {
/* 76 */           mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vec3.getX(), vec3
/* 77 */                 .getY(), vec3.getZ(), false));
/*    */         } 
/* 79 */         lastY = vec3.getY();
/*    */       } 
/*    */       
/* 82 */       mc.thePlayer.setPosition(x, y, z);
/* 83 */       setEnabled(false);
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onMove(EventMove event) {
/* 89 */     MoveUtils.INSTANCE.setSpeed(0.0D);
/* 90 */     mc.thePlayer.motionY = 0.0D;
/* 91 */     event.y = 0.0D;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public final void onSendPacket(EventPacketSend event) {
/* 96 */     if (!this.tp && event.getPacket() instanceof C03PacketPlayer)
/* 97 */       event.setCancelled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\move\TeleportMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */