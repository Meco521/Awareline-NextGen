/*     */ package net.optifine.config;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagDouble;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ 
/*     */ public class NbtTagValue {
/*  13 */   private String[] parents = null;
/*  14 */   private String name = null;
/*     */   private boolean negative = false;
/*  16 */   private int type = 0;
/*  17 */   private String value = null;
/*  18 */   private int valueFormat = 0;
/*     */   private static final int TYPE_TEXT = 0;
/*     */   private static final int TYPE_PATTERN = 1;
/*     */   private static final int TYPE_IPATTERN = 2;
/*     */   private static final int TYPE_REGEX = 3;
/*     */   private static final int TYPE_IREGEX = 4;
/*     */   private static final String PREFIX_PATTERN = "pattern:";
/*     */   private static final String PREFIX_IPATTERN = "ipattern:";
/*     */   private static final String PREFIX_REGEX = "regex:";
/*     */   private static final String PREFIX_IREGEX = "iregex:";
/*     */   private static final int FORMAT_DEFAULT = 0;
/*     */   private static final int FORMAT_HEX_COLOR = 1;
/*     */   private static final String PREFIX_HEX_COLOR = "#";
/*  31 */   private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[\\da-f]{6}+$");
/*     */ 
/*     */   
/*     */   public NbtTagValue(String tag, String value) {
/*  35 */     String[] astring = Config.tokenize(tag, ".");
/*  36 */     this.parents = Arrays.<String>copyOfRange(astring, 0, astring.length - 1);
/*  37 */     this.name = astring[astring.length - 1];
/*     */     
/*  39 */     if (!value.isEmpty() && value.charAt(0) == '!') {
/*     */       
/*  41 */       this.negative = true;
/*  42 */       value = value.substring(1);
/*     */     } 
/*     */     
/*  45 */     if (value.startsWith("pattern:")) {
/*     */       
/*  47 */       this.type = 1;
/*  48 */       value = value.substring("pattern:".length());
/*     */     }
/*  50 */     else if (value.startsWith("ipattern:")) {
/*     */       
/*  52 */       this.type = 2;
/*  53 */       value = value.substring("ipattern:".length()).toLowerCase();
/*     */     }
/*  55 */     else if (value.startsWith("regex:")) {
/*     */       
/*  57 */       this.type = 3;
/*  58 */       value = value.substring("regex:".length());
/*     */     }
/*  60 */     else if (value.startsWith("iregex:")) {
/*     */       
/*  62 */       this.type = 4;
/*  63 */       value = value.substring("iregex:".length()).toLowerCase();
/*     */     }
/*     */     else {
/*     */       
/*  67 */       this.type = 0;
/*     */     } 
/*     */     
/*  70 */     value = StringEscapeUtils.unescapeJava(value);
/*     */     
/*  72 */     if (this.type == 0 && PATTERN_HEX_COLOR.matcher(value).matches())
/*     */     {
/*  74 */       this.valueFormat = 1;
/*     */     }
/*     */     
/*  77 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(NBTTagCompound nbt) {
/*  82 */     return this.negative ? (!matchesCompound(nbt)) : matchesCompound(nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesCompound(NBTTagCompound nbt) {
/*  87 */     if (nbt == null)
/*     */     {
/*  89 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  93 */     NBTTagCompound nBTTagCompound = nbt;
/*     */     
/*  95 */     for (int i = 0; i < this.parents.length; i++) {
/*     */       
/*  97 */       String s = this.parents[i];
/*  98 */       nBTBase = getChildTag((NBTBase)nBTTagCompound, s);
/*     */       
/* 100 */       if (nBTBase == null)
/*     */       {
/* 102 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     if (this.name.equals("*"))
/*     */     {
/* 108 */       return matchesAnyChild(nBTBase);
/*     */     }
/*     */ 
/*     */     
/* 112 */     NBTBase nBTBase = getChildTag(nBTBase, this.name);
/*     */     
/* 114 */     if (nBTBase == null)
/*     */     {
/* 116 */       return false;
/*     */     }
/* 118 */     if (matchesBase(nBTBase))
/*     */     {
/* 120 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesAnyChild(NBTBase tagBase) {
/* 132 */     if (tagBase instanceof NBTTagCompound) {
/*     */       
/* 134 */       NBTTagCompound nbttagcompound = (NBTTagCompound)tagBase;
/*     */       
/* 136 */       for (String s : nbttagcompound.getKeySet()) {
/*     */         
/* 138 */         NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */         
/* 140 */         if (matchesBase(nbtbase))
/*     */         {
/* 142 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     if (tagBase instanceof NBTTagList) {
/*     */       
/* 149 */       NBTTagList nbttaglist = (NBTTagList)tagBase;
/* 150 */       int i = nbttaglist.tagCount();
/*     */       
/* 152 */       for (int j = 0; j < i; j++) {
/*     */         
/* 154 */         NBTBase nbtbase1 = nbttaglist.get(j);
/*     */         
/* 156 */         if (matchesBase(nbtbase1))
/*     */         {
/* 158 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static NBTBase getChildTag(NBTBase tagBase, String tag) {
/* 168 */     if (tagBase instanceof NBTTagCompound) {
/*     */       
/* 170 */       NBTTagCompound nbttagcompound = (NBTTagCompound)tagBase;
/* 171 */       return nbttagcompound.getTag(tag);
/*     */     } 
/* 173 */     if (tagBase instanceof NBTTagList) {
/*     */       
/* 175 */       NBTTagList nbttaglist = (NBTTagList)tagBase;
/*     */       
/* 177 */       if (tag.equals("count"))
/*     */       {
/* 179 */         return (NBTBase)new NBTTagInt(nbttaglist.tagCount());
/*     */       }
/*     */ 
/*     */       
/* 183 */       int i = Config.parseInt(tag, -1);
/* 184 */       return (i >= 0 && i < nbttaglist.tagCount()) ? nbttaglist.get(i) : null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesBase(NBTBase nbtBase) {
/* 195 */     if (nbtBase == null)
/*     */     {
/* 197 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 201 */     String s = getNbtString(nbtBase, this.valueFormat);
/* 202 */     return matchesValue(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesValue(String nbtValue) {
/* 208 */     if (nbtValue == null)
/*     */     {
/* 210 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 214 */     switch (this.type) {
/*     */       
/*     */       case 0:
/* 217 */         return nbtValue.equals(this.value);
/*     */       
/*     */       case 1:
/* 220 */         return matchesPattern(nbtValue, this.value);
/*     */       
/*     */       case 2:
/* 223 */         return matchesPattern(nbtValue.toLowerCase(), this.value);
/*     */       
/*     */       case 3:
/* 226 */         return matchesRegex(nbtValue, this.value);
/*     */       
/*     */       case 4:
/* 229 */         return matchesRegex(nbtValue.toLowerCase(), this.value);
/*     */     } 
/*     */     
/* 232 */     throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesPattern(String str, String pattern) {
/* 239 */     return StrUtils.equalsMask(str, pattern, '*', '?');
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesRegex(String str, String regex) {
/* 244 */     return str.matches(regex);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getNbtString(NBTBase nbtBase, int format) {
/* 249 */     if (nbtBase == null)
/*     */     {
/* 251 */       return null;
/*     */     }
/* 253 */     if (nbtBase instanceof NBTTagString) {
/*     */       
/* 255 */       NBTTagString nbttagstring = (NBTTagString)nbtBase;
/* 256 */       return nbttagstring.getString();
/*     */     } 
/* 258 */     if (nbtBase instanceof NBTTagInt) {
/*     */       
/* 260 */       NBTTagInt nbttagint = (NBTTagInt)nbtBase;
/* 261 */       return (format == 1) ? ("#" + StrUtils.fillLeft(Integer.toHexString(nbttagint.getInt()), 6, '0')) : Integer.toString(nbttagint.getInt());
/*     */     } 
/* 263 */     if (nbtBase instanceof NBTTagByte) {
/*     */       
/* 265 */       NBTTagByte nbttagbyte = (NBTTagByte)nbtBase;
/* 266 */       return Byte.toString(nbttagbyte.getByte());
/*     */     } 
/* 268 */     if (nbtBase instanceof NBTTagShort) {
/*     */       
/* 270 */       NBTTagShort nbttagshort = (NBTTagShort)nbtBase;
/* 271 */       return Short.toString(nbttagshort.getShort());
/*     */     } 
/* 273 */     if (nbtBase instanceof NBTTagLong) {
/*     */       
/* 275 */       NBTTagLong nbttaglong = (NBTTagLong)nbtBase;
/* 276 */       return Long.toString(nbttaglong.getLong());
/*     */     } 
/* 278 */     if (nbtBase instanceof NBTTagFloat) {
/*     */       
/* 280 */       NBTTagFloat nbttagfloat = (NBTTagFloat)nbtBase;
/* 281 */       return Float.toString(nbttagfloat.getFloat());
/*     */     } 
/* 283 */     if (nbtBase instanceof NBTTagDouble) {
/*     */       
/* 285 */       NBTTagDouble nbttagdouble = (NBTTagDouble)nbtBase;
/* 286 */       return Double.toString(nbttagdouble.getDouble());
/*     */     } 
/*     */ 
/*     */     
/* 290 */     return nbtBase.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 296 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 298 */     for (int i = 0; i < this.parents.length; i++) {
/*     */       
/* 300 */       String s = this.parents[i];
/*     */       
/* 302 */       if (i > 0)
/*     */       {
/* 304 */         stringbuffer.append(".");
/*     */       }
/*     */       
/* 307 */       stringbuffer.append(s);
/*     */     } 
/*     */     
/* 310 */     if (stringbuffer.length() > 0)
/*     */     {
/* 312 */       stringbuffer.append(".");
/*     */     }
/*     */     
/* 315 */     stringbuffer.append(this.name);
/* 316 */     stringbuffer.append(" = ");
/* 317 */     stringbuffer.append(this.value);
/* 318 */     return stringbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\config\NbtTagValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */