/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Stack;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class JsonToNBT
/*     */ {
/*  14 */   private static final Logger logger = LogManager.getLogger();
/*  15 */   private static final Pattern field_179273_b = Pattern.compile("\\[[-+\\d|,\\s]+\\]");
/*     */ 
/*     */   
/*     */   public static NBTTagCompound getTagFromJson(String jsonString) throws NBTException {
/*  19 */     jsonString = jsonString.trim();
/*     */     
/*  21 */     if (jsonString.isEmpty() || jsonString.charAt(0) != '{')
/*     */     {
/*  23 */       throw new NBTException("Invalid tag encountered, expected '{' as first char.");
/*     */     }
/*  25 */     if (func_150310_b(jsonString) != 1)
/*     */     {
/*  27 */       throw new NBTException("Encountered multiple top tags, only one expected");
/*     */     }
/*     */ 
/*     */     
/*  31 */     return (NBTTagCompound)func_150316_a("tag", jsonString).parse();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int func_150310_b(String p_150310_0_) throws NBTException {
/*  37 */     int i = 0;
/*  38 */     boolean flag = false;
/*  39 */     Stack<Character> stack = new Stack<>();
/*     */     
/*  41 */     for (int j = 0; j < p_150310_0_.length(); j++) {
/*     */       
/*  43 */       char c0 = p_150310_0_.charAt(j);
/*     */       
/*  45 */       if (c0 == '"') {
/*     */         
/*  47 */         if (func_179271_b(p_150310_0_, j))
/*     */         {
/*  49 */           if (!flag)
/*     */           {
/*  51 */             throw new NBTException("Illegal use of \\\": " + p_150310_0_);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/*  56 */           flag = !flag;
/*     */         }
/*     */       
/*  59 */       } else if (!flag) {
/*     */         
/*  61 */         if (c0 != '{' && c0 != '[') {
/*     */           
/*  63 */           if (c0 == '}' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '{'))
/*     */           {
/*  65 */             throw new NBTException("Unbalanced curly brackets {}: " + p_150310_0_);
/*     */           }
/*     */           
/*  68 */           if (c0 == ']' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '['))
/*     */           {
/*  70 */             throw new NBTException("Unbalanced square brackets []: " + p_150310_0_);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/*  75 */           if (stack.isEmpty())
/*     */           {
/*  77 */             i++;
/*     */           }
/*     */           
/*  80 */           stack.push(Character.valueOf(c0));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     if (flag)
/*     */     {
/*  87 */       throw new NBTException("Unbalanced quotation: " + p_150310_0_);
/*     */     }
/*  89 */     if (!stack.isEmpty())
/*     */     {
/*  91 */       throw new NBTException("Unbalanced brackets: " + p_150310_0_);
/*     */     }
/*     */ 
/*     */     
/*  95 */     if (i == 0 && !p_150310_0_.isEmpty())
/*     */     {
/*  97 */       i = 1;
/*     */     }
/*     */     
/* 100 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Any func_179272_a(String... p_179272_0_) throws NBTException {
/* 106 */     return func_150316_a(p_179272_0_[0], p_179272_0_[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   static Any func_150316_a(String p_150316_0_, String p_150316_1_) throws NBTException {
/* 111 */     p_150316_1_ = p_150316_1_.trim();
/*     */     
/* 113 */     if (!p_150316_1_.isEmpty() && p_150316_1_.charAt(0) == '{') {
/*     */       
/* 115 */       p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
/*     */       
/*     */       Compound jsontonbt$compound;
/*     */       
/* 119 */       for (jsontonbt$compound = new Compound(p_150316_0_); !p_150316_1_.isEmpty(); p_150316_1_ = p_150316_1_.substring(s1.length() + 1)) {
/*     */         
/* 121 */         String s1 = func_150314_a(p_150316_1_, true);
/*     */         
/* 123 */         if (!s1.isEmpty()) {
/*     */           
/* 125 */           boolean flag1 = false;
/* 126 */           jsontonbt$compound.field_150491_b.add(func_179270_a(s1, flag1));
/*     */         } 
/*     */         
/* 129 */         if (p_150316_1_.length() < s1.length() + 1) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 134 */         char c1 = p_150316_1_.charAt(s1.length());
/*     */         
/* 136 */         if (c1 != ',' && c1 != '{' && c1 != '}' && c1 != '[' && c1 != ']')
/*     */         {
/* 138 */           throw new NBTException("Unexpected token '" + c1 + "' at: " + p_150316_1_.substring(s1.length()));
/*     */         }
/*     */       } 
/*     */       
/* 142 */       return jsontonbt$compound;
/*     */     } 
/* 144 */     if (!p_150316_1_.isEmpty() && p_150316_1_.charAt(0) == '[' && !field_179273_b.matcher(p_150316_1_).matches()) {
/*     */       
/* 146 */       p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
/*     */       
/*     */       List jsontonbt$list;
/*     */       
/* 150 */       for (jsontonbt$list = new List(p_150316_0_); !p_150316_1_.isEmpty(); p_150316_1_ = p_150316_1_.substring(s.length() + 1)) {
/*     */         
/* 152 */         String s = func_150314_a(p_150316_1_, false);
/*     */         
/* 154 */         if (!s.isEmpty()) {
/*     */           
/* 156 */           boolean flag = true;
/* 157 */           jsontonbt$list.field_150492_b.add(func_179270_a(s, flag));
/*     */         } 
/*     */         
/* 160 */         if (p_150316_1_.length() < s.length() + 1) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 165 */         char c0 = p_150316_1_.charAt(s.length());
/*     */         
/* 167 */         if (c0 != ',' && c0 != '{' && c0 != '}' && c0 != '[' && c0 != ']')
/*     */         {
/* 169 */           throw new NBTException("Unexpected token '" + c0 + "' at: " + p_150316_1_.substring(s.length()));
/*     */         }
/*     */       } 
/*     */       
/* 173 */       return jsontonbt$list;
/*     */     } 
/*     */ 
/*     */     
/* 177 */     return new Primitive(p_150316_0_, p_150316_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Any func_179270_a(String p_179270_0_, boolean p_179270_1_) throws NBTException {
/* 183 */     String s = func_150313_b(p_179270_0_, p_179270_1_);
/* 184 */     String s1 = func_150311_c(p_179270_0_, p_179270_1_);
/* 185 */     return func_179272_a(new String[] { s, s1 });
/*     */   }
/*     */ 
/*     */   
/*     */   private static String func_150314_a(String p_150314_0_, boolean p_150314_1_) throws NBTException {
/* 190 */     int i = func_150312_a(p_150314_0_, ':');
/* 191 */     int j = func_150312_a(p_150314_0_, ',');
/*     */     
/* 193 */     if (p_150314_1_) {
/*     */       
/* 195 */       if (i == -1)
/*     */       {
/* 197 */         throw new NBTException("Unable to locate name/value separator for string: " + p_150314_0_);
/*     */       }
/*     */       
/* 200 */       if (j != -1 && j < i)
/*     */       {
/* 202 */         throw new NBTException("Name error at: " + p_150314_0_);
/*     */       }
/*     */     }
/* 205 */     else if (i == -1 || i > j) {
/*     */       
/* 207 */       i = -1;
/*     */     } 
/*     */     
/* 210 */     return func_179269_a(p_150314_0_, i);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String func_179269_a(String p_179269_0_, int p_179269_1_) throws NBTException {
/* 215 */     Stack<Character> stack = new Stack<>();
/* 216 */     int i = p_179269_1_ + 1;
/* 217 */     boolean flag = false;
/* 218 */     boolean flag1 = false;
/* 219 */     boolean flag2 = false;
/*     */     
/* 221 */     for (int j = 0; i < p_179269_0_.length(); i++) {
/*     */       
/* 223 */       char c0 = p_179269_0_.charAt(i);
/*     */       
/* 225 */       if (c0 == '"') {
/*     */         
/* 227 */         if (func_179271_b(p_179269_0_, i))
/*     */         {
/* 229 */           if (!flag)
/*     */           {
/* 231 */             throw new NBTException("Illegal use of \\\": " + p_179269_0_);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 236 */           flag = !flag;
/*     */           
/* 238 */           if (flag && !flag2)
/*     */           {
/* 240 */             flag1 = true;
/*     */           }
/*     */           
/* 243 */           if (!flag)
/*     */           {
/* 245 */             j = i;
/*     */           }
/*     */         }
/*     */       
/* 249 */       } else if (!flag) {
/*     */         
/* 251 */         if (c0 != '{' && c0 != '[') {
/*     */           
/* 253 */           if (c0 == '}' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '{'))
/*     */           {
/* 255 */             throw new NBTException("Unbalanced curly brackets {}: " + p_179269_0_);
/*     */           }
/*     */           
/* 258 */           if (c0 == ']' && (stack.isEmpty() || ((Character)stack.pop()).charValue() != '['))
/*     */           {
/* 260 */             throw new NBTException("Unbalanced square brackets []: " + p_179269_0_);
/*     */           }
/*     */           
/* 263 */           if (c0 == ',' && stack.isEmpty())
/*     */           {
/* 265 */             return p_179269_0_.substring(0, i);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 270 */           stack.push(Character.valueOf(c0));
/*     */         } 
/*     */       } 
/*     */       
/* 274 */       if (!Character.isWhitespace(c0)) {
/*     */         
/* 276 */         if (!flag && flag1 && j != i)
/*     */         {
/* 278 */           return p_179269_0_.substring(0, j + 1);
/*     */         }
/*     */         
/* 281 */         flag2 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     return p_179269_0_.substring(0, i);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String func_150313_b(String p_150313_0_, boolean p_150313_1_) throws NBTException {
/* 290 */     if (p_150313_1_) {
/*     */       
/* 292 */       p_150313_0_ = p_150313_0_.trim();
/*     */       
/* 294 */       if ((!p_150313_0_.isEmpty() && p_150313_0_.charAt(0) == '{') || (!p_150313_0_.isEmpty() && p_150313_0_.charAt(0) == '['))
/*     */       {
/* 296 */         return "";
/*     */       }
/*     */     } 
/*     */     
/* 300 */     int i = func_150312_a(p_150313_0_, ':');
/*     */     
/* 302 */     if (i == -1) {
/*     */       
/* 304 */       if (p_150313_1_)
/*     */       {
/* 306 */         return "";
/*     */       }
/*     */ 
/*     */       
/* 310 */       throw new NBTException("Unable to locate name/value separator for string: " + p_150313_0_);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 315 */     return p_150313_0_.substring(0, i).trim();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String func_150311_c(String p_150311_0_, boolean p_150311_1_) throws NBTException {
/* 321 */     if (p_150311_1_) {
/*     */       
/* 323 */       p_150311_0_ = p_150311_0_.trim();
/*     */       
/* 325 */       if ((!p_150311_0_.isEmpty() && p_150311_0_.charAt(0) == '{') || (!p_150311_0_.isEmpty() && p_150311_0_.charAt(0) == '['))
/*     */       {
/* 327 */         return p_150311_0_;
/*     */       }
/*     */     } 
/*     */     
/* 331 */     int i = func_150312_a(p_150311_0_, ':');
/*     */     
/* 333 */     if (i == -1) {
/*     */       
/* 335 */       if (p_150311_1_)
/*     */       {
/* 337 */         return p_150311_0_;
/*     */       }
/*     */ 
/*     */       
/* 341 */       throw new NBTException("Unable to locate name/value separator for string: " + p_150311_0_);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 346 */     return p_150311_0_.substring(i + 1).trim();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int func_150312_a(String p_150312_0_, char p_150312_1_) {
/* 352 */     int i = 0;
/*     */     
/* 354 */     for (boolean flag = true; i < p_150312_0_.length(); i++) {
/*     */       
/* 356 */       char c0 = p_150312_0_.charAt(i);
/*     */       
/* 358 */       if (c0 == '"') {
/*     */         
/* 360 */         if (!func_179271_b(p_150312_0_, i))
/*     */         {
/* 362 */           flag = !flag;
/*     */         }
/*     */       }
/* 365 */       else if (flag) {
/*     */         
/* 367 */         if (c0 == p_150312_1_)
/*     */         {
/* 369 */           return i;
/*     */         }
/*     */         
/* 372 */         if (c0 == '{' || c0 == '[')
/*     */         {
/* 374 */           return -1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 379 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean func_179271_b(String p_179271_0_, int p_179271_1_) {
/* 384 */     return (p_179271_1_ > 0 && p_179271_0_.charAt(p_179271_1_ - 1) == '\\' && !func_179271_b(p_179271_0_, p_179271_1_ - 1));
/*     */   }
/*     */   
/*     */   static abstract class Any
/*     */   {
/*     */     protected String json;
/*     */     
/*     */     public abstract NBTBase parse() throws NBTException;
/*     */   }
/*     */   
/*     */   static class Compound
/*     */     extends Any {
/* 396 */     protected java.util.List<JsonToNBT.Any> field_150491_b = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     public Compound(String p_i45137_1_) {
/* 400 */       this.json = p_i45137_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public NBTBase parse() throws NBTException {
/* 405 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 407 */       for (JsonToNBT.Any jsontonbt$any : this.field_150491_b)
/*     */       {
/* 409 */         nbttagcompound.setTag(jsontonbt$any.json, jsontonbt$any.parse());
/*     */       }
/*     */       
/* 412 */       return nbttagcompound;
/*     */     }
/*     */   }
/*     */   
/*     */   static class List
/*     */     extends Any {
/* 418 */     protected java.util.List<JsonToNBT.Any> field_150492_b = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     public List(String json) {
/* 422 */       this.json = json;
/*     */     }
/*     */ 
/*     */     
/*     */     public NBTBase parse() throws NBTException {
/* 427 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 429 */       for (JsonToNBT.Any jsontonbt$any : this.field_150492_b)
/*     */       {
/* 431 */         nbttaglist.appendTag(jsontonbt$any.parse());
/*     */       }
/*     */       
/* 434 */       return nbttaglist;
/*     */     }
/*     */   }
/*     */   
/*     */   static class Primitive
/*     */     extends Any {
/* 440 */     private static final Pattern DOUBLE = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
/* 441 */     private static final Pattern FLOAT = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
/* 442 */     private static final Pattern BYTE = Pattern.compile("[-+]?[0-9]+[b|B]");
/* 443 */     private static final Pattern LONG = Pattern.compile("[-+]?[0-9]+[l|L]");
/* 444 */     private static final Pattern SHORT = Pattern.compile("[-+]?[0-9]+[s|S]");
/* 445 */     private static final Pattern INTEGER = Pattern.compile("[-+]?[0-9]+");
/* 446 */     private static final Pattern DOUBLE_UNTYPED = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
/* 447 */     private static final Splitter SPLITTER = Splitter.on(',').omitEmptyStrings();
/*     */     
/*     */     protected String jsonValue;
/*     */     
/*     */     public Primitive(String p_i45139_1_, String p_i45139_2_) {
/* 452 */       this.json = p_i45139_1_;
/* 453 */       this.jsonValue = p_i45139_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public NBTBase parse() {
/*     */       try {
/* 459 */         if (DOUBLE.matcher(this.jsonValue).matches())
/*     */         {
/* 461 */           return new NBTTagDouble(Double.parseDouble(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 464 */         if (FLOAT.matcher(this.jsonValue).matches())
/*     */         {
/* 466 */           return new NBTTagFloat(Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 469 */         if (BYTE.matcher(this.jsonValue).matches())
/*     */         {
/* 471 */           return new NBTTagByte(Byte.parseByte(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 474 */         if (LONG.matcher(this.jsonValue).matches())
/*     */         {
/* 476 */           return new NBTTagLong(Long.parseLong(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 479 */         if (SHORT.matcher(this.jsonValue).matches())
/*     */         {
/* 481 */           return new NBTTagShort(Short.parseShort(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
/*     */         }
/*     */         
/* 484 */         if (INTEGER.matcher(this.jsonValue).matches())
/*     */         {
/* 486 */           return new NBTTagInt(Integer.parseInt(this.jsonValue));
/*     */         }
/*     */         
/* 489 */         if (DOUBLE_UNTYPED.matcher(this.jsonValue).matches())
/*     */         {
/* 491 */           return new NBTTagDouble(Double.parseDouble(this.jsonValue));
/*     */         }
/*     */         
/* 494 */         if (this.jsonValue.equalsIgnoreCase("true") || this.jsonValue.equalsIgnoreCase("false"))
/*     */         {
/* 496 */           return new NBTTagByte((byte)(Boolean.parseBoolean(this.jsonValue) ? 1 : 0));
/*     */         }
/*     */       }
/* 499 */       catch (NumberFormatException var6) {
/*     */         
/* 501 */         this.jsonValue = this.jsonValue.replaceAll("\\\\\"", "\"");
/* 502 */         return new NBTTagString(this.jsonValue);
/*     */       } 
/*     */       
/* 505 */       if (!this.jsonValue.isEmpty() && this.jsonValue.charAt(0) == '[' && !this.jsonValue.isEmpty() && this.jsonValue.charAt(this.jsonValue.length() - 1) == ']') {
/*     */         
/* 507 */         String s = this.jsonValue.substring(1, this.jsonValue.length() - 1);
/* 508 */         String[] astring = (String[])Iterables.toArray(SPLITTER.split(s), String.class);
/*     */ 
/*     */         
/*     */         try {
/* 512 */           int[] aint = new int[astring.length];
/*     */           
/* 514 */           for (int j = 0; j < astring.length; j++)
/*     */           {
/* 516 */             aint[j] = Integer.parseInt(astring[j].trim());
/*     */           }
/*     */           
/* 519 */           return new NBTTagIntArray(aint);
/*     */         }
/* 521 */         catch (NumberFormatException var5) {
/*     */           
/* 523 */           return new NBTTagString(this.jsonValue);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 528 */       if (!this.jsonValue.isEmpty() && this.jsonValue.charAt(0) == '"' && !this.jsonValue.isEmpty() && this.jsonValue.charAt(this.jsonValue.length() - 1) == '"')
/*     */       {
/* 530 */         this.jsonValue = this.jsonValue.substring(1, this.jsonValue.length() - 1);
/*     */       }
/*     */       
/* 533 */       this.jsonValue = this.jsonValue.replaceAll("\\\\\"", "\"");
/* 534 */       StringBuilder stringbuilder = new StringBuilder();
/*     */       
/* 536 */       for (int i = 0; i < this.jsonValue.length(); i++) {
/*     */         
/* 538 */         if (i < this.jsonValue.length() - 1 && this.jsonValue.charAt(i) == '\\' && this.jsonValue.charAt(i + 1) == '\\') {
/*     */           
/* 540 */           stringbuilder.append('\\');
/* 541 */           i++;
/*     */         }
/*     */         else {
/*     */           
/* 545 */           stringbuilder.append(this.jsonValue.charAt(i));
/*     */         } 
/*     */       } 
/*     */       
/* 549 */       return new NBTTagString(stringbuilder.toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\nbt\JsonToNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */