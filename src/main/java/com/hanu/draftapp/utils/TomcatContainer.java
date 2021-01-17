package com.hanu.draftapp.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class TomcatContainer implements ServerContainer {
    // constants
    private static final int DEFAULT_PORT = 8080;
    private static final String DEFAULT_HOSTNAME = "localhost";
    private static final String DEFAULT_APP_BASE = ".";
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String CONTEXT_PATH = "";
    private static final Logger logger = Logger.getLogger(TomcatContainer.class.getName());

    // fields
    private Tomcat instance;
    private Context context;

    public TomcatContainer() {
        instance = new Tomcat();
    }

    @Override
    public ServerContainer defaultConfigure() {
        instance.setHostname(DEFAULT_HOSTNAME);
        instance.setPort(DEFAULT_PORT);
        instance.getConnector();
        instance.getHost().setAppBase(DEFAULT_APP_BASE);
        context = instance.addContext(CONTEXT_PATH, TMP_DIR);
        return this;
    }

    @Override
    public ServerContainer addServlet(Class<? extends HttpServlet> servletClass) 
            throws InstantiationException, IllegalAccessException,
                IllegalArgumentException, InvocationTargetException, 
                NoSuchMethodException, SecurityException {
        WebServlet servletAnnotation = servletClass.getAnnotation(WebServlet.class);
        String servletName = servletAnnotation.name();
        HttpServlet servletInstance = servletClass.getConstructor(new Class[] {}).newInstance();
        String[] urlPatterns = servletAnnotation.urlPatterns();
        instance.addServlet(CONTEXT_PATH, servletName, servletInstance);
        for (String urlPattern : urlPatterns) {
            context.addServletMappingDecoded(urlPattern, servletName);
        }
        return this;
    }

    @Override
    public ServerContainer addServlets(List<Class<? extends HttpServlet>> servletClasses) {
        for (Class<? extends HttpServlet> servletClass : servletClasses) {
            try {
                addServlet(servletClass);
            } catch (InstantiationException | IllegalAccessException 
                    | IllegalArgumentException | InvocationTargetException 
                    | NoSuchMethodException | SecurityException e) {
                logger.warning(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return this;
    }

    @Override
    public ServerContainer addServlets(String basePackage) 
        throws ClassNotFoundException, IOException {
        List<Class<? extends HttpServlet>> servletClasses = 
            ServletLoader.getServletClasses(basePackage);
        addServlets(servletClasses);
        return this;
    }

    @Override
    public void start() throws IOException {
        try {
            instance.start();
            instance.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(255);
        }
    }

    static class ServletLoader {
        private static final String PACKAGE_SEPARATOR = ".";
        private static final String PATH_SEPARATOR = "/";
        private static final String PARENT_DIR = "./src/main/java/";
        private static final Logger logger = Logger.getLogger(ServletLoader.class.getName());

        private static String packageToPath(String packageName) {
            return packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
        }

        private static String fullyQualifiedClassNameFrom(String packageName, String className) {
            return packageName.concat(".").concat(className.replace(".java", "")).replace(".class", "");
        }

        @SuppressWarnings("unchecked")
        private static void addServletClassToCollection(List<Class<? extends HttpServlet>> servletClasses,
                Class<?> servletClass) {
            if (!servletClass.equals(HttpServlet.class) && !HttpServlet.class.isAssignableFrom(servletClass)) {
                return;
            }
            if (servletClass.getAnnotation(WebServlet.class) == null) {
                return;
            }
            servletClasses.add((Class<HttpServlet>) servletClass);
            logger.info("Added servlet class: " + servletClass.getCanonicalName());
        }

        private static String[] getServletClassFileNamesFromJar(String servletPackageName) throws IOException {
            String currentJarFile = ServletLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                    .substring(1);

            JarFile file = new JarFile(currentJarFile);
            Enumeration<JarEntry> files = file.entries();
            List<String> servletClasses = new ArrayList<>();
            String pkgPath = packageToPath(servletPackageName) + "/";
            while (files.hasMoreElements()) {
                String name = files.nextElement().getName();
                if (name.startsWith(pkgPath) && !name.equals(pkgPath)) {
                    servletClasses.add(name.replace(pkgPath, ""));
                }
            }
            file.close();
            return servletClasses.toArray(new String[servletClasses.size()]);
        }

        public static List<Class<? extends HttpServlet>> getServletClasses(String servletPackageName)
                throws ClassNotFoundException, IOException {
            logger.info("Initializing servlet classes...");
            logger.info(new File(".").getAbsolutePath());
            String servletPkgPath = PARENT_DIR.concat(packageToPath(servletPackageName));

            String[] servletClassFileNames = null;

            try {
                servletClassFileNames = new File(servletPkgPath).list();
                @SuppressWarnings("unused")
                Object o = servletClassFileNames[0];
            } catch (Exception e) {
                servletClassFileNames = getServletClassFileNamesFromJar(servletPackageName);
            }

            List<Class<? extends HttpServlet>> servletClasses = new ArrayList<>();
            assert servletClassFileNames != null;
            for (String servletClassName : servletClassFileNames) {
                Class<?> servletClass = Class
                        .forName(fullyQualifiedClassNameFrom(servletPackageName, servletClassName));
                addServletClassToCollection(servletClasses, servletClass);
            }
            logger.info("Finished loading servlets.");
            return servletClasses;
        }
    }
}
