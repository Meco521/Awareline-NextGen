/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ServerList
/*     */ {
/*  16 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*  20 */   private final List<ServerData> servers = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public ServerList(Minecraft mcIn) {
/*  24 */     this.mc = mcIn;
/*  25 */     loadServerList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadServerList() {
/*     */     try {
/*  36 */       this.servers.clear();
/*  37 */       NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
/*     */       
/*  39 */       if (nbttagcompound == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  44 */       NBTTagList nbttaglist = nbttagcompound.getTagList("servers", 10);
/*     */       
/*  46 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */       {
/*  48 */         this.servers.add(ServerData.getServerDataFromNBTCompound(nbttaglist.getCompoundTagAt(i)));
/*     */       }
/*     */     }
/*  51 */     catch (Exception exception) {
/*     */       
/*  53 */       logger.error("Couldn't load server list", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveServerList() {
/*     */     try {
/*  65 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/*  67 */       for (ServerData serverdata : this.servers)
/*     */       {
/*  69 */         nbttaglist.appendTag((NBTBase)serverdata.getNBTCompound());
/*     */       }
/*     */       
/*  72 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  73 */       nbttagcompound.setTag("servers", (NBTBase)nbttaglist);
/*  74 */       CompressedStreamTools.safeWrite(nbttagcompound, new File(this.mc.mcDataDir, "servers.dat"));
/*     */     }
/*  76 */     catch (Exception exception) {
/*     */       
/*  78 */       logger.error("Couldn't save server list", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerData getServerData(int index) {
/*  87 */     return this.servers.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeServerData(int index) {
/*  95 */     this.servers.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServerData(ServerData server) {
/* 103 */     this.servers.add(server);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countServers() {
/* 111 */     return this.servers.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swapServers(int p_78857_1_, int p_78857_2_) {
/* 119 */     ServerData serverdata = getServerData(p_78857_1_);
/* 120 */     this.servers.set(p_78857_1_, getServerData(p_78857_2_));
/* 121 */     this.servers.set(p_78857_2_, serverdata);
/* 122 */     saveServerList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_147413_a(int index, ServerData server) {
/* 127 */     this.servers.set(index, server);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_147414_b(ServerData p_147414_0_) {
/* 132 */     ServerList serverlist = new ServerList(Minecraft.getMinecraft());
/* 133 */     serverlist.loadServerList();
/*     */     
/* 135 */     for (int i = 0; i < serverlist.countServers(); i++) {
/*     */       
/* 137 */       ServerData serverdata = serverlist.getServerData(i);
/*     */       
/* 139 */       if (serverdata.serverName.equals(p_147414_0_.serverName) && serverdata.serverIP.equals(p_147414_0_.serverIP)) {
/*     */         
/* 141 */         serverlist.func_147413_a(i, p_147414_0_);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 146 */     serverlist.saveServerList();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\multiplayer\ServerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */