/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ public class TileEntitySkull
/*     */   extends TileEntity {
/*     */   private int skullType;
/*     */   private int skullRotation;
/*  19 */   private GameProfile playerProfile = null;
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  23 */     super.writeToNBT(compound);
/*  24 */     compound.setByte("SkullType", (byte)(this.skullType & 0xFF));
/*  25 */     compound.setByte("Rot", (byte)(this.skullRotation & 0xFF));
/*     */     
/*  27 */     if (this.playerProfile != null) {
/*     */       
/*  29 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  30 */       NBTUtil.writeGameProfile(nbttagcompound, this.playerProfile);
/*  31 */       compound.setTag("Owner", (NBTBase)nbttagcompound);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  37 */     super.readFromNBT(compound);
/*  38 */     this.skullType = compound.getByte("SkullType");
/*  39 */     this.skullRotation = compound.getByte("Rot");
/*     */     
/*  41 */     if (this.skullType == 3)
/*     */     {
/*  43 */       if (compound.hasKey("Owner", 10)) {
/*     */         
/*  45 */         this.playerProfile = NBTUtil.readGameProfileFromNBT(compound.getCompoundTag("Owner"));
/*     */       }
/*  47 */       else if (compound.hasKey("ExtraType", 8)) {
/*     */         
/*  49 */         String s = compound.getString("ExtraType");
/*     */         
/*  51 */         if (!StringUtils.isNullOrEmpty(s)) {
/*     */           
/*  53 */           this.playerProfile = new GameProfile((UUID)null, s);
/*  54 */           updatePlayerProfile();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile getPlayerProfile() {
/*  62 */     return this.playerProfile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/*  71 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  72 */     writeToNBT(nbttagcompound);
/*  73 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 4, nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setType(int type) {
/*  78 */     this.skullType = type;
/*  79 */     this.playerProfile = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerProfile(GameProfile playerProfile) {
/*  84 */     this.skullType = 3;
/*  85 */     this.playerProfile = playerProfile;
/*  86 */     updatePlayerProfile();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePlayerProfile() {
/*  91 */     this.playerProfile = updateGameprofile(this.playerProfile);
/*  92 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public static GameProfile updateGameprofile(GameProfile input) {
/*  97 */     if (input != null && !StringUtils.isNullOrEmpty(input.getName())) {
/*     */       
/*  99 */       if (input.isComplete() && input.getProperties().containsKey("textures"))
/*     */       {
/* 101 */         return input;
/*     */       }
/* 103 */       if (MinecraftServer.getServer() == null)
/*     */       {
/* 105 */         return input;
/*     */       }
/*     */ 
/*     */       
/* 109 */       GameProfile gameprofile = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(input.getName());
/*     */       
/* 111 */       if (gameprofile == null)
/*     */       {
/* 113 */         return input;
/*     */       }
/*     */ 
/*     */       
/* 117 */       Property property = (Property)Iterables.getFirst(gameprofile.getProperties().get("textures"), null);
/*     */       
/* 119 */       if (property == null)
/*     */       {
/* 121 */         gameprofile = MinecraftServer.getServer().getMinecraftSessionService().fillProfileProperties(gameprofile, true);
/*     */       }
/*     */       
/* 124 */       return gameprofile;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     return input;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkullType() {
/* 136 */     return this.skullType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkullRotation() {
/* 141 */     return this.skullRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSkullRotation(int rotation) {
/* 146 */     this.skullRotation = rotation;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntitySkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */