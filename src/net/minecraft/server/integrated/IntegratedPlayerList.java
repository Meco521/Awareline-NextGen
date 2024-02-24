/*    */ package net.minecraft.server.integrated;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.net.SocketAddress;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.ServerConfigurationManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntegratedPlayerList
/*    */   extends ServerConfigurationManager
/*    */ {
/*    */   private NBTTagCompound hostPlayerData;
/*    */   
/*    */   public IntegratedPlayerList(IntegratedServer server) {
/* 19 */     super(server);
/* 20 */     setViewDistance(10);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writePlayerData(EntityPlayerMP playerIn) {
/* 28 */     if (playerIn.getName().equals(getServerInstance().getServerOwner())) {
/*    */       
/* 30 */       this.hostPlayerData = new NBTTagCompound();
/* 31 */       playerIn.writeToNBT(this.hostPlayerData);
/*    */     } 
/*    */     
/* 34 */     super.writePlayerData(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String allowUserToConnect(SocketAddress address, GameProfile profile) {
/* 42 */     return (profile.getName().equalsIgnoreCase(getServerInstance().getServerOwner()) && getPlayerByUsername(profile.getName()) != null) ? "That name is already taken." : super.allowUserToConnect(address, profile);
/*    */   }
/*    */ 
/*    */   
/*    */   public IntegratedServer getServerInstance() {
/* 47 */     return (IntegratedServer)super.getServerInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTTagCompound getHostPlayerData() {
/* 55 */     return this.hostPlayerData;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\integrated\IntegratedPlayerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */