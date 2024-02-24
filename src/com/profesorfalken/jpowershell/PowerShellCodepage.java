/*     */ package com.profesorfalken.jpowershell;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PowerShellCodepage
/*     */ {
/*  28 */   private static final Map<String, String> codePages = new HashMap<>();
/*     */   
/*     */   static {
/*  31 */     codePages.put("37", "IBM037");
/*  32 */     codePages.put("437", "IBM437");
/*  33 */     codePages.put("500", "IBM500");
/*  34 */     codePages.put("708", "ASMO-708");
/*  35 */     codePages.put("709", "");
/*  36 */     codePages.put("710", "");
/*  37 */     codePages.put("720", "DOS-720");
/*  38 */     codePages.put("737", "ibm737");
/*  39 */     codePages.put("775", "ibm775");
/*  40 */     codePages.put("850", "ibm850");
/*  41 */     codePages.put("852", "ibm852");
/*  42 */     codePages.put("855", "IBM855");
/*  43 */     codePages.put("857", "ibm857");
/*  44 */     codePages.put("858", "IBM00858");
/*  45 */     codePages.put("860", "IBM860");
/*  46 */     codePages.put("861", "ibm861");
/*  47 */     codePages.put("862", "DOS-862");
/*  48 */     codePages.put("863", "IBM863");
/*  49 */     codePages.put("864", "IBM864");
/*  50 */     codePages.put("865", "IBM865");
/*  51 */     codePages.put("866", "cp866");
/*  52 */     codePages.put("869", "ibm869");
/*  53 */     codePages.put("870", "IBM870");
/*  54 */     codePages.put("874", "windows-874");
/*  55 */     codePages.put("875", "cp875");
/*  56 */     codePages.put("932", "shift_jis");
/*  57 */     codePages.put("936", "gb2312");
/*  58 */     codePages.put("949", "ks_c_5601-1987");
/*  59 */     codePages.put("950", "big5");
/*  60 */     codePages.put("1026", "IBM1026");
/*  61 */     codePages.put("1047", "IBM01047");
/*  62 */     codePages.put("1140", "IBM01140");
/*  63 */     codePages.put("1141", "IBM01141");
/*  64 */     codePages.put("1142", "IBM01142");
/*  65 */     codePages.put("1143", "IBM01143");
/*  66 */     codePages.put("1144", "IBM01144");
/*  67 */     codePages.put("1145", "IBM01145");
/*  68 */     codePages.put("1146", "IBM01146");
/*  69 */     codePages.put("1147", "IBM01147");
/*  70 */     codePages.put("1148", "IBM01148");
/*  71 */     codePages.put("1149", "IBM01149");
/*  72 */     codePages.put("1200", "utf-16");
/*  73 */     codePages.put("1201", "unicodeFFFE");
/*  74 */     codePages.put("1250", "windows-1250");
/*  75 */     codePages.put("1251", "windows-1251");
/*  76 */     codePages.put("1252", "windows-1252");
/*  77 */     codePages.put("1253", "windows-1253");
/*  78 */     codePages.put("1254", "windows-1254");
/*  79 */     codePages.put("1255", "windows-1255");
/*  80 */     codePages.put("1256", "windows-1256");
/*  81 */     codePages.put("1257", "windows-1257");
/*  82 */     codePages.put("1258", "windows-1258");
/*  83 */     codePages.put("1361", "Johab");
/*  84 */     codePages.put("10000", "macintosh");
/*  85 */     codePages.put("10001", "x-mac-japanese");
/*  86 */     codePages.put("10002", "x-mac-chinesetrad");
/*  87 */     codePages.put("10003", "x-mac-korean");
/*  88 */     codePages.put("10004", "x-mac-arabic");
/*  89 */     codePages.put("10005", "x-mac-hebrew");
/*  90 */     codePages.put("10006", "x-mac-greek");
/*  91 */     codePages.put("10007", "x-mac-cyrillic");
/*  92 */     codePages.put("10008", "x-mac-chinesesimp");
/*  93 */     codePages.put("10010", "x-mac-romanian");
/*  94 */     codePages.put("10017", "x-mac-ukrainian");
/*  95 */     codePages.put("10021", "x-mac-thai");
/*  96 */     codePages.put("10029", "x-mac-ce");
/*  97 */     codePages.put("10079", "x-mac-icelandic");
/*  98 */     codePages.put("10081", "x-mac-turkish");
/*  99 */     codePages.put("10082", "x-mac-croatian");
/* 100 */     codePages.put("12000", "utf-32");
/* 101 */     codePages.put("12001", "utf-32BE");
/* 102 */     codePages.put("20000", "x-Chinese_CNS");
/* 103 */     codePages.put("20001", "x-cp20001");
/* 104 */     codePages.put("20002", "x_Chinese-Eten");
/* 105 */     codePages.put("20003", "x-cp20003");
/* 106 */     codePages.put("20004", "x-cp20004");
/* 107 */     codePages.put("20005", "x-cp20005");
/* 108 */     codePages.put("20105", "x-IA5");
/* 109 */     codePages.put("20106", "x-IA5-German");
/* 110 */     codePages.put("20107", "x-IA5-Swedish");
/* 111 */     codePages.put("20108", "x-IA5-Norwegian");
/* 112 */     codePages.put("20127", "us-ascii");
/* 113 */     codePages.put("20261", "x-cp20261");
/* 114 */     codePages.put("20269", "x-cp20269");
/* 115 */     codePages.put("20273", "IBM273");
/* 116 */     codePages.put("20277", "IBM277");
/* 117 */     codePages.put("20278", "IBM278");
/* 118 */     codePages.put("20280", "IBM280");
/* 119 */     codePages.put("20284", "IBM284");
/* 120 */     codePages.put("20285", "IBM285");
/* 121 */     codePages.put("20290", "IBM290");
/* 122 */     codePages.put("20297", "IBM297");
/* 123 */     codePages.put("20420", "IBM420");
/* 124 */     codePages.put("20423", "IBM423");
/* 125 */     codePages.put("20424", "IBM424");
/* 126 */     codePages.put("20833", "x-EBCDIC-KoreanExtended");
/* 127 */     codePages.put("20838", "IBM-Thai");
/* 128 */     codePages.put("20866", "koi8-r");
/* 129 */     codePages.put("20871", "IBM871");
/* 130 */     codePages.put("20880", "IBM880");
/* 131 */     codePages.put("20905", "IBM905");
/* 132 */     codePages.put("20924", "IBM00924");
/* 133 */     codePages.put("20932", "EUC-JP");
/* 134 */     codePages.put("20936", "x-cp20936");
/* 135 */     codePages.put("20949", "x-cp20949");
/* 136 */     codePages.put("21025", "cp1025");
/* 137 */     codePages.put("21027", "");
/* 138 */     codePages.put("21866", "koi8-u");
/* 139 */     codePages.put("28591", "iso-8859-1");
/* 140 */     codePages.put("28592", "iso-8859-2");
/* 141 */     codePages.put("28593", "iso-8859-3");
/* 142 */     codePages.put("28594", "iso-8859-4");
/* 143 */     codePages.put("28595", "iso-8859-5");
/* 144 */     codePages.put("28596", "iso-8859-6");
/* 145 */     codePages.put("28597", "iso-8859-7");
/* 146 */     codePages.put("28598", "iso-8859-8");
/* 147 */     codePages.put("28599", "iso-8859-9");
/* 148 */     codePages.put("28603", "iso-8859-13");
/* 149 */     codePages.put("28605", "iso-8859-15");
/* 150 */     codePages.put("29001", "x-Europa");
/* 151 */     codePages.put("38598", "iso-8859-8-i");
/* 152 */     codePages.put("50220", "iso-2022-jp");
/* 153 */     codePages.put("50221", "csISO2022JP");
/* 154 */     codePages.put("50222", "iso-2022-jp");
/* 155 */     codePages.put("50225", "iso-2022-kr");
/* 156 */     codePages.put("50227", "x-cp50227");
/* 157 */     codePages.put("50229", "");
/* 158 */     codePages.put("50930", "");
/* 159 */     codePages.put("50931", "");
/* 160 */     codePages.put("50933", "");
/* 161 */     codePages.put("50935", "");
/* 162 */     codePages.put("50936", "");
/* 163 */     codePages.put("50937", "");
/* 164 */     codePages.put("50939", "");
/* 165 */     codePages.put("51932", "euc-jp");
/* 166 */     codePages.put("51936", "EUC-CN");
/* 167 */     codePages.put("51949", "euc-kr");
/* 168 */     codePages.put("51950", "");
/* 169 */     codePages.put("52936", "hz-gb-2312");
/* 170 */     codePages.put("54936", "GB18030");
/* 171 */     codePages.put("57002", "x-iscii-de");
/* 172 */     codePages.put("57003", "x-iscii-be");
/* 173 */     codePages.put("57004", "x-iscii-ta");
/* 174 */     codePages.put("57005", "x-iscii-te");
/* 175 */     codePages.put("57006", "x-iscii-as");
/* 176 */     codePages.put("57007", "x-iscii-or");
/* 177 */     codePages.put("57008", "x-iscii-ka");
/* 178 */     codePages.put("57009", "x-iscii-ma");
/* 179 */     codePages.put("57010", "x-iscii-gu");
/* 180 */     codePages.put("57011", "x-iscii-pa");
/* 181 */     codePages.put("65000", "utf-7");
/* 182 */     codePages.put("65001", "utf-8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCodePageNameByIdetifier(String cpIdentifier) {
/* 192 */     return codePages.get(cpIdentifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getIdentifierByCodePageName(String cpName) {
/* 202 */     if (cpName != null) {
/* 203 */       for (Map.Entry<String, String> codePage : codePages.entrySet()) {
/* 204 */         if (((String)codePage.getValue()).toLowerCase().equals(cpName.toLowerCase())) {
/* 205 */           return codePage.getKey();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 210 */     return "65001";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\jpowershell\PowerShellCodepage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */