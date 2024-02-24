/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerData
/*     */ {
/*     */   public String serverName;
/*     */   public String serverIP;
/*     */   public String populationInfo;
/*     */   public String serverMOTD;
/*     */   public long pingToServer;
/*  26 */   public int version = 47;
/*     */ 
/*     */   
/*  29 */   public String gameVersion = "1.8.9";
/*     */   public boolean field_78841_f;
/*     */   public String playerList;
/*  32 */   private ServerResourceMode resourceMode = ServerResourceMode.PROMPT;
/*     */   
/*     */   private String serverIcon;
/*     */   
/*     */   private boolean lanServer;
/*     */ 
/*     */   
/*     */   public ServerData(String name, String ip, boolean isLan) {
/*  40 */     this.serverName = name;
/*  41 */     this.serverIP = ip;
/*  42 */     this.lanServer = isLan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBTCompound() {
/*  50 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  51 */     nbttagcompound.setString("name", this.serverName);
/*  52 */     nbttagcompound.setString("ip", this.serverIP);
/*     */     
/*  54 */     if (this.serverIcon != null)
/*     */     {
/*  56 */       nbttagcompound.setString("icon", this.serverIcon);
/*     */     }
/*     */     
/*  59 */     if (this.resourceMode == ServerResourceMode.ENABLED) {
/*     */       
/*  61 */       nbttagcompound.setBoolean("acceptTextures", true);
/*     */     }
/*  63 */     else if (this.resourceMode == ServerResourceMode.DISABLED) {
/*     */       
/*  65 */       nbttagcompound.setBoolean("acceptTextures", false);
/*     */     } 
/*     */     
/*  68 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerResourceMode getResourceMode() {
/*  73 */     return this.resourceMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResourceMode(ServerResourceMode mode) {
/*  78 */     this.resourceMode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServerData getServerDataFromNBTCompound(NBTTagCompound nbtCompound) {
/*  86 */     ServerData serverdata = new ServerData(nbtCompound.getString("name"), nbtCompound.getString("ip"), false);
/*     */     
/*  88 */     if (nbtCompound.hasKey("icon", 8))
/*     */     {
/*  90 */       serverdata.serverIcon = nbtCompound.getString("icon");
/*     */     }
/*     */     
/*  93 */     if (nbtCompound.hasKey("acceptTextures", 1)) {
/*     */       
/*  95 */       if (nbtCompound.getBoolean("acceptTextures"))
/*     */       {
/*  97 */         serverdata.resourceMode = ServerResourceMode.ENABLED;
/*     */       }
/*     */       else
/*     */       {
/* 101 */         serverdata.resourceMode = ServerResourceMode.DISABLED;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 106 */       serverdata.resourceMode = ServerResourceMode.PROMPT;
/*     */     } 
/*     */     
/* 109 */     return serverdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBase64EncodedIconData() {
/* 117 */     return this.serverIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBase64EncodedIconData(String icon) {
/* 122 */     this.serverIcon = icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnLAN() {
/* 130 */     return this.lanServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyFrom(ServerData serverDataIn) {
/* 135 */     this.serverIP = serverDataIn.serverIP;
/* 136 */     this.serverName = serverDataIn.serverName;
/* 137 */     this.resourceMode = serverDataIn.resourceMode;
/* 138 */     this.serverIcon = serverDataIn.serverIcon;
/* 139 */     this.lanServer = serverDataIn.lanServer;
/*     */   }
/*     */   
/*     */   public enum ServerResourceMode
/*     */   {
/* 144 */     ENABLED("enabled"),
/* 145 */     DISABLED("disabled"),
/* 146 */     PROMPT("prompt");
/*     */     
/*     */     private final IChatComponent motd;
/*     */ 
/*     */     
/*     */     ServerResourceMode(String name) {
/* 152 */       this.motd = (IChatComponent)new ChatComponentTranslation("addServer.resourcePack." + name, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public IChatComponent getMotd() {
/* 157 */       return this.motd;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\multiplayer\ServerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */