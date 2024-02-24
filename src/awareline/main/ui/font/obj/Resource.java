/*    */ package awareline.main.ui.font.obj;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public final class Resource {
/*    */   private static final String BASE_DIRECTORY = "/assets/vector/";
/*    */   private static final String BASE_DIRECTORY2 = "/assets/minecraft/client/fonts/";
/*    */   private final String resourceName;
/*    */   
/*    */   public Resource(String resourceName) {
/* 11 */     this.resourceName = formatName(resourceName);
/*    */   }
/*    */   public Resource(boolean clientResourse, String... names) {
/* 14 */     StringBuilder b = new StringBuilder();
/* 15 */     int len = names.length;
/*    */     
/* 17 */     for (int i = 0; i < len; i++) {
/* 18 */       b.append(names[i]);
/*    */       
/* 20 */       if (i != len - 1) {
/* 21 */         b.append('/');
/*    */       }
/*    */     } 
/*    */     
/* 25 */     this.resourceName = formatName2(b.toString());
/*    */   }
/*    */   
/*    */   public Resource(String... names) {
/* 29 */     StringBuilder b = new StringBuilder();
/* 30 */     int len = names.length;
/*    */     
/* 32 */     for (int i = 0; i < len; i++) {
/* 33 */       b.append(names[i]);
/*    */       
/* 35 */       if (i != len - 1) {
/* 36 */         b.append('/');
/*    */       }
/*    */     } 
/*    */     
/* 40 */     this.resourceName = formatName(b.toString());
/*    */   }
/*    */   
/*    */   public InputStream openStream() {
/* 44 */     InputStream in = Resource.class.getResourceAsStream(this.resourceName);
/*    */     
/* 46 */     if (in == null) throw new RuntimeException("Can't open input stream: " + this.resourceName);
/*    */     
/* 48 */     return in;
/*    */   }
/*    */   
/*    */   private static String formatName2(String name) {
/* 52 */     return "/assets/minecraft/client/fonts/" + name;
/*    */   }
/*    */   
/*    */   private static String formatName(String name) {
/* 56 */     return "/assets/vector/" + name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\font\obj\Resource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */