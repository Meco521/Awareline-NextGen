/*    */ package awareline.main.mod.implement.globals;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.misc.EventChat;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.event.ClickEvent;
/*    */ import net.minecraft.event.HoverEvent;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatStyle;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class ChatCopy
/*    */   extends Module {
/*    */   public ChatCopy() {
/* 17 */     super("ChatCopy", ModuleType.Globals);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onChatFrom(EventChat e) {
/* 22 */     IChatComponent ChatComponent = e.getChatComponent();
/*    */     
/* 24 */     e.getChatComponent().appendSibling((new ChatComponentText(EnumChatFormatting.GRAY + " [C]"))
/* 25 */         .setChatStyle((new ChatStyle())
/* 26 */           .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, (new StringBuilder())
/* 27 */               .insert(0, "/messagecopy ")
/* 28 */               .append(EnumChatFormatting.getTextWithoutFormattingCodes(e
/* 29 */                   .getMessage()))
/* 30 */               .toString()))
/* 31 */           .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)new ChatComponentText("Click on this to copy this message.")))));
/*    */     
/* 33 */     e.setChatComponent(ChatComponent);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\ChatCopy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */