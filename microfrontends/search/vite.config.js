import { resolve } from 'node:path'
import { defineConfig } from 'vite'
import viteReact from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import federation from '@originjs/vite-plugin-federation'

import { TanStackRouterVite } from '@tanstack/router-plugin/vite'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    TanStackRouterVite({ autoCodeSplitting: true }),
    viteReact(),
    tailwindcss(),
    federation({
      name: 'remoteApp',
      filename: 'remoteEntry.js',
      // Modules to expose
      exposes: {
        './Search': './src/components/search.tsx',
      },
      shared: ['react', 'react-dom'],
    }),
  ],
  test: {
    globals: true,
    environment: 'jsdom',
  },
  resolve: {
    alias: {
      '@': resolve(__dirname, './src'),
    },
  },
  build: {
    modulePreload: false,
    target: 'esnext',
    minify: false,
    cssCodeSplit: false,
  },
  preview: {
    port: 5174,
    strictPort: true,
  },
  server: {
    port: 5175,
    strictPort: true,
    host: true,
    origin: 'http://0.0.0.0:5175',
  },
})
