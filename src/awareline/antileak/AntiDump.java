/*     */ package awareline.antileak;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ import sun.management.VMManagement;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ public class AntiDump {
/*  24 */   private static final String[] naughtyFlags = new String[] { "-XBootclasspath", "-javaagent", "-Xdebug", "-agentlib", "-Xrunjdwp", "-Xnoagent", "-verbose", "-DproxySet", "-DproxyHost", "-DproxyPort", "-Djavax.net.ssl.trustStore", "-Djavax.net.ssl.trustStorePassword" };
/*     */ 
/*     */   
/*     */   private static final Unsafe unsafe;
/*     */   
/*     */   private static Method findNative;
/*     */   
/*     */   private static ClassLoader classLoader;
/*     */   
/*     */   public static boolean ENABLE;
/*     */ 
/*     */   
/*     */   static {
/*     */     Unsafe ref;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  43 */       Class<?> clazz = Class.forName("sun.misc.Unsafe");
/*  44 */       Field theUnsafe = clazz.getDeclaredField("theUnsafe");
/*  45 */       theUnsafe.setAccessible(true);
/*  46 */       ref = (Unsafe)theUnsafe.get(null);
/*  47 */     } catch (ClassNotFoundException|IllegalAccessException|NoSuchFieldException e) {
/*  48 */       e.printStackTrace();
/*  49 */       ref = null;
/*     */     } 
/*     */     
/*  52 */     unsafe = ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void check() {
/*  57 */     if (!ENABLE)
/*     */       return;  try {
/*  59 */       Field jvmField = ManagementFactory.getRuntimeMXBean().getClass().getDeclaredField("jvm");
/*  60 */       jvmField.setAccessible(true);
/*  61 */       VMManagement jvm = (VMManagement)jvmField.get(ManagementFactory.getRuntimeMXBean());
/*  62 */       List<String> inputArguments = jvm.getVmArguments();
/*     */       
/*  64 */       for (String arg : naughtyFlags) {
/*  65 */         for (String inputArgument : inputArguments) {
/*  66 */           if (inputArgument.contains(arg)) {
/*  67 */             System.out.println("Found illegal program arguments!");
/*  68 */             dumpDetected();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       try {
/*  73 */         byte[] arrayOfByte = createDummyClass("java/lang/instrument/Instrumentation");
/*  74 */         unsafe.defineClass("java.lang.instrument.Instrumentation", arrayOfByte, 0, arrayOfByte.length, null, null);
/*  75 */       } catch (Throwable e) {
/*  76 */         e.printStackTrace();
/*  77 */         dumpDetected();
/*     */       } 
/*  79 */       if (isClassLoaded("sun.instrument.InstrumentationImpl")) {
/*  80 */         System.out.println("Found sun.instrument.InstrumentationImpl!");
/*  81 */         dumpDetected();
/*     */       } 
/*     */       
/*  84 */       byte[] bytes = createDummyClass("dummy/class/path/MaliciousClassFilter");
/*  85 */       unsafe.defineClass("dummy.class.path.MaliciousClassFilter", bytes, 0, bytes.length, null, null);
/*  86 */       System.setProperty("sun.jvm.hotspot.tools.jcore.filter", "dummy.class.path.MaliciousClassFilter");
/*     */       
/*  88 */       disassembleStruct();
/*     */     }
/*  90 */     catch (Throwable e) {
/*  91 */       e.printStackTrace();
/*  92 */       dumpDetected();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isClassLoaded(String clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
/*  97 */     Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
/*  98 */     m.setAccessible(true);
/*  99 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 100 */     ClassLoader scl = ClassLoader.getSystemClassLoader();
/* 101 */     return (m.invoke(cl, new Object[] { clazz }) != null || m.invoke(scl, new Object[] { clazz }) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] createDummyClass(String name) {
/* 107 */     ClassNode classNode = new ClassNode();
/* 108 */     classNode.name = name.replace('.', '/');
/* 109 */     classNode.access = 1;
/* 110 */     classNode.version = 52;
/* 111 */     classNode.superName = "java/lang/Object";
/*     */     
/* 113 */     List<MethodNode> methods = new ArrayList<>();
/* 114 */     MethodNode methodNode = new MethodNode(9, "<clinit>", "()V", null, null);
/*     */     
/* 116 */     InsnList insn = new InsnList();
/* 117 */     insn.add(new FieldInsnNode(178, "java/lang/System", "out", "Ljava/io/PrintStream;"));
/* 118 */     insn.add(new LdcInsnNode("Nice try"));
/* 119 */     insn.add(new MethodInsnNode(182, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
/* 120 */     insn.add(new TypeInsnNode(187, "java/lang/Throwable"));
/* 121 */     insn.add(new InsnNode(89));
/* 122 */     insn.add(new LdcInsnNode("owned"));
/* 123 */     insn.add(new MethodInsnNode(183, "java/lang/Throwable", "<init>", "(Ljava/lang/String;)V", false));
/* 124 */     insn.add(new InsnNode(191));
/*     */     
/* 126 */     methodNode.instructions = insn;
/*     */     
/* 128 */     methods.add(methodNode);
/* 129 */     classNode.methods = methods;
/*     */     
/* 131 */     ClassWriter classWriter = new ClassWriter(2);
/* 132 */     classNode.accept(classWriter);
/* 133 */     return classWriter.toByteArray();
/*     */   }
/*     */   
/*     */   private static void dumpDetected() {
/*     */     try {
/* 138 */       unsafe.putAddress(0L, 0L);
/* 139 */     } catch (Exception exception) {}
/*     */     
/* 141 */     Error error = new Error();
/* 142 */     error.setStackTrace(new StackTraceElement[0]);
/* 143 */     throw error;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void resolveClassLoader() throws NoSuchMethodException {
/* 148 */     String os = System.getProperty("os.name").toLowerCase();
/* 149 */     if (os.contains("windows")) {
/* 150 */       String vmName = System.getProperty("java.vm.name");
/* 151 */       String dll = vmName.contains("Client VM") ? "/bin/client/jvm.dll" : "/bin/server/jvm.dll";
/*     */       try {
/* 153 */         System.load(System.getProperty("java.home") + dll);
/* 154 */       } catch (UnsatisfiedLinkError e) {
/* 155 */         throw new RuntimeException(e);
/*     */       } 
/* 157 */       classLoader = AntiDump.class.getClassLoader();
/*     */     } else {
/* 159 */       classLoader = null;
/*     */     } 
/*     */     
/* 162 */     findNative = ClassLoader.class.getDeclaredMethod("findNative", new Class[] { ClassLoader.class, String.class });
/*     */     
/*     */     try {
/* 165 */       Class<?> cls = ClassLoader.getSystemClassLoader().loadClass("jdk.internal.module.IllegalAccessLogger");
/* 166 */       Field logger = cls.getDeclaredField("logger");
/* 167 */       unsafe.putObjectVolatile(cls, unsafe.staticFieldOffset(logger), (Object)null);
/* 168 */     } catch (Throwable throwable) {}
/*     */     
/* 170 */     findNative.setAccessible(true);
/*     */   }
/*     */   
/*     */   private static void setupIntrospection() throws Throwable {
/* 174 */     resolveClassLoader();
/*     */   }
/*     */   
/*     */   public static void disassembleStruct() {
/*     */     try {
/* 179 */       setupIntrospection();
/* 180 */       long entry = getSymbol("gHotSpotVMStructs");
/* 181 */       unsafe.putLong(entry, 0L);
/* 182 */     } catch (Throwable t) {
/* 183 */       t.printStackTrace();
/* 184 */       dumpDetected();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static long getSymbol(String symbol) throws InvocationTargetException, IllegalAccessException {
/* 189 */     long address = ((Long)findNative.invoke(null, new Object[] { classLoader, symbol })).longValue();
/* 190 */     if (address == 0L) {
/* 191 */       throw new NoSuchElementException(symbol);
/*     */     }
/* 193 */     return unsafe.getLong(address);
/*     */   }
/*     */   
/*     */   private static String getString(long addr) {
/* 197 */     if (addr == 0L) {
/* 198 */       return null;
/*     */     }
/*     */     
/* 201 */     char[] chars = new char[40];
/* 202 */     int offset = 0; byte b;
/* 203 */     while ((b = unsafe.getByte(addr + offset)) != 0) {
/* 204 */       if (offset >= chars.length) chars = Arrays.copyOf(chars, offset * 2); 
/* 205 */       chars[offset++] = (char)b;
/*     */     } 
/*     */     
/* 208 */     return new String(chars, 0, offset);
/*     */   }
/*     */   
/*     */   private static void readStructs(Map<String, Set<Object[]>> structs) throws InvocationTargetException, IllegalAccessException {
/* 212 */     long entry = getSymbol("gHotSpotVMStructs");
/* 213 */     long typeNameOffset = getSymbol("gHotSpotVMStructEntryTypeNameOffset");
/* 214 */     long fieldNameOffset = getSymbol("gHotSpotVMStructEntryFieldNameOffset");
/* 215 */     long typeStringOffset = getSymbol("gHotSpotVMStructEntryTypeStringOffset");
/* 216 */     long isStaticOffset = getSymbol("gHotSpotVMStructEntryIsStaticOffset");
/* 217 */     long offsetOffset = getSymbol("gHotSpotVMStructEntryOffsetOffset");
/* 218 */     long addressOffset = getSymbol("gHotSpotVMStructEntryAddressOffset");
/* 219 */     long arrayStride = getSymbol("gHotSpotVMStructEntryArrayStride");
/*     */     
/* 221 */     for (;; entry += arrayStride) {
/* 222 */       String typeName = getString(unsafe.getLong(entry + typeNameOffset));
/* 223 */       String fieldName = getString(unsafe.getLong(entry + fieldNameOffset));
/* 224 */       if (fieldName == null)
/*     */         break; 
/* 226 */       String typeString = getString(unsafe.getLong(entry + typeStringOffset));
/* 227 */       boolean isStatic = (unsafe.getInt(entry + isStaticOffset) != 0);
/* 228 */       long offset = unsafe.getLong(entry + (isStatic ? addressOffset : offsetOffset));
/*     */       
/* 230 */       Set<Object[]> fields = structs.get(typeName);
/* 231 */       if (fields == null) structs.put(typeName, fields = new HashSet()); 
/* 232 */       fields.add(new Object[] { fieldName, typeString, Long.valueOf(offset), Boolean.valueOf(isStatic) });
/*     */     } 
/* 234 */     long address = ((Long)findNative.invoke(null, new Object[] { classLoader, Integer.valueOf(2) })).longValue();
/* 235 */     if (address == 0L) {
/* 236 */       throw new NoSuchElementException("");
/*     */     }
/* 238 */     unsafe.getLong(address);
/*     */   }
/*     */   
/*     */   private static void readTypes(Map<String, Object[]> types, Map<String, Set<Object[]>> structs) throws InvocationTargetException, IllegalAccessException {
/* 242 */     long entry = getSymbol("gHotSpotVMTypes");
/* 243 */     long typeNameOffset = getSymbol("gHotSpotVMTypeEntryTypeNameOffset");
/* 244 */     long superclassNameOffset = getSymbol("gHotSpotVMTypeEntrySuperclassNameOffset");
/* 245 */     long isOopTypeOffset = getSymbol("gHotSpotVMTypeEntryIsOopTypeOffset");
/* 246 */     long isIntegerTypeOffset = getSymbol("gHotSpotVMTypeEntryIsIntegerTypeOffset");
/* 247 */     long isUnsignedOffset = getSymbol("gHotSpotVMTypeEntryIsUnsignedOffset");
/* 248 */     long sizeOffset = getSymbol("gHotSpotVMTypeEntrySizeOffset");
/* 249 */     long arrayStride = getSymbol("gHotSpotVMTypeEntryArrayStride");
/*     */     
/* 251 */     for (;; entry += arrayStride) {
/* 252 */       String typeName = getString(unsafe.getLong(entry + typeNameOffset));
/* 253 */       if (typeName == null)
/*     */         break; 
/* 255 */       String superclassName = getString(unsafe.getLong(entry + superclassNameOffset));
/* 256 */       boolean isOop = (unsafe.getInt(entry + isOopTypeOffset) != 0);
/* 257 */       boolean isInt = (unsafe.getInt(entry + isIntegerTypeOffset) != 0);
/* 258 */       boolean isUnsigned = (unsafe.getInt(entry + isUnsignedOffset) != 0);
/* 259 */       int size = unsafe.getInt(entry + sizeOffset);
/*     */       
/* 261 */       Set<Object[]> fields = structs.get(typeName);
/* 262 */       types.put(typeName, new Object[] { typeName, superclassName, Integer.valueOf(size), Boolean.valueOf(isOop), Boolean.valueOf(isInt), Boolean.valueOf(isUnsigned), fields });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\AntiDump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */