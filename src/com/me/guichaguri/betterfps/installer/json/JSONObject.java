/*      */ package com.me.guichaguri.betterfps.installer.json;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JSONObject
/*      */ {
/*      */   private final Map<String, Object> map;
/*      */   
/*      */   private static final class Null
/*      */   {
/*      */     protected final Object clone() {
/*  108 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  120 */       return (object == null || object == this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  129 */       return "null";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  144 */   public static final Object NULL = new Null();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject() {
/*  150 */     this.map = new HashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(JSONObject jo, String[] names) {
/*  165 */     this();
/*  166 */     for (int i = 0; i < names.length; i++) {
/*      */       try {
/*  168 */         putOnce(names[i], jo.opt(names[i]));
/*  169 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(JSONTokener x) throws JSONException {
/*  182 */     this();
/*      */ 
/*      */ 
/*      */     
/*  186 */     if (x.nextClean() != '{') {
/*  187 */       throw x.syntaxError("A JSONObject text must begin with '{'");
/*      */     }
/*      */     while (true) {
/*  190 */       char c = x.nextClean();
/*  191 */       switch (c) {
/*      */         case '\000':
/*  193 */           throw x.syntaxError("A JSONObject text must end with '}'");
/*      */         case '}':
/*      */           return;
/*      */       } 
/*  197 */       x.back();
/*  198 */       String key = x.nextValue().toString();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  203 */       c = x.nextClean();
/*  204 */       if (c != ':') {
/*  205 */         throw x.syntaxError("Expected a ':' after a key");
/*      */       }
/*  207 */       putOnce(key, x.nextValue());
/*      */ 
/*      */ 
/*      */       
/*  211 */       switch (x.nextClean()) {
/*      */         case ',':
/*      */         case ';':
/*  214 */           if (x.nextClean() == '}') {
/*      */             return;
/*      */           }
/*  217 */           x.back(); continue;
/*      */         case '}':
/*      */           return;
/*      */       }  break;
/*      */     } 
/*  222 */     throw x.syntaxError("Expected a ',' or '}'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(Map<String, Object> map) {
/*  235 */     this.map = new HashMap<>();
/*  236 */     if (map != null) {
/*  237 */       Iterator<Map.Entry<String, Object>> i = map.entrySet().iterator();
/*  238 */       while (i.hasNext()) {
/*  239 */         Map.Entry<String, Object> entry = i.next();
/*  240 */         Object value = entry.getValue();
/*  241 */         if (value != null) {
/*  242 */           this.map.put(entry.getKey(), wrap(value));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(Object bean) {
/*  269 */     this();
/*  270 */     populateMap(bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(Object object, String[] names) {
/*  286 */     this();
/*  287 */     Class<?> c = object.getClass();
/*  288 */     for (int i = 0; i < names.length; i++) {
/*  289 */       String name = names[i];
/*      */       try {
/*  291 */         putOpt(name, c.getField(name).get(object));
/*  292 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(String source) throws JSONException {
/*  308 */     this(new JSONTokener(source));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(String baseName, Locale locale) throws JSONException {
/*  319 */     this();
/*  320 */     ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, 
/*  321 */         Thread.currentThread().getContextClassLoader());
/*      */ 
/*      */ 
/*      */     
/*  325 */     Enumeration<String> keys = bundle.getKeys();
/*  326 */     while (keys.hasMoreElements()) {
/*  327 */       Object key = keys.nextElement();
/*  328 */       if (key != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  334 */         String[] path = ((String)key).split("\\.");
/*  335 */         int last = path.length - 1;
/*  336 */         JSONObject target = this;
/*  337 */         for (int i = 0; i < last; i++) {
/*  338 */           String segment = path[i];
/*  339 */           JSONObject nextTarget = target.optJSONObject(segment);
/*  340 */           if (nextTarget == null) {
/*  341 */             nextTarget = new JSONObject();
/*  342 */             target.put(segment, nextTarget);
/*      */           } 
/*  344 */           target = nextTarget;
/*      */         } 
/*  346 */         target.put(path[last], bundle.getString((String)key));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject accumulate(String key, Object value) throws JSONException {
/*  368 */     testValidity(value);
/*  369 */     Object object = opt(key);
/*  370 */     if (object == null) {
/*  371 */       put(key, (value instanceof JSONArray) ? (new JSONArray())
/*  372 */           .put(value) : value);
/*      */     }
/*  374 */     else if (object instanceof JSONArray) {
/*  375 */       ((JSONArray)object).put(value);
/*      */     } else {
/*  377 */       put(key, (new JSONArray()).put(object).put(value));
/*      */     } 
/*  379 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject append(String key, Object value) throws JSONException {
/*  395 */     testValidity(value);
/*  396 */     Object object = opt(key);
/*  397 */     if (object == null) {
/*  398 */       put(key, (new JSONArray()).put(value));
/*  399 */     } else if (object instanceof JSONArray) {
/*  400 */       put(key, ((JSONArray)object).put(value));
/*      */     } else {
/*  402 */       throw new JSONException("JSONObject[" + key + "] is not a JSONArray.");
/*      */     } 
/*      */     
/*  405 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String doubleToString(double d) {
/*  416 */     if (Double.isInfinite(d) || Double.isNaN(d)) {
/*  417 */       return "null";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  422 */     String string = Double.toString(d);
/*  423 */     if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string
/*  424 */       .indexOf('E') < 0) {
/*  425 */       while (!string.isEmpty() && string.charAt(string.length() - 1) == '0') {
/*  426 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*  428 */       if (!string.isEmpty() && string.charAt(string.length() - 1) == '.') {
/*  429 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*      */     } 
/*  432 */     return string;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(String key) throws JSONException {
/*  443 */     if (key == null) {
/*  444 */       throw new JSONException("Null key.");
/*      */     }
/*  446 */     Object object = opt(key);
/*  447 */     if (object == null) {
/*  448 */       throw new JSONException("JSONObject[" + quote(key) + "] not found.");
/*      */     }
/*  450 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(String key) throws JSONException {
/*  462 */     Object object = get(key);
/*  463 */     if (object.equals(Boolean.FALSE) || (object instanceof String && ((String)object)
/*      */       
/*  465 */       .equalsIgnoreCase("false")))
/*  466 */       return false; 
/*  467 */     if (object.equals(Boolean.TRUE) || (object instanceof String && ((String)object)
/*      */       
/*  469 */       .equalsIgnoreCase("true"))) {
/*  470 */       return true;
/*      */     }
/*  472 */     throw new JSONException("JSONObject[" + quote(key) + "] is not a Boolean.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(String key) throws JSONException {
/*  485 */     Object object = get(key);
/*      */     try {
/*  487 */       return (object instanceof Number) ? ((Number)object).doubleValue() : 
/*  488 */         Double.parseDouble((String)object);
/*  489 */     } catch (Exception e) {
/*  490 */       throw new JSONException("JSONObject[" + quote(key) + "] is not a number.");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(String key) throws JSONException {
/*  504 */     Object object = get(key);
/*      */     try {
/*  506 */       return (object instanceof Number) ? ((Number)object).intValue() : 
/*  507 */         Integer.parseInt((String)object);
/*  508 */     } catch (Exception e) {
/*  509 */       throw new JSONException("JSONObject[" + quote(key) + "] is not an int.");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray getJSONArray(String key) throws JSONException {
/*  522 */     Object object = get(key);
/*  523 */     if (object instanceof JSONArray) {
/*  524 */       return (JSONArray)object;
/*      */     }
/*  526 */     throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONArray.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject getJSONObject(String key) throws JSONException {
/*  538 */     Object object = get(key);
/*  539 */     if (object instanceof JSONObject) {
/*  540 */       return (JSONObject)object;
/*      */     }
/*  542 */     throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONObject.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(String key) throws JSONException {
/*  555 */     Object object = get(key);
/*      */     try {
/*  557 */       return (object instanceof Number) ? ((Number)object).longValue() : 
/*  558 */         Long.parseLong((String)object);
/*  559 */     } catch (Exception e) {
/*  560 */       throw new JSONException("JSONObject[" + quote(key) + "] is not a long.");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getNames(JSONObject jo) {
/*  571 */     int length = jo.length();
/*  572 */     if (length == 0) {
/*  573 */       return null;
/*      */     }
/*  575 */     Iterator<String> iterator = jo.keys();
/*  576 */     String[] names = new String[length];
/*  577 */     int i = 0;
/*  578 */     while (iterator.hasNext()) {
/*  579 */       names[i] = iterator.next();
/*  580 */       i++;
/*      */     } 
/*  582 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getNames(Object object) {
/*  591 */     if (object == null) {
/*  592 */       return null;
/*      */     }
/*  594 */     Class<?> klass = object.getClass();
/*  595 */     Field[] fields = klass.getFields();
/*  596 */     int length = fields.length;
/*  597 */     if (length == 0) {
/*  598 */       return null;
/*      */     }
/*  600 */     String[] names = new String[length];
/*  601 */     for (int i = 0; i < length; i++) {
/*  602 */       names[i] = fields[i].getName();
/*      */     }
/*  604 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(String key) throws JSONException {
/*  615 */     Object object = get(key);
/*  616 */     if (object instanceof String) {
/*  617 */       return (String)object;
/*      */     }
/*  619 */     throw new JSONException("JSONObject[" + quote(key) + "] not a string.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean has(String key) {
/*  629 */     return this.map.containsKey(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject increment(String key) throws JSONException {
/*  643 */     Object value = opt(key);
/*  644 */     if (value == null) {
/*  645 */       put(key, 1);
/*  646 */     } else if (value instanceof Integer) {
/*  647 */       put(key, ((Integer)value).intValue() + 1);
/*  648 */     } else if (value instanceof Long) {
/*  649 */       put(key, ((Long)value).longValue() + 1L);
/*  650 */     } else if (value instanceof Double) {
/*  651 */       put(key, ((Double)value).doubleValue() + 1.0D);
/*  652 */     } else if (value instanceof Float) {
/*  653 */       put(key, (((Float)value).floatValue() + 1.0F));
/*      */     } else {
/*  655 */       throw new JSONException("Unable to increment [" + quote(key) + "].");
/*      */     } 
/*  657 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNull(String key) {
/*  669 */     return NULL.equals(opt(key));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<String> keys() {
/*  678 */     return keySet().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> keySet() {
/*  687 */     return this.map.keySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  696 */     return this.map.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray names() {
/*  707 */     JSONArray ja = new JSONArray();
/*  708 */     Iterator<String> keys = keys();
/*  709 */     while (keys.hasNext()) {
/*  710 */       ja.put(keys.next());
/*      */     }
/*  712 */     return (ja.length() == 0) ? null : ja;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String numberToString(Number number) throws JSONException {
/*  723 */     if (number == null) {
/*  724 */       throw new JSONException("Null pointer");
/*      */     }
/*  726 */     testValidity(number);
/*      */ 
/*      */ 
/*      */     
/*  730 */     String string = number.toString();
/*  731 */     if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string
/*  732 */       .indexOf('E') < 0) {
/*  733 */       while (!string.isEmpty() && string.charAt(string.length() - 1) == '0') {
/*  734 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*  736 */       if (!string.isEmpty() && string.charAt(string.length() - 1) == '.') {
/*  737 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*      */     } 
/*  740 */     return string;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object opt(String key) {
/*  750 */     return (key == null) ? null : this.map.get(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean optBoolean(String key) {
/*  761 */     return optBoolean(key, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean optBoolean(String key, boolean defaultValue) {
/*      */     try {
/*  775 */       return getBoolean(key);
/*  776 */     } catch (Exception e) {
/*  777 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double optDouble(String key) {
/*  790 */     return optDouble(key, Double.NaN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double optDouble(String key, double defaultValue) {
/*      */     try {
/*  804 */       return getDouble(key);
/*  805 */     } catch (Exception e) {
/*  806 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int optInt(String key) {
/*  819 */     return optInt(key, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int optInt(String key, int defaultValue) {
/*      */     try {
/*  833 */       return getInt(key);
/*  834 */     } catch (Exception e) {
/*  835 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray optJSONArray(String key) {
/*  847 */     Object o = opt(key);
/*  848 */     return (o instanceof JSONArray) ? (JSONArray)o : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject optJSONObject(String key) {
/*  859 */     Object object = opt(key);
/*  860 */     return (object instanceof JSONObject) ? (JSONObject)object : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long optLong(String key) {
/*  872 */     return optLong(key, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long optLong(String key, long defaultValue) {
/*      */     try {
/*  886 */       return getLong(key);
/*  887 */     } catch (Exception e) {
/*  888 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String optString(String key) {
/*  901 */     return optString(key, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String optString(String key, String defaultValue) {
/*  913 */     Object object = opt(key);
/*  914 */     return NULL.equals(object) ? defaultValue : object.toString();
/*      */   }
/*      */   
/*      */   private void populateMap(Object bean) {
/*  918 */     Class<?> klass = bean.getClass();
/*      */ 
/*      */ 
/*      */     
/*  922 */     boolean includeSuperClass = (klass.getClassLoader() != null);
/*      */ 
/*      */     
/*  925 */     Method[] methods = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();
/*  926 */     for (int i = 0; i < methods.length; i++) {
/*      */       try {
/*  928 */         Method method = methods[i];
/*  929 */         if (Modifier.isPublic(method.getModifiers())) {
/*  930 */           String name = method.getName();
/*  931 */           String key = "";
/*  932 */           if (name.startsWith("get")) {
/*  933 */             if ("getClass".equals(name) || "getDeclaringClass"
/*  934 */               .equals(name)) {
/*  935 */               key = "";
/*      */             } else {
/*  937 */               key = name.substring(3);
/*      */             } 
/*  939 */           } else if (name.startsWith("is")) {
/*  940 */             key = name.substring(2);
/*      */           } 
/*  942 */           if (key.length() > 0 && 
/*  943 */             Character.isUpperCase(key.charAt(0)) && (method
/*  944 */             .getParameterTypes()).length == 0) {
/*  945 */             if (key.length() == 1) {
/*  946 */               key = key.toLowerCase();
/*  947 */             } else if (!Character.isUpperCase(key.charAt(1))) {
/*      */               
/*  949 */               key = key.substring(0, 1).toLowerCase() + key.substring(1);
/*      */             } 
/*      */             
/*  952 */             Object result = method.invoke(bean, (Object[])null);
/*  953 */             if (result != null) {
/*  954 */               this.map.put(key, wrap(result));
/*      */             }
/*      */           } 
/*      */         } 
/*  958 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, boolean value) throws JSONException {
/*  972 */     put(key, value ? Boolean.TRUE : Boolean.FALSE);
/*  973 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, Collection<Object> value) throws JSONException {
/*  986 */     put(key, new JSONArray(value));
/*  987 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, double value) throws JSONException {
/*  999 */     put(key, new Double(value));
/* 1000 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, int value) throws JSONException {
/* 1012 */     put(key, new Integer(value));
/* 1013 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, long value) throws JSONException {
/* 1025 */     put(key, new Long(value));
/* 1026 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, Map<String, Object> value) throws JSONException {
/* 1039 */     put(key, new JSONObject(value));
/* 1040 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, Object value) throws JSONException {
/* 1055 */     if (key == null) {
/* 1056 */       throw new NullPointerException("Null key.");
/*      */     }
/* 1058 */     if (value != null) {
/* 1059 */       testValidity(value);
/* 1060 */       this.map.put(key, value);
/*      */     } else {
/* 1062 */       remove(key);
/*      */     } 
/* 1064 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject putOnce(String key, Object value) throws JSONException {
/* 1078 */     if (key != null && value != null) {
/* 1079 */       if (opt(key) != null) {
/* 1080 */         throw new JSONException("Duplicate key \"" + key + "\"");
/*      */       }
/* 1082 */       put(key, value);
/*      */     } 
/* 1084 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject putOpt(String key, Object value) throws JSONException {
/* 1099 */     if (key != null && value != null) {
/* 1100 */       put(key, value);
/*      */     }
/* 1102 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String quote(String string) {
/* 1115 */     StringWriter sw = new StringWriter();
/* 1116 */     synchronized (sw.getBuffer()) {
/*      */       
/* 1118 */       return quote(string, sw).toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Writer quote(String string, Writer w) throws IOException {
/* 1127 */     if (string == null || string.length() == 0) {
/* 1128 */       w.write("\"\"");
/* 1129 */       return w;
/*      */     } 
/*      */ 
/*      */     
/* 1133 */     char c = Character.MIN_VALUE;
/*      */ 
/*      */     
/* 1136 */     int len = string.length();
/*      */     
/* 1138 */     w.write(34);
/* 1139 */     for (int i = 0; i < len; i++) {
/* 1140 */       char b = c;
/* 1141 */       c = string.charAt(i);
/* 1142 */       switch (c) {
/*      */         case '"':
/*      */         case '\\':
/* 1145 */           w.write(92);
/* 1146 */           w.write(c);
/*      */           break;
/*      */         case '/':
/* 1149 */           if (b == '<') {
/* 1150 */             w.write(92);
/*      */           }
/* 1152 */           w.write(c);
/*      */           break;
/*      */         case '\b':
/* 1155 */           w.write("\\b");
/*      */           break;
/*      */         case '\t':
/* 1158 */           w.write("\\t");
/*      */           break;
/*      */         case '\n':
/* 1161 */           w.write("\\n");
/*      */           break;
/*      */         case '\f':
/* 1164 */           w.write("\\f");
/*      */           break;
/*      */         case '\r':
/* 1167 */           w.write("\\r");
/*      */           break;
/*      */         default:
/* 1170 */           if (c < ' ' || (c >= '' && c < ' ') || (c >= ' ' && c < '℀')) {
/*      */             
/* 1172 */             w.write("\\u");
/* 1173 */             String hhhh = Integer.toHexString(c);
/* 1174 */             w.write("0000", 0, 4 - hhhh.length());
/* 1175 */             w.write(hhhh); break;
/*      */           } 
/* 1177 */           w.write(c);
/*      */           break;
/*      */       } 
/*      */     } 
/* 1181 */     w.write(34);
/* 1182 */     return w;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object remove(String key) {
/* 1193 */     return this.map.remove(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean similar(Object other) {
/*      */     try {
/* 1206 */       if (!(other instanceof JSONObject)) {
/* 1207 */         return false;
/*      */       }
/* 1209 */       Set<String> set = keySet();
/* 1210 */       if (!set.equals(((JSONObject)other).keySet())) {
/* 1211 */         return false;
/*      */       }
/* 1213 */       Iterator<String> iterator = set.iterator();
/* 1214 */       while (iterator.hasNext()) {
/* 1215 */         String name = iterator.next();
/* 1216 */         Object valueThis = get(name);
/* 1217 */         Object valueOther = ((JSONObject)other).get(name);
/* 1218 */         if (valueThis instanceof JSONObject) {
/* 1219 */           if (!((JSONObject)valueThis).similar(valueOther))
/* 1220 */             return false;  continue;
/*      */         } 
/* 1222 */         if (valueThis instanceof JSONArray) {
/* 1223 */           if (!((JSONArray)valueThis).similar(valueOther))
/* 1224 */             return false;  continue;
/*      */         } 
/* 1226 */         if (!valueThis.equals(valueOther)) {
/* 1227 */           return false;
/*      */         }
/*      */       } 
/* 1230 */       return true;
/* 1231 */     } catch (Throwable exception) {
/* 1232 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object stringToValue(String string) {
/* 1245 */     if (string.isEmpty()) {
/* 1246 */       return string;
/*      */     }
/* 1248 */     if (string.equalsIgnoreCase("true")) {
/* 1249 */       return Boolean.TRUE;
/*      */     }
/* 1251 */     if (string.equalsIgnoreCase("false")) {
/* 1252 */       return Boolean.FALSE;
/*      */     }
/* 1254 */     if (string.equalsIgnoreCase("null")) {
/* 1255 */       return NULL;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1263 */     char b = string.charAt(0);
/* 1264 */     if ((b >= '0' && b <= '9') || b == '-') {
/*      */       try {
/* 1266 */         if (string.indexOf('.') > -1 || string.indexOf('e') > -1 || string
/* 1267 */           .indexOf('E') > -1) {
/* 1268 */           double d = Double.parseDouble(string);
/* 1269 */           if (!Double.isInfinite(d) && !Double.isNaN(d)) {
/* 1270 */             return Double.valueOf(d);
/*      */           }
/*      */         } else {
/* 1273 */           Long myLong = new Long(string);
/* 1274 */           if (string.equals(myLong.toString())) {
/* 1275 */             if (myLong.longValue() == myLong.intValue()) {
/* 1276 */               return Integer.valueOf(myLong.intValue());
/*      */             }
/* 1278 */             return myLong;
/*      */           }
/*      */         
/*      */         } 
/* 1282 */       } catch (Exception exception) {}
/*      */     }
/*      */     
/* 1285 */     return string;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void testValidity(Object o) throws JSONException {
/* 1295 */     if (o != null) {
/* 1296 */       if (o instanceof Double) {
/* 1297 */         if (((Double)o).isInfinite() || ((Double)o).isNaN()) {
/* 1298 */           throw new JSONException("JSON does not allow non-finite numbers.");
/*      */         }
/*      */       }
/* 1301 */       else if (o instanceof Float && ((
/* 1302 */         (Float)o).isInfinite() || ((Float)o).isNaN())) {
/* 1303 */         throw new JSONException("JSON does not allow non-finite numbers.");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray toJSONArray(JSONArray names) throws JSONException {
/* 1320 */     if (names == null || names.length() == 0) {
/* 1321 */       return null;
/*      */     }
/* 1323 */     JSONArray ja = new JSONArray();
/* 1324 */     for (int i = 0; i < names.length(); i++) {
/* 1325 */       ja.put(opt(names.getString(i)));
/*      */     }
/* 1327 */     return ja;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     try {
/* 1344 */       return toString(0);
/* 1345 */     } catch (Exception e) {
/* 1346 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(int indentFactor) throws JSONException {
/* 1363 */     StringWriter w = new StringWriter();
/* 1364 */     synchronized (w.getBuffer()) {
/* 1365 */       return write(w, indentFactor, 0).toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueToString(Object value) throws JSONException {
/* 1392 */     if (value == null || value.equals(null)) {
/* 1393 */       return "null";
/*      */     }
/* 1395 */     if (value instanceof JSONString) {
/*      */       Object object;
/*      */       try {
/* 1398 */         object = ((JSONString)value).toJSONString();
/* 1399 */       } catch (Exception e) {
/* 1400 */         throw new JSONException(e);
/*      */       } 
/* 1402 */       if (object instanceof String) {
/* 1403 */         return (String)object;
/*      */       }
/* 1405 */       throw new JSONException("Bad value from toJSONString: " + object);
/*      */     } 
/* 1407 */     if (value instanceof Number) {
/* 1408 */       return numberToString((Number)value);
/*      */     }
/* 1410 */     if (value instanceof Boolean || value instanceof JSONObject || value instanceof JSONArray)
/*      */     {
/* 1412 */       return value.toString();
/*      */     }
/* 1414 */     if (value instanceof Map) {
/*      */       
/* 1416 */       Map<String, Object> map = (Map<String, Object>)value;
/* 1417 */       return (new JSONObject(map)).toString();
/*      */     } 
/* 1419 */     if (value instanceof Collection) {
/*      */       
/* 1421 */       Collection<Object> coll = (Collection<Object>)value;
/* 1422 */       return (new JSONArray(coll)).toString();
/*      */     } 
/* 1424 */     if (value.getClass().isArray()) {
/* 1425 */       return (new JSONArray(value)).toString();
/*      */     }
/* 1427 */     return quote(value.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object wrap(Object object) {
/*      */     try {
/* 1443 */       if (object == null) {
/* 1444 */         return NULL;
/*      */       }
/* 1446 */       if (object instanceof JSONObject || object instanceof JSONArray || NULL
/* 1447 */         .equals(object) || object instanceof JSONString || object instanceof Byte || object instanceof Character || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof Boolean || object instanceof Float || object instanceof Double || object instanceof String)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1453 */         return object;
/*      */       }
/*      */       
/* 1456 */       if (object instanceof Collection) {
/*      */         
/* 1458 */         Collection<Object> coll = (Collection<Object>)object;
/* 1459 */         return new JSONArray(coll);
/*      */       } 
/* 1461 */       if (object.getClass().isArray()) {
/* 1462 */         return new JSONArray(object);
/*      */       }
/* 1464 */       if (object instanceof Map) {
/*      */         
/* 1466 */         Map<String, Object> map = (Map<String, Object>)object;
/* 1467 */         return new JSONObject(map);
/*      */       } 
/* 1469 */       Package objectPackage = object.getClass().getPackage();
/*      */       
/* 1471 */       String objectPackageName = (objectPackage != null) ? objectPackage.getName() : "";
/* 1472 */       if (objectPackageName.startsWith("java.") || objectPackageName
/* 1473 */         .startsWith("javax.") || object
/* 1474 */         .getClass().getClassLoader() == null) {
/* 1475 */         return object.toString();
/*      */       }
/* 1477 */       return new JSONObject(object);
/* 1478 */     } catch (Exception exception) {
/* 1479 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer write(Writer writer) throws JSONException {
/* 1493 */     return write(writer, 0, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   static final Writer writeValue(Writer writer, Object value, int indentFactor, int indent) throws JSONException, IOException {
/* 1498 */     if (value == null || value.equals(null)) {
/* 1499 */       writer.write("null");
/* 1500 */     } else if (value instanceof JSONObject) {
/* 1501 */       ((JSONObject)value).write(writer, indentFactor, indent);
/* 1502 */     } else if (value instanceof JSONArray) {
/* 1503 */       ((JSONArray)value).write(writer, indentFactor, indent);
/* 1504 */     } else if (value instanceof Map) {
/*      */       
/* 1506 */       Map<String, Object> map = (Map<String, Object>)value;
/* 1507 */       (new JSONObject(map)).write(writer, indentFactor, indent);
/* 1508 */     } else if (value instanceof Collection) {
/*      */       
/* 1510 */       Collection<Object> coll = (Collection<Object>)value;
/* 1511 */       (new JSONArray(coll)).write(writer, indentFactor, indent);
/*      */     }
/* 1513 */     else if (value.getClass().isArray()) {
/* 1514 */       (new JSONArray(value)).write(writer, indentFactor, indent);
/* 1515 */     } else if (value instanceof Number) {
/* 1516 */       writer.write(numberToString((Number)value));
/* 1517 */     } else if (value instanceof Boolean) {
/* 1518 */       writer.write(value.toString());
/* 1519 */     } else if (value instanceof JSONString) {
/*      */       Object o;
/*      */       try {
/* 1522 */         o = ((JSONString)value).toJSONString();
/* 1523 */       } catch (Exception e) {
/* 1524 */         throw new JSONException(e);
/*      */       } 
/* 1526 */       writer.write((o != null) ? o.toString() : quote(value.toString()));
/*      */     } else {
/* 1528 */       quote(value.toString(), writer);
/*      */     } 
/* 1530 */     return writer;
/*      */   }
/*      */   
/*      */   static final void indent(Writer writer, int indent) throws IOException {
/* 1534 */     for (int i = 0; i < indent; i++) {
/* 1535 */       writer.write(32);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
/*      */     try {
/* 1551 */       boolean commanate = false;
/* 1552 */       int length = length();
/* 1553 */       Iterator<String> keys = keys();
/* 1554 */       writer.write(123);
/*      */       
/* 1556 */       if (length == 1) {
/* 1557 */         Object key = keys.next();
/* 1558 */         writer.write(quote(key.toString()));
/* 1559 */         writer.write(58);
/* 1560 */         if (indentFactor > 0) {
/* 1561 */           writer.write(32);
/*      */         }
/* 1563 */         writeValue(writer, this.map.get(key), indentFactor, indent);
/* 1564 */       } else if (length != 0) {
/* 1565 */         int newindent = indent + indentFactor;
/* 1566 */         while (keys.hasNext()) {
/* 1567 */           Object key = keys.next();
/* 1568 */           if (commanate) {
/* 1569 */             writer.write(44);
/*      */           }
/* 1571 */           if (indentFactor > 0) {
/* 1572 */             writer.write(10);
/*      */           }
/* 1574 */           indent(writer, newindent);
/* 1575 */           writer.write(quote(key.toString()));
/* 1576 */           writer.write(58);
/* 1577 */           if (indentFactor > 0) {
/* 1578 */             writer.write(32);
/*      */           }
/* 1580 */           writeValue(writer, this.map.get(key), indentFactor, newindent);
/* 1581 */           commanate = true;
/*      */         } 
/* 1583 */         if (indentFactor > 0) {
/* 1584 */           writer.write(10);
/*      */         }
/* 1586 */         indent(writer, indent);
/*      */       } 
/* 1588 */       writer.write(125);
/* 1589 */       return writer;
/* 1590 */     } catch (IOException exception) {
/* 1591 */       throw new JSONException(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\installer\json\JSONObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */