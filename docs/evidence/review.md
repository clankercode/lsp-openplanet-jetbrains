# Independent source review

T3 native reviewer: Codex ocx/gpt-6.1-sol, high reasoning, ultrafast, task jetbrains-source-review-1. Read-only review against current sources and LSP4IJ 0.21.0 JAR.

No additional confirmed implementation/security/packaging bug beyond the already-known dynamic-root issue. Flagged unfinished verification.md, real screenshot, real process/mapping coverage, and insufficient initial/nested-root tests.

Response: verification.md and real screenshot/protocol proof are added before publication. Added IntelliJ VFS tests for initial roots, two plugins and configuration marker/fallback. The first VFS test failed with file:///src instead of file:///src/one; changed production discovery to use IntelliJ VFS and removed the unused NIO resolver. Full test/packaging and real editor checks follow the fix.
