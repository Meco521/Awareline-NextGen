/*    */ package awareline.main.utility.render.render.blur.util;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileUtils
/*    */ {
/*    */   public static String readInputStream(InputStream inputStream) {
/* 12 */     StringBuilder stringBuilder = new StringBuilder();
/*    */     
/*    */     try {
/* 15 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); String line;
/* 16 */       while ((line = bufferedReader.readLine()) != null) {
/* 17 */         stringBuilder.append(line).append('\n');
/*    */       }
/*    */     }
/* 20 */     catch (Exception e) {
/* 21 */       e.printStackTrace();
/*    */     } 
/* 23 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\render\blu\\util\FileUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */