package server.src.main.java;

import org.junit.Test;

import java.io.IOException;
import static org.testng.AssertJUnit.assertTrue;

class ServerThread_Test {

    @Test
    void test_filtro_palavras() throws IOException {
        ServerThread res = new ServerThread(8888);
        assertTrue(res.filtro_palavras("ban"));
    }

    @Test
    void test_server_log() {
    }
}