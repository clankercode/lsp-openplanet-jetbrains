package com.clankercode.openplanet;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.nio.file.*;
import java.util.List;
import static org.junit.Assert.*;
public class ServerConfigurationTest {
    @Rule public TemporaryFolder temporary=new TemporaryFolder();
    @Test public void usesStdioAndNeverShellSplitsPaths() {
        assertEquals(List.of("openplanet-lsp","--lsp"),ServerConfiguration.command(""));
        assertEquals(List.of("openplanet-lsp","--lsp"),ServerConfiguration.command(null));
        assertEquals(List.of("/some folder/lsp","--lsp"),ServerConfiguration.command(" /some folder/lsp "));
    }
    @Test public void explicitMissingOrRelativeExecutableFailsWithoutFallback() throws Exception {
        for(String invalid: List.of("/missing/lsp","relative-lsp",temporary.getRoot().toString()))
            assertThrows(IllegalArgumentException.class,()->ServerConfiguration.validateExecutable(invalid));
        Path executable=temporary.newFile("server with spaces").toPath();
        assertTrue(executable.toFile().setExecutable(true));
        ServerConfiguration.validateExecutable(executable.toString());
        ServerConfiguration.validateExecutable("");
    }
    @Test public void defaultsInheritAndExplicitOptionsAreRetained() {
        assertEquals(0,ServerConfiguration.initializationOptions("").size());
        assertEquals(0,ServerConfiguration.initializationOptions(null).size());
        var options=ServerConfiguration.initializationOptions("{\"unused\":false,\"defines\":[\"TEST\"],\"game_target\":\"tmnext\"}");
        assertFalse(options.get("unused").getAsBoolean());
        assertEquals("TEST",options.getAsJsonArray("defines").get(0).getAsString());
        assertEquals("tmnext",options.get("game_target").getAsString());
    }
    @Test public void rejectsInvalidJsonShapesAndCamelCase() {
        for(String bad: List.of("{","[]","null","true","{\"serverPath\":1}"))
            assertThrows(IllegalArgumentException.class,()->ServerConfiguration.initializationOptions(bad));
    }
    @Test public void onlyExactManifestMatches() {
        assertTrue(ServerConfiguration.isManifest("info.toml"));
        for(String other: List.of("config.toml","info.toml.bak","Info.toml","Cargo.toml")) assertFalse(ServerConfiguration.isManifest(other));
    }
    @Test public void nearestMarkerWinsWithSingleFileAndProjectFallback() throws Exception {
        Path workspace=temporary.newFolder("workspace").toPath();
        Path plugin=Files.createDirectories(workspace.resolve("plugin/src"));
        Path source=plugin.resolve("Main.as");
        assertEquals(workspace,ServerConfiguration.root(source,workspace));
        assertEquals(plugin,ServerConfiguration.root(source,null));
        Files.createFile(workspace.resolve(".openplanet-lsp.toml"));
        assertEquals(workspace,ServerConfiguration.root(source,workspace));
        Files.createFile(plugin.getParent().resolve("info.toml"));
        assertEquals(plugin.getParent(),ServerConfiguration.root(source,workspace));
        Path outside=temporary.newFolder("outside").toPath().resolve("file.as");
        assertEquals(outside.getParent(),ServerConfiguration.root(outside,workspace));
    }
}
