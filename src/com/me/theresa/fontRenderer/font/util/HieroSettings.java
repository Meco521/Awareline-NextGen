/*     */ package com.me.theresa.fontRenderer.font.util;
/*     */ 
/*     */ import com.me.theresa.fontRenderer.font.SlickException;
/*     */ import com.me.theresa.fontRenderer.font.effect.ConfigurableEffect;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HieroSettings {
/*  17 */   private int fontSize = 12;
/*     */   
/*     */   private boolean bold = false;
/*     */   
/*     */   private boolean italic = false;
/*     */   
/*     */   private int paddingTop;
/*     */   
/*     */   private int paddingLeft;
/*     */   
/*     */   private int paddingBottom;
/*     */   
/*     */   private int paddingRight;
/*     */   
/*     */   private int paddingAdvanceX;
/*     */   
/*     */   private int paddingAdvanceY;
/*     */   
/*  35 */   private int glyphPageWidth = 512;
/*     */   
/*  37 */   private int glyphPageHeight = 512;
/*     */   
/*  39 */   private final List effects = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   public HieroSettings() {}
/*     */ 
/*     */   
/*     */   public HieroSettings(String hieroFileRef) throws SlickException {
/*  47 */     this(ResourceLoader.getResourceAsStream(hieroFileRef));
/*     */   }
/*     */ 
/*     */   
/*     */   public HieroSettings(InputStream in) throws SlickException {
/*     */     try {
/*  53 */       BufferedReader reader = new BufferedReader(new InputStreamReader(in));
/*     */       while (true) {
/*  55 */         String line = reader.readLine();
/*  56 */         if (line == null)
/*  57 */           break;  line = line.trim();
/*  58 */         if (line.length() == 0)
/*  59 */           continue;  String[] pieces = line.split("=", 2);
/*  60 */         String name = pieces[0].trim();
/*  61 */         String value = pieces[1];
/*  62 */         if (name.equals("font.size")) {
/*  63 */           this.fontSize = Integer.parseInt(value); continue;
/*  64 */         }  if (name.equals("font.bold")) {
/*  65 */           this.bold = Boolean.valueOf(value).booleanValue(); continue;
/*  66 */         }  if (name.equals("font.italic")) {
/*  67 */           this.italic = Boolean.valueOf(value).booleanValue(); continue;
/*  68 */         }  if (name.equals("pad.top")) {
/*  69 */           this.paddingTop = Integer.parseInt(value); continue;
/*  70 */         }  if (name.equals("pad.right")) {
/*  71 */           this.paddingRight = Integer.parseInt(value); continue;
/*  72 */         }  if (name.equals("pad.bottom")) {
/*  73 */           this.paddingBottom = Integer.parseInt(value); continue;
/*  74 */         }  if (name.equals("pad.left")) {
/*  75 */           this.paddingLeft = Integer.parseInt(value); continue;
/*  76 */         }  if (name.equals("pad.advance.x")) {
/*  77 */           this.paddingAdvanceX = Integer.parseInt(value); continue;
/*  78 */         }  if (name.equals("pad.advance.y")) {
/*  79 */           this.paddingAdvanceY = Integer.parseInt(value); continue;
/*  80 */         }  if (name.equals("glyph.page.width")) {
/*  81 */           this.glyphPageWidth = Integer.parseInt(value); continue;
/*  82 */         }  if (name.equals("glyph.page.height")) {
/*  83 */           this.glyphPageHeight = Integer.parseInt(value); continue;
/*  84 */         }  if (name.equals("effect.class")) {
/*     */           try {
/*  86 */             this.effects.add(Class.forName(value).newInstance());
/*  87 */           } catch (Exception ex) {
/*  88 */             throw new SlickException("Unable to create effect instance: " + value, ex);
/*     */           }  continue;
/*  90 */         }  if (name.startsWith("effect.")) {
/*     */           
/*  92 */           name = name.substring(7);
/*  93 */           ConfigurableEffect effect = this.effects.get(this.effects.size() - 1);
/*  94 */           List values = effect.getValues();
/*  95 */           for (Iterator<ConfigurableEffect.Value> iter = values.iterator(); iter.hasNext(); ) {
/*  96 */             ConfigurableEffect.Value effectValue = iter.next();
/*  97 */             if (effectValue.getName().equals(name)) {
/*  98 */               effectValue.setString(value);
/*     */               break;
/*     */             } 
/*     */           } 
/* 102 */           effect.setValues(values);
/*     */         } 
/*     */       } 
/* 105 */       reader.close();
/* 106 */     } catch (Exception ex) {
/* 107 */       throw new SlickException("Unable to load Hiero font file", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingTop() {
/* 113 */     return this.paddingTop;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingTop(int paddingTop) {
/* 118 */     this.paddingTop = paddingTop;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingLeft() {
/* 123 */     return this.paddingLeft;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingLeft(int paddingLeft) {
/* 128 */     this.paddingLeft = paddingLeft;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingBottom() {
/* 133 */     return this.paddingBottom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingBottom(int paddingBottom) {
/* 138 */     this.paddingBottom = paddingBottom;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingRight() {
/* 143 */     return this.paddingRight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingRight(int paddingRight) {
/* 148 */     this.paddingRight = paddingRight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingAdvanceX() {
/* 153 */     return this.paddingAdvanceX;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingAdvanceX(int paddingAdvanceX) {
/* 158 */     this.paddingAdvanceX = paddingAdvanceX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaddingAdvanceY() {
/* 163 */     return this.paddingAdvanceY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaddingAdvanceY(int paddingAdvanceY) {
/* 168 */     this.paddingAdvanceY = paddingAdvanceY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGlyphPageWidth() {
/* 173 */     return this.glyphPageWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGlyphPageWidth(int glyphPageWidth) {
/* 178 */     this.glyphPageWidth = glyphPageWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGlyphPageHeight() {
/* 183 */     return this.glyphPageHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGlyphPageHeight(int glyphPageHeight) {
/* 188 */     this.glyphPageHeight = glyphPageHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFontSize() {
/* 193 */     return this.fontSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFontSize(int fontSize) {
/* 198 */     this.fontSize = fontSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBold() {
/* 203 */     return this.bold;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBold(boolean bold) {
/* 208 */     this.bold = bold;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isItalic() {
/* 213 */     return this.italic;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItalic(boolean italic) {
/* 218 */     this.italic = italic;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getEffects() {
/* 223 */     return this.effects;
/*     */   }
/*     */   
/*     */   public void save(File file) throws IOException {
/* 227 */     PrintStream out = new PrintStream(Files.newOutputStream(file.toPath(), new java.nio.file.OpenOption[0]));
/* 228 */     out.println("font.size=" + this.fontSize);
/* 229 */     out.println("font.bold=" + this.bold);
/* 230 */     out.println("font.italic=" + this.italic);
/* 231 */     out.println();
/* 232 */     out.println("pad.top=" + this.paddingTop);
/* 233 */     out.println("pad.right=" + this.paddingRight);
/* 234 */     out.println("pad.bottom=" + this.paddingBottom);
/* 235 */     out.println("pad.left=" + this.paddingLeft);
/* 236 */     out.println("pad.advance.x=" + this.paddingAdvanceX);
/* 237 */     out.println("pad.advance.y=" + this.paddingAdvanceY);
/* 238 */     out.println();
/* 239 */     out.println("glyph.page.width=" + this.glyphPageWidth);
/* 240 */     out.println("glyph.page.height=" + this.glyphPageHeight);
/* 241 */     out.println();
/* 242 */     for (Iterator<ConfigurableEffect> iter = this.effects.iterator(); iter.hasNext(); ) {
/* 243 */       ConfigurableEffect effect = iter.next();
/* 244 */       out.println("effect.class=" + effect.getClass().getName());
/* 245 */       for (Iterator<ConfigurableEffect.Value> iter2 = effect.getValues().iterator(); iter2.hasNext(); ) {
/* 246 */         ConfigurableEffect.Value value = iter2.next();
/* 247 */         out.println("effect." + value.getName() + "=" + value.getString());
/*     */       } 
/* 249 */       out.println();
/*     */     } 
/* 251 */     out.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\fon\\util\HieroSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */