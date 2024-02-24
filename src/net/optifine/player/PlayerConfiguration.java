/*    */ package net.optifine.player;
/*    */ 
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class PlayerConfiguration
/*    */ {
/*  9 */   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
/*    */   
/*    */   private boolean initialized = false;
/*    */   
/*    */   public void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
/* 14 */     if (this.initialized)
/*    */     {
/* 16 */       for (int i = 0; i < this.playerItemModels.length; i++) {
/*    */         
/* 18 */         PlayerItemModel playeritemmodel = this.playerItemModels[i];
/* 19 */         playeritemmodel.render(modelBiped, player, scale, partialTicks);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInitialized() {
/* 26 */     return this.initialized;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInitialized(boolean initialized) {
/* 31 */     this.initialized = initialized;
/*    */   }
/*    */ 
/*    */   
/*    */   public PlayerItemModel[] getPlayerItemModels() {
/* 36 */     return this.playerItemModels;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addPlayerItemModel(PlayerItemModel playerItemModel) {
/* 41 */     this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray((Object[])this.playerItemModels, playerItemModel);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\player\PlayerConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */