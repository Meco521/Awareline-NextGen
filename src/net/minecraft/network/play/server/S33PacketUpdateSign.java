/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S33PacketUpdateSign
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private World world;
/*    */   private BlockPos blockPos;
/*    */   private IChatComponent[] lines;
/*    */   
/*    */   public S33PacketUpdateSign() {}
/*    */   
/*    */   public S33PacketUpdateSign(World worldIn, BlockPos blockPosIn, IChatComponent[] linesIn) {
/* 24 */     this.world = worldIn;
/* 25 */     this.blockPos = blockPosIn;
/* 26 */     this.lines = new IChatComponent[] { linesIn[0], linesIn[1], linesIn[2], linesIn[3] };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.blockPos = buf.readBlockPos();
/* 35 */     this.lines = new IChatComponent[4];
/*    */     
/* 37 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 39 */       this.lines[i] = buf.readChatComponent();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 48 */     buf.writeBlockPos(this.blockPos);
/*    */     
/* 50 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 52 */       buf.writeChatComponent(this.lines[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 61 */     handler.handleUpdateSign(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPos() {
/* 66 */     return this.blockPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent[] getLines() {
/* 71 */     return this.lines;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\play\server\S33PacketUpdateSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */