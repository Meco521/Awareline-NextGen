/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.IntegerCache;
/*     */ import net.optifine.CustomColors;
/*     */ 
/*     */ 
/*     */ public class PotionHelper
/*     */ {
/*  15 */   public static final String unusedString = null;
/*     */   public static final String sugarEffect = "-0+1-2-3&4-4+13";
/*     */   public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
/*     */   public static final String spiderEyeEffect = "-0-1+2-3&4-4+13";
/*     */   public static final String fermentedSpiderEyeEffect = "-0+3-4+13";
/*     */   public static final String speckledMelonEffect = "+0-1+2-3&4-4+13";
/*     */   public static final String blazePowderEffect = "+0-1-2+3&4-4+13";
/*     */   public static final String magmaCreamEffect = "+0+1-2-3&4-4+13";
/*     */   public static final String redstoneEffect = "-5+6-7";
/*     */   public static final String glowstoneEffect = "+5-6-7";
/*     */   public static final String gunpowderEffect = "+14&13-13";
/*     */   public static final String goldenCarrotEffect = "-0+1+2-3+13&4-4";
/*     */   public static final String pufferfishEffect = "+0-1+2+3+13&4-4";
/*     */   public static final String rabbitFootEffect = "+0+1-2+3&4-4+13";
/*  29 */   private static final Map<Integer, String> potionRequirements = Maps.newHashMap();
/*  30 */   private static final Map<Integer, String> potionAmplifiers = Maps.newHashMap();
/*  31 */   private static final Map<Integer, Integer> DATAVALUE_COLORS = Maps.newHashMap();
/*     */ 
/*     */   
/*  34 */   private static final String[] potionPrefixes = new String[] { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkFlag(int p_77914_0_, int p_77914_1_) {
/*  41 */     return ((p_77914_0_ & 1 << p_77914_1_) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int isFlagSet(int p_77910_0_, int p_77910_1_) {
/*  49 */     return checkFlag(p_77910_0_, p_77910_1_) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int isFlagUnset(int p_77916_0_, int p_77916_1_) {
/*  57 */     return checkFlag(p_77916_0_, p_77916_1_) ? 0 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPotionPrefixIndex(int dataValue) {
/*  65 */     return getPotionPrefixIndexFlags(dataValue, 5, 4, 3, 2, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcPotionLiquidColor(Collection<PotionEffect> p_77911_0_) {
/*  73 */     int i = 3694022;
/*     */     
/*  75 */     if (p_77911_0_ != null && !p_77911_0_.isEmpty()) {
/*     */       
/*  77 */       float f = 0.0F;
/*  78 */       float f1 = 0.0F;
/*  79 */       float f2 = 0.0F;
/*  80 */       float f3 = 0.0F;
/*     */       
/*  82 */       for (PotionEffect potioneffect : p_77911_0_) {
/*     */         
/*  84 */         if (potioneffect.getIsShowParticles()) {
/*     */           
/*  86 */           int j = Potion.potionTypes[potioneffect.getPotionID()].getLiquidColor();
/*     */           
/*  88 */           if (Config.isCustomColors())
/*     */           {
/*  90 */             j = CustomColors.getPotionColor(potioneffect.getPotionID(), j);
/*     */           }
/*     */           
/*  93 */           for (int k = 0; k <= potioneffect.getAmplifier(); k++) {
/*     */             
/*  95 */             f += (j >> 16 & 0xFF) / 255.0F;
/*  96 */             f1 += (j >> 8 & 0xFF) / 255.0F;
/*  97 */             f2 += (j >> 0 & 0xFF) / 255.0F;
/*  98 */             f3++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 103 */       if (f3 == 0.0F)
/*     */       {
/* 105 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 109 */       f = f / f3 * 255.0F;
/* 110 */       f1 = f1 / f3 * 255.0F;
/* 111 */       f2 = f2 / f3 * 255.0F;
/* 112 */       return (int)f << 16 | (int)f1 << 8 | (int)f2;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 117 */     return Config.isCustomColors() ? CustomColors.getPotionColor(0, i) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getAreAmbient(Collection<PotionEffect> potionEffects) {
/* 126 */     for (PotionEffect potioneffect : potionEffects) {
/*     */       
/* 128 */       if (!potioneffect.getIsAmbient())
/*     */       {
/* 130 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLiquidColor(int dataValue, boolean bypassCache) {
/* 142 */     Integer integer = IntegerCache.getInteger(dataValue);
/*     */     
/* 144 */     if (!bypassCache) {
/*     */       
/* 146 */       if (DATAVALUE_COLORS.containsKey(integer))
/*     */       {
/* 148 */         return ((Integer)DATAVALUE_COLORS.get(integer)).intValue();
/*     */       }
/*     */ 
/*     */       
/* 152 */       int i = calcPotionLiquidColor(getPotionEffects(integer.intValue(), false));
/* 153 */       DATAVALUE_COLORS.put(integer, Integer.valueOf(i));
/* 154 */       return i;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     return calcPotionLiquidColor(getPotionEffects(integer.intValue(), true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPotionPrefix(int dataValue) {
/* 168 */     int i = getPotionPrefixIndex(dataValue);
/* 169 */     return potionPrefixes[i];
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getPotionEffect(boolean p_77904_0_, boolean p_77904_1_, boolean p_77904_2_, int p_77904_3_, int p_77904_4_, int p_77904_5_, int p_77904_6_) {
/* 174 */     int i = 0;
/*     */     
/* 176 */     if (p_77904_0_) {
/*     */       
/* 178 */       i = isFlagUnset(p_77904_6_, p_77904_4_);
/*     */     }
/* 180 */     else if (p_77904_3_ != -1) {
/*     */       
/* 182 */       if (p_77904_3_ == 0 && countSetFlags(p_77904_6_) == p_77904_4_)
/*     */       {
/* 184 */         i = 1;
/*     */       }
/* 186 */       else if (p_77904_3_ == 1 && countSetFlags(p_77904_6_) > p_77904_4_)
/*     */       {
/* 188 */         i = 1;
/*     */       }
/* 190 */       else if (p_77904_3_ == 2 && countSetFlags(p_77904_6_) < p_77904_4_)
/*     */       {
/* 192 */         i = 1;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 197 */       i = isFlagSet(p_77904_6_, p_77904_4_);
/*     */     } 
/*     */     
/* 200 */     if (p_77904_1_)
/*     */     {
/* 202 */       i *= p_77904_5_;
/*     */     }
/*     */     
/* 205 */     if (p_77904_2_)
/*     */     {
/* 207 */       i *= -1;
/*     */     }
/*     */     
/* 210 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int countSetFlags(int p_77907_0_) {
/*     */     int i;
/* 220 */     for (i = 0; p_77907_0_ > 0; i++)
/*     */     {
/* 222 */       p_77907_0_ &= p_77907_0_ - 1;
/*     */     }
/*     */     
/* 225 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parsePotionEffects(String p_77912_0_, int p_77912_1_, int p_77912_2_, int p_77912_3_) {
/* 230 */     if (p_77912_1_ < p_77912_0_.length() && p_77912_2_ >= 0 && p_77912_1_ < p_77912_2_) {
/*     */       
/* 232 */       int i = p_77912_0_.indexOf('|', p_77912_1_);
/*     */       
/* 234 */       if (i >= 0 && i < p_77912_2_) {
/*     */         
/* 236 */         int l1 = parsePotionEffects(p_77912_0_, p_77912_1_, i - 1, p_77912_3_);
/*     */         
/* 238 */         if (l1 > 0)
/*     */         {
/* 240 */           return l1;
/*     */         }
/*     */ 
/*     */         
/* 244 */         int j2 = parsePotionEffects(p_77912_0_, i + 1, p_77912_2_, p_77912_3_);
/* 245 */         return (j2 > 0) ? j2 : 0;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 250 */       int j = p_77912_0_.indexOf('&', p_77912_1_);
/*     */       
/* 252 */       if (j >= 0 && j < p_77912_2_) {
/*     */         
/* 254 */         int i2 = parsePotionEffects(p_77912_0_, p_77912_1_, j - 1, p_77912_3_);
/*     */         
/* 256 */         if (i2 <= 0)
/*     */         {
/* 258 */           return 0;
/*     */         }
/*     */ 
/*     */         
/* 262 */         int k2 = parsePotionEffects(p_77912_0_, j + 1, p_77912_2_, p_77912_3_);
/* 263 */         return (k2 <= 0) ? 0 : ((i2 > k2) ? i2 : k2);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 268 */       boolean flag = false;
/* 269 */       boolean flag1 = false;
/* 270 */       boolean flag2 = false;
/* 271 */       boolean flag3 = false;
/* 272 */       boolean flag4 = false;
/* 273 */       int k = -1;
/* 274 */       int l = 0;
/* 275 */       int i1 = 0;
/* 276 */       int j1 = 0;
/*     */       
/* 278 */       for (int k1 = p_77912_1_; k1 < p_77912_2_; k1++) {
/*     */         
/* 280 */         char c0 = p_77912_0_.charAt(k1);
/*     */         
/* 282 */         if (c0 >= '0' && c0 <= '9') {
/*     */           
/* 284 */           if (flag)
/*     */           {
/* 286 */             i1 = c0 - 48;
/* 287 */             flag1 = true;
/*     */           }
/*     */           else
/*     */           {
/* 291 */             l *= 10;
/* 292 */             l += c0 - 48;
/* 293 */             flag2 = true;
/*     */           }
/*     */         
/* 296 */         } else if (c0 == '*') {
/*     */           
/* 298 */           flag = true;
/*     */         }
/* 300 */         else if (c0 == '!') {
/*     */           
/* 302 */           if (flag2) {
/*     */             
/* 304 */             j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 305 */             flag3 = false;
/* 306 */             flag4 = false;
/* 307 */             flag = false;
/* 308 */             flag1 = false;
/* 309 */             flag2 = false;
/* 310 */             i1 = 0;
/* 311 */             l = 0;
/* 312 */             k = -1;
/*     */           } 
/*     */           
/* 315 */           flag3 = true;
/*     */         }
/* 317 */         else if (c0 == '-') {
/*     */           
/* 319 */           if (flag2) {
/*     */             
/* 321 */             j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 322 */             flag3 = false;
/* 323 */             flag4 = false;
/* 324 */             flag = false;
/* 325 */             flag1 = false;
/* 326 */             flag2 = false;
/* 327 */             i1 = 0;
/* 328 */             l = 0;
/* 329 */             k = -1;
/*     */           } 
/*     */           
/* 332 */           flag4 = true;
/*     */         }
/* 334 */         else if (c0 != '=' && c0 != '<' && c0 != '>') {
/*     */           
/* 336 */           if (c0 == '+' && flag2)
/*     */           {
/* 338 */             j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 339 */             flag3 = false;
/* 340 */             flag4 = false;
/* 341 */             flag = false;
/* 342 */             flag1 = false;
/* 343 */             flag2 = false;
/* 344 */             i1 = 0;
/* 345 */             l = 0;
/* 346 */             k = -1;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 351 */           if (flag2) {
/*     */             
/* 353 */             j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 354 */             flag3 = false;
/* 355 */             flag4 = false;
/* 356 */             flag = false;
/* 357 */             flag1 = false;
/* 358 */             flag2 = false;
/* 359 */             i1 = 0;
/* 360 */             l = 0;
/* 361 */             k = -1;
/*     */           } 
/*     */           
/* 364 */           if (c0 == '=') {
/*     */             
/* 366 */             k = 0;
/*     */           }
/* 368 */           else if (c0 == '<') {
/*     */             
/* 370 */             k = 2;
/*     */           }
/* 372 */           else if (c0 == '>') {
/*     */             
/* 374 */             k = 1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 379 */       if (flag2)
/*     */       {
/* 381 */         j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/*     */       }
/*     */       
/* 384 */       return j1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 390 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<PotionEffect> getPotionEffects(int p_77917_0_, boolean p_77917_1_) {
/* 396 */     List<PotionEffect> list = null;
/*     */     
/* 398 */     for (Potion potion : Potion.potionTypes) {
/*     */       
/* 400 */       if (potion != null && (!potion.isUsable() || p_77917_1_)) {
/*     */         
/* 402 */         String s = potionRequirements.get(Integer.valueOf(potion.getId()));
/*     */         
/* 404 */         if (s != null) {
/*     */           
/* 406 */           int i = parsePotionEffects(s, 0, s.length(), p_77917_0_);
/*     */           
/* 408 */           if (i > 0) {
/*     */             
/* 410 */             int j = 0;
/* 411 */             String s1 = potionAmplifiers.get(Integer.valueOf(potion.getId()));
/*     */             
/* 413 */             if (s1 != null) {
/*     */               
/* 415 */               j = parsePotionEffects(s1, 0, s1.length(), p_77917_0_);
/*     */               
/* 417 */               if (j < 0)
/*     */               {
/* 419 */                 j = 0;
/*     */               }
/*     */             } 
/*     */             
/* 423 */             if (potion.isInstant()) {
/*     */               
/* 425 */               i = 1;
/*     */             }
/*     */             else {
/*     */               
/* 429 */               i = 1200 * (i * 3 + (i - 1 << 1));
/* 430 */               i >>= j;
/* 431 */               i = (int)Math.round(i * potion.getEffectiveness());
/*     */               
/* 433 */               if ((p_77917_0_ & 0x4000) != 0)
/*     */               {
/* 435 */                 i = (int)Math.round(i * 0.75D + 0.5D);
/*     */               }
/*     */             } 
/*     */             
/* 439 */             if (list == null)
/*     */             {
/* 441 */               list = Lists.newArrayList();
/*     */             }
/*     */             
/* 444 */             PotionEffect potioneffect = new PotionEffect(potion.getId(), i, j);
/*     */             
/* 446 */             if ((p_77917_0_ & 0x4000) != 0)
/*     */             {
/* 448 */               potioneffect.setSplashPotion(true);
/*     */             }
/*     */             
/* 451 */             list.add(potioneffect);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 457 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int brewBitOperations(int p_77906_0_, int p_77906_1_, boolean p_77906_2_, boolean p_77906_3_, boolean p_77906_4_) {
/* 465 */     if (p_77906_4_) {
/*     */       
/* 467 */       if (!checkFlag(p_77906_0_, p_77906_1_))
/*     */       {
/* 469 */         return 0;
/*     */       }
/*     */     }
/* 472 */     else if (p_77906_2_) {
/*     */       
/* 474 */       p_77906_0_ &= 1 << p_77906_1_ ^ 0xFFFFFFFF;
/*     */     }
/* 476 */     else if (p_77906_3_) {
/*     */       
/* 478 */       if ((p_77906_0_ & 1 << p_77906_1_) == 0)
/*     */       {
/* 480 */         p_77906_0_ |= 1 << p_77906_1_;
/*     */       }
/*     */       else
/*     */       {
/* 484 */         p_77906_0_ &= 1 << p_77906_1_ ^ 0xFFFFFFFF;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 489 */       p_77906_0_ |= 1 << p_77906_1_;
/*     */     } 
/*     */     
/* 492 */     return p_77906_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int applyIngredient(int p_77913_0_, String p_77913_1_) {
/* 500 */     int i = 0;
/* 501 */     int j = p_77913_1_.length();
/* 502 */     boolean flag = false;
/* 503 */     boolean flag1 = false;
/* 504 */     boolean flag2 = false;
/* 505 */     boolean flag3 = false;
/* 506 */     int k = 0;
/*     */     
/* 508 */     for (int l = i; l < j; l++) {
/*     */       
/* 510 */       char c0 = p_77913_1_.charAt(l);
/*     */       
/* 512 */       if (c0 >= '0' && c0 <= '9') {
/*     */         
/* 514 */         k *= 10;
/* 515 */         k += c0 - 48;
/* 516 */         flag = true;
/*     */       }
/* 518 */       else if (c0 == '!') {
/*     */         
/* 520 */         if (flag) {
/*     */           
/* 522 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 523 */           flag3 = false;
/* 524 */           flag1 = false;
/* 525 */           flag2 = false;
/* 526 */           flag = false;
/* 527 */           k = 0;
/*     */         } 
/*     */         
/* 530 */         flag1 = true;
/*     */       }
/* 532 */       else if (c0 == '-') {
/*     */         
/* 534 */         if (flag) {
/*     */           
/* 536 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 537 */           flag3 = false;
/* 538 */           flag1 = false;
/* 539 */           flag2 = false;
/* 540 */           flag = false;
/* 541 */           k = 0;
/*     */         } 
/*     */         
/* 544 */         flag2 = true;
/*     */       }
/* 546 */       else if (c0 == '+') {
/*     */         
/* 548 */         if (flag)
/*     */         {
/* 550 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 551 */           flag3 = false;
/* 552 */           flag1 = false;
/* 553 */           flag2 = false;
/* 554 */           flag = false;
/* 555 */           k = 0;
/*     */         }
/*     */       
/* 558 */       } else if (c0 == '&') {
/*     */         
/* 560 */         if (flag) {
/*     */           
/* 562 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 563 */           flag3 = false;
/* 564 */           flag1 = false;
/* 565 */           flag2 = false;
/* 566 */           flag = false;
/* 567 */           k = 0;
/*     */         } 
/*     */         
/* 570 */         flag3 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 574 */     if (flag)
/*     */     {
/* 576 */       p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/*     */     }
/*     */     
/* 579 */     return p_77913_0_ & 0x7FFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPotionPrefixIndexFlags(int p_77908_0_, int p_77908_1_, int p_77908_2_, int p_77908_3_, int p_77908_4_, int p_77908_5_) {
/* 584 */     return (checkFlag(p_77908_0_, p_77908_1_) ? 16 : 0) | (checkFlag(p_77908_0_, p_77908_2_) ? 8 : 0) | (checkFlag(p_77908_0_, p_77908_3_) ? 4 : 0) | (checkFlag(p_77908_0_, p_77908_4_) ? 2 : 0) | (checkFlag(p_77908_0_, p_77908_5_) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 589 */     potionRequirements.put(Integer.valueOf(Potion.regeneration.getId()), "0 & !1 & !2 & !3 & 0+6");
/* 590 */     potionRequirements.put(Integer.valueOf(Potion.moveSpeed.getId()), "!0 & 1 & !2 & !3 & 1+6");
/* 591 */     potionRequirements.put(Integer.valueOf(Potion.fireResistance.getId()), "0 & 1 & !2 & !3 & 0+6");
/* 592 */     potionRequirements.put(Integer.valueOf(Potion.heal.getId()), "0 & !1 & 2 & !3");
/* 593 */     potionRequirements.put(Integer.valueOf(Potion.poison.getId()), "!0 & !1 & 2 & !3 & 2+6");
/* 594 */     potionRequirements.put(Integer.valueOf(Potion.weakness.getId()), "!0 & !1 & !2 & 3 & 3+6");
/* 595 */     potionRequirements.put(Integer.valueOf(Potion.harm.getId()), "!0 & !1 & 2 & 3");
/* 596 */     potionRequirements.put(Integer.valueOf(Potion.moveSlowdown.getId()), "!0 & 1 & !2 & 3 & 3+6");
/* 597 */     potionRequirements.put(Integer.valueOf(Potion.damageBoost.getId()), "0 & !1 & !2 & 3 & 3+6");
/* 598 */     potionRequirements.put(Integer.valueOf(Potion.nightVision.getId()), "!0 & 1 & 2 & !3 & 2+6");
/* 599 */     potionRequirements.put(Integer.valueOf(Potion.invisibility.getId()), "!0 & 1 & 2 & 3 & 2+6");
/* 600 */     potionRequirements.put(Integer.valueOf(Potion.waterBreathing.getId()), "0 & !1 & 2 & 3 & 2+6");
/* 601 */     potionRequirements.put(Integer.valueOf(Potion.jump.getId()), "0 & 1 & !2 & 3 & 3+6");
/* 602 */     potionAmplifiers.put(Integer.valueOf(Potion.moveSpeed.getId()), "5");
/* 603 */     potionAmplifiers.put(Integer.valueOf(Potion.digSpeed.getId()), "5");
/* 604 */     potionAmplifiers.put(Integer.valueOf(Potion.damageBoost.getId()), "5");
/* 605 */     potionAmplifiers.put(Integer.valueOf(Potion.regeneration.getId()), "5");
/* 606 */     potionAmplifiers.put(Integer.valueOf(Potion.harm.getId()), "5");
/* 607 */     potionAmplifiers.put(Integer.valueOf(Potion.heal.getId()), "5");
/* 608 */     potionAmplifiers.put(Integer.valueOf(Potion.resistance.getId()), "5");
/* 609 */     potionAmplifiers.put(Integer.valueOf(Potion.poison.getId()), "5");
/* 610 */     potionAmplifiers.put(Integer.valueOf(Potion.jump.getId()), "5");
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\potion\PotionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */