/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.shaders.config.ShaderOption;
/*    */ import net.optifine.shaders.config.ShaderProfile;
/*    */ 
/*    */ 
/*    */ public class ShaderUtils
/*    */ {
/*    */   public static ShaderOption getShaderOption(String name, ShaderOption[] opts) {
/* 11 */     if (opts == null)
/*    */     {
/* 13 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 17 */     for (int i = 0; i < opts.length; i++) {
/*    */       
/* 19 */       ShaderOption shaderoption = opts[i];
/*    */       
/* 21 */       if (shaderoption.getName().equals(name))
/*    */       {
/* 23 */         return shaderoption;
/*    */       }
/*    */     } 
/*    */     
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ShaderProfile detectProfile(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 33 */     if (profs == null)
/*    */     {
/* 35 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 39 */     for (int i = 0; i < profs.length; i++) {
/*    */       
/* 41 */       ShaderProfile shaderprofile = profs[i];
/*    */       
/* 43 */       if (matchProfile(shaderprofile, opts, def))
/*    */       {
/* 45 */         return shaderprofile;
/*    */       }
/*    */     } 
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean matchProfile(ShaderProfile prof, ShaderOption[] opts, boolean def) {
/* 55 */     if (prof == null)
/*    */     {
/* 57 */       return false;
/*    */     }
/* 59 */     if (opts == null)
/*    */     {
/* 61 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 65 */     String[] astring = prof.getOptions();
/*    */     
/* 67 */     for (int i = 0; i < astring.length; i++) {
/*    */       
/* 69 */       String s = astring[i];
/* 70 */       ShaderOption shaderoption = getShaderOption(s, opts);
/*    */       
/* 72 */       if (shaderoption != null) {
/*    */         
/* 74 */         String s1 = def ? shaderoption.getValueDefault() : shaderoption.getValue();
/* 75 */         String s2 = prof.getValue(s);
/*    */         
/* 77 */         if (!Config.equals(s1, s2))
/*    */         {
/* 79 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 84 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\ShaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */