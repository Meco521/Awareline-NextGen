/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.RegistrySimple;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class SoundRegistry
/*    */   extends RegistrySimple<ResourceLocation, SoundEventAccessorComposite>
/*    */ {
/*    */   private Map<ResourceLocation, SoundEventAccessorComposite> soundRegistry;
/*    */   
/*    */   protected Map<ResourceLocation, SoundEventAccessorComposite> createUnderlyingMap() {
/* 15 */     this.soundRegistry = Maps.newHashMap();
/* 16 */     return this.soundRegistry;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerSound(SoundEventAccessorComposite p_148762_1_) {
/* 21 */     putObject(p_148762_1_.getSoundEventLocation(), p_148762_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clearMap() {
/* 29 */     this.soundRegistry.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\SoundRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */