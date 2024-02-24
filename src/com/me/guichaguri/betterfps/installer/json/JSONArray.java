/*     */ package com.me.guichaguri.betterfps.installer.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class JSONArray
/*     */   implements Iterable<Object>
/*     */ {
/*     */   private final ArrayList<Object> myArrayList;
/*     */   
/*     */   public JSONArray() {
/*  91 */     this.myArrayList = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray(JSONTokener x) throws JSONException {
/* 101 */     this();
/* 102 */     if (x.nextClean() != '[') {
/* 103 */       throw x.syntaxError("A JSONArray text must start with '['");
/*     */     }
/* 105 */     if (x.nextClean() != ']') {
/* 106 */       x.back();
/*     */       while (true) {
/* 108 */         if (x.nextClean() == ',') {
/* 109 */           x.back();
/* 110 */           this.myArrayList.add(JSONObject.NULL);
/*     */         } else {
/* 112 */           x.back();
/* 113 */           this.myArrayList.add(x.nextValue());
/*     */         } 
/* 115 */         switch (x.nextClean()) {
/*     */           case ',':
/* 117 */             if (x.nextClean() == ']') {
/*     */               return;
/*     */             }
/* 120 */             x.back(); continue;
/*     */           case ']':
/*     */             return;
/*     */         }  break;
/*     */       } 
/* 125 */       throw x.syntaxError("Expected a ',' or ']'");
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
/*     */   public JSONArray(String source) throws JSONException {
/* 140 */     this(new JSONTokener(source));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray(Collection<Object> collection) {
/* 149 */     this.myArrayList = new ArrayList();
/* 150 */     if (collection != null) {
/* 151 */       Iterator<Object> iter = collection.iterator();
/* 152 */       while (iter.hasNext()) {
/* 153 */         this.myArrayList.add(JSONObject.wrap(iter.next()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray(Object array) throws JSONException {
/* 164 */     this();
/* 165 */     if (array.getClass().isArray()) {
/* 166 */       int length = Array.getLength(array);
/* 167 */       for (int i = 0; i < length; i++) {
/* 168 */         put(JSONObject.wrap(Array.get(array, i)));
/*     */       }
/*     */     } else {
/* 171 */       throw new JSONException("JSONArray initial value should be a string or collection or array.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Object> iterator() {
/* 178 */     return this.myArrayList.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(int index) throws JSONException {
/* 189 */     Object object = opt(index);
/* 190 */     if (object == null) {
/* 191 */       throw new JSONException("JSONArray[" + index + "] not found.");
/*     */     }
/* 193 */     return object;
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
/*     */   public boolean getBoolean(int index) throws JSONException {
/* 206 */     Object object = get(index);
/* 207 */     if (object.equals(Boolean.FALSE) || (object instanceof String && ((String)object)
/*     */       
/* 209 */       .equalsIgnoreCase("false")))
/* 210 */       return false; 
/* 211 */     if (object.equals(Boolean.TRUE) || (object instanceof String && ((String)object)
/*     */       
/* 213 */       .equalsIgnoreCase("true"))) {
/* 214 */       return true;
/*     */     }
/* 216 */     throw new JSONException("JSONArray[" + index + "] is not a boolean.");
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
/*     */   public double getDouble(int index) throws JSONException {
/* 228 */     Object object = get(index);
/*     */     try {
/* 230 */       return (object instanceof Number) ? ((Number)object).doubleValue() : 
/* 231 */         Double.parseDouble((String)object);
/* 232 */     } catch (Exception e) {
/* 233 */       throw new JSONException("JSONArray[" + index + "] is not a number.");
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
/*     */   public int getInt(int index) throws JSONException {
/* 245 */     Object object = get(index);
/*     */     try {
/* 247 */       return (object instanceof Number) ? ((Number)object).intValue() : 
/* 248 */         Integer.parseInt((String)object);
/* 249 */     } catch (Exception e) {
/* 250 */       throw new JSONException("JSONArray[" + index + "] is not a number.");
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
/*     */   public JSONArray getJSONArray(int index) throws JSONException {
/* 263 */     Object object = get(index);
/* 264 */     if (object instanceof JSONArray) {
/* 265 */       return (JSONArray)object;
/*     */     }
/* 267 */     throw new JSONException("JSONArray[" + index + "] is not a JSONArray.");
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
/*     */   public JSONObject getJSONObject(int index) throws JSONException {
/* 279 */     Object object = get(index);
/* 280 */     if (object instanceof JSONObject) {
/* 281 */       return (JSONObject)object;
/*     */     }
/* 283 */     throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
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
/*     */   public long getLong(int index) throws JSONException {
/* 295 */     Object object = get(index);
/*     */     try {
/* 297 */       return (object instanceof Number) ? ((Number)object).longValue() : 
/* 298 */         Long.parseLong((String)object);
/* 299 */     } catch (Exception e) {
/* 300 */       throw new JSONException("JSONArray[" + index + "] is not a number.");
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
/*     */   public String getString(int index) throws JSONException {
/* 312 */     Object object = get(index);
/* 313 */     if (object instanceof String) {
/* 314 */       return (String)object;
/*     */     }
/* 316 */     throw new JSONException("JSONArray[" + index + "] not a string.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNull(int index) {
/* 326 */     return JSONObject.NULL.equals(opt(index));
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
/*     */   public String join(String separator) throws JSONException {
/* 339 */     int len = length();
/* 340 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 342 */     for (int i = 0; i < len; i++) {
/* 343 */       if (i > 0) {
/* 344 */         sb.append(separator);
/*     */       }
/* 346 */       sb.append(JSONObject.valueToString(this.myArrayList.get(i)));
/*     */     } 
/* 348 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 357 */     return this.myArrayList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object opt(int index) {
/* 367 */     return (index < 0 || index >= length()) ? null : this.myArrayList
/* 368 */       .get(index);
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
/*     */   public boolean optBoolean(int index) {
/* 380 */     return optBoolean(index, false);
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
/*     */   public boolean optBoolean(int index, boolean defaultValue) {
/*     */     try {
/* 394 */       return getBoolean(index);
/* 395 */     } catch (Exception e) {
/* 396 */       return defaultValue;
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
/*     */   public double optDouble(int index) {
/* 409 */     return optDouble(index, Double.NaN);
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
/*     */   public double optDouble(int index, double defaultValue) {
/*     */     try {
/* 423 */       return getDouble(index);
/* 424 */     } catch (Exception e) {
/* 425 */       return defaultValue;
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
/*     */   public int optInt(int index) {
/* 438 */     return optInt(index, 0);
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
/*     */   public int optInt(int index, int defaultValue) {
/*     */     try {
/* 452 */       return getInt(index);
/* 453 */     } catch (Exception e) {
/* 454 */       return defaultValue;
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
/*     */   public JSONArray optJSONArray(int index) {
/* 466 */     Object o = opt(index);
/* 467 */     return (o instanceof JSONArray) ? (JSONArray)o : null;
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
/*     */   public JSONObject optJSONObject(int index) {
/* 479 */     Object o = opt(index);
/* 480 */     return (o instanceof JSONObject) ? (JSONObject)o : null;
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
/*     */   public long optLong(int index) {
/* 492 */     return optLong(index, 0L);
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
/*     */   public long optLong(int index, long defaultValue) {
/*     */     try {
/* 506 */       return getLong(index);
/* 507 */     } catch (Exception e) {
/* 508 */       return defaultValue;
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
/*     */   public String optString(int index) {
/* 521 */     return optString(index, "");
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
/*     */   public String optString(int index, String defaultValue) {
/* 533 */     Object object = opt(index);
/* 534 */     return JSONObject.NULL.equals(object) ? defaultValue : object
/* 535 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray put(boolean value) {
/* 545 */     put(value ? Boolean.TRUE : Boolean.FALSE);
/* 546 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray put(Collection<Object> value) {
/* 557 */     put(new JSONArray(value));
/* 558 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray put(double value) throws JSONException {
/* 569 */     Double d = new Double(value);
/* 570 */     JSONObject.testValidity(d);
/* 571 */     put(d);
/* 572 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray put(int value) {
/* 582 */     put(new Integer(value));
/* 583 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray put(long value) {
/* 593 */     put(new Long(value));
/* 594 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONArray put(Map<String, Object> value) {
/* 605 */     put(new JSONObject(value));
/* 606 */     return this;
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
/*     */   public JSONArray put(Object value) {
/* 618 */     this.myArrayList.add(value);
/* 619 */     return this;
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
/*     */   public JSONArray put(int index, boolean value) throws JSONException {
/* 633 */     put(index, value ? Boolean.TRUE : Boolean.FALSE);
/* 634 */     return this;
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
/*     */   public JSONArray put(int index, Collection<Object> value) throws JSONException {
/* 647 */     put(index, new JSONArray(value));
/* 648 */     return this;
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
/*     */   public JSONArray put(int index, double value) throws JSONException {
/* 662 */     put(index, new Double(value));
/* 663 */     return this;
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
/*     */   public JSONArray put(int index, int value) throws JSONException {
/* 677 */     put(index, new Integer(value));
/* 678 */     return this;
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
/*     */   public JSONArray put(int index, long value) throws JSONException {
/* 692 */     put(index, new Long(value));
/* 693 */     return this;
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
/*     */   public JSONArray put(int index, Map<String, Object> value) throws JSONException {
/* 707 */     put(index, new JSONObject(value));
/* 708 */     return this;
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
/*     */   public JSONArray put(int index, Object value) throws JSONException {
/* 725 */     JSONObject.testValidity(value);
/* 726 */     if (index < 0) {
/* 727 */       throw new JSONException("JSONArray[" + index + "] not found.");
/*     */     }
/* 729 */     if (index < length()) {
/* 730 */       this.myArrayList.set(index, value);
/*     */     } else {
/* 732 */       while (index != length()) {
/* 733 */         put(JSONObject.NULL);
/*     */       }
/* 735 */       put(value);
/*     */     } 
/* 737 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(int index) {
/* 748 */     return (index >= 0 && index < length()) ? this.myArrayList
/* 749 */       .remove(index) : null;
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
/*     */   public boolean similar(Object other) {
/* 761 */     if (!(other instanceof JSONArray)) {
/* 762 */       return false;
/*     */     }
/* 764 */     int len = length();
/* 765 */     if (len != ((JSONArray)other).length()) {
/* 766 */       return false;
/*     */     }
/* 768 */     for (int i = 0; i < len; i++) {
/* 769 */       Object valueThis = get(i);
/* 770 */       Object valueOther = ((JSONArray)other).get(i);
/* 771 */       if (valueThis instanceof JSONObject) {
/* 772 */         if (!((JSONObject)valueThis).similar(valueOther)) {
/* 773 */           return false;
/*     */         }
/* 775 */       } else if (valueThis instanceof JSONArray) {
/* 776 */         if (!((JSONArray)valueThis).similar(valueOther)) {
/* 777 */           return false;
/*     */         }
/* 779 */       } else if (!valueThis.equals(valueOther)) {
/* 780 */         return false;
/*     */       } 
/*     */     } 
/* 783 */     return true;
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
/*     */   public JSONObject toJSONObject(JSONArray names) throws JSONException {
/* 797 */     if (names == null || names.length() == 0 || length() == 0) {
/* 798 */       return null;
/*     */     }
/* 800 */     JSONObject jo = new JSONObject();
/* 801 */     for (int i = 0; i < names.length(); i++) {
/* 802 */       jo.put(names.getString(i), opt(i));
/*     */     }
/* 804 */     return jo;
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
/*     */   public String toString() {
/*     */     try {
/* 820 */       return toString(0);
/* 821 */     } catch (Exception e) {
/* 822 */       return null;
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
/*     */   public String toString(int indentFactor) throws JSONException {
/* 838 */     StringWriter sw = new StringWriter();
/* 839 */     synchronized (sw.getBuffer()) {
/* 840 */       return write(sw, indentFactor, 0).toString();
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
/*     */   public Writer write(Writer writer) throws JSONException {
/* 854 */     return write(writer, 0, 0);
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
/*     */   Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
/*     */     try {
/* 871 */       boolean commanate = false;
/* 872 */       int length = length();
/* 873 */       writer.write(91);
/*     */       
/* 875 */       if (length == 1) {
/* 876 */         JSONObject.writeValue(writer, this.myArrayList.get(0), indentFactor, indent);
/*     */       }
/* 878 */       else if (length != 0) {
/* 879 */         int newindent = indent + indentFactor;
/*     */         
/* 881 */         for (int i = 0; i < length; i++) {
/* 882 */           if (commanate) {
/* 883 */             writer.write(44);
/*     */           }
/* 885 */           if (indentFactor > 0) {
/* 886 */             writer.write(10);
/*     */           }
/* 888 */           JSONObject.indent(writer, newindent);
/* 889 */           JSONObject.writeValue(writer, this.myArrayList.get(i), indentFactor, newindent);
/*     */           
/* 891 */           commanate = true;
/*     */         } 
/* 893 */         if (indentFactor > 0) {
/* 894 */           writer.write(10);
/*     */         }
/* 896 */         JSONObject.indent(writer, indent);
/*     */       } 
/* 898 */       writer.write(93);
/* 899 */       return writer;
/* 900 */     } catch (IOException e) {
/* 901 */       throw new JSONException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\installer\json\JSONArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */