/*     */ package awareline.main.mod.implement.misc;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.misc.EventChat;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AntiSpam
/*     */   extends Module
/*     */ {
/*     */   public AntiSpam() {
/*  17 */     super("AntiSpam", ModuleType.Misc);
/*     */   }
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
/*     */   @EventHandler
/*     */   public void onChat(EventChat event) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual getChatLines : ()Ljava/util/List;
/*     */     //   4: astore_2
/*     */     //   5: aload_2
/*     */     //   6: invokeinterface isEmpty : ()Z
/*     */     //   11: ifeq -> 15
/*     */     //   14: return
/*     */     //   15: getstatic awareline/main/mod/implement/misc/AntiSpam.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   18: getfield ingameGUI : Lnet/minecraft/client/gui/GuiIngame;
/*     */     //   21: invokevirtual getChatGUI : ()Lnet/minecraft/client/gui/GuiNewChat;
/*     */     //   24: astore_3
/*     */     //   25: aload_1
/*     */     //   26: invokevirtual getComponent : ()Lnet/minecraft/util/IChatComponent;
/*     */     //   29: aload_3
/*     */     //   30: invokevirtual getChatWidth : ()I
/*     */     //   33: i2f
/*     */     //   34: aload_3
/*     */     //   35: invokevirtual getChatScale : ()F
/*     */     //   38: fdiv
/*     */     //   39: invokestatic floor : (F)I
/*     */     //   42: getstatic awareline/main/mod/implement/misc/AntiSpam.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   45: getfield fontRendererObj : Lnet/minecraft/client/gui/FontRenderer;
/*     */     //   48: iconst_0
/*     */     //   49: iconst_0
/*     */     //   50: invokestatic splitText : (Lnet/minecraft/util/IChatComponent;ILnet/minecraft/client/gui/FontRenderer;ZZ)Ljava/util/List;
/*     */     //   53: astore #4
/*     */     //   55: iconst_1
/*     */     //   56: istore #5
/*     */     //   58: iconst_0
/*     */     //   59: istore #6
/*     */     //   61: aload_2
/*     */     //   62: invokeinterface size : ()I
/*     */     //   67: iconst_1
/*     */     //   68: isub
/*     */     //   69: istore #7
/*     */     //   71: iload #7
/*     */     //   73: iflt -> 457
/*     */     //   76: aload_2
/*     */     //   77: iload #7
/*     */     //   79: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   84: checkcast net/minecraft/client/gui/ChatLine
/*     */     //   87: invokevirtual getChatComponent : ()Lnet/minecraft/util/IChatComponent;
/*     */     //   90: invokeinterface getUnformattedText : ()Ljava/lang/String;
/*     */     //   95: astore #8
/*     */     //   97: iload #6
/*     */     //   99: aload #4
/*     */     //   101: invokeinterface size : ()I
/*     */     //   106: iconst_1
/*     */     //   107: isub
/*     */     //   108: if_icmpgt -> 419
/*     */     //   111: aload #4
/*     */     //   113: iload #6
/*     */     //   115: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   120: checkcast net/minecraft/util/IChatComponent
/*     */     //   123: invokeinterface getUnformattedText : ()Ljava/lang/String;
/*     */     //   128: astore #9
/*     */     //   130: iload #6
/*     */     //   132: aload #4
/*     */     //   134: invokeinterface size : ()I
/*     */     //   139: iconst_1
/*     */     //   140: isub
/*     */     //   141: if_icmpge -> 166
/*     */     //   144: aload #8
/*     */     //   146: aload #9
/*     */     //   148: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   151: ifeq -> 160
/*     */     //   154: iinc #6, 1
/*     */     //   157: goto -> 451
/*     */     //   160: iconst_0
/*     */     //   161: istore #6
/*     */     //   163: goto -> 451
/*     */     //   166: aload #8
/*     */     //   168: aload #9
/*     */     //   170: invokevirtual startsWith : (Ljava/lang/String;)Z
/*     */     //   173: ifne -> 182
/*     */     //   176: iconst_0
/*     */     //   177: istore #6
/*     */     //   179: goto -> 451
/*     */     //   182: iload #7
/*     */     //   184: ifle -> 316
/*     */     //   187: iload #6
/*     */     //   189: aload #4
/*     */     //   191: invokeinterface size : ()I
/*     */     //   196: iconst_1
/*     */     //   197: isub
/*     */     //   198: if_icmpne -> 316
/*     */     //   201: new java/lang/StringBuilder
/*     */     //   204: dup
/*     */     //   205: invokespecial <init> : ()V
/*     */     //   208: aload #8
/*     */     //   210: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   213: aload_2
/*     */     //   214: iload #7
/*     */     //   216: iconst_1
/*     */     //   217: isub
/*     */     //   218: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   223: checkcast net/minecraft/client/gui/ChatLine
/*     */     //   226: invokevirtual getChatComponent : ()Lnet/minecraft/util/IChatComponent;
/*     */     //   229: invokeinterface getUnformattedText : ()Ljava/lang/String;
/*     */     //   234: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   237: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   240: aload #9
/*     */     //   242: invokevirtual length : ()I
/*     */     //   245: invokevirtual substring : (I)Ljava/lang/String;
/*     */     //   248: astore #10
/*     */     //   250: aload #10
/*     */     //   252: ldc ' [x'
/*     */     //   254: invokevirtual startsWith : (Ljava/lang/String;)Z
/*     */     //   257: ifeq -> 316
/*     */     //   260: aload #10
/*     */     //   262: aload #10
/*     */     //   264: invokevirtual length : ()I
/*     */     //   267: iconst_1
/*     */     //   268: isub
/*     */     //   269: invokevirtual charAt : (I)C
/*     */     //   272: bipush #93
/*     */     //   274: if_icmpne -> 316
/*     */     //   277: aload #10
/*     */     //   279: iconst_3
/*     */     //   280: aload #10
/*     */     //   282: invokevirtual length : ()I
/*     */     //   285: iconst_1
/*     */     //   286: isub
/*     */     //   287: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   290: astore #11
/*     */     //   292: aload #11
/*     */     //   294: invokestatic isInteger : (Ljava/lang/String;)Z
/*     */     //   297: ifeq -> 316
/*     */     //   300: iload #5
/*     */     //   302: aload #11
/*     */     //   304: invokestatic parseInt : (Ljava/lang/String;)I
/*     */     //   307: iadd
/*     */     //   308: istore #5
/*     */     //   310: iinc #6, 1
/*     */     //   313: goto -> 451
/*     */     //   316: aload #8
/*     */     //   318: invokevirtual length : ()I
/*     */     //   321: aload #9
/*     */     //   323: invokevirtual length : ()I
/*     */     //   326: if_icmpne -> 335
/*     */     //   329: iinc #5, 1
/*     */     //   332: goto -> 419
/*     */     //   335: aload #8
/*     */     //   337: aload #9
/*     */     //   339: invokevirtual length : ()I
/*     */     //   342: invokevirtual substring : (I)Ljava/lang/String;
/*     */     //   345: astore #10
/*     */     //   347: aload #10
/*     */     //   349: ldc ' [x'
/*     */     //   351: invokevirtual startsWith : (Ljava/lang/String;)Z
/*     */     //   354: ifeq -> 374
/*     */     //   357: aload #10
/*     */     //   359: aload #10
/*     */     //   361: invokevirtual length : ()I
/*     */     //   364: iconst_1
/*     */     //   365: isub
/*     */     //   366: invokevirtual charAt : (I)C
/*     */     //   369: bipush #93
/*     */     //   371: if_icmpeq -> 380
/*     */     //   374: iconst_0
/*     */     //   375: istore #6
/*     */     //   377: goto -> 451
/*     */     //   380: aload #10
/*     */     //   382: iconst_3
/*     */     //   383: aload #10
/*     */     //   385: invokevirtual length : ()I
/*     */     //   388: iconst_1
/*     */     //   389: isub
/*     */     //   390: invokevirtual substring : (II)Ljava/lang/String;
/*     */     //   393: astore #11
/*     */     //   395: aload #11
/*     */     //   397: invokestatic isInteger : (Ljava/lang/String;)Z
/*     */     //   400: ifne -> 409
/*     */     //   403: iconst_0
/*     */     //   404: istore #6
/*     */     //   406: goto -> 451
/*     */     //   409: iload #5
/*     */     //   411: aload #11
/*     */     //   413: invokestatic parseInt : (Ljava/lang/String;)I
/*     */     //   416: iadd
/*     */     //   417: istore #5
/*     */     //   419: iload #7
/*     */     //   421: iload #6
/*     */     //   423: iadd
/*     */     //   424: istore #9
/*     */     //   426: iload #9
/*     */     //   428: iload #7
/*     */     //   430: if_icmplt -> 448
/*     */     //   433: aload_2
/*     */     //   434: iload #9
/*     */     //   436: invokeinterface remove : (I)Ljava/lang/Object;
/*     */     //   441: pop
/*     */     //   442: iinc #9, -1
/*     */     //   445: goto -> 426
/*     */     //   448: iconst_0
/*     */     //   449: istore #6
/*     */     //   451: iinc #7, -1
/*     */     //   454: goto -> 71
/*     */     //   457: iload #5
/*     */     //   459: iconst_1
/*     */     //   460: if_icmple -> 498
/*     */     //   463: aload_1
/*     */     //   464: invokevirtual getComponent : ()Lnet/minecraft/util/IChatComponent;
/*     */     //   467: new java/lang/StringBuilder
/*     */     //   470: dup
/*     */     //   471: invokespecial <init> : ()V
/*     */     //   474: ldc ' [x'
/*     */     //   476: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   479: iload #5
/*     */     //   481: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   484: ldc ']'
/*     */     //   486: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   489: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   492: invokeinterface appendText : (Ljava/lang/String;)Lnet/minecraft/util/IChatComponent;
/*     */     //   497: pop
/*     */     //   498: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #22	-> 0
/*     */     //   #23	-> 5
/*     */     //   #24	-> 14
/*     */     //   #26	-> 15
/*     */     //   #27	-> 25
/*     */     //   #28	-> 55
/*     */     //   #29	-> 58
/*     */     //   #30	-> 61
/*     */     //   #31	-> 71
/*     */     //   #32	-> 76
/*     */     //   #35	-> 97
/*     */     //   #36	-> 111
/*     */     //   #37	-> 130
/*     */     //   #38	-> 144
/*     */     //   #39	-> 154
/*     */     //   #41	-> 160
/*     */     //   #43	-> 163
/*     */     //   #44	-> 166
/*     */     //   #45	-> 176
/*     */     //   #46	-> 179
/*     */     //   #48	-> 182
/*     */     //   #49	-> 201
/*     */     //   #50	-> 250
/*     */     //   #51	-> 277
/*     */     //   #52	-> 292
/*     */     //   #53	-> 300
/*     */     //   #54	-> 310
/*     */     //   #55	-> 313
/*     */     //   #59	-> 316
/*     */     //   #60	-> 329
/*     */     //   #62	-> 335
/*     */     //   #63	-> 347
/*     */     //   #64	-> 374
/*     */     //   #65	-> 377
/*     */     //   #67	-> 380
/*     */     //   #68	-> 395
/*     */     //   #69	-> 403
/*     */     //   #70	-> 406
/*     */     //   #72	-> 409
/*     */     //   #78	-> 419
/*     */     //   #79	-> 426
/*     */     //   #80	-> 433
/*     */     //   #81	-> 442
/*     */     //   #83	-> 448
/*     */     //   #85	-> 451
/*     */     //   #86	-> 454
/*     */     //   #87	-> 457
/*     */     //   #88	-> 463
/*     */     //   #90	-> 498
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   292	24	11	substring2	Ljava/lang/String;
/*     */     //   250	66	10	substring	Ljava/lang/String;
/*     */     //   395	24	11	substring4	Ljava/lang/String;
/*     */     //   347	72	10	substring3	Ljava/lang/String;
/*     */     //   130	289	9	unformattedText2	Ljava/lang/String;
/*     */     //   426	25	9	j	I
/*     */     //   97	357	8	unformattedText	Ljava/lang/String;
/*     */     //   0	499	0	this	Lawareline/main/mod/implement/misc/AntiSpam;
/*     */     //   0	499	1	event	Lawareline/main/event/events/misc/EventChat;
/*     */     //   5	494	2	chatLines	Ljava/util/List;
/*     */     //   25	474	3	chatGUI	Lnet/minecraft/client/gui/GuiNewChat;
/*     */     //   55	444	4	splitText	Ljava/util/List;
/*     */     //   58	441	5	n	I
/*     */     //   61	438	6	n2	I
/*     */     //   71	428	7	i	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   5	494	2	chatLines	Ljava/util/List<Lnet/minecraft/client/gui/ChatLine;>;
/*     */     //   55	444	4	splitText	Ljava/util/List<Lnet/minecraft/util/IChatComponent;>;
/*     */   }
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
/*     */   private static boolean isInteger(String s) {
/*     */     try {
/*  94 */       Integer.parseInt(s);
/*  95 */       return true;
/*  96 */     } catch (NumberFormatException ex) {
/*  97 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int floor(float n) {
/* 102 */     int n3, n2 = (int)n;
/*     */     
/* 104 */     if (n < n2) {
/* 105 */       n3 = n2 - 1;
/*     */     } else {
/* 107 */       n3 = n2;
/*     */     } 
/* 109 */     return n3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\AntiSpam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */