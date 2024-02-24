/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultPlayerSkin
/*    */ {
/* 10 */   private static final ResourceLocation TEXTURE_STEVE = new ResourceLocation("textures/entity/steve.png");
/*    */ 
/*    */   
/* 13 */   private static final ResourceLocation TEXTURE_ALEX = new ResourceLocation("textures/entity/alex.png");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResourceLocation getDefaultSkinLegacy() {
/* 20 */     return TEXTURE_STEVE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResourceLocation getDefaultSkin(UUID playerUUID) {
/* 28 */     return isSlimSkin(playerUUID) ? TEXTURE_ALEX : TEXTURE_STEVE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getSkinType(UUID playerUUID) {
/* 36 */     return isSlimSkin(playerUUID) ? "slim" : "default";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isSlimSkin(UUID playerUUID) {
/* 44 */     return ((playerUUID.hashCode() & 0x1) == 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\DefaultPlayerSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */