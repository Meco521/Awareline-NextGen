/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ 
/*    */ public class ResourcePackFileNotFoundException
/*    */   extends FileNotFoundException
/*    */ {
/*    */   public ResourcePackFileNotFoundException(File resourcePack, String p_i1294_2_) {
/* 10 */     super(String.format("'%s' in ResourcePack '%s'", new Object[] { p_i1294_2_, resourcePack }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\ResourcePackFileNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */