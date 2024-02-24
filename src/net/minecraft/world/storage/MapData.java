/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S34PacketMaps;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec4b;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapData
/*     */   extends WorldSavedData
/*     */ {
/*     */   public int xCenter;
/*     */   public int zCenter;
/*     */   public byte dimension;
/*     */   public byte scale;
/*  29 */   public byte[] colors = new byte[16384];
/*  30 */   public List<MapInfo> playersArrayList = Lists.newArrayList();
/*  31 */   private final Map<EntityPlayer, MapInfo> playersHashMap = Maps.newHashMap();
/*  32 */   public Map<String, Vec4b> mapDecorations = Maps.newLinkedHashMap();
/*     */ 
/*     */   
/*     */   public MapData(String mapname) {
/*  36 */     super(mapname);
/*     */   }
/*     */ 
/*     */   
/*     */   public void calculateMapCenter(double x, double z, int mapScale) {
/*  41 */     int i = 128 * (1 << mapScale);
/*  42 */     int j = MathHelper.floor_double((x + 64.0D) / i);
/*  43 */     int k = MathHelper.floor_double((z + 64.0D) / i);
/*  44 */     this.xCenter = j * i + i / 2 - 64;
/*  45 */     this.zCenter = k * i + i / 2 - 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  53 */     this.dimension = nbt.getByte("dimension");
/*  54 */     this.xCenter = nbt.getInteger("xCenter");
/*  55 */     this.zCenter = nbt.getInteger("zCenter");
/*  56 */     this.scale = nbt.getByte("scale");
/*  57 */     this.scale = (byte)MathHelper.clamp_int(this.scale, 0, 4);
/*  58 */     int i = nbt.getShort("width");
/*  59 */     int j = nbt.getShort("height");
/*     */     
/*  61 */     if (i == 128 && j == 128) {
/*     */       
/*  63 */       this.colors = nbt.getByteArray("colors");
/*     */     }
/*     */     else {
/*     */       
/*  67 */       byte[] abyte = nbt.getByteArray("colors");
/*  68 */       this.colors = new byte[16384];
/*  69 */       int k = (128 - i) / 2;
/*  70 */       int l = (128 - j) / 2;
/*     */       
/*  72 */       for (int i1 = 0; i1 < j; i1++) {
/*     */         
/*  74 */         int j1 = i1 + l;
/*     */         
/*  76 */         if (j1 >= 0 || j1 < 128)
/*     */         {
/*  78 */           for (int k1 = 0; k1 < i; k1++) {
/*     */             
/*  80 */             int l1 = k1 + k;
/*     */             
/*  82 */             if (l1 >= 0 || l1 < 128)
/*     */             {
/*  84 */               this.colors[l1 + (j1 << 7)] = abyte[k1 + i1 * i];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  97 */     nbt.setByte("dimension", this.dimension);
/*  98 */     nbt.setInteger("xCenter", this.xCenter);
/*  99 */     nbt.setInteger("zCenter", this.zCenter);
/* 100 */     nbt.setByte("scale", this.scale);
/* 101 */     nbt.setShort("width", (short)128);
/* 102 */     nbt.setShort("height", (short)128);
/* 103 */     nbt.setByteArray("colors", this.colors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVisiblePlayers(EntityPlayer player, ItemStack mapStack) {
/* 111 */     if (!this.playersHashMap.containsKey(player)) {
/*     */       
/* 113 */       MapInfo mapdata$mapinfo = new MapInfo(player);
/* 114 */       this.playersHashMap.put(player, mapdata$mapinfo);
/* 115 */       this.playersArrayList.add(mapdata$mapinfo);
/*     */     } 
/*     */     
/* 118 */     if (!player.inventory.hasItemStack(mapStack))
/*     */     {
/* 120 */       this.mapDecorations.remove(player.getName());
/*     */     }
/*     */     
/* 123 */     for (int i = 0; i < this.playersArrayList.size(); i++) {
/*     */       
/* 125 */       MapInfo mapdata$mapinfo1 = this.playersArrayList.get(i);
/*     */       
/* 127 */       if (!mapdata$mapinfo1.entityplayerObj.isDead && (mapdata$mapinfo1.entityplayerObj.inventory.hasItemStack(mapStack) || mapStack.isOnItemFrame())) {
/*     */         
/* 129 */         if (!mapStack.isOnItemFrame() && mapdata$mapinfo1.entityplayerObj.dimension == this.dimension)
/*     */         {
/* 131 */           updateDecorations(0, mapdata$mapinfo1.entityplayerObj.worldObj, mapdata$mapinfo1.entityplayerObj.getName(), mapdata$mapinfo1.entityplayerObj.posX, mapdata$mapinfo1.entityplayerObj.posZ, mapdata$mapinfo1.entityplayerObj.rotationYaw);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 136 */         this.playersHashMap.remove(mapdata$mapinfo1.entityplayerObj);
/* 137 */         this.playersArrayList.remove(mapdata$mapinfo1);
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     if (mapStack.isOnItemFrame()) {
/*     */       
/* 143 */       EntityItemFrame entityitemframe = mapStack.getItemFrame();
/* 144 */       BlockPos blockpos = entityitemframe.getHangingPosition();
/* 145 */       updateDecorations(1, player.worldObj, "frame-" + entityitemframe.getEntityId(), blockpos.getX(), blockpos.getZ(), (entityitemframe.facingDirection.getHorizontalIndex() * 90));
/*     */     } 
/*     */     
/* 148 */     if (mapStack.hasTagCompound() && mapStack.getTagCompound().hasKey("Decorations", 9)) {
/*     */       
/* 150 */       NBTTagList nbttaglist = mapStack.getTagCompound().getTagList("Decorations", 10);
/*     */       
/* 152 */       for (int j = 0; j < nbttaglist.tagCount(); j++) {
/*     */         
/* 154 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
/*     */         
/* 156 */         if (!this.mapDecorations.containsKey(nbttagcompound.getString("id")))
/*     */         {
/* 158 */           updateDecorations(nbttagcompound.getByte("type"), player.worldObj, nbttagcompound.getString("id"), nbttagcompound.getDouble("x"), nbttagcompound.getDouble("z"), nbttagcompound.getDouble("rot"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateDecorations(int type, World worldIn, String entityIdentifier, double worldX, double worldZ, double rotation) {
/*     */     byte b2;
/* 166 */     int i = 1 << this.scale;
/* 167 */     float f = (float)(worldX - this.xCenter) / i;
/* 168 */     float f1 = (float)(worldZ - this.zCenter) / i;
/* 169 */     byte b0 = (byte)(int)((f * 2.0F) + 0.5D);
/* 170 */     byte b1 = (byte)(int)((f1 * 2.0F) + 0.5D);
/* 171 */     int j = 63;
/*     */ 
/*     */     
/* 174 */     if (f >= -j && f1 >= -j && f <= j && f1 <= j) {
/*     */       
/* 176 */       rotation += (rotation < 0.0D) ? -8.0D : 8.0D;
/* 177 */       b2 = (byte)(int)(rotation * 16.0D / 360.0D);
/*     */       
/* 179 */       if (this.dimension < 0)
/*     */       {
/* 181 */         int k = (int)(worldIn.getWorldInfo().getWorldTime() / 10L);
/* 182 */         b2 = (byte)(k * k * 34187121 + k * 121 >> 15 & 0xF);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 187 */       if (Math.abs(f) >= 320.0F || Math.abs(f1) >= 320.0F) {
/*     */         
/* 189 */         this.mapDecorations.remove(entityIdentifier);
/*     */         
/*     */         return;
/*     */       } 
/* 193 */       type = 6;
/* 194 */       b2 = 0;
/*     */       
/* 196 */       if (f <= -j)
/*     */       {
/* 198 */         b0 = (byte)(int)((j << 1) + 2.5D);
/*     */       }
/*     */       
/* 201 */       if (f1 <= -j)
/*     */       {
/* 203 */         b1 = (byte)(int)((j << 1) + 2.5D);
/*     */       }
/*     */       
/* 206 */       if (f >= j)
/*     */       {
/* 208 */         b0 = (byte)((j << 1) + 1);
/*     */       }
/*     */       
/* 211 */       if (f1 >= j)
/*     */       {
/* 213 */         b1 = (byte)((j << 1) + 1);
/*     */       }
/*     */     } 
/*     */     
/* 217 */     this.mapDecorations.put(entityIdentifier, new Vec4b((byte)type, b0, b1, b2));
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet getMapPacket(ItemStack mapStack, World worldIn, EntityPlayer player) {
/* 222 */     MapInfo mapdata$mapinfo = this.playersHashMap.get(player);
/* 223 */     return (mapdata$mapinfo == null) ? null : mapdata$mapinfo.getPacket(mapStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateMapData(int x, int y) {
/* 228 */     markDirty();
/*     */     
/* 230 */     for (MapInfo mapdata$mapinfo : this.playersArrayList)
/*     */     {
/* 232 */       mapdata$mapinfo.update(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MapInfo getMapInfo(EntityPlayer player) {
/* 238 */     MapInfo mapdata$mapinfo = this.playersHashMap.get(player);
/*     */     
/* 240 */     if (mapdata$mapinfo == null) {
/*     */       
/* 242 */       mapdata$mapinfo = new MapInfo(player);
/* 243 */       this.playersHashMap.put(player, mapdata$mapinfo);
/* 244 */       this.playersArrayList.add(mapdata$mapinfo);
/*     */     } 
/*     */     
/* 247 */     return mapdata$mapinfo;
/*     */   }
/*     */   
/*     */   public class MapInfo
/*     */   {
/*     */     public final EntityPlayer entityplayerObj;
/*     */     private boolean field_176105_d = true;
/* 254 */     private int minX = 0;
/* 255 */     private int minY = 0;
/* 256 */     private int maxX = 127;
/* 257 */     private int maxY = 127;
/*     */     
/*     */     private int field_176109_i;
/*     */     public int field_82569_d;
/*     */     
/*     */     public MapInfo(EntityPlayer player) {
/* 263 */       this.entityplayerObj = player;
/*     */     }
/*     */ 
/*     */     
/*     */     public Packet getPacket(ItemStack stack) {
/* 268 */       if (this.field_176105_d) {
/*     */         
/* 270 */         this.field_176105_d = false;
/* 271 */         return (Packet)new S34PacketMaps(stack.getMetadata(), MapData.this.scale, MapData.this.mapDecorations.values(), MapData.this.colors, this.minX, this.minY, this.maxX + 1 - this.minX, this.maxY + 1 - this.minY);
/*     */       } 
/*     */ 
/*     */       
/* 275 */       return (this.field_176109_i++ % 5 == 0) ? (Packet)new S34PacketMaps(stack.getMetadata(), MapData.this.scale, MapData.this.mapDecorations.values(), MapData.this.colors, 0, 0, 0, 0) : null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(int x, int y) {
/* 281 */       if (this.field_176105_d) {
/*     */         
/* 283 */         this.minX = Math.min(this.minX, x);
/* 284 */         this.minY = Math.min(this.minY, y);
/* 285 */         this.maxX = Math.max(this.maxX, x);
/* 286 */         this.maxY = Math.max(this.maxY, y);
/*     */       }
/*     */       else {
/*     */         
/* 290 */         this.field_176105_d = true;
/* 291 */         this.minX = x;
/* 292 */         this.minY = y;
/* 293 */         this.maxX = x;
/* 294 */         this.maxY = y;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\storage\MapData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */