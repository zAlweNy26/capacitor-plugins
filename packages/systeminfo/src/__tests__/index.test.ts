import { describe, expect, it } from 'vitest'

describe('systemInfo', () => {
  it('should be importable', async () => {
    const mod = await import('../index')
    expect(mod.SystemInfo).toBeDefined()
  })
})
