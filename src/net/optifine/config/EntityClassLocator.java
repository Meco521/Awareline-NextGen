/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.util.EntityUtils;
/*    */ 
/*    */ public class EntityClassLocator
/*    */   implements IObjectLocator
/*    */ {
/*    */   public Object getObject(ResourceLocation loc) {
/* 10 */     Class oclass = EntityUtils.getEntityClassByName(loc.getResourcePath());
/* 11 */     return oclass;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\config\EntityClassLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */