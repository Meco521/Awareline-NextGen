/*    */ package awareline.main.mod.implement.globals;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.misc.EventChat;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.utility.translate.StringUtils;
/*    */ import net.minecraft.event.ClickEvent;
/*    */ import net.minecraft.event.HoverEvent;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatStyle;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class ChatTranslator extends Module {
/*    */   public ChatTranslator() {
/* 18 */     super("ChatTranslator", ModuleType.Globals);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onChat(EventChat event) {
/* 23 */     event.getChatComponent().appendSibling((new ChatComponentText(EnumChatFormatting.GRAY + " [T]"))
/* 24 */         .setChatStyle((new ChatStyle())
/* 25 */           .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, (new StringBuilder())
/* 26 */               .insert(0, "/translate ")
/* 27 */               .append(EnumChatFormatting.getTextWithoutFormattingCodes(event
/* 28 */                   .getMessage()))
/* 29 */               .toString()))
/* 30 */           .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText("Click on this to translate this message.")))));
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onSendMessage(EventChat event) {
/* 36 */     if (!event.getMessage().isEmpty() && event.getMessage().charAt(0) == '/')
/*    */       return; 
/* 38 */     if (!event.getMessage().isEmpty() && event.getMessage().charAt(0) == 'T') {
/* 39 */       event.setCancelled(true);
/* 40 */       Thread translate = new Thread(() -> {
/*    */             C01PacketChatMessage chat = new C01PacketChatMessage(StringUtils.translate(event.getMessage().substring(1)));
/*    */             if (mc.thePlayer != null) {
/*    */               mc.thePlayer.sendQueue.getNetworkManager().sendPacket((Packet)chat);
/*    */             }
/*    */           });
/* 46 */       translate.setDaemon(true);
/* 47 */       translate.start();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\ChatTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */