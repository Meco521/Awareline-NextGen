/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class S29PacketSoundEffect implements Packet<INetHandlerPlayClient> {
/*    */   private String soundName;
/*    */   private int posX;
/* 13 */   private int posY = Integer.MAX_VALUE;
/*    */ 
/*    */   
/*    */   private int posZ;
/*    */   
/*    */   private float soundVolume;
/*    */   
/*    */   private int soundPitch;
/*    */ 
/*    */   
/*    */   public S29PacketSoundEffect(String soundNameIn, double soundX, double soundY, double soundZ, float volume, float pitch) {
/* 24 */     Validate.notNull(soundNameIn, "name", new Object[0]);
/* 25 */     this.soundName = soundNameIn;
/* 26 */     this.posX = (int)(soundX * 8.0D);
/* 27 */     this.posY = (int)(soundY * 8.0D);
/* 28 */     this.posZ = (int)(soundZ * 8.0D);
/* 29 */     this.soundVolume = volume;
/* 30 */     this.soundPitch = (int)(pitch * 63.0F);
/* 31 */     pitch = MathHelper.clamp_float(pitch, 0.0F, 255.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) {
/* 38 */     this.soundName = buf.readStringFromBuffer(256);
/* 39 */     this.posX = buf.readInt();
/* 40 */     this.posY = buf.readInt();
/* 41 */     this.posZ = buf.readInt();
/* 42 */     this.soundVolume = buf.readFloat();
/* 43 */     this.soundPitch = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) {
/* 50 */     buf.writeString(this.soundName);
/* 51 */     buf.writeInt(this.posX);
/* 52 */     buf.writeInt(this.posY);
/* 53 */     buf.writeInt(this.posZ);
/* 54 */     buf.writeFloat(this.soundVolume);
/* 55 */     buf.writeByte(this.soundPitch);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSoundName() {
/* 60 */     return this.soundName;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getX() {
/* 65 */     return (this.posX / 8.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getY() {
/* 70 */     return (this.posY / 8.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getZ() {
/* 75 */     return (this.posZ / 8.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getVolume() {
/* 80 */     return this.soundVolume;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPitch() {
/* 85 */     return this.soundPitch / 63.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 93 */     handler.handleSoundEffect(this);
/*    */   }
/*    */   
/*    */   public S29PacketSoundEffect() {}
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S29PacketSoundEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */