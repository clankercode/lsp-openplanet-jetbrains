package com.clankercode.openplanet;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.redhat.devtools.lsp4ij.client.features.FileUriSupport;
import com.redhat.devtools.lsp4ij.features.workspaceFolder.ProjectWorkspaceFolderStrategy;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.jetbrains.annotations.NotNull;

/** Discover each opened plugin root while retaining the project root at initialization. */
public final class OpenplanetWorkspaceStrategy extends ProjectWorkspaceFolderStrategy {
  public OpenplanetWorkspaceStrategy() {
    super(true);
  }

  @Override
  public java.util.List<WorkspaceFolder> getInitialWorkspaceFolders(
      @NotNull Project project, @NotNull FileUriSupport uris) {
    return getWorkspaceFolders(project, uris);
  }

  @Override
  public WorkspaceFolder getWorkspaceFolderForFile(
      @NotNull VirtualFile file, @NotNull Project project, @NotNull FileUriSupport uris) {
    VirtualFile parent = file.isDirectory() ? file : file.getParent();
    VirtualFile projectRoot = null;
    for (VirtualFile current = parent; current != null; current = current.getParent()) {
      VirtualFile manifest = current.findChild("info.toml");
      VirtualFile config = current.findChild(".openplanet-lsp.toml");
      if ((manifest != null && !manifest.isDirectory())
          || (config != null && !config.isDirectory())) {
        return new WorkspaceFolder(uris.toString(current), current.getName());
      }
      if (current.getPath().equals(project.getBasePath())) projectRoot = current;
    }
    VirtualFile root = projectRoot == null ? parent : projectRoot;
    return root == null ? null : new WorkspaceFolder(uris.toString(root), root.getName());
  }
}
