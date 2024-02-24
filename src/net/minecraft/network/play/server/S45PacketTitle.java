/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S45PacketTitle
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private Type type;
/*     */   private IChatComponent message;
/*     */   private int fadeInTime;
/*     */   private int displayTime;
/*     */   private int fadeOutTime;
/*     */   
/*     */   public S45PacketTitle() {}
/*     */   
/*     */   public S45PacketTitle(Type type, IChatComponent message) {
/*  24 */     this(type, message, -1, -1, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public S45PacketTitle(int fadeInTime, int displayTime, int fadeOutTime) {
/*  29 */     this(Type.TIMES, (IChatComponent)null, fadeInTime, displayTime, fadeOutTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public S45PacketTitle(Type type, IChatComponent message, int fadeInTime, int displayTime, int fadeOutTime) {
/*  34 */     this.type = type;
/*  35 */     this.message = message;
/*  36 */     this.fadeInTime = fadeInTime;
/*  37 */     this.displayTime = displayTime;
/*  38 */     this.fadeOutTime = fadeOutTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  46 */     this.type = (Type)buf.readEnumValue(Type.class);
/*     */     
/*  48 */     if (this.type == Type.TITLE || this.type == Type.SUBTITLE)
/*     */     {
/*  50 */       this.message = buf.readChatComponent();
/*     */     }
/*     */     
/*  53 */     if (this.type == Type.TIMES) {
/*     */       
/*  55 */       this.fadeInTime = buf.readInt();
/*  56 */       this.displayTime = buf.readInt();
/*  57 */       this.fadeOutTime = buf.readInt();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  66 */     buf.writeEnumValue(this.type);
/*     */     
/*  68 */     if (this.type == Type.TITLE || this.type == Type.SUBTITLE)
/*     */     {
/*  70 */       buf.writeChatComponent(this.message);
/*     */     }
/*     */     
/*  73 */     if (this.type == Type.TIMES) {
/*     */       
/*  75 */       buf.writeInt(this.fadeInTime);
/*  76 */       buf.writeInt(this.displayTime);
/*  77 */       buf.writeInt(this.fadeOutTime);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  86 */     handler.handleTitle(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType() {
/*  91 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent getMessage() {
/*  96 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFadeInTime() {
/* 101 */     return this.fadeInTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDisplayTime() {
/* 106 */     return this.displayTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFadeOutTime() {
/* 111 */     return this.fadeOutTime;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 116 */     TITLE,
/* 117 */     SUBTITLE,
/* 118 */     TIMES,
/* 119 */     CLEAR,
/* 120 */     RESET;
/*     */ 
/*     */     
/*     */     public static Type byName(String name) {
/* 124 */       for (Type s45packettitle$type : values()) {
/*     */         
/* 126 */         if (s45packettitle$type.name().equalsIgnoreCase(name))
/*     */         {
/* 128 */           return s45packettitle$type;
/*     */         }
/*     */       } 
/*     */       
/* 132 */       return TITLE;
/*     */     }
/*     */ 
/*     */     
/*     */     public static String[] getNames() {
/* 137 */       String[] astring = new String[(values()).length];
/* 138 */       int i = 0;
/*     */       
/* 140 */       for (Type s45packettitle$type : values())
/*     */       {
/* 142 */         astring[i++] = s45packettitle$type.name().toLowerCase();
/*     */       }
/*     */       
/* 145 */       return astring;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S45PacketTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */