/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import net.optifine.expr.IExpression;
/*    */ 
/*    */ public class RenderResolverEntity
/*    */   implements IRenderResolver
/*    */ {
/*    */   public IExpression getParameter(String name) {
/*  9 */     RenderEntityParameterBool renderentityparameterbool = RenderEntityParameterBool.parse(name);
/*    */     
/* 11 */     if (renderentityparameterbool != null)
/*    */     {
/* 13 */       return (IExpression)renderentityparameterbool;
/*    */     }
/*    */ 
/*    */     
/* 17 */     RenderEntityParameterFloat renderentityparameterfloat = RenderEntityParameterFloat.parse(name);
/* 18 */     return (renderentityparameterfloat != null) ? (IExpression)renderentityparameterfloat : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\anim\RenderResolverEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */