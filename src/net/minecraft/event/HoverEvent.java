/*     */ package net.minecraft.event;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HoverEvent
/*     */ {
/*     */   private final Action action;
/*     */   private final IChatComponent value;
/*     */   
/*     */   public HoverEvent(Action actionIn, IChatComponent valueIn) {
/*  15 */     this.action = actionIn;
/*  16 */     this.value = valueIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() {
/*  24 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getValue() {
/*  33 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  38 */     if (this == p_equals_1_)
/*     */     {
/*  40 */       return true;
/*     */     }
/*  42 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/*  44 */       HoverEvent hoverevent = (HoverEvent)p_equals_1_;
/*     */       
/*  46 */       if (this.action != hoverevent.action)
/*     */       {
/*  48 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  52 */       if (this.value != null) {
/*     */         
/*  54 */         if (!this.value.equals(hoverevent.value))
/*     */         {
/*  56 */           return false;
/*     */         }
/*     */       }
/*  59 */       else if (hoverevent.value != null) {
/*     */         
/*  61 */         return false;
/*     */       } 
/*     */       
/*  64 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  75 */     return "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  80 */     int i = this.action.hashCode();
/*  81 */     i = 31 * i + ((this.value != null) ? this.value.hashCode() : 0);
/*  82 */     return i;
/*     */   }
/*     */   
/*     */   public enum Action
/*     */   {
/*  87 */     SHOW_TEXT("show_text", true),
/*  88 */     SHOW_ACHIEVEMENT("show_achievement", true),
/*  89 */     SHOW_ITEM("show_item", true),
/*  90 */     SHOW_ENTITY("show_entity", true);
/*     */     
/*  92 */     private static final Map<String, Action> nameMapping = Maps.newHashMap();
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
/* 118 */       for (Action hoverevent$action : values())
/*     */       {
/* 120 */         nameMapping.put(hoverevent$action.canonicalName, hoverevent$action);
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


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\event\HoverEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */