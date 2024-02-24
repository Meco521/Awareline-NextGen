/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class MapGenStructureIO
/*     */ {
/*  13 */   private static final Logger logger = LogManager.getLogger();
/*  14 */   private static final Map<String, Class<? extends StructureStart>> startNameToClassMap = Maps.newHashMap();
/*  15 */   private static final Map<Class<? extends StructureStart>, String> startClassToNameMap = Maps.newHashMap();
/*  16 */   private static final Map<String, Class<? extends StructureComponent>> componentNameToClassMap = Maps.newHashMap();
/*  17 */   private static final Map<Class<? extends StructureComponent>, String> componentClassToNameMap = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private static void registerStructure(Class<? extends StructureStart> startClass, String structureName) {
/*  21 */     startNameToClassMap.put(structureName, startClass);
/*  22 */     startClassToNameMap.put(startClass, structureName);
/*     */   }
/*     */ 
/*     */   
/*     */   static void registerStructureComponent(Class<? extends StructureComponent> componentClass, String componentName) {
/*  27 */     componentNameToClassMap.put(componentName, componentClass);
/*  28 */     componentClassToNameMap.put(componentClass, componentName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getStructureStartName(StructureStart start) {
/*  33 */     return startClassToNameMap.get(start.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getStructureComponentName(StructureComponent component) {
/*  38 */     return componentClassToNameMap.get(component.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructureStart getStructureStart(NBTTagCompound tagCompound, World worldIn) {
/*  43 */     StructureStart structurestart = null;
/*     */ 
/*     */     
/*     */     try {
/*  47 */       Class<? extends StructureStart> oclass = startNameToClassMap.get(tagCompound.getString("id"));
/*     */       
/*  49 */       if (oclass != null)
/*     */       {
/*  51 */         structurestart = oclass.newInstance();
/*     */       }
/*     */     }
/*  54 */     catch (Exception exception) {
/*     */       
/*  56 */       logger.warn("Failed Start with id " + tagCompound.getString("id"));
/*  57 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  60 */     if (structurestart != null) {
/*     */       
/*  62 */       structurestart.readStructureComponentsFromNBT(worldIn, tagCompound);
/*     */     }
/*     */     else {
/*     */       
/*  66 */       logger.warn("Skipping Structure with id " + tagCompound.getString("id"));
/*     */     } 
/*     */     
/*  69 */     return structurestart;
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructureComponent getStructureComponent(NBTTagCompound tagCompound, World worldIn) {
/*  74 */     StructureComponent structurecomponent = null;
/*     */ 
/*     */     
/*     */     try {
/*  78 */       Class<? extends StructureComponent> oclass = componentNameToClassMap.get(tagCompound.getString("id"));
/*     */       
/*  80 */       if (oclass != null)
/*     */       {
/*  82 */         structurecomponent = oclass.newInstance();
/*     */       }
/*     */     }
/*  85 */     catch (Exception exception) {
/*     */       
/*  87 */       logger.warn("Failed Piece with id " + tagCompound.getString("id"));
/*  88 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  91 */     if (structurecomponent != null) {
/*     */       
/*  93 */       structurecomponent.readStructureBaseNBT(worldIn, tagCompound);
/*     */     }
/*     */     else {
/*     */       
/*  97 */       logger.warn("Skipping Piece with id " + tagCompound.getString("id"));
/*     */     } 
/*     */     
/* 100 */     return structurecomponent;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 105 */     registerStructure((Class)StructureMineshaftStart.class, "Mineshaft");
/* 106 */     registerStructure((Class)MapGenVillage.Start.class, "Village");
/* 107 */     registerStructure((Class)MapGenNetherBridge.Start.class, "Fortress");
/* 108 */     registerStructure((Class)MapGenStronghold.Start.class, "Stronghold");
/* 109 */     registerStructure((Class)MapGenScatteredFeature.Start.class, "Temple");
/* 110 */     registerStructure((Class)StructureOceanMonument.StartMonument.class, "Monument");
/* 111 */     StructureMineshaftPieces.registerStructurePieces();
/* 112 */     StructureVillagePieces.registerVillagePieces();
/* 113 */     StructureNetherBridgePieces.registerNetherFortressPieces();
/* 114 */     StructureStrongholdPieces.registerStrongholdPieces();
/* 115 */     ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
/* 116 */     StructureOceanMonumentPieces.registerOceanMonumentPieces();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\MapGenStructureIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */