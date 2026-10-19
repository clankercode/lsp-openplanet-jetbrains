package com.clankercode.openplanet;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.redhat.devtools.lsp4ij.DocumentMatcher;
import org.jetbrains.annotations.NotNull;
public final class ManifestMatcher implements DocumentMatcher {
    public boolean match(@NotNull VirtualFile file, @NotNull Project project) {
        return !file.isDirectory() && file.isInLocalFileSystem() && ServerConfiguration.isManifest(file.getName());
    }
}
