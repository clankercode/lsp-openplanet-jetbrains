package com.clankercode.openplanet;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.redhat.devtools.lsp4ij.LanguageServerFactory;
import com.redhat.devtools.lsp4ij.server.*;
import com.redhat.devtools.lsp4ij.client.features.*;
import com.redhat.devtools.lsp4ij.features.workspaceFolder.*;
import org.jetbrains.annotations.NotNull;

public final class OpenplanetServerFactory implements LanguageServerFactory {
    public @NotNull StreamConnectionProvider createConnectionProvider(@NotNull Project project) {
        var settings = OpenplanetSettings.getInstance();
        String executable = settings.serverPath;
        String options = settings.initializationOptions;
        var provider = new OSProcessStreamConnectionProvider() {
            @Override public void start() throws CannotStartProcessException {
                try { ServerConfiguration.validateExecutable(executable); ServerConfiguration.initializationOptions(options); }
                catch (IllegalArgumentException e) { throw new CannotStartProcessException(e.getMessage()); }
                super.start();
            }
            @Override public Object getInitializationOptions(VirtualFile root) { return ServerConfiguration.initializationOptions(options); }
        };
        var command = new GeneralCommandLine(ServerConfiguration.command(executable));
        if (project.getBasePath() != null) command.withWorkDirectory(project.getBasePath());
        provider.setCommandLine(command);
        return provider;
    }
    public @NotNull LSPClientFeatures createClientFeatures() {
        var features = new LSPClientFeatures();
        features.setWorkspaceFolderFeature(new LSPWorkspaceFolderFeature() {
            @Override protected @NotNull WorkspaceFolderStrategy createStrategy() { return new OpenplanetWorkspaceStrategy(); }
        });
        return features;
    }
}
