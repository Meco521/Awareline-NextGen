/*    */ package awareline.main.mod.implement.player;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class Blink extends Module {
/* 17 */   private final List<Packet> packetsList = new ArrayList<>();
/*    */   private EntityOtherPlayerMP blinkEntity;
/*    */   
/*    */   public Blink() {
/* 21 */     super("Blink", ModuleType.Player);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 26 */     this; if (mc.thePlayer == null) {
/*    */       return;
/*    */     }
/* 29 */     this; this.blinkEntity = new EntityOtherPlayerMP((World)mc.theWorld, new GameProfile(new UUID(69L, 96L), "Blink"));
/* 30 */     this; this.blinkEntity.inventory = mc.thePlayer.inventory;
/* 31 */     this; this.blinkEntity.inventoryContainer = mc.thePlayer.inventoryContainer;
/* 32 */     this; this; this; this; this; this.blinkEntity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
/* 33 */     this; this.blinkEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
/* 34 */     this.blinkEntity.setHealth(mc.thePlayer.getHealth());
/* 35 */     mc.theWorld.addEntityToWorld(this.blinkEntity.getEntityId(), (Entity)this.blinkEntity);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPacketSend(EventPacketSend event) {
/* 40 */     if (event.getPacket() instanceof net.minecraft.network.play.client.C0BPacketEntityAction || event
/* 41 */       .getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer || event.getPacket() instanceof net.minecraft.network.play.client.C02PacketUseEntity || event
/* 42 */       .getPacket() instanceof net.minecraft.network.play.client.C0APacketAnimation || event.getPacket() instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement) {
/* 43 */       this.packetsList.add(event.getPacket());
/* 44 */       event.setCancelled(true);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 50 */     if (mc.theWorld == null) {
/*    */       return;
/*    */     }
/* 53 */     for (Packet packet : this.packetsList) {
/* 54 */       sendPacketNoEvent(packet);
/*    */     }
/* 56 */     this.packetsList.clear();
/* 57 */     mc.theWorld.removeEntityFromWorld(this.blinkEntity.getEntityId());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\Blink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */