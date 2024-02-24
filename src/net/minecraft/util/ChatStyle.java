/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ 
/*     */ public class ChatStyle
/*     */ {
/*     */   private ChatStyle parentStyle;
/*     */   EnumChatFormatting color;
/*     */   Boolean bold;
/*     */   Boolean italic;
/*     */   Boolean underlined;
/*     */   Boolean strikethrough;
/*     */   Boolean obfuscated;
/*     */   ClickEvent chatClickEvent;
/*     */   HoverEvent chatHoverEvent;
/*     */   String insertion;
/*     */   
/*  28 */   private static final ChatStyle rootStyle = new ChatStyle()
/*     */     {
/*     */       public EnumChatFormatting getColor()
/*     */       {
/*  32 */         return null;
/*     */       }
/*     */       
/*     */       public boolean getBold() {
/*  36 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getItalic() {
/*  40 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getStrikethrough() {
/*  44 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getUnderlined() {
/*  48 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getObfuscated() {
/*  52 */         return false;
/*     */       }
/*     */       
/*     */       public ClickEvent getChatClickEvent() {
/*  56 */         return null;
/*     */       }
/*     */       
/*     */       public HoverEvent getChatHoverEvent() {
/*  60 */         return null;
/*     */       }
/*     */       
/*     */       public String getInsertion() {
/*  64 */         return null;
/*     */       }
/*     */       
/*     */       public ChatStyle setColor(EnumChatFormatting color) {
/*  68 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setBold(Boolean boldIn) {
/*  72 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setItalic(Boolean italic) {
/*  76 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setStrikethrough(Boolean strikethrough) {
/*  80 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setUnderlined(Boolean underlined) {
/*  84 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setObfuscated(Boolean obfuscated) {
/*  88 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setChatClickEvent(ClickEvent event) {
/*  92 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setChatHoverEvent(HoverEvent event) {
/*  96 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setParentStyle(ChatStyle parent) {
/* 100 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public String toString() {
/* 104 */         return "Style.ROOT";
/*     */       }
/*     */       
/*     */       public ChatStyle createShallowCopy() {
/* 108 */         return this;
/*     */       }
/*     */       
/*     */       public ChatStyle createDeepCopy() {
/* 112 */         return this;
/*     */       }
/*     */       
/*     */       public String getFormattingCode() {
/* 116 */         return "";
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumChatFormatting getColor() {
/* 125 */     return (this.color == null) ? getParent().getColor() : this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBold() {
/* 133 */     return (this.bold == null) ? getParent().getBold() : this.bold.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getItalic() {
/* 141 */     return (this.italic == null) ? getParent().getItalic() : this.italic.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getStrikethrough() {
/* 149 */     return (this.strikethrough == null) ? getParent().getStrikethrough() : this.strikethrough.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUnderlined() {
/* 157 */     return (this.underlined == null) ? getParent().getUnderlined() : this.underlined.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getObfuscated() {
/* 165 */     return (this.obfuscated == null) ? getParent().getObfuscated() : this.obfuscated.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 173 */     return (this.bold == null && this.italic == null && this.strikethrough == null && this.underlined == null && this.obfuscated == null && this.color == null && this.chatClickEvent == null && this.chatHoverEvent == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClickEvent getChatClickEvent() {
/* 181 */     return (this.chatClickEvent == null) ? getParent().getChatClickEvent() : this.chatClickEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HoverEvent getChatHoverEvent() {
/* 189 */     return (this.chatHoverEvent == null) ? getParent().getChatHoverEvent() : this.chatHoverEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInsertion() {
/* 197 */     return (this.insertion == null) ? getParent().getInsertion() : this.insertion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setColor(EnumChatFormatting color) {
/* 206 */     this.color = color;
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setBold(Boolean boldIn) {
/* 216 */     this.bold = boldIn;
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setItalic(Boolean italic) {
/* 226 */     this.italic = italic;
/* 227 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setStrikethrough(Boolean strikethrough) {
/* 236 */     this.strikethrough = strikethrough;
/* 237 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setUnderlined(Boolean underlined) {
/* 246 */     this.underlined = underlined;
/* 247 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setObfuscated(Boolean obfuscated) {
/* 256 */     this.obfuscated = obfuscated;
/* 257 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setChatClickEvent(ClickEvent event) {
/* 265 */     this.chatClickEvent = event;
/* 266 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setChatHoverEvent(HoverEvent event) {
/* 274 */     this.chatHoverEvent = event;
/* 275 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setInsertion(String insertion) {
/* 283 */     this.insertion = insertion;
/* 284 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setParentStyle(ChatStyle parent) {
/* 293 */     this.parentStyle = parent;
/* 294 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattingCode() {
/* 302 */     if (isEmpty())
/*     */     {
/* 304 */       return (this.parentStyle != null) ? this.parentStyle.getFormattingCode() : "";
/*     */     }
/*     */ 
/*     */     
/* 308 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 310 */     if (getColor() != null)
/*     */     {
/* 312 */       stringbuilder.append(getColor());
/*     */     }
/*     */     
/* 315 */     if (getBold())
/*     */     {
/* 317 */       stringbuilder.append(EnumChatFormatting.BOLD);
/*     */     }
/*     */     
/* 320 */     if (getItalic())
/*     */     {
/* 322 */       stringbuilder.append(EnumChatFormatting.ITALIC);
/*     */     }
/*     */     
/* 325 */     if (getUnderlined())
/*     */     {
/* 327 */       stringbuilder.append(EnumChatFormatting.UNDERLINE);
/*     */     }
/*     */     
/* 330 */     if (getObfuscated())
/*     */     {
/* 332 */       stringbuilder.append(EnumChatFormatting.OBFUSCATED);
/*     */     }
/*     */     
/* 335 */     if (getStrikethrough())
/*     */     {
/* 337 */       stringbuilder.append(EnumChatFormatting.STRIKETHROUGH);
/*     */     }
/*     */     
/* 340 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChatStyle getParent() {
/* 349 */     return (this.parentStyle == null) ? rootStyle : this.parentStyle;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 354 */     return "Style{hasParent=" + ((this.parentStyle != null) ? 1 : 0) + ", color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", obfuscated=" + this.obfuscated + ", clickEvent=" + getChatClickEvent() + ", hoverEvent=" + getChatHoverEvent() + ", insertion=" + getInsertion() + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 359 */     if (this == p_equals_1_)
/*     */     {
/* 361 */       return true;
/*     */     }
/* 363 */     if (!(p_equals_1_ instanceof ChatStyle))
/*     */     {
/* 365 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     ChatStyle chatstyle = (ChatStyle)p_equals_1_;
/*     */     
/* 374 */     if (getBold() == chatstyle.getBold() && getColor() == chatstyle.getColor() && getItalic() == chatstyle.getItalic() && getObfuscated() == chatstyle.getObfuscated() && getStrikethrough() == chatstyle.getStrikethrough() && getUnderlined() == chatstyle.getUnderlined()) {
/*     */ 
/*     */ 
/*     */       
/* 378 */       if (getChatClickEvent() != null)
/*     */       
/* 380 */       { if (!getChatClickEvent().equals(chatstyle.getChatClickEvent()))
/*     */         
/*     */         { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 416 */           boolean flag = false;
/* 417 */           return flag; }  } else if (chatstyle.getChatClickEvent() != null) { return false; }  if (getChatHoverEvent() != null) { if (!getChatHoverEvent().equals(chatstyle.getChatHoverEvent()))
/*     */           return false;  } else if (chatstyle.getChatHoverEvent() != null) { return false; }
/* 419 */        if (getInsertion() != null) { if (getInsertion().equals(chatstyle.getInsertion())) { boolean flag = true;
/* 420 */           return flag; }
/*     */          }
/*     */       else if (chatstyle.getInsertion() == null)
/*     */       { return true; }
/*     */     
/*     */     } 
/* 426 */     return false; } public int hashCode() { int i = this.color.hashCode();
/* 427 */     i = 31 * i + this.bold.hashCode();
/* 428 */     i = 31 * i + this.italic.hashCode();
/* 429 */     i = 31 * i + this.underlined.hashCode();
/* 430 */     i = 31 * i + this.strikethrough.hashCode();
/* 431 */     i = 31 * i + this.obfuscated.hashCode();
/* 432 */     i = 31 * i + this.chatClickEvent.hashCode();
/* 433 */     i = 31 * i + this.chatHoverEvent.hashCode();
/* 434 */     i = 31 * i + this.insertion.hashCode();
/* 435 */     return i; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle createShallowCopy() {
/* 445 */     ChatStyle chatstyle = new ChatStyle();
/* 446 */     chatstyle.bold = this.bold;
/* 447 */     chatstyle.italic = this.italic;
/* 448 */     chatstyle.strikethrough = this.strikethrough;
/* 449 */     chatstyle.underlined = this.underlined;
/* 450 */     chatstyle.obfuscated = this.obfuscated;
/* 451 */     chatstyle.color = this.color;
/* 452 */     chatstyle.chatClickEvent = this.chatClickEvent;
/* 453 */     chatstyle.chatHoverEvent = this.chatHoverEvent;
/* 454 */     chatstyle.parentStyle = this.parentStyle;
/* 455 */     chatstyle.insertion = this.insertion;
/* 456 */     return chatstyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle createDeepCopy() {
/* 465 */     ChatStyle chatstyle = new ChatStyle();
/* 466 */     chatstyle.setBold(Boolean.valueOf(getBold()));
/* 467 */     chatstyle.setItalic(Boolean.valueOf(getItalic()));
/* 468 */     chatstyle.setStrikethrough(Boolean.valueOf(getStrikethrough()));
/* 469 */     chatstyle.setUnderlined(Boolean.valueOf(getUnderlined()));
/* 470 */     chatstyle.setObfuscated(Boolean.valueOf(getObfuscated()));
/* 471 */     chatstyle.setColor(getColor());
/* 472 */     chatstyle.setChatClickEvent(getChatClickEvent());
/* 473 */     chatstyle.setChatHoverEvent(getChatHoverEvent());
/* 474 */     chatstyle.setInsertion(getInsertion());
/* 475 */     return chatstyle;
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<ChatStyle>, JsonSerializer<ChatStyle>
/*     */   {
/*     */     public ChatStyle deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 482 */       if (p_deserialize_1_.isJsonObject()) {
/*     */         
/* 484 */         ChatStyle chatstyle = new ChatStyle();
/* 485 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*     */         
/* 487 */         if (jsonobject == null)
/*     */         {
/* 489 */           return null;
/*     */         }
/*     */ 
/*     */         
/* 493 */         if (jsonobject.has("bold"))
/*     */         {
/* 495 */           chatstyle.bold = Boolean.valueOf(jsonobject.get("bold").getAsBoolean());
/*     */         }
/*     */         
/* 498 */         if (jsonobject.has("italic"))
/*     */         {
/* 500 */           chatstyle.italic = Boolean.valueOf(jsonobject.get("italic").getAsBoolean());
/*     */         }
/*     */         
/* 503 */         if (jsonobject.has("underlined"))
/*     */         {
/* 505 */           chatstyle.underlined = Boolean.valueOf(jsonobject.get("underlined").getAsBoolean());
/*     */         }
/*     */         
/* 508 */         if (jsonobject.has("strikethrough"))
/*     */         {
/* 510 */           chatstyle.strikethrough = Boolean.valueOf(jsonobject.get("strikethrough").getAsBoolean());
/*     */         }
/*     */         
/* 513 */         if (jsonobject.has("obfuscated"))
/*     */         {
/* 515 */           chatstyle.obfuscated = Boolean.valueOf(jsonobject.get("obfuscated").getAsBoolean());
/*     */         }
/*     */         
/* 518 */         if (jsonobject.has("color"))
/*     */         {
/* 520 */           chatstyle.color = (EnumChatFormatting)p_deserialize_3_.deserialize(jsonobject.get("color"), EnumChatFormatting.class);
/*     */         }
/*     */         
/* 523 */         if (jsonobject.has("insertion"))
/*     */         {
/* 525 */           chatstyle.insertion = jsonobject.get("insertion").getAsString();
/*     */         }
/*     */         
/* 528 */         if (jsonobject.has("clickEvent")) {
/*     */           
/* 530 */           JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");
/*     */           
/* 532 */           if (jsonobject1 != null) {
/*     */             
/* 534 */             JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
/* 535 */             ClickEvent.Action clickevent$action = (jsonprimitive == null) ? null : ClickEvent.Action.getValueByCanonicalName(jsonprimitive.getAsString());
/* 536 */             JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
/* 537 */             String s = (jsonprimitive1 == null) ? null : jsonprimitive1.getAsString();
/*     */             
/* 539 */             if (clickevent$action != null && s != null && clickevent$action.shouldAllowInChat())
/*     */             {
/* 541 */               chatstyle.chatClickEvent = new ClickEvent(clickevent$action, s);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 546 */         if (jsonobject.has("hoverEvent")) {
/*     */           
/* 548 */           JsonObject jsonobject2 = jsonobject.getAsJsonObject("hoverEvent");
/*     */           
/* 550 */           if (jsonobject2 != null) {
/*     */             
/* 552 */             JsonPrimitive jsonprimitive2 = jsonobject2.getAsJsonPrimitive("action");
/* 553 */             HoverEvent.Action hoverevent$action = (jsonprimitive2 == null) ? null : HoverEvent.Action.getValueByCanonicalName(jsonprimitive2.getAsString());
/* 554 */             IChatComponent ichatcomponent = (IChatComponent)p_deserialize_3_.deserialize(jsonobject2.get("value"), IChatComponent.class);
/*     */             
/* 556 */             if (hoverevent$action != null && ichatcomponent != null && hoverevent$action.shouldAllowInChat())
/*     */             {
/* 558 */               chatstyle.chatHoverEvent = new HoverEvent(hoverevent$action, ichatcomponent);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 563 */         return chatstyle;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 568 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ChatStyle p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 574 */       if (p_serialize_1_.isEmpty())
/*     */       {
/* 576 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 580 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 582 */       if (p_serialize_1_.bold != null)
/*     */       {
/* 584 */         jsonobject.addProperty("bold", p_serialize_1_.bold);
/*     */       }
/*     */       
/* 587 */       if (p_serialize_1_.italic != null)
/*     */       {
/* 589 */         jsonobject.addProperty("italic", p_serialize_1_.italic);
/*     */       }
/*     */       
/* 592 */       if (p_serialize_1_.underlined != null)
/*     */       {
/* 594 */         jsonobject.addProperty("underlined", p_serialize_1_.underlined);
/*     */       }
/*     */       
/* 597 */       if (p_serialize_1_.strikethrough != null)
/*     */       {
/* 599 */         jsonobject.addProperty("strikethrough", p_serialize_1_.strikethrough);
/*     */       }
/*     */       
/* 602 */       if (p_serialize_1_.obfuscated != null)
/*     */       {
/* 604 */         jsonobject.addProperty("obfuscated", p_serialize_1_.obfuscated);
/*     */       }
/*     */       
/* 607 */       if (p_serialize_1_.color != null)
/*     */       {
/* 609 */         jsonobject.add("color", p_serialize_3_.serialize(p_serialize_1_.color));
/*     */       }
/*     */       
/* 612 */       if (p_serialize_1_.insertion != null)
/*     */       {
/* 614 */         jsonobject.add("insertion", p_serialize_3_.serialize(p_serialize_1_.insertion));
/*     */       }
/*     */       
/* 617 */       if (p_serialize_1_.chatClickEvent != null) {
/*     */         
/* 619 */         JsonObject jsonobject1 = new JsonObject();
/* 620 */         jsonobject1.addProperty("action", p_serialize_1_.chatClickEvent.getAction().getCanonicalName());
/* 621 */         jsonobject1.addProperty("value", p_serialize_1_.chatClickEvent.getValue());
/* 622 */         jsonobject.add("clickEvent", (JsonElement)jsonobject1);
/*     */       } 
/*     */       
/* 625 */       if (p_serialize_1_.chatHoverEvent != null) {
/*     */         
/* 627 */         JsonObject jsonobject2 = new JsonObject();
/* 628 */         jsonobject2.addProperty("action", p_serialize_1_.chatHoverEvent.getAction().getCanonicalName());
/* 629 */         jsonobject2.add("value", p_serialize_3_.serialize(p_serialize_1_.chatHoverEvent.getValue()));
/* 630 */         jsonobject.add("hoverEvent", (JsonElement)jsonobject2);
/*     */       } 
/*     */       
/* 633 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ChatStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */