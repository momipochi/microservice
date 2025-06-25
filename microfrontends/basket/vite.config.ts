import { defineConfig } from "vitest/config";
import viteReact from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import federation from "@originjs/vite-plugin-federation";

import { tanstackRouter } from "@tanstack/router-plugin/vite";
import { resolve } from "node:path";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    tanstackRouter({ target: "react", autoCodeSplitting: true }),
    viteReact(),
    tailwindcss(),
    federation({
      name: "remoteApp",
      filename: "remoteEntry.js",
      // Modules to expose
      exposes: {},
      shared: [
        "react",
        "react-dom",
        "@tanstack/react-query",
        "@tanstack/react-router",
      ],
    }),
  ],
  test: {
    globals: true,
    environment: "jsdom",
  },
  resolve: {
    alias: {
      "@": resolve(__dirname, "./src"),
    },
  },
  build: {
    modulePreload: false,
    target: "esnext",
    minify: false,
    cssCodeSplit: false,
  },
  preview: {
    port: 5176,
    strictPort: true,
  },
  server: {
    port: 5176,
    strictPort: true,
    host: true,
    origin: "http://0.0.0.0:5176",
  },
});
