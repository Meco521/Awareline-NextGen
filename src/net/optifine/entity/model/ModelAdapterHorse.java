/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelHorse;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderHorse;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class ModelAdapterHorse
/*     */   extends ModelAdapter
/*     */ {
/*  17 */   private static Map<String, Integer> mapPartFields = null;
/*     */ 
/*     */   
/*     */   public ModelAdapterHorse() {
/*  21 */     super(EntityHorse.class, "horse", 0.75F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelAdapterHorse(Class entityClass, String name, float shadowSize) {
/*  26 */     super(entityClass, name, shadowSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBase makeModel() {
/*  31 */     return (ModelBase)new ModelHorse();
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/*  36 */     if (!(model instanceof ModelHorse))
/*     */     {
/*  38 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  42 */     ModelHorse modelhorse = (ModelHorse)model;
/*  43 */     Map<String, Integer> map = getMapPartFields();
/*     */     
/*  45 */     if (map.containsKey(modelPart)) {
/*     */       
/*  47 */       int i = ((Integer)map.get(modelPart)).intValue();
/*  48 */       return (ModelRenderer)Reflector.getFieldValue(modelhorse, Reflector.ModelHorse_ModelRenderers, i);
/*     */     } 
/*     */ 
/*     */     
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getModelRendererNames() {
/*  59 */     return new String[] { "head", "upper_mouth", "lower_mouth", "horse_left_ear", "horse_right_ear", "mule_left_ear", "mule_right_ear", "neck", "horse_face_ropes", "mane", "body", "tail_base", "tail_middle", "tail_tip", "back_left_leg", "back_left_shin", "back_left_hoof", "back_right_leg", "back_right_shin", "back_right_hoof", "front_left_leg", "front_left_shin", "front_left_hoof", "front_right_leg", "front_right_shin", "front_right_hoof", "mule_left_chest", "mule_right_chest", "horse_saddle_bottom", "horse_saddle_front", "horse_saddle_back", "horse_left_saddle_rope", "horse_left_saddle_metal", "horse_right_saddle_rope", "horse_right_saddle_metal", "horse_left_face_metal", "horse_right_face_metal", "horse_left_rein", "horse_right_rein" };
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<String, Integer> getMapPartFields() {
/*  64 */     if (mapPartFields != null)
/*     */     {
/*  66 */       return mapPartFields;
/*     */     }
/*     */ 
/*     */     
/*  70 */     mapPartFields = new HashMap<>();
/*  71 */     mapPartFields.put("head", Integer.valueOf(0));
/*  72 */     mapPartFields.put("upper_mouth", Integer.valueOf(1));
/*  73 */     mapPartFields.put("lower_mouth", Integer.valueOf(2));
/*  74 */     mapPartFields.put("horse_left_ear", Integer.valueOf(3));
/*  75 */     mapPartFields.put("horse_right_ear", Integer.valueOf(4));
/*  76 */     mapPartFields.put("mule_left_ear", Integer.valueOf(5));
/*  77 */     mapPartFields.put("mule_right_ear", Integer.valueOf(6));
/*  78 */     mapPartFields.put("neck", Integer.valueOf(7));
/*  79 */     mapPartFields.put("horse_face_ropes", Integer.valueOf(8));
/*  80 */     mapPartFields.put("mane", Integer.valueOf(9));
/*  81 */     mapPartFields.put("body", Integer.valueOf(10));
/*  82 */     mapPartFields.put("tail_base", Integer.valueOf(11));
/*  83 */     mapPartFields.put("tail_middle", Integer.valueOf(12));
/*  84 */     mapPartFields.put("tail_tip", Integer.valueOf(13));
/*  85 */     mapPartFields.put("back_left_leg", Integer.valueOf(14));
/*  86 */     mapPartFields.put("back_left_shin", Integer.valueOf(15));
/*  87 */     mapPartFields.put("back_left_hoof", Integer.valueOf(16));
/*  88 */     mapPartFields.put("back_right_leg", Integer.valueOf(17));
/*  89 */     mapPartFields.put("back_right_shin", Integer.valueOf(18));
/*  90 */     mapPartFields.put("back_right_hoof", Integer.valueOf(19));
/*  91 */     mapPartFields.put("front_left_leg", Integer.valueOf(20));
/*  92 */     mapPartFields.put("front_left_shin", Integer.valueOf(21));
/*  93 */     mapPartFields.put("front_left_hoof", Integer.valueOf(22));
/*  94 */     mapPartFields.put("front_right_leg", Integer.valueOf(23));
/*  95 */     mapPartFields.put("front_right_shin", Integer.valueOf(24));
/*  96 */     mapPartFields.put("front_right_hoof", Integer.valueOf(25));
/*  97 */     mapPartFields.put("mule_left_chest", Integer.valueOf(26));
/*  98 */     mapPartFields.put("mule_right_chest", Integer.valueOf(27));
/*  99 */     mapPartFields.put("horse_saddle_bottom", Integer.valueOf(28));
/* 100 */     mapPartFields.put("horse_saddle_front", Integer.valueOf(29));
/* 101 */     mapPartFields.put("horse_saddle_back", Integer.valueOf(30));
/* 102 */     mapPartFields.put("horse_left_saddle_rope", Integer.valueOf(31));
/* 103 */     mapPartFields.put("horse_left_saddle_metal", Integer.valueOf(32));
/* 104 */     mapPartFields.put("horse_right_saddle_rope", Integer.valueOf(33));
/* 105 */     mapPartFields.put("horse_right_saddle_metal", Integer.valueOf(34));
/* 106 */     mapPartFields.put("horse_left_face_metal", Integer.valueOf(35));
/* 107 */     mapPartFields.put("horse_right_face_metal", Integer.valueOf(36));
/* 108 */     mapPartFields.put("horse_left_rein", Integer.valueOf(37));
/* 109 */     mapPartFields.put("horse_right_rein", Integer.valueOf(38));
/* 110 */     return mapPartFields;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 116 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 117 */     RenderHorse renderhorse = new RenderHorse(rendermanager, (ModelHorse)modelBase, shadowSize);
/* 118 */     return (IEntityRenderer)renderhorse;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */