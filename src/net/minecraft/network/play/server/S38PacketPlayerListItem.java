/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class S38PacketPlayerListItem
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private Action action;
/*  20 */   private final List<AddPlayerData> players = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S38PacketPlayerListItem(Action actionIn, EntityPlayerMP... players) {
/*  28 */     this.action = actionIn;
/*     */     
/*  30 */     for (EntityPlayerMP entityplayermp : players)
/*     */     {
/*  32 */       this.players.add(new AddPlayerData(entityplayermp.getGameProfile(), entityplayermp.ping, entityplayermp.theItemInWorldManager.getGameType(), entityplayermp.getTabListDisplayName()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public S38PacketPlayerListItem(Action actionIn, Iterable<EntityPlayerMP> players) {
/*  38 */     this.action = actionIn;
/*     */     
/*  40 */     for (EntityPlayerMP entityplayermp : players)
/*     */     {
/*  42 */       this.players.add(new AddPlayerData(entityplayermp.getGameProfile(), entityplayermp.ping, entityplayermp.theItemInWorldManager.getGameType(), entityplayermp.getTabListDisplayName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  51 */     this.action = (Action)buf.readEnumValue(Action.class);
/*  52 */     int i = buf.readVarIntFromBuffer();
/*     */     
/*  54 */     for (int j = 0; j < i; j++) {
/*     */       int l, i1;
/*  56 */       GameProfile gameprofile = null;
/*  57 */       int k = 0;
/*  58 */       WorldSettings.GameType worldsettings$gametype = null;
/*  59 */       IChatComponent ichatcomponent = null;
/*     */       
/*  61 */       switch (this.action) {
/*     */         
/*     */         case ADD_PLAYER:
/*  64 */           gameprofile = new GameProfile(buf.readUuid(), buf.readStringFromBuffer(16));
/*  65 */           l = buf.readVarIntFromBuffer();
/*  66 */           i1 = 0;
/*     */           
/*  68 */           for (; i1 < l; i1++) {
/*     */             
/*  70 */             String s = buf.readStringFromBuffer(32767);
/*  71 */             String s1 = buf.readStringFromBuffer(32767);
/*     */             
/*  73 */             if (buf.readBoolean()) {
/*     */               
/*  75 */               gameprofile.getProperties().put(s, new Property(s, s1, buf.readStringFromBuffer(32767)));
/*     */             }
/*     */             else {
/*     */               
/*  79 */               gameprofile.getProperties().put(s, new Property(s, s1));
/*     */             } 
/*     */           } 
/*     */           
/*  83 */           worldsettings$gametype = WorldSettings.GameType.getByID(buf.readVarIntFromBuffer());
/*  84 */           k = buf.readVarIntFromBuffer();
/*     */           
/*  86 */           if (buf.readBoolean())
/*     */           {
/*  88 */             ichatcomponent = buf.readChatComponent();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case UPDATE_GAME_MODE:
/*  94 */           gameprofile = new GameProfile(buf.readUuid(), (String)null);
/*  95 */           worldsettings$gametype = WorldSettings.GameType.getByID(buf.readVarIntFromBuffer());
/*     */           break;
/*     */         
/*     */         case UPDATE_LATENCY:
/*  99 */           gameprofile = new GameProfile(buf.readUuid(), (String)null);
/* 100 */           k = buf.readVarIntFromBuffer();
/*     */           break;
/*     */         
/*     */         case UPDATE_DISPLAY_NAME:
/* 104 */           gameprofile = new GameProfile(buf.readUuid(), (String)null);
/*     */           
/* 106 */           if (buf.readBoolean())
/*     */           {
/* 108 */             ichatcomponent = buf.readChatComponent();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case REMOVE_PLAYER:
/* 114 */           gameprofile = new GameProfile(buf.readUuid(), (String)null);
/*     */           break;
/*     */       } 
/* 117 */       this.players.add(new AddPlayerData(gameprofile, k, worldsettings$gametype, ichatcomponent));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 126 */     buf.writeEnumValue(this.action);
/* 127 */     buf.writeVarIntToBuffer(this.players.size());
/*     */     
/* 129 */     for (AddPlayerData s38packetplayerlistitem$addplayerdata : this.players) {
/*     */       
/* 131 */       switch (this.action) {
/*     */         
/*     */         case ADD_PLAYER:
/* 134 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 135 */           buf.writeString(s38packetplayerlistitem$addplayerdata.getProfile().getName());
/* 136 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getProfile().getProperties().size());
/*     */           
/* 138 */           for (Property property : s38packetplayerlistitem$addplayerdata.getProfile().getProperties().values()) {
/*     */             
/* 140 */             buf.writeString(property.getName());
/* 141 */             buf.writeString(property.getValue());
/*     */             
/* 143 */             if (property.hasSignature()) {
/*     */               
/* 145 */               buf.writeBoolean(true);
/* 146 */               buf.writeString(property.getSignature());
/*     */               
/*     */               continue;
/*     */             } 
/* 150 */             buf.writeBoolean(false);
/*     */           } 
/*     */ 
/*     */           
/* 154 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getGameMode().getID());
/* 155 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getPing());
/*     */           
/* 157 */           if (s38packetplayerlistitem$addplayerdata.getDisplayName() == null) {
/*     */             
/* 159 */             buf.writeBoolean(false);
/*     */             
/*     */             continue;
/*     */           } 
/* 163 */           buf.writeBoolean(true);
/* 164 */           buf.writeChatComponent(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case UPDATE_GAME_MODE:
/* 170 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 171 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getGameMode().getID());
/*     */ 
/*     */         
/*     */         case UPDATE_LATENCY:
/* 175 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 176 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getPing());
/*     */ 
/*     */         
/*     */         case UPDATE_DISPLAY_NAME:
/* 180 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*     */           
/* 182 */           if (s38packetplayerlistitem$addplayerdata.getDisplayName() == null) {
/*     */             
/* 184 */             buf.writeBoolean(false);
/*     */             
/*     */             continue;
/*     */           } 
/* 188 */           buf.writeBoolean(true);
/* 189 */           buf.writeChatComponent(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case REMOVE_PLAYER:
/* 195 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 205 */     handler.handlePlayerListItem(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AddPlayerData> getEntries() {
/* 210 */     return this.players;
/*     */   }
/*     */ 
/*     */   
/*     */   public Action getAction() {
/* 215 */     return this.action;
/*     */   }
/*     */   public S38PacketPlayerListItem() {}
/*     */   
/*     */   public String toString() {
/* 220 */     return Objects.toStringHelper(this).add("action", this.action).add("entries", this.players).toString();
/*     */   }
/*     */   
/*     */   public enum Action
/*     */   {
/* 225 */     ADD_PLAYER,
/* 226 */     UPDATE_GAME_MODE,
/* 227 */     UPDATE_LATENCY,
/* 228 */     UPDATE_DISPLAY_NAME,
/* 229 */     REMOVE_PLAYER;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class AddPlayerData
/*     */   {
/*     */     private final int ping;
/*     */     private final WorldSettings.GameType gamemode;
/*     */     private final GameProfile profile;
/*     */     private final IChatComponent displayName;
/*     */     
/*     */     public AddPlayerData(GameProfile profile, int pingIn, WorldSettings.GameType gamemodeIn, IChatComponent displayNameIn) {
/* 241 */       this.profile = profile;
/* 242 */       this.ping = pingIn;
/* 243 */       this.gamemode = gamemodeIn;
/* 244 */       this.displayName = displayNameIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getProfile() {
/* 249 */       return this.profile;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPing() {
/* 254 */       return this.ping;
/*     */     }
/*     */ 
/*     */     
/*     */     public WorldSettings.GameType getGameMode() {
/* 259 */       return this.gamemode;
/*     */     }
/*     */ 
/*     */     
/*     */     public IChatComponent getDisplayName() {
/* 264 */       return this.displayName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 269 */       return Objects.toStringHelper(this).add("latency", this.ping).add("gameMode", this.gamemode).add("profile", this.profile).add("displayName", (this.displayName == null) ? null : IChatComponent.Serializer.componentToJson(this.displayName)).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S38PacketPlayerListItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */