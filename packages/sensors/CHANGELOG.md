# Changelog

All notable changes to this project will be documented in this file. See [commit-and-tag-version](https://github.com/absolute-version/commit-and-tag-version) for commit guidelines.

## 1.0.2 (2026-07-09)

### ⚠ BREAKING CHANGES

* upgraded to Capacitor v8 (peer dependency bumped to `@capacitor/core >=8.0.0`)

### Code Refactoring

* migrated to a Bun workspace monorepo (build, lint, and test now run from the repo root via tsdown, ESLint flat config, and vitest)
