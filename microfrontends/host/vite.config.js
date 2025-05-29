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
      name: 'host',
      remotes: {
        productRemote: 'http://localhost:5176/assets/remoteEntry.js',
        searchRemote: 'http://localhost:5175/assets/remoteEntry.js',
      },
      shared: [
        'react',
        'react-dom',
        '@tanstack/react-query',
        '@tanstack/react-router',
      ],
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

  server: {
    port: 3000,
    strictPort: true,
    host: true,
    origin: 'http://localhost:3000',
  },
})
