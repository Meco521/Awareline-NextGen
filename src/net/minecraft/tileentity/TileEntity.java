/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockJukebox;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class TileEntity
/*     */ {
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*  22 */   private static final Map<String, Class<? extends TileEntity>> nameToClassMap = Maps.newHashMap();
/*  23 */   static Map<Class<? extends TileEntity>, String> classToNameMap = Maps.newHashMap();
/*     */   
/*     */   protected World worldObj;
/*     */   
/*  27 */   protected BlockPos pos = BlockPos.ORIGIN;
/*     */   protected boolean tileEntityInvalid;
/*  29 */   private int blockMetadata = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Block blockType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMapping(Class<? extends TileEntity> cl, String id) {
/*  39 */     if (nameToClassMap.containsKey(id))
/*     */     {
/*  41 */       throw new IllegalArgumentException("Duplicate id: " + id);
/*     */     }
/*     */ 
/*     */     
/*  45 */     nameToClassMap.put(id, cl);
/*  46 */     classToNameMap.put(cl, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  55 */     return this.worldObj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldObj(World worldIn) {
/*  63 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWorldObj() {
/*  71 */     return (this.worldObj != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  76 */     this.pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  81 */     String s = classToNameMap.get(getClass());
/*     */     
/*  83 */     if (s == null)
/*     */     {
/*  85 */       throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
/*     */     }
/*     */ 
/*     */     
/*  89 */     compound.setString("id", s);
/*  90 */     compound.setInteger("x", this.pos.getX());
/*  91 */     compound.setInteger("y", this.pos.getY());
/*  92 */     compound.setInteger("z", this.pos.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TileEntity createAndLoadEntity(NBTTagCompound nbt) {
/* 101 */     TileEntity tileentity = null;
/*     */ 
/*     */     
/*     */     try {
/* 105 */       Class<? extends TileEntity> oclass = nameToClassMap.get(nbt.getString("id"));
/*     */       
/* 107 */       if (oclass != null)
/*     */       {
/* 109 */         tileentity = oclass.newInstance();
/*     */       }
/*     */     }
/* 112 */     catch (Exception exception) {
/*     */       
/* 114 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 117 */     if (tileentity != null) {
/*     */       
/* 119 */       tileentity.readFromNBT(nbt);
/*     */     }
/*     */     else {
/*     */       
/* 123 */       logger.warn("Skipping BlockEntity with id " + nbt.getString("id"));
/*     */     } 
/*     */     
/* 126 */     return tileentity;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockMetadata() {
/* 131 */     if (this.blockMetadata == -1) {
/*     */       
/* 133 */       IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
/* 134 */       this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */     } 
/*     */     
/* 137 */     return this.blockMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 146 */     if (this.worldObj != null) {
/*     */       
/* 148 */       IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
/* 149 */       this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 150 */       this.worldObj.markChunkDirty(this.pos, this);
/*     */       
/* 152 */       if (getBlockType() != Blocks.air)
/*     */       {
/* 154 */         this.worldObj.updateComparatorOutputLevel(this.pos, getBlockType());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistanceSq(double x, double y, double z) {
/* 164 */     double d0 = this.pos.getX() + 0.5D - x;
/* 165 */     double d1 = this.pos.getY() + 0.5D - y;
/* 166 */     double d2 = this.pos.getZ() + 0.5D - z;
/* 167 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/* 172 */     return 4096.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPos() {
/* 177 */     return this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlockType() {
/* 185 */     if (this.blockType == null)
/*     */     {
/* 187 */       this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
/*     */     }
/*     */     
/* 190 */     return this.blockType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/* 199 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvalid() {
/* 204 */     return this.tileEntityInvalid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 212 */     this.tileEntityInvalid = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 220 */     this.tileEntityInvalid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateContainingBlockInfo() {
/* 230 */     this.blockType = null;
/* 231 */     this.blockMetadata = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addInfoToCrashReport(CrashReportCategory reportCategory) {
/* 236 */     reportCategory.addCrashSectionCallable("Name", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 239 */             return (String)TileEntity.classToNameMap.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
/*     */           }
/*     */         });
/*     */     
/* 243 */     if (this.worldObj != null) {
/*     */       
/* 245 */       CrashReportCategory.addBlockInfo(reportCategory, this.pos, getBlockType(), getBlockMetadata());
/* 246 */       reportCategory.addCrashSectionCallable("Actual block type", new Callable<String>()
/*     */           {
/*     */             public String call() {
/* 249 */               int i = Block.getIdFromBlock(TileEntity.this.worldObj.getBlockState(TileEntity.this.pos).getBlock());
/*     */ 
/*     */               
/*     */               try {
/* 253 */                 return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(i), Block.getBlockById(i).getUnlocalizedName(), Block.getBlockById(i).getClass().getCanonicalName() });
/*     */               }
/* 255 */               catch (Throwable var3) {
/*     */                 
/* 257 */                 return "ID #" + i;
/*     */               } 
/*     */             }
/*     */           });
/* 261 */       reportCategory.addCrashSectionCallable("Actual block data value", new Callable<String>()
/*     */           {
/*     */             public String call() {
/* 264 */               IBlockState iblockstate = TileEntity.this.worldObj.getBlockState(TileEntity.this.pos);
/* 265 */               int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */               
/* 267 */               if (i < 0)
/*     */               {
/* 269 */                 return "Unknown? (Got " + i + ")";
/*     */               }
/*     */ 
/*     */               
/* 273 */               String s = String.format("%4s", new Object[] { Integer.toBinaryString(i) }).replace(" ", "0");
/* 274 */               return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(i), s });
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPos(BlockPos posIn) {
/* 283 */     this.pos = posIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_183000_F() {
/* 288 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 293 */     addMapping((Class)TileEntityFurnace.class, "Furnace");
/* 294 */     addMapping((Class)TileEntityChest.class, "Chest");
/* 295 */     addMapping((Class)TileEntityEnderChest.class, "EnderChest");
/* 296 */     addMapping((Class)BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
/* 297 */     addMapping((Class)TileEntityDispenser.class, "Trap");
/* 298 */     addMapping((Class)TileEntityDropper.class, "Dropper");
/* 299 */     addMapping((Class)TileEntitySign.class, "Sign");
/* 300 */     addMapping((Class)TileEntityMobSpawner.class, "MobSpawner");
/* 301 */     addMapping((Class)TileEntityNote.class, "Music");
/* 302 */     addMapping((Class)TileEntityPiston.class, "Piston");
/* 303 */     addMapping((Class)TileEntityBrewingStand.class, "Cauldron");
/* 304 */     addMapping((Class)TileEntityEnchantmentTable.class, "EnchantTable");
/* 305 */     addMapping((Class)TileEntityEndPortal.class, "Airportal");
/* 306 */     addMapping((Class)TileEntityCommandBlock.class, "Control");
/* 307 */     addMapping((Class)TileEntityBeacon.class, "Beacon");
/* 308 */     addMapping((Class)TileEntitySkull.class, "Skull");
/* 309 */     addMapping((Class)TileEntityDaylightDetector.class, "DLDetector");
/* 310 */     addMapping((Class)TileEntityHopper.class, "Hopper");
/* 311 */     addMapping((Class)TileEntityComparator.class, "Comparator");
/* 312 */     addMapping((Class)TileEntityFlowerPot.class, "FlowerPot");
/* 313 */     addMapping((Class)TileEntityBanner.class, "Banner");
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */