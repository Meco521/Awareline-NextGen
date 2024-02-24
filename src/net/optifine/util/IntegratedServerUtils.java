/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.server.integrated.IntegratedServer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ 
/*    */ public class IntegratedServerUtils
/*    */ {
/*    */   public static WorldServer getWorldServer() {
/* 20 */     Minecraft minecraft = Config.getMinecraft();
/* 21 */     WorldClient worldClient = minecraft.theWorld;
/*    */     
/* 23 */     if (worldClient == null)
/*    */     {
/* 25 */       return null;
/*    */     }
/* 27 */     if (!minecraft.isIntegratedServerRunning())
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     IntegratedServer integratedserver = minecraft.getIntegratedServer();
/*    */     
/* 35 */     if (integratedserver == null)
/*    */     {
/* 37 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 41 */     WorldProvider worldprovider = ((World)worldClient).provider;
/*    */     
/* 43 */     if (worldprovider == null)
/*    */     {
/* 45 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 49 */     int i = worldprovider.getDimensionId();
/*    */ 
/*    */     
/*    */     try {
/* 53 */       WorldServer worldserver = integratedserver.worldServerForDimension(i);
/* 54 */       return worldserver;
/*    */     }
/* 56 */     catch (NullPointerException var6) {
/*    */       
/* 58 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Entity getEntity(UUID uuid) {
/* 67 */     WorldServer worldserver = getWorldServer();
/*    */     
/* 69 */     if (worldserver == null)
/*    */     {
/* 71 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 75 */     Entity entity = worldserver.getEntityFromUuid(uuid);
/* 76 */     return entity;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static TileEntity getTileEntity(BlockPos pos) {
/* 82 */     WorldServer worldserver = getWorldServer();
/*    */     
/* 84 */     if (worldserver == null)
/*    */     {
/* 86 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 90 */     Chunk chunk = worldserver.getChunkProvider().provideChunk(pos.getX() >> 4, pos.getZ() >> 4);
/*    */     
/* 92 */     if (chunk == null)
/*    */     {
/* 94 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 98 */     TileEntity tileentity = chunk.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
/* 99 */     return tileentity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\IntegratedServerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */