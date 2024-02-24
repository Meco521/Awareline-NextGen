/*    */ package awareline.main.utility.shader.ketaUtils.render;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ResourceUtil
/*    */ {
/*    */   public static InputStream getResourceStream(String path) {
/* 11 */     String s = "/assets/minecraft/github/" + path;
/* 12 */     return ResourceUtil.class.getResourceAsStream(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\ketaUtils\render\ResourceUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */