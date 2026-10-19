# Openplanet for JetBrains IDEs

AngelScript editing for [Openplanet](https://openplanet.dev) using [openplanet-lsp](https://github.com/clankercode/lsp-openplanet) and the free [LSP4IJ](https://plugins.jetbrains.com/plugin/23257-lsp4ij) plugin. Works with IntelliJ IDEA Community; no Ultimate LSP license required.

- `.as` file association and lexical syntax highlighting.
- Server diagnostics, completion, hover, navigation, rename and signature help via LSP4IJ, subject to server capabilities.
- `info.toml` connects to the same server while retaining its existing TOML file type. Other TOML files are untouched.
- Nearest `info.toml` / `.openplanet-lsp.toml` workspace roots, project and single-file fallbacks.
- Explicit executable path and JSON initialization options under **Settings → Languages & Frameworks → Openplanet**.

## Install

1. Use IntelliJ IDEA **2024.3.x** (Community supported). Install **LSP4IJ 0.21.0** from Settings → Plugins → Marketplace; restart if asked. This first release intentionally declares platform build `243.*` only. Other JetBrains products and newer platform builds have not been verified.
2. Install the server separately: `npm install -g openplanet-lsp` or `cargo install openplanet-lsp`. Native archives are available from [server releases](https://github.com/clankercode/lsp-openplanet/releases). Verify `openplanet-lsp --version` (tested with 0.8.0).
3. Download the plugin ZIP from this repository's [Releases](https://github.com/clankercode/lsp-openplanet-jetbrains/releases), or build it below. In Settings → Plugins → ⚙ → **Install Plugin from Disk**, select the ZIP and restart.
4. Open your Openplanet plugin folder containing `info.toml`, then open a `.as` file. Use **View → Tool Windows → LSP Consoles** to inspect the Openplanet connection.

The executable runs as `openplanet-lsp --lsp` over stdio. There is no runtime downloader. Desktop-launched IDEs may have a different PATH from the terminal; set an absolute server path in Openplanet settings when needed. On Windows use the native `.exe` release or Cargo binary; npm `.cmd` launchers are not verified.

## Settings

**Server executable**: leave blank to resolve `openplanet-lsp` on the IDE process PATH, or enter an absolute executable file path. Spaces are supported. Do not add quotes or arguments. An invalid explicit path fails visibly; it never silently falls back to another binary.

**Initialization options**: blank or `{}` inherits server defaults and configuration files. To override a setting, supply a JSON object with the server's snake_case keys, for example:

```json
{
  "openplanet_dir": "/home/me/OpenplanetNext",
  "defines": ["MY_FEATURE"]
}
```

These are global IDE settings, so prefer per-plugin `.openplanet-lsp.toml` for project-specific options. Server precedence is: explicit initialization options → per-plugin config → parent workspace config → user `~/.config/openplanet-lsp/config.toml` → auto-detection. The plugin validates JSON object syntax and key spelling, while the server defines supported keys and value types. No default values are injected. Type databases are discovered by the server; no fixture database is bundled.

After applying changes, **stop and start Openplanet in LSP Consoles**. Existing server sessions keep the configuration they started with.

## Build and test

Requires **JDK 21**. Gradle 8.13 is pinned with its official SHA-256. Downloads require network access.

```sh
./gradlew --no-daemon --max-workers=2 test buildPlugin
```

Output: `build/distributions/openplanet-jetbrains-0.1.0.zip`. `gradle.properties` caps workers at two, disables parallel project builds, and limits Gradle's visible CPUs. Tests use one fork. To reuse an installed Community IDE: add `-PlocalIde=/absolute/path/to/idea`.

[Verification and actual test limits](docs/verification.md) · [API research](docs/api-research.md) · [Publishing / Marketplace handoff](docs/publishing.md)

## Troubleshooting

- **No diagnostics/completion:** confirm LSP4IJ is installed and enabled; open LSP Consoles and start Openplanet. Check its Log tab and the IDE event log. Confirm your file type is Openplanet AngelScript.
- **Server fails to start:** run the configured binary with `--version`, check executable permissions, and set its absolute path. An explicit path containing `~`, shell quotes or extra flags is not expanded.
- **Unknown Openplanet API:** point the server at your actual Openplanet installation or configure its type database paths in `.openplanet-lsp.toml`. A missing local game installation is not repaired by this plugin.
- **Wrong `.as` association:** Settings → Editor → File Types → Openplanet AngelScript. Resolve conflicts with ActionScript or another AngelScript plugin explicitly.
- **Manifest validation absent:** the filename must be exactly `info.toml` on a local filesystem. Install your preferred TOML plugin for TOML highlighting; this plugin does not replace it.
- **Newer IDE refuses ZIP:** the verified compatibility range is deliberately limited; do not bypass it without running the tests and GUI verification on that build.

## License

Original project code, documentation, and screenshots are available under **Unlicense OR CC0-1.0**, at your choice. Complete texts: [Unlicense](LICENSE) and [CC0](LICENSE-CC0). Dependencies and the Gradle wrapper keep their own licenses; see [third-party notices](THIRD_PARTY_NOTICES.md).
