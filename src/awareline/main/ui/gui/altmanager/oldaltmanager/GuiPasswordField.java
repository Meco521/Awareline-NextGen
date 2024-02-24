/*     */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*     */ 
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public final class GuiPasswordField
/*     */   extends Gui {
/*  12 */   private static final Pattern COMPILE = Pattern.compile("\\.");
/*  13 */   private static final Pattern PATTERN = Pattern.compile("\\.");
/*     */   private final int field_146209_f;
/*     */   private final int field_146210_g;
/*     */   private final FontRenderer field_146211_a;
/*     */   private final boolean field_146212_n;
/*     */   private boolean field_146213_o;
/*     */   private int field_146214_l;
/*     */   private final boolean field_146215_m;
/*     */   private String field_146216_j;
/*     */   private int field_146217_k;
/*     */   private final int field_146218_h;
/*     */   private final int field_146219_i;
/*     */   private final boolean field_146220_v;
/*     */   private final int field_146221_u;
/*     */   private final int field_146222_t;
/*     */   private int field_146223_s;
/*     */   private int field_146224_r;
/*     */   private int field_146225_q;
/*     */   private final boolean field_146226_p;
/*     */   
/*     */   public GuiPasswordField(FontRenderer p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_) {
/*  34 */     this.field_146212_n = true;
/*  35 */     this.field_146215_m = true;
/*  36 */     this.field_146216_j = "";
/*  37 */     this.field_146217_k = 32;
/*  38 */     this.field_146220_v = true;
/*  39 */     this.field_146221_u = 7368816;
/*  40 */     this.field_146222_t = 14737632;
/*  41 */     this.field_146226_p = true;
/*  42 */     this.field_146211_a = p_i1032_1_;
/*  43 */     this.field_146209_f = p_i1032_2_;
/*  44 */     this.field_146210_g = p_i1032_3_;
/*  45 */     this.field_146218_h = p_i1032_4_;
/*  46 */     this.field_146219_i = p_i1032_5_;
/*     */   }
/*     */   
/*     */   public void drawTextBox() {
/*  50 */     if (func_146176_q()) {
/*  51 */       if (func_146181_i()) {
/*  52 */         Gui.drawRect(this.field_146209_f - 1, this.field_146210_g - 1, this.field_146209_f + this.field_146218_h + 1, this.field_146210_g + this.field_146219_i + 1, -6250336);
/*  53 */         Gui.drawRect(this.field_146209_f, this.field_146210_g, this.field_146209_f + this.field_146218_h, this.field_146210_g + this.field_146219_i, -16777216);
/*     */       } 
/*  55 */       int var1 = this.field_146226_p ? this.field_146222_t : this.field_146221_u;
/*  56 */       int var2 = this.field_146224_r - this.field_146225_q;
/*  57 */       int var3 = this.field_146223_s - this.field_146225_q;
/*  58 */       String var4 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/*  59 */       boolean var5 = (var2 >= 0 && var2 <= var4.length());
/*  60 */       boolean var6 = (this.field_146213_o && this.field_146214_l / 6 % 2 == 0 && var5);
/*  61 */       int var7 = this.field_146215_m ? (this.field_146209_f + 4) : this.field_146209_f;
/*  62 */       int var8 = this.field_146215_m ? (this.field_146210_g + (this.field_146219_i - 8) / 2) : this.field_146210_g;
/*  63 */       int var9 = var7;
/*  64 */       if (var3 > var4.length()) {
/*  65 */         var3 = var4.length();
/*     */       }
/*  67 */       if (!var4.isEmpty()) {
/*  68 */         String var10 = var5 ? var4.substring(0, var2) : var4;
/*  69 */         var9 = this.field_146211_a.drawString(COMPILE.matcher(var10).replaceAll("*"), var7, var8, var1);
/*     */       } 
/*  71 */       boolean var11 = (this.field_146224_r < this.field_146216_j.length() || this.field_146216_j.length() >= func_146208_g());
/*  72 */       int var12 = var9;
/*  73 */       if (!var5) {
/*  74 */         var12 = (var2 > 0) ? (var7 + this.field_146218_h) : var7;
/*  75 */       } else if (var11) {
/*  76 */         var12 = var9 - 1;
/*  77 */         var9--;
/*     */       } 
/*  79 */       if (!var4.isEmpty() && var5 && var2 < var4.length()) {
/*  80 */         this.field_146211_a.drawString(PATTERN.matcher(var4.substring(var2)).replaceAll("*"), var9, var8, var1);
/*     */       }
/*  82 */       if (var6) {
/*  83 */         if (var11) {
/*  84 */           Gui.drawRect(var12, var8 - 1, var12 + 1, var8 + 1 + this.field_146211_a.FONT_HEIGHT, -3092272);
/*     */         } else {
/*  86 */           this.field_146211_a.drawString("_", var12, var8, var1);
/*     */         } 
/*     */       }
/*  89 */       if (var3 != var2) {
/*  90 */         func_146188_c();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_146175_b(int p_146175_1_) {
/*  96 */     if (!this.field_146216_j.isEmpty()) {
/*  97 */       if (this.field_146223_s != this.field_146224_r) {
/*  98 */         func_146191_b("");
/*     */       } else {
/* 100 */         boolean var2 = (p_146175_1_ < 0);
/* 101 */         int var3 = var2 ? (this.field_146224_r + p_146175_1_) : this.field_146224_r;
/* 102 */         int var4 = var2 ? this.field_146224_r : (this.field_146224_r + p_146175_1_);
/* 103 */         String var5 = "";
/* 104 */         if (var3 >= 0) {
/* 105 */           var5 = this.field_146216_j.substring(0, var3);
/*     */         }
/* 107 */         if (var4 < this.field_146216_j.length()) {
/* 108 */           var5 = var5 + this.field_146216_j.substring(var4);
/*     */         }
/* 110 */         this.field_146216_j = var5;
/* 111 */         if (var2) {
/* 112 */           func_146182_d(p_146175_1_);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean func_146176_q() {
/* 119 */     return this.field_146220_v;
/*     */   }
/*     */   
/*     */   public void func_146177_a(int p_146177_1_) {
/* 123 */     if (!this.field_146216_j.isEmpty()) {
/* 124 */       if (this.field_146223_s != this.field_146224_r) {
/* 125 */         func_146191_b("");
/*     */       } else {
/* 127 */         func_146175_b(func_146187_c(p_146177_1_) - this.field_146224_r);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean func_146181_i() {
/* 133 */     return this.field_146215_m;
/*     */   }
/*     */   
/*     */   public void func_146182_d(int p_146182_1_) {
/* 137 */     func_146190_e(this.field_146223_s + p_146182_1_);
/*     */   }
/*     */   
/*     */   public int func_146183_a(int p_146183_1_) {
/* 141 */     return func_146197_a(p_146183_1_, func_146198_h(), true);
/*     */   }
/*     */   
/*     */   public int func_146186_n() {
/* 145 */     return this.field_146223_s;
/*     */   }
/*     */   
/*     */   public int func_146187_c(int p_146187_1_) {
/* 149 */     return func_146183_a(p_146187_1_);
/*     */   }
/*     */   
/*     */   private static void func_146188_c() {
/* 153 */     GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
/* 154 */     GL11.glDisable(3553);
/* 155 */     GL11.glEnable(3058);
/* 156 */     GL11.glLogicOp(5387);
/* 157 */     GL11.glDisable(3058);
/* 158 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   public void func_146190_e(int p_146190_1_) {
/* 162 */     this.field_146224_r = p_146190_1_;
/* 163 */     int var2 = this.field_146216_j.length();
/* 164 */     if (this.field_146224_r < 0) {
/* 165 */       this.field_146224_r = 0;
/*     */     }
/* 167 */     if (this.field_146224_r > var2) {
/* 168 */       this.field_146224_r = var2;
/*     */     }
/* 170 */     func_146199_i(this.field_146224_r);
/*     */   }
/*     */   public void func_146191_b(String p_146191_1_) {
/*     */     int var7;
/* 174 */     String var2 = "";
/* 175 */     String var3 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
/* 176 */     int var4 = Math.min(this.field_146224_r, this.field_146223_s);
/* 177 */     int var5 = Math.max(this.field_146224_r, this.field_146223_s);
/* 178 */     int var6 = this.field_146217_k - this.field_146216_j.length() - var4 - this.field_146223_s;
/* 179 */     if (!this.field_146216_j.isEmpty()) {
/* 180 */       var2 = var2 + this.field_146216_j.substring(0, var4);
/*     */     }
/*     */     
/* 183 */     if (var6 < var3.length()) {
/* 184 */       var2 = var2 + var3.substring(0, var6);
/* 185 */       var7 = var6;
/*     */     } else {
/* 187 */       var2 = var2 + var3;
/* 188 */       var7 = var3.length();
/*     */     } 
/* 190 */     if (!this.field_146216_j.isEmpty() && var5 < this.field_146216_j.length()) {
/* 191 */       var2 = var2 + this.field_146216_j.substring(var5);
/*     */     }
/* 193 */     this.field_146216_j = var2;
/* 194 */     func_146182_d(var4 - this.field_146223_s + var7);
/*     */   }
/*     */   
/*     */   public void func_146196_d() {
/* 198 */     func_146190_e(0);
/*     */   }
/*     */   
/*     */   public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
/* 202 */     int var4 = p_146197_2_;
/* 203 */     boolean var5 = (p_146197_1_ < 0);
/* 204 */     for (int var6 = Math.abs(p_146197_1_), var7 = 0; var7 < var6; var7++) {
/* 205 */       if (var5) {
/*     */         do {
/* 207 */           var4--;
/* 208 */         } while (!p_146197_3_ || var4 <= 0 || this.field_146216_j.charAt(var4 - 1) == ' '); do {  }
/* 209 */         while (--var4 > 0 && 
/* 210 */           this.field_146216_j.charAt(var4 - 1) != ' ');
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 215 */         int var8 = this.field_146216_j.length();
/* 216 */         var4 = this.field_146216_j.indexOf(' ', var4);
/* 217 */         if (var4 == -1) {
/* 218 */           var4 = var8;
/*     */         } else {
/* 220 */           while (p_146197_3_ && var4 < var8 && this.field_146216_j.charAt(var4) == ' ') {
/* 221 */             var4++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 226 */     return var4;
/*     */   }
/*     */   
/*     */   public int func_146198_h() {
/* 230 */     return this.field_146224_r;
/*     */   }
/*     */   
/*     */   public void func_146199_i(int p_146199_1_) {
/* 234 */     int var2 = this.field_146216_j.length();
/* 235 */     if (p_146199_1_ > var2) {
/* 236 */       p_146199_1_ = var2;
/*     */     }
/* 238 */     if (p_146199_1_ < 0) {
/* 239 */       p_146199_1_ = 0;
/*     */     }
/* 241 */     this.field_146223_s = p_146199_1_;
/* 242 */     if (this.field_146211_a != null) {
/* 243 */       if (this.field_146225_q > var2) {
/* 244 */         this.field_146225_q = var2;
/*     */       }
/* 246 */       int var3 = func_146200_o();
/* 247 */       String var4 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), var3);
/* 248 */       int var5 = var4.length() + this.field_146225_q;
/* 249 */       if (p_146199_1_ == this.field_146225_q) {
/* 250 */         this.field_146225_q -= this.field_146211_a.trimStringToWidth(this.field_146216_j, var3, true).length();
/*     */       }
/* 252 */       if (p_146199_1_ > var5) {
/* 253 */         this.field_146225_q += p_146199_1_ - var5;
/* 254 */       } else if (p_146199_1_ <= this.field_146225_q) {
/* 255 */         this.field_146225_q -= this.field_146225_q - p_146199_1_;
/*     */       } 
/* 257 */       if (this.field_146225_q < 0) {
/* 258 */         this.field_146225_q = 0;
/*     */       }
/* 260 */       if (this.field_146225_q > var2) {
/* 261 */         this.field_146225_q = var2;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int func_146200_o() {
/* 267 */     return func_146181_i() ? (this.field_146218_h - 8) : this.field_146218_h;
/*     */   }
/*     */   
/*     */   public void func_146202_e() {
/* 271 */     func_146190_e(this.field_146216_j.length());
/*     */   }
/*     */   
/*     */   public void func_146203_f(int p_146203_1_) {
/* 275 */     this.field_146217_k = p_146203_1_;
/* 276 */     if (this.field_146216_j.length() > p_146203_1_) {
/* 277 */       this.field_146216_j = this.field_146216_j.substring(0, p_146203_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   public String func_146207_c() {
/* 282 */     int var1 = Math.min(this.field_146224_r, this.field_146223_s);
/* 283 */     int var2 = Math.max(this.field_146224_r, this.field_146223_s);
/* 284 */     return this.field_146216_j.substring(var1, var2);
/*     */   }
/*     */   
/*     */   public int func_146208_g() {
/* 288 */     return this.field_146217_k;
/*     */   }
/*     */   
/*     */   public String getText() {
/* 292 */     return this.field_146216_j;
/*     */   }
/*     */   
/*     */   public boolean isFocused() {
/* 296 */     return this.field_146213_o;
/*     */   }
/*     */   
/*     */   public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
/* 300 */     boolean var4 = (p_146192_1_ >= this.field_146209_f && p_146192_1_ < this.field_146209_f + this.field_146218_h && p_146192_2_ >= this.field_146210_g && p_146192_2_ < this.field_146210_g + this.field_146219_i);
/* 301 */     if (this.field_146212_n) {
/* 302 */       setFocused(var4);
/*     */     }
/* 304 */     if (this.field_146213_o && p_146192_3_ == 0) {
/* 305 */       int var5 = p_146192_1_ - this.field_146209_f;
/* 306 */       if (this.field_146215_m) {
/* 307 */         var5 -= 4;
/*     */       }
/* 309 */       String var6 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/* 310 */       func_146190_e(this.field_146211_a.trimStringToWidth(var6, var5).length() + this.field_146225_q);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFocused(boolean p_146195_1_) {
/* 315 */     if (p_146195_1_ && !this.field_146213_o) {
/* 316 */       this.field_146214_l = 0;
/*     */     }
/* 318 */     this.field_146213_o = p_146195_1_;
/*     */   }
/*     */   
/*     */   public void setText(String p_146180_1_) {
/* 322 */     if (p_146180_1_.length() > this.field_146217_k) {
/* 323 */       this.field_146216_j = p_146180_1_.substring(0, this.field_146217_k);
/*     */     } else {
/* 325 */       this.field_146216_j = p_146180_1_;
/*     */     } 
/* 327 */     func_146202_e();
/*     */   }
/*     */   
/*     */   public void textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
/* 331 */     if (!this.field_146213_o) {
/*     */       return;
/*     */     }
/* 334 */     switch (p_146201_1_) {
/*     */       case '\001':
/* 336 */         func_146202_e();
/* 337 */         func_146199_i(0);
/*     */         return;
/*     */       case '\003':
/* 340 */         GuiScreen.setClipboardString(func_146207_c());
/*     */         return;
/*     */       case '\026':
/* 343 */         if (this.field_146226_p) {
/* 344 */           func_146191_b(GuiScreen.getClipboardString());
/*     */         }
/*     */         return;
/*     */       case '\030':
/* 348 */         GuiScreen.setClipboardString(func_146207_c());
/* 349 */         if (this.field_146226_p) {
/* 350 */           func_146191_b("");
/*     */         }
/*     */         return;
/*     */     } 
/* 354 */     switch (p_146201_2_) {
/*     */       case 14:
/* 356 */         if (GuiScreen.isCtrlKeyDown()) {
/* 357 */           if (this.field_146226_p) {
/* 358 */             func_146177_a(-1);
/*     */           }
/* 360 */         } else if (this.field_146226_p) {
/* 361 */           func_146175_b(-1);
/*     */         } 
/*     */         return;
/*     */       case 199:
/* 365 */         if (GuiScreen.isShiftKeyDown()) {
/* 366 */           func_146199_i(0);
/*     */         } else {
/* 368 */           func_146196_d();
/*     */         } 
/*     */         return;
/*     */       case 203:
/* 372 */         if (GuiScreen.isShiftKeyDown()) {
/* 373 */           if (GuiScreen.isCtrlKeyDown()) {
/* 374 */             func_146199_i(func_146183_a(-1));
/*     */           } else {
/* 376 */             func_146199_i(func_146186_n() - 1);
/*     */           } 
/* 378 */         } else if (GuiScreen.isCtrlKeyDown()) {
/* 379 */           func_146190_e(func_146187_c(-1));
/*     */         } else {
/* 381 */           func_146182_d(-1);
/*     */         } 
/*     */         return;
/*     */       case 205:
/* 385 */         if (GuiScreen.isShiftKeyDown()) {
/* 386 */           if (GuiScreen.isCtrlKeyDown()) {
/* 387 */             func_146199_i(func_146183_a(1));
/*     */           } else {
/* 389 */             func_146199_i(func_146186_n() + 1);
/*     */           } 
/* 391 */         } else if (GuiScreen.isCtrlKeyDown()) {
/* 392 */           func_146190_e(func_146187_c(1));
/*     */         } else {
/* 394 */           func_146182_d(1);
/*     */         } 
/*     */         return;
/*     */       case 207:
/* 398 */         if (GuiScreen.isShiftKeyDown()) {
/* 399 */           func_146199_i(this.field_146216_j.length());
/*     */         } else {
/* 401 */           func_146202_e();
/*     */         } 
/*     */         return;
/*     */       case 211:
/* 405 */         if (GuiScreen.isCtrlKeyDown()) {
/* 406 */           if (this.field_146226_p) {
/* 407 */             func_146177_a(1);
/*     */           }
/* 409 */         } else if (this.field_146226_p) {
/* 410 */           func_146175_b(1);
/*     */         } 
/*     */         return;
/*     */     } 
/* 414 */     if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_) && 
/* 415 */       this.field_146226_p) {
/* 416 */       func_146191_b(Character.toString(p_146201_1_));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCursorCounter() {
/* 424 */     this.field_146214_l++;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\GuiPasswordField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */