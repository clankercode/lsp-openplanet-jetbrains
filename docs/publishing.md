# Publishing

This repository can be built and installed without a Marketplace account. No npm package or paid JetBrains subscription is needed.

## GitHub release

1. Run `./gradlew --no-daemon --max-workers=2 clean test buildPlugin` with JDK 21.
2. Review `docs/verification.md` and the plugin ZIP. Verify it contains our plugin JAR and license texts, not IntelliJ or LSP4IJ binaries.
3. Bump `version` in `build.gradle.kts` and update documentation before subsequent releases.
4. Create a GitHub release for the reviewed commit and attach `build/distributions/openplanet-jetbrains-<version>.zip` and SHA-256 checksums.
5. Users install the ZIP through Plugins → Install Plugin from Disk. LSP4IJ is a separately declared required dependency.

## JetBrains Marketplace — human account required

No Marketplace submission has been made. Max must:

1. Sign in at https://plugins.jetbrains.com/ with the publishing JetBrains account and complete the vendor profile / applicable agreement.
2. Choose **Upload plugin**, upload the built ZIP, and create the listing for plugin ID `com.clankercode.openplanet`, display name **Openplanet**.
3. Use repository URL `https://github.com/clankercode/lsp-openplanet-jetbrains`, the README description, real editor screenshot, supported IDE range `243.*`, and license expression **Unlicense OR CC0-1.0**. Mention LSP4IJ and separate server installation prominently.
4. Review any Plugin Verifier findings, then submit for Marketplace review. The declared dependency is `com.redhat.devtools.lsp4ij`.
5. After acceptance, record the assigned Marketplace listing URL in README. There is no account-free registry PR for JetBrains.

For automated future uploads, create a scoped Marketplace upload token and place it in a CI secret; add a gated `publishPlugin` job only after the listing exists. Do not commit tokens or enable automatic publishing on unreviewed pushes. Plugin signing may be added using Marketplace's documented certificate setup. This initial workflow only builds/tests and publishes CI artifacts / documentation.
