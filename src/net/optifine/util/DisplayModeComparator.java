/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import org.lwjgl.opengl.DisplayMode;
/*    */ 
/*    */ 
/*    */ public class DisplayModeComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 11 */     DisplayMode displaymode = (DisplayMode)o1;
/* 12 */     DisplayMode displaymode1 = (DisplayMode)o2;
/* 13 */     return (displaymode.getWidth() != displaymode1.getWidth()) ? (displaymode.getWidth() - displaymode1.getWidth()) : ((displaymode.getHeight() != displaymode1.getHeight()) ? (displaymode.getHeight() - displaymode1.getHeight()) : ((displaymode.getBitsPerPixel() != displaymode1.getBitsPerPixel()) ? (displaymode.getBitsPerPixel() - displaymode1.getBitsPerPixel()) : ((displaymode.getFrequency() != displaymode1.getFrequency()) ? (displaymode.getFrequency() - displaymode1.getFrequency()) : 0)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\DisplayModeComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */