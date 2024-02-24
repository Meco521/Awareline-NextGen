/*     */ package awareline.main.ui.gui.altmanager.oldaltmanager;
/*     */ 
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ 
/*     */ public final class GuiUserField extends Gui {
/*     */   private final int field_146209_f;
/*     */   private final int field_146210_g;
/*     */   private final FontRenderer field_146211_a;
/*     */   private final boolean field_146212_n;
/*     */   private boolean field_146213_o;
/*     */   private final boolean field_146215_m;
/*     */   private String field_146216_j;
/*     */   private final int field_146217_k;
/*     */   private final int field_146218_h;
/*     */   private final int field_146219_i;
/*     */   private int field_146223_s;
/*     */   private int field_146224_r;
/*     */   private int field_146225_q;
/*     */   private final boolean field_146226_p;
/*     */   
/*     */   public GuiUserField(FontRenderer p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_) {
/*  25 */     this.field_146212_n = true;
/*  26 */     this.field_146215_m = true;
/*  27 */     this.field_146216_j = "";
/*  28 */     this.field_146217_k = 32;
/*  29 */     this.field_146226_p = true;
/*  30 */     this.field_146211_a = p_i1032_1_;
/*  31 */     this.field_146209_f = p_i1032_2_;
/*  32 */     this.field_146210_g = p_i1032_3_;
/*  33 */     this.field_146218_h = p_i1032_4_;
/*  34 */     this.field_146219_i = p_i1032_5_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146175_b(int p_146175_1_) {
/*  39 */     if (!this.field_146216_j.isEmpty()) {
/*  40 */       if (this.field_146223_s != this.field_146224_r) {
/*  41 */         func_146191_b("");
/*     */       } else {
/*  43 */         boolean var2 = (p_146175_1_ < 0);
/*  44 */         int var3 = var2 ? (this.field_146224_r + p_146175_1_) : this.field_146224_r;
/*  45 */         int var4 = var2 ? this.field_146224_r : (this.field_146224_r + p_146175_1_);
/*  46 */         String var5 = "";
/*  47 */         if (var3 >= 0) {
/*  48 */           var5 = this.field_146216_j.substring(0, var3);
/*     */         }
/*  50 */         if (var4 < this.field_146216_j.length()) {
/*  51 */           var5 = var5 + this.field_146216_j.substring(var4);
/*     */         }
/*  53 */         this.field_146216_j = var5;
/*  54 */         if (var2) {
/*  55 */           func_146182_d(p_146175_1_);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_146177_a(int p_146177_1_) {
/*  62 */     if (!this.field_146216_j.isEmpty()) {
/*  63 */       if (this.field_146223_s != this.field_146224_r) {
/*  64 */         func_146191_b("");
/*     */       } else {
/*  66 */         func_146175_b(func_146187_c(p_146177_1_) - this.field_146224_r);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean func_146181_i() {
/*  72 */     return this.field_146215_m;
/*     */   }
/*     */   
/*     */   public void func_146182_d(int p_146182_1_) {
/*  76 */     func_146190_e(this.field_146223_s + p_146182_1_);
/*     */   }
/*     */   
/*     */   public int func_146183_a(int p_146183_1_) {
/*  80 */     return func_146197_a(p_146183_1_, func_146198_h(), true);
/*     */   }
/*     */   
/*     */   public int func_146186_n() {
/*  84 */     return this.field_146223_s;
/*     */   }
/*     */   
/*     */   public int func_146187_c(int p_146187_1_) {
/*  88 */     return func_146183_a(p_146187_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146190_e(int p_146190_1_) {
/*  93 */     this.field_146224_r = p_146190_1_;
/*  94 */     int var2 = this.field_146216_j.length();
/*  95 */     if (this.field_146224_r < 0) {
/*  96 */       this.field_146224_r = 0;
/*     */     }
/*  98 */     if (this.field_146224_r > var2) {
/*  99 */       this.field_146224_r = var2;
/*     */     }
/* 101 */     func_146199_i(this.field_146224_r);
/*     */   }
/*     */   public void func_146191_b(String p_146191_1_) {
/*     */     int var7;
/* 105 */     String var2 = "";
/* 106 */     String var3 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
/* 107 */     int var4 = Math.min(this.field_146224_r, this.field_146223_s);
/* 108 */     int var5 = Math.max(this.field_146224_r, this.field_146223_s);
/* 109 */     int var6 = this.field_146217_k - this.field_146216_j.length() - var4 - this.field_146223_s;
/* 110 */     if (!this.field_146216_j.isEmpty()) {
/* 111 */       var2 = var2 + this.field_146216_j.substring(0, var4);
/*     */     }
/*     */     
/* 114 */     if (var6 < var3.length()) {
/* 115 */       var2 = var2 + var3.substring(0, var6);
/* 116 */       var7 = var6;
/*     */     } else {
/* 118 */       var2 = var2 + var3;
/* 119 */       var7 = var3.length();
/*     */     } 
/* 121 */     if (!this.field_146216_j.isEmpty() && var5 < this.field_146216_j.length()) {
/* 122 */       var2 = var2 + this.field_146216_j.substring(var5);
/*     */     }
/* 124 */     this.field_146216_j = var2;
/* 125 */     func_146182_d(var4 - this.field_146223_s + var7);
/*     */   }
/*     */   
/*     */   public void func_146196_d() {
/* 129 */     func_146190_e(0);
/*     */   }
/*     */   
/*     */   public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
/* 133 */     int var4 = p_146197_2_;
/* 134 */     boolean var5 = (p_146197_1_ < 0);
/* 135 */     for (int var6 = Math.abs(p_146197_1_), var7 = 0; var7 < var6; var7++) {
/* 136 */       if (var5) {
/*     */         do {
/* 138 */           var4--;
/* 139 */         } while (!p_146197_3_ || var4 <= 0 || this.field_146216_j.charAt(var4 - 1) == ' '); do {  }
/* 140 */         while (--var4 > 0 && 
/* 141 */           this.field_146216_j.charAt(var4 - 1) != ' ');
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 146 */         int var8 = this.field_146216_j.length();
/* 147 */         var4 = this.field_146216_j.indexOf(' ', var4);
/* 148 */         if (var4 == -1) {
/* 149 */           var4 = var8;
/*     */         } else {
/* 151 */           while (p_146197_3_ && var4 < var8 && this.field_146216_j.charAt(var4) == ' ') {
/* 152 */             var4++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 157 */     return var4;
/*     */   }
/*     */   
/*     */   public int func_146198_h() {
/* 161 */     return this.field_146224_r;
/*     */   }
/*     */   
/*     */   public void func_146199_i(int p_146199_1_) {
/* 165 */     int var2 = this.field_146216_j.length();
/* 166 */     if (p_146199_1_ > var2) {
/* 167 */       p_146199_1_ = var2;
/*     */     }
/* 169 */     if (p_146199_1_ < 0) {
/* 170 */       p_146199_1_ = 0;
/*     */     }
/* 172 */     this.field_146223_s = p_146199_1_;
/* 173 */     if (this.field_146211_a != null) {
/* 174 */       if (this.field_146225_q > var2) {
/* 175 */         this.field_146225_q = var2;
/*     */       }
/* 177 */       int var3 = func_146200_o();
/* 178 */       String var4 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), var3);
/* 179 */       int var5 = var4.length() + this.field_146225_q;
/* 180 */       if (p_146199_1_ == this.field_146225_q) {
/* 181 */         this.field_146225_q -= this.field_146211_a.trimStringToWidth(this.field_146216_j, var3, true).length();
/*     */       }
/* 183 */       if (p_146199_1_ > var5) {
/* 184 */         this.field_146225_q += p_146199_1_ - var5;
/* 185 */       } else if (p_146199_1_ <= this.field_146225_q) {
/* 186 */         this.field_146225_q -= this.field_146225_q - p_146199_1_;
/*     */       } 
/* 188 */       if (this.field_146225_q < 0) {
/* 189 */         this.field_146225_q = 0;
/*     */       }
/* 191 */       if (this.field_146225_q > var2) {
/* 192 */         this.field_146225_q = var2;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int func_146200_o() {
/* 198 */     return func_146181_i() ? (this.field_146218_h - 8) : this.field_146218_h;
/*     */   }
/*     */   
/*     */   public void func_146202_e() {
/* 202 */     func_146190_e(this.field_146216_j.length());
/*     */   }
/*     */   
/*     */   public String func_146207_c() {
/* 206 */     int var1 = Math.min(this.field_146224_r, this.field_146223_s);
/* 207 */     int var2 = Math.max(this.field_146224_r, this.field_146223_s);
/* 208 */     return this.field_146216_j.substring(var1, var2);
/*     */   }
/*     */   
/*     */   public String getText() {
/* 212 */     return this.field_146216_j;
/*     */   }
/*     */   
/*     */   public boolean isFocused() {
/* 216 */     return this.field_146213_o;
/*     */   }
/*     */   
/*     */   public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
/* 220 */     boolean var4 = (p_146192_1_ >= this.field_146209_f && p_146192_1_ < this.field_146209_f + this.field_146218_h && p_146192_2_ >= this.field_146210_g && p_146192_2_ < this.field_146210_g + this.field_146219_i);
/* 221 */     if (this.field_146212_n) {
/* 222 */       this.field_146213_o = var4;
/*     */     }
/* 224 */     if (this.field_146213_o && p_146192_3_ == 0) {
/* 225 */       int var5 = p_146192_1_ - this.field_146209_f;
/* 226 */       if (this.field_146215_m) {
/* 227 */         var5 -= 4;
/*     */       }
/* 229 */       String var6 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/* 230 */       func_146190_e(this.field_146211_a.trimStringToWidth(var6, var5).length() + this.field_146225_q);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFocused(boolean p_146195_1_) {
/* 235 */     this.field_146213_o = p_146195_1_;
/*     */   }
/*     */   
/*     */   public void textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
/* 239 */     if (!this.field_146213_o) {
/*     */       return;
/*     */     }
/* 242 */     switch (p_146201_1_) {
/*     */       case '\001':
/* 244 */         func_146202_e();
/* 245 */         func_146199_i(0);
/*     */         return;
/*     */       case '\003':
/* 248 */         GuiScreen.setClipboardString(func_146207_c());
/*     */         return;
/*     */       case '\026':
/* 251 */         if (this.field_146226_p) {
/* 252 */           func_146191_b(GuiScreen.getClipboardString());
/*     */         }
/*     */         return;
/*     */       case '\030':
/* 256 */         GuiScreen.setClipboardString(func_146207_c());
/* 257 */         if (this.field_146226_p) {
/* 258 */           func_146191_b("");
/*     */         }
/*     */         return;
/*     */     } 
/* 262 */     switch (p_146201_2_) {
/*     */       case 14:
/* 264 */         if (GuiScreen.isCtrlKeyDown()) {
/* 265 */           if (this.field_146226_p) {
/* 266 */             func_146177_a(-1);
/*     */           }
/* 268 */         } else if (this.field_146226_p) {
/* 269 */           func_146175_b(-1);
/*     */         } 
/*     */         return;
/*     */       case 199:
/* 273 */         if (GuiScreen.isShiftKeyDown()) {
/* 274 */           func_146199_i(0);
/*     */         } else {
/* 276 */           func_146196_d();
/*     */         } 
/*     */         return;
/*     */       case 203:
/* 280 */         if (GuiScreen.isShiftKeyDown()) {
/* 281 */           if (GuiScreen.isCtrlKeyDown()) {
/* 282 */             func_146199_i(func_146183_a(-1));
/*     */           } else {
/* 284 */             func_146199_i(func_146186_n() - 1);
/*     */           } 
/* 286 */         } else if (GuiScreen.isCtrlKeyDown()) {
/* 287 */           func_146190_e(func_146187_c(-1));
/*     */         } else {
/* 289 */           func_146182_d(-1);
/*     */         } 
/*     */         return;
/*     */       case 205:
/* 293 */         if (GuiScreen.isShiftKeyDown()) {
/* 294 */           if (GuiScreen.isCtrlKeyDown()) {
/* 295 */             func_146199_i(func_146183_a(1));
/*     */           } else {
/* 297 */             func_146199_i(func_146186_n() + 1);
/*     */           } 
/* 299 */         } else if (GuiScreen.isCtrlKeyDown()) {
/* 300 */           func_146190_e(func_146187_c(1));
/*     */         } else {
/* 302 */           func_146182_d(1);
/*     */         } 
/*     */         return;
/*     */       case 207:
/* 306 */         System.out.println("88888");
/* 307 */         if (GuiScreen.isShiftKeyDown()) {
/* 308 */           func_146199_i(this.field_146216_j.length());
/*     */         } else {
/* 310 */           func_146202_e();
/*     */         } 
/*     */         return;
/*     */       case 211:
/* 314 */         if (GuiScreen.isCtrlKeyDown()) {
/* 315 */           if (this.field_146226_p) {
/* 316 */             func_146177_a(1);
/*     */           }
/* 318 */         } else if (this.field_146226_p) {
/* 319 */           func_146175_b(1);
/*     */         } 
/*     */         return;
/*     */     } 
/* 323 */     if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_) && 
/* 324 */       this.field_146226_p)
/* 325 */       func_146191_b(Character.toString(p_146201_1_)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\altmanager\oldaltmanager\GuiUserField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */