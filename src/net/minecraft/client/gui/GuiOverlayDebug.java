/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import awareline.main.mod.implement.misc.fake.FakeFPSBro;
/*     */ import awareline.main.mod.implement.misc.fake.FakeRTXBro;
/*     */ import awareline.main.ui.font.fontmanager.font.FontManager;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.TextureAnimations;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.MathUtils;
/*     */ import net.optifine.util.MemoryMonitor;
/*     */ import net.optifine.util.NativeMemory;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiOverlayDebug
/*     */   extends Gui {
/*     */   private final Minecraft mc;
/*     */   private final FontRenderer fontRenderer;
/*     */   private String debugOF;
/*     */   
/*     */   public GuiOverlayDebug(Minecraft mc) {
/*  49 */     this.mc = mc;
/*  50 */     this.fontRenderer = mc.fontRendererObj;
/*     */   }
/*     */   private List<String> debugInfoLeft; private List<String> debugInfoRight; private long updateInfoLeftTimeMs; private long updateInfoRightTimeMs;
/*     */   
/*     */   public void renderDebugInfo(ScaledResolution scaledResolutionIn) {
/*  55 */     this.mc.mcProfiler.startSection("debug");
/*  56 */     GlStateManager.pushMatrix();
/*  57 */     renderDebugInfoLeft();
/*  58 */     renderDebugInfoRight(scaledResolutionIn);
/*  59 */     GlStateManager.popMatrix();
/*     */     
/*  61 */     if (this.mc.gameSettings.showLagometer)
/*     */     {
/*  63 */       renderLagometer();
/*     */     }
/*     */     
/*  66 */     this.mc.mcProfiler.endSection();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isReducedDebug() {
/*  71 */     return (this.mc.thePlayer.hasReducedDebug() || this.mc.gameSettings.reducedDebugInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderDebugInfoLeft() {
/*  76 */     List<String> list = this.debugInfoLeft;
/*     */     
/*  78 */     if (list == null || System.currentTimeMillis() > this.updateInfoLeftTimeMs) {
/*     */       
/*  80 */       list = call();
/*  81 */       this.debugInfoLeft = list;
/*  82 */       this.updateInfoLeftTimeMs = System.currentTimeMillis() + 100L;
/*     */     } 
/*     */     
/*  85 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  87 */       String s = list.get(i);
/*     */       
/*  89 */       if (!Strings.isNullOrEmpty(s)) {
/*     */         
/*  91 */         int k = FontManager.system14.getStringWidth(s);
/*  92 */         int j = this.fontRenderer.FONT_HEIGHT;
/*     */         
/*  94 */         int i1 = 2 + j * i;
/*  95 */         drawRect(1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
/*  96 */         FontManager.system14.drawString(s, 2.0D, i1, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderDebugInfoRight(ScaledResolution scaledRes) {
/* 104 */     List<String> list = this.debugInfoRight;
/*     */     
/* 106 */     if (list == null || System.currentTimeMillis() > this.updateInfoRightTimeMs) {
/*     */       
/* 108 */       list = getDebugInfoRight();
/* 109 */       this.debugInfoRight = list;
/* 110 */       this.updateInfoRightTimeMs = System.currentTimeMillis() + 100L;
/*     */     } 
/*     */     
/* 113 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 115 */       String s = list.get(i);
/*     */       
/* 117 */       if (!Strings.isNullOrEmpty(s)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 128 */         int j = this.fontRenderer.FONT_HEIGHT;
/* 129 */         int k = FontManager.system14.getStringWidth(s);
/* 130 */         int l = scaledRes.getScaledWidth() - 2 - k;
/* 131 */         int i1 = 2 + j * i;
/* 132 */         drawRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
/* 133 */         FontManager.system14.drawString(s, l, i1, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> call() {
/* 141 */     BlockPos blockpos = new BlockPos((this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity().getEntityBoundingBox()).minY, (this.mc.getRenderViewEntity()).posZ);
/*     */     
/* 143 */     if (this.mc.debug != this.debugOF) {
/*     */       
/* 145 */       StringBuilder stringbuffer = new StringBuilder(this.mc.debug);
/* 146 */       int i = Config.getFpsMin();
/* 147 */       int j = this.mc.debug.indexOf(" fps ");
/*     */       
/* 149 */       if (j >= 0)
/*     */       {
/* 151 */         stringbuffer.insert(j, FakeFPSBro.getInstance.isEnabled() ? (
/* 152 */             (int)MathUtils.randomNumber(3.0D, 0.0D) + "/" + i + (int)MathUtils.randomNumber(3.0D, 0.0D)) : ("/" + i));
/*     */       }
/*     */       
/* 155 */       if (Config.isSmoothFps())
/*     */       {
/* 157 */         stringbuffer.append(" sf");
/*     */       }
/*     */       
/* 160 */       if (Config.isFastRender())
/*     */       {
/* 162 */         stringbuffer.append(" fr");
/*     */       }
/*     */       
/* 165 */       if (Config.isAnisotropicFiltering())
/*     */       {
/* 167 */         stringbuffer.append(" af");
/*     */       }
/*     */       
/* 170 */       if (Config.isAntialiasing())
/*     */       {
/* 172 */         stringbuffer.append(" aa");
/*     */       }
/*     */       
/* 175 */       if (Config.isRenderRegions())
/*     */       {
/* 177 */         stringbuffer.append(" reg");
/*     */       }
/*     */       
/* 180 */       if (Config.isShaders())
/*     */       {
/* 182 */         stringbuffer.append(" sh");
/*     */       }
/*     */       
/* 185 */       this.mc.debug = stringbuffer.toString();
/* 186 */       this.debugOF = this.mc.debug;
/*     */     } 
/*     */     
/* 189 */     StringBuilder stringbuilder = new StringBuilder();
/* 190 */     TextureMap texturemap = Config.getTextureMap();
/* 191 */     stringbuilder.append(", A: ");
/*     */     
/* 193 */     if (SmartAnimations.isActive()) {
/*     */       
/* 195 */       stringbuilder.append(texturemap.getCountAnimationsActive() + TextureAnimations.getCountAnimationsActive());
/* 196 */       stringbuilder.append("/");
/*     */     } 
/*     */     
/* 199 */     stringbuilder.append(texturemap.getCountAnimations() + TextureAnimations.getCountAnimations());
/* 200 */     String s1 = stringbuilder.toString();
/*     */     
/* 202 */     if (isReducedDebug())
/*     */     {
/* 204 */       return Lists.newArrayList((Object[])new String[] { "Minecraft 1.8.9 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.getDebugLoadedEntities() + s1, this.mc.theWorld.getProviderName(), "", String.format("Chunk-relative: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 0xF), Integer.valueOf(blockpos.getY() & 0xF), Integer.valueOf(blockpos.getZ() & 0xF) }) });
/*     */     }
/*     */ 
/*     */     
/* 208 */     Entity entity = this.mc.getRenderViewEntity();
/* 209 */     EnumFacing enumfacing = entity.getHorizontalFacing();
/* 210 */     String s = "Invalid";
/*     */     
/* 212 */     switch (enumfacing) {
/*     */       
/*     */       case NORTH:
/* 215 */         s = "Towards negative Z";
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 219 */         s = "Towards positive Z";
/*     */         break;
/*     */       
/*     */       case WEST:
/* 223 */         s = "Towards negative X";
/*     */         break;
/*     */       
/*     */       case EAST:
/* 227 */         s = "Towards positive X";
/*     */         break;
/*     */     } 
/* 230 */     List<String> list = Lists.newArrayList((Object[])new String[] { "Minecraft 1.8.9 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.getDebugLoadedEntities() + s1, this.mc.theWorld.getProviderName(), "", String.format("XYZ: %.3f / %.5f / %.3f", new Object[] { Double.valueOf((this.mc.getRenderViewEntity()).posX), Double.valueOf((this.mc.getRenderViewEntity().getEntityBoundingBox()).minY), Double.valueOf((this.mc.getRenderViewEntity()).posZ) }), String.format("Block: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) }), String.format("Chunk: %d %d %d in %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 0xF), Integer.valueOf(blockpos.getY() & 0xF), Integer.valueOf(blockpos.getZ() & 0xF), Integer.valueOf(blockpos.getX() >> 4), Integer.valueOf(blockpos.getY() >> 4), Integer.valueOf(blockpos.getZ() >> 4) }), String.format("Facing: %s (%s) (%.1f / %.1f)", new Object[] { enumfacing, s, Float.valueOf(MathHelper.wrapAngleTo180_float(entity.rotationYaw)), Float.valueOf(MathHelper.wrapAngleTo180_float(entity.rotationPitch)) }) });
/*     */     
/* 232 */     if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(blockpos)) {
/*     */       
/* 234 */       Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(blockpos);
/* 235 */       list.add("Biome: " + (chunk.getBiome(blockpos, this.mc.theWorld.getWorldChunkManager())).biomeName);
/* 236 */       list.add("Light: " + chunk.getLightSubtracted(blockpos, 0) + " (" + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " sky, " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " block)");
/* 237 */       DifficultyInstance difficultyinstance = this.mc.theWorld.getDifficultyForLocation(blockpos);
/*     */       
/* 239 */       if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
/*     */         
/* 241 */         EntityPlayerMP entityplayermp = this.mc.getIntegratedServer().getConfigurationManager().getPlayerByUUID(this.mc.thePlayer.getUniqueID());
/*     */         
/* 243 */         if (entityplayermp != null) {
/*     */           
/* 245 */           DifficultyInstance difficultyinstance1 = this.mc.getIntegratedServer().getDifficultyAsync(entityplayermp.worldObj, new BlockPos((Entity)entityplayermp));
/*     */           
/* 247 */           if (difficultyinstance1 != null)
/*     */           {
/* 249 */             difficultyinstance = difficultyinstance1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 254 */       list.add(String.format("Local Difficulty: %.2f (Day %d)", new Object[] { Float.valueOf(difficultyinstance.getAdditionalDifficulty()), Long.valueOf(this.mc.theWorld.getWorldTime() / 24000L) }));
/*     */     } 
/*     */     
/* 257 */     if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive())
/*     */     {
/* 259 */       list.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
/*     */     }
/*     */     
/* 262 */     if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
/*     */       
/* 264 */       BlockPos blockpos1 = this.mc.objectMouseOver.getBlockPos();
/* 265 */       list.add(String.format("Looking at: %d %d %d", new Object[] { Integer.valueOf(blockpos1.getX()), Integer.valueOf(blockpos1.getY()), Integer.valueOf(blockpos1.getZ()) }));
/*     */     } 
/*     */     
/* 268 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> getDebugInfoRight() {
/* 274 */     long i = Runtime.getRuntime().maxMemory();
/* 275 */     long j = Runtime.getRuntime().totalMemory();
/* 276 */     long k = Runtime.getRuntime().freeMemory();
/* 277 */     long l = j - k;
/* 278 */     List<String> list = Lists.newArrayList((Object[])new String[] { String.format("Java: %s %dbit", new Object[] { System.getProperty("java.version"), Integer.valueOf(this.mc.isJava64bit() ? 64 : 32) }), String.format("Mem: % 2d%% %03d/%03dMB", new Object[] { Long.valueOf(l * 100L / i), Long.valueOf(bytesToMb(l)), Long.valueOf(bytesToMb(i)) }), String.format("Allocated: % 2d%% %03dMB", new Object[] { Long.valueOf(j * 100L / i), Long.valueOf(bytesToMb(j)) }), "", String.format("CPU: %s", new Object[] { OpenGlHelper.getCpu() }), "", String.format("Display: %dx%d (%s)", new Object[] { Integer.valueOf(Display.getWidth()), Integer.valueOf(Display.getHeight()), GL11.glGetString(7936)
/* 279 */             }), FakeRTXBro.getInstance.isEnabled() ? "NVIDIA GeForce RTX 3080/PCIe/SSE" : GL11.glGetString(7937), GL11.glGetString(7938) });
/* 280 */     long i1 = NativeMemory.getBufferAllocated();
/* 281 */     long j1 = NativeMemory.getBufferMaximum();
/* 282 */     String s = "Native: " + bytesToMb(i1) + "/" + bytesToMb(j1) + "MB";
/* 283 */     list.add(4, s);
/* 284 */     list.set(5, "GC: " + MemoryMonitor.getAllocationRateMb() + "MB/s");
/*     */     
/* 286 */     if (Reflector.FMLCommonHandler_getBrandings.exists()) {
/*     */       
/* 288 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 289 */       list.add("");
/* 290 */       list.addAll((Collection<? extends String>)Reflector.call(object, Reflector.FMLCommonHandler_getBrandings, new Object[] { Boolean.FALSE }));
/*     */     } 
/*     */     
/* 293 */     if (!isReducedDebug() && 
/* 294 */       this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
/* 295 */       BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 296 */       IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/*     */       
/* 298 */       if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
/* 299 */         iblockstate = iblockstate.getBlock().getActualState(iblockstate, (IBlockAccess)this.mc.theWorld, blockpos);
/*     */       }
/*     */       
/* 302 */       list.add("");
/* 303 */       list.add(String.valueOf(Block.blockRegistry.getNameForObject(iblockstate.getBlock())));
/*     */       
/* 305 */       for (UnmodifiableIterator<Map.Entry<IProperty, Comparable>> unmodifiableIterator = iblockstate.getProperties().entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<IProperty, Comparable> entry = unmodifiableIterator.next();
/* 306 */         StringBuilder s1 = new StringBuilder(((Comparable)entry.getValue()).toString());
/*     */         
/* 308 */         if (entry.getValue() == Boolean.TRUE) {
/* 309 */           s1.append(EnumChatFormatting.GREEN);
/* 310 */         } else if (entry.getValue() == Boolean.FALSE) {
/* 311 */           s1.append(EnumChatFormatting.RED);
/*     */         } 
/*     */         
/* 314 */         list.add(((IProperty)entry.getKey()).getName() + ": " + s1); }
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 319 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderLagometer() {}
/*     */ 
/*     */   
/*     */   private static long bytesToMb(long bytes) {
/* 328 */     return bytes / 1024L / 1024L;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiOverlayDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */