// Original minimal Openplanet example for editor integration verification.
// The mistakes below are intentional: inspect their live LSP diagnostics.
void Main() {
    uint laps = "three";
    MissingFunction(laps);
}
