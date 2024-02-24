/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ 
/*     */ public class ReflectorForge
/*     */ {
/*  22 */   public static Object EVENT_RESULT_ALLOW = Reflector.getFieldValue(Reflector.Event_Result_ALLOW);
/*  23 */   public static Object EVENT_RESULT_DENY = Reflector.getFieldValue(Reflector.Event_Result_DENY);
/*  24 */   public static Object EVENT_RESULT_DEFAULT = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
/*     */ 
/*     */   
/*     */   public static void FMLClientHandler_trackBrokenTexture(ResourceLocation loc, String message) {
/*  28 */     if (!Reflector.FMLClientHandler_trackBrokenTexture.exists()) {
/*     */       
/*  30 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*  31 */       Reflector.call(object, Reflector.FMLClientHandler_trackBrokenTexture, new Object[] { loc, message });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void FMLClientHandler_trackMissingTexture(ResourceLocation loc) {
/*  37 */     if (!Reflector.FMLClientHandler_trackMissingTexture.exists()) {
/*     */       
/*  39 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*  40 */       Reflector.call(object, Reflector.FMLClientHandler_trackMissingTexture, new Object[] { loc });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void putLaunchBlackboard(String key, Object value) {
/*  46 */     Map<String, Object> map = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
/*     */     
/*  48 */     if (map != null)
/*     */     {
/*  50 */       map.put(key, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean renderFirstPersonHand(RenderGlobal renderGlobal, float partialTicks, int pass) {
/*  56 */     return !Reflector.ForgeHooksClient_renderFirstPersonHand.exists() ? false : Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, new Object[] { renderGlobal, Float.valueOf(partialTicks), Integer.valueOf(pass) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static InputStream getOptiFineResourceStream(String path) {
/*  61 */     if (!Reflector.OptiFineClassTransformer_instance.exists())
/*     */     {
/*  63 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  67 */     Object object = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
/*     */     
/*  69 */     if (object == null)
/*     */     {
/*  71 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  75 */     if (!path.isEmpty() && path.charAt(0) == '/')
/*     */     {
/*  77 */       path = path.substring(1);
/*     */     }
/*     */     
/*  80 */     byte[] abyte = (byte[])Reflector.call(object, Reflector.OptiFineClassTransformer_getOptiFineResource, new Object[] { path });
/*     */     
/*  82 */     if (abyte == null)
/*     */     {
/*  84 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  88 */     InputStream inputstream = new ByteArrayInputStream(abyte);
/*  89 */     return inputstream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean blockHasTileEntity(IBlockState state) {
/*  97 */     Block block = state.getBlock();
/*  98 */     return !Reflector.ForgeBlock_hasTileEntity.exists() ? block.hasTileEntity() : Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { state });
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isItemDamaged(ItemStack stack) {
/* 103 */     return !Reflector.ForgeItem_showDurabilityBar.exists() ? stack.isItemDamaged() : Reflector.callBoolean(stack.getItem(), Reflector.ForgeItem_showDurabilityBar, new Object[] { stack });
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean armorHasOverlay(ItemArmor itemArmor, ItemStack itemStack) {
/* 108 */     int i = itemArmor.getColor(itemStack);
/* 109 */     return (i != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MapData getMapData(ItemMap itemMap, ItemStack stack, World world) {
/* 114 */     return Reflector.ForgeHooksClient.exists() ? ((ItemMap)stack.getItem()).getMapData(stack, world) : itemMap.getMapData(stack, world);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getForgeModIds() {
/* 119 */     if (!Reflector.Loader.exists())
/*     */     {
/* 121 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 125 */     Object object = Reflector.call(Reflector.Loader_instance, new Object[0]);
/* 126 */     List list = (List)Reflector.call(object, Reflector.Loader_getActiveModList, new Object[0]);
/*     */     
/* 128 */     if (list == null)
/*     */     {
/* 130 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 134 */     List<String> list1 = new ArrayList<>();
/*     */     
/* 136 */     for (Object object1 : list) {
/*     */       
/* 138 */       if (Reflector.ModContainer.isInstance(object1)) {
/*     */         
/* 140 */         String s = Reflector.callString(object1, Reflector.ModContainer_getModId, new Object[0]);
/*     */         
/* 142 */         if (s != null)
/*     */         {
/* 144 */           list1.add(s);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     String[] astring = list1.<String>toArray(new String[0]);
/* 150 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canEntitySpawn(EntityLiving entityliving, World world, float x, float y, float z) {
/* 157 */     Object object = Reflector.call(Reflector.ForgeEventFactory_canEntitySpawn, new Object[] { entityliving, world, Float.valueOf(x), Float.valueOf(y), Float.valueOf(z) });
/* 158 */     return (object == EVENT_RESULT_ALLOW || (object == EVENT_RESULT_DEFAULT && entityliving.getCanSpawnHere() && entityliving.isNotColliding()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean doSpecialSpawn(EntityLiving entityliving, World world, float x, int y, float z) {
/* 163 */     return Reflector.ForgeEventFactory_doSpecialSpawn.exists() ? Reflector.callBoolean(Reflector.ForgeEventFactory_doSpecialSpawn, new Object[] { entityliving, world, Float.valueOf(x), Integer.valueOf(y), Float.valueOf(z) }) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\reflect\ReflectorForge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */