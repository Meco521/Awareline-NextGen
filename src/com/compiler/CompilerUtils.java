/*     */ package com.compiler;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.security.AccessController;
/*     */ import java.util.Arrays;
/*     */ import javax.tools.JavaCompiler;
/*     */ import javax.tools.StandardJavaFileManager;
/*     */ import javax.tools.ToolProvider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum CompilerUtils
/*     */ {
/*     */   public static final CachedCompiler CACHED_COMPILER;
/*     */   private static final Method DEFINE_CLASS_METHOD;
/*     */   private static final Charset UTF_8;
/*     */   static JavaCompiler s_compiler;
/*     */   static StandardJavaFileManager s_standardJavaFileManager;
/*     */   
/*     */   static {
/*  45 */     CACHED_COMPILER = new CachedCompiler(null, null);
/*     */ 
/*     */     
/*  48 */     UTF_8 = StandardCharsets.UTF_8;
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
/*     */     try {
/*  65 */       Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
/*     */       
/*  67 */       setAccessible(theUnsafe, true);
/*  68 */       Unsafe u = (Unsafe)theUnsafe.get(null);
/*     */       
/*  70 */       DEFINE_CLASS_METHOD = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
/*     */       try {
/*  72 */         Field f = AccessibleObject.class.getDeclaredField("override");
/*  73 */         long offset = u.objectFieldOffset(f);
/*  74 */         u.putBoolean(DEFINE_CLASS_METHOD, offset, true);
/*  75 */       } catch (NoSuchFieldException e) {
/*  76 */         setAccessible(DEFINE_CLASS_METHOD, true);
/*     */       }
/*     */     
/*  79 */     } catch (NoSuchMethodException|IllegalAccessException|NoSuchFieldException e) {
/*  80 */       throw new AssertionError(e);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  85 */     reset();
/*     */   }
/*     */   
/*     */   private static void reset() {
/*  89 */     s_compiler = ToolProvider.getSystemJavaCompiler();
/*  90 */     if (s_compiler == null) {
/*     */       try {
/*  92 */         Class<?> javacTool = Class.forName("com.sun.tools.javac.api.JavacTool");
/*  93 */         Method create = javacTool.getMethod("create", new Class[0]);
/*  94 */         s_compiler = (JavaCompiler)create.invoke(null, new Object[0]);
/*  95 */       } catch (Exception e) {
/*  96 */         throw new AssertionError(e);
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
/*     */   public static Class loadFromJava(String className, String javaCode) throws ClassNotFoundException {
/* 110 */     return CACHED_COMPILER.loadFromJava(Thread.currentThread().getContextClassLoader(), className, javaCode); } static void setAccessible(AccessibleObject ao, boolean accessible) {
/*     */     if (System.getSecurityManager() == null) {
/*     */       ao.setAccessible(accessible);
/*     */     } else {
/*     */       AccessController.doPrivileged(() -> {
/*     */             ao.setAccessible(accessible);
/*     */             return null;
/*     */           });
/*     */     } 
/*     */   } public static void defineClass(String className, byte[] bytes) {
/* 120 */     defineClass(Thread.currentThread().getContextClassLoader(), className, bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class defineClass(ClassLoader classLoader, String className, byte[] bytes) {
/*     */     try {
/* 132 */       return (Class)DEFINE_CLASS_METHOD.invoke(classLoader, new Object[] { className, bytes, Integer.valueOf(0), Integer.valueOf(bytes.length) });
/* 133 */     } catch (IllegalAccessException e) {
/* 134 */       throw new AssertionError(e);
/* 135 */     } catch (InvocationTargetException e) {
/*     */       
/* 137 */       throw new AssertionError(e.getCause());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readBytes(File file) {
/* 144 */     if (!file.exists()) return null; 
/* 145 */     long len = file.length();
/* 146 */     if (len > Runtime.getRuntime().totalMemory() / 10L)
/* 147 */       throw new IllegalStateException("Attempted to read large file " + file + " was " + len + " bytes."); 
/* 148 */     byte[] bytes = new byte[(int)len];
/* 149 */     DataInputStream dis = null;
/*     */     try {
/* 151 */       dis = new DataInputStream(Files.newInputStream(file.toPath(), new java.nio.file.OpenOption[0]));
/* 152 */       dis.readFully(bytes);
/* 153 */     } catch (IOException e) {
/* 154 */       close(dis);
/* 155 */       throw new IllegalStateException("Unable to read file " + file, e);
/*     */     } 
/*     */     
/* 158 */     return bytes;
/*     */   }
/*     */   
/*     */   private static void close(Closeable closeable) {
/* 162 */     if (closeable != null)
/*     */       try {
/* 164 */         closeable.close();
/* 165 */       } catch (IOException e) {
/* 166 */         e.printStackTrace();
/*     */       }  
/*     */   }
/*     */   
/*     */   public static boolean writeText(File file, String text) {
/* 171 */     return writeBytes(file, encodeUTF8(text));
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] encodeUTF8(String text) {
/*     */     try {
/* 177 */       return text.getBytes(UTF_8.name());
/* 178 */     } catch (UnsupportedEncodingException e) {
/* 179 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean writeBytes(File file, byte[] bytes) {
/* 184 */     File parentDir = file.getParentFile();
/* 185 */     if (!parentDir.isDirectory() && !parentDir.mkdirs()) {
/* 186 */       throw new IllegalStateException("Unable to create directory " + parentDir);
/*     */     }
/* 188 */     File bak = null;
/* 189 */     if (file.exists()) {
/* 190 */       byte[] bytes2 = readBytes(file);
/* 191 */       if (Arrays.equals(bytes, bytes2))
/* 192 */         return false; 
/* 193 */       bak = new File(parentDir, file.getName() + ".bak");
/* 194 */       file.renameTo(bak);
/*     */     } 
/*     */     
/* 197 */     FileOutputStream fos = null;
/*     */     try {
/* 199 */       fos = new FileOutputStream(file);
/* 200 */       fos.write(bytes);
/* 201 */     } catch (IOException e) {
/* 202 */       close(fos);
/* 203 */       file.delete();
/* 204 */       if (bak != null)
/* 205 */         bak.renameTo(file); 
/* 206 */       throw new IllegalStateException("Unable to write " + file, e);
/*     */     } 
/* 208 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\compiler\CompilerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */