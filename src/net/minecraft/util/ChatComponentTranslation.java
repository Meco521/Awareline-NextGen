/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ChatComponentTranslation
/*     */   extends ChatComponentStyle
/*     */ {
/*     */   private final String key;
/*     */   private final Object[] formatArgs;
/*  17 */   private final Object syncLock = new Object();
/*  18 */   private long lastTranslationUpdateTimeInMilliseconds = -1L;
/*  19 */   List<IChatComponent> children = Lists.newArrayList();
/*  20 */   public static final Pattern stringVariablePattern = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
/*     */ 
/*     */   
/*     */   public ChatComponentTranslation(String translationKey, Object... args) {
/*  24 */     this.key = translationKey;
/*  25 */     this.formatArgs = args;
/*     */     
/*  27 */     for (Object object : args) {
/*     */       
/*  29 */       if (object instanceof IChatComponent)
/*     */       {
/*  31 */         ((IChatComponent)object).getChatStyle().setParentStyle(getChatStyle());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void ensureInitialized() {
/*  41 */     synchronized (this.syncLock) {
/*     */       
/*  43 */       long i = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
/*     */       
/*  45 */       if (i == this.lastTranslationUpdateTimeInMilliseconds) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  50 */       this.lastTranslationUpdateTimeInMilliseconds = i;
/*  51 */       this.children.clear();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  56 */       initializeFromFormat(StatCollector.translateToLocal(this.key));
/*     */     }
/*  58 */     catch (ChatComponentTranslationFormatException chatcomponenttranslationformatexception) {
/*     */       
/*  60 */       this.children.clear();
/*     */ 
/*     */       
/*     */       try {
/*  64 */         initializeFromFormat(StatCollector.translateToFallback(this.key));
/*     */       }
/*  66 */       catch (ChatComponentTranslationFormatException var5) {
/*     */         
/*  68 */         throw chatcomponenttranslationformatexception;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeFromFormat(String format) {
/*  78 */     boolean flag = false;
/*  79 */     Matcher matcher = stringVariablePattern.matcher(format);
/*  80 */     int i = 0;
/*  81 */     int j = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  87 */       for (; matcher.find(j); j = l) {
/*     */         
/*  89 */         int k = matcher.start();
/*  90 */         int l = matcher.end();
/*     */         
/*  92 */         if (k > j) {
/*     */           
/*  94 */           ChatComponentText chatcomponenttext = new ChatComponentText(String.format(format.substring(j, k), new Object[0]));
/*  95 */           chatcomponenttext.getChatStyle().setParentStyle(getChatStyle());
/*  96 */           this.children.add(chatcomponenttext);
/*     */         } 
/*     */         
/*  99 */         String s2 = matcher.group(2);
/* 100 */         String s = format.substring(k, l);
/*     */         
/* 102 */         if ("%".equals(s2) && "%%".equals(s)) {
/*     */           
/* 104 */           ChatComponentText chatcomponenttext2 = new ChatComponentText("%");
/* 105 */           chatcomponenttext2.getChatStyle().setParentStyle(getChatStyle());
/* 106 */           this.children.add(chatcomponenttext2);
/*     */         }
/*     */         else {
/*     */           
/* 110 */           if (!"s".equals(s2))
/*     */           {
/* 112 */             throw new ChatComponentTranslationFormatException(this, "Unsupported format: '" + s + "'");
/*     */           }
/*     */           
/* 115 */           String s1 = matcher.group(1);
/* 116 */           int i1 = (s1 != null) ? (Integer.parseInt(s1) - 1) : i++;
/*     */           
/* 118 */           if (i1 < this.formatArgs.length)
/*     */           {
/* 120 */             this.children.add(getFormatArgumentAsComponent(i1));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 125 */       if (j < format.length())
/*     */       {
/* 127 */         ChatComponentText chatcomponenttext1 = new ChatComponentText(String.format(format.substring(j), new Object[0]));
/* 128 */         chatcomponenttext1.getChatStyle().setParentStyle(getChatStyle());
/* 129 */         this.children.add(chatcomponenttext1);
/*     */       }
/*     */     
/* 132 */     } catch (IllegalFormatException illegalformatexception) {
/*     */       
/* 134 */       throw new ChatComponentTranslationFormatException(this, illegalformatexception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private IChatComponent getFormatArgumentAsComponent(int index) {
/*     */     IChatComponent ichatcomponent;
/* 140 */     if (index >= this.formatArgs.length)
/*     */     {
/* 142 */       throw new ChatComponentTranslationFormatException(this, index);
/*     */     }
/*     */ 
/*     */     
/* 146 */     Object object = this.formatArgs[index];
/*     */ 
/*     */     
/* 149 */     if (object instanceof IChatComponent) {
/*     */       
/* 151 */       ichatcomponent = (IChatComponent)object;
/*     */     }
/*     */     else {
/*     */       
/* 155 */       ichatcomponent = new ChatComponentText((object == null) ? "null" : object.toString());
/* 156 */       ichatcomponent.getChatStyle().setParentStyle(getChatStyle());
/*     */     } 
/*     */     
/* 159 */     return ichatcomponent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent setChatStyle(ChatStyle style) {
/* 165 */     super.setChatStyle(style);
/*     */     
/* 167 */     for (Object object : this.formatArgs) {
/*     */       
/* 169 */       if (object instanceof IChatComponent)
/*     */       {
/* 171 */         ((IChatComponent)object).getChatStyle().setParentStyle(getChatStyle());
/*     */       }
/*     */     } 
/*     */     
/* 175 */     if (this.lastTranslationUpdateTimeInMilliseconds > -1L)
/*     */     {
/* 177 */       for (IChatComponent ichatcomponent : this.children)
/*     */       {
/* 179 */         ichatcomponent.getChatStyle().setParentStyle(style);
/*     */       }
/*     */     }
/*     */     
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<IChatComponent> iterator() {
/* 188 */     ensureInitialized();
/* 189 */     return Iterators.concat(createDeepCopyIterator(this.children), createDeepCopyIterator(this.siblings));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnformattedTextForChat() {
/* 198 */     ensureInitialized();
/* 199 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 201 */     for (IChatComponent ichatcomponent : this.children)
/*     */     {
/* 203 */       stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
/*     */     }
/*     */     
/* 206 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatComponentTranslation createCopy() {
/* 214 */     Object[] aobject = new Object[this.formatArgs.length];
/*     */     
/* 216 */     for (int i = 0; i < this.formatArgs.length; i++) {
/*     */       
/* 218 */       if (this.formatArgs[i] instanceof IChatComponent) {
/*     */         
/* 220 */         aobject[i] = ((IChatComponent)this.formatArgs[i]).createCopy();
/*     */       }
/*     */       else {
/*     */         
/* 224 */         aobject[i] = this.formatArgs[i];
/*     */       } 
/*     */     } 
/*     */     
/* 228 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(this.key, aobject);
/* 229 */     chatcomponenttranslation.setChatStyle(getChatStyle().createShallowCopy());
/*     */     
/* 231 */     for (IChatComponent ichatcomponent : getSiblings())
/*     */     {
/* 233 */       chatcomponenttranslation.appendSibling(ichatcomponent.createCopy());
/*     */     }
/*     */     
/* 236 */     return chatcomponenttranslation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 241 */     if (this == p_equals_1_)
/*     */     {
/* 243 */       return true;
/*     */     }
/* 245 */     if (!(p_equals_1_ instanceof ChatComponentTranslation))
/*     */     {
/* 247 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 251 */     ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation)p_equals_1_;
/* 252 */     return (Arrays.equals(this.formatArgs, chatcomponenttranslation.formatArgs) && this.key.equals(chatcomponenttranslation.key) && super.equals(p_equals_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 258 */     int i = super.hashCode();
/* 259 */     i = 31 * i + this.key.hashCode();
/* 260 */     i = 31 * i + Arrays.hashCode(this.formatArgs);
/* 261 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 266 */     return "TranslatableComponent{key='" + this.key + '\'' + ", args=" + Arrays.toString(this.formatArgs) + ", siblings=" + this.siblings + ", style=" + getChatStyle() + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKey() {
/* 271 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getFormatArgs() {
/* 276 */     return this.formatArgs;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\ChatComponentTranslation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */