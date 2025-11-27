import { defineConfig } from 'vitest/config'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  test: {
    environment: 'jsdom',
    globals: true,
    setupFiles: './setupTests.js',
    include: ['**/*.test.js', '**/*.test.jsx', '**/*.test.vue']
  }
})
