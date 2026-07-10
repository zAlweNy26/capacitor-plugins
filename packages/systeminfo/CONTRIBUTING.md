# Contributing

This guide provides instructions for contributing to this Capacitor plugin.

## Developing

### Local Setup

1. Fork and clone the repo.
1. Install the dependencies from the **repo root** (this plugin is part of a Bun workspace monorepo).

    ```shell
    bun install
    ```

1. Install SwiftLint if you're on macOS.

    ```shell
    brew install swiftlint
    ```

### Scripts

#### `bun run build` (repo root)

Building web assets happens once for all plugins from the **monorepo root**, not per-plugin. From the repo root, run:

```shell
bun run build
```

This compiles the TypeScript code from each plugin's `src/` using [tsdown](https://tsdown.dev) in workspace mode, producing ESM (`dist/index.mjs`), CJS (`dist/index.cjs`), and type declarations (`dist/index.d.mts` / `dist/index.d.cts`).

To generate plugin API documentation, run `bun run docgen` from the repo root (uses [`@capacitor/docgen`](https://github.com/ionic-team/capacitor-docgen)).

#### `bun run verify`

Validate the native iOS and Android projects build correctly. Web build/lint/test is handled by the root CI, not this script.

#### `bun run lint` / `bun run fmt`

Check formatting and code quality for this plugin, autoformat/autofix if possible.

This checks Prettier and SwiftLint. Linting of TypeScript code (ESLint) is handled at the monorepo root via `bun run lint`.

## Publishing

Publishing is automated via GitHub Actions and is not done manually with `npm publish`.

1. From the repo root, run this plugin's release script (e.g. `bun run release:sensors`), which uses `commit-and-tag-version` to bump the version, update `CHANGELOG.md`, and create a git tag.
1. Push the tag: `git push --follow-tags`.
1. Pushing a tag matching `capacitor-*@*` triggers the `release.yml` GitHub Actions workflow, which builds from the repo root and publishes this plugin to npm via [trusted publishing](https://docs.npmjs.com/trusted-publishers) (OIDC) — no npm token or manual `npm publish` needed.

> **Note**: The [`files`](https://docs.npmjs.com/cli/v7/configuring-npm/package-json#files) array in `package.json` specifies which files get published. If you rename files/directories or add files elsewhere, you may need to update it.
