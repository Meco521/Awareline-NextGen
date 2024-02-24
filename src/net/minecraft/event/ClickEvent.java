/*     */ package net.minecraft.event;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClickEvent
/*     */ {
/*     */   private final Action action;
/*     */   private final String value;
/*     */   
/*     */   public ClickEvent(Action theAction, String theValue) {
/*  14 */     this.action = theAction;
/*  15 */     this.value = theValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() {
/*  23 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  32 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  37 */     if (this == p_equals_1_)
/*     */     {
/*  39 */       return true;
/*     */     }
/*  41 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/*  43 */       ClickEvent clickevent = (ClickEvent)p_equals_1_;
/*     */       
/*  45 */       if (this.action != clickevent.action)
/*     */       {
/*  47 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  51 */       if (this.value != null) {
/*     */         
/*  53 */         if (!this.value.equals(clickevent.value))
/*     */         {
/*  55 */           return false;
/*     */         }
/*     */       }
/*  58 */       else if (clickevent.value != null) {
/*     */         
/*  60 */         return false;
/*     */       } 
/*     */       
/*  63 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  74 */     return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  79 */     int i = this.action.hashCode();
/*  80 */     i = 31 * i + ((this.value != null) ? this.value.hashCode() : 0);
/*  81 */     return i;
/*     */   }
/*     */   
/*     */   public enum Action
/*     */   {
/*  86 */     OPEN_URL("open_url", true),
/*  87 */     OPEN_FILE("open_file", false),
/*  88 */     RUN_COMMAND("run_command", true),
/*     */     
/*  90 */     SUGGEST_COMMAND("suggest_command", true),
/*  91 */     CHANGE_PAGE("change_page", true);
/*     */     
/*  93 */     private static final Map<String, Action> nameMapping = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean allowedInChat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String canonicalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 119 */       for (Action clickevent$action : values())
/*     */       {
/* 121 */         nameMapping.put(clickevent$action.canonicalName, clickevent$action);
/*     */       }
/*     */     }
/*     */     
/*     */     Action(String canonicalNameIn, boolean allowedInChatIn) {
/*     */       this.canonicalName = canonicalNameIn;
/*     */       this.allowedInChat = allowedInChatIn;
/*     */     }
/*     */     
/*     */     public boolean shouldAllowInChat() {
/*     */       return this.allowedInChat;
/*     */     }
/*     */     
/*     */     public String getCanonicalName() {
/*     */       return this.canonicalName;
/*     */     }
/*     */     
/*     */     public static Action getValueByCanonicalName(String canonicalNameIn) {
/*     */       return nameMapping.get(canonicalNameIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\event\ClickEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */