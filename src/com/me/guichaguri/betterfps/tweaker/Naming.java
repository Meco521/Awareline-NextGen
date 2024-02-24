/*     */ package com.me.guichaguri.betterfps.tweaker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Naming
/*     */ {
/*  13 */   M_StaticBlock("<clinit>", null, null, "()V"),
/*  14 */   M_Constructor("<init>", null, null, "()V"),
/*     */   
/*  16 */   C_Minecraft("net.minecraft.client.Minecraft", null),
/*  17 */   C_KeyBinding("net.minecraft.client.settings.KeyBinding", null),
/*  18 */   C_World("net.minecraft.world.World", null, null),
/*  19 */   C_Chunk("net.minecraft.world.chunk.Chunk", null, null),
/*  20 */   C_ChunkCoordIntPair("net.minecraft.world.ChunkCoordIntPair", null, null),
/*  21 */   C_EntityPlayer("net.minecraft.entity.player.EntityPlayer", null, null),
/*  22 */   C_MathHelper("net.minecraft.util.MathHelper", null, null),
/*  23 */   C_PrimedTNT("net.minecraft.entity.item.EntityTNTPrimed", null, null),
/*  24 */   C_ClientBrandRetriever("net.minecraft.client.ClientBrandRetriever", null),
/*  25 */   C_GuiOptions("net.minecraft.client.gui.GuiOptions", null),
/*  26 */   C_WorldClient("net.minecraft.client.multiplayer.WorldClient", null),
/*  27 */   C_WorldServer("net.minecraft.world.WorldServer", null, null),
/*  28 */   C_IntegratedServer("net.minecraft.server.integrated.IntegratedServer", null),
/*  29 */   C_DedicatedServer("net.minecraft.server.dedicated.DedicatedServer", null, null),
/*  30 */   C_TileEntityBeacon("net.minecraft.tileentity.TileEntityBeacon", null, null),
/*  31 */   C_BeamSegment("net.minecraft.tileentity.TileEntityBeacon$BeamSegment", null, null),
/*  32 */   C_TileEntityHopper("net.minecraft.tileentity.TileEntityHopper", null, null),
/*  33 */   C_BlockHopper("net.minecraft.block.BlockHopper", null, null),
/*  34 */   C_ModelBox("net.minecraft.client.model.ModelBox", null),
/*  35 */   C_EntityRenderer("net.minecraft.client.renderer.EntityRenderer", null),
/*     */   
/*  37 */   M_startGame("startGame", null, null, "()V"),
/*  38 */   M_onTick("onTick", null, null, "(I)V"),
/*  39 */   M_sin("sin", "a", null, "(F)F"),
/*  40 */   M_cos("cos", "b", null, "(F)F"),
/*  41 */   M_tick("tick", null, null, "()V"),
/*  42 */   M_onUpdate("onUpdate", null, null, "()V"),
/*  43 */   M_updateBlocks("updateBlocks", null, null, "()V"),
/*  44 */   M_getClientModName("getClientModName", null, null, "()Ljava/lang/String;"),
/*  45 */   M_freeMemory("freeMemory", null, null, "()V"),
/*  46 */   M_initGui("initGui", null, null, "()V"),
/*  47 */   M_startServer("startServer", null, null, "()Z"),
/*  48 */   M_setActivePlayerChunksAndCheckLight("setActivePlayerChunksAndCheckLight", null, null, "()V"),
/*  49 */   M_captureDroppedItems("captureDroppedItems", null, null, null),
/*     */   
/*  51 */   F_memoryReserve("memoryReserve", null, null, "[B"),
/*  52 */   F_SIN_TABLE("SIN_TABLE", "a", null, "[F");
/*     */ 
/*     */   
/*     */   private boolean useOb = false;
/*     */   
/*     */   private boolean useBukkit = false;
/*     */   
/*     */   private final String deob;
/*     */   
/*     */   private final String deobRepl;
/*     */   
/*     */   private String ob;
/*     */   
/*     */   private String obRepl;
/*     */   
/*     */   private String bukkit;
/*     */   
/*     */   private String bukkitRepl;
/*     */   
/*     */   private String desc;
/*     */ 
/*     */   
/*     */   Naming(String deob, String ob, String bukkit) {
/*  75 */     this.deob = deob;
/*  76 */     this.deobRepl = deob.replaceAll("\\.", "/");
/*  77 */     if (ob != null) {
/*  78 */       this.useOb = true;
/*  79 */       this.ob = ob;
/*  80 */       this.obRepl = ob.replaceAll("\\.", "/");
/*     */     } 
/*  82 */     if (bukkit != null) {
/*  83 */       this.useBukkit = true;
/*  84 */       this.bukkit = bukkit;
/*  85 */       this.bukkitRepl = bukkit.replaceAll("\\.", "/");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Naming(String deob, String ob, String bukkit, String desc) {
/*  92 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(String name) {
/*  97 */     if (name.equals(this.deob)) return true; 
/*  98 */     if (this.useOb && name.equals(this.ob)) return true; 
/*  99 */     if (this.useBukkit && name.equals(this.bukkit)) return true; 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isASM(String name) {
/* 105 */     if (name.equals(this.deobRepl)) return true; 
/* 106 */     if (this.useOb && name.equals(this.obRepl)) return true; 
/* 107 */     if (this.useBukkit && name.equals(this.bukkitRepl)) return true; 
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(String name, String desc) {
/* 113 */     if (this.desc.equals(desc) && is(name)) {
/* 114 */       return true;
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\tweaker\Naming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */