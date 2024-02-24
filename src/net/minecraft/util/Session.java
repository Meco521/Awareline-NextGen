/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.util.UUIDTypeAdapter;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ 
/*    */ public class Session {
/*    */   private final String username;
/*    */   private final String playerID;
/*    */   private final String token;
/*    */   private final Type sessionType;
/*    */   private final ResourceLocation head;
/*    */   private final ThreadDownloadImageData imageData;
/*    */   
/*    */   public Session(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
/* 21 */     this.username = usernameIn;
/* 22 */     this.playerID = playerIDIn;
/* 23 */     this.token = tokenIn;
/* 24 */     this.sessionType = Type.setSessionType(sessionTypeIn);
/* 25 */     this.head = new ResourceLocation("heads/" + usernameIn);
/* 26 */     this.imageData = new ThreadDownloadImageData(null, "https://minotar.net/avatar/" + usernameIn, null, null);
/*    */   }
/*    */   
/*    */   public String getSessionID() {
/* 30 */     return "token:" + this.token + ":" + this.playerID;
/*    */   }
/*    */   
/*    */   public String getPlayerID() {
/* 34 */     return this.playerID;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 38 */     return this.username;
/*    */   }
/*    */   
/*    */   public String getToken() {
/* 42 */     return this.token;
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile getProfile() {
/*    */     try {
/* 48 */       UUID uuid = UUIDTypeAdapter.fromString(this.playerID);
/* 49 */       return new GameProfile(uuid, this.username);
/* 50 */     } catch (IllegalArgumentException var2) {
/* 51 */       return new GameProfile((UUID)null, this.username);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getSessionType() {
/* 60 */     return this.sessionType;
/*    */   }
/*    */   
/*    */   public void loadHead() {
/* 64 */     Minecraft.getMinecraft().getTextureManager().loadTexture(this.head, (ITextureObject)this.imageData);
/*    */   }
/*    */   
/*    */   public ResourceLocation getHead() {
/* 68 */     return this.head;
/*    */   }
/*    */   
/*    */   public enum Type {
/* 72 */     LEGACY("legacy"),
/* 73 */     MOJANG("mojang");
/*    */     
/* 75 */     private static final Map<String, Type> SESSION_TYPES = Maps.newHashMap();
/*    */     
/*    */     static {
/* 78 */       for (Type session$type : values()) {
/* 79 */         SESSION_TYPES.put(session$type.sessionType, session$type);
/*    */       }
/*    */     }
/*    */     
/*    */     private final String sessionType;
/*    */     
/*    */     Type(String sessionTypeIn) {
/* 86 */       this.sessionType = sessionTypeIn;
/*    */     }
/*    */     
/*    */     public static Type setSessionType(String sessionTypeIn) {
/* 90 */       return SESSION_TYPES.get(sessionTypeIn.toLowerCase());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\Session.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */