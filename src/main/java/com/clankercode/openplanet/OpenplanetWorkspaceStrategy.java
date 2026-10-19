package com.clankercode.openplanet;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.*;
import com.redhat.devtools.lsp4ij.client.features.FileUriSupport;
import com.redhat.devtools.lsp4ij.features.workspaceFolder.ProjectWorkspaceFolderStrategy;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

/** Discover each opened plugin root while retaining the project root at initialization. */
public final class OpenplanetWorkspaceStrategy extends ProjectWorkspaceFolderStrategy {
    @Override public WorkspaceFolder getWorkspaceFolderForFile(@NotNull VirtualFile file, @NotNull Project project, @NotNull FileUriSupport uris) {
        if (!file.isInLocalFileSystem()) return super.getWorkspaceFolderForFile(file, project, uris);
        Path root = ServerConfiguration.root(Path.of(file.getPath()), project.getBasePath() == null ? null : Path.of(project.getBasePath()));
        VirtualFile folder = LocalFileSystem.getInstance().findFileByNioFile(root);
        return folder == null ? super.getWorkspaceFolderForFile(file, project, uris) : new WorkspaceFolder(uris.toString(folder), folder.getName());
    }
}
