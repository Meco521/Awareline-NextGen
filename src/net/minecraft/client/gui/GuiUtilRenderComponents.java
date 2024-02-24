/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiUtilRenderComponents
/*     */ {
/*     */   public static String func_178909_a(String p_178909_0_, boolean p_178909_1_) {
/*  15 */     return (!p_178909_1_ && !(Minecraft.getMinecraft()).gameSettings.chatColours) ? EnumChatFormatting.getTextWithoutFormattingCodes(p_178909_0_) : p_178909_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<IChatComponent> splitText(IChatComponent p_178908_0_, int p_178908_1_, FontRenderer p_178908_2_, boolean p_178908_3_, boolean p_178908_4_) {
/*  20 */     int i = 0;
/*  21 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*  22 */     List<IChatComponent> list = Lists.newArrayList();
/*  23 */     List<IChatComponent> list1 = Lists.newArrayList((Iterable)p_178908_0_);
/*     */     
/*  25 */     for (int j = 0; j < list1.size(); j++) {
/*     */       
/*  27 */       IChatComponent ichatcomponent1 = list1.get(j);
/*  28 */       String s = ichatcomponent1.getUnformattedTextForChat();
/*  29 */       boolean flag = false;
/*     */       
/*  31 */       if (s.contains("\n")) {
/*     */         
/*  33 */         int k = s.indexOf('\n');
/*  34 */         String s1 = s.substring(k + 1);
/*  35 */         s = s.substring(0, k + 1);
/*  36 */         ChatComponentText chatcomponenttext = new ChatComponentText(s1);
/*  37 */         chatcomponenttext.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  38 */         list1.add(j + 1, chatcomponenttext);
/*  39 */         flag = true;
/*     */       } 
/*     */       
/*  42 */       String s4 = func_178909_a(ichatcomponent1.getChatStyle().getFormattingCode() + s, p_178908_4_);
/*  43 */       String s5 = (!s4.isEmpty() && s4.charAt(s4.length() - 1) == '\n') ? s4.substring(0, s4.length() - 1) : s4;
/*  44 */       int i1 = p_178908_2_.getStringWidth(s5);
/*  45 */       ChatComponentText chatcomponenttext1 = new ChatComponentText(s5);
/*  46 */       chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*     */       
/*  48 */       if (i + i1 > p_178908_1_) {
/*     */         
/*  50 */         String s2 = p_178908_2_.trimStringToWidth(s4, p_178908_1_ - i, false);
/*  51 */         String s3 = (s2.length() < s4.length()) ? s4.substring(s2.length()) : null;
/*     */         
/*  53 */         if (s3 != null) {
/*     */           
/*  55 */           int l = s2.lastIndexOf(' ');
/*     */           
/*  57 */           if (l >= 0 && p_178908_2_.getStringWidth(s4.substring(0, l)) > 0) {
/*     */             
/*  59 */             s2 = s4.substring(0, l);
/*     */             
/*  61 */             if (p_178908_3_)
/*     */             {
/*  63 */               l++;
/*     */             }
/*     */             
/*  66 */             s3 = s4.substring(l);
/*     */           }
/*  68 */           else if (i > 0 && !s4.contains(" ")) {
/*     */             
/*  70 */             s2 = "";
/*  71 */             s3 = s4;
/*     */           } 
/*     */           
/*  74 */           ChatComponentText chatcomponenttext2 = new ChatComponentText(s3);
/*  75 */           chatcomponenttext2.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  76 */           list1.add(j + 1, chatcomponenttext2);
/*     */         } 
/*     */         
/*  79 */         i1 = p_178908_2_.getStringWidth(s2);
/*  80 */         chatcomponenttext1 = new ChatComponentText(s2);
/*  81 */         chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  82 */         flag = true;
/*     */       } 
/*     */       
/*  85 */       if (i + i1 <= p_178908_1_) {
/*     */         
/*  87 */         i += i1;
/*  88 */         chatComponentText.appendSibling((IChatComponent)chatcomponenttext1);
/*     */       }
/*     */       else {
/*     */         
/*  92 */         flag = true;
/*     */       } 
/*     */       
/*  95 */       if (flag) {
/*     */         
/*  97 */         list.add(chatComponentText);
/*  98 */         i = 0;
/*  99 */         chatComponentText = new ChatComponentText("");
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     list.add(chatComponentText);
/* 104 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiUtilRenderComponents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */