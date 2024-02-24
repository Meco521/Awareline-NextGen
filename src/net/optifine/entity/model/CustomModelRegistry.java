/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ 
/*     */ public class CustomModelRegistry
/*     */ {
/*  13 */   private static final Map<String, ModelAdapter> mapModelAdapters = makeMapModelAdapters();
/*     */ 
/*     */   
/*     */   private static Map<String, ModelAdapter> makeMapModelAdapters() {
/*  17 */     Map<String, ModelAdapter> map = new LinkedHashMap<>();
/*  18 */     addModelAdapter(map, new ModelAdapterArmorStand());
/*  19 */     addModelAdapter(map, new ModelAdapterBat());
/*  20 */     addModelAdapter(map, new ModelAdapterBlaze());
/*  21 */     addModelAdapter(map, new ModelAdapterBoat());
/*  22 */     addModelAdapter(map, new ModelAdapterCaveSpider());
/*  23 */     addModelAdapter(map, new ModelAdapterChicken());
/*  24 */     addModelAdapter(map, new ModelAdapterCow());
/*  25 */     addModelAdapter(map, new ModelAdapterCreeper());
/*  26 */     addModelAdapter(map, new ModelAdapterDragon());
/*  27 */     addModelAdapter(map, new ModelAdapterEnderCrystal());
/*  28 */     addModelAdapter(map, new ModelAdapterEnderman());
/*  29 */     addModelAdapter(map, new ModelAdapterEndermite());
/*  30 */     addModelAdapter(map, new ModelAdapterGhast());
/*  31 */     addModelAdapter(map, new ModelAdapterGuardian());
/*  32 */     addModelAdapter(map, new ModelAdapterHorse());
/*  33 */     addModelAdapter(map, new ModelAdapterIronGolem());
/*  34 */     addModelAdapter(map, new ModelAdapterLeadKnot());
/*  35 */     addModelAdapter(map, new ModelAdapterMagmaCube());
/*  36 */     addModelAdapter(map, new ModelAdapterMinecart());
/*  37 */     addModelAdapter(map, new ModelAdapterMinecartTnt());
/*  38 */     addModelAdapter(map, new ModelAdapterMinecartMobSpawner());
/*  39 */     addModelAdapter(map, new ModelAdapterMooshroom());
/*  40 */     addModelAdapter(map, new ModelAdapterOcelot());
/*  41 */     addModelAdapter(map, new ModelAdapterPig());
/*  42 */     addModelAdapter(map, new ModelAdapterPigZombie());
/*  43 */     addModelAdapter(map, new ModelAdapterRabbit());
/*  44 */     addModelAdapter(map, new ModelAdapterSheep());
/*  45 */     addModelAdapter(map, new ModelAdapterSilverfish());
/*  46 */     addModelAdapter(map, new ModelAdapterSkeleton());
/*  47 */     addModelAdapter(map, new ModelAdapterSlime());
/*  48 */     addModelAdapter(map, new ModelAdapterSnowman());
/*  49 */     addModelAdapter(map, new ModelAdapterSpider());
/*  50 */     addModelAdapter(map, new ModelAdapterSquid());
/*  51 */     addModelAdapter(map, new ModelAdapterVillager());
/*  52 */     addModelAdapter(map, new ModelAdapterWitch());
/*  53 */     addModelAdapter(map, new ModelAdapterWither());
/*  54 */     addModelAdapter(map, new ModelAdapterWitherSkull());
/*  55 */     addModelAdapter(map, new ModelAdapterWolf());
/*  56 */     addModelAdapter(map, new ModelAdapterZombie());
/*  57 */     addModelAdapter(map, new ModelAdapterSheepWool());
/*  58 */     addModelAdapter(map, new ModelAdapterBanner());
/*  59 */     addModelAdapter(map, new ModelAdapterBook());
/*  60 */     addModelAdapter(map, new ModelAdapterChest());
/*  61 */     addModelAdapter(map, new ModelAdapterChestLarge());
/*  62 */     addModelAdapter(map, new ModelAdapterEnderChest());
/*  63 */     addModelAdapter(map, new ModelAdapterHeadHumanoid());
/*  64 */     addModelAdapter(map, new ModelAdapterHeadSkeleton());
/*  65 */     addModelAdapter(map, new ModelAdapterSign());
/*  66 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter) {
/*  71 */     addModelAdapter(map, modelAdapter, modelAdapter.getName());
/*  72 */     String[] astring = modelAdapter.getAliases();
/*     */     
/*  74 */     if (astring != null)
/*     */     {
/*  76 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/*  78 */         String s = astring[i];
/*  79 */         addModelAdapter(map, modelAdapter, s);
/*     */       } 
/*     */     }
/*     */     
/*  83 */     ModelBase modelbase = modelAdapter.makeModel();
/*  84 */     String[] astring1 = modelAdapter.getModelRendererNames();
/*     */     
/*  86 */     for (int j = 0; j < astring1.length; j++) {
/*     */       
/*  88 */       String s1 = astring1[j];
/*  89 */       ModelRenderer modelrenderer = modelAdapter.getModelRenderer(modelbase, s1);
/*     */       
/*  91 */       if (modelrenderer == null)
/*     */       {
/*  93 */         Config.warn("Model renderer not found, model: " + modelAdapter.getName() + ", name: " + s1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter, String name) {
/* 100 */     if (map.containsKey(name))
/*     */     {
/* 102 */       Config.warn("Model adapter already registered for id: " + name + ", class: " + modelAdapter.getEntityClass().getName());
/*     */     }
/*     */     
/* 105 */     map.put(name, modelAdapter);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelAdapter getModelAdapter(String name) {
/* 110 */     return mapModelAdapters.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getModelNames() {
/* 115 */     Set<String> set = mapModelAdapters.keySet();
/* 116 */     String[] astring = set.<String>toArray(new String[0]);
/* 117 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\CustomModelRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */