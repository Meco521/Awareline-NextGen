/*    */ package awareline.main.ui.font.fontmanager;
/*    */ 
/*    */ import awareline.main.mod.manager.Manager;
/*    */ import java.awt.Font;
/*    */ import java.io.InputStream;
/*    */ import java.util.LinkedHashMap;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontManager
/*    */   implements Manager
/*    */ {
/* 15 */   private final LinkedHashMap<String, LinkedHashMap<Float, UnicodeFontRenderer>> fonts = new LinkedHashMap<>();
/*    */   
/*    */   public UnicodeFontRenderer baloo17;
/*    */   
/*    */   public UnicodeFontRenderer baloo18;
/*    */   
/*    */   public UnicodeFontRenderer arial16;
/*    */   
/*    */   public UnicodeFontRenderer arial17;
/*    */   public UnicodeFontRenderer comfortaa17;
/*    */   public UnicodeFontRenderer comfortaa16;
/*    */   public UnicodeFontRenderer Roboto17;
/*    */   public UnicodeFontRenderer SF17;
/*    */   public UnicodeFontRenderer SF18;
/*    */   public UnicodeFontRenderer RobotoLight18;
/*    */   public UnicodeFontRenderer RobotoLight40;
/*    */   
/*    */   public UnicodeFontRenderer getFont(String s, float size, boolean otf) {
/* 33 */     UnicodeFontRenderer unicodeFontRenderer = null; try {
/*    */       String s2;
/* 35 */       if (this.fonts.containsKey(s) && ((LinkedHashMap)this.fonts.get(s)).containsKey(Float.valueOf(size))) {
/* 36 */         return (UnicodeFontRenderer)((LinkedHashMap)this.fonts.get(s)).get(Float.valueOf(size));
/*    */       }
/*    */       
/* 39 */       if (otf) {
/* 40 */         s2 = ".otf";
/*    */       } else {
/* 42 */         s2 = ".ttf";
/*    */       } 
/*    */ 
/*    */       
/* 46 */       InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("client/fonts/" + s + s2)).getInputStream();
/*    */       
/* 48 */       Font font = Font.createFont(0, is).deriveFont(size);
/*    */       
/* 50 */       unicodeFontRenderer = new UnicodeFontRenderer(font.deriveFont(size), (s.equals("wqy_zenhei") || s.equals("msyh")));
/* 51 */       unicodeFontRenderer.setUnicodeFlag(true);
/* 52 */       unicodeFontRenderer.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
/* 53 */       LinkedHashMap<Float, UnicodeFontRenderer> hashMap = new LinkedHashMap<>();
/* 54 */       if (this.fonts.containsKey(s)) {
/* 55 */         hashMap.putAll(this.fonts.get(s));
/*    */       }
/* 57 */       hashMap.put(Float.valueOf(size), unicodeFontRenderer);
/* 58 */       this.fonts.put(s, hashMap);
/* 59 */     } catch (Exception ex) {
/* 60 */       ex.printStackTrace();
/*    */     } 
/*    */     
/* 63 */     return unicodeFontRenderer;
/*    */   }
/*    */   public UnicodeFontRenderer productsans16; public UnicodeFontRenderer productsans17; public UnicodeFontRenderer productsans18; public UnicodeFontRenderer productsans19; public UnicodeFontRenderer productsans20; public UnicodeFontRenderer regular15; public UnicodeFontRenderer regular16; public UnicodeFontRenderer regular17; public UnicodeFontRenderer regular18; public UnicodeFontRenderer regular19;
/*    */   public int offset;
/*    */   
/*    */   public void init() {
/* 69 */     this.baloo17 = getFont("baloo", 17.0F, false);
/* 70 */     this.baloo18 = getFont("baloo", 18.0F, false);
/* 71 */     this.arial16 = getFont("Arial", 16.0F, false);
/* 72 */     this.arial17 = getFont("Arial", 17.0F, false);
/* 73 */     this.regular15 = getFont("regular", 15.0F, false);
/* 74 */     this.regular16 = getFont("regular", 16.0F, false);
/* 75 */     this.regular17 = getFont("regular", 17.0F, false);
/* 76 */     this.regular18 = getFont("regular", 18.0F, false);
/* 77 */     this.regular19 = getFont("regular", 19.0F, false);
/* 78 */     this.productsans16 = getFont("productsans", 16.0F, false);
/* 79 */     this.productsans17 = getFont("productsans", 17.0F, false);
/* 80 */     this.productsans18 = getFont("productsans", 18.0F, false);
/* 81 */     this.productsans19 = getFont("productsans", 19.0F, false);
/* 82 */     this.productsans20 = getFont("productsans", 20.0F, false);
/* 83 */     this.SF17 = getFont("SF", 17.0F, false);
/* 84 */     this.SF18 = getFont("SF", 18.0F, false);
/* 85 */     this.comfortaa17 = getFont("comfortaa", 17.0F, false);
/* 86 */     this.comfortaa16 = getFont("comfortaa", 16.0F, false);
/* 87 */     this.Roboto17 = getFont("roboto", 17.0F, false);
/* 88 */     this.RobotoLight18 = getFont("RobotoLight", 18.0F, false);
/* 89 */     this.RobotoLight40 = getFont("RobotoLight", 40.0F, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fontmanager\FontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */