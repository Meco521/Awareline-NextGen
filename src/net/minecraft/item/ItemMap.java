/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.HashMultiset;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Multiset;
/*     */ import com.google.common.collect.Multisets;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class ItemMap
/*     */   extends ItemMapBase {
/*     */   protected ItemMap() {
/*  29 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MapData loadMapData(int mapId, World worldIn) {
/*  34 */     String s = "map_" + mapId;
/*  35 */     MapData mapdata = (MapData)worldIn.loadItemData(MapData.class, s);
/*     */     
/*  37 */     if (mapdata == null) {
/*     */       
/*  39 */       mapdata = new MapData(s);
/*  40 */       worldIn.setItemData(s, (WorldSavedData)mapdata);
/*     */     } 
/*     */     
/*  43 */     return mapdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapData getMapData(ItemStack stack, World worldIn) {
/*  48 */     String s = "map_" + stack.getMetadata();
/*  49 */     MapData mapdata = (MapData)worldIn.loadItemData(MapData.class, s);
/*     */     
/*  51 */     if (mapdata == null && !worldIn.isRemote) {
/*     */       
/*  53 */       stack.setItemDamage(worldIn.getUniqueDataId("map"));
/*  54 */       s = "map_" + stack.getMetadata();
/*  55 */       mapdata = new MapData(s);
/*  56 */       mapdata.scale = 3;
/*  57 */       mapdata.calculateMapCenter(worldIn.getWorldInfo().getSpawnX(), worldIn.getWorldInfo().getSpawnZ(), mapdata.scale);
/*  58 */       mapdata.dimension = (byte)worldIn.provider.getDimensionId();
/*  59 */       mapdata.markDirty();
/*  60 */       worldIn.setItemData(s, (WorldSavedData)mapdata);
/*     */     } 
/*     */     
/*  63 */     return mapdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateMapData(World worldIn, Entity viewer, MapData data) {
/*  68 */     if (worldIn.provider.getDimensionId() == data.dimension && viewer instanceof EntityPlayer) {
/*     */       
/*  70 */       int i = 1 << data.scale;
/*  71 */       int j = data.xCenter;
/*  72 */       int k = data.zCenter;
/*  73 */       int l = MathHelper.floor_double(viewer.posX - j) / i + 64;
/*  74 */       int i1 = MathHelper.floor_double(viewer.posZ - k) / i + 64;
/*  75 */       int j1 = 128 / i;
/*     */       
/*  77 */       if (worldIn.provider.getHasNoSky())
/*     */       {
/*  79 */         j1 /= 2;
/*     */       }
/*     */       
/*  82 */       MapData.MapInfo mapdata$mapinfo = data.getMapInfo((EntityPlayer)viewer);
/*  83 */       mapdata$mapinfo.field_82569_d++;
/*  84 */       boolean flag = false;
/*     */       
/*  86 */       for (int k1 = l - j1 + 1; k1 < l + j1; k1++) {
/*     */         
/*  88 */         if ((k1 & 0xF) == (mapdata$mapinfo.field_82569_d & 0xF) || flag) {
/*     */           
/*  90 */           flag = false;
/*  91 */           double d0 = 0.0D;
/*     */           
/*  93 */           for (int l1 = i1 - j1 - 1; l1 < i1 + j1; l1++) {
/*     */             
/*  95 */             if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
/*     */               
/*  97 */               int i2 = k1 - l;
/*  98 */               int j2 = l1 - i1;
/*  99 */               boolean flag1 = (i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2));
/* 100 */               int k2 = (j / i + k1 - 64) * i;
/* 101 */               int l2 = (k / i + l1 - 64) * i;
/* 102 */               HashMultiset hashMultiset = HashMultiset.create();
/* 103 */               Chunk chunk = worldIn.getChunkFromBlockCoords(new BlockPos(k2, 0, l2));
/*     */               
/* 105 */               if (!chunk.isEmpty()) {
/*     */                 
/* 107 */                 int i3 = k2 & 0xF;
/* 108 */                 int j3 = l2 & 0xF;
/* 109 */                 int k3 = 0;
/* 110 */                 double d1 = 0.0D;
/*     */                 
/* 112 */                 if (worldIn.provider.getHasNoSky()) {
/*     */                   
/* 114 */                   int l3 = k2 + l2 * 231871;
/* 115 */                   l3 = l3 * l3 * 31287121 + l3 * 11;
/*     */                   
/* 117 */                   if ((l3 >> 20 & 0x1) == 0) {
/*     */                     
/* 119 */                     hashMultiset.add(Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.DIRT)), 10);
/*     */                   }
/*     */                   else {
/*     */                     
/* 123 */                     hashMultiset.add(Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.STONE)), 100);
/*     */                   } 
/*     */                   
/* 126 */                   d1 = 100.0D;
/*     */                 }
/*     */                 else {
/*     */                   
/* 130 */                   BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */                   
/* 132 */                   for (int i4 = 0; i4 < i; i4++) {
/*     */                     
/* 134 */                     for (int j4 = 0; j4 < i; j4++) {
/*     */                       
/* 136 */                       int k4 = chunk.getHeightValue(i4 + i3, j4 + j3) + 1;
/* 137 */                       IBlockState iblockstate = Blocks.air.getDefaultState();
/*     */                       
/* 139 */                       if (k4 > 1) {
/*     */                         
/*     */                         do
/*     */                         {
/*     */ 
/*     */                           
/* 145 */                           k4--;
/* 146 */                           iblockstate = chunk.getBlockState((BlockPos)blockpos$mutableblockpos.set(i4 + i3, k4, j4 + j3));
/*     */                         }
/* 148 */                         while (iblockstate.getBlock().getMapColor(iblockstate) == MapColor.airColor && k4 > 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                         
/* 154 */                         if (k4 > 0 && iblockstate.getBlock().getMaterial().isLiquid()) {
/*     */                           Block block;
/* 156 */                           int l4 = k4 - 1;
/*     */ 
/*     */                           
/*     */                           do {
/* 160 */                             block = chunk.getBlock(i4 + i3, l4--, j4 + j3);
/* 161 */                             k3++;
/*     */                           }
/* 163 */                           while (l4 > 0 && block.getMaterial().isLiquid());
/*     */                         } 
/*     */                       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/* 172 */                       d1 += k4 / (i * i);
/* 173 */                       hashMultiset.add(iblockstate.getBlock().getMapColor(iblockstate));
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 
/* 178 */                 k3 /= i * i;
/* 179 */                 double d2 = (d1 - d0) * 4.0D / (i + 4) + ((k1 + l1 & 0x1) - 0.5D) * 0.4D;
/* 180 */                 int i5 = 1;
/*     */                 
/* 182 */                 if (d2 > 0.6D)
/*     */                 {
/* 184 */                   i5 = 2;
/*     */                 }
/*     */                 
/* 187 */                 if (d2 < -0.6D)
/*     */                 {
/* 189 */                   i5 = 0;
/*     */                 }
/*     */                 
/* 192 */                 MapColor mapcolor = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)hashMultiset), MapColor.airColor);
/*     */                 
/* 194 */                 if (mapcolor == MapColor.waterColor) {
/*     */                   
/* 196 */                   d2 = k3 * 0.1D + (k1 + l1 & 0x1) * 0.2D;
/* 197 */                   i5 = 1;
/*     */                   
/* 199 */                   if (d2 < 0.5D)
/*     */                   {
/* 201 */                     i5 = 2;
/*     */                   }
/*     */                   
/* 204 */                   if (d2 > 0.9D)
/*     */                   {
/* 206 */                     i5 = 0;
/*     */                   }
/*     */                 } 
/*     */                 
/* 210 */                 d0 = d1;
/*     */                 
/* 212 */                 if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 0x1) != 0)) {
/*     */                   
/* 214 */                   byte b0 = data.colors[k1 + (l1 << 7)];
/* 215 */                   byte b1 = (byte)((mapcolor.colorIndex << 2) + i5);
/*     */                   
/* 217 */                   if (b0 != b1) {
/*     */                     
/* 219 */                     data.colors[k1 + (l1 << 7)] = b1;
/* 220 */                     data.updateMapData(k1, l1);
/* 221 */                     flag = true;
/*     */                   } 
/*     */                 } 
/*     */               } 
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
/*     */   
/*     */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
/* 238 */     if (!worldIn.isRemote) {
/*     */       
/* 240 */       MapData mapdata = getMapData(stack, worldIn);
/*     */       
/* 242 */       if (entityIn instanceof EntityPlayer) {
/*     */         
/* 244 */         EntityPlayer entityplayer = (EntityPlayer)entityIn;
/* 245 */         mapdata.updateVisiblePlayers(entityplayer, stack);
/*     */       } 
/*     */       
/* 248 */       if (isSelected)
/*     */       {
/* 250 */         updateMapData(worldIn, entityIn, mapdata);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player) {
/* 257 */     return getMapData(stack, worldIn).getMapPacket(stack, worldIn, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/* 265 */     if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("map_is_scaling")) {
/*     */       
/* 267 */       MapData mapdata = Items.filled_map.getMapData(stack, worldIn);
/* 268 */       stack.setItemDamage(worldIn.getUniqueDataId("map"));
/* 269 */       MapData mapdata1 = new MapData("map_" + stack.getMetadata());
/* 270 */       mapdata1.scale = (byte)(mapdata.scale + 1);
/*     */       
/* 272 */       if (mapdata1.scale > 4)
/*     */       {
/* 274 */         mapdata1.scale = 4;
/*     */       }
/*     */       
/* 277 */       mapdata1.calculateMapCenter(mapdata.xCenter, mapdata.zCenter, mapdata1.scale);
/* 278 */       mapdata1.dimension = mapdata.dimension;
/* 279 */       mapdata1.markDirty();
/* 280 */       worldIn.setItemData("map_" + stack.getMetadata(), (WorldSavedData)mapdata1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 289 */     MapData mapdata = getMapData(stack, playerIn.worldObj);
/*     */     
/* 291 */     if (advanced)
/*     */     {
/* 293 */       if (mapdata == null) {
/*     */         
/* 295 */         tooltip.add("Unknown map");
/*     */       }
/*     */       else {
/*     */         
/* 299 */         tooltip.add("Scaling at 1:" + (1 << mapdata.scale));
/* 300 */         tooltip.add("(Level " + mapdata.scale + "/" + '\004' + ")");
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\ItemMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */