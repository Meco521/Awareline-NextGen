/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public interface ISound
/*    */ {
/*    */   ResourceLocation getSoundLocation();
/*    */   
/*    */   boolean canRepeat();
/*    */   
/*    */   int getRepeatDelay();
/*    */   
/*    */   float getVolume();
/*    */   
/*    */   float getPitch();
/*    */   
/*    */   float getXPosF();
/*    */   
/*    */   float getYPosF();
/*    */   
/*    */   float getZPosF();
/*    */   
/*    */   AttenuationType getAttenuationType();
/*    */   
/*    */   public enum AttenuationType
/*    */   {
/* 27 */     NONE(0),
/* 28 */     LINEAR(2);
/*    */     
/*    */     private final int type;
/*    */ 
/*    */     
/*    */     AttenuationType(int typeIn) {
/* 34 */       this.type = typeIn;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getTypeInt() {
/* 39 */       return this.type;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\audio\ISound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */