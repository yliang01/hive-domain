package cc.cc3c.hive.domain;

import org.h2.tools.Server;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class H2FileDatabaseServer {
    private static final String DB_FILE = "./data/db/hive";
    private static final String JDBC_OPTIONS = ";MODE=MySQL;DATABASE_TO_LOWER=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE;DB_CLOSE_ON_EXIT=FALSE";

    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String TCP_PORT = "9092";
    private static final String WEB_PORT = "8082";
    private static final boolean TCP_ALLOW_OTHERS = false;
    private static final boolean WEB_ALLOW_OTHERS = false;

    public static void main(String[] args) throws Exception {
        Path dbFile = Paths.get(DB_FILE).toAbsolutePath().normalize();
        Path dbDir = dbFile.getParent();
        if (dbDir != null) {
            Files.createDirectories(dbDir);
        }

        String dbPath = dbFile.toString().replace('\\', '/');
        String fileJdbcUrl = "jdbc:h2:file:" + dbPath + JDBC_OPTIONS;
        String tcpJdbcUrl = "jdbc:h2:tcp://localhost:" + TCP_PORT + "/" + dbPath + JDBC_OPTIONS;
        try (var ignored = DriverManager.getConnection(fileJdbcUrl, USER, PASSWORD)) {
            // Open once so the configured local database file is created before exposing H2 tools.
        }

        Server tcpServer = Server.createTcpServer(tcpArgs(dbDir)).start();
        Server webServer = Server.createWebServer(webArgs()).start();

        System.out.println("H2 file database started");
        System.out.println("File JDBC URL: " + fileJdbcUrl);
        System.out.println("TCP JDBC URL: " + tcpJdbcUrl);
        System.out.println("User: " + USER);
        System.out.println("Web Console: http://localhost:" + WEB_PORT);
        System.out.println("Press Ctrl+C to stop.");

        CountDownLatch stopLatch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            webServer.stop();
            tcpServer.stop();
            stopLatch.countDown();
        }, "h2-file-database-shutdown"));
        stopLatch.await();
    }

    private static String[] tcpArgs(Path dbDir) {
        List<String> args = new ArrayList<>();
        args.add("-tcp");
        args.add("-tcpPort");
        args.add(TCP_PORT);
        args.add("-ifNotExists");
        if (dbDir != null) {
            args.add("-baseDir");
            args.add(dbDir.toString());
        }
        if (TCP_ALLOW_OTHERS) {
            args.add("-tcpAllowOthers");
        }
        return args.toArray(String[]::new);
    }

    private static String[] webArgs() {
        List<String> args = new ArrayList<>();
        args.add("-web");
        args.add("-webPort");
        args.add(WEB_PORT);
        if (WEB_ALLOW_OTHERS) {
            args.add("-webAllowOthers");
        }
        return args.toArray(String[]::new);
    }
}
