/*    */ package awareline.main.ui.font.fastuni;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class FontLoader
/*    */ {
/* 10 */   public static FastUniFontRenderer PF17 = getFont(17, "regular.ttf", 26);
/* 11 */   public static FastUniFontRenderer PF18 = getFont(18, "regular.ttf", 26);
/* 12 */   public static FastUniFontRenderer PF16 = getFont(16, "regular.ttf", 26);
/* 13 */   public static FastUniFontRenderer PF32 = getFont(32, "regular.ttf", 26);
/*    */ 
/*    */   
/*    */   public static void init() {}
/*    */ 
/*    */   
/*    */   public static FastUniFontRenderer getFont(int size, String fontname) {
/*    */     Font font;
/*    */     try {
/* 22 */       InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("client/fonts/" + fontname)).getInputStream();
/* 23 */       font = Font.createFont(0, is);
/* 24 */       font = font.deriveFont(0, size);
/* 25 */     } catch (Exception ex) {
/* 26 */       ex.printStackTrace();
/* 27 */       System.out.println("Error loading font");
/* 28 */       font = new Font("default", 0, size);
/*    */     } 
/*    */     
/* 31 */     return new FastUniFontRenderer(font, size, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public static FastUniFontRenderer getFont(int size, String fontname, int prog) {
/*    */     Font font;
/*    */     try {
/* 38 */       InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("client/fonts/" + fontname)).getInputStream();
/* 39 */       font = Font.createFont(0, is);
/* 40 */       font = font.deriveFont(0, size);
/* 41 */     } catch (Exception ex) {
/* 42 */       ex.printStackTrace();
/* 43 */       System.out.println("Error loading font");
/* 44 */       font = new Font("default", 0, size);
/*    */     } 
/* 46 */     Minecraft.getMinecraft().drawSplashScreen(prog);
/*    */     
/* 48 */     return new FastUniFontRenderer(font, size, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Font getFonts(int size, String fontname) {
/*    */     Font font;
/*    */     try {
/* 55 */       InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("client/fonts/" + fontname)).getInputStream();
/* 56 */       font = Font.createFont(0, is);
/* 57 */       font = font.deriveFont(0, size);
/* 58 */     } catch (Exception ex) {
/* 59 */       ex.printStackTrace();
/* 60 */       System.out.println("Error loading font");
/* 61 */       font = new Font("default", 0, size);
/*    */     } 
/*    */     
/* 64 */     return font;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fastuni\FontLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */