/*     */ package awareline.main.ui.font;
/*     */ 
/*     */ import awareline.main.ui.font.cfont.CFontRenderer;
/*     */ import java.awt.Font;
/*     */ import java.io.InputStream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class FontLoaders
/*     */ {
/*  12 */   public final CFontRenderer Noti45 = new CFontRenderer(
/*  13 */       getFont("NOTIFICATIONS", 45)),
/*  16 */      SF15 = new CFontRenderer(getFont("SF", 15)),
/*  17 */      SF16 = new CFontRenderer(getFont("SF", 16)),
/*  18 */      SF17 = new CFontRenderer(getFont("SF", 17)),
/*  19 */      SF18 = new CFontRenderer(getFont("SF", 18)),
/*  20 */      SF19 = new CFontRenderer(getFont("SF", 19)),
/*  21 */      SF20 = new CFontRenderer(getFont("SF", 20)),
/*  22 */      SF21 = new CFontRenderer(getFont("SF", 21)),
/*  23 */      SF23 = new CFontRenderer(getFont("SF", 23)),
/*  24 */      SF24 = new CFontRenderer(getFont("SF", 24)),
/*  25 */      SF22 = new CFontRenderer(getFont("SF", 22)),
/*  26 */      SF32 = new CFontRenderer(getFont("SF", 32)),
/*  29 */      FLUXICON21 = new CFontRenderer(FLUX(21)),
/*  30 */      FLUXICON16 = new CFontRenderer(FLUX(16)),
/*  31 */      FLUXICON17 = new CFontRenderer(FLUX(17)),
/*  34 */      NovICON10 = new CFontRenderer(FLUX(10)),
/*  35 */      NovICON12 = new CFontRenderer(FLUX(12)),
/*  36 */      NovICON24 = new CFontRenderer(FLUX(24)),
/*  37 */      NovICON42 = new CFontRenderer(FLUX(42)),
/*  40 */      Comfortaa10 = new CFontRenderer(getComfortaa(10)),
/*  41 */      Comfortaa11 = new CFontRenderer(getComfortaa(11)),
/*  42 */      Comfortaa12 = new CFontRenderer(getComfortaa(12)),
/*  43 */      Comfortaa13 = new CFontRenderer(getComfortaa(13)),
/*  44 */      Comfortaa14 = new CFontRenderer(getComfortaa(14)),
/*  45 */      Comfortaa15 = new CFontRenderer(getComfortaa(15)),
/*  46 */      Comfortaa16 = new CFontRenderer(getComfortaa(16)),
/*  47 */      Comfortaa17 = new CFontRenderer(getComfortaa(17)),
/*  48 */      Comfortaa18 = new CFontRenderer(getComfortaa(18)),
/*  49 */      Comfortaa19 = new CFontRenderer(getComfortaa(19)),
/*  50 */      Comfortaa20 = new CFontRenderer(getComfortaa(20)),
/*  51 */      Comfortaa22 = new CFontRenderer(getComfortaa(22)),
/*  52 */      Comfortaa24 = new CFontRenderer(getComfortaa(24)),
/*  54 */      Comfortaa30 = new CFontRenderer(getComfortaa(30)),
/*  55 */      Comfortaa34 = new CFontRenderer(getComfortaa(34)),
/*  56 */      Comfortaa36 = new CFontRenderer(getComfortaa(36)),
/*  57 */      Comfortaa45 = new CFontRenderer(getComfortaa(45)),
/*  60 */      bold50 = new CFontRenderer(getBold(50)),
/*  61 */      bold45 = new CFontRenderer(getBold(45)),
/*  62 */      bold30 = new CFontRenderer(getBold(30)),
/*  65 */      guiicons22 = new CFontRenderer(GuiICONS(22)),
/*  66 */      guiicons28 = new CFontRenderer(GuiICONS(28)),
/*  67 */      guiicons30 = new CFontRenderer(GuiICONS(30)),
/*  70 */      icon24 = new CFontRenderer(GUIICONS2(24)),
/*  71 */      icon26 = new CFontRenderer(GUIICONS2(26)),
/*  74 */      CSGO40 = new CFontRenderer(getFont("CSGO", 40)),
/*  75 */      CSGO46 = new CFontRenderer(getFont("CSGO", 46)),
/*  76 */      CSGO36 = new CFontRenderer(getFont("CSGO", 36)),
/*  79 */      guiicons18 = new CFontRenderer(GuiICONS(18)),
/*  80 */      guiicons26 = new CFontRenderer(GuiICONS(26)),
/*  81 */      guiicons24 = new CFontRenderer(GuiICONS(24)),
/*  84 */      icon18 = new CFontRenderer(GUIICONS2(18)),
/*  85 */      icon20 = new CFontRenderer(GUIICONS2(20));
/*     */   
/*  87 */   public final CFontRenderer bold18 = new CFontRenderer(
/*  88 */       bold(18)),
/*  89 */      bold20 = new CFontRenderer(bold(20)),
/*  90 */      bold22 = new CFontRenderer(bold(22)),
/*  91 */      bold16 = new CFontRenderer(bold(16)),
/*  92 */      bold15 = new CFontRenderer(bold(15)),
/*  94 */      thebold20 = new CFontRenderer(bold(20)),
/*  96 */      Logo10 = new CFontRenderer(FLUX(10)),
/*  98 */      sessionInfo16 = new CFontRenderer(getInfoFont(16)),
/*  99 */      sessionInfo19 = new CFontRenderer(getInfoFont(19)),
/* 100 */      sessionInfo20 = new CFontRenderer(getInfoFont(20)),
/* 101 */      sessionInfo22 = new CFontRenderer(getInfoFont(22)),
/* 103 */      novoicons25 = new CFontRenderer(getFont("stylesicons", 25)),
/* 104 */      novoicons24 = new CFontRenderer(getFont("stylesicons", 24)),
/* 105 */      novoicons18 = new CFontRenderer(getFont("stylesicons", 18)),
/* 106 */      novoicons45 = new CFontRenderer(getFont("stylesicons", 45)),
/* 108 */      productsans16 = new CFontRenderer(getFont("productsans", 16)),
/* 109 */      productsans17 = new CFontRenderer(getFont("productsans", 17)),
/* 110 */      productsans18 = new CFontRenderer(getFont("productsans", 18)),
/* 111 */      productsans19 = new CFontRenderer(getFont("productsans", 19)),
/* 112 */      productsans20 = new CFontRenderer(getFont("productsans", 20)),
/* 114 */      regular26 = new CFontRenderer(getFont("regular", 26)),
/* 115 */      regular22 = new CFontRenderer(getFont("regular", 22)),
/* 116 */      regular24 = new CFontRenderer(getFont("regular", 24)),
/* 117 */      regular20 = new CFontRenderer(getFont("regular", 20)),
/* 118 */      regular32 = new CFontRenderer(getFont("regular", 32)),
/* 119 */      regular19 = new CFontRenderer(getFont("regular", 19)),
/* 120 */      regular18 = new CFontRenderer(getFont("regular", 18)),
/* 121 */      regular17 = new CFontRenderer(getFont("regular", 17)),
/* 122 */      regular16 = new CFontRenderer(getFont("regular", 16)),
/* 123 */      regular15 = new CFontRenderer(getFont("regular", 15)),
/* 124 */      regular14 = new CFontRenderer(getFont("regular", 14)),
/* 125 */      regular13 = new CFontRenderer(getFont("regular", 13)),
/* 126 */      regular12 = new CFontRenderer(getFont("regular", 12)),
/* 127 */      regular10 = new CFontRenderer(getFont("regular", 10)),
/* 130 */      HUDCu16 = new CFontRenderer(getFont("cu", 16)),
/* 131 */      Sans18 = new CFontRenderer(getFont("product_sans_regular", 18)),
/* 132 */      Sans20 = new CFontRenderer(getFont("product_sans_regular", 20)),
/* 133 */      Sans19 = new CFontRenderer(getFont("product_sans_regular", 19)),
/* 135 */      Roboto16 = new CFontRenderer(getFont("roboto", 16)),
/* 136 */      Roboto17 = new CFontRenderer(getFont("roboto", 17)),
/* 137 */      Roboto18 = new CFontRenderer(getFont("roboto", 18)),
/* 138 */      Roboto20 = new CFontRenderer(getFont("roboto", 20));
/*     */ 
/*     */ 
/*     */   
/*     */   private Font getFont(String fontName, int size) {
/*     */     Font font;
/*     */     try {
/* 145 */       InputStream is2 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("client/fonts/" + fontName + ".ttf")).getInputStream();
/* 146 */       font = Font.createFont(0, is2);
/* 147 */       font = font.deriveFont(0, size);
/* 148 */     } catch (Exception ex2) {
/* 149 */       System.err.println("Error loading font " + fontName);
/* 150 */       font = new Font("Arial", 0, size);
/*     */     } 
/*     */     
/* 153 */     return font;
/*     */   }
/*     */ 
/*     */   
/*     */   private Font getInfoFont(int size) {
/* 158 */     return getFont("SessionInfo", size);
/*     */   }
/*     */   
/*     */   public Font getBold(int size) {
/* 162 */     return getFont("ArialBold", size);
/*     */   }
/*     */   
/*     */   public Font bold(int size) {
/* 166 */     return getFont("Lato-Bold", size);
/*     */   }
/*     */   
/*     */   private Font GUIICONS2(int size) {
/* 170 */     return getFont("GuiICONS2", size);
/*     */   }
/*     */   
/*     */   private Font GuiICONS(int size) {
/* 174 */     return getFont("GuiICONS", size);
/*     */   }
/*     */   
/*     */   private Font FLUX(int size) {
/* 178 */     return getFont("fluxicon", size);
/*     */   }
/*     */   
/*     */   private Font getComfortaa(int size) {
/* 182 */     return getFont("Arial", size);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\FontLoaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */