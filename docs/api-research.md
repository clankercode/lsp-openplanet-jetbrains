# API and compatibility research

Research date: 2026-10-19. Primary sources:

- [LSP4IJ Developer Guide](https://github.com/redhat-developer/lsp4ij/blob/main/docs/DeveloperGuide.md): required plugin ID, factory, connection provider, language and filename mappings.
- [LSP4IJ LSP API](https://github.com/redhat-developer/lsp4ij/blob/main/docs/LSPApi.md): workspace-folder strategy extension.
- [Marketplace update API](https://plugins.jetbrains.com/api/plugins/23257/updates?size=5): 0.21.0, update 1157565, since-build 242.0, no upper bound.
- [IntelliJ custom-language guide](https://plugins.jetbrains.com/docs/intellij/custom-language-support.html).
- [Gradle IntelliJ Platform plugin](https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html).

Inspected LSP4IJ source commit `b7d8d9b94922ec044711802ee67cd790ca448d34` and checked the actual 0.21.0 release JAR signatures with `javap`. This matters because main may differ from the published binary. Source graph indexing was attempted, but the graph tool was listed without a callable function in this delegated environment; source and release JAR inspection provided the fallback.

Implementation uses `LanguageServerFactory#createConnectionProvider(Project)`, `OSProcessStreamConnectionProvider#setCommandLine(GeneralCommandLine)`, `start()` / `CannotStartProcessException`, and `getInitializationOptions(VirtualFile)`. LSP4IJ owns process shutdown and stderr handling. Arguments are passed as a list, without a shell. `languageMapping` sends `angelscript`; `fileNamePatternMapping patterns="info.toml"` plus a local-file matcher sends `toml` without taking over TOML file types.

`createClientFeatures()` supplies `LSPWorkspaceFolderFeature#createStrategy()` backed by a custom `ProjectWorkspaceFolderStrategy`. Initialization retains the IDE project root; opened files select the closest plugin/config marker, then project root, then containing folder. Tests cover nesting and fallback.

The language includes a lossless PSI bridge, lexical highlighting, explicit folding and parameter-info registrations. Semantics come from the LSP server, not a second parser in the IDE. No LSP4J or Gson JAR is bundled: these APIs come from LSP4IJ / the platform to avoid classloader conflicts.

The plugin is built against **IntelliJ IDEA Community 2024.3.6 (243.26574.91)**, with **JDK 21**, **LSP4IJ 0.21.0**, **Gradle 8.13**, **IntelliJ Platform Gradle plugin 2.5.0**. Descriptor compatibility is `243` through `243.*`, narrower than LSP4IJ's own compatibility. Expanding this range requires testing a newer IDE; it is not inferred from LSP4IJ's range.
