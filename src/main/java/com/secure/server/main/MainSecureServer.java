package com.secure.server.main;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.swagger.jaxrs.config.BeanConfig;

/**
 * Main class.
 */
public class MainSecureServer {

    // Base URI the Grizzly HTTP server will listen on
    private static URI BASE_URI;

    private static final String KEYSTORE_LOC = "keystore_server";

    private static final String KEYSTORE_PASS = "123456";

    private static final String TRUSTSTORE_LOC = "truststore_server";

    private static final String TRUSTSTORE_PASS = "123456";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * 
     * @return Grizzly HTTP server.
     */
    public static HttpServer getLookupServer(Config config) {
        BASE_URI = URI.create("http://0.0.0.0:" + config.getPort());
        String resources = "com.secure.server.main";
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.1");
        beanConfig.setSchemes(new String[] { "https" });
        beanConfig.setBasePath("");
        beanConfig.setResourcePackage(resources);
        beanConfig.setScan(true);

        final ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages(resources);
        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        resourceConfig.register(JacksonFeature.class);
        resourceConfig.register(JacksonJsonProvider.class);
        resourceConfig.register(new CrossDomainFilter());

        SSLContextConfigurator sslCon = new SSLContextConfigurator();

        sslCon.setKeyStoreFile(KEYSTORE_LOC);
        sslCon.setKeyStorePass(KEYSTORE_PASS);

        sslCon.setTrustStoreFile(TRUSTSTORE_LOC);
        sslCon.setTrustStorePass(TRUSTSTORE_PASS);

        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, true,
            new SSLEngineConfigurator(sslCon).setClientMode(false).setNeedClientAuth(false));

    }

    /**
     * Main method.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = MainSecureServer.getLookupServer(Config.getConfig());
        server.start();
        ClassLoader loader = MainSecureServer.class.getClassLoader();
        CLStaticHttpHandler docsHandler = new CLStaticHttpHandler(loader, "swagger-ui/");
        docsHandler.setFileCacheEnabled(false);
        ServerConfiguration cfg = server.getServerConfiguration();
        cfg.addHttpHandler(docsHandler, "/docs/");
    }
}