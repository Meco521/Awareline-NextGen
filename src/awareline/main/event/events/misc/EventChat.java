/*    */ package awareline.main.event.events.misc;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.ChatLine;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class EventChat extends Event {
/*    */   private String message;
/*    */   private final List<ChatLine> chatLines;
/*    */   private IChatComponent component;
/*    */   
/* 12 */   public String getMessage() { return this.message; }
/* 13 */   public List<ChatLine> getChatLines() { return this.chatLines; } public IChatComponent getComponent() {
/* 14 */     return this.component;
/*    */   }
/*    */   public EventChat(String message, IChatComponent minecomponent, List<ChatLine> minechatline) {
/* 17 */     this.message = message;
/* 18 */     this.chatLines = minechatline;
/* 19 */     this.component = minecomponent;
/* 20 */     setType((byte)0);
/*    */   }
/*    */   
/*    */   public void setComponent(IChatComponent p_setComponent_1_) {
/* 24 */     this.component = p_setComponent_1_;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 28 */     this.message = message;
/*    */   }
/*    */   
/*    */   public IChatComponent getChatComponent() {
/* 32 */     return this.component;
/*    */   }
/*    */   
/*    */   public void setChatComponent(IChatComponent ChatComponent) {
/* 36 */     this.component = ChatComponent;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\misc\EventChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */