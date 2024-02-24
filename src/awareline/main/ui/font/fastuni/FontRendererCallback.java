/*    */ package awareline.main.ui.font.fastuni;
/*    */ 
/*    */ import com.ibm.icu.text.ArabicShaping;
/*    */ import com.ibm.icu.text.ArabicShapingException;
/*    */ import com.ibm.icu.text.Bidi;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontRendererCallback
/*    */ {
/*    */   public static boolean betterFontsEnabled = true;
/*    */   
/*    */   public static void constructor(IBFFontRenderer font, ResourceLocation location) {
/* 16 */     if (((FontRenderer)font).getClass() != FontRenderer.class)
/*    */       return; 
/* 18 */     if (location.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && font.getStringCache() == null) {
/*    */       
/* 20 */       font.setDropShadowEnabled(true);
/*    */       
/* 22 */       int[] colorCode = ((FontRenderer)font).colorCode;
/* 23 */       font.setStringCache(new StringCache(colorCode));
/* 24 */       font.getStringCache().setDefaultFont("Lucida Sans Regular", 18, true);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static String bidiReorder(IBFFontRenderer font, String text) {
/* 30 */     if (betterFontsEnabled && font.getStringCache() != null)
/*    */     {
/* 32 */       return text;
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 37 */       Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
/* 38 */       bidi.setReorderingMode(0);
/* 39 */       return bidi.writeReordered(2);
/* 40 */     } catch (ArabicShapingException var3) {
/*    */       
/* 42 */       return text;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\fastuni\FontRendererCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */