package com.clankercode.openplanet;

import com.google.gson.*;
import java.nio.file.*;
import java.util.*;

/** Pure configuration policy, shared by the UI and connection provider. */
public final class ServerConfiguration {
  private ServerConfiguration() {}

  public static List<String> command(String executable) {
    String value = executable == null ? "" : executable.trim();
    return List.of(value.isEmpty() ? "openplanet-lsp" : value, "--lsp");
  }

  public static void validateExecutable(String executable) {
    String value = executable == null ? "" : executable.trim();
    if (value.isEmpty()) return;
    Path path = Path.of(value);
    if (!path.isAbsolute() || !Files.isRegularFile(path) || !Files.isExecutable(path)) {
      throw new IllegalArgumentException(
          "Server path must be an absolute executable file: " + value);
    }
  }

  public static JsonObject initializationOptions(String json) {
    if (json == null || json.isBlank()) return new JsonObject();
    JsonElement value;
    try {
      value = JsonParser.parseString(json);
    } catch (JsonParseException e) {
      throw new IllegalArgumentException("Initialization options must be valid JSON", e);
    }
    if (!value.isJsonObject())
      throw new IllegalArgumentException("Initialization options must be a JSON object");
    JsonObject result = value.getAsJsonObject();
    for (String key : result.keySet()) {
      if (!key.matches("[a-z][a-z0-9_]*"))
        throw new IllegalArgumentException("Use server snake_case keys: " + key);
    }
    return result;
  }

  public static boolean isManifest(String name) {
    return "info.toml".equals(name);
  }
}
