# Verification record

Date: 2026-10-19. Linux x86_64. This record distinguishes automated tests, real editor observations and untested surfaces.

## Pinned environment

- IntelliJ IDEA Community 2024.3.6, build IC-243.26574.91; portable distribution in `/tmp/openplanet-jetbrains-ide`.
- LSP4IJ 0.21.0, Marketplace update 1157565.
- openplanet-lsp 0.8.0 installed at `/home/xertrov/.local/bin/openplanet-lsp`.
- Build JDK: OpenJDK 21. Runtime: IntelliJ bundled JetBrains Runtime 21.0.6.
- Gradle wrapper 8.13. IntelliJ Platform Gradle plugin 2.5.0.
- `GRADLE_USER_HOME=/tmp/openplanet-jetbrains-gradle`. Worker maximum **2**, parallel projects **off**, test forks **1**, test/helper JVMs **2 visible processors**. No upstream server build, system updates or global IDE installation.

Archive SHA-256 values:

```text
ideaIC-2024.3.6.tar.gz  0a1fc2c59ee5c22b9c020ccc4b4e1b80e6a15350b8e3c3e4228036332b52e0dd
lsp4ij-0.21.0.zip      2ed125cc36620545c226186587d36ca4a8fc7c3db85e1ee5dfe0946b973e6caa
gradle-8.13-bin.zip    20f1b1176237254a6fc204d8434196fa11a4cfb387567519c61556e8710aed78
```

The IntelliJ and Gradle archive checksums match the publishers' checksum endpoints. LSP4IJ was fetched from JetBrains Marketplace; its checksum above records the downloaded bytes.

## Automated verification

Command used (from this repository):

```sh
JAVA_HOME=/usr/lib/jvm/java-21-openjdk \
GRADLE_USER_HOME=/tmp/openplanet-jetbrains-gradle \
./gradlew --no-daemon --max-workers=2 \
  -PlocalIde=/tmp/openplanet-jetbrains-ide test buildPlugin
```

Regression history: the initial 12-test run failed exactly the new dynamic workspace-root test (11 passed). The lazy strategy fix exposed a second regression test using IntelliJ's in-memory VFS: a `java.nio.file.Files` root lookup ignored virtual marker files. The implementation now discovers markers through IntelliJ VFS and preserves initial project roots while allowing dynamic plugin roots. Final run: **13 tests, 0 failures, 0 skipped**, followed by **BUILD SUCCESSFUL** in 58 seconds. Raw JUnit results and build output are in `docs/evidence/`. The ZIP contains only our two JARs; full Unlicense, CC0 and Gradle license texts are included in the main JAR.

## Actual editor observations

Private Xvfb display `:117`, 1440×1000. The portable IDE uses only `/tmp/openplanet-jetbrains-gui/{config,system,plugins,log}`. LSP4IJ and this plugin are loaded by the real IDE. The original `examples/diagnostics-demo` is opened as a project. No simulated editor UI or manufactured LSP messages are used.

A transparent test-only stdio relay records traffic while invoking the installed real server with `--lsp`. Recorded `initialize` has `initializationOptions: {}`. `Main.as` is sent with `languageId: angelscript`. The real server sends `undefined identifier MissingFunction` with severity 1 and source `openplanet-lsp`; the IDE displays the red underline and corresponding Problems entry. Further checks and final screenshot are recorded below.

## Limits

Only Linux x86_64 and IntelliJ Community 2024.3.6 were exercised. Windows/macOS, other JetBrains products, platform builds outside 243.*, remote filesystems and npm `.cmd` launchers are untested. This plugin therefore declares only `243.*` compatibility. A full JetBrains Plugin Verifier product matrix has not been run. Completion/navigation/signature features are provided by LSP4IJ but are not all asserted end-to-end. No Marketplace upload was attempted; an account is required (see publishing.md).

The initial IDE startup emitted a platform HiDPI preload warning before our plugin was installed, plus bundled-plugin warnings and host fontconfig warnings. These are recorded as environment noise, not suppressed or attributed to this integration. Gradle warns that 2.5.0 has newer versions; the pinned build remains intentional and reproducible.

## Final artifact

`build/distributions/openplanet-jetbrains-0.1.0.zip` SHA-256: `7f01f39b80e7835e816a068d9b2569fa0c7d56a25dca545d49712586767de0fd`. The final packaged ZIP was extracted into the temporary IDE profile for the screenshot run.

Final GUI mapping check: opened `Main.as`, `info.toml`, and `unrelated.toml` in the packaged plugin. Raw client traffic contains `didOpen` for AngelScript and `info.toml` as `toml`, and none for `unrelated.toml`. The unrelated file retained the IDE TOML file type and highlighting.

## Screenshot

[`images/intellij-diagnostics.png`](images/intellij-diagnostics.png) is a direct capture of the final package running in the actual IDE. The private window was resized to **1400×760** before capture to keep the source and Problems entry together. The image was inspected at full size and with code/diagnostic crops; the file was validated with Pillow. No editor facsimile, overlays or synthetic diagnostics were used.
