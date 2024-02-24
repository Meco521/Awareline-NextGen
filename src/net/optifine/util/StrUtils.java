/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ public class StrUtils
/*     */ {
/*     */   public static boolean equalsMask(String str, String mask, char wildChar, char wildCharSingle) {
/*  11 */     if (mask != null && str != null) {
/*     */       
/*  13 */       if (mask.indexOf(wildChar) < 0)
/*     */       {
/*  15 */         return (mask.indexOf(wildCharSingle) < 0) ? mask.equals(str) : equalsMaskSingle(str, mask, wildCharSingle);
/*     */       }
/*     */ 
/*     */       
/*  19 */       List<String> list = new ArrayList();
/*  20 */       String s = String.valueOf(wildChar);
/*     */       
/*  22 */       if (mask.startsWith(s))
/*     */       {
/*  24 */         list.add("");
/*     */       }
/*     */       
/*  27 */       StringTokenizer stringtokenizer = new StringTokenizer(mask, s);
/*     */       
/*  29 */       while (stringtokenizer.hasMoreElements())
/*     */       {
/*  31 */         list.add(stringtokenizer.nextToken());
/*     */       }
/*     */       
/*  34 */       if (mask.endsWith(s))
/*     */       {
/*  36 */         list.add("");
/*     */       }
/*     */       
/*  39 */       String s1 = list.get(0);
/*     */       
/*  41 */       if (!startsWithMaskSingle(str, s1, wildCharSingle))
/*     */       {
/*  43 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  47 */       String s2 = list.get(list.size() - 1);
/*     */       
/*  49 */       if (!endsWithMaskSingle(str, s2, wildCharSingle))
/*     */       {
/*  51 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  55 */       int i = 0;
/*     */       
/*  57 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/*  59 */         String s3 = list.get(j);
/*     */         
/*  61 */         if (!s3.isEmpty()) {
/*     */           
/*  63 */           int k = indexOfMaskSingle(str, s3, i, wildCharSingle);
/*     */           
/*  65 */           if (k < 0)
/*     */           {
/*  67 */             return false;
/*     */           }
/*     */           
/*  70 */           i = k + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/*  74 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     return (mask == str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equalsMaskSingle(String str, String mask, char wildCharSingle) {
/*  87 */     if (str != null && mask != null) {
/*     */       
/*  89 */       if (str.length() != mask.length())
/*     */       {
/*  91 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  95 */       for (int i = 0; i < mask.length(); i++) {
/*     */         
/*  97 */         char c0 = mask.charAt(i);
/*     */         
/*  99 */         if (c0 != wildCharSingle && str.charAt(i) != c0)
/*     */         {
/* 101 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 105 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     return (str == mask);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOfMaskSingle(String str, String mask, int startPos, char wildCharSingle) {
/* 116 */     if (str != null && mask != null) {
/*     */       
/* 118 */       if (startPos >= 0 && startPos <= str.length()) {
/*     */         
/* 120 */         if (str.length() < startPos + mask.length())
/*     */         {
/* 122 */           return -1;
/*     */         }
/*     */ 
/*     */         
/* 126 */         for (int i = startPos; i + mask.length() <= str.length(); i++) {
/*     */           
/* 128 */           String s = str.substring(i, i + mask.length());
/*     */           
/* 130 */           if (equalsMaskSingle(s, mask, wildCharSingle))
/*     */           {
/* 132 */             return i;
/*     */           }
/*     */         } 
/*     */         
/* 136 */         return -1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 141 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean endsWithMaskSingle(String str, String mask, char wildCharSingle) {
/* 152 */     if (str != null && mask != null) {
/*     */       
/* 154 */       if (str.length() < mask.length())
/*     */       {
/* 156 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 160 */       String s = str.substring(str.length() - mask.length(), str.length());
/* 161 */       return equalsMaskSingle(s, mask, wildCharSingle);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     return (str == mask);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean startsWithMaskSingle(String str, String mask, char wildCharSingle) {
/* 172 */     if (str != null && mask != null) {
/*     */       
/* 174 */       if (str.length() < mask.length())
/*     */       {
/* 176 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 180 */       String s = str.substring(0, mask.length());
/* 181 */       return equalsMaskSingle(s, mask, wildCharSingle);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 186 */     return (str == mask);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equalsMask(String str, String[] masks, char wildChar) {
/* 192 */     for (int i = 0; i < masks.length; i++) {
/*     */       
/* 194 */       String s = masks[i];
/*     */       
/* 196 */       if (equalsMask(str, s, wildChar))
/*     */       {
/* 198 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsMask(String str, String mask, char wildChar) {
/* 207 */     if (mask != null && str != null) {
/*     */       
/* 209 */       if (mask.indexOf(wildChar) < 0)
/*     */       {
/* 211 */         return mask.equals(str);
/*     */       }
/*     */ 
/*     */       
/* 215 */       List<String> list = new ArrayList();
/* 216 */       String s = String.valueOf(wildChar);
/*     */       
/* 218 */       if (mask.startsWith(s))
/*     */       {
/* 220 */         list.add("");
/*     */       }
/*     */       
/* 223 */       StringTokenizer stringtokenizer = new StringTokenizer(mask, s);
/*     */       
/* 225 */       while (stringtokenizer.hasMoreElements())
/*     */       {
/* 227 */         list.add(stringtokenizer.nextToken());
/*     */       }
/*     */       
/* 230 */       if (mask.endsWith(s))
/*     */       {
/* 232 */         list.add("");
/*     */       }
/*     */       
/* 235 */       String s1 = list.get(0);
/*     */       
/* 237 */       if (!str.startsWith(s1))
/*     */       {
/* 239 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 243 */       String s2 = list.get(list.size() - 1);
/*     */       
/* 245 */       if (!str.endsWith(s2))
/*     */       {
/* 247 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 251 */       int i = 0;
/*     */       
/* 253 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/* 255 */         String s3 = list.get(j);
/*     */         
/* 257 */         if (!s3.isEmpty()) {
/*     */           
/* 259 */           int k = str.indexOf(s3, i);
/*     */           
/* 261 */           if (k < 0)
/*     */           {
/* 263 */             return false;
/*     */           }
/*     */           
/* 266 */           i = k + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/* 270 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     return (mask == str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] split(String str, String separators) {
/* 283 */     if (str != null && !str.isEmpty()) {
/*     */       
/* 285 */       if (separators == null)
/*     */       {
/* 287 */         return new String[] { str };
/*     */       }
/*     */ 
/*     */       
/* 291 */       List<String> list = new ArrayList();
/* 292 */       int i = 0;
/*     */       
/* 294 */       for (int j = 0; j < str.length(); j++) {
/*     */         
/* 296 */         char c0 = str.charAt(j);
/*     */         
/* 298 */         if (equals(c0, separators)) {
/*     */           
/* 300 */           list.add(str.substring(i, j));
/* 301 */           i = j + 1;
/*     */         } 
/*     */       } 
/*     */       
/* 305 */       list.add(str.substring(i, str.length()));
/* 306 */       return list.<String>toArray(new String[0]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 311 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equals(char ch, String matches) {
/* 317 */     for (int i = 0; i < matches.length(); i++) {
/*     */       
/* 319 */       if (matches.charAt(i) == ch)
/*     */       {
/* 321 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsTrim(String a, String b) {
/* 330 */     if (a != null)
/*     */     {
/* 332 */       a = a.trim();
/*     */     }
/*     */     
/* 335 */     if (b != null)
/*     */     {
/* 337 */       b = b.trim();
/*     */     }
/*     */     
/* 340 */     return equals(a, b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEmpty(String string) {
/* 345 */     return (string == null) ? true : ((string.trim().length() <= 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String stringInc(String str) {
/* 350 */     int i = parseInt(str, -1);
/*     */     
/* 352 */     if (i == -1)
/*     */     {
/* 354 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 358 */     i++;
/* 359 */     String s = String.valueOf(i);
/* 360 */     return (s.length() > str.length()) ? "" : fillLeft(String.valueOf(i), str.length(), '0');
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseInt(String s, int defVal) {
/* 366 */     if (s == null)
/*     */     {
/* 368 */       return defVal;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 374 */       return Integer.parseInt(s);
/*     */     }
/* 376 */     catch (NumberFormatException var3) {
/*     */       
/* 378 */       return defVal;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFilled(String string) {
/* 385 */     return !isEmpty(string);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String addIfNotContains(String target, String source) {
/* 390 */     StringBuilder targetBuilder = new StringBuilder(target);
/* 391 */     for (int i = 0; i < source.length(); i++) {
/*     */       
/* 393 */       if (targetBuilder.toString().indexOf(source.charAt(i)) < 0)
/*     */       {
/* 395 */         targetBuilder.append(source.charAt(i));
/*     */       }
/*     */     } 
/* 398 */     target = targetBuilder.toString();
/*     */     
/* 400 */     return target;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fillLeft(String s, int len, char fillChar) {
/* 405 */     if (s == null)
/*     */     {
/* 407 */       s = "";
/*     */     }
/*     */     
/* 410 */     if (s.length() >= len)
/*     */     {
/* 412 */       return s;
/*     */     }
/*     */ 
/*     */     
/* 416 */     StringBuffer stringbuffer = new StringBuffer();
/* 417 */     int i = len - s.length();
/*     */     
/* 419 */     while (stringbuffer.length() < i)
/*     */     {
/* 421 */       stringbuffer.append(fillChar);
/*     */     }
/*     */     
/* 424 */     return stringbuffer + s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fillRight(String s, int len, char fillChar) {
/* 430 */     if (s == null)
/*     */     {
/* 432 */       s = "";
/*     */     }
/*     */     
/* 435 */     if (s.length() >= len)
/*     */     {
/* 437 */       return s;
/*     */     }
/*     */ 
/*     */     
/* 441 */     StringBuffer stringbuffer = new StringBuffer(s);
/*     */     
/* 443 */     while (stringbuffer.length() < len)
/*     */     {
/* 445 */       stringbuffer.append(fillChar);
/*     */     }
/*     */     
/* 448 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Object a, Object b) {
/* 454 */     return (a == b) ? true : ((a != null && a.equals(b)) ? true : ((b != null && b.equals(a))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean startsWith(String str, String[] prefixes) {
/* 459 */     if (str == null)
/*     */     {
/* 461 */       return false;
/*     */     }
/* 463 */     if (prefixes == null)
/*     */     {
/* 465 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 469 */     for (int i = 0; i < prefixes.length; i++) {
/*     */       
/* 471 */       String s = prefixes[i];
/*     */       
/* 473 */       if (str.startsWith(s))
/*     */       {
/* 475 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 479 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean endsWith(String str, String[] suffixes) {
/* 485 */     if (str == null)
/*     */     {
/* 487 */       return false;
/*     */     }
/* 489 */     if (suffixes == null)
/*     */     {
/* 491 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 495 */     for (int i = 0; i < suffixes.length; i++) {
/*     */       
/* 497 */       String s = suffixes[i];
/*     */       
/* 499 */       if (str.endsWith(s))
/*     */       {
/* 501 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 505 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String prefix) {
/* 511 */     if (str != null && prefix != null) {
/*     */       
/* 513 */       if (str.startsWith(prefix))
/*     */       {
/* 515 */         str = str.substring(prefix.length());
/*     */       }
/*     */       
/* 518 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 522 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String str, String suffix) {
/* 528 */     if (str != null && suffix != null) {
/*     */       
/* 530 */       if (str.endsWith(suffix))
/*     */       {
/* 532 */         str = str.substring(0, str.length() - suffix.length());
/*     */       }
/*     */       
/* 535 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 539 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replaceSuffix(String str, String suffix, String suffixNew) {
/* 545 */     if (str != null && suffix != null) {
/*     */       
/* 547 */       if (!str.endsWith(suffix))
/*     */       {
/* 549 */         return str;
/*     */       }
/*     */ 
/*     */       
/* 553 */       if (suffixNew == null)
/*     */       {
/* 555 */         suffixNew = "";
/*     */       }
/*     */       
/* 558 */       str = str.substring(0, str.length() - suffix.length());
/* 559 */       return str + suffixNew;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 564 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replacePrefix(String str, String prefix, String prefixNew) {
/* 570 */     if (str != null && prefix != null) {
/*     */       
/* 572 */       if (!str.startsWith(prefix))
/*     */       {
/* 574 */         return str;
/*     */       }
/*     */ 
/*     */       
/* 578 */       if (prefixNew == null)
/*     */       {
/* 580 */         prefixNew = "";
/*     */       }
/*     */       
/* 583 */       str = str.substring(prefix.length());
/* 584 */       return prefixNew + str;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 589 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findPrefix(String[] strs, String prefix) {
/* 595 */     if (strs != null && prefix != null) {
/*     */       
/* 597 */       for (int i = 0; i < strs.length; i++) {
/*     */         
/* 599 */         String s = strs[i];
/*     */         
/* 601 */         if (s.startsWith(prefix))
/*     */         {
/* 603 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 607 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 611 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findSuffix(String[] strs, String suffix) {
/* 617 */     if (strs != null && suffix != null) {
/*     */       
/* 619 */       for (int i = 0; i < strs.length; i++) {
/*     */         
/* 621 */         String s = strs[i];
/*     */         
/* 623 */         if (s.endsWith(suffix))
/*     */         {
/* 625 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 629 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 633 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] remove(String[] strs, int start, int end) {
/* 639 */     if (strs == null)
/*     */     {
/* 641 */       return strs;
/*     */     }
/* 643 */     if (end > 0 && start < strs.length) {
/*     */       
/* 645 */       if (start >= end)
/*     */       {
/* 647 */         return strs;
/*     */       }
/*     */ 
/*     */       
/* 651 */       List<String> list = new ArrayList<>(strs.length);
/*     */       
/* 653 */       for (int i = 0; i < strs.length; i++) {
/*     */         
/* 655 */         String s = strs[i];
/*     */         
/* 657 */         if (i < start || i >= end)
/*     */         {
/* 659 */           list.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 663 */       String[] astring = list.<String>toArray(new String[0]);
/* 664 */       return astring;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 669 */     return strs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String str, String[] suffixes) {
/* 675 */     if (str != null && suffixes != null) {
/*     */       
/* 677 */       int i = str.length();
/*     */       
/* 679 */       for (int j = 0; j < suffixes.length; j++) {
/*     */         
/* 681 */         String s = suffixes[j];
/* 682 */         str = removeSuffix(str, s);
/*     */         
/* 684 */         if (str.length() != i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 690 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 694 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String[] prefixes) {
/* 700 */     if (str != null && prefixes != null) {
/*     */       
/* 702 */       int i = str.length();
/*     */       
/* 704 */       for (int j = 0; j < prefixes.length; j++) {
/*     */         
/* 706 */         String s = prefixes[j];
/* 707 */         str = removePrefix(str, s);
/*     */         
/* 709 */         if (str.length() != i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 715 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 719 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String str, String[] prefixes, String[] suffixes) {
/* 725 */     str = removePrefix(str, prefixes);
/* 726 */     str = removeSuffix(str, suffixes);
/* 727 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String str, String prefix, String suffix) {
/* 732 */     return removePrefixSuffix(str, new String[] { prefix }, new String[] { suffix });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSegment(String str, String start, String end) {
/* 737 */     if (str != null && start != null && end != null) {
/*     */       
/* 739 */       int i = str.indexOf(start);
/*     */       
/* 741 */       if (i < 0)
/*     */       {
/* 743 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 747 */       int j = str.indexOf(end, i);
/* 748 */       return (j < 0) ? null : str.substring(i, j + end.length());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 753 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addSuffixCheck(String str, String suffix) {
/* 759 */     return (str != null && suffix != null) ? (str.endsWith(suffix) ? str : (str + suffix)) : str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String addPrefixCheck(String str, String prefix) {
/* 764 */     return (str != null && prefix != null) ? (str.endsWith(prefix) ? str : (prefix + str)) : str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String trim(String str, String chars) {
/* 769 */     if (str != null && chars != null) {
/*     */       
/* 771 */       str = trimLeading(str, chars);
/* 772 */       str = trimTrailing(str, chars);
/* 773 */       return str;
/*     */     } 
/*     */ 
/*     */     
/* 777 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String trimLeading(String str, String chars) {
/* 783 */     if (str != null && chars != null) {
/*     */       
/* 785 */       int i = str.length();
/*     */       
/* 787 */       for (int j = 0; j < i; j++) {
/*     */         
/* 789 */         char c0 = str.charAt(j);
/*     */         
/* 791 */         if (chars.indexOf(c0) < 0)
/*     */         {
/* 793 */           return str.substring(j);
/*     */         }
/*     */       } 
/*     */       
/* 797 */       return "";
/*     */     } 
/*     */ 
/*     */     
/* 801 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String trimTrailing(String str, String chars) {
/* 807 */     if (str != null && chars != null) {
/*     */       
/* 809 */       int i = str.length();
/*     */       
/*     */       int j;
/* 812 */       for (j = i; j > 0; j--) {
/*     */         
/* 814 */         char c0 = str.charAt(j - 1);
/*     */         
/* 816 */         if (chars.indexOf(c0) < 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 822 */       return (j == i) ? str : str.substring(0, j);
/*     */     } 
/*     */ 
/*     */     
/* 826 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\StrUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */