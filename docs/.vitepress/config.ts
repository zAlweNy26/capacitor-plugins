import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'Capacitor Plugins',
  description: 'A collection of Capacitor plugins by zAlweNy26',
  base: '/capacitor-plugins/',
  lastUpdated: true,
  head: [
    ['link', { rel: 'icon', href: '/capacitor-plugins/capacitor-icon.svg', type: 'image/svg+xml' }],
  ],

  themeConfig: {
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Getting Started', link: '/getting-started' },
      { text: 'Plugins', link: '/plugins/battery' },
    ],

    sidebar: [
      {
        text: 'Getting Started',
        link: '/getting-started',
      },
      {
        text: 'Plugins',
        items: [
          { text: 'Battery', link: '/plugins/battery' },
          { text: 'Sensors', link: '/plugins/sensors' },
          { text: 'System Info', link: '/plugins/systeminfo' },
        ],
      },
      {
        text: 'Changelog',
        items: [
          { text: 'Battery', link: '/changelog/battery' },
          { text: 'Sensors', link: '/changelog/sensors' },
          { text: 'System Info', link: '/changelog/systeminfo' },
        ],
      },
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/zAlweNy26/capacitor-plugins' },
    ],

    search: {
      provider: 'local',
    },

    footer: {
      message: 'Built with VitePress',
      copyright: 'Copyright © 2026-present zAlweNy26',
    },
  },
})
