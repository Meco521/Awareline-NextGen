/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.ProfileLookupCallback;
/*    */ import java.io.File;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.StringUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class PreYggdrasilConverter
/*    */ {
/* 22 */   static final Logger LOGGER = LogManager.getLogger();
/* 23 */   public static final File OLD_IPBAN_FILE = new File("banned-ips.txt");
/* 24 */   public static final File OLD_PLAYERBAN_FILE = new File("banned-players.txt");
/* 25 */   public static final File OLD_OPS_FILE = new File("ops.txt");
/* 26 */   public static final File OLD_WHITELIST_FILE = new File("white-list.txt");
/*    */ 
/*    */   
/*    */   private static void lookupNames(MinecraftServer server, Collection<String> names, ProfileLookupCallback callback) {
/* 30 */     String[] astring = (String[])Iterators.toArray((Iterator)Iterators.filter(names.iterator(), new Predicate<String>()
/*    */           {
/*    */             public boolean apply(String p_apply_1_)
/*    */             {
/* 34 */               return !StringUtils.isNullOrEmpty(p_apply_1_);
/*    */             }
/*    */           }), String.class);
/*    */     
/* 38 */     if (server.isServerInOnlineMode()) {
/*    */       
/* 40 */       server.getGameProfileRepository().findProfilesByNames(astring, Agent.MINECRAFT, callback);
/*    */     }
/*    */     else {
/*    */       
/* 44 */       for (String s : astring) {
/*    */         
/* 46 */         UUID uuid = EntityPlayer.getUUID(new GameProfile((UUID)null, s));
/* 47 */         GameProfile gameprofile = new GameProfile(uuid, s);
/* 48 */         callback.onProfileLookupSucceeded(gameprofile);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getStringUUIDFromName(String p_152719_0_) {
/* 55 */     if (!StringUtils.isNullOrEmpty(p_152719_0_) && p_152719_0_.length() <= 16) {
/*    */       
/* 57 */       final MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 58 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(p_152719_0_);
/*    */       
/* 60 */       if (gameprofile != null && gameprofile.getId() != null)
/*    */       {
/* 62 */         return gameprofile.getId().toString();
/*    */       }
/* 64 */       if (!minecraftserver.isSinglePlayer() && minecraftserver.isServerInOnlineMode()) {
/*    */         
/* 66 */         final List<GameProfile> list = Lists.newArrayList();
/* 67 */         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback()
/*    */           {
/*    */             public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_)
/*    */             {
/* 71 */               minecraftserver.getPlayerProfileCache().addEntry(p_onProfileLookupSucceeded_1_);
/* 72 */               list.add(p_onProfileLookupSucceeded_1_);
/*    */             }
/*    */             
/*    */             public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
/* 76 */               PreYggdrasilConverter.LOGGER.warn("Could not lookup user whitelist entry for " + p_onProfileLookupFailed_1_.getName(), p_onProfileLookupFailed_2_);
/*    */             }
/*    */           };
/* 79 */         lookupNames(minecraftserver, Lists.newArrayList((Object[])new String[] { p_152719_0_ }, ), profilelookupcallback);
/* 80 */         return (!list.isEmpty() && ((GameProfile)list.get(0)).getId() != null) ? ((GameProfile)list.get(0)).getId().toString() : "";
/*    */       } 
/*    */ 
/*    */       
/* 84 */       return EntityPlayer.getUUID(new GameProfile((UUID)null, p_152719_0_)).toString();
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 89 */     return p_152719_0_;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\PreYggdrasilConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */