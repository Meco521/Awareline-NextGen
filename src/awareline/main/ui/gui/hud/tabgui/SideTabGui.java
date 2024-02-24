/*     */ package awareline.main.ui.gui.hud.tabgui;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*     */ import awareline.main.event.events.misc.EventKey;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SideTabGui
/*     */ {
/*     */   public float baseCategoryWidth;
/*     */   public float baseCategoryHeight;
/*     */   public int categoryTab;
/*  27 */   public float categoryPosition = 22.0F;
/*  28 */   public float modPosition = 22.0F;
/*     */   
/*     */   public float shouldY;
/*     */   float hue;
/*     */   float h;
/*  33 */   private Section section = Section.CATEGORY; float h2; float h3; int hudMode;
/*     */   private int modTab;
/*  35 */   private float categoryTargetPosition = 22.0F;
/*  36 */   private float modTargetPosition = 22.0F; private float modulesXanim; private int alpha;
/*     */   private Color color33;
/*     */   private Color color332;
/*     */   private Color color333;
/*     */   
/*     */   public void init() {
/*  42 */     int highestWidth = 0;
/*     */     ModuleType[] values;
/*  44 */     int length = (values = ModuleType.values()).length;
/*  45 */     for (int i = 0; i < length; i++) {
/*  46 */       ModuleType moduleType = values[i];
/*  47 */       String name = Character.toUpperCase(moduleType.name().charAt(0)) + moduleType.name().substring(1);
/*  48 */       int stringWidth = Client.instance.FontLoaders.Comfortaa18.getStringWidth(name);
/*  49 */       highestWidth = Math.max(stringWidth, highestWidth);
/*     */     } 
/*  51 */     this.baseCategoryWidth = (highestWidth + 25);
/*  52 */     this.baseCategoryHeight = (((ModuleType.values()).length - 3) * 14 + 2);
/*     */   }
/*     */   
/*     */   @EventHandler(4)
/*     */   public void onKey(EventKey e) {
/*  57 */     if (!HUD.getInstance.isEnabled()) {
/*  58 */       if (!((Boolean)HUD.getInstance.tabGui.get()).booleanValue()) {
/*     */         return;
/*     */       }
/*  61 */       if ((Minecraft.getMinecraft()).gameSettings.showDebugInfo) {
/*     */         return;
/*     */       }
/*     */       return;
/*     */     } 
/*  66 */     if (!((Boolean)HUD.getInstance.tabGui.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*  69 */     if ((Minecraft.getMinecraft()).gameSettings.showDebugInfo) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  74 */     if ((Minecraft.getMinecraft()).currentScreen != null) {
/*     */       return;
/*     */     }
/*  77 */     if (this.section == Section.CATEGORY) {
/*  78 */       int highestWidth; Iterator<Module> var3; switch (e.getKey()) {
/*     */         default:
/*     */           return;
/*     */         
/*     */         case 200:
/*  83 */           this.categoryTab--;
/*  84 */           this.categoryTargetPosition -= 12.0F;
/*  85 */           if (this.categoryTab < 0) {
/*  86 */             this.categoryTargetPosition = (22 + ((ModuleType.values()).length - 3) * 12);
/*  87 */             this.categoryTab = (ModuleType.values()).length - 3;
/*     */           } 
/*     */         
/*     */         case 205:
/*  91 */           highestWidth = 0;
/*     */ 
/*     */           
/*  94 */           for (var3 = getModsInCategory(ModuleType.values()[this.categoryTab]).iterator(); var3.hasNext(); highestWidth = Math.max(stringWidth, highestWidth)) {
/*  95 */             Module module = var3.next();
/*  96 */             String name = Character.toUpperCase(module.getHUDName().charAt(0)) + module.getHUDName().substring(1);
/*  97 */             int stringWidth = Client.instance.FontManager.baloo18.getStringWidth(name);
/*     */           } 
/*     */           
/* 100 */           this.modTargetPosition = this.modPosition = this.categoryTargetPosition;
/* 101 */           this.modTab = 0;
/* 102 */           this.section = Section.MODS;
/* 103 */           this.modulesXanim = 0.0F;
/* 104 */           this.alpha = 0;
/*     */         case 208:
/*     */           break;
/* 107 */       }  this.categoryTab++;
/* 108 */       this.categoryTargetPosition += 12.0F;
/* 109 */       if (this.categoryTab > (ModuleType.values()).length - 3) {
/* 110 */         this.categoryTargetPosition = 22.0F;
/* 111 */         this.categoryTab = 0;
/*     */       } 
/*     */     } 
/* 114 */     if (this.section == Section.MODS) {
/*     */       Module mod;
/* 116 */       switch (e.getKey()) {
/*     */         case 28:
/*     */         case 205:
/* 119 */           mod = getModsInCategory(ModuleType.values()[this.categoryTab]).get(this.modTab);
/* 120 */           mod.setEnabled(!mod.isEnabled());
/*     */ 
/*     */ 
/*     */         
/*     */         case 200:
/* 125 */           this.modTab--;
/* 126 */           this.modTargetPosition -= 12.0F;
/* 127 */           if (this.modTab < 0) {
/* 128 */             this.modTargetPosition = this.categoryTargetPosition + ((getModsInCategory(ModuleType.values()[this.categoryTab]).size() - 1) * 12);
/* 129 */             this.modTab = getModsInCategory(ModuleType.values()[this.categoryTab]).size() - 1;
/*     */           } 
/*     */ 
/*     */         
/*     */         case 203:
/* 134 */           this.section = Section.CATEGORY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 208:
/* 141 */           this.modTab++;
/* 142 */           this.modTargetPosition += 12.0F;
/* 143 */           if (this.modTab > getModsInCategory(ModuleType.values()[this.categoryTab]).size() - 1) {
/* 144 */             this.modTargetPosition = this.categoryTargetPosition;
/* 145 */             this.modTab = 0;
/*     */           } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateBars() {
/* 155 */     if (!HUD.getInstance.isEnabled()) {
/* 156 */       if (!((Boolean)HUD.getInstance.tabGui.get()).booleanValue()) {
/*     */         return;
/*     */       }
/* 159 */       if ((Minecraft.getMinecraft()).gameSettings.showDebugInfo) {
/*     */         return;
/*     */       }
/*     */       return;
/*     */     } 
/* 164 */     if (!((Boolean)HUD.getInstance.tabGui.get()).booleanValue()) {
/*     */       return;
/*     */     }
/* 167 */     if ((Minecraft.getMinecraft()).gameSettings.showDebugInfo) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 172 */     if (this.categoryPosition < this.categoryTargetPosition) {
/* 173 */       this.categoryPosition = AnimationUtil.moveUD(this.categoryPosition, this.categoryTargetPosition, 
/* 174 */           SimpleRender.processFPS(0.015F), SimpleRender.processFPS(0.013F));
/* 175 */       if (this.categoryPosition >= this.categoryTargetPosition) {
/* 176 */         this.categoryPosition = this.categoryTargetPosition;
/*     */       }
/* 178 */     } else if (this.categoryPosition > this.categoryTargetPosition) {
/* 179 */       this.categoryPosition = AnimationUtil.moveUD(this.categoryPosition, this.categoryTargetPosition, 
/* 180 */           SimpleRender.processFPS(0.015F), SimpleRender.processFPS(0.013F));
/* 181 */       if (this.categoryPosition <= this.categoryTargetPosition) {
/* 182 */         this.categoryPosition = this.categoryTargetPosition;
/*     */       }
/*     */     } 
/*     */     
/* 186 */     if (this.modPosition < this.modTargetPosition) {
/* 187 */       this.modPosition = AnimationUtil.moveUD(this.modPosition, this.modTargetPosition, 
/* 188 */           SimpleRender.processFPS(0.015F), SimpleRender.processFPS(0.013F));
/* 189 */       if (this.modPosition >= this.modTargetPosition) {
/* 190 */         this.modPosition = this.modTargetPosition;
/*     */       }
/* 192 */     } else if (this.modPosition > this.modTargetPosition) {
/* 193 */       this.modPosition = AnimationUtil.moveUD(this.modPosition, this.modTargetPosition, 
/* 194 */           SimpleRender.processFPS(0.015F), SimpleRender.processFPS(0.013F));
/* 195 */       if (this.modPosition <= this.modTargetPosition) {
/* 196 */         this.modPosition = this.modTargetPosition;
/*     */       }
/*     */     } 
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
/*     */   public void renderTabGui(EventRender2D e) {
/*     */     // Byte code:
/*     */     //   0: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   3: invokevirtual isEnabled : ()Z
/*     */     //   6: ifne -> 42
/*     */     //   9: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   12: getfield tabGui : Lawareline/main/mod/values/Option;
/*     */     //   15: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   18: checkcast java/lang/Boolean
/*     */     //   21: invokevirtual booleanValue : ()Z
/*     */     //   24: ifne -> 28
/*     */     //   27: return
/*     */     //   28: invokestatic getMinecraft : ()Lnet/minecraft/client/Minecraft;
/*     */     //   31: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*     */     //   34: getfield showDebugInfo : Z
/*     */     //   37: ifeq -> 41
/*     */     //   40: return
/*     */     //   41: return
/*     */     //   42: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   45: getfield tabGui : Lawareline/main/mod/values/Option;
/*     */     //   48: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   51: checkcast java/lang/Boolean
/*     */     //   54: invokevirtual booleanValue : ()Z
/*     */     //   57: ifne -> 61
/*     */     //   60: return
/*     */     //   61: invokestatic getMinecraft : ()Lnet/minecraft/client/Minecraft;
/*     */     //   64: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*     */     //   67: getfield showDebugInfo : Z
/*     */     //   70: ifeq -> 74
/*     */     //   73: return
/*     */     //   74: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   77: astore_2
/*     */     //   78: aload_2
/*     */     //   79: pop
/*     */     //   80: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   83: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   86: ldc 'Sense'
/*     */     //   88: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   91: ifne -> 142
/*     */     //   94: aload_2
/*     */     //   95: pop
/*     */     //   96: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   99: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   102: ldc 'Weave'
/*     */     //   104: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   107: ifne -> 142
/*     */     //   110: aload_2
/*     */     //   111: pop
/*     */     //   112: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   115: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   118: ldc 'RoundOutline'
/*     */     //   120: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   123: ifne -> 142
/*     */     //   126: aload_2
/*     */     //   127: pop
/*     */     //   128: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   131: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   134: ldc 'Round'
/*     */     //   136: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   139: ifeq -> 150
/*     */     //   142: aload_0
/*     */     //   143: iconst_1
/*     */     //   144: putfield hudMode : I
/*     */     //   147: goto -> 195
/*     */     //   150: aload_2
/*     */     //   151: pop
/*     */     //   152: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   155: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   158: ldc 'Frame'
/*     */     //   160: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   163: ifne -> 182
/*     */     //   166: aload_2
/*     */     //   167: pop
/*     */     //   168: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   171: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   174: ldc 'Big'
/*     */     //   176: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   179: ifeq -> 190
/*     */     //   182: aload_0
/*     */     //   183: iconst_2
/*     */     //   184: putfield hudMode : I
/*     */     //   187: goto -> 195
/*     */     //   190: aload_0
/*     */     //   191: iconst_0
/*     */     //   192: putfield hudMode : I
/*     */     //   195: aload_0
/*     */     //   196: getfield hudMode : I
/*     */     //   199: iconst_1
/*     */     //   200: if_icmpne -> 229
/*     */     //   203: aload_0
/*     */     //   204: getfield shouldY : F
/*     */     //   207: ldc 5.0
/*     */     //   209: fcmpl
/*     */     //   210: ifeq -> 291
/*     */     //   213: aload_0
/*     */     //   214: aload_0
/*     */     //   215: getfield shouldY : F
/*     */     //   218: ldc 5.0
/*     */     //   220: invokestatic moveUD : (FF)F
/*     */     //   223: putfield shouldY : F
/*     */     //   226: goto -> 291
/*     */     //   229: aload_0
/*     */     //   230: getfield hudMode : I
/*     */     //   233: ifne -> 260
/*     */     //   236: aload_0
/*     */     //   237: getfield shouldY : F
/*     */     //   240: fconst_0
/*     */     //   241: fcmpl
/*     */     //   242: ifeq -> 291
/*     */     //   245: aload_0
/*     */     //   246: aload_0
/*     */     //   247: getfield shouldY : F
/*     */     //   250: fconst_0
/*     */     //   251: invokestatic moveUD : (FF)F
/*     */     //   254: putfield shouldY : F
/*     */     //   257: goto -> 291
/*     */     //   260: aload_0
/*     */     //   261: getfield hudMode : I
/*     */     //   264: iconst_2
/*     */     //   265: if_icmpne -> 291
/*     */     //   268: aload_0
/*     */     //   269: getfield shouldY : F
/*     */     //   272: ldc 7.0
/*     */     //   274: fcmpl
/*     */     //   275: ifeq -> 291
/*     */     //   278: aload_0
/*     */     //   279: aload_0
/*     */     //   280: getfield shouldY : F
/*     */     //   283: ldc 7.0
/*     */     //   285: invokestatic moveUD : (FF)F
/*     */     //   288: putfield shouldY : F
/*     */     //   291: aload_2
/*     */     //   292: pop
/*     */     //   293: getstatic awareline/main/mod/implement/visual/HUD.rainbow : Lawareline/main/mod/values/Option;
/*     */     //   296: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   299: checkcast java/lang/Boolean
/*     */     //   302: invokevirtual booleanValue : ()Z
/*     */     //   305: ifeq -> 421
/*     */     //   308: aload_0
/*     */     //   309: dup
/*     */     //   310: getfield hue : F
/*     */     //   313: ldc 0.05
/*     */     //   315: invokestatic processFPS : (F)F
/*     */     //   318: fadd
/*     */     //   319: putfield hue : F
/*     */     //   322: aload_0
/*     */     //   323: getfield hue : F
/*     */     //   326: ldc 255.0
/*     */     //   328: fcmpl
/*     */     //   329: ifle -> 337
/*     */     //   332: aload_0
/*     */     //   333: fconst_0
/*     */     //   334: putfield hue : F
/*     */     //   337: aload_0
/*     */     //   338: aload_0
/*     */     //   339: getfield hue : F
/*     */     //   342: ldc 200.0
/*     */     //   344: fadd
/*     */     //   345: putfield h : F
/*     */     //   348: aload_0
/*     */     //   349: aload_0
/*     */     //   350: getfield hue : F
/*     */     //   353: ldc 85.0
/*     */     //   355: fadd
/*     */     //   356: putfield h2 : F
/*     */     //   359: aload_0
/*     */     //   360: aload_0
/*     */     //   361: getfield hue : F
/*     */     //   364: ldc 170.0
/*     */     //   366: fadd
/*     */     //   367: putfield h3 : F
/*     */     //   370: aload_0
/*     */     //   371: aload_0
/*     */     //   372: getfield h : F
/*     */     //   375: ldc 255.0
/*     */     //   377: fdiv
/*     */     //   378: ldc 0.9
/*     */     //   380: fconst_1
/*     */     //   381: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
/*     */     //   384: putfield color33 : Ljava/awt/Color;
/*     */     //   387: aload_0
/*     */     //   388: aload_0
/*     */     //   389: getfield h2 : F
/*     */     //   392: ldc 255.0
/*     */     //   394: fdiv
/*     */     //   395: ldc 0.9
/*     */     //   397: fconst_1
/*     */     //   398: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
/*     */     //   401: putfield color332 : Ljava/awt/Color;
/*     */     //   404: aload_0
/*     */     //   405: aload_0
/*     */     //   406: getfield h3 : F
/*     */     //   409: ldc 255.0
/*     */     //   411: fdiv
/*     */     //   412: ldc 0.9
/*     */     //   414: fconst_1
/*     */     //   415: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
/*     */     //   418: putfield color333 : Ljava/awt/Color;
/*     */     //   421: aload_0
/*     */     //   422: invokespecial updateBars : ()V
/*     */     //   425: invokestatic values : ()[Lawareline/main/mod/ModuleType;
/*     */     //   428: astore_3
/*     */     //   429: aload_0
/*     */     //   430: ldc 68.0
/*     */     //   432: putfield baseCategoryWidth : F
/*     */     //   435: aload_0
/*     */     //   436: aload_3
/*     */     //   437: arraylength
/*     */     //   438: iconst_2
/*     */     //   439: isub
/*     */     //   440: bipush #14
/*     */     //   442: imul
/*     */     //   443: iconst_2
/*     */     //   444: isub
/*     */     //   445: i2f
/*     */     //   446: putfield baseCategoryHeight : F
/*     */     //   449: ldc 5.0
/*     */     //   451: ldc 21.0
/*     */     //   453: aload_0
/*     */     //   454: getfield shouldY : F
/*     */     //   457: fadd
/*     */     //   458: fconst_2
/*     */     //   459: aload_0
/*     */     //   460: getfield baseCategoryWidth : F
/*     */     //   463: fadd
/*     */     //   464: ldc 24.0
/*     */     //   466: aload_0
/*     */     //   467: getfield baseCategoryHeight : F
/*     */     //   470: fadd
/*     */     //   471: ldc 13.0
/*     */     //   473: fsub
/*     */     //   474: aload_0
/*     */     //   475: getfield shouldY : F
/*     */     //   478: fadd
/*     */     //   479: new java/awt/Color
/*     */     //   482: dup
/*     */     //   483: bipush #15
/*     */     //   485: bipush #15
/*     */     //   487: bipush #15
/*     */     //   489: sipush #200
/*     */     //   492: invokespecial <init> : (IIII)V
/*     */     //   495: invokevirtual getRGB : ()I
/*     */     //   498: invokestatic drawRectForFloat : (FFFFI)V
/*     */     //   501: fconst_1
/*     */     //   502: fstore #4
/*     */     //   504: ldc 7.5
/*     */     //   506: fstore #5
/*     */     //   508: ldc 8.5
/*     */     //   510: fstore #6
/*     */     //   512: aload_2
/*     */     //   513: pop
/*     */     //   514: getstatic awareline/main/mod/implement/visual/HUD.dynamicColor : Lawareline/main/mod/values/Option;
/*     */     //   517: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   520: checkcast java/lang/Boolean
/*     */     //   523: invokevirtual booleanValue : ()Z
/*     */     //   526: ifeq -> 746
/*     */     //   529: aload_2
/*     */     //   530: pop
/*     */     //   531: getstatic awareline/main/mod/implement/visual/HUD.rainbow : Lawareline/main/mod/values/Option;
/*     */     //   534: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   537: checkcast java/lang/Boolean
/*     */     //   540: invokevirtual booleanValue : ()Z
/*     */     //   543: ifeq -> 643
/*     */     //   546: ldc 7.5
/*     */     //   548: aload_0
/*     */     //   549: getfield categoryPosition : F
/*     */     //   552: fconst_1
/*     */     //   553: fsub
/*     */     //   554: aload_0
/*     */     //   555: getfield shouldY : F
/*     */     //   558: fadd
/*     */     //   559: fconst_1
/*     */     //   560: fadd
/*     */     //   561: ldc 8.5
/*     */     //   563: aload_0
/*     */     //   564: getfield categoryPosition : F
/*     */     //   567: ldc 11.0
/*     */     //   569: fadd
/*     */     //   570: aload_0
/*     */     //   571: getfield shouldY : F
/*     */     //   574: fadd
/*     */     //   575: fconst_2
/*     */     //   576: fsub
/*     */     //   577: aload_2
/*     */     //   578: pop
/*     */     //   579: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   582: ldc 'Client'
/*     */     //   584: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   587: ifeq -> 600
/*     */     //   590: aload_0
/*     */     //   591: getfield color332 : Ljava/awt/Color;
/*     */     //   594: invokevirtual getRGB : ()I
/*     */     //   597: goto -> 607
/*     */     //   600: aload_0
/*     */     //   601: getfield color33 : Ljava/awt/Color;
/*     */     //   604: invokevirtual getRGB : ()I
/*     */     //   607: aload_2
/*     */     //   608: pop
/*     */     //   609: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   612: ldc 'Client'
/*     */     //   614: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   617: ifeq -> 630
/*     */     //   620: aload_0
/*     */     //   621: getfield color333 : Ljava/awt/Color;
/*     */     //   624: invokevirtual getRGB : ()I
/*     */     //   627: goto -> 637
/*     */     //   630: aload_0
/*     */     //   631: getfield color332 : Ljava/awt/Color;
/*     */     //   634: invokevirtual getRGB : ()I
/*     */     //   637: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   640: goto -> 902
/*     */     //   643: aload_2
/*     */     //   644: new java/awt/Color
/*     */     //   647: dup
/*     */     //   648: aload_2
/*     */     //   649: pop
/*     */     //   650: getstatic awareline/main/mod/implement/visual/HUD.r : Lawareline/main/mod/values/Numbers;
/*     */     //   653: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   656: checkcast java/lang/Double
/*     */     //   659: invokevirtual intValue : ()I
/*     */     //   662: aload_2
/*     */     //   663: pop
/*     */     //   664: getstatic awareline/main/mod/implement/visual/HUD.g : Lawareline/main/mod/values/Numbers;
/*     */     //   667: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   670: checkcast java/lang/Double
/*     */     //   673: invokevirtual intValue : ()I
/*     */     //   676: aload_2
/*     */     //   677: pop
/*     */     //   678: getstatic awareline/main/mod/implement/visual/HUD.b : Lawareline/main/mod/values/Numbers;
/*     */     //   681: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   684: checkcast java/lang/Double
/*     */     //   687: invokevirtual intValue : ()I
/*     */     //   690: invokespecial <init> : (III)V
/*     */     //   693: bipush #70
/*     */     //   695: bipush #25
/*     */     //   697: invokevirtual fade : (Ljava/awt/Color;II)Ljava/awt/Color;
/*     */     //   700: astore #7
/*     */     //   702: ldc 7.5
/*     */     //   704: aload_0
/*     */     //   705: getfield categoryPosition : F
/*     */     //   708: fconst_1
/*     */     //   709: fsub
/*     */     //   710: aload_0
/*     */     //   711: getfield shouldY : F
/*     */     //   714: fadd
/*     */     //   715: ldc 1.5
/*     */     //   717: fadd
/*     */     //   718: ldc 8.5
/*     */     //   720: aload_0
/*     */     //   721: getfield categoryPosition : F
/*     */     //   724: ldc 11.0
/*     */     //   726: fadd
/*     */     //   727: aload_0
/*     */     //   728: getfield shouldY : F
/*     */     //   731: fadd
/*     */     //   732: ldc 2.5
/*     */     //   734: fsub
/*     */     //   735: aload #7
/*     */     //   737: invokevirtual getRGB : ()I
/*     */     //   740: invokestatic drawRectForFloat : (FFFFI)V
/*     */     //   743: goto -> 902
/*     */     //   746: aload_2
/*     */     //   747: pop
/*     */     //   748: getstatic awareline/main/mod/implement/visual/HUD.rainbow : Lawareline/main/mod/values/Option;
/*     */     //   751: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   754: checkcast java/lang/Boolean
/*     */     //   757: invokevirtual booleanValue : ()Z
/*     */     //   760: ifeq -> 860
/*     */     //   763: ldc 7.5
/*     */     //   765: aload_0
/*     */     //   766: getfield categoryPosition : F
/*     */     //   769: fconst_1
/*     */     //   770: fsub
/*     */     //   771: aload_0
/*     */     //   772: getfield shouldY : F
/*     */     //   775: fadd
/*     */     //   776: fconst_1
/*     */     //   777: fadd
/*     */     //   778: ldc 8.5
/*     */     //   780: aload_0
/*     */     //   781: getfield categoryPosition : F
/*     */     //   784: ldc 11.0
/*     */     //   786: fadd
/*     */     //   787: aload_0
/*     */     //   788: getfield shouldY : F
/*     */     //   791: fadd
/*     */     //   792: fconst_2
/*     */     //   793: fsub
/*     */     //   794: aload_2
/*     */     //   795: pop
/*     */     //   796: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   799: ldc 'Client'
/*     */     //   801: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   804: ifeq -> 817
/*     */     //   807: aload_0
/*     */     //   808: getfield color332 : Ljava/awt/Color;
/*     */     //   811: invokevirtual getRGB : ()I
/*     */     //   814: goto -> 824
/*     */     //   817: aload_0
/*     */     //   818: getfield color33 : Ljava/awt/Color;
/*     */     //   821: invokevirtual getRGB : ()I
/*     */     //   824: aload_2
/*     */     //   825: pop
/*     */     //   826: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   829: ldc 'Client'
/*     */     //   831: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   834: ifeq -> 847
/*     */     //   837: aload_0
/*     */     //   838: getfield color333 : Ljava/awt/Color;
/*     */     //   841: invokevirtual getRGB : ()I
/*     */     //   844: goto -> 854
/*     */     //   847: aload_0
/*     */     //   848: getfield color332 : Ljava/awt/Color;
/*     */     //   851: invokevirtual getRGB : ()I
/*     */     //   854: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   857: goto -> 902
/*     */     //   860: ldc 7.5
/*     */     //   862: aload_0
/*     */     //   863: getfield categoryPosition : F
/*     */     //   866: fconst_1
/*     */     //   867: fsub
/*     */     //   868: aload_0
/*     */     //   869: getfield shouldY : F
/*     */     //   872: fadd
/*     */     //   873: fconst_1
/*     */     //   874: fadd
/*     */     //   875: ldc 8.5
/*     */     //   877: aload_0
/*     */     //   878: getfield categoryPosition : F
/*     */     //   881: ldc 11.0
/*     */     //   883: fadd
/*     */     //   884: aload_0
/*     */     //   885: getfield shouldY : F
/*     */     //   888: fadd
/*     */     //   889: fconst_2
/*     */     //   890: fsub
/*     */     //   891: aload_0
/*     */     //   892: invokespecial HUDColor : ()I
/*     */     //   895: aload_0
/*     */     //   896: invokespecial HUDColor : ()I
/*     */     //   899: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   902: ldc 21.0
/*     */     //   904: fstore #7
/*     */     //   906: iconst_0
/*     */     //   907: istore #8
/*     */     //   909: iload #8
/*     */     //   911: aload_3
/*     */     //   912: arraylength
/*     */     //   913: iconst_2
/*     */     //   914: isub
/*     */     //   915: if_icmpge -> 1009
/*     */     //   918: aload_3
/*     */     //   919: iload #8
/*     */     //   921: aaload
/*     */     //   922: astore #10
/*     */     //   924: new java/lang/StringBuilder
/*     */     //   927: dup
/*     */     //   928: invokespecial <init> : ()V
/*     */     //   931: aload #10
/*     */     //   933: invokevirtual name : ()Ljava/lang/String;
/*     */     //   936: invokevirtual toLowerCase : ()Ljava/lang/String;
/*     */     //   939: iconst_0
/*     */     //   940: invokevirtual charAt : (I)C
/*     */     //   943: invokestatic toUpperCase : (C)C
/*     */     //   946: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*     */     //   949: aload #10
/*     */     //   951: invokevirtual name : ()Ljava/lang/String;
/*     */     //   954: invokevirtual toLowerCase : ()Ljava/lang/String;
/*     */     //   957: iconst_1
/*     */     //   958: invokevirtual substring : (I)Ljava/lang/String;
/*     */     //   961: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   964: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   967: astore #9
/*     */     //   969: getstatic awareline/main/Client.instance : Lawareline/main/Client;
/*     */     //   972: getfield FontManager : Lawareline/main/ui/font/fontmanager/FontManager;
/*     */     //   975: getfield comfortaa17 : Lawareline/main/ui/font/fontmanager/UnicodeFontRenderer;
/*     */     //   978: aload #9
/*     */     //   980: ldc2_w 10.0
/*     */     //   983: fload #7
/*     */     //   985: aload_0
/*     */     //   986: getfield shouldY : F
/*     */     //   989: fadd
/*     */     //   990: f2d
/*     */     //   991: ldc -2105377
/*     */     //   993: invokevirtual drawStringOther : (Ljava/lang/String;DDI)V
/*     */     //   996: fload #7
/*     */     //   998: ldc 12.0
/*     */     //   1000: fadd
/*     */     //   1001: fstore #7
/*     */     //   1003: iinc #8, 1
/*     */     //   1006: goto -> 909
/*     */     //   1009: aload_0
/*     */     //   1010: getfield modulesXanim : F
/*     */     //   1013: fconst_0
/*     */     //   1014: fcmpl
/*     */     //   1015: iflt -> 1053
/*     */     //   1018: aload_0
/*     */     //   1019: getfield section : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   1022: getstatic awareline/main/ui/gui/hud/tabgui/SideTabGui$Section.MODS : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   1025: if_acmpeq -> 1053
/*     */     //   1028: aload_0
/*     */     //   1029: aload_0
/*     */     //   1030: getfield modulesXanim : F
/*     */     //   1033: fconst_0
/*     */     //   1034: ldc 0.015
/*     */     //   1036: invokestatic processFPS : (F)F
/*     */     //   1039: ldc 0.013
/*     */     //   1041: invokestatic processFPS : (F)F
/*     */     //   1044: invokestatic moveUD : (FFFF)F
/*     */     //   1047: putfield modulesXanim : F
/*     */     //   1050: goto -> 1086
/*     */     //   1053: aload_0
/*     */     //   1054: getfield modulesXanim : F
/*     */     //   1057: ldc 6.0
/*     */     //   1059: fcmpg
/*     */     //   1060: ifge -> 1086
/*     */     //   1063: aload_0
/*     */     //   1064: aload_0
/*     */     //   1065: getfield modulesXanim : F
/*     */     //   1068: ldc 6.0
/*     */     //   1070: ldc 0.015
/*     */     //   1072: invokestatic processFPS : (F)F
/*     */     //   1075: ldc 0.013
/*     */     //   1077: invokestatic processFPS : (F)F
/*     */     //   1080: invokestatic moveUD : (FFFF)F
/*     */     //   1083: putfield modulesXanim : F
/*     */     //   1086: aload_0
/*     */     //   1087: getfield section : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   1090: getstatic awareline/main/ui/gui/hud/tabgui/SideTabGui$Section.MODS : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   1093: if_acmpeq -> 1123
/*     */     //   1096: aload_0
/*     */     //   1097: aload_0
/*     */     //   1098: getfield alpha : I
/*     */     //   1101: i2f
/*     */     //   1102: fconst_0
/*     */     //   1103: ldc 0.018
/*     */     //   1105: invokestatic processFPS : (F)F
/*     */     //   1108: ldc 0.015
/*     */     //   1110: invokestatic processFPS : (F)F
/*     */     //   1113: invokestatic moveUD : (FFFF)F
/*     */     //   1116: f2i
/*     */     //   1117: putfield alpha : I
/*     */     //   1120: goto -> 1153
/*     */     //   1123: aload_0
/*     */     //   1124: getfield alpha : I
/*     */     //   1127: sipush #200
/*     */     //   1130: if_icmpge -> 1146
/*     */     //   1133: aload_0
/*     */     //   1134: dup
/*     */     //   1135: getfield alpha : I
/*     */     //   1138: iconst_5
/*     */     //   1139: iadd
/*     */     //   1140: putfield alpha : I
/*     */     //   1143: goto -> 1153
/*     */     //   1146: aload_0
/*     */     //   1147: sipush #200
/*     */     //   1150: putfield alpha : I
/*     */     //   1153: aload_0
/*     */     //   1154: getfield section : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   1157: getstatic awareline/main/ui/gui/hud/tabgui/SideTabGui$Section.MODS : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   1160: if_acmpeq -> 1172
/*     */     //   1163: aload_0
/*     */     //   1164: getfield modulesXanim : F
/*     */     //   1167: fconst_1
/*     */     //   1168: fcmpl
/*     */     //   1169: ifle -> 1662
/*     */     //   1172: aload_0
/*     */     //   1173: aload_3
/*     */     //   1174: aload_0
/*     */     //   1175: getfield categoryTab : I
/*     */     //   1178: aaload
/*     */     //   1179: invokespecial getModsInCategory : (Lawareline/main/mod/ModuleType;)Ljava/util/List;
/*     */     //   1182: invokeinterface size : ()I
/*     */     //   1187: istore #10
/*     */     //   1189: aload_0
/*     */     //   1190: getfield baseCategoryWidth : F
/*     */     //   1193: aload_0
/*     */     //   1194: getfield modulesXanim : F
/*     */     //   1197: fadd
/*     */     //   1198: aload_0
/*     */     //   1199: getfield categoryPosition : F
/*     */     //   1202: aload_0
/*     */     //   1203: getfield shouldY : F
/*     */     //   1206: fadd
/*     */     //   1207: fconst_1
/*     */     //   1208: fsub
/*     */     //   1209: aload_0
/*     */     //   1210: getfield baseCategoryWidth : F
/*     */     //   1213: fconst_2
/*     */     //   1214: fadd
/*     */     //   1215: ldc 16.0
/*     */     //   1217: fsub
/*     */     //   1218: ldc 79.0
/*     */     //   1220: fadd
/*     */     //   1221: aload_0
/*     */     //   1222: getfield modulesXanim : F
/*     */     //   1225: fadd
/*     */     //   1226: ldc 10.0
/*     */     //   1228: fadd
/*     */     //   1229: ldc 10.0
/*     */     //   1231: fadd
/*     */     //   1232: aload_0
/*     */     //   1233: getfield categoryPosition : F
/*     */     //   1236: iload #10
/*     */     //   1238: bipush #12
/*     */     //   1240: imul
/*     */     //   1241: i2f
/*     */     //   1242: fadd
/*     */     //   1243: fconst_1
/*     */     //   1244: fsub
/*     */     //   1245: aload_0
/*     */     //   1246: getfield shouldY : F
/*     */     //   1249: fadd
/*     */     //   1250: new java/awt/Color
/*     */     //   1253: dup
/*     */     //   1254: bipush #15
/*     */     //   1256: bipush #15
/*     */     //   1258: bipush #15
/*     */     //   1260: aload_0
/*     */     //   1261: getfield alpha : I
/*     */     //   1264: invokespecial <init> : (IIII)V
/*     */     //   1267: invokevirtual getRGB : ()I
/*     */     //   1270: new java/awt/Color
/*     */     //   1273: dup
/*     */     //   1274: bipush #15
/*     */     //   1276: bipush #15
/*     */     //   1278: bipush #15
/*     */     //   1280: aload_0
/*     */     //   1281: getfield alpha : I
/*     */     //   1284: invokespecial <init> : (IIII)V
/*     */     //   1287: invokevirtual getRGB : ()I
/*     */     //   1290: invokestatic drawGradientSidewaysForFloat : (FFFFII)V
/*     */     //   1293: aload_2
/*     */     //   1294: pop
/*     */     //   1295: getstatic awareline/main/mod/implement/visual/HUD.rainbow : Lawareline/main/mod/values/Option;
/*     */     //   1298: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   1301: checkcast java/lang/Boolean
/*     */     //   1304: invokevirtual booleanValue : ()Z
/*     */     //   1307: ifeq -> 1437
/*     */     //   1310: aload_0
/*     */     //   1311: getfield baseCategoryWidth : F
/*     */     //   1314: aload_0
/*     */     //   1315: getfield modulesXanim : F
/*     */     //   1318: fadd
/*     */     //   1319: fconst_1
/*     */     //   1320: fadd
/*     */     //   1321: fconst_1
/*     */     //   1322: fadd
/*     */     //   1323: aload_0
/*     */     //   1324: getfield modPosition : F
/*     */     //   1327: aload_0
/*     */     //   1328: getfield shouldY : F
/*     */     //   1331: fadd
/*     */     //   1332: ldc 0.5
/*     */     //   1334: fadd
/*     */     //   1335: aload_0
/*     */     //   1336: getfield baseCategoryWidth : F
/*     */     //   1339: ldc 8.0
/*     */     //   1341: fadd
/*     */     //   1342: ldc 57.0
/*     */     //   1344: fadd
/*     */     //   1345: aload_0
/*     */     //   1346: getfield modulesXanim : F
/*     */     //   1349: fadd
/*     */     //   1350: ldc 7.0
/*     */     //   1352: fadd
/*     */     //   1353: ldc 70.0
/*     */     //   1355: fsub
/*     */     //   1356: ldc 1.5
/*     */     //   1358: fadd
/*     */     //   1359: aload_0
/*     */     //   1360: getfield modPosition : F
/*     */     //   1363: ldc 8.5
/*     */     //   1365: fadd
/*     */     //   1366: aload_0
/*     */     //   1367: getfield shouldY : F
/*     */     //   1370: fadd
/*     */     //   1371: aload_2
/*     */     //   1372: pop
/*     */     //   1373: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   1376: ldc 'Client'
/*     */     //   1378: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   1381: ifeq -> 1394
/*     */     //   1384: aload_0
/*     */     //   1385: getfield color332 : Ljava/awt/Color;
/*     */     //   1388: invokevirtual getRGB : ()I
/*     */     //   1391: goto -> 1401
/*     */     //   1394: aload_0
/*     */     //   1395: getfield color33 : Ljava/awt/Color;
/*     */     //   1398: invokevirtual getRGB : ()I
/*     */     //   1401: aload_2
/*     */     //   1402: pop
/*     */     //   1403: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   1406: ldc 'Client'
/*     */     //   1408: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   1411: ifeq -> 1424
/*     */     //   1414: aload_0
/*     */     //   1415: getfield color333 : Ljava/awt/Color;
/*     */     //   1418: invokevirtual getRGB : ()I
/*     */     //   1421: goto -> 1431
/*     */     //   1424: aload_0
/*     */     //   1425: getfield color332 : Ljava/awt/Color;
/*     */     //   1428: invokevirtual getRGB : ()I
/*     */     //   1431: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   1434: goto -> 1509
/*     */     //   1437: aload_0
/*     */     //   1438: getfield baseCategoryWidth : F
/*     */     //   1441: aload_0
/*     */     //   1442: getfield modulesXanim : F
/*     */     //   1445: fadd
/*     */     //   1446: fconst_1
/*     */     //   1447: fadd
/*     */     //   1448: fconst_1
/*     */     //   1449: fadd
/*     */     //   1450: aload_0
/*     */     //   1451: getfield modPosition : F
/*     */     //   1454: aload_0
/*     */     //   1455: getfield shouldY : F
/*     */     //   1458: fadd
/*     */     //   1459: ldc 0.5
/*     */     //   1461: fadd
/*     */     //   1462: aload_0
/*     */     //   1463: getfield baseCategoryWidth : F
/*     */     //   1466: ldc 8.0
/*     */     //   1468: fadd
/*     */     //   1469: ldc 57.0
/*     */     //   1471: fadd
/*     */     //   1472: aload_0
/*     */     //   1473: getfield modulesXanim : F
/*     */     //   1476: fadd
/*     */     //   1477: ldc 7.0
/*     */     //   1479: fadd
/*     */     //   1480: ldc 70.0
/*     */     //   1482: fsub
/*     */     //   1483: ldc 1.5
/*     */     //   1485: fadd
/*     */     //   1486: aload_0
/*     */     //   1487: getfield modPosition : F
/*     */     //   1490: ldc 8.5
/*     */     //   1492: fadd
/*     */     //   1493: aload_0
/*     */     //   1494: getfield shouldY : F
/*     */     //   1497: fadd
/*     */     //   1498: aload_0
/*     */     //   1499: invokespecial HUDColorOnlyMobs : ()I
/*     */     //   1502: aload_0
/*     */     //   1503: invokespecial HUDColorOnlyMobs : ()I
/*     */     //   1506: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   1509: aload_0
/*     */     //   1510: getfield categoryPosition : F
/*     */     //   1513: fstore #7
/*     */     //   1515: iconst_0
/*     */     //   1516: istore #8
/*     */     //   1518: iload #8
/*     */     //   1520: iload #10
/*     */     //   1522: if_icmpge -> 1662
/*     */     //   1525: aload_0
/*     */     //   1526: aload_3
/*     */     //   1527: aload_0
/*     */     //   1528: getfield categoryTab : I
/*     */     //   1531: aaload
/*     */     //   1532: invokespecial getModsInCategory : (Lawareline/main/mod/ModuleType;)Ljava/util/List;
/*     */     //   1535: iload #8
/*     */     //   1537: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1542: checkcast awareline/main/mod/Module
/*     */     //   1545: astore #11
/*     */     //   1547: getstatic awareline/main/Client.instance : Lawareline/main/Client;
/*     */     //   1550: getfield FontManager : Lawareline/main/ui/font/fontmanager/FontManager;
/*     */     //   1553: getfield comfortaa17 : Lawareline/main/ui/font/fontmanager/UnicodeFontRenderer;
/*     */     //   1556: aload #11
/*     */     //   1558: invokevirtual getHUDName : ()Ljava/lang/String;
/*     */     //   1561: aload_0
/*     */     //   1562: getfield baseCategoryWidth : F
/*     */     //   1565: ldc 11.0
/*     */     //   1567: fadd
/*     */     //   1568: aload_0
/*     */     //   1569: getfield modulesXanim : F
/*     */     //   1572: fadd
/*     */     //   1573: f2d
/*     */     //   1574: ldc2_w 5.3
/*     */     //   1577: dsub
/*     */     //   1578: fload #7
/*     */     //   1580: aload_0
/*     */     //   1581: getfield shouldY : F
/*     */     //   1584: fadd
/*     */     //   1585: ldc 0.5
/*     */     //   1587: fsub
/*     */     //   1588: f2d
/*     */     //   1589: aload #11
/*     */     //   1591: invokevirtual isEnabled : ()Z
/*     */     //   1594: ifne -> 1623
/*     */     //   1597: new java/awt/Color
/*     */     //   1600: dup
/*     */     //   1601: sipush #153
/*     */     //   1604: sipush #153
/*     */     //   1607: sipush #153
/*     */     //   1610: aload_0
/*     */     //   1611: getfield alpha : I
/*     */     //   1614: invokespecial <init> : (IIII)V
/*     */     //   1617: invokevirtual getRGB : ()I
/*     */     //   1620: goto -> 1646
/*     */     //   1623: new java/awt/Color
/*     */     //   1626: dup
/*     */     //   1627: sipush #255
/*     */     //   1630: sipush #255
/*     */     //   1633: sipush #255
/*     */     //   1636: aload_0
/*     */     //   1637: getfield alpha : I
/*     */     //   1640: invokespecial <init> : (IIII)V
/*     */     //   1643: invokevirtual getRGB : ()I
/*     */     //   1646: invokevirtual drawStringOther : (Ljava/lang/String;DDI)V
/*     */     //   1649: fload #7
/*     */     //   1651: ldc 12.0
/*     */     //   1653: fadd
/*     */     //   1654: fstore #7
/*     */     //   1656: iinc #8, 1
/*     */     //   1659: goto -> 1518
/*     */     //   1662: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #202	-> 0
/*     */     //   #204	-> 9
/*     */     //   #205	-> 27
/*     */     //   #207	-> 28
/*     */     //   #208	-> 40
/*     */     //   #210	-> 41
/*     */     //   #212	-> 42
/*     */     //   #213	-> 60
/*     */     //   #215	-> 61
/*     */     //   #216	-> 73
/*     */     //   #220	-> 74
/*     */     //   #221	-> 78
/*     */     //   #222	-> 104
/*     */     //   #223	-> 120
/*     */     //   #224	-> 142
/*     */     //   #225	-> 150
/*     */     //   #226	-> 176
/*     */     //   #227	-> 182
/*     */     //   #229	-> 190
/*     */     //   #231	-> 195
/*     */     //   #232	-> 203
/*     */     //   #233	-> 213
/*     */     //   #235	-> 229
/*     */     //   #236	-> 236
/*     */     //   #237	-> 245
/*     */     //   #239	-> 260
/*     */     //   #240	-> 268
/*     */     //   #241	-> 278
/*     */     //   #245	-> 291
/*     */     //   #246	-> 308
/*     */     //   #247	-> 322
/*     */     //   #248	-> 332
/*     */     //   #250	-> 337
/*     */     //   #251	-> 348
/*     */     //   #252	-> 359
/*     */     //   #253	-> 370
/*     */     //   #254	-> 387
/*     */     //   #255	-> 404
/*     */     //   #257	-> 421
/*     */     //   #258	-> 425
/*     */     //   #260	-> 429
/*     */     //   #262	-> 435
/*     */     //   #264	-> 449
/*     */     //   #265	-> 501
/*     */     //   #266	-> 504
/*     */     //   #267	-> 508
/*     */     //   #268	-> 512
/*     */     //   #269	-> 529
/*     */     //   #270	-> 546
/*     */     //   #271	-> 584
/*     */     //   #270	-> 637
/*     */     //   #273	-> 643
/*     */     //   #274	-> 702
/*     */     //   #275	-> 737
/*     */     //   #274	-> 740
/*     */     //   #276	-> 743
/*     */     //   #278	-> 746
/*     */     //   #279	-> 763
/*     */     //   #281	-> 860
/*     */     //   #284	-> 902
/*     */     //   #287	-> 906
/*     */     //   #288	-> 918
/*     */     //   #289	-> 924
/*     */     //   #290	-> 969
/*     */     //   #291	-> 996
/*     */     //   #287	-> 1003
/*     */     //   #293	-> 1009
/*     */     //   #294	-> 1028
/*     */     //   #296	-> 1053
/*     */     //   #297	-> 1063
/*     */     //   #300	-> 1086
/*     */     //   #301	-> 1096
/*     */     //   #303	-> 1123
/*     */     //   #304	-> 1133
/*     */     //   #306	-> 1146
/*     */     //   #309	-> 1153
/*     */     //   #310	-> 1172
/*     */     //   #311	-> 1189
/*     */     //   #315	-> 1267
/*     */     //   #311	-> 1290
/*     */     //   #317	-> 1293
/*     */     //   #318	-> 1310
/*     */     //   #321	-> 1378
/*     */     //   #318	-> 1431
/*     */     //   #323	-> 1437
/*     */     //   #326	-> 1499
/*     */     //   #323	-> 1506
/*     */     //   #329	-> 1509
/*     */     //   #330	-> 1515
/*     */     //   #331	-> 1525
/*     */     //   #332	-> 1547
/*     */     //   #333	-> 1591
/*     */     //   #334	-> 1617
/*     */     //   #335	-> 1643
/*     */     //   #332	-> 1646
/*     */     //   #336	-> 1649
/*     */     //   #330	-> 1656
/*     */     //   #339	-> 1662
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   702	41	7	Ranbow	Ljava/awt/Color;
/*     */     //   924	79	10	category	Lawareline/main/mod/ModuleType;
/*     */     //   969	40	9	name	Ljava/lang/String;
/*     */     //   1547	109	11	mod	Lawareline/main/mod/Module;
/*     */     //   1189	473	10	size	I
/*     */     //   0	1663	0	this	Lawareline/main/ui/gui/hud/tabgui/SideTabGui;
/*     */     //   0	1663	1	e	Lawareline/main/event/events/world/renderEvents/EventRender2D;
/*     */     //   78	1585	2	HUD	Lawareline/main/mod/implement/visual/HUD;
/*     */     //   429	1234	3	values	[Lawareline/main/mod/ModuleType;
/*     */     //   504	1159	4	move	F
/*     */     //   508	1155	5	x	F
/*     */     //   512	1151	6	x2	F
/*     */     //   906	757	7	yPos	F
/*     */     //   909	754	8	i	I
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
/*     */   public void renderTabGuiBloom(EventShader e) {
/*     */     // Byte code:
/*     */     //   0: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   3: invokevirtual isEnabled : ()Z
/*     */     //   6: ifne -> 42
/*     */     //   9: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   12: getfield tabGui : Lawareline/main/mod/values/Option;
/*     */     //   15: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   18: checkcast java/lang/Boolean
/*     */     //   21: invokevirtual booleanValue : ()Z
/*     */     //   24: ifne -> 28
/*     */     //   27: return
/*     */     //   28: invokestatic getMinecraft : ()Lnet/minecraft/client/Minecraft;
/*     */     //   31: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*     */     //   34: getfield showDebugInfo : Z
/*     */     //   37: ifeq -> 41
/*     */     //   40: return
/*     */     //   41: return
/*     */     //   42: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   45: getfield tabGui : Lawareline/main/mod/values/Option;
/*     */     //   48: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   51: checkcast java/lang/Boolean
/*     */     //   54: invokevirtual booleanValue : ()Z
/*     */     //   57: ifne -> 61
/*     */     //   60: return
/*     */     //   61: invokestatic getMinecraft : ()Lnet/minecraft/client/Minecraft;
/*     */     //   64: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*     */     //   67: getfield showDebugInfo : Z
/*     */     //   70: ifeq -> 74
/*     */     //   73: return
/*     */     //   74: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   77: astore_2
/*     */     //   78: aload_2
/*     */     //   79: pop
/*     */     //   80: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   83: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   86: ldc 'Sense'
/*     */     //   88: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   91: ifne -> 142
/*     */     //   94: aload_2
/*     */     //   95: pop
/*     */     //   96: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   99: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   102: ldc 'Weave'
/*     */     //   104: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   107: ifne -> 142
/*     */     //   110: aload_2
/*     */     //   111: pop
/*     */     //   112: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   115: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   118: ldc 'RoundOutline'
/*     */     //   120: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   123: ifne -> 142
/*     */     //   126: aload_2
/*     */     //   127: pop
/*     */     //   128: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   131: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   134: ldc 'Round'
/*     */     //   136: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   139: ifeq -> 150
/*     */     //   142: aload_0
/*     */     //   143: iconst_1
/*     */     //   144: putfield hudMode : I
/*     */     //   147: goto -> 195
/*     */     //   150: aload_2
/*     */     //   151: pop
/*     */     //   152: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   155: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   158: ldc 'Frame'
/*     */     //   160: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   163: ifne -> 182
/*     */     //   166: aload_2
/*     */     //   167: pop
/*     */     //   168: getstatic awareline/main/mod/implement/visual/HUD.getInstance : Lawareline/main/mod/implement/visual/HUD;
/*     */     //   171: getfield waterMarks : Lawareline/main/mod/values/Mode;
/*     */     //   174: ldc 'Big'
/*     */     //   176: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   179: ifeq -> 190
/*     */     //   182: aload_0
/*     */     //   183: iconst_2
/*     */     //   184: putfield hudMode : I
/*     */     //   187: goto -> 195
/*     */     //   190: aload_0
/*     */     //   191: iconst_0
/*     */     //   192: putfield hudMode : I
/*     */     //   195: aload_0
/*     */     //   196: getfield hudMode : I
/*     */     //   199: iconst_1
/*     */     //   200: if_icmpne -> 229
/*     */     //   203: aload_0
/*     */     //   204: getfield shouldY : F
/*     */     //   207: ldc 5.0
/*     */     //   209: fcmpl
/*     */     //   210: ifeq -> 291
/*     */     //   213: aload_0
/*     */     //   214: aload_0
/*     */     //   215: getfield shouldY : F
/*     */     //   218: ldc 5.0
/*     */     //   220: invokestatic moveUD : (FF)F
/*     */     //   223: putfield shouldY : F
/*     */     //   226: goto -> 291
/*     */     //   229: aload_0
/*     */     //   230: getfield hudMode : I
/*     */     //   233: ifne -> 260
/*     */     //   236: aload_0
/*     */     //   237: getfield shouldY : F
/*     */     //   240: fconst_0
/*     */     //   241: fcmpl
/*     */     //   242: ifeq -> 291
/*     */     //   245: aload_0
/*     */     //   246: aload_0
/*     */     //   247: getfield shouldY : F
/*     */     //   250: fconst_0
/*     */     //   251: invokestatic moveUD : (FF)F
/*     */     //   254: putfield shouldY : F
/*     */     //   257: goto -> 291
/*     */     //   260: aload_0
/*     */     //   261: getfield hudMode : I
/*     */     //   264: iconst_2
/*     */     //   265: if_icmpne -> 291
/*     */     //   268: aload_0
/*     */     //   269: getfield shouldY : F
/*     */     //   272: ldc 7.0
/*     */     //   274: fcmpl
/*     */     //   275: ifeq -> 291
/*     */     //   278: aload_0
/*     */     //   279: aload_0
/*     */     //   280: getfield shouldY : F
/*     */     //   283: ldc 7.0
/*     */     //   285: invokestatic moveUD : (FF)F
/*     */     //   288: putfield shouldY : F
/*     */     //   291: aload_2
/*     */     //   292: pop
/*     */     //   293: getstatic awareline/main/mod/implement/visual/HUD.rainbow : Lawareline/main/mod/values/Option;
/*     */     //   296: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   299: checkcast java/lang/Boolean
/*     */     //   302: invokevirtual booleanValue : ()Z
/*     */     //   305: ifeq -> 421
/*     */     //   308: aload_0
/*     */     //   309: dup
/*     */     //   310: getfield hue : F
/*     */     //   313: ldc 0.05
/*     */     //   315: invokestatic processFPS : (F)F
/*     */     //   318: fadd
/*     */     //   319: putfield hue : F
/*     */     //   322: aload_0
/*     */     //   323: getfield hue : F
/*     */     //   326: ldc 255.0
/*     */     //   328: fcmpl
/*     */     //   329: ifle -> 337
/*     */     //   332: aload_0
/*     */     //   333: fconst_0
/*     */     //   334: putfield hue : F
/*     */     //   337: aload_0
/*     */     //   338: aload_0
/*     */     //   339: getfield hue : F
/*     */     //   342: ldc 200.0
/*     */     //   344: fadd
/*     */     //   345: putfield h : F
/*     */     //   348: aload_0
/*     */     //   349: aload_0
/*     */     //   350: getfield hue : F
/*     */     //   353: ldc 85.0
/*     */     //   355: fadd
/*     */     //   356: putfield h2 : F
/*     */     //   359: aload_0
/*     */     //   360: aload_0
/*     */     //   361: getfield hue : F
/*     */     //   364: ldc 170.0
/*     */     //   366: fadd
/*     */     //   367: putfield h3 : F
/*     */     //   370: aload_0
/*     */     //   371: aload_0
/*     */     //   372: getfield h : F
/*     */     //   375: ldc 255.0
/*     */     //   377: fdiv
/*     */     //   378: ldc 0.9
/*     */     //   380: fconst_1
/*     */     //   381: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
/*     */     //   384: putfield color33 : Ljava/awt/Color;
/*     */     //   387: aload_0
/*     */     //   388: aload_0
/*     */     //   389: getfield h2 : F
/*     */     //   392: ldc 255.0
/*     */     //   394: fdiv
/*     */     //   395: ldc 0.9
/*     */     //   397: fconst_1
/*     */     //   398: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
/*     */     //   401: putfield color332 : Ljava/awt/Color;
/*     */     //   404: aload_0
/*     */     //   405: aload_0
/*     */     //   406: getfield h3 : F
/*     */     //   409: ldc 255.0
/*     */     //   411: fdiv
/*     */     //   412: ldc 0.9
/*     */     //   414: fconst_1
/*     */     //   415: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
/*     */     //   418: putfield color333 : Ljava/awt/Color;
/*     */     //   421: aload_0
/*     */     //   422: invokespecial updateBars : ()V
/*     */     //   425: invokestatic values : ()[Lawareline/main/mod/ModuleType;
/*     */     //   428: astore_3
/*     */     //   429: aload_0
/*     */     //   430: ldc 68.0
/*     */     //   432: putfield baseCategoryWidth : F
/*     */     //   435: aload_0
/*     */     //   436: aload_3
/*     */     //   437: arraylength
/*     */     //   438: iconst_2
/*     */     //   439: isub
/*     */     //   440: bipush #14
/*     */     //   442: imul
/*     */     //   443: iconst_2
/*     */     //   444: isub
/*     */     //   445: i2f
/*     */     //   446: putfield baseCategoryHeight : F
/*     */     //   449: ldc 5.0
/*     */     //   451: ldc 21.0
/*     */     //   453: aload_0
/*     */     //   454: getfield shouldY : F
/*     */     //   457: fadd
/*     */     //   458: fconst_2
/*     */     //   459: aload_0
/*     */     //   460: getfield baseCategoryWidth : F
/*     */     //   463: fadd
/*     */     //   464: ldc 24.0
/*     */     //   466: aload_0
/*     */     //   467: getfield baseCategoryHeight : F
/*     */     //   470: fadd
/*     */     //   471: ldc 13.0
/*     */     //   473: fsub
/*     */     //   474: aload_0
/*     */     //   475: getfield shouldY : F
/*     */     //   478: fadd
/*     */     //   479: getstatic awareline/main/Client.instance : Lawareline/main/Client;
/*     */     //   482: sipush #255
/*     */     //   485: invokevirtual getClientColor : (I)I
/*     */     //   488: invokestatic drawRectForFloat : (FFFFI)V
/*     */     //   491: fconst_1
/*     */     //   492: fstore #4
/*     */     //   494: ldc 7.5
/*     */     //   496: fstore #5
/*     */     //   498: ldc 8.5
/*     */     //   500: fstore #6
/*     */     //   502: aload_2
/*     */     //   503: pop
/*     */     //   504: getstatic awareline/main/mod/implement/visual/HUD.dynamicColor : Lawareline/main/mod/values/Option;
/*     */     //   507: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   510: checkcast java/lang/Boolean
/*     */     //   513: invokevirtual booleanValue : ()Z
/*     */     //   516: ifeq -> 736
/*     */     //   519: aload_2
/*     */     //   520: pop
/*     */     //   521: getstatic awareline/main/mod/implement/visual/HUD.rainbow : Lawareline/main/mod/values/Option;
/*     */     //   524: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   527: checkcast java/lang/Boolean
/*     */     //   530: invokevirtual booleanValue : ()Z
/*     */     //   533: ifeq -> 633
/*     */     //   536: ldc 7.5
/*     */     //   538: aload_0
/*     */     //   539: getfield categoryPosition : F
/*     */     //   542: fconst_1
/*     */     //   543: fsub
/*     */     //   544: aload_0
/*     */     //   545: getfield shouldY : F
/*     */     //   548: fadd
/*     */     //   549: fconst_1
/*     */     //   550: fadd
/*     */     //   551: ldc 8.5
/*     */     //   553: aload_0
/*     */     //   554: getfield categoryPosition : F
/*     */     //   557: ldc 11.0
/*     */     //   559: fadd
/*     */     //   560: aload_0
/*     */     //   561: getfield shouldY : F
/*     */     //   564: fadd
/*     */     //   565: fconst_2
/*     */     //   566: fsub
/*     */     //   567: aload_2
/*     */     //   568: pop
/*     */     //   569: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   572: ldc 'Client'
/*     */     //   574: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   577: ifeq -> 590
/*     */     //   580: aload_0
/*     */     //   581: getfield color332 : Ljava/awt/Color;
/*     */     //   584: invokevirtual getRGB : ()I
/*     */     //   587: goto -> 597
/*     */     //   590: aload_0
/*     */     //   591: getfield color33 : Ljava/awt/Color;
/*     */     //   594: invokevirtual getRGB : ()I
/*     */     //   597: aload_2
/*     */     //   598: pop
/*     */     //   599: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   602: ldc 'Client'
/*     */     //   604: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   607: ifeq -> 620
/*     */     //   610: aload_0
/*     */     //   611: getfield color333 : Ljava/awt/Color;
/*     */     //   614: invokevirtual getRGB : ()I
/*     */     //   617: goto -> 627
/*     */     //   620: aload_0
/*     */     //   621: getfield color332 : Ljava/awt/Color;
/*     */     //   624: invokevirtual getRGB : ()I
/*     */     //   627: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   630: goto -> 892
/*     */     //   633: aload_2
/*     */     //   634: new java/awt/Color
/*     */     //   637: dup
/*     */     //   638: aload_2
/*     */     //   639: pop
/*     */     //   640: getstatic awareline/main/mod/implement/visual/HUD.r : Lawareline/main/mod/values/Numbers;
/*     */     //   643: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   646: checkcast java/lang/Double
/*     */     //   649: invokevirtual intValue : ()I
/*     */     //   652: aload_2
/*     */     //   653: pop
/*     */     //   654: getstatic awareline/main/mod/implement/visual/HUD.g : Lawareline/main/mod/values/Numbers;
/*     */     //   657: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   660: checkcast java/lang/Double
/*     */     //   663: invokevirtual intValue : ()I
/*     */     //   666: aload_2
/*     */     //   667: pop
/*     */     //   668: getstatic awareline/main/mod/implement/visual/HUD.b : Lawareline/main/mod/values/Numbers;
/*     */     //   671: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   674: checkcast java/lang/Double
/*     */     //   677: invokevirtual intValue : ()I
/*     */     //   680: invokespecial <init> : (III)V
/*     */     //   683: bipush #70
/*     */     //   685: bipush #25
/*     */     //   687: invokevirtual fade : (Ljava/awt/Color;II)Ljava/awt/Color;
/*     */     //   690: astore #7
/*     */     //   692: ldc 7.5
/*     */     //   694: aload_0
/*     */     //   695: getfield categoryPosition : F
/*     */     //   698: fconst_1
/*     */     //   699: fsub
/*     */     //   700: aload_0
/*     */     //   701: getfield shouldY : F
/*     */     //   704: fadd
/*     */     //   705: ldc 1.5
/*     */     //   707: fadd
/*     */     //   708: ldc 8.5
/*     */     //   710: aload_0
/*     */     //   711: getfield categoryPosition : F
/*     */     //   714: ldc 11.0
/*     */     //   716: fadd
/*     */     //   717: aload_0
/*     */     //   718: getfield shouldY : F
/*     */     //   721: fadd
/*     */     //   722: ldc 2.5
/*     */     //   724: fsub
/*     */     //   725: aload #7
/*     */     //   727: invokevirtual getRGB : ()I
/*     */     //   730: invokestatic drawRectForFloat : (FFFFI)V
/*     */     //   733: goto -> 892
/*     */     //   736: aload_2
/*     */     //   737: pop
/*     */     //   738: getstatic awareline/main/mod/implement/visual/HUD.rainbow : Lawareline/main/mod/values/Option;
/*     */     //   741: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   744: checkcast java/lang/Boolean
/*     */     //   747: invokevirtual booleanValue : ()Z
/*     */     //   750: ifeq -> 850
/*     */     //   753: ldc 7.5
/*     */     //   755: aload_0
/*     */     //   756: getfield categoryPosition : F
/*     */     //   759: fconst_1
/*     */     //   760: fsub
/*     */     //   761: aload_0
/*     */     //   762: getfield shouldY : F
/*     */     //   765: fadd
/*     */     //   766: fconst_1
/*     */     //   767: fadd
/*     */     //   768: ldc 8.5
/*     */     //   770: aload_0
/*     */     //   771: getfield categoryPosition : F
/*     */     //   774: ldc 11.0
/*     */     //   776: fadd
/*     */     //   777: aload_0
/*     */     //   778: getfield shouldY : F
/*     */     //   781: fadd
/*     */     //   782: fconst_2
/*     */     //   783: fsub
/*     */     //   784: aload_2
/*     */     //   785: pop
/*     */     //   786: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   789: ldc 'Client'
/*     */     //   791: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   794: ifeq -> 807
/*     */     //   797: aload_0
/*     */     //   798: getfield color332 : Ljava/awt/Color;
/*     */     //   801: invokevirtual getRGB : ()I
/*     */     //   804: goto -> 814
/*     */     //   807: aload_0
/*     */     //   808: getfield color33 : Ljava/awt/Color;
/*     */     //   811: invokevirtual getRGB : ()I
/*     */     //   814: aload_2
/*     */     //   815: pop
/*     */     //   816: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   819: ldc 'Client'
/*     */     //   821: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   824: ifeq -> 837
/*     */     //   827: aload_0
/*     */     //   828: getfield color333 : Ljava/awt/Color;
/*     */     //   831: invokevirtual getRGB : ()I
/*     */     //   834: goto -> 844
/*     */     //   837: aload_0
/*     */     //   838: getfield color332 : Ljava/awt/Color;
/*     */     //   841: invokevirtual getRGB : ()I
/*     */     //   844: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   847: goto -> 892
/*     */     //   850: ldc 7.5
/*     */     //   852: aload_0
/*     */     //   853: getfield categoryPosition : F
/*     */     //   856: fconst_1
/*     */     //   857: fsub
/*     */     //   858: aload_0
/*     */     //   859: getfield shouldY : F
/*     */     //   862: fadd
/*     */     //   863: fconst_1
/*     */     //   864: fadd
/*     */     //   865: ldc 8.5
/*     */     //   867: aload_0
/*     */     //   868: getfield categoryPosition : F
/*     */     //   871: ldc 11.0
/*     */     //   873: fadd
/*     */     //   874: aload_0
/*     */     //   875: getfield shouldY : F
/*     */     //   878: fadd
/*     */     //   879: fconst_2
/*     */     //   880: fsub
/*     */     //   881: aload_0
/*     */     //   882: invokespecial HUDColor : ()I
/*     */     //   885: aload_0
/*     */     //   886: invokespecial HUDColor : ()I
/*     */     //   889: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   892: aload_0
/*     */     //   893: getfield modulesXanim : F
/*     */     //   896: fconst_0
/*     */     //   897: fcmpl
/*     */     //   898: iflt -> 936
/*     */     //   901: aload_0
/*     */     //   902: getfield section : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   905: getstatic awareline/main/ui/gui/hud/tabgui/SideTabGui$Section.MODS : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   908: if_acmpeq -> 936
/*     */     //   911: aload_0
/*     */     //   912: aload_0
/*     */     //   913: getfield modulesXanim : F
/*     */     //   916: fconst_0
/*     */     //   917: ldc 0.015
/*     */     //   919: invokestatic processFPS : (F)F
/*     */     //   922: ldc 0.013
/*     */     //   924: invokestatic processFPS : (F)F
/*     */     //   927: invokestatic moveUD : (FFFF)F
/*     */     //   930: putfield modulesXanim : F
/*     */     //   933: goto -> 969
/*     */     //   936: aload_0
/*     */     //   937: getfield modulesXanim : F
/*     */     //   940: ldc 6.0
/*     */     //   942: fcmpg
/*     */     //   943: ifge -> 969
/*     */     //   946: aload_0
/*     */     //   947: aload_0
/*     */     //   948: getfield modulesXanim : F
/*     */     //   951: ldc 6.0
/*     */     //   953: ldc 0.015
/*     */     //   955: invokestatic processFPS : (F)F
/*     */     //   958: ldc 0.013
/*     */     //   960: invokestatic processFPS : (F)F
/*     */     //   963: invokestatic moveUD : (FFFF)F
/*     */     //   966: putfield modulesXanim : F
/*     */     //   969: aload_0
/*     */     //   970: getfield section : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   973: getstatic awareline/main/ui/gui/hud/tabgui/SideTabGui$Section.MODS : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   976: if_acmpeq -> 1006
/*     */     //   979: aload_0
/*     */     //   980: aload_0
/*     */     //   981: getfield alpha : I
/*     */     //   984: i2f
/*     */     //   985: fconst_0
/*     */     //   986: ldc 0.018
/*     */     //   988: invokestatic processFPS : (F)F
/*     */     //   991: ldc 0.015
/*     */     //   993: invokestatic processFPS : (F)F
/*     */     //   996: invokestatic moveUD : (FFFF)F
/*     */     //   999: f2i
/*     */     //   1000: putfield alpha : I
/*     */     //   1003: goto -> 1036
/*     */     //   1006: aload_0
/*     */     //   1007: getfield alpha : I
/*     */     //   1010: sipush #200
/*     */     //   1013: if_icmpge -> 1029
/*     */     //   1016: aload_0
/*     */     //   1017: dup
/*     */     //   1018: getfield alpha : I
/*     */     //   1021: iconst_5
/*     */     //   1022: iadd
/*     */     //   1023: putfield alpha : I
/*     */     //   1026: goto -> 1036
/*     */     //   1029: aload_0
/*     */     //   1030: sipush #200
/*     */     //   1033: putfield alpha : I
/*     */     //   1036: aload_0
/*     */     //   1037: getfield section : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   1040: getstatic awareline/main/ui/gui/hud/tabgui/SideTabGui$Section.MODS : Lawareline/main/ui/gui/hud/tabgui/SideTabGui$Section;
/*     */     //   1043: if_acmpeq -> 1055
/*     */     //   1046: aload_0
/*     */     //   1047: getfield modulesXanim : F
/*     */     //   1050: fconst_1
/*     */     //   1051: fcmpl
/*     */     //   1052: ifle -> 1386
/*     */     //   1055: aload_0
/*     */     //   1056: aload_3
/*     */     //   1057: aload_0
/*     */     //   1058: getfield categoryTab : I
/*     */     //   1061: aaload
/*     */     //   1062: invokespecial getModsInCategory : (Lawareline/main/mod/ModuleType;)Ljava/util/List;
/*     */     //   1065: invokeinterface size : ()I
/*     */     //   1070: istore #8
/*     */     //   1072: aload_0
/*     */     //   1073: getfield baseCategoryWidth : F
/*     */     //   1076: aload_0
/*     */     //   1077: getfield modulesXanim : F
/*     */     //   1080: fadd
/*     */     //   1081: aload_0
/*     */     //   1082: getfield categoryPosition : F
/*     */     //   1085: aload_0
/*     */     //   1086: getfield shouldY : F
/*     */     //   1089: fadd
/*     */     //   1090: fconst_1
/*     */     //   1091: fsub
/*     */     //   1092: aload_0
/*     */     //   1093: getfield baseCategoryWidth : F
/*     */     //   1096: fconst_2
/*     */     //   1097: fadd
/*     */     //   1098: ldc 16.0
/*     */     //   1100: fsub
/*     */     //   1101: ldc 79.0
/*     */     //   1103: fadd
/*     */     //   1104: aload_0
/*     */     //   1105: getfield modulesXanim : F
/*     */     //   1108: fadd
/*     */     //   1109: ldc 10.0
/*     */     //   1111: fadd
/*     */     //   1112: ldc 10.0
/*     */     //   1114: fadd
/*     */     //   1115: aload_0
/*     */     //   1116: getfield categoryPosition : F
/*     */     //   1119: iload #8
/*     */     //   1121: bipush #12
/*     */     //   1123: imul
/*     */     //   1124: i2f
/*     */     //   1125: fadd
/*     */     //   1126: fconst_1
/*     */     //   1127: fsub
/*     */     //   1128: aload_0
/*     */     //   1129: getfield shouldY : F
/*     */     //   1132: fadd
/*     */     //   1133: getstatic awareline/main/Client.instance : Lawareline/main/Client;
/*     */     //   1136: sipush #255
/*     */     //   1139: invokevirtual getClientColor : (I)I
/*     */     //   1142: getstatic awareline/main/Client.instance : Lawareline/main/Client;
/*     */     //   1145: sipush #255
/*     */     //   1148: invokevirtual getClientColor : (I)I
/*     */     //   1151: invokestatic drawGradientSidewaysForFloat : (FFFFII)V
/*     */     //   1154: aload_2
/*     */     //   1155: pop
/*     */     //   1156: getstatic awareline/main/mod/implement/visual/HUD.rainbow : Lawareline/main/mod/values/Option;
/*     */     //   1159: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   1162: checkcast java/lang/Boolean
/*     */     //   1165: invokevirtual booleanValue : ()Z
/*     */     //   1168: ifeq -> 1298
/*     */     //   1171: aload_0
/*     */     //   1172: getfield baseCategoryWidth : F
/*     */     //   1175: aload_0
/*     */     //   1176: getfield modulesXanim : F
/*     */     //   1179: fadd
/*     */     //   1180: fconst_1
/*     */     //   1181: fadd
/*     */     //   1182: fconst_1
/*     */     //   1183: fadd
/*     */     //   1184: aload_0
/*     */     //   1185: getfield modPosition : F
/*     */     //   1188: aload_0
/*     */     //   1189: getfield shouldY : F
/*     */     //   1192: fadd
/*     */     //   1193: ldc 0.5
/*     */     //   1195: fadd
/*     */     //   1196: aload_0
/*     */     //   1197: getfield baseCategoryWidth : F
/*     */     //   1200: ldc 8.0
/*     */     //   1202: fadd
/*     */     //   1203: ldc 57.0
/*     */     //   1205: fadd
/*     */     //   1206: aload_0
/*     */     //   1207: getfield modulesXanim : F
/*     */     //   1210: fadd
/*     */     //   1211: ldc 7.0
/*     */     //   1213: fadd
/*     */     //   1214: ldc 70.0
/*     */     //   1216: fsub
/*     */     //   1217: ldc 1.5
/*     */     //   1219: fadd
/*     */     //   1220: aload_0
/*     */     //   1221: getfield modPosition : F
/*     */     //   1224: ldc 8.5
/*     */     //   1226: fadd
/*     */     //   1227: aload_0
/*     */     //   1228: getfield shouldY : F
/*     */     //   1231: fadd
/*     */     //   1232: aload_2
/*     */     //   1233: pop
/*     */     //   1234: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   1237: ldc 'Client'
/*     */     //   1239: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   1242: ifeq -> 1255
/*     */     //   1245: aload_0
/*     */     //   1246: getfield color332 : Ljava/awt/Color;
/*     */     //   1249: invokevirtual getRGB : ()I
/*     */     //   1252: goto -> 1262
/*     */     //   1255: aload_0
/*     */     //   1256: getfield color33 : Ljava/awt/Color;
/*     */     //   1259: invokevirtual getRGB : ()I
/*     */     //   1262: aload_2
/*     */     //   1263: pop
/*     */     //   1264: getstatic awareline/main/mod/implement/visual/HUD.rainbowMode : Lawareline/main/mod/values/Mode;
/*     */     //   1267: ldc 'Client'
/*     */     //   1269: invokevirtual is : (Ljava/lang/String;)Z
/*     */     //   1272: ifeq -> 1285
/*     */     //   1275: aload_0
/*     */     //   1276: getfield color333 : Ljava/awt/Color;
/*     */     //   1279: invokevirtual getRGB : ()I
/*     */     //   1282: goto -> 1292
/*     */     //   1285: aload_0
/*     */     //   1286: getfield color332 : Ljava/awt/Color;
/*     */     //   1289: invokevirtual getRGB : ()I
/*     */     //   1292: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   1295: goto -> 1370
/*     */     //   1298: aload_0
/*     */     //   1299: getfield baseCategoryWidth : F
/*     */     //   1302: aload_0
/*     */     //   1303: getfield modulesXanim : F
/*     */     //   1306: fadd
/*     */     //   1307: fconst_1
/*     */     //   1308: fadd
/*     */     //   1309: fconst_1
/*     */     //   1310: fadd
/*     */     //   1311: aload_0
/*     */     //   1312: getfield modPosition : F
/*     */     //   1315: aload_0
/*     */     //   1316: getfield shouldY : F
/*     */     //   1319: fadd
/*     */     //   1320: ldc 0.5
/*     */     //   1322: fadd
/*     */     //   1323: aload_0
/*     */     //   1324: getfield baseCategoryWidth : F
/*     */     //   1327: ldc 8.0
/*     */     //   1329: fadd
/*     */     //   1330: ldc 57.0
/*     */     //   1332: fadd
/*     */     //   1333: aload_0
/*     */     //   1334: getfield modulesXanim : F
/*     */     //   1337: fadd
/*     */     //   1338: ldc 7.0
/*     */     //   1340: fadd
/*     */     //   1341: ldc 70.0
/*     */     //   1343: fsub
/*     */     //   1344: ldc 1.5
/*     */     //   1346: fadd
/*     */     //   1347: aload_0
/*     */     //   1348: getfield modPosition : F
/*     */     //   1351: ldc 8.5
/*     */     //   1353: fadd
/*     */     //   1354: aload_0
/*     */     //   1355: getfield shouldY : F
/*     */     //   1358: fadd
/*     */     //   1359: aload_0
/*     */     //   1360: invokespecial HUDColorOnlyMobs : ()I
/*     */     //   1363: aload_0
/*     */     //   1364: invokespecial HUDColorOnlyMobs : ()I
/*     */     //   1367: invokestatic drawGradientSidewaysVForFloat : (FFFFII)V
/*     */     //   1370: iconst_0
/*     */     //   1371: istore #7
/*     */     //   1373: iload #7
/*     */     //   1375: iload #8
/*     */     //   1377: if_icmpge -> 1386
/*     */     //   1380: iinc #7, 1
/*     */     //   1383: goto -> 1373
/*     */     //   1386: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #342	-> 0
/*     */     //   #344	-> 9
/*     */     //   #345	-> 27
/*     */     //   #347	-> 28
/*     */     //   #348	-> 40
/*     */     //   #350	-> 41
/*     */     //   #352	-> 42
/*     */     //   #353	-> 60
/*     */     //   #355	-> 61
/*     */     //   #356	-> 73
/*     */     //   #360	-> 74
/*     */     //   #361	-> 78
/*     */     //   #362	-> 104
/*     */     //   #363	-> 120
/*     */     //   #364	-> 142
/*     */     //   #365	-> 150
/*     */     //   #366	-> 176
/*     */     //   #367	-> 182
/*     */     //   #369	-> 190
/*     */     //   #371	-> 195
/*     */     //   #372	-> 203
/*     */     //   #373	-> 213
/*     */     //   #375	-> 229
/*     */     //   #376	-> 236
/*     */     //   #377	-> 245
/*     */     //   #379	-> 260
/*     */     //   #380	-> 268
/*     */     //   #381	-> 278
/*     */     //   #385	-> 291
/*     */     //   #386	-> 308
/*     */     //   #387	-> 322
/*     */     //   #388	-> 332
/*     */     //   #390	-> 337
/*     */     //   #391	-> 348
/*     */     //   #392	-> 359
/*     */     //   #393	-> 370
/*     */     //   #394	-> 387
/*     */     //   #395	-> 404
/*     */     //   #397	-> 421
/*     */     //   #398	-> 425
/*     */     //   #400	-> 429
/*     */     //   #402	-> 435
/*     */     //   #403	-> 449
/*     */     //   #404	-> 485
/*     */     //   #403	-> 488
/*     */     //   #405	-> 491
/*     */     //   #406	-> 494
/*     */     //   #407	-> 498
/*     */     //   #408	-> 502
/*     */     //   #409	-> 519
/*     */     //   #410	-> 536
/*     */     //   #411	-> 574
/*     */     //   #410	-> 627
/*     */     //   #413	-> 633
/*     */     //   #414	-> 692
/*     */     //   #415	-> 727
/*     */     //   #414	-> 730
/*     */     //   #416	-> 733
/*     */     //   #418	-> 736
/*     */     //   #419	-> 753
/*     */     //   #421	-> 850
/*     */     //   #425	-> 892
/*     */     //   #426	-> 911
/*     */     //   #428	-> 936
/*     */     //   #429	-> 946
/*     */     //   #432	-> 969
/*     */     //   #433	-> 979
/*     */     //   #435	-> 1006
/*     */     //   #436	-> 1016
/*     */     //   #438	-> 1029
/*     */     //   #441	-> 1036
/*     */     //   #442	-> 1055
/*     */     //   #443	-> 1072
/*     */     //   #447	-> 1139
/*     */     //   #443	-> 1151
/*     */     //   #449	-> 1154
/*     */     //   #450	-> 1171
/*     */     //   #453	-> 1239
/*     */     //   #450	-> 1292
/*     */     //   #455	-> 1298
/*     */     //   #458	-> 1360
/*     */     //   #455	-> 1367
/*     */     //   #461	-> 1370
/*     */     //   #466	-> 1386
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   692	41	7	Ranbow	Ljava/awt/Color;
/*     */     //   1072	314	8	size	I
/*     */     //   1373	13	7	i	I
/*     */     //   0	1387	0	this	Lawareline/main/ui/gui/hud/tabgui/SideTabGui;
/*     */     //   0	1387	1	e	Lawareline/main/event/events/ketaShaderCall/EventShader;
/*     */     //   78	1309	2	HUD	Lawareline/main/mod/implement/visual/HUD;
/*     */     //   429	958	3	values	[Lawareline/main/mod/ModuleType;
/*     */     //   494	893	4	move	F
/*     */     //   498	889	5	x	F
/*     */     //   502	885	6	x2	F
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
/*     */   private int HUDColorOnlyMobs() {
/* 468 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), this.alpha)).getRGB();
/*     */   }
/*     */   
/*     */   private int HUDColor() {
/* 472 */     return (new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue(), 200)).getRGB();
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Module> getModsInCategory(ModuleType category) {
/* 477 */     List<Module> modList = new ArrayList<>();
/* 478 */     for (Module mod : Client.instance.getModuleManager().getModules()) {
/* 479 */       if (mod.getModuleType() == category) {
/* 480 */         modList.add(mod);
/*     */       }
/*     */     } 
/* 483 */     modList.sort(Comparator.comparing(Module::getHUDName));
/* 484 */     return modList;
/*     */   }
/*     */   
/*     */   private enum Section {
/* 488 */     CATEGORY,
/* 489 */     MODS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\hud\tabgui\SideTabGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */