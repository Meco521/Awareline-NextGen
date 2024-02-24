/*     */ package com.compiler;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URI;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.tools.FileObject;
/*     */ import javax.tools.JavaFileManager;
/*     */ import javax.tools.JavaFileObject;
/*     */ import javax.tools.SimpleJavaFileObject;
/*     */ import javax.tools.StandardJavaFileManager;
/*     */ import javax.tools.StandardLocation;
/*     */ import sun.misc.Unsafe;
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
/*     */ class MyJavaFileManager
/*     */   implements JavaFileManager
/*     */ {
/*     */   private static final Unsafe unsafe;
/*     */   private static final long OVERRIDE_OFFSET;
/*     */   private final StandardJavaFileManager fileManager;
/*     */   
/*     */   static {
/*     */     long offset;
/*     */     try {
/*  46 */       Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
/*  47 */       theUnsafe.setAccessible(true);
/*  48 */       unsafe = (Unsafe)theUnsafe.get(null);
/*  49 */     } catch (Exception ex) {
/*  50 */       throw new AssertionError(ex);
/*     */     } 
/*     */     try {
/*  53 */       Field f = AccessibleObject.class.getDeclaredField("override");
/*  54 */       offset = unsafe.objectFieldOffset(f);
/*  55 */     } catch (NoSuchFieldException e) {
/*  56 */       offset = 0L;
/*     */     } 
/*  58 */     OVERRIDE_OFFSET = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   final Map<String, CloseableByteArrayOutputStream> buffers = Collections.synchronizedMap(new LinkedHashMap<>());
/*     */   
/*     */   MyJavaFileManager(StandardJavaFileManager fileManager) {
/*  67 */     this.fileManager = fileManager;
/*     */   }
/*     */   
/*     */   public Iterable<Set<JavaFileManager.Location>> listLocationsForModules(JavaFileManager.Location location) {
/*  71 */     return invokeNamedMethodIfAvailable(location, "listLocationsForModules");
/*     */   }
/*     */   
/*     */   public String inferModuleName(JavaFileManager.Location location) {
/*  75 */     return invokeNamedMethodIfAvailable(location, "inferModuleName");
/*     */   }
/*     */   
/*     */   public ClassLoader getClassLoader(JavaFileManager.Location location) {
/*  79 */     return this.fileManager.getClassLoader(location);
/*     */   }
/*     */   
/*     */   public Iterable<JavaFileObject> list(JavaFileManager.Location location, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
/*  83 */     return this.fileManager.list(location, packageName, kinds, recurse);
/*     */   }
/*     */   
/*     */   public String inferBinaryName(JavaFileManager.Location location, JavaFileObject file) {
/*  87 */     return this.fileManager.inferBinaryName(location, file);
/*     */   }
/*     */   
/*     */   public boolean isSameFile(FileObject a, FileObject b) {
/*  91 */     return this.fileManager.isSameFile(a, b);
/*     */   }
/*     */   
/*     */   public boolean handleOption(String current, Iterator<String> remaining) {
/*  95 */     return this.fileManager.handleOption(current, remaining);
/*     */   }
/*     */   
/*     */   public boolean hasLocation(JavaFileManager.Location location) {
/*  99 */     return this.fileManager.hasLocation(location);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaFileObject getJavaFileForInput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind) throws IOException {
/* 104 */     if (location == StandardLocation.CLASS_OUTPUT) {
/*     */       boolean success;
/*     */       final byte[] bytes;
/* 107 */       synchronized (this.buffers) {
/* 108 */         success = (this.buffers.containsKey(className) && kind == JavaFileObject.Kind.CLASS);
/* 109 */         bytes = ((CloseableByteArrayOutputStream)this.buffers.get(className)).toByteArray();
/*     */       } 
/* 111 */       if (success)
/*     */       {
/* 113 */         return new SimpleJavaFileObject(URI.create(className), kind)
/*     */           {
/*     */             public InputStream openInputStream() {
/* 116 */               return new ByteArrayInputStream(bytes);
/*     */             }
/*     */           };
/*     */       }
/*     */     } 
/* 121 */     return this.fileManager.getJavaFileForInput(location, className, kind);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, final String className, JavaFileObject.Kind kind, FileObject sibling) {
/* 126 */     return new SimpleJavaFileObject(URI.create(className), kind)
/*     */       {
/*     */         public OutputStream openOutputStream()
/*     */         {
/* 130 */           CloseableByteArrayOutputStream baos = new CloseableByteArrayOutputStream();
/*     */ 
/*     */ 
/*     */           
/* 134 */           MyJavaFileManager.this.buffers.putIfAbsent(className, baos);
/*     */           
/* 136 */           return baos;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public FileObject getFileForInput(JavaFileManager.Location location, String packageName, String relativeName) throws IOException {
/* 142 */     return this.fileManager.getFileForInput(location, packageName, relativeName);
/*     */   }
/*     */   
/*     */   public FileObject getFileForOutput(JavaFileManager.Location location, String packageName, String relativeName, FileObject sibling) throws IOException {
/* 146 */     return this.fileManager.getFileForOutput(location, packageName, relativeName, sibling);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 154 */     this.fileManager.close();
/*     */   }
/*     */   
/*     */   public int isSupportedOption(String option) {
/* 158 */     return this.fileManager.isSupportedOption(option);
/*     */   }
/*     */   
/*     */   public void clearBuffers() {
/* 162 */     this.buffers.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, byte[]> getAllBuffers() {
/* 167 */     Map<String, byte[]> ret = (Map)new LinkedHashMap<>(this.buffers.size() << 1);
/* 168 */     Map<String, CloseableByteArrayOutputStream> compiledClasses = new LinkedHashMap<>(ret.size());
/*     */     
/* 170 */     synchronized (this.buffers) {
/* 171 */       compiledClasses.putAll(this.buffers);
/*     */     } 
/*     */     
/* 174 */     for (Map.Entry<String, CloseableByteArrayOutputStream> e : compiledClasses.entrySet()) {
/*     */       
/*     */       try {
/* 177 */         ((CloseableByteArrayOutputStream)e.getValue()).closeFuture().get(30L, TimeUnit.SECONDS);
/* 178 */       } catch (InterruptedException t) {
/* 179 */         Thread.currentThread().interrupt();
/*     */         
/* 181 */         System.out.println("Interrupted while waiting for compilation result [class=" + (String)e.getKey() + "]");
/*     */         
/*     */         break;
/* 184 */       } catch (ExecutionException|java.util.concurrent.TimeoutException t) {
/* 185 */         t.printStackTrace();
/* 186 */         System.out.println("Failed to wait for compilation result [class=" + (String)e.getKey() + "]");
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 191 */       byte[] value = ((CloseableByteArrayOutputStream)e.getValue()).toByteArray();
/*     */       
/* 193 */       ret.put(e.getKey(), value);
/*     */     } 
/*     */     
/* 196 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> T invokeNamedMethodIfAvailable(JavaFileManager.Location location, String name) {
/* 201 */     Method[] methods = this.fileManager.getClass().getDeclaredMethods();
/* 202 */     for (Method method : methods) {
/* 203 */       if (method.getName().equals(name) && (method.getParameterTypes()).length == 1 && method
/* 204 */         .getParameterTypes()[0] == JavaFileManager.Location.class) {
/*     */         try {
/* 206 */           if (OVERRIDE_OFFSET == 0L) {
/* 207 */             method.setAccessible(true);
/*     */           } else {
/* 209 */             unsafe.putBoolean(method, OVERRIDE_OFFSET, true);
/* 210 */           }  return (T)method.invoke(this.fileManager, new Object[] { location });
/* 211 */         } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 212 */           throw new UnsupportedOperationException("Unable to invoke method " + name);
/*     */         } 
/*     */       }
/*     */     } 
/* 216 */     throw new UnsupportedOperationException("Unable to find method " + name);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\compiler\MyJavaFileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */