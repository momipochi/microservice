import { defineConfig } from 'vite'
import viteReact from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import federation from '@originjs/vite-plugin-federation'

import { TanStackRouterVite } from '@tanstack/router-plugin/vite'
import { resolve } from 'node:path'

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
        './Listings': './src/components/Listings.tsx',
      },
      shared: ['react', 'react-dom', '@tanstack/react-query'],
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
    modulePreload: true,
    target: 'esnext',
    minify: true,
    cssCodeSplit: true,
  },
  preview: {
    port: 5176,
    strictPort: true,
  },
  server: {
    port: 5176,
    strictPort: true,
    host: true,
    origin: 'http://0.0.0.0:5176',
  },
})
