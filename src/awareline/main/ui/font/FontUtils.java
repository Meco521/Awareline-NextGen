/*     */ package awareline.main.ui.font;
/*     */ 
/*     */ import awareline.main.ui.font.fastuni.FastUniFontRenderer;
/*     */ import awareline.main.ui.font.fontmanager.font.TrueTypeFontDrawer;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiUtilRenderComponents;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public final class FontUtils
/*     */ {
/*     */   public static List<IChatComponent> splitText(IChatComponent p_178908_0_, int p_178908_1_, TrueTypeFontDrawer fontDrawer, boolean p_178908_3_, boolean p_178908_4_) {
/*  16 */     int i = 0;
/*  17 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*  18 */     List<IChatComponent> list = Lists.newArrayList();
/*  19 */     List<IChatComponent> list1 = Lists.newArrayList((Iterable)p_178908_0_);
/*     */     
/*  21 */     for (int j = 0; j < list1.size(); j++) {
/*     */       
/*  23 */       IChatComponent ichatcomponent1 = list1.get(j);
/*  24 */       String s = ichatcomponent1.getUnformattedTextForChat();
/*  25 */       boolean flag = false;
/*     */       
/*  27 */       if (s.contains("\n")) {
/*     */         
/*  29 */         int k = s.indexOf('\n');
/*  30 */         String s1 = s.substring(k + 1);
/*  31 */         s = s.substring(0, k + 1);
/*  32 */         ChatComponentText chatcomponenttext = new ChatComponentText(s1);
/*  33 */         chatcomponenttext.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  34 */         list1.add(j + 1, chatcomponenttext);
/*  35 */         flag = true;
/*     */       } 
/*     */       
/*  38 */       String s4 = GuiUtilRenderComponents.func_178909_a(ichatcomponent1.getChatStyle().getFormattingCode() + s, p_178908_4_);
/*  39 */       String s5 = s4.endsWith("\n") ? s4.substring(0, s4.length() - 1) : s4;
/*  40 */       int i1 = fontDrawer.getStringWidth(s5);
/*  41 */       ChatComponentText chatcomponenttext1 = new ChatComponentText(s5);
/*  42 */       chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*     */       
/*  44 */       if (i + i1 > p_178908_1_) {
/*     */         
/*  46 */         String s2 = trimStringToWidth(fontDrawer, s4, p_178908_1_ - i, false);
/*  47 */         String s3 = (s2.length() < s4.length()) ? s4.substring(s2.length()) : null;
/*     */         
/*  49 */         if (s3 != null) {
/*     */           
/*  51 */           int l = s2.lastIndexOf(' ');
/*     */           
/*  53 */           if (l >= 0 && fontDrawer.getStringWidth(s4.substring(0, l)) > 0) {
/*     */             
/*  55 */             s2 = s4.substring(0, l);
/*     */             
/*  57 */             if (p_178908_3_)
/*     */             {
/*  59 */               l++;
/*     */             }
/*     */             
/*  62 */             s3 = s4.substring(l);
/*     */           }
/*  64 */           else if (i > 0 && !s4.contains(" ")) {
/*     */             
/*  66 */             s2 = "";
/*  67 */             s3 = s4;
/*     */           } 
/*     */           
/*  70 */           ChatComponentText chatcomponenttext2 = new ChatComponentText(s3);
/*  71 */           chatcomponenttext2.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  72 */           list1.add(j + 1, chatcomponenttext2);
/*     */         } 
/*     */         
/*  75 */         i1 = fontDrawer.getStringWidth(s2);
/*  76 */         chatcomponenttext1 = new ChatComponentText(s2);
/*  77 */         chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  78 */         flag = true;
/*     */       } 
/*     */       
/*  81 */       if (i + i1 <= p_178908_1_) {
/*     */         
/*  83 */         i += i1;
/*  84 */         chatComponentText.appendSibling((IChatComponent)chatcomponenttext1);
/*     */       }
/*     */       else {
/*     */         
/*  88 */         flag = true;
/*     */       } 
/*     */       
/*  91 */       if (flag) {
/*     */         
/*  93 */         list.add(chatComponentText);
/*  94 */         i = 0;
/*  95 */         chatComponentText = new ChatComponentText("");
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     list.add(chatComponentText);
/* 100 */     return list;
/*     */   }
/*     */   public static List<IChatComponent> splitText(IChatComponent p_178908_0_, int p_178908_1_, FastUniFontRenderer fontDrawer, boolean p_178908_3_, boolean p_178908_4_) {
/* 103 */     if (fontDrawer == null) {
/* 104 */       return null;
/*     */     }
/* 106 */     int i = 0;
/* 107 */     ChatComponentText chatComponentText = new ChatComponentText("");
/* 108 */     List<IChatComponent> list = Lists.newArrayList();
/* 109 */     List<IChatComponent> list1 = Lists.newArrayList((Iterable)p_178908_0_);
/*     */     
/* 111 */     for (int j = 0; j < list1.size(); j++) {
/*     */       
/* 113 */       IChatComponent ichatcomponent1 = list1.get(j);
/* 114 */       String s = ichatcomponent1.getUnformattedTextForChat();
/* 115 */       boolean flag = false;
/*     */       
/* 117 */       if (s.contains("\n")) {
/*     */         
/* 119 */         int k = s.indexOf('\n');
/* 120 */         String s1 = s.substring(k + 1);
/* 121 */         s = s.substring(0, k + 1);
/* 122 */         ChatComponentText chatcomponenttext = new ChatComponentText(s1);
/* 123 */         chatcomponenttext.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/* 124 */         list1.add(j + 1, chatcomponenttext);
/* 125 */         flag = true;
/*     */       } 
/*     */       
/* 128 */       String s4 = GuiUtilRenderComponents.func_178909_a(ichatcomponent1.getChatStyle().getFormattingCode() + s, p_178908_4_);
/* 129 */       String s5 = s4.endsWith("\n") ? s4.substring(0, s4.length() - 1) : s4;
/* 130 */       int i1 = fontDrawer.getStringWidth(s5);
/* 131 */       ChatComponentText chatcomponenttext1 = new ChatComponentText(s5);
/* 132 */       chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*     */       
/* 134 */       if (i + i1 > p_178908_1_) {
/*     */         
/* 136 */         String s2 = trimStringToWidth(fontDrawer, s4, p_178908_1_ - i, false);
/* 137 */         String s3 = (s2.length() < s4.length()) ? s4.substring(s2.length()) : null;
/*     */         
/* 139 */         if (s3 != null) {
/*     */           
/* 141 */           int l = s2.lastIndexOf(' ');
/*     */           
/* 143 */           if (l >= 0 && fontDrawer.getStringWidth(s4.substring(0, l)) > 0) {
/*     */             
/* 145 */             s2 = s4.substring(0, l);
/*     */             
/* 147 */             if (p_178908_3_)
/*     */             {
/* 149 */               l++;
/*     */             }
/*     */             
/* 152 */             s3 = s4.substring(l);
/*     */           }
/* 154 */           else if (i > 0 && !s4.contains(" ")) {
/*     */             
/* 156 */             s2 = "";
/* 157 */             s3 = s4;
/*     */           } 
/*     */           
/* 160 */           ChatComponentText chatcomponenttext2 = new ChatComponentText(s3);
/* 161 */           chatcomponenttext2.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/* 162 */           list1.add(j + 1, chatcomponenttext2);
/*     */         } 
/*     */         
/* 165 */         i1 = fontDrawer.getStringWidth(s2);
/* 166 */         chatcomponenttext1 = new ChatComponentText(s2);
/* 167 */         chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/* 168 */         flag = true;
/*     */       } 
/*     */       
/* 171 */       if (i + i1 <= p_178908_1_) {
/*     */         
/* 173 */         i += i1;
/* 174 */         chatComponentText.appendSibling((IChatComponent)chatcomponenttext1);
/*     */       }
/*     */       else {
/*     */         
/* 178 */         flag = true;
/*     */       } 
/*     */       
/* 181 */       if (flag) {
/*     */         
/* 183 */         list.add(chatComponentText);
/* 184 */         i = 0;
/* 185 */         chatComponentText = new ChatComponentText("");
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     list.add(chatComponentText);
/* 190 */     return list;
/*     */   }
/*     */   public static String trimStringToWidth(TrueTypeFontDrawer fontDrawer, String text, int width) {
/* 193 */     return trimStringToWidth(fontDrawer, text, width, false);
/*     */   }
/*     */   public static String trimStringToWidth(FastUniFontRenderer fontDrawer, String text, int width) {
/* 196 */     return trimStringToWidth(fontDrawer, text, width, false);
/*     */   }
/*     */   public static String trimStringToWidth(FastUniFontRenderer fontDrawer, String text, int width, boolean reverse) {
/* 199 */     StringBuilder stringbuilder = new StringBuilder();
/* 200 */     float f = 0.0F;
/* 201 */     int i = reverse ? (text.length() - 1) : 0;
/* 202 */     int j = reverse ? -1 : 1;
/* 203 */     boolean flag = false;
/* 204 */     boolean flag1 = false;
/*     */     int k;
/* 206 */     for (k = i; k >= 0 && k < text.length() && f < width; k += j) {
/*     */       
/* 208 */       char c0 = text.charAt(k);
/* 209 */       float f1 = fontDrawer.getCharWidth(c0);
/*     */       
/* 211 */       if (flag) {
/*     */         
/* 213 */         flag = false;
/*     */         
/* 215 */         if (c0 != 'l' && c0 != 'L')
/*     */         {
/* 217 */           if (c0 == 'r' || c0 == 'R')
/*     */           {
/* 219 */             flag1 = false;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 224 */           flag1 = true;
/*     */         }
/*     */       
/* 227 */       } else if (f1 < 0.0F) {
/*     */         
/* 229 */         flag = true;
/*     */       }
/*     */       else {
/*     */         
/* 233 */         f += f1;
/*     */         
/* 235 */         if (flag1)
/*     */         {
/* 237 */           f++;
/*     */         }
/*     */       } 
/*     */       
/* 241 */       if (f > width) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 246 */       if (reverse) {
/*     */         
/* 248 */         stringbuilder.insert(0, c0);
/*     */       }
/*     */       else {
/*     */         
/* 252 */         stringbuilder.append(c0);
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public static String trimStringToWidth(TrueTypeFontDrawer fontDrawer, String text, int width, boolean reverse) {
/* 260 */     StringBuilder stringbuilder = new StringBuilder();
/* 261 */     float f = 0.0F;
/* 262 */     int i = reverse ? (text.length() - 1) : 0;
/* 263 */     int j = reverse ? -1 : 1;
/* 264 */     boolean flag = false;
/* 265 */     boolean flag1 = false;
/*     */     int k;
/* 267 */     for (k = i; k >= 0 && k < text.length() && f < width; k += j) {
/*     */       
/* 269 */       char c0 = text.charAt(k);
/* 270 */       float f1 = fontDrawer.getCharWidth(c0);
/*     */       
/* 272 */       if (flag) {
/*     */         
/* 274 */         flag = false;
/*     */         
/* 276 */         if (c0 != 'l' && c0 != 'L')
/*     */         {
/* 278 */           if (c0 == 'r' || c0 == 'R')
/*     */           {
/* 280 */             flag1 = false;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 285 */           flag1 = true;
/*     */         }
/*     */       
/* 288 */       } else if (f1 < 0.0F) {
/*     */         
/* 290 */         flag = true;
/*     */       }
/*     */       else {
/*     */         
/* 294 */         f += f1;
/*     */         
/* 296 */         if (flag1)
/*     */         {
/* 298 */           f++;
/*     */         }
/*     */       } 
/*     */       
/* 302 */       if (f > width) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 307 */       if (reverse) {
/*     */         
/* 309 */         stringbuilder.insert(0, c0);
/*     */       }
/*     */       else {
/*     */         
/* 313 */         stringbuilder.append(c0);
/*     */       } 
/*     */     } 
/*     */     
/* 317 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public static List<String> listFormattedStringToWidth(TrueTypeFontDrawer fontDrawer, String str, int wrapWidth) {
/* 321 */     return Arrays.asList(wrapFormattedStringToWidth(fontDrawer, str, wrapWidth).split("\n"));
/*     */   }
/*     */   public static List<String> listFormattedStringToWidth(FastUniFontRenderer fontDrawer, String str, int wrapWidth) {
/* 324 */     return Arrays.asList(wrapFormattedStringToWidth(fontDrawer, str, wrapWidth).split("\n"));
/*     */   }
/*     */   public static String wrapFormattedStringToWidth(TrueTypeFontDrawer fontDrawer, String str, int wrapWidth) {
/* 327 */     if (str.length() <= 1)
/*     */     {
/* 329 */       return str;
/*     */     }
/*     */ 
/*     */     
/* 333 */     int i = sizeStringToWidth(fontDrawer, str, wrapWidth);
/*     */     
/* 335 */     if (str.length() <= i)
/*     */     {
/* 337 */       return str;
/*     */     }
/*     */ 
/*     */     
/* 341 */     String s = str.substring(0, i);
/* 342 */     char c0 = str.charAt(i);
/* 343 */     boolean flag = (c0 == ' ' || c0 == '\n');
/* 344 */     String s1 = FontRenderer.getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
/* 345 */     return s + "\n" + wrapFormattedStringToWidth(fontDrawer, s1, wrapWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String wrapFormattedStringToWidth(FastUniFontRenderer fontDrawer, String str, int wrapWidth) {
/* 350 */     if (str.length() <= 1)
/*     */     {
/* 352 */       return str;
/*     */     }
/*     */ 
/*     */     
/* 356 */     int i = sizeStringToWidth(fontDrawer, str, wrapWidth);
/*     */     
/* 358 */     if (str.length() <= i)
/*     */     {
/* 360 */       return str;
/*     */     }
/*     */ 
/*     */     
/* 364 */     String s = str.substring(0, i);
/* 365 */     char c0 = str.charAt(i);
/* 366 */     boolean flag = (c0 == ' ' || c0 == '\n');
/* 367 */     String s1 = FontRenderer.getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
/* 368 */     return s + "\n" + wrapFormattedStringToWidth(fontDrawer, s1, wrapWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int sizeStringToWidth(FastUniFontRenderer fontDrawer, String str, int wrapWidth) {
/* 373 */     int i = str.length();
/* 374 */     float f = 0.0F;
/* 375 */     int j = 0;
/* 376 */     int k = -1;
/*     */     
/* 378 */     for (boolean flag = false; j < i; j++) {
/*     */       
/* 380 */       char c0 = str.charAt(j);
/*     */       
/* 382 */       switch (c0) {
/*     */         
/*     */         case '\n':
/* 385 */           j--;
/*     */           break;
/*     */         
/*     */         case ' ':
/* 389 */           k = j;
/*     */         
/*     */         default:
/* 392 */           f += fontDrawer.getCharWidth(c0);
/*     */           
/* 394 */           if (flag)
/*     */           {
/* 396 */             f++;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case '§':
/* 402 */           if (j < i - 1) {
/*     */             
/* 404 */             j++;
/* 405 */             char c1 = str.charAt(j);
/*     */             
/* 407 */             if (c1 != 'l' && c1 != 'L') {
/*     */               
/* 409 */               if (c1 == 'r' || c1 == 'R' || FontRenderer.isFormatColor(c1))
/*     */               {
/* 411 */                 flag = false;
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/* 416 */             flag = true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 421 */       if (c0 == '\n') {
/*     */ 
/*     */         
/* 424 */         k = ++j;
/*     */         
/*     */         break;
/*     */       } 
/* 428 */       if (Math.round(f) > wrapWidth) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 434 */     return (j != i && k != -1 && k < j) ? k : j;
/*     */   }
/*     */   public static int sizeStringToWidth(TrueTypeFontDrawer fontDrawer, String str, int wrapWidth) {
/* 437 */     int i = str.length();
/* 438 */     float f = 0.0F;
/* 439 */     int j = 0;
/* 440 */     int k = -1;
/*     */     
/* 442 */     for (boolean flag = false; j < i; j++) {
/*     */       
/* 444 */       char c0 = str.charAt(j);
/*     */       
/* 446 */       switch (c0) {
/*     */         
/*     */         case '\n':
/* 449 */           j--;
/*     */           break;
/*     */         
/*     */         case ' ':
/* 453 */           k = j;
/*     */         
/*     */         default:
/* 456 */           f += fontDrawer.getCharWidth(c0);
/*     */           
/* 458 */           if (flag)
/*     */           {
/* 460 */             f++;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case '§':
/* 466 */           if (j < i - 1) {
/*     */             
/* 468 */             j++;
/* 469 */             char c1 = str.charAt(j);
/*     */             
/* 471 */             if (c1 != 'l' && c1 != 'L') {
/*     */               
/* 473 */               if (c1 == 'r' || c1 == 'R' || FontRenderer.isFormatColor(c1))
/*     */               {
/* 475 */                 flag = false;
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/* 480 */             flag = true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 485 */       if (c0 == '\n') {
/*     */ 
/*     */         
/* 488 */         k = ++j;
/*     */         
/*     */         break;
/*     */       } 
/* 492 */       if (Math.round(f) > wrapWidth) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 498 */     return (j != i && k != -1 && k < j) ? k : j;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\FontUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */