/*    */ package awareline.main.utility.shader.motionblur;
/*    */ 
/*    */ import awareline.main.mod.implement.visual.MotionBlur;
/*    */ import java.io.InputStream;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.data.IMetadataSection;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ public class MotionBlurResource
/*    */   implements IResource
/*    */ {
/*    */   public InputStream getInputStream() {
/* 15 */     MotionBlur module = MotionBlur.getInstance;
/* 16 */     double amount = ((Double)module.multiplier.getValue()).doubleValue();
/* 17 */     return IOUtils.toInputStream(String.format(Locale.ENGLISH, "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}", new Object[] {
/*    */             
/* 19 */             Double.valueOf(amount), Double.valueOf(amount), Double.valueOf(amount) }));
/*    */   }
/*    */   
/*    */   public boolean hasMetadata() {
/* 23 */     return false;
/*    */   }
/*    */   
/*    */   public IMetadataSection getMetadata(String metadata) {
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public ResourceLocation getResourceLocation() {
/* 31 */     return null;
/*    */   }
/*    */   
/*    */   public String getResourcePackName() {
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\shader\motionblur\MotionBlurResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */