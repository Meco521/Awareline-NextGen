/*     */ package net.minecraft.network;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*     */ import net.minecraft.network.login.server.S03PacketEnableCompression;
/*     */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*     */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*     */ import net.minecraft.network.play.server.S0APacketUseBed;
/*     */ import net.minecraft.network.play.server.S14PacketEntity;
/*     */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*     */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*     */ import net.minecraft.network.play.server.S41PacketServerDifficulty;
/*     */ import net.minecraft.network.play.server.S45PacketTitle;
/*     */ import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
/*     */ import net.minecraft.network.status.client.C01PacketPing;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public enum EnumConnectionState {
/*  25 */   HANDSHAKING(-1)
/*     */   {
/*     */     EnumConnectionState(int protocolId) {
/*  28 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C00Handshake.class);
/*     */     }
/*     */   },
/*  31 */   PLAY(0)
/*     */   {
/*     */     EnumConnectionState(int protocolId) {
/*  34 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S00PacketKeepAlive.class);
/*  35 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S01PacketJoinGame.class);
/*  36 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S02PacketChat.class);
/*  37 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S03PacketTimeUpdate.class);
/*  38 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S04PacketEntityEquipment.class);
/*  39 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S05PacketSpawnPosition.class);
/*  40 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S06PacketUpdateHealth.class);
/*  41 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S07PacketRespawn.class);
/*  42 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S08PacketPlayerPosLook.class);
/*  43 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S09PacketHeldItemChange.class);
/*  44 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S0APacketUseBed.class);
/*  45 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S0BPacketAnimation.class);
/*  46 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S0CPacketSpawnPlayer.class);
/*  47 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S0DPacketCollectItem.class);
/*  48 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S0EPacketSpawnObject.class);
/*  49 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S0FPacketSpawnMob.class);
/*  50 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S10PacketSpawnPainting.class);
/*  51 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S11PacketSpawnExperienceOrb.class);
/*  52 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S12PacketEntityVelocity.class);
/*  53 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S13PacketDestroyEntities.class);
/*  54 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S14PacketEntity.class);
/*  55 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S14PacketEntity.S15PacketEntityRelMove.class);
/*  56 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S14PacketEntity.S16PacketEntityLook.class);
/*  57 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S14PacketEntity.S17PacketEntityLookMove.class);
/*  58 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S18PacketEntityTeleport.class);
/*  59 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S19PacketEntityHeadLook.class);
/*  60 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S19PacketEntityStatus.class);
/*  61 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S1BPacketEntityAttach.class);
/*  62 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S1CPacketEntityMetadata.class);
/*  63 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S1DPacketEntityEffect.class);
/*  64 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S1EPacketRemoveEntityEffect.class);
/*  65 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S1FPacketSetExperience.class);
/*  66 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S20PacketEntityProperties.class);
/*  67 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S21PacketChunkData.class);
/*  68 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S22PacketMultiBlockChange.class);
/*  69 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S23PacketBlockChange.class);
/*  70 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S24PacketBlockAction.class);
/*  71 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S25PacketBlockBreakAnim.class);
/*  72 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S26PacketMapChunkBulk.class);
/*  73 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S27PacketExplosion.class);
/*  74 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S28PacketEffect.class);
/*  75 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S29PacketSoundEffect.class);
/*  76 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S2APacketParticles.class);
/*  77 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S2BPacketChangeGameState.class);
/*  78 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S2CPacketSpawnGlobalEntity.class);
/*  79 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S2DPacketOpenWindow.class);
/*  80 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S2EPacketCloseWindow.class);
/*  81 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S2FPacketSetSlot.class);
/*  82 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S30PacketWindowItems.class);
/*  83 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S31PacketWindowProperty.class);
/*  84 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S32PacketConfirmTransaction.class);
/*  85 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S33PacketUpdateSign.class);
/*  86 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S34PacketMaps.class);
/*  87 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S35PacketUpdateTileEntity.class);
/*  88 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S36PacketSignEditorOpen.class);
/*  89 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S37PacketStatistics.class);
/*  90 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S38PacketPlayerListItem.class);
/*  91 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S39PacketPlayerAbilities.class);
/*  92 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S3APacketTabComplete.class);
/*  93 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S3BPacketScoreboardObjective.class);
/*  94 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S3CPacketUpdateScore.class);
/*  95 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S3DPacketDisplayScoreboard.class);
/*  96 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S3EPacketTeams.class);
/*  97 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S3FPacketCustomPayload.class);
/*  98 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S40PacketDisconnect.class);
/*  99 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S41PacketServerDifficulty.class);
/* 100 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S42PacketCombatEvent.class);
/* 101 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S43PacketCamera.class);
/* 102 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S44PacketWorldBorder.class);
/* 103 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S45PacketTitle.class);
/* 104 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S46PacketSetCompressionLevel.class);
/* 105 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S47PacketPlayerListHeaderFooter.class);
/* 106 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S48PacketResourcePackSend.class);
/* 107 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S49PacketUpdateEntityNBT.class);
/* 108 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C00PacketKeepAlive.class);
/* 109 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C01PacketChatMessage.class);
/* 110 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C02PacketUseEntity.class);
/* 111 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C03PacketPlayer.class);
/* 112 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C03PacketPlayer.C04PacketPlayerPosition.class);
/* 113 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C03PacketPlayer.C05PacketPlayerLook.class);
/* 114 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C03PacketPlayer.C06PacketPlayerPosLook.class);
/* 115 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C07PacketPlayerDigging.class);
/* 116 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C08PacketPlayerBlockPlacement.class);
/* 117 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C09PacketHeldItemChange.class);
/* 118 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C0APacketAnimation.class);
/* 119 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C0BPacketEntityAction.class);
/* 120 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C0CPacketInput.class);
/* 121 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C0DPacketCloseWindow.class);
/* 122 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C0EPacketClickWindow.class);
/* 123 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C0FPacketConfirmTransaction.class);
/* 124 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C10PacketCreativeInventoryAction.class);
/* 125 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C11PacketEnchantItem.class);
/* 126 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C12PacketUpdateSign.class);
/* 127 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C13PacketPlayerAbilities.class);
/* 128 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C14PacketTabComplete.class);
/* 129 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C15PacketClientSettings.class);
/* 130 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C16PacketClientStatus.class);
/* 131 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C17PacketCustomPayload.class);
/* 132 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C18PacketSpectate.class);
/* 133 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C19PacketResourcePackStatus.class);
/*     */     }
/*     */   },
/* 136 */   STATUS(1)
/*     */   {
/*     */     EnumConnectionState(int protocolId) {
/* 139 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C00PacketServerQuery.class);
/* 140 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S00PacketServerInfo.class);
/* 141 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C01PacketPing.class);
/* 142 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S01PacketPong.class);
/*     */     }
/*     */   },
/* 145 */   LOGIN(2)
/*     */   {
/*     */     EnumConnectionState(int protocolId) {
/* 148 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S00PacketDisconnect.class);
/* 149 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S01PacketEncryptionRequest.class);
/* 150 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S02PacketLoginSuccess.class);
/* 151 */       registerPacket(EnumPacketDirection.CLIENTBOUND, (Class)S03PacketEnableCompression.class);
/* 152 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C00PacketLoginStart.class);
/* 153 */       registerPacket(EnumPacketDirection.SERVERBOUND, (Class)C01PacketEncryptionResponse.class);
/*     */     } };
/*     */   private static final int field_181136_e = -1; private static final int field_181137_f = 2; private static final EnumConnectionState[] STATES_BY_ID; private static final Map<Class<? extends Packet>, EnumConnectionState> STATES_BY_CLASS; private final int id;
/*     */   private final Map<EnumPacketDirection, BiMap<Integer, Class<? extends Packet>>> directionMaps;
/*     */   
/*     */   static {
/* 159 */     STATES_BY_ID = new EnumConnectionState[4];
/* 160 */     STATES_BY_CLASS = Maps.newHashMap();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     for (EnumConnectionState enumconnectionstate : values()) {
/*     */       
/* 221 */       int i = enumconnectionstate.id;
/*     */       
/* 223 */       if (i < -1 || i > 2)
/*     */       {
/* 225 */         throw new Error("Invalid protocol ID " + i);
/*     */       }
/*     */       
/* 228 */       STATES_BY_ID[i - -1] = enumconnectionstate;
/*     */       
/* 230 */       for (BiMap<Integer, Class<? extends Packet>> integerClassBiMap : enumconnectionstate.directionMaps.values()) {
/*     */         
/* 232 */         for (Class<? extends Packet> oclass : (Iterable<Class<? extends Packet>>)integerClassBiMap.values()) {
/*     */           
/* 234 */           if (STATES_BY_CLASS.containsKey(oclass) && STATES_BY_CLASS.get(oclass) != enumconnectionstate)
/*     */           {
/* 236 */             throw new Error("Packet " + oclass + " is already assigned to protocol " + STATES_BY_CLASS.get(oclass) + " - can't reassign to " + enumconnectionstate);
/*     */           }
/*     */ 
/*     */           
/*     */           try {
/* 241 */             oclass.newInstance();
/*     */           }
/* 243 */           catch (Throwable var10) {
/*     */             
/* 245 */             throw new Error("Packet " + oclass + " fails instantiation checks! " + oclass);
/*     */           } 
/*     */           
/* 248 */           STATES_BY_CLASS.put(oclass, enumconnectionstate);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   EnumConnectionState(int protocolId) {
/*     */     this.directionMaps = Maps.newEnumMap(EnumPacketDirection.class);
/*     */     this.id = protocolId;
/*     */   }
/*     */   
/*     */   protected EnumConnectionState registerPacket(EnumPacketDirection direction, Class<? extends Packet> packetClass) {
/*     */     HashBiMap hashBiMap;
/*     */     BiMap<Integer, Class<? extends Packet>> bimap = this.directionMaps.get(direction);
/*     */     if (bimap == null) {
/*     */       hashBiMap = HashBiMap.create();
/*     */       this.directionMaps.put(direction, hashBiMap);
/*     */     } 
/*     */     if (hashBiMap.containsValue(packetClass)) {
/*     */       String s = direction + " packet " + packetClass + " is already known to ID " + hashBiMap.inverse().get(packetClass);
/*     */       LogManager.getLogger().fatal(s);
/*     */       throw new IllegalArgumentException(s);
/*     */     } 
/*     */     hashBiMap.put(Integer.valueOf(hashBiMap.size()), packetClass);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public Integer getPacketId(EnumPacketDirection direction, Packet packetIn) {
/*     */     return (Integer)((BiMap)this.directionMaps.get(direction)).inverse().get(packetIn.getClass());
/*     */   }
/*     */   
/*     */   public Packet getPacket(EnumPacketDirection direction, int packetId) throws InstantiationException, IllegalAccessException {
/*     */     Class<? extends Packet> oclass = (Class<? extends Packet>)((BiMap)this.directionMaps.get(direction)).get(Integer.valueOf(packetId));
/*     */     return (oclass == null) ? null : oclass.newInstance();
/*     */   }
/*     */   
/*     */   public int getId() {
/*     */     return this.id;
/*     */   }
/*     */   
/*     */   public static EnumConnectionState getById(int stateId) {
/*     */     return (stateId >= -1 && stateId <= 2) ? STATES_BY_ID[stateId - -1] : null;
/*     */   }
/*     */   
/*     */   public static EnumConnectionState getFromPacket(Packet packetIn) {
/*     */     return STATES_BY_CLASS.get(packetIn.getClass());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\EnumConnectionState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */