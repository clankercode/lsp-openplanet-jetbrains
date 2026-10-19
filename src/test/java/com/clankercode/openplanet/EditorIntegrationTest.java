package com.clankercode.openplanet;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.redhat.devtools.lsp4ij.LanguageServerFactory;
import com.redhat.devtools.lsp4ij.server.ProcessDataProvider;
import java.util.List;

public class EditorIntegrationTest extends BasePlatformTestCase {
  public void testAngelScriptPsiAndSyntaxAreRegistered() {
    var file = myFixture.configureByText("Main.as", "void Main() { int value = 1; }");
    assertSame(AngelScriptLanguage.INSTANCE, file.getLanguage());
    assertSame(AngelScriptFileType.INSTANCE, file.getFileType());
    assertNotNull(file.getNode());
  }

  public void testOrdinaryTomlIsNotTakenOver() {
    var file = myFixture.configureByText("config.toml", "[meta]\nname=\"Example\"");
    assertNotSame(AngelScriptFileType.INSTANCE, file.getFileType());
    assertFalse(new ManifestMatcher().match(file.getVirtualFile(), getProject()));
    var manifest = myFixture.addFileToProject("info.toml", "[meta]\nname=\"Example\"");
    assertTrue(new ManifestMatcher().match(manifest.getVirtualFile(), getProject()));
  }

  public void testWorkspaceStrategyAllowsDynamicPluginRoots() {
    assertFalse(new OpenplanetWorkspaceStrategy().sendAllFoldersOnInitialization());
  }

  public void testWorkspaceStrategyKeepsInitialRootsAndSelectsEachPlugin() {
    var first =
        myFixture.addFileToProject("one/info.toml", "[meta]\nname=\"One\"").getVirtualFile();
    var source = myFixture.addFileToProject("one/src/Main.as", "void Main() {}").getVirtualFile();
    var second =
        myFixture.addFileToProject("two/info.toml", "[meta]\nname=\"Two\"").getVirtualFile();
    var strategy = new OpenplanetWorkspaceStrategy();
    var uris = com.redhat.devtools.lsp4ij.client.features.FileUriSupport.DEFAULT;
    assertEquals(
        strategy.getWorkspaceFolders(getProject(), uris),
        strategy.getInitialWorkspaceFolders(getProject(), uris));
    assertEquals(
        uris.toString(first.getParent()),
        strategy.getWorkspaceFolderForFile(source, getProject(), uris).getUri());
    assertEquals(
        uris.toString(second.getParent()),
        strategy.getWorkspaceFolderForFile(second, getProject(), uris).getUri());
  }

  public void testConfigMarkerAndUnmarkedSingleFileFallback() {
    var config = myFixture.addFileToProject("plugin/.openplanet-lsp.toml", "").getVirtualFile();
    var source = myFixture.addFileToProject("plugin/src/File.as", "").getVirtualFile();
    var loose = myFixture.addFileToProject("loose/File.as", "").getVirtualFile();
    var strategy = new OpenplanetWorkspaceStrategy();
    var uris = com.redhat.devtools.lsp4ij.client.features.FileUriSupport.DEFAULT;
    assertEquals(
        uris.toString(config.getParent()),
        strategy.getWorkspaceFolderForFile(source, getProject(), uris).getUri());
    assertNotNull(strategy.getWorkspaceFolderForFile(loose, getProject(), uris));
  }

  public void testFactoryUsesLsp4ijAndEmptyInitializationOptions() {
    OpenplanetSettings.getInstance().serverPath = "";
    OpenplanetSettings.getInstance().initializationOptions = "";
    LanguageServerFactory factory = new OpenplanetServerFactory();
    var provider = factory.createConnectionProvider(getProject());
    assertEquals(
        List.of("openplanet-lsp", "--lsp"), ((ProcessDataProvider) provider).getCommands());
    assertEquals("{}", provider.getInitializationOptions(null).toString());
    assertNotNull(factory.createClientFeatures().getWorkspaceFolderFeature());
  }
}
