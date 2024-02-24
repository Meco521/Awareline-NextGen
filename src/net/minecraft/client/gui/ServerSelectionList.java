/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerList;
/*     */ import net.minecraft.client.network.LanServerDetector;
/*     */ 
/*     */ public class ServerSelectionList
/*     */   extends GuiListExtended
/*     */ {
/*     */   private final GuiMultiplayer owner;
/*  13 */   private final List<ServerListEntryNormal> serverListInternet = Lists.newArrayList();
/*  14 */   private final List<ServerListEntryLanDetected> serverListLan = Lists.newArrayList();
/*  15 */   private final GuiListExtended.IGuiListEntry lanScanEntry = new ServerListEntryLanScan();
/*  16 */   private int selectedSlotIndex = -1;
/*     */ 
/*     */   
/*     */   public ServerSelectionList(GuiMultiplayer ownerIn, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/*  20 */     super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*  21 */     this.owner = ownerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListExtended.IGuiListEntry getListEntry(int index) {
/*  29 */     if (index < this.serverListInternet.size())
/*     */     {
/*  31 */       return this.serverListInternet.get(index);
/*     */     }
/*     */ 
/*     */     
/*  35 */     index -= this.serverListInternet.size();
/*     */     
/*  37 */     if (index == 0)
/*     */     {
/*  39 */       return this.lanScanEntry;
/*     */     }
/*     */ 
/*     */     
/*  43 */     index--;
/*  44 */     return this.serverListLan.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  51 */     return this.serverListInternet.size() + 1 + this.serverListLan.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedSlotIndex(int selectedSlotIndexIn) {
/*  56 */     this.selectedSlotIndex = selectedSlotIndexIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int slotIndex) {
/*  64 */     return (slotIndex == this.selectedSlotIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_148193_k() {
/*  69 */     return this.selectedSlotIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_148195_a(ServerList p_148195_1_) {
/*  74 */     this.serverListInternet.clear();
/*     */     
/*  76 */     for (int i = 0; i < p_148195_1_.countServers(); i++)
/*     */     {
/*  78 */       this.serverListInternet.add(new ServerListEntryNormal(this.owner, p_148195_1_.getServerData(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_148194_a(List<LanServerDetector.LanServer> p_148194_1_) {
/*  84 */     this.serverListLan.clear();
/*     */     
/*  86 */     for (LanServerDetector.LanServer lanserverdetector$lanserver : p_148194_1_)
/*     */     {
/*  88 */       this.serverListLan.add(new ServerListEntryLanDetected(this.owner, lanserverdetector$lanserver));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  94 */     return super.getScrollBarX() + 30;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 102 */     return super.getListWidth() + 85;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\ServerSelectionList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */