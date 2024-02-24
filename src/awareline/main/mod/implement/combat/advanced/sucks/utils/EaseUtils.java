/*     */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*     */ 
/*     */ 
/*     */ public final class EaseUtils
/*     */ {
/*     */   public static double easeInSine(double x) {
/*   7 */     double d = x * Math.PI / 2.0D;
/*   8 */     double d2 = 1.0D;
/*   9 */     double d3 = Math.cos(d);
/*  10 */     return d2 - d3;
/*     */   }
/*     */   
/*     */   public static double easeOutSine(double x) {
/*  14 */     double d = x * Math.PI / 2.0D;
/*  15 */     return Math.sin(d);
/*     */   }
/*     */   
/*     */   public static double easeInOutSine(double x) {
/*  19 */     double d = Math.PI * x;
/*  20 */     return -(Math.cos(d) - 1.0D) / 2.0D;
/*     */   }
/*     */   
/*     */   public static double easeInQuad(double x) {
/*  24 */     return x * x;
/*     */   }
/*     */   
/*     */   public static double easeOutQuad(double x) {
/*  28 */     return 1.0D - (1.0D - x) * (1.0D - x);
/*     */   }
/*     */   
/*     */   public static double easeInOutQuad(double x) {
/*     */     double d;
/*  33 */     if (x < 0.5D) {
/*  34 */       d = 2.0D * x * x;
/*     */     } else {
/*  36 */       double d2 = -2.0D * x + 2.0D;
/*  37 */       int n = 2;
/*  38 */       double d3 = 1.0D;
/*  39 */       double d4 = Math.pow(d2, n);
/*  40 */       d = d3 - d4 / 2.0D;
/*     */     } 
/*  42 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeInCubic(double x) {
/*  46 */     return x * x * x;
/*     */   }
/*     */   
/*     */   public static double easeOutCubic(double x) {
/*  50 */     double d = 1.0D - x;
/*  51 */     int n = 3;
/*  52 */     double d2 = 1.0D;
/*  53 */     double d3 = Math.pow(d, n);
/*  54 */     return d2 - d3;
/*     */   }
/*     */   
/*     */   public static double easeInOutCubic(double x) {
/*     */     double d;
/*  59 */     if (x < 0.5D) {
/*  60 */       d = 4.0D * x * x * x;
/*     */     } else {
/*  62 */       double d2 = -2.0D * x + 2.0D;
/*  63 */       int n = 3;
/*  64 */       double d3 = 1.0D;
/*  65 */       double d4 = Math.pow(d2, n);
/*  66 */       d = d3 - d4 / 2.0D;
/*     */     } 
/*  68 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeInQuart(double x) {
/*  72 */     return x * x * x * x;
/*     */   }
/*     */   
/*     */   public static double easeOutQuart(double x) {
/*  76 */     double d = 1.0D - x;
/*  77 */     int n = 4;
/*  78 */     double d2 = 1.0D;
/*  79 */     double d3 = Math.pow(d, n);
/*  80 */     return d2 - d3;
/*     */   }
/*     */   
/*     */   public static double easeInOutQuart(double x) {
/*     */     double d;
/*  85 */     if (x < 0.5D) {
/*  86 */       d = 8.0D * x * x * x * x;
/*     */     } else {
/*  88 */       double d2 = -2.0D * x + 2.0D;
/*  89 */       int n = 4;
/*  90 */       double d3 = 1.0D;
/*  91 */       double d4 = Math.pow(d2, n);
/*  92 */       d = d3 - d4 / 2.0D;
/*     */     } 
/*  94 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeInQuint(double x) {
/*  98 */     return x * x * x * x * x;
/*     */   }
/*     */   
/*     */   public static double easeOutQuint(double x) {
/* 102 */     double d = 1.0D - x;
/* 103 */     int n = 5;
/* 104 */     double d2 = 1.0D;
/* 105 */     double d3 = Math.pow(d, n);
/* 106 */     return d2 - d3;
/*     */   }
/*     */   
/*     */   public static double easeInOutQuint(double x) {
/*     */     double d;
/* 111 */     if (x < 0.5D) {
/* 112 */       d = 16.0D * x * x * x * x * x;
/*     */     } else {
/* 114 */       double d2 = -2.0D * x + 2.0D;
/* 115 */       int n = 5;
/* 116 */       double d3 = 1.0D;
/* 117 */       double d4 = Math.pow(d2, n);
/* 118 */       d = d3 - d4 / 2.0D;
/*     */     } 
/* 120 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeInExpo(double x) {
/*     */     double d;
/* 125 */     if (x == 0.0D) {
/* 126 */       d = 0.0D;
/*     */     } else {
/* 128 */       double d2 = 2.0D;
/* 129 */       double d3 = 10.0D * x - 10.0D;
/* 130 */       d = Math.pow(d2, d3);
/*     */     } 
/* 132 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeOutExpo(double x) {
/*     */     double d;
/* 137 */     if (x == 1.0D) {
/* 138 */       d = 1.0D;
/*     */     } else {
/* 140 */       double d2 = 2.0D;
/* 141 */       double d3 = -10.0D * x;
/* 142 */       double d4 = 1.0D;
/* 143 */       double d5 = Math.pow(d2, d3);
/* 144 */       d = d4 - d5;
/*     */     } 
/* 146 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeInOutExpo(double x) {
/*     */     double d;
/* 151 */     if (x == 0.0D) {
/* 152 */       d = 0.0D;
/* 153 */     } else if (x == 1.0D) {
/* 154 */       d = 1.0D;
/* 155 */     } else if (x < 0.5D) {
/* 156 */       double d2 = 2.0D;
/* 157 */       double d3 = 20.0D * x - 10.0D;
/* 158 */       d = Math.pow(d2, d3) / 2.0D;
/*     */     } else {
/* 160 */       double d4 = 2.0D;
/* 161 */       double d5 = -20.0D * x + 10.0D;
/* 162 */       double d6 = 2.0D;
/* 163 */       double d7 = Math.pow(d4, d5);
/* 164 */       d = (d6 - d7) / 2.0D;
/*     */     } 
/* 166 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeInCirc(double x) {
/* 170 */     double d = x;
/* 171 */     int n = 2;
/* 172 */     double d2 = 1.0D;
/* 173 */     double d3 = 1.0D;
/* 174 */     double d4 = Math.pow(d, n);
/* 175 */     d = d2 - d4;
/* 176 */     d2 = Math.sqrt(d);
/* 177 */     return d3 - d2;
/*     */   }
/*     */   
/*     */   public static double easeOutCirc(double x) {
/* 181 */     double d = x - 1.0D;
/* 182 */     int n = 2;
/* 183 */     double d2 = 1.0D;
/* 184 */     double d3 = Math.pow(d, n);
/* 185 */     d = d2 - d3;
/* 186 */     return Math.sqrt(d);
/*     */   }
/*     */   
/*     */   public static double easeInOutCirc(double x) {
/*     */     double d;
/* 191 */     if (x < 0.5D) {
/* 192 */       double d2 = 2.0D * x;
/* 193 */       int n = 2;
/* 194 */       double d3 = 1.0D;
/* 195 */       double d4 = 1.0D;
/* 196 */       double d5 = Math.pow(d2, n);
/* 197 */       d2 = d3 - d5;
/* 198 */       d3 = Math.sqrt(d2);
/* 199 */       d = (d4 - d3) / 2.0D;
/*     */     } else {
/* 201 */       double d6 = -2.0D * x + 2.0D;
/* 202 */       int n = 2;
/* 203 */       double d7 = 1.0D;
/* 204 */       double d8 = Math.pow(d6, n);
/* 205 */       d6 = d7 - d8;
/* 206 */       d = (Math.sqrt(d6) + 1.0D) / 2.0D;
/*     */     } 
/* 208 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeInBack(double x) {
/* 212 */     double c1 = 1.70158D;
/* 213 */     double c3 = c1 + 1.0D;
/* 214 */     return c3 * x * x * x - c1 * x * x;
/*     */   }
/*     */   
/*     */   public static double easeOutBack(double x) {
/* 218 */     double c1 = 1.70158D;
/* 219 */     double c3 = c1 + 1.0D;
/* 220 */     double d = x - 1.0D;
/* 221 */     int n = 3;
/* 222 */     double d2 = c3;
/* 223 */     double d3 = 1.0D;
/* 224 */     double d4 = Math.pow(d, n);
/* 225 */     double d5 = d3 + d2 * d4;
/* 226 */     d = x - 1.0D;
/* 227 */     n = 2;
/* 228 */     d2 = c1;
/* 229 */     d3 = d5;
/* 230 */     d4 = Math.pow(d, n);
/* 231 */     return d3 + d2 * d4;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double easeInOutBack(double x) {
/* 236 */     double d, c1 = 1.70158D;
/* 237 */     double c2 = c1 * 1.525D;
/* 238 */     if (x < 0.5D) {
/* 239 */       double d2 = 2.0D * x;
/* 240 */       int n = 2;
/* 241 */       d = Math.pow(d2, n) * ((c2 + 1.0D) * 2.0D * x - c2) / 2.0D;
/*     */     } else {
/* 243 */       double d3 = 2.0D * x - 2.0D;
/* 244 */       int n = 2;
/* 245 */       d = (Math.pow(d3, n) * ((c2 + 1.0D) * (x * 2.0D - 2.0D) + c2) + 2.0D) / 2.0D;
/*     */     } 
/* 247 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double easeInElastic(double x) {
/* 252 */     double d, c4 = 2.0943951023931953D;
/* 253 */     if (x == 0.0D) {
/* 254 */       d = 0.0D;
/* 255 */     } else if (x == 1.0D) {
/* 256 */       d = 1.0D;
/*     */     } else {
/* 258 */       double d2 = -2.0D;
/* 259 */       double d3 = 10.0D * x - 10.0D;
/* 260 */       double d4 = Math.pow(d2, d3);
/* 261 */       d2 = (x * 10.0D - 10.75D) * c4;
/* 262 */       double d6 = Math.sin(d2);
/* 263 */       d = d4 * d6;
/*     */     } 
/* 265 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double easeOutElastic(double x) {
/* 270 */     double d, c4 = 2.0943951023931953D;
/* 271 */     if (x == 0.0D) {
/* 272 */       d = 0.0D;
/* 273 */     } else if (x == 1.0D) {
/* 274 */       d = 1.0D;
/*     */     } else {
/* 276 */       double d2 = 2.0D;
/* 277 */       double d3 = -10.0D * x;
/* 278 */       double d4 = Math.pow(d2, d3);
/* 279 */       d2 = (x * 10.0D - 0.75D) * c4;
/* 280 */       double d6 = Math.sin(d2);
/* 281 */       d = d4 * d6 + 1.0D;
/*     */     } 
/* 283 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double easeInOutElastic(double x) {
/* 288 */     double d, c5 = 1.3962634015954636D;
/* 289 */     if (x == 0.0D) {
/* 290 */       d = 0.0D;
/* 291 */     } else if (x == 1.0D) {
/* 292 */       d = 1.0D;
/* 293 */     } else if (x < 0.5D) {
/* 294 */       double d2 = 2.0D;
/* 295 */       double d3 = 20.0D * x - 10.0D;
/* 296 */       double d4 = Math.pow(d2, d3);
/* 297 */       d2 = (20.0D * x - 11.125D) * c5;
/* 298 */       double d6 = Math.sin(d2);
/* 299 */       d = -(d4 * d6) / 2.0D;
/*     */     } else {
/* 301 */       double d7 = 2.0D;
/* 302 */       double d8 = -20.0D * x + 10.0D;
/* 303 */       double d9 = Math.pow(d7, d8);
/* 304 */       d7 = (20.0D * x - 11.125D) * c5;
/* 305 */       double d11 = Math.sin(d7);
/* 306 */       d = d9 * d11 / 2.0D + 1.0D;
/*     */     } 
/* 308 */     return d;
/*     */   }
/*     */   
/*     */   public static double easeInBounce(double x) {
/* 312 */     return 1.0D - easeOutBounce(1.0D - x);
/*     */   }
/*     */   
/*     */   public static double easeOutBounce(double animeX) {
/* 316 */     double x = animeX;
/* 317 */     double n1 = 7.5625D;
/* 318 */     double d1 = 2.75D;
/* 319 */     if (x < 1.0D / d1) {
/* 320 */       return n1 * x * x;
/*     */     }
/* 322 */     if (x < 2.0D / d1) {
/* 323 */       return n1 * (x -= 1.5D) / d1 * x + 0.75D;
/*     */     }
/* 325 */     if (x < 2.5D / d1) {
/* 326 */       return n1 * (x -= 2.25D) / d1 * x + 0.9375D;
/*     */     }
/* 328 */     return n1 * (x -= 2.625D) / d1 * x + 0.984375D;
/*     */   }
/*     */   
/*     */   public static double easeInOutBounce(double x) {
/* 332 */     return (x < 0.5D) ? ((1.0D - easeOutBounce(1.0D - 2.0D * x)) / 2.0D) : ((1.0D + easeOutBounce(2.0D * x - 1.0D)) / 2.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\EaseUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */