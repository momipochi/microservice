import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import federation from "@originjs/vite-plugin-federation";
// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    federation({
      name: "remoteApp",
      filename: "remoteEntry.js",
      // Modules to expose
      exposes: {
        "./Product": "./src/Product.tsx",
      },
      shared: ["react", "react-dom"],
    }),
  ],
  build: {
    modulePreload: false,
    target: "esnext",
    minify: false,
    cssCodeSplit: false,
  },
  preview: {
    port: 5174,
    strictPort: true,
  },
  server: {
    port: 5174,
    strictPort: true,
    host: true,
    origin: "http://0.0.0.0:5174",
  },
});
