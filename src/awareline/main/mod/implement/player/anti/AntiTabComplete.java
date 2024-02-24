/*    */ package awareline.main.mod.implement.player.anti;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.packetEvents.EventPacketReceive;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import java.util.Random;
/*    */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*    */ 
/*    */ public class AntiTabComplete
/*    */   extends Module {
/*    */   public AntiTabComplete() {
/* 13 */     super("AntiTabComplete", ModuleType.Player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPacket(EventPacketReceive event) {
/* 18 */     if (event.getPacket() instanceof C14PacketTabComplete) {
/* 19 */       C14PacketTabComplete packet = (C14PacketTabComplete)event.getPacket();
/*    */       
/* 21 */       if (!packet.getMessage().isEmpty() && packet.getMessage().charAt(0) == '.') {
/* 22 */         String[] arguments = packet.getMessage().split(" ");
/* 23 */         String[] messages = { "hey what's up ", "dude ", "hey ", "hi ", "man ", "yo ", "howdy ", "omg " };
/* 24 */         Random random = new Random();
/*    */         
/* 26 */         packet.setMessage(messages[random.nextInt(messages.length)] + arguments[arguments.length - 1]);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\anti\AntiTabComplete.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */