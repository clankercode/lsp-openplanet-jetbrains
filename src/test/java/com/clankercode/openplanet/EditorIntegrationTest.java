package com.clankercode.openplanet;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.redhat.devtools.lsp4ij.LanguageServerFactory;
import com.redhat.devtools.lsp4ij.server.ProcessDataProvider;
import java.util.List;
public class EditorIntegrationTest extends BasePlatformTestCase {
    public void testAngelScriptPsiAndSyntaxAreRegistered() {
        var file=myFixture.configureByText("Main.as","void Main() { int value = 1; }");
        assertSame(AngelScriptLanguage.INSTANCE,file.getLanguage());
        assertSame(AngelScriptFileType.INSTANCE,file.getFileType()); assertNotNull(file.getNode());
    }
    public void testOrdinaryTomlIsNotTakenOver() {
        var file=myFixture.configureByText("config.toml","[meta]\nname=\"Example\"");
        assertNotSame(AngelScriptFileType.INSTANCE,file.getFileType());
        assertFalse(new ManifestMatcher().match(file.getVirtualFile(),getProject()));
        var manifest=myFixture.addFileToProject("info.toml","[meta]\nname=\"Example\"");
        assertTrue(new ManifestMatcher().match(manifest.getVirtualFile(),getProject()));
    }
    public void testWorkspaceStrategyAllowsDynamicPluginRoots() {
        assertFalse(new OpenplanetWorkspaceStrategy().sendAllFoldersOnInitialization());
    }
    public void testFactoryUsesLsp4ijAndEmptyInitializationOptions() {
        OpenplanetSettings.getInstance().serverPath=""; OpenplanetSettings.getInstance().initializationOptions="";
        LanguageServerFactory factory=new OpenplanetServerFactory(); var provider=factory.createConnectionProvider(getProject());
        assertEquals(List.of("openplanet-lsp","--lsp"),((ProcessDataProvider)provider).getCommands());
        assertEquals("{}",provider.getInitializationOptions(null).toString());
        assertNotNull(factory.createClientFeatures().getWorkspaceFolderFeature());
    }
}
