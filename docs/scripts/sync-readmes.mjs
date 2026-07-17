import { existsSync, mkdirSync, readFileSync, writeFileSync } from 'node:fs'
import { dirname, join } from 'node:path'
import { fileURLToPath } from 'node:url'

const __dirname = dirname(fileURLToPath(import.meta.url))
const docsRoot = join(__dirname, '..')
const packagesRoot = join(docsRoot, '..', 'packages')
const pluginsOutDir = join(docsRoot, 'plugins')
const changelogOutDir = join(docsRoot, 'changelog')

const plugins = ['battery', 'sensors', 'systeminfo']

if (!existsSync(pluginsOutDir))
  mkdirSync(pluginsOutDir, { recursive: true })
if (!existsSync(changelogOutDir))
  mkdirSync(changelogOutDir, { recursive: true })

for (const name of plugins) {
  const readmePath = join(packagesRoot, name, 'README.md')
  const readmeContent = readFileSync(readmePath, 'utf-8')
  writeFileSync(join(pluginsOutDir, `${name}.md`), readmeContent)
  console.log(`synced ${name}/README.md -> docs/plugins/${name}.md`)

  const changelogPath = join(packagesRoot, name, 'CHANGELOG.md')
  const changelogContent = readFileSync(changelogPath, 'utf-8')
  writeFileSync(join(changelogOutDir, `${name}.md`), changelogContent)
  console.log(`synced ${name}/CHANGELOG.md -> docs/changelog/${name}.md`)
}
