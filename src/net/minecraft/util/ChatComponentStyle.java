/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class ChatComponentStyle
/*     */   implements IChatComponent
/*     */ {
/*  12 */   protected List<IChatComponent> siblings = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private ChatStyle style;
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent appendSibling(IChatComponent component) {
/*  20 */     component.getChatStyle().setParentStyle(getChatStyle());
/*  21 */     this.siblings.add(component);
/*  22 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IChatComponent> getSiblings() {
/*  27 */     return this.siblings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent appendText(String text) {
/*  35 */     return appendSibling(new ChatComponentText(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent setChatStyle(ChatStyle style) {
/*  40 */     this.style = style;
/*     */     
/*  42 */     for (IChatComponent ichatcomponent : this.siblings)
/*     */     {
/*  44 */       ichatcomponent.getChatStyle().setParentStyle(getChatStyle());
/*     */     }
/*     */     
/*  47 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatStyle getChatStyle() {
/*  52 */     if (this.style == null) {
/*     */       
/*  54 */       this.style = new ChatStyle();
/*     */       
/*  56 */       for (IChatComponent ichatcomponent : this.siblings)
/*     */       {
/*  58 */         ichatcomponent.getChatStyle().setParentStyle(this.style);
/*     */       }
/*     */     } 
/*     */     
/*  62 */     return this.style;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<IChatComponent> iterator() {
/*  67 */     return Iterators.concat((Iterator)Iterators.forArray((Object[])new ChatComponentStyle[] { this }, ), createDeepCopyIterator(this.siblings));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getUnformattedText() {
/*  75 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  77 */     for (IChatComponent ichatcomponent : this)
/*     */     {
/*  79 */       stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
/*     */     }
/*     */     
/*  82 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getFormattedText() {
/*  90 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  92 */     for (IChatComponent ichatcomponent : this) {
/*     */       
/*  94 */       stringbuilder.append(ichatcomponent.getChatStyle().getFormattingCode());
/*  95 */       stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
/*  96 */       stringbuilder.append(EnumChatFormatting.RESET);
/*     */     } 
/*     */     
/*  99 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterator<IChatComponent> createDeepCopyIterator(Iterable<IChatComponent> components) {
/* 104 */     Iterator<IChatComponent> iterator = Iterators.concat(Iterators.transform(components.iterator(), new Function<IChatComponent, Iterator<IChatComponent>>()
/*     */           {
/*     */             public Iterator<IChatComponent> apply(IChatComponent p_apply_1_)
/*     */             {
/* 108 */               return p_apply_1_.iterator();
/*     */             }
/*     */           }));
/* 111 */     iterator = Iterators.transform(iterator, new Function<IChatComponent, IChatComponent>()
/*     */         {
/*     */           public IChatComponent apply(IChatComponent p_apply_1_)
/*     */           {
/* 115 */             IChatComponent ichatcomponent = p_apply_1_.createCopy();
/* 116 */             ichatcomponent.setChatStyle(ichatcomponent.getChatStyle().createDeepCopy());
/* 117 */             return ichatcomponent;
/*     */           }
/*     */         });
/* 120 */     return iterator;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 125 */     if (this == p_equals_1_)
/*     */     {
/* 127 */       return true;
/*     */     }
/* 129 */     if (!(p_equals_1_ instanceof ChatComponentStyle))
/*     */     {
/* 131 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 135 */     ChatComponentStyle chatcomponentstyle = (ChatComponentStyle)p_equals_1_;
/* 136 */     return (this.siblings.equals(chatcomponentstyle.siblings) && getChatStyle().equals(chatcomponentstyle.getChatStyle()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 142 */     return 31 * this.style.hashCode() + this.siblings.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 147 */     return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ChatComponentStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */