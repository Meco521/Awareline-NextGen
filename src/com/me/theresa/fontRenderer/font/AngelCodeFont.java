/*     */ package com.me.theresa.fontRenderer.font;
/*     */ import com.me.theresa.fontRenderer.font.impl.Font;
/*     */ import com.me.theresa.fontRenderer.font.log.Log;
/*     */ import com.me.theresa.fontRenderer.font.opengl.renderer.Renderer;
/*     */ import com.me.theresa.fontRenderer.font.util.ResourceLoader;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class AngelCodeFont implements Font {
/*  19 */   private static final SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   private static final int DISPLAY_LIST_CACHE_SIZE = 200;
/*     */ 
/*     */   
/*     */   private static final int MAX_CHAR = 255;
/*     */ 
/*     */   
/*     */   private boolean displayListCaching = true;
/*     */ 
/*     */   
/*     */   final Image fontImage;
/*     */   
/*     */   private CharDef[] chars;
/*     */   
/*     */   private int lineHeight;
/*     */   
/*  37 */   private int baseDisplayListID = -1;
/*     */   
/*     */   int eldestDisplayListID;
/*     */   
/*     */   DisplayList eldestDisplayList;
/*     */ 
/*     */   
/*  44 */   private final LinkedHashMap displayLists = new LinkedHashMap<Object, Object>(200, 1.0F, true) {
/*     */       protected boolean removeEldestEntry(Map.Entry eldest) {
/*  46 */         AngelCodeFont.this.eldestDisplayList = (AngelCodeFont.DisplayList)eldest.getValue();
/*  47 */         AngelCodeFont.this.eldestDisplayListID = AngelCodeFont.this.eldestDisplayList.id;
/*     */         
/*  49 */         return false;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public AngelCodeFont(String fntFile, Image image) throws SlickException {
/*  55 */     this.fontImage = image;
/*     */     
/*  57 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
/*     */   }
/*     */ 
/*     */   
/*     */   public AngelCodeFont(String fntFile, String imgFile) throws SlickException {
/*  62 */     this.fontImage = new Image(imgFile);
/*     */     
/*  64 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AngelCodeFont(String fntFile, Image image, boolean caching) throws SlickException {
/*  70 */     this.fontImage = image;
/*  71 */     this.displayListCaching = caching;
/*  72 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AngelCodeFont(String fntFile, String imgFile, boolean caching) throws SlickException {
/*  78 */     this.fontImage = new Image(imgFile);
/*  79 */     this.displayListCaching = caching;
/*  80 */     parseFnt(ResourceLoader.getResourceAsStream(fntFile));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AngelCodeFont(String name, InputStream fntFile, InputStream imgFile) throws SlickException {
/*  86 */     this.fontImage = new Image(imgFile, name, false);
/*     */     
/*  88 */     parseFnt(fntFile);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AngelCodeFont(String name, InputStream fntFile, InputStream imgFile, boolean caching) throws SlickException {
/*  94 */     this.fontImage = new Image(imgFile, name, false);
/*     */     
/*  96 */     this.displayListCaching = caching;
/*  97 */     parseFnt(fntFile);
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseFnt(InputStream fntFile) throws SlickException {
/* 102 */     if (this.displayListCaching) {
/* 103 */       this.baseDisplayListID = GL.glGenLists(200);
/* 104 */       if (this.baseDisplayListID == 0) this.displayListCaching = false;
/*     */     
/*     */     } 
/*     */     
/*     */     try {
/* 109 */       BufferedReader in = new BufferedReader(new InputStreamReader(fntFile));
/*     */       
/* 111 */       String info = in.readLine();
/* 112 */       String common = in.readLine();
/* 113 */       String page = in.readLine();
/*     */       
/* 115 */       Map<Object, Object> kerning = new HashMap<>(64);
/* 116 */       List<CharDef> charDefs = new ArrayList(255);
/* 117 */       int maxChar = 0;
/* 118 */       boolean done = false;
/* 119 */       while (!done) {
/* 120 */         String line = in.readLine();
/* 121 */         if (line == null) {
/* 122 */           done = true; continue;
/*     */         } 
/* 124 */         if (!line.startsWith("chars c"))
/*     */         {
/* 126 */           if (line.startsWith("char")) {
/* 127 */             CharDef def = parseChar(line);
/* 128 */             if (def != null) {
/* 129 */               maxChar = Math.max(maxChar, def.id);
/* 130 */               charDefs.add(def);
/*     */             } 
/*     */           }  } 
/* 133 */         if (line.startsWith("kernings c"))
/*     */           continue; 
/* 135 */         if (line.startsWith("kerning")) {
/* 136 */           StringTokenizer tokens = new StringTokenizer(line, " =");
/* 137 */           tokens.nextToken();
/* 138 */           tokens.nextToken();
/* 139 */           short first = Short.parseShort(tokens.nextToken());
/* 140 */           tokens.nextToken();
/* 141 */           int second = Integer.parseInt(tokens.nextToken());
/* 142 */           tokens.nextToken();
/* 143 */           int offset = Integer.parseInt(tokens.nextToken());
/* 144 */           List<Short> values = (List)kerning.get(new Short(first));
/* 145 */           if (values == null) {
/* 146 */             values = new ArrayList();
/* 147 */             kerning.put(new Short(first), values);
/*     */           } 
/*     */           
/* 150 */           values.add(new Short((short)(offset << 8 | second)));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 155 */       this.chars = new CharDef[maxChar + 1];
/* 156 */       for (Iterator<CharDef> iterator = charDefs.iterator(); iterator.hasNext(); ) {
/* 157 */         CharDef def = iterator.next();
/* 158 */         this.chars[def.id] = def;
/*     */       } 
/*     */ 
/*     */       
/* 162 */       for (Iterator<Map.Entry> iter = kerning.entrySet().iterator(); iter.hasNext(); ) {
/* 163 */         Map.Entry entry = iter.next();
/* 164 */         short first = ((Short)entry.getKey()).shortValue();
/* 165 */         List valueList = (List)entry.getValue();
/* 166 */         short[] valueArray = new short[valueList.size()];
/* 167 */         int i = 0;
/* 168 */         for (Iterator valueIter = valueList.iterator(); valueIter.hasNext(); i++)
/* 169 */           valueArray[i] = ((Short)valueIter.next()).shortValue(); 
/* 170 */         (this.chars[first]).kerning = valueArray;
/*     */       } 
/* 172 */     } catch (IOException e) {
/* 173 */       Log.error(e);
/* 174 */       throw new SlickException("Failed to parse font file: " + fntFile);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private CharDef parseChar(String line) throws SlickException {
/* 180 */     CharDef def = new CharDef();
/* 181 */     StringTokenizer tokens = new StringTokenizer(line, " =");
/*     */     
/* 183 */     tokens.nextToken();
/* 184 */     tokens.nextToken();
/* 185 */     def.id = Short.parseShort(tokens.nextToken());
/* 186 */     if (def.id < 0) {
/* 187 */       return null;
/*     */     }
/* 189 */     if (def.id > 255) {
/* 190 */       throw new SlickException("Invalid character '" + def.id + "': AngelCodeFont does not support characters above " + 'ÿ');
/*     */     }
/*     */ 
/*     */     
/* 194 */     tokens.nextToken();
/* 195 */     def.x = Short.parseShort(tokens.nextToken());
/* 196 */     tokens.nextToken();
/* 197 */     def.y = Short.parseShort(tokens.nextToken());
/* 198 */     tokens.nextToken();
/* 199 */     def.width = Short.parseShort(tokens.nextToken());
/* 200 */     tokens.nextToken();
/* 201 */     def.height = Short.parseShort(tokens.nextToken());
/* 202 */     tokens.nextToken();
/* 203 */     def.xoffset = Short.parseShort(tokens.nextToken());
/* 204 */     tokens.nextToken();
/* 205 */     def.yoffset = Short.parseShort(tokens.nextToken());
/* 206 */     tokens.nextToken();
/* 207 */     def.xadvance = Short.parseShort(tokens.nextToken());
/*     */     
/* 209 */     def.init();
/*     */     
/* 211 */     if (def.id != 32) {
/* 212 */       this.lineHeight = Math.max(def.height + def.yoffset, this.lineHeight);
/*     */     }
/*     */     
/* 215 */     return def;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text) {
/* 220 */     drawString(x, y, text, Color.white);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text, Color col) {
/* 225 */     drawString(x, y, text, col, 0, text.length() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text, Color col, int startIndex, int endIndex) {
/* 231 */     this.fontImage.bind();
/* 232 */     col.bind();
/*     */     
/* 234 */     GL.glTranslatef(x, y, 0.0F);
/* 235 */     if (this.displayListCaching && startIndex == 0 && endIndex == text.length() - 1) {
/* 236 */       DisplayList displayList = (DisplayList)this.displayLists.get(text);
/* 237 */       if (displayList != null) {
/* 238 */         GL.glCallList(displayList.id);
/*     */       } else {
/*     */         
/* 241 */         displayList = new DisplayList();
/* 242 */         displayList.text = text;
/* 243 */         int displayListCount = this.displayLists.size();
/* 244 */         if (displayListCount < 200) {
/* 245 */           displayList.id = this.baseDisplayListID + displayListCount;
/*     */         } else {
/* 247 */           displayList.id = this.eldestDisplayListID;
/* 248 */           this.displayLists.remove(this.eldestDisplayList.text);
/*     */         } 
/*     */         
/* 251 */         this.displayLists.put(text, displayList);
/*     */         
/* 253 */         GL.glNewList(displayList.id, 4865);
/* 254 */         render(text, startIndex, endIndex);
/* 255 */         GL.glEndList();
/*     */       } 
/*     */     } else {
/* 258 */       render(text, startIndex, endIndex);
/*     */     } 
/* 260 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void render(String text, int start, int end) {
/* 265 */     GL.glBegin(7);
/*     */     
/* 267 */     int x = 0, y = 0;
/* 268 */     CharDef lastCharDef = null;
/* 269 */     char[] data = text.toCharArray();
/* 270 */     for (int i = 0; i < data.length; i++) {
/* 271 */       int id = data[i];
/* 272 */       if (id == 10) {
/* 273 */         x = 0;
/* 274 */         y += this.lineHeight;
/*     */       
/*     */       }
/* 277 */       else if (id < this.chars.length) {
/*     */ 
/*     */         
/* 280 */         CharDef charDef = this.chars[id];
/* 281 */         if (charDef != null)
/*     */         
/*     */         { 
/*     */           
/* 285 */           if (lastCharDef != null) x += lastCharDef.getKerning(id); 
/* 286 */           lastCharDef = charDef;
/*     */           
/* 288 */           if (i >= start && i <= end) {
/* 289 */             charDef.draw(x, y);
/*     */           }
/*     */           
/* 292 */           x += charDef.xadvance; } 
/*     */       } 
/* 294 */     }  GL.glEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYOffset(String text) {
/* 299 */     DisplayList displayList = null;
/* 300 */     if (this.displayListCaching) {
/* 301 */       displayList = (DisplayList)this.displayLists.get(text);
/* 302 */       if (displayList != null && displayList.yOffset != null) return displayList.yOffset.intValue();
/*     */     
/*     */     } 
/* 305 */     int stopIndex = text.indexOf('\n');
/* 306 */     if (stopIndex == -1) stopIndex = text.length();
/*     */     
/* 308 */     int minYOffset = 10000;
/* 309 */     for (int i = 0; i < stopIndex; i++) {
/* 310 */       int id = text.charAt(i);
/* 311 */       CharDef charDef = this.chars[id];
/* 312 */       if (charDef != null)
/*     */       {
/*     */         
/* 315 */         minYOffset = Math.min(charDef.yoffset, minYOffset);
/*     */       }
/*     */     } 
/* 318 */     if (displayList != null) displayList.yOffset = new Short((short)minYOffset);
/*     */     
/* 320 */     return minYOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight(String text) {
/* 325 */     DisplayList displayList = null;
/* 326 */     if (this.displayListCaching) {
/* 327 */       displayList = (DisplayList)this.displayLists.get(text);
/* 328 */       if (displayList != null && displayList.height != null) return displayList.height.intValue();
/*     */     
/*     */     } 
/* 331 */     int lines = 0;
/* 332 */     int maxHeight = 0;
/* 333 */     for (int i = 0; i < text.length(); i++) {
/* 334 */       int id = text.charAt(i);
/* 335 */       if (id == 10) {
/* 336 */         lines++;
/* 337 */         maxHeight = 0;
/*     */ 
/*     */       
/*     */       }
/* 341 */       else if (id != 32) {
/*     */ 
/*     */         
/* 344 */         CharDef charDef = this.chars[id];
/* 345 */         if (charDef != null)
/*     */         {
/*     */ 
/*     */           
/* 349 */           maxHeight = Math.max(charDef.height + charDef.yoffset, maxHeight);
/*     */         }
/*     */       } 
/*     */     } 
/* 353 */     maxHeight += lines * this.lineHeight;
/*     */     
/* 355 */     if (displayList != null) displayList.height = new Short((short)maxHeight);
/*     */     
/* 357 */     return maxHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth(String text) {
/* 362 */     DisplayList displayList = null;
/* 363 */     if (this.displayListCaching) {
/* 364 */       displayList = (DisplayList)this.displayLists.get(text);
/* 365 */       if (displayList != null && displayList.width != null) return displayList.width.intValue();
/*     */     
/*     */     } 
/* 368 */     int maxWidth = 0;
/* 369 */     int width = 0;
/* 370 */     CharDef lastCharDef = null;
/* 371 */     for (int i = 0, n = text.length(); i < n; i++) {
/* 372 */       int id = text.charAt(i);
/* 373 */       if (id == 10) {
/* 374 */         width = 0;
/*     */       
/*     */       }
/* 377 */       else if (id < this.chars.length) {
/*     */ 
/*     */         
/* 380 */         CharDef charDef = this.chars[id];
/* 381 */         if (charDef != null) {
/*     */ 
/*     */ 
/*     */           
/* 385 */           if (lastCharDef != null) width += lastCharDef.getKerning(id); 
/* 386 */           lastCharDef = charDef;
/*     */           
/* 388 */           if (i < n - 1) {
/* 389 */             width += charDef.xadvance;
/*     */           } else {
/* 391 */             width += charDef.width;
/*     */           } 
/* 393 */           maxWidth = Math.max(maxWidth, width);
/*     */         } 
/*     */       } 
/* 396 */     }  if (displayList != null) displayList.width = new Short((short)maxWidth);
/*     */     
/* 398 */     return maxWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class CharDef
/*     */   {
/*     */     public short id;
/*     */ 
/*     */     
/*     */     public short x;
/*     */ 
/*     */     
/*     */     public short y;
/*     */ 
/*     */     
/*     */     public short width;
/*     */     
/*     */     public short height;
/*     */     
/*     */     public short xoffset;
/*     */     
/*     */     public short yoffset;
/*     */     
/*     */     public short xadvance;
/*     */     
/*     */     public Image image;
/*     */     
/*     */     public short dlIndex;
/*     */     
/*     */     public short[] kerning;
/*     */ 
/*     */     
/*     */     public void init() {
/* 432 */       this.image = AngelCodeFont.this.fontImage.getSubImage(this.x, this.y, this.width, this.height);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 437 */       return "[CharDef id=" + this.id + " x=" + this.x + " y=" + this.y + "]";
/*     */     }
/*     */ 
/*     */     
/*     */     public void draw(float x, float y) {
/* 442 */       this.image.drawEmbedded(x + this.xoffset, y + this.yoffset, this.width, this.height);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getKerning(int otherCodePoint) {
/* 447 */       if (this.kerning == null) return 0; 
/* 448 */       int low = 0;
/* 449 */       int high = this.kerning.length - 1;
/* 450 */       while (low <= high) {
/* 451 */         int midIndex = low + high >>> 1;
/* 452 */         int value = this.kerning[midIndex];
/* 453 */         int foundCodePoint = value & 0xFF;
/* 454 */         if (foundCodePoint < otherCodePoint) {
/* 455 */           low = midIndex + 1; continue;
/* 456 */         }  if (foundCodePoint > otherCodePoint) {
/* 457 */           high = midIndex - 1; continue;
/*     */         } 
/* 459 */         return value >> 8;
/*     */       } 
/* 461 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLineHeight() {
/* 467 */     return this.lineHeight;
/*     */   }
/*     */   
/*     */   private static class DisplayList {
/*     */     int id;
/*     */     Short yOffset;
/*     */     Short width;
/*     */     Short height;
/*     */     String text;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\AngelCodeFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */