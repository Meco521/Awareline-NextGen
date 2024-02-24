/*     */ package net.optifine.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.config.ShaderOption;
/*     */ import net.optifine.shaders.gui.GuiButtonShaderOption;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ public class TooltipProviderShaderOptions
/*     */   extends TooltipProviderOptions
/*     */ {
/*     */   public String[] getTooltipLines(GuiButton btn, int width) {
/*  20 */     if (!(btn instanceof GuiButtonShaderOption))
/*     */     {
/*  22 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  26 */     GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)btn;
/*  27 */     ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*  28 */     String[] astring = makeTooltipLines(shaderoption, width);
/*  29 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(ShaderOption so, int width) {
/*  35 */     String s = so.getNameText();
/*  36 */     String s1 = Config.normalize(so.getDescriptionText()).trim();
/*  37 */     String[] astring = splitDescription(s1);
/*  38 */     GameSettings gamesettings = Config.getGameSettings();
/*  39 */     String s2 = null;
/*     */     
/*  41 */     if (!s.equals(so.getName()) && gamesettings.advancedItemTooltips)
/*     */     {
/*  43 */       s2 = "§8" + Lang.get("of.general.id") + ": " + so.getName();
/*     */     }
/*     */     
/*  46 */     String s3 = null;
/*     */     
/*  48 */     if (so.getPaths() != null && gamesettings.advancedItemTooltips)
/*     */     {
/*  50 */       s3 = "§8" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths());
/*     */     }
/*     */     
/*  53 */     String s4 = null;
/*     */     
/*  55 */     if (so.getValueDefault() != null && gamesettings.advancedItemTooltips) {
/*     */       
/*  57 */       String s5 = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
/*  58 */       s4 = "§8" + Lang.getDefault() + ": " + s5;
/*     */     } 
/*     */     
/*  61 */     List<String> list = new ArrayList<>();
/*  62 */     list.add(s);
/*  63 */     list.addAll(Arrays.asList(astring));
/*     */     
/*  65 */     if (s2 != null)
/*     */     {
/*  67 */       list.add(s2);
/*     */     }
/*     */     
/*  70 */     if (s3 != null)
/*     */     {
/*  72 */       list.add(s3);
/*     */     }
/*     */     
/*  75 */     if (s4 != null)
/*     */     {
/*  77 */       list.add(s4);
/*     */     }
/*     */     
/*  80 */     String[] astring1 = makeTooltipLines(width, list);
/*  81 */     return astring1;
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] splitDescription(String desc) {
/*  86 */     if (desc.length() <= 0)
/*     */     {
/*  88 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/*  92 */     desc = StrUtils.removePrefix(desc, "//");
/*  93 */     String[] astring = desc.split("\\. ");
/*     */     
/*  95 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  97 */       astring[i] = "- " + astring[i].trim();
/*  98 */       astring[i] = StrUtils.removeSuffix(astring[i], ".");
/*     */     } 
/*     */     
/* 101 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(int width, List<String> args) {
/* 107 */     FontRenderer fontrenderer = (Config.getMinecraft()).fontRendererObj;
/* 108 */     List<String> list = new ArrayList<>();
/*     */     
/* 110 */     for (int i = 0; i < args.size(); i++) {
/*     */       
/* 112 */       String s = args.get(i);
/*     */       
/* 114 */       if (s != null && !s.isEmpty())
/*     */       {
/* 116 */         list.addAll(fontrenderer.listFormattedStringToWidth(s, width));
/*     */       }
/*     */     } 
/*     */     
/* 120 */     String[] astring = list.<String>toArray(new String[0]);
/* 121 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\gui\TooltipProviderShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */