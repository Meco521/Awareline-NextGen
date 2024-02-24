/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ public class FrameEvent
/*    */ {
/* 10 */   private static final Map<String, Integer> mapEventFrames = new HashMap<>();
/*    */ 
/*    */   
/*    */   public static boolean isActive(String name, int frameInterval) {
/* 14 */     synchronized (mapEventFrames) {
/*    */       
/* 16 */       int i = (Minecraft.getMinecraft()).entityRenderer.frameCount;
/* 17 */       Integer integer = mapEventFrames.get(name);
/*    */       
/* 19 */       if (integer == null) {
/*    */         
/* 21 */         integer = new Integer(i);
/* 22 */         mapEventFrames.put(name, integer);
/*    */       } 
/*    */       
/* 25 */       int j = integer.intValue();
/*    */       
/* 27 */       if (i > j && i < j + frameInterval)
/*    */       {
/* 29 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 33 */       mapEventFrames.put(name, new Integer(i));
/* 34 */       return true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\FrameEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */