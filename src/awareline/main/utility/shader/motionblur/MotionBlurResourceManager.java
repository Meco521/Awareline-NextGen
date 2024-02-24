/*    */ package awareline.main.utility.shader.motionblur;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class MotionBlurResourceManager
/*    */   implements IResourceManager
/*    */ {
/*    */   public Set<String> getResourceDomains() {
/* 13 */     return null;
/*    */   }
/*    */   
/*    */   public IResource getResource(ResourceLocation location) {
/* 17 */     return new MotionBlurResource();
/*    */   }
/*    */   
/*    */   public List<IResource> getAllResources(ResourceLocation location) {
/* 21 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\motionblur\MotionBlurResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */