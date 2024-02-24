/*     */ package awareline.main.event;
/*     */ 
/*     */ import awareline.main.mod.manager.Manager;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public class EventManager
/*     */   implements Manager
/*     */ {
/*  23 */   private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<>();
/*     */   
/*  25 */   private static final Map<Class<? extends Event>, List<MethodData>> ALL_TIME_MAP = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void runAllTime(Object... objects) {
/*  34 */     for (Object object : objects) {
/*  35 */       for (Method method : object.getClass().getDeclaredMethods()) {
/*  36 */         if ((method.getParameterTypes()).length == 1 && method.isAnnotationPresent((Class)EventCancel.class)) {
/*  37 */           final MethodData data = new MethodData(object, method, ((EventCancel)method.<EventCancel>getAnnotation(EventCancel.class)).priority());
/*  38 */           if (!data.getTarget().isAccessible()) {
/*  39 */             data.getTarget().setAccessible(true);
/*     */           }
/*  41 */           Class<? extends Event> indexClass = (Class)method.getParameterTypes()[0];
/*  42 */           if (ALL_TIME_MAP.containsKey(indexClass)) {
/*  43 */             if (!((List)ALL_TIME_MAP.get(indexClass)).contains(data)) {
/*  44 */               ((List<MethodData>)ALL_TIME_MAP.get(indexClass)).add(data);
/*     */             }
/*     */           } else {
/*  47 */             ALL_TIME_MAP.put(indexClass, new CopyOnWriteArrayList<MethodData>()
/*     */                 {
/*     */                   private static final long serialVersionUID = 666L;
/*     */                 });
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Object... objects) {
/*  60 */     for (Object object : objects) {
/*  61 */       for (Method method : object.getClass().getDeclaredMethods()) {
/*  62 */         if (!isMethodBad(method))
/*     */         {
/*     */           
/*  65 */           register_method(method, object);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregister(Object... objects) {
/*  76 */     for (Object object : objects) {
/*  77 */       for (List<MethodData> dataList : REGISTRY_MAP.values()) {
/*  78 */         dataList.removeIf(data -> data.getSource().equals(object));
/*     */       }
/*     */       
/*  81 */       cleanMap(true);
/*     */     } 
/*     */   }
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
/*     */ 
/*     */   
/*     */   private static void register_method(Method method, Object object) {
/*  97 */     Class<? extends Event> indexClass = (Class)method.getParameterTypes()[0];
/*  98 */     final MethodData data = new MethodData(object, method, ((EventHandler)method.<EventHandler>getAnnotation(EventHandler.class)).value());
/*  99 */     if (!data.getTarget().isAccessible()) {
/* 100 */       data.getTarget().setAccessible(true);
/*     */     }
/*     */     
/* 103 */     if (REGISTRY_MAP.containsKey(indexClass)) {
/* 104 */       if (!((List)REGISTRY_MAP.get(indexClass)).contains(data)) {
/* 105 */         ((List<MethodData>)REGISTRY_MAP.get(indexClass)).add(data);
/*     */       }
/*     */     } else {
/* 108 */       REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList<MethodData>()
/*     */           {
/*     */             private static final long serialVersionUID = 666L;
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeEntry(Class<? extends Event> indexClass) {
/* 124 */     Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
/*     */     
/* 126 */     while (mapIterator.hasNext()) {
/* 127 */       if (((Class)((Map.Entry)mapIterator.next()).getKey()).equals(indexClass)) {
/* 128 */         mapIterator.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cleanMap(boolean onlyEmptyEntries) {
/* 141 */     Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
/*     */     
/* 143 */     while (mapIterator.hasNext()) {
/* 144 */       if (!onlyEmptyEntries || ((List)((Map.Entry)mapIterator.next()).getValue()).isEmpty()) {
/* 145 */         mapIterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isMethodBad(Method method) {
/* 159 */     return ((method.getParameterTypes()).length != 1 || !method.isAnnotationPresent((Class)EventHandler.class));
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Event call(Event event) {
/* 177 */     List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());
/*     */     
/* 179 */     if (dataList != null) {
/* 180 */       for (MethodData data : dataList) {
/* 181 */         invoke(data, event);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 186 */     List<MethodData> dataAllList = ALL_TIME_MAP.get(event.getClass());
/*     */     
/* 188 */     if (dataAllList != null) {
/*     */       
/* 190 */       for (MethodData data : dataAllList) {
/* 191 */         invoke(data, event);
/*     */       }
/* 193 */       return event;
/*     */     } 
/* 195 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void invoke(MethodData data, Event argument) {
/*     */     try {
/* 208 */       data.getTarget().invoke(data.getSource(), new Object[] { argument });
/* 209 */     } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class MethodData
/*     */   {
/*     */     private final Object source;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Method target;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final byte priority;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MethodData(Object source, Method target, byte priority) {
/* 240 */       this.source = source;
/* 241 */       this.target = target;
/* 242 */       this.priority = priority;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getSource() {
/* 251 */       return this.source;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Method getTarget() {
/* 260 */       return this.target;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte getPriority() {
/* 269 */       return this.priority;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */